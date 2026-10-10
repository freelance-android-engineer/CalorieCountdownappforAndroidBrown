package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KittyCalculatorTest {

    private static void assertResult(KittyCalculator.Result r, KittyCalculator.Outcome outcome, int value) {
        assertEquals(outcome, r.outcome);
        assertEquals(value, r.value);
    }

    @Test
    public void recommendedStepChallengeIs15500Steps() {
        assertEquals(15_500, KittyCalculator.RECOMMENDED_STEP_CHALLENGE);
        assertEquals("Client Step Challenge = 15,500 Steps or equivalent Activity.",
                KittyCalculator.recommendedStepChallengeText());
    }

    @Test
    public void dayEndTargetIsPreviousDayEndMinus250() {
        assertEquals(9_750, KittyCalculator.dayEndTarget(10_000));
    }

    // Scenario 1: 2,500 - (11,000 - 9,750) = 1,250
    @Test
    public void scenario1_aboveTarget_bmrNotApplied() {
        KittyCalculator.Result r = KittyCalculator.calculate(10_000, 11_000, 2_500, false);
        assertEquals(9_750, r.dayEndTarget);
        assertResult(r, KittyCalculator.Outcome.KITTY, 1_250);
    }

    // Scenario 2: 2,500 + 150 = 2,650
    @Test
    public void scenario2_belowTarget_bmrNotApplied() {
        assertResult(KittyCalculator.calculate(10_000, 9_600, 2_500, false), KittyCalculator.Outcome.KITTY, 2_650);
    }

    // Scenario 3: overdraft of 1,250 → Step Challenge, burn 1,250
    @Test
    public void scenario3_aboveTarget_bmrApplied() {
        assertResult(KittyCalculator.calculate(10_000, 11_000, 2_500, true), KittyCalculator.Outcome.STEP_CHALLENGE, 1_250);
    }

    @Test
    public void debitAndCreditUseLatestBalance() {
        // Credit 10,000 → 11,000, then debit 11,000 → 10,500: each call uses the mutated balance.
        assertResult(KittyCalculator.calculate(10_000, 11_000, 2_500, false), KittyCalculator.Outcome.KITTY, 1_250);
        assertResult(KittyCalculator.calculate(10_000, 10_500, 2_500, false), KittyCalculator.Outcome.KITTY, 1_750);
    }

    @Test
    public void equalToTarget() {
        assertResult(KittyCalculator.calculate(10_000, 9_750, 2_500, false), KittyCalculator.Outcome.KITTY, 2_500);
        assertResult(KittyCalculator.calculate(10_000, 9_750, 2_500, true), KittyCalculator.Outcome.ZERO, 0);
    }

    @Test
    public void bmrAppliedBelowTargetLeavesKitty() {
        assertResult(KittyCalculator.calculate(10_000, 9_600, 2_500, true), KittyCalculator.Outcome.KITTY, 150);
    }

    @Test
    public void bmrNotAppliedOverdraftBeyondBmr() {
        // 2,500 - (13,000 - 9,750) = -750 → burn 750
        assertResult(KittyCalculator.calculate(10_000, 13_000, 2_500, false), KittyCalculator.Outcome.STEP_CHALLENGE, 750);
        // exactly BMR over target → zero
        assertResult(KittyCalculator.calculate(10_000, 12_250, 2_500, false), KittyCalculator.Outcome.ZERO, 0);
    }

    @Test
    public void zeroBalanceAndZeroBmr() {
        assertResult(KittyCalculator.calculate(0, 0, 2_500, false), KittyCalculator.Outcome.KITTY, 2_250);
        assertResult(KittyCalculator.calculate(10_000, 11_000, 0, false), KittyCalculator.Outcome.STEP_CHALLENGE, 1_250);
    }
}
