package ese.com.caloriecountdownappforandroidbrown;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BloodSugarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private SQLDatabase_Food_Items_CIF6 dbHelper;
    private BloodSugarAdapter adapter;
    private List<HealthReadingModel> readings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Blood Sugar");
        }

        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        dbHelper = new SQLDatabase_Food_Items_CIF6(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddEditDialog(null));

        loadReadings();
    }

    private void loadReadings() {
        readings = dbHelper.getAllBloodSugar();
        if (readings.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);
        }
        adapter = new BloodSugarAdapter(readings);
        recyclerView.setAdapter(adapter);
    }

    private void showAddEditDialog(final HealthReadingModel existing) {
        final boolean isEdit = (existing != null);
        Context ctx = this;
        int dpPadding = (int) (16 * getResources().getDisplayMetrics().density);

        final Calendar cal = Calendar.getInstance();
        final String[] selectedDate = {
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime())
        };
        final String[] selectedTime = {
                new SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.getTime())
        };

        if (isEdit) {
            selectedDate[0] = existing.getDate();
            String t = existing.getTime();
            selectedTime[0] = (t != null && t.length() >= 5) ? t.substring(0, 5) : t;
        }

        LinearLayout layout = new LinearLayout(ctx);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dpPadding, dpPadding, dpPadding, 8);

        // Date
        TextView labelDate = new TextView(ctx);
        labelDate.setText("Date:");
        layout.addView(labelDate);

        final TextView tvDate = new TextView(ctx);
        tvDate.setText(selectedDate[0]);
        tvDate.setTextColor(0xFF3F51B5);
        tvDate.setPadding(0, 8, 0, 16);
        tvDate.setClickable(true);
        tvDate.setFocusable(true);
        layout.addView(tvDate);

        tvDate.setOnClickListener(v -> new DatePickerDialog(ctx,
                (view, year, month, day) -> {
                    selectedDate[0] = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
                    tvDate.setText(selectedDate[0]);
                },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show());

        // Time
        TextView labelTime = new TextView(ctx);
        labelTime.setText("Time:");
        layout.addView(labelTime);

        final TextView tvTime = new TextView(ctx);
        tvTime.setText(selectedTime[0]);
        tvTime.setTextColor(0xFF3F51B5);
        tvTime.setPadding(0, 8, 0, 16);
        tvTime.setClickable(true);
        tvTime.setFocusable(true);
        layout.addView(tvTime);

        tvTime.setOnClickListener(v -> new TimePickerDialog(ctx,
                (view, hour, minute) -> {
                    selectedTime[0] = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                    tvTime.setText(selectedTime[0]);
                },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
        ).show());

        // mg/dL
        TextView labelMgdl = new TextView(ctx);
        labelMgdl.setText("Blood Sugar (mg/dL):");
        layout.addView(labelMgdl);

        final EditText etMgdl = new EditText(ctx);
        etMgdl.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMgdl.setHint("e.g. 110");
        if (isEdit) etMgdl.setText(String.valueOf(existing.getValue1()));
        layout.addView(etMgdl);

        new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Edit Blood Sugar" : "Add Blood Sugar")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String mgdlStr = etMgdl.getText().toString().trim();

                    if (mgdlStr.isEmpty()) {
                        Toast.makeText(this, "Please enter a blood sugar value", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int mgDl = Integer.parseInt(mgdlStr);

                        if (mgDl < 10 || mgDl > 1000) {
                            Toast.makeText(this, "Blood sugar must be between 10 and 1000 mg/dL", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String timeWithSeconds = selectedTime[0] + ":00";
                        if (isEdit) {
                            dbHelper.updateBloodSugar(existing.getId(), selectedDate[0], timeWithSeconds, mgDl);
                            Toast.makeText(this, "Blood sugar reading updated", Toast.LENGTH_SHORT).show();
                        } else {
                            dbHelper.insertBloodSugar(selectedDate[0], timeWithSeconds, mgDl);
                            Toast.makeText(this, "Blood sugar reading saved", Toast.LENGTH_SHORT).show();
                        }
                        loadReadings();

                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmDialog(HealthReadingModel reading) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Reading")
                .setMessage("Delete this blood sugar reading?\n\n"
                        + reading.getValue1() + " mg/dL\n"
                        + reading.getDate() + "  " + reading.getTime())
                .setPositiveButton("Delete", (d, w) -> {
                    dbHelper.deleteBloodSugar(reading.getId());
                    Toast.makeText(this, "Reading deleted", Toast.LENGTH_SHORT).show();
                    loadReadings();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ─── Adapter ──────────────────────────────────────────────────────────────

    private class BloodSugarAdapter extends RecyclerView.Adapter<BloodSugarAdapter.ViewHolder> {

        private final List<HealthReadingModel> data;

        BloodSugarAdapter(List<HealthReadingModel> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_health_reading, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HealthReadingModel reading = data.get(position);
            holder.tvValue.setText(reading.getValue1() + " mg/dL");
            holder.tvDateTime.setText(reading.getDate() + "   " + reading.getTime());

            holder.itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(BloodSugarActivity.this)
                        .setTitle("Options")
                        .setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
                            if (which == 0) showAddEditDialog(reading);
                            else showDeleteConfirmDialog(reading);
                        })
                        .show();
                return true;
            });
        }

        @Override
        public int getItemCount() { return data.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView tvValue;
            final TextView tvDateTime;

            ViewHolder(View itemView) {
                super(itemView);
                tvValue = itemView.findViewById(R.id.tvValue);
                tvDateTime = itemView.findViewById(R.id.tvDateTime);
            }
        }
    }
}
