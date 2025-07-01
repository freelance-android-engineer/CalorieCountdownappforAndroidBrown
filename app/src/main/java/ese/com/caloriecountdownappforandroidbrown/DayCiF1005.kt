package ese.com.caloriecountdownappforandroidbrown

import android.text.format.DateUtils
import java.util.Date
import java.time.LocalDateTime

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

    /*var mSteps: Exerice
    var mPhysicalActivity: Fitness Minutes
            var exe: etc Check FoodNotes i */


}
