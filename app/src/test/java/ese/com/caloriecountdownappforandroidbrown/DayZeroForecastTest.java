package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DayZeroForecastTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 10, 12, 9, 30);

    @Test
    public void clientExample_10000OpeningBalance_is400DaysCountingDown25() {
        List<DayCiF1005> days = DayZeroForecast.build(10_000, START);

        assertEquals(400, days.size());
        assertEquals("9975", days.get(0).getBudget());
        assertEquals("9950", days.get(1).getBudget());
        assertEquals("9925", days.get(2).getBudget());
        assertEquals("9900", days.get(3).getBudget());
        assertEquals("0", days.get(399).getBudget());
        assertEquals(0, days.get(399).getBudgetedDayEndBalanceForThisDay());
    }

    @Test
    public void dates_startOnStartWeightLossDateAndAdvanceOneDayPerObject() {
        List<DayCiF1005> days = DayZeroForecast.build(10_000, START);

        assertEquals(START, days.get(0).getDay());
        assertEquals(START.plusDays(1), days.get(1).getDay());
        assertEquals(LocalDateTime.of(2027, 11, 15, 9, 30), days.get(399).getDay());
    }

    @Test
    public void balances_chainFromOpeningBalance() {
        DayCiF1005 day2 = DayZeroForecast.build(10_000, START).get(1);

        assertEquals(10_000, day2.getStartBalanceBFWD());
        assertEquals(9_975, day2.getCurrentBalance());
        assertEquals(9_950, day2.getEndBalanceCFWD());
        assertEquals(9_950, day2.getBudgetedDayEndBalanceForThisDay());
    }

    @Test
    public void actualBalance_isNotInventedForForecastDays() {
        DayCiF1005 day1 = DayZeroForecast.build(10_000, START).get(0);

        assertEquals("", day1.getActualDayEnd());
        assertEquals(0, day1.getActualDayEndBalance());
    }

    @Test
    public void nonDivisibleOpeningBalance_addsFinalDayClampedToZero() {
        List<DayCiF1005> days = DayZeroForecast.build(10_010, START);

        assertEquals(401, days.size());
        assertEquals("10", days.get(399).getBudget());
        assertEquals("0", days.get(400).getBudget());
    }

    @Test
    public void zeroOrNegativeOpeningBalance_givesEmptyForecast() {
        assertTrue(DayZeroForecast.build(0, START).isEmpty());
        assertTrue(DayZeroForecast.build(-500, START).isEmpty());
    }

    @Test
    public void isSameForecast_detectsRepeatedInitialization() {
        List<DayCiF1005> days = DayZeroForecast.build(10_000, START);

        assertTrue(DayZeroForecast.isSameForecast(days, 10_000, START));
        assertFalse(DayZeroForecast.isSameForecast(days, 12_000, START));
        assertFalse(DayZeroForecast.isSameForecast(days, 10_000, START.plusDays(1)));
        assertFalse(DayZeroForecast.isSameForecast(new java.util.ArrayList<>(), 10_000, START));
    }
}
