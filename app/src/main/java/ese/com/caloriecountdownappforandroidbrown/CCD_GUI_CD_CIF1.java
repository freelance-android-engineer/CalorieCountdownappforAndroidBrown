package ese.com.caloriecountdownappforandroidbrown;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.pdf.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CCD_GUI_CD_CIF1 extends AppCompatActivity {

    public static String itemToFind = "No item Picked up";

    public static CCD_GUI_CD_CIF1 instance;

    private static final String ACTION_STORE_BALANCE = "ese.com.caloriecountdownappforandroid.action.STORE_BALANCE";
    private static final String TAG = "Calorie Countdown app";
    private static final String webadress = "https://web2.0calc.com/";
    private static final String CALCULATOR_PACKAGE_NAME = "com.android.calculator2";
    private static final String CALCULATOR_CLASS_NAME = "com.android.calculator2.Calculator";
    private static final String filepath = "~/ClientGuide.pdf";

    private static final int REQUEST_CODE_GET_FOOD_ITEM = 1;
    private static final int REQUEST_CODE_START_DEBIT_ACTIVITY = 5;
    private static final int REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY = 8;
    private static final int REQUEST_CODE_LOG_IT_IN = 15;
    private static final int REQUEST_CODE_NEW_DAY = 7;
    private static final int REQUEST_CODE_MONTHLY_STATEMENT_ACTIVITY = 9;
    private static final int REQUEST_CODE_DIET_PLAN = 16;
    private static final int REQUEST_CODE_START_JOURNAL_ACTIVITY = 17;
    private static final int REQUEST_CODE_GET_FOOD_NOTE_ITEM = 18;
    private static final int REQUEST_CODE_RECALIBRATION = 19;
    private static final int REQUEST_CODE_PDFViewer = 20;

    private Button mCreditButton;
    private Button mDebitButton;

    private SummaryBoxCIF12 mSummation;

    private Date ResetBreakfastTime;
    private Date ResetLunchTime;
    private Date ResetDinnerTime;
    private Date ResetMidnight;

    private String SResetBreakfastTime;
    private String SResetLunchTime;
    private String SResetDinnerTime;
    private String SResetMidnight;

    private String mBalance_text;

    private int mBalance;

    private TextView mBalance_textview;

    private static Context appContext;
    public static MyCallBack mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccd__gui__cd__cif1);

        instance = this;

       mCallback = new MyCallBack()
       {
            @Override
            public void refreshMainActivity()
            {
                CCD_GUI_CD_CIF1.this.recreate();

                //"OR"

                //finish();
                //startActivity(getIntent());
            }
        };

        instance.Set_currentBalance();
        //instance.Start_Cycle(); //Only after "Start_Weight_Loss_Used_For_First_Time!
        appContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher7);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Update your Food Diary", Snackbar.LENGTH_LONG)
                        .setAction("Update", null).show();
            }
        });

        mCreditButton = (Button) findViewById(R.id.button2);
        mDebitButton = (Button) findViewById(R.id.button);
        mCreditButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
                //data_model_adapter.setSex(true);
                StartFoodDiaryAidSheetCIF3();
                //CancelAlarm();
            }
        });

        mDebitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartDebitActivityCIF13();
                //Set_currentBalance();
            }
        });


        //final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        //countdownbalance.setText(RetrieveCountdownBalance());

        //boolean value = getIntent().getBooleanExtra(NewDayCountdown.START_WEIGHT_LOSS,false);
        //if(value)
        //{
            //Start_Weight_LossPlus24();
        //}

        //Start_Weight_Loss();

    }


    //protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ccdgui__cif1);


        //mCreditButton = (Button) findViewById(R.id.button2);
        //mDebitButton = (Button) findViewById(R.id.button);
        //mCreditButton.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View v) {
                //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
                //data_model_adapter.setSex(true);


          //      StartFoodDiaryAidSheetCIF3();
                //CancelAlarm();

        //    }
        //});
        //mDebitButton.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View v) {
            //    StartDebitActivityCIF13();
          //  }
        //});


        //final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        //countdownbalance.setText(RetrieveCountdownBalance());

        //boolean value = getIntent().getBooleanExtra(NewDayCountdown.START_WEIGHT_LOSS,false);
        //if(value)
       // {
            //Start_Weight_LossPlus24();
     //   }




   // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ccd__gui__cd__cif1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.sub_submenuitem1) {

            StartWebCalculatorFragment2();

            return true;
        }

        if (id == R.id.sub_submenuitem91) {

            StartFoodDiaryNotes();

            return true;
        }

        if (id == R.id.sub_submenuitem2) {

            Start_Native_Calculator();

            return true;
        }

        if (id == R.id.action_start_weightloss)

        {
            Start_Weight_Loss_ActivityCIF4();
            return true;
        }

        if (id == R.id.action_stop_weightlossb)

        {
            Start_Recalibration();
            return true;
        }

        if (id == R.id.action_log_it_in_reminder)

        {
            Start_Notification_ActivityCIF5();
            return true;
        }

        if (id == R.id.submenu3)

        {
            Start_Diet_Plan_Activity();
            return true;
        }

        if (id == R.id.submenu6)

        {
            Populate_SQLite_Database();
            return true;
        }

        if (id == R.id.submenu9)

        {
            De_Populate_SQLite_Database();

            return true;
        }

        if (id == R.id.submenu7)

        {
            Clear_SQLite_Database();
            return true;
        }

        if (id == R.id.submenu2a)

        {
            Start_Journal_Activity_CiF115();
            return true;
        }

        if (id == R.id.action_fitness_log_debit) // Physical Activity Debit
        {
            StartDebitActivityCIF13();

        }

        if (id == R.id.action_Client_Guide) // Physical Activity Debit
        {
            Start_Client_Guide();
        }





        return super.onOptionsItemSelected(item);
    }

    private void Start_Client_Guide()
    {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.PDFViewer.class);
        this.startActivityForResult(i, REQUEST_CODE_PDFViewer);

        //ShootClientGuide();

        //File file = new File("ClientGuide001.pdf");
        //Uri path = Uri.fromFile(file);

        //Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        //pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //pdfOpenintent.setDataAndType(path, "application/pdf");

        //try
        //{
          //  startActivity(pdfOpenintent);
        //}
        //catch (ActivityNotFoundException e)
        //{

        //}


    }

    private void ShootClientGuide()
    {

        File file = new File(filepath);
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        }
        catch (ActivityNotFoundException e) {

        }

    }



    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        try
        {
            if (data == null)
            {
                Log.d(TAG, "Sorry Mate, Intent is null Baby!");
                return;
            }
        }

        catch (Exception c)
        {

        }

        if (requestcode == REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY) {
            String vitals;
            int openingbalance;

            if (data != null) {
                openingbalance = data.getIntExtra(Start_Weight_Loss_ActivityCIF14Fragment.OPENING_BALANCE, 1);
                ResetBreakfastTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultBreakfastTime));
                ResetLunchTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultLunchTime));
                ResetDinnerTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultDinnerTime));
                ResetMidnight = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultMidnight));
                vitals = data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.VitalStats);

                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(this);
                display_dialog_cif11.Showing(vitals);
                //CCDGUI_CIF1.CurrentCalendar cc = new CCDGUI_CIF1.CurrentCalendar();
                //Start_Weight_Loss(cc.Hour, cc.Minute, cc.Day_of_the_Month, cc.Month, cc.Year);
                Start_Weight_Loss();
                OpenAccount(openingbalance);
                //android.util.Log.d("Start_Weight_Loss", "these are your values:Hour:" + new RoundingCIF13().IntToString(cc.Hour) + " Min: " + new RoundingCIF13().IntToString(cc.Minute) + " Day of month: " + new RoundingCIF13().IntToString(cc.Day_of_the_Month) + " Month: " + new RoundingCIF13().IntToString(cc.Month) + " Year: " + new RoundingCIF13().IntToString(cc.Year));
                ResetAlarmTimer(ResetBreakfastTime, ResetLunchTime, ResetDinnerTime, ResetMidnight);
            } else {
                ResetBreakfastTime = new Date();
                ResetLunchTime = new Date();
                ResetDinnerTime = new Date();
                ResetMidnight = ProperMidnight();
                vitals = "It's empty mate";
                openingbalance = 0;
            }

        }



        if (requestcode == REQUEST_CODE_LOG_IT_IN) {
            String vitals;
            int openingbalance;

            if (data != null)
            {
                openingbalance = data.getIntExtra(Start_Weight_Loss_ActivityCIF14Fragment.OPENING_BALANCE, 1);

                //ResetBreakfastTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultBreakfastTime));
                //ResetLunchTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultLunchTime));
                //ResetDinnerTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultDinnerTime));
                //ResetMidnight = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultMidnight));

                SResetBreakfastTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultBreakfastTime));
                SResetLunchTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultLunchTime));
                SResetDinnerTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultDinnerTime));
                SResetMidnight = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultMidnight));



                android.util.Log.d("Supreme_Money_Shot RestB", SResetBreakfastTime );
                android.util.Log.d("Supreme_Money_Shot RestL", SResetLunchTime );
                android.util.Log.d("Supreme_Money_Shot RestD", SResetDinnerTime );
                android.util.Log.d("Supreme_Money_Shot RestN", SResetMidnight );

                ResetBreakfastTime = myStringToDate(SResetBreakfastTime);
                ResetLunchTime = myStringToDate(SResetLunchTime);
                ResetDinnerTime = myStringToDate(SResetDinnerTime);
                ResetMidnight = myStringToDate(SResetMidnight);

                ResetAlarmTimer(ResetBreakfastTime, ResetLunchTime, ResetDinnerTime, ResetMidnight);



            }
            else
                {

                ResetBreakfastTime = new Date();
                ResetLunchTime = new Date();
                ResetDinnerTime = new Date();
                ResetMidnight = ProperMidnight();
                vitals = "It's empty mate";
                openingbalance = 0;

                }

                //Start_Day_End();

        }


        if (requestcode == REQUEST_CODE_GET_FOOD_ITEM) {

            try {

                android.util.Log.d("Credit_Value, Pos 1", "We are in Start of Request Food Diary" );


                int CreditResult = data.getIntExtra(Food_Diary_Sheet_CIF3.TOTAL_CREDIT_VALUE, 1);

                android.util.Log.d("Credit_Value, Pos 2", "We are in Start of Request Food Diary" );

                String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
                //How it for Countdown Screen, get App ready ready for use, might have to look in Intent

                android.util.Log.d("Credit_Value, Pos 3", "We are in Start of Request Food Diary" );

                mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
                mSummation.Set_mCurrentBalance(Get_currentBalance());
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);

                android.util.Log.d("Credit_Value, Pos 4", "We are in Start of Request Food Diary" );

                long res_of_balance_store = Record_Food_Journal(mSummation.Get_mFoodItems());

                String display_string = "This are the results of storing Balance to SQLite : "  +  new RoundingCIF13().LongToString(res_of_balance_store);

                display_dialog_cif11.Showing(display_string);

                android.util.Log.d("Credit_Value, Pos 5", "We are in Start of Request Food Diary" );

                Log.d("1st RecordJ Fi name", mSummation.Get_mFoodItems().get(0).Get_food_item_name());

                android.util.Log.d("Credit_Value, Pos 6", "We are in Start of Request Food Diary" );

                display_dialog_cif11.SummaryBoxShowing(mSummation);
                android.util.Log.d("Credit_Value, Pos 7", "We are in Start of Request Food Diary" );
                mSummation.reset();
                android.util.Log.d("Credit_Value, Pos 8", "We are in Start of Request Food Diary" );
                //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
                Countup(CreditResult);
                android.util.Log.d("Credit_Value, Pos 9", "We are in Start of Request Food Diary" );
                Refresh();
                android.util.Log.d("Credit_Value, Pos 10", "We are in Start of Request Food Diary" );


                if(isItDayEnd())
                {
                    New_Day_2();
                }
            }
            catch(NullPointerException e)
            {
                Log.d("Countdown", "Null Pointer Sent Back" + e.toString());
            }
        }


        if (requestcode == REQUEST_CODE_START_DEBIT_ACTIVITY) {


            int DebitResult = data.getIntExtra(Debit_Activity_CiF003_fragment_box.TOTAL_DEBIT_VALUE, 1);
            //String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
            //How it for Countdown Screen, get App ready ready for use, might have to look in Intent
            mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
            mSummation.Set_mCurrentBalance(Get_currentBalance());
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.SummaryBoxShowingDebit(mSummation);

            //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
            Countdown(DebitResult);
            Store_Dayend(instance.Get_currentBalanceInt());
            Refresh();
        }
    }


        private void StartWebCalculatorFragment2 ()
        {

            Uri uri = Uri.parse(webadress);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

            //CancelAlarm();

        }


        private void Start_Native_Calculator ()
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(new android.content.ComponentName(CALCULATOR_PACKAGE_NAME, CALCULATOR_CLASS_NAME));
            try {
                this.startActivity(intent);
            } catch (android.content.ActivityNotFoundException noSuchActivity) {
                // handle exception where calculator intent filter is not registered
            }
        }

        private void Start_Weight_Loss_ActivityCIF4 ()
        {
            Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.Start_Weight_Loss_ActivityCIF14.class);
            this.startActivityForResult(i, REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY);

        }

    private void Start_Recalibration()
    {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.Recalibrate.class);
        this.startActivityForResult(i, REQUEST_CODE_RECALIBRATION);

    }


        public void Start_Weight_Loss ()
        {
            //Alogrithm Engineering ~> Android (Black ~> Builder) : This is Lego Box for where 5pm Magic Occurs :

            //MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
            //dayEndSetAlarm.NewDayEndSetAlarm(new Date(), this);

        }

        public void Start_Day_End()
        {
            //android.util.Log.d("Day END inside Bridge","Day end cfwd Started");

            //mBalance = 76702;
            //mBalance_text = "76,702";

            //mBalance_textview = (TextView)findViewById(R.id.textView);
            //mBalance_textview.setText(mBalance_text);


            //New_Day_1();
        }

        public void Start_Notification_ActivityCIF5()
        {
            //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

            Intent i = new Intent(CCD_GUI_CD_CIF1.this, Log_It_In_CIF15.class);
            this.startActivityForResult(i, REQUEST_CODE_LOG_IT_IN);

        }

    public void Start_Day_End_ActivityCIF5a()
    {
        //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

       //Intent i = new Intent(CCD_GUI_CD_CIF1.this, Day_END_CIF15a.class);
        // this.startActivityForResult(i, REQUEST_CODE_NEW_DAY);

    }

        public void Start_Diet_Plan_Activity()
        {
            //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

            Intent i = new Intent(CCD_GUI_CD_CIF1.this, Diet_Plan_Activity_fragment008Fragment.class);
            this.startActivityForResult(i, REQUEST_CODE_DIET_PLAN);

        }

    public void Countup(int credit) {
        android.util.Log.d("Countdown","Consider Countdown Updated Token") ;
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        String CountdownFigure = countdownbalance.getText().toString();
        CountdownFigure = new RoundingCIF13().IntToString(new RoundingCIF13().StringToInt(CountdownFigure) + credit);
        countdownbalance.setText(CountdownFigure);
        StoreCountdownBalance(CountdownFigure);
        Kitty();

    }

        private void OpenAccount ( int OpeningBalance)
        {
            //android.util.Log.d("Countdown","Consider Countdown Updated Token") ;
            final TextView countdownbalance = (TextView) findViewById(R.id.textView);
            String CountdownFigure = countdownbalance.getText().toString();
            CountdownFigure = new RoundingCIF13().IntToString(OpeningBalance);

            CountdownFigure = Strip_Comma(CountdownFigure);

            countdownbalance.setText(CountdownFigure);
            StoreCountdownBalance(CountdownFigure);

        }

    private boolean ResetAlarm(Date b, Date l, Date d, Date m, Date e, Date mm)
    {

        try
        {
            SpecialThreadCIF15 speetread = new SpecialThreadCIF15(getApplicationContext());

            speetread.Set_RBreakfastTime(b);
            speetread.Set_RLunchTime(l);
            speetread.Set_RDinnerTime(d);
            speetread.Set_RMidnightTime(m);

            ObjectWithAllTheTimesCIF10 objectWithAllTheTimesCIF10 = speetread.ActivateAlarm();
            //ResetAlarmTimer(objectWithAllTheTimesCIF10);

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

    private void ResetAlarmTimer(Date resetBreakfastTime, Date resetLunchTime, Date resetDinnerTime, Date resetMidnight)
    {

        resetBreakfastTime = add24(resetBreakfastTime); //?
        resetLunchTime = add24(resetLunchTime);
        resetDinnerTime = add24(resetDinnerTime);
        resetMidnight = add24(resetMidnight);

        if(ResetAlarm(resetBreakfastTime, resetLunchTime, resetDinnerTime, resetMidnight, resetLunchTime, resetDinnerTime))
        {
            ;
        }

        this.ResetAlarm( plus24hour(resetBreakfastTime),plus24hour(resetLunchTime),plus24hour(resetDinnerTime),plus24hour(resetMidnight),plus24hour(resetLunchTime), plus24hour(resetDinnerTime));

    }

    public Date ProperMidnight()
    {
        int hour = 5;
        int minute = 0;

        Calendar Greg = Calendar.getInstance();
        Greg.set(Calendar.HOUR, hour);
        Greg.set(Calendar.MINUTE, minute);
        Greg.set(Calendar.AM_PM,Calendar.PM);
        return Greg.getTime();
    }

    public long StoreCountdownBalance(String Balance)
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        return data_model_adapter.StoreBalance(Balance);
    }

    public long Store_Dayend(int data)
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        return data_model_adapter.StoreDayEndBalance(data);
    }

    private Date add24(Date time)
    {
        //long OneMinute = (1000 * 60 * 60 * 24);
        //time.setTime(time.getTime() + (OneMinute * 3000));
        return time;

    }

    private Date plus24hour(Date time)
    {
        long OneMinute = (1000 * 60 * 60 * 24);
        time.setTime(time.getTime() + (OneMinute * 3000));
        return time;
    }


    private void StartFoodDiaryAidSheetCIF3() {
        android.util.Log.d("Pre Multi-Search", "number2");
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Food_Diary_Sheet_CIF3.class);
        android.util.Log.d("Pre Multi-Search", "number3");
        this.startActivityForResult(i, REQUEST_CODE_GET_FOOD_ITEM);
        android.util.Log.d("Pre Multi-Search", "number4");
        //CancelAlarm();

    }

    private void StartFoodDiaryNotes()
    {
        android.util.Log.d("Pre Food Diary Notes", "number2");
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, FoodNoteItemDetailHostActivity.class);
        android.util.Log.d("Pre Food Diary Notes", "number3");
        this.startActivityForResult(i, REQUEST_CODE_GET_FOOD_NOTE_ITEM);
        android.util.Log.d("Pre Food Diary Notes", "number4");

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

    @Override
    public void recreate()
    {
        /*android.util.Log.d("Main", "We have been recreated, now delete this");
        //Simply get new Balance and display.

        //Get Actual balance please tech
        mBalance = 76702;
        //yeah
        mBalance_text = "76,702";
//call new day and subtrack please
        mBalance_textview = (TextView)findViewById(R.id.textView);
        mBalance_textview.setText(mBalance_text);*/



        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ccd__gui__cd__cif1);

        instance = this;

        mCallback = new MyCallBack()
        {
            @Override
            public void refreshMainActivity()
            {
                CCD_GUI_CD_CIF1.this.recreate();

                //"OR"

                //finish();
                //startActivity(getIntent());
            }
        };

        instance.Set_currentBalance();
        //instance.Start_Cycle(); //Only after "Start_Weight_Loss_Used_For_First_Time!
        appContext = getApplicationContext();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher7);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Update your Food Diary", Snackbar.LENGTH_LONG)
                        .setAction("Update", null).show();
            }
        });

        mCreditButton = (Button) findViewById(R.id.button2);
        mDebitButton = (Button) findViewById(R.id.button);
        mCreditButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
                //data_model_adapter.setSex(true);
                StartFoodDiaryAidSheetCIF3();
                //CancelAlarm();
            }
        });

        mDebitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartDebitActivityCIF13();
                //Set_currentBalance();
            }
        });


        //final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        //countdownbalance.setText(RetrieveCountdownBalance());

        //boolean value = getIntent().getBooleanExtra(NewDayCountdown.START_WEIGHT_LOSS,false);
        //if(value)
        //{
        //Start_Weight_LossPlus24();
        //}

        //Start_Weight_Loss();*/
    }


    public static Context getAppContext() {
        return appContext;
    }


    public interface MyCallBack
    {
        public void refreshMainActivity();
    }

    private void StartDebitActivityCIF13()
    {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Debit_Activity_CiF003_fragment_box.class);
        this.startActivityForResult(i, REQUEST_CODE_START_DEBIT_ACTIVITY);
    }

    private void Start_Journal_Activity_CiF115()
    {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Journal_Activity_CiF0115_fragment_box.class);
        this.startActivityForResult(i, REQUEST_CODE_START_JOURNAL_ACTIVITY);
    }


    private void NewDay()
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(this);
        String currentBalance = data_model_adapter.RetrieveBalance();
        mBalance = new RoundingCIF13().StringToInt(currentBalance);
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
        data_model_adapter.StoreDayEndBalance((mBalance - 350 + 2570));
    }

    private void New_Day_1()
    {
        NewDay();
    }


    private boolean Populate_SQLite_Database()
    {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        return Gen_Pop.Populate();

    }

    private boolean De_Populate_SQLite_Database()
    {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        return Gen_Pop.De_Populate_Database();
    }

    private void Clear_SQLite_Database()
    {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        Gen_Pop.Delete_Database();
    }


    private void Kitty() {

        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        int dayend = data_model_adapter.RetriveDayEnd();
        int currentbalance = Get_currentBalanceInt();
        int kit = currentbalance - dayend;
        if(kit < 0)
        {
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyMinus(kit));
        }

        if(kit == 0)
        {
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyZero(kit));
        }
        if(kit > 0)
        {
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyPlus(kit));
        }

    }

    private String KittyMinus(int in)
    {
        int kit = Math.abs(in);
        String out = "You have " + new RoundingCIF13().IntToString(kit) + " Calories left in the Kitty for the meals left in the day.";
        return out;
    }

    private String KittyZero(int in)
    {
        String out = "You have no Calories to eat left in your Kitty, Do not eat or exercise for the rest of the day, you are on track for Weight loss. ";
        return out;
    }

    private String KittyPlus(int in)
    {
        int walkminutes = (int) in/7;
        String out = "You now need to Walk for " + new RoundingCIF13().IntToString(walkminutes) + " minutes to be on track for Weight Loss, only dispose of the dialog once you have performed.";
        return out;
    }


    public int Get_currentBalanceInt()
    {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        return new RoundingCIF13().StringToInt(countdownbalance.getText().toString());
    }

    public String Get_currentBalance()
    {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        return new String(countdownbalance.getText().toString());
    }

    private void Set_currentBalance()
    {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        MIF4_Data_Model_Adapter model_adapter = new MIF4_Data_Model_Adapter(this);

        mBalance_text = model_adapter.RetrieveBalance();

        android.util.Log.d("Set Balance = ", mBalance_text);
        model_adapter.StoreTargetWeightLossPounds("249");

        countdownbalance.setText(mBalance_text);
    }

    private void Set_Balance(String input)
    {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);


        mBalance_text = input;

        android.util.Log.d("Set Balance = ", mBalance_text);

        countdownbalance.setText(mBalance_text);
        StoreCountdownBalance(mBalance_text);
        Kitty();
    }

    private void Refresh()
    {
        getWindow().getDecorView().findViewById(R.id.fragment).invalidate();
    }

    private long Record_Food_Journal(ArrayList<Food_Item_CIF4> INPUT)
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(this);
        data_model_adapter.Record_Food_Journal(INPUT);
        return data_model_adapter.StoreBalance(mBalance_text);

    }










