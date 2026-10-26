package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Client regression: recalibrating with a current weight of 117.84 kg showed -1,009,932 Cr because
 * the Target Weight saved in pounds (249) was subtracted from a weight in kilograms.
 */
public class RecalibrationCalculatorTest {

    private static final float CLIENT_WEIGHT_KG = 117.84f;

    // ── Test A: Recalibrate ──

    @Test
    public void unconvertedPoundsTargetReproducesTheReportedBalance() {
        // What the latest version did: (117.84 − 249) × 2.2 × 3500
        assertEquals(-1_009_932, RecalibrationCalculator.recalibrateKilograms(CLIENT_WEIGHT_KG, 249f));
    }

    @Test
    public void kilogramTargetGivesThePreviousVersionsResult() {
        // Previous working version used a Target Weight of 112.9 kg for kilogram recalibration
        assertEquals(38_037, RecalibrationCalculator.recalibrateKilograms(CLIENT_WEIGHT_KG, 112.9f));
        Float target = RecalibrationCalculator.convert(112.9f, "kilograms", RecalibrationCalculator.UNIT_KILOGRAMS);
        assertEquals(38_037, RecalibrationCalculator.recalibrateKilograms(CLIENT_WEIGHT_KG, target));
    }

    @Test
    public void poundsTargetIsConvertedBeforeKilogramRecalibration() {
        Float targetKg = RecalibrationCalculator.convert(249f, "pounds", RecalibrationCalculator.UNIT_KILOGRAMS);
        assertEquals(112.9446f, targetKg, 0.001f);
        int balance = RecalibrationCalculator.recalibrateKilograms(CLIENT_WEIGHT_KG, targetKg);
        assertEquals(37_694, balance);
        assertTrue("balance must be positive and in the expected range", balance > 0 && balance < 100_000);
    }

    @Test
    public void kilogramTargetIsConvertedBeforePoundRecalibration() {
        Float targetLb = RecalibrationCalculator.convert(112.9f, "kilograms", RecalibrationCalculator.UNIT_POUNDS);
        assertEquals(248.9016f, targetLb, 0.001f);
        // Existing pounds formula: (weight lb − target lb) × 3500
        assertEquals(37_765, RecalibrationCalculator.recalibratePounds(259.79f, 249f));
    }

    @Test
    public void unknownTargetUnitIsNotGuessed() {
        assertNull(RecalibrationCalculator.convert(249f, null, RecalibrationCalculator.UNIT_KILOGRAMS));
        assertNull(RecalibrationCalculator.convert(249f, "", RecalibrationCalculator.UNIT_KILOGRAMS));
    }

    @Test
    public void startWeightLossUnitNamesAreRecognised() {
        assertEquals("kilograms", RecalibrationCalculator.normaliseUnit("kilograms"));
        assertEquals("kilograms", RecalibrationCalculator.normaliseUnit("Kilograms"));
        assertEquals("pounds", RecalibrationCalculator.normaliseUnit("pounds"));
        assertEquals("pounds", RecalibrationCalculator.normaliseUnit("pounds lbs"));
        assertNull(RecalibrationCalculator.normaliseUnit("stone"));
    }

    @Test
    public void weightInputIsParsedWithItsDecimals() {
        assertEquals(117.84f, RecalibrationCalculator.parseWeight("117.84"), 0f);
        assertEquals(112.9f, RecalibrationCalculator.parseWeight(" 112.9 "), 0f);
        assertNull(RecalibrationCalculator.parseWeight(""));
        assertNull(RecalibrationCalculator.parseWeight("abc"));
        assertNull(RecalibrationCalculator.parseWeight("-5"));
        assertNull(RecalibrationCalculator.parseWeight("0"));
        assertNull(RecalibrationCalculator.parseWeight(null));
    }

    @Test
    public void confirmedTargetWeightMustBeRealistic() {
        assertTrue(RecalibrationCalculator.isValidTargetWeight("112.9"));
        assertTrue(RecalibrationCalculator.isValidTargetWeight("249"));
        assertTrue(RecalibrationCalculator.isValidTargetWeight("1000"));
        org.junit.Assert.assertFalse(RecalibrationCalculator.isValidTargetWeight("1129112.925")); // would overflow
        org.junit.Assert.assertFalse(RecalibrationCalculator.isValidTargetWeight("1"));
        org.junit.Assert.assertFalse(RecalibrationCalculator.isValidTargetWeight("0"));
        org.junit.Assert.assertFalse(RecalibrationCalculator.isValidTargetWeight(""));
    }

    // ── Test C: Steps Numerator ──

    @Test
    public void stepsChallengeUsesRecommendedNumeratorUnlessChosen() {
        // Client's phone showed 0.11466784184239999 = 116.62 kg × 2.20462 × 0.000446 (Start Weight Loss)
        assertEquals(0.089, StepsNumeratorChoice.effective(0.11466784184239999,
                StepsNumeratorChoice.SOURCE_WEIGHT_DERIVED), 0.0);
        assertEquals(0.089, StepsNumeratorChoice.effective(null, null), 0.0);
        assertEquals(0.089, StepsNumeratorChoice.effective(0.089, StepsNumeratorChoice.SOURCE_RECOMMENDED), 0.0);
        // An explicit choice is kept
        assertEquals(0.1, StepsNumeratorChoice.effective(0.1, StepsNumeratorChoice.SOURCE_CUSTOM), 0.0);
        assertEquals(0.11, StepsNumeratorChoice.effective(0.11, StepsNumeratorChoice.SOURCE_ACCEPTED), 0.0);
    }
}
