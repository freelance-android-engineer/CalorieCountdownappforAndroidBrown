package ese.com.caloriecountdownappforandroidbrown;

/**
 * Pure Weight-Loss Maintenance Mode transition rules, applied once per daily cycle after the
 * Countdown Report. No Android dependencies so it can be unit tested on the JVM.
 *
 * Sign convention (from CCD_GUI_CD_CIF1): Countdown (debit) lowers the Main Countdown Balance,
 * Countup (credit) raises it.
 *
 * Countdown Mode:
 *   balance > 0           → stays in Countdown Mode, nothing changes.
 *   balance <= 0 (journey started) → enters Maintenance Mode; Main Balance is held at 0 and any
 *                           amount below 0 is credited to the surplus account.
 * Maintenance Mode:
 *   balance < 0           → amount below 0 credited to surplus; Main Balance held at 0.
 *   balance == 0          → no change.
 *   balance > 0           → positive balance stays in the Main Balance; returns to Countdown Mode.
 */
final class MaintenanceModeCalculator {

    enum Transition { NONE, ENTERED_MAINTENANCE, STAYED_IN_MAINTENANCE, RETURNED_TO_COUNTDOWN }

    static final class Result {
        final boolean maintenanceActive;
        final int mainBalance;
        final int surplusCredit;
        final Transition transition;

        Result(boolean maintenanceActive, int mainBalance, int surplusCredit, Transition transition) {
            this.maintenanceActive = maintenanceActive;
            this.mainBalance = mainBalance;
            this.surplusCredit = surplusCredit;
            this.transition = transition;
        }
    }

    private MaintenanceModeCalculator() {}

    /**
     * @param maintenanceActive whether Maintenance Mode is currently active
     * @param mainBalance       Main Countdown Balance after the daily cycle
     * @param journeyStarted    whether Start Weight-Loss has set an Opening Balance; without one a
     *                          0 balance is "not started", not "reached 0"
     */
    static Result settle(boolean maintenanceActive, int mainBalance, boolean journeyStarted) {
        if (!maintenanceActive) {
            if (mainBalance > 0 || !journeyStarted) {
                return new Result(false, mainBalance, 0, Transition.NONE);
            }
            return new Result(true, 0, -mainBalance, Transition.ENTERED_MAINTENANCE);
        }
        if (mainBalance > 0) {
            return new Result(false, mainBalance, 0, Transition.RETURNED_TO_COUNTDOWN);
        }
        return new Result(true, 0, -mainBalance, Transition.STAYED_IN_MAINTENANCE);
    }
}
