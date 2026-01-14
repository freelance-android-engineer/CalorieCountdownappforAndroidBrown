package ese.com.caloriecountdownappforandroidbrown

import android.database.Cursor
import android.database.CursorWrapper
import android.util.Log
import java.util.Date

/**
 * Created by ESE on 08 MAR 17.
 *
 * //Android
 */

class CountdownToXeroDayType1004_Cursor(private val cursor: Cursor) : CursorWrapper(cursor)
{
    private val mCountdownToZeroDayCiF1004: CountdownToZeroDayCiF1004? = CountdownToZeroDayCiF1004(0, HealthProfileCiF3())

    fun GetDays(): CountdownToZeroDayCiF1004?
    {
        val T100 = mCountdownToZeroDayCiF1004

        // Clear the initial day that was created in the constructor
        T100?.numberOFDaysToXero03FEB10?.clear()

        try {
            var trash_collector = moveToFirst()

            Log.d("CursorDebug", "=== GetDays started, row count: $count ===")

            // Log column indices to debug
            val idIndex = getColumnIndex(COLUMN_DAYEND_ID2)
            val dateIndex = getColumnIndex(COLUMN_DAYEND_BALANCE_DATE2)
            val budgetIndex = getColumnIndex(COLUMN_DAYEND_BALANCE_BALANCE_BUDGET)
            val actualIndex = getColumnIndex(COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL)
            Log.d("CursorDebug", "Column indices: id=$idIndex, date=$dateIndex, budget=$budgetIndex, actual=$actualIndex")

            for (x in 0 until count)
            {
                try {
                    // Create a NEW DayCiF1005 for EACH row
                    val Line_50 = DayCiF1005(0, 0, 0, 0, java.time.LocalDateTime.now(), "0", "0")

                    // Get dayend_id (use 0 if column not found)
                    if (idIndex >= 0) {
                        val idStr = getString(idIndex)
                        Line_50.dayend_id = if (idStr != null) RoundingCIF13().StringToInt(idStr) else x
                    } else {
                        Line_50.dayend_id = x
                    }

                    // Get date string
                    if (dateIndex >= 0) {
                        Line_50.dayString = getString(dateIndex) ?: ""
                    }

                    // Parse the date string back to LocalDateTime
                    var validDate = false
                    if (Line_50.dayString.isNotEmpty()) {
                        try {
                            Line_50.day = java.time.LocalDateTime.parse(Line_50.dayString)
                            validDate = true
                        } catch (e: Exception) {
                            // Skip rows with invalid dates (old data)
                            Log.d("CursorDebug", "Row $x: Skipping - could not parse date '${Line_50.dayString}'")
                            trash_collector = moveToNext()
                            continue // Skip this row entirely
                        }
                    } else {
                        // Skip rows with empty dates
                        trash_collector = moveToNext()
                        continue
                    }

                    // Get budget
                    if (budgetIndex >= 0) {
                        val budgetStr = getString(budgetIndex)
                        Line_50.budgetedDayEndBalanceForThisDay = if (budgetStr != null) RoundingCIF13().StringToInt(budgetStr) else 0
                    }
                    Line_50.currentBalance = Line_50.budgetedDayEndBalanceForThisDay

                    // Get actual
                    if (actualIndex >= 0) {
                        val actualStr = getString(actualIndex)
                        Line_50.actualDayEndBalance = if (actualStr != null) RoundingCIF13().StringToInt(actualStr) else 0
                    }

                    T100?.numberOFDaysToXero03FEB10?.add(Line_50)

                    // Log first few and last few rows
                    if (x < 3 || x >= count - 2) {
                        Log.d("CursorDebug", "Row $x: date=${Line_50.dayString}, budget=${Line_50.budgetedDayEndBalanceForThisDay}")
                    }

                    trash_collector = moveToNext()
                } catch (rowEx: Exception) {
                    Log.e("CursorDebug", "Error processing row $x: ${rowEx.message}")
                    trash_collector = moveToNext()
                }
            }

            Log.d("CursorDebug", "=== GetDays completed, list size: ${T100?.numberOFDaysToXero03FEB10?.size} ===")

        } catch (e: Exception) {
            Log.e("CursorDebug", "Exception in GetDays: ${e.message}", e)
        }

        return T100

    }

