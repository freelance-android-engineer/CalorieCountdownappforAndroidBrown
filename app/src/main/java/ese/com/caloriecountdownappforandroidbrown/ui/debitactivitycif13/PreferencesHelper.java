package ese.com.caloriecountdownappforandroidbrown.ui.debitactivitycif13;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_FIRST_RUN = "isFirstRun";

    private final SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Check if this is the first run
    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(KEY_FIRST_RUN, true);
    }

    // Mark that the guide has been shown
    public void markGuideAsShown() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.apply();
    }
}
