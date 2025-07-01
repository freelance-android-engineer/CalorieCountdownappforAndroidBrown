package ese.com.caloriecountdownappforandroidbrown;



import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CreditBreakfastBox extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CREDIT_BREAKFAST_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_BREAKFAST";
    private static final String ACTION_BAZ = "ese.com.caloriecountdownappforandroid.action.BAZ";
    private static int REQUEST_CODE_BREAKFAST = 8;
    private static int mID = 68;

    // TODO: Rename parameters
    private static final String EXTRA_BALANCE = "ese.com.caloriecountdownappforandroid.extra.BALANCE";
    private static final String EXTRA_PARAM2 = "ese.com.caloriecountdownappforandroid.extra.PARAM2";

    private Context mContext;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCreditBalance(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CreditBreakfastBox.class);
        intent.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        intent.putExtra(EXTRA_BALANCE, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CreditBreakfastBox.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_BALANCE, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public CreditBreakfastBox() {
        super("Credit Breakfast Transaction"); //from i Green, can also get from Orange especially Brown(print) IDO.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        android.util.Log.d("BreakfastTransation", "Do we ever enter event handling by Service?");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CREDIT_BREAKFAST_TRANSACTION.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionStoreBalance(param1, param2);

                android.util.Log.d("BreakfastTransaction", "We are in Intent and have registered Action Credit Breakfast Handle");

                handleActionStoreBalance();
            } else if (ACTION_BAZ.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionBaz(param1, param2);
            } else
                android.util.Log.d("BreakfastTransaction", "Action Breakfast Transaction Handle NOT DETECTED");
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    @TargetApi(11)
    private void handleActionStoreBalance() {
        android.util.Log.d("BreakfastTransaction", "We made it we are Breakfast Log it In Service ready to do our stuff and restart");
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(CreditBreakfastBox.this);
        String currentBalance = data_model_adapter.RetrieveBalance();

        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Credit Breakfast Transaction (Food items you had for breakfast by lauching App and clicking Credit Button">
        //Need to find out right Context to give it.

        //NotifyBreakfast();
        SpecialThreadCIF15 specialThreadCIF15 = new SpecialThreadCIF15(this);
        specialThreadCIF15.ActivateAlarm();



    }

    ///////////////////QURANTINE/////////////////////////////////////////////////////////////

    private void SetNewDayAlarm(int h, int m, int d, int mon, int yr) {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();
        int hour = h;
        int minute = m;
        int day_of_month = d;
        int month = mon;
        int year = yr;
        java.util.Calendar Kalends = Calendar.getInstance();
        Kalends.setTime(new Date());
        Kalends.set(Calendar.HOUR_OF_DAY, hour);
        Kalends.set(Calendar.MINUTE, minute);
        Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
        Kalends.set(Calendar.MONTH, month);
        Kalends.set(Calendar.YEAR, year);
        Intent i = new Intent(CreditBreakfastBox.this, CCD_GUI_CD_CIF1.class);
        i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Kalends.getTimeInMillis(), pi);
        Log.d("Countdown", "Alarm Manager Set Yeah");
    }

    private void SetNewDayAlarm(Calendar Kalends) {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        Intent i = new Intent(CreditBreakfastBox.this, CCD_GUI_CD_CIF1.class);
        i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Kalends.getTimeInMillis(), pi);
        Log.d("Countdown", "Alarm Manager Set Yeah");
    }

    private Calendar GetCurrentKalends24(int h, int m) {
        int hour = h;
        int minute = m;
        java.util.Calendar Kalends = Calendar.getInstance();
        Kalends.setTime(new Date());
        Kalends.set(Calendar.HOUR_OF_DAY, hour);
        Kalends.set(Calendar.MINUTE, minute);
        long time = Kalends.getTimeInMillis();
        time = time + 86400000;
        Kalends.setTimeInMillis(time);
        return Kalends;
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void Toaster(String text) {
        Toast bread = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        bread.show();
    }

    private String todaysTag() {
        Calendar Greg = Calendar.getInstance();

        String year = new RoundingCIF13().IntToString(Greg.get(Calendar.YEAR));
        String month = new RoundingCIF13().IntToString(Greg.get(Calendar.MONTH));
        String day = new RoundingCIF13().IntToString(Greg.get(Calendar.DAY_OF_MONTH));

        month = Americanize(month);

        month = month + " " + day;
        month = month + ",";
        month = month + year;

        return month;
    }

    private String Americanize(String month) {
        if (month == "0") month = "January";
        if (month == "1") month = "February";
        if (month == "2") month = "March";
        if (month == "3") month = "April";
        if (month == "4") month = "May";
        if (month == "5") month = "June";
        if (month == "6") month = "July";
        if (month == "7") month = "August";
        if (month == "8") month = "September";
        if (month == "9") month = "October";
        if (month == "10") month = "November";
        if (month == "11") month = "December";

        return month;

    }

    private void NotifyBreakfast() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Credit Breakfast " + todaysTag())
                    .setContentText("Open Calorie Countdown App, Hit Credit Button to Log in all Food Items you had in your Breakfast Box.")
                    .setTicker("Breakfast Notification");
            Intent resultIntent = new Intent(this, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Breakfast", "We are Notifying the User as we Speak");
        }


        //("Credit Breakfast Transaction: Hit Ok and the Credit button in App and record all Food Items in your Breakfast Box");

        //Positive Button should Say Launch App.
    }
}
