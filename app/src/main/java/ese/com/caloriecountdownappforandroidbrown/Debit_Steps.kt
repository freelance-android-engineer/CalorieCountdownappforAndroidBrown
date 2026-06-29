package ese.com.caloriecountdownappforandroidbrown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import java.lang.Exception

class Debit_Steps : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debit_steps)

        findViewById<android.widget.Button>(R.id.button23).setOnClickListener {
            // Persist the entered step count for Midnight Scrape reconciliation
            val stepsText = findViewById<EditText>(R.id.editTextTextPersonName5)
                .text.toString().trim()
            val stepsEntered = stepsText.toIntOrNull() ?: 0
            if (stepsEntered <= 0) {
                finish()
                return@setOnClickListener
            }

            try {
                val today = java.text.SimpleDateFormat(
                    "dd-MM-yyyy", java.util.Locale.getDefault()
                ).format(java.util.Date())
                SQLDatabase_Food_Items_CIF6(this).storeRecordedSteps(today, stepsEntered)
            } catch (ex: Exception) {
                android.util.Log.e("DebitSteps", "storeRecordedSteps failed: ${ex.message}", ex)
            }

            // Return debit value via the standard activity-result contract
            // Parent's onActivityResult will route this through Countdown() which subtracts
            val debitPoints = (stepsEntered * 0.089).toInt()
            val result = android.content.Intent()
            result.putExtra(Debit_Activity_CiF003_fragment_box.TOTAL_DEBIT_VALUE, debitPoints)
            setResult(android.app.Activity.RESULT_OK, result)
            finish()
        }


    }

}