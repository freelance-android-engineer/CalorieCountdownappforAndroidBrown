package ese.com.caloriecountdownappforandroidbrown;

/**
 * The Subscriber's choice of Steps Numerator (calories burned per step) used by Accountability
 * and de-Accountability: points = round(steps × Steps Numerator).
 *
 * Client rule: show where the value comes from and offer three choices —
 *   A. accept the value displayed by the app (calculated at Start Weight Loss from the start
 *      weight: start weight in lbs × 0.000446),
 *   B. enter a custom value,
 *   C. use 0.089 — Recommended.
 * The choice is stored in SQLite (steps_numerator.sn_source) so it is never silently replaced.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class StepsNumeratorChoice {

    /** Client's recommended personal Steps Numerator. */
    public static final double RECOMMENDED = 0.089;
    /** Factor used at Start Weight Loss: Steps Numerator = start weight (lbs) × 0.000446. */
    public static final double WEIGHT_FACTOR = 0.000446;

    /** Calculated at Start Weight Loss; not chosen by the Subscriber yet. */
    public static final String SOURCE_WEIGHT_DERIVED = "WEIGHT_DERIVED";
    /** Option A: Subscriber accepted the value displayed by the app. */
    public static final String SOURCE_ACCEPTED = "ACCEPTED_DISPLAYED";
    /** Option B: Subscriber entered their own value. */
    public static final String SOURCE_CUSTOM = "CUSTOM";
    /** Option C: Subscriber chose the recommended 0.089. */
    public static final String SOURCE_RECOMMENDED = "RECOMMENDED";

    /** Accepted range for a custom value (exclusive of 0). */
    public static final double MAX_CUSTOM = 1.0;

    private StepsNumeratorChoice() {}

    /** True when the Subscriber has made an explicit choice (A, B or C). */
    public static boolean isExplicitChoice(String source) {
        return SOURCE_ACCEPTED.equals(source) || SOURCE_CUSTOM.equals(source) || SOURCE_RECOMMENDED.equals(source);
    }

    /**
     * The Steps Numerator calculations use: the Subscriber's explicit choice (A, B or C), otherwise
     * the recommended 0.089 — a value only calculated at Start Weight Loss is never used silently.
     */
    public static double effective(Double storedValue, String source) {
        if (storedValue != null && isExplicitChoice(source) && storedValue > 0
                && !storedValue.isNaN() && !storedValue.isInfinite()) {
            return storedValue;
        }
        return RECOMMENDED;
    }

    /**
     * Parses a custom Steps Numerator.
     *
     * @return the value if it is a number greater than 0 and at most {@link #MAX_CUSTOM},
     *         otherwise null (empty, non-numeric, zero, negative, too large, NaN, infinite)
     */
    public static Double parseCustom(String input) {
        if (input == null) return null;
        String trimmed = input.trim().replace(',', '.');
        if (!trimmed.matches("\\d{0,3}(\\.\\d{1,6})?") || trimmed.isEmpty() || ".".equals(trimmed)) return null;
        double value;
        try {
            value = Double.parseDouble(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
        if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0 || value > MAX_CUSTOM) return null;
        return value;
    }

    /** Human readable label for a stored source. */
    public static String describeSource(String source) {
        if (SOURCE_ACCEPTED.equals(source)) return "Accepted value displayed by the app";
        if (SOURCE_CUSTOM.equals(source)) return "Custom value entered by you";
        if (SOURCE_RECOMMENDED.equals(source)) return "Recommended value (0.089)";
        if (SOURCE_WEIGHT_DERIVED.equals(source)) return "Calculated at Start Weight Loss (not yet confirmed by you)";
        return "Not set";
    }
}
