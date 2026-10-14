package ese.com.caloriecountdownappforandroidbrown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A previously assigned Step Challenge, read back for the Variables → "Steps Challenge" option.
 * No Android dependencies so it can be unit tested on the JVM.
 *
 * The app saves Step Challenges in two places:
 *   MAIN_SCREEN   → steps_challenge_log      (main-screen "Steps Challenge" button)
 *   FOUR_PM_DEBIT → fourpm_debit_challenge   (4PM Food Notes processing → "View Step Challenge")
 * {@link #mostRecent} picks whichever was saved last.
 */
public final class LastStepChallenge {

    public enum Source { MAIN_SCREEN, FOUR_PM_DEBIT }

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final String CREATED_AT_PATTERN = "dd-MM-yyyy HH:mm:ss";

    public final Source source;
    /** Date the challenge was assigned for, "dd-MM-yyyy". */
    public final String challengeDate;
    /** When the row was saved, "dd-MM-yyyy HH:mm:ss" (may be empty on old rows). */
    public final String createdAt;
    public final int stepChallenge;
    public final boolean isCapped;

    public LastStepChallenge(Source source, String challengeDate, String createdAt,
                             int stepChallenge, boolean isCapped) {
        this.source = source;
        this.challengeDate = challengeDate != null ? challengeDate.trim() : "";
        this.createdAt = createdAt != null ? createdAt.trim() : "";
        this.stepChallenge = stepChallenge;
        this.isCapped = isCapped;
    }

    /** Save time of this challenge, falling back to its date; null if neither can be read. */
    Date savedTime() {
        Date saved = parse(createdAt, CREATED_AT_PATTERN);
        return saved != null ? saved : parse(challengeDate, DATE_PATTERN);
    }

    /** The more recently saved of two challenges; either may be null. */
    public static LastStepChallenge mostRecent(LastStepChallenge a, LastStepChallenge b) {
        if (a == null) return b;
        if (b == null) return a;
        Date timeA = a.savedTime();
        Date timeB = b.savedTime();
        if (timeA == null) return timeB != null ? b : a;
        if (timeB == null) return a;
        return timeB.after(timeA) ? b : a;
    }

    public String buildMessage() {
        StringBuilder sb = new StringBuilder("Your last assigned Step Challenge:\n\n");
        sb.append(String.format("%,d", stepChallenge)).append(" Steps");
        if (stepChallenge == 0) {
            sb.append("\n(No steps were needed that day.)");
        }
        if (isCapped) {
            sb.append("\n(Capped at 30,000 steps.)");
        }
        sb.append("\n\nAssigned on: ").append(createdAt.isEmpty() ? challengeDate : createdAt);
        sb.append("\nFrom: ").append(source == Source.FOUR_PM_DEBIT
                ? "4PM Food Notes processing"
                : "Steps Challenge (main screen)");
        return sb.toString();
    }

    public static String emptyMessage() {
        return "No Step Challenge has been assigned yet.\n\n"
                + "Your Step Challenge will appear here once the app has calculated one for you.";
    }

    private static Date parse(String value, String pattern) {
        if (value == null || value.isEmpty()) return null;
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        format.setLenient(false);
        try {
            return format.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
