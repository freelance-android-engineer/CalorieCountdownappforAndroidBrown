package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.content.Context;

import java.util.Date;
import java.util.Calendar;

public class DinnerBoxService_CIFragement001 extends Service
{
    CCD_GUI_CD_CIF1.MyCallBack callBack;

    private SpecialThreadCIF15 mSpecialThread;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Date mBreakfastNotificationTime;

    private AlarmManager mAlarmManager;
    private PendingIntent alarmIntent;
    private Context goey;

    private Context goey_main_app_bridge;

    private int Hourx =17;
    private int Minx = 0;

    private static final String ACTION_SET_DINNER_NOTIFICATION = "ese.com.caloriecountdownappforandroid.action.DINNER_NOTIFICATION";
    private static int REQUEST_CODE_NEW_DAY_DINNER = 14;





    public DinnerBoxService_CIFragement001()
    {

        mSpecialThread = null;

        goey_main_app_bridge = CCD_GUI_CD_CIF1.getAppContext();
        goey = CCD_GUI_CD_CIF1.getAppContext();

        callBack = CCD_GUI_CD_CIF1.mCallback;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "dinner"; //?
            String channel_id = "dinner"; //?
            String channel_description = "This is a description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);

            channel.setDescription(channel_description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            mNotificationManager = (NotificationManager) goey.getSystemService(NotificationManager.class);
            mNotificationManager.createNotificationChannel(channel);
        }

        //mNotificationManager = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(goey,"dinner") //?
                .setSmallIcon(R.drawable.ic_launcher2) // notification icon
                .setContentTitle("Day End" + todaysTag()) // title for notification
                .setContentText("Day End reached, Balance CFWD")// message for notification //
                .setTicker("New Day Notification")
                .setAutoCancel(false); // clear notification after click

        mBreakfastNotificationTime = new Date();


        mBuilder.setLights(Color.BLUE, 500, 500);
        long [] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);



        mAlarmManager = (AlarmManager)goey.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(goey, DinnerBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_DINNER_NOTIFICATION);

        alarmIntent = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_DINNER, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(alarmIntent);

        mNotificationManager.notify(0, mBuilder.build());



        //mAlarmManager = (AlarmManager)goey.getSystemService(Context.ALARM_SERVICE);

        //Intent i = new Intent(goey, BreakfastBoxService_CIFragement001.class);
        //i.setAction(ACTION_SET_BREAKFAST_NOTIFICATION);

