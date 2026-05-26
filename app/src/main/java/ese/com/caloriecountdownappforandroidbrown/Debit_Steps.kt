package ese.com.caloriecountdownappforandroidbrown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.installations.time.SystemClock
import java.lang.Exception
import java.util.*

class Debit_Steps : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debit_steps)

        findViewById<android.widget.Button>(R.id.button23).setOnClickListener {
            // Persist the entered step count for Midnight Scrape reconciliation
            val stepsText = findViewById<EditText>(R.id.editTextTextPersonName5)
                .text.toString().trim()
            val stepsEntered = stepsText.toIntOrNull() ?: 0
            if (stepsEntered > 0) {
                try {
                    val today = java.text.SimpleDateFormat(
                        "dd-MM-yyyy", java.util.Locale.getDefault()
                    ).format(java.util.Date())
                    SQLDatabase_Food_Items_CIF6(this).storeRecordedSteps(today, stepsEntered)
                } catch (ex: Exception) {
                    android.util.Log.e("DebitSteps", "storeRecordedSteps failed: ${ex.message}", ex)
                }
            }

            // Existing behaviour — unchanged
            CCD_GUI_CD_CIF1.instance.Countup(GetManualSteps())
            finish()
        }


    }

    private fun GetManualSteps(): Int
    {
        try{

            val cal2:Float = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString().toFloat()
            val currenttime = java.util.Date(SystemClock.getInstance().currentTimeMillis())
            val dayend = java.util.Calendar.getInstance()
            dayend.set(Calendar.HOUR_OF_DAY, 16)
            dayend.set(Calendar.MINUTE,0)

            if(dayend.time.after(currenttime)) {

                return (cal2 * 0.089 * -1).toInt()
            }
            else
            {
                val answer = (cal2 * 0.089 * -1).toInt() - 2225
                CCD_GUI_CD_CIF1.instance.Store_Dayend(
                    CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt() - answer)
                return answer;

            }

        }
        catch (e: Exception)
        {
            android.util.Log.d("Raw Calories", "Wrong Type")
        }

        return 0
    }
}