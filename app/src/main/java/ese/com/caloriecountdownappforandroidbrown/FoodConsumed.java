package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

/**
 * food_consumed — the Food Notes "Sum Notes" total that the Subscriber credited with the
 * dialog's Credit button, kept for Kitty Reports.
 *
 * Stored for the day in the "Calorie_Countdown" prefs (same approach as
 * Debit_Activity_CiF003_fragment_box.KEY_BMR_APPLIED_DATE), so it survives the Subscriber
 * leaving Food Notes or restarting the app, and is ignored once the day changes.
 * Each successful Credit sets it to that dialog's summed value.
 */
public final class FoodConsumed {

    static final String KEY_FOOD_CONSUMED = "food_consumed";
    static final String KEY_FOOD_CONSUMED_DATE = "food_consumed_date";

    private FoodConsumed() {}

    /** Sets today's food_consumed to the credited summed value. */
    public static void record(SharedPreferences prefs, String today, int summedCalories) {
        prefs.edit()
                .putInt(KEY_FOOD_CONSUMED, summedCalories)
                .putString(KEY_FOOD_CONSUMED_DATE, today)
                .apply();
    }

    /** food_consumed for the given day, or null if nothing has been credited that day. */
    public static Integer forDate(SharedPreferences prefs, String today) {
        if (today == null || !today.equals(prefs.getString(KEY_FOOD_CONSUMED_DATE, null))) return null;
        if (!prefs.contains(KEY_FOOD_CONSUMED)) return null;
        return prefs.getInt(KEY_FOOD_CONSUMED, 0);
    }
}