/*    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        try {

            if (data == null)
            {
                Log.d(TAG, "Sorry Mate, Intent is null Baby!");
                return;
            }
        }
        catch (Exception c)
        {
            ;
        }

        if (requestcode == REQUEST_CODE_GET_FOOD_ITEM) {

            try {
                int CreditResult = data.getIntExtra(Food_Diary_Sheet_CIF3.TOTAL_CREDIT_VALUE, 1);
                String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
                //How it for Countdown Screen, get App ready ready for use, might have to look in Intent
                mSummation = SummaryBoxCIF12.get(CCDGUI_CIF1.this);
                mSummation.Set_mCurrentBalance(Get_currentBalance());
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCDGUI_CIF1.this);

                Record_Food_Journal(mSummation.Get_mFoodItems());

                Log.d("1st RecordJ Fi name", mSummation.Get_mFoodItems().get(0).Get_food_item_name());

                display_dialog_cif11.SummaryBoxShowing(mSummation);
                mSummation.reset();
                //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
                Countup(CreditResult);
                Refresh();
            }
            catch(NullPointerException e)
            {
                Log.d("Countdown", "Null Pointer Sent Back" + e.toString());
            }
        }

        if (requestcode == REQUEST_CODE_START_DEBIT_ACTIVITY) {


            int DebitResult = data.getIntExtra(Debit_Activity_CIF13Fragment.TOTAL_DEBIT_VALUE, 1);
            //String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
            //How it for Countdown Screen, get App ready ready for use, might have to look in Intent
            mSummation = SummaryBoxCIF12.get(CCDGUI_CIF1.this);
            mSummation.Set_mCurrentBalance(Get_currentBalance());
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCDGUI_CIF1.this);
            display_dialog_cif11.SummaryBoxShowingDebit(mSummation);

            //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
            Countdown(DebitResult);
            Refresh();
        }

        if (requestcode == REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY)
        {
            String vitals;
            int openingbalance;

            if(data != null)
            {
                openingbalance = data.getIntExtra(Start_Weight_Loss_ActivityCIF14Fragment.OPENING_BALANCE, 1);
                ResetBreakfastTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultBreakfastTime));
                ResetLunchTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultLunchTime));
                ResetDinnerTime = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultDinnerTime));
                ResetMidnight = new RoundingCIF13().StringToDate(data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.ResultMidnight));
                vitals = data.getStringExtra(Start_Weight_Loss_ActivityCIF14Fragment.VitalStats);

                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(this);
                display_dialog_cif11.Showing(vitals);
                //CCDGUI_CIF1.CurrentCalendar cc = new CCDGUI_CIF1.CurrentCalendar();
                //Start_Weight_Loss(cc.Hour, cc.Minute, cc.Day_of_the_Month, cc.Month, cc.Year);
                Start_Weight_Loss();
                OpenAccount(openingbalance);
                //android.util.Log.d("Start_Weight_Loss", "these are your values:Hour:" + new RoundingCIF13().IntToString(cc.Hour) + " Min: " + new RoundingCIF13().IntToString(cc.Minute) + " Day of month: " + new RoundingCIF13().IntToString(cc.Day_of_the_Month) + " Month: " + new RoundingCIF13().IntToString(cc.Month) + " Year: " + new RoundingCIF13().IntToString(cc.Year));
                ResetAlarmTimer(ResetBreakfastTime, ResetLunchTime,ResetDinnerTime,ResetMidnight);
            }
            else
            {
                ResetBreakfastTime = new Date();
                ResetLunchTime = new Date();
                ResetDinnerTime = new Date();
                ResetMidnight = ProperMidnight();
                vitals = "It's empty mate";
                openingbalance = 0;
            }

        }

        else
        {
            return;
        }


    }*/

    public void Countdown(int debit) {

        //android.util.Log.d("Countdown","Consider Countdown Updated Token") ;
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        String CountdownFigure = countdownbalance.getText().toString();
        CountdownFigure = new RoundingCIF13().IntToString(new RoundingCIF13().StringToInt(CountdownFigure) - debit);
        countdownbalance.setText((mBalance_text = CountdownFigure));
        StoreCountdownBalance((mBalance_text =CountdownFigure));
        Kitty();
    }



    public String RetrieveCountdownBalance() {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        return data_model_adapter.RetrieveBalance();
    }

    public String RetrieveTargetWeightPounds() {

        //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        //return data_model_adapter.RetrieveTargetWeight();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        return pref.getString("Target_Weight", null);


    }


    public void DisplaySummaryString(String summary) {
        Log.d(TAG, summary);
    }
    public void DisplaySummaryInt(int in) {
        Log.d(TAG, new RoundingCIF13().IntToString(in));
    }

    public void UpdateCountupUI() {
        mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
        //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
        DisplaySummaryString(mSummation.GetSummaryString()); //Toast, alert or dialog or both
        Countup(mSummation.GetCreditValue());
        mSummation.reset();
        Refresh();//GUI

    }

    public void UpdateCountdownUI()
    {
        mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
        //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
        DisplaySummaryString(mSummation.GetSummaryString()); //Toast, alert or dialog
        Countdown(mSummation.GetDebitValue());
        mSummation.reset();
        Refresh();//GUI

    }


    public void Start_Weight_LossPlus24()
    {
        MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
        dayEndSetAlarm.NewDayEndSetAlarm(add24(new Date()), this);

    }

    public void Start_Weight_Loss_CancelAlarm()
    {
        MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
        dayEndSetAlarm.CancelAlarm();
    }


    private void CancelAlarm() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.cancel(pi);
        pi.cancel();
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

        //CFF Refinement Box i : *“Make DayEnd 9pm work properly first most important execution. *See Blackberry. *Bring NewDaySetAlarm out into MIF, when one DayEnd is set it Calls MIF for next go, must be and 9pm ask users to round up last remaining boxes for the day give 30 minutes so 8:30pm notification and boom new day end like iPhone calculator steps set in Data like current for new Day end” check it works in Samsung well tight like boxes before (re)Loading ~> www.ese-edet.eu.
        int hour = h;
        int minute = m;
        int day_of_month = d;
        int month = mon;
        int year = yr;
        java.util.Calendar Kalends = Calendar.getInstance();
        Kalends.setTime(new Date());
        Kalends.set(Calendar.HOUR_OF_DAY, hour);
        Kalends.set(Calendar.MINUTE, minute);
        // Kalends.set(Calendar.DAY_OF_MONTH, day_of_month);
        //Kalends.set(Calendar.MONTH,month);
        // Kalends.set(Calendar.YEAR, year);
        long KalendTime = (System.currentTimeMillis() - Kalends.getTimeInMillis());
        long intervalmillis = (24 * 60 * 60 * 1000); //24 hours

        Intent i = new Intent(CCD_GUI_CD_CIF1.this, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis()+ KalendTime),intervalmillis, pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");

    }

    public void New_Day_2()
    {
        //Calendar event emitted & Admob & 77p & B x G = ¢#∞§€¡@!£5.57 + WWW.ESE-EDET.EU & Numbers 7v7
    }

    public void setResetDayEnd()
    {

    }

    public void setResetBreakfastTime(Date btime)
    {
        ResetBreakfastTime = btime;
    }

    public void setResetLunchTime(Date ltime)
    {
        ResetLunchTime = ltime;
    }

    public void setResetDinnerTime(Date dtime)
    {
        ResetDinnerTime = dtime;
    }

    public void ChangeTextColor(String input)
    {
       Set_Balance(input);
    }

    public void ChangeButtonColor()
    {

    }

    public void ChangeBackgroundImage()
    {

    }

    public void Recalibrate()
    {

    }

    private void ResetAlarmTimer(ObjectWithAllTheTimesCIF10 obj)
    {
        ResetAlarmTimer(obj.getResetBreakfastTime(), obj.getResetLunchTime(), obj.getResetDinnerTime(), obj.getResetDayEnd());
    }

    private String Strip_Comma(String INPUT)
    {
        for(int c = 0; c < INPUT.length(); c++)
        {
            if(INPUT.charAt(c) == ',')
            {
                StringBuffer IN = new StringBuffer(INPUT);
                IN.deleteCharAt(c);
                return IN.toString();
            }
        }

        return INPUT;
    }

    private boolean isItDayEnd()
    {
        return false;
    }

    public void Store_Target_Weight_Pounds(String Input1)
    {
        //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        //data_model_adapter.StoreTargetWeightLossPounds(Input1);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();

        editor.putString("Target_Weight", Input1);
        editor.commit();

    }




    class CurrentCalendar
    {
        private java.util.Calendar Kalends;
        public int Hour;
        public int Minute;
        public int Day_of_the_Month;
        public int Month;
        public int Year;


        public CurrentCalendar()
        {
            Kalends = Calendar.getInstance();
            Kalends.setTimeInMillis(System.currentTimeMillis() + 180000);
            //Kalends.set(Calendar.HOUR, 17);
            //Kalends.set(Calendar.MINUTE, 06);
            Hour = Kalends.HOUR;
            Minute = Kalends.MINUTE;
            Day_of_the_Month = Kalends.DAY_OF_MONTH;
            Month = Kalends.MONTH;
            Year = Kalends.YEAR;
        }





    }










}
