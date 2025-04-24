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

                val url =
                    URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-002:generateContent?key=$apiKey")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true

                conn.outputStream.use { os ->
                    os.write(requestBody.toString().toByteArray())
                }

                val response = conn.inputStream.bufferedReader().use { it.readText() }
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
