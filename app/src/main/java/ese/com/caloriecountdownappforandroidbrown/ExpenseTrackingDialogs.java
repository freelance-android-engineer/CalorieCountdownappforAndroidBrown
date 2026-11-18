package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Expense Tracking dialogs for the Food Notes screen: "Add New Expense" and "Show Expense"
 * (Summary / Detailed). Money expenses only — calorie Debits are not touched.
 */
final class ExpenseTrackingDialogs {

    private ExpenseTrackingDialogs() {}

    // ── Default currency ─────────────────────────────────────────────────────

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences("Calorie_Countdown", Context.MODE_PRIVATE);
    }

    /** The Subscriber's default expense currency (ISO 4217 code), or null if not chosen yet. */
    static String defaultCurrency(Context context) {
        String code = prefs(context).getString(ExpenseTracking.KEY_DEFAULT_CURRENCY, null);
        return (code == null || code.trim().isEmpty()) ? null : code;
    }

    /**
     * Runs {@code then} with the default currency, first asking for it once if it was never chosen.
     * Nothing is preselected; cancelling the choice cancels the action.
     */
    private static void withDefaultCurrency(AppCompatActivity activity, CurrencyAction then) {
        String saved = defaultCurrency(activity);
        if (saved != null) {
            then.run(saved);
            return;
        }
        final List<Currency> currencies = new ArrayList<>(Currency.getAvailableCurrencies());
        Collections.sort(currencies, (a, b) -> a.getCurrencyCode().compareTo(b.getCurrencyCode()));
        String[] labels = new String[currencies.size()];
        for (int i = 0; i < currencies.size(); i++) {
            Currency c = currencies.get(i);
            labels[i] = c.getCurrencyCode() + " – " + c.getDisplayName(Locale.getDefault());
        }
        new AlertDialog.Builder(activity)
                .setTitle("Select your default expense currency")
                .setItems(labels, (dialog, which) -> {
                    String code = currencies.get(which).getCurrencyCode();
                    prefs(activity).edit().putString(ExpenseTracking.KEY_DEFAULT_CURRENCY, code).apply();
                    Toast.makeText(activity, "Default currency set to " + code, Toast.LENGTH_SHORT).show();
                    then.run(code);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private interface CurrencyAction {
        void run(String currency);
    }

    /** Decimal places allowed for the currency (e.g. 2 for GBP, 0 for JPY). */
    private static int decimalsFor(String code) {
        try {
            int digits = Currency.getInstance(code).getDefaultFractionDigits();
            return digits < 0 ? 2 : digits;
        } catch (Exception e) {
            return 2;
        }
    }

    // ── Add New Expense ──────────────────────────────────────────────────────

    static void showAddExpense(AppCompatActivity activity) {
        withDefaultCurrency(activity, currency -> showAddExpenseForm(activity, currency));
    }

    private static void showAddExpenseForm(AppCompatActivity activity, String currency) {
        View view = LayoutInflater.from(activity).inflate(R.layout.add_expense_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(activity).setView(view).create();

        // Month and Day are taken once, when the form opens, and decide the monthly table
        Calendar now = Calendar.getInstance();
        final int month = now.get(Calendar.MONTH);
        final int day = now.get(Calendar.DAY_OF_MONTH);

        ((EditText) view.findViewById(R.id.etExpenseMonth)).setText(ExpenseTracking.monthName(month));
        ((EditText) view.findViewById(R.id.etExpenseDay)).setText(String.valueOf(day));
        ((EditText) view.findViewById(R.id.etExpenseCurrency)).setText(currency);
        EditText etDescription = view.findViewById(R.id.etExpenseDescription);
        EditText etAmount = view.findViewById(R.id.etExpenseAmount);
        TextInputLayout tilDescription = view.findViewById(R.id.tilExpenseDescription);
        TextInputLayout tilAmount = view.findViewById(R.id.tilExpenseAmount);

        view.findViewById(R.id.btnExpenseCancel).setOnClickListener(v -> dialog.dismiss());
        Button btnSave = view.findViewById(R.id.btnExpenseSave);
        btnSave.setOnClickListener(v -> {
            String description = etDescription.getText() == null ? "" : etDescription.getText().toString().trim();
            String descriptionError = ExpenseTracking.descriptionError(description);
            BigDecimal amount = ExpenseTracking.parseAmount(
                    etAmount.getText() == null ? null : etAmount.getText().toString(), decimalsFor(currency));
            tilDescription.setError(descriptionError);
            tilAmount.setError(amount == null ? "Enter a positive amount (max " + decimalsFor(currency)
                    + " decimal places)" : null);
            if (descriptionError != null || amount == null) return;

            btnSave.setEnabled(false); // no double save
            long id = new SQLDatabase_Food_Items_CIF6(activity).insertExpense(month, day, description, amount, currency);
            if (id == -1) {
                btnSave.setEnabled(true);
                Toast.makeText(activity, "Could not save the expense. Please try again.", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(activity, "Expense saved: " + ExpenseTracking.formatMoney(currency, amount)
                    + " (" + ExpenseTracking.monthName(month) + " " + day + ")", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    // ── Show Expense ─────────────────────────────────────────────────────────

    static void showExpenses(AppCompatActivity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("How would you like to view your expenses?")
                .setItems(new String[]{"Summary", "Detailed"}, (dialog, which) -> {
                    if (which == 0) showSummary(activity);
                    else showDetailed(activity);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Current month's expenses, or null after telling the Subscriber the table could not be read. */
    private static List<ExpenseTracking.Entry> currentMonthExpenses(AppCompatActivity activity, int month) {
        List<ExpenseTracking.Entry> entries = new SQLDatabase_Food_Items_CIF6(activity).getExpenses(month);
        if (entries == null) {
            Toast.makeText(activity, "Could not read your expenses. Please try again.", Toast.LENGTH_LONG).show();
        }
        return entries;
    }

    /** Currency shown with the total: the default currency (all entries use it). */
    private static String totalCurrency(AppCompatActivity activity, List<ExpenseTracking.Entry> entries) {
        String saved = defaultCurrency(activity);
        if (saved != null) return saved;
        return entries.isEmpty() ? "" : entries.get(0).currency;
    }

    private static void showSummary(AppCompatActivity activity) {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        List<ExpenseTracking.Entry> entries = currentMonthExpenses(activity, month);
        if (entries == null) return;
        String title = ExpenseTracking.monthName(month) + " Expense Summary";
        String message = entries.isEmpty()
                ? "No expenses recorded for " + ExpenseTracking.monthName(month) + " yet."
                : "Grand Total: " + ExpenseTracking.formatMoney(totalCurrency(activity, entries),
                        ExpenseTracking.total(entries));
        new AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton("OK", null).show();
    }

    private static void showDetailed(AppCompatActivity activity) {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        List<ExpenseTracking.Entry> entries = currentMonthExpenses(activity, month);
        if (entries == null) return;
        String title = ExpenseTracking.monthName(month) + " Expenses";
        if (entries.isEmpty()) {
            new AlertDialog.Builder(activity).setTitle(title)
                    .setMessage("No expenses recorded for " + ExpenseTracking.monthName(month) + " yet.")
                    .setPositiveButton("OK", null).show();
            return;
        }

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_expense_detail, null);
        View header = view.findViewById(R.id.expenseHeader);
        for (int id : new int[]{R.id.tvExpenseDay, R.id.tvExpenseDescription, R.id.tvExpenseAmount, R.id.tvExpenseCurrency}) {
            ((TextView) header.findViewById(id)).setTypeface(null, android.graphics.Typeface.BOLD);
        }
        ListView list = view.findViewById(R.id.lvExpenses);
        list.setAdapter(new ArrayAdapter<ExpenseTracking.Entry>(activity, R.layout.item_expense_row, entries) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView != null ? convertView
                        : LayoutInflater.from(getContext()).inflate(R.layout.item_expense_row, parent, false);
                ExpenseTracking.Entry entry = getItem(position);
                ((TextView) row.findViewById(R.id.tvExpenseDay)).setText(String.valueOf(entry.day));
                ((TextView) row.findViewById(R.id.tvExpenseDescription)).setText(entry.description);
                ((TextView) row.findViewById(R.id.tvExpenseAmount)).setText(
                        entry.amount != null ? ExpenseTracking.formatAmount(entry.amount) : "?");
                ((TextView) row.findViewById(R.id.tvExpenseCurrency)).setText(entry.currency);
                return row;
            }
        });
        ((TextView) view.findViewById(R.id.tvExpenseGrandTotal)).setText("Grand Total: "
                + ExpenseTracking.formatMoney(totalCurrency(activity, entries), ExpenseTracking.total(entries)));

        new AlertDialog.Builder(activity).setTitle(title).setView(view).setPositiveButton("Close", null).show();
    }
}
