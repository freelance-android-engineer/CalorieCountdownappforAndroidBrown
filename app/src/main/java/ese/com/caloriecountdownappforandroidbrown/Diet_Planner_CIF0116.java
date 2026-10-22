package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;
import java.util.List;

/**
 * Diet_Planner: a named weekly diet (e.g. Keto Diet, Mediterranean, Carnivore, Atkins, South Beach,
 * 5:2, Intermittent Fasting). Monday … Sunday each hold six meal boxes of Food_Item_CIF4 food and
 * drink items, as retrieved from the cloud backend (or added through Add New Food Item).
 *
 * Each item keeps its nutrition per 100 g and the grams eaten (weight), so the existing Credit
 * formula applies unchanged: Calorie_Value = calories_per_100g × weight / 100
 * (Food_Item_CIF4.Make_calorie_value()).
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public class Diet_Planner_CIF0116 {

    public static final String[] DAYS = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /** The six meal collections, in the client's order. */
    public static final String[] MEAL_BOXES = {
            "Breakfast_Box", "Lunch_Box", "Brunch_Box", "Dinner_Box", "Supper_Box", "Snack_Box"};

    public static final int BREAKFAST_BOX = 0;
    public static final int LUNCH_BOX = 1;
    public static final int BRUNCH_BOX = 2;
    public static final int DINNER_BOX = 3;
    public static final int SUPPER_BOX = 4;
    public static final int SNACK_BOX = 5;

    /** Example diet names offered by the wizard; the Subscriber may type any name. */
    public static final String[] EXAMPLE_DIETS = {
            "Keto Diet", "Mediterranean", "Carnivore", "Atkins", "South Beach", "5:2", "Intermittent Fasting"};

    /** Prefs key ("Calorie_Countdown") holding the last credited Diet Planner meal event id. */
    static final String KEY_LAST_DIET_PLANNER_CREDIT_EVENT = "diet_planner_credit_last_event_id";

    private long id;
    private String dietName;
    private final Day[] days = new Day[DAYS.length];

    public Diet_Planner_CIF0116(String dietName) {
        this.dietName = dietName;
        for (int d = 0; d < days.length; d++) {
            days[d] = new Day(DAYS[d]);
        }
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getDietName() { return dietName; }
    public void setDietName(String dietName) { this.dietName = dietName; }

    public Day getDay(int dayIndex) { return days[dayIndex]; }
    public Day getMonday() { return days[0]; }
    public Day getTuesday() { return days[1]; }
    public Day getWednesday() { return days[2]; }
    public Day getThursday() { return days[3]; }
    public Day getFriday() { return days[4]; }
    public Day getSaturday() { return days[5]; }
    public Day getSunday() { return days[6]; }

    /** Food and drink items of one meal box. */
    public ArrayList<Food_Item_CIF4> getBox(int dayIndex, int mealIndex) {
        return days[dayIndex].getBox(mealIndex);
    }

    /** True if no meal box of any day holds an item. */
    public boolean isEmpty() {
        for (Day day : days) {
            for (int m = 0; m < MEAL_BOXES.length; m++) {
                if (!day.getBox(m).isEmpty()) return false;
            }
        }
        return true;
    }

    /** Trimmed diet name, or null if it is empty. */
    public static String cleanDietName(String input) {
        if (input == null) return null;
        String trimmed = input.trim().replaceAll("\\s+", " ");
        return trimmed.isEmpty() || trimmed.length() > 60 ? null : trimmed;
    }

    /** Parses the grams eaten for an item: a whole number from 1 to 5000, otherwise null. */
    public static Integer parseGrams(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        if (!trimmed.matches("\\d{1,4}")) return null;
        int grams = Integer.parseInt(trimmed);
        return grams >= 1 && grams <= 5000 ? grams : null;
    }

    /**
     * Credits of a meal with the existing formula: each item's
     * Calorie_Value = calories_per_100g × weight / 100, summed as SummaryBoxCIF12 does.
     */
    public static int mealCredits(List<Food_Item_CIF4> items) {
        int total = 0;
        if (items == null) return total;
        for (Food_Item_CIF4 item : items) {
            item.Make_calorie_value();
            total += item.Get_calorie_value();
        }
        return total;
    }

    /** One day of the plan with its six meal boxes. */
    public static class Day {
        private final String dayName;
        private final ArrayList<Food_Item_CIF4> Breakfast_Box = new ArrayList<>();
        private final ArrayList<Food_Item_CIF4> Lunch_Box = new ArrayList<>();
        private final ArrayList<Food_Item_CIF4> Brunch_Box = new ArrayList<>();
        private final ArrayList<Food_Item_CIF4> Dinner_Box = new ArrayList<>();
        private final ArrayList<Food_Item_CIF4> Supper_Box = new ArrayList<>();
        private final ArrayList<Food_Item_CIF4> Snack_Box = new ArrayList<>();

        public Day(String dayName) { this.dayName = dayName; }

        public String getDayName() { return dayName; }
        public ArrayList<Food_Item_CIF4> getBreakfast_Box() { return Breakfast_Box; }
        public ArrayList<Food_Item_CIF4> getLunch_Box() { return Lunch_Box; }
        public ArrayList<Food_Item_CIF4> getBrunch_Box() { return Brunch_Box; }
        public ArrayList<Food_Item_CIF4> getDinner_Box() { return Dinner_Box; }
        public ArrayList<Food_Item_CIF4> getSupper_Box() { return Supper_Box; }
        public ArrayList<Food_Item_CIF4> getSnack_Box() { return Snack_Box; }

        public ArrayList<Food_Item_CIF4> getBox(int mealIndex) {
            switch (mealIndex) {
                case BREAKFAST_BOX: return Breakfast_Box;
                case LUNCH_BOX: return Lunch_Box;
                case BRUNCH_BOX: return Brunch_Box;
                case DINNER_BOX: return Dinner_Box;
                case SUPPER_BOX: return Supper_Box;
                case SNACK_BOX: return Snack_Box;
                default: throw new IllegalArgumentException("Unknown meal box " + mealIndex);
            }
        }
    }
}
