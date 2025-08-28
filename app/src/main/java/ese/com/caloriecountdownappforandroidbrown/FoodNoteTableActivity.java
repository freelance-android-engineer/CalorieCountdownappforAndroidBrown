package ese.com.caloriecountdownappforandroidbrown;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Button btnFoodNoteAi = findViewById(R.id.btnFoodNoteAi);

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
                transferAllFoodNotes();
            }
        });


        btnFoodNoteAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAiButtonClick();
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

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String food = etFood.getText().toString().trim();
//                String quantity = etQuantity.getText().toString().trim();
//                String calories = etCalories.getText().toString().trim();
//
//                if (food.isEmpty()) {
//                    Toast.makeText(FoodNoteTableActivity.this, "Food field cannot be empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Insert data into the database
//                databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
//
//                // Add the new row to the table
//                addRowToTable(food, quantity, calories, currentDateTime);
//
//                dialog.dismiss();
//            }
//        });

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

                // Check if food contains "water" (case-insensitive)
                if (food.toLowerCase().contains("water")) {
                    new AlertDialog.Builder(FoodNoteTableActivity.this)
                            .setTitle("Confirm!")
                            .setMessage("Do you want to add this data to your Water Tracker?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    // Launch Water Tracker activity
                                    Intent intent = new Intent(FoodNoteTableActivity.this, AddWaterData.class);
                                    startActivity(intent);

                                    // Close both dialogs
                                    dialog.dismiss();      // closes the current add-food dialog
                                    dialogInterface.dismiss(); // closes the confirmation dialog
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    // Just close the current add-food dialog
                                    databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
                                    addRowToTable(food, quantity, calories, currentDateTime);

                                    dialog.dismiss();
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                } else {
                    // Normal save flow
                    databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
                    addRowToTable(food, quantity, calories, currentDateTime);

                    dialog.dismiss(); // close the input dialog only
                }
            }
        });


        dialog.show();
    }

