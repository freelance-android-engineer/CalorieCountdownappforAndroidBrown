package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class YesterdayDayEndTest {

    private static final String TODAY = "2026-11-03";
    private static final String YESTERDAY = "2026-11-02";
    private static final String NOW = "2026-11-03 10:00:00";

    private static DayEndBalance.Record yesterdayRecord(int balance) {
        return new DayEndBalance.Record(YESTERDAY, balance, DayEndBalance.SOURCE_SCHEDULED, "2026-11-02 23:59:00", null);
    }

    @Test
    public void yesterdaysDayEndBalanceIsTheReference() {
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null,
                yesterdayRecord(38_287), null, 38_500, NOW);
        assertEquals(38_287, ref.balance);
        assertEquals(YesterdayDayEnd.TYPE_PREVIOUS_DAY_END, ref.type);
        assertEquals(YESTERDAY, ref.referenceDate);
        assertEquals(TODAY, ref.forDate);
    }

    @Test
    public void recalibrationTodayTakesPriority() {
        // Latest of several recalibrations is what saveDayEndBalance keeps in deb_recalibrated_balance
        DayEndBalance.Record today = new DayEndBalance.Record(TODAY, 38_037, DayEndBalance.SOURCE_RECALIBRATION,
                "2026-11-03 09:15:00", 38_037);
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, today, 37_000,
                yesterdayRecord(40_000), null, 38_100, NOW);
        assertEquals(38_037, ref.balance);
        assertEquals(YesterdayDayEnd.TYPE_RECALIBRATED_TODAY, ref.type);
        assertEquals("2026-11-03 09:15:00", ref.capturedAt);
    }

    @Test
    public void recalibratedPrefsUsedOnlyWhenSqliteCaptureMissing() {
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, 38_037,
                yesterdayRecord(40_000), null, 38_100, NOW);
        assertEquals(38_037, ref.balance);
        assertEquals(YesterdayDayEnd.TYPE_RECALIBRATED_TODAY, ref.type);
    }

    @Test
    public void todayRecordWithoutRecalibrationIsNotUsed() {
        DayEndBalance.Record today = new DayEndBalance.Record(TODAY, 1, DayEndBalance.SOURCE_SCHEDULED, "x", null);
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, today, null,
                yesterdayRecord(38_287), null, 38_500, NOW);
        assertEquals(38_287, ref.balance);
    }

    @Test
    public void noRecordUsesCurrentBalanceAsBaseline() {
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null, null, null, 38_500, NOW);
        assertEquals(38_500, ref.balance);
        assertEquals(YesterdayDayEnd.TYPE_NO_RECORD_BASELINE, ref.type);
        assertEquals(NOW, ref.capturedAt);
    }

    @Test
    public void savedBaselineIsKeptOnReopen() {
        YesterdayDayEnd.Reference saved = new YesterdayDayEnd.Reference(TODAY, 38_500,
                YesterdayDayEnd.TYPE_NO_RECORD_BASELINE, TODAY, NOW);
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null, null, saved, 38_100,
                "2026-11-03 18:00:00");
        assertSame(saved, ref);
        assertTrue(ref.sameAs(saved)); // nothing new to write
    }

    @Test
    public void baselineFromAnotherDayIsNotReused() {
        YesterdayDayEnd.Reference old = new YesterdayDayEnd.Reference(YESTERDAY, 40_000,
                YesterdayDayEnd.TYPE_NO_RECORD_BASELINE, YESTERDAY, "2026-11-02 08:00:00");
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null, null, old, 38_100, NOW);
        assertEquals(38_100, ref.balance);
        assertEquals(TODAY, ref.forDate);
    }

    @Test
    public void realRecordReplacesBaselineWhenItAppears() {
        YesterdayDayEnd.Reference saved = new YesterdayDayEnd.Reference(TODAY, 38_500,
                YesterdayDayEnd.TYPE_NO_RECORD_BASELINE, TODAY, NOW);
        YesterdayDayEnd.Reference ref = YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null,
                yesterdayRecord(38_287), saved, 38_500, NOW);
        assertEquals(YesterdayDayEnd.TYPE_PREVIOUS_DAY_END, ref.type);
    }

    @Test
    public void noRecordAndNoCurrentBalanceGivesNothing() {
        assertNull(YesterdayDayEnd.resolve(TODAY, YESTERDAY, null, null, null, null, null, NOW));
    }

    @Test
    public void targetRangeAndRemainingPoints() {
        int r = 38_287;
        assertEquals(38_037, YesterdayDayEnd.targetUpper(r));
        assertEquals(37_987, YesterdayDayEnd.targetLower(r));
        assertEquals(214, YesterdayDayEnd.pointsToBeatReference(r, 38_500));
        assertEquals(463, YesterdayDayEnd.pointsToPreferredTarget(r, 38_500));
        assertEquals(1, YesterdayDayEnd.pointsToBeatReference(r, r));
        assertEquals(0, YesterdayDayEnd.pointsToBeatReference(r, 38_282));
        assertEquals(245, YesterdayDayEnd.pointsToPreferredTarget(r, 38_282));
        assertEquals(0, YesterdayDayEnd.pointsToPreferredTarget(r, 38_037));
    }

    @Test
    public void statusCoversEveryCase() {
        int r = 38_287;
        assertEquals(YesterdayDayEnd.Status.ABOVE_REFERENCE, YesterdayDayEnd.status(r, 38_288));
        assertEquals(YesterdayDayEnd.Status.EQUAL_TO_REFERENCE, YesterdayDayEnd.status(r, r));
        assertEquals(YesterdayDayEnd.Status.BELOW_REFERENCE, YesterdayDayEnd.status(r, 38_282));
        assertEquals(YesterdayDayEnd.Status.WITHIN_TARGET, YesterdayDayEnd.status(r, 38_037));
        assertEquals(YesterdayDayEnd.Status.WITHIN_TARGET, YesterdayDayEnd.status(r, 37_987));
        assertEquals(YesterdayDayEnd.Status.BEYOND_TARGET, YesterdayDayEnd.status(r, 37_986));
    }

    @Test
    public void messagesAreHonest() {
        YesterdayDayEnd.Reference base = new YesterdayDayEnd.Reference(TODAY, 38_500,
                YesterdayDayEnd.TYPE_NO_RECORD_BASELINE, TODAY, NOW);
        String msg = YesterdayDayEnd.buildMessage(base, 38_500, true, "03 Nov 2026, 10:00");
        assertTrue(msg, msg.startsWith("No Record."));
        assertTrue(msg, msg.contains("38,250 to 38,200"));

        YesterdayDayEnd.Reference prev = new YesterdayDayEnd.Reference(TODAY, 38_287,
                YesterdayDayEnd.TYPE_PREVIOUS_DAY_END, YESTERDAY, "2026-11-02 23:59:00");
        msg = YesterdayDayEnd.buildMessage(prev, 38_037, false, "02 Nov 2026, 23:59");
        assertTrue(msg, msg.contains("Yesterday's Day End Balance (02 Nov 2026, 23:59):\n38,287"));
        assertTrue(msg, msg.contains("within the preferred"));
    }
}
