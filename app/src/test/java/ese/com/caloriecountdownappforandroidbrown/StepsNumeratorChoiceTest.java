package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StepsNumeratorChoiceTest {

    @Test
    public void recommendedValueIs0089() {
        assertEquals(0.089, StepsNumeratorChoice.RECOMMENDED, 0.0);
    }

    @Test
    public void validCustomValuesAreAccepted() {
        assertEquals(0.089, StepsNumeratorChoice.parseCustom("0.089"), 0.0);
        assertEquals(0.11, StepsNumeratorChoice.parseCustom(" 0.11 "), 0.0);
        assertEquals(0.05, StepsNumeratorChoice.parseCustom(".05"), 0.0);
        assertEquals(0.089, StepsNumeratorChoice.parseCustom("0,089"), 0.0); // decimal comma keyboards
        assertEquals(1.0, StepsNumeratorChoice.parseCustom("1"), 0.0);
    }

    @Test
    public void invalidCustomValuesAreRejected() {
        assertNull(StepsNumeratorChoice.parseCustom(null));
        assertNull(StepsNumeratorChoice.parseCustom(""));
        assertNull(StepsNumeratorChoice.parseCustom("   "));
        assertNull(StepsNumeratorChoice.parseCustom("."));
        assertNull(StepsNumeratorChoice.parseCustom("abc"));
        assertNull(StepsNumeratorChoice.parseCustom("0"));
        assertNull(StepsNumeratorChoice.parseCustom("0.0"));
        assertNull(StepsNumeratorChoice.parseCustom("-0.089"));
        assertNull(StepsNumeratorChoice.parseCustom("1.5"));
        assertNull(StepsNumeratorChoice.parseCustom("NaN"));
        assertNull(StepsNumeratorChoice.parseCustom("Infinity"));
        assertNull(StepsNumeratorChoice.parseCustom("1e-2"));
    }

    @Test
    public void onlyOptionsABCAreExplicitChoices() {
        assertTrue(StepsNumeratorChoice.isExplicitChoice(StepsNumeratorChoice.SOURCE_ACCEPTED));
        assertTrue(StepsNumeratorChoice.isExplicitChoice(StepsNumeratorChoice.SOURCE_CUSTOM));
        assertTrue(StepsNumeratorChoice.isExplicitChoice(StepsNumeratorChoice.SOURCE_RECOMMENDED));
        // A weight-derived value saved at Start Weight Loss (e.g. ~0.11) was never confirmed
        assertFalse(StepsNumeratorChoice.isExplicitChoice(StepsNumeratorChoice.SOURCE_WEIGHT_DERIVED));
        assertFalse(StepsNumeratorChoice.isExplicitChoice(null));
    }

    @Test
    public void accountabilityUsesTheChosenNumerator() {
        // Same 5,000 steps with the old weight-derived value vs the recommended value
        assertEquals(Integer.valueOf(550), Accountability.toPoints(5_000, 0.11));
        assertEquals(Integer.valueOf(445), Accountability.toPoints(5_000, StepsNumeratorChoice.RECOMMENDED));
    }

    @Test
    public void weightFactorMatchesStartWeightLoss() {
        // Start Weight Loss: stepsNumerator = startWeightPounds × 0.000446
        assertEquals(0.0892, 200 * StepsNumeratorChoice.WEIGHT_FACTOR, 1e-9);
    }
}
