package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ChartView — lightweight canvas-based chart for the Progress & Forecast Charts feature.
 *
 * Draws two stacked sections:
 *   1. Progress Chart  — historical Countdown Balance as a filled line graph.
 *   2. Forecast Chart  — historical line + dashed forecast extending to zero.
 *
 * No external library. Zero side-effects on existing app logic.
 */
public class ChartView extends View {

    // ── Data ──────────────────────────────────────────────────────────────────
    /** Raw data: each element is {timestampMs, balance}. Set before first draw. */
    private List<long[]> mData = new ArrayList<>();
    /** Current live balance (used as the starting point for the forecast). */
    private int mCurrentBalance = 0;
    /** Average daily reduction used for the forecast line. */
    private int mAvgDailyReduction = 250;

    // ── Layout constants (dp-independent because we scale by density) ─────────
    private static final int SECTION_PADDING_DP = 16;
    private static final int AXIS_LABEL_WIDTH_DP = 72;
    private static final int X_LABEL_HEIGHT_DP = 40;
    private static final int SECTION_TITLE_HEIGHT_DP = 36;
    private static final int SECTION_GAP_DP = 24;

    // ── Paints ────────────────────────────────────────────────────────────────
    private final Paint mAxisPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mGridPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mLinePaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mFillPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mForecastPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mDotPaint     = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTextPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTitlePaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mLabelPaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mStatPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mZeroPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mDensity = 1f;

    public ChartView(Context context) {
        super(context);
        init(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mDensity = context.getResources().getDisplayMetrics().density;
        float sp = context.getResources().getDisplayMetrics().scaledDensity;

        int green = Color.parseColor("#2E7D32");   // dark green — matches existing app palette
        int lightGreen = Color.parseColor("#66BB6A");
        int forecastColor = Color.parseColor("#FF6F00"); // amber for forecast

        mAxisPaint.setColor(Color.parseColor("#444444"));
        mAxisPaint.setStrokeWidth(dp(1.5f));
        mAxisPaint.setStyle(Paint.Style.STROKE);

        mGridPaint.setColor(Color.parseColor("#E0E0E0"));
        mGridPaint.setStrokeWidth(dp(0.5f));
        mGridPaint.setStyle(Paint.Style.STROKE);

        mLinePaint.setColor(green);
        mLinePaint.setStrokeWidth(dp(2f));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mFillPaint.setColor(Color.parseColor("#1A2E7D32")); // 10% opacity green fill
        mFillPaint.setStyle(Paint.Style.FILL);

        mForecastPaint.setColor(forecastColor);
        mForecastPaint.setStrokeWidth(dp(2f));
        mForecastPaint.setStyle(Paint.Style.STROKE);
        mForecastPaint.setPathEffect(new DashPathEffect(new float[]{dp(8), dp(4)}, 0));

        mDotPaint.setColor(green);
        mDotPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setColor(Color.parseColor("#555555"));
        mTextPaint.setTextSize(sp * 10f);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);

        mTitlePaint.setColor(green);
        mTitlePaint.setTextSize(sp * 13f);
        mTitlePaint.setFakeBoldText(true);

        mLabelPaint.setColor(Color.parseColor("#555555"));
        mLabelPaint.setTextSize(sp * 9f);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);

        mStatPaint.setColor(Color.parseColor("#333333"));
        mStatPaint.setTextSize(sp * 11f);

        mZeroPaint.setColor(Color.parseColor("#C62828")); // red — "zero" marker
        mZeroPaint.setStrokeWidth(dp(1f));
        mZeroPaint.setStyle(Paint.Style.STROKE);
        mZeroPaint.setPathEffect(new DashPathEffect(new float[]{dp(4), dp(4)}, 0));
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void setData(List<long[]> data, int currentBalance, int avgDailyReduction) {
        mData = (data != null) ? data : new ArrayList<>();
        mCurrentBalance = Math.max(0, currentBalance);
        mAvgDailyReduction = (avgDailyReduction > 0) ? avgDailyReduction : 250;
        invalidate();
    }

