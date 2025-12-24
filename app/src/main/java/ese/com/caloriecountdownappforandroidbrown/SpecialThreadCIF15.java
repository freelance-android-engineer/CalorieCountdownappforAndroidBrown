package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.lang.Thread;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import java.lang.String;

/**
 * Created by ESE on 24/05/2016.
 */
public class SpecialThreadCIF15 extends Thread {

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: ESE </p>
 *
 * @author not attributable
 * @version 1.0
 */


    private Timer timerAll;
    private TimerTask timertaskBreakfast;
    private TimerTask timertaskLunch;
    private TimerTask timertaskDinner;
    private TimerTask timertaskSupper;
    private TimerTask timertaskExercise;
    private TimerTask timertaskMidnight;

    public Context goey;
    private boolean activityalarmchanged = false;
    private Date RMidnight;
    private Date RDayEndTime;
    private Date RBreakfastTime;
    private Date RLunchTime;
    private Date RDinnerTime;
    private ObjectWithAllTheTimesCIF10 obj;
    private static int mID = 69;

    private static int REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX = 151;
    private static int REQUEST_CODE_LOG_IT_IN_LUNCH_BOX = 152;
    private static int REQUEST_CODE_LOG_IT_IN_DINNER_BOX = 153;
    private static int REQUEST_CODE_NEW_DAY = 7;
    private static int REQUEST_CODE_NEW_DAY_BREAKFAST = 8;
    private static int REQUEST_CODE_NEW_DAY_LUNCH = 9;
    private static int REQUEST_CODE_NEW_DAY_DINNER = 10;

    private static int REQUEST_CODE_NEW_DAY_FIVEPM = 11;

    private static final String ACTION_STORE_BALANCE = "ese.com.caloriecountdownappforandroid.action.STORE_BALANCE";
    private static final String ACTION_CREDIT_BREAKFAST_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_BREAKFAST";
    private static final String ACTION_CREDIT_LUNCH_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_LUNCH";
    private static final String ACTION_CREDIT_FINALMEAL_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_DINNER";
    private static final String ACTION_SET_BREAKFAST_NOTIFICATION = "ese.com.caloriecountdownappforandroid.action.BREAKFAST_NOTIFICATION";
    private static final String ACTION_SET_LUNCH_NOTIFICATION = "ese.com.caloriecountdownappforandroid.action.LUNCH_NOTIFICATION";
    private static final String ACTION_SET_DINNER_NOTIFICATION = "ese.com.caloriecountdownappforandroid.action.DINNER_NOTIFICATION";
    private static final String ACTION_SET_DAYEND_NOTIFICATION = "ese.com.caloriecountdownappforandroid.action.DAYEND_NOTIFICATION";



    public SpecialThreadCIF15( Context g)
    {

        super();
        goey = g;

        int hour = 7;
        int minute = 20;

        Calendar Greg1 = Calendar.getInstance();
        Greg1.setTime(new Date());
        Greg1.set(Calendar.HOUR, hour);
        Greg1.set(Calendar.MINUTE, minute);

        hour = 10;
        minute = 31;


        Calendar Greg2 = Calendar.getInstance();
        Greg2.setTime(new Date());
        Greg2.set(Calendar.HOUR, hour);
        Greg2.set(Calendar.MINUTE, minute);

        hour = 17;
        minute = 00;

        Calendar Greg3 = Calendar.getInstance();
        Greg3.setTime(new Date());
        Greg3.set(Calendar.HOUR, hour);
        Greg3.set(Calendar.MINUTE, minute);

        hour = 17;
        minute = 01;

        Calendar Greg4 = Calendar.getInstance();
        Greg4.setTime(new Date());
        Greg4.set(Calendar.HOUR, hour);
        Greg4.set(Calendar.MINUTE, minute);

        //Greg.set(Calendar.AM_PM, Calendar.PM);


        //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(g);
        //RBreakfastTime = Convert_Int_to_Date(data_model_adapter.Retrieve_Btime(g));
        //RLunchTime = Convert_Int_to_Date(data_model_adapter.Retrieve_Ltime(g));
        //RDinnerTime = Convert_Int_to_Date(data_model_adapter.Retrieve_Dtime(g));
        //RMidnight =  RDayEndTime = Greg.getTime();


        RBreakfastTime = Greg1.getTime();

        RLunchTime = Greg2.getTime();
        RDinnerTime = Greg3.getTime();
        RMidnight =  RDayEndTime = Greg4.getTime();

        //RBreakfastTime = new Date();

        //RLunchTime = new Date();
        //RLunchTime.setTime((RLunchTime.getTime() + (1000 * 60 * 2)));

        //RDinnerTime = new Date();
        //RDinnerTime.setTime((RDinnerTime.getTime() + (1000 * 60 * 4)));


        android.util.Log.d("Money Shot Breakfast", "RBreakfastime " + RBreakfastTime.toString());
        android.util.Log.d("Money Shot Lunch", "RLunchtime " + RLunchTime.toString());
        android.util.Log.d("Money Shot Din Din", "RDinner " + RDinnerTime.toString());

        timerAll = new Timer();
        //replace with Back command?
        timertaskBreakfast = new TimerTask() {

            //@TargetApi(11)
            public void run()
            {
                android.util.Log.d("Breakfast Fix", "I'm I in? OVER HERE");
                NBreakfast(RBreakfastTime);
        };};


        timertaskLunch = new TimerTask() {

            //@TargetApi(11)
            public void run()
            {
                android.util.Log.d("Lunch Fix", "I'm I in? OVER HERE");
                NLunch();
            };};


        timertaskDinner = new TimerTask() {

            //@TargetApi(11)
            public void run()
            {
                android.util.Log.d("Dinner Fix", "I'm I in? OVER HERE");
                NDinner();
            };};


        timertaskMidnight = new TimerTask()
        {

            public void run()
            {
                android.util.Log.d("New Day", "We made it we are NEW DAY Service ready to do our stuff and restart");
                //SetNewDayAlarm();
                //CallResetAlarm(obj);
                //NDayEnd();
            };
        };


    }


