package ese.com.caloriecountdownappforandroidbrown

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.concurrent.TimeUnit

class FoodDetectionService(
    private val context: Context,
    private val database: SQLDatabase_Food_Items_CIF6
) {
    companion object {
        private const val TAG = "FoodDetectionService"
    }

    fun fetchAndStoreFoodItems(
        imageBase64: String,
        breakfastBox: Breakfast_Box_CIF17,
        callback: ResultCallback
    ) {
        Log.d(TAG, "========== Starting fetchAndStoreFoodItems ==========")
        Log.d(TAG, "Image base64 length: ${imageBase64.length}")

        val apiKey = context.getString(R.string.gemini_api_key)
        Log.d(TAG, "API Key retrieved: ${if (apiKey.isNotEmpty()) "Yes (length: ${apiKey.length})" else "No - EMPTY!"}")

        val model = "gemini-2.5-flash" // or "gemini-2.5-pro" for higher quality
        val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
        Log.d(TAG, "API URL: $url")

        Log.d(TAG, "Building request body...")
        val requestBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("inlineData", JSONObject().apply {
                                put("mimeType", "image/jpeg")
                                put("data", imageBase64)
                            })
                        })
                        put(JSONObject().apply {
                            put(
                                "text", """
                            Detect and separate each food item in the image.
                            If there is no food in the image, return an empty JSON array [].
                            For each food item, provide estimated nutritional values in this exact JSON format:

                            [{
                              "food_item": "FoodName",
                              "calories_per_100g": 0,
                              "fat_per_100g": 0,
                              "saturated_fat": 0,
                              "trans_fat": 0,
                              "protein_per_100g": 0,
                              "carbs_per_100g": 0,
                              "sugar_per_100g": 0,
                              "salt_per_100g": 0,
                              "fiber": 0,
                              "polyunsaturated": 0,
                              "monounsaturated": 0,
                              "cholesterol_mg": 0,
                              "sodium_mg": 0,
                              "potassium_mg": 0,
                              "vitamin_a_percent": 0,
                              "vitamin_c_percent": 0,
                              "calcium_percent": 0,
                              "iron_percent": 0
                            }]
                        """.trimIndent()
                            )
                        })
                    })
                })
            })
        }
        Log.d(TAG, "Request body created. Size: ${requestBody.toString().length} bytes")
        Log.d(TAG, "Creating OkHttpClient with 30s timeouts...")
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
            .build()

// Build the request
        Log.d(TAG, "Building HTTP POST request...")
        val request = Request.Builder()
            .url(url)
            .post(
                RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    requestBody.toString()
                )
            )
            .build()

