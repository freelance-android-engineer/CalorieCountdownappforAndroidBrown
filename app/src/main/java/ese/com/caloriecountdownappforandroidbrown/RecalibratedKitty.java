package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

/**
 * Client rule: when the Subscriber recalibrates, the Kitty is reset to the full BMR
 * (2500 male / 2000 female) and starts anew from the recalibrated balance.
 *
 * Recalibrating replaces the Main Countdown Balance, so the drop from yesterday's Day-End
 * Balance is not a countdown and must not be added to the Kitty. For the rest of the day the
 * Kitty uses the recalibrated balance as its baseline:
 *
 *   previousDayEnd = recalibratedBalance + 250   (so dayEndTarget = recalibratedBalance)
 *
 * BMR applied before the recalibration no longer counts (the new balance does not include it);
 * BMR applied after it does. Stored per day in the "Calorie_Countdown" prefs, like FoodConsumed.
 */
public final class RecalibratedKitty {

    static final String KEY_RECALIBRATED_BALANCE = "recalibrated_kitty_balance";
    static final String KEY_RECALIBRATED_DATE = "recalibrated_kitty_date";
    static final String KEY_BMR_APPLIED_BEFORE = "recalibrated_kitty_bmr_applied_before";

    private RecalibratedKitty() {}

    /** Records today's recalibrated balance and whether BMR had already been applied today. */
    public static void record(SharedPreferences prefs, String today, int recalibratedBalance,
                              boolean bmrAppliedBefore) {
        prefs.edit()
                .putInt(KEY_RECALIBRATED_BALANCE, recalibratedBalance)
                .putString(KEY_RECALIBRATED_DATE, today)
                .putBoolean(KEY_BMR_APPLIED_BEFORE, bmrAppliedBefore)
                .apply();
    }

    /** True if the Subscriber recalibrated on the given day. */
    public static boolean isRecalibrated(SharedPreferences prefs, String today) {
        return today != null && today.equals(prefs.getString(KEY_RECALIBRATED_DATE, null))
                && prefs.contains(KEY_RECALIBRATED_BALANCE);
    }

    /** Previous Day-End Balance for the Kitty, or null if the Subscriber did not recalibrate that day. */
    public static Integer previousDayEnd(SharedPreferences prefs, String today) {
        if (!isRecalibrated(prefs, today)) return null;
        return prefs.getInt(KEY_RECALIBRATED_BALANCE, 0) + KittyCalculator.DAY_END_TARGET_DROP;
    }

    /** Whether BMR counts as applied for the Kitty: only BMR applied after the recalibration. */
    public static boolean bmrApplied(SharedPreferences prefs, String today, boolean bmrAppliedToday) {
        if (!isRecalibrated(prefs, today)) return bmrAppliedToday;
        return bmrAppliedToday && !prefs.getBoolean(KEY_BMR_APPLIED_BEFORE, false);
    }
}
