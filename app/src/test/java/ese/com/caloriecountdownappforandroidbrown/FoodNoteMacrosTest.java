package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FoodNoteMacrosTest {

    private static Map<String, String> note(String food, String qty) {
        Map<String, String> m = new HashMap<>();
        m.put("food", food);
        m.put("quantity", qty);
        return m;
    }

    private static final String TWO_ITEMS =
            "Item 1\nCalories: 165\nProtein: 31\nCarbohydrates: 0\nTotal Fat: 3.6\nSaturated Fat: 1\n"
                    + "Dietary Fiber: 0\nTotal Sugars: 0\nSodium: 74\n\n"
                    + "Item 2\nCalories: 130\nProtein: 2.7\nCarbohydrates: 28\nTotal Fat: 0.3\nSaturated Fat: 0.1\n"
                    + "Dietary Fiber: 0.4\nTotal Sugars: N/A\nSodium: 1\n";

    @Test
    public void promptListsEveryNoteWithQuantity() {
        String p = FoodNoteMacros.buildPrompt(Arrays.asList(note("Chicken breast", "100g"), note("Rice", "0")));
        assertTrue(p.contains("1. Chicken breast (quantity: 100g)"));
        assertTrue(p.contains("2. Rice\n"));
        assertTrue(p.contains("Saturated Fat: <number>"));
        assertTrue(p.contains("Sodium: <number>"));
    }

    @Test
    public void singleNoteIsParsed() {
        List<Double[]> items = FoodNoteMacros.parse(TWO_ITEMS.split("\n\n")[0], 1);
        assertEquals(165.0, items.get(0)[0], 0);
        assertEquals(3.6, items.get(0)[3], 0);   // Total Fat, not Saturated Fat
        assertEquals(1.0, items.get(0)[4], 0);   // Saturated Fat
        assertEquals(74.0, items.get(0)[7], 0);  // Sodium
    }

    @Test
    public void singleNoteWithoutItemHeaderIsParsed() {
        List<Double[]> items = FoodNoteMacros.parse("Calories: 52\nProtein: 0.3\nTotal Fat: 0.2", 1);
        assertEquals(52.0, items.get(0)[0], 0);
        assertEquals(0.2, items.get(0)[3], 0);
        assertNull(items.get(0)[7]);
    }

    @Test
    public void multipleNotesGiveIndividualAndCombinedValues() {
        List<Double[]> items = FoodNoteMacros.parse(TWO_ITEMS, 2);
        assertEquals(130.0, items.get(1)[0], 0);
        assertNull("N/A is not invented as 0", items.get(1)[6]);

        Double[] total = FoodNoteMacros.total(items);
        assertEquals(295.0, total[0], 0);
        assertEquals(33.7, total[1], 1e-9);
        assertEquals(0.0, total[6], 0);
        assertTrue(FoodNoteMacros.isPartial(items, 6));
        assertFalse(FoodNoteMacros.isPartial(items, 0));
    }

    @Test
    public void reportShowsEachNoteCombinedAndEstimateNotice() {
        List<Map<String, String>> foods = Arrays.asList(note("Chicken breast", "100g"), note("Rice", "1 cup"));
        String report = FoodNoteMacros.buildReport(foods, FoodNoteMacros.parse(TWO_ITEMS, 2));
        assertTrue(report.contains("1. Chicken breast\nQuantity: 100g\nCalories: 165 kcal"));
        assertTrue(report.contains("2. Rice\nQuantity: 1 cup"));
        assertTrue(report.contains("COMBINED (2 food notes)\nCalories: 295 kcal"));
        assertTrue(report.contains("Total Sugars: 0 g *"));
        assertTrue(report.contains("SI estimates, not verified measurements"));
    }

    @Test
    public void unusableReplyIsDetected() {
        assertTrue(FoodNoteMacros.isEmpty(FoodNoteMacros.parse("Sorry, I can't help with that.", 2)));
        assertTrue(FoodNoteMacros.isEmpty(FoodNoteMacros.parse(null, 1)));
        assertFalse(FoodNoteMacros.isEmpty(FoodNoteMacros.parse(TWO_ITEMS, 2)));
    }

    @Test
    public void itemNumbersOutOfRangeAreIgnored() {
        List<Double[]> items = FoodNoteMacros.parse("Item 3\nCalories: 999\n", 2);
        assertTrue(FoodNoteMacros.isEmpty(new ArrayList<>(items)));
    }
}
