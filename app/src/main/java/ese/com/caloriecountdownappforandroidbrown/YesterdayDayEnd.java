package ese.com.caloriecountdownappforandroidbrown;

/**
 * Variables → "Yesterday Dayend Balance": reminds the Subscriber of the balance they must finish
 * today under (preferably by 250 - 300 points, but any amount is a win).
 *
 * Reference Balance (client rules):
 *   1. Recalibrated today → the latest Recalibrated Balance ("the ultimate Truth").
 *   2. Otherwise yesterday's definitive Day End Balance (day_end_balance_def, see DayEndBalance).
 *   3. Otherwise "No Record": the Current Balance becomes the new Previous Day Dayend and is saved
 *      once for today, so reopening the dialog keeps the same baseline.
 *
 * Target range: Reference − 300 to Reference − 250.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class YesterdayDayEnd {

    /** Reference is yesterday's Day End Balance. */
    public static final String TYPE_PREVIOUS_DAY_END = "PREVIOUS_DAY_END";
    /** Reference is today's latest Recalibrated Balance. */
    public static final String TYPE_RECALIBRATED_TODAY = "RECALIBRATED_TODAY";
    /** No record existed: the Current Balance was saved as the new Previous Day Dayend. */
    public static final String TYPE_NO_RECORD_BASELINE = "NO_RECORD_BASELINE";

    /** Preferred finish: 250 - 300 points under the reference. */
    public static final int PREFERRED_MIN_DROP = 250;
    public static final int PREFERRED_MAX_DROP = 300;

    private YesterdayDayEnd() {}

    /** The reference used today, as stored in SQLite (one row per day). */
    public static final class Reference {
        /** Day the reference applies to ("yyyy-MM-dd", today). */
        public final String forDate;
        public final int balance;
        public final String type;
        /** Date of the balance itself: yesterday, or today for Recalibration / baseline. */
        public final String referenceDate;
        /** When the balance was captured ("yyyy-MM-dd HH:mm:ss"), may be null. */
        public final String capturedAt;

        public Reference(String forDate, int balance, String type, String referenceDate, String capturedAt) {
            this.forDate = forDate;
            this.balance = balance;
            this.type = type;
            this.referenceDate = referenceDate;
            this.capturedAt = capturedAt;
        }

        public boolean sameAs(Reference other) {
            return other != null && balance == other.balance && type.equals(other.type)
                    && eq(forDate, other.forDate) && eq(referenceDate, other.referenceDate)
                    && eq(capturedAt, other.capturedAt);
        }

        private static boolean eq(String a, String b) {
            return a == null ? b == null : a.equals(b);
        }
    }

    /**
     * Chooses today's reference.
     *
     * @param today               "yyyy-MM-dd"
     * @param yesterday           "yyyy-MM-dd"
     * @param todayRecord         today's Day End Balance record (holds the latest Recalibrated Balance), or null
     * @param prefsRecalibrated   today's recalibrated balance kept for the Kitty, used only if the
     *                            SQLite capture is missing; null if not recalibrated today
     * @param yesterdayRecord     yesterday's Day End Balance record, or null
     * @param storedToday         reference already saved for today, or null
     * @param currentBalance      Main Countdown Balance on screen, or null if it could not be read
     * @param now                 "yyyy-MM-dd HH:mm:ss"
     * @return the reference, or null if there is no record and the Current Balance is unavailable
     */
    public static Reference resolve(String today, String yesterday, DayEndBalance.Record todayRecord,
                                    Integer prefsRecalibrated, DayEndBalance.Record yesterdayRecord,
                                    Reference storedToday, Integer currentBalance, String now) {
        if (todayRecord != null && todayRecord.recalibratedBalance != null) {
            // saveDayEndBalance keeps the latest Recalibration of the day
            String at = DayEndBalance.SOURCE_RECALIBRATION.equals(todayRecord.source) ? todayRecord.capturedAt : null;
            return new Reference(today, todayRecord.recalibratedBalance, TYPE_RECALIBRATED_TODAY, today, at);
        }
        if (prefsRecalibrated != null) {
            return new Reference(today, prefsRecalibrated, TYPE_RECALIBRATED_TODAY, today, null);
        }
        if (yesterdayRecord != null) {
            return new Reference(today, yesterdayRecord.balance, TYPE_PREVIOUS_DAY_END, yesterday,
                    yesterdayRecord.capturedAt);
        }
        if (storedToday != null && TYPE_NO_RECORD_BASELINE.equals(storedToday.type) && today.equals(storedToday.forDate)) {
            return storedToday; // already saved: never replaced by a later Current Balance
        }
        if (currentBalance == null) return null;
        return new Reference(today, currentBalance, TYPE_NO_RECORD_BASELINE, today, now);
    }

    /** Preferred target: the highest balance that is 250 points under the reference. */
    public static int targetUpper(int reference) {
        return reference - PREFERRED_MIN_DROP;
    }

    /** Lowest balance of the preferred range (300 points under the reference). */
    public static int targetLower(int reference) {
        return reference - PREFERRED_MAX_DROP;
    }

    /** Points still to reduce to finish below the reference (0 when already below). */
    public static int pointsToBeatReference(int reference, int current) {
        return Math.max(0, current - reference + 1);
    }

    /** Points still to reduce to reach the preferred 250-point drop (0 when reached). */
    public static int pointsToPreferredTarget(int reference, int current) {
        return Math.max(0, current - targetUpper(reference));
    }

    /** Where the Current Balance stands against the reference. */
    public enum Status { ABOVE_REFERENCE, EQUAL_TO_REFERENCE, BELOW_REFERENCE, WITHIN_TARGET, BEYOND_TARGET }

    public static Status status(int reference, int current) {
        if (current > reference) return Status.ABOVE_REFERENCE;
        if (current == reference) return Status.EQUAL_TO_REFERENCE;
        if (current > targetUpper(reference)) return Status.BELOW_REFERENCE;
        if (current >= targetLower(reference)) return Status.WITHIN_TARGET;
        return Status.BEYOND_TARGET;
    }

    /**
     * Dialog text.
     *
     * @param newlySaved true when this opening saved a No Record baseline
     * @param dateLabel  reference date as shown to the Subscriber
     */
    public static String buildMessage(Reference ref, int current, boolean newlySaved, String dateLabel) {
        StringBuilder sb = new StringBuilder();
        int r = ref.balance;
        if (TYPE_RECALIBRATED_TODAY.equals(ref.type)) {
            sb.append("You recalibrated today, so your Recalibrated Balance is the new reference.\n\n");
            sb.append("Recalibrated Balance (").append(dateLabel).append("):\n").append(fmt(r));
        } else if (TYPE_PREVIOUS_DAY_END.equals(ref.type)) {
            sb.append("Yesterday's Day End Balance (").append(dateLabel).append("):\n").append(fmt(r));
        } else {
            sb.append("No Record.\n\nThere is no Day End Balance for yesterday, so ");
            sb.append(newlySaved ? "your Current Balance has been saved" : "your Current Balance was saved");
            sb.append(" as the new Previous Day Dayend (").append(dateLabel).append("):\n").append(fmt(r));
        }

        sb.append("\n\nToday's Target (250 - 300 points lower):\n")
                .append(fmt(targetUpper(r))).append(" to ").append(fmt(targetLower(r)));
        sb.append("\n\nCurrent Balance:\n").append(fmt(current)).append("\n\n");

        switch (status(r, current)) {
            case ABOVE_REFERENCE:
                sb.append("You are ").append(fmt(current - r)).append(" points above the reference.\n")
                        .append("Reduce by ").append(fmt(pointsToBeatReference(r, current)))
                        .append(" points to finish under it (any amount is a win), or by ")
                        .append(fmt(pointsToPreferredTarget(r, current))).append(" points to reach the preferred target.");
                break;
            case EQUAL_TO_REFERENCE:
                sb.append("You are level with the reference.\n")
                        .append("Reduce by 1 point to finish under it, or by ")
                        .append(fmt(pointsToPreferredTarget(r, current))).append(" points to reach the preferred target.");
                break;
            case BELOW_REFERENCE:
                sb.append("Well done! You are ").append(fmt(r - current)).append(" points under the reference.\n")
                        .append("Reduce by ").append(fmt(pointsToPreferredTarget(r, current)))
                        .append(" more points to reach the preferred target.");
                break;
            case WITHIN_TARGET:
                sb.append("Excellent! You are ").append(fmt(r - current))
                        .append(" points under the reference, within the preferred 250 - 300 point target.");
                break;
            default:
                sb.append("Excellent! You are ").append(fmt(r - current))
                        .append(" points under the reference, beyond the preferred 250 - 300 point target.");
                break;
        }
        return sb.toString();
    }

    private static String fmt(int value) {
        return String.format("%,d", value);
    }
}
