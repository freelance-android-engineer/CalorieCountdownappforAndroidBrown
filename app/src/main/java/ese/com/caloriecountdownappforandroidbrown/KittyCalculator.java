package ese.com.caloriecountdownappforandroidbrown;

/**
 * Pure calculation for the Countdown Report (Kitty) dialog. No Android dependencies
 * so it can be unit tested on the JVM.
 *
 * Client rules:
 *   dayEndTarget = previousDayEndBalance - 250
 *   difference   = currentBalance - dayEndTarget
 *
 *   BMR NOT applied:  kitty = BMR - difference
 *                     (balance below target → shortage is added to BMR)
 *   BMR applied:      difference > 0 is an overdraft → Step Challenge, burn = difference
 */
public final class KittyCalculator {

    public static final int DAY_END_TARGET_DROP = 250;

    public enum Outcome {
        /** value = calories left in the Kitty (> 0). */
        KITTY,
        /** Nothing left in the Kitty and no overdraft. */
        ZERO,
        /** value = calories to burn (> 0) — show the Step Challenge. */
        STEP_CHALLENGE
    }

    public static final class Result {
        public final Outcome outcome;
        public final int value;
        public final int dayEndTarget;

        Result(Outcome outcome, int value, int dayEndTarget) {
            this.outcome = outcome;
            this.value = value;
            this.dayEndTarget = dayEndTarget;
        }
    }

    private KittyCalculator() {}

    public static int dayEndTarget(int previousDayEndBalance) {
        return previousDayEndBalance - DAY_END_TARGET_DROP;
    }

    public static Result calculate(int previousDayEndBalance, int currentBalance,
                                   int bmr, boolean bmrApplied) {
        int target = dayEndTarget(previousDayEndBalance);
        int difference = currentBalance - target;

        // When BMR is already applied it must not be added again — the remaining
        // Kitty is simply the room left below the target.
        int kitty = bmrApplied ? -difference : bmr - difference;

        if (kitty > 0) return new Result(Outcome.KITTY, kitty, target);
        if (kitty == 0) return new Result(Outcome.ZERO, 0, target);
        return new Result(Outcome.STEP_CHALLENGE, -kitty, target);
    }
}