        //alarmIntent = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_BREAKFAST, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //mNotificationManager.notify(0, mBuilder.build());
    }

    public DinnerBoxService_CIFragement001(SpecialThreadCIF15 IN)
    {
        mSpecialThread = IN;
        goey = IN.goey;
        mAlarmManager = (AlarmManager)goey.getSystemService(Context.ALARM_SERVICE);
    }

    public DinnerBoxService_CIFragement001(SpecialThreadCIF15 INSIDE, NotificationManager IN, NotificationCompat.Builder INPUT, Date notificationtime, PendingIntent pendingIntent)
    {
        mNotificationManager = IN;
        mBuilder = INPUT;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 17);
        calendar1.set(Calendar.MINUTE,1);

        mBreakfastNotificationTime = notificationtime;

        mBreakfastNotificationTime.setTime(calendar1.getTimeInMillis());

        //long spacing = 86400000;



        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mBreakfastNotificationTime.getTime());


        Hourx = calendar.get(Calendar.HOUR);
        Minx = calendar.get(Calendar.MINUTE);


        mSpecialThread = INSIDE;

        goey = INSIDE.goey;

        if(goey != null)
        {

            mAlarmManager = (AlarmManager) goey.getSystemService(Context.ALARM_SERVICE);
        }
        else
        {
            android.util.Log.d("Dinner Service", "goey Context is NULL, why?");
        }

        Intent i = new Intent(goey, DinnerBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_DINNER_NOTIFICATION);

        alarmIntent = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_DINNER, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //mNotificationManager.notify(0, mBuilder.build());





    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        //setRepeating(mBreakfastNotificationTime);

        ///mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // In this sample, we'll use the same text for the ticker and the expanded notification
        ///CharSequence text = "Notification Service Started";

        // The PendingIntent to launch our activity if the user selects this notification
        ///PendingIntent contentIntent = PendingIntent.getActivity(this, 8,
        ///        new Intent(this, CCD_GUI_CD_CIF1.class), 0);

        // Set the info for the views that show in the notification panel.


        ///mBuilder = new NotificationCompat.Builder(goey,"Breakfast")
        ///        .setSmallIcon(R.drawable.ic_launcher2) // notification icon
        ///        .setContentTitle("Update" + todaysTag()) // title for notification
        ///        .setContentText("Update balance with Breakfast items")// message for notification
        ///        .setTicker("Breakfast Notification")
        ///        .setAutoCancel(false); // clear notification after click

        ///mBreakfastNotificationTime = new Date();


        ///mBuilder.setLights(Color.BLUE, 500, 500);
        ///long [] pattern = {500,500,500,500,500,500,500,500,500};
        ///mBuilder.setVibrate(pattern);
        ///mBuilder.setDefaults(Notification.DEFAULT_SOUND);


        ///mAlarmManager = (AlarmManager)goey.getSystemService(Context.ALARM_SERVICE);

        //Intent i = new Intent(goey, BreakfastBoxService_CIFragement001.class);
        //i.setAction(ACTION_SET_BREAKFAST_NOTIFICATION);

        //alarmIntent = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_BREAKFAST, i, PendingIntent.FLAG_UPDATE_CURRENT);
        ///mBuilder.setContentIntent(contentIntent);

        ///mNotificationManager.notify(0, mBuilder.build());
        // mNotificationManager.notify(0, mBuilder.build());
        android.util.Log.d("Noftify","We are in Repeating Daybreak Dinner");

        NewDay();

        android.util.Log.d("Noftify","Calling Refresh");

        callBack.refreshMainActivity();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int start_id)
    {
        setRepeating(mBreakfastNotificationTime);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setRepeating(Date INrepeatTime)
    {
        //Set Schedule like repeating alarm here.

        ///mBreakfastNotificationTime = INrepeatTime;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Hourx);
        calendar.set(Calendar.MINUTE,Minx);

        mBreakfastNotificationTime.setTime(calendar.getTimeInMillis());

        long spacing = 86400000;

        ///calendar.setTimeInMillis(mBreakfastNotificationTime.getTime());

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.

        //mNotificationManager.notify(0, mBuilder.build());

        //mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mBreakfastNotificationTime.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
        //mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mBreakfastNotificationTime.getTime(), alarmIntent); //Channels.
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mBreakfastNotificationTime.getTime(), 1000 * 86400, alarmIntent);

        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable r = new Runnable()
        {
            public void run()
            {

                mNotificationManager.notify(24, mBuilder.build());

                CCD_GUI_CD_CIF1 instance = ((CCD_GUI_CD_CIF1)goey_main_app_bridge);
                instance.Start_Day_End();


                mBreakfastNotificationTime = setNextTime(mBreakfastNotificationTime,86430000);
                setRepeating(new Date());
            }
        };
        handler.postDelayed(r, spacing);



    }

    private void Breakfast_Notify(String title, String content, String description)
    {
        ;
    }

    private void Lunch_Notify(String title, String content, String description)
    {
        ;
    }

    private void Dinner_Notify(String title, String content, String description)
    {
        ;
    }


    private String todaysTag()
    {
        Calendar Greg = Calendar.getInstance();

        String year = new RoundingCIF13().IntToString(Greg.get(Calendar.YEAR));
        String month = new RoundingCIF13().IntToString(Greg.get(Calendar.MONTH)+1);
        String day = new RoundingCIF13().IntToString(Greg.get(Calendar.DAY_OF_MONTH));

        month = Americanize(month, Greg.get(Calendar.MONTH));

        month = " " + month + " " + day;
        month = month + ",";
        month = month + year;

        return month;
    }

    private String Americanize(String month, int IN)
    {
        if(month == "0")month = "January";
        if(month == "1")month = "February";
        if(IN ==1){month = "February";}
        if(month == "2")month = "March";
        if(month == "3")month = "April";
        if(month == "4")month = "May";
        if(month == "5")month = "June";
        if(month == "6")month = "July";
        if(month == "7")month = "August";
        if(month == "8")month = "September";
        if(month == "9")month = "October";
        if(month == "10")month = "November";
        if(month == "11")month = "December";

        return month;

    }

    private Date setNextTime(Date IN, long extension)
    {
        long Out = IN.getTime();

        Out = Out + extension;

        Date Outdate = new Date();
        Outdate.setTime(Out);

        return Outdate;
    }

    private void NewDay()
    {
        android.util.Log.d("New Day 3","In New day 1a");
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(goey);
        String currentBalance = data_model_adapter.RetrieveBalance();
        int mBalance = new RoundingCIF13().StringToInt(currentBalance);
        android.util.Log.d("New Day 3","In New day 1b");
        if(data_model_adapter.getSex() == "MALE")
        {
            mBalance = mBalance - 2500;
        }
        android.util.Log.d("New Day 3","In New day 2");
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

        android.util.Log.d("New Day 3","In New day 4");
    }

    /*private void SetNewDayAlarm()
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        //CFF Refinement Box i : *“Make DayEnd 9pm work properly first most important execution. *See Blackberry. *Bring NewDaySetAlarm out into MIF, when one DayEnd is set it Calls MIF for next go, must be and 9pm ask users to round up last remaining boxes for the day give 30 minutes so 8:30pm notification and boom new day end like iPhone calculator steps set in Data like current for new Day end” check it works in Samsung well tight like boxes before (re)Loading ~> www.ese-edet.eu.



        long KalendTime = (System.currentTimeMillis());
        Intent i = new Intent(goey, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");

    }

    public void SetNewDayAlarmBreakfast(NotificationManager mNotificationManager, NotificationCompat.Builder mBuilder, Date notificationtime)
    {



        long KalendTime = (System.currentTimeMillis());
        KalendTime = notificationtime.getTime();

        Intent i = new Intent(goey, BreakfastBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_BREAKFAST_NOTIFICATION);

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_BREAKFAST, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        //alarmManager.set(AlarmManager.RTC, KalendTime, pi);
        BreakfastBoxService_CIFragement001 BeeService = new BreakfastBoxService_CIFragement001(this, mNotificationManager, mBuilder, notificationtime, pi);
        BeeService.setRepeating(notificationtime);
        Log.d("Countdown_Breakfast1", "Alarm Manager Set Yeah verify");

    }

    public void SetNewDayAlarmLunch(NotificationManager mNotificationManager, NotificationCompat.Builder mBuilder, Date notificationtime)
    {
        long KalendTime = (System.currentTimeMillis());
        KalendTime = notificationtime.getTime();

        Intent i = new Intent(goey, LunchBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_LUNCH_NOTIFICATION);

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_LUNCH, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        //alarmManager.set(AlarmManager.RTC, KalendTime, pi);
        LunchBoxService_CIFragement001 BeeService = new LunchBoxService_CIFragement001(this, mNotificationManager, mBuilder, notificationtime, pi);
        BeeService.setRepeating(notificationtime);
        Log.d("Countdown_Lunch1", "Alarm Manager Set Yeah verify");
    }

    public void SetNewDayAlarmDinner(NotificationManager mNotificationManager, NotificationCompat.Builder mBuilder, Date notificationtime)
    {
        long KalendTime = (System.currentTimeMillis());
        KalendTime = notificationtime.getTime();

        Intent i = new Intent(goey, DinnerBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_DINNER_NOTIFICATION);

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_DINNER, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        //alarmManager.set(AlarmManager.RTC, KalendTime, pi);
        DinnerBoxService_CIFragement001 BeeService = new DinnerBoxService_CIFragement001(this, mNotificationManager, mBuilder, notificationtime, pi);
        BeeService.setRepeating(notificationtime);
        Log.d("Countdown_Dinner1", "Alarm Manager Set Yeah verify");

    }

    public void SetNewDayAlarmDayend(NotificationManager mNotificationManager, NotificationCompat.Builder mBuilder, Date notificationtime)
    {
        long KalendTime = (System.currentTimeMillis());
        KalendTime = notificationtime.getTime();

        Intent i = new Intent(goey, FivePMBoxService_CIFragement001.class);
        i.setAction(ACTION_SET_DAYEND_NOTIFICATION);

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_FIVEPM, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        //alarmManager.set(AlarmManager.RTC, KalendTime, pi);
        FivePMBoxService_CIFragement001 BeeService = new FivePMBoxService_CIFragement001(this, mNotificationManager, mBuilder, notificationtime, pi);
        BeeService.setRepeating(notificationtime);
        Log.d("Countdown_DayEnd1", "Alarm Manager Set Yeah verify");

    }*/

    private Date Convert_String_to_Date(String INPUT)
    {

        //Algorithm Engineering ~> ANDROID

        //Assume the String is in this formart 1000 for 10am

        //Step One.
        //Create a Calendar Gregum
        String hour = INPUT.substring(0,1);
        String minute = INPUT.substring(2,3);


        Calendar Greg = Calendar.getInstance();

        Greg.set(Calendar.HOUR, new RoundingCIF13().StringToInt(hour));
        Greg.set(Calendar.MINUTE, new RoundingCIF13().StringToInt(minute));

        //Step Two.
        //Create Today's Date
        Date Todays_Date = Greg.getTime();

        //Step Three.
        //Set Time to Breakfast/INPUT Time
        //Todays_Date.setTime(Greg.getTimeInMillis());

        //Step Four.
        //Add INPUT to Calendar Gregum
        Greg.setTime(Todays_Date);

        //Step Five.
        //Just return INPUT Time
        return Todays_Date;
    }

    private Date Convert_Int_to_Date(int INPUT)
    {

        //Algorithm Engineering ~> ANDROID

        //Assume the String is in this formart 1000 for 10am

        //Step One.
        //Create a Calendar Gregum
        int Backup = INPUT;
        String hour = (new RoundingCIF13().IntToString(INPUT)).substring(0,1);
        String minute = (new RoundingCIF13().IntToString(Backup)).substring(2,3);


        Calendar Greg = Calendar.getInstance();

        Greg.set(Calendar.HOUR, new RoundingCIF13().StringToInt(hour));
        Greg.set(Calendar.MINUTE, new RoundingCIF13().StringToInt(minute));

        //Step Two.
        //Create Today's Date
        Date Todays_Date = Greg.getTime();

        //Step Three.
        //Set Time to Breakfast/INPUT Time
        //Todays_Date.setTime(Greg.getTimeInMillis());

        //Step Four.
        //Add INPUT to Calendar Gregum
        Greg.setTime(Todays_Date);

        //Step Five.
        //Just return INPUT Time
        return Todays_Date;
    }


}
