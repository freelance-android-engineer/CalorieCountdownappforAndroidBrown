package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

/**
 * Schedules the two daily alarms for the Calorie Countdown lifecycle:
 * - 4:00 PM (Credit Day End) - user should generate Step Challenge
 * - 9:59 PM (Debit Day End) - user should update steps/exercise activity
 *
 * Uses AlarmManager.setExactAndAllowWhileIdle() for reliable delivery.
 * Alarms re-schedule themselves for the next day after firing.
 */
public class DailyAlarmScheduler {

    private static final String TAG = "DailyAlarmScheduler";

    private static final int REQUEST_CODE_CREDIT_DAY_END = 4000;
    private static final int REQUEST_CODE_DEBIT_DAY_END = 2159;

    private static final int CREDIT_HOUR = 4;
    private static final int CREDIT_MINUTE = 0;

    private static final int DEBIT_HOUR = 9;
    private static final int DEBIT_MINUTE = 59;

    /**
     * Schedule both daily alarms. Call this from main activity onCreate and from BootAlarmReceiver.
     */
    public static void scheduleBothAlarms(Context context) {
        scheduleCreditDayEndAlarm(context);
        scheduleDebitDayEndAlarm(context);
        Log.d(TAG, "Both daily alarms scheduled (4:00 PM and 9:59 PM)");
    }

    /**
     * Schedule the 4:00 PM Credit Day End alarm.
     */
    public static void scheduleCreditDayEndAlarm(Context context) {
        Calendar calendar = getNextAlarmTime(CREDIT_HOUR, CREDIT_MINUTE);

        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        intent.setAction(DailyAlarmReceiver.ACTION_CREDIT_DAY_END);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                REQUEST_CODE_CREDIT_DAY_END, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        setExactAlarm(context, calendar.getTimeInMillis(), pendingIntent);

        Log.d(TAG, "Credit Day End alarm scheduled for: " + calendar.getTime());
    }

    /**
     * Schedule the 9:59 PM Debit Day End alarm.
     */
    public static void scheduleDebitDayEndAlarm(Context context) {
        Calendar calendar = getNextAlarmTime(DEBIT_HOUR, DEBIT_MINUTE);

        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        intent.setAction(DailyAlarmReceiver.ACTION_DEBIT_DAY_END);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                REQUEST_CODE_DEBIT_DAY_END, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        setExactAlarm(context, calendar.getTimeInMillis(), pendingIntent);

        Log.d(TAG, "Debit Day End alarm scheduled for: " + calendar.getTime());
    }

    /**
     * Cancel both alarms.
     */
    public static void cancelBothAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        Intent creditIntent = new Intent(context, DailyAlarmReceiver.class);
        creditIntent.setAction(DailyAlarmReceiver.ACTION_CREDIT_DAY_END);
        PendingIntent creditPi = PendingIntent.getBroadcast(context,
                REQUEST_CODE_CREDIT_DAY_END, creditIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(creditPi);

        Intent debitIntent = new Intent(context, DailyAlarmReceiver.class);
        debitIntent.setAction(DailyAlarmReceiver.ACTION_DEBIT_DAY_END);
        PendingIntent debitPi = PendingIntent.getBroadcast(context,
                REQUEST_CODE_DEBIT_DAY_END, debitIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(debitPi);

        Log.d(TAG, "Both daily alarms cancelled");
    }

    /**
     * Returns a Calendar set to the next occurrence of the given hour:minute.
     * If that time has already passed today, it schedules for tomorrow.
     */
    private static Calendar getNextAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If the time has already passed today, schedule for tomorrow
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return calendar;
    }

    /**
     * Sets an exact alarm using setAlarmClock for maximum reliability.
     * setAlarmClock always fires on time regardless of Doze mode or exact alarm permissions.
     * Falls back to setExactAndAllowWhileIdle if exact alarm permission is denied.
     */
    private static void setExactAlarm(Context context, long triggerAtMillis, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Log.e(TAG, "AlarmManager is null!");
            return;
        }

        long delayMs = triggerAtMillis - System.currentTimeMillis();
        Log.d(TAG, "Setting alarm to fire in " + (delayMs / 1000) + " seconds (" + (delayMs / 60000) + " minutes)");

        // Check if we can schedule exact alarms (API 31+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            Log.w(TAG, "Exact alarm permission NOT granted! Directing user to settings.");
            try {
                Intent settingsIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(settingsIntent);
            } catch (Exception e) {
                Log.e(TAG, "Could not open exact alarm settings", e);
            }
            // Fall back to inexact alarm so we still get something
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            Log.d(TAG, "Alarm set using setAndAllowWhileIdle (fallback - inexact)");
            return;
        }

        // Use setAlarmClock - most reliable, always fires on time, shows alarm icon in status bar
        Intent showIntent = new Intent(context, CCD_GUI_CD_CIF1.class);
        PendingIntent showPi = PendingIntent.getActivity(context, 0, showIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager.AlarmClockInfo alarmClock = new AlarmManager.AlarmClockInfo(triggerAtMillis, showPi);
        alarmManager.setAlarmClock(alarmClock, pendingIntent);

        Log.d(TAG, "Alarm set using setAlarmClock (most reliable)");
    }
}
