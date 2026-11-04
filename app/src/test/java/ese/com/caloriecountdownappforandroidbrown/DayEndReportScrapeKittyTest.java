package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Countdown Day End Report, Midnight Scrape final Steps comparison and Interim Kitty. */
public class DayEndReportScrapeKittyTest {

    // ── Task 1: Countdown Day End Report ──

    @Test
    public void clientExampleShowsCountdownOf350() {
        assertEquals(350, DayEndReport.countdown(10_000, 9_650));
        assertEquals("🔻 350 Points", DayEndReport.movement(10_000, 9_650));
        String section = DayEndReport.buildSection(10_000, "Yesterday's Day End Balance (02 Nov 2026)", 9_650,
                8_000, DayEndReport.SOURCE_MANUAL, Collections.emptyList());
        assertTrue(section, section.contains("Yesterday's Day End Balance (02 Nov 2026): 10,000"));
        assertTrue(section, section.contains("Today's Final Balance: 9,650"));
        assertTrue(section, section.contains("🔻 350 Points (counted down)"));
        assertTrue(section, section.contains("Steps (Manual): 8,000"));
    }

    @Test
    public void countUpAndNoChangeAreShown() {
        assertEquals("🔺 120 Points", DayEndReport.movement(10_000, 10_120));
        assertEquals("0 Points", DayEndReport.movement(10_000, 10_000));
        String up = DayEndReport.buildSection(10_000, "Ref", 10_120, 500, DayEndReport.SOURCE_SENSOR, null);
        assertTrue(up, up.contains("(counted up)"));
        assertTrue(up, up.contains("Steps (Sensor): 500"));
    }

    @Test
    public void missingYesterdayIsReportedHonestly() {
        String section = DayEndReport.buildSection(null, null, 9_650, 8_000, DayEndReport.SOURCE_MANUAL, null);
        assertTrue(section, section.contains("Yesterday's Day End Balance: No Record"));
        assertFalse(section, section.contains("Points"));
    }

    @Test
    public void missingDebitsComeFromExistingMarkers() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        List<String> missing = DayEndReport.missingDebits(prefs, "03-11-2026", "02-11-2026", 8_000);
        assertEquals(3, missing.size());

        prefs.edit().putString(Debit_Activity_CiF003_fragment_box.KEY_BMR_APPLIED_DATE, "03-11-2026")
                .putString(DayEndReport.KEY_PHYSICAL_ACTIVITY_DATE, "03-11-2026")
                .putString(MidnightScrape.KEY_LAST_SCRAPED_DATE, "02-11-2026").apply();
        assertTrue(DayEndReport.missingDebits(prefs, "03-11-2026", "02-11-2026", 8_000).isEmpty());

        // No saved Steps yesterday → nothing to scrape; BMR from another day does not count
        prefs.edit().putString(MidnightScrape.KEY_LAST_SCRAPED_DATE, null)
                .putString(Debit_Activity_CiF003_fragment_box.KEY_BMR_APPLIED_DATE, "02-11-2026").apply();
        assertEquals(Collections.singletonList("BMR"),
                DayEndReport.missingDebits(prefs, "03-11-2026", "02-11-2026", -1));
    }

    // ── Task 2: Midnight Scrape ──

    @Test
    public void extraStepsAfterFinalEntryAreDebitedWithStepsNumerator() {
        int missed = MidnightScrape.missedSteps(8_000, 10_000);
        assertEquals(2_000, missed);
        assertEquals(Integer.valueOf(178), Accountability.toPoints(missed, 0.089)); // round(2000 × 0.089)
    }

    @Test
    public void noDebitWhenActualIsEqualOrLower() {
        assertEquals(0, MidnightScrape.missedSteps(10_000, 10_000));
        assertEquals(0, MidnightScrape.missedSteps(10_000, 8_000));
    }

    @Test
    public void eachDateIsScrapedOnce() {
        FakeSharedPreferences prefs = new FakeSharedPreferences();
        assertFalse("no saved final Steps", MidnightScrape.isPending(prefs, "02-11-2026", -1));
        assertTrue(MidnightScrape.isPending(prefs, "02-11-2026", 8_000));
        assertTrue(MidnightScrape.claim(prefs, "02-11-2026"));
        assertFalse("second press", MidnightScrape.claim(prefs, "02-11-2026"));
        assertFalse(MidnightScrape.isPending(prefs, "02-11-2026", 8_000));
        // Next day's Steps are a new date
        assertTrue(MidnightScrape.isPending(prefs, "03-11-2026", 9_000));
    }

    // ── Task 3: Interim Kitty ──

    private static long at(int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2026, Calendar.NOVEMBER, day, hour, minute);
        return cal.getTimeInMillis();
    }

    @Test
    public void onlyNotesFromYesterday4pmToToday4pmAreSummed() {
        List<InterimKitty.Note> notes = Arrays.asList(
                new InterimKitty.Note(1, "02-11-2026 15:59", "900", null),   // before window
                new InterimKitty.Note(2, "02-11-2026 16:00", "400", null),   // window start (included)
                new InterimKitty.Note(3, "02-11-2026 20:30", "600", null),
                new InterimKitty.Note(4, "03-11-2026 08:15", "300", null),
                new InterimKitty.Note(5, "03-11-2026 16:00", "700", null));  // window end (excluded)
        InterimKitty.Sum sum = InterimKitty.sum(notes, at(3, 11, 0));
        assertEquals(1_300, sum.calories);
        assertEquals(3, sum.notesCounted);
        assertEquals(1_200, InterimKitty.kitty(2_500, sum.calories));
    }

    @Test
    public void confirmedCaloriesWinAndIncompleteNotesAreSkipped() {
        List<InterimKitty.Note> notes = Arrays.asList(
                new InterimKitty.Note(1, "03-11-2026 08:00", "300", 350),  // 4 PM review confirmed 350
                new InterimKitty.Note(2, "03-11-2026 09:00", "", null),
                new InterimKitty.Note(3, "03-11-2026 10:00", "abc", null),
                new InterimKitty.Note(4, "03-11-2026 11:00", null, null),
                new InterimKitty.Note(5, "bad date", "500", null));
        InterimKitty.Sum sum = InterimKitty.sum(notes, at(3, 12, 0));
        assertEquals(350, sum.calories);
        assertEquals(1, sum.notesCounted);
        assertEquals(3, sum.notesWithoutCalories);
    }

    @Test
    public void sameNoteIsCountedOnce() {
        InterimKitty.Note note = new InterimKitty.Note(7, "03-11-2026 08:00", "500", null);
        assertEquals(500, InterimKitty.sum(Arrays.asList(note, note), at(3, 12, 0)).calories);
    }

    @Test
    public void kittyOverBudgetIsNegative() {
        assertEquals(-300, InterimKitty.kitty(2_000, 2_300));
    }
}
