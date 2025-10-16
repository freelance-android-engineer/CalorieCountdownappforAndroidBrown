package ese.com.caloriecountdownappforandroidbrown

import android.app.Activity
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.regex.*
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


object GeminiApiService {
    fun fetchNutritionData(
        context: Context,
        foodName: String,
        onResult: (Food_Item_CIF4) -> Unit,
        onError: (String) -> Unit
    ) {
        Thread {
            try {
                Log.d("NutritionInfo", "Starting API request")

                val apiKey = context.getString(R.string.gemini_api_key)

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


                val requestBody = JSONObject().apply {
                    put(
                        "contents", JSONArray().put(
                            JSONObject().put("role", "user").put(
                                "parts", JSONArray().put(
                                    JSONObject().put("text", prompt)
                                )
                            )
                        )
                    )
                }

                val model = "gemini-2.5-flash" // or use "gemini-2.5-pro" for higher quality
                val url =
                    URL("https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true

                conn.outputStream.use { os ->
                    os.write(requestBody.toString().toByteArray())
                }

                // Check response code before reading
                val responseCode = conn.responseCode
                Log.d("NutritionInfo", "Response Code: $responseCode")

                val response = if (responseCode in 200..299) {
                    conn.inputStream.bufferedReader().use { it.readText() }
                } else {
                    val errorResponse = conn.errorStream?.bufferedReader()?.use { it.readText() }
                        ?: "No error details"
                    Log.e("NutritionError", "API Error ($responseCode): $errorResponse")
                    throw IOException("API request failed with code $responseCode: $errorResponse")
                }

                Log.d("NutritionInfo", "Response: $response")

                val responseJson = JSONObject(response)
                val contentText = responseJson.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")


                val item: Food_Item_CIF4 = FoodItemParser.fromGeminiText(contentText, foodName)
                Log.d(
                    "NutritionInfo",
                    "item: ca;lorie count ${item.Get_calorie_value()}  fat count ${item.Get_fat_per_100g()} protein count ${item.Get_protein_per_100g()}"
                )

                (context as? Activity)?.runOnUiThread {
                    onResult(item)
                }

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


    private val client = OkHttpClient()

    @JvmStatic
    fun calculateCalories(context: Context, prompt: String, callback: CalorieCallback) {
        val apiKey = context.getString(R.string.gemini_api_key)

        Log.d("GeminiApiService", "🔥 calculateCalories() called with prompt: $prompt")

        val jsonBody = JSONObject()
        val contentsArray = JSONArray()
        val partsArray = JSONArray()
        val textObject = JSONObject()
        textObject.put("text", prompt)
        partsArray.put(textObject)

        val contentObject = JSONObject()
        contentObject.put("parts", partsArray)
        contentsArray.put(contentObject)
        jsonBody.put("contents", contentsArray)

        Log.d("GeminiApiService", "📦 Request body JSON: $jsonBody")

        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonBody.toString()
        )


        val model = "gemini-2.5-flash" // or "gemini-2.5-pro" for higher quality
        val url =
            "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"


        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        Log.d("GeminiApiService", "🌍 Sending request to Gemini API")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GeminiApiService", "❌ Request failed: ${e.message}", e)
                (context as? Activity)?.runOnUiThread {
                    callback.onResult(null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("GeminiApiService", "✅ Response received, status=${response.code}")

                response.use {
                    if (!response.isSuccessful) {
                        Log.e("GeminiApiService", "⚠️ Unsuccessful response: ${response.code}")
                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(null)
                        }
                        return
                    }

                    val responseData = response.body?.string()
                    Log.d("GeminiApiService", "📥 Raw response: $responseData")

                    try {
                        val jsonResponse = JSONObject(responseData)
                        val candidates = jsonResponse.optJSONArray("candidates")
                        val contentText = candidates?.getJSONObject(0)
                            ?.getJSONObject("content")
                            ?.getJSONArray("parts")
                            ?.getJSONObject(0)
                            ?.optString("text")

                        Log.d("GeminiApiService", "🍽 Parsed text: $contentText")

                        (context as? Activity)?.runOnUiThread {
                            callback.onResult(contentText)
                        }
                    } catch (ex: Exception) {
                        Log.e("GeminiApiService", "❌ Exception parsing response", ex)
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
