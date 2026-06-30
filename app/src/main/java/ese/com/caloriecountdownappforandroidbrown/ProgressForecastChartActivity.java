package ese.com.caloriecountdownappforandroidbrown;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ProgressForecastChartActivity — displays Progress & Forecast Charts for the
 * Countdown Balance journey.
 *
 * Data is loaded off the UI thread using a single-thread executor so the main
 * thread is never blocked.  All existing business logic is untouched; this
 * Activity only reads from {@code dayend_balance2} via the read-only
 * {@link SQLDatabase_Food_Items_CIF6#getHistoricalBalancesWithDates()} method.
 */
public class ProgressForecastChartActivity extends AppCompatActivity {

    private static final String TAG = "ProgressForecastChart";

    private ProgressBar mProgressBar;
    private TextView    mStatusText;
    private ChartView   mChartView;
    private ScrollView  mScrollView;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final Handler         mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_forecast_chart);

        Toolbar toolbar = findViewById(R.id.chart_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Progress & Forecast Charts");
            }
        }

        mProgressBar = findViewById(R.id.chart_progress_bar);
        mStatusText  = findViewById(R.id.chart_status_text);
        mChartView   = findViewById(R.id.chart_view);
        mScrollView  = findViewById(R.id.chart_scroll_view);

        loadDataAsync();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }

    // ── Data loading ──────────────────────────────────────────────────────────

    private void loadDataAsync() {
        showLoading(true);
        mExecutor.execute(() -> {
            try {
                SQLDatabase_Food_Items_CIF6 db =
                        new SQLDatabase_Food_Items_CIF6(getApplicationContext());

                List<long[]> history = db.getHistoricalBalancesWithDates();

                // Current balance from the live balance store
                int currentBalance = 0;
                try {
                    MIF4_Data_Model_Adapter adapter =
                            new MIF4_Data_Model_Adapter(getApplicationContext());
                    String raw = adapter.RetrieveBalance();
                    if (raw != null && !raw.trim().isEmpty()) {
                        // Strip commas before parsing
                        currentBalance = Integer.parseInt(raw.trim().replace(",", ""));
                    }
                } catch (Exception e) {
                    android.util.Log.w(TAG, "Could not read current balance: " + e.getMessage());
                }

                // Average daily reduction (same algorithm as Show_Estimated_Date_To_Zero)
                int avgDailyReduction = 250; // standard fallback
                if (history.size() >= 2) {
                    int opening = (int) history.get(0)[1];
                    int latest  = (int) history.get(history.size() - 1)[1];
                    int totalReduction = opening - latest;
                    int totalDays      = history.size() - 1;
                    if (totalReduction > 0 && totalDays > 0) {
                        avgDailyReduction = Math.max(1, totalReduction / totalDays);
                    }
                }

                final List<long[]> finalHistory  = history;
                final int          finalBalance   = currentBalance;
                final int          finalRate      = avgDailyReduction;

                mMainHandler.post(() -> {
                    showLoading(false);
                    if (finalHistory.isEmpty()) {
                        showStatus("No historical Countdown Balance data found.\n\n"
                                + "Complete at least one Credit cycle and Day End to\n"
                                + "generate chart data.");
                    } else {
                        mStatusText.setVisibility(View.GONE);
                        mChartView.setVisibility(View.VISIBLE);
                        mChartView.setData(finalHistory, finalBalance, finalRate);
                    }
                });

            } catch (Exception e) {
                android.util.Log.e(TAG, "Error loading chart data: " + e.getMessage(), e);
                mMainHandler.post(() -> {
                    showLoading(false);
                    showStatus("Unable to load chart data.\n(" + e.getMessage() + ")");
                });
            }
        });
    }

    // ── UI helpers ────────────────────────────────────────────────────────────

    private void showLoading(boolean loading) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
        if (loading) {
            mChartView.setVisibility(View.GONE);
        }
    }

    private void showStatus(String message) {
        if (mStatusText != null) {
            mStatusText.setText(message);
            mStatusText.setVisibility(View.VISIBLE);
        }
        if (mChartView != null) {
            mChartView.setVisibility(View.GONE);
        }
    }
}
