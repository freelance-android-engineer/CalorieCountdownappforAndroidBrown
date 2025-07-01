package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FoodNoteTableActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private SQLDatabase_Food_Items_CIF6 databaseHelper; // Assuming you have a DatabaseHelper class
   private ExecutorService executorService = Executors.newSingleThreadExecutor(); // Initializes a single-thread executor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_note_table);

        // Bind views
        tableLayout = findViewById(R.id.tableLayout);
        databaseHelper = new SQLDatabase_Food_Items_CIF6(this); // Initialize the database helper
        Button btnAddNewRow = findViewById(R.id.btnAddNewRow);
        Button btnAddCertainty = findViewById(R.id.btnAddCertainty);
        Button btnSumCalories = findViewById(R.id.btnSumCalories);
        Button btnInputNote = findViewById(R.id.btnInputNote);
        Button btnTransferCredit = findViewById(R.id.btnTransferCredit);

        // Load existing data into the table
        loadFoodNotesFromDatabase();

        // Add New Row Button
        btnAddNewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoodInputDialog();
            }
        });

        // Add Certainty Button
        btnAddCertainty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCertaintyDialog();
            }
        });

        btnSumCalories.setOnClickListener(v -> {
            // Run the database query in a background thread
            executorService.submit(() -> {
                int totalCalories = databaseHelper.getTotalCalories();  // Get total calories from the database

                // Update UI on the main thread
                runOnUiThread(() -> {
                    Toast.makeText(FoodNoteTableActivity.this, "Total calories" + totalCalories, Toast.LENGTH_SHORT).show();
                    showTotalCaloriesDialog(totalCalories);
                });
            });
        });

        // Input Food/Drink Note Button
        btnInputNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodNoteTableActivity.this, "Input Food/Drink Note Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Transfer to Credit Button
        btnTransferCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodNoteTableActivity.this, "Transfer to Credit Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFoodInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_row_food_note_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        EditText etFood = view.findViewById(R.id.etFood);
        EditText etQuantity = view.findViewById(R.id.etQuantity);
        EditText etCalories = view.findViewById(R.id.etCalories);
        TextView tvDateTime = view.findViewById(R.id.tvDateTime);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
        tvDateTime.setText("Date & Time: " + currentDateTime);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = etFood.getText().toString().trim();
                String quantity = etQuantity.getText().toString().trim();
                String calories = etCalories.getText().toString().trim();

                if (food.isEmpty()) {
                    Toast.makeText(FoodNoteTableActivity.this, "Food field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert data into the database
                databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);

                // Add the new row to the table
                addRowToTable(food, quantity, calories, currentDateTime);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addRowToTable(String food, String quantity, String calories, String dateTime) {
        // Create a new TableRow
        TableRow row = new TableRow(this);

        // LayoutParams to set margins for the TableRow
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        rowParams.setMargins(0, 10, 0, 10); // Add top and bottom margins
        row.setLayoutParams(rowParams);

        // Create and configure TextViews for each column
        TextView tvFood = createColumnTextView(food);
        TextView tvQuantity = createColumnTextView(quantity);
        TextView tvCalories = createColumnTextView(calories);
        TextView tvDateTime = createColumnTextView(dateTime);

        // Add TextViews to the TableRow
        row.addView(tvFood);
        row.addView(tvCalories);
        row.addView(tvQuantity);
        row.addView(tvDateTime);

        // Add the TableRow to the TableLayout
        tableLayout.addView(row);
    }

    // Helper method to create a TextView with specific properties
    private TextView createColumnTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(
                0, // Set width to 0dp for weight distribution
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Set weight for equal distribution
        ));
        textView.setEllipsize(TextUtils.TruncateAt.END); // Truncate long text with "..."
        textView.setMaxLines(3); // Allow up to 3 lines of text
        textView.setPadding(2, 5, 2, 5); // Optional padding inside each cell
//        textView.setTextColor(getResources().getColor(android.R.color.black)); // Set text color
        textView.setTextSize(15); // Set text size
        return textView;
    }


    private void loadFoodNotesFromDatabase() {
        android.util.Log.d("FOOD NOTES", "Loading...");
        Cursor cursor = databaseHelper.getAllFoodNotes();
        android.util.Log.d("FOOD NOTES", "Loading");
        if (cursor != null && cursor.moveToFirst()) {
            android.util.Log.d("FOOD NOTES", "Data found");
            // Get column indices safely
            int dateIndex = cursor.getColumnIndex("note_date");
            int foodIndex = cursor.getColumnIndex("note_food");
            int caloriesIndex = cursor.getColumnIndex("note_calories");
            int quantityIndex = cursor.getColumnIndex("note_quantity");

            if (dateIndex == -1 || foodIndex == -1 || caloriesIndex == -1 || quantityIndex == -1) {
                // Log an error if any column index is invalid
                android.util.Log.d("FOOD NOTES", "DatabaseError One or more column names are invalid. Please check the database schema.");
                cursor.close();
                return;
            }

            // Iterate through the rows
            do {
                String dateTime = cursor.getString(dateIndex);
                String food = cursor.getString(foodIndex);
                String calories = cursor.getString(caloriesIndex);
                String quantity = cursor.getString(quantityIndex);
                android.util.Log.d("FOOD NOTES", "Data" + dateTime + food + calories + quantity);
                // Add the data to the table
                addRowToTable(food, quantity, calories, dateTime);
            } while (cursor.moveToNext());
            // Close the cursor to release resources
            cursor.close();
        } else {
            android.util.Log.d("FOOD NOTES", "DatabaseWarning No data found in the food notes table or cursor is null.");
        }
    }


    private void showCertaintyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_certainty_input, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        EditText etCertainty = view.findViewById(R.id.etCertainty);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String certaintyText = etCertainty.getText().toString();

                if (certaintyText.isEmpty()) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter a certainty value.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int certainty = Integer.parseInt(certaintyText);

                    if (certainty > 100) {
                        Toast.makeText(FoodNoteTableActivity.this, "Certainty value cannot be more than 100.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int extraPoints = 0; // Initialize extra points
                    if (certainty == 50) {
                        extraPoints = 1000;
                    } else if (certainty == 90) {
                        extraPoints = 200;
                    } else if (certainty < 50) {
                        extraPoints = 1500;
                    } else if (certainty == 100) {
                        extraPoints = 0;
                    }

                    Toast.makeText(FoodNoteTableActivity.this, "Certainty Saved: " + certainty + ". Extra Points: " + extraPoints, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                } catch (NumberFormatException e) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    // Function to show the dialog with the total calories
    public void showTotalCaloriesDialog(int totalCalories) {
        // Create an instance of the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_sum_calories);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvTotalCalories = dialog.findViewById(R.id.tvTotalCalories);
        if (tvTotalCalories != null) {
            tvTotalCalories.setText(String.valueOf(totalCalories));  // Explicitly convert to string// Update with the calculated value
        }
        Button btnOk = dialog.findViewById(R.id.btnOk);
        if (btnOk != null) {
            btnOk.setOnClickListener(v1 -> dialog.dismiss());  // Close the dialog when OK is clicked
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor when the activity is destroyed to avoid memory leaks
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }}
}
