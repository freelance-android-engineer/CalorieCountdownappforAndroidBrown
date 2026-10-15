package ese.com.caloriecountdownappforandroidbrown;

/**
 * Input rules for the Debit screen's "Accountability" button, where the Subscriber enters the
 * steps they missed from their daily Steps Challenge. No Android dependencies so it can be
 * unit tested on the JVM.
 *
 * The missed steps are converted to Calories/Points with the Subscriber's Steps Numerator
 * (calories burned per step, stored in SQLite) and credited to the Main Countdown Balance:
 *
 *   credit = round(missedSteps × Steps Numerator)
 *
 * This is the inverse of the Step Challenge (steps = calories ÷ Steps Numerator).
 */
public final class Accountability {

    private Accountability() {}

    /**
     * Parses the missed-steps input.
     *
     * @return the step count if it is a whole number greater than 0 (up to 9 digits),
     *         otherwise null (empty, zero, negative, decimal, non-numeric).
     */
    public static Integer parseMissedSteps(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        if (!trimmed.matches("\\d{1,9}")) return null;
        int steps = Integer.parseInt(trimmed);
        return steps > 0 ? steps : null;
    }

    /**
     * Calories/Points to credit for the missed steps.
     *
     * @return round(missedSteps × stepsNumerator), or 0 if the inputs are not usable
     */
    public static int toCredit(int missedSteps, double stepsNumerator) {
        if (missedSteps <= 0 || !(stepsNumerator > 0) || Double.isInfinite(stepsNumerator)) return 0;
        long credit = Math.round(missedSteps * stepsNumerator);
        return (int) Math.min(credit, Integer.MAX_VALUE);
    }
}
