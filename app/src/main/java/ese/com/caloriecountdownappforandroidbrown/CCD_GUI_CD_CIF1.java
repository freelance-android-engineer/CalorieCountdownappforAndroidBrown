package ese.com.caloriecountdownappforandroidbrown;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
//import com.erkutaras.showcaseview.ShowcaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ese.com.caloriecountdownappforandroidbrown.ui.debitactivitycif13.PreferencesHelper;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

//import smartdevelop.ir.eram.showcaseviewlib.GuideView;
//import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
//import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
//import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;
//import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

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


    public static CountdownToZeroDayCiF1004 mDaysToZero;
    private int mBalance;
    private java.util.Date mBalanceLastUpdated;

    private TextView mBalance_textview;

    private static Context appContext;
    public static MyCallBack mCallback;
    TextView countdownbalance;
    Toolbar toolbar;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccd__gui__cd__cif1);


        instance = this;

        mCallback = new MyCallBack() {
            @Override
            public void refreshMainActivity() {
                CCD_GUI_CD_CIF1.this.recreate();

                //"OR"

                //finish();
                //startActivity(getIntent());
            }
        };

        instance.Set_currentBalance();
        //instance.Start_Cycle(); //Only after "Start_Weight_Loss_Used_For_First_Time!
        appContext = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher7);