//    private void addRowToTable(String food, String quantity, String calories, String dateTime) {
//        // Create a new TableRow
//        TableRow row = new TableRow(this);
//
//        // LayoutParams to set margins for the TableRow
//        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT
//        );
//        rowParams.setMargins(0, 10, 0, 10); // Add top and bottom margins
//        row.setLayoutParams(rowParams);
//
//        // Create and configure TextViews for each column
//        TextView tvFood = createColumnTextView(food);
//        TextView tvQuantity = createColumnTextView(quantity);
//        TextView tvCalories = createColumnTextView(calories);
//        TextView tvDateTime = createColumnTextView(dateTime);
//
//        // Add TextViews to the TableRow
//        row.addView(tvFood);
//        row.addView(tvCalories);
//        row.addView(tvQuantity);
//        row.addView(tvDateTime);
//
//        // Add the TableRow to the TableLayout
//        tableLayout.addView(row);
//    }


    private void addRowToTable(String food, String quantity, String calories, String dateTime) {
        TableRow row = new TableRow(this);

        // Checkbox
        CheckBox checkBox = new CheckBox(this);
        row.addView(checkBox);

        // Food
        row.addView(createColumnTextView(food));
        // Calories
        row.addView(createColumnTextView(calories));
        // Quantity
        row.addView(createColumnTextView(quantity));
        // DateTime
        row.addView(createColumnTextView(dateTime));

        tableLayout.addView(row);
    }

    private TextView createColumnTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(10, 5, 10, 5);
        textView.setTextSize(15);
        return textView;
    }

    private void loadFoodNotesFromDatabase() {
        android.util.Log.d("FOOD NOTES", "Loading...");
        Cursor cursor = databaseHelper.getAllFoodNotes();
        if (cursor != null && cursor.moveToFirst()) {
            android.util.Log.d("FOOD NOTES", "Data found");

            // Get column indices safely
            int dateIndex = cursor.getColumnIndex("note_date");
            int foodIndex = cursor.getColumnIndex("note_food");
            int caloriesIndex = cursor.getColumnIndex("note_calories");
            int quantityIndex = cursor.getColumnIndex("note_quantity");
            int transferredIndex = cursor.getColumnIndex("isTransferred");

            if (dateIndex == -1 || foodIndex == -1 || caloriesIndex == -1 ||
                    quantityIndex == -1 || transferredIndex == -1) {
                android.util.Log.d("FOOD NOTES", "DatabaseError One or more column names are invalid.");
                cursor.close();
                return;
            }

            // Iterate through the rows
            do {
                int isTransferred = cursor.getInt(transferredIndex);

                if (isTransferred == 0) {  // Only add if not transferred
                    String dateTime = cursor.getString(dateIndex);
                    String food = cursor.getString(foodIndex);
                    String calories = cursor.getString(caloriesIndex);
                    String quantity = cursor.getString(quantityIndex);

                    android.util.Log.d("FOOD NOTES", "Adding row: " + dateTime + food + calories + quantity);
                    addRowToTable(food, quantity, calories, dateTime);
                }
            } while (cursor.moveToNext());

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

                    int uncertainCalories = calculateExtraCalories(certainty);
                    String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

                    databaseHelper.insertFoodNote(currentDateTime, "Uncertainty", String.valueOf(uncertainCalories), // int → String
                            String.valueOf(1));
                    Toast.makeText(FoodNoteTableActivity.this, "Certainty Saved: " + certainty + ". Extra Points: " + uncertainCalories, Toast.LENGTH_SHORT).show();
                    addRowToTable("Uncertainty", String.valueOf(1), String.valueOf(uncertainCalories), currentDateTime);
                    dialog.dismiss();

                } catch (NumberFormatException e) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public static int calculateExtraCalories(int certaintyPercent) {
        int clamped = Math.max(0, Math.min(100, certaintyPercent));
        int uncertainty = 100 - clamped;
        return 100 * uncertainty;
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


    private void transferAllFoodNotes() {
        Cursor cursor = databaseHelper.getAllFoodNotes();
        if (cursor != null) {
            boolean hasZeroValue = false;

            if (cursor.moveToFirst()) {
                do {
                    int valueIndex = cursor.getColumnIndex("isTransferred"); // replace with actual column name
                    if (valueIndex != -1) {
                        int value = cursor.getInt(valueIndex);
                        if (value == 0) {
                            hasZeroValue = true;
                            break; // no need to check further
                        }
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();

            if (hasZeroValue) {
                databaseHelper.markAllAsTransferred();
                launchCreditView();
            } else {
                Toast.makeText(FoodNoteTableActivity.this, "No notes to transfer", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void launchCreditView() {
        Intent intent = new Intent(FoodNoteTableActivity.this, Food_Diary_Sheet_CIF3.class);
        startActivity(intent);
        finish(); // closes current activity
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor when the activity is destroyed to avoid memory leaks
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    //AI LOGICAL PART
    private void handleAiButtonClick() {
        // Use correct type instead of Object
        ArrayList<Map<String, String>> selectedFoods = new ArrayList<>();

        // Skip index 0 (header row)
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            CheckBox checkBox = (CheckBox) row.getChildAt(0);
            TextView foodText = (TextView) row.getChildAt(1);     // food column
            TextView quantityText = (TextView) row.getChildAt(2); // quantity column

            if (checkBox.isChecked()) {
                Map<String, String> foodMap = new HashMap<>();
                foodMap.put("food", foodText.getText().toString());
                foodMap.put("quantity", quantityText.getText().toString());
                selectedFoods.add(foodMap);
            }
        }

        if (selectedFoods.isEmpty()) {
            showNoSelectionDialog();
        } else {
            // ✅ Now types match
            showSelectedItemsDialog(selectedFoods);
        }
    }


    private void showNoSelectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Items Selected")
                .setMessage("Please select food items to proceed")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showSelectedItemsDialog(List<Map<String, String>> selectedFoods) {
        StringBuilder message = new StringBuilder();
        for (Map<String, String> food : selectedFoods) {
            message.append(food.get("food"))
                    .append(" - ")
                    .append(food.get("quantity"))
                    .append("\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Selected food items to calculate calories")
                .setMessage(message.toString())
                .setPositiveButton("Proceed", (dialog, which) -> {
                    // 👉 Build the prompt
                    String prompt = buildCaloriesPrompt(selectedFoods);

                    // 👉 Call your Gemini API service


                    GeminiApiService.calculateCalories(this, prompt, new GeminiApiService.CalorieCallback() {
                        @Override
                        public void onResult(String result) {
                            runOnUiThread(() -> {
                                if (result != null) {
                                    showCaloriesResult(result);
                                } else {
                                    Toast.makeText(FoodNoteTableActivity.this, "Failed to calculate calories", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                })
                .setNegativeButton("Select More", (dialog, which) -> dialog.dismiss())
                .show();
    }


    private String buildCaloriesPrompt(List<Map<String, String>> foods) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calculate the calories for the following foods:\n\n");

        for (Map<String, String> food : foods) {
            String name = food.getOrDefault("food", "");
            String qty = food.getOrDefault("quantity", "").trim();

            if (!qty.isEmpty()) {
                sb.append("- ").append(name)
                        .append(", Quantity: ").append(qty).append("\n");
            } else {
                sb.append("- ").append(name)
                        .append(" (assume 100g serving)").append("\n");
            }
        }

        sb.append("\nFor each item, provide estimated calories. If quantity is missing, assume per 100g.\n");
        sb.append("Finally, provide the total calories.\n");

        return sb.toString();
    }

    private void showCaloriesResult(String response) {
        String message;

        if (response != null && response.toLowerCase().contains("calorie")) {
            // Clean / format response if needed
            message = response.trim();
        } else {
            message = "Something went wrong. Please try again.";
        }

        // Show dialog
        new AlertDialog.Builder(this)
                .setTitle("Calorie Estimation")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }


}
