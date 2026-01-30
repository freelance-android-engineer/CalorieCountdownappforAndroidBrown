package ese.com.caloriecountdownappforandroidbrown

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * KittyActivity - The Calorie Kitty Flow
 *
 * This activity displays the user's daily calorie status:
 * - Left side (CONSUMED): Calories eaten since yesterday 4pm
 * - Right side (REMAINING): Calories left in the daily budget (Kitty)
 *
 * The calculations are based on:
 * - Previous day's closing balance (from DayEnd2 table at 4pm)
 * - Current balance
 * - Gender-based daily budget (Female: 2000, Male: 2500)
 *
 * Example:
 * - Previous day closing at 4pm: 20,000 points
 * - Today's target: 19,750 points (20,000 - 250)
 * - Current balance: 21,250 points
 * - Female daily budget: 2,000 calories
 * - Consumed: 21,250 - 20,000 = 1,250 calories
 * - Remaining in Kitty: 2,000 - 1,250 = 750 calories
 */
class KittyActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KittyActivity"
        private const val PREFS_NAME = "Calorie_Countdown"
        private const val KEY_GENDER = "Gender_Type"

        // Daily calorie budgets
        const val FEMALE_DAILY_BUDGET = 2000
        const val MALE_DAILY_BUDGET = 2500

        // Default steps per calorie (approximate)
        const val STEPS_PER_CALORIE = 20
    }

    // UI Components
    private lateinit var tvConsumedValue: TextView
    private lateinit var tvRemainingValue: TextView
    private lateinit var tvGoalPercent: TextView
    private lateinit var tvTargetInfo: TextView
    private lateinit var tvStepsChallenge: TextView
    private lateinit var progressConsumed: ProgressBar
    private lateinit var progressRemaining: ProgressBar
    private lateinit var goalProgressBar: ProgressBar
    private lateinit var stepsChallengeLayout: LinearLayout
    private lateinit var btnAddFood: Button

    // Database helper
    private lateinit var dbHelper: SQLDatabase_Food_Items_CIF6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitty)

        // Initialize database helper
        dbHelper = SQLDatabase_Food_Items_CIF6(this)

        // Initialize UI components
        initializeViews()

        // Calculate and display Kitty values
        calculateAndDisplayKitty()

        // Set up button click listener
        btnAddFood.setOnClickListener {
            // Navigate back to FoodNoteTableActivity to add more food
            finish()
        }
    }

    private fun initializeViews() {
        tvConsumedValue = findViewById(R.id.tvConsumedValue)
        tvRemainingValue = findViewById(R.id.tvRemainingValue)
        tvGoalPercent = findViewById(R.id.tvGoalPercent)
        tvTargetInfo = findViewById(R.id.tvTargetInfo)
        tvStepsChallenge = findViewById(R.id.tvStepsChallenge)
        progressConsumed = findViewById(R.id.progressConsumed)
        progressRemaining = findViewById(R.id.progressRemaining)
        goalProgressBar = findViewById(R.id.goalProgressBar)
        stepsChallengeLayout = findViewById(R.id.stepsChallengLayout)
        btnAddFood = findViewById(R.id.btnAddFood)
    }

    private fun calculateAndDisplayKitty() {
        // Get previous day's closing balance from DayEnd2 table
        val previousDayEndBalance = dbHelper.getPreviousDayEndBalance()

        // Get current balance from CCD_GUI_CD_CIF1
        val currentBalance = getCurrentBalance()

        // Get gender and daily budget
        val gender = getGenderFromPrefs()
        val dailyBudget = if (gender == "Female") FEMALE_DAILY_BUDGET else MALE_DAILY_BUDGET

        android.util.Log.d(TAG, "Previous Day End Balance: $previousDayEndBalance")
        android.util.Log.d(TAG, "Current Balance: $currentBalance")
        android.util.Log.d(TAG, "Gender: $gender, Daily Budget: $dailyBudget")

        // Calculate consumed calories since yesterday 4pm
        // Consumed = Current Balance - Previous Day End Balance
        // (If current balance increased, it means calories were added/eaten)
        val consumed: Int
        val remaining: Int

        if (previousDayEndBalance > 0) {
            // We have a previous day-end balance
            consumed = if (currentBalance > previousDayEndBalance) {
                currentBalance - previousDayEndBalance
            } else {
                0 // Balance decreased (due to debit/exercise)
            }
            remaining = maxOf(0, dailyBudget - consumed)
        } else {
            // No previous day-end balance - use current balance as baseline
            // For first-time users or when no day-end data exists
            consumed = 0
            remaining = dailyBudget

            // Show info message
            tvTargetInfo.text = "No previous day-end balance found. Your daily budget is $dailyBudget calories."
            tvTargetInfo.visibility = View.VISIBLE
        }

        android.util.Log.d(TAG, "Consumed: $consumed, Remaining: $remaining")

        // Update UI with calculated values
        updateUI(consumed, remaining, dailyBudget, previousDayEndBalance)

        // Check if calories are exhausted and show steps challenge if needed
        if (remaining <= 0 && consumed > dailyBudget) {
            showStepsChallenge(consumed - dailyBudget)
        }
    }

    private fun getCurrentBalance(): Int {
        return try {
            // Try to get balance from CCD_GUI_CD_CIF1 instance
            if (CCD_GUI_CD_CIF1.instance != null) {
                CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt()
            } else {
                // Fallback: get from SharedPreferences or database
                val adapter = MIF4_Data_Model_Adapter(this)
                val balanceStr = adapter.RetrieveBalance()
                balanceStr?.replace(",", "")?.toIntOrNull() ?: 0
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error getting current balance: ${e.message}")
            0
        }
    }

    private fun getGenderFromPrefs(): String {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_GENDER, "Male") ?: "Male"
    }

    private fun updateUI(consumed: Int, remaining: Int, dailyBudget: Int, previousBalance: Int) {
        // Update consumed value (left side)
        tvConsumedValue.text = formatNumber(consumed)

        // Update remaining value (right side)
        tvRemainingValue.text = formatNumber(remaining)

        // Update progress bars
        progressConsumed.max = dailyBudget
        progressConsumed.progress = minOf(consumed, dailyBudget)

        progressRemaining.max = dailyBudget
        progressRemaining.progress = remaining

        // Calculate and display goal percentage
        val goalPercent = if (dailyBudget > 0) {
            ((consumed.toFloat() / dailyBudget.toFloat()) * 100).toInt()
        } else {
            0
        }
        tvGoalPercent.text = "$goalPercent% of daily goal"
        goalProgressBar.progress = minOf(goalPercent, 100)

        // Show target info if we have previous balance
        if (previousBalance > 0) {
            val todayTarget = previousBalance - 250
            tvTargetInfo.text = "Today's target: ${formatNumber(todayTarget)} points\n(Previous: ${formatNumber(previousBalance)} - 250)"
            tvTargetInfo.visibility = View.VISIBLE
        }

        // Update button text based on remaining calories
        if (remaining > 0) {
            btnAddFood.text = "ADD FOOD"
        } else {
            btnAddFood.text = "VIEW BALANCE"
        }
    }

    private fun showStepsChallenge(excessCalories: Int) {
        // Show the steps challenge section
        stepsChallengeLayout.visibility = View.VISIBLE

        // Calculate steps needed to burn excess calories
        // Approximate: 100 steps burns about 5 calories
        // So to burn 1 calorie, need about 20 steps
        val stepsNeeded = excessCalories * STEPS_PER_CALORIE

        tvStepsChallenge.text = "${formatNumber(stepsNeeded)} steps needed"

        // Hide add food button or change its text
        btnAddFood.text = "I'LL DO THE CHALLENGE"
    }

    private fun formatNumber(number: Int): String {
        return String.format("%,d", number)
    }
}
