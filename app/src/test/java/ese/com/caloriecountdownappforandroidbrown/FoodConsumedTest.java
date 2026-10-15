package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FoodConsumedTest {

    @Test
    public void nothingCreditedIsNull() {
        assertNull(FoodConsumed.forDate(new FakePrefs(), "15-10-2026"));
    }

    @Test
    public void creditedSumIsAvailableTheSameDay() {
        FakePrefs prefs = new FakePrefs();
        FoodConsumed.record(prefs, "15-10-2026", 1_850);
        assertEquals(Integer.valueOf(1_850), FoodConsumed.forDate(prefs, "15-10-2026"));
    }

    @Test
    public void ignoredOnAnotherDay() {
        FakePrefs prefs = new FakePrefs();
        FoodConsumed.record(prefs, "15-10-2026", 1_850);
        assertNull(FoodConsumed.forDate(prefs, "16-10-2026"));
        assertNull(FoodConsumed.forDate(prefs, null));
    }

    @Test
    public void latestCreditReplacesEarlierOne() {
        FakePrefs prefs = new FakePrefs();
        FoodConsumed.record(prefs, "15-10-2026", 900);
        FoodConsumed.record(prefs, "15-10-2026", 2_100);
        assertEquals(Integer.valueOf(2_100), FoodConsumed.forDate(prefs, "15-10-2026"));
    }

    @Test
    public void doesNotTouchOtherPrefs() {
        FakePrefs prefs = new FakePrefs();
        prefs.values.put("bmr_applied_date", "15-10-2026");
        FoodConsumed.record(prefs, "15-10-2026", 500);
        assertEquals("15-10-2026", prefs.values.get("bmr_applied_date"));
        assertEquals(3, prefs.values.size());
    }

    /** Minimal in-memory SharedPreferences for JVM tests. */
    private static final class FakePrefs implements SharedPreferences {
        final Map<String, Object> values = new HashMap<>();

        @Override public Map<String, ?> getAll() { return values; }
        @Override public String getString(String k, String d) { return values.containsKey(k) ? (String) values.get(k) : d; }
        @Override public Set<String> getStringSet(String k, Set<String> d) { return d; }
        @Override public int getInt(String k, int d) { return values.containsKey(k) ? (Integer) values.get(k) : d; }
        @Override public long getLong(String k, long d) { return d; }
        @Override public float getFloat(String k, float d) { return d; }
        @Override public boolean getBoolean(String k, boolean d) { return d; }
        @Override public boolean contains(String k) { return values.containsKey(k); }
        @Override public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener l) {}
        @Override public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener l) {}

        @Override public Editor edit() {
            final Map<String, Object> pending = new HashMap<>();
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
