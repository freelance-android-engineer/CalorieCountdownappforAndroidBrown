package ese.com.caloriecountdownappforandroidbrown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import android.content.Context;

/**
 * Created by ESE on 14/04/2016.
 */
public class MIF1NewDayEndSetAlarm
{

    private static final String ACTION_STORE_BALANCE = "ese.com.caloriecountdownappforandroid.action.STORE_BALANCE";
    private static int REQUEST_CODE_NEW_DAY = 7;
    private Context mContext;

    public  Context NewDayEndSetAlarm(Date time, Context mCon)
        {
            //Alogrithm Engineering ~> Android :
            //Here you prime AlarmManager to first shoot newdaycountdown intent at 5pm the same day
            //then it will continue to reset it self.
            //It can be called by anybody not just StartWeight we will pretend to be start weight loss
            //and start using it to continue countdown. let it shoot a warning that countdown day is
            //coming to an end at 4pm please do all final updates.
            //Should also check that if already on do nothign etc.
            //CancelAlarm();

            //CFF Refinement Box i : *“Make DayEnd 9pm work properly first most important execution.
            // *See Blackberry. *Bring NewDaySetAlarm out into MIF, when one DayEnd is set it Calls MIF for next go,
            // must be and 9pm. ask users to round up last remaining boxes for the day give
            // 30 minutes so 8:30pm Dinner notification and boom new day end like iPhone calculator
            // steps set in Data like current for new Day end” check it works in Samsung well tight
            // like boxes before (re)Load... -> PUSH -> WWW.ESE-EDET.EU*.

            mContext = mCon;
            java.util.Calendar Kalends = Calendar.getInstance();
            Kalends.setTime(time);
            Kalends.set(Calendar.HOUR_OF_DAY, 16);
            Kalends.set(Calendar.MINUTE, 8);
            // Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
            //Kalends.set(Calendar.MONTH,month);
            // Kalends.set(Calendar.YEAR, year);
            long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
            long intervalmillis = (24 * 60 * 60 * 1000); //24 hours
            Intent i = new Intent(mContext, NewDayCountdown.class);
            i.setAction(ACTION_STORE_BALANCE);
            PendingIntent pi = PendingIntent.getService(mContext, REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
            Log.d("Countdown", "Alarm Manager Set Yeah verify Lo Lorenzo");

            return mContext;

    }

    public void CancelAlarm()
    {

        Intent i = new Intent(mContext, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(mContext, REQUEST_CODE_NEW_DAY, i, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        alarmManager.cancel(pi);
        pi.cancel();

    }
}
