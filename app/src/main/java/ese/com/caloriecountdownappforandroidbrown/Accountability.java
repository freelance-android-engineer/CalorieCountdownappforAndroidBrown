package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

/**
 * Rules for the "Accountability" and "de-Accountability" buttons on the Debit and Credit screens.
 * No Android UI dependencies so it can be unit tested on the JVM.
 *
 * Debit screen (steps):
 *   Accountability    — steps missed from the daily Steps Challenge, CREDITED to the balance
 *   de-Accountability — surplus steps above the daily Steps Challenge, DEBITED from the balance
 *
 * Both convert steps with the Subscriber's Steps Numerator (calories burned per step, stored
 * in SQLite):  points = round(steps × Steps Numerator)
 * This is the inverse of the Step Challenge (steps = calories ÷ Steps Numerator).
 *
 * Credit screen (calories eaten), no conversion — points = calories:
 *   Accountability    — calories eaten above the daily Credit Budget, CREDITED to the balance
 *   de-Accountability — calories eaten below the daily Credit Budget, DEBITED from the balance
 */
public final class Accountability {

    /** Prefs keys holding the last processed event id, so one event is never applied twice. */
    static final String KEY_LAST_ACCOUNTABILITY_EVENT = "accountability_last_event_id";
    static final String KEY_LAST_DE_ACCOUNTABILITY_EVENT = "de_accountability_last_event_id";
    static final String KEY_LAST_CREDIT_ACCOUNTABILITY_EVENT = "credit_accountability_last_event_id";
    static final String KEY_LAST_CREDIT_DE_ACCOUNTABILITY_EVENT = "credit_de_accountability_last_event_id";

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

    /** Parses the surplus-steps input (steps above the daily target); same rules as missed steps. */
    public static Integer parseSurplusSteps(String input) {
        return parseMissedSteps(input);
    }

    /** Parses the Credit screen calories input (calories above / below the Credit Budget); same rules as steps. */
    public static Integer parseCalories(String input) {
        return parseMissedSteps(input);
    }

    /**
     * Points for a number of steps: round(steps × stepsNumerator).
     *
     * @return the points (0 when the numerator is 0), or null when the steps or the numerator
     *         are missing or invalid, in which case the balance must not be changed
     */
    public static Integer toPoints(int steps, Double stepsNumerator) {
        if (steps <= 0 || stepsNumerator == null || stepsNumerator.isNaN()
                || stepsNumerator.isInfinite() || stepsNumerator < 0) {
            return null;
        }
        long points = Math.round(steps * stepsNumerator);
        return (int) Math.min(points, Integer.MAX_VALUE);
    }

    /**
     * Claims an event so it is applied at most once, persisted before the balance changes.
     *
     * @return true if the event had not been processed and is now recorded; false if it was
     *         already processed or could not be recorded (the balance must then not change)
     */
    public static boolean claimEvent(SharedPreferences prefs, String key, String eventId) {
        if (eventId == null || eventId.isEmpty()) return false;
        if (eventId.equals(prefs.getString(key, null))) return false;
        return prefs.edit().putString(key, eventId).commit();
    }

    /**
     * Countdown Report summary for a Credit screen (de-)Accountability entry, shown to the
     * Subscriber for transparency.
     */
    public static String creditReport(boolean accountability, int calories,
                                      int previousBalance, int newBalance) {
        StringBuilder report = new StringBuilder(header("Credit", accountability));
        report.append("Your food eaten (Credit) was ").append(format(calories)).append(" calories ")
                .append(accountability ? "ABOVE" : "BELOW").append(" your daily Credit Budget.\n\n");
        appendBalanceChange(report, accountability, calories, previousBalance, newBalance);
        return report.toString();
    }

    /**
     * Countdown Report summary for a Debit screen (de-)Accountability entry, shown to the
     * Subscriber for transparency.
     */
    public static String debitReport(boolean accountability, int steps, double stepsNumerator, int points,
                                     int previousBalance, int newBalance) {
        StringBuilder report = new StringBuilder(header("Debit", accountability));
        report.append("Your steps (Debit) were ").append(format(steps)).append(" steps ")
                .append(accountability ? "BELOW" : "ABOVE").append(" your daily Steps Budget.\n\n");
        report.append(format(steps)).append(" steps × ").append(stepsNumerator)
                .append(" (Steps Numerator) = ").append(format(points)).append(" points\n\n");
        appendBalanceChange(report, accountability, points, previousBalance, newBalance);
        return report.toString();
    }

    private static String header(String side, boolean accountability) {
        return "═══ Countdown Report ═══\n\n" + side + " — "
                + (accountability ? "Accountability" : "de-Accountability") + "\n\n";
    }

    private static void appendBalanceChange(StringBuilder report, boolean accountability, int points,
                                            int previousBalance, int newBalance) {
        report.append(format(points)).append(" points ")
                .append(accountability ? "added to" : "taken off").append(" your Main Countdown Balance.\n\n");
        report.append("Previous Balance:  ").append(format(previousBalance)).append(" Cr\n");
        report.append("New Balance:       ").append(format(newBalance)).append(" Cr\n");
        if (accountability) {
            report.append("Net Countup:       +").append(format(points)).append(" pts 🔺");
        } else {
            report.append("Net Countdown:     -").append(format(points)).append(" pts 🔻");
        }
    }

    private static String format(int value) {
        return String.format(java.util.Locale.US, "%,d", value);
    }
}
