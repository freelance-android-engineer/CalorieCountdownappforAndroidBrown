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
    public static final String ACTION_4PM_FOOD_NOTES = "ese.com.caloriecountdownappforandroidbrown.FOURPM_FOOD_NOTES";

    private static final String CHANNEL_ID_CREDIT = "credit_day_end_channel";
    private static final String CHANNEL_ID_DEBIT = "debit_day_end_channel";
    private static final String CHANNEL_ID_4PM = "fourpm_food_notes_channel";

    private static final int NOTIFICATION_ID_CREDIT = 4000;
    private static final int NOTIFICATION_ID_DEBIT = 2159;
    private static final int NOTIFICATION_ID_4PM = 1600;

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
                // Trigger the midnight scrape snapshot if the main activity is alive
                triggerMidnightScrape(context);
                // Re-schedule for tomorrow
                DailyAlarmScheduler.scheduleDebitDayEndAlarm(context);
            } else if (ACTION_4PM_FOOD_NOTES.equals(action)) {
                show4PMFoodNotesNotification(context);
                // Re-schedule for tomorrow
                DailyAlarmScheduler.schedule4PMFoodNotesAlarm(context);
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

    /**
     * 4:00 PM Notification — Food Notes Processing
     * Prompts user to open Food Notes and run the 4PM processing workflow.
     * Tapping the notification opens FoodNoteTableActivity and auto-starts the workflow.
     */
    private void show4PMFoodNotesNotification(Context context) {
        Log.d(TAG, "Showing 4PM Food Notes Processing notification");

        createNotificationChannel(context, CHANNEL_ID_4PM,
                "4PM Food Notes", "Daily 4:00 PM reminder to process yesterday's food notes");

        // Open FoodNoteTableActivity and auto-trigger the 4PM workflow
        Intent openIntent = new Intent(context, FoodNoteTableActivity.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openIntent.putExtra("start_4pm_processing", true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_4PM,
                openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_4PM)
                .setSmallIcon(R.drawable.ic_launcher2)
                .setContentTitle("4PM Food Notes Processing")
                .setContentText("Time to process yesterday's food notes!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("It's 4:00 PM — tap to open your Food Notes and process yesterday's entries. "
                                + "The AI will estimate any missing calorie values, and you can review the totals before confirming."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID_4PM, builder.build());
        }
    }

    /**
     * Trigger the midnight scrape (Store_Dayend2) snapshot.
     *
     * If CCD_GUI_CD_CIF1 is alive, call Store_Dayend2() directly via Handler.
     * If not alive (app in background), do the DB snapshot from the receiver using
     * SharedPreferences + SQLDatabase directly — so the snapshot is never lost.
     */
    private void triggerMidnightScrape(Context context) {
        // Path 1: Main activity is alive — delegate to it (it has balance in memory)
        if (CCD_GUI_CD_CIF1.instance != null) {
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                try {
                    CCD_GUI_CD_CIF1.instance.Store_Dayend2_Public();
                    Log.d(TAG, "[triggerMidnightScrape] Store_Dayend2 via live instance.");
                } catch (Exception e) {
                    Log.e(TAG, "[triggerMidnightScrape] Exception calling Store_Dayend2: " + e.getMessage());
                }
            });
            return;
        }

        // Path 2: App in background — snapshot from receiver directly
        try {
            java.util.Calendar now = java.util.Calendar.getInstance();
            String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                    .format(now.getTime());

            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(context);
            if (db.isDayEnd2StoredForDate(today)) {
                Log.d(TAG, "[triggerMidnightScrape] Already stored for " + today);
                return;
            }

            // Read balance from SharedPreferences (persisted by StoreCountdownBalance)
            android.content.SharedPreferences prefs = context.getSharedPreferences("Calorie_Countdown", 0);
            String balanceStr = prefs.getString("countdown_balance", "0");
            int balance = 0;
            try { balance = Integer.parseInt(balanceStr.replace(",", "").trim()); }
            catch (NumberFormatException ignored) {}

            String gender = prefs.getString("Gender_Type", "male");
            int dailyBudget = "female".equalsIgnoreCase(gender) ? 2000 : 2500;
            int todayFood = db.getTotalFoodNoteCaloriesToday();
            int kitty = dailyBudget - todayFood;

            int daysToZero = balance > 0 ? (int) Math.ceil((double) balance / 250.0) : 0;
            java.util.Calendar zeroDate = java.util.Calendar.getInstance();
            zeroDate.add(java.util.Calendar.DAY_OF_YEAR, daysToZero);
            String estimatedZeroDate = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                    .format(zeroDate.getTime());

            db.storeDayEnd2Snapshot(today, balance, dailyBudget, todayFood, kitty, estimatedZeroDate);
            Log.d(TAG, "[triggerMidnightScrape] Background snapshot stored: balance=" + balance + " kitty=" + kitty);

            // Azure backend sync — fire-and-forget from receiver
            final int finalBalance = balance;
            final int finalBudget = dailyBudget;
            final int finalFood = todayFood;
            final int finalKitty = kitty;
            final String finalZeroDate = estimatedZeroDate;
            final String finalToday = today;
            final android.content.Context appCtx = context.getApplicationContext();
            new Thread(() -> {
                try {
                    android.content.SharedPreferences p2 = appCtx.getSharedPreferences("Calorie_Countdown", 0);
                    String clientName = p2.getString("client_name", "Client");
                    if (clientName == null || clientName.isEmpty()) clientName = "Client";
                    SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(appCtx);
                    apiClient.syncDayEnd(clientName, finalToday, finalBalance, finalBudget,
                            finalFood, finalKitty, finalZeroDate, new ApiResultCallback() {
                        @Override public void onSuccess(String response) {
                            Log.d(TAG, "[triggerMidnightScrape][syncDayEnd] OK date=" + finalToday);
                        }
                        @Override public void onFailure() {
                            Log.w(TAG, "[triggerMidnightScrape][syncDayEnd] Backend sync failed — local DB is authoritative.");
                        }
                    });
                } catch (Exception ex) {
                    Log.w(TAG, "[triggerMidnightScrape][syncDayEnd] Exception: " + ex.getMessage());
                }
            }).start();

        } catch (Exception e) {
            Log.e(TAG, "[triggerMidnightScrape] Background snapshot failed: " + e.getMessage(), e);
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
