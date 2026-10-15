package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountabilityTest {

    @Test
    public void acceptsPositiveWholeNumbers() {
        assertEquals(Integer.valueOf(1), Accountability.parseMissedSteps("1"));
        assertEquals(Integer.valueOf(2_198), Accountability.parseMissedSteps("2198"));
        assertEquals(Integer.valueOf(500), Accountability.parseMissedSteps("  500 "));
        assertEquals(Integer.valueOf(7), Accountability.parseMissedSteps("007"));
        assertEquals(Integer.valueOf(999_999_999), Accountability.parseMissedSteps("999999999"));
    }

    @Test
    public void rejectsEmptyZeroNegativeAndNonNumeric() {
        assertNull(Accountability.parseMissedSteps(null));
        assertNull(Accountability.parseMissedSteps(""));
        assertNull(Accountability.parseMissedSteps("   "));
        assertNull(Accountability.parseMissedSteps("0"));
        assertNull(Accountability.parseMissedSteps("000"));
        assertNull(Accountability.parseMissedSteps("-100"));
        assertNull(Accountability.parseMissedSteps("+100"));
        assertNull(Accountability.parseMissedSteps("12.5"));
        assertNull(Accountability.parseMissedSteps("1,000"));
        assertNull(Accountability.parseMissedSteps("abc"));
        assertNull(Accountability.parseMissedSteps("10 steps"));
    }

    @Test
    public void rejectsNumbersTooLargeForAnInt() {
        assertNull(Accountability.parseMissedSteps("1000000000"));
        assertNull(Accountability.parseMissedSteps("99999999999999999999"));
    }

    @Test
    public void creditIsMissedStepsTimesStepsNumerator() {
        // Default Steps Numerator 0.089: 1,000 missed steps → 89 calories
        assertEquals(89, Accountability.toCredit(1_000, 0.089));
        // Weight-based numerator, e.g. 257 lb × 0.000446 = 0.114622
        assertEquals(252, Accountability.toCredit(2_198, 0.114622));   // 251.94 → 252
    }

    @Test
    public void creditRoundsToNearestWholeCalorie() {
        assertEquals(1, Accountability.toCredit(6, 0.089));   // 0.534 → 1
        assertEquals(0, Accountability.toCredit(5, 0.089));   // 0.445 → 0, nothing to credit
    }

    @Test
    public void unusableInputsCreditNothing() {
        assertEquals(0, Accountability.toCredit(0, 0.089));
        assertEquals(0, Accountability.toCredit(-100, 0.089));
        assertEquals(0, Accountability.toCredit(1_000, 0));
        assertEquals(0, Accountability.toCredit(1_000, -0.089));
        assertEquals(0, Accountability.toCredit(1_000, Double.NaN));
        assertEquals(0, Accountability.toCredit(1_000, Double.POSITIVE_INFINITY));
    }

    @Test
    public void largestAllowedInputDoesNotOverflow() {
        assertEquals(Integer.MAX_VALUE, Accountability.toCredit(999_999_999, 1_000_000));
        assertEquals(89_000_000, Accountability.toCredit(999_999_999, 0.089)); // 88,999,999.9 → 89,000,000
    }
}
