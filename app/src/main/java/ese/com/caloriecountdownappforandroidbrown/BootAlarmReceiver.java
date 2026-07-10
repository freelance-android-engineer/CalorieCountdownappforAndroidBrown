package ese.com.caloriecountdownappforandroidbrown;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Re-registers all daily alarms (3:30 PM, 4:00 PM, 9:59 PM) after device restart.
 * Triggered by BOOT_COMPLETED broadcast.
 */
public class BootAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "BootAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || "android.intent.action.QUICKBOOT_POWERON".equals(action)
                || "com.htc.intent.action.QUICKBOOT_POWERON".equals(action)) {
            Log.d(TAG, "Device booted (" + action + ") - re-registering daily alarms");
            DailyAlarmScheduler.scheduleBothAlarms(context);
        }
    }
}

