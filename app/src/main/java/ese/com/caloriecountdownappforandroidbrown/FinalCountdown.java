package ese.com.caloriecountdownappforandroidbrown;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Noir – Final Countdown: the Water Tracker Congratulations Graphic with its own message and
 * audio, shown once when the Main Countdown Balance comes down to 10,000 Cr or below.
 *
 * Audio: res/raw/final_countdown.wav (Android resource names must be lower case). If the file
 * is not in the project the graphic still shows, without sound.
 */
public class FinalCountdown extends WaterTrackerCongratulations {

    private static final String TAG = "FinalCountdown";

    static final int THRESHOLD = 10000;
    static final String MESSAGE =
            "Well done, don't STOP! now! It's the FINAL COUNTDOWN to zero balance: 🙌";

    private static final String PREFS = "Calorie_Countdown";
    /** True while the balance is above the threshold, i.e. the next drop to 10,000 or below should celebrate. */
    private static final String KEY_ARMED = "final_countdown_armed";
    private static final String AUDIO_RES_NAME = "final_countdown";

    private MediaPlayer player;
    private AlertDialog dialog;
    private DefaultLifecycleObserver lifecycleObserver;

    public FinalCountdown(Context context) {
        super(context, 0);
    }

    @Override
    protected String getGoalMessage() {
        return MESSAGE;
    }

    @Override
    protected String getTotalText() {
        return null; // the water total does not apply here
    }

    /** True when the balance has just come down from above 10,000 Cr to 10,000 Cr or below. */
    static boolean shouldTrigger(boolean armed, int balance) {
        return armed && balance <= THRESHOLD;
    }

    /** Armed again only once the balance goes back above 10,000 Cr (a new countdown event). */
    static boolean nextArmed(int balance) {
        return balance > THRESHOLD;
    }

    /**
     * Call after every Main Countdown Balance update. Shows the Final Countdown graphic once per
     * crossing; the state is kept in SharedPreferences so restarts and refreshes do not repeat it.
     */
    public static void onBalanceUpdated(Activity activity, int balance) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean armed = prefs.getBoolean(KEY_ARMED, false);
        boolean trigger = shouldTrigger(armed, balance);
        boolean newArmed = nextArmed(balance);
        if (newArmed != armed) {
            prefs.edit().putBoolean(KEY_ARMED, newArmed).apply();
        }
        if (!trigger) return;

        Log.d(TAG, "Balance " + balance + " reached the Final Countdown threshold");
        activity.runOnUiThread(() -> {
            if (activity.isFinishing() || activity.isDestroyed()) return;
            new FinalCountdown(activity).show();
        });
    }

    @Override
    protected void onShown(AlertDialog dialog) {
        this.dialog = dialog;
        if (context instanceof LifecycleOwner) {
            lifecycleObserver = new DefaultLifecycleObserver() {
                @Override
                public void onStop(@NonNull LifecycleOwner owner) {
                    releasePlayer(); // app left / interrupted: stop the music
                }

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    if (FinalCountdown.this.dialog != null && FinalCountdown.this.dialog.isShowing()) {
                        FinalCountdown.this.dialog.dismiss();
                    }
                }
            };
            ((LifecycleOwner) context).getLifecycle().addObserver(lifecycleObserver);
        }
        startAudio();
    }

    @Override
    protected void onDismissed() {
        releasePlayer();
        if (lifecycleObserver != null && context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().removeObserver(lifecycleObserver);
            lifecycleObserver = null;
        }
    }

    private void startAudio() {
        int resId = context.getResources().getIdentifier(AUDIO_RES_NAME, "raw", context.getPackageName());
        if (resId == 0) {
            Log.w(TAG, "res/raw/" + AUDIO_RES_NAME + ".wav not found; showing graphic without audio");
            return;
        }
        try {
            player = MediaPlayer.create(context, resId);
            if (player == null) {
                Log.e(TAG, "Could not create MediaPlayer for Final Countdown audio");
                return;
            }
            player.setOnCompletionListener(mp -> releasePlayer());
            player.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "Playback error: what=" + what + " extra=" + extra);
                releasePlayer();
                return true;
            });
            player.start();
        } catch (Exception e) {
            Log.e(TAG, "Could not play Final Countdown audio: " + e.getMessage(), e);
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player == null) return;
        try {
            if (player.isPlaying()) player.stop();
        } catch (IllegalStateException ignored) {
        }
        player.release();
        player = null;
    }
}
