package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DayZeroForecastTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 10, 12, 9, 30);

    @Test
    public void clientExample_77000OpeningBalance_is770DaysCountingDown100() {
        List<DayCiF1005> days = DayZeroForecast.build(77_000, START);

        assertEquals(770, days.size());
        assertEquals("76900", days.get(0).getBudget());
        assertEquals("76800", days.get(1).getBudget());
        assertEquals("76700", days.get(2).getBudget());
        assertEquals("0", days.get(769).getBudget());
        assertEquals(0, days.get(769).getBudgetedDayEndBalanceForThisDay());
        for (int i = 1; i < days.size(); i++) {
            assertEquals(100, days.get(i - 1).getEndBalanceCFWD() - days.get(i).getEndBalanceCFWD());
        }
    }

    @Test
    public void dates_startOnStartWeightLossDateAndAdvanceOneDayPerObject() {
        List<DayCiF1005> days = DayZeroForecast.build(77_000, START);

        assertEquals(START, days.get(0).getDay());
        assertEquals(START.plusDays(1), days.get(1).getDay());
        assertEquals(START.plusDays(769), days.get(769).getDay());
        assertEquals("2028-11-19", DayZeroForecast.dateKey(days.get(769))); // goal date, across a leap year
    }

    @Test
    public void balances_chainFromOpeningBalance() {
        DayCiF1005 day2 = DayZeroForecast.build(77_000, START).get(1);

        assertEquals(77_000, day2.getStartBalanceBFWD());
        assertEquals(76_900, day2.getCurrentBalance());
        assertEquals(76_800, day2.getEndBalanceCFWD());
        assertEquals(76_800, day2.getBudgetedDayEndBalanceForThisDay());
    }

    @Test
    public void actualBalance_isNotInventedForForecastDays() {
        DayCiF1005 day1 = DayZeroForecast.build(77_000, START).get(0);

        assertEquals("", day1.getActualDayEnd());
        assertEquals(0, day1.getActualDayEndBalance());
        assertFalse(DayZeroForecast.hasActual(day1));
    }

    @Test
    public void nonDivisibleOpeningBalance_addsFinalDayClampedToZero() {
        List<DayCiF1005> days = DayZeroForecast.build(77_050, START);

        assertEquals(771, days.size());
        assertEquals("50", days.get(769).getBudget());
        assertEquals("0", days.get(770).getBudget());
    }

    @Test
    public void zeroOrNegativeOpeningBalance_givesEmptyForecast() {
        assertTrue(DayZeroForecast.build(0, START).isEmpty());
        assertTrue(DayZeroForecast.build(-500, START).isEmpty());
        assertEquals(0, DayZeroForecast.numberOfDays(0));
        assertEquals(1, DayZeroForecast.numberOfDays(1));
    }

    @Test
    public void isSameForecast_detectsRepeatedInitialization() {
        List<DayCiF1005> days = DayZeroForecast.build(77_000, START);

        assertTrue(DayZeroForecast.isSameForecast(days, 77_000, START));
        assertFalse(DayZeroForecast.isSameForecast(days, 80_000, START));
        assertFalse(DayZeroForecast.isSameForecast(days, 77_000, START.plusDays(1)));
        assertFalse(DayZeroForecast.isSameForecast(new java.util.ArrayList<>(), 77_000, START));
    }

    // ── Actual Balance (Day End Balance integration) ──

    @Test
    public void dayEndBalanceUpdatesOnlyTheMatchingDate() {
        List<DayCiF1005> days = DayZeroForecast.build(77_000, START);
        Map<String, Integer> saved = new HashMap<>();
        saved.put("2026-10-12", 76_850);   // Day 1
        saved.put("2026-10-14", 76_500);   // Day 3
        saved.put("2026-10-11", 99_999);   // before the forecast: ignored

        assertEquals(2, DayZeroForecast.applyActuals(days, saved));
        assertEquals("76850", days.get(0).getActualDayEnd());
        assertEquals(76_850, days.get(0).getActualDayEndBalance());
        assertFalse("Day 2 has no Day End Balance — not 0", DayZeroForecast.hasActual(days.get(1)));
        assertEquals(76_500, days.get(2).getActualDayEndBalance());
    }

    @Test
    public void latestDayEndBalanceOfADateWins() {
        DayCiF1005 day = DayZeroForecast.build(77_000, START).get(0);
        DayZeroForecast.setActual(day, 76_850);
        DayZeroForecast.setActual(day, 76_700);
        assertEquals(76_700, day.getActualDayEndBalance());
        DayZeroForecast.setActual(day, 0);
        assertTrue("a real 0 balance is kept as an actual", DayZeroForecast.hasActual(day));
    }

    @Test
    public void indexOfFindsForecastDayByDate() {
        List<DayCiF1005> days = DayZeroForecast.build(77_000, START);
        assertEquals(0, DayZeroForecast.indexOf(days, LocalDate.of(2026, 10, 12)));
        assertEquals(769, DayZeroForecast.indexOf(days, LocalDate.of(2028, 11, 19)));
        assertEquals(-1, DayZeroForecast.indexOf(days, LocalDate.of(2026, 10, 11)));
        assertEquals(-1, DayZeroForecast.indexOf(days, LocalDate.of(2028, 11, 20)));
    }
}
