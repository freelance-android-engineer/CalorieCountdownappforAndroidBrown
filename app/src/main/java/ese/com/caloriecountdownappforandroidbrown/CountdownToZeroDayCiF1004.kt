package ese.com.caloriecountdownappforandroidbrown



import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

                                 // ANDROID ENGINEER
class CountdownToZeroDayCiF1004(_openingBalance: Int, inputB_HealthProfile: HealthProfileCiF3)
{
       private var openingBalance: Int = _openingBalance
           get() {return field}
           set(value)
           {
               field  = value
           }

       var clientHealthProfileType: HealthProfileCiF3 = inputB_HealthProfile

       val todaysDate: LocalDateTime = LocalDateTime.parse("2025-02-18T15:43:00")

       private var dayz: DayCiF1005? = DayCiF1005(0,(openingBalance -100),openingBalance,
           0,todaysDate,(openingBalance -100).toString(),"0" )
           get() = field
           set(value)
           {
               field = value
           }

    private var nextOB = (openingBalance - 100)

       var xero: String = "Day expected to arrive at zero Balance is: 24 FEB 25 (Well Done !!! No.1 exe 22!! Days early good performance and Challenge well met green and red and Blue"

       var numberOFDaysToXero03FEB10: MutableList<DayCiF1005?> = mutableListOf(dayz)
       private val surplusPostXEROCaloriesAcct: SurplusAccountType094Noir = SurplusAccountType094Noir(0.0)


       //clientHealthProfileType.mForecast = this




                        //01582572148 ESE in red & bebe


    fun setupType(size: Int)
    {
        //Algorithm Engineering : Step One
        //For the size given in a loop initialize this Type by creating
        //an equivalent number of DayType1005s and add to ListOf.

        //Remember number of days to xero contains a record of all the days
        //done so far since "Start Weight Loss"  initialized the Journey and at 4pm each day
        //from Debit Value Button (correct bugs) stored in DayEnd Table via Model Adapter
        // Model Adapter takes in a DayType1005s  and Stores it in SQLite,
        //then based on the current Balance estimates the numbers of day to xero
        //by dividing by 300/100 (the expect daily points DR countdown) and creates
        //an equal matching number of DayTypes1005() to end of course have no var balance value
        //it is from number of days to xero that you can forecast number of days to xero and arrival date,
        // and draw
        //charts like Sweatcoin, let the app partner with Sweatcoin
        // color the button, lend ai powered all in subsequent versions and upgrades &
        // via Trial Period Balance to Approved for Sale and PRODUCTION always focus
        // on Green and xero and 18 FEB 04.

        //♻️push⚫️ :ℹ️ : ⚫️ENGLISH : "Hello android".
        //Step One: (re)Load... -> version 2.0.0 -> play.google.com -> £5,500 -> 🔵ESE S.C.I. LTD
        //⚫️Noir⚫️

        println(message = "ESE S.C.I. lTD")


        (0..(size -1)).forEach(){

            var id = 0
            var _date = LocalDateTime.now()
            dayz = DayCiF1005(id++, nextOB,0,0, _date, (nextOB - 100).toString(), "0")

            numberOFDaysToXero03FEB10.add(dayz)

            _date = incrementByaDay(_date)

            nextOB =- 100
        }
    }

    fun printDaysType()
    {
        numberOFDaysToXero03FEB10.forEach { it -> println(it?.currentBalance ?: 0)}
    }


    fun incrementByaDay(input: java.time.LocalDateTime) : java.time.LocalDateTime
    {
        var localDate: java.time.LocalDateTime = input //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.plusDays(1)
        return localDate
    }

    fun printContents()
    {
        val output = printContentsDayEND2Table()
        println(output)
    }

    private fun printContentsDayEND2Table(): String
    {
        return "Make Kitty Work."
    }

    fun getCurrentDayType1005(): DayCiF1005? {
        //Algorithm Engineering -> android -> Noir.
        //Step One
        //Find out what Today's Day is
        val todaysDate: java.time.LocalDateTime = java.time.LocalDateTime.now()
        var result: DayCiF1005? = null
        //Go through the day list
        /* for (x in 0 until numberOFDaysToXero03FEB10.size)
        {
        }*/

        numberOFDaysToXero03FEB10.forEach {

            if (it != null)
            {
                if (getYearFromDate(it.day) == getYearFromDate(todaysDate) &&
                    getMonthFromDate(it.day) == getMonthFromDate(todaysDate) &&
                    getDayFromDate(it.day) == getDayFromDate(todaysDate)
                )
                {
                    result = it
                    return@forEach
                }
                return result
            } else return result



        }

        //Get DayType1005? who's date matches current/today's date
        //Eject and Return That Value as Output

        return result

    }

    private fun getYearFromDate(date: java.time.LocalDateTime): Int
    {
        val localDate = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.year
    }

    private fun getMonthFromDate(date: java.time.LocalDateTime): Int
    {
        val localDate = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.month.value
    }

    private fun getDayFromDate(date: java.time.LocalDateTime): Int
    {
        val localDate = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.dayOfMonth
    }

    private fun getHourFromDate(date: java.time.LocalDateTime): Int
    {
        val localDatetime = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        return localDatetime.hour
    }

    private fun getMinuteFromDate(date: java.time.LocalDateTime): Int
    {
        val localDatetime = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        return localDatetime.minute
    }

    private fun getSecondsFromDate(date: java.time.LocalDateTime): Int
    {
        val localDatetime = date//.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        return localDatetime.second
    }
}