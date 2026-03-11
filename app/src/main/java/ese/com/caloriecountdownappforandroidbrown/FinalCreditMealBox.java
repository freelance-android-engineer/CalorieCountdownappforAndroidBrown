package ese.com.caloriecountdownappforandroidbrown;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
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
public class FinalCreditMealBox extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CREDIT_FINALMEAL_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_DINNER";
    private static final String ACTION_BAZ = "ese.com.caloriecountdownappforandroid.action.BAZ";
    private static int REQUEST_CODE_FINAL_MEAL = 10;

    // TODO: Rename parameters
    private static final String EXTRA_BALANCE = "ese.com.caloriecountdownappforandroid.extra.BALANCE";
    private static final String EXTRA_PARAM2 = "ese.com.caloriecountdownappforandroid.extra.PARAM2";
    public static final long twentyFourHours = (24*60*60*1000);

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCreditBalance(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CreditBreakfastBox.class);
        intent.setAction(ACTION_CREDIT_FINALMEAL_TRANSACTION);
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
        Intent intent = new Intent(context, FinalCreditMealBox.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_BALANCE, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public FinalCreditMealBox() {
        super("Credit Dinner Transaction"); //from i Green, can also get from Orange especially Brown(print) IDO.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        android.util.Log.d("CreditDinnerTransation", "Do we ever enter event handling by Service?");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CREDIT_FINALMEAL_TRANSACTION.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionStoreBalance(param1, param2);
                android.util.Log.d("CreditDinnerTransaction", "We are in Intent and have registered Action Credit Dinner Handle");
                handleActionStoreBalance();
            } else if (ACTION_BAZ.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionBaz(param1, param2);
            } else
                android.util.Log.d("CreditDinnerTransaction", "Action Dinner Transaction Handle NOT DETECTED");
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStoreBalance() {
        android.util.Log.d("CreditDinnerTransaction", "We made it we are Dinner Log it In Service ready to do our stuff and restart");
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(FinalCreditMealBox.this);
        String currentBalance = data_model_adapter.RetrieveBalance();

        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Credit Breakfast Transaction (Food items you had for breakfast by lauching App and clicking Credit Button">
        //Need to find out right Context to give it.

        Toaster("Credit Dinner Transaction: Hit Ok and the Credit button in app and record all Food Items in your Dinner Box");

        //Positive Button should Say Launch App.


        Intent i = new Intent(FinalCreditMealBox.this, CCD_GUI_CD_CIF1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Start New Day",true);
        startActivity(i);

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
        Intent i = new Intent(FinalCreditMealBox.this, CCD_GUI_CD_CIF1.class);
        i.setAction(ACTION_CREDIT_FINALMEAL_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_FINAL_MEAL, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Kalends.getTimeInMillis(), pi);
        alarmManager.setRepeating(AlarmManager.RTC,Kalends.getTimeInMillis(),twentyFourHours, pi);
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

        Intent i = new Intent(FinalCreditMealBox.this, CCD_GUI_CD_CIF1.class);
        i.setAction(ACTION_CREDIT_FINALMEAL_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_FINAL_MEAL, i, 0);
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

}
