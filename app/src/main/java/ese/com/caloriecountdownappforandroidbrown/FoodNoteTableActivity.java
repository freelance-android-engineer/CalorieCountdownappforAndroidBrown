package ese.com.caloriecountdownappforandroidbrown;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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
    private SQLDatabase_Food_Items_CIF6 databaseHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_note_table);
        tableLayout = findViewById(R.id.tableLayout);
        databaseHelper = new SQLDatabase_Food_Items_CIF6(this);
        Button btnAddNewRow = findViewById(R.id.btnAddNewRow);
        Button btnEditNote = findViewById(R.id.btnEditRow);
        Button btnAddCertainty = findViewById(R.id.btnAddCertainty);
        Button btnSumCalories = findViewById(R.id.btnSumCalories);
        Button btnInputNote = findViewById(R.id.btnInputNote);
        Button btnTransferCredit = findViewById(R.id.btnTransferCredit);
        Button btnFoodNoteAi = findViewById(R.id.btnFoodNoteAi);
        Button btnDeleteFoodNote = findViewById(R.id.btnDeleteNote);
        Button btnKitty = findViewById(R.id.btnKitty);

        loadFoodNotesFromDatabase();

        btnAddNewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoodInputDialog(null, "", "", "");
            }
        });

        btnAddCertainty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCertaintyDialog();
            }
        });

        btnSumCalories.setOnClickListener(v -> {
            // First check if any notes are selected
            int selectedCalories = sumSelectedNotes();

            if (selectedCalories != -1) {
                // If notes are selected, use the sum of selected notes only
                Toast.makeText(FoodNoteTableActivity.this, "Total calories (selected): " + selectedCalories, Toast.LENGTH_SHORT).show();
                showTotalCaloriesDialog(selectedCalories);
            } else {
                // Otherwise, use the existing logic (sum all where is_transferred = 0)
                executorService.submit(() -> {
                    int totalCalories = databaseHelper.getTotalCalories();
                    runOnUiThread(() -> {
                        Toast.makeText(FoodNoteTableActivity.this, "Total calories: " + totalCalories, Toast.LENGTH_SHORT).show();
                        showTotalCaloriesDialog(totalCalories);
                    });
                });
            }
        });

        btnInputNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodNoteTableActivity.this, "Input Food/Drink Note Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAiButtonClick(false, true);
            }
        });

        btnTransferCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferAllFoodNotes();
            }
        });


        btnFoodNoteAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAiButtonClick(true, false);
            }
        });

        btnDeleteFoodNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAiButtonClick(false, false);
            }
        });
        btnKitty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleKittyButtonClick();
            }
        });
    }


    private void showFoodInputDialog(String noteId, String food, String quantity, String calories) {
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

        etFood.setText(food);
        etQuantity.setText(quantity);
        etCalories.setText(calories);

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

                // === Update case ===
                if (noteId != null) {
                    databaseHelper.updateFoodNote(
                            Integer.parseInt(noteId),
                            currentDateTime,
                            food,
                            calories,
                            quantity
                    );
                    updateRowInTable(Integer.parseInt(noteId), food, quantity, calories, currentDateTime);
                    dialog.dismiss();
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
                                    Intent intent = new Intent(FoodNoteTableActivity.this, AddWaterData.class);
                                    startActivity(intent);

                                    dialog.dismiss();
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    long insertedId = databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
                                    addRowToTable((int) insertedId, food, quantity, calories, currentDateTime);

                                    dialog.dismiss();
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                } else {
                    long insertedId = databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
                    addRowToTable((int) insertedId, food, quantity, calories, currentDateTime);

                    dialog.dismiss();
                }
            }
        });


        dialog.show();
    }

    private void addRowToTable(int noteId, String food, String quantity, String calories, String dateTime) {
        TableRow row = new TableRow(this);
        row.setTag(noteId);

        // 1. Add Checkbox (Index 0)
        CheckBox checkBox = new CheckBox(this);
        row.addView(checkBox);

        // 2. Add new S. No. column (Index 1)
        // The serial number is the current number of rows (including the header)
        // which equals the correct next number.
        int serialNumber = tableLayout.getChildCount();
        row.addView(createColumnTextView(String.valueOf(serialNumber)));

        // 3. Add the rest of the columns at their new indices
        row.addView(createColumnTextView(food));      // Index 2
        row.addView(createColumnTextView(calories));  // Index 3
        row.addView(createColumnTextView(quantity));  // Index 4
        row.addView(createColumnTextView(dateTime));  // Index 5

        tableLayout.addView(row);
    }

    private void updateRowInTable(int noteId, String food, String quantity, String calories, String dateTime) {
        for (int i = 1; i < tableLayout.getChildCount(); i++) { // start from 1 to skip header
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            if (row.getTag() != null && row.getTag().toString().equals(String.valueOf(noteId))) {
                // The indices must be updated to account for the new "S. No." column.
                // childAt(0) -> CheckBox
                // childAt(1) -> S. No. (We don't need to update this)
                ((TextView) row.getChildAt(2)).setText(food);      // Food
                ((TextView) row.getChildAt(3)).setText(calories);  // Calories
                ((TextView) row.getChildAt(4)).setText(quantity);  // Quantity
                ((TextView) row.getChildAt(5)).setText(dateTime);  // DateTime
                break;
            }
        }
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
        Cursor cursor = databaseHelper.getAllFoodNotes();
        if (cursor != null && cursor.moveToFirst()) {
            int noteId = cursor.getColumnIndex("note_id");
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

            do {
                int isTransferred = cursor.getInt(transferredIndex);

                if (isTransferred == 0) {
                    long id = cursor.getLong(noteId);
                    String dateTime = cursor.getString(dateIndex);
                    String food = cursor.getString(foodIndex);
                    String calories = cursor.getString(caloriesIndex);
                    String quantity = cursor.getString(quantityIndex);
                    addRowToTable((int) id, food, quantity, calories, dateTime);
                }
            } while (cursor.moveToNext());
            cursor.close();
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

                    long insertedId = databaseHelper.insertFoodNote(currentDateTime, "Uncertainty", String.valueOf(uncertainCalories), // int → String
                            String.valueOf(1));
                    Toast.makeText(FoodNoteTableActivity.this, "Certainty Saved: " + certainty + ". Extra Points: " + uncertainCalories, Toast.LENGTH_SHORT).show();
                    addRowToTable((int) insertedId, "Uncertainty", String.valueOf(1), String.valueOf(uncertainCalories), currentDateTime);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_sum_calories);

        AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvTotalCalories = dialog.findViewById(R.id.tvTotalCalories);
        if (tvTotalCalories != null) {
            tvTotalCalories.setText(String.valueOf(totalCalories));
        }
        Button btnOk = dialog.findViewById(R.id.btnOk);
        if (btnOk != null) {
            btnOk.setOnClickListener(v1 -> dialog.dismiss());
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

    private void handleAiButtonClick(boolean isAi, boolean isEditRow) {
        android.util.Log.d("FoodNoteAI", "handleAiButtonClick called - isAi=" + isAi + ", isEditRow=" + isEditRow);

        ArrayList<Map<String, String>> selectedFoods = new ArrayList<>();

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            CheckBox checkBox = (CheckBox) row.getChildAt(0);
            TextView foodText = (TextView) row.getChildAt(2);     // food column
            TextView caloriesText = (TextView) row.getChildAt(3); // quantity column
            TextView quantityText = (TextView) row.getChildAt(4); // quantity column


            if (checkBox.isChecked()) {
                Map<String, String> foodMap = new HashMap<>();
                foodMap.put("note_id", String.valueOf(row.getTag()));
                foodMap.put("food", foodText.getText().toString());
                foodMap.put("quantity", quantityText.getText().toString());
                foodMap.put("calories", caloriesText.getText().toString());
                selectedFoods.add(foodMap);
                android.util.Log.d("FoodNoteAI", "Selected: " + foodText.getText().toString());
            }
        }

        android.util.Log.d("FoodNoteAI", "Total selected items: " + selectedFoods.size());

        if (selectedFoods.isEmpty()) {
            showNoSelectionDialog();
        } else if (isEditRow) {
            if (selectedFoods.size() > 1) {
                Toast.makeText(this, "Please select only one food note to edit", Toast.LENGTH_SHORT).show();
            } else {
                // Exactly one row selected → pre-populate dialog
                Map<String, String> selectedFood = selectedFoods.get(0);
                showFoodInputDialog(
                        selectedFood.get("note_id"),
                        selectedFood.get("food"),
                        selectedFood.get("quantity"),
                        selectedFood.get("calories")
                );
            }
        } else {
            showSelectedItemsDialog(isAi, selectedFoods);
        }
    }


    private void showNoSelectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Items Selected")
                .setMessage("Please select food note(s) to proceed")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showSelectedItemsDialog(boolean isAi, List<Map<String, String>> selectedFoods) {
        StringBuilder message = new StringBuilder();
        for (Map<String, String> food : selectedFoods) {
            message.append(food.get("food"))
                    .append(" - ")
                    .append(food.get("quantity"))
                    .append("\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Selected food notes to " + (isAi ? "calculate calories" : "delete"))
                .setMessage(message.toString())
                .setPositiveButton("Proceed", (dialog, which) -> {

                    if (isAi) {
                        // Show loading indicator
                        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(this);
                        progressDialog.setMessage("Calculating calories with AI...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        String prompt = buildCaloriesPrompt(selectedFoods);
                        android.util.Log.d("FoodNoteAI", "Starting AI calculation for " + selectedFoods.size() + " items");

                        GeminiApiService.calculateCalories(this, prompt, new GeminiApiService.CalorieCallback() {
                            @Override
                            public void onResult(String result) {
                                runOnUiThread(() -> {
                                    progressDialog.dismiss();
                                    if (result != null && !result.trim().isEmpty()) {
                                        android.util.Log.d("FoodNoteAI", "AI result received: " + result);
                                        showCaloriesResult(result);
                                    } else {
                                        android.util.Log.e("FoodNoteAI", "AI returned null or empty result");
                                        Toast.makeText(FoodNoteTableActivity.this, "Failed to calculate calories. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    } else {
                        List<Integer> selectedIds = new ArrayList<>();
                        for (Map<String, String> food : selectedFoods) {
                            String idStr = food.get("note_id");
                            if (idStr != null) {
                                try {
                                    selectedIds.add(Integer.parseInt(idStr));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (!selectedIds.isEmpty()) {
                            databaseHelper.deleteFoodNotes(selectedIds);
                            Toast.makeText(FoodNoteTableActivity.this, "Deleted " + selectedIds.size() + " items", Toast.LENGTH_SHORT).show();
                            removeRowsByIds(selectedIds);
                        }
                    }
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
            message = response.trim();
        } else {
            message = "Something went wrong. Please try again.";
        }

        new AlertDialog.Builder(this)
                .setTitle("Calorie Estimation")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void removeRowsByIds(List<Integer> idsToDelete) {
        List<View> rowsToRemove = new ArrayList<>();
        for (int i = 1; i < tableLayout.getChildCount(); i++) { // skip header
            View row = tableLayout.getChildAt(i);
            Object tag = row.getTag();
            if (tag instanceof Integer && idsToDelete.contains((Integer) tag)) {
                rowsToRemove.add(row);
            }
        }
        for (View row : rowsToRemove) {
            tableLayout.removeView(row);
        }
        renumberTableRows();
    }

    private void renumberTableRows() {
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            TextView snoTextView = (TextView) row.getChildAt(1);
            if (snoTextView != null) {
                snoTextView.setText(String.valueOf(i));
            }
        }
    }

    private void handleKittyButtonClick() {
        int totalCalories = databaseHelper.getTotalCalories();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        String userGender = pref.getString("user_gender", null);
        if (userGender == null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_gender", "female");
            editor.apply();
            userGender = "female";
        }

        int budget = userGender.equalsIgnoreCase("female") ? 2000 : 2500;
        int result = budget - totalCalories;
        String message;

        if (result > 0) {
            message = "You have " + result + " Calories left in your Kitty.";
        } else if (result == 0) {
            message = "You have 0 Calories left in your Kitty.";
        } else {
            int stepChallenge = StepChallengeFun(Math.abs(result)); // placeholder function
            message = "You have 0 Calories left in your Kitty and have the additional challenge of doing "
                    + stepChallenge + " steps by 7 PM or midnight.";
        }

        new AlertDialog.Builder(this)
                .setTitle("Kitty Report")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private int StepChallengeFun(int calories) {
        // TODO: Implement your logic later
        return calories * 20; // Example placeholder conversion
    }

    /**
     * Calculates the sum of calories for only the selected food notes (checked checkboxes)
     * @return total calories of selected notes, or -1 if no notes are selected
     */
    private int sumSelectedNotes() {
        int totalCalories = 0;
        boolean hasSelection = false;

        // Iterate through table rows (skip header at index 0)
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            // Get checkbox (index 0) and calories (index 3)
            CheckBox checkBox = (CheckBox) row.getChildAt(0);
            TextView caloriesText = (TextView) row.getChildAt(3);

            if (checkBox.isChecked()) {
                hasSelection = true;
                String caloriesStr = caloriesText.getText().toString().trim();

                // Parse calories, handle empty or invalid values
                if (!caloriesStr.isEmpty()) {
                    try {
                        int calories = Integer.parseInt(caloriesStr);
                        totalCalories += calories;
                    } catch (NumberFormatException e) {
                        android.util.Log.w("FoodNote", "Invalid calories value: " + caloriesStr);
                    }
                }
            }
        }

        // Return -1 if no selection, otherwise return the sum
        return hasSelection ? totalCalories : -1;
    }


}
