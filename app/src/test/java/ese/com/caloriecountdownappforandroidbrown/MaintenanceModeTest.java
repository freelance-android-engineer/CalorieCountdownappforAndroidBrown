package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MaintenanceModeTest {

    private FakePrefs prefs;
    private WeightLossMaintenanceMode maintenance;
    /** Simulated persisted Main Countdown Balance. */
    private int storedMainBalance;
    private int mainBalanceWrites;

    @Before
    public void setUp() {
        prefs = new FakePrefs();
        maintenance = new WeightLossMaintenanceMode(prefs);
        storedMainBalance = Integer.MIN_VALUE;
        mainBalanceWrites = 0;
    }

    private MaintenanceModeCalculator.Result settle(int balance) {
        return maintenance.settle(balance, true, b -> {
            mainBalanceWrites++;
            storedMainBalance = b;
            return 1;
        });
    }

    private long surplus() {
        return Math.round(maintenance.getSurplusAccount().getAccountValue());
    }

    // ── Calculator rules ────────────────────────────────────────────────────────

    @Test
    public void countdownMode_positiveBalance_noChange() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(false, 500, true);
        assertFalse(r.maintenanceActive);
        assertEquals(500, r.mainBalance);
        assertEquals(0, r.surplusCredit);
        assertEquals(MaintenanceModeCalculator.Transition.NONE, r.transition);
    }

    @Test
    public void countdownMode_exactZero_entersMaintenance() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(false, 0, true);
        assertTrue(r.maintenanceActive);
        assertEquals(0, r.mainBalance);
        assertEquals(0, r.surplusCredit);
        assertEquals(MaintenanceModeCalculator.Transition.ENTERED_MAINTENANCE, r.transition);
    }

    @Test
    public void countdownMode_belowZero_entersMaintenanceAndCreditsOvershoot() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(false, -120, true);
        assertTrue(r.maintenanceActive);
        assertEquals(0, r.mainBalance);
        assertEquals(120, r.surplusCredit);
    }

    @Test
    public void countdownMode_journeyNotStarted_neverEntersMaintenance() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(false, -300, false);
        assertFalse(r.maintenanceActive);
        assertEquals(-300, r.mainBalance);
        assertEquals(0, r.surplusCredit);
    }

    @Test
    public void maintenance_belowZero_creditsSurplusAndHoldsMainAtZero() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(true, -250, true);
        assertTrue(r.maintenanceActive);
        assertEquals(0, r.mainBalance);
        assertEquals(250, r.surplusCredit);
        assertEquals(MaintenanceModeCalculator.Transition.STAYED_IN_MAINTENANCE, r.transition);
    }

    @Test
    public void maintenance_exactZero_staysWithNoCredit() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(true, 0, true);
        assertTrue(r.maintenanceActive);
        assertEquals(0, r.surplusCredit);
    }

    @Test
    public void maintenance_aboveZero_returnsToCountdownKeepingPositiveBalance() {
        MaintenanceModeCalculator.Result r = MaintenanceModeCalculator.settle(true, 400, true);
        assertFalse(r.maintenanceActive);
        assertEquals(400, r.mainBalance);
        assertEquals(0, r.surplusCredit);
        assertEquals(MaintenanceModeCalculator.Transition.RETURNED_TO_COUNTDOWN, r.transition);
    }

    // ── Persistence, retries and journeys ───────────────────────────────────────

    @Test
    public void fullJourney_persistsModeAndSurplus() {
        settle(-100);                      // reaches 0 with 100 overshoot
        assertTrue(maintenance.isActive());
        assertEquals(0, storedMainBalance);
        assertEquals(100, surplus());

        settle(-250);                      // counts down further in maintenance
        assertEquals(350, surplus());
        assertEquals(0, storedMainBalance);

        settle(300);                       // counts up above 0
        assertFalse(maintenance.isActive());
        assertEquals(350, surplus());      // surplus history kept
    }

    @Test
    public void retriedCycle_doesNotDoubleCreditSurplus() {
        settle(-200);
        assertEquals(200, surplus());

        // Same cycle / report processed again: Main Balance is already settled to 0
        settle(storedMainBalance);
        settle(storedMainBalance);

        assertEquals(200, surplus());
        assertTrue(maintenance.isActive());
    }

    @Test
    public void exactZeroInMaintenance_doesNotRewriteMainBalance() {
        settle(-50);
        int writes = mainBalanceWrites;

        settle(0);

        assertEquals(writes, mainBalanceWrites);
        assertEquals(50, surplus());
    }

    @Test
    public void failedMainBalanceStore_changesNothingSoRetryIsSafe() {
        MaintenanceModeCalculator.Result failed = maintenance.settle(-200, true, b -> -1);
        assertNull(failed);
        assertFalse(maintenance.isActive());
        assertEquals(0, surplus());

        settle(-200);                      // retry succeeds and credits exactly once
        assertEquals(200, surplus());
        assertTrue(maintenance.isActive());
    }

    @Test
    public void newJourney_returnsToCountdownModeAndKeepsSurplus() {
        settle(-500);
        assertTrue(maintenance.isActive());

        maintenance.startNewJourney();

        assertFalse(maintenance.isActive());
        assertEquals(500, surplus());
    }

    @Test
    public void surplusAccount_creditDebitEmpty() {
        SurplusAccountType094 account = new SurplusAccountType094Noir(10.0);
        assertTrue(account.creditAccount(15));
        assertEquals(25.0, account.getAccountValue(), 0.0);
        assertTrue(account.debitAccount(5));
        assertEquals(20.0, account.getAccountValue(), 0.0);
        assertFalse(account.creditAccount(-1));
        assertTrue(account.emptyAccount());
        assertEquals(0.0, account.getAccountValue(), 0.0);
    }

    /** Minimal in-memory SharedPreferences for JVM tests. */
    private static final class FakePrefs implements SharedPreferences {
        private final Map<String, Object> values = new HashMap<>();

        @Override public Map<String, ?> getAll() { return values; }
        @Override public String getString(String k, String d) { return values.containsKey(k) ? (String) values.get(k) : d; }
        @Override public Set<String> getStringSet(String k, Set<String> d) { return d; }
        @Override public int getInt(String k, int d) { return values.containsKey(k) ? (Integer) values.get(k) : d; }
        @Override public long getLong(String k, long d) { return values.containsKey(k) ? (Long) values.get(k) : d; }
        @Override public float getFloat(String k, float d) { return values.containsKey(k) ? (Float) values.get(k) : d; }
        @Override public boolean getBoolean(String k, boolean d) { return values.containsKey(k) ? (Boolean) values.get(k) : d; }
        @Override public boolean contains(String k) { return values.containsKey(k); }
        @Override public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener l) {}
        @Override public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener l) {}

        @Override public Editor edit() {
            Map<String, Object> pending = new HashMap<>();
            return new Editor() {
                @Override public Editor putString(String k, String v) { pending.put(k, v); return this; }
                @Override public Editor putStringSet(String k, Set<String> v) { pending.put(k, v); return this; }
                @Override public Editor putInt(String k, int v) { pending.put(k, v); return this; }
                @Override public Editor putLong(String k, long v) { pending.put(k, v); return this; }
                @Override public Editor putFloat(String k, float v) { pending.put(k, v); return this; }
                @Override public Editor putBoolean(String k, boolean v) { pending.put(k, v); return this; }
                @Override public Editor remove(String k) { pending.put(k, null); return this; }
                @Override public Editor clear() { values.clear(); return this; }
                @Override public boolean commit() { apply(); return true; }
                @Override public void apply() {
                    for (Map.Entry<String, Object> e : pending.entrySet()) {
                        if (e.getValue() == null) values.remove(e.getKey()); else values.put(e.getKey(), e.getValue());
                    }
                }
            };
        }
    }
}
