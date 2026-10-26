package ese.com.caloriecountdownappforandroidbrown;

/**
 * Recalibrate: Main Countdown Balance from the Subscriber's current weight and Target Weight.
 *
 * The formulas are unchanged from Recalibrate.kt:
 *   Kilograms: balance = ((weight kg − target kg) × 2.2) × 3500
 *   Pounds:    balance =  (weight lb − target lb) × 3500
 *
 * The Target Weight is saved in the unit chosen at Start Weight Loss (or, in older versions, as
 * a hardcoded value per unit), so it is converted to the recalibration unit before subtracting.
 * Subtracting a Target Weight in pounds from a weight in kilograms gave
 * (117.84 − 249) × 2.2 × 3500 = −1,009,932 Cr.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class RecalibrationCalculator {

    public static final String UNIT_KILOGRAMS = "kilograms";
    public static final String UNIT_POUNDS = "pounds";
    /** Same kg → lb factor as Start Weight Loss uses for the Steps Numerator. */
    public static final double POUNDS_PER_KILOGRAM = 2.20462;

    /** Prefs key ("Calorie_Countdown"): unit of the saved Target_Weight. */
    public static final String KEY_TARGET_WEIGHT_UNITS = "Target_Weight_Units";

    private RecalibrationCalculator() {}

    /** "kilograms" / "pounds" for the unit names used across the app, otherwise null (unknown). */
    public static String normaliseUnit(String unit) {
        if (unit == null) return null;
        String u = unit.trim().toLowerCase(java.util.Locale.US);
        if (u.startsWith("kilogram") || u.equals("kg") || u.equals("kgs")) return UNIT_KILOGRAMS;
        if (u.startsWith("pound") || u.equals("lb") || u.equals("lbs")) return UNIT_POUNDS;
        return null;
    }

    /**
     * Target Weight in the wanted unit.
     *
     * @return the converted value, or null if the Target Weight's unit is unknown
     */
    public static Float convert(float target, String targetUnit, String wantedUnit) {
        String from = normaliseUnit(targetUnit);
        String to = normaliseUnit(wantedUnit);
        if (from == null || to == null) return null;
        if (from.equals(to)) return target;
        if (to.equals(UNIT_KILOGRAMS)) return (float) (target / POUNDS_PER_KILOGRAM);
        return (float) (target * POUNDS_PER_KILOGRAM);
    }

    /** Existing kilograms formula (same Float/Double arithmetic and truncation as Recalibrate.kt). */
    public static int recalibrateKilograms(float weightKg, float targetKg) {
        return (int) (((weightKg - targetKg) * 2.2) * 3500);
    }

    /** Existing pounds formula (same Float arithmetic and truncation as Recalibrate.kt). */
    public static int recalibratePounds(float weightLb, float targetLb) {
        return (int) ((weightLb - targetLb) * 3500);
    }

    /** Largest Target Weight accepted when confirming it (kilograms or pounds). */
    public static final float MAX_TARGET_WEIGHT = 1000f;

    /** A confirmed Target Weight must be a number above 1 and at most {@link #MAX_TARGET_WEIGHT}. */
    public static boolean isValidTargetWeight(String input) {
        Float value = parseWeight(input);
        return value != null && value > 1f && value <= MAX_TARGET_WEIGHT;
    }

    /** A weight typed by the Subscriber: a number greater than 0, otherwise null. */
    public static Float parseWeight(String input) {
        if (input == null) return null;
        try {
            float value = Float.parseFloat(input.trim());
            return (value > 0 && !Float.isInfinite(value) && !Float.isNaN(value)) ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
