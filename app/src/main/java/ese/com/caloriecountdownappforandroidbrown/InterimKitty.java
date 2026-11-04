package ese.com.caloriecountdownappforandroidbrown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Interim Kitty Value (client rule): until the Subscriber confirms "Sums Calories" (around 4 PM),
 * the Kitty uses the calories of the Food Notes created from yesterday 4 PM to today 4 PM.
 * Once Sums Calories has been credited today, that confirmed total is used instead.
 *
 * Kitty formula is unchanged: Kitty = daily budget (2500 / 2000) − food consumed.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class InterimKitty {

    public static final int WINDOW_HOUR = 16;
    /** Food Note date format (quick_food_note.note_date). */
    public static final String NOTE_DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private InterimKitty() {}

    /** One Food Note as stored. */
    public static final class Note {
        public final int id;
        public final String date;
        public final String calories;
        /** Calories confirmed by the 4 PM review (final_calories), or null if not confirmed. */
        public final Integer confirmedCalories;

        public Note(int id, String date, String calories, Integer confirmedCalories) {
            this.id = id;
            this.date = date;
            this.calories = calories;
            this.confirmedCalories = confirmedCalories;
        }
    }

    /** Sum of the window's Food Notes. */
    public static final class Sum {
        public final int calories;
        public final int notesCounted;
        /** Notes in the window without a usable calorie value (empty / not a number). */
        public final int notesWithoutCalories;

        Sum(int calories, int notesCounted, int notesWithoutCalories) {
            this.calories = calories;
            this.notesCounted = notesCounted;
            this.notesWithoutCalories = notesWithoutCalories;
        }
    }

    /** Start of the window: yesterday at 4 PM, relative to now. */
    public static long windowStart(long nowMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(nowMillis);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        setWindowHour(cal);
        return cal.getTimeInMillis();
    }

    /** End of the window (exclusive): today at 4 PM. */
    public static long windowEnd(long nowMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(nowMillis);
        setWindowHour(cal);
        return cal.getTimeInMillis();
    }

    private static void setWindowHour(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, WINDOW_HOUR);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /** Parses a Food Note date, or null if it is not "dd-MM-yyyy HH:mm". */
    public static Long parseNoteDate(String date) {
        if (date == null) return null;
        SimpleDateFormat format = new SimpleDateFormat(NOTE_DATE_FORMAT, Locale.US);
        format.setLenient(false);
        try {
            Date parsed = format.parse(date.trim());
            return parsed != null ? parsed.getTime() : null;
        } catch (ParseException e) {
            return null;
        }
    }

    /** Calories of a note: the 4 PM confirmed value if there is one, otherwise the entered value. */
    public static Integer caloriesOf(Note note) {
        if (note.confirmedCalories != null) return note.confirmedCalories;
        if (note.calories == null) return null;
        try {
            return Integer.parseInt(note.calories.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Sums the notes created in [yesterday 4 PM, today 4 PM). Each note id is counted once.
     */
    public static Sum sum(Collection<Note> notes, long nowMillis) {
        long start = windowStart(nowMillis);
        long end = windowEnd(nowMillis);
        Set<Integer> seen = new HashSet<>();
        int total = 0;
        int counted = 0;
        int withoutCalories = 0;
        for (Note note : notes) {
            Long at = parseNoteDate(note.date);
            if (at == null || at < start || at >= end) continue;
            if (!seen.add(note.id)) continue;
            Integer calories = caloriesOf(note);
            if (calories == null) {
                withoutCalories++;
                continue;
            }
            total += calories;
            counted++;
        }
        return new Sum(total, counted, withoutCalories);
    }

    /** Existing Kitty formula: daily budget − food consumed. */
    public static int kitty(int dailyBudget, int foodConsumed) {
        return dailyBudget - foodConsumed;
    }
}
