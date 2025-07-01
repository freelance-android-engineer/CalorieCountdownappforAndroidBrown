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
    fun fetchAndStoreFoodItems(
        imageBase64: String,
        breakfastBox: Breakfast_Box_CIF17,
        callback: ResultCallback
    ) {
        val apiKey = context.getString(R.string.gemini_api_key)
        val url =
            "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=$apiKey"

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
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
            .build()

// Build the request
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
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "API call failed: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                    callback.onFailure()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (!response.isSuccessful || responseBody == null) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            context,
                            "Failed to get valid response from API",
                            Toast.LENGTH_LONG
                        ).show()
                        callback.onFailure()
                    }
                    return
                }

                try {
                    val contentText = JSONObject(responseBody)
                        .getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                    val cleanedText = contentText
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()
                    val jsonArray = JSONArray(cleanedText)

                    if (jsonArray.length() == 0) {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(context, "No food items detected.", Toast.LENGTH_SHORT)
                                .show()
                            callback.onFailure()
                        }
                        return
                    }

                    val foodItems = mutableListOf<Food_Item_CIF4>()

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
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
                    }

                    breakfastBox.food_item_list_two = ArrayList(foodItems)
                    insertAllFoodItems(breakfastBox.food_item_list_two);

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            context,
                            "Food items saved successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        callback.onSuccess()
                    }

                } catch (e: Exception) {
                    val errorDetails =
                        StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString()
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Error parsing response.", Toast.LENGTH_LONG).show()
                        callback.onFailure()
                    }
                }

            }
        })
    }

    fun insertAllFoodItems(foodItems: List<Food_Item_CIF4?>) {
        for (item in foodItems) {
            val rowId: Long = database.Insert_Food_Item_Row(item)
            Log.d("DB_INSERT", "Inserted item with row ID: $rowId")
        }
    }

}

interface ResultCallback {
    fun onSuccess()
    fun onFailure()
}