package ese.com.caloriecountdownappforandroidbrown



import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

                                 // ANDROID ENGINEER.
class CountdownToZeroDayCiF1004(_openingBalance: Int, inputB_HealthProfile: HealthProfileCiF3) {
                                     private var openingBalance: Int = _openingBalance
                                         get() {
                                             return field
                                         }
                                         set(value) {
                                             field = value
                                         }

                                     var clientHealthProfileType: HealthProfileCiF3 =
                                         inputB_HealthProfile

                                     val Type_Size = (openingBalance / 100) as Int

                                     val todaysDate: LocalDateTime =
                                         LocalDateTime.parse("2025-02-18T15:43:00")

                                     private var dayz: DayCiF1005? = DayCiF1005(
                                         0, (openingBalance - 100), openingBalance,
                                         0, todaysDate, (openingBalance - 100).toString(), "0"
                                     )
                                         get() = field
                                         set(value) {
                                             field = value
                                         }

                                     private var nextOB = (openingBalance - 100)

                                     var _date = LocalDateTime.now()

                                     var xero: String =
                                         "Day expected to arrive at zero Balance is: 24 FEB 25 (Well Done !!! No.1 exe 22!! Days early good performance and Challenge well met green and red and Blue"

                                     var numberOFDaysToXero03FEB10 = mutableListOf(dayz)
                                     private val surplusPostXEROCaloriesAcct: SurplusAccountType094Noir =
                                         SurplusAccountType094Noir(0.0)

                                     var isSetup = false






                        //01582572148 ESE in red & bebe

    fun storeType1004ToSQLiteDayEnd2Table()
    {

    }



    fun setupType(size: Int)
    {
        //Algorithm Engineering : Step One
        //For the size given in a loop initialize this Type by creating
        //an equivalent number of DayType1005s and add to ListOf.

        //Remember number of days to xero contains a record of all the days
        //done so far since "Start Weight Loss"  initialized the Journey and at 4:30pm each day
        //from Debit Value Button (correct bugs) stored in DayEnd Table via Model Adapter
        // Model Adapter takes in a DayType1005s  and Stores it in SQLite,
        //then based on the current Balance estimates the numbers of day to xero
        //by dividing by 300/100 (the expect daily points DR countdown) and creates
        //an equal matching number of DayTypes1005() (if at last day and not yet reached zero divide remaining ba;ance by 50 or 100 and add corresponding number of days to the end of the list)
        // (also acknolwede that count ups will happen) to end of course have no var balance value
        //it is from number of days to xero that you can forecast number of days to xero and arrival date,
        // and draw
        //charts like Sweatcoin, let the app partner with Sweatcoin, wild coin etc Calorie Countdown App get Team for real and Business Plan.
        // color the button, lend ai powered all in subsequent versions and updates/upgrades &
        // via Trial Period Balance to Approved for Sale and PRODUCTION always focus
        // on Green and xero and 18 FEB 04.

        //♻️push⚫️ :ℹ️ : ⚫️ENGLISH : "Hello android".
        //Step One: (re)Load... -> version 2.0.0 -> play.google.com -> £5,500 -> 🔵ESE S.C.I. LTD
        //⚫️Noir⚫️

        println(message = "ESE S.C.I. LTD, means we have entered setupType()")
        var id = 0

        for(i in 1..100)
        {



            dayz = DayCiF1005(id++, nextOB,0,0, _date, (nextOB - 100).toString(), "0")

            numberOFDaysToXero03FEB10.add(dayz)

            //_date = incrementByaDay(_date)
            println("_date before plusDays = $_date")
            _date = _date.plusDays(1)
            println("_date after plusDays = $_date")

            nextOB -= 100


        }

        isSetup = true
        println(message = "Size of numberOFDaysToXero03FEB10 = " + numberOFDaysToXero03FEB10.size.toString())
       // println( printDaysType() )
    }



    fun printDaysType(): String
    {


        var output: String = "I am Empty\n"

        val micheck: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(0)
        val micheck1: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(1)
        val micheck2: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(2)
        val micheck3: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(3)
        val micheck4: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(4)
        val micheck5: DayCiF1005? = numberOFDaysToXero03FEB10.removeAt(5)

        output += micheck?.day.toString()
        output += " "
        output += micheck?.currentBalance.toString()
        output += "\n"

        output += micheck1?.day.toString()
        output += " "
        output += micheck1?.currentBalance.toString()
        output += "\n"

        output += micheck2?.day.toString()
        output += " "
        output += micheck2?.currentBalance.toString()
        output += "\n"

        output += micheck3?.day.toString()
        output += " "
        output += micheck3?.currentBalance.toString()
        output += "\n"

        output += micheck4?.day.toString()
        output += " "
        output += micheck4?.currentBalance.toString()
        output += "\n"

        output += micheck5?.day.toString()
        output += " "
        output += micheck5?.currentBalance.toString()
        output += "\n"



        return output
    }



    fun incrementByaDay(input: java.time.LocalDateTime) : java.time.LocalDateTime
    {
        var localDate: java.time.LocalDateTime = input //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.plusDays(24)
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