package ese.com.caloriecountdownappforandroidbrown;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

/**
 * Expense Tracking (Version 2.0.1): the Subscriber's money expenses, kept apart from calorie Debits.
 *
 * Client rule: 12 monthly SQLite tables (January … December), each with Day, Description, Amount
 * and Currency, plus an _id primary key so a row can be updated or deleted safely. Every expense
 * uses the Subscriber's default currency, chosen once on first use.
 *
 * Amounts are exact decimals (BigDecimal), stored as plain text such as "250" or "12.50", so no
 * floating-point rounding is involved in saving or totalling.
 *
 * Note: the tables have no year (client spec), so the same month of different years shares a table.
 *
 * No Android dependencies so it can be unit tested on the JVM.
 */
public final class ExpenseTracking {

    /** Prefs ("Calorie_Countdown"): ISO 4217 code of the Subscriber's default expense currency. */
    public static final String KEY_DEFAULT_CURRENCY = "expense_default_currency";

    public static final int MAX_DESCRIPTION_LENGTH = 100;
    /** Largest amount accepted for one expense. */
    public static final BigDecimal MAX_AMOUNT = new BigDecimal("999999999.99");

    private static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    private ExpenseTracking() {}

    /** One saved expense. */
    public static final class Entry {
        public final long id;
        public final int day;
        public final String description;
        public final BigDecimal amount;
        public final String currency;

        public Entry(long id, int day, String description, BigDecimal amount, String currency) {
            this.id = id;
            this.day = day;
            this.description = description;
            this.amount = amount;
            this.currency = currency;
        }
    }

    /** SQLite table for a month (Calendar.JANUARY = 0 … Calendar.DECEMBER = 11), e.g. "expense_november". */
    public static String tableForMonth(int calendarMonth) {
        return "expense_" + monthName(calendarMonth).toLowerCase(Locale.US);
    }

    /** All 12 monthly tables, January first. */
    public static String[] allTables() {
        String[] tables = new String[12];
        for (int m = 0; m < 12; m++) tables[m] = tableForMonth(m);
        return tables;
    }

    /** "January" … "December". */
    public static String monthName(int calendarMonth) {
        if (calendarMonth < Calendar.JANUARY || calendarMonth > Calendar.DECEMBER) {
            throw new IllegalArgumentException("month " + calendarMonth);
        }
        return MONTH_NAMES[calendarMonth];
    }

    /**
     * Parses an entered amount: a positive number with at most {@code maxDecimals} decimal places
     * (commas allowed as thousands separators), no larger than {@link #MAX_AMOUNT}.
     *
     * @return the exact amount, or null if it is not a valid positive money value
     */
    public static BigDecimal parseAmount(String text, int maxDecimals) {
        if (text == null) return null;
        String cleaned = text.trim().replace(",", "");
        if (cleaned.isEmpty() || !cleaned.matches("\\d+(\\.\\d+)?|\\.\\d+")) return null;
        try {
            BigDecimal amount = new BigDecimal(cleaned).stripTrailingZeros();
            if (amount.signum() <= 0 || amount.compareTo(MAX_AMOUNT) > 0) return null;
            if (amount.scale() > Math.max(0, maxDecimals)) return null;
            return amount.scale() < 0 ? amount.setScale(0) : amount;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Text stored in the Amount column, e.g. "250" or "12.5". */
    public static String toStored(BigDecimal amount) {
        return amount.toPlainString();
    }

    /** Reads the Amount column back; null if the stored text is not a number. */
    public static BigDecimal fromStored(String stored) {
        if (stored == null) return null;
        try {
            return new BigDecimal(stored.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Grand Total of the entries (exact). */
    public static BigDecimal total(Collection<Entry> entries) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Entry entry : entries) {
            if (entry.amount != null) sum = sum.add(entry.amount);
        }
        return sum;
    }

    /** "1,750" or "12.50": thousands separators, decimals only when the amount has them. */
    public static String formatAmount(BigDecimal amount) {
        BigDecimal value = amount.stripTrailingZeros();
        if (value.scale() <= 0) {
            return String.format(Locale.US, "%,d", value.setScale(0).toBigIntegerExact());
        }
        return String.format(Locale.US, "%,." + Math.max(2, value.scale()) + "f", value);
    }

    /** Currency before the total, e.g. "INR 1,750". */
    public static String formatMoney(String currency, BigDecimal amount) {
        return currency + " " + formatAmount(amount);
    }

    /** Validation message for a description, or null when it is valid. */
    public static String descriptionError(String description) {
        String text = description == null ? "" : description.trim();
        if (text.isEmpty()) return "Enter a description";
        if (text.length() > MAX_DESCRIPTION_LENGTH) return "Description is too long (max " + MAX_DESCRIPTION_LENGTH + ")";
        return null;
    }
}
