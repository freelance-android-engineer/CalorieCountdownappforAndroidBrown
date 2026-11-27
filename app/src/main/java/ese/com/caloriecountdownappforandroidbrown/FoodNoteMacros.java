package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Food Notes "Macros" button: builds the SI prompt for the selected Food Notes and reads the
 * SI reply back into per-note values and a combined total. Values are SI estimates only.
 */
final class FoodNoteMacros {

    /** Label used in the SI reply, unit shown to the subscriber. */
    static final String[][] NUTRIENTS = {
            {"Calories", "kcal"},
            {"Protein", "g"},
            {"Carbohydrates", "g"},
            {"Total Fat", "g"},
            {"Saturated Fat", "g"},
            {"Dietary Fiber", "g"},
            {"Total Sugars", "g"},
            {"Sodium", "mg"},
    };

    private FoodNoteMacros() {}

    /** Quantity text worth showing / sending ("" and "0" mean no quantity recorded). */
    static String quantityOf(Map<String, String> food) {
        String qty = food.get("quantity");
        if (qty == null) return "";
        qty = qty.trim();
        return qty.equals("0") ? "" : qty;
    }

    static String buildPrompt(List<Map<String, String>> foods) {
        StringBuilder sb = new StringBuilder();
        sb.append("Estimate the nutritional information for each of these food notes:\n");
        for (int i = 0; i < foods.size(); i++) {
            Map<String, String> food = foods.get(i);
            String qty = quantityOf(food);
            sb.append(i + 1).append(". ").append(food.get("food"));
            if (!qty.isEmpty()) sb.append(" (quantity: ").append(qty).append(")");
            sb.append("\n");
        }
        sb.append("\nReply ONLY in this exact format, one block per food note, in the same order:\n");
        sb.append("Item <number>\n");
        for (String[] n : NUTRIENTS) {
            sb.append(n[0]).append(": <number>\n");
        }
        sb.append("\nCalories in kcal, Sodium in mg, everything else in grams. "
                + "Numbers only, no ranges, no units. Write N/A if a value is unknown. No other text.");
        return sb.toString();
    }

    /**
     * Splits the SI reply into one array of values per food note (null = not returned).
     * Returns a list of the same size as {@code count}; missing items are all-null.
     */
    static List<Double[]> parse(String reply, int count) {
        List<Double[]> result = new ArrayList<>();
        for (int i = 0; i < count; i++) result.add(new Double[NUTRIENTS.length]);
        if (reply == null) return result;

        Matcher item = Pattern.compile("(?im)^[\\s*#]*Item\\s*(\\d+)").matcher(reply);
        List<int[]> starts = new ArrayList<>(); // {itemNumber, offset}
        while (item.find()) starts.add(new int[]{Integer.parseInt(item.group(1)), item.end()});
        if (starts.isEmpty() && count == 1) starts.add(new int[]{1, 0}); // single note without "Item 1"
        for (int s = 0; s < starts.size(); s++) {
            int index = starts.get(s)[0] - 1;
            if (index < 0 || index >= count) continue;
            int end = s + 1 < starts.size() ? starts.get(s + 1)[1] : reply.length();
            String block = reply.substring(starts.get(s)[1], end);
            Double[] values = result.get(index);
            for (int n = 0; n < NUTRIENTS.length; n++) {
                values[n] = valueOf(block, NUTRIENTS[n][0]);
            }
        }
        return result;
    }

    private static Double valueOf(String block, String label) {
        Matcher m = Pattern.compile("(?im)^[\\s*\\-]*" + Pattern.quote(label)
                + "[\\s*]*:\\s*([\\d]+(?:\\.\\d+)?)").matcher(block);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }

    /** True when the reply contained no usable value at all. */
    static boolean isEmpty(List<Double[]> items) {
        for (Double[] values : items) {
            for (Double v : values) {
                if (v != null) return false;
            }
        }
        return true;
    }

    /** Combined values; a nutrient is null only when no food note returned it. */
    static Double[] total(List<Double[]> items) {
        Double[] total = new Double[NUTRIENTS.length];
        for (Double[] values : items) {
            for (int n = 0; n < NUTRIENTS.length; n++) {
                if (values[n] == null) continue;
                total[n] = (total[n] == null ? 0 : total[n]) + values[n];
            }
        }
        return total;
    }

    /** True when a nutrient is missing for at least one food note (combined value is partial). */
    static boolean isPartial(List<Double[]> items, int nutrient) {
        for (Double[] values : items) {
            if (values[nutrient] == null) return true;
        }
        return false;
    }

    static String format(Double value, String unit) {
        if (value == null) return "N/A";
        String number = value == Math.rint(value)
                ? String.format(Locale.getDefault(), "%,d", value.longValue())
                : String.format(Locale.getDefault(), "%,.1f", value);
        return number + " " + unit;
    }

    /** Text shown in the Macros result dialog. */
    static String buildReport(List<Map<String, String>> foods, List<Double[]> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foods.size(); i++) {
            Map<String, String> food = foods.get(i);
            sb.append(foods.size() > 1 ? (i + 1) + ". " : "").append(food.get("food")).append("\n");
            String qty = quantityOf(food);
            if (!qty.isEmpty()) sb.append("Quantity: ").append(qty).append("\n");
            appendValues(sb, items.get(i), null);
            sb.append("\n");
        }
        if (foods.size() > 1) {
            sb.append("COMBINED (").append(foods.size()).append(" food notes)\n");
            appendValues(sb, total(items), items);
            sb.append("\n");
        }
        sb.append("These values are SI estimates, not verified measurements.");
        return sb.toString();
    }

    private static void appendValues(StringBuilder sb, Double[] values, List<Double[]> partialCheck) {
        boolean anyPartial = false;
        for (int n = 0; n < NUTRIENTS.length; n++) {
            sb.append(NUTRIENTS[n][0]).append(": ").append(format(values[n], NUTRIENTS[n][1]));
            if (partialCheck != null && values[n] != null && isPartial(partialCheck, n)) {
                sb.append(" *");
                anyPartial = true;
            }
            sb.append("\n");
        }
        if (anyPartial) sb.append("* not returned for every food note\n");
    }
}
