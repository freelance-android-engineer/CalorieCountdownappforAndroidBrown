package ese.com.caloriecountdownappforandroidbrown

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DayCiF1005(_id: Int, _balance: Int, _startBalance: Int, _endBalance: Int, _date: java.time.LocalDateTime, _budget: String, _actualDayEnd: String)
{
    var dayend_id: Int = _id
    var day: java.time.LocalDateTime = _date //Store
    var dayString: String = ""
    var currentTime: java.time.LocalDateTime = LocalDateTime.now() //No need to Store

    var startBalanceBFWD: Int = _startBalance //No need to Store
    var currentBalance: Int = _balance //No need to Store
    var endBalanceCFWD: Int = _endBalance //Store

    var budgetedDayEndBalanceStartWeightLoss: Int = 0 //From SQLite
    var budgetedDayEndBalanceStartPreviousDay: Int = 0 //Calculated from previous day Dayend balance (startBalanceCFWD)
    var budgetedDayEndBalanceStartNextDay: Int = 0
    var budgetedDayEndBalanceForThisDay: Int = 0
    var actualDayEndBalance: Int = 0 //Money Shot, Store

    var firstBrekkieBox: BoxCIF17 = BoxCIF17() //From Food Notes and Credit Button, Food and drinks consumend by Client this day used for Calcualtions.
                                                //Food Notes Input goes here, where it is picked up by Credit Button for further...
    var bigBrunchBox: BoxCIF17 = BoxCIF17() //From Food Notes and Credit Button, Food and drinks consumend by Client this day used for Calcualtions.
                                             //Food Notes Input goes here, where it is picked up by Credit Button for further...
    var lastUpdated: Date = Date() //utiltiy variable, no need to Store

    var budget: String = _budget
    var actualDayEnd: String = _actualDayEnd

    // Stored food notes collection for this day
    var savedFoodNotesJson: String = ""
    var savedFoodNotesTotalCalories: Int = 0

    // Whether the user has completed their debit update (steps/exercise) for this day
    // Resets to false each new day. Set to true after 9:59pm debit update is performed.
    var debitUpdatePerformed: Boolean = false

    // The previous day's closing balance, used for Countdown Report comparison
    var previousDayEndBalance: Int = 0

    /*var mSteps: Exerice
    var mPhysicalActivity: Fitness Minutes
            var exe: etc Check FoodNotes i */

    /**
     * Save a collection of food notes to SQLite database for this day's date.
     * Converts the list of food notes to JSON and stores them in the food_notes_collection table.
     *
     * @param context The Android context for database access
     * @param foodNotes List of food note data maps containing note_food, note_calories, note_quantity
     * @return The ID of the inserted collection, or -1 if failed
     */
    fun Save_Notes(context: Context, foodNotes: List<Map<String, Any>>): Long {
        Log.d("DayCiF1005", "Save_Notes called with ${foodNotes.size} notes")

        // Format the date to match the collection date format (dd MMM yy)
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy")
        val collectionDate = day.format(dateFormatter)
        Log.d("DayCiF1005", "Collection date: $collectionDate")

        // Convert food notes to JSON array
        val jsonArray = JSONArray()
        var totalCalories = 0

        for (note in foodNotes) {
            val jsonObject = JSONObject()
            jsonObject.put("note_food", note["note_food"] ?: "")
            val calories = (note["note_calories"] as? Number)?.toInt() ?: 0
            jsonObject.put("note_calories", calories)
            jsonObject.put("note_quantity", note["note_quantity"] ?: 1)
            jsonArray.put(jsonObject)
            totalCalories += calories
        }

        val notesJson = jsonArray.toString()
        Log.d("DayCiF1005", "Notes JSON: $notesJson, Total calories: $totalCalories")

        // Store in instance variables
        savedFoodNotesJson = notesJson
        savedFoodNotesTotalCalories = totalCalories

        // Save to SQLite database
        val dbHelper = SQLDatabase_Food_Items_CIF6(context)
        val insertedId = dbHelper.insertFoodNotesCollection(collectionDate, notesJson, totalCalories)

        if (insertedId > 0) {
            Log.d("DayCiF1005", "Food notes collection saved successfully with ID: $insertedId")
        } else {
            Log.e("DayCiF1005", "Failed to save food notes collection")
        }

        return insertedId
    }
}
