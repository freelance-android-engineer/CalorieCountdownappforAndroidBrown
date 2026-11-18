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
    private ForecastChartView mForecastChart;
    private TextView    mForecastStatus;

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
        mForecastChart  = findViewById(R.id.forecast_chart_view);
        mForecastStatus = findViewById(R.id.forecast_status);

        loadForecastAsync();
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

    // ── Forecast (mDayZero) ───────────────────────────────────────────────────

    /** Restores mDayZero from SQLite and shows Budget vs Actual Balance. Read-only. */
    private void loadForecastAsync() {
        mExecutor.execute(() -> {
            List<DayCiF1005> days;
            try {
                CCD_GUI_CD_CIF1.restoreMDayZero(getApplicationContext());
                days = new java.util.ArrayList<>(CCD_GUI_CD_CIF1.mDayZero);
            } catch (Exception e) {
                android.util.Log.e(TAG, "Error loading forecast: " + e.getMessage(), e);
                days = new java.util.ArrayList<>();
            }
            final List<DayCiF1005> finalDays = days;
            final java.time.LocalDate today = java.time.LocalDate.now();
            mMainHandler.post(() -> {
                if (isFinishing() || mForecastChart == null) return;
                if (finalDays.isEmpty()) {
                    mForecastStatus.setText("No forecast yet. It is created when you use Start Weight Loss.");
                    mForecastChart.setVisibility(View.GONE);
                    return;
                }
                DayCiF1005 last = finalDays.get(finalDays.size() - 1);
                mForecastStatus.setText(String.format(java.util.Locale.getDefault(),
                        "Opening Balance %,d → 0 in %d days (−%d per day). Goal date: %s.\n"
                                + "Swipe left/right to scroll; tap a day to see its values.",
                        finalDays.get(0).getStartBalanceBFWD(), finalDays.size(),
                        DayZeroForecast.DAILY_BUDGET_DECREMENT,
                        last.getDay().toLocalDate().format(java.time.format.DateTimeFormatter
                                .ofPattern("dd MMM yyyy", java.util.Locale.getDefault()))));
                mForecastChart.setData(finalDays, today);
                mForecastChart.setVisibility(View.VISIBLE);
            });
        });
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
