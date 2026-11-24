package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinalCountdownTest {

    /** Feeds balances through the same state machine as onBalanceUpdated; returns how many times it fired. */
    private static int run(boolean armed, int... balances) {
        int fired = 0;
        for (int b : balances) {
            if (FinalCountdown.shouldTrigger(armed, b)) fired++;
            armed = FinalCountdown.nextArmed(b);
        }
        return fired;
    }

    @Test
    public void aboveThresholdDoesNotTrigger() {
        assertEquals(0, run(false, 50000, 20000, 10001));
    }

    @Test
    public void exactlyTenThousandTriggers() {
        assertEquals(1, run(false, 12000, 10000));
    }

    @Test
    public void belowTenThousandTriggers() {
        assertEquals(1, run(false, 10500, 9800));
    }

    @Test
    public void reachingZeroDoesNotRepeat() {
        assertEquals(1, run(false, 11000, 9000, 5000, 100, 0));
    }

    @Test
    public void refreshAtSameBalanceDoesNotRepeat() {
        // App restart / refresh re-stores the same balance: state is persisted as "not armed".
        assertEquals(1, run(false, 15000, 9000, 9000, 9000));
    }

    @Test
    public void goingBackAboveStartsANewEvent() {
        assertEquals(2, run(false, 15000, 9000, 12000, 8000));
    }

    @Test
    public void notTriggeredWithoutComingDownFromAbove() {
        assertFalse(FinalCountdown.shouldTrigger(false, 9000));
        assertTrue(FinalCountdown.shouldTrigger(true, 9000));
    }

    @Test
    public void messageIsExact() {
        assertEquals("Well done, don't STOP! now! It's the FINAL COUNTDOWN to zero balance: 🙌",
                FinalCountdown.MESSAGE);
    }
}
