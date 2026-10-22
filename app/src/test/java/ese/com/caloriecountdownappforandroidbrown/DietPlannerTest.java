package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class DietPlannerTest {

    private static Food_Item_CIF4 food(String name, float caloriesPer100g, int grams) {
        Food_Item_CIF4 item = new Food_Item_CIF4();
        item.Set_food_item_name(name);
        item.Set_calories_per_100g(caloriesPer100g);
        item.Set_weight(grams);
        return item;
    }

    @Test
    public void planHasSevenDaysOfSixMealBoxes() {
        Diet_Planner_CIF0116 plan = new Diet_Planner_CIF0116("Keto Diet");
        assertEquals("Keto Diet", plan.getDietName());
        assertEquals(7, Diet_Planner_CIF0116.DAYS.length);
        assertEquals(6, Diet_Planner_CIF0116.MEAL_BOXES.length);
        assertEquals("Monday", plan.getMonday().getDayName());
        assertEquals("Sunday", plan.getSunday().getDayName());
        assertSame(plan.getMonday().getBreakfast_Box(), plan.getBox(0, Diet_Planner_CIF0116.BREAKFAST_BOX));
        assertSame(plan.getSunday().getSnack_Box(), plan.getBox(6, Diet_Planner_CIF0116.SNACK_BOX));
        assertSame(plan.getWednesday().getBrunch_Box(), plan.getBox(2, Diet_Planner_CIF0116.BRUNCH_BOX));
        assertSame(plan.getFriday().getSupper_Box(), plan.getBox(4, Diet_Planner_CIF0116.SUPPER_BOX));
        assertTrue(plan.isEmpty());

        plan.getTuesday().getLunch_Box().add(food("Avocado", 160, 100));
        assertFalse(plan.isEmpty());
    }

    @Test
    public void mealCreditsUseTheExistingCreditFormula() {
        ArrayList<Food_Item_CIF4> meal = new ArrayList<>();
        meal.add(food("Eggs", 155, 120));    // 155 × 120 / 100 = 186
        meal.add(food("Bacon", 541, 30));    // 541 × 30 / 100 = 162.3 → 162
        meal.add(food("Black coffee", 2, 250)); // 2 × 250 / 100 = 5
        assertEquals(353, Diet_Planner_CIF0116.mealCredits(meal));
        // Each item's Calorie_Value is set, which SummaryBoxCIF12.GetListingsTotalCaloriesIN() sums
        assertEquals(186, meal.get(0).Get_calorie_value());
        assertEquals(162, meal.get(1).Get_calorie_value());
    }

    @Test
    public void emptyOrMissingMealCreditsNothing() {
        assertEquals(0, Diet_Planner_CIF0116.mealCredits(new ArrayList<>()));
        assertEquals(0, Diet_Planner_CIF0116.mealCredits(null));
    }

    @Test
    public void dietNamesAreTrimmedAndRequired() {
        assertEquals("Keto Diet", Diet_Planner_CIF0116.cleanDietName("  Keto   Diet "));
        assertEquals("5:2", Diet_Planner_CIF0116.cleanDietName("5:2"));
        assertNull(Diet_Planner_CIF0116.cleanDietName("   "));
        assertNull(Diet_Planner_CIF0116.cleanDietName(null));
    }

    @Test
    public void gramsMustBeAWholeNumberFrom1To5000() {
        assertEquals(Integer.valueOf(150), Diet_Planner_CIF0116.parseGrams(" 150 "));
        assertEquals(Integer.valueOf(5000), Diet_Planner_CIF0116.parseGrams("5000"));
        assertNull(Diet_Planner_CIF0116.parseGrams("0"));
        assertNull(Diet_Planner_CIF0116.parseGrams("5001"));
        assertNull(Diet_Planner_CIF0116.parseGrams("12.5"));
        assertNull(Diet_Planner_CIF0116.parseGrams("-5"));
        assertNull(Diet_Planner_CIF0116.parseGrams(""));
    }
}
