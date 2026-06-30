package ese.com.caloriecountdownappforandroidbrown

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class SQLHeavyClientType008(private val context: Context) {

    companion object {
        private const val BASE_URL = "https://api.carbonemissionstrading.eu/api/v1"
        private const val ENDPOINT_FOOD_SEARCH = "$BASE_URL/food/search"
        private const val ENDPOINT_ADD_FOOD = "$BASE_URL/food"
        private const val ENDPOINT_BALANCE_SYNC = "$BASE_URL/balance/sync"
        private const val ENDPOINT_DAYEND_SYNC = "$BASE_URL/balance/dayend"
        private const val CONTENT_TYPE_JSON = "application/json"
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // -------------------- GENERIC POST REQUEST --------------------
    private fun postRequestAsync(url: String, jsonBody: JSONObject, callback: ApiResultCallback) {
        Thread {
            try {
                val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = RequestBody.create(mediaType, jsonBody.toString())

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build()

                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    if (!response.isSuccessful) {
                        println("❌ HTTP Error: ${response.code} - ${response.message}")
                        println("📤 Sent JSON: ${jsonBody.toString(2)}")  // log JSON for debug
                        callback.onFailure()
                        return@use
                    }
                    println("✔️ Response: $responseBody")
                    callback.onSuccess(responseBody)
                }
            } catch (e: Exception) {
                println("💥 Exception Type: ${e::class.java.name}")
                e.printStackTrace()
                callback.onFailure()
            }
        }.start()
    }


    // -------------------- FOOD APIs --------------------

    /** 🔍 Search for food item by name (raw JSON) */
    fun searchFood(foodName: String, callback: ApiResultCallback) {
        val json = JSONObject().apply {
            put("@context", "https://schema.org")
            put("@type", "SearchAction")
            put("query", JSONObject().apply {
                put("food_item_name", foodName)
            })
        }

        println("🔎 Searching food: $foodName $json")
        postRequestAsync(ENDPOINT_FOOD_SEARCH, json, callback)
    }

    /** 🔍 Search for food item and parse results into Food_Item_CIF4 objects */
    fun searchFoodParsed(foodName: String, callback: FoodSearchCallback) {
        searchFood(foodName, object : ApiResultCallback {
            override fun onSuccess(response: String?) {
                val results = ArrayList<Food_Item_CIF4>()
                if (!response.isNullOrEmpty()) {
                    try {
                        val jsonArray = JSONObject(response).optJSONArray("results")
                        if (jsonArray != null) {
                            for (i in 0 until jsonArray.length()) {
                                val obj = jsonArray.getJSONObject(i)
                                val item = Food_Item_CIF4()

                                obj.optString("food_item_name")?.let { item.Set_food_item_name(it) }
                                item.Set_calories_per_100g(obj.optDouble("calories_per_100g", 0.0).toFloat())
                                item.Set_fat_per_100g(obj.optDouble("fat_per_100g", 0.0).toFloat())
                                item.Set_saturated_fat(obj.optDouble("saturated_fat", 0.0).toFloat())
                                item.Set_trans_fat(obj.optDouble("trans_fat", 0.0).toFloat())
                                item.Set_protein_per_100g(obj.optDouble("protein_per_100g", 0.0).toFloat())
                                item.Set_carbs_per_100g(obj.optDouble("carbs_per_100g", 0.0).toFloat())
                                item.Set_sugar_per_100g(obj.optDouble("sugar_per_100g", 0.0).toFloat())
                                item.Set_salt_per_100g(obj.optDouble("salt_per_100g", 0.0).toFloat())
                                item.Set_wellbeing_index(obj.optBoolean("wellbeing_index", false))

                                results.add(item)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                callback.onResult(results)
            }

            override fun onFailure() {
                callback.onResult(ArrayList()) // empty list on failure
            }
        })
    }

    /** ➕ Add a new food item */
    fun addFoodItem(foodData: Map<String, Any>, callback: ApiResultCallback) {
        val json = JSONObject(foodData)
        println("🍴 Adding food item: ${foodData["food_item_name"]}")
        postRequestAsync(ENDPOINT_ADD_FOOD, json, callback)
    }

    // -------------------- BALANCE SYNC APIs --------------------

    /**
     * Sync a credit or debit balance update to the backend.
     *
     * @param clientName  client identifier from SharedPreferences "client_name"
     * @param date        date string "dd-MM-yyyy"
     * @param balance     new countdown balance (integer)
     * @param transactionType  "CREDIT" or "DEBIT"
     * @param callback    result callback (fire-and-forget; failures are logged only)
     */
    fun syncBalance(
        clientName: String,
        date: String,
        balance: Int,
        transactionType: String,
        callback: ApiResultCallback
    ) {
        val json = JSONObject().apply {
            put("@context", "https://schema.org")
            put("@type", "FinancialTransaction")
            put("client_name", clientName)
            put("transaction_date", date)
            put("countdown_balance", balance)
            put("transaction_type", transactionType)
        }
        println("💰 Syncing balance: client=$clientName date=$date balance=$balance type=$transactionType")
        postRequestAsync(ENDPOINT_BALANCE_SYNC, json, callback)
    }

    /**
     * Sync the 9:59 PM day-end snapshot to the backend.
     *
     * @param clientName       client identifier
     * @param date             snapshot date "dd-MM-yyyy"
     * @param balance          countdown balance at day-end
     * @param dailyBudget      gender-based budget (2000F / 2500M)
     * @param foodCals         total food note calories consumed today
     * @param kittyValue       dailyBudget - foodCals (can be negative)
     * @param estimatedZeroDate formatted date when balance reaches zero "dd MMM yyyy"
     * @param callback         result callback
     */
    fun syncDayEnd(
        clientName: String,
        date: String,
        balance: Int,
        dailyBudget: Int,
        foodCals: Int,
        kittyValue: Int,
        estimatedZeroDate: String,
        callback: ApiResultCallback
    ) {
        val json = JSONObject().apply {
            put("@context", "https://schema.org")
            put("@type", "DayEndSnapshot")
            put("client_name", clientName)
            put("snapshot_date", date)
            put("countdown_balance", balance)
            put("daily_budget", dailyBudget)
            put("food_calories_consumed", foodCals)
            put("kitty_value", kittyValue)
            put("estimated_zero_date", estimatedZeroDate)
        }
        println("🌙 Syncing day-end: client=$clientName date=$date balance=$balance kitty=$kittyValue zeroDate=$estimatedZeroDate")
        postRequestAsync(ENDPOINT_DAYEND_SYNC, json, callback)
    }
}

// -------------------- CALLBACK INTERFACES --------------------

interface ApiResultCallback {
    fun onSuccess(response: String?) // called with API response
    fun onFailure()                   // called on network error
}

interface FoodSearchCallback {
    fun onResult(results: ArrayList<Food_Item_CIF4>)
}
