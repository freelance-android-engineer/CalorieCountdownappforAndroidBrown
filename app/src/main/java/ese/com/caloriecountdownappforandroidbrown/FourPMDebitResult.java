package ese.com.caloriecountdownappforandroidbrown;

/**
 * Immutable data model representing the result of one 4PM Debit / Steps Challenge calculation.
 *
 * <p>Produced by {@link FourPMDebitStepsProcessor#calculate} and persisted to the
 * {@code fourpm_debit_challenge} table via
 * {@link SQLDatabase_Food_Items_CIF6#saveDebitStepsChallenge}.</p>
 *
 * <p>All fields are {@code final} — create a new instance rather than mutating.</p>
 */
public class FourPMDebitResult {

    // -------------------------------------------------------------------------
    // Metadata
    // -------------------------------------------------------------------------

    /** Date this challenge was generated for, in {@code dd-MM-yyyy} format. */
    public final String processingDate;

    // -------------------------------------------------------------------------
    // Inputs captured at calculation time
    // -------------------------------------------------------------------------

    /** Total confirmed food-note calories from the preceding 4PM credit processing. */
    public final int totalFoodCalories;

    /** Gender-based daily calorie budget (2 000 female / 2 500 male). */
    public final int dailyBudget;

    /** Countdown balance recorded at the moment of calculation. May be negative (overdrawn). */
    public final int currentBalance;

    // -------------------------------------------------------------------------
    // Intermediate calculation values (stored for transparency / audit)
    // -------------------------------------------------------------------------

    /**
     * Difference between food consumed and the daily budget.
     * <ul>
     *   <li>Positive → over budget (kitty is empty + surplus).</li>
     *   <li>Zero / negative → within budget (kitty has calories to spare).</li>
     * </ul>
     */
    public final int kittyExcess;

    /**
     * Extra penalty calories arising from a negative (overdrawn) countdown balance.
     * {@code max(0, -currentBalance)}.  Always {@code >= 0}.
     */
    public final int balancePenalty;

    /**
     * Total calories that must be "walked off" as steps.
     * {@code max(0, kittyExcess) + balancePenalty}.  Always {@code >= 0}.
     */
    public final int excessCalories;

    // -------------------------------------------------------------------------
    // Final challenge output
    // -------------------------------------------------------------------------

    /** Number of steps required to offset excess calories. {@code 0} if no challenge needed. */
    public final int stepChallenge;

    /** {@code true} if the raw step count exceeded 30 000 and was capped. */
    public final boolean isCapped;

    /**
     * {@code true} when a non-zero step challenge was generated.
     * {@code false} when the user is within budget and the balance is not overdrawn.
     */
    public final boolean hasChallengeNeeded;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * All-args constructor — use {@link FourPMDebitStepsProcessor#calculate} as the
     * preferred factory rather than calling this directly.
     */
    public FourPMDebitResult(String processingDate,
                              int totalFoodCalories,
                              int dailyBudget,
                              int currentBalance,
                              int kittyExcess,
                              int balancePenalty,
                              int excessCalories,
                              int stepChallenge,
                              boolean isCapped,
                              boolean hasChallengeNeeded) {
        this.processingDate    = processingDate    != null ? processingDate : "";
        this.totalFoodCalories = totalFoodCalories;
        this.dailyBudget       = dailyBudget;
        this.currentBalance    = currentBalance;
        this.kittyExcess       = kittyExcess;
        this.balancePenalty    = balancePenalty;
        this.excessCalories    = excessCalories;
        this.stepChallenge     = stepChallenge;
        this.isCapped          = isCapped;
        this.hasChallengeNeeded = hasChallengeNeeded;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Builds a human-readable summary message suitable for display in a dialog.
     */
    public String buildSummaryMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Food Calories (yesterday):  ").append(totalFoodCalories).append("\n");
        sb.append("Daily Budget:               ").append(dailyBudget).append("\n");
        sb.append("Countdown Balance:          ").append(currentBalance).append("\n\n");

        if (kittyExcess > 0) {
            sb.append("Over daily budget by:       ").append(kittyExcess).append(" cal\n");
        } else {
            sb.append("Within budget (surplus:     ").append(Math.abs(kittyExcess)).append(" cal)\n");
        }

        if (balancePenalty > 0) {
            sb.append("Balance overdrawn penalty:  ").append(balancePenalty).append(" cal\n");
        }

        sb.append("Total to offset:            ").append(excessCalories).append(" cal\n\n");

        if (!hasChallengeNeeded) {
            sb.append("No step challenge needed!\n")
              .append("You are within your daily budget and your balance is healthy.");
        } else {
            sb.append("Step Challenge:  ").append(String.format("%,d", stepChallenge)).append(" steps");
            if (isCapped) {
                sb.append("\n\nChallenge capped at 30 000 steps.\n")
                  .append("Consider using Accrual to lighten tomorrow's load.");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "FourPMDebitResult{"
                + "date=" + processingDate
                + ", foodCal=" + totalFoodCalories
                + ", budget=" + dailyBudget
                + ", balance=" + currentBalance
                + ", excess=" + excessCalories
                + ", steps=" + stepChallenge
                + ", capped=" + isCapped
                + '}';
    }
}