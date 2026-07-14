package ese.com.caloriecountdownappforandroidbrown

import android.app.Activity
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.regex.*
import java.io.IOException

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.util.concurrent.TimeUnit


object GeminiApiService {

    private const val BACKEND_URL = "https://api.carbonemissionstrading.eu/api/v1/gemini/generate"

    fun fetchNutritionData(
        context: Context,
        foodName: String,
        onResult: (Food_Item_CIF4) -> Unit,
        onError: (String) -> Unit
    ) {
        Thread {
            try {
                Log.d("NutritionInfo", "Starting API request")

                val prompt = """
Provide only the nutritional values per 100 grams or milliliters for $foodName in the following format, with no additional text, context, or disclaimers:

Calories:
Protein:
Fat:
Saturated Fat:
Trans Fat:
Carbohydrates:
Fiber:
Sugar:
Salt:
""".trimIndent()

                val contentsArray = JSONArray().put(
                    JSONObject().put("role", "user").put(
                        "parts", JSONArray().put(
                            JSONObject().put("text", prompt)
                        )
                    )
                )

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("model", "gemini-2.5-flash")
                    .addFormDataPart("temperature", "0.4")
                    .addFormDataPart("maxOutputTokens", "2048")
                    .addFormDataPart("contents", contentsArray.toString())
                    .build()

                val request = Request.Builder()
                    .url(BACKEND_URL)
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val responseCode = response.code
                Log.d("NutritionInfo", "Response Code: $responseCode")

                if (!response.isSuccessful) {
                    val errorResponse = response.body?.string() ?: "No error details"
                    Log.e("NutritionError", "API Error ($responseCode): $errorResponse")
                    throw IOException("API request failed with code $responseCode: $errorResponse")
                }

                val responseStr = response.body?.string() ?: ""
                Log.d("NutritionInfo", "Response: $responseStr")

                val responseJson = JSONObject(responseStr)
                var contentText: String? = null

                // Backend wraps response in data object
                val dataObj = responseJson.optJSONObject("data")
                if (dataObj != null) {
                    val text = dataObj.optString("text", "")
                    if (text.isNotEmpty()) contentText = text
                }

                // Fallback: parse from candidates (data.raw or root)
                if (contentText.isNullOrEmpty()) {
                    val rawObj = dataObj?.optJSONObject("raw") ?: responseJson
                    val parts = rawObj.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")

                    for (i in parts.length() - 1 downTo 0) {
                        val part = parts.getJSONObject(i)
                        if (!part.optBoolean("thought", false)) {
                            contentText = part.optString("text")
                            break
                        }
                    }
                    if (contentText == null) {
                        contentText = parts.getJSONObject(parts.length() - 1).getString("text")
                    }
                }


                val item: Food_Item_CIF4 = FoodItemParser.fromGeminiText(contentText, foodName)
                Log.d(
                    "NutritionInfo",
                    "item: ca;lorie count ${item.Get_calorie_value()}  fat count ${item.Get_fat_per_100g()} protein count ${item.Get_protein_per_100g()}"
                )

                (context as? Activity)?.runOnUiThread {
                    onResult(item)
                }

                // Background: check backend for duplicates and upload if new
                syncAiResultToBackendIfNew(context, item)

            } catch (e: Exception) {
                val errorDetails =
                    StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString()
                Log.e("NutritionError", errorDetails)
                (context as? Activity)?.runOnUiThread {
                    onError(errorDetails)
                }
            }
        }.start()
    }


    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @JvmStatic
    fun calculateCalories(context: Context, prompt: String, callback: CalorieCallback) {

        Log.d("GeminiApiService", "calculateCalories() called with prompt: $prompt")

        val contentsArray = JSONArray()
        val partsArray = JSONArray()
        val textObject = JSONObject()
        textObject.put("text", prompt)
        partsArray.put(textObject)

        val contentObject = JSONObject()
        contentObject.put("role", "user")
        contentObject.put("parts", partsArray)
        contentsArray.put(contentObject)

        Log.d("GeminiApiService", "Contents JSON: $contentsArray")

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("model", "gemini-2.5-flash")
            .addFormDataPart("temperature", "0.4")
            .addFormDataPart("maxOutputTokens", "2048")
            .addFormDataPart("contents", contentsArray.toString())
            .build()

        val request = Request.Builder()
            .url(BACKEND_URL)
            .post(body)
            .build()

        Log.d("GeminiApiService", "Sending request to backend API")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GeminiCalc", "[calculateCalories] Request failed: ${e::class.java.simpleName}: ${e.message}", e)
                (context as? Activity)?.runOnUiThread {
                    callback.onResult(null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("GeminiCalc", "[calculateCalories] Response received, status=${response.code}")

                response.use {
                    if (!response.isSuccessful) {
                        val errorBody = response.body?.string() ?: "No error body"
                        Log.e("GeminiCalc", "[calculateCalories] HTTP ${response.code}: $errorBody")
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                        return
                    }

                    val responseData = response.body?.string()
                    if (responseData.isNullOrEmpty()) {
                        Log.e("GeminiCalc", "[calculateCalories] Response body is null/empty")
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                        return
                    }

                    Log.d("GeminiCalc", "[calculateCalories] Raw response: $responseData")

                    try {
                        val jsonResponse = JSONObject(responseData)
                        var contentText: String? = null

                        // Backend wraps response in data object
                        val dataObj = jsonResponse.optJSONObject("data")
                        if (dataObj != null) {
                            val text = dataObj.optString("text", "")
                            if (text.isNotEmpty()) contentText = text
                        }

                        // Fallback: try parsing from candidates (data.raw or root)
                        if (contentText.isNullOrEmpty()) {
                            val rawObj = dataObj?.optJSONObject("raw") ?: jsonResponse
                            val candidates = rawObj.optJSONArray("candidates")
                            val parts = candidates?.getJSONObject(0)
                                ?.getJSONObject("content")
                                ?.getJSONArray("parts")

                            if (parts != null && parts.length() > 0) {
                                for (i in parts.length() - 1 downTo 0) {
                                    val part = parts.getJSONObject(i)
                                    if (!part.optBoolean("thought", false)) {
                                        contentText = part.optString("text")
                                        break
                                    }
                                }
                                if (contentText == null) {
                                    contentText = parts.getJSONObject(parts.length() - 1).optString("text")
                                }
                            }
                        }

                        Log.d("GeminiCalc", "[calculateCalories] Parsed text: $contentText")

                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(contentText)
                        }
                    } catch (ex: Exception) {
                        Log.e("GeminiCalc", "[calculateCalories] Parse exception: ${ex.message}", ex)
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                    }
                }
            }
        })
    }


    @JvmStatic
    fun analyzeMemo(context: Context, prompt: String, imageBase64: String?, callback: CalorieCallback) {
        Log.d("GeminiApiService", "analyzeMemo() called, hasImage=${imageBase64 != null}")

        val contentsArray = JSONArray()
        val partsArray = JSONArray()
        val textObject = JSONObject()
        textObject.put("text", prompt)
        partsArray.put(textObject)

        val contentObject = JSONObject()
        contentObject.put("role", "user")
        contentObject.put("parts", partsArray)
        contentsArray.put(contentObject)

        val bodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("model", "gemini-2.5-flash")
            .addFormDataPart("temperature", "0.4")
            .addFormDataPart("maxOutputTokens", "2048")
            .addFormDataPart("contents", contentsArray.toString())

        if (imageBase64 != null) {
            val imageBytes = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT)
            bodyBuilder.addFormDataPart(
                "image", "memo_image.jpg",
                imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            )
        }

        val request = Request.Builder()
            .url(BACKEND_URL)
            .post(bodyBuilder.build())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GeminiApiService", "analyzeMemo request failed: ${e.message}", e)
                (context as? Activity)?.runOnUiThread {
                    callback.onResult(null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        Log.e("GeminiApiService", "analyzeMemo unsuccessful response: ${response.code}")
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                        return
                    }

                    val responseData = response.body?.string()
                    try {
                        val jsonResponse = JSONObject(responseData)
                        var contentText: String? = null

                        val dataObj = jsonResponse.optJSONObject("data")
                        if (dataObj != null) {
                            val text = dataObj.optString("text", "")
                            if (text.isNotEmpty()) contentText = text
                        }

                        if (contentText.isNullOrEmpty()) {
                            val rawObj = dataObj?.optJSONObject("raw") ?: jsonResponse
                            val candidates = rawObj.optJSONArray("candidates")
                            val parts = candidates?.getJSONObject(0)
                                ?.getJSONObject("content")
                                ?.getJSONArray("parts")

                            if (parts != null && parts.length() > 0) {
                                for (i in parts.length() - 1 downTo 0) {
                                    val part = parts.getJSONObject(i)
                                    if (!part.optBoolean("thought", false)) {
                                        contentText = part.optString("text")
                                        break
                                    }
                                }
                                if (contentText == null) {
                                    contentText = parts.getJSONObject(parts.length() - 1).optString("text")
                                }
                            }
                        }

                        Log.d("GeminiApiService", "analyzeMemo parsed text: $contentText")
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(contentText)
                        }
                    } catch (ex: Exception) {
                        Log.e("GeminiApiService", "Exception parsing analyzeMemo response", ex)
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                    }
                }
            }
        })
    }


    interface CalorieCallback {
        fun onResult(result: String?)
    }

    /**
     * Checks the backend for an existing food item matching [item]'s name.
     * If no match is found, uploads the AI result to the backend.
     * If a match exists, does nothing (prevents duplicates).
     * Runs entirely in the background — does not affect the user-facing AI response.
     */
    private fun syncAiResultToBackendIfNew(context: Context, item: Food_Item_CIF4) {
        val foodName = item.Get_food_item_name()
        if (foodName.isNullOrBlank()) {
            Log.d("AiBackendSync", "Skipping sync: food name is empty")
            return
        }
        if (item.Get_calories_per_100g() <= 0) {
            Log.d("AiBackendSync", "Skipping sync: calories <= 0 for $foodName")
            return
        }
        if (!NetworkUtil.isInternetAvailable(context)) {
            Log.d("AiBackendSync", "Skipping sync: no network for $foodName")
            return
        }

        val apiClient = SQLHeavyClientType008(context)

        // Step 1: Search backend for existing entry
        apiClient.searchFoodParsed(foodName, object : FoodSearchCallback {
            override fun onResult(results: ArrayList<Food_Item_CIF4>) {
                if (results.isNotEmpty()) {
                    Log.d("AiBackendSync", "Food already exists in backend: $foodName (${results.size} match(es))")
                    return
                }

                // Step 2: No match found — format and upload
                Log.d("AiBackendSync", "No match in backend, uploading: $foodName")
                val foodData = HashMap<String, Any>()
                foodData["food_item_name"] = foodName.trim()
                foodData["calories_per_100g"] = item.Get_calories_per_100g().toDouble()
                foodData["fat_per_100g"] = item.Get_fat_per_100g().toDouble()
                foodData["saturated_fat"] = item.Get_saturated_fat().toDouble()
                foodData["trans_fat"] = item.Get_trans_fat().toDouble()
                foodData["protein_per_100g"] = item.Get_protein_per_100g().toDouble()
                foodData["carbs_per_100g"] = item.Get_carbs_per_100g().toDouble()
                foodData["sugar_per_100g"] = item.Get_sugar_per_100g().toDouble()
                foodData["salt_per_100g"] = item.Get_salt_per_100g().toDouble()
                foodData["fiber"] = item.Get_fiber().toDouble()
                foodData["quantity"] = "100g"

                apiClient.addFoodItem(foodData, object : ApiResultCallback {
                    override fun onSuccess(response: String?) {
                        Log.d("AiBackendSync", "AI result uploaded to backend: $foodName")
                    }

                    override fun onFailure() {
                        Log.w("AiBackendSync", "Failed to upload AI result to backend: $foodName")
                    }
                })
            }
        })
    }

}