    companion object {
        //A Convenience class to wrap a cursor that returns rows for the "dayend_balacne2" table.
        //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.
        private const val TABLE_DAYEND_BALANCE2 = "dayend_balance2"
        private const val COLUMN_DAYEND_ID2 = "dayend_id"
        private const val COLUMN_DAYEND_BALANCE_DATE2 = "balance_date"
        private const val COLUMN_DAYEND_BALANCE_BALANCE_BUDGET = "balance_dayend_budget"
        private const val COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL = "balance_dayend_actual"

    }
}
    /*private val Transaction_Items: BoxCIF17? = null
    private val Mealio: Meal_Items_Cursor? = null


    fun GetTransactions(): Transactions_CIF22
    {
        val T100 = Transactions_CIF22()

        var trash_collector = moveToFirst()


        for (x in 0 until count) { //while(!(isBeforeFirst() || isAfterLast())) {

            val T101 = Transaction_CIF52()
            val Line_50 = Transaction_Line_CIF17()
            val BBox = Breakfast_Box_CIF17()
            val dummy_rice_4 = Food_Item_CIF4("Porridge")

            Log.d("Cursor", "This is Row Count :" + RoundingCIF13().IntToString(count))

            Line_50.Set_Transaction_ID(
                RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMN_TRANSACTIONS_ID
                        )
                    )
                ).toLong()
            )
            Log.d(
                "Money Shot : Trans ID",
                RoundingCIF13().LongToString(Line_50.Get_Transaction_ID())
            )

            Line_50.Set_Description("Meal Time")

            Line_50.Set_Transaction_Amount(
                RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMUM_TRANSACTIONS_AMOUNT
                        )
                    )
                )
            )
            Log.d(
                "Money Shot : Amount",
                RoundingCIF13().IntToString(Line_50.Get_Transaction_Amount())
            )

            Line_50.Set_Transaction_Meal_Type(
                getString(
                    getColumnIndex(
                        COLUMUM_TRANSACTIONS_MEAL_TYPE
                    )
                )
            )
            Log.d("Money Shot : Meal_Type", Line_50.Get_Transaction_Meal_Type())

            Line_50.Set_Transaction_Meal_Type_ID(
                RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMUM_TRANSACTIONS_MEAL_TYPE_ID
                        )
                    )
                )
            )
            Log.d(
                "Money Shot : Meal T ID",
                RoundingCIF13().LongToString(Line_50.Get_Transaction_Meal_Type_ID())
            )

            Line_50.Set_Transaction_Balance(
                RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMUM_TRANSACTIONS_BALANCE
                        )
                    )
                )
            )
            Log.d(
                "Money Shot : Balance",
                RoundingCIF13().IntToString(Line_50.Get_Transaction_Balance())
            )

            Line_50.Set_Transaction_DateX(
                RoundingCIF13().StringToLong(
                    getString(
                        getColumnIndex(
                            COLMUM_TRANSACTIONS_DATE
                        )
                    )
                )
            )
            Log.d(
                "Money Shot : Date x",
                RoundingCIF13().LongToString(Line_50.Get_Transaction_DateX().time)
            )


            //BBox.Set_Breakfast_ID((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
            //Mealio = new Meal_Items_Cursor(cursor);
            //Transaction_Items = Mealio.Get_Transaction_Food_Items(new RoundingCIF13().StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
            //BBox.Set_Breakfast_Date((new RoundingCIF13()).StringToLong((getString(getColumnIndex(COLMUM_TRANSACTIONS_DATE)))));
            //BBox.Set_Breakfast_Meal_Type(getString(getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE)));
            //BBox.Set_Breakfast_Meal_Type_ID((new RoundingCIF13().StringToInt((getString((getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID)))))));
            //BBox.Set_Breakfast_Amount((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_AMOUNT)))));
            //BBox.Set_Breakfast_Balance((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_BALANCE)))));

            //T100.Set_Internal_Box(BBox);
            dummy_rice_4.Set_category("ING")
            dummy_rice_4.Set_food_item_name("McDonalds : Burgers : Double Cheeseburger flo")
            dummy_rice_4.Set_grams_per_serving_portion(100.00.toFloat())
            dummy_rice_4.Set_calories_per_100g(445.00.toFloat())
            dummy_rice_4.Set_fat_per_100g(5.00.toFloat())
            dummy_rice_4.Set_carbs_per_100g(5.00.toFloat())
            dummy_rice_4.Set_protein_per_100g(50f)

            Line_50.add_Food_item(dummy_rice_4)
            T101.Set_Single_Transaction_Line(Line_50)
            T100.Add_TransactionLine_2_List(T101)


            trash_collector = moveToNext()
        }


        return T100
    }


    companion object {
        //A Convenience class to wrap a cursor that returns rows for the "food items" table.
        //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.
        private const val TABLE_DAYEND_BALANCE2 = "dayend_balance2"
        private const val COLUMN_DAYEND_ID2 = "dayend_id"
        private const val COLUMN_DAYEND_BALANCE_DATE2 = "balance_date""
        private const val COLUMN_DAYEND_BALANCE_BALANCE_BUDGET = "balance_dayend_budget"
        private const val COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL = "balance_dayend_actual"

    }




}*/