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
import android.widget.LinearLayout;
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

    // Intent extra: when set to true in the launching intent, auto-start the 4PM workflow
    private static final String EXTRA_START_4PM_PROCESSING = "start_4pm_processing";

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
    private Button btnMemoAi;
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
                if (checkPreviousDayDebitUpdate()) {
                    showFoodInputDialog(null, "", "", "");
                }
            }
        });

        btnAddRowWithImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPreviousDayDebitUpdate()) {
                    showAddRowWithImageDialog();
                }
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

        // 4PM Food Notes Processing button
        Button btn4PM = findViewById(R.id.btn4PM);
        btn4PM.setOnClickListener(v -> start4PMProcessingWorkflow());

        // Check Before You Eat button
        Button btnCheckBeforeYouEat = findViewById(R.id.btnCheckBeforeYouEat);
        btnCheckBeforeYouEat.setOnClickListener(v -> showCheckBeforeYouEatDialog());

        // Initialize Memo Panel views
        initializeMemoPanel();

        // Auto-trigger 4PM workflow if launched from the 4PM notification
        if (getIntent() != null && getIntent().getBooleanExtra(EXTRA_START_4PM_PROCESSING, false)) {
            // Post with slight delay to ensure UI is fully ready
            tableLayout.post(() -> start4PMProcessingWorkflow());
        }
    }

    // ========== CHECK BEFORE YOU EAT ==========

    /**
     * "Check Before You Eat" feature.
     *
     * User enters a food name (and optional quantity/portion).
     * AI returns macros: Calories, Protein, Carbs, Fats.
     * Result is shown inline — fast lookup before deciding whether to eat.
     */
    private void showCheckBeforeYouEatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check Before You Eat");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(48, 32, 48, 16);

        final EditText etFoodName = new EditText(this);
        etFoodName.setHint("Food name (e.g. Grilled Chicken)");
        etFoodName.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        layout.addView(etFoodName);

        final EditText etPortion = new EditText(this);
        etPortion.setHint("Portion/quantity (e.g. 200g, 1 cup) — optional");
        etPortion.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        layout.addView(etPortion);

        builder.setView(layout);

        builder.setPositiveButton("Check", null); // Set to null to override default dismiss
        builder.setNegativeButton("Cancel", (d, w) -> d.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Override Positive button to prevent auto-dismiss on validation failure
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String foodName = etFoodName.getText().toString().trim();
            String portion = etPortion.getText().toString().trim();

            if (foodName.isEmpty()) {
                Toast.makeText(this, "Please enter a food name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Build a structured prompt for macros
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("Provide the macronutrients for ");
            if (!portion.isEmpty()) {
                promptBuilder.append(portion).append(" of ");
            }
            promptBuilder.append(foodName).append(".\n\n");
            promptBuilder.append("Reply ONLY in this exact format (numbers only, no units):\n");
            promptBuilder.append("Calories: <number>\n");
            promptBuilder.append("Protein: <number>g\n");
            promptBuilder.append("Carbs: <number>g\n");
            promptBuilder.append("Fat: <number>g\n");
            promptBuilder.append("\nNo other text, no ranges, just these 4 lines.");

            dialog.dismiss();

            // Show loading
            android.app.ProgressDialog progress = new android.app.ProgressDialog(this);
            progress.setMessage("Checking macros for \"" + foodName + "\"...");
            progress.setCancelable(false);
            progress.show();

            android.util.Log.d("CheckBeforeEat", "Prompt: " + promptBuilder);

            GeminiApiService.calculateCalories(this, promptBuilder.toString(),
                    new GeminiApiService.CalorieCallback() {
                        @Override
                        public void onResult(String result) {
                            runOnUiThread(() -> {
                                progress.dismiss();
                                if (result != null && !result.trim().isEmpty()) {
                                    showMacroResultDialog(foodName, portion, result.trim());
                                } else {
                                    Toast.makeText(FoodNoteTableActivity.this,
                                            "AI check failed. Please check connection and try again.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
        });
    }

    /**
     * Parse and display the macro result from AI in a clean dialog.
     * Extracts Calories, Protein, Carbs, Fat from AI response.
     * Also offers a quick "Add to Notes" shortcut.
     */
    private void showMacroResultDialog(String foodName, String portion, String aiResponse) {
        // Parse macros from AI text
        int calories = extractMacroInt(aiResponse, "Calories");
        String proteinStr = extractMacroStr(aiResponse, "Protein");
        String carbsStr = extractMacroStr(aiResponse, "Carbs");
        String fatStr = extractMacroStr(aiResponse, "Fat");

        String displayPortion = portion.isEmpty() ? "standard serving" : portion;

        StringBuilder msg = new StringBuilder();
        msg.append("Food: ").append(foodName).append("\n");
        msg.append("Portion: ").append(displayPortion).append("\n\n");
        msg.append("Calories:  ").append(calories).append(" kcal\n");
        msg.append("Protein:   ").append(proteinStr).append("\n");
        msg.append("Carbs:     ").append(carbsStr).append("\n");
        msg.append("Fat:       ").append(fatStr).append("\n\n");

        // Contextual advice
        if (calories > 800) {
            msg.append("⚠️ High calorie item — consider a smaller portion.");
        } else if (calories < 150) {
            msg.append("✓ Low calorie choice — good to go!");
        } else {
            msg.append("Moderate calorie item — fits most plans in moderation.");
        }

        AlertDialog.Builder resultBuilder = new AlertDialog.Builder(this);
        resultBuilder.setTitle("Macro Check Result");
        resultBuilder.setMessage(msg.toString());

        // Quick-add to food notes with these calories
        final int finalCalories = calories;
        resultBuilder.setPositiveButton("Add to Notes", (d, w) -> {
            String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
            long insertedId = databaseHelper.insertFoodNote(
                    currentDateTime, foodName, String.valueOf(finalCalories),
                    portion.isEmpty() ? "1" : portion);
            if (insertedId > 0) {
                addRowToTable((int) insertedId, foodName,
                        portion.isEmpty() ? "1" : portion,
                        String.valueOf(finalCalories), currentDateTime);
                Toast.makeText(this, "\"" + foodName + "\" added to your food notes", Toast.LENGTH_SHORT).show();
            }
        });
        resultBuilder.setNegativeButton("Done", (d, w) -> d.dismiss());
        resultBuilder.show();
    }

    /** Extract an integer macro value from AI text for a given label (e.g. "Calories"). */
    private int extractMacroInt(String text, String label) {
        try {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                    "(?i)" + label + "\\s*:\\s*([\\d]+)");
            java.util.regex.Matcher m = p.matcher(text);
            if (m.find()) return Integer.parseInt(m.group(1));
        } catch (Exception ignored) {}
        return 0;
    }

    /** Extract a macro value string (e.g. "25g") from AI text for a given label. */
    private String extractMacroStr(String text, String label) {
        try {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                    "(?i)" + label + "\\s*:\\s*([\\d.]+(?:g|mg)?)");
            java.util.regex.Matcher m = p.matcher(text);
            if (m.find()) return m.group(1);
        } catch (Exception ignored) {}
        return "N/A";
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
        btnMemoAi = findViewById(R.id.btnMemoAi);

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

        // AI button - analyzes memo text and/or attached image with AI
        btnMemoAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMemoAiButtonClick();
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
     * Handle AI button click on memo panel.
     * Collects memo text and attached image (if any), sends to AI for analysis,
     * and shows the result in a dialog.
     * Supports: text only, image only, or text + image together.
     */
    private void handleMemoAiButtonClick() {
        String memoText = etMemoText.getText().toString().trim();
        boolean hasText = !memoText.isEmpty();
        boolean hasImage = memoImageBase64 != null;

        if (!hasText && !hasImage) {
            Toast.makeText(this, "Please add some text or an image to analyze", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build AI prompt based on what's available
        String prompt;
        if (hasText && hasImage) {
            prompt = "Analyze the following memo text and the attached image together. " +
                    "Provide a detailed response covering key observations, any food-related insights, " +
                    "and a brief summary.\n\nMemo text:\n" + memoText;
        } else if (hasText) {
            prompt = "Analyze the following memo and provide key insights, observations, " +
                    "and a brief summary:\n\n" + memoText;
        } else {
            prompt = "Analyze this image and provide a detailed description of what you see, " +
                    "including any food items, quantities, or relevant observations.";
        }

        // Show loading dialog
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Analyzing memo with AI...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        android.util.Log.d("MemoAI", "Starting AI analysis: hasText=" + hasText + ", hasImage=" + hasImage);

        GeminiApiService.analyzeMemo(this, prompt, hasImage ? memoImageBase64 : null,
                new GeminiApiService.CalorieCallback() {
                    @Override
                    public void onResult(String result) {
                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            if (result != null && !result.trim().isEmpty()) {
                                android.util.Log.d("MemoAI", "AI result received successfully");
                                new AlertDialog.Builder(FoodNoteTableActivity.this)
                                        .setTitle("AI Analysis")
                                        .setMessage(result.trim())
                                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                        .show();
                            } else {
                                android.util.Log.e("MemoAI", "AI returned null or empty result");
                                Toast.makeText(FoodNoteTableActivity.this,
                                        "AI analysis failed. Please check your internet connection and try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
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
        Button btnAddToCloud = view.findViewById(R.id.btnAddToCloud);

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

        // Add to Cloud button - adds the note as a new food/drink item to the backend
        btnAddToCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = etFood.getText().toString().trim();
                String quantityStr = etQuantity.getText().toString().trim();
                String caloriesStr = etCalories.getText().toString().trim();

                if (foodName.isEmpty()) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter a food/drink name first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (caloriesStr.isEmpty()) {
                    Toast.makeText(FoodNoteTableActivity.this, "Please enter or estimate calories before adding to cloud", Toast.LENGTH_SHORT).show();
                    return;
                }

                // First use AI to get full nutrition data, then check for duplicates and add
                android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(FoodNoteTableActivity.this);
                progressDialog.setMessage("Fetching nutrition data for \"" + foodName + "\"...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                GeminiApiService.INSTANCE.fetchNutritionData(FoodNoteTableActivity.this, foodName,
                    foodItem -> {
                        progressDialog.dismiss();

                        // Override calories with the user's value from the note
                        try {
                            float userCalories = Float.parseFloat(caloriesStr);
                            foodItem.Set_calories_per_100g(userCalories);
                            foodItem.Set_calorie_value((int) userCalories);
                        } catch (NumberFormatException e) {
                            // keep AI-estimated value
                        }

                        // Check local DB for duplicate
                        Food_Item_CIF4 existing = databaseHelper.getFoodItemByExactName(foodName);
                        if (existing != null) {
                            // Duplicate found - ask user
                            new AlertDialog.Builder(FoodNoteTableActivity.this)
                                .setTitle("Duplicate Found")
                                .setMessage("\"" + foodName + "\" already exists in the database.\n\n"
                                    + "Existing: " + (int) existing.Get_calories_per_100g() + " cal/100g\n"
                                    + "New: " + (int) foodItem.Get_calories_per_100g() + " cal/100g\n\n"
                                    + "What would you like to do?")
                                .setPositiveButton("Overwrite", (dialogInterface, which) -> {
                                    databaseHelper.deleteFoodItemByName(foodName);
                                    databaseHelper.Insert_Food_Item_Row(foodItem);
                                    addFoodItemToBackendCloud(foodItem, quantityStr);
                                    Toast.makeText(FoodNoteTableActivity.this, "\"" + foodName + "\" overwritten and synced to cloud", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                })
                                .setNegativeButton("Keep Existing", (dialogInterface, which) -> {
                                    Toast.makeText(FoodNoteTableActivity.this, "Kept existing entry for \"" + foodName + "\"", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                })
                                .show();
                        } else {
                            // No duplicate - insert directly
                            databaseHelper.Insert_Food_Item_Row(foodItem);
                            addFoodItemToBackendCloud(foodItem, quantityStr);
                            Toast.makeText(FoodNoteTableActivity.this, "\"" + foodName + "\" added to cloud database", Toast.LENGTH_SHORT).show();
                        }
                        return kotlin.Unit.INSTANCE;
                    },
                    error -> {
                        progressDialog.dismiss();
                        android.util.Log.e("AddToCloud", "Failed to fetch nutrition data: " + error);
                        Toast.makeText(FoodNoteTableActivity.this, "Failed to fetch nutrition data. Please try again.", Toast.LENGTH_LONG).show();
                        return kotlin.Unit.INSTANCE;
                    }
                );
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
                                // Extract the first number from AI response safely
                                java.util.regex.Matcher matcher = java.util.regex.Pattern
                                        .compile("\\d+(\\.\\d+)?")
                                        .matcher(result.trim());
                                if (matcher.find()) {
                                    try {
                                        int calorieValue = (int) Math.round(Double.parseDouble(matcher.group()));
                                        etCalories.setText(String.valueOf(calorieValue));
                                        Toast.makeText(FoodNoteTableActivity.this, "AI estimated: " + calorieValue + " calories", Toast.LENGTH_SHORT).show();
                                    } catch (NumberFormatException e) {
                                        android.util.Log.e("FoodNoteAI", "Number parse failed for: " + result.trim());
                                        etCalories.setText("");
                                        Toast.makeText(FoodNoteTableActivity.this, "Could not parse AI response. Try again.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    android.util.Log.w("FoodNoteAI", "No number found in AI response: " + result.trim());
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

    private void addFoodItemToBackendCloud(Food_Item_CIF4 foodItem, String quantity) {
        Map<String, Object> foodData = new HashMap<>();
        foodData.put("food_item_name", foodItem.Get_food_item_name());
        foodData.put("calories_per_100g", (double) foodItem.Get_calories_per_100g());
        foodData.put("fat_per_100g", (double) foodItem.Get_fat_per_100g());
        foodData.put("saturated_fat", (double) foodItem.Get_saturated_fat());
        foodData.put("trans_fat", (double) foodItem.Get_trans_fat());
        foodData.put("protein_per_100g", (double) foodItem.Get_protein_per_100g());
        foodData.put("carbs_per_100g", (double) foodItem.Get_carbs_per_100g());
        foodData.put("sugar_per_100g", (double) foodItem.Get_sugar_per_100g());
        foodData.put("salt_per_100g", (double) foodItem.Get_salt_per_100g());
        foodData.put("fiber", (double) foodItem.Get_fiber());
        foodData.put("quantity", quantity.isEmpty() ? "100g" : quantity);

        SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(this);
        apiClient.addFoodItem(foodData, new ApiResultCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    android.util.Log.d("AddToCloud", "Food item synced to cloud: " + foodItem.Get_food_item_name());
                });
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> {
                    Toast.makeText(FoodNoteTableActivity.this,
                        "Cloud sync failed for \"" + foodItem.Get_food_item_name() + "\" - saved locally.", Toast.LENGTH_LONG).show();
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

        // Credit button - adds calories to existing Countdown Balance in CCD_GUI_CD_CIF1
        Button btnCredit = dialog.findViewById(R.id.btnCredit);
        if (btnCredit != null) {
            btnCredit.setOnClickListener(v1 -> {
                android.util.Log.d("FoodNoteCredit", "[btnCredit] Attempting to add " + totalCalories + " calories to balance");

                if (totalCalories <= 0) {
                    // Root cause fix: guard against zero/negative credit which would be misleading
                    Toast.makeText(FoodNoteTableActivity.this, "No calories to add (total is " + totalCalories + ")", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                if (CCD_GUI_CD_CIF1.instance != null) {
                    // Root cause fix: AddToBalance now has full NFE/null safety and logs internally.
                    // Toast is shown here (not inside AddToBalance) to keep UI feedback clear.
                    CCD_GUI_CD_CIF1.instance.AddToBalance(String.valueOf(totalCalories));
                    android.util.Log.d("FoodNoteCredit", "[btnCredit] AddToBalance called for " + totalCalories + " calories");
                    Toast.makeText(FoodNoteTableActivity.this,
                            "Added " + totalCalories + " calories to Countdown Balance", Toast.LENGTH_SHORT).show();
                } else {
                    // Root cause fix: CCD_GUI_CD_CIF1.instance null means main activity is not in memory.
                    // Log so developer can diagnose process/activity lifecycle issues.
                    android.util.Log.e("FoodNoteCredit", "[btnCredit] CCD_GUI_CD_CIF1.instance is null — main activity not available");
                    Toast.makeText(FoodNoteTableActivity.this,
                            "Cannot update balance: please return to the main screen first, then try again.", Toast.LENGTH_LONG).show();
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
            TextView caloriesText = (TextView) row.getChildAt(3); // calories column
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
                                        showCaloriesResult(result, selectedFoods);
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
        sb.append("Estimate the calories for each food below. ");
        sb.append("For EACH item output exactly one line in the format:\n");
        sb.append("FOOD_NAME: NUMBER calories\n");
        sb.append("Then on the last line: TOTAL: NUMBER calories\n");
        sb.append("Use only whole numbers. No extra text.\n\n");

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

        return sb.toString();
    }

    /**
     * Shows the AI calorie estimation result and offers to apply the values back to the
     * selected food notes. If parsing succeeds, a confirmation step shows what will be written
     * before any DB change is made.
     */
    private void showCaloriesResult(String response, List<Map<String, String>> selectedFoods) {
        String message = (response != null && !response.trim().isEmpty())
                ? response.trim()
                : "AI did not return a result. Please try again.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Calorie Estimation")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Only show Apply button when we have foods to update and a non-empty response
        if (selectedFoods != null && !selectedFoods.isEmpty()
                && response != null && !response.trim().isEmpty()) {
            builder.setNeutralButton("Apply to Notes", (dialog, which) -> {
                List<Integer> parsed = parseCaloriesFromAiResponse(response, selectedFoods.size());
                if (parsed.isEmpty()) {
                    Toast.makeText(this,
                            "Could not extract calorie values from AI response. Edit notes manually.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                confirmAndApplyCalories(parsed, selectedFoods);
            });
        }

        builder.show();
    }

    /**
     * Parses per-item calorie values from the AI response text.
     * Scans each line for the pattern "N calories" or "N kcal" (whole numbers only).
     * Lines that contain "total" are excluded so the sum line is not mistaken for an item.
     * Returns up to {@code maxItems} values in the order they appear.
     */
    private List<Integer> parseCaloriesFromAiResponse(String response, int maxItems) {
        List<Integer> result = new ArrayList<>();
        if (response == null || response.trim().isEmpty()) return result;

        // Match whole numbers followed by "cal", "calories", or "kcal" (case-insensitive)
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                "(\\d+)\\s*(?:cal(?:ories)?|kcal)", java.util.regex.Pattern.CASE_INSENSITIVE);

        for (String line : response.split("\n")) {
            // Skip the total line — it is not a per-item value
            if (line.trim().toLowerCase(java.util.Locale.getDefault()).startsWith("total")) continue;

            java.util.regex.Matcher m = p.matcher(line);
            if (m.find()) {
                try {
                    int cal = Integer.parseInt(m.group(1));
                    if (cal > 0 && cal <= 10000) {
                        result.add(cal);
                        android.util.Log.d("FoodNoteAI", "Parsed calories=" + cal + " from: " + line.trim());
                    }
                } catch (NumberFormatException ignore) { /* skip unparseable */ }
            }
            if (result.size() >= maxItems) break;
        }

        android.util.Log.d("FoodNoteAI", "parseCaloriesFromAiResponse: found " + result.size()
                + "/" + maxItems + " values");
        return result;
    }

    /**
     * Shows a confirmation dialog listing the calorie values that will be written to each note,
     * then writes them to the DB and refreshes the table on user confirmation.
     */
    private void confirmAndApplyCalories(List<Integer> parsedCalories,
                                          List<Map<String, String>> selectedFoods) {
        int count = Math.min(parsedCalories.size(), selectedFoods.size());
        StringBuilder sb = new StringBuilder("Apply these calorie values to your food notes?\n\n");
        for (int i = 0; i < count; i++) {
            String foodName = selectedFoods.get(i).getOrDefault("food", "(unknown)");
            sb.append(foodName).append(": ").append(parsedCalories.get(i)).append(" cal\n");
        }
        if (parsedCalories.size() < selectedFoods.size()) {
            sb.append("\n(").append(selectedFoods.size() - parsedCalories.size())
              .append(" note(s) without a parsed value will not be changed.)");
        }

        new AlertDialog.Builder(this)
                .setTitle("Confirm Apply")
                .setMessage(sb.toString())
                .setPositiveButton("Apply", (d, w) -> {
                    int updated = 0;
                    for (int i = 0; i < count; i++) {
                        String noteIdStr = selectedFoods.get(i).get("note_id");
                        if (noteIdStr == null) continue;
                        try {
                            int noteId = Integer.parseInt(noteIdStr);
                            int cal    = parsedCalories.get(i);
                            // Update only calories — leave date, food name, quantity unchanged
                            int rows = databaseHelper.updateFoodNoteCaloriesOnly(noteId, cal);
                            if (rows > 0) updated++;
                            android.util.Log.d("FoodNoteAI", "Updated note id=" + noteId
                                    + " calories=" + cal + " rows=" + rows);
                        } catch (NumberFormatException e) {
                            android.util.Log.w("FoodNoteAI", "Invalid note_id: " + noteIdStr);
                        }
                    }
                    loadFoodNotesFromDatabase();
                    Toast.makeText(this, updated + " note(s) updated with AI calorie values.",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
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
            int stepChallenge = StepChallengeFun(Math.abs(result));
            message = "You have 0 Calories left in your Kitty and have the additional challenge of doing "
                    + stepChallenge + " steps by 9:59 PM.";
            if (stepChallenge >= 30_000) {
                message += "\n\nStep Challenge capped at 30,000! Consider using Accrual to lighten the load.";
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Kitty Report")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Generates Step Challenge: adds 250 bonus points, converts calories to steps (1 step = 0.089 cal).
     * Capped at 30,000 steps.
     */
    private int StepChallengeFun(int calories) {
        int targetCalories = calories + 250;
        int steps = (int) (targetCalories / 0.089);
        if (steps > 30_000) {
            steps = 30_000;
        }
        return steps;
    }

    /**
     * Opens the nearest/closest AI app available on the device.
     * Priority order: ChatGPT -> Gemini -> Claude -> Copilot -> Google Assistant -> Browser fallback
     */
    private void openNearestAiApp() {
        // List of AI app package names in priority order
        String[][] aiApps = {
            {"com.openai.chatgpt", "ChatGPT"},
            {"com.google.android.apps.bard", "Gemini"},
            {"com.anthropic.claude", "Claude"},
            {"com.microsoft.copilot", "Copilot"}
        };

        // Try to open each AI app in order
        for (String[] app : aiApps) {
            String packageName = app[0];
            String appName = app[1];
            try {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    android.util.Log.d("NearestAI", "Opening " + appName);
                    Toast.makeText(this, "Opening " + appName + "...", Toast.LENGTH_SHORT).show();
                    startActivity(launchIntent);
                    return;
                }
            } catch (Exception e) {
                android.util.Log.w("NearestAI", "Could not query " + packageName + ": " + e.getMessage());
            }
        }

        // No AI app found — try Google Assistant as fallback
        android.util.Log.d("NearestAI", "No AI app found, trying Google Assistant");
        if (tryOpenGoogleAssistant()) return;

        // Google Assistant also not available — show browser/install dialog
        showAiAppNotFoundDialog();
    }

    /**
     * Attempts to launch Google Assistant.
     * Returns true if successfully launched, false otherwise.
     */
    private boolean tryOpenGoogleAssistant() {
        // Primary: ACTION_ASSIST (standard assistant intent)
        Intent assistIntent = new Intent(Intent.ACTION_ASSIST);
        if (assistIntent.resolveActivity(getPackageManager()) != null) {
            android.util.Log.d("NearestAI", "Launching Google Assistant via ACTION_ASSIST");
            Toast.makeText(this, "Opening Google Assistant...", Toast.LENGTH_SHORT).show();
            startActivity(assistIntent);
            return true;
        }

        // Fallback: ACTION_VOICE_COMMAND
        Intent voiceIntent = new Intent(Intent.ACTION_VOICE_COMMAND);
        voiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (voiceIntent.resolveActivity(getPackageManager()) != null) {
            android.util.Log.d("NearestAI", "Launching Google Assistant via ACTION_VOICE_COMMAND");
            Toast.makeText(this, "Opening Google Assistant...", Toast.LENGTH_SHORT).show();
            startActivity(voiceIntent);
            return true;
        }

        android.util.Log.w("NearestAI", "Google Assistant not available on this device");
        return false;
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
     * 1. After 3:30 PM and user hasn't selected a date for the current period
     * 2. First-time user (no date ever selected)
     * Before 3:30 PM, user can continue with the previously selected date.
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

        // Only show automatic dialog if it's after 3:30 PM (15:30)
        int currentMinute = now.get(Calendar.MINUTE);
        if (currentHour > 15 || (currentHour == 15 && currentMinute >= 30)) {
            android.util.Log.d("DATE_PICKER", "After 3:30 PM");
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
            // Before 3:30 PM, just show last selected date
            android.util.Log.d("DATE_PICKER", "Before 3:30 PM - showing last selected date");
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
     * The user can select a date once per day. After 3:30 PM the next day, they can select again.
     *
     * Logic:
     * 1. Get current time
     * 2. Calculate the "reset boundary" - today at 3:30 PM (15:30)
     * 3. If current time < 3:30 PM, use yesterday at 3:30 PM as the boundary
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

        // Calculate the 3:30 PM (15:30) boundary
        Calendar resetBoundary = Calendar.getInstance();
        resetBoundary.set(Calendar.HOUR_OF_DAY, 15);
        resetBoundary.set(Calendar.MINUTE, 30);
        resetBoundary.set(Calendar.SECOND, 0);
        resetBoundary.set(Calendar.MILLISECOND, 0);

        // If current time is before 3:30 PM today, use yesterday's 3:30 PM as boundary
        if (now.before(resetBoundary)) {
            android.util.Log.d("DATE_PICKER", "Before 3:30 PM - adjusting boundary to yesterday");
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
     * Format: "Food Notes between 3:30pm [previous_day] and 3:30pm [selected_day]"
     * Example: User selects "10 OCT 25" → "Food Notes between 3:30pm 9 OCT and 3:30pm 10 OCT 25"
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
                String title = "Food Notes between 3:30pm " + previousDayStr + " and 3:30pm " + selectedDayStr;
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

    // ==================================================================================
    // 4PM FOOD NOTES PROCESSING WORKFLOW
    // ==================================================================================

    /**
     * Entry point for the 4PM processing workflow.
     * Performs idempotent guard, fetches previous-day notes, then starts AI enrichment.
     * Safe to call from the manual button or from the notification intent.
     */
    private void start4PMProcessingWorkflow() {
        String todayDate = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                .format(new java.util.Date());

        // Idempotent guard — one processing per calendar day
        if (databaseHelper.isAlreadyProcessedForDate(todayDate)) {
            new AlertDialog.Builder(this)
                    .setTitle("Already Processed Today")
                    .setMessage("4PM Food Notes have already been processed for today (" + todayDate + ").\n\n"
                            + "Each day can only be processed once to prevent duplicate balance updates.")
                    .setPositiveButton("OK", (d, w) -> d.dismiss())
                    .show();
            return;
        }

        // Fetch yesterday's unprocessed notes
        java.util.List<FoodNoteProcessingItem> notes = databaseHelper.getPreviousDayFoodNotes();

        if (notes.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("No Notes Found")
                    .setMessage("No unprocessed food notes found for yesterday.\n\n"
                            + "Add food notes during the day, then use the 4PM button at or after 4:00 PM "
                            + "to process them.")
                    .setPositiveButton("OK", (d, w) -> d.dismiss())
                    .show();
            return;
        }

        // Count notes with missing calories
        int missingCaloriesCount = 0;
        for (FoodNoteProcessingItem note : notes) {
            if (note.originalCalories == null || note.originalCalories.trim().isEmpty()
                    || note.originalCalories.trim().equals("0")) {
                missingCaloriesCount++;
            }
        }

        String previewMessage = "Found " + notes.size() + " food note(s) from yesterday.\n";
        if (missingCaloriesCount > 0) {
            previewMessage += "AI will estimate calories for " + missingCaloriesCount + " note(s) with missing values.\n";
        } else {
            previewMessage += "All notes already have calorie values.\n";
        }
        previewMessage += "\nProceed with processing?";

        final String confirmMsg = previewMessage;
        new AlertDialog.Builder(this)
                .setTitle("4PM Food Notes Processing")
                .setMessage(confirmMsg)
                .setPositiveButton("Start Processing", (d, w) -> {
                    d.dismiss();
                    execute4PMProcessing(notes);
                })
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .show();
    }

    /**
     * Shows a progress dialog and drives sequential AI enrichment of all notes.
     * When done, transitions to the review dialog.
     */
    private void execute4PMProcessing(java.util.List<FoodNoteProcessingItem> notes) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Preparing to process " + notes.size() + " note(s)...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        processNotesSequentiallyFrom(notes, 0, progressDialog, () -> {
            progressDialog.dismiss();

            // Calculate totals
            int totalCalories = 0;
            int totalPoints = 0;
            for (FoodNoteProcessingItem note : notes) {
                totalCalories += note.finalCalories;
                totalPoints += note.finalPoints;
            }

            android.util.Log.d("4PM_WORKFLOW", "All notes processed. totalCalories=" + totalCalories
                    + ", totalPoints=" + totalPoints);

            show4PMReviewDialog(notes, totalCalories, totalPoints);
        });
    }

    /**
     * Processes one note at a time, recursing through the list via AI callbacks.
     * Notes that already have calorie values are passed through without an AI call.
     * If AI fails for a note, processing continues and the note retains its original value.
     *
     * @param notes          Full list of notes to process.
     * @param index          Current position in the list.
     * @param progressDialog Dialog to update with current progress.
     * @param onAllDone      Runnable called when all notes are processed.
     */
    private void processNotesSequentiallyFrom(java.util.List<FoodNoteProcessingItem> notes,
                                               int index,
                                               android.app.ProgressDialog progressDialog,
                                               Runnable onAllDone) {
        if (index >= notes.size()) {
            onAllDone.run();
            return;
        }

        FoodNoteProcessingItem note = notes.get(index);
        progressDialog.setMessage("Processing " + (index + 1) + " / " + notes.size() + ":\n" + note.food);

        boolean caloriesMissing = (note.originalCalories == null
                || note.originalCalories.trim().isEmpty()
                || note.originalCalories.trim().equals("0"));

        if (!caloriesMissing) {
            // Calories already present — no AI call needed
            try {
                note.finalCalories = Integer.parseInt(note.originalCalories.trim());
            } catch (NumberFormatException nfe) {
                android.util.Log.w("4PM_WORKFLOW", "Non-integer calories for note " + note.noteId
                        + " (" + note.originalCalories + ") — defaulting to 0");
                note.finalCalories = 0;
            }
            note.finalPoints = note.finalCalories;
            processNotesSequentiallyFrom(notes, index + 1, progressDialog, onAllDone);
            return;
        }

        // Build AI prompt — reuses the same pattern as existing btnAI in showFoodInputDialog
        StringBuilder promptBuilder = new StringBuilder("How many calories are in ");
        if (note.quantity != null && !note.quantity.trim().isEmpty()) {
            promptBuilder.append(note.quantity.trim()).append(" of ");
        } else {
            promptBuilder.append("a typical serving of ");
        }
        promptBuilder.append(note.food).append(
                "? Respond with ONLY a single integer number representing the total calories. "
                        + "No text, no units, just the number.");

        android.util.Log.d("4PM_AI", "Calling AI for note " + note.noteId + " (" + note.food + ")");

        GeminiApiService.calculateCalories(this, promptBuilder.toString(), result -> {
            android.util.Log.d("4PM_AI", "AI result for '" + note.food + "': " + result);

            if (result != null && !result.trim().isEmpty()) {
                java.util.regex.Matcher matcher = java.util.regex.Pattern
                        .compile("\\d+(\\.\\d+)?")
                        .matcher(result.trim());
                if (matcher.find()) {
                    try {
                        int aiCalories = (int) Math.round(Double.parseDouble(matcher.group()));
                        // Sanity bounds: 1–10000 kcal
                        if (aiCalories >= 1 && aiCalories <= 10000) {
                            note.finalCalories = aiCalories;
                            note.finalPoints = aiCalories;
                            note.aiUpdatedFields = "calories";
                            note.aiProcessed = true;
                            android.util.Log.d("4PM_AI", "AI filled calories=" + aiCalories
                                    + " for '" + note.food + "'");
                        } else {
                            android.util.Log.w("4PM_AI", "AI returned out-of-range value " + aiCalories
                                    + " for '" + note.food + "' — discarding");
                        }
                    } catch (NumberFormatException nfe) {
                        android.util.Log.e("4PM_AI", "Failed to parse AI number for '" + note.food + "'");
                    }
                } else {
                    android.util.Log.w("4PM_AI", "No numeric value found in AI response for '"
                            + note.food + "'");
                }
            } else {
                android.util.Log.w("4PM_AI", "AI returned null/empty for '" + note.food + "' — skipping");
            }

            // Continue regardless of success or failure
            processNotesSequentiallyFrom(notes, index + 1, progressDialog, onAllDone);
        });
    }

    /**
     * Shows the 4PM review dialog with the processed notes list, totals,
     * and Confirm / Edit Again / Cancel buttons.
     */
    private void show4PMReviewDialog(java.util.List<FoodNoteProcessingItem> notes,
                                      int totalCalories, int totalPoints) {
        android.view.View dialogView = android.view.LayoutInflater.from(this)
                .inflate(R.layout.dialog_4pm_review, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog reviewDialog = builder.create();

        LinearLayout llContainer = dialogView.findViewById(R.id.llNotesContainer);
        TextView tvTotalCalories = dialogView.findViewById(R.id.tvTotalCalories4PM);
        TextView tvTotalPoints = dialogView.findViewById(R.id.tvTotalPoints4PM);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm4PM);
        Button btnEditAgain = dialogView.findViewById(R.id.btnEditAgain4PM);
        Button btnCancelReview = dialogView.findViewById(R.id.btnCancel4PM);

        tvTotalCalories.setText(String.valueOf(totalCalories));
        tvTotalPoints.setText(String.valueOf(totalPoints));

        // Inflate one row per processed note
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(this);
        for (FoodNoteProcessingItem note : notes) {
            android.view.View rowView = inflater.inflate(R.layout.item_4pm_food_note, llContainer, false);

            TextView tvFood = rowView.findViewById(R.id.tvFoodName4PM);
            TextView tvCal = rowView.findViewById(R.id.tvCalories4PM);
            TextView tvPts = rowView.findViewById(R.id.tvPoints4PM);
            TextView tvAiFlag = rowView.findViewById(R.id.tvAiFlag4PM);

            tvFood.setText(note.food);
            tvCal.setText(String.valueOf(note.finalCalories));
            tvPts.setText(String.valueOf(note.finalPoints));

            if (note.aiProcessed) {
                // Highlight AI-filled value in orange to distinguish from user-entered data
                tvCal.setTextColor(android.graphics.Color.parseColor("#E65100"));
                tvAiFlag.setVisibility(View.VISIBLE);
            }

            llContainer.addView(rowView);

            // Thin divider between rows
            View divider = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            lp.setMargins(0, 0, 0, 0);
            divider.setLayoutParams(lp);
            divider.setBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"));
            llContainer.addView(divider);
        }

        btnConfirm.setOnClickListener(v -> {
            reviewDialog.dismiss();
            confirm4PMProcessing(notes, totalCalories, totalPoints);
        });

        btnEditAgain.setOnClickListener(v -> {
            reviewDialog.dismiss();
            Toast.makeText(this,
                    "Edit your food notes, then press '4PM' again to re-process.",
                    Toast.LENGTH_LONG).show();
        });

        btnCancelReview.setOnClickListener(v -> {
            reviewDialog.dismiss();
            Toast.makeText(this, "4PM processing cancelled. No changes saved.", Toast.LENGTH_SHORT).show();
        });

        reviewDialog.show();
    }

    /**
     * Called when the user taps Confirm in the review dialog.
     * Writes processing results to DB, updates the Countdown Balance,
     * and syncs to the backend.
     */
    private void confirm4PMProcessing(java.util.List<FoodNoteProcessingItem> notes,
                                       int totalCalories, int totalPoints) {
        String todayDate = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                .format(new java.util.Date());
        String todayDateTime = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", java.util.Locale.getDefault())
                .format(new java.util.Date());

        // Build note IDs JSON array for the log entry
        StringBuilder noteIdsJson = new StringBuilder("[");
        for (int i = 0; i < notes.size(); i++) {
            if (i > 0) noteIdsJson.append(",");
            noteIdsJson.append(notes.get(i).noteId);
        }
        noteIdsJson.append("]");

        final String finalNoteIdsJson = noteIdsJson.toString();

        // DB writes on background thread (following existing executorService pattern)
        executorService.submit(() -> {
            try {
                // Step 1: Persist AI-enriched values for each note
                for (FoodNoteProcessingItem note : notes) {
                    databaseHelper.update4PMNote(
                            note.noteId, note.finalCalories, note.finalPoints, note.aiUpdatedFields);
                }

                // Step 2: Insert idempotent processing log (CONFLICT_IGNORE prevents duplicates)
                long logId = databaseHelper.insert4PMProcessingResult(
                        todayDate, totalCalories, totalPoints, finalNoteIdsJson);
                android.util.Log.d("4PM_CONFIRM", "Processing log saved — logId=" + logId);
            } catch (Exception e) {
                android.util.Log.e("4PM_CONFIRM", "DB write error during confirm: " + e.getMessage());
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                // Step 3: Add to Main Countdown Balance
                boolean balanceUpdated = false;
                if (totalCalories > 0 && CCD_GUI_CD_CIF1.instance != null) {
                    CCD_GUI_CD_CIF1.instance.AddToBalance(String.valueOf(totalCalories));
                    balanceUpdated = true;
                    android.util.Log.d("4PM_CONFIRM", "AddToBalance(" + totalCalories + ") called");
                } else if (CCD_GUI_CD_CIF1.instance == null) {
                    android.util.Log.w("4PM_CONFIRM",
                            "CCD_GUI_CD_CIF1.instance is null — balance update deferred");
                }

                // Step 4: Best-effort backend sync
                sync4PMResultToBackend(notes, totalCalories, todayDateTime);

                // Step 5: Success message
                String successMsg = "Successfully processed " + notes.size() + " food note(s).\n\n"
                        + "Total Calories: " + totalCalories + "\n"
                        + "Total Points:   " + totalPoints + "\n\n";
                if (balanceUpdated) {
                    successMsg += "Countdown Balance updated!";
                } else if (totalCalories <= 0) {
                    successMsg += "No calories to add (total is 0).";
                } else {
                    successMsg += "Return to the main screen to see the updated balance.";
                }

                final String finalSuccessMsg = successMsg;
                final int finalTotalCalories = totalCalories;
                final String finalTodayDate = todayDate;
                new AlertDialog.Builder(this)
                        .setTitle("4PM Processing Complete")
                        .setMessage(finalSuccessMsg)
                        .setPositiveButton("View Step Challenge", (d, w) -> {
                            d.dismiss();
                            // Step 6: Chain Debit / Steps Challenge calculation
                            launch4PMDebitStepsChallenge(finalTotalCalories, finalTodayDate);
                        })
                        .setNeutralButton("OK", (d, w) -> d.dismiss())
                        .show();
            });
        });
    }

    /**
     * Entry point for the 4PM Debit / Steps Challenge calculation.
     *
     * <p>Reads the current countdown balance and the gender-based daily budget,
     * runs {@link FourPMDebitStepsProcessor#calculate}, persists the result,
     * and shows the summary dialog. Idempotent: if a challenge already exists
     * for today, the saved result is displayed instead of recalculating.</p>
     *
     * @param confirmedCalories Total calories confirmed during the 4PM credit processing.
     * @param processingDate    Today's date in {@code dd-MM-yyyy} format.
     */
    private void launch4PMDebitStepsChallenge(int confirmedCalories, String processingDate) {
        android.util.Log.d("4PM_DEBIT", "launch4PMDebitStepsChallenge: cal=" + confirmedCalories
                + ", date=" + processingDate);

        // Guard: if already calculated today, show the saved result
        if (databaseHelper.isDebitChallengeProcessedForDate(processingDate)) {
            android.util.Log.d("4PM_DEBIT", "Debit challenge already processed for " + processingDate
                    + " — loading saved result");
            FourPMDebitResult saved = databaseHelper.getLastDebitChallenge();
            if (saved != null) {
                show4PMDebitStepsDialog(saved);
            }
            return;
        }

        // Read current countdown balance (before today's credit is visible in main UI)
        int currentBalance = 0;
        try {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(this);
            String balanceStr = adapter.RetrieveBalance();
            if (balanceStr != null && !balanceStr.trim().isEmpty()) {
                currentBalance = Integer.parseInt(balanceStr.trim().replace(",", ""));
            }
        } catch (NumberFormatException nfe) {
            android.util.Log.w("4PM_DEBIT", "Could not parse balance — defaulting to 0");
        }

        // Read gender-based daily budget from SharedPreferences
        android.content.SharedPreferences pref =
                getSharedPreferences("Calorie_Countdown", MODE_PRIVATE);
        String gender = pref.getString("user_gender", "female");
        int dailyBudget = FourPMDebitStepsProcessor.resolveDailyBudget(gender);

        android.util.Log.d("4PM_DEBIT", "gender=" + gender + ", budget=" + dailyBudget
                + ", balance=" + currentBalance);

        // Calculate challenge on background thread; persist and show on main thread
        final int capturedBalance = currentBalance;
        final int capturedBudget  = dailyBudget;
        executorService.submit(() -> {
            FourPMDebitResult result = FourPMDebitStepsProcessor.calculate(
                    processingDate, confirmedCalories, capturedBudget, capturedBalance);

            long savedId = databaseHelper.saveDebitStepsChallenge(result);
            android.util.Log.d("4PM_DEBIT", "Challenge saved, db id=" + savedId
                    + ", result=" + result);

            runOnUiThread(() -> show4PMDebitStepsDialog(result));
        });
    }

    /**
     * Shows the 4PM Debit / Steps Challenge result dialog.
     *
     * <p>Highlights the step target in a large, bold TextView and provides context
     * on how the figure was derived. Includes an Accrual reminder when capped.</p>
     *
     * @param result The fully calculated {@link FourPMDebitResult} to display.
     */
    private void show4PMDebitStepsDialog(FourPMDebitResult result) {
        if (result == null) {
            android.util.Log.e("4PM_DEBIT", "show4PMDebitStepsDialog: result is null");
            return;
        }

        String title  = result.hasChallengeNeeded
                ? "Step Challenge — " + String.format("%,d", result.stepChallenge) + " steps"
                : "No Step Challenge Today!";

        String message = result.buildSummaryMessage();

        android.util.Log.d("4PM_DEBIT", "Showing debit dialog: " + title);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Got it!", (d, w) -> d.dismiss())
                .setNegativeButton("Details", (d, w) -> {
                    d.dismiss();
                    show4PMDebitDetailsDialog(result);
                })
                .show();
    }

    /**
     * Shows a detailed breakdown dialog for the Debit / Steps Challenge.
     * Gives the user a clear view of every number in the algorithm.
     */
    private void show4PMDebitDetailsDialog(FourPMDebitResult result) {
        StringBuilder detail = new StringBuilder();
        detail.append("--- Inputs ---\n");
        detail.append("Processing date:     ").append(result.processingDate).append("\n");
        detail.append("Food calories:       ").append(result.totalFoodCalories).append(" cal\n");
        detail.append("Daily budget:        ").append(result.dailyBudget).append(" cal\n");
        detail.append("Countdown balance:   ").append(result.currentBalance).append("\n\n");

        detail.append("--- Calculation ---\n");
        detail.append("Kitty excess:        ").append(result.kittyExcess).append(" cal");
        detail.append(result.kittyExcess > 0 ? " (over budget)\n" : " (within budget)\n");
        detail.append("Balance penalty:     ").append(result.balancePenalty).append(" cal\n");
        detail.append("Total excess:        ").append(result.excessCalories).append(" cal\n");
        if (result.hasChallengeNeeded) {
            detail.append("+ Bonus penalty:     ").append(FourPMDebitStepsProcessor.BONUS_PENALTY_CALORIES).append(" cal\n");
            detail.append("Target:              ")
                  .append(result.excessCalories + FourPMDebitStepsProcessor.BONUS_PENALTY_CALORIES)
                  .append(" cal\n");
            detail.append("Steps (target/0.089): ").append(String.format("%,d", result.stepChallenge));
            if (result.isCapped) detail.append(" (CAPPED at 30 000)");
            detail.append("\n");
        }

        detail.append("\n--- Formula ---\n");
        detail.append("steps = (excess + 250) / 0.089\ncapped at 30 000 max.");

        new AlertDialog.Builder(this)
                .setTitle("Step Challenge Breakdown")
                .setMessage(detail.toString())
                .setPositiveButton("Close", (d, w) -> d.dismiss())
                .show();
    }

    /**
     * Syncs the 4PM processing result to the Azure backend.
     * Uses the existing addFoodItem endpoint with a processing-result marker.
     * Failures are logged and silently swallowed — local data is the source of truth.
     */
    private void sync4PMResultToBackend(java.util.List<FoodNoteProcessingItem> notes,
                                         int totalCalories, String dateTime) {
        java.util.Map<String, Object> syncData = new java.util.HashMap<>();
        syncData.put("food_item_name", "4PM_Processing_" + dateTime.replace(":", "-"));
        syncData.put("calories_per_100g", (double) totalCalories);
        syncData.put("note_date", dateTime);
        syncData.put("quantity", notes.size() + "_notes_processed");
        syncData.put("food_type", "4PM_LOG");

        SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(this);
        apiClient.addFoodItem(syncData, new ApiResultCallback() {
            @Override
            public void onSuccess(String response) {
                android.util.Log.d("4PM_SYNC", "4PM processing result synced to backend");
            }

            @Override
            public void onFailure() {
                android.util.Log.w("4PM_SYNC",
                        "4PM backend sync failed — data safe locally, will not retry");
            }
        });
    }

    // ==================================================================================
    // END 4PM PROCESSING WORKFLOW
    // ==================================================================================

    /**
     * Checks if the previous day's debit update has been performed.
     * If not, shows a dialog prompting the user to complete it before adding new food notes.
     *
     * @return true if the user can proceed (debit done or no previous day), false if blocked
     */
    private boolean checkPreviousDayDebitUpdate() {
        CCD_GUI_CD_CIF1 mainActivity = CCD_GUI_CD_CIF1.instance;
        if (mainActivity == null) return true;

        if (!mainActivity.isPreviousDayDebitComplete()) {
            // Get client name
            String clientName = "Client";
            SharedPreferences pref = getSharedPreferences("Calorie_Countdown", 0);
            String name = pref.getString("client_name", null);
            if (name != null && !name.isEmpty()) {
                clientName = name;
            }

            String message = "Dear " + clientName + ",\n\n" +
                    "You did not update your Debit (that is Steps, Exercise and Physical Activity) " +
                    "performed yesterday.\n\n" +
                    "Please complete yesterday's Debit Update before adding new food notes.\n\n" +
                    "Use the Debit button on the main screen to enter your activity.";

            new AlertDialog.Builder(this)
                    .setTitle("Countdown Report")
                    .setMessage(message)
                    .setPositiveButton("Go to Debit Update", (dialog, which) -> {
                        // Take user back to main activity to do debit update
                        finish();
                    })
                    .setNegativeButton("Skip for now", (dialog, which) -> {
                        // Allow user to proceed anyway but warn them
                        Toast.makeText(this, "Remember to complete yesterday's debit update!", Toast.LENGTH_LONG).show();
                        // Don't block - let them add food note after dismissing
                    })
                    .setCancelable(false)
                    .show();

            return false;
        }

        return true;
    }

}
