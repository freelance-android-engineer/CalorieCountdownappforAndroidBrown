package ese.com.caloriecountdownappforandroidbrown;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * BroadcastReceiver that handles the two daily alarms:
 * - 3:30 PM (Credit Day End) - Prompts user to generate Step Challenge
 * - 9:59 PM (Debit Day End) - Prompts user to update debit activity (steps/exercise)
 */
public class DailyAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "DailyAlarmReceiver";
    public static final String ACTION_CREDIT_DAY_END = "ese.com.caloriecountdownappforandroidbrown.CREDIT_DAY_END";
    public static final String ACTION_DEBIT_DAY_END = "ese.com.caloriecountdownappforandroidbrown.DEBIT_DAY_END";

    private static final String CHANNEL_ID_CREDIT = "credit_day_end_channel";
    private static final String CHANNEL_ID_DEBIT = "debit_day_end_channel";

    private static final int NOTIFICATION_ID_CREDIT = 4000;
    private static final int NOTIFICATION_ID_DEBIT = 2159;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Use goAsync() to prevent the system from killing the receiver before we finish
        final PendingResult pendingResult = goAsync();

        String action = intent.getAction();
        Log.d(TAG, "Alarm received with action: " + action);

        try {
            if (ACTION_CREDIT_DAY_END.equals(action)) {
                showCreditDayEndNotification(context);
                // Re-schedule for tomorrow since we use setExact (not repeating)
                DailyAlarmScheduler.scheduleCreditDayEndAlarm(context);
            } else if (ACTION_DEBIT_DAY_END.equals(action)) {
                showDebitDayEndNotification(context);
                // Re-schedule for tomorrow
                DailyAlarmScheduler.scheduleDebitDayEndAlarm(context);
            }
        } finally {
            pendingResult.finish();
        }
    }

    /**
     * 3:30 PM Notification - Credit Day End
     * Tells user the day is up and they should generate their Step Challenge
     */
    private void showCreditDayEndNotification(Context context) {
        Log.d(TAG, "Showing Credit Day End notification (3:30 PM)");

        createNotificationChannel(context, CHANNEL_ID_CREDIT,
                "Credit Day End", "Daily 3:30 PM reminder to generate Step Challenge");

        // Open main activity when tapped
        Intent openIntent = new Intent(context, CCD_GUI_CD_CIF1.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_CREDIT,
                openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_CREDIT)
                .setSmallIcon(R.drawable.ic_launcher2)
                .setContentTitle("Credit Day End - 3:30 PM")
                .setContentText("Your day is up! Generate your Step Challenge now.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your Credit Day has ended. Check your remaining calories and generate your Steps or Physical Activity Challenge to countdown your balance by 9:59 PM tonight."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID_CREDIT, builder.build());
        }
    }

    /**
     * 9:59 PM Notification - Debit Day End
     * Tells user to update the app with their steps/exercise for the day
     */
    private void showDebitDayEndNotification(Context context) {
        Log.d(TAG, "Showing Debit Day End notification (9:59 PM)");

        createNotificationChannel(context, CHANNEL_ID_DEBIT,
                "Debit Day End", "Daily 9:59 PM reminder to update debit activity");

        // Open main activity when tapped
        Intent openIntent = new Intent(context, CCD_GUI_CD_CIF1.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_DEBIT,
                openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_DEBIT)
                .setSmallIcon(R.drawable.ic_launcher2)
                .setContentTitle("Debit Day End - 9:59 PM")
                .setContentText("Update your Steps and Exercise activity now!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Time to update your Debit activity! Enter your Steps, Exercise, and Physical Activity performed today to see your Countdown Report."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID_DEBIT, builder.build());
        }
    }

    private void createNotificationChannel(Context context, String channelId, String name, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
