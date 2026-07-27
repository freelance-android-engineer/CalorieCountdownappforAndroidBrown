package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * In-app library for viewing saved Voice Notes and Memo Pictures.
 *
 * <p>Voice notes are read from the app's private cache directory
 * (files matching {@code voice_note_*.m4a}).</p>
 *
 * <p>Memo pictures are read from the {@code memo} table in the local SQLite database.</p>
 *
 * <p>No files are moved, exposed to external storage, or altered in any way.</p>
 */
public class MediaLibraryActivity extends AppCompatActivity {

    private static final String TAG = "MediaLibraryActivity";

    private LinearLayout voiceNotesContainer;
    private LinearLayout memoPicturesContainer;
    private TextView     tvNoVoiceNotes;
    private TextView     tvNoMemoPictures;

    private SQLDatabase_Food_Items_CIF6 databaseHelper;

    // ── Active voice-note playback state ──────────────────────────────────────
    private MediaPlayer currentPlayer    = null;
    private Button      activePlayButton = null;
    private Button      activeStopButton = null;

    // ── Date formatter for voice-note filenames ────────────────────────────────
    private final SimpleDateFormat displayFmt =
            new SimpleDateFormat("dd MMM yyyy  HH:mm", Locale.getDefault());

    // ── Date formatter for memo dates stored in DB ─────────────────────────────
    private final SimpleDateFormat dbDateFmt =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    // ==========================================================================
    // Lifecycle
    // ==========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_library);