//=======
        countdownbalance = (TextView) findViewById(R.id.textView);
        preferencesHelper = new PreferencesHelper(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Update your Food Diary", Snackbar.LENGTH_LONG)
                        .setAction("Update", null).show();
            }
        });


        mCreditButton = (Button) findViewById(R.id.button2);
        mDebitButton = (Button) findViewById(R.id.button);
        mCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
                //data_model_adapter.setSex(true);
                StartFoodDiaryAidSheetCIF3();
                //CancelAlarm();
            }
        });


        mDebitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh balance from storage when returning to this activity
        refreshBalanceFromStorage();
    }

    private void refreshBalanceFromStorage() {
        try {
            MIF4_Data_Model_Adapter model_adapter = new MIF4_Data_Model_Adapter(this);
            String storedBalance = model_adapter.RetrieveBalance();
            if (storedBalance != null && !storedBalance.isEmpty()) {
                mBalance_text = storedBalance;
                final TextView countdownbalance = (TextView) findViewById(R.id.textView);
                if (countdownbalance != null) {
                    countdownbalance.setText(mBalance_text);
                    android.util.Log.d("Balance Refresh", "Balance refreshed to: " + mBalance_text);
                }
            }
        } catch (Exception e) {
            android.util.Log.e("Balance Refresh", "Error refreshing balance: " + e.getMessage());
        }
    }


    private void showGuideOnOverflowMenu(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View child = toolbar.getChildAt(i);
            if (child.getClass().getSimpleName().equals("OverflowMenuButton")) {
                new GuideView.Builder(this)
                        .setTitle(getString(R.string.guide_title_overflow))
                        .setContentText(getString(R.string.guide_content_overflow))
                        .setTargetView(child)
                        .setGravity(Gravity.center)
                        .setDismissType(DismissType.anywhere)
                        .build()
                        .show();
                return;
            }
        }
        Log.e("GuideView", "Overflow menu button not found.");
    }


    private void openBalance() {
        new GuideView.Builder(this)
                .setTitle(getString(R.string.guide_title_balance))
                .setContentText(getString(R.string.guide_content_balance))
                .setPointerType(PointerType.circle)
                .setTitleTypeFace(Typeface.DEFAULT_BOLD)
                .setTargetView(countdownbalance)//optional - default dismissible by TargetView
                .setDismissType(DismissType.anywhere)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        new Handler().post(() -> {
                            if (!isFinishing() && !isDestroyed()) {
                                openCredit();
                            }
                        });
                    }
                })
                .build()
                .show();
    }


    private void openDebit() {
        new GuideView.Builder(this)
                .setTitle(getString(R.string.guide_title_debit))
                .setContentText(getString(R.string.guide_content_debit))
                .setPointerType(PointerType.circle)
                .setTitleTypeFace(Typeface.DEFAULT_BOLD)
                .setTargetView(mDebitButton)
                .setDismissType(DismissType.anywhere)
//                .setGuideListener(view -> showPopupMenu(view))
                .build()
                .show();
    }

    private void openCredit() {
        new GuideView.Builder(this)
                .setTitle(getString(R.string.guide_title_credit))
                .setContentText(getString(R.string.guide_content_credit))
                .setPointerType(PointerType.circle)
                .setTitleTypeFace(Typeface.DEFAULT_BOLD)
                .setTargetView(mCreditButton)
                .setDismissType(DismissType.anywhere)
                .setGuideListener(view -> new Handler().post(() -> {
                    if (!isFinishing() && !isDestroyed()) {
                        openDebit();
                    }
                }))
                .build()
                .show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ccd__gui__cd__cif1, menu);
        MenuItem overflowItem = menu.findItem(R.id.action_overflow);
        View overflowView = overflowItem.getActionView();

        View customView = overflowItem.getActionView();

        if (customView != null) {
            customView.setOnClickListener(v -> showPopupMenu(v));
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (preferencesHelper.isFirstRun()) {
                appDescriptionDialog(() -> {
                    if (overflowView != null) {
                        showGuideView(overflowView);
                    } else {
                        Log.e("GuideView", "Overflow menu view not found.");
                    }
                    preferencesHelper.markGuideAsShown();
                });
            }
        }, 500);
        return true;
    }


    private void showGuideView(View overflowView) {
        new GuideView.Builder(this)
                .setTitle(getString(R.string.guide_title_main_menu))
                .setContentText(getString(R.string.guide_content_main_menu))
                .setPointerType(PointerType.circle)
                .setTitleTypeFace(Typeface.DEFAULT_BOLD)
                .setTargetView(overflowView)
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setGuideListener(view -> openBalance())
                .build()
                .show();
    }


    public void appDescriptionDialog(Runnable onDismiss) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.guide_dialog, null);

        TextView title = view.findViewById(R.id.dialog_title);
        TextView message = view.findViewById(R.id.dialog_message);
        Button okButton = view.findViewById(R.id.dialog_ok_button);

        message.setText(getString(R.string.Get_Started_Primer_Tutorial));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        okButton.setOnClickListener(v -> {
            dialog.dismiss();

            // Delay to ensure dialog is fully dismissed before executing the guide
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (onDismiss != null) onDismiss.run();
            }, 250); // Slight delay for smoother UX
        });

        dialog.show();
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);

        // Inflate the menu for the popup
        popupMenu.getMenuInflater().inflate(R.menu.overflow_menu_file, popupMenu.getMenu());

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener(item -> {

            int id = item.getItemId();

            if (id == R.id.action_settings) {
                return true;
            }

            if (id == R.id.quick_start_guide) {
                appDescriptionDialog(null);
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

            if (id == R.id.action_start_weightloss) {
                Start_Weight_Loss_ActivityCIF4();
                return true;
            }
            if (id == R.id.add_water_data) {
                Start_Add_water_Activity();
                return true;
            }
            if (id == R.id.show_water_data) {
                Start_Show_Water_Records_Activity();
                return true;
            }

            if (id == R.id.action_stop_weightlossb) {
                Start_Recalibration();
                return true;
            }

            if (id == R.id.action_log_it_in_reminder) {
                Start_Notification_ActivityCIF5();
                return true;
            }

            if (id == R.id.submenu3) {
                Start_Diet_Plan_Activity();
                return true;
            }

            if (id == R.id.submenu6) {
                Populate_SQLite_Database();
                return true;
            }

            if (id == R.id.submenu9) {
                De_Populate_SQLite_Database();

                return true;
            }

            if (id == R.id.submenu7) {
                Clear_SQLite_Database();
                return true;
            }

            if (id == R.id.submenu2a) {
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

            return true;

        });
        popupMenu.setGravity(android.view.Gravity.END);
        // Show the popup menu
        popupMenu.show();

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get Toolbar and MenuItem
        return super.onPrepareOptionsMenu(menu);
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

        if (id == R.id.action_start_weightloss) {
            Start_Weight_Loss_ActivityCIF4();
            return true;
        }

        if (id == R.id.action_stop_weightlossb) {
            Start_Recalibration();
            return true;
        }

        if (id == R.id.action_log_it_in_reminder) {
            Start_Notification_ActivityCIF5();
            return true;
        }

        if (id == R.id.submenu3) {
            Start_Diet_Plan_Activity();
            return true;
        }

        if (id == R.id.submenu6) {
            Populate_SQLite_Database();
            return true;
        }

        if (id == R.id.submenu9) {
            De_Populate_SQLite_Database();

            return true;
        }

        if (id == R.id.submenu7) {
            Clear_SQLite_Database();
            return true;
        }

        if (id == R.id.submenu2a) {
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

    private void Start_Client_Guide() {
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

    private void ShootClientGuide() {

        File file = new File(filepath);
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {

        }

    }


    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        try {
            if (data == null) {
                Log.d(TAG, "Sorry Mate, Intent is null Baby!");
                return;
            }
        } catch (Exception c) {

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

            if (data != null) {
                openingbalance = data.getIntExtra(Start_Weight_Loss_ActivityCIF14Fragment.OPENING_BALANCE, 1);

                //ResetBreakfastTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultBreakfastTime));
                //ResetLunchTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultLunchTime));
                //ResetDinnerTime = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultDinnerTime));
                //ResetMidnight = new RoundingCIF13().StringToDate(data.getStringExtra(Log_It_In_CIF15Fragment.ResultMidnight));

                SResetBreakfastTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultBreakfastTime));
                SResetLunchTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultLunchTime));
                SResetDinnerTime = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultDinnerTime));
                SResetMidnight = (data.getStringExtra(Log_It_In_CIF15Fragment.ResultMidnight));


                android.util.Log.d("Supreme_Money_Shot RestB", SResetBreakfastTime);
                android.util.Log.d("Supreme_Money_Shot RestL", SResetLunchTime);
                android.util.Log.d("Supreme_Money_Shot RestD", SResetDinnerTime);
                android.util.Log.d("Supreme_Money_Shot RestN", SResetMidnight);

                ResetBreakfastTime = myStringToDate(SResetBreakfastTime);
                ResetLunchTime = myStringToDate(SResetLunchTime);
                ResetDinnerTime = myStringToDate(SResetDinnerTime);
                ResetMidnight = myStringToDate(SResetMidnight);

                ResetAlarmTimer(ResetBreakfastTime, ResetLunchTime, ResetDinnerTime, ResetMidnight);


            } else {

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

                android.util.Log.d("Credit_Value, Pos 1", "We are in Start of Request Food Diary");


                int CreditResult = data.getIntExtra(Food_Diary_Sheet_CIF3.TOTAL_CREDIT_VALUE, 1);

                android.util.Log.d("Credit_Value, Pos 2", "We are in Start of Request Food Diary");

                String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
                //How it for Countdown Screen, get App ready ready for use, might have to look in Intent

                android.util.Log.d("Credit_Value, Pos 3", "We are in Start of Request Food Diary");

                mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
                mSummation.Set_mCurrentBalance(Get_currentBalance());
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);

                android.util.Log.d("Credit_Value, Pos 4", "We are in Start of Request Food Diary");

                long res_of_balance_store = Record_Food_Journal(mSummation.Get_mFoodItems());

                String display_string = "This are the results of storing Balance to SQLite : " + new RoundingCIF13().LongToString(res_of_balance_store);

                display_dialog_cif11.Showing(display_string);

                android.util.Log.d("Credit_Value, Pos 5", "We are in Start of Request Food Diary");

                Log.d("1st RecordJ Fi name", mSummation.Get_mFoodItems().get(0).Get_food_item_name());

                android.util.Log.d("Credit_Value, Pos 6", "We are in Start of Request Food Diary");

                display_dialog_cif11.SummaryBoxShowing(mSummation);
                android.util.Log.d("Credit_Value, Pos 7", "We are in Start of Request Food Diary");
                mSummation.reset();
                android.util.Log.d("Credit_Value, Pos 8", "We are in Start of Request Food Diary");
                //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
                Countup(CreditResult);
                android.util.Log.d("Credit_Value, Pos 9", "We are in Start of Request Food Diary");
                Refresh();
                android.util.Log.d("Credit_Value, Pos 10", "We are in Start of Request Food Diary");


                if (isItDayEnd()) {
                    New_Day_2();
                }
            } catch (NullPointerException e) {
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
            Store_Dayend2();
            Refresh();
        }
    }

    private void Store_Dayend2()
    {
        //Algorithm Engineering Noir:
        //Step One
        //Android
        //You need to first check if the current time is 4pm or past 4pm.
        //If it is the activate this Call and do something, if not, do nothing.
        //If it is past 4pm and the boolean variable  hasDayEndAlreadyBeenStoredandCFWD4theDay is not true
        //then store the current balance in SQLite (just once) has to be in DayEnd2 Table.
        //then check somet the initial the next day DayType and store relevatne variable in there and in
        //current day DayType in the overall list collecton of Dayz in CiF001.
        //Remember only deal with DayEnd2 Table in SQLite to get Kitty() working well and properely.
    }



    private void StartWebCalculatorFragment2()
        {


        Uri uri = Uri.parse(webadress);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

        //CancelAlarm();

    }


    private void Start_Native_Calculator() {
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

    private void Start_Weight_Loss_ActivityCIF4() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.Start_Weight_Loss_ActivityCIF14.class);
        this.startActivityForResult(i, REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY);

    }

    private void Start_Add_water_Activity() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.AddWaterData.class);
        this.startActivityForResult(i, REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY);

    }

    private void Start_Show_Water_Records_Activity() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.WaterRecordsActivity.class);
        this.startActivityForResult(i, REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY);

    }

    private void Start_Recalibration() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.Recalibrate.class);
        this.startActivityForResult(i, REQUEST_CODE_RECALIBRATION);

    }


    public void Start_Weight_Loss() {
        //Alogrithm Engineering ~> Android (Black ~> Builder) : This is Lego Box for where 5pm Magic Occurs :

        //MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
        //dayEndSetAlarm.NewDayEndSetAlarm(new Date(), this);

    }

    public void Start_Day_End() {
        //android.util.Log.d("Day END inside Bridge","Day end cfwd Started");

        //mBalance = 76702;
        //mBalance_text = "76,702";

        //mBalance_textview = (TextView)findViewById(R.id.textView);
        //mBalance_textview.setText(mBalance_text);


        //New_Day_1();
    }

    public void Start_Notification_ActivityCIF5() {
        //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Log_It_In_CIF15.class);
        this.startActivityForResult(i, REQUEST_CODE_LOG_IT_IN);

    }

    public void Start_Day_End_ActivityCIF5a() {
        //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

        //Intent i = new Intent(CCD_GUI_CD_CIF1.this, Day_END_CIF15a.class);
        // this.startActivityForResult(i, REQUEST_CODE_NEW_DAY);

    }

    public void Start_Diet_Plan_Activity() {
        //Alogrithm Engineering ~> Android ( Black ~> Builder -> (re)Load... -> www.ese-edet.eu ) : This is Lego Box for where ___ Occurs :

        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Diet_Plan_Activity_fragment008Fragment.class);
        this.startActivityForResult(i, REQUEST_CODE_DIET_PLAN);

    }

    public void Countup(int credit) {
        android.util.Log.d("Countdown", "Consider Countdown Updated Token");
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        String CountdownFigure = countdownbalance.getText().toString();
        CountdownFigure = new RoundingCIF13().IntToString(new RoundingCIF13().StringToInt(CountdownFigure) + credit);
        countdownbalance.setText(CountdownFigure);
        StoreCountdownBalance(CountdownFigure);
        Kitty();

    }

    private void OpenAccount(int OpeningBalance) {
        //android.util.Log.d("Countdown","Consider Countdown Updated Token") ;
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        String CountdownFigure = countdownbalance.getText().toString();
        CountdownFigure = new RoundingCIF13().IntToString(OpeningBalance);

        CountdownFigure = Strip_Comma(CountdownFigure);

        countdownbalance.setText(CountdownFigure);
        StoreCountdownBalance(CountdownFigure);

    }

    private boolean ResetAlarm(Date b, Date l, Date d, Date m, Date e, Date mm) {

        try {
            SpecialThreadCIF15 speetread = new SpecialThreadCIF15(getApplicationContext());

            speetread.Set_RBreakfastTime(b);
            speetread.Set_RLunchTime(l);
            speetread.Set_RDinnerTime(d);
            speetread.Set_RMidnightTime(m);

            ObjectWithAllTheTimesCIF10 objectWithAllTheTimesCIF10 = speetread.ActivateAlarm();
            //ResetAlarmTimer(objectWithAllTheTimesCIF10);

            speetread.start();
            return true;
        } catch (Exception c) {
            //Make exception more specifit
            //Catch the right name of exception that is thrown if thread already running e.g.
            //if already set in Start Weight Loss Menuitem. and tell user by displaying on screen
            //using appropiate display functions that alarm already set and running
            return false;
        }

    }

    private void ResetAlarmTimer(Date resetBreakfastTime, Date resetLunchTime, Date resetDinnerTime, Date resetMidnight) {

        resetBreakfastTime = add24(resetBreakfastTime); //?
        resetLunchTime = add24(resetLunchTime);
        resetDinnerTime = add24(resetDinnerTime);
        resetMidnight = add24(resetMidnight);

        if (ResetAlarm(resetBreakfastTime, resetLunchTime, resetDinnerTime, resetMidnight, resetLunchTime, resetDinnerTime)) {
            ;
        }

        this.ResetAlarm(plus24hour(resetBreakfastTime), plus24hour(resetLunchTime), plus24hour(resetDinnerTime), plus24hour(resetMidnight), plus24hour(resetLunchTime), plus24hour(resetDinnerTime));

    }

    public Date ProperMidnight() {
        int hour = 5;
        int minute = 0;

        Calendar Greg = Calendar.getInstance();
        Greg.set(Calendar.HOUR, hour);
        Greg.set(Calendar.MINUTE, minute);
        Greg.set(Calendar.AM_PM, Calendar.PM);
        return Greg.getTime();
    }

    public long StoreCountdownBalance(String Balance) {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        data_model_adapter.StoreDayEndBalance(Integer.parseInt(Balance) - 100);
        return data_model_adapter.StoreBalance(Balance);
    }


    public long Store_Dayend(int data)
    {
            //Algorithm Engineering
            //Insert Implementation Code Logic to Store Day End2 Balance here, if past 16:00
            //remember to implement those double try bug fixed


        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        return data_model_adapter.StoreDayEndBalance(data);
    }

    private Date add24(Date time) {
        //long OneMinute = (1000 * 60 * 60 * 24);
        //time.setTime(time.getTime() + (OneMinute * 3000));
        return time;

    }

    private Date plus24hour(Date time) {
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

    private void StartFoodDiaryNotes() {
        android.util.Log.d("Pre Food Diary Notes", "number2");
//        Intent i = new Intent(CCD_GUI_CD_CIF1.this, FoodNoteItemDetailHostActivity.class);
//        android.util.Log.d("Pre Food Diary Notes", "number3");
//        this.startActivityForResult(i, REQUEST_CODE_GET_FOOD_NOTE_ITEM);
//        android.util.Log.d("Pre Food Diary Notes", "number4");
        Intent intent = new Intent();
        intent.setClassName("ese.com.caloriecountdownappforandroidbrown2", "ese.com.caloriecountdownappforandroidbrown.FoodNoteTableActivity");
        startActivity(intent);

    }


    private Date myStringToDate(String editText) {
        String hour = editText.substring(0, 2);
        String minute = editText.substring(2, 4);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, new RoundingCIF13().StringToInt(hour));
        calendar.set(Calendar.MINUTE, new RoundingCIF13().StringToInt(minute));

        Date result = new Date(calendar.getTimeInMillis());

        return result;
    }

    @Override
    public void recreate() {
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

        mCallback = new MyCallBack() {
            @Override
            public void refreshMainActivity() {
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


    public interface MyCallBack {
        public void refreshMainActivity();
    }

    private void StartDebitActivityCIF13() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Debit_Activity_CiF003_fragment_box.class);
        this.startActivityForResult(i, REQUEST_CODE_START_DEBIT_ACTIVITY);
    }

    private void Start_Journal_Activity_CiF115() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, Journal_Activity_CiF0115_fragment_box.class);
        this.startActivityForResult(i, REQUEST_CODE_START_JOURNAL_ACTIVITY);
    }


    private void NewDay() {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(this);
        String currentBalance = data_model_adapter.RetrieveBalance();
        mBalance = new RoundingCIF13().StringToInt(currentBalance);
        if (data_model_adapter.getSex() == "MALE") {
            mBalance = mBalance - 2000;
        }
        if (data_model_adapter.getSex() == "FEMALE") {
            mBalance = mBalance - 2000;
        } else {
            mBalance = mBalance - 2000;
        }
        data_model_adapter.StoreBalance(new RoundingCIF13().IntToString(mBalance));
        data_model_adapter.StoreDayEndBalance((mBalance - 350 + 2570));
    }

    private void New_Day_1() {
        NewDay();
    }


    private boolean Populate_SQLite_Database() {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        return Gen_Pop.Populate();

    }

    private boolean De_Populate_SQLite_Database() {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        return Gen_Pop.De_Populate_Database();
    }

    private void Clear_SQLite_Database() {
        Populate_SQLDatabase_Food_Items_CIF7 Gen_Pop = new Populate_SQLDatabase_Food_Items_CIF7(this);
        Gen_Pop.Delete_Database();
    }


    private void Kitty() {

        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        //int dayend = data_model_adapter.RetriveDayEnd();
        int dayend = 100;
        if(mDaysToZero == null)
        {
            mDaysToZero = data_model_adapter.RetrievemForecast();
            android.util.Log.d("Calorie Countdown app", "mDayToZero, retrieved, this is Contents:");
            android.util.Log.d("Version  2.0.0", mDaysToZero.printDaysType());
        }

        if(mDaysToZero != null)
        {
            android.util.Log.d("Calorie Countdown app", "mDayToZero, already Intialized, this is Contents:");
            android.util.Log.d("Version  2.0.0", mDaysToZero.printDaysType());

            DayCiF1005 mToday = mDaysToZero.getCurrentDayType1005();
            if((mToday) != null)
            {
                android.util.Log.d("Calorie Countdown app for iOS", "mToday NOT NULL, contents of mToday:");

                dayend = mToday.getBudgetedDayEndBalanceForThisDay();
            }
            else
            {
                dayend = 101;
                android.util.Log.d("assigning int dayend","dayend equals 101");
            }
        }
        else
        {
            android.util.Log.d("mDaysToZero", "Sorry mate, this var is null, Sort it! Noir.");

        }


        int currentbalance = Get_currentBalanceInt();


        int kit = currentbalance - dayend; //See i from Food Note and Complete
        if(kit > 0) //positive value for kit mean the about of live calories/points that need to be
            // burnt and Debitted. Go ahead and give Step Challange and amount of Steps needed, considering current
            //value of Steps done.
        {

            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyMinus(kit));
        }

        if (kit == 0) {
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyZero(kit));
        }

        if(kit < 0) //this will be a negative value and means smooth sailing, no need to anything
            // as you are below 100 or 300 dr points target, the more this widens the more the countdown
            // but don't recommend more than 1000 Dr - 1500 Points dr the more it widens the more Countdown Balance reduce
            // at 16:00 do Bloomberg report things, remember 7pm final and scrap next day prep etc 0 cr and Surplus account and
            // repeat Start Weight Loss Menuitem, updated Client Guide, adMob 0280 xero.sys Download AWS & Google Play Logo.
            // Link in green and Create.
            //Version 2.0.0
        {

            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            display_dialog_cif11.Showing(KittyPlus(kit));
        }

    }


    private String KittyMinus(int in)
    {
        int kit = Math.abs(in);
        in = kit;
        int walkminutes = GenerateStepsChallenge(in);
        String out = "Your Steps Challenge to successfully countdown your Balance by 300 pionts by Dayend (7pm) is: " + new RoundingCIF13().IntToString(walkminutes) + " Steps.\n\nOnly dispose of the Dialog once you have performed it.";

        return out;
    }

    private String KittyZero(int in) {
        String out = "You have no Calories to eat left in your Kitty, Do not eat or exercise for the rest of the day, you are on track for Weight loss. ";
        return out;
    }


    
    private String KittyPlus(int in)
    {
      //private String KittyPlus(int in) {
        //int walkminutes = (int) in / 7;
       // String out = "You now need to Walk for " + new RoundingCIF13().IntToString(walkminutes) + " minutes to be on track for Weight Loss, only dispose of the dialog once you have performed.";


        int kit = Math.abs(in);
        String out = "You have " + new RoundingCIF13().IntToString(kit) + " Calories left in the Kitty for the meals left in the day, this includes all exercise done, this figure left as it is, will be subtracted from your Countdown Balance.";


        return out;
    }


    public int Get_currentBalanceInt() {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        return new RoundingCIF13().StringToInt(countdownbalance.getText().toString());
    }

    public String Get_currentBalance() {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        return new String(countdownbalance.getText().toString());
    }

    private void Set_currentBalance() {

        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        MIF4_Data_Model_Adapter model_adapter = new MIF4_Data_Model_Adapter(this);

        mBalance_text = model_adapter.RetrieveBalance();

        android.util.Log.d("Set Balance = ", mBalance_text);
        model_adapter.StoreTargetWeightLossPounds("249");

        countdownbalance.setText(mBalance_text);
    }
