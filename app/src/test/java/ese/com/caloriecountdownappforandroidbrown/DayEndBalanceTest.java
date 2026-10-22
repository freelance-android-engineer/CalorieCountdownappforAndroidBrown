package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DayEndBalanceTest {

    private static long at(int year, int month, int day, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    // ── Calendar date of a capture ──

    @Test
    public void captureAt2359IsStoredForThatDay() {
        // Client example date: 23 October 2026
        assertEquals("2026-10-23", DayEndBalance.dateKey(at(2026, Calendar.OCTOBER, 23, 23, 59)));
    }

    @Test
    public void nextAlarmIs2359Tonight() {
        Calendar next = DailyAlarmScheduler.getNextAlarmTime(DayEndBalance.CAPTURE_HOUR,
                DayEndBalance.CAPTURE_MINUTE, at(2026, Calendar.OCTOBER, 23, 21, 0));
        assertEquals(23, next.get(Calendar.DAY_OF_MONTH));
        assertEquals(23, next.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, next.get(Calendar.MINUTE));
        // The receiver stores the capture against the scheduled date, so a late delivery keeps the date
        assertEquals("2026-10-23", DayEndBalance.dateKey(next.getTimeInMillis()));
    }

    @Test
    public void afterMidnightTheNextAlarmIsTheSameEvening() {
        Calendar next = DailyAlarmScheduler.getNextAlarmTime(DayEndBalance.CAPTURE_HOUR,
                DayEndBalance.CAPTURE_MINUTE, at(2026, Calendar.OCTOBER, 24, 0, 5));
        assertEquals("2026-10-24", DayEndBalance.dateKey(next.getTimeInMillis()));
    }

    @Test
    public void captureOnDaylightSavingDayKeepsItsDate() {
        // Clocks change in late October / late March in many zones; 23:59 is never in the gap
        assertEquals("2026-10-25", DayEndBalance.dateKey(at(2026, Calendar.OCTOBER, 25, 23, 59)));
        assertEquals("2026-03-29", DayEndBalance.dateKey(at(2026, Calendar.MARCH, 29, 23, 59)));
    }

    @Test
    public void timestampsSortChronologically() {
        String earlier = DayEndBalance.timestamp(at(2026, Calendar.OCTOBER, 23, 9, 5));
        String later = DayEndBalance.timestamp(at(2026, Calendar.OCTOBER, 23, 23, 59));
        assertTrue(later.compareTo(earlier) > 0);
        assertEquals("2026-10-23 23:59:00", later);
    }

    @Test
    public void onlyWellFormedDateKeysAreAccepted() {
        assertTrue(DayEndBalance.isDateKey("2026-10-23"));
        assertFalse(DayEndBalance.isDateKey("23-10-2026"));
        assertFalse(DayEndBalance.isDateKey(""));
        assertFalse(DayEndBalance.isDateKey(null));
    }

    // ── Missed 23:59 capture: recovery from the last balance of the day ──

    @Test
    public void missingDateIsRecovered() {
        assertTrue(DayEndBalance.shouldRecover(null, null, "2026-10-23 22:10:00"));
    }

    @Test
    public void scheduledCaptureIsNeverReplacedByRecovery() {
        assertFalse(DayEndBalance.shouldRecover(DayEndBalance.SOURCE_SCHEDULED,
                "2026-10-23 23:59:00", "2026-10-23 23:59:30"));
    }

    @Test
    public void recalibrationIsReplacedOnlyWhenTheBalanceChangedLaterThatDay() {
        // Recalibrated at 10:00, then a meal was credited at 13:00 → 13:00 balance is the later one
        assertTrue(DayEndBalance.shouldRecover(DayEndBalance.SOURCE_RECALIBRATION,
                "2026-10-23 10:00:00", "2026-10-23 13:00:00"));
        // No change after the recalibration → keep the recalibration capture
        assertFalse(DayEndBalance.shouldRecover(DayEndBalance.SOURCE_RECALIBRATION,
                "2026-10-23 10:00:00", "2026-10-23 10:00:00"));
    }

    @Test
    public void recoveredRecordIsNotRecoveredAgain() {
        assertFalse(DayEndBalance.shouldRecover(DayEndBalance.SOURCE_RECOVERED,
                "2026-10-23 22:10:00", "2026-10-23 22:10:00"));
    }
}
