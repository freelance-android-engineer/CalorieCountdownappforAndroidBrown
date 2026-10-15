package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

/**
 * Rules for the Debit screen's "Accountability" and "de-Accountability" buttons.
 * No Android UI dependencies so it can be unit tested on the JVM.
 *
 *   Accountability    — steps missed from the daily Steps Challenge, CREDITED to the balance
 *   de-Accountability — surplus steps above the daily Steps Challenge, DEBITED from the balance
 *
 * Both convert steps with the Subscriber's Steps Numerator (calories burned per step, stored
 * in SQLite):  points = round(steps × Steps Numerator)
 * This is the inverse of the Step Challenge (steps = calories ÷ Steps Numerator).
 */
public final class Accountability {

    /** Prefs keys holding the last processed event id, so one event is never applied twice. */
    static final String KEY_LAST_ACCOUNTABILITY_EVENT = "accountability_last_event_id";
    static final String KEY_LAST_DE_ACCOUNTABILITY_EVENT = "de_accountability_last_event_id";

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
}
