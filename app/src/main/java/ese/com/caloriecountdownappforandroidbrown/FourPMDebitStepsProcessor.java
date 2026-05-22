package ese.com.caloriecountdownappforandroidbrown;

import android.util.Log;

/**
 * Pure-Java algorithm class that calculates the 4PM Debit / Steps Challenge.
 *
 * <p>Contains <strong>no Android lifecycle coupling</strong> — safe to unit-test and call
 * from any thread without a Context.</p>
 *
 * <h3>Algorithm Overview</h3>
 * <pre>
 *  kittyExcess    = totalFoodCalories − dailyBudget
 *  balancePenalty = max(0, −currentBalance)         // overdrawn balance adds burden
 *  excessCalories = max(0, kittyExcess) + balancePenalty
 *  targetCalories = excessCalories + BONUS_PENALTY   // fixed 250-cal accountability bonus
 *  rawSteps       = targetCalories / CALORIES_PER_STEP (0.089 kcal / step)
 *  stepChallenge  = min(rawSteps, STEP_CAP)          // capped at 30 000 steps
 * </pre>
 *
 * <h3>Example</h3>
 * <pre>
 *  Input : foodCal=2 400, budget=2 000, balance=−100
 *  kittyExcess    = 2 400 − 2 000 = 400
 *  balancePenalty = max(0, −(−100)) = 100
 *  excessCalories = 400 + 100 = 500
 *  targetCalories = 500 + 250 = 750
 *  steps          = 750 / 0.089 ≈ 8 427
 * </pre>
 *
 * @see FourPMDebitResult
 * @see SQLDatabase_Food_Items_CIF6#saveDebitStepsChallenge
 */
public final class FourPMDebitStepsProcessor {

    private static final String TAG = "4PM_DEBIT_ALGO";

    // -------------------------------------------------------------------------
    // Public algorithm constants (exposed so they can be documented / tested)
    // -------------------------------------------------------------------------

    /**
     * Calories burned per step.
     * Matches the constant used in {@code Debit_Steps.kt} and {@code StepChallengeFun}.
     */
    public static final float CALORIES_PER_STEP = 0.089f;

    /**
     * Fixed bonus-penalty calories added to the excess before conversion to steps.
     * Matches the existing 250-point bonus used in {@code StepChallengeFun}.
     */
    public static final int BONUS_PENALTY_CALORIES = 250;

    /** Maximum allowed step challenge. Raw values above this are capped. */
    public static final int STEP_CAP = 30_000;

    // Utility class — no instantiation.
    private FourPMDebitStepsProcessor() {}

    // =========================================================================
    // Public API
    // =========================================================================

    /**
     * Calculate the 4PM Debit / Steps Challenge for a given day.
     *
     * <p>The method is <em>pure</em>: it reads no global state and produces the same output
     * for the same inputs every time.</p>
     *
     * @param processingDate    Date of processing in {@code dd-MM-yyyy} format.
     *                          Used as the key when persisting the result.
     * @param totalFoodCalories Confirmed calories from the preceding 4PM credit processing
     *                          (sum of {@link FoodNoteProcessingItem#finalCalories} for all notes).
     * @param dailyBudget       User's daily calorie budget.
     *                          Typically 2 000 for female, 2 500 for male.
     * @param currentBalance    Current countdown balance (may be negative if overdrawn).
     *                          Read from {@link MIF4_Data_Model_Adapter#RetrieveBalance()}
     *                          <em>before</em> today's credit is applied.
     * @return A fully populated, immutable {@link FourPMDebitResult}.
     */
    public static FourPMDebitResult calculate(String processingDate,
                                               int totalFoodCalories,
                                               int dailyBudget,
                                               int currentBalance) {

        Log.d(TAG, "calculate() — date=" + processingDate
                + ", foodCal=" + totalFoodCalories
                + ", budget=" + dailyBudget
                + ", balance=" + currentBalance);

        // ------------------------------------------------------------------
        // Step 1: Kitty excess (positive = over budget, negative = under)
        // ------------------------------------------------------------------
        int kittyExcess = totalFoodCalories - dailyBudget;
        Log.d(TAG, "kittyExcess=" + kittyExcess);

        // ------------------------------------------------------------------
        // Step 2: Balance penalty (only overdrawn balances add to challenge)
        // ------------------------------------------------------------------
        int balancePenalty = Math.max(0, -currentBalance);
        Log.d(TAG, "balancePenalty=" + balancePenalty);

        // ------------------------------------------------------------------
        // Step 3: Combined calories to be offset by steps
        //         Only the *positive* portion of kittyExcess matters here.
        // ------------------------------------------------------------------
        int excessCalories = Math.max(0, kittyExcess) + balancePenalty;
        Log.d(TAG, "excessCalories=" + excessCalories);

        // ------------------------------------------------------------------
        // Step 4: Convert excess to a step challenge
        // ------------------------------------------------------------------
        if (excessCalories <= 0) {
            Log.d(TAG, "No challenge needed — within budget and balance healthy");
            return new FourPMDebitResult(
                    processingDate,
                    totalFoodCalories, dailyBudget, currentBalance,
                    kittyExcess, balancePenalty, 0,
                    0, false, false);
        }

        int targetCalories = excessCalories + BONUS_PENALTY_CALORIES;
        float rawSteps     = targetCalories / CALORIES_PER_STEP;
        boolean isCapped   = rawSteps > STEP_CAP;
        int stepChallenge  = (int) Math.min(rawSteps, STEP_CAP);

        Log.d(TAG, "targetCal=" + targetCalories
                + ", rawSteps=" + rawSteps
                + ", stepChallenge=" + stepChallenge
                + ", capped=" + isCapped);

        return new FourPMDebitResult(
                processingDate,
                totalFoodCalories, dailyBudget, currentBalance,
                kittyExcess, balancePenalty, excessCalories,
                stepChallenge, isCapped, true);
    }

    // =========================================================================
    // Convenience helpers
    // =========================================================================

    /**
     * Resolve the gender-based daily calorie budget from a gender string.
     *
     * @param gender Gender string stored in SharedPreferences (case-insensitive).
     *               Recognises {@code "male"} and defaults everything else to female budget.
     * @return 2 500 for male, 2 000 for female (or unknown / null).
     */
    public static int resolveDailyBudget(String gender) {
        if (gender != null && gender.trim().equalsIgnoreCase("male")) {
            return 2_500;
        }
        return 2_000;
    }
}
