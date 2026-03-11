package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


/**
 * A placeholder fragment containing a simple view.
 */
public class Log_It_In_CIF15Fragment extends Fragment {

    private static int REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX = 151;
    private static int REQUEST_CODE_LOG_IT_IN_LUNCH_BOX = 152;
    private static int REQUEST_CODE_LOG_IT_IN_DINNER_BOX = 153;
    private static int REQUEST_CODE_LOG_IT_IN_MIDNIGHT = 154;
    private static final String ACTION_CREDIT_BREAKFAST_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_BREAKFAST";
    private static final String ACTION_CREDIT_LUNCH_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_LUNCH";
    private static final String ACTION_CREDIT_FINALMEAL_TRANSACTION = "ese.com.caloriecountdownappforandroid.action.CREDIT_DINNER";

    public static final String ResultBreakfastTime = "Breakfast time";
    public static final String ResultLunchTime = "Lunch time";
    public static final String ResultDinnerTime = "Dinner time";
    public static final String ResultMidnight = "1700";

    public static final long twentyFourHours = (24*60*60*1000);



    private Button mServices;
    private String BreakfastTime = "0800";
    private String LunchTime = "1400";
    private String DinnerTime = "2000";

    public Log_It_In_CIF15Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log__it__in__cif15, container, false);


        mServices = (Button) v.findViewById(R.id.button20);

        mServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ExtractTimes();
                //innerTime it = new innerTime();
                //it.setInnerTime(BreakfastTime);
               // SetTimesBreakfast(16, 43, 3, 1, 2019);
                //SetTimesBreakfast(it.hour, it.minute, it.day, it.month, it.year);
                //SetTimesLunch(13, 53, 3, 8, 2016);
                //SetTimesDinner(14,29,17,3,2016);
                BackToParent();

            }
        });

        return v;

    }


    private void ExtractTimes()
    {

        EditText editText80 = (EditText) getActivity().findViewById(R.id.edit_text80);
        EditText editText81 = (EditText) getActivity().findViewById(R.id.edit_text81);
        EditText editText82 = (EditText) getActivity().findViewById(R.id.edit_text82);

        BreakfastTime = editText80.getText().toString();
        LunchTime = editText81.getText().toString();
        DinnerTime = editText82.getText().toString();

        android.util.Log.d("Extracted Breakfast ", BreakfastTime);
        android.util.Log.d("Extracted Lunch ", LunchTime);
        android.util.Log.d("Extracted Dinner ", DinnerTime);




    }

    private void SetTimesBreakfast(int h,int m,int d,int mon, int yr)
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
        Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
        Kalends.set(Calendar.MONTH,month);
        Kalends.set(Calendar.YEAR, year);
        long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        long intervalmillis = (24*60*60*1000); //24 hours
        Intent i = new Intent(getActivity(), CreditBreakfastBox.class);
        i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getActivity(), REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, (System.currentTimeMillis()), pi);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,Kalends.getTimeInMillis(),twentyFourHours, pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");

    }

    private void SetTimesLunch(int h,int m,int d,int mon, int yr)
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
        Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
        Kalends.set(Calendar.MONTH,month);
        Kalends.set(Calendar.YEAR, year);
        long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        long intervalmillis = (24*60*60*1000); //24 hours
        Intent i = new Intent(getActivity(), CreditLunchBox.class);
        i.setAction(ACTION_CREDIT_BREAKFAST_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getActivity(), REQUEST_CODE_LOG_IT_IN_BREAKFAST_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Kalends.getTimeInMillis(),intervalmillis, pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");
    }

    private void SetTimesDinner(int h,int m,int d,int mon, int yr)
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
        Kalends.set(Calendar.HOUR_OF_DAY, 19);
        Kalends.set(Calendar.MINUTE, 30);
        // Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
        //Kalends.set(Calendar.MONTH,month);
        // Kalends.set(Calendar.YEAR, year);
        long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        long intervalmillis = (24*60*60*1000); //24 hours
        Intent i = new Intent(getActivity(), FinalCreditMealBox.class);
        i.setAction(ACTION_CREDIT_FINALMEAL_TRANSACTION);
        PendingIntent pi = PendingIntent.getService(getActivity(), REQUEST_CODE_LOG_IT_IN_DINNER_BOX, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Kalends.getTimeInMillis(),twentyFourHours, pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");
    }


    private void BackToParent()
    {
        //Algorithm Engineering > ANDROID
        //Put collected time in intent and send to Parent.

        Intent i2 = new Intent();



        android.util.Log.d("DebuggingN", "We are about to shoot intent1");

        i2.putExtra(ResultBreakfastTime, BreakfastTime);
        i2.putExtra(ResultLunchTime, LunchTime);
        i2.putExtra(ResultDinnerTime, DinnerTime);
        i2.putExtra(ResultMidnight, "1700");

        android.util.Log.d("Check Breakfastime", ResultBreakfastTime + " " + BreakfastTime);
        android.util.Log.d("Check LunchTime", ResultLunchTime + " " + LunchTime);
        android.util.Log.d("Check DinnerTime", ResultDinnerTime + " " + DinnerTime);
        android.util.Log.d("Check midnight", ResultMidnight);

        android.util.Log.d("DebuggingN", "We are about to shoot intent2");

        getActivity().setResult(getActivity().RESULT_OK, i2);
        android.util.Log.d("DebuggingN", "We are about to shoot intent3");
        getActivity().finish();
        android.util.Log.d("DebuggingN","We are about to shoot intent4");

    }

    private Date myStringToDate(String editText)
    {
        String hour = editText.substring(0,2);
        String minute = editText.substring(2,4);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, new RoundingCIF13().StringToInt(hour));
        calendar.set(Calendar.MINUTE,new RoundingCIF13().StringToInt(minute));

        Date result = new Date(calendar.getTimeInMillis());

        return result;
    }


    public class innerTime
    {
        public int hour;
        public int minute;
        public int day;
        public int month;
        public int year;

        public void setInnerTime(String bt)
        {
            Calendar Greg = Calendar.getInstance();
            Greg.set(Calendar.YEAR, year);
            Greg.set(Calendar.MONTH, month);
            Greg.set(Calendar.DAY_OF_MONTH, day);

            hour = 12;//Integer.parseInt(bt.substring(0, 2));
            minute = 30;//Integer.parseInt(bt.substring(3, 5));

        }
    }

}

