package ese.com.caloriecountdownappforandroidbrown;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * AppCustomization — reads customization prefs and applies them to the home screen UI.
 * All settings live in SharedPreferences "Calorie_Countdown".
 * Call apply(activity) from onResume() AFTER refreshBalanceFromStorage().
 */
public class AppCustomization {

    private static final String PREFS_NAME = "Calorie_Countdown";

    // SharedPreferences keys
    public static final String KEY_TEXT_COLOR   = "countdown_text_color";
    public static final String KEY_FONT_STYLE   = "countdown_font_style";
    public static final String KEY_NUMBER_FORMAT = "countdown_number_format";
    public static final String KEY_CIRCLE_STYLE = "countdown_circle_style";
    public static final String KEY_DEBIT_COLOR  = "debit_button_color";
    public static final String KEY_CREDIT_COLOR = "credit_button_color";
    public static final String KEY_BACKGROUND   = "home_background";

    // Defaults (mirror the hard-coded values in the layout/colors.xml)
    public static final int    DEFAULT_TEXT_COLOR   = Color.parseColor("#2db24b");
    public static final int    DEFAULT_CREDIT_COLOR = Color.parseColor("#FFC107");
    public static final int    DEFAULT_DEBIT_COLOR  = Color.parseColor("#4CAF50");
    public static final String DEFAULT_BACKGROUND   = "whitepeacock22";

    // Font style values
    public static final String FONT_DEFAULT = "default";
    public static final String FONT_BOLD    = "bold";
    public static final String FONT_SERIF   = "serif";
    public static final String FONT_MONO    = "monospace";

    // Number format values
    public static final String FORMAT_PLAIN = "plain";
    public static final String FORMAT_COMMA = "comma";

    // Circle style values
    public static final String CIRCLE_NONE    = "none";
    public static final String CIRCLE_THIN    = "thin";
    public static final String CIRCLE_MEDIUM  = "medium";
    public static final String CIRCLE_THICK   = "thick";
    public static final String CIRCLE_OVERLAY = "overlay";

    // Available background drawable resource names (must match res/drawable file names)
    public static final String[] BACKGROUNDS = {
        "whitepeacock22",  "whitepeacock1",  "whitepeacock5",  "whitepeacock8",
        "whitepeacock9",   "whitepeacock10", "whitepeacock11", "whitepeacock12",
        "whitepeacock13",  "whitepeacock14", "whitepeacock15", "whitepeacock16",
        "whitepeacock17",  "whitepeacock18", "whitepeacock19", "whitepeacock20",
        "whitepeacock21",  "blackrock",      "berries",        "cat",
        "changetextcolor"
    };

    public static final String[] BACKGROUND_LABELS = {
        "Peacock 22 (Default)", "Peacock 1",  "Peacock 5",  "Peacock 8",
        "Peacock 9",  "Peacock 10", "Peacock 11", "Peacock 12",
        "Peacock 13", "Peacock 14", "Peacock 15", "Peacock 16",
        "Peacock 17", "Peacock 18", "Peacock 19", "Peacock 20",
        "Peacock 21", "Black Rock", "Berries",    "Cat",
        "Color Change"
    };

