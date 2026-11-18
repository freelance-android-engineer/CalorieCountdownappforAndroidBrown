package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ExpenseTrackingTest {

    @Test
    public void twelveDistinctSafeMonthlyTables() {
        String[] tables = ExpenseTracking.allTables();
        assertEquals(12, tables.length);
        assertEquals(12, new HashSet<>(Arrays.asList(tables)).size());
        assertEquals("expense_january", tables[0]);
        assertEquals("expense_november", ExpenseTracking.tableForMonth(Calendar.NOVEMBER));
        assertEquals("expense_december", tables[11]);
        for (String table : tables) {
            assertEquals(table, true, table.matches("[a-z_]+"));
        }
    }

    @Test
    public void decemberToJanuaryUsesTheRightTables() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2026, Calendar.DECEMBER, 31, 23, 59);
        assertEquals("expense_december", ExpenseTracking.tableForMonth(cal.get(Calendar.MONTH)));
        assertEquals("December", ExpenseTracking.monthName(cal.get(Calendar.MONTH)));
        cal.add(Calendar.MINUTE, 1);
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals("expense_january", ExpenseTracking.tableForMonth(cal.get(Calendar.MONTH)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMonthIsRejected() {
        ExpenseTracking.tableForMonth(12);
    }

    @Test
    public void amountsAreExactPositiveMoney() {
        assertEquals(new BigDecimal("250"), ExpenseTracking.parseAmount("250", 2));
        assertEquals(new BigDecimal("12.5"), ExpenseTracking.parseAmount(" 12.50 ", 2));
        assertEquals(new BigDecimal("1500"), ExpenseTracking.parseAmount("1,500", 2));
        assertEquals(new BigDecimal("0.5"), ExpenseTracking.parseAmount(".5", 2));
        assertNull(ExpenseTracking.parseAmount("0", 2));
        assertNull(ExpenseTracking.parseAmount("-5", 2));
        assertNull(ExpenseTracking.parseAmount("abc", 2));
        assertNull(ExpenseTracking.parseAmount("", 2));
        assertNull(ExpenseTracking.parseAmount(null, 2));
        assertNull(ExpenseTracking.parseAmount("1.234", 2));
        assertNull("JPY has no decimals", ExpenseTracking.parseAmount("10.5", 0));
        assertNull(ExpenseTracking.parseAmount("1e5", 2));
        assertNull(ExpenseTracking.parseAmount("1000000000", 2));
    }

    @Test
    public void storedAmountRoundTripsExactly() {
        BigDecimal amount = ExpenseTracking.parseAmount("0.10", 2);
        assertEquals("0.1", ExpenseTracking.toStored(amount));
        assertEquals(amount, ExpenseTracking.fromStored(ExpenseTracking.toStored(amount)));
        assertNull(ExpenseTracking.fromStored("x"));
    }

    @Test
    public void clientExampleGrandTotal() {
        List<ExpenseTracking.Entry> november = Arrays.asList(
                new ExpenseTracking.Entry(1, 18, "Lunch", new BigDecimal("250"), "INR"),
                new ExpenseTracking.Entry(2, 18, "Petrol", new BigDecimal("500"), "INR"),
                new ExpenseTracking.Entry(3, 19, "Shopping", new BigDecimal("1000"), "INR"));
        assertEquals("INR 1,750", ExpenseTracking.formatMoney("INR", ExpenseTracking.total(november)));
    }

    @Test
    public void decimalTotalsHaveNoFloatingPointError() {
        List<ExpenseTracking.Entry> entries = Arrays.asList(
                new ExpenseTracking.Entry(1, 1, "A", new BigDecimal("0.1"), "GBP"),
                new ExpenseTracking.Entry(2, 1, "B", new BigDecimal("0.2"), "GBP"));
        assertEquals(new BigDecimal("0.3"), ExpenseTracking.total(entries));
        assertEquals("GBP 0.30", ExpenseTracking.formatMoney("GBP", ExpenseTracking.total(entries)));
        assertEquals("1,234.50", ExpenseTracking.formatAmount(new BigDecimal("1234.5")));
    }

    @Test
    public void emptyMonthTotalsZero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(ExpenseTracking.total(Collections.emptyList())));
    }

    @Test
    public void descriptionIsRequired() {
        assertNotNull(ExpenseTracking.descriptionError("  "));
        assertNotNull(ExpenseTracking.descriptionError(null));
        assertNull(ExpenseTracking.descriptionError("Lunch"));
        assertNotNull(ExpenseTracking.descriptionError(new String(new char[101]).replace('\0', 'x')));
    }
}
