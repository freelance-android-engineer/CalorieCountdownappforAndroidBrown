package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Forecast chart (Version 2.0.1): Budget Balance vs Actual Balance for every day of mDayZero,
 * from the Start Weight Loss date to the zero-balance goal date.
 *
 * X axis = Day, Y axis = Balance. The chart scrolls horizontally (drag / fling); only the days
 * on screen are drawn, so thousands of days stay smooth. The Y axis and legend stay fixed.
 * Days without a saved Day End Balance have no Actual point (a gap, never 0).
 * Tapping a day shows its date, Budget and Actual values. The chart only reads its data.
 */
public class ForecastChartView extends View {

    private static final int BUDGET_COLOR = Color.parseColor("#1565C0");
    private static final int ACTUAL_COLOR = Color.parseColor("#2E7D32");
    private static final int TODAY_COLOR  = Color.parseColor("#FF6F00");
    private static final DateTimeFormatter LABEL_DATE = DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault());
    private static final DateTimeFormatter INFO_DATE = DateTimeFormatter.ofPattern("EEE dd MMM yyyy", Locale.getDefault());

    // ── Data (copied from mDayZero; the forecast itself is never changed here) ──
    private LocalDate[] mDates = new LocalDate[0];
    private int[] mBudget = new int[0];
    private Integer[] mActual = new Integer[0];
    private int mTodayIndex = -1;
    private int mSelectedIndex = -1;
    private int mMinY = 0;
    private int mMaxY = 1;

    // ── Scrolling ──
    private float mOffset = 0f;
    private boolean mInitialScrollDone = false;
    private final OverScroller mScroller;
    private final GestureDetector mGestures;

    // ── Layout ──
    private final float mDensity;
    private final float mDayWidth;
    private final float mAxisWidth;
    private final float mTopHeight;
    private final float mBottomHeight;

    private final Paint mBudgetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mActualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTodayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mBackgroundPaint = new Paint();
    private final Path mPath = new Path();

    public ForecastChartView(Context context) {
        this(context, null);
    }

    public ForecastChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = context.getResources().getDisplayMetrics().density;
        float sp = context.getResources().getDisplayMetrics().scaledDensity;
        mDayWidth = dp(18);
        mAxisWidth = dp(64);
        mTopHeight = dp(78);
        mBottomHeight = dp(40);

        mBudgetPaint.setColor(BUDGET_COLOR);
        mBudgetPaint.setStyle(Paint.Style.STROKE);
        mBudgetPaint.setStrokeWidth(dp(2));
        mBudgetPaint.setPathEffect(new DashPathEffect(new float[]{dp(6), dp(4)}, 0));

        mActualPaint.setColor(ACTUAL_COLOR);
        mActualPaint.setStyle(Paint.Style.STROKE);
        mActualPaint.setStrokeWidth(dp(2.5f));

        mDotPaint.setColor(ACTUAL_COLOR);
        mDotPaint.setStyle(Paint.Style.FILL);

        mGridPaint.setColor(Color.parseColor("#E0E0E0"));
        mGridPaint.setStrokeWidth(dp(1));

        mTodayPaint.setColor(TODAY_COLOR);
        mTodayPaint.setStrokeWidth(dp(1.5f));

        mSelectPaint.setColor(Color.parseColor("#9E9E9E"));
        mSelectPaint.setStrokeWidth(dp(1));

        mTextPaint.setColor(Color.parseColor("#444444"));
        mTextPaint.setTextSize(11 * sp);

        mBoxPaint.setColor(Color.parseColor("#F5F5F5"));
        mBoxPaint.setStyle(Paint.Style.FILL);

        mBackgroundPaint.setColor(Color.WHITE);

        mScroller = new OverScroller(context);
        mGestures = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                mScroller.forceFinished(true);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                if (Math.abs(dx) > Math.abs(dy) && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                setOffset(mOffset + dx);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
                mScroller.fling((int) mOffset, 0, (int) -vx, 0, 0, (int) maxOffset(), 0, 0);
                postInvalidateOnAnimation();
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                int index = indexAt(e.getX());
                if (index >= 0) {
                    mSelectedIndex = index;
                    invalidate();
                }
                return true;
            }
        });
    }

    /**
     * Shows the forecast. Scrolling starts at today's day (or the nearest end of the forecast).
     *
     * @param days  mDayZero, oldest day first
     * @param today current date, to mark progress
     */
    public void setData(List<DayCiF1005> days, LocalDate today) {
        int n = days.size();
        mDates = new LocalDate[n];
        mBudget = new int[n];
        mActual = new Integer[n];
        int min = 0;
        int max = 1;
        for (int i = 0; i < n; i++) {
            DayCiF1005 day = days.get(i);
            mDates[i] = day.getDay().toLocalDate();
            mBudget[i] = day.getEndBalanceCFWD();
            mActual[i] = DayZeroForecast.hasActual(day) ? Integer.valueOf(day.getActualDayEndBalance()) : null;
            max = Math.max(max, Math.max(mBudget[i], day.getStartBalanceBFWD()));
            if (mActual[i] != null) {
                max = Math.max(max, mActual[i]);
                min = Math.min(min, mActual[i]);
            }
        }
        mMinY = min;
        mMaxY = max;
        mTodayIndex = DayZeroForecast.indexOf(days, today);
        if (mTodayIndex >= 0) {
            mSelectedIndex = mTodayIndex;
        } else if (n > 0) {
            mSelectedIndex = (today != null && today.isBefore(mDates[0])) ? 0 : n - 1;
        } else {
            mSelectedIndex = -1;
        }
        mInitialScrollDone = false;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!mInitialScrollDone && w > 0 && mDates.length > 0) {
            // Keep today (or the selected end) about a third in from the left
            float target = mSelectedIndex * mDayWidth - plotWidth() / 3f;
            mOffset = clamp(target);
            mInitialScrollDone = true;
        } else {
            mOffset = clamp(mOffset);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = mGestures.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            if (getParent() != null) getParent().requestDisallowInterceptTouchEvent(false);
        }
        return handled || super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            setOffset(mScroller.getCurrX());
            postInvalidateOnAnimation();
        }
    }

    // ── Drawing ──────────────────────────────────────────────────────────────

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);
        int n = mDates.length;
        if (n == 0) return;
        if (!mInitialScrollDone && getWidth() > 0) {
            onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        }

        float top = mTopHeight;
        float bottom = getHeight() - mBottomHeight;
        float left = mAxisWidth;
        float right = getWidth() - dp(8);

        // Horizontal grid
        for (int g = 0; g <= 4; g++) {
            float y = top + (bottom - top) * g / 4f;
            canvas.drawLine(left, y, right, y, mGridPaint);
        }

        int first = Math.max(0, (int) Math.floor(mOffset / mDayWidth) - 1);
        int last = Math.min(n - 1, (int) Math.ceil((mOffset + (right - left)) / mDayWidth) + 1);

        canvas.save();
        canvas.clipRect(left, 0, right, getHeight());

        // Day / date labels every 7 days, from Day 1
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = first; i <= last; i++) {
            if (i % 7 != 0 && i != n - 1) continue;
            float x = xOf(i);
            canvas.drawLine(x, bottom, x, bottom + dp(4), mGridPaint);
            canvas.drawText("Day " + (i + 1), x, bottom + dp(16), mTextPaint);
            canvas.drawText(mDates[i].format(LABEL_DATE), x, bottom + dp(30), mTextPaint);
        }

        // Today marker
        if (mTodayIndex >= first && mTodayIndex <= last) {
            float x = xOf(mTodayIndex);
            canvas.drawLine(x, top, x, bottom, mTodayPaint);
            mTextPaint.setColor(TODAY_COLOR);
            canvas.drawText("Today", x, top - dp(4), mTextPaint);
            mTextPaint.setColor(Color.parseColor("#444444"));
        }

        // Selected day marker
        if (mSelectedIndex >= first && mSelectedIndex <= last && mSelectedIndex != mTodayIndex) {
            float x = xOf(mSelectedIndex);
            canvas.drawLine(x, top, x, bottom, mSelectPaint);
        }

        // Budget Balance (expected) — every forecast day
        mPath.reset();
        for (int i = first; i <= last; i++) {
            float x = xOf(i);
            float y = yOf(mBudget[i], top, bottom);
            if (i == first) mPath.moveTo(x, y);
            else mPath.lineTo(x, y);
        }
        canvas.drawPath(mPath, mBudgetPaint);

        // Actual Balance — only days with a saved Day End Balance; a missing day breaks the line
        mPath.reset();
        boolean drawing = false;
        for (int i = first; i <= last; i++) {
            if (mActual[i] == null) {
                drawing = false;
                continue;
            }
            float x = xOf(i);
            float y = yOf(mActual[i], top, bottom);
            if (drawing) mPath.lineTo(x, y);
            else mPath.moveTo(x, y);
            drawing = true;
            canvas.drawCircle(x, y, dp(3), mDotPaint);
        }
        canvas.drawPath(mPath, mActualPaint);
        canvas.restore();

        // Fixed Y axis (balance)
        canvas.drawRect(0, 0, left, getHeight(), mBackgroundPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (int g = 0; g <= 4; g++) {
            float y = top + (bottom - top) * g / 4f;
            int value = Math.round(mMaxY - (mMaxY - mMinY) * g / 4f);
            canvas.drawText(fmt(value), left - dp(6), y + dp(4), mTextPaint);
        }
        canvas.drawLine(left, top, left, bottom, mSelectPaint);

        drawLegendAndInfo(canvas, left, right);
    }

    private void drawLegendAndInfo(Canvas canvas, float left, float right) {
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        float y = dp(16);
        float x = left;
        canvas.drawLine(x, y - dp(4), x + dp(20), y - dp(4), mBudgetPaint);
        canvas.drawText("Budget Balance", x + dp(24), y, mTextPaint);
        x += dp(130);
        canvas.drawLine(x, y - dp(4), x + dp(20), y - dp(4), mActualPaint);
        canvas.drawText("Actual Balance", x + dp(24), y, mTextPaint);

        if (mSelectedIndex < 0 || mSelectedIndex >= mDates.length) return;
        int i = mSelectedIndex;
        RectF box = new RectF(left, dp(24), right, dp(60));
        canvas.drawRoundRect(box, dp(4), dp(4), mBoxPaint);
        String actual = mActual[i] != null ? fmt(mActual[i]) : "No record";
        String line1 = "Day " + (i + 1) + " of " + mDates.length + " · " + mDates[i].format(INFO_DATE)
                + (i == mTodayIndex ? " (Today)" : "");
        String line2 = "Budget: " + fmt(mBudget[i]) + "   Actual: " + actual;
        canvas.drawText(line1, left + dp(6), dp(38), mTextPaint);
        canvas.drawText(line2, left + dp(6), dp(54), mTextPaint);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private float xOf(int index) {
        return mAxisWidth + mDayWidth / 2f + index * mDayWidth - mOffset;
    }

    private float yOf(int value, float top, float bottom) {
        float range = Math.max(1, mMaxY - mMinY);
        return bottom - (value - mMinY) / range * (bottom - top);
    }

    private int indexAt(float x) {
        if (x < mAxisWidth || mDates.length == 0) return -1;
        int index = (int) Math.floor((x - mAxisWidth + mOffset) / mDayWidth);
        return (index >= 0 && index < mDates.length) ? index : -1;
    }

    private float plotWidth() {
        return Math.max(0, getWidth() - mAxisWidth - dp(8));
    }

    private float maxOffset() {
        return Math.max(0, mDates.length * mDayWidth - plotWidth());
    }

    private float clamp(float offset) {
        return Math.max(0, Math.min(offset, maxOffset()));
    }

    private void setOffset(float offset) {
        float clamped = clamp(offset);
        if (clamped != mOffset) {
            mOffset = clamped;
            invalidate();
        }
    }

    private float dp(float value) {
        return value * mDensity;
    }

    private static String fmt(int value) {
        return String.format(Locale.getDefault(), "%,d", value);
    }
}
