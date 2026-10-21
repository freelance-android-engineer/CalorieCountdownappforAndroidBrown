package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccountabilityTest {

    // ── Step input (missed steps for Accountability, surplus steps for de-Accountability) ──

    @Test
    public void acceptsPositiveWholeNumbers() {
        assertEquals(Integer.valueOf(1), Accountability.parseMissedSteps("1"));
        assertEquals(Integer.valueOf(2_198), Accountability.parseMissedSteps("2198"));
        assertEquals(Integer.valueOf(500), Accountability.parseMissedSteps("  500 "));
        assertEquals(Integer.valueOf(7), Accountability.parseMissedSteps("007"));
        assertEquals(Integer.valueOf(999_999_999), Accountability.parseMissedSteps("999999999"));
        assertEquals(Integer.valueOf(1_500), Accountability.parseSurplusSteps("1500"));
    }

    @Test
    public void rejectsEmptyZeroNegativeAndNonNumeric() {
        String[] invalid = {null, "", "   ", "0", "000", "-100", "+100", "12.5", "1,000", "abc", "10 steps"};
        for (String input : invalid) {
            assertNull("missed: " + input, Accountability.parseMissedSteps(input));
            assertNull("surplus: " + input, Accountability.parseSurplusSteps(input));
        }
    }

    @Test
    public void rejectsNumbersTooLargeForAnInt() {
        assertNull(Accountability.parseMissedSteps("1000000000"));
        assertNull(Accountability.parseSurplusSteps("99999999999999999999"));
    }

    // ── Points = steps × Steps Numerator ──

    @Test
    public void pointsAreStepsTimesTheSubscribersNumerator() {
        assertEquals(Integer.valueOf(89), Accountability.toPoints(1_000, 0.089));
        // Surplus example: 17,000 walked − 15,500 target = 1,500 surplus steps
        assertEquals(Integer.valueOf(134), Accountability.toPoints(1_500, 0.089));     // 133.5 → 134
        // A heavier Subscriber has a larger numerator, so the same steps give more points
        assertEquals(Integer.valueOf(252), Accountability.toPoints(2_198, 0.114622));  // 251.94 → 252
    }

    @Test
    public void pointsRoundToNearestWholePoint() {
        assertEquals(Integer.valueOf(1), Accountability.toPoints(6, 0.089));   // 0.534 → 1
        assertEquals(Integer.valueOf(0), Accountability.toPoints(5, 0.089));   // 0.445 → 0
    }

    @Test
    public void zeroNumeratorGivesZeroPoints() {
        assertEquals(Integer.valueOf(0), Accountability.toPoints(1_000, 0.0));
    }

    @Test
    public void missingOrInvalidNumeratorGivesNoPoints() {
        assertNull(Accountability.toPoints(1_000, null));
        assertNull(Accountability.toPoints(1_000, -0.089));
        assertNull(Accountability.toPoints(1_000, Double.NaN));
        assertNull(Accountability.toPoints(1_000, Double.POSITIVE_INFINITY));
    }

    @Test
    public void invalidStepsGiveNoPoints() {
        assertNull(Accountability.toPoints(0, 0.089));
        assertNull(Accountability.toPoints(-100, 0.089));
    }

    @Test
    public void largestAllowedInputDoesNotOverflow() {
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), Accountability.toPoints(999_999_999, 1_000_000.0));
        assertEquals(Integer.valueOf(89_000_000), Accountability.toPoints(999_999_999, 0.089)); // 88,999,999.9
    }

    // ── One event is applied at most once ──

    @Test
    public void eventIsClaimedOnlyOnce() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        String key = Accountability.KEY_LAST_ACCOUNTABILITY_EVENT;
        assertTrue(Accountability.claimEvent(prefs, key, "event-1"));
        assertFalse(Accountability.claimEvent(prefs, key, "event-1"));   // repeated tap / callback
        assertTrue(Accountability.claimEvent(prefs, key, "event-2"));    // a new entry is allowed
        assertFalse(Accountability.claimEvent(prefs, key, "event-2"));
    }

    @Test
    public void creditAndDebitEventsAreTrackedSeparately() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        assertTrue(Accountability.claimEvent(prefs, Accountability.KEY_LAST_ACCOUNTABILITY_EVENT, "same-id"));
        assertTrue(Accountability.claimEvent(prefs, Accountability.KEY_LAST_DE_ACCOUNTABILITY_EVENT, "same-id"));
    }

    @Test
    public void missingEventIdIsNeverClaimed() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        assertFalse(Accountability.claimEvent(prefs, Accountability.KEY_LAST_ACCOUNTABILITY_EVENT, null));
        assertFalse(Accountability.claimEvent(prefs, Accountability.KEY_LAST_ACCOUNTABILITY_EVENT, ""));
    }

    @Test
    public void storageFailureMeansNoClaim() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        prefs.failCommits = true;
        assertFalse(Accountability.claimEvent(prefs, Accountability.KEY_LAST_ACCOUNTABILITY_EVENT, "event-1"));
    }

    // ── Credit screen (calories) ──

    @Test
    public void creditCaloriesUseTheSameInputRules() {
        assertEquals(Integer.valueOf(410), Accountability.parseCalories("410"));
        assertNull(Accountability.parseCalories("0"));
        assertNull(Accountability.parseCalories("-410"));
        assertNull(Accountability.parseCalories("41.0"));
    }

    @Test
    public void creditScreenEventsAreTrackedSeparatelyFromDebitScreen() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        assertTrue(Accountability.claimEvent(prefs, Accountability.KEY_LAST_ACCOUNTABILITY_EVENT, "same-id"));
        assertTrue(Accountability.claimEvent(prefs, Accountability.KEY_LAST_CREDIT_ACCOUNTABILITY_EVENT, "same-id"));
        assertTrue(Accountability.claimEvent(prefs, Accountability.KEY_LAST_CREDIT_DE_ACCOUNTABILITY_EVENT, "same-id"));
    }

    // ── Countdown Report summaries ──

    @Test
    public void creditAccountabilityReport() {
        // Client example: Budget 3500, ate 3910 → 410 above budget → +410
        String report = Accountability.creditReport(true, 410, 71_917, 72_327);
        assertTrue(report.contains("Countdown Report"));
        assertTrue(report.contains("Credit — Accountability"));
        assertTrue(report.contains("410 calories ABOVE your daily Credit Budget"));
        assertTrue(report.contains("410 points added to your Main Countdown Balance"));
        assertTrue(report.contains("Previous Balance:  71,917 Cr"));
        assertTrue(report.contains("New Balance:       72,327 Cr"));
        assertTrue(report.contains("Net Countup:       +410 pts"));
    }

    @Test
    public void creditDeAccountabilityReport() {
        // Client example: Budget 3500, ate 3090 → 410 below budget → -410
        String report = Accountability.creditReport(false, 410, 71_917, 71_507);
        assertTrue(report.contains("Credit — de-Accountability"));
        assertTrue(report.contains("410 calories BELOW your daily Credit Budget"));
        assertTrue(report.contains("410 points taken off your Main Countdown Balance"));
        assertTrue(report.contains("Net Countdown:     -410 pts"));
    }

    @Test
    public void debitAccountabilityReport() {
        // Client example: Budget 15,500 steps, walked 10,500 → 5,000 × 0.089 = 445 → +445
        String report = Accountability.debitReport(true, 5_000, 0.089, 445, 70_000, 70_445);
        assertTrue(report.contains("Debit — Accountability"));
        assertTrue(report.contains("5,000 steps BELOW your daily Steps Budget"));
        assertTrue(report.contains("5,000 steps × 0.089 (Steps Numerator) = 445 points"));
        assertTrue(report.contains("Net Countup:       +445 pts"));
    }

    @Test
    public void debitDeAccountabilityReport() {
        // Client example: Budget 15,500 steps, walked 20,500 → 5,000 × 0.089 = 445 → -445
        String report = Accountability.debitReport(false, 5_000, 0.089, 445, 70_000, 69_555);
        assertTrue(report.contains("Debit — de-Accountability"));
        assertTrue(report.contains("5,000 steps ABOVE your daily Steps Budget"));
        assertTrue(report.contains("445 points taken off your Main Countdown Balance"));
        assertTrue(report.contains("New Balance:       69,555 Cr"));
        assertTrue(report.contains("Net Countdown:     -445 pts"));
    }
}
