package ese.com.caloriecountdownappforandroidbrown;

/**
 * State for the optional "Apply BMR" button in the Sum Calories dialog.
 * No Android dependencies so it can be unit tested on the JVM.
 *
 * Example: summed total 2,000, BMR 1,500 → 500.
 *
 * The adjusted value is always computed from the original summed total, and BMR can be
 * applied only once per dialog, so repeated clicks never subtract it twice.
 */
final class SumCaloriesBmr {

    private final int summedTotal;
    private int appliedBmr;
    private boolean bmrApplied;

    SumCaloriesBmr(int summedTotal) {
        this.summedTotal = summedTotal;
    }

    /**
     * Subtracts the BMR from the summed total. Has no effect if BMR was already applied.
     *
     * @return true if BMR was applied by this call
     */
    boolean applyBmr(int bmr) {
        if (bmrApplied || bmr <= 0) {
            return false;
        }
        appliedBmr = bmr;
        bmrApplied = true;
        return true;
    }

    boolean isBmrApplied() {
        return bmrApplied;
    }

    int getAppliedBmr() {
        return appliedBmr;
    }

    /** The total the dialog shows and credits: summed total, minus BMR if it was applied. */
    int getTotal() {
        return bmrApplied ? summedTotal - appliedBmr : summedTotal;
    }
}