//==========
    private void Set_Balance(String input) {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);


        mBalance_text = input;

        android.util.Log.d("Set Balance = ", mBalance_text);

        countdownbalance.setText(mBalance_text);
        StoreCountdownBalance(mBalance_text);
        Kitty();
    }

    private void Refresh() {
        getWindow().getDecorView().findViewById(R.id.fragment).invalidate();
    }

    private long Record_Food_Journal(ArrayList<Food_Item_CIF4> INPUT) {
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
        StoreCountdownBalance((mBalance_text = CountdownFigure));
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

    public void UpdateCountdownUI() {
        mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
        //Update button in Food_Diary_Sheet_CIF3 Activity creates a return event, them this method called
        DisplaySummaryString(mSummation.GetSummaryString()); //Toast, alert or dialog
        Countdown(mSummation.GetDebitValue());
        mSummation.reset();
        Refresh();//GUI

    }


    public void Start_Weight_LossPlus24() {
        MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
        dayEndSetAlarm.NewDayEndSetAlarm(add24(new Date()), this);

    }

    public void Start_Weight_Loss_CancelAlarm() {
        MIF1NewDayEndSetAlarm dayEndSetAlarm = new MIF1NewDayEndSetAlarm();
        dayEndSetAlarm.CancelAlarm();
    }


    private void CancelAlarm() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, NewDayCountdown.class);
        i.setAction(ACTION_STORE_BALANCE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.cancel(pi);
        pi.cancel();
    }


    private void SetNewDayAlarm(int h, int m, int d, int mon, int yr) {
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
        alarmManager.setRepeating(AlarmManager.RTC, (System.currentTimeMillis() + KalendTime), intervalmillis, pi);
        Log.d("Countdown", "Alarm Manager Set Yeah verify");

    }

    public void New_Day_2() {
        //Calendar event emitted & Admob & 77p & B x G = ¢#∞§€¡@!£5.57 + WWW.ESE-EDET.EU & Numbers 7v7
    }

    public void setResetDayEnd() {

    }

    public void setResetBreakfastTime(Date btime) {
        ResetBreakfastTime = btime;
    }

    public void setResetLunchTime(Date ltime) {
        ResetLunchTime = ltime;
    }

    public void setResetDinnerTime(Date dtime) {
        ResetDinnerTime = dtime;
    }

    public void ChangeTextColor(String input) {
        Set_Balance(input);
    }

    public void ChangeButtonColor() {

    }

    public void ChangeBackgroundImage() {

    }

    public void Recalibrate() {

    }


    public void InitializeCountdownToXeroDayType1004inCiF001asStatic(HealthProfileCiF3 IN)
    {
        mDaysToZero = new CountdownToZeroDayCiF1004(IN.getStartCountdown(), IN);
        //mDaysToZero.setupType();
    }

    private void ResetAlarmTimer(ObjectWithAllTheTimesCIF10 obj)
    {

        ResetAlarmTimer(obj.getResetBreakfastTime(), obj.getResetLunchTime(), obj.getResetDinnerTime(), obj.getResetDayEnd());
    }

    private String Strip_Comma(String INPUT) {
        for (int c = 0; c < INPUT.length(); c++) {
            if (INPUT.charAt(c) == ',') {
                StringBuffer IN = new StringBuffer(INPUT);
                IN.deleteCharAt(c);
                return IN.toString();
            }
        }

        return INPUT;
    }

    private boolean isItDayEnd() {
        return false;
    }

    public void Store_Target_Weight_Pounds(String Input1) {
        //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        //data_model_adapter.StoreTargetWeightLossPounds(Input1);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();

        editor.putString("Target_Weight", Input1);
        editor.commit();

    }


    class CurrentCalendar {
        private java.util.Calendar Kalends;
        public int Hour;
        public int Minute;
        public int Day_of_the_Month;
        public int Month;
        public int Year;


        public CurrentCalendar() {
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

    private int GenerateStepsChallenge(int in)
    {
        return 10_000;
    }

}

