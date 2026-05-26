package ese.com.caloriecountdownappforandroidbrown;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * CustomizationSettingsActivity — lets the user adjust the visual appearance
 * of the home countdown screen.  All changes persist to SharedPreferences
 * "Calorie_Countdown" immediately; they are applied to the UI when the main
 * activity resumes via AppCustomization.apply().
 */
public class CustomizationSettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    /** Color palette swatches shown for text / button color pickers. */
    private static final int[] PALETTE = {
        Color.parseColor("#2db24b"), // Green (default balance)
        Color.parseColor("#FFFFFF"), // White
        Color.parseColor("#000000"), // Black
        Color.parseColor("#FF5252"), // Red
        Color.parseColor("#2196F3"), // Blue
        Color.parseColor("#FFC107"), // Amber/Yellow
        Color.parseColor("#FF9800"), // Orange
        Color.parseColor("#9C27B0"), // Purple
        Color.parseColor("#E91E63"), // Pink
        Color.parseColor("#00BCD4"), // Cyan
        Color.parseColor("#607D8B"), // Blue-Grey
        Color.parseColor("#795548"), // Brown
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization_settings);

        prefs = getSharedPreferences("Calorie_Countdown", MODE_PRIVATE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Customize Appearance");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupTextColorPalette();
        setupFontStylePicker();
        setupNumberFormatPicker();
        setupCircleStylePicker();
        setupCreditColorPalette();
        setupDebitColorPalette();
        setupBackgroundPicker();

        findViewById(R.id.btn_apply_customization).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        findViewById(R.id.btn_reset_customization).setOnClickListener(v -> resetToDefaults());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // ── Text Color ────────────────────────────────────────────────────────

    private void setupTextColorPalette() {
        LinearLayout container = findViewById(R.id.palette_text_color);
        container.removeAllViews();
        int selected = prefs.getInt(AppCustomization.KEY_TEXT_COLOR, AppCustomization.DEFAULT_TEXT_COLOR);
        buildColorRow(container, selected, color -> {
            prefs.edit().putInt(AppCustomization.KEY_TEXT_COLOR, color).apply();
            setupTextColorPalette();
        });
    }

    // ── Font Style ────────────────────────────────────────────────────────

    private void setupFontStylePicker() {
        RadioGroup rg = findViewById(R.id.rg_font_style);
        String current = prefs.getString(AppCustomization.KEY_FONT_STYLE, AppCustomization.FONT_DEFAULT);
        switch (current) {
            case AppCustomization.FONT_BOLD:  rg.check(R.id.rb_font_bold);  break;
            case AppCustomization.FONT_SERIF: rg.check(R.id.rb_font_serif); break;
            case AppCustomization.FONT_MONO:  rg.check(R.id.rb_font_mono);  break;
            default:                          rg.check(R.id.rb_font_default); break;
        }
        rg.setOnCheckedChangeListener((group, id) -> {
            String style;
            if      (id == R.id.rb_font_bold)  style = AppCustomization.FONT_BOLD;
            else if (id == R.id.rb_font_serif) style = AppCustomization.FONT_SERIF;
            else if (id == R.id.rb_font_mono)  style = AppCustomization.FONT_MONO;
            else                               style = AppCustomization.FONT_DEFAULT;
            prefs.edit().putString(AppCustomization.KEY_FONT_STYLE, style).apply();
        });
    }

    // ── Number Format ─────────────────────────────────────────────────────

    private void setupNumberFormatPicker() {
        RadioGroup rg = findViewById(R.id.rg_number_format);
        String current = prefs.getString(AppCustomization.KEY_NUMBER_FORMAT, AppCustomization.FORMAT_PLAIN);
        rg.check(AppCustomization.FORMAT_COMMA.equals(current) ? R.id.rb_format_comma : R.id.rb_format_plain);
        rg.setOnCheckedChangeListener((group, id) -> {
            String fmt = (id == R.id.rb_format_comma) ? AppCustomization.FORMAT_COMMA : AppCustomization.FORMAT_PLAIN;
            prefs.edit().putString(AppCustomization.KEY_NUMBER_FORMAT, fmt).apply();
        });
    }

    // ── Circle Style ──────────────────────────────────────────────────────

    private void setupCircleStylePicker() {
        RadioGroup rg = findViewById(R.id.rg_circle_style);
        String current = prefs.getString(AppCustomization.KEY_CIRCLE_STYLE, AppCustomization.CIRCLE_NONE);
        switch (current) {
            case AppCustomization.CIRCLE_THIN:    rg.check(R.id.rb_circle_thin);    break;
            case AppCustomization.CIRCLE_MEDIUM:  rg.check(R.id.rb_circle_medium);  break;
            case AppCustomization.CIRCLE_THICK:   rg.check(R.id.rb_circle_thick);   break;
            case AppCustomization.CIRCLE_OVERLAY: rg.check(R.id.rb_circle_overlay); break;
            default:                              rg.check(R.id.rb_circle_none);    break;
        }
        rg.setOnCheckedChangeListener((group, id) -> {
            String style;
            if      (id == R.id.rb_circle_thin)    style = AppCustomization.CIRCLE_THIN;
            else if (id == R.id.rb_circle_medium)  style = AppCustomization.CIRCLE_MEDIUM;
            else if (id == R.id.rb_circle_thick)   style = AppCustomization.CIRCLE_THICK;
            else if (id == R.id.rb_circle_overlay) style = AppCustomization.CIRCLE_OVERLAY;
            else                                   style = AppCustomization.CIRCLE_NONE;
            prefs.edit().putString(AppCustomization.KEY_CIRCLE_STYLE, style).apply();
        });
    }

    // ── Credit Button Color ───────────────────────────────────────────────

    private void setupCreditColorPalette() {
        LinearLayout container = findViewById(R.id.palette_credit_color);
        container.removeAllViews();
        int selected = prefs.getInt(AppCustomization.KEY_CREDIT_COLOR, AppCustomization.DEFAULT_CREDIT_COLOR);
        buildColorRow(container, selected, color -> {
            prefs.edit().putInt(AppCustomization.KEY_CREDIT_COLOR, color).apply();
            setupCreditColorPalette();
        });
    }

    // ── Debit Button Color ────────────────────────────────────────────────

    private void setupDebitColorPalette() {
        LinearLayout container = findViewById(R.id.palette_debit_color);
        container.removeAllViews();
        int selected = prefs.getInt(AppCustomization.KEY_DEBIT_COLOR, AppCustomization.DEFAULT_DEBIT_COLOR);
        buildColorRow(container, selected, color -> {
            prefs.edit().putInt(AppCustomization.KEY_DEBIT_COLOR, color).apply();
            setupDebitColorPalette();
        });
    }

    // ── Background Picker ─────────────────────────────────────────────────

    private void setupBackgroundPicker() {
        LinearLayout container = findViewById(R.id.container_background_picker);
        container.removeAllViews();
        String selected = prefs.getString(AppCustomization.KEY_BACKGROUND, AppCustomization.DEFAULT_BACKGROUND);

        for (int i = 0; i < AppCustomization.BACKGROUNDS.length; i++) {
            final String bgName = AppCustomization.BACKGROUNDS[i];
            String label = AppCustomization.BACKGROUND_LABELS[i];

            Button btn = new Button(this);
            btn.setText(bgName.equals(selected) ? "✓  " + label : label);
            btn.setAlpha(bgName.equals(selected) ? 1.0f : 0.65f);
            btn.setOnClickListener(v -> {
                prefs.edit().putString(AppCustomization.KEY_BACKGROUND, bgName).apply();
                setupBackgroundPicker();
            });
            container.addView(btn);
        }
    }

    // ── Reset ─────────────────────────────────────────────────────────────

    private void resetToDefaults() {
        prefs.edit()
            .remove(AppCustomization.KEY_TEXT_COLOR)
            .remove(AppCustomization.KEY_FONT_STYLE)
            .remove(AppCustomization.KEY_NUMBER_FORMAT)
            .remove(AppCustomization.KEY_CIRCLE_STYLE)
            .remove(AppCustomization.KEY_CREDIT_COLOR)
            .remove(AppCustomization.KEY_DEBIT_COLOR)
            .remove(AppCustomization.KEY_BACKGROUND)
            .apply();
        setupTextColorPalette();
        setupFontStylePicker();
        setupNumberFormatPicker();
        setupCircleStylePicker();
        setupCreditColorPalette();
        setupDebitColorPalette();
        setupBackgroundPicker();
        Toast.makeText(this, "Appearance reset to defaults", Toast.LENGTH_SHORT).show();
    }

    // ── Color palette builder (shared) ────────────────────────────────────

    private void buildColorRow(LinearLayout container, int selectedColor, ColorCallback cb) {
        float density = getResources().getDisplayMetrics().density;
        int sizePx   = (int)(46 * density);
        int marginPx = (int)(5  * density);

        for (int color : PALETTE) {
            View swatch = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizePx, sizePx);
            lp.setMargins(marginPx, marginPx, marginPx, marginPx);
            swatch.setLayoutParams(lp);

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.OVAL);
            gd.setColor(color);
            if (color == selectedColor) {
                // White ring around selected swatch
                gd.setStroke((int)(3 * density), Color.WHITE);
            }
            swatch.setBackground(gd);

            final int c = color;
            swatch.setOnClickListener(v -> cb.onColorSelected(c));
            container.addView(swatch);
        }
    }

    interface ColorCallback {
        void onColorSelected(int color);
    }
}
