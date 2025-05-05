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
    private val mDayCiF1005: DayCiF1005? = DayCiF1005(0,0,0,0, java.time.LocalDateTime.now(), "0","0")

    fun GetDays(): CountdownToZeroDayCiF1004?
    {
        val T100 = mCountdownToZeroDayCiF1004
        val Line_50 = mDayCiF1005
        var trash_collector = moveToFirst()

        for (x in 0 until count)
        {

            Log.d("Cursor", "This is Row Count :" + RoundingCIF13().IntToString(count))



            Line_50?.dayend_id = RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMN_DAYEND_ID2
                        )
                    )
                )


            Line_50?.dayend_id?.let { RoundingCIF13().IntToString(it) }?.let {
                Log.d(
                    "Money Shot : dayend_id",
                    it
                )
            }




            Line_50?.dayString = getString(
                    getColumnIndex(
                        COLUMN_DAYEND_BALANCE_DATE2
                    )
                )


            Line_50?.budgetedDayEndBalanceForThisDay = RoundingCIF13().StringToInt(
                    getString(
                        getColumnIndex(
                            COLUMN_DAYEND_BALANCE_BALANCE_BUDGET
                        )
                    )
                )



            Log.d(
                "Money Shot : Expected Budget",
                Line_50?.budgetedDayEndBalanceForThisDay?.let { RoundingCIF13().IntToString(it) }.toString()
            )

            Line_50?.actualDayEndBalance = RoundingCIF13().StringToInt(
                getString(
                    getColumnIndex(
                        COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL
                    )
                )
            )



            Log.d(
                "Money Shot : Expected Budget",
                (Line_50?.actualDayEndBalance)?.let { RoundingCIF13().IntToString(it) }.toString()
            )


            T100?.numberOFDaysToXero03FEB10?.add(Line_50)

            trash_collector = moveToNext()
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