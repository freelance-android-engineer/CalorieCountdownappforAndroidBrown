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
 * In-app library for viewing and deleting saved Voice Notes and Memo Pictures.
 *
 * <p>Voice notes are read from the app's private cache directory
 * (files matching {@code voice_note_*.m4a}).</p>
 *
 * <p>Memo pictures are read from the {@code memo} table in the local SQLite database.</p>
 *
 * <p>All files remain in private storage — nothing is exposed externally.</p>
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
    // File currently loaded into the player (to detect if it is deleted mid-play)
    private File        activeFile       = null;

    // ── Date formatters ───────────────────────────────────────────────────────
    private final SimpleDateFormat displayFmt =
            new SimpleDateFormat("dd MMM yyyy  HH:mm", Locale.getDefault());
    private final SimpleDateFormat dbDateFmt  =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    // ==========================================================================
    // Lifecycle
    // ==========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_library);

        databaseHelper        = new SQLDatabase_Food_Items_CIF6(this);
        voiceNotesContainer   = findViewById(R.id.voiceNotesContainer);
        memoPicturesContainer = findViewById(R.id.memoPicturesContainer);
        tvNoVoiceNotes        = findViewById(R.id.tvNoVoiceNotes);
        tvNoMemoPictures      = findViewById(R.id.tvNoMemoPictures);

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
    // Voice Notes — load
    // ==========================================================================

    private void loadVoiceNotes() {
        File   cacheDir = getCacheDir();
        File[] files    = cacheDir.listFiles(
                (dir, name) -> name.startsWith("voice_note_") && name.endsWith(".m4a"));

        if (files == null || files.length == 0) {
            tvNoVoiceNotes.setVisibility(View.VISIBLE);
            return;
        }

        // Newest first
        Arrays.sort(files, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));

        for (File file : files) {
            addVoiceNoteRow(file);
        }
    }

    private void addVoiceNoteRow(File file) {
        String label = "Recording  —  "
                + displayFmt.format(new Date(file.lastModified()));

        // ── Row wrapper (stored as tag on itself so we can remove it) ──────────
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 14, 8, 14);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setTag(file.getAbsolutePath());   // used to find divider for removal

        // Label
        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextSize(13f);
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(tvLabel);

        // Play button
        Button btnPlay = new Button(this);
        btnPlay.setText("▶");
        btnPlay.setTextSize(12f);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnLp.setMarginStart(4);
        btnPlay.setLayoutParams(btnLp);

        // Stop button
        Button btnStop = new Button(this);
        btnStop.setText("■");
        btnStop.setTextSize(12f);
        btnStop.setEnabled(false);
        LinearLayout.LayoutParams stopLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        stopLp.setMarginStart(4);
        btnStop.setLayoutParams(stopLp);

        // Delete button
        Button btnDelete = new Button(this);
        btnDelete.setText("🗑");
        btnDelete.setTextSize(14f);
        LinearLayout.LayoutParams delLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        delLp.setMarginStart(4);
        btnDelete.setLayoutParams(delLp);

        row.addView(btnPlay);
        row.addView(btnStop);
        row.addView(btnDelete);

        btnPlay.setOnClickListener(v   -> startPlayback(file, btnPlay, btnStop));
        btnStop.setOnClickListener(v   -> releaseCurrentPlayer());
        btnDelete.setOnClickListener(v -> confirmDeleteVoiceNote(file, row));

        voiceNotesContainer.addView(row);

        // Thin divider (tag = "divider_<path>" so we can remove it together with the row)
        View divider = new View(this);
        divider.setBackgroundColor(0xFFE0E0E0);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setTag("divider_" + file.getAbsolutePath());
        voiceNotesContainer.addView(divider);
    }

    // ==========================================================================
    // Voice Notes — playback
    // ==========================================================================

    private void startPlayback(File file, Button btnPlay, Button btnStop) {
        releaseCurrentPlayer();

        try {
            currentPlayer = new MediaPlayer();
            currentPlayer.setDataSource(file.getAbsolutePath());
            currentPlayer.setOnCompletionListener(mp ->
                    runOnUiThread(this::releaseCurrentPlayer));
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

            activePlayButton = btnPlay;
            activeStopButton = btnStop;
            activeFile       = file;

            btnPlay.setEnabled(false);
            btnStop.setEnabled(true);

        } catch (IOException e) {
            Log.e(TAG, "Could not start playback: " + e.getMessage(), e);
            Toast.makeText(this, "Could not play this recording.", Toast.LENGTH_SHORT).show();
            releaseCurrentPlayer();
        }
    }

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
        if (activePlayButton != null) { activePlayButton.setEnabled(true);  activePlayButton = null; }
        if (activeStopButton != null) { activeStopButton.setEnabled(false); activeStopButton = null; }
        activeFile = null;
    }

    // ==========================================================================
    // Voice Notes — delete
    // ==========================================================================

    private void confirmDeleteVoiceNote(File file, LinearLayout row) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recording")
                .setMessage("Delete this voice note?\n\n"
                        + displayFmt.format(new Date(file.lastModified()))
                        + "\n\nThis cannot be undone.")
                .setPositiveButton("Delete", (d, w) -> deleteVoiceNote(file, row))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteVoiceNote(File file, LinearLayout row) {
        // Stop playback if this file is currently playing
        if (file.equals(activeFile)) {
            releaseCurrentPlayer();
        }

        boolean deleted = file.delete();
        if (deleted) {
            // Remove row and its divider from the container
            voiceNotesContainer.removeView(row);
            View divider = voiceNotesContainer.findViewWithTag(
                    "divider_" + file.getAbsolutePath());
            if (divider != null) voiceNotesContainer.removeView(divider);

            // Show "empty" label if no rows remain
            if (voiceNotesContainer.getChildCount() == 0) {
                tvNoVoiceNotes.setVisibility(View.VISIBLE);
            }
            Toast.makeText(this, "Recording deleted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Could not delete recording. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // ==========================================================================
    // Memo Pictures — load
    // ==========================================================================

    private void loadMemoPictures() {
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getAllMemos();
            if (cursor == null || !cursor.moveToFirst()) {
                tvNoMemoPictures.setVisibility(View.VISIBLE);
                return;
            }

            boolean anyImage = false;
            do {
                int idxId    = cursor.getColumnIndex("memo_id");
                int idxImage = cursor.getColumnIndex("memo_image");
                int idxDate  = cursor.getColumnIndex("memo_date");
                int idxTitle = cursor.getColumnIndex("memo_title");
                int idxText  = cursor.getColumnIndex("memo_text");

                int    memoId      = (idxId    >= 0) ? cursor.getInt(idxId)       : -1;
                String imageBase64 = (idxImage >= 0) ? cursor.getString(idxImage) : null;
                String date        = (idxDate  >= 0) ? cursor.getString(idxDate)  : "";
                String title       = (idxTitle >= 0) ? cursor.getString(idxTitle) : "";
                String text        = (idxText  >= 0) ? cursor.getString(idxText)  : "";

                if (imageBase64 != null && !imageBase64.trim().isEmpty()) {
                    addMemoPictureRow(memoId, imageBase64, date, title, text);
                    anyImage = true;
                }
            } while (cursor.moveToNext());

            if (!anyImage) tvNoMemoPictures.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e(TAG, "Error loading memo pictures: " + e.getMessage(), e);
            tvNoMemoPictures.setVisibility(View.VISIBLE);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private void addMemoPictureRow(int memoId, String imageBase64,
                                   String date, String title, String text) {
        Bitmap thumbnail = decodeBase64Bitmap(imageBase64, 4);
        if (thumbnail == null) return;

        // ── Row ───────────────────────────────────────────────────────────────
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(8, 12, 8, 12);
        row.setGravity(Gravity.CENTER_VERTICAL);

        // Thumbnail
        ImageView ivThumb = new ImageView(this);
        int sz = dpToPx(72);
        LinearLayout.LayoutParams thumbLp = new LinearLayout.LayoutParams(sz, sz);
        thumbLp.setMarginEnd(12);
        ivThumb.setLayoutParams(thumbLp);
        ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivThumb.setImageBitmap(thumbnail);
        row.addView(ivThumb);

        // Meta text
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

        // Delete button
        Button btnDelete = new Button(this);
        btnDelete.setText("🗑");
        btnDelete.setTextSize(14f);
        LinearLayout.LayoutParams delLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        delLp.setMarginStart(4);
        btnDelete.setLayoutParams(delLp);
        row.addView(btnDelete);

        // Tap row (excluding delete btn) → full-screen
        final String base64Copy = imageBase64;
        ivThumb.setOnClickListener(v -> showFullScreenImage(base64Copy, title));
        meta.setOnClickListener(v   -> showFullScreenImage(base64Copy, title));

        // Delete
        btnDelete.setOnClickListener(v -> confirmDeleteMemoPicture(memoId, row, title));

        // Tag row for removal
        row.setTag("memorow_" + memoId);
        memoPicturesContainer.addView(row);

        // Divider
        View divider = new View(this);
        divider.setBackgroundColor(0xFFE0E0E0);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setTag("memodiv_" + memoId);
        memoPicturesContainer.addView(divider);
    }

    // ==========================================================================
    // Memo Pictures — full screen
    // ==========================================================================

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

        String dialogTitle = (title != null && !title.trim().isEmpty())
                ? title.trim() : "Memo Picture";

        new AlertDialog.Builder(this)
                .setTitle(dialogTitle)
                .setView(imageView)
                .setPositiveButton("Close", (d, w) -> d.dismiss())
                .show();
    }

    // ==========================================================================
    // Memo Pictures — delete
    // ==========================================================================

    private void confirmDeleteMemoPicture(int memoId, LinearLayout row, String title) {
        String label = (title != null && !title.trim().isEmpty())
                ? "\"" + title.trim() + "\""
                : "this memo picture";

        new AlertDialog.Builder(this)
                .setTitle("Delete Memo Picture")
                .setMessage("Delete " + label + "?\n\nThis cannot be undone.")
                .setPositiveButton("Delete", (d, w) -> deleteMemoPicture(memoId, row))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteMemoPicture(int memoId, LinearLayout row) {
        int rows = databaseHelper.deleteMemoById(memoId);
        if (rows > 0) {
            memoPicturesContainer.removeView(row);
            View divider = memoPicturesContainer.findViewWithTag("memodiv_" + memoId);
            if (divider != null) memoPicturesContainer.removeView(divider);

            if (memoPicturesContainer.getChildCount() == 0) {
                tvNoMemoPictures.setVisibility(View.VISIBLE);
            }
            Toast.makeText(this, "Memo picture deleted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Could not delete memo picture. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // ==========================================================================
    // Helpers
    // ==========================================================================

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

    private String formatMemoDate(String rawDate) {
        if (rawDate == null || rawDate.trim().isEmpty()) return "";
        try {
            Date d = dbDateFmt.parse(rawDate.trim());
            return d != null ? displayFmt.format(d) : rawDate;
        } catch (ParseException e) {
            return rawDate;
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
