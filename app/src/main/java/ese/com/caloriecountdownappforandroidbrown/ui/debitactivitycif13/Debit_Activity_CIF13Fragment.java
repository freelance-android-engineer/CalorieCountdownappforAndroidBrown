package ese.com.caloriecountdownappforandroidbrown.ui.debitactivitycif13;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ese.com.caloriecountdownappforandroidbrown.ExerciseItem;
import ese.com.caloriecountdownappforandroidbrown.Fitness_Item_CIF5;
import ese.com.caloriecountdownappforandroidbrown.GeminiApiService;
import ese.com.caloriecountdownappforandroidbrown.MIF4_Data_Model_Adapter;
import ese.com.caloriecountdownappforandroidbrown.R;
import ese.com.caloriecountdownappforandroidbrown.RoundingCIF13;
import ese.com.caloriecountdownappforandroidbrown.SummaryBoxCIF12;

public class Debit_Activity_CIF13Fragment extends Fragment {

    private DebitActivityCIF13ViewModel mViewModel;
    public static final String TOTAL_DEBIT_VALUE = "Total Debit Countdown Value";

    private Button mDebit;
    private Button mCancel;
    private Button mCardio;
    private Button mStrength;
    private Button mAiButton;
    private Fitness_Item_CIF5 mCountdown;

    public static Debit_Activity_CIF13Fragment newInstance() {
        return new Debit_Activity_CIF13Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.debit__activity__c_i_f13_fragment, container, false);

        mDebit    = v.findViewById(R.id.button12);
        mCancel   = v.findViewById(R.id.button13);
        mCardio   = v.findViewById(R.id.button_cardio);
        mStrength = v.findViewById(R.id.button_strength);
        mAiButton = v.findViewById(R.id.button_ai);

