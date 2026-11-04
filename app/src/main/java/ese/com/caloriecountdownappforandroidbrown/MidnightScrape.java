package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

/**
 * Midnight Scrape — final Steps comparison (client rule).
 *
 * The final Steps entered at the Countdown Day End are saved for that date (recorded_steps).
 * Steps walked after that entry, up to midnight, are not in the balance. The following day the
 * Subscriber presses Midnight Scrape and enters yesterday's actual final Steps (from their step
 * app); if it is greater than the saved final Steps, the difference is debited:
 *
 *   points = round((actual − saved) × Steps Numerator)    (Accountability.toPoints)
 *
 * Each date is scraped once: the date is claimed in prefs before the balance changes.
 *
 * Android-free apart from SharedPreferences so it can be unit tested on the JVM.
 */
public final class MidnightScrape {

    /** Prefs ("Calorie_Countdown"): last date (dd-MM-yyyy) whose final Steps were scraped. */
    public static final String KEY_LAST_SCRAPED_DATE = "midnight_scrape_last_scraped_date";

    private MidnightScrape() {}

    /** True when the date has saved final Steps that have not been scraped yet. */
    public static boolean isPending(SharedPreferences prefs, String date, int savedFinalSteps) {
        return savedFinalSteps >= 0 && date != null && !date.equals(prefs.getString(KEY_LAST_SCRAPED_DATE, null));
    }

    /** Steps walked after the saved final entry (0 when actual ≤ saved). */
    public static int missedSteps(int savedFinalSteps, int actualFinalSteps) {
        return Math.max(0, actualFinalSteps - savedFinalSteps);
    }

    /**
     * Claims the date before the balance changes, so a repeated press or callback cannot debit it twice.
     *
     * @return true if the date was not scraped before and is now claimed
     */
    public static boolean claim(SharedPreferences prefs, String date) {
        return Accountability.claimEvent(prefs, KEY_LAST_SCRAPED_DATE, date);
    }
}
