package ese.com.caloriecountdownappforandroidbrown;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FoodNoteTableActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private SQLDatabase_Food_Items_CIF6 databaseHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // SharedPreferences constants
    private static final String PREFS_NAME = "FoodNotePrefs";
    private static final String PREF_LAST_SELECTED_DATE = "last_selected_date";
    private static final String PREF_SELECTED_DATE_TIMESTAMP = "selected_date_timestamp";

    private TextView tvDateRangeTitle;

    // Camera and Gallery constants
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;
    private static final int REQUEST_CAMERA_CAPTURE = 102;
    private static final int REQUEST_GALLERY_PICK = 103;

    // Memo Camera and Gallery constants
    private static final int REQUEST_MEMO_CAMERA_PERMISSION = 200;
    private static final int REQUEST_MEMO_CAMERA_CAPTURE = 201;
    private static final int REQUEST_MEMO_GALLERY_PICK = 202;

    // Memo panel views
    private EditText etMemoText;
    private ImageView ivMemoImagePreview;
    private ImageView ivMemoAddImage;
    private Button btnMemoSave;
    private Button btnMemoClear;
    private String memoImageBase64 = null;

    // Dialog components for image upload
    private AlertDialog imageUploadDialog;
    private ImageView dialogImageView;
    private TextView dialogOverlayText;
    private Button dialogScanButton;
    private ProgressBar dialogProgressBar;
    private TextView dialogStatusText;
    private String currentImageBase64 = null;
    private FoodDetectionService foodDetectionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_note_table);
        tableLayout = findViewById(R.id.tableLayout);
        databaseHelper = new SQLDatabase_Food_Items_CIF6(this);
        foodDetectionService = new FoodDetectionService(this, databaseHelper);

        Button btnAddNewRow = findViewById(R.id.btnAddNewRow);
        Button btnAddRowWithImage = findViewById(R.id.btnAddRowWithImage);
        Button btnEditNote = findViewById(R.id.btnEditRow);
        Button btnAddCertainty = findViewById(R.id.btnAddCertainty);
        Button btnSumCalories = findViewById(R.id.btnSumCalories);
        Button btnInputNote = findViewById(R.id.btnInputNote);
        Button btnTransferCredit = findViewById(R.id.btnTransferCredit);
        Button btnFoodNoteAi = findViewById(R.id.btnFoodNoteAi);
        Button btnDeleteFoodNote = findViewById(R.id.btnDeleteNote);
        Button btnSaveNotes = findViewById(R.id.btnSaveNotes);
        Button nearestAi = findViewById(R.id.nearestAi);
        Button btnKitty = findViewById(R.id.btnKitty);

        // Initialize date range title
        tvDateRangeTitle = findViewById(R.id.tvDateRangeTitle);

        // Initialize edit icon for date selection
        ImageView ivEditDate = findViewById(R.id.ivEditDate);
        ivEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMandatoryDatePickerDialog();
            }
        });

        // Check if user needs to select date and show mandatory dialog
        checkAndShowMandatoryDatePicker();

        loadFoodNotesFromDatabase();

        btnAddNewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoodInputDialog(null, "", "", "");
            }
        });

        btnAddRowWithImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRowWithImageDialog();
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

        btnSaveNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveNotesButtonClick();
            }
        });

        // Nearest AI button - opens the closest AI app (ChatGPT, Gemini, etc.)
        nearestAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNearestAiApp();
            }
        });

        btnKitty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleKittyButtonClick();
            }
        });

        // Initialize Memo Panel views
        initializeMemoPanel();
    }

    // ========== MEMO PANEL LOGIC ==========

    /**
     * Initialize memo panel views and set up click listeners
     */
    private void initializeMemoPanel() {
        etMemoText = findViewById(R.id.etMemoText);
        ivMemoImagePreview = findViewById(R.id.ivMemoImagePreview);
        ivMemoAddImage = findViewById(R.id.ivMemoAddImage);
        btnMemoSave = findViewById(R.id.btnMemoSave);
        btnMemoClear = findViewById(R.id.btnMemoClear);

        // Image icon click - shows dialog with Camera/Gallery options
        ivMemoAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemoImageSourceDialog();
            }
        });

        // Image preview click - also shows dialog to change image
        ivMemoImagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemoImageSourceDialog();
            }
        });

        // Save button - saves memo with FIFO logic (max 10)
        btnMemoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
            }
        });

        // Clear button - clears memo text and image
        btnMemoClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMemoPanel();
            }
        });

        // Load the last added memo (if any)
        loadLastMemo();
    }

    /**
     * Get the current food note title from the UI (tvDateRangeTitle)
     * @return The current food note title string, or empty string if not available
     */
    private String getCurrentFoodNoteTitle() {
        if (tvDateRangeTitle != null) {
            String title = tvDateRangeTitle.getText().toString();
            android.util.Log.d("MEMO", "Current food note title: " + title);
            return title;
        }
        return "";
    }

    /**
     * Load and display the memo associated with the current food note title.
     * If no memo exists for this title, shows empty state.
     */
    private void loadLastMemo() {
        String currentTitle = getCurrentFoodNoteTitle();
        android.util.Log.d("MEMO", "loadLastMemo called with title: " + currentTitle);

        // Clear current memo state first
        clearMemoPanelWithoutToast();

        // Try to load memo for current title
        Cursor cursor = databaseHelper.getMemoByTitle(currentTitle);
        if (cursor != null && cursor.moveToFirst()) {
            // Get column indices
            int textIndex = cursor.getColumnIndex("memo_text");
            int imageIndex = cursor.getColumnIndex("memo_image");

            if (textIndex != -1) {
                String memoText = cursor.getString(textIndex);
                if (memoText != null && !memoText.isEmpty()) {
                    etMemoText.setText(memoText);
                }
            }

            if (imageIndex != -1) {
                String imageBase64 = cursor.getString(imageIndex);
                if (imageBase64 != null && !imageBase64.isEmpty()) {
                    // Convert base64 to bitmap and display
                    try {
                        byte[] decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT);
                        Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        if (bitmap != null) {
                            ivMemoAddImage.setVisibility(View.GONE);
                            ivMemoImagePreview.setImageBitmap(bitmap);
                            ivMemoImagePreview.setVisibility(View.VISIBLE);
                            memoImageBase64 = imageBase64;
                        }
                    } catch (Exception e) {
                        android.util.Log.e("MEMO", "Error decoding memo image: " + e.getMessage());
                    }
                }
            }

            cursor.close();
            android.util.Log.d("MEMO", "Loaded memo for title: " + currentTitle);
        } else {
            android.util.Log.d("MEMO", "No memo found for title: " + currentTitle + ", showing empty state");
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Clear memo panel without showing toast (used internally when switching titles)
     */
    private void clearMemoPanelWithoutToast() {
        etMemoText.setText("");
        memoImageBase64 = null;
        ivMemoImagePreview.setImageDrawable(null);
        ivMemoImagePreview.setVisibility(View.GONE);
        ivMemoAddImage.setVisibility(View.VISIBLE);
    }

    /**
     * Show dialog to choose between camera and gallery for memo image
     */
    private void showMemoImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openMemoCamera();
                        } else {
                            openMemoGallery();
                        }
                    }
                })
                .show();
    }

    /**
     * Open camera to capture image for memo
     */
    private void openMemoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_MEMO_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_MEMO_CAMERA_CAPTURE);
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Open gallery to select image for memo
     */
    private void openMemoGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_MEMO_GALLERY_PICK);
        } else {
            Toast.makeText(this, "No gallery app found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save memo to database with FIFO logic (max 10 entries).
     * Associates the memo with the current food note title.
     */
    private void saveMemo() {
        String memoText = etMemoText.getText().toString().trim();

        if (memoText.isEmpty() && memoImageBase64 == null) {
            Toast.makeText(this, "Please enter some text or add an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current food note title to associate with this memo
        String currentTitle = getCurrentFoodNoteTitle();
        android.util.Log.d("MEMO", "Saving memo with title: " + currentTitle);

        // Insert memo into database with title (FIFO logic is handled in the database helper)
        long insertedId = databaseHelper.insertMemo(memoText, memoImageBase64, currentTitle);

        if (insertedId != -1) {
            Toast.makeText(this, "Memo saved successfully!", Toast.LENGTH_SHORT).show();
//            clearMemoPanel();
        } else {
            Toast.makeText(this, "Failed to save memo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clear memo panel - reset text and image
     */
    private void clearMemoPanel() {
        etMemoText.setText("");
        memoImageBase64 = null;
        ivMemoImagePreview.setImageDrawable(null);
        ivMemoImagePreview.setVisibility(View.GONE);
        ivMemoAddImage.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Memo cleared", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handle memo image result from camera or gallery
     */
    private void handleMemoImageResult(Bitmap bitmap) {
        if (bitmap != null) {
            // Hide the add image icon and show preview
            ivMemoAddImage.setVisibility(View.GONE);
            ivMemoImagePreview.setImageBitmap(bitmap);
            ivMemoImagePreview.setVisibility(View.VISIBLE);

            // Convert to base64 for storage
            memoImageBase64 = convertBitmapToBase64(bitmap);

            Toast.makeText(this, "Image added to memo", Toast.LENGTH_SHORT).show();
        }
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
        Button btnAI = view.findViewById(R.id.btnAI);

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
                    syncFoodNoteToBackend(food, quantity, calories, currentDateTime);
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
                                    syncFoodNoteToBackend(food, quantity, calories, currentDateTime);

                                    dialog.dismiss();
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();

                } else {
                    long insertedId = databaseHelper.insertFoodNote(currentDateTime, food, calories, quantity);
                    addRowToTable((int) insertedId, food, quantity, calories, currentDateTime);
                    syncFoodNoteToBackend(food, quantity, calories, currentDateTime);

                    dialog.dismiss();
                }
            }
        });

        // AI button - uses Gemini to estimate calories from food name and quantity
        btnAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = etFood.getText().toString().trim();
                String quantity = etQuantity.getText().toString().trim();

                if (food.isEmpty()) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter a food/drink name first", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Build prompt for calorie estimation
                StringBuilder promptBuilder = new StringBuilder();
                promptBuilder.append("How many calories are in ");
                if (!quantity.isEmpty()) {
                    promptBuilder.append(quantity).append(" of ");
                }
                promptBuilder.append(food).append("?\n");
                promptBuilder.append("Respond with ONLY a single number representing the total calories. No text, no units, just the number.");

                // Show loading
                android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(FoodNoteTableActivity.this);
                progressDialog.setMessage("AI is estimating calories...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                GeminiApiService.calculateCalories(FoodNoteTableActivity.this, promptBuilder.toString(), new GeminiApiService.CalorieCallback() {
                    @Override
                    public void onResult(String result) {
                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            if (result != null && !result.trim().isEmpty()) {
                                // Extract numeric value from response
                                String cleaned = result.trim().replaceAll("[^0-9.]", "");
                                if (!cleaned.isEmpty()) {
                                    try {
                                        // Round to whole number
                                        int calorieValue = (int) Math.round(Double.parseDouble(cleaned));
                                        etCalories.setText(String.valueOf(calorieValue));
                                        Toast.makeText(FoodNoteTableActivity.this, "AI estimated: " + calorieValue + " calories", Toast.LENGTH_SHORT).show();
                                    } catch (NumberFormatException e) {
                                        etCalories.setText(result.trim());
                                        Toast.makeText(FoodNoteTableActivity.this, "AI response: " + result.trim(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(FoodNoteTableActivity.this, "Could not parse AI response. Try again.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                android.util.Log.e("FoodNoteAI", "AI returned null or empty for food: " + etFood.getText().toString());
                                Toast.makeText(FoodNoteTableActivity.this, "AI estimation failed. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });

        dialog.show();
    }

    private void syncFoodNoteToBackend(String food, String quantity, String calories, String dateTime) {
        Map<String, Object> foodData = new HashMap<>();
        foodData.put("food_item_name", food);
        foodData.put("quantity", quantity.isEmpty() ? "1" : quantity);
        foodData.put("note_date", dateTime);

        double caloriesValue = 0;
        if (!calories.isEmpty()) {
            try {
                caloriesValue = Double.parseDouble(calories);
            } catch (NumberFormatException e) {
                android.util.Log.w("SYNC_FOOD", "Invalid calories value: " + calories);
            }
        }
        foodData.put("calories_per_100g", caloriesValue);

        SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(this);
        apiClient.addFoodItem(foodData, new ApiResultCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    Toast.makeText(FoodNoteTableActivity.this,
                        "Synced to server!", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> {
                    Toast.makeText(FoodNoteTableActivity.this,
                        "Server sync failed - will retry later.", Toast.LENGTH_LONG).show();
                });
            }
        });
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
        android.util.Log.d("FOOD NOTES", "Loading food notes from database...");
        Cursor cursor = databaseHelper.getAllFoodNotes();
        if (cursor != null && cursor.moveToFirst()) {
            android.util.Log.d("FOOD NOTES", "Cursor has data, total rows: " + cursor.getCount());
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

            int loadedCount = 0;
            do {
                int isTransferred = cursor.getInt(transferredIndex);
                android.util.Log.d("FOOD NOTES", "Row isTransferred: " + isTransferred);

                if (isTransferred == 0) {
                    long id = cursor.getLong(noteId);
                    String dateTime = cursor.getString(dateIndex);
                    String food = cursor.getString(foodIndex);
                    String calories = cursor.getString(caloriesIndex);
                    String quantity = cursor.getString(quantityIndex);
                    android.util.Log.d("FOOD NOTES", "Loading row: ID=" + id + ", Food=" + food + ", Calories=" + calories + ", Quantity=" + quantity);
                    addRowToTable((int) id, food, quantity, calories, dateTime);
                    loadedCount++;
                }
            } while (cursor.moveToNext());
            android.util.Log.d("FOOD NOTES", "Loaded " + loadedCount + " food notes from database");
            cursor.close();
        } else {
            android.util.Log.d("FOOD NOTES", "No data in cursor or cursor is null");
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

        // Credit button - adds calories to existing balance in CCD_GUI_CD_CIF1
        Button btnCredit = dialog.findViewById(R.id.btnCredit);
        if (btnCredit != null) {
            btnCredit.setOnClickListener(v1 -> {
                // Add calories to existing balance instead of overwriting
                if (CCD_GUI_CD_CIF1.instance != null) {
                    CCD_GUI_CD_CIF1.instance.AddToBalance(String.valueOf(totalCalories));
                    Toast.makeText(FoodNoteTableActivity.this, "Added " + totalCalories + " calories to balance", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FoodNoteTableActivity.this, "Unable to update balance - main activity not available", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
        }
    }


    private void transferAllFoodNotes() {
        // Collect selected note IDs from table rows
        List<Integer> selectedNoteIds = new ArrayList<>();

        for (int i = 1; i < tableLayout.getChildCount(); i++) { // Skip header row
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) row.getChildAt(0);

            if (checkBox.isChecked()) {
                Object tag = row.getTag();
                if (tag != null) {
                    try {
                        int noteId = Integer.parseInt(tag.toString());
                        selectedNoteIds.add(noteId);
                    } catch (NumberFormatException e) {
                        android.util.Log.e("TRANSFER", "Invalid note ID in tag: " + tag);
                    }
                }
            }
        }

        // Check if any notes are selected
        if (selectedNoteIds.isEmpty()) {
            Toast.makeText(FoodNoteTableActivity.this, "Please select food notes to transfer", Toast.LENGTH_SHORT).show();
            return;
        }

        // Transfer only the selected notes
        int transferredCount = databaseHelper.transferFoodNotesByIds(selectedNoteIds);

        if (transferredCount > 0) {
            Toast.makeText(FoodNoteTableActivity.this, "Transferred " + transferredCount + " note(s)", Toast.LENGTH_SHORT).show();
            launchCreditView();
        } else {
            Toast.makeText(FoodNoteTableActivity.this, "No notes were transferred (may already be transferred)", Toast.LENGTH_SHORT).show();
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
     * Opens the nearest/closest AI app available on the device.
     * Priority order: ChatGPT app -> Gemini app -> Claude app -> Browser fallback
     */
    private void openNearestAiApp() {
        // List of AI app package names in priority order
        String[][] aiApps = {
            {"com.openai.chatgpt", "ChatGPT"},           // ChatGPT
            {"com.google.android.apps.bard", "Gemini"}, // Google Gemini
            {"com.anthropic.claude", "Claude"},          // Claude
            {"com.microsoft.copilot", "Copilot"}         // Microsoft Copilot
        };

        // Try to open each AI app in order
        for (String[] app : aiApps) {
            String packageName = app[0];
            String appName = app[1];

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                Toast.makeText(this, "Opening " + appName + "...", Toast.LENGTH_SHORT).show();
                startActivity(launchIntent);
                return;
            }
        }

        // No AI app installed - show dialog to open in browser or install
        showAiAppNotFoundDialog();
    }

    /**
     * Shows a dialog when no AI apps are found, offering browser options
     */
    private void showAiAppNotFoundDialog() {
        String[] options = {"Open ChatGPT in Browser", "Open Gemini in Browser", "Install ChatGPT from Play Store"};

        new AlertDialog.Builder(this)
            .setTitle("No AI App Found")
            .setMessage("No AI assistant app is installed. Choose an option:")
            .setItems(options, (dialog, which) -> {
                Intent intent;
                switch (which) {
                    case 0: // ChatGPT in browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.openai.com"));
                        startActivity(intent);
                        break;
                    case 1: // Gemini in browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gemini.google.com"));
                        startActivity(intent);
                        break;
                    case 2: // Install ChatGPT from Play Store
                        try {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.openai.chatgpt"));
                            startActivity(intent);
                        } catch (android.content.ActivityNotFoundException e) {
                            // Play Store not installed, open in browser
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.openai.chatgpt"));
                            startActivity(intent);
                        }
                        break;
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    /**
     * Handle Save Notes button click.
     * Collects selected food notes (or all if none selected) and saves them as a collection
     * to the SQLite database using DayCiF1005's Save_Notes function.
     *
     * Only saves notes that haven't been saved to a collection yet (isSavedToCollection = 0).
     * After successful save, marks those notes as saved to prevent duplicates.
     */
    private void handleSaveNotesButtonClick() {
        android.util.Log.d("SAVE_NOTES", "handleSaveNotesButtonClick called");

        // Collect selected food notes (or all if none selected)
        List<Map<String, Object>> foodNotesToSave = new ArrayList<>();
        List<Integer> noteIdsToSave = new ArrayList<>(); // Track note IDs for marking as saved
        boolean hasSelection = false;
        int skippedCount = 0; // Count already-saved notes that were skipped

        // Check if any notes are selected
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) row.getChildAt(0);
            if (checkBox.isChecked()) {
                hasSelection = true;
                break;
            }
        }

        // Collect food notes (selected only if there's a selection, otherwise all)
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) row.getChildAt(0);
            TextView foodText = (TextView) row.getChildAt(2);
            TextView caloriesText = (TextView) row.getChildAt(3);
            TextView quantityText = (TextView) row.getChildAt(4);

            // Include if no selection made (save all) OR if this row is selected
            if (!hasSelection || checkBox.isChecked()) {
                // Get the note ID from the row tag
                Object tagObj = row.getTag();
                int noteId = -1;
                if (tagObj != null) {
                    try {
                        noteId = Integer.parseInt(tagObj.toString());
                    } catch (NumberFormatException e) {
                        android.util.Log.w("SAVE_NOTES", "Invalid note ID in tag: " + tagObj);
                    }
                }

                // Check if this note has already been saved to a collection
                if (noteId > 0 && databaseHelper.isNoteSavedToCollection(noteId)) {
                    android.util.Log.d("SAVE_NOTES", "Skipping note ID " + noteId + " - already saved to collection");
                    skippedCount++;
                    continue; // Skip this note as it's already saved
                }

                Map<String, Object> noteMap = new HashMap<>();
                noteMap.put("note_food", foodText.getText().toString());
                noteMap.put("note_id", noteId); // Include note ID in the map

                // Parse calories safely
                String caloriesStr = caloriesText.getText().toString().trim();
                int calories = 0;
                if (!caloriesStr.isEmpty()) {
                    try {
                        calories = Integer.parseInt(caloriesStr);
                    } catch (NumberFormatException e) {
                        android.util.Log.w("SAVE_NOTES", "Invalid calories value: " + caloriesStr);
                    }
                }
                noteMap.put("note_calories", calories);

                // Parse quantity safely
                String quantityStr = quantityText.getText().toString().trim();
                int quantity = 1;
                if (!quantityStr.isEmpty()) {
                    try {
                        quantity = Integer.parseInt(quantityStr);
                    } catch (NumberFormatException e) {
                        android.util.Log.w("SAVE_NOTES", "Invalid quantity value: " + quantityStr);
                    }
                }
                noteMap.put("note_quantity", quantity);

                foodNotesToSave.add(noteMap);
                if (noteId > 0) {
                    noteIdsToSave.add(noteId);
                }
            }
        }

        // Show message if all notes were already saved
        if (foodNotesToSave.isEmpty()) {
            if (skippedCount > 0) {
                Toast.makeText(this, "All selected notes have already been saved to a collection", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No food notes to save", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        android.util.Log.d("SAVE_NOTES", "Saving " + foodNotesToSave.size() + " food notes (skipped " + skippedCount + " already saved)");

        // Get the selected date from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String selectedDateStr = prefs.getString(PREF_LAST_SELECTED_DATE, null);

        if (selectedDateStr == null) {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the selected date and create a DayCiF1005 instance
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
            Date selectedDate = inputFormat.parse(selectedDateStr);

            if (selectedDate != null) {
                // Convert to LocalDateTime
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                LocalDateTime dateTime = LocalDateTime.of(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH),
                        0, 0
                );

                // Create DayCiF1005 instance with the selected date
                DayCiF1005 day = new DayCiF1005(0, 0, 0, 0, dateTime, "", "");

                // Save the food notes collection
                long insertedId = day.Save_Notes(this, foodNotesToSave);

                if (insertedId > 0) {
                    // Mark the saved notes as saved to collection
                    if (!noteIdsToSave.isEmpty()) {
                        int markedCount = databaseHelper.markNotesAsSavedToCollection(noteIdsToSave);
                        android.util.Log.d("SAVE_NOTES", "Marked " + markedCount + " notes as saved to collection");
                    }

                    String message = hasSelection
                            ? "Saved " + foodNotesToSave.size() + " selected note(s) to collection"
                            : "Saved all " + foodNotesToSave.size() + " note(s) to collection";
                    if (skippedCount > 0) {
                        message += " (" + skippedCount + " already saved)";
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    android.util.Log.d("SAVE_NOTES", "Food notes collection saved with ID: " + insertedId);
                } else {
                    Toast.makeText(this, "Failed to save notes collection", Toast.LENGTH_SHORT).show();
                    android.util.Log.e("SAVE_NOTES", "Failed to save food notes collection");
                }
            }
        } catch (Exception e) {
            android.util.Log.e("SAVE_NOTES", "Error saving food notes: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error saving notes: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    // ========== DATE PICKER LOGIC ==========

    /**
     * Check on activity open and show mandatory date picker if needed.
     * Dialog shows when:
     * 1. After 4 PM and user hasn't selected a date for the current period
     * 2. First-time user (no date ever selected)
     * Before 4 PM, user can continue with the previously selected date.
     */
    private void checkAndShowMandatoryDatePicker() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String lastSelectedDate = prefs.getString(PREF_LAST_SELECTED_DATE, null);

        android.util.Log.d("DATE_PICKER", "checkAndShowMandatoryDatePicker called");
        android.util.Log.d("DATE_PICKER", "Last selected date: " + lastSelectedDate);

        // First-time user - must select a date before proceeding
        if (lastSelectedDate == null) {
            android.util.Log.d("DATE_PICKER", "First-time user - showing mandatory date picker");
            showMandatoryDatePickerDialog();
            return;
        }

        // Get current time
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        android.util.Log.d("DATE_PICKER", "Current hour: " + currentHour);

        // Check if user needs to select a new date
        boolean needsNewSelection = canSelectDateToday();
        android.util.Log.d("DATE_PICKER", "Needs new selection: " + needsNewSelection);

        // Only show automatic dialog if it's after 4 PM (16:00)
        if (currentHour >= 16) {
            android.util.Log.d("DATE_PICKER", "After 4 PM");
            if (needsNewSelection) {
                // User needs to select a date - show mandatory dialog
                android.util.Log.d("DATE_PICKER", "User can select a new date - showing dialog");
                showMandatoryDatePickerDialog();
            } else {
                // User has already selected a date for this period, just show the title
                android.util.Log.d("DATE_PICKER", "User already selected for this period - showing title");
                updateDateRangeTitle(lastSelectedDate);
            }
        } else {
            // Before 4 PM, just show last selected date
            android.util.Log.d("DATE_PICKER", "Before 4 PM - showing last selected date");
            updateDateRangeTitle(lastSelectedDate);
        }
    }

    /**
     * Show a mandatory date picker dialog (non-dismissible).
     * User cannot proceed without selecting a date.
     */
    private void showMandatoryDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.DatePickerTheme,  // Your custom theme
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Create a calendar for the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);

                        // Format the selected date as "dd MMM yy" (e.g., "10 OCT 25")
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        // Save the selected date to SharedPreferences
                        saveSelectedDate(formattedDate);

                        // Update the title
                        updateDateRangeTitle(formattedDate);

                        Toast.makeText(FoodNoteTableActivity.this,
                                "Date selected: " + formattedDate,
                                Toast.LENGTH_SHORT).show();
                    }
                },
                year, month, day
        );

        // Prevent dismissing without selection
        datePickerDialog.setCancelable(false);
        datePickerDialog.setCanceledOnTouchOutside(false);

        // Remove the Cancel button entirely
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, null, (DialogInterface.OnClickListener) null);

        // Disable future dates
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        // Show the dialog
        datePickerDialog.show();

        // After showing, explicitly hide the cancel button (for some OEMs that auto-add it)
        Button cancelButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (cancelButton != null) {
            cancelButton.setVisibility(View.GONE);
        }
    }



    /**
     * Check if the user can select a date today.
     * The user can select a date once per day. After 4 PM the next day, they can select again.
     *
     * Logic:
     * 1. Get current time
     * 2. Calculate the "reset boundary" - today at 4 PM (16:00)
     * 3. If current time < 4 PM, use yesterday at 4 PM as the boundary
     * 4. Check if last selection was before this boundary
     *
     * @return true if the user can select a date, false otherwise
     */
    private boolean canSelectDateToday() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        long lastSelectionTime = prefs.getLong(PREF_SELECTED_DATE_TIMESTAMP, 0);

        android.util.Log.d("DATE_PICKER", "canSelectDateToday - Last selection timestamp: " + lastSelectionTime);

        if (lastSelectionTime == 0) {
            // Never selected before
            android.util.Log.d("DATE_PICKER", "Never selected before - returning true");
            return true;
        }

        // Get current time
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        android.util.Log.d("DATE_PICKER", "Current time: " + sdf.format(now.getTime()));

        // Calculate the 4 PM (16:00) boundary
        Calendar resetBoundary = Calendar.getInstance();
        resetBoundary.set(Calendar.HOUR_OF_DAY, 16);
        resetBoundary.set(Calendar.MINUTE, 0);
        resetBoundary.set(Calendar.SECOND, 0);
        resetBoundary.set(Calendar.MILLISECOND, 0);

        // If current time is before 4 PM today, use yesterday's 4 PM as boundary
        if (now.before(resetBoundary)) {
            android.util.Log.d("DATE_PICKER", "Before 4 PM - adjusting boundary to yesterday");
            resetBoundary.add(Calendar.DAY_OF_MONTH, -1);
        }

        android.util.Log.d("DATE_PICKER", "Reset boundary: " + sdf.format(resetBoundary.getTime()));
        android.util.Log.d("DATE_PICKER", "Last selection was: " + sdf.format(new Date(lastSelectionTime)));

        boolean canSelect = lastSelectionTime < resetBoundary.getTimeInMillis();
        android.util.Log.d("DATE_PICKER", "Can select date: " + canSelect);

        // Check if last selection was before the boundary
        return canSelect;
    }

    /**
     * Save the selected date to SharedPreferences
     * @param selectedDate The selected date in "dd MMM yy" format
     */
    private void saveSelectedDate(String selectedDate) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_LAST_SELECTED_DATE, selectedDate);
        editor.putLong(PREF_SELECTED_DATE_TIMESTAMP, System.currentTimeMillis());
        editor.apply();

        android.util.Log.d("DATE_PICKER", "Saved selected date: " + selectedDate + " at " + System.currentTimeMillis());
    }

    /**
     * Update the date range title based on the selected date
     *
     * Format: "Food Notes between 4pm [previous_day] and 4pm [selected_day]"
     * Example: User selects "10 OCT 25" → "Food Notes between 4pm 9 OCT and 4pm 10 OCT 25"
     *
     * Also loads the memo associated with the new title.
     *
     * @param selectedDateStr The selected date in "dd MMM yy" format
     */
    private void updateDateRangeTitle(String selectedDateStr) {
        try {
            // Parse the selected date
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
            Date selectedDate = inputFormat.parse(selectedDateStr);

            if (selectedDate != null) {
                // Calculate previous day
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date previousDay = calendar.getTime();

                // Format dates for display
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                String previousDayStr = outputFormat.format(previousDay);
                String selectedDayStr = outputFormat.format(selectedDate) + " " + new SimpleDateFormat("yy", Locale.getDefault()).format(selectedDate);

                // Build the title
                String title = "Food Notes between 4pm " + previousDayStr + " and 4pm " + selectedDayStr;
                tvDateRangeTitle.setText(title);

                android.util.Log.d("DATE_PICKER", "Updated title: " + title);

                // Load memo for the new title (only if memo panel is initialized)
                if (etMemoText != null) {
                    android.util.Log.d("MEMO", "Title changed, loading memo for new title");
                    loadLastMemo();
                }
            }
        } catch (Exception e) {
            android.util.Log.e("DATE_PICKER", "Error updating date range title: " + e.getMessage());
            tvDateRangeTitle.setText("Food Notes for " + selectedDateStr);
        }
    }

    // ========== ADD ROW WITH IMAGE LOGIC ==========

    /**
     * Show dialog to add food note with image
     */
    private void showAddRowWithImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_row_with_image, null);
        builder.setView(view);
        builder.setCancelable(false); // Prevent dismissing on outside click

        imageUploadDialog = builder.create();
        imageUploadDialog.setCanceledOnTouchOutside(false); // Extra safety

        // Initialize dialog views
        dialogImageView = view.findViewById(R.id.imageBox);
        dialogOverlayText = view.findViewById(R.id.tvOverlayText);
        dialogScanButton = view.findViewById(R.id.btnScanAndAdd);
        dialogProgressBar = view.findViewById(R.id.progressBar);
        dialogStatusText = view.findViewById(R.id.tvStatus);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Reset state
        currentImageBase64 = null;
        dialogScanButton.setEnabled(false);
        dialogProgressBar.setVisibility(View.GONE);
        dialogStatusText.setVisibility(View.GONE);

        // Image box click listener
        dialogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });

        // Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUploadDialog.dismiss();
            }
        });

        // Scan and Add button
        dialogScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageBase64 != null) {
                    scanAndAddFoodNote();
                } else {
                    Toast.makeText(FoodNoteTableActivity.this, "Please upload an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageUploadDialog.show();
    }

    /**
     * Show dialog to choose between camera and gallery
     */
    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Camera
                            openCamera();
                        } else {
                            // Gallery
                            openGallery();
                        }
                    }
                })
                .show();
    }

    /**
     * Open camera to capture image
     */
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Open gallery to select image
     * Note: ACTION_PICK doesn't require READ_EXTERNAL_STORAGE permission on modern Android
     */
    private void openGallery() {
        // No permission needed for ACTION_PICK - it uses system picker with scoped storage
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_GALLERY_PICK);
        } else {
            Toast.makeText(this, "No gallery app found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Scan image and add food note to table
     */
    private void scanAndAddFoodNote() {
        // Disable button and show loading
        dialogScanButton.setEnabled(false);
        dialogScanButton.setText("Scanning...");
        dialogProgressBar.setVisibility(View.VISIBLE);
        dialogStatusText.setVisibility(View.VISIBLE);
        dialogStatusText.setText("Analyzing food image...");

        foodDetectionService.fetchFoodNoteData(currentImageBase64, new FoodNoteCallback() {
            @Override
            public void onSuccess(String foodName, String calories, String quantity) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Insert into database
                        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
                        android.util.Log.d("ADD_ROW_IMAGE", "Inserting food note: " + foodName + ", calories: " + calories + ", quantity: " + quantity);
                        long insertedId = databaseHelper.insertFoodNote(currentDateTime, foodName, calories, quantity);
                        android.util.Log.d("ADD_ROW_IMAGE", "Inserted with ID: " + insertedId);

                        // Verify insertion by querying the database
                        android.util.Log.d("ADD_ROW_IMAGE", "Verifying data was saved...");

                        // Add to table
                        addRowToTable((int) insertedId, foodName, quantity, calories, currentDateTime);
                        android.util.Log.d("ADD_ROW_IMAGE", "Added row to table with ID: " + insertedId);

                        Toast.makeText(FoodNoteTableActivity.this, "Food note added successfully! ID: " + insertedId, Toast.LENGTH_SHORT).show();

                        // Close dialog
                        imageUploadDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Re-enable button and hide loading
                        dialogScanButton.setEnabled(true);
                        dialogScanButton.setText("Scan & Add Row");
                        dialogProgressBar.setVisibility(View.GONE);
                        dialogStatusText.setVisibility(View.GONE);

                        Toast.makeText(FoodNoteTableActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * Convert bitmap to base64 string
     */
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    /**
     * Handle activity result from camera or gallery
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = null;

                // Handle Food Note image dialog requests
                if (requestCode == REQUEST_CAMERA_CAPTURE) {
                    if (data != null && data.getExtras() != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                    }
                } else if (requestCode == REQUEST_GALLERY_PICK) {
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    }
                }

                // Handle image for food note dialog
                if ((requestCode == REQUEST_CAMERA_CAPTURE || requestCode == REQUEST_GALLERY_PICK) && bitmap != null) {
                    // Display image in dialog
                    if (dialogImageView != null) {
                        dialogImageView.setImageBitmap(bitmap);
                    }
                    if (dialogOverlayText != null) {
                        dialogOverlayText.setVisibility(View.GONE);
                    }

                    // Convert to base64
                    currentImageBase64 = convertBitmapToBase64(bitmap);

                    // Enable scan button
                    if (dialogScanButton != null) {
                        dialogScanButton.setEnabled(true);
                    }

                    Toast.makeText(this, "Image loaded! Ready to scan.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle Memo image requests
                if (requestCode == REQUEST_MEMO_CAMERA_CAPTURE) {
                    if (data != null && data.getExtras() != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        handleMemoImageResult(bitmap);
                    }
                } else if (requestCode == REQUEST_MEMO_GALLERY_PICK) {
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        handleMemoImageResult(bitmap);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Handle permission results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CAMERA_PERMISSION) {
                openCamera();
            } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
                openGallery();
            } else if (requestCode == REQUEST_MEMO_CAMERA_PERMISSION) {
                openMemoCamera();
            }
        } else {
            String source = "camera";
            if (requestCode == REQUEST_STORAGE_PERMISSION) {
                source = "gallery";
            }
            Toast.makeText(this, "Permission denied. Cannot access " + source, Toast.LENGTH_SHORT).show();
        }
    }

}
