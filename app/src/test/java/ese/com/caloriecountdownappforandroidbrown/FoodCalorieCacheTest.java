package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FoodCalorieCacheTest {

    @Test
    public void savedFoodIsFoundAgain() {
        FoodCalorieCache cache = new FoodCalorieCache();
        assertTrue(cache.put("Apple", "95"));
        assertEquals(Integer.valueOf(95), cache.lookup("Apple"));
    }

    @Test
    public void lookupIgnoresCaseAndSurroundingSpaces() {
        FoodCalorieCache cache = new FoodCalorieCache();
        cache.put("Apple", "95");
        assertEquals(Integer.valueOf(95), cache.lookup("APPLE"));
        assertEquals(Integer.valueOf(95), cache.lookup("  apple  "));
        assertEquals(Integer.valueOf(120), lookupAfterPut("Peanut  Butter", "120", "peanut butter"));
    }

    @Test
    public void differentFoodsAreNotMerged() {
        FoodCalorieCache cache = new FoodCalorieCache();
        cache.put("Apple", "95");
        assertNull(cache.lookup("Apple Pie"));
        assertNull(cache.lookup("Apples"));
    }

    @Test
    public void unknownOrBlankFoodReturnsNull() {
        FoodCalorieCache cache = new FoodCalorieCache();
        assertNull(cache.lookup("Banana"));
        assertNull(cache.lookup(""));
        assertNull(cache.lookup(null));
    }

    @Test
    public void invalidCaloriesNeverOverwriteValidEntry() {
        FoodCalorieCache cache = new FoodCalorieCache();
        cache.put("Apple", "95");
        assertFalse(cache.put("Apple", null));
        assertFalse(cache.put("Apple", ""));
        assertFalse(cache.put("Apple", "abc"));
        assertFalse(cache.put("Apple", "-10"));
        assertFalse(cache.put("Apple", "95.5"));
        assertFalse(cache.put("  ", "95"));
        assertEquals(Integer.valueOf(95), cache.lookup("apple"));
        assertEquals(1, cache.size());
    }

    @Test
    public void latestSaveWinsAndDoesNotDuplicate() {
        FoodCalorieCache cache = new FoodCalorieCache();
        cache.put("Apple", "95");
        cache.put(" APPLE ", "100");
        assertEquals(Integer.valueOf(100), cache.lookup("Apple"));
        assertEquals(1, cache.size());
    }

    @Test
    public void zeroCaloriesIsValid() {
        FoodCalorieCache cache = new FoodCalorieCache();
        assertTrue(cache.put("Black Coffee", " 0 "));
        assertEquals(Integer.valueOf(0), cache.lookup("black coffee"));
    }

    @Test
    public void loadsFromStoredEntriesSkippingNulls() {
        Map<String, Integer> stored = new HashMap<>();
        stored.put("apple", 95);
        stored.put("broken", null);
        FoodCalorieCache cache = new FoodCalorieCache(stored);
        assertEquals(Integer.valueOf(95), cache.lookup("Apple"));
        assertNull(cache.lookup("broken"));
        assertEquals(1, cache.size());
    }

    private static Integer lookupAfterPut(String food, String calories, String query) {
        FoodCalorieCache cache = new FoodCalorieCache();
        cache.put(food, calories);
        return cache.lookup(query);
    }
}
