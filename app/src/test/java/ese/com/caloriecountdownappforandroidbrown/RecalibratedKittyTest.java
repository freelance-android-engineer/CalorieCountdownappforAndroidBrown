package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RecalibratedKittyTest {

    private static final String TODAY = "21-10-2026";

    @Test
    public void notRecalibratedUsesNormalRules() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        assertNull(RecalibratedKitty.previousDayEnd(prefs, TODAY));
        assertTrue(RecalibratedKitty.bmrApplied(prefs, TODAY, true));
        assertFalse(RecalibratedKitty.bmrApplied(prefs, TODAY, false));
    }

    @Test
    public void recalibrationResetsKittyToFullBmr() {
        // Client report: yesterday's Day End 71,917, recalibrated to 67,066 → Kitty showed 7,101.
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        RecalibratedKitty.record(prefs, TODAY, 67_066, false);

        int previousDayEnd = RecalibratedKitty.previousDayEnd(prefs, TODAY);
        KittyCalculator.Result result = KittyCalculator.calculate(previousDayEnd, 67_066, 2_500,
                RecalibratedKitty.bmrApplied(prefs, TODAY, false));

        assertEquals(KittyCalculator.Outcome.KITTY, result.outcome);
        assertEquals(2_500, result.value);
    }

    @Test
    public void creditAfterRecalibrationReducesKitty() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        RecalibratedKitty.record(prefs, TODAY, 67_066, false);

        KittyCalculator.Result result = KittyCalculator.calculate(
                RecalibratedKitty.previousDayEnd(prefs, TODAY), 67_066 + 600, 2_500, false);

        assertEquals(1_900, result.value);
    }

    @Test
    public void bmrAppliedBeforeRecalibrationNoLongerCounts() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        RecalibratedKitty.record(prefs, TODAY, 67_066, true);

        boolean bmrApplied = RecalibratedKitty.bmrApplied(prefs, TODAY, true);
        KittyCalculator.Result result = KittyCalculator.calculate(
                RecalibratedKitty.previousDayEnd(prefs, TODAY), 67_066, 2_500, bmrApplied);

        assertFalse(bmrApplied);
        assertEquals(2_500, result.value);
    }

    @Test
    public void bmrAppliedAfterRecalibrationStillLeavesFullKitty() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        RecalibratedKitty.record(prefs, TODAY, 67_066, false);

        // Applying BMR takes 2,500 off the balance and marks BMR applied for today.
        boolean bmrApplied = RecalibratedKitty.bmrApplied(prefs, TODAY, true);
        KittyCalculator.Result result = KittyCalculator.calculate(
                RecalibratedKitty.previousDayEnd(prefs, TODAY), 67_066 - 2_500, 2_500, bmrApplied);

        assertTrue(bmrApplied);
        assertEquals(2_500, result.value);
    }

    @Test
    public void ignoredOnAnotherDay() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        RecalibratedKitty.record(prefs, TODAY, 67_066, true);
        assertNull(RecalibratedKitty.previousDayEnd(prefs, "22-10-2026"));
        assertNull(RecalibratedKitty.previousDayEnd(prefs, null));
        assertTrue(RecalibratedKitty.bmrApplied(prefs, "22-10-2026", true));
    }
}