    public void Set_RBreakfastTime(Date Rbt)
    {
        RBreakfastTime = Rbt;
    }

    public Date Get_RBreakfastTime()
    {
        return RBreakfastTime;
    }

    public void Set_RLunchTime(Date Rbt)
    {
        RLunchTime = Rbt;
    }

    public Date Get_RLunchTime()
    {
        return RLunchTime;
    }

    public void Set_RDinnerTime(Date Rbt)
    {
        RDinnerTime = Rbt;
    }

    public Date Get_RDinnerTime()
    {
        return RDinnerTime;
    }

    public void Set_RMidnightTime(Date Rbt)
    {
        RMidnight = RDayEndTime = Rbt;
    }

    public Date Get_RMidnightTime()
    {
        return RMidnight;
    }

    public void Set_RDayEndTime(Date Rbt)
    {
        RDayEndTime = RMidnight = Rbt;
    }

    public Date Get_RDayEndTime()
    {
        return RDayEndTime;
    }



  /* public SpecialThreadCIF15( Context g) {

        super();
        goey = g;

        timerAll = new Timer();
        //replace with Back command?

        timertaskBreakfast = new TimerTask() {
           // private Mif12MyAlertChoice ac;
            //private Mif12SetTimer settee;


            @TargetApi(11)
            public void run() {

                NotifyBreakfast();
            };
        };



        timertaskExercise = new TimerTask() {
          //  private Mif12MyAlertChoice ac;
          //  private Mif12SetTimer settee;
           // public void run() { UiApplication.getUiApplication().requestForeground(); UiApplication.getApplication().invokeLater(new Runnable() {
             //   public void run() {
             //       Dialog.alert("Please Launch Calorie Countdown and Click 'Fitness Journal' to Log in All Exercises and Activities you've done today if you've not already logged it in, and anything you're about to do before Bed time.");
            //    }
           // });}
        };



        timertaskMidnight = new TimerTask() {

            public void run()
            {
                goey.NewDayCountdown();

            }
        };



    }*/

    private void NotifyBreakfast()
    {
        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Credit Breakfast Transaction (Food items you had for breakfast by lauching App and clicking Credit Button">
        //Need to find out right Context to give it.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey,"default")
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Update your balance with Breakfast Food items. " + todaysTag())
                    .setContentText("Update Balance, Click this notification the click Credit button and update with Breakfast food items.")
                    .setTicker("Breakfast Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);

            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);

            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);

            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Breakfast", "We are Notifying the User as we Speak");
        }


        //("Credit Breakfast Transaction: Hit Ok and the Credit button in App and record all Food Items in your Breakfast Box");

        //Positive Button should Say Launch App.


