package ese.com.caloriecountdownappforandroidbrown;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Client rule — definitive Day End Balance:
 * "At 23:59 each day or at the moment of Recalibration capture and store what the Main Countdown
 * Balance is on the screen and have this as the Day End Balance for that day, to be used for
 * important calculations and plots starting from the following day."
 *
 * One record per calendar date (yyyy-MM-dd, stored in SQLite with the date as PRIMARY KEY).
 * When several captures happen on the same date the latest capture wins, because it is what the
 * Main Countdown Balance shows at the end of the day; a Recalibration value is also kept for audit.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class DayEndBalance {

    /** Captured by the 23:59 alarm. */
    public static final String SOURCE_SCHEDULED = "SCHEDULED_2359";
    /** Captured when the Subscriber recalibrated. */
    public static final String SOURCE_RECALIBRATION = "RECALIBRATION";
    /**
     * The 23:59 capture was missed (device off, app killed): recovered from the last balance
     * recorded that day in daily_countdown_history, which is written on every balance change.
     */
    public static final String SOURCE_RECOVERED = "RECOVERED_HISTORY";

    public static final int CAPTURE_HOUR = 23;
    public static final int CAPTURE_MINUTE = 59;

    private DayEndBalance() {}

    /** Calendar date key (yyyy-MM-dd, local time) for a moment in time. */
    public static String dateKey(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date(millis));
    }

    /** Capture timestamp (yyyy-MM-dd HH:mm:ss, local time); sorts chronologically as text. */
    public static String timestamp(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(millis));
    }

    /** True for a well-formed yyyy-MM-dd key. */
    public static boolean isDateKey(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Whether a recovered balance (last history update of the day) may be stored for a date.
     * It never replaces a 23:59 capture; it replaces a Recalibration capture only when the balance
     * changed again later that day, so the record still holds the latest balance of the day.
     *
     * @param existingSource     source of the stored record, or null if the date has none
     * @param existingCapturedAt capture timestamp of the stored record
     * @param historyUpdatedAt   time of the last balance change that day
     */
    public static boolean shouldRecover(String existingSource, String existingCapturedAt, String historyUpdatedAt) {
        if (existingSource == null) return true;
        if (!SOURCE_RECALIBRATION.equals(existingSource)) return false;
        return historyUpdatedAt != null && existingCapturedAt != null
                && historyUpdatedAt.compareTo(existingCapturedAt) > 0;
    }

    /** One stored Day End Balance. */
    public static final class Record {
        public final String date;
        public final int balance;
        public final String source;
        public final String capturedAt;
        /** Balance captured at Recalibration that day, or null if the Subscriber did not recalibrate. */
        public final Integer recalibratedBalance;

        public Record(String date, int balance, String source, String capturedAt, Integer recalibratedBalance) {
            this.date = date;
            this.balance = balance;
            this.source = source;
            this.capturedAt = capturedAt;
            this.recalibratedBalance = recalibratedBalance;
        }
    }
}
