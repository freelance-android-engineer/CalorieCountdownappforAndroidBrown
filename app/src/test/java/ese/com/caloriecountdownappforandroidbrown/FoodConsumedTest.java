package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FoodConsumedTest {

    @Test
    public void nothingCreditedIsNull() {
        assertNull(FoodConsumed.forDate(new FakeSharedPreferences(), "15-10-2026"));
    }

    @Test
    public void creditedSumIsAvailableTheSameDay() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        FoodConsumed.record(prefs, "15-10-2026", 1_850);
        assertEquals(Integer.valueOf(1_850), FoodConsumed.forDate(prefs, "15-10-2026"));
    }

    @Test
    public void ignoredOnAnotherDay() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        FoodConsumed.record(prefs, "15-10-2026", 1_850);
        assertNull(FoodConsumed.forDate(prefs, "16-10-2026"));
        assertNull(FoodConsumed.forDate(prefs, null));
    }

    @Test
    public void latestCreditReplacesEarlierOne() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        FoodConsumed.record(prefs, "15-10-2026", 900);
        FoodConsumed.record(prefs, "15-10-2026", 2_100);
        assertEquals(Integer.valueOf(2_100), FoodConsumed.forDate(prefs, "15-10-2026"));
    }

    @Test
    public void doesNotTouchOtherPrefs() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        prefs.values.put("bmr_applied_date", "15-10-2026");
        FoodConsumed.record(prefs, "15-10-2026", 500);
        assertEquals("15-10-2026", prefs.values.get("bmr_applied_date"));
        assertEquals(3, prefs.values.size());
    }
}