        databaseHelper       = new SQLDatabase_Food_Items_CIF6(this);
        voiceNotesContainer  = findViewById(R.id.voiceNotesContainer);
        memoPicturesContainer = findViewById(R.id.memoPicturesContainer);
        tvNoVoiceNotes       = findViewById(R.id.tvNoVoiceNotes);
        tvNoMemoPictures     = findViewById(R.id.tvNoMemoPictures);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        loadVoiceNotes();
        loadMemoPictures();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCurrentPlayer();
    }

    // ==========================================================================
    // Voice Notes
    // ==========================================================================

    /**
     * Reads all {@code voice_note_*.m4a} files from the app's cache directory
     * and builds a row for each, sorted newest-first.
     */
    private void loadVoiceNotes() {
        File cacheDir = getCacheDir();
        File[] files  = cacheDir.listFiles(
                (dir, name) -> name.startsWith("voice_note_") && name.endsWith(".m4a"));

        if (files == null || files.length == 0) {
            tvNoVoiceNotes.setVisibility(View.VISIBLE);
            return;
        }

        // Newest first (by last-modified timestamp)
        Arrays.sort(files, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));

        for (File file : files) {
            addVoiceNoteRow(file);
        }
    }

    /**
     * Adds one voice-note row to {@link #voiceNotesContainer}.
     */
    private void addVoiceNoteRow(File file) {
        String label = "Recording  —  "
                + displayFmt.format(new Date(file.lastModified()));

        // ── Row container ──────────────────────────────────────────────────────
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 14, 8, 14);
        row.setGravity(Gravity.CENTER_VERTICAL);

        // Label
        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextSize(13f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        tvLabel.setLayoutParams(lp);
        row.addView(tvLabel);

        // Play button
        Button btnPlay = new Button(this);
        btnPlay.setText("▶ Play");
        btnPlay.setTextSize(12f);
        LinearLayout.LayoutParams playLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        playLp.setMarginStart(6);
        btnPlay.setLayoutParams(playLp);

        // Stop button (starts disabled)
        Button btnStop = new Button(this);
        btnStop.setText("■ Stop");
        btnStop.setTextSize(12f);
        btnStop.setEnabled(false);
        LinearLayout.LayoutParams stopLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        stopLp.setMarginStart(6);
        btnStop.setLayoutParams(stopLp);

        row.addView(btnPlay);
        row.addView(btnStop);

        // Click handlers
        btnPlay.setOnClickListener(v -> startPlayback(file, btnPlay, btnStop));
        btnStop.setOnClickListener(v -> releaseCurrentPlayer());

        voiceNotesContainer.addView(row);

        // Thin divider
        View divider = new View(this);
        divider.setBackgroundColor(0xFFE0E0E0);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        voiceNotesContainer.addView(divider);
    }

    /**
     * Starts playing the given file. Any previously playing note is stopped first.
     */
    private void startPlayback(File file, Button btnPlay, Button btnStop) {
        // Stop whatever is currently playing
        releaseCurrentPlayer();

        try {
            currentPlayer = new MediaPlayer();
            currentPlayer.setDataSource(file.getAbsolutePath());
            currentPlayer.setOnCompletionListener(mp -> runOnUiThread(this::releaseCurrentPlayer));
            currentPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "Playback error: what=" + what + " extra=" + extra);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Playback error.", Toast.LENGTH_SHORT).show();
                    releaseCurrentPlayer();
                });
                return true;
            });
            currentPlayer.prepare();
            currentPlayer.start();

            // Track the buttons that belong to the active row
            activePlayButton = btnPlay;
            activeStopButton = btnStop;

            btnPlay.setEnabled(false);
            btnStop.setEnabled(true);

        } catch (IOException e) {
            Log.e(TAG, "Could not start playback: " + e.getMessage(), e);
            Toast.makeText(this, "Could not play this recording.", Toast.LENGTH_SHORT).show();
            releaseCurrentPlayer();
        }
    }

    /**
     * Stops and releases the active {@link MediaPlayer} and restores button states.
     */
    private void releaseCurrentPlayer() {
        if (currentPlayer != null) {
            try {
                if (currentPlayer.isPlaying()) currentPlayer.stop();
                currentPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing player: " + e.getMessage());
            }
            currentPlayer = null;
        }
        if (activePlayButton != null) {
            activePlayButton.setEnabled(true);
            activePlayButton = null;
        }
        if (activeStopButton != null) {
            activeStopButton.setEnabled(false);
            activeStopButton = null;
        }
    }

    // ==========================================================================
    // Memo Pictures
    // ==========================================================================

    /**
     * Reads all memo records from the database and displays those that have
     * a saved image.
     */
    private void loadMemoPictures() {
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getAllMemos();
            if (cursor == null || !cursor.moveToFirst()) {
                tvNoMemoPictures.setVisibility(View.VISIBLE);
                return;
            }

            boolean anyImageFound = false;

            do {
                int idxImage = cursor.getColumnIndex("memo_image");
                int idxDate  = cursor.getColumnIndex("memo_date");
                int idxTitle = cursor.getColumnIndex("memo_title");
                int idxText  = cursor.getColumnIndex("memo_text");

                String imageBase64 = (idxImage >= 0) ? cursor.getString(idxImage) : null;
                String date        = (idxDate  >= 0) ? cursor.getString(idxDate)  : "";
                String title       = (idxTitle >= 0) ? cursor.getString(idxTitle) : "";
                String text        = (idxText  >= 0) ? cursor.getString(idxText)  : "";

                if (imageBase64 != null && !imageBase64.trim().isEmpty()) {
                    addMemoPictureRow(imageBase64, date, title, text);
                    anyImageFound = true;
                }
            } while (cursor.moveToNext());

            if (!anyImageFound) {
                tvNoMemoPictures.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error loading memo pictures: " + e.getMessage(), e);
            tvNoMemoPictures.setVisibility(View.VISIBLE);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * Adds one memo-picture row (thumbnail + date/title + tap-to-fullscreen).
     */
    private void addMemoPictureRow(String imageBase64, String date,
                                   String title, String text) {
        // Decode a small thumbnail (inSampleSize=4 keeps memory low)
        Bitmap thumbnail = decodeBase64Bitmap(imageBase64, 4);
        if (thumbnail == null) return;          // skip rows with corrupted data

        // ── Row container ──────────────────────────────────────────────────────
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 12, 8, 12);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setClickable(true);
        row.setFocusable(true);

        // Thumbnail
        ImageView ivThumb = new ImageView(this);
        int thumbSizePx = dpToPx(72);
        LinearLayout.LayoutParams thumbLp = new LinearLayout.LayoutParams(thumbSizePx, thumbSizePx);
        thumbLp.setMarginEnd(12);
        ivThumb.setLayoutParams(thumbLp);
        ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivThumb.setImageBitmap(thumbnail);
        row.addView(ivThumb);

        // Meta (date + title/text snippet)
        LinearLayout meta = new LinearLayout(this);
        meta.setOrientation(LinearLayout.VERTICAL);
        meta.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView tvDate = new TextView(this);
        tvDate.setText(formatMemoDate(date));
        tvDate.setTextSize(12f);
        tvDate.setTextColor(0xFF757575);
        meta.addView(tvDate);

        if (title != null && !title.trim().isEmpty()) {
            TextView tvTitle = new TextView(this);
            tvTitle.setText(title.trim());
            tvTitle.setTextSize(14f);
            tvTitle.setTextColor(0xFF212121);
            meta.addView(tvTitle);
        }

        if (text != null && !text.trim().isEmpty()) {
            TextView tvSnippet = new TextView(this);
            String snippet = text.trim();
            if (snippet.length() > 60) snippet = snippet.substring(0, 60) + "…";
            tvSnippet.setText(snippet);
            tvSnippet.setTextSize(12f);
            tvSnippet.setTextColor(0xFF616161);
            meta.addView(tvSnippet);
        }

        row.addView(meta);

        // Tap → full-screen
        final String base64Copy = imageBase64;
        row.setOnClickListener(v -> showFullScreenImage(base64Copy, title));

        memoPicturesContainer.addView(row);

        // Thin divider
        View divider = new View(this);
        divider.setBackgroundColor(0xFFE0E0E0);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        memoPicturesContainer.addView(divider);
    }

    /**
     * Opens a full-screen AlertDialog showing the memo image.
     */
    private void showFullScreenImage(String imageBase64, String title) {
        Bitmap full = decodeBase64Bitmap(imageBase64, 1);
        if (full == null) {
            Toast.makeText(this, "Could not load image.", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(full);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);

        LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ivLp.setMargins(0, 0, 0, 0);
        imageView.setLayoutParams(ivLp);

        String dialogTitle = (title != null && !title.trim().isEmpty())
                ? title.trim()
                : "Memo Picture";

        new AlertDialog.Builder(this)
                .setTitle(dialogTitle)
                .setView(imageView)
                .setPositiveButton("Close", (d, w) -> d.dismiss())
                .show();
    }

    // ==========================================================================
    // Helpers
    // ==========================================================================

    /**
     * Decodes a Base64 string to a {@link Bitmap}.
     *
     * @param base64     the encoded image string.
     * @param sampleSize {@code BitmapFactory.Options.inSampleSize} (1 = full quality,
     *                   4 = quarter resolution thumbnail).
     * @return decoded bitmap, or {@code null} on failure.
     */
    private Bitmap decodeBase64Bitmap(String base64, int sampleSize) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = sampleSize;
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        } catch (Exception e) {
            Log.e(TAG, "Failed to decode bitmap: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses the DB date string ({@code yyyy-MM-dd HH:mm:ss}) and returns a
     * human-friendly label, e.g. {@code "27 Jul 2026  14:30"}.
     */
    private String formatMemoDate(String rawDate) {
        if (rawDate == null || rawDate.trim().isEmpty()) return "";
        try {
            Date d = dbDateFmt.parse(rawDate.trim());
            return d != null ? displayFmt.format(d) : rawDate;
        } catch (ParseException e) {
            return rawDate;
        }
    }

    /** Converts dp to pixels using the current display density. */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
