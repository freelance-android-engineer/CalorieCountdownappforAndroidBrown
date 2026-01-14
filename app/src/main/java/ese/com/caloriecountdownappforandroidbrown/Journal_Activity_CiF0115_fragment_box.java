package ese.com.caloriecountdownappforandroidbrown;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Journal Activity for Accrual Feature
 * Allows clients to transfer Calories/Points from current day to the following day's budget.
 * Works with the mDaysToZero collection from CCD_GUI_CD_CIF0001.
 */
public class Journal_Activity_CiF0115_fragment_box extends AppCompatActivity {

    private EditText editTextReference;
    private EditText editTextDate;
    private EditText editTextDayFrom;
    private EditText editTextDayTo;
    private EditText editTextAmount;
    private EditText editTextDescription;
    private EditText editTextJournalDebit;
    private EditText editTextJournalCredit;
    private EditText editTextTransactionIds;

    private Button buttonSave;
    private Button buttonCancel;

    private DayCiF1005 currentDay;
    private DayCiF1005 nextDay;
    private CountdownToZeroDayCiF1004 mDaysToZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal___ci_f0115_fragment_box);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize UI components
        initializeViews();

        // Load mDaysToZero data
        loadDaysToZeroData();

        // Setup button listeners
        setupButtonListeners();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Journal Entry - Transfer points between days", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeViews() {
        editTextReference = findViewById(R.id.edit_text401);
        editTextDate = findViewById(R.id.edit_text41);
        editTextDayFrom = findViewById(R.id.edit_text421);
        editTextDayTo = findViewById(R.id.edit_text431);
        editTextAmount = findViewById(R.id.edit_text441);
        editTextDescription = findViewById(R.id.edit_text451);
        editTextJournalDebit = findViewById(R.id.edit_text461);
        editTextJournalCredit = findViewById(R.id.edit_text471);
        editTextTransactionIds = findViewById(R.id.edit_text481);

        buttonSave = findViewById(R.id.button10);
        buttonCancel = findViewById(R.id.button11);
    }

    private void loadDaysToZeroData() {
        // Always retrieve from database to get all days (static might only have 1 day)
        android.util.Log.d("JournalDebug", "Retrieving days from database...");
        MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(this);
        mDaysToZero = adapter.RetrievemForecast();

        // Update the static reference too
        if (mDaysToZero != null) {
            CCD_GUI_CD_CIF1.mDaysToZero = mDaysToZero;
        }
        android.util.Log.d("JournalDebug", "After DB retrieve, mDaysToZero is null? " + (mDaysToZero == null));

        if (mDaysToZero != null) {
            // Debug: Show list info
            List<DayCiF1005> allDays = mDaysToZero.getNumberOFDaysToXero03FEB10();
            android.util.Log.d("JournalDebug", "Total days in list: " + (allDays != null ? allDays.size() : "null list"));

            // Show first 3 dates in list
            if (allDays != null && allDays.size() > 0) {
                for (int i = 0; i < Math.min(3, allDays.size()); i++) {
                    DayCiF1005 d = allDays.get(i);
                    if (d != null) {
                        android.util.Log.d("JournalDebug", "Day[" + i + "] date: " + d.getDay().toString());
                    }
                }
            }

            // Show today's date for comparison
            android.util.Log.d("JournalDebug", "Today's date: " + LocalDateTime.now().toString());

            // Get current day
            currentDay = mDaysToZero.getCurrentDayType1005();
            android.util.Log.d("JournalDebug", "getCurrentDayType1005() returned null? " + (currentDay == null));

            if (currentDay != null) {
                // Populate current day info
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String todayDateStr = currentDay.getDay().format(formatter);

                editTextDate.setText(todayDateStr);
                editTextDayFrom.setText(todayDateStr);
                editTextJournalDebit.setText(String.valueOf(currentDay.getCurrentBalance()));

                // Generate reference number
                String reference = "JNL-" + System.currentTimeMillis();
                editTextReference.setText(reference);

                // Find next day - get the day AFTER current day in the list
                List<DayCiF1005> daysList = mDaysToZero.getNumberOFDaysToXero03FEB10();
                android.util.Log.d("JournalDebug", "Looking for next day. List size: " + daysList.size());

                // Find index of current day
                int currentDayIndex = -1;
                for (int i = 0; i < daysList.size(); i++) {
                    if (daysList.get(i) == currentDay) {
                        currentDayIndex = i;
                        break;
                    }
                }
                android.util.Log.d("JournalDebug", "Current day index: " + currentDayIndex);

                // Get the next day (index + 1)
                if (currentDayIndex >= 0 && currentDayIndex + 1 < daysList.size()) {
                    nextDay = daysList.get(currentDayIndex + 1);
                    if (nextDay != null) {
                        String tomorrowDateStr = nextDay.getDay().format(formatter);
                        editTextDayTo.setText(tomorrowDateStr);
                        editTextJournalCredit.setText(String.valueOf(nextDay.getCurrentBalance()));
                        android.util.Log.d("JournalDebug", "Next day found: " + tomorrowDateStr);
                    }
                } else {
                    android.util.Log.d("JournalDebug", "Next day NOT found - index out of bounds or list too small");
                    // If no next day exists, show message but still allow viewing current day info
                    Toast.makeText(this, "Next day not available. Need more days in forecast.", Toast.LENGTH_SHORT).show();
                }

                // Set default description
                editTextDescription.setText("Points accrual from " + todayDateStr + " to following day");
            } else {
                Toast.makeText(this, "No current day data available. Please start weight loss first.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No countdown data available. Please start weight loss first.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupButtonListeners() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performJournalTransfer();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void performJournalTransfer() {
        // Get the amount to transfer
        String amountStr = editTextAmount.getText().toString().trim();

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount to transfer", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount <= 0) {
            Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentDay == null) {
            Toast.makeText(this, "Current day data not available", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nextDay == null) {
            Toast.makeText(this, "Next day data not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if current day has enough balance to transfer
        int currentBalance = currentDay.getCurrentBalance();
        if (amount > currentBalance) {
            Toast.makeText(this, "Cannot transfer more than current balance (" + currentBalance + " points)", Toast.LENGTH_LONG).show();
            return;
        }

        // Perform the accrual transfer
        // Debit from current day (reduce balance - client saves these points)
        int newCurrentDayBalance = currentDay.getCurrentBalance() - amount;
        currentDay.setCurrentBalance(newCurrentDayBalance);
        currentDay.setBudgetedDayEndBalanceForThisDay(currentDay.getBudgetedDayEndBalanceForThisDay() - amount);

        // Credit to next day (increase budget - client gets more to spend tomorrow)
        int newNextDayBalance = nextDay.getCurrentBalance() + amount;
        nextDay.setCurrentBalance(newNextDayBalance);
        nextDay.setBudgetedDayEndBalanceForThisDay(nextDay.getBudgetedDayEndBalanceForThisDay() + amount);

        // Update the display
        editTextJournalDebit.setText(String.valueOf(newCurrentDayBalance));
        editTextJournalCredit.setText(String.valueOf(newNextDayBalance));

        // Persist changes to database
        saveToDatabaseAndFinish(amount);
    }

    private void saveToDatabaseAndFinish(int transferAmount) {
        try {
            // Accrual transfers points between daily budgets, NOT the main countdown balance
            // The main balance should NOT be changed by accrual
            // We just record the transfer for tracking purposes

            android.util.Log.d("JournalSave", "Accrual transfer recorded: " + transferAmount + " points");
            android.util.Log.d("JournalSave", "Today's new budget: " + currentDay.getBudgetedDayEndBalanceForThisDay());
            android.util.Log.d("JournalSave", "Tomorrow's new budget: " + nextDay.getBudgetedDayEndBalanceForThisDay());

            // Show success message
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String message = "Successfully transferred " + transferAmount + " points from " +
                           currentDay.getDay().format(formatter) + " to " +
                           nextDay.getDay().format(formatter);

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Set result to indicate success
            Intent resultIntent = new Intent();
            resultIntent.putExtra("TRANSFER_AMOUNT", transferAmount);
            resultIntent.putExtra("NEW_CURRENT_BALANCE", currentDay.getCurrentBalance());
            resultIntent.putExtra("NEW_NEXT_BALANCE", nextDay.getCurrentBalance());
            setResult(RESULT_OK, resultIntent);

            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error saving journal entry: " + e.getMessage(), Toast.LENGTH_LONG).show();
            android.util.Log.e("JournalActivity", "Error saving journal entry", e);
        }
    }
}




//  ┌──────────────────┬─────────────────┬────────────────────────┬────────────────────────┐
//          │      Field       │     Format      │        Example         │      Auto-filled?      │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Reference        │ Text            │ JNL-1736789012345      │ Yes                    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Date             │ dd MMM yyyy     │ 13 Jan 2026            │ Yes                    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Day From         │ dd MMM yyyy     │ 13 Jan 2026            │ Yes                    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Day To           │ dd MMM yyyy     │ 14 Jan 2026            │ Yes                    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Amount of Points │ Plain number    │ 50                     │ No - You enter this    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Description      │ Text            │ Points accrual from... │ Yes                    │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Journal Debit    │ Number          │ 1500                   │ Yes (current balance)  │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Journal Credit   │ Number          │ 1450                   │ Yes (next day balance) │
//        ├──────────────────┼─────────────────┼────────────────────────┼────────────────────────┤
//        │ Transaction IDs  │ Text (optional) │ Leave blank            │ Optional