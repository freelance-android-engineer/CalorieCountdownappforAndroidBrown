package ese.com.caloriecountdownappforandroidbrown;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewStrengthItemActivity extends AppCompatActivity {

    private static final String TAG = "NewStrengthItem";

    private EditText etName;
    private EditText etCalPerMin;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strength_item);

        etName      = findViewById(R.id.et_strength_item_name);
        etCalPerMin = findViewById(R.id.et_strength_cal_per_min);
        btnSave     = findViewById(R.id.btn_save_strength_item);
        btnCancel   = findViewById(R.id.btn_cancel_strength_item);

        btnSave.setOnClickListener(v -> saveStrengthItem());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveStrengthItem() {
        String name   = etName.getText().toString().trim();
        String calStr = etCalPerMin.getText().toString().trim();

        // ── Validation ───────────────────────────────────────────────────────
        if (name.isEmpty()) {
            etName.setError(getString(R.string.new_strength_error_name_empty));
            etName.requestFocus();
            return;
        }

        if (calStr.isEmpty()) {
            etCalPerMin.setError(getString(R.string.new_strength_error_cal_empty));
            etCalPerMin.requestFocus();
            return;
        }

        float calPerMin;
        try {
            calPerMin = Float.parseFloat(calStr);
        } catch (NumberFormatException e) {
            etCalPerMin.setError(getString(R.string.new_strength_error_cal_invalid));
            etCalPerMin.requestFocus();
            return;
        }

        if (calPerMin <= 0) {
            etCalPerMin.setError(getString(R.string.new_strength_error_cal_positive));
            etCalPerMin.requestFocus();
            return;
        }

        // ── Loading state ────────────────────────────────────────────────────
        btnSave.setEnabled(false);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.new_strength_saving));
        progress.setCancelable(false);
        progress.show();

        // ── Save on background thread ────────────────────────────────────────
        final float finalCalPerMin = calPerMin;
        new Thread(() -> {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(this);

            // Check duplicate
            if (adapter.exerciseItemExists(ExerciseItem.CATEGORY_STRENGTH, name)) {
                runOnUiThread(() -> {
                    progress.dismiss();
                    btnSave.setEnabled(true);
                    etName.setError("\"" + name + "\" already exists.");
                    etName.requestFocus();
                });
                return;
            }

            // Insert locally: durationMinutes=1 as default, reps=0, user-provided cal/min
            long localRowId = adapter.addExerciseItem(
                    ExerciseItem.CATEGORY_STRENGTH, name, 1f, 0, finalCalPerMin);
            Log.d(TAG, "Local insert rowId=" + localRowId + " name=" + name);

            if (localRowId == -1L) {
                runOnUiThread(() -> {
                    progress.dismiss();
                    btnSave.setEnabled(true);
                    Toast.makeText(this,
                            getString(R.string.new_strength_error_save_failed),
                            Toast.LENGTH_LONG).show();
                });
                return;
            }

            // ── Sync to backend ──────────────────────────────────────────────
            if (NetworkUtil.isInternetAvailable(this)) {
                SharedPreferences prefs = getSharedPreferences("Calorie_Countdown", 0);
                String clientName = prefs.getString("client_name", "Client");
                if (clientName == null || clientName.isEmpty()) clientName = "Client";

                SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(this);
                apiClient.addExerciseItem(clientName, ExerciseItem.CATEGORY_STRENGTH,
                        name, finalCalPerMin, new ApiResultCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Backend sync OK for: " + name);
                        runOnUiThread(() -> {
                            progress.dismiss();
                            Toast.makeText(NewStrengthItemActivity.this,
                                    getString(R.string.new_strength_success),
                                    Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        });
                    }

                    @Override
                    public void onFailure() {
                        Log.w(TAG, "Backend sync failed for: " + name + " — saved locally.");
                        runOnUiThread(() -> {
                            progress.dismiss();
                            Toast.makeText(NewStrengthItemActivity.this,
                                    getString(R.string.new_strength_success_offline),
                                    Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        });
                    }
                });
            } else {
                Log.w(TAG, "Offline — saved locally only: " + name);
                runOnUiThread(() -> {
                    progress.dismiss();
                    Toast.makeText(this,
                            getString(R.string.new_strength_success_offline),
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });
            }
        }).start();
    }
}
