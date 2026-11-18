package ese.com.caloriecountdownappforandroidbrown;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Pure forecast logic behind {@link CCD_GUI_CD_CIF1#mDayZero}.
 *
 * Rule (client spec, Version 2.0.1): the budgeted Countdown Balance falls by
 * {@link #DAILY_BUDGET_DECREMENT} CR per day from the Opening Balance until it reaches 0.
 *   Opening Balance 77,000 → 770 days: Day 1 budget 76,900, Day 2 76,800 ... Day 770 budget 0.
 *
 * Day N is dated Start Weight-Loss date + (N - 1) days. Actual balances are unknown for
 * forecast days, so actualDayEnd is left empty ("" = no record, never 0) and actualDayEndBalance
 * stays at its default; they are filled in only from the saved Day End Balance of that date.
 */
final class DayZeroForecast {

    /** Budgeted CR countdown per day for the mDayZero forecast. */
    static final int DAILY_BUDGET_DECREMENT = 100;

    private DayZeroForecast() {}

    /**
     * Number of forecast days until the budget reaches 0. A remainder (e.g. 77,050 CR)
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

    // ── Actual Balance ───────────────────────────────────────────────────────

    /** Calendar date of a forecast day, "yyyy-MM-dd" — the same key as DayEndBalance.dateKey. */
    static String dateKey(DayCiF1005 day) {
        return day.getDay().toLocalDate().toString();
    }

    /** True when the day has a saved Day End Balance (a missing actual is never treated as 0). */
    static boolean hasActual(DayCiF1005 day) {
        return day.getActualDayEnd() != null && !day.getActualDayEnd().isEmpty();
    }

    /** Sets the day's Actual Balance from the saved Day End Balance, or clears it when null. */
    static void setActual(DayCiF1005 day, Integer dayEndBalance) {
        if (dayEndBalance == null) {
            day.setActualDayEnd("");
            day.setActualDayEndBalance(0);
        } else {
            day.setActualDayEnd(String.valueOf(dayEndBalance));
            day.setActualDayEndBalance(dayEndBalance);
        }
    }

    /**
     * Applies saved Day End Balances to the matching forecast dates only.
     *
     * @param actualsByDate "yyyy-MM-dd" → Day End Balance
     * @return number of days that received an Actual Balance
     */
    static int applyActuals(List<DayCiF1005> days, Map<String, Integer> actualsByDate) {
        int applied = 0;
        for (DayCiF1005 day : days) {
            Integer actual = actualsByDate.get(dateKey(day));
            setActual(day, actual);
            if (actual != null) applied++;
        }
        return applied;
    }

    /** Index of the forecast day for {@code date}, or -1 when the date is outside the forecast. */
    static int indexOf(List<DayCiF1005> days, LocalDate date) {
        if (days == null || days.isEmpty() || date == null) return -1;
        long index = java.time.temporal.ChronoUnit.DAYS.between(days.get(0).getDay().toLocalDate(), date);
        return (index >= 0 && index < days.size()) ? (int) index : -1;
    }
}