        // ── Existing Debit button ─────────────────────────────────────────────
        mDebit.setOnClickListener(view -> {
            try {
                EditText editText63 = v.findViewById(R.id.edit_text63);
                EditText editText64 = v.findViewById(R.id.edit_text64);
                Spinner  spinner    = v.findViewById(R.id.spincity);

                if (editText63 == null || editText63.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter your weight in lbs.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editText64 == null || editText64.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter minutes or reps performed.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinner == null || spinner.getSelectedItem() == null) {
                    Toast.makeText(requireContext(), "Please select an activity.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mCountdown = new Fitness_Item_CIF5();
                mCountdown.setmUserWeightlbs(
                        new RoundingCIF13().StringToFloat(editText63.getText().toString().trim()));
                mCountdown.setmMinutesPerformed(
                        (int) new RoundingCIF13().StringToFloat(editText64.getText().toString().trim()));
                mCountdown.ConvertSpinnerItem(spinner.getSelectedItem().toString());

                BackToParent(GetCountdownDebit(mCountdown));

            } catch (NumberFormatException e) {
                android.util.Log.e("DebitCIF13", "NFE: " + e.getMessage(), e);
                Toast.makeText(requireContext(),
                        "Invalid number entered. Please check weight and minutes fields.",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                android.util.Log.e("DebitCIF13", "Debit error: " + e.getMessage(), e);
                Toast.makeText(requireContext(),
                        "Error processing debit: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // ── Cancel ────────────────────────────────────────────────────────────
        mCancel.setOnClickListener(view -> {
            if (getActivity() != null) {
                getActivity().setResult(AppCompatActivity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        // ── Cardio button ─────────────────────────────────────────────────────
        mCardio.setOnClickListener(view ->
                showExerciseDialog(ExerciseItem.CATEGORY_CARDIO, "Cardio"));

        // ── Strength Training button ──────────────────────────────────────────
        mStrength.setOnClickListener(view ->
                showExerciseDialog(ExerciseItem.CATEGORY_STRENGTH, "Strength Training"));

        // ── AI Debit button ───────────────────────────────────────────────────
        mAiButton.setOnClickListener(view -> showAiDebitInputDialog());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DebitActivityCIF13ViewModel.class);
    }

    // ── Exercise Dialog ───────────────────────────────────────────────────────

    /**
     * Shows a dialog listing all exercise items for the given category.
     * The user can check items, see total calories, add new items, and apply.
     */
    private void showExerciseDialog(String category, String displayName) {
        if (!isAdded() || getContext() == null) return;

        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_exercise_list, null, false);

        TextView titleView     = dialogView.findViewById(R.id.tv_exercise_dialog_title);
        LinearLayout container = dialogView.findViewById(R.id.ll_exercise_items_container);
        TextView totalView     = dialogView.findViewById(R.id.tv_exercise_total_calories);
        Button btnAdd          = dialogView.findViewById(R.id.btn_add_exercise_item);
        Button btnApply        = dialogView.findViewById(R.id.btn_exercise_apply);
        Button btnCancel       = dialogView.findViewById(R.id.btn_exercise_cancel);

        titleView.setText(displayName + " Exercises");
        btnAdd.setText(category.equals(ExerciseItem.CATEGORY_CARDIO)
                ? getString(R.string.btn_add_cardio_item)
                : getString(R.string.btn_add_strength_item));

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // Tracks checkboxes parallel to the loaded items list
        final List<ExerciseItem> loadedItems  = new ArrayList<>();
        final List<CheckBox>     checkBoxes   = new ArrayList<>();

        // Reload helper — refreshes the list inside the dialog
        Runnable reloadList = () -> {
            container.removeAllViews();
            loadedItems.clear();
            checkBoxes.clear();

            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(requireContext());
            List<ExerciseItem> items = adapter.getExerciseItems(category);
            loadedItems.addAll(items);

            if (items.isEmpty()) {
                TextView empty = new TextView(requireContext());
                empty.setText("No items found. Tap \"Add New Item\" to create one.");
                empty.setPadding(8, 8, 8, 8);
                container.addView(empty);
            }

            for (ExerciseItem item : items) {
                CheckBox cb = new CheckBox(requireContext());
                cb.setText(item.getDisplayText());
                cb.setPadding(4, 8, 4, 8);
                cb.setOnCheckedChangeListener((btn, checked) ->
                        updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView));
                container.addView(cb);
                checkBoxes.add(cb);
            }
            updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView);
        };

        // Load items on a background thread, then populate UI on main thread
        new Thread(() -> {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(requireContext());
            List<ExerciseItem> items = adapter.getExerciseItems(category);
            new Handler(Looper.getMainLooper()).post(() -> {
                if (!isAdded() || getContext() == null) return;
                loadedItems.addAll(items);

                if (items.isEmpty()) {
                    TextView empty = new TextView(requireContext());
                    empty.setText("No items found. Tap \"Add New Item\" to create one.");
                    empty.setPadding(8, 8, 8, 8);
                    container.addView(empty);
                }

                for (ExerciseItem item : items) {
                    CheckBox cb = new CheckBox(requireContext());
                    cb.setText(item.getDisplayText());
                    cb.setPadding(4, 8, 4, 8);
                    cb.setOnCheckedChangeListener((btn, checked) ->
                            updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView));
                    container.addView(cb);
                    checkBoxes.add(cb);
                }
                updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView);
            });
        }).start();

        // ── Add New Item ──────────────────────────────────────────────────────
        btnAdd.setOnClickListener(v ->
                showAddItemDialog(category, displayName, () -> {
                    // Reload the list after a new item is saved
                    new Thread(() -> {
                        MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(requireContext());
                        List<ExerciseItem> fresh = adapter.getExerciseItems(category);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            if (!isAdded() || getContext() == null) return;
                            container.removeAllViews();
                            loadedItems.clear();
                            checkBoxes.clear();
                            loadedItems.addAll(fresh);
                            for (ExerciseItem item : fresh) {
                                CheckBox cb = new CheckBox(requireContext());
                                cb.setText(item.getDisplayText());
                                cb.setPadding(4, 8, 4, 8);
                                cb.setOnCheckedChangeListener((btn, checked) ->
                                        updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView));
                                container.addView(cb);
                                checkBoxes.add(cb);
                            }
                            updateTotalCaloriesLabel(loadedItems, checkBoxes, totalView);
                        });
                    }).start();
                }));

        // ── Apply Selected ────────────────────────────────────────────────────
        btnApply.setOnClickListener(v -> {
            int total = computeSelectedCalories(loadedItems, checkBoxes);
            if (total == 0) {
                Toast.makeText(requireContext(),
                        "Please select at least one exercise item.", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.dismiss();
            BackToParent(total);
        });

        // ── Cancel ────────────────────────────────────────────────────────────
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Shows a small input dialog to add a new exercise item.
     * Validates name uniqueness and saves to DB on a background thread.
     */
    private void showAddItemDialog(String category, String displayName, Runnable onSaved) {
        if (!isAdded() || getContext() == null) return;

        boolean isCardio = ExerciseItem.CATEGORY_CARDIO.equals(category);

        LinearLayout inputLayout = new LinearLayout(requireContext());
        inputLayout.setOrientation(LinearLayout.VERTICAL);
        int pad = 48;
        inputLayout.setPadding(pad, pad / 2, pad, 0);

        // Name field
        EditText etName = new EditText(requireContext());
        etName.setHint("Exercise name (e.g. " + (isCardio ? "Treadmill" : "Arnold Press") + ")");
        etName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        inputLayout.addView(etName);

        // Duration or Reps field
        EditText etValue = new EditText(requireContext());
        etValue.setHint(isCardio ? "Duration (minutes, e.g. 20)" : "Reps (e.g. 12)");
        etValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputLayout.addView(etValue);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add " + displayName + " Item")
                .setView(inputLayout)
                .setPositiveButton("Save", (dlg, which) -> {
                    String name  = etName.getText().toString().trim();
                    String sVal  = etValue.getText().toString().trim();

                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (sVal.isEmpty()) {
                        Toast.makeText(requireContext(),
                                isCardio ? "Please enter duration in minutes." : "Please enter rep count.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    float numericVal;
                    try {
                        numericVal = Float.parseFloat(sVal);
                    } catch (NumberFormatException e) {
                        Toast.makeText(requireContext(), "Invalid number.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    float durationMin = isCardio  ? numericVal : 0f;
                    int   reps        = isCardio  ? 0 : (int) numericVal;
                    // Default calorie rates: 8 cal/min for cardio, 0.5 cal/rep for strength
                    float calPerUnit  = isCardio  ? 8.0f : 0.5f;

                    new Thread(() -> {
                        MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(requireContext());
                        long result = adapter.addExerciseItem(category, name, durationMin, reps, calPerUnit);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            if (!isAdded() || getContext() == null) return;
                            if (result == -1L) {
                                Toast.makeText(requireContext(),
                                        "\"" + name + "\" already exists in " + displayName + ".",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(requireContext(),
                                        "\"" + name + "\" added successfully.",
                                        Toast.LENGTH_SHORT).show();
                                if (onSaved != null) onSaved.run();
                            }
                        });
                    }).start();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Sums calories of all checked items. */
    private int computeSelectedCalories(List<ExerciseItem> items, List<CheckBox> boxes) {
        int total = 0;
        for (int i = 0; i < boxes.size() && i < items.size(); i++) {
            if (boxes.get(i).isChecked()) {
                total += items.get(i).calculateCalories();
            }
        }
        return total;
    }

    /** Updates the "Selected: X cal" label whenever a checkbox changes. */
    private void updateTotalCaloriesLabel(List<ExerciseItem> items, List<CheckBox> boxes,
                                          TextView totalView) {
        int total = computeSelectedCalories(items, boxes);
        totalView.setText("Selected: " + total + " cal");
    }

    private int GetCountdownDebit(Fitness_Item_CIF5 fizz) {
        int debit = fizz.CalculateCountdown();
        SummaryBoxCIF12 summy = SummaryBoxCIF12.get(requireActivity());
        summy.Set_mFitnessItems(fizz);
        return debit;
    }

    private void BackToParent(int debit) {
        if (getActivity() == null) return;
        Intent i2 = new Intent();
        i2.putExtra(TOTAL_DEBIT_VALUE, debit);
        getActivity().setResult(AppCompatActivity.RESULT_OK, i2);
        getActivity().finish();
    }

    // ── AI Debit ──────────────────────────────────────────────────────────────

    /** Shows an input dialog so the user can describe their activity before calling the AI. */
    private void showAiDebitInputDialog() {
        if (!isAdded() || getContext() == null) return;

        EditText etActivity = new EditText(requireContext());
        etActivity.setHint("e.g. 30 min run, 1 hour cycling");
        etActivity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        int pad = 48;
        etActivity.setPadding(pad, pad / 2, pad, pad / 2);

        new AlertDialog.Builder(requireContext())
                .setTitle("AI Calorie Debit")
                .setMessage("Describe your activity and AI will estimate calories burned:")
                .setView(etActivity)
                .setPositiveButton("Calculate", (dlg, which) -> {
                    String activity = etActivity.getText().toString().trim();
                    if (activity.isEmpty()) {
                        Toast.makeText(requireContext(),
                                "Please describe your activity.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    runAiDebit(activity);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Calls the existing AI service with a calorie-burn prompt and applies the result as a debit. */
    private void runAiDebit(String activityDescription) {
        if (!isAdded() || getActivity() == null) return;

        android.app.ProgressDialog progress = new android.app.ProgressDialog(requireContext());
        progress.setMessage("AI is estimating calories burned...");
        progress.setCancelable(false);
        progress.show();

        String prompt = "How many calories would I burn doing " + activityDescription
                + "? Respond with ONLY a single integer number representing the total calories burned."
                + " No text, no units, just the number.";

        GeminiApiService.calculateCalories(requireActivity(), prompt, new GeminiApiService.CalorieCallback() {
            @Override
            public void onResult(String result) {
                if (!isAdded() || getContext() == null) {
                    progress.dismiss();
                    return;
                }
                progress.dismiss();
                if (result == null || result.trim().isEmpty()) {
                    android.util.Log.e("DebitAI", "AI returned null or empty");
                    Toast.makeText(requireContext(),
                            "AI estimation failed. Please check your connection and try again.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                java.util.regex.Matcher matcher = java.util.regex.Pattern
                        .compile("\\d+(\\.\\d+)?")
                        .matcher(result.trim());
                if (!matcher.find()) {
                    android.util.Log.w("DebitAI", "No number found in AI response: " + result.trim());
                    Toast.makeText(requireContext(),
                            "Could not parse AI response. Please try again.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    int debitValue = (int) Math.round(Double.parseDouble(matcher.group()));
                    if (debitValue <= 0) {
                        Toast.makeText(requireContext(),
                                "AI returned an invalid value. Please try again.",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    BackToParent(debitValue);
                } catch (NumberFormatException e) {
                    android.util.Log.e("DebitAI", "Number parse failed: " + result.trim());
                    Toast.makeText(requireContext(),
                            "Could not parse AI response. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
