package ese.com.caloriecountdownappforandroidbrown;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Cache Type — dictionary of normalized food name → calories, used by the Food Note
 * dialog to auto-populate calories for a food the Subscriber has logged before.
 * No Android dependencies so it can be unit tested on the JVM; persistence lives in
 * {@link SQLDatabase_Food_Items_CIF6} (food_calorie_cache table).
 *
 * Rules:
 *   key      = food name trimmed, inner whitespace collapsed, lower-cased
 *   value    = whole, non-negative calorie count (the note's calories field is free text)
 *   conflict = the most recently saved Food Note wins
 */
public final class FoodCalorieCache {

    private final Map<String, Integer> entries = new HashMap<>();

    public FoodCalorieCache() {}

    public FoodCalorieCache(Map<String, Integer> initial) {
        if (initial == null) return;
        for (Map.Entry<String, Integer> e : initial.entrySet()) {
            put(e.getKey(), e.getValue() == null ? null : String.valueOf(e.getValue()));
        }
    }

    /** Returns the lookup key for a food name, or null if the name is blank. */
    public static String normalize(String foodName) {
        if (foodName == null) return null;
        String key = foodName.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
        return key.isEmpty() ? null : key;
    }

    /** Returns the calories as an int if they are a whole, non-negative number, otherwise null. */
    public static Integer parseValidCalories(String calories) {
        if (calories == null) return null;
        String trimmed = calories.trim();
        if (!trimmed.matches("\\d{1,7}")) return null;
        return Integer.parseInt(trimmed);
    }

    /** Calories for this food, or null when it has not been cached. */
    public synchronized Integer lookup(String foodName) {
        String key = normalize(foodName);
        return key == null ? null : entries.get(key);
    }

    /**
     * Adds or updates the entry. Invalid names or calories are ignored so they can never
     * overwrite a valid entry. Returns true if the entry was stored.
     */
    public synchronized boolean put(String foodName, String calories) {
        String key = normalize(foodName);
        Integer value = parseValidCalories(calories);
        if (key == null || value == null) return false;
        entries.put(key, value);
        return true;
    }

    public synchronized int size() {
        return entries.size();
    }
}