        //Bring Noti.Builder Functionality here.
        //if(!(UiApplication.getUiApplication().isForeground()))
        // {UiApplication.getUiApplication().requestForeground();}
        // UiApplication.getApplication().invokeLater(new Runnable(){public void run(){
        // Dialog.alert("Please Launch Calorie Countdown and use the 'Food Diary' feature to Log In all you've had between yesterday's Supper and today's Breakfast including Snacks");}});}
    };



    private void NBreakfast(Date btime)
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        //java.util.Calendar Kalends = Calendar.getInstance();
        //Kalends.setTime(new Date());
        //long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        //long intervalmillis = (24*60*60*1000); //24 hours
        //Intent i = new Intent(goey, CreditBreakfastBox.class);
        //i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        //PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) goey.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
        //Log.d("Countdown", "Alarm Manager Set Yeah verify");

        android.util.Log.d("Final Check", new RoundingCIF13().DateToStringeseformat(RBreakfastTime));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            Breakfast_Notify("Breakfast", "Update app with Brunch Food items", "A brief description", RBreakfastTime);

        }
    }


    private void NLunch()
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        //java.util.Calendar Kalends = Calendar.getInstance();
        //Kalends.setTime(new Date());
        //long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        //long intervalmillis = (24*60*60*1000); //24 hours
        //Intent i = new Intent(goey, CreditBreakfastBox.class);
        //i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        //PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) goey.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
        //Log.d("Countdown", "Alarm Manager Set Yeah verify");

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Update your balance with Lunch Food items. " + todaysTag())
                    .setContentText("Update Balance, Click this notification the click Credit button and update with Lunch food items.")
                    .setTicker("Lunch Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Lunch", "We are Notifying the User as we Speak"); */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            Lunch_Notify("Lunch", "Update app with Lunch Food items", "A brief description Lunch", RLunchTime);

        }

    }


    private void NDinner()
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        //java.util.Calendar Kalends = Calendar.getInstance();
        //Kalends.setTime(new Date());
        //long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        //long intervalmillis = (24*60*60*1000); //24 hours
        //Intent i = new Intent(goey, CreditBreakfastBox.class);
        //i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        //PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) goey.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
        //Log.d("Countdown", "Alarm Manager Set Yeah verify");

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Update your balance with Dinner Food items. " + todaysTag())
                    .setContentText("Update Balance, Click this notification the click Credit button and update with Dinner food items (final meal).")
                    .setTicker("Dinner Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("NDinner", "We are Notifying the User as we Speak");
        }*/

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            Dinner_Notify("Dayend", "Processing Day End and Carried FWD", "A brief description New Day", RDinnerTime);

        }
    }


    private void NDayEnd()
    {
        //Alogrithm Engineering ~> Android :
        //Here you prime AlarmManager to first shoot newdaycountdown intent at 9pm the same day
        //then it will continue to reset it self.
        //It can be called by anybody not just StartWeight we will pretend to be start weight loss
        //and start using it to continue countdown. let is shoot a warning that countdown day is
        //coming to an end please do all final updates.
        //Should also check that if already on do nothign etc.
        //CancelAlarm();

        //java.util.Calendar Kalends = Calendar.getInstance();
        //Kalends.setTime(new Date());
        //long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        //long intervalmillis = (24*60*60*1000); //24 hours
        //Intent i = new Intent(goey, CreditBreakfastBox.class);
        //i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        //PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) goey.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
        //Log.d("Countdown", "Alarm Manager Set Yeah verify");

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Update your balance with Dinner Food items. " + todaysTag())
                    .setContentText("Update Balance, Click this notification the click Credit button and update with Dinner food items (final meal).")
                    .setTicker("Dinner Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("NDinner", "We are Notifying the User as we Speak");
        }*/

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            FivePM_Notify("Day End", "Carrying FWD", "New Day", RDayEndTime);

        }
    }


    private void NotifyLunch()
    {
        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Credit Breakfast Transaction (Food items you had for breakfast by lauching App and clicking Credit Button">
        //Need to find out right Context to give it.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Credit Lunch " + todaysTag())
                    .setContentText("Time to Update your Balance, hit this Notifcation and the Click Credit")
                    //.setContentText("Open Calorie Countdown App, Hit Credit Button to Log in all Food Items you had in your Breakfast Box.")
                    .setTicker("Breakfast Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Breakfast/L", "We are Notifying the User as we Speak");
        }


        //("Credit Breakfast Transaction: Hit Ok and the Credit button in App and record all Food Items in your Breakfast Box");

        //Positive Button should Say Launch App.


        //Bring Noti.Builder Functionality here.
        //if(!(UiApplication.getUiApplication().isForeground()))
        // {UiApplication.getUiApplication().requestForeground();}
        // UiApplication.getApplication().invokeLater(new Runnable(){public void run(){
        // Dialog.alert("Please Launch Calorie Countdown and use the 'Food Diary' feature to Log In all you've had between yesterday's Supper and today's Breakfast including Snacks");}});}
    };

    private void NotifyDinner()
    {
        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Credit Breakfast Transaction (Food items you had for breakfast by lauching App and clicking Credit Button">
        //Need to find out right Context to give it.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Day End " + todaysTag())
                    .setContentText("Preforming Day End and CFWD.")
                    .setTicker("Day End Notification");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Breakfast/D ndinner", "We are Notifying the User as we Speak");
        }


        //("Credit Breakfast Transaction: Hit Ok and the Credit button in App and record all Food Items in your Breakfast Box");

        //Positive Button should Say Launch App.


        //Bring Noti.Builder Functionality here.
        //if(!(UiApplication.getUiApplication().isForeground()))
        // {UiApplication.getUiApplication().requestForeground();}
        // UiApplication.getApplication().invokeLater(new Runnable(){public void run(){
        // Dialog.alert("Please Launch Calorie Countdown and use the 'Food Diary' feature to Log In all you've had between yesterday's Supper and today's Breakfast including Snacks");}});}
    };


    private void NotifyFivePM()
    {
        //Launch intent i see or reconn to previous diagram box Technology. to Launch CIF1 with message & <"Day End Transaction (round up Food items you had and activity done by lauching App and clicking Credit the Debit Button">
        //Need to find out right Context to give it.

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey)
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Day End " + todaysTag())
                    .setContentText("Peforming Day End Action.")
                    .setTicker("Day End");
            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Day End", "We are Notifying the User as we Speak");
        }


        //("Credit Breakfast Transaction: Hit Ok and the Credit button in App and record all Food Items in your Breakfast Box");

        //Positive Button should Say Launch App.


        //Bring Noti.Builder Functionality here.
        //if(!(UiApplication.getUiApplication().isForeground()))
        // {UiApplication.getUiApplication().requestForeground();}
        // UiApplication.getApplication().invokeLater(new Runnable(){public void run(){
        // Dialog.alert("Please Launch Calorie Countdown and use the 'Food Diary' feature to Log In all you've had between yesterday's Supper and today's Breakfast including Snacks");}});}
    };

    public void Submenuitem_ChangeActivityAlarmTime(Date NewActivityTime) //Tested function that will be one touched for QVM of this CFF
    {
        //Algortihm Engineering : CFF : Sub : Log It In > Change Activity Alarm Time
        //1. User clicks option for one touch
        //2. Screen with new time form bit like log it in appers
        //2b) Copy Log It in Box but remove all other boxes just leaving one and and text change Activity Log in reminder alarm.
        //3. work id done by next order function/cif/cif method changeActivity() time attached to right spot to change activity time
        //4. Test this see how it goes if it rings with new time
        //5.works and rings in exactly the same stream as log it in, activity reminder time is changes
        //6. testing stages
        //

        //goey.setResetExerciseTime(NewActivityTime);
       // this.NewActivityTimeActivation(NewActivityTime);

    }

    public void Submenuitem_Reminderoff()
    {
        //Algorithm Engineering : CFF : Sub : Log It In > Reminder Off

        //See Menuitem_Reminder off for complete Algorithm Engineering

        //this.ActivateOnlyMidnight();

        //display.setCurrent(previousscreen);


    }

    public void Submenuitem_Recalibrate(int currentw)
    {

        // Show Times just equal show gathered data

        //goey.Recalibrate(currentw);


    }


    private void NewActivityTimeActivation(Date NewAct)
    {
        // Algorithm Eng : Take in new activity time and close old time activation and start this new one especially if same thread issuse,
        //see activiate alarm

        //activityalarmchanged = true;
        //timerAll.schedule(timertaskExercise, NewAct); //end of the day;
    }


    ///////////////////////////////Above is for Log It In Menuitem//////////////////////////////////////////////


    public void run()
    {

        // timer.schedule(timertask, 1000,5000);
    }



    public void StopTimer()
    {
        if(timerAll != null)
        {
            timerAll.cancel();
        }

    }

    public void StartTimer(Date date, long gap)
    {
        if(timerAll == null)
        {
            timerAll = new Timer();
            timerAll.schedule(timertaskBreakfast,date, gap);
        }


    }

    public ObjectWithAllTheTimesCIF10 ActivateAlarm()
    {
        try {

            //RBreakfastTime = GMTCheck(RBreakfastTime);
           // RLunchTime = GMTCheck(RLunchTime);
            //RDinnerTime = GMTCheck(RDinnerTime);

            int hour = 17;
            int minute = 00;

            Calendar Greg = Calendar.getInstance();
            Greg.set(Calendar.HOUR, hour);
            Greg.set(Calendar.MINUTE, minute);
            //Greg.set(Calendar.AM_PM,Calendar.PM);
            RMidnight = RDayEndTime = Greg.getTime();
           // RMidnight = GMTCheck(RMidnight);

            android.util.Log.d("SpecialThread", "ABOUT TO SCHEDULE BREAKFAST TIME");

            android.util.Log.d("SpecialThreadBr", new RoundingCIF13().DateToStringeseformat(RBreakfastTime));
            android.util.Log.d("SpecialThreadLun", new RoundingCIF13().DateToStringeseformat(RLunchTime));
            android.util.Log.d("SpecialThreadDin", new RoundingCIF13().DateToStringeseformat(RDinnerTime));
            android.util.Log.d("SpecialThreadMid", new RoundingCIF13().DateToStringeseformat(RMidnight));
            android.util.Log.d("SpecialThreadDEnd", new RoundingCIF13().DateToStringeseformat(RDayEndTime));
            if(timerAll == null){timerAll = new Timer();}
            android.util.Log.d("timerall nulL","passed timerall null");
            timerAll.schedule(timertaskBreakfast, RBreakfastTime);
            //Critical software below, we tell software when next alarm times are and are stored, in this case you have to set something in the past so it doesn't ring? at right some point whatnext() sets them 24hours in future
            android.util.Log.d("timerall Breakfst","passed timerall breakfast");
            timerAll.schedule(timertaskLunch, RLunchTime);//RLunchTime);
            android.util.Log.d("timerall Rlunch","passed timerall RLunch");
            timerAll.schedule(timertaskDinner, RDinnerTime);//RDinnerTime);
            android.util.Log.d("timerall RDinner","passed timerall RDinner");
            timerAll.schedule(timertaskMidnight, RDayEndTime); //activates and calls function store all values please for next day, last stop.
            android.util.Log.d("timerall Rdayendtime","passed timerall RDayEnd time");
            //RBreakfastTime = PostGMT(RBreakfastTime);
            //RLunchTime = PostGMT(RLunchTime);
            //RDinnerTime = PostGMT(RDinnerTime);

            ObjectWithAllTheTimesCIF10 goey1 = new ObjectWithAllTheTimesCIF10();
            goey1.setResetBreakfastTime(next24Hours(RBreakfastTime));
            goey1.setResetLunchTime(next24Hours(RLunchTime));
            goey1.setResetDinnerTime(next24Hours(RDinnerTime));
            //goey1.setResetDayEnd(RDayEndTime);
            goey1.setResetDayEnd(next24Hours(RDayEndTime));
            obj = goey1;
            //goey.StoreAllData();

            //Shoot intent packaing all of these
            //UiApplication.getUiApplication().popScreen();
            return goey1;


        }
        catch(NumberFormatException de)//ConnectionNotFoundException d)
        {
            //Dear User you will have to set alarms manually now not on the activate wizard and when you finish and go to next Software will go to previous phone screen and keep running
            //Or Simply keep calorie countdown running by returning to pre-calorie countdown launch screen attained at startup() using Display.getCurrent and stored in CiF1 hence get it from CiF1
            //Remember in Software Engineering simplest solution always best, as we are programming in Exception Handling here, chose the tell user option using Display message function

            String message = "Dear Client, your Mobile does not support auto alarms, alarm will not work when Software is off, please set alarms manually each time you open Calorie Countdown or use your phone Alarm reminder";

            this.DisplayMessage(message);
        }
        catch(IllegalArgumentException def)
        {
            this.DisplayMessage("Mate it's illegal argument");
        }

        catch(IllegalStateException fed)
        {
            this.DisplayMessage("Mate it's illegal state");
        }
        catch(SecurityException f)
        {
            //explain and same action as above. Or you could just tell the user it's not happening. (ask user to relaunch, reset or handle self)
            String message = "Dear Client, your Mobile does not support auto alarms, alarm will not work when Software is off, please set alarms manually each time you open Calorie Countdown or use your phone Alarm reminder";

            this.DisplayMessage(message);

        }
        catch(Exception e)
        {

            this.DisplayMessage("Mate $ final exception caught"); if(timerAll == null){this.DisplayMessage("and it's null baby");}
            if(RBreakfastTime == null){this.DisplayMessage("this time it's timertask breakfast mate");}
            this.DisplayMessage(e.getMessage());

        }
         finally
         {
            //StopTimer();
             return new ObjectWithAllTheTimesCIF10();
        } //GI on wwww.ese and raise 6 mill like wiki on www.
    }





    /*public void ActivateOnlyMidnight()
    {
        try {
            //Stop Timer
            //then activate only midnight
            //think of push issues

            if(timerAll != null)timerAll.cancel();

            goey.setReminderStateFalse();

            int hour = 21;
            int minute = 00;

            Calendar Greg = Calendar.getInstance();
            Greg.set(Calendar.HOUR_OF_DAY, hour);
            Greg.set(Calendar.MINUTE, minute);

            Date Midnight = Greg.getTime();

            System.out.println("ABOUT TO Schedute MIDNIGHT");

            timerAll.schedule(timertaskMidnight, Midnight); //activates and calls function store all values please for next day, last stop.



        } catch (NumberFormatException de) { //ConnectionNotFoundException d)
            //Dear User you will have to set alarms manually now not on the activate wizard and when you finish and go to next Software will go to previous phone screen and keep running
            //Or Simply keep calorie countdown running by returning to pre-calorie countdown launch screen attained at startup() using Display.getCurrent and stored in CiF1 hence get it from CiF1
            //Remember in Software Engineering simplest solution always best, as we are programming in Exception Handling here, chose the tell user option using Display message function

            String message = "Dear Client, your Mobile does not support auto alarms, alarm will not work when Software is off, please set alarms manually each time you open Calorie Countdown or use your phone Alarm reminder";

            this.DisplayMessage(message);
        } catch (SecurityException f) {
            //explain and same action as above. Or you could just tell the user it's not happening. (ask user to relaunch, reset or handle self)
            String message = "Dear Client, your Mobile does not support auto alarms, alarm will not work when Software is off, please set alarms manually each time you open Calorie Countdown or use your phone Alarm reminder";

            this.DisplayMessage(message);

        } catch (Exception e) {

            this.DisplayMessage(e.getMessage());

        }
        // finally
        // {
        //    StopTimer();
        //} //GI on wwww.ese and raise 6 mill like wiki on www.
    }*/

    public HealthProfileCiF3 BMIForm(HealthProfileCiF3 abs)
    {
        //return goey.BMIForm(abs);
        return abs;
    }




    private Date GMTCheck(Date check)
    {
        if(AreWeInGMT())
        {
            return AdjustToGMT(check);
        }
        else
        {
            return check;
        }

    }

    private boolean AreWeInGMT()
    {
        //ESE -S.C.I. LTD : Black Office : Alogrithm Engineering GMTUKCheck

        //Step 1. Get Current Date
        Date currentdate = new Date();

        //Step 2. Create temp attribute startDST get rules from value from Green or Orange
        // Green and/or Orange <interject>: Daylight Saving Time in the E.U. start after 01:59:99 on the last Sunday in March and Ends on 01:59:99 on the last Sunday in October

        //Step 2b. (building startDST and endDST attributes)Get current year (remember Black Office rules if more than 3 lines or step split into new algrithm function)

        Date startDST;
        Date endDST;

        //Step 2c. Get month of March
        //Step 2d. Get month of October
        int hour = 01;
        int minute = 59;
        int dstmonthstart = Calendar.MARCH;
        int dstmonthend = Calendar.OCTOBER;

        Calendar Greg = Calendar.getInstance();

        Greg.set(Calendar.HOUR, hour);
        Greg.set(Calendar.MINUTE, minute);
        Greg.set(Calendar.MONTH, dstmonthstart);
        Greg.set(Calendar.AM_PM, Calendar.AM);





        //Step 2e. For the month of march start from day 31, return this if it's a sunday or next sunday in for loop search (Black code algorthi cube smart more efficient, less lines memeory fast)

        int date = 1;
        for(int c = 31; c > 0;c--)
        {
            Greg.set(Calendar.DATE ,c);
            if(Greg.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            {
                //Step 2d. use this day to form and return startDST Date attribute
                date = c;
                break;
            }
        }

        Greg.set(Calendar.DATE, date);
        startDST = Greg.getTime();

        //Step 2e. Repeat for October to create endDST variable

        hour = 1;
        minute = 59;


        Greg.set(Calendar.HOUR, hour);
        Greg.set(Calendar.MINUTE, minute);
        Greg.set(Calendar.MONTH,dstmonthend);
        Greg.set(Calendar.AM_PM, Calendar.AM);

        for(int c = 31; c > 0;c--)
        {
            Greg.set(Calendar.DATE, c);
            if(Greg.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            {
                //Step 2d. use this day to form and return startDST Date attribute
                date = c;
                break;
            }
        }

        Greg.set(Calendar.DATE,date);
        endDST = Greg.getTime();
        //Step 3. If currnet date is greater startDST (inc 2am in long)and less than to endDST return true
        //Step 4. else return falsue

        System.out.println("This is Start DST");
        System.out.println(startDST.toString());
        System.out.println("This is en DST");
        System.out.println(endDST.toString());

        if(currentdate.getTime() > startDST.getTime() && currentdate.getTime() <= endDST.getTime())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private Date AdjustToGMT(Date adj)
    {
        //ESE -S.C.I. LTD : Black Office : Alogrithm Engineering GMTUKCheck

        //Step 1. In DST Adjust by 1 hour by taking 1 hour long value off input to bring time to GMT equivalent

        long onehour = 60 * 60 * 1000;
        long time = adj.getTime();
        time = time - onehour;
        adj.setTime(time);

        //Step 2. Return adjusted Date
        return adj;
    }

    private Date PostGMT(Date adj)
    {
        //ESE -S.C.I. LTD : Black Office : Alogrithm Engineering GMTUKCheck

        //Step 1. In DST Adjust by 1 hour by taking 1 hour long value off input to bring time to GMT equivalent

        long onehour = 60 * 60 * 1000;
        long time = adj.getTime();
        time = time + onehour;
        adj.setTime(time);

        //Step 2. Return adjusted Date
        return adj;
    }

    private void DisplayMessage(String a) //Generic Display Message
    {

    }


    private String todaysTag()
    {
        Calendar Greg = Calendar.getInstance();

        String year = new RoundingCIF13().IntToString(Greg.get(Calendar.YEAR));
        String month = new RoundingCIF13().IntToString((Greg.get(Calendar.MONTH) + 1));
        String day = new RoundingCIF13().IntToString(Greg.get((Calendar.DAY_OF_MONTH) + 0));

        month = Americanize(month);


        int moon = new RoundingCIF13().StringToInt(month);
        moon++;
        month = new RoundingCIF13().IntToString(moon);


        month = month + " " + day;
        month = month + ",";
        month = month + year;

        return month;
    }

    private String Americanize(String month)
    {
        if(month == "0")month = "January";
        if(month == "1")month = "February";
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


    private java.util.Date next24Hours(java.util.Date sucker)
    {
        long milliseconds = sucker.getTime();
        milliseconds = milliseconds + (86400000);
        sucker = new Date(milliseconds);
        return sucker;
    }

    private void thisisatestdummy()
    {
        android.util.Log.d("Debugging", "This is a Test Dummy");
    }

    private void CallResetAlarm(ObjectWithAllTheTimesCIF10 objectWithAllTheTimesCIF10)
    {
        //ResetAlarmTimer(objectWithAllTheTimesCIF10);

    }

    private void ResetAlarmTimer(ObjectWithAllTheTimesCIF10 obj)
    {
        ResetAlarmTimer(obj.getResetBreakfastTime(), obj.getResetLunchTime(), obj.getResetDinnerTime(), obj.getResetDayEnd());
    }

    private void ResetAlarmTimer(Date resetBreakfastTime, Date resetLunchTime, Date resetDinnerTime, Date resetMidnight)
    {

        resetBreakfastTime = add24(resetBreakfastTime);
        resetLunchTime = add24(resetLunchTime);
        resetDinnerTime = add24(resetDinnerTime);
        resetMidnight = add24(resetMidnight);

        if(ResetAlarm(resetBreakfastTime, resetLunchTime, resetDinnerTime, resetMidnight, resetLunchTime, resetDinnerTime))
        {
            ;
        }
    }

    private boolean ResetAlarm(Date b, Date l, Date d, Date m, Date e, Date mm)
    {

        try
        {
            SpecialThreadCIF15 speetread = new SpecialThreadCIF15(goey);

            speetread.Set_RBreakfastTime(b);
            speetread.Set_RLunchTime(l);
            speetread.Set_RDinnerTime(d);
            speetread.Set_RMidnightTime(m);

            ObjectWithAllTheTimesCIF10 objectWithAllTheTimesCIF10 = speetread.ActivateAlarm();


            speetread.start();
            return true;
        }

        catch(Exception c)
        {
            //Make exception more specifit
            //Catch the right name of exception that is thrown if thread already running e.g.
            //if already set in Start Weight Loss Menuitem. and tell user by displaying on screen
            //using appropiate display functions that alarm already set and running
            return false;
        }

    }

    private Date add24(Date time)
    {
        long OneMinute = (1000 * 60 * 60 * 24);
        time.setTime(time.getTime() + (OneMinute * 3000));
        return time;

    }

    private void NewDay()
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(goey);
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
    }

    private void SetNewDayAlarm()
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
        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_BREAKFAST, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_LUNCH, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_DINNER, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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

        PendingIntent pi = PendingIntent.getService(goey, REQUEST_CODE_NEW_DAY_FIVEPM, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) goey.getSystemService(goey.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        //alarmManager.set(AlarmManager.RTC, KalendTime, pi);
        FivePMBoxService_CIFragement001 BeeService = new FivePMBoxService_CIFragement001(this, mNotificationManager, mBuilder, notificationtime, pi);
        BeeService.setRepeating(notificationtime);
        Log.d("Countdown_DayEnd1", "Alarm Manager Set Yeah verify");

    }

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


    private void Breakfast_Notify(String title, String content, String description, Date breakfasttime)
    {


        createNotificationChannel_Fragment(title, content, description);

        /*
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setSound(alarmSound) // set alarm sound for notification
                .setAutoCancel(true); // clear notification after click

        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
         */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey,title)
                .setSmallIcon(R.drawable.ic_launcher2) // notification icon
                .setContentTitle("Update " + todaysTag()) // title for notification
                .setContentText(content)// message for notification
                .setTicker("Breakfast Notification")
                .setAutoCancel(false); // clear notification after click

        Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(goey, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setLights(Color.BLUE, 500, 500);
        long [] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
        SetNewDayAlarmBreakfast(mNotificationManager, mBuilder, breakfasttime);

        //BreakfastBoxService_CIFragement001 BService = new BreakfastBoxService_CIFragement001(this);
        //BService.setRepeating();

        android.util.Log.d("Breakfast2", "We are Notifying the User as we Speak");

        /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey, "default")
                    .setSmallIcon(R.drawable.ic_launcher2)
                    .setContentTitle("Update Balance now " + todaysTag())
                    .setContentText("Update Balance, Click this notification the click Credit button and update with Breakfast food items.")
                    .setTicker("Breakfast Notification");

            Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(goey);
            stackBuilder.addParentStack(CCD_GUI_CD_CIF1.class);
            stackBuilder.addNextIntent(resultIntent);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent.getService(getApplicationContext(), REQUEST_CODE_BREAKFAST, i, 0);

            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setLights(Color.BLUE, 500, 500);
            long [] pattern = {500,500,500,500,500,500,500,500,500};
            mBuilder.setVibrate(pattern);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManager mNotify = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
            mNotify.notify(mID, mBuilder.build());
            android.util.Log.d("Breakfast", "We are Notifying the User as we Speak");*/

    }

    private void Lunch_Notify(String title, String content, String description, Date lunchtime)
    {

        createNotificationChannel_Fragment(title, content, description);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey,title)
                .setSmallIcon(R.drawable.ic_launcher2) // notification icon
                .setContentTitle("Update " + todaysTag()) // title for notification
                .setContentText(content)// message for notification
                .setTicker("Lunch Notification")
                .setAutoCancel(false); // clear notification after click

        Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(goey, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setLights(Color.BLUE, 500, 500);
        long [] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
        SetNewDayAlarmLunch(mNotificationManager, mBuilder, lunchtime);

        android.util.Log.d("Lunch2", "We are Notifying the User as we Speak");


    }

    private void Dinner_Notify(String title, String content, String description, Date dinnertime)
    {
        createNotificationChannel_Fragment(title, content, description);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey,title)
                .setSmallIcon(R.drawable.ic_launcher2) // notification icon
                .setContentTitle("Update " + todaysTag()) // title for notification
                .setContentText(content)// message for notification
                .setTicker("Day_End Notification")
                .setAutoCancel(false); // clear notification after click

        Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(goey, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setLights(Color.BLUE, 500, 500);
        long [] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
        SetNewDayAlarmDinner(mNotificationManager, mBuilder, dinnertime);

        android.util.Log.d("Day end 2", "We are Notifying the User as we Speak");

    }

    private void FivePM_Notify(String title, String content, String description, Date dayendtime)
    {
        createNotificationChannel_Fragment(title, content, description);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(goey,title)
                .setSmallIcon(R.drawable.ic_launcher2) // notification icon
                .setContentTitle("Update " + todaysTag()) // title for notification
                .setContentText(content)// message for notification
                .setTicker("Day End Notification")
                .setAutoCancel(false); // clear notification after click

        Intent resultIntent = new Intent(goey, CCD_GUI_CD_CIF1.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(goey, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setLights(Color.BLUE, 500, 500);
        long [] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) goey.getSystemService(goey.NOTIFICATION_SERVICE);
        SetNewDayAlarmDayend(mNotificationManager, mBuilder, dayendtime);

        android.util.Log.d("Day End", "We are Notifying the User as we Speak");

    }

    private void createNotificationChannel_Fragment(String channel_id, String channel_name, String channel_description)
    {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = channel_name;
            String id = channel_id;
            String description = channel_description;

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);

            channel.setDescription(channel_description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            NotificationManager notificationManager = (NotificationManager) goey.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        /*NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }*/


    }


}


/**************************ESE BLACKBOARD DOCUMENTATION********************************
 * INPUT
 * Number of Input= 0
 *
 *
 *
 *
 * OUTPUT
 * Number of Output = 0
 *
 *
 *
 * DEFINITIONS
 * No terms to define
 *
 *
 *
 * ALGORITHM ENGINEERING
 * 1.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * EXAMPLES
 * none
 *
 *
 * SUPPORTING CIF(s)/Mif(s) TBL
 * 1
 * Contact ese Office(Black) at ese_blackboard@ese-edet.com for queries.
 *
 * TESTING AND PERFORMANCE LOGS
 *
 * Generate as many test case as you can, one extra point for every correct
 * test case.
 *
 * In each test case use different examples of inputs to show that the Method's Output
 * is right everytime.
 *
 * Use a wide range and varying inputs to cover as many different scenarios as possible.
 * Aim to write efficient code so that your implementation runs as fast and correctly as possible.
 *
 * Submit your implementation work back to ese_blackboard@ese-edet.com and wait for you
 * CIF Implementation to be acknowledged, approved and your Points added to you account.
 *
 *
 * *********************************************************************************/