object FoodItemParser {
    fun fromGeminiText(text: String?, foodName: String): Food_Item_CIF4 {
        val item = Food_Item_CIF4()

        if (text.isNullOrBlank()) return item

        val pattern =
            Pattern.compile("(?i)(calories|protein|fat|saturated fat|trans fat|salt|carbohydrates|fiber|sugar):\\s*([\\d.]+)")
        val matcher = pattern.matcher(text)

        while (matcher.find()) {
            val label = matcher.group(1).lowercase(Locale.getDefault()).trim()
            val value = matcher.group(2).toFloat()

            when (label) {
                "calories" -> {
                    item.Set_calories_per_100g(value)
                    item.Set_calorie_value(value.toInt())
                }

                "protein" -> item.Set_protein_per_100g(value)
                "fat" -> item.Set_fat_per_100g(value)
                "saturated fat" -> item.Set_saturated_fat(value)
                "trans fat" -> item.Set_trans_fat(value)
                "salt" -> {
                    item.Set_salt_per_100g(value)
                    item.Set_sodium_mg(value * 1000f) // g to mg
                }

                "carbohydrates" -> item.Set_carbs_per_100g(value)
                "fiber" -> item.Set_fiber(value)
                "sugar" -> item.Set_sugar_per_100g(value)
            }
        }

        // Set name and other fields
        val cleanName = foodName.trim()
        item.Set_food_item_name(cleanName)
        item.Set_Image_File("data/image_src_$cleanName")

        val namesList = arrayListOf(cleanName)
        item.Set_reserve_food_item_name_list(namesList)

        item.Set_category("ING")
        item.Set_food_type("ING")
        item.Set_Barcode(1010101011L)
        item.Set_grams_per_serving_portion(100f)
        item.Set_weight(100)

        return item
    }

}
