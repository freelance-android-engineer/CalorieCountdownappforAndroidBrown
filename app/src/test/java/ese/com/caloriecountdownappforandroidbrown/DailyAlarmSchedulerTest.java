package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class DailyAlarmSchedulerTest {

    private static long at(int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(2026, Calendar.OCTOBER, 15, hour, minute, second);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    private static void assertFiresAt(Calendar next, int day, int hour, int minute) {
        assertEquals(day, next.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, next.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, next.get(Calendar.MINUTE));
        assertEquals(0, next.get(Calendar.SECOND));
    }

    @Test
    public void debitDayEndIsNinePm() {
        assertEquals(21, DailyAlarmScheduler.DEBIT_HOUR);
        assertEquals(0, DailyAlarmScheduler.DEBIT_MINUTE);
    }

    @Test
    public void beforeNinePmFiresTonight() {
        // 8:30 PM — the old 8:00 PM cut-off has passed, but Debit Day End is still today at 9:00 PM
        Calendar next = DailyAlarmScheduler.getNextAlarmTime(
                DailyAlarmScheduler.DEBIT_HOUR, DailyAlarmScheduler.DEBIT_MINUTE, at(20, 30, 0));
        assertFiresAt(next, 15, 21, 0);
    }

    @Test
    public void atNinePmFiresTomorrow() {
        Calendar next = DailyAlarmScheduler.getNextAlarmTime(
                DailyAlarmScheduler.DEBIT_HOUR, DailyAlarmScheduler.DEBIT_MINUTE, at(21, 0, 0));
        assertFiresAt(next, 16, 21, 0);
    }

    @Test
    public void afterNinePmFiresTomorrow() {
        Calendar next = DailyAlarmScheduler.getNextAlarmTime(
                DailyAlarmScheduler.DEBIT_HOUR, DailyAlarmScheduler.DEBIT_MINUTE, at(21, 0, 1));
        assertFiresAt(next, 16, 21, 0);
    }

    @Test
    public void otherAlarmTimesAreUnchanged() {
        // 3:30 PM Credit Day End and 4:00 PM Food Notes alarms keep their times
        assertFiresAt(DailyAlarmScheduler.getNextAlarmTime(15, 30, at(9, 0, 0)), 15, 15, 30);
        assertFiresAt(DailyAlarmScheduler.getNextAlarmTime(16, 0, at(9, 0, 0)), 15, 16, 0);
    }
}
