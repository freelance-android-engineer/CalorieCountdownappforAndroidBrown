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
                view -> CCD_GUI_CD_CIF1.instance.ChangeTextColor( recalibratedBalance( checkWhichUnitisChecked()))

            finish()


        }

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
        val target = getLatestTargetWeight()
        if (target == null) {
            Toast.makeText(this, "Target weight not set. Please complete Start Weight Loss first.", Toast.LENGTH_LONG).show()
            return CCD_GUI_CD_CIF1.instance.Get_currentBalance()
        }

        //Make sure mass is converted to Pounds! Safely and that it is Kilograms.
        val mass:Float = Input1.toFloat()

        android.util.Log.d("RETRIEVE TARGET WEIGHT", target.toString())

        val output =  ((mass - target) * 2.2)  * 3500

        return output.toInt().toString()
    }




    private fun recalibratePounds(Input1: String):String
    {
        val target = getLatestTargetWeight()
        if (target == null) {
            Toast.makeText(this, "Target weight not set. Please complete Start Weight Loss first.", Toast.LENGTH_LONG).show()
            return CCD_GUI_CD_CIF1.instance.Get_currentBalance()
        }

        val mass:Float = Input1.toFloat()
        val output = ((mass - target) ) * 3500

        return output.toInt().toString()
    }

    /**
     * Reads the latest target weight from SQLite (authoritative source, written by Start Weight Loss).
     * Falls back to SharedPreferences if SQLite has no valid user-entered value.
     * Returns null if no valid target weight has been saved yet.
     */
    private fun getLatestTargetWeight(): Float? {
        // Primary: SQLite target_weight table (written by Start Weight Loss)
        val dbValue = SQLDatabase_Food_Items_CIF6(this).GetTargetWeight()
        if (!dbValue.isNullOrEmpty() && dbValue != "0" && dbValue != "1") {
            return try { dbValue.toFloat() } catch (e: NumberFormatException) { null }
        }
        // Fallback: SharedPreferences (also written by Start Weight Loss)
        val prefValue = CCD_GUI_CD_CIF1.instance?.RetrieveTargetWeightPounds()
        if (!prefValue.isNullOrEmpty()) {
            return try { prefValue.toFloat() } catch (e: NumberFormatException) { null }
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


        if(CCD_GUI_CD_CIF1.mDaysToZero != null)
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
