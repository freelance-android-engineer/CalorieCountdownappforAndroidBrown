package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SumCaloriesBmrTest {

    @Test
    public void withoutApplyingBmr_totalIsUnchanged() {
        SumCaloriesBmr sum = new SumCaloriesBmr(2_000);
        assertFalse(sum.isBmrApplied());
        assertEquals(2_000, sum.getTotal());
    }

    @Test
    public void clientExample_2000Minus1500Is500() {
        SumCaloriesBmr sum = new SumCaloriesBmr(2_000);
        assertTrue(sum.applyBmr(1_500));
        assertEquals(500, sum.getTotal());
        assertEquals(1_500, sum.getAppliedBmr());
    }

    @Test
    public void repeatedClicks_subtractBmrOnlyOnce() {
        SumCaloriesBmr sum = new SumCaloriesBmr(3_000);
        assertTrue(sum.applyBmr(2_500));
        assertFalse(sum.applyBmr(2_500));
        assertFalse(sum.applyBmr(2_500));
        assertEquals(500, sum.getTotal());
    }

    @Test
    public void changedBmrSelectionAfterApplying_doesNotSubtractAgain() {
        SumCaloriesBmr sum = new SumCaloriesBmr(3_000);
        sum.applyBmr(2_500);
        assertFalse(sum.applyBmr(2_000));
        assertEquals(500, sum.getTotal());
        assertEquals(2_500, sum.getAppliedBmr());
    }

    @Test
    public void missingBmr_isNotApplied() {
        SumCaloriesBmr sum = new SumCaloriesBmr(2_000);
        assertFalse(sum.applyBmr(0));
        assertFalse(sum.isBmrApplied());
        assertEquals(2_000, sum.getTotal());
    }

    @Test
    public void bmrLargerThanTotal_givesNegativeTotal() {
        SumCaloriesBmr sum = new SumCaloriesBmr(1_200);
        sum.applyBmr(2_000);
        assertEquals(-800, sum.getTotal());
    }

    @Test
    public void selectedBmr_usesExistingGenderMapping() {
        assertEquals(2_500, FourPMDebitStepsProcessor.resolveDailyBudget("Male"));
        assertEquals(2_000, FourPMDebitStepsProcessor.resolveDailyBudget("Female"));
    }
}
