package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;
import java.util.List;

/**
 * Countdown Day End Report (client rule): when the Subscriber enters the day's Steps (manually or
 * from the Sensor), all daily Debits — BMR, Midnight Scrape, Physical Activity and the Steps — are
 * in, so today's final Countdown Balance is compared with Yesterday's Day End Balance, e.g.
 * 10,000 → 9,650 shows "🔻 350 Points".
 *
 * The report only reads balances; the Steps debit itself goes through the existing Countdown flow.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class DayEndReport {

    /** Debit result extras: the debit came from a Steps entry. */
    public static final String EXTRA_DEBIT_KIND = "debit_kind";
    public static final String KIND_STEPS = "STEPS";
    public static final String EXTRA_STEPS = "debit_steps";
    public static final String EXTRA_STEPS_SOURCE = "debit_steps_source";
    public static final String SOURCE_MANUAL = "Manual";
    public static final String SOURCE_SENSOR = "Sensor";

    /** Prefs ("Calorie_Countdown"): date (dd-MM-yyyy) a Physical Activity debit was last entered. */
    public static final String KEY_PHYSICAL_ACTIVITY_DATE = "physical_activity_date";

    private DayEndReport() {}

    /**
     * Daily debits not yet recorded today, from the markers the app already keeps.
     *
     * @param today              "dd-MM-yyyy"
     * @param yesterday          "dd-MM-yyyy"
     * @param yesterdaySavedSteps final Steps saved for yesterday, or -1 if none
     */
    public static List<String> missingDebits(android.content.SharedPreferences prefs, String today, String yesterday,
                                             int yesterdaySavedSteps) {
        boolean bmrApplied = today.equals(prefs.getString(Debit_Activity_CiF003_fragment_box.KEY_BMR_APPLIED_DATE, null));
        boolean activity = today.equals(prefs.getString(KEY_PHYSICAL_ACTIVITY_DATE, null));
        return missingDebits(bmrApplied, MidnightScrape.isPending(prefs, yesterday, yesterdaySavedSteps), activity);
    }

    /** Daily debits not yet recorded today (empty when everything is in). */
    public static List<String> missingDebits(boolean bmrApplied, boolean midnightScrapePending,
                                             boolean physicalActivityRecorded) {
        List<String> missing = new ArrayList<>();
        if (!bmrApplied) missing.add("BMR");
        if (midnightScrapePending) missing.add("Midnight Scrape (yesterday's steps after your final entry)");
        if (!physicalActivityRecorded) missing.add("Physical Activity (skip if you did none today)");
        return missing;
    }

    /** Points counted down since the reference: positive = counted down, negative = counted up. */
    public static int countdown(int referenceBalance, int finalBalance) {
        return referenceBalance - finalBalance;
    }

    /** "🔻 350 Points", "🔺 120 Points" or "0 Points". */
    public static String movement(int referenceBalance, int finalBalance) {
        int diff = countdown(referenceBalance, finalBalance);
        if (diff > 0) return "🔻 " + fmt(diff) + " Points";
        if (diff < 0) return "🔺 " + fmt(-diff) + " Points";
        return "0 Points";
    }

    /**
     * Report section appended to the Countdown Report after a Steps entry.
     *
     * @param reference     Yesterday's Day End Balance (or today's Recalibrated Balance), null if no record
     * @param referenceLabel label and date of the reference, e.g. "Yesterday's Day End Balance (02 Nov 2026)"
     * @param finalBalance  today's final Countdown Balance after the Steps debit
     * @param steps         Steps entered
     * @param source        SOURCE_MANUAL or SOURCE_SENSOR
     * @param missing       daily debits not recorded when the report was made
     */
    public static String buildSection(Integer reference, String referenceLabel, int finalBalance,
                                      int steps, String source, List<String> missing) {
        StringBuilder sb = new StringBuilder();
        sb.append("── Countdown Day End Report ──\n");
        sb.append("Steps (").append(source).append("): ").append(fmt(steps)).append("\n");
        if (missing == null || missing.isEmpty()) {
            sb.append("Daily Debits: BMR, Midnight Scrape, Physical Activity and Steps included.\n\n");
        } else {
            sb.append("Not recorded today: ").append(String.join(", ", missing)).append("\n\n");
        }

        if (reference == null) {
            sb.append("Yesterday's Day End Balance: No Record\n");
            sb.append("Today's Final Balance: ").append(fmt(finalBalance)).append("\n");
            return sb.toString();
        }

        int diff = countdown(reference, finalBalance);
        sb.append(referenceLabel).append(": ").append(fmt(reference)).append("\n");
        sb.append("Today's Final Balance: ").append(fmt(finalBalance)).append("\n");
        sb.append(movement(reference, finalBalance));
        if (diff > 0) sb.append(" (counted down)");
        else if (diff < 0) sb.append(" (counted up)");
        else sb.append(" (no change)");
        sb.append("\n");
        return sb.toString();
    }

    private static String fmt(int value) {
        return String.format("%,d", value);
    }
}