// Make the API call with the custom client
        Log.d(TAG, "Sending API request to Gemini...")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "========== API CALL FAILED ==========")
                Log.e(TAG, "Error type: ${e.javaClass.simpleName}")
                Log.e(TAG, "Error message: ${e.message}")
                Log.e(TAG, "Full stack trace:")
                e.printStackTrace()

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "API call failed: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                    callback.onFailure()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "========== API RESPONSE RECEIVED ==========")
                Log.d(TAG, "Response code: ${response.code}")
                Log.d(TAG, "Response message: ${response.message}")
                Log.d(TAG, "Response successful: ${response.isSuccessful}")

                val responseBody = response.body?.string()
                Log.d(TAG, "Response body length: ${responseBody?.length ?: 0}")
                Log.d(TAG, "Response body preview: ${responseBody?.take(500)}")

                if (!response.isSuccessful || responseBody == null) {
                    Log.e(TAG, "========== API RESPONSE FAILED ==========")
                    Log.e(TAG, "Response code: ${response.code}")
                    Log.e(TAG, "Response body (full): $responseBody")

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            context,
                            "Failed to get valid response from API. Code: ${response.code}",
                            Toast.LENGTH_LONG
                        ).show()
                        callback.onFailure()
                    }
                    return
                }

                try {
                    Log.d(TAG, "Parsing response JSON...")
                    val responseJson = JSONObject(responseBody)
                    Log.d(TAG, "Response JSON keys: ${responseJson.keys().asSequence().toList()}")

                    val contentText = responseJson
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")

                    Log.d(TAG, "Extracted content text: $contentText")

                    val cleanedText = contentText
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()

                    Log.d(TAG, "Cleaned text: $cleanedText")

                    val jsonArray = JSONArray(cleanedText)
                    Log.d(TAG, "Parsed JSON array with ${jsonArray.length()} items")

                    if (jsonArray.length() == 0) {
                        Log.w(TAG, "No food items detected in the image")
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(context, "No food items detected.", Toast.LENGTH_SHORT)
                                .show()
                            callback.onFailure()
                        }
                        return
                    }

                    Log.d(TAG, "Processing ${jsonArray.length()} food items...")
                    val foodItems = mutableListOf<Food_Item_CIF4>()

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        Log.d(TAG, "Processing food item $i: ${item.optString("food_item")}")

                        val food = Food_Item_CIF4().apply {
                            Set_food_item_name(item.optString("food_item"))
                            Set_calories_per_100g(item.optDouble("calories_per_100g").toFloat())
                            Set_fat_per_100g(item.optDouble("fat_per_100g").toFloat())
                            Set_saturated_fat(item.optDouble("saturated_fat").toFloat())
                            Set_trans_fat(item.optDouble("trans_fat").toFloat())
                            Set_protein_per_100g(item.optDouble("protein_per_100g").toFloat())
                            Set_carbs_per_100g(item.optDouble("carbs_per_100g").toFloat())
                            Set_sugar_per_100g(item.optDouble("sugar_per_100g").toFloat())
                            Set_salt_per_100g(item.optDouble("salt_per_100g").toFloat())
                            Set_fiber(item.optDouble("fiber").toFloat())
                            Set_polyunsaturated(item.optDouble("polyunsaturated").toFloat())
                            Set_monounsaturated(item.optDouble("monounsaturated").toFloat())
                            Set_cholesterol_mg(item.optDouble("cholesterol_mg").toFloat())
                            Set_sodium_mg(item.optDouble("sodium_mg").toFloat())
                            Set_potassium_mg(item.optDouble("potassium_mg").toFloat())
                            Set_vitamin_a_percent(item.optDouble("vitamin_a_percent").toFloat())
                            Set_vitamin_c_percent(item.optDouble("vitamin_c_percent").toFloat())
                            Set_calcium_percent(item.optDouble("calcium_percent").toFloat())
                            Set_iron_percent(item.optDouble("iron_percent").toFloat())
                        }
                        foodItems.add(food)
                        Log.d(TAG, "Added food item: ${food.Get_food_item_name()}")
                    }

                    Log.d(TAG, "All food items parsed. Total: ${foodItems.size}")
                    breakfastBox.food_item_list_two = ArrayList(foodItems)

                    Log.d(TAG, "Inserting food items into database...")
                    insertAllFoodItems(breakfastBox.food_item_list_two)
                    Log.d(TAG, "Food items inserted successfully")

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            context,
                            "Food items saved successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        callback.onSuccess()
                    }
                    Log.d(TAG, "========== Process completed successfully ==========")

                } catch (e: Exception) {
                    Log.e(TAG, "========== EXCEPTION DURING PARSING ==========")
                    Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
                    Log.e(TAG, "Exception message: ${e.message}")

                    val errorDetails =
                        StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString()
                    Log.e(TAG, "Full stack trace:\n$errorDetails")

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Error parsing response: ${e.message}", Toast.LENGTH_LONG).show()
                        callback.onFailure()
                    }
                }

            }
        })
    }

    fun insertAllFoodItems(foodItems: List<Food_Item_CIF4?>) {
        Log.d(TAG, "Inserting ${foodItems.size} food items into database...")
        for ((index, item) in foodItems.withIndex()) {
            try {
                val rowId: Long = database.Insert_Food_Item_Row(item)
                Log.d(TAG, "Inserted item $index (${item?.Get_food_item_name()}) with row ID: $rowId")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to insert item $index: ${e.message}")
                e.printStackTrace()
            }
        }
        Log.d(TAG, "Database insertion completed")
    }

    /**
     * Simplified method to fetch food note data (name, calories, quantity) from an image
     * for adding to the food notes table
     */
    fun fetchFoodNoteData(
        imageBase64: String,
        callback: FoodNoteCallback
    ) {
        Log.d(TAG, "========== Starting fetchFoodNoteData ==========")
        Log.d(TAG, "Image base64 length: ${imageBase64.length}")

        val apiKey = context.getString(R.string.gemini_api_key)
        Log.d(TAG, "API Key retrieved: ${if (apiKey.isNotEmpty()) "Yes (length: ${apiKey.length})" else "No - EMPTY!"}")

        val model = "gemini-2.5-flash"
        val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
        Log.d(TAG, "API URL: $url")

        Log.d(TAG, "Building request body for food note data...")
        val requestBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("inlineData", JSONObject().apply {
                                put("mimeType", "image/jpeg")
                                put("data", imageBase64)
                            })
                        })
                        put(JSONObject().apply {
                            put(
                                "text", """
                            Analyze this food image and provide ONLY the following information in JSON format.
                            If multiple food items are present, provide data for the main/largest item only.
                            If no food is detected, return an empty object {}.

                            Required format:
                            {
                              "food_name": "name of the food item",
                              "calories": approximate total calories (number only, no units),
                              "quantity": approximate quantity or serving size (e.g., "1 plate", "200g", "1 cup", "1 piece")
                            }

                            Important:
                            - calories should be total calories for the visible portion, not per 100g
                            - Be concise with food_name
                            - Use common serving descriptions for quantity
                        """.trimIndent()
                            )
                        })
                    })
                })
            })
        }
        Log.d(TAG, "Request body created. Size: ${requestBody.toString().length} bytes")

        Log.d(TAG, "Creating OkHttpClient with 30s timeouts...")
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Log.d(TAG, "Building HTTP POST request...")
        val request = Request.Builder()
            .url(url)
            .post(
                RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    requestBody.toString()
                )
            )
            .build()

        Log.d(TAG, "Sending API request to Gemini for food note data...")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "========== API CALL FAILED (Food Note) ==========")
                Log.e(TAG, "Error type: ${e.javaClass.simpleName}")
                Log.e(TAG, "Error message: ${e.message}")
                e.printStackTrace()

                Handler(Looper.getMainLooper()).post {
                    callback.onFailure("Network error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "========== API RESPONSE RECEIVED (Food Note) ==========")
                Log.d(TAG, "Response code: ${response.code}")
                Log.d(TAG, "Response successful: ${response.isSuccessful}")

                val responseBody = response.body?.string()
                Log.d(TAG, "Response body length: ${responseBody?.length ?: 0}")

                if (!response.isSuccessful || responseBody == null) {
                    Log.e(TAG, "========== API RESPONSE FAILED ==========")
                    Log.e(TAG, "Response code: ${response.code}")
                    Log.e(TAG, "Response body: $responseBody")

                    Handler(Looper.getMainLooper()).post {
                        callback.onFailure("API Error: ${response.code}")
                    }
                    return
                }

                try {
                    Log.d(TAG, "Parsing response JSON...")
                    val responseJson = JSONObject(responseBody)

                    val contentText = responseJson
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")

                    Log.d(TAG, "Extracted content text: $contentText")

                    val cleanedText = contentText
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()

                    Log.d(TAG, "Cleaned text: $cleanedText")

                    val foodData = JSONObject(cleanedText)

                    if (foodData.length() == 0) {
                        Log.w(TAG, "No food detected in the image")
                        Handler(Looper.getMainLooper()).post {
                            callback.onFailure("No food detected in the image")
                        }
                        return
                    }

                    val foodName = foodData.optString("food_name", "Unknown Food")
                    val calories = foodData.optString("calories", "0")
                    val quantity = foodData.optString("quantity", "1 serving")

                    Log.d(TAG, "Parsed food data: name=$foodName, calories=$calories, quantity=$quantity")

                    Handler(Looper.getMainLooper()).post {
                        callback.onSuccess(foodName, calories, quantity)
                    }
                    Log.d(TAG, "========== Food note data extraction completed successfully ==========")

                } catch (e: Exception) {
                    Log.e(TAG, "========== EXCEPTION DURING PARSING ==========")
                    Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
                    Log.e(TAG, "Exception message: ${e.message}")
                    e.printStackTrace()

                    Handler(Looper.getMainLooper()).post {
                        callback.onFailure("Failed to parse response: ${e.message}")
                    }
                }
            }
        })
    }

}

interface ResultCallback {
    fun onSuccess()
    fun onFailure()
}

interface FoodNoteCallback {
    fun onSuccess(foodName: String, calories: String, quantity: String)
    fun onFailure(error: String)
}