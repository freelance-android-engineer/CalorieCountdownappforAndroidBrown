package ese.com.caloriecountdownappforandroidbrown;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Announces a change to the recommended Client Step Challenge with a device notification,
 * using the same NotificationCompat setup as {@link DailyAlarmReceiver}.
 *
 * Shown once per recommended value: the value is recorded in the "Calorie_Countdown" prefs
 * only after the notification is posted, so app restarts and screen rebuilds don't repeat it,
 * and a device without notification permission gets it on a later launch once allowed.
 */
public final class StepChallengeUpdateNotifier {

    private static final String TAG = "StepChallengeNotifier";
    private static final String PREFS = "Calorie_Countdown";
    private static final String KEY_ANNOUNCED_TARGET = "announced_recommended_step_challenge";
    private static final String CHANNEL_ID = "step_challenge_update_channel";
    private static final int NOTIFICATION_ID = 15500;

    private StepChallengeUpdateNotifier() {}

    public static void notifyIfTargetChanged(Context context) {
        try {
            int target = KittyCalculator.RECOMMENDED_STEP_CHALLENGE;
            SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
            if (prefs.getInt(KEY_ANNOUNCED_TARGET, -1) == target) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    && context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Notification permission not granted; will retry on next launch");
                return;
            }

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager == null) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "Step Challenge Updates", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Changes to the recommended Client Step Challenge");
                manager.createNotificationChannel(channel);
            }

            Intent openIntent = new Intent(context, CCD_GUI_CD_CIF1.class);
            openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID,
                    openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            String text = String.format(java.util.Locale.US,
                    "Your recommended Client Step Challenge is now %,d Steps or equivalent Activity.", target);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Step Challenge Updated")
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            manager.notify(NOTIFICATION_ID, builder.build());
            prefs.edit().putInt(KEY_ANNOUNCED_TARGET, target).apply();
            Log.d(TAG, "Posted recommended Step Challenge notification: " + target);
        } catch (Exception e) {
            Log.e(TAG, "Could not post Step Challenge notification: " + e.getMessage(), e);
        }
    }
}
