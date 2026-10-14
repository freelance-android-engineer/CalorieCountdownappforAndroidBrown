package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class LastStepChallengeTest {

    private static LastStepChallenge main(String date, String createdAt, int steps) {
        return new LastStepChallenge(LastStepChallenge.Source.MAIN_SCREEN, date, createdAt, steps, false);
    }

    private static LastStepChallenge debit(String date, String createdAt, int steps, boolean capped) {
        return new LastStepChallenge(LastStepChallenge.Source.FOUR_PM_DEBIT, date, createdAt, steps, capped);
    }

    @Test
    public void noChallengeAnywhereIsNull() {
        assertNull(LastStepChallenge.mostRecent(null, null));
    }

    @Test
    public void onlyOneSourceIsReturned() {
        LastStepChallenge m = main("10-10-2026", "10-10-2026 16:30:00", 2_198);
        LastStepChallenge d = debit("10-10-2026", "10-10-2026 16:30:00", 5_000, false);
        assertSame(m, LastStepChallenge.mostRecent(m, null));
        assertSame(d, LastStepChallenge.mostRecent(null, d));
    }

    @Test
    public void laterSaveWinsAcrossDays() {
        LastStepChallenge older = main("09-10-2026", "09-10-2026 18:00:00", 1_000);
        LastStepChallenge newer = debit("10-10-2026", "10-10-2026 08:00:00", 2_000, false);
        assertSame(newer, LastStepChallenge.mostRecent(older, newer));
        assertSame(newer, LastStepChallenge.mostRecent(newer, older));
    }

    @Test
    public void laterSaveWinsOnSameDay() {
        LastStepChallenge morning = debit("10-10-2026", "10-10-2026 09:15:00", 3_000, false);
        LastStepChallenge evening = main("10-10-2026", "10-10-2026 17:45:30", 2_198);
        assertSame(evening, LastStepChallenge.mostRecent(morning, evening));
    }

    @Test
    public void comparesRealDatesNotText() {
        // As text "31-01-2026" > "01-02-2026"; as dates 1 Feb is later.
        LastStepChallenge jan = main("31-01-2026", "31-01-2026 10:00:00", 1_000);
        LastStepChallenge feb = debit("01-02-2026", "01-02-2026 10:00:00", 2_000, false);
        assertSame(feb, LastStepChallenge.mostRecent(jan, feb));
    }

    @Test
    public void missingCreatedAtFallsBackToDate() {
        LastStepChallenge old = main("09-10-2026", "", 1_000);
        LastStepChallenge newer = debit("10-10-2026", null, 2_000, false);
        assertSame(newer, LastStepChallenge.mostRecent(old, newer));
    }

    @Test
    public void unreadableTimestampLosesToReadableOne() {
        LastStepChallenge broken = main("garbage", "garbage", 1_000);
        LastStepChallenge ok = debit("10-10-2026", "10-10-2026 08:00:00", 2_000, false);
        assertSame(ok, LastStepChallenge.mostRecent(broken, ok));
        assertSame(ok, LastStepChallenge.mostRecent(ok, broken));
    }

    @Test
    public void messageShowsStoredStepsDateAndSource() {
        String msg = debit("10-10-2026", "10-10-2026 16:05:12", 30_000, true).buildMessage();
        assertTrue(msg.contains("Steps"));
        assertTrue(msg.contains("Assigned on: 10-10-2026 16:05:12"));
        assertTrue(msg.contains("From: 4PM Food Notes processing"));
        assertTrue(msg.contains("Capped at 30,000 steps"));
    }

    @Test
    public void messageForMainScreenUsesDateWhenNoTimestamp() {
        String msg = main("10-10-2026", "", 2_198).buildMessage();
        assertTrue(msg.contains("Assigned on: 10-10-2026"));
        assertTrue(msg.contains("From: Steps Challenge (main screen)"));
        assertFalse(msg.contains("Capped"));
    }

    @Test
    public void zeroStepChallengeIsExplained() {
        assertTrue(main("10-10-2026", "", 0).buildMessage().contains("No steps were needed"));
    }

    @Test
    public void emptyMessageIsFriendly() {
        assertTrue(LastStepChallenge.emptyMessage().startsWith("No Step Challenge has been assigned yet."));
    }
}