    // ── Measurement ───────────────────────────────────────────────────────────

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        // Two chart sections + titles + stats + padding
        int h = (int) (dp(SECTION_TITLE_HEIGHT_DP + 220
                + SECTION_TITLE_HEIGHT_DP + 220
                + SECTION_GAP_DP * 3
                + X_LABEL_HEIGHT_DP * 2
                + 80));        // stats rows
        setMeasuredDimension(w, h);
    }

    // ── Drawing ───────────────────────────────────────────────────────────────

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        int w = getWidth();
        float pad   = dp(SECTION_PADDING_DP);
        float axisW = dp(AXIS_LABEL_WIDTH_DP);
        float xLblH = dp(X_LABEL_HEIGHT_DP);
        float titleH= dp(SECTION_TITLE_HEIGHT_DP);
        float secGap= dp(SECTION_GAP_DP);

        float chartH = dp(200);

        // ── Section 1: Progress Chart ─────────────────────────────────────────
        float top1 = pad;
        drawSectionTitle(canvas, "  Progress Chart", top1);
        float chartTop1 = top1 + titleH;
        float chartBot1 = chartTop1 + chartH;
        drawProgressChart(canvas, axisW, pad, w - pad, chartTop1, chartBot1);
        drawProgressStats(canvas, pad, chartBot1 + xLblH + dp(4));

        // ── Section 2: Forecast Chart ─────────────────────────────────────────
        float top2 = chartBot1 + xLblH + dp(60) + secGap;
        drawSectionTitle(canvas, "  Forecast Chart", top2);
        float chartTop2 = top2 + titleH;
        float chartBot2 = chartTop2 + chartH;
        drawForecastChart(canvas, axisW, pad, w - pad, chartTop2, chartBot2);
        drawForecastStats(canvas, pad, chartBot2 + xLblH + dp(4));
    }

    // ── Progress Chart ────────────────────────────────────────────────────────

    private void drawProgressChart(Canvas canvas,
                                   float axisLeft, float padRight,
                                   float right, float top, float bottom) {
        if (mData.size() < 1) {
            drawNoData(canvas, axisLeft, right, top, bottom, "No historical data yet");
            drawAxes(canvas, axisLeft, right, top, bottom);
            return;
        }

        int maxBal = 0;
        int minBal = Integer.MAX_VALUE;
        for (long[] pt : mData) {
            int b = (int) pt[1];
            if (b > maxBal) maxBal = b;
            if (b < minBal) minBal = b;
        }
        if (maxBal == 0) maxBal = 1;
        // Round max up to nearest 5000
        maxBal = ((maxBal / 5000) + 1) * 5000;
        minBal = Math.max(0, (minBal / 5000) * 5000);

        drawYGrid(canvas, axisLeft, right, top, bottom, minBal, maxBal);
        drawAxes(canvas, axisLeft, right, top, bottom);
        drawYLabels(canvas, axisLeft - dp(4), top, bottom, minBal, maxBal);

        int n = mData.size();
        float chartW = right - axisLeft;
        float chartH = bottom - top;

        // Build path
        Path linePath = new Path();
        Path fillPath = new Path();
        boolean first = true;

        for (int i = 0; i < n; i++) {
            int bal = (int) mData.get(i)[1];
            float x = axisLeft + (n == 1 ? chartW / 2f : (i / (float)(n - 1)) * chartW);
            float y = bottom - ((bal - minBal) / (float)(maxBal - minBal)) * chartH;

            if (first) {
                linePath.moveTo(x, y);
                fillPath.moveTo(x, bottom);
                fillPath.lineTo(x, y);
                first = false;
            } else {
                linePath.lineTo(x, y);
                fillPath.lineTo(x, y);
            }

            // dot at last point
            if (i == n - 1) {
                canvas.drawCircle(x, y, dp(4), mDotPaint);
            }
        }
        // Close fill path
        if (n > 0) {
            float lastX = axisLeft + (n == 1 ? chartW / 2f : chartW);
            fillPath.lineTo(lastX, bottom);
            fillPath.close();
        }

        canvas.drawPath(fillPath, mFillPaint);
        canvas.drawPath(linePath, mLinePaint);

        // X-axis date labels (at most 4)
        drawXLabels(canvas, axisLeft, bottom + dp(6), chartW, n);
    }

    private void drawProgressStats(Canvas canvas, float x, float y) {
        if (mData.size() < 2) return;

        int opening = (int) mData.get(0)[1];
        int latest  = (int) mData.get(mData.size() - 1)[1];
        int reduced = opening - latest;
        int days    = mData.size() - 1;
        double pct  = (opening > 0) ? (reduced * 100.0 / opening) : 0;

        float lineH = dp(18);
        mStatPaint.setColor(Color.parseColor("#2E7D32"));
        canvas.drawText(String.format("Opening: %,d pts  |  Current: %,d pts", opening, mCurrentBalance),
                x, y + lineH, mStatPaint);
        canvas.drawText(String.format("Reduced: %,d pts over %d days  (%.1f%% complete)",
                reduced, days, pct), x, y + lineH * 2, mStatPaint);
        if (days > 0) {
            canvas.drawText(String.format("Avg daily reduction: %,d pts/day",
                    Math.max(1, reduced / days)), x, y + lineH * 3, mStatPaint);
        }
    }

    // ── Forecast Chart ────────────────────────────────────────────────────────

    private void drawForecastChart(Canvas canvas,
                                   float axisLeft, float padRight,
                                   float right, float top, float bottom) {
        int currentBal  = mCurrentBalance;
        int avgRate     = mAvgDailyReduction;
        int daysToZero  = (avgRate > 0) ? (int) Math.ceil((double) currentBal / avgRate) : 9999;
        daysToZero = Math.min(daysToZero, 3650); // cap at 10 years for rendering

        // Historical data points, then project forward
        int histCount   = mData.size();
        int maxBal      = (histCount > 0) ? (int) mData.get(0)[1] : currentBal;
        if (maxBal < currentBal) maxBal = currentBal;
        maxBal = Math.max(maxBal, avgRate);
        maxBal = ((maxBal / 5000) + 1) * 5000;

        drawYGrid(canvas, axisLeft, right, top, bottom, 0, maxBal);
        drawAxes(canvas, axisLeft, right, top, bottom);
        drawYLabels(canvas, axisLeft - dp(4), top, bottom, 0, maxBal);

        float chartW = right - axisLeft;
        float chartH  = bottom - top;

        // Total x-span = historical days + daysToZero
        int totalDays = Math.max(1, histCount + daysToZero);

        // Draw zero line (red dashed)
        canvas.drawLine(axisLeft, bottom, right, bottom, mZeroPaint);

        // ── Historical line ───────────────────────────────────────────────────
        if (histCount >= 1) {
            Path histPath = new Path();
            boolean first = true;
            for (int i = 0; i < histCount; i++) {
                int bal = (int) mData.get(i)[1];
                float x = axisLeft + (i / (float) totalDays) * chartW;
                float y = bottom - ((float) bal / maxBal) * chartH;
                if (first) { histPath.moveTo(x, y); first = false; }
                else histPath.lineTo(x, y);
            }
            canvas.drawPath(histPath, mLinePaint);
        }

        // ── Forecast line (dashed amber) ──────────────────────────────────────
        float forecastStartX = axisLeft + ((float) histCount / totalDays) * chartW;
        float forecastStartY = bottom - ((float) currentBal / maxBal) * chartH;
        float forecastEndX   = axisLeft + ((float)(histCount + daysToZero) / totalDays) * chartW;
        forecastEndX = Math.min(forecastEndX, right);
        float forecastEndY   = bottom; // zero

        Path forecastPath = new Path();
        forecastPath.moveTo(forecastStartX, forecastStartY);
        forecastPath.lineTo(forecastEndX, forecastEndY);
        canvas.drawPath(forecastPath, mForecastPaint);

        // Dot at forecast start
        canvas.drawCircle(forecastStartX, forecastStartY, dp(4), mDotPaint);

        // "ZERO" marker at bottom-right
        mZeroPaint.setPathEffect(null);
        mZeroPaint.setStyle(Paint.Style.FILL);
        mZeroPaint.setTextSize(dp(10));
        canvas.drawCircle(forecastEndX, forecastEndY, dp(5), mZeroPaint);
        mZeroPaint.setStyle(Paint.Style.STROKE);
        mZeroPaint.setPathEffect(new DashPathEffect(new float[]{dp(4), dp(4)}, 0));

        // X-axis labels: "Today" and "Est. Zero"
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Today", forecastStartX, bottom + dp(14), mLabelPaint);
        if (forecastEndX < right + dp(40)) {
            canvas.drawText("Est. Zero", forecastEndX, bottom + dp(14), mLabelPaint);
        }
    }

    private void drawForecastStats(Canvas canvas, float x, float y) {
        int avgRate    = mAvgDailyReduction;
        int daysToZero = (avgRate > 0) ? (int) Math.ceil((double) mCurrentBalance / avgRate) : 0;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, daysToZero);
        String estDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(cal.getTime());

        float lineH = dp(18);
        mStatPaint.setColor(Color.parseColor("#E65100")); // amber-dark for forecast stats
        canvas.drawText(String.format("Current Balance: %,d pts", mCurrentBalance), x, y + lineH, mStatPaint);
        canvas.drawText(String.format("Avg daily reduction: %,d pts/day", avgRate), x, y + lineH * 2, mStatPaint);
        canvas.drawText(String.format("Estimated days to zero: %,d days", daysToZero), x, y + lineH * 3, mStatPaint);
        canvas.drawText("Estimated completion: " + estDate, x, y + lineH * 4, mStatPaint);
    }

    // ── Shared drawing helpers ────────────────────────────────────────────────

    private void drawSectionTitle(Canvas canvas, String title, float top) {
        canvas.drawText(title, dp(SECTION_PADDING_DP), top + dp(24), mTitlePaint);
    }

    private void drawAxes(Canvas canvas, float left, float right, float top, float bottom) {
        canvas.drawLine(left, top, left, bottom, mAxisPaint);   // Y-axis
        canvas.drawLine(left, bottom, right, bottom, mAxisPaint); // X-axis
    }

    private void drawYGrid(Canvas canvas, float left, float right,
                           float top, float bottom, int minVal, int maxVal) {
        int steps = 4;
        float rangeH = bottom - top;
        int rangeV = maxVal - minVal;
        if (rangeV <= 0) return;
        for (int i = 1; i <= steps; i++) {
            float y = bottom - (i / (float) steps) * rangeH;
            canvas.drawLine(left, y, right, y, mGridPaint);
        }
    }

    private void drawYLabels(Canvas canvas, float x, float top, float bottom, int minVal, int maxVal) {
        int steps = 4;
        float rangeH = bottom - top;
        int rangeV = maxVal - minVal;
        if (rangeV <= 0) return;
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i <= steps; i++) {
            int val = minVal + (int)((i / (float) steps) * rangeV);
            float y = bottom - (i / (float) steps) * rangeH + dp(4);
            canvas.drawText(formatShort(val), x, y, mTextPaint);
        }
    }

    private void drawXLabels(Canvas canvas, float axisLeft, float y, float chartW, int count) {
        if (count < 1) return;
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
        // Show at most 4 evenly-spaced x-axis labels (day indices)
        int step = Math.max(1, (count - 1) / 3);
        for (int i = 0; i < count; i += step) {
            float x = axisLeft + (count == 1 ? chartW / 2f : (i / (float)(count - 1)) * chartW);
            canvas.drawText("D" + (i + 1), x, y + dp(10), mLabelPaint);
        }
        // Always include last label
        if (count > 1) {
            float x = axisLeft + chartW;
            canvas.drawText("D" + count, x, y + dp(10), mLabelPaint);
        }
    }

    private void drawNoData(Canvas canvas, float left, float right,
                            float top, float bottom, String message) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.parseColor("#999999"));
        float cx = (left + right) / 2f;
        float cy = (top + bottom) / 2f;
        canvas.drawText(message, cx, cy, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setColor(Color.parseColor("#555555"));
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    private float dp(float value) {
        return value * mDensity;
    }

    /** Formats large numbers compactly: 75853 → "75.9k", 250 → "250" */
    private String formatShort(int val) {
        if (val >= 1000) return String.format("%.1fk", val / 1000f);
        return String.valueOf(val);
    }
}
