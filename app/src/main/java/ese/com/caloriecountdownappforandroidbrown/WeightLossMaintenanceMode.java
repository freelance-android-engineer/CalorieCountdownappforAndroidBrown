package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Persists Weight-Loss Maintenance Mode and its surplus account (SurplusAccountType094) in the
 * app's existing "Calorie_Countdown" SharedPreferences. Transition rules live in
 * {@link MaintenanceModeCalculator}.
 *
 * The surplus balance is history: it is never reset here, including when a new journey starts.
 */
final class WeightLossMaintenanceMode {

    private static final String PREFS_NAME = "Calorie_Countdown";
    static final String KEY_MAINTENANCE_ACTIVE = "maintenance_mode_active";
    static final String KEY_SURPLUS_BALANCE = "maintenance_surplus_balance";

    /** Stores the settled Main Countdown Balance; returns a negative value on failure. */
    interface MainBalanceWriter {
        long store(int mainBalance);
    }

    private final SharedPreferences prefs;

    WeightLossMaintenanceMode(Context context) {
        this(context.getSharedPreferences(PREFS_NAME, 0));
    }

    WeightLossMaintenanceMode(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    boolean isActive() {
        return prefs.getBoolean(KEY_MAINTENANCE_ACTIVE, false);
    }

    /** The maintenance surplus account, loaded from storage. */
    SurplusAccountType094 getSurplusAccount() {
        return new SurplusAccountType094Noir((double) prefs.getLong(KEY_SURPLUS_BALANCE, 0L));
    }

    /**
     * Applies the Maintenance Mode rules to the Main Countdown Balance after a daily cycle.
     *
     * Settlement depends only on the current state, so re-running it (a retried daily cycle or a
     * regenerated report) credits only amounts that are still below 0 — once settled, the Main
     * Balance is 0 and there is nothing left to credit.
     *
     * The Main Balance is written first; the surplus credit and mode change are saved only if
     * that write succeeds, so a failed write cannot cause a double credit on the next retry.
     *
     * @return the applied result, or null if the Main Balance could not be stored (nothing changed)
     */
    MaintenanceModeCalculator.Result settle(int mainBalance, boolean journeyStarted, MainBalanceWriter writer) {
        MaintenanceModeCalculator.Result result =
                MaintenanceModeCalculator.settle(isActive(), mainBalance, journeyStarted);

        if (result.mainBalance != mainBalance && writer.store(result.mainBalance) < 0) {
            return null;
        }

        SurplusAccountType094 surplus = getSurplusAccount();
        if (result.surplusCredit > 0) {
            surplus.creditAccount(result.surplusCredit);
        }
        prefs.edit()
                .putBoolean(KEY_MAINTENANCE_ACTIVE, result.maintenanceActive)
                .putLong(KEY_SURPLUS_BALANCE, Math.round(surplus.getAccountValue()))
                .commit();
        return result;
    }

    /**
     * A new journey from Start Weight-Loss returns the app to Countdown Mode.
     * The surplus balance is kept.
     */
    void startNewJourney() {
        prefs.edit().putBoolean(KEY_MAINTENANCE_ACTIVE, false).commit();
    }
}
