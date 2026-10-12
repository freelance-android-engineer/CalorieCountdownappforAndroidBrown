package ese.com.caloriecountdownappforandroidbrown;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure forecast logic behind {@link CCD_GUI_CD_CIF1#mDayZero}.
 *
 * Rule (per client spec): the budgeted Countdown Balance falls by
 * {@link #DAILY_BUDGET_DECREMENT} CR per day from the Opening Balance until it reaches 0.
 *   Opening Balance 10,000 → Day 1 budget 9,975, Day 2 9,950 ... Day 400 budget 0.
 *
 * Day N is dated Start Weight-Loss date + (N - 1) days. Actual balances are unknown for
 * forecast days, so actualDayEnd is left empty and actualDayEndBalance stays at its default;
 * they are filled in from real day-end data, never from the forecast.
 */
final class DayZeroForecast {

    /** Budgeted CR countdown per day for the mDayZero forecast. */
    static final int DAILY_BUDGET_DECREMENT = 25;

    private DayZeroForecast() {}

    /**
     * Number of forecast days until the budget reaches 0. A remainder (e.g. 10,010 CR)
     * adds one final day whose budget is clamped to 0.
     */
    static int numberOfDays(int openingBalance) {
        if (openingBalance <= 0) {
            return 0;
        }
        return (openingBalance + DAILY_BUDGET_DECREMENT - 1) / DAILY_BUDGET_DECREMENT;
    }

    /**
     * Builds the forecast list. Returns an empty list when the Opening Balance is zero or negative.
     *
     * @param openingBalance Opening Balance calculated by Start Weight-Loss
     * @param startDate      Start Weight-Loss date (date of Day 1)
     */
    static List<DayCiF1005> build(int openingBalance, LocalDateTime startDate) {
        int days = numberOfDays(openingBalance);
        List<DayCiF1005> result = new ArrayList<>(days);

        int startOfDayBudget = openingBalance;
        for (int i = 0; i < days; i++) {
            int budgetBalance = Math.max(0, startOfDayBudget - DAILY_BUDGET_DECREMENT);
            DayCiF1005 day = new DayCiF1005(i, startOfDayBudget, openingBalance, budgetBalance,
                    startDate.plusDays(i), String.valueOf(budgetBalance), "");
            day.setBudgetedDayEndBalanceStartWeightLoss(openingBalance);
            day.setBudgetedDayEndBalanceStartPreviousDay(startOfDayBudget);
            day.setBudgetedDayEndBalanceForThisDay(budgetBalance);
            result.add(day);
            startOfDayBudget = budgetBalance;
        }
        return result;
    }

    /**
     * True if {@code existing} is already the forecast for this Opening Balance and start date,
     * so a repeated trigger of the same workflow can skip rebuilding it.
     */
    static boolean isSameForecast(List<DayCiF1005> existing, int openingBalance, LocalDateTime startDate) {
        return existing != null
                && !existing.isEmpty()
                && existing.size() == numberOfDays(openingBalance)
                && existing.get(0).getStartBalanceBFWD() == openingBalance
                && existing.get(0).getDay().equals(startDate);
    }
}