    /**
     * Apply all stored customizations to the home screen activity's UI.
     * Safe to call when views are not found (null-checked throughout).
     */
    public static void apply(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // ── Balance TextView ──────────────────────────────────────────────
        TextView textView = activity.findViewById(R.id.textView);
        if (textView != null) {
            // Color
            int textColor = prefs.getInt(KEY_TEXT_COLOR, DEFAULT_TEXT_COLOR);
            textView.setTextColor(textColor);

            // Font
            applyFont(textView, prefs.getString(KEY_FONT_STYLE, FONT_DEFAULT));

            // Number format (strip commas first so we start from a plain number)
            String rawText = stripCommas(textView.getText().toString().trim());
            textView.setText(formatBalance(rawText, prefs.getString(KEY_NUMBER_FORMAT, FORMAT_PLAIN)));

            // Circle style
            applyCircleStyle(activity, textView, prefs.getString(KEY_CIRCLE_STYLE, CIRCLE_NONE), textColor);
        }

        // ── Credit button text color ──────────────────────────────────────
        Button creditButton = activity.findViewById(R.id.button2);
        if (creditButton != null) {
            creditButton.setTextColor(prefs.getInt(KEY_CREDIT_COLOR, DEFAULT_CREDIT_COLOR));
        }

        // ── Debit button text color ───────────────────────────────────────
        Button debitButton = activity.findViewById(R.id.button);
        if (debitButton != null) {
            debitButton.setTextColor(prefs.getInt(KEY_DEBIT_COLOR, DEFAULT_DEBIT_COLOR));
        }

        // ── Home background ───────────────────────────────────────────────
        View rootLayout = activity.findViewById(R.id.home_root_layout);
        if (rootLayout != null) {
            String bgName = prefs.getString(KEY_BACKGROUND, DEFAULT_BACKGROUND);
            int resId = getDrawableResId(activity, bgName);
            if (resId != 0) {
                rootLayout.setBackgroundResource(resId);
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private static void applyFont(TextView tv, String style) {
        switch (style == null ? FONT_DEFAULT : style) {
            case FONT_BOLD:
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case FONT_SERIF:
                tv.setTypeface(Typeface.SERIF);
                break;
            case FONT_MONO:
                tv.setTypeface(Typeface.MONOSPACE);
                break;
            default:
                tv.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
                break;
        }
    }

    private static void applyCircleStyle(Context ctx, TextView tv, String style, int textColor) {
        if (style == null || CIRCLE_NONE.equals(style)) {
            tv.setBackground(null);
            tv.setPadding(0, 0, 0, 0);
            return;
        }

        float density = ctx.getResources().getDisplayMetrics().density;
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);

        if (CIRCLE_OVERLAY.equals(style)) {
            // Semi-transparent glass effect
            circle.setColor(Color.argb(80, 255, 255, 255));
            circle.setStroke((int)(3 * density), Color.argb(140, 255, 255, 255));
        } else {
            circle.setColor(Color.TRANSPARENT);
            int strokeDp;
            switch (style) {
                case CIRCLE_THIN:   strokeDp = 3;  break;
                case CIRCLE_MEDIUM: strokeDp = 7;  break;
                case CIRCLE_THICK:  strokeDp = 14; break;
                default:            strokeDp = 0;  break;
            }
            if (strokeDp > 0) {
                circle.setStroke((int)(strokeDp * density), textColor);
            }
        }

        tv.setBackground(circle);
        int padPx = (int)(32 * density);
        tv.setPadding(padPx, padPx, padPx, padPx);
    }

    /**
     * Format a raw numeric string according to the chosen format.
     * Safe: returns the original string unchanged if it cannot be parsed.
     */
    public static String formatBalance(String rawText, String format) {
        if (rawText == null || rawText.isEmpty()) return rawText == null ? "" : rawText;
        try {
            long value = Long.parseLong(stripCommas(rawText));
            if (FORMAT_COMMA.equals(format)) {
                return NumberFormat.getNumberInstance(Locale.US).format(value);
            } else {
                return String.valueOf(value);
            }
        } catch (NumberFormatException e) {
            return rawText;
        }
    }

    /**
     * Strip commas (and any other non-digit characters except minus) from a number string.
     * Used before arithmetic so comma-formatted display text parses cleanly.
     */
    public static String stripCommas(String text) {
        if (text == null) return "";
        return text.replaceAll("[^\\d-]", "");
    }

    /** Resolve a drawable name to its resource ID, 0 if not found. */
    public static int getDrawableResId(Context ctx, String name) {
        if (name == null || name.isEmpty()) return 0;
        return ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
    }
}
