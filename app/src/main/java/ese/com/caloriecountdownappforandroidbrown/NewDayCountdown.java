package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NewDayCountdown extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_STORE_BALANCE = "ese.com.caloriecountdownappforandroid.action.STORE_BALANCE";
    private static final String ACTION_BAZ = "ese.com.caloriecountdownappforandroid.action.BAZ";
    private static int REQUEST_CODE_NEW_DAY = 7;
    public static String START_WEIGHT_LOSS = "ese.com.caloriecountdownappforandroid.action.START_WEIGHT_LOSS";

    // TODO: Rename parameters
    private static final String EXTRA_BALANCE = "ese.com.caloriecountdownappforandroid.extra.BALANCE";
    private static final String EXTRA_PARAM2 = "ese.com.caloriecountdownappforandroid.extra.PARAM2";

    public interface MyCallBack
    {
        public void refreshMainActivity();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionStoreBalance(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NewDayCountdown.class);
        intent.setAction(ACTION_STORE_BALANCE);
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
        Intent intent = new Intent(context, NewDayCountdown.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_BALANCE, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public NewDayCountdown() {
        super("NewDayCountdown");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        android.util.Log.d("NewDay","Do we ever enter event handling by Service?");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_STORE_BALANCE.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionStoreBalance(param1, param2);
                android.util.Log.d("NewDay","We are in Intent and have registered Action Store Balance Handle");
                handleActionStoreBalance();
            } else if (ACTION_BAZ.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_BALANCE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                //handleActionBaz(param1, param2);
            }
            else android.util.Log.d("NewDay","Action Store Balance Handle NOT DETECTED");
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStoreBalance()
    {

        NewDay();
        /*android.util.Log.d("New Day", "We made it we are NEW DAY Service ready to do our stuff and restart");
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(NewDayCountdown.this);
        String currentBalance = data_model_adapter.RetrieveBalance();
        int mBalance = new RoundingCIF13().StringToInt(currentBalance);
        if(data_model_adapter.getSex() == "MALE")
        {
            mBalance = mBalance - 2500;
        }
        if(data_model_adapter.getSex() == "FEMALE")
        {
            mBalance = mBalance - 2000;
        }
        else
        {
          mBalance = mBalance - 2500;
        }
        data_model_adapter.StoreBalance(new RoundingCIF13().IntToString(mBalance));
        data_model_adapter.StoreDayEndBalance((mBalance - 350 + 2500));
        //SetNewDayAlarm(GetCurrentKalends24(21,00));*/
        boolean yes = true;
        boolean no = false;

        Intent i = new Intent(NewDayCountdown.this, CCD_GUI_CD_CIF1.class);
        i.putExtra(START_WEIGHT_LOSS,no);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void NewDay()
    {
        CCD_GUI_CD_CIF1 proxy = CCD_GUI_CD_CIF1.instance;
        proxy.Start_Notification_ActivityCIF5();

        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(NewDayCountdown.this);
        String currentBalance = data_model_adapter.RetrieveBalance();
        int mBalance = new RoundingCIF13().StringToInt(currentBalance);
        if(data_model_adapter.getSex() == "MALE")
        {
            mBalance = mBalance - 2000;
        }
        if(data_model_adapter.getSex() == "FEMALE")
        {
            mBalance = mBalance - 2000;
        }
        else
        {
            mBalance = mBalance - 2000;
        }
        data_model_adapter.StoreBalance(new RoundingCIF13().IntToString(mBalance));
        data_model_adapter.StoreDayEndBalance((mBalance - 350 + 2000));
    }

    private void SetNewDayAlarm(int h, int m, int d, int mon, int yr)
    {
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
        Kalends.set(Calendar.DAY_OF_MONTH,day_of_month);
        Kalends.set(Calendar.MONTH,month);
        Kalends.set(Calendar.YEAR,year);
        Intent i = new Intent(NewDayCountdown.this, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Kalends.getTimeInMillis(), pi);
        Log.d("Countdown", "Alarm Manager Set Yeah");
    }

    private void SetNewDayAlarm(Calendar Kalends)
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        Intent i = new Intent(NewDayCountdown.this, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Kalends.getTimeInMillis(), pi);
        Log.d("Countdown", "Alarm Manager Set Yeah");
    }

    private Calendar GetCurrentKalends24(int h, int m)
    {
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
}
