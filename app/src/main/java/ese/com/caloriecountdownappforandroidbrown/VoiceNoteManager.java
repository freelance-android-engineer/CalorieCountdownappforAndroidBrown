package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import java.io.File;

/**
 * Manages voice note recording and playback for the Food Notes Voice Notes feature.
 *
 * <p>Lifecycle: create one instance per dialog session, call {@link #release()} when done.</p>
 */
public class VoiceNoteManager {

    private static final String TAG = "VoiceNoteManager";

    // ---- Callbacks ---------------------------------------------------------

    public interface OnPlaybackCompleteListener {
        void onComplete();
    }

    public interface OnPlaybackErrorListener {
        void onError(String message);
    }

    // ---- State -------------------------------------------------------------

    private final Context context;
    private MediaRecorder mediaRecorder;
    private MediaPlayer   mediaPlayer;
    private File          audioFile;

    private boolean recording    = false;
    private boolean hasRecording = false;
    private boolean playing      = false;

    // ---- Constructor -------------------------------------------------------

    public VoiceNoteManager(Context context) {
        this.context = context.getApplicationContext();
    }

    // ---- State accessors ---------------------------------------------------

    public boolean isRecording()    { return recording; }
    public boolean hasRecording()   { return hasRecording; }
    public boolean isPlaying()      { return playing; }

    // ---- Recording ---------------------------------------------------------

    /**
     * Starts recording audio to a timestamped .m4a file in the app's cache directory.
     *
     * @return {@code true} if recording started successfully; {@code false} on failure.
     */
    public boolean startRecording() {
        try {
            File file = new File(context.getCacheDir(),
                    "voice_note_" + System.currentTimeMillis() + ".m4a");
            audioFile = file;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                mediaRecorder = new MediaRecorder(context);
            } else {
                //noinspection deprecation
                mediaRecorder = new MediaRecorder();
            }

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioEncodingBitRate(128000);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();

            recording = true;
            Log.d(TAG, "Recording started: " + file.getAbsolutePath());
            return true;

        } catch (Exception e) {
            Log.e(TAG, "Failed to start recording: " + e.getMessage(), e);
            releaseRecorder();
            return false;
        }
    }

    /**
     * Stops the current recording and updates {@link #hasRecording()}.
     */
    public void stopRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                Log.d(TAG, "Recording stopped.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error stopping recording: " + e.getMessage(), e);
        } finally {
            mediaRecorder  = null;
            recording      = false;
            hasRecording   = audioFile != null
                    && audioFile.exists()
                    && audioFile.length() > 0;
        }
    }

    // ---- Playback ----------------------------------------------------------

    /**
     * Plays back the most recently saved recording.
     *
     * @param onComplete called on the thread that created MediaPlayer when playback finishes.
     * @param onError    called when an error prevents playback.
     */
    public void startPlayback(OnPlaybackCompleteListener onComplete,
                              OnPlaybackErrorListener    onError) {
        if (audioFile == null || !audioFile.exists() || audioFile.length() == 0) {
            onError.onError("No recording available to play back.");
            return;
        }

        try {
            stopPlayback(); // release any previous player

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(mp -> {
                playing = false;
                onComplete.onComplete();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "MediaPlayer error: what=" + what + ", extra=" + extra);
                playing = false;
                onError.onError("Playback error occurred.");
                return true;
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
            playing = true;
            Log.d(TAG, "Playback started.");

        } catch (Exception e) {
            Log.e(TAG, "Failed to start playback: " + e.getMessage(), e);
            releasePlayer();
            onError.onError("Could not play recording: " + e.getMessage());
        }
    }

    /**
     * Stops playback if currently playing.
     */
    public void stopPlayback() {
        try {
            if (mediaPlayer != null) {
                if (playing) mediaPlayer.stop();
                mediaPlayer.release();
                Log.d(TAG, "Playback stopped.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error stopping playback: " + e.getMessage(), e);
        } finally {
            mediaPlayer = null;
            playing     = false;
        }
    }

    // ---- Lifecycle ---------------------------------------------------------

    /**
     * Stops any ongoing recording or playback and releases all resources.
     * Call this when the owning dialog or screen is dismissed/destroyed.
     */
    public void release() {
        if (recording) stopRecording();
        if (playing)   stopPlayback();
        releaseRecorder();
        releasePlayer();
    }

    // ---- Private helpers ---------------------------------------------------

    private void releaseRecorder() {
        try {
            if (mediaRecorder != null) mediaRecorder.release();
        } catch (Exception e) {
            Log.e(TAG, "Error releasing recorder: " + e.getMessage());
        } finally {
            mediaRecorder = null;
            recording     = false;
        }
    }

    private void releasePlayer() {
        try {
            if (mediaPlayer != null) mediaPlayer.release();
        } catch (Exception e) {
            Log.e(TAG, "Error releasing player: " + e.getMessage());
        } finally {
            mediaPlayer = null;
            playing     = false;
        }
    }
}
