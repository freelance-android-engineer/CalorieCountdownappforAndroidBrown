package ese.com.caloriecountdownappforandroidbrown;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class WaterRecordsActivity extends AppCompatActivity {

    private TextView startDateText, endDateText;
    private Button filterButton;
    private TableLayout tableLayout;

    private Calendar startDateCal = Calendar.getInstance();
    private Calendar endDateCal = Calendar.getInstance();

    private SQLDatabase_Food_Items_CIF6 dbHelper; // Replace with your actual DB helper class name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_records);

        startDateText = findViewById(R.id.startDateText);
        endDateText = findViewById(R.id.endDateText);
        filterButton = findViewById(R.id.filterButton);
        tableLayout = findViewById(R.id.tableLayout);

        dbHelper = new SQLDatabase_Food_Items_CIF6(this);

        startDateText.setOnClickListener(v -> showDatePicker(startDateCal, startDateText));
        endDateText.setOnClickListener(v -> showDatePicker(endDateCal, endDateText));

        filterButton.setOnClickListener(v -> filterData());
    }

    private void showDatePicker(Calendar calendar, TextView targetTextView) {
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    targetTextView.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void filterData() {
        tableLayout.removeAllViews();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate = sdf.format(startDateCal.getTime());
        String endDate = sdf.format(endDateCal.getTime());

        List<WaterTrackedModel> allData = dbHelper.getAllWaterData();

        Map<String, WaterTrackedModel> dateSummaryMap = new TreeMap<>();

        for (WaterTrackedModel item : allData) {
            String date = item.getDate();
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                if (dateSummaryMap.containsKey(date)) {
                    WaterTrackedModel existing = dateSummaryMap.get(date);
                    int totalMl = existing.getMlWaterDrunk() + item.getMlWaterDrunk();
                    double totalCups = existing.getEquivalentCups() + item.getEquivalentCups();
                    dateSummaryMap.put(date, new WaterTrackedModel(date, date, totalMl, totalCups)); // reused date as ID
                } else {
                    dateSummaryMap.put(date, new WaterTrackedModel(date, date, item.getMlWaterDrunk(), item.getEquivalentCups()));
                }
            }
        }

        // Add table header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Date"));
        headerRow.addView(createTextView("ML Drunk"));
        headerRow.addView(createTextView("Cups"));
        tableLayout.addView(headerRow);

        for (Map.Entry<String, WaterTrackedModel> entry : dateSummaryMap.entrySet()) {
            WaterTrackedModel model = entry.getValue();
            TableRow row = new TableRow(this);
            row.addView(createTextView(model.getDate()));
            row.addView(createTextView(String.valueOf(model.getMlWaterDrunk())));
            row.addView(createTextView(String.format(Locale.getDefault(), "%.2f", model.getEquivalentCups())));
            tableLayout.addView(row);
        }
    }


    private TextView createTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(16, 16, 16, 16);
        return tv;
    }
}
