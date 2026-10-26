package ese.com.caloriecountdownappforandroidbrown

import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class Recalibrate : AppCompatActivity()
{
    /** True once a valid recalibrated balance has been calculated in this tap. */
    private var recalibrated = false




    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recalibrate)
        setSupportActionBar(findViewById(R.id.toolbar))


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        findViewById<android.widget.Button>(R.id.button19).setOnClickListener {
                //Check which unit is Selected

                //Check and confirm that it is not possible to select both units and that both units are not selected

                //new fragment takes in unit and user value and returns recalibrated balance as text
                view ->
                val units = checkWhichUnitisChecked()
                val target = getLatestTargetWeight()
                val weight = RecalibrationCalculator.parseWeight(findViewById<EditText>(R.id.editTextTextPersonName2).text.toString())
                if (target != null && target.second == null && weight != null) {
                    // Older versions saved the Target Weight without its unit: ask once instead of guessing
                    confirmTargetWeight(target.first) { recalibrateAndFinish(units) }
                } else {
                    recalibrateAndFinish(units)
                }
        }

    }




    private fun recalibrateAndFinish(units: String)
    {
        recalibrated = false
        CCD_GUI_CD_CIF1.instance.ChangeTextColor( recalibratedBalance(units))
        if (recalibrated) captureDayEndBalance()

        finish()
    }

    private fun recalibratedBalance(Input1: String) : String
    {
        val mass = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()

        android.util.Log.d("What we got and check Table DayEND2", mass)

        checkTableDayEND2()

        if(isThisANumber(mass) == false)
        {
            Toast.makeText(this, "Please insert a numerical value as your Current weight", Toast.LENGTH_LONG).show()
            return CCD_GUI_CD_CIF1.instance.Get_currentBalance()
        }

            if(Input1 == "Kilograms")
            {
                return recalibrateKilo(mass)
            }
            if(Input1 == "Pounds")
            {
               return recalibratePounds(mass)
            }
        else return CCD_GUI_CD_CIF1.instance.Get_currentBalance()

    }




    private fun checkWhichUnitisChecked():String
    {
        if(findViewById<RadioButton>(R.id.radioButton).isChecked == true && findViewById<RadioButton>(R.id.radioButton2).isChecked == false)
        {
            return "Kilograms"
        }

        if(findViewById<RadioButton>(R.id.radioButton).isChecked == false && findViewById<RadioButton>(R.id.radioButton2).isChecked == true)
        {
            return "Pounds"
        }

        if(findViewById<RadioButton>(R.id.radioButton).isChecked == true && findViewById<RadioButton>(R.id.radioButton2).isChecked == true)
        {
            Toast.makeText(this,"Please Select only ONE Option for measurement units", Toast.LENGTH_LONG).show()
        }

        return "Kilograms"
    }




    private fun recalibrateKilo(Input1: String):String
    {
        val target = targetWeightIn(RecalibrationCalculator.UNIT_KILOGRAMS)
                ?: return CCD_GUI_CD_CIF1.instance.Get_currentBalance()

        //Make sure mass is converted to Pounds! Safely and that it is Kilograms.
        val mass:Float = Input1.toFloat()

        android.util.Log.d("RETRIEVE TARGET WEIGHT", target.toString() + " kg")

        val output = RecalibrationCalculator.recalibrateKilograms(mass, target)

        resetKitty(output)
        return output.toString()
    }




    private fun recalibratePounds(Input1: String):String
    {
        val target = targetWeightIn(RecalibrationCalculator.UNIT_POUNDS)
                ?: return CCD_GUI_CD_CIF1.instance.Get_currentBalance()

        val mass:Float = Input1.toFloat()

        android.util.Log.d("RETRIEVE TARGET WEIGHT", target.toString() + " lb")

        val output = RecalibrationCalculator.recalibratePounds(mass, target)

        resetKitty(output)
        return output.toString()
    }

    /** The saved Target Weight converted to the recalibration unit, or null (with a message) if unavailable. */
    private fun targetWeightIn(units: String): Float?
    {
        val target = getLatestTargetWeight()
        if (target == null) {
            Toast.makeText(this, "Target weight not set. Please complete Start Weight Loss first.", Toast.LENGTH_LONG).show()
            return null
        }
        val converted = RecalibrationCalculator.convert(target.first, target.second, units)
        if (converted == null) {
            Toast.makeText(this, "Please confirm the unit of your Target Weight, then recalibrate again.", Toast.LENGTH_LONG).show()
        }
        return converted
    }

    /**
     * Asks the Subscriber to confirm the saved Target Weight and its unit (older versions did not
     * record the unit). Saved the same way Start Weight Loss saves it, so it is asked only once.
     */
    private fun confirmTargetWeight(saved: Float, onConfirmed: () -> Unit)
    {
        val savedText = java.math.BigDecimal(saved.toString()).stripTrailingZeros().toPlainString()
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        input.setText(savedText)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Confirm your Target Weight")
                .setMessage("Your saved Target Weight is $savedText, but its unit was not recorded.\n\n"
                        + "Please check the value and choose its unit. You will only be asked once.")
                .setView(input)
                .setPositiveButton("Kilograms", null) // set below so invalid input keeps the dialog open
                .setNeutralButton("Pounds", null)
                .setNegativeButton("Cancel", null)
                .create()
        dialog.setOnShowListener {
            val save = { units: String ->
                if (!RecalibrationCalculator.isValidTargetWeight(input.text.toString())) {
                    Toast.makeText(this, "Please enter your Target Weight as a number between 1 and "
                            + RecalibrationCalculator.MAX_TARGET_WEIGHT.toInt() + ".", Toast.LENGTH_SHORT).show()
                } else {
                    val text = input.text.toString().trim()
                    SQLDatabase_Food_Items_CIF6(this).PostValueToTargetWeightTable(text)
                    CCD_GUI_CD_CIF1.instance.Store_Target_Weight_Pounds(text)
                    getSharedPreferences("Calorie_Countdown", 0).edit()
                            .putString(RecalibrationCalculator.KEY_TARGET_WEIGHT_UNITS, units).apply()
                    dialog.dismiss()
                    onConfirmed()
                }
            }
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener { save(RecalibrationCalculator.UNIT_KILOGRAMS) }
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL)
                    .setOnClickListener { save(RecalibrationCalculator.UNIT_POUNDS) }
        }
        dialog.show()
    }

    /**
     * Client rule: recalibrating resets the Kitty to the full BMR (2500 / 2000) and starts it
     * anew from the recalibrated balance. Recorded before the balance is set, because setting it
     * shows the Kitty dialog straight away.
     */
    private fun resetKitty(recalibratedBalance: Int)
    {
        val prefs = getSharedPreferences("Calorie_Countdown", 0)
        val today = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        val bmrAppliedToday = today == prefs.getString(Debit_Activity_CiF003_fragment_box.KEY_BMR_APPLIED_DATE, null)
        RecalibratedKitty.record(prefs, today, recalibratedBalance, bmrAppliedToday)
        recalibrated = true
    }

    /**
     * Client rule: at the moment of Recalibration, store the Main Countdown Balance shown on the
     * screen as today's Day End Balance (a later 23:59 capture replaces it, see DayEndBalance).
     */
    private fun captureDayEndBalance()
    {
        try {
            val now = System.currentTimeMillis()
            val balance = CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt()
            SQLDatabase_Food_Items_CIF6(this).saveDayEndBalance(DayEndBalance.dateKey(now), balance,
                    DayEndBalance.SOURCE_RECALIBRATION, now)
        } catch (e: Exception) {
            android.util.Log.e("DayEndBalance", "Recalibration capture failed: " + e.message, e)
        }
    }

    /**
     * Reads the latest target weight from SQLite (authoritative source, written by Start Weight Loss).
     * Falls back to SharedPreferences if SQLite has no valid user-entered value.
     * Returns the value with its unit (null if the unit was not recorded), or null if no valid
     * target weight has been saved yet.
     */
    private fun getLatestTargetWeight(): Pair<Float, String?>? {
        val units = RecalibrationCalculator.normaliseUnit(getSharedPreferences("Calorie_Countdown", 0)
                .getString(RecalibrationCalculator.KEY_TARGET_WEIGHT_UNITS, null))
        // Primary: SQLite target_weight table (written by Start Weight Loss), decimals kept
        val dbValue = RecalibrationCalculator.parseWeight(SQLDatabase_Food_Items_CIF6(this).getTargetWeightText())
        if (dbValue != null && dbValue > 1f) {
            return Pair(dbValue, units)
        }
        // Fallback: SharedPreferences (also written by Start Weight Loss)
        val prefValue = RecalibrationCalculator.parseWeight(CCD_GUI_CD_CIF1.instance?.RetrieveTargetWeightPounds())
        if (prefValue != null) {
            return Pair(prefValue, units)
        }
        return null
    }


    private fun checkTableDayEND2()
    {
        println(message = "ESE S.C.I. LTD")
        println(message = "ese.")
        //ENGLISH i -> IDO -> Algorithm Engineering
        //Check that StormmForecast Works
        //Make necessary changes if any needed.

        android.util.Log.d("What we got and check Table DayEND2", "Cheching SQLite/Room Beginsv")
        val modelAdapter: MIF4_Data_Model_Adapter = MIF4_Data_Model_Adapter(baseContext)


        if(CCD_GUI_CD_CIF1.mDaysToZero == null)
        {
            CCD_GUI_CD_CIF1.mDaysToZero = modelAdapter.RetrievemForecast()

        }

        CCD_GUI_CD_CIF1.mDaysToZero?.let{
            it.printContents()
        }


    }


    private fun isThisANumber(Input3: String):Boolean
    {
        try
        {
            var xero:Float = Input3.toFloat()
        }
        catch (e:Exception)
        {
            return false
        }

        return true
    }
}
