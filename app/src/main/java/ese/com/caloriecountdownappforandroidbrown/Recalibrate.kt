package ese.com.caloriecountdownappforandroidbrown

import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class Recalibrate : AppCompatActivity() {



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

        android.util.Log.d("What we got", mass)

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

    private fun recalibrateKilo(Input1: String):String
    {

        CCD_GUI_CD_CIF1.instance.Store_Target_Weight_Pounds("112.9")

        //Make sure mass is converted to Pounds! Safely and that it is Kilograms.
        val mass:Float = Input1.toFloat()
        val target = CCD_GUI_CD_CIF1.instance.RetrieveTargetWeightPounds().toFloat()



        android.util.Log.d("RETRIEVE TARGET WEIGH", target.toString())

        val output =  ((mass - target) * 2.2)  * 3500

        return output.toInt().toString()


    }

    private fun recalibratePounds(Input1: String):String
    {
        CCD_GUI_CD_CIF1.instance.Store_Target_Weight_Pounds("249")

        val mass:Float = Input1.toFloat()
        val target = CCD_GUI_CD_CIF1.instance.RetrieveTargetWeightPounds().toFloat()//MIF4_Data_Model_Adapter(this).RetrieveTargetWeight().toFloat()
        val output = ((mass - target) ) * 3500

        return output.toInt().toString()
    }
}