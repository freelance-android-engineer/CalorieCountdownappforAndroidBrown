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
import android.os.Build;
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
import android.widget.EditText;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Button mStepsChallengeMain;

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

    /**
     * mDayZero — flat list of DayCiF1005 objects spanning from today to estimated zero date.
     * Rule: numberOfDays = openingBalance / 25  (each day targets 25-point minimum countdown).
     * Populated by buildMDayZeroList() when opening balance is available.
     * Use mDaysToZero for the richer CountdownToZeroDayCiF1004 collection.
     */
    public static java.util.List<DayCiF1005> mDayZero = new java.util.ArrayList<>();

    private int mBalance;
    private java.util.Date mBalanceLastUpdated;

    private TextView mBalance_textview;

    private static Context appContext;
    public static MyCallBack mCallback;
    TextView countdownbalance;
    Toolbar toolbar;
    private PreferencesHelper preferencesHelper;

    // Kitty deployment guard: prevents duplicate Kitty triggers within a single credit cycle.
    // Reset to false at the start of each new credit operation; set true once Kitty fires.
    private volatile boolean mKittyDeployed = false;
    private final Handler mKittyHandler = new Handler(Looper.getMainLooper());

    // Background executor for DB operations that must not run on the main thread.
    private ExecutorService mBgExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccd__gui__cd__cif1);

        instance = this;
        mBgExecutor = Executors.newSingleThreadExecutor();

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

        // Request notification permission for Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        // Schedule the two daily alarms: 4:00 PM (Credit Day End) and 9:59 PM (Debit Day End)
        DailyAlarmScheduler.scheduleBothAlarms(this);

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
                Snackbar.make(view, "Update Countdown Balance", Snackbar.LENGTH_LONG)
                        .setAction("Update", null).show();
            }
        });


        mCreditButton = (Button) findViewById(R.id.button2);
        mDebitButton = (Button) findViewById(R.id.button);
        mCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Double-click guard: disable immediately so a second tap cannot start a
                // second Food_Diary_Sheet_CIF3 before the first one returns.
                mCreditButton.setEnabled(false);
                // Reset Kitty guard so this new credit operation can trigger Kitty exactly once.
                mKittyDeployed = false;
                StartFoodDiaryAidSheetCIF3();
            }
        });


        mDebitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDebitActivityCIF13();
                //Set_currentBalance();
            }
        });

        // Steps Challenge button — visible on main screen, used after crediting food notes
        mStepsChallengeMain = (Button) findViewById(R.id.btnStepsChallengeMain);
        if (mStepsChallengeMain != null) {
            mStepsChallengeMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStepsChallengeFromMain();
                }
            });
        }


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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            // Re-schedule alarms after notification permission is handled
            DailyAlarmScheduler.scheduleBothAlarms(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh balance from storage when returning to this activity
        refreshBalanceFromStorage();
        // Apply any saved appearance customizations (color, font, format, circle, background)
        AppCustomization.apply(this);
        // Re-schedule alarms on every resume for reliability (e.g. after returning from settings)
        DailyAlarmScheduler.scheduleBothAlarms(this);
    }

    private void refreshBalanceFromStorage() {
        try {
            MIF4_Data_Model_Adapter model_adapter = new MIF4_Data_Model_Adapter(this);
            String storedBalance = model_adapter.RetrieveBalance();
            android.util.Log.d("BalanceRefresh", "[refreshBalanceFromStorage] Raw DB balance: " + storedBalance);
            // Root cause fix: treat null/empty balance as "0" to prevent downstream NFE
            if (storedBalance == null || storedBalance.trim().isEmpty()) {
                storedBalance = "0";
                android.util.Log.w("BalanceRefresh", "[refreshBalanceFromStorage] DB balance null/empty, defaulting to 0");
            }
            mBalance_text = storedBalance;
            final TextView countdownbalance = (TextView) findViewById(R.id.textView);
            if (countdownbalance != null) {
                countdownbalance.setText(mBalance_text);
                android.util.Log.d("BalanceRefresh", "[refreshBalanceFromStorage] UI updated to: " + mBalance_text);
            } else {
                android.util.Log.e("BalanceRefresh", "[refreshBalanceFromStorage] countdownbalance TextView is null");
            }
        } catch (Exception e) {
            android.util.Log.e("BalanceRefresh", "[refreshBalanceFromStorage] Exception: " + e.getMessage(), e);
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

            // Settings sub-menu items (placeholder - do nothing for now)
            if (id == R.id.settings_countdown_mode) {
                // Countdown/Weight Loss mode - to be implemented
                return true;
            }

            if (id == R.id.settings_hold_ground_mode) {
                // Hold Ground/Weight Maintenance mode - to be implemented
                return true;
            }

            if (id == R.id.settings_surplus_mode) {
                // Surplus account mode - to be implemented
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

            if (id == R.id.measure_heart_rate) {
                Start_Heart_Rate_Activity();
                return true;
            }

            if (id == R.id.health_blood_pressure) {
                startActivity(new android.content.Intent(this, ese.com.caloriecountdownappforandroidbrown.BloodPressureActivity.class));
                return true;
            }

            if (id == R.id.health_heart_rate_readings) {
                startActivity(new android.content.Intent(this, ese.com.caloriecountdownappforandroidbrown.HeartRateReadingsActivity.class));
                return true;
            }

            if (id == R.id.health_blood_sugar) {
                startActivity(new android.content.Intent(this, ese.com.caloriecountdownappforandroidbrown.BloodSugarActivity.class));
                return true;
            }

            if (id == R.id.action_recalibrate) {
                Start_Recalibration();
                return true;
            }

            if (id == R.id.action_estimated_date_to_zero) {
                Show_Estimated_Date_To_Zero();
                return true;
            }

            if (id == R.id.action_progress_forecast_charts) {
                startActivity(new Intent(CCD_GUI_CD_CIF1.this, ProgressForecastChartActivity.class));
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

            if (id == R.id.submenu3aa) {
                showAccrualDialog();
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

            if (id == R.id.action_customize) {
                startActivity(new Intent(CCD_GUI_CD_CIF1.this, CustomizationSettingsActivity.class));
                return true;
            }

            if (id == R.id.convert_stones_to_kg) {
                showConversionInputDialog("Stones to Kilograms", "Enter weight in Stones:", "stones_to_kg");
                return true;
            }

            if (id == R.id.convert_pounds_to_kg) {
                showConversionInputDialog("Pounds to Kilograms", "Enter weight in Pounds:", "pounds_to_kg");
                return true;
            }

            if (id == R.id.convert_kg_to_stones) {
                showConversionInputDialog("Kilograms to Stones", "Enter weight in Kilograms:", "kg_to_stones");
                return true;
            }

            if (id == R.id.convert_kg_to_pounds) {
                showConversionInputDialog("Kilograms to Pounds", "Enter weight in Kilograms:", "kg_to_pounds");
                return true;
            }

            if (id == R.id.variables_balance_last_updated) {
                showBalanceLastUpdatedDialog();
                return true;
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

        if (id == R.id.action_recalibrate) {
            Start_Recalibration();
            return true;
        }

        if (id == R.id.action_stop_weightlossb) {
            return true;
        }

        if (id == R.id.action_estimated_date_to_zero) {
            Show_Estimated_Date_To_Zero();
            return true;
        }

        if (id == R.id.action_progress_forecast_charts) {
            startActivity(new Intent(CCD_GUI_CD_CIF1.this, ProgressForecastChartActivity.class));
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

        if (id == R.id.submenu3aa) {
            showAccrualDialog();
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

        if (id == R.id.action_customize) {
            startActivity(new Intent(this, CustomizationSettingsActivity.class));
            return true;
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

        android.util.Log.d("ActivityResult", "[onActivityResult] requestcode=" + requestcode + ", resultcode=" + resultcode + ", data=" + (data != null ? "present" : "null"));

        // Re-enable the credit button as soon as we return from the food diary,
        // whether the user confirmed food items OR cancelled. This covers both paths
        // and prevents the button staying disabled if the user backs out.
        if (requestcode == REQUEST_CODE_GET_FOOD_ITEM && mCreditButton != null) {
            mCreditButton.setEnabled(true);
        }

        // Root cause fix: null data check moved OUTSIDE try-catch so it cannot be silently swallowed.
        // The original empty catch (Exception c) {} was swallowing the return statement's effect
        // when any unexpected exception occurred, allowing null data to reach code below.
        if (data == null) {
            Log.w(TAG, "[onActivityResult] Intent data is null for requestcode=" + requestcode);
            // Allow weight loss activity to still process (it has its own null handling)
            if (requestcode != REQUEST_CODE_START_WEIGHT_LOSS_ACTIVITY) {
                return;
            }
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
            // Root cause fix: broader exception catching (was only NullPointerException,
            // missing NumberFormatException thrown by StringToInt on empty balance TextView)
            try {
                android.util.Log.d("CreditFlow", "[CREDIT] Starting credit flow");

                // Root cause fix: default to 0 (not 1) so empty result doesn't silently add 1 point
                int CreditResult = data.getIntExtra(Food_Diary_Sheet_CIF3.TOTAL_CREDIT_VALUE, 0);
                String Summation = data.getStringExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT);
                android.util.Log.d("CreditFlow", "[CREDIT] creditValue=" + CreditResult + ", summation=" + Summation);

                mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
                mSummation.Set_mCurrentBalance(Get_currentBalance());
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);

                long res_of_balance_store = Record_Food_Journal(mSummation.Get_mFoodItems());
                android.util.Log.d("CreditFlow", "[CREDIT] Record_Food_Journal result=" + res_of_balance_store);

                String display_string = "Balance stored to DB: " + new RoundingCIF13().LongToString(res_of_balance_store);
                display_dialog_cif11.Showing(display_string);

                // Root cause fix: mSummation.Get_mFoodItems() may be null/empty — guard before get(0)
                // Original code: mSummation.Get_mFoodItems().get(0) throws IndexOutOfBoundsException if empty
                ArrayList<Food_Item_CIF4> foodItems = mSummation.Get_mFoodItems();
                if (foodItems != null && !foodItems.isEmpty()) {
                    android.util.Log.d("CreditFlow", "[CREDIT] First food item: " + foodItems.get(0).Get_food_item_name());
                } else {
                    android.util.Log.w("CreditFlow", "[CREDIT] Food items list is null or empty");
                }

                display_dialog_cif11.SummaryBoxShowing(mSummation);
                mSummation.reset();

                // Capture balance BEFORE credit for Countdown Report
                int previousBalance = Get_currentBalanceInt();
                android.util.Log.d("CreditFlow", "[CREDIT] previousBalance=" + previousBalance);

                android.util.Log.d("CreditFlow", "[CREDIT] Calling Countup with creditValue=" + CreditResult);
                Countup(CreditResult);
                android.util.Log.d("CreditFlow", "[CREDIT] Countup completed, refreshing UI");
                Refresh();

                int newBalance = Get_currentBalanceInt();
                showCountdownReport(previousBalance, newBalance);

                if (isItDayEnd()) {
                    New_Day_2();
                }
                android.util.Log.d("CreditFlow", "[CREDIT] Credit flow completed successfully");
            } catch (NullPointerException e) {
                // Root cause: SummaryBox or food items null — log full trace for diagnostics
                android.util.Log.e("CreditFlow", "[CREDIT] NPE: " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Credit update failed (null data). Please try again.", android.widget.Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e) {
                // Root cause: balance TextView was empty/null → StringToInt("") throws NFE
                android.util.Log.e("CreditFlow", "[CREDIT] NFE (likely empty balance): " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Credit update failed (balance format error). Please check your countdown balance.", android.widget.Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                android.util.Log.e("CreditFlow", "[CREDIT] Unexpected exception: " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Credit update failed: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
            }
        }


        if (requestcode == REQUEST_CODE_START_DEBIT_ACTIVITY) {
            // Root cause fix: entire debit block wrapped in try-catch since no exception handling existed.
            // Any exception here (e.g. NFE from StringToInt on empty balance, NPE from null mSummation)
            // would previously crash the app with no user feedback.
            try {
                android.util.Log.d("DebitFlow", "[DEBIT] Starting debit flow");

                // Capture balance BEFORE debit for Countdown Report comparison
                int previousBalance = Get_currentBalanceInt();
                android.util.Log.d("DebitFlow", "[DEBIT] previousBalance=" + previousBalance);

                // Root cause fix: default to 0 (not 1) so empty result doesn't silently deduct 1 point
                int DebitResult = data.getIntExtra(Debit_Activity_CiF003_fragment_box.TOTAL_DEBIT_VALUE, 0);
                android.util.Log.d("DebitFlow", "[DEBIT] debitValue=" + DebitResult);

                mSummation = SummaryBoxCIF12.get(CCD_GUI_CD_CIF1.this);
                mSummation.Set_mCurrentBalance(Get_currentBalance());
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                display_dialog_cif11.SummaryBoxShowingDebit(mSummation);

                android.util.Log.d("DebitFlow", "[DEBIT] Calling Countdown with debitValue=" + DebitResult);
                Countdown(DebitResult);
                android.util.Log.d("DebitFlow", "[DEBIT] Countdown completed");

                Store_Dayend(instance.Get_currentBalanceInt());
                Store_Dayend2();
                Refresh();

                markDebitUpdatePerformed();
                int newBalance = Get_currentBalanceInt();
                android.util.Log.d("DebitFlow", "[DEBIT] Debit complete. prev=" + previousBalance + ", new=" + newBalance);
                showCountdownReport(previousBalance, newBalance);
                android.util.Log.d("DebitFlow", "[DEBIT] Debit flow completed successfully");
            } catch (NumberFormatException e) {
                // Root cause: balance TextView empty/null → StringToInt("") → NFE
                android.util.Log.e("DebitFlow", "[DEBIT] NFE (likely empty balance): " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Debit update failed (balance format error). Please check your countdown balance.", android.widget.Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                android.util.Log.e("DebitFlow", "[DEBIT] NPE: " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Debit update failed (null data). Please try again.", android.widget.Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                android.util.Log.e("DebitFlow", "[DEBIT] Unexpected exception: " + e.getMessage(), e);
                android.widget.Toast.makeText(this, "Debit update failed: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
            }
        }

        // Handle Journal Activity result for Accrual transfers
        if (requestcode == REQUEST_CODE_START_JOURNAL_ACTIVITY) {
            if (resultcode == RESULT_OK && data != null) {
                int transferAmount = data.getIntExtra("TRANSFER_AMOUNT", 0);
                int newCurrentBalance = data.getIntExtra("NEW_CURRENT_BALANCE", 0);
                int newNextBalance = data.getIntExtra("NEW_NEXT_BALANCE", 0);

                // Accrual only moves points between daily budgets
                // The main countdown balance should NOT change
                // Just refresh mDaysToZero for consistency
                MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(this);
                mDaysToZero = adapter.RetrievemForecast();

                // Log the successful accrual transfer (don't change main balance)
                Log.d("JournalAccrual", "Accrual transfer completed: " + transferAmount +
                      " points moved from today to tomorrow's budget.");

                // Refresh display without changing balance
                Refresh();
            }
        }
    }

    /** Public entry point for DailyAlarmReceiver to call when the 9:59 PM alarm fires. */
    public void Store_Dayend2_Public() {
        Store_Dayend2();
    }

    /**
     * 9:59 PM Midnight Scrape — Store Day End 2 Snapshot.
     *
     * Fires after the 9:59 PM alarm (and also after every debit update if past 4 PM).
     * Idempotent: stores only ONCE per calendar day using isDayEnd2StoredForDate() guard.
     *
     * Captures:
     *   - Current countdown balance
     *   - Gender-based daily calorie budget (2000F / 2500M)
     *   - Total food note calories consumed today (non-transferred notes)
     *   - Kitty = budget - food_calories
     *   - Estimated Zero Date (balance / 250 days from today)
     *
     * Stores result in dayend_balance2 SQLite table via storeDayEnd2Snapshot().
     */
    private void Store_Dayend2() {
        try {
            Calendar now = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR_OF_DAY);

            // Only run at or after 4 PM (16:00)
            if (hour < 16) {
                android.util.Log.d("DayEnd2", "[Store_Dayend2] Before 4 PM — skipping.");
                return;
            }

            String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                    .format(now.getTime());
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(this);

            // Idempotent guard: only snapshot once per day
            if (db.isDayEnd2StoredForDate(today)) {
                android.util.Log.d("DayEnd2", "[Store_Dayend2] Already stored for " + today + " — skipping.");
                return;
            }

            int currentBalance = Get_currentBalanceInt();

            // Gender-based daily calorie budget
            SharedPreferences prefs = getSharedPreferences("Calorie_Countdown", 0);
            String gender = prefs.getString("user_gender", "male");
            int dailyBudget = "female".equalsIgnoreCase(gender) ? 2000 : 2500;

            // Today's food note calories (non-transferred)
            int todayFoodCalories = db.getTotalFoodNoteCaloriesToday();
            int kittyValue = dailyBudget - todayFoodCalories;

            // Estimated zero date at 250 cal/day countdown rate
            int daysToZero = (currentBalance > 0) ? (int) Math.ceil((double) currentBalance / 250.0) : 0;
            Calendar zeroDate = Calendar.getInstance();
            zeroDate.add(Calendar.DAY_OF_YEAR, daysToZero);
            String estimatedZeroDate = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                    .format(zeroDate.getTime());

            // Store snapshot locally
            db.storeDayEnd2Snapshot(today, currentBalance, dailyBudget, todayFoodCalories, kittyValue, estimatedZeroDate);

            android.util.Log.d("DayEnd2", "[Store_Dayend2] Snapshot stored:"
                    + " date=" + today
                    + " balance=" + currentBalance
                    + " budget=" + dailyBudget
                    + " food=" + todayFoodCalories
                    + " kitty=" + kittyValue
                    + " zeroDate=" + estimatedZeroDate);

            // Azure backend sync — fire-and-forget
            final String snapDate = today;
            final int snapBalance = currentBalance;
            final int snapBudget = dailyBudget;
            final int snapFood = todayFoodCalories;
            final int snapKitty = kittyValue;
            final String snapZeroDate = estimatedZeroDate;
            new Thread(() -> {
                try {
                    SharedPreferences prefs2 = getSharedPreferences("Calorie_Countdown", 0);
                    String clientName = prefs2.getString("client_name", "Client");
                    if (clientName == null || clientName.isEmpty()) clientName = "Client";
                    SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(getApplicationContext());
                    apiClient.syncDayEnd(clientName, snapDate, snapBalance, snapBudget,
                            snapFood, snapKitty, snapZeroDate, new ApiResultCallback() {
                        @Override public void onSuccess(String response) {
                            android.util.Log.d("DayEnd2", "[syncDayEnd] OK date=" + snapDate + " balance=" + snapBalance);
                        }
                        @Override public void onFailure() {
                            android.util.Log.w("DayEnd2", "[syncDayEnd] Backend sync failed — local DB is authoritative.");
                        }
                    });
                } catch (Exception ex) {
                    android.util.Log.w("DayEnd2", "[syncDayEnd] Exception during sync: " + ex.getMessage());
                }
            }).start();

        } catch (Exception e) {
            android.util.Log.e("DayEnd2", "[Store_Dayend2] Exception: " + e.getMessage(), e);
        }
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

    private void Start_Heart_Rate_Activity() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.HeartRateActivity.class);
        this.startActivity(i);
    }

    private void Start_Recalibration() {
        Intent i = new Intent(CCD_GUI_CD_CIF1.this, ese.com.caloriecountdownappforandroidbrown.Recalibrate.class);
        this.startActivityForResult(i, REQUEST_CODE_RECALIBRATION);

    }

    private void Show_Estimated_Date_To_Zero() {
        final int currentBalance = Get_currentBalanceInt();

        if (currentBalance <= 0) {
            Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
            display_dialog_cif11.Set_mAppContext(this);
            display_dialog_cif11.Showing("Your Countdown Balance is already at zero or below. Congratulations!");
            return;
        }

        mBgExecutor.execute(() -> {
        try {
            // ── Fetch historical daily balances on background thread ──────────
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(getApplicationContext());
            java.util.List<Integer> history = db.getHistoricalDayEndBalances();

            int avgDailyReduction;
            String rateSource;
            int openingBalance = 0;
            boolean hasHistory = false;

            if (history != null && history.size() >= 2) {
                openingBalance = history.get(0);
                int latestHistorical = history.get(history.size() - 1);
                int totalDays = history.size() - 1;
                int totalReduction = openingBalance - latestHistorical;

                if (totalReduction > 0 && totalDays > 0) {
                    avgDailyReduction = Math.max(1, totalReduction / totalDays);
                    rateSource = "based on " + history.size() + " day snapshots";
                    hasHistory = true;
                } else {
                    // Balance not reducing — fall back to standard rate
                    avgDailyReduction = 250;
                    rateSource = "standard rate (balance not reducing in history)";
                }
            } else {
                avgDailyReduction = 250;
                rateSource = "standard rate (insufficient history)";
            }

            // ── Estimated Date to Zero ───────────────────────────────────────
            int daysToZero = (int) Math.ceil((double) currentBalance / avgDailyReduction);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, daysToZero);

            String[] monthNames = {"January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"};

            String estimatedDate = buildDateLabel(calendar, monthNames);

            // ── Progress Forecast ─────────────────────────────────────────────
            StringBuilder sb = new StringBuilder();

            sb.append("=== ESTIMATED DATE TO ZERO ===\n\n");
            sb.append("Current Balance:        ").append(String.format("%,d", currentBalance)).append(" pts\n");
            sb.append("Avg Daily Reduction:    ").append(String.format("%,d", avgDailyReduction))
              .append(" pts/day\n                        (").append(rateSource).append(")\n");
            sb.append("Days Remaining:         ").append(String.format("%,d", daysToZero)).append(" days\n");
            sb.append("Estimated Date:         ").append(estimatedDate).append("\n");

            if (hasHistory && openingBalance > 0) {
                sb.append("\n=== PROGRESS FORECAST ===\n\n");

                int totalProgress = openingBalance - currentBalance;
                double progressPct = (totalProgress * 100.0) / openingBalance;
                double remainingPct = 100.0 - progressPct;

                sb.append("Opening Balance:        ").append(String.format("%,d", openingBalance)).append(" pts\n");
                sb.append("Progress So Far:        ").append(String.format("%,d", totalProgress))
                  .append(" pts (").append(String.format("%.1f", progressPct)).append("%)\n");
                sb.append("Remaining:              ").append(String.format("%,d", currentBalance))
                  .append(" pts (").append(String.format("%.1f", remainingPct)).append("%)\n");
                sb.append("Avg Daily Progress:     ").append(String.format("%,d", avgDailyReduction)).append(" pts/day\n");

                String trend;
                if (avgDailyReduction > 250) {
                    trend = "Ahead of 250 pt/day target";
                } else if (avgDailyReduction == 250) {
                    trend = "On target (250 pts/day)";
                } else {
                    trend = "Below 250 pt/day target";
                }
                sb.append("Trend:                  ").append(trend).append("\n");
                sb.append("Forecast Completion:    ").append(estimatedDate).append("\n");
            }

            android.util.Log.d("EstimatedZero", "avg=" + avgDailyReduction + " days=" + daysToZero
                    + " date=" + estimatedDate + " historyCount=" + (history != null ? history.size() : 0));

            final String result = sb.toString();
            mKittyHandler.post(() -> {
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                display_dialog_cif11.Showing(result);
            });

        } catch (Exception e) {
            android.util.Log.e("EstimatedZero", "Error: " + e.getMessage(), e);
            // Never crash — fall back to minimal safe display
            mKittyHandler.post(() -> {
                try {
                    int balance = Get_currentBalanceInt();
                    int days = (balance > 0) ? (int) Math.ceil(balance / 250.0) : 0;
                    Display_Dialog_CIF11 fallback = new Display_Dialog_CIF11();
                    fallback.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                    fallback.Showing("Estimated Days to Zero: " + days + " days (standard rate)");
                } catch (Exception ignored) {}
            });
        }
        }); // end mBgExecutor.execute
    }

    /** Formats a Calendar as "1st January, 2027". Extracted to avoid duplication. */
    private String buildDateLabel(Calendar cal, String[] monthNames) {
        int day   = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year  = cal.get(Calendar.YEAR);
        String suffix;
        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1:  suffix = "st"; break;
                case 2:  suffix = "nd"; break;
                case 3:  suffix = "rd"; break;
                default: suffix = "th"; break;
            }
        }
        return day + suffix + " " + monthNames[month] + ", " + year;
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
        android.util.Log.d("CreditCalc", "[Countup] called with credit=" + credit);
        try {
            final TextView countdownbalance = (TextView) findViewById(R.id.textView);
            if (countdownbalance == null) {
                android.util.Log.e("CreditCalc", "[Countup] countdownbalance TextView is null — cannot update UI");
                return;
            }

            // Root cause fix: empty TextView causes Integer.parseInt("") → NFE in StringToInt
            String CountdownFigure = countdownbalance.getText().toString().trim();
            android.util.Log.d("CreditCalc", "[Countup] Balance before credit: '" + CountdownFigure + "'");
            if (CountdownFigure.isEmpty()) {
                CountdownFigure = "0";
                android.util.Log.w("CreditCalc", "[Countup] Balance was empty, treating as 0");
            }
            // Strip commas that may be present when comma-format display is active
            CountdownFigure = AppCustomization.stripCommas(CountdownFigure);

            int currentBalance = new RoundingCIF13().StringToInt(CountdownFigure);
            int newBalance = currentBalance + credit;
            android.util.Log.d("CreditCalc", "[Countup] currentBalance=" + currentBalance + ", credit=" + credit + ", newBalance=" + newBalance);

            String plainFigure = new RoundingCIF13().IntToString(newBalance);
            String numberFormat = getSharedPreferences("Calorie_Countdown", 0)
                    .getString(AppCustomization.KEY_NUMBER_FORMAT, AppCustomization.FORMAT_PLAIN);
            CountdownFigure = AppCustomization.formatBalance(plainFigure, numberFormat);
            countdownbalance.setText(CountdownFigure);
            // Also update mBalance_text so it stays in sync
            mBalance_text = CountdownFigure;
            // Store the pre-credit balance before the balance is updated.
            // DB: used by GetDayEndBalance() for Step Challenge calculations.
            // SharedPreferences: available to any component without a DB query.
            new SQLDatabase_Food_Items_CIF6(getApplicationContext()).setPreCreditDayEndBalance(currentBalance);
            getSharedPreferences("Calorie_Countdown", 0)
                    .edit()
                    .putInt("pre_credit_balance", currentBalance)
                    .apply();
            android.util.Log.d("CreditCalc", "[Countup] Stored pre-credit balance=" + currentBalance);
            long storeResult = StoreCountdownBalance(plainFigure);
            android.util.Log.d("CreditCalc", "[Countup] Balance stored, storeResult=" + storeResult + ", newBalanceText=" + CountdownFigure);
            // Step 3/3: All credit steps confirmed (food logged, balance updated, DB stored)
            // → trigger Kitty deployment exactly once via the guarded dispatcher.
            triggerKittyDeployment("Countup-before4pm");
        } catch (NumberFormatException e) {
            android.util.Log.e("CreditCalc", "[Countup] NFE: " + e.getMessage(), e);
            // Kitty must NOT fire on failure — do not call triggerKittyDeployment here.
        } catch (Exception e) {
            android.util.Log.e("CreditCalc", "[Countup] Exception: " + e.getMessage(), e);
            // Kitty must NOT fire on failure — do not call triggerKittyDeployment here.
        }
    }

    /**
     * Routes food credit to the next day's firstBrekkieBox when entered after 4pm.
     * The calories will be added to tomorrow's DayCiF1005 in the mDaysToZero collection.
     */
    private void routeCreditToNextDay(int credit) {
        if (mDaysToZero == null) {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            mDaysToZero = adapter.RetrievemForecast();
        }

        if (mDaysToZero != null) {
            // Find tomorrow's DayCiF1005
            DayCiF1005 today = mDaysToZero.getCurrentDayType1005();
            if (today != null) {
                java.time.LocalDateTime todayDate = today.getDay();
                java.time.LocalDateTime tomorrowDate = todayDate.plusDays(1);

                // Search for tomorrow in the collection
                for (Object obj : mDaysToZero.getNumberOFDaysToXero03FEB10()) {
                    DayCiF1005 day = (DayCiF1005) obj;
                    if (day != null &&
                        day.getDay().getYear() == tomorrowDate.getYear() &&
                        day.getDay().getMonthValue() == tomorrowDate.getMonthValue() &&
                        day.getDay().getDayOfMonth() == tomorrowDate.getDayOfMonth()) {

                        // Add credit to next day's firstBrekkieBox
                        Food_Item_CIF4 carryOverItem = new Food_Item_CIF4();
                        carryOverItem.Set_food_item_name("Carry Over (after 4pm)");
                        carryOverItem.Set_calorie_value(credit);
                        carryOverItem.Set_calories_per_100g((float) credit);
                        day.getFirstBrekkieBox().addFoodItem(carryOverItem);

                        Log.d("Countdown", "Credit of " + credit + " routed to tomorrow's firstBrekkieBox");

                        // Notify user
                        Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
                        dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                        dialog.Showing("It's past 4:00 PM (Credit Day End).\n\n" +
                                "Your food entry of " + credit + " calories has been added to tomorrow's first meal box.\n\n" +
                                "Focus on completing your Step Challenge for today!");
                        // Credit routed to next day successfully — trigger Kitty for today's status.
                        triggerKittyDeployment("routeCredit-after4pm-found");
                        return;
                    }
                }
            }
        }

        // Fallback: if no tomorrow found, just notify
        Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
        dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
        dialog.Showing("It's past 4:00 PM (Credit Day End).\n\n" +
                "Your food entry of " + credit + " calories will be carried over to tomorrow.\n\n" +
                "Focus on completing your Step Challenge for today!");
        // Fallback: tomorrow's day not found in collection — still trigger Kitty for today.
        triggerKittyDeployment("routeCredit-after4pm-fallback");
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
        android.util.Log.d("BalanceStore", "[StoreCountdownBalance] Storing balance: '" + Balance + "'");
        try {
            // Root cause fix: Integer.parseInt(Balance) throws NFE if Balance is null, empty, or
            // contains commas. Strip commas and guard against null/empty before parsing.
            String cleanBalance = (Balance != null) ? Balance.replace(",", "").trim() : "0";
            if (cleanBalance.isEmpty()) {
                cleanBalance = "0";
                android.util.Log.w("BalanceStore", "[StoreCountdownBalance] Balance was empty/null, defaulting to 0");
            }
            int balanceInt = Integer.parseInt(cleanBalance);
            MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            long dayEndResult = data_model_adapter.StoreDayEndBalance(balanceInt - 100);
            long storeResult = data_model_adapter.StoreBalance(cleanBalance);
            android.util.Log.d("BalanceStore", "[StoreCountdownBalance] stored balance=" + cleanBalance
                    + ", dayEnd=" + (balanceInt - 100) + ", storeResult=" + storeResult + ", dayEndResult=" + dayEndResult);

            // Record today's balance in daily_countdown_history for the progress graph.
            // UPSERT: updates today's row if it exists, inserts if first change of the day.
            // Previous days are never modified.
            new SQLDatabase_Food_Items_CIF6(getApplicationContext()).upsertDailyHistory(balanceInt);

            // Azure backend sync — fire-and-forget, never blocks UI, failures logged only
            final int finalBalanceInt = balanceInt;
            final String finalBalance = cleanBalance;
            new Thread(() -> {
                try {
                    SharedPreferences prefs = getSharedPreferences("Calorie_Countdown", 0);
                    String clientName = prefs.getString("client_name", "Client");
                    if (clientName == null || clientName.isEmpty()) clientName = "Client";
                    String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                            .format(new java.util.Date());
                    SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(getApplicationContext());
                    apiClient.syncBalance(clientName, today, finalBalanceInt, "BALANCE_UPDATE", new ApiResultCallback() {
                        @Override public void onSuccess(String response) {
                            android.util.Log.d("BalanceSync", "[syncBalance] OK balance=" + finalBalance);
                        }
                        @Override public void onFailure() {
                            android.util.Log.w("BalanceSync", "[syncBalance] Backend sync failed — local DB is authoritative.");
                        }
                    });
                } catch (Exception ex) {
                    android.util.Log.w("BalanceSync", "[syncBalance] Exception during sync: " + ex.getMessage());
                }
            }).start();

            return storeResult;
        } catch (NumberFormatException e) {
            android.util.Log.e("BalanceStore", "[StoreCountdownBalance] NFE parsing balance='" + Balance + "': " + e.getMessage(), e);
            return -1;
        } catch (Exception e) {
            android.util.Log.e("BalanceStore", "[StoreCountdownBalance] Exception: " + e.getMessage(), e);
            return -1;
        }
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
                Snackbar.make(view, "Update Countdown Balance", Snackbar.LENGTH_LONG)
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

        mStepsChallengeMain = (Button) findViewById(R.id.btnStepsChallengeMain);
        if (mStepsChallengeMain != null) {
            mStepsChallengeMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStepsChallengeFromMain();
                }
            });
        }


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

    /**
     * Public method to refresh the countdown balance TextView from the database.
     * Called by other activities (e.g. Debit_Activity_CiF003_fragment_box) after they
     * directly write to the DB (e.g. Drop 100 Points, BMR deduction) so the UI updates
     * immediately without waiting for onResume.
     */
    public void refreshBalanceDisplay() {
        refreshBalanceFromStorage();
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


    /**
     * Triggers Kitty deployment after a confirmed credit transaction.
     *
     * Call this ONLY after ALL three credit steps have succeeded:
     *   1. Food journal recorded to local DB (Record_Food_Journal / DB store)
     *   2. Balance updated on the UI TextView (Countup / routeCreditToNextDay)
     *   3. Balance persisted to SQLite (StoreCountdownBalance)
     *
     * Safety guarantees:
     *   - mKittyDeployed flag prevents a second call from the same credit cycle firing again.
     *   - Handler.post() keeps Kitty on the main thread — non-blocking to the caller.
     *   - mKittyDeployed is reset inside the finally block so the NEXT credit cycle is clean.
     *   - Failure catch-blocks in Countup() deliberately do NOT call this method.
     *
     * @param context short tag identifying the code path (used for logcat only).
     */
    private void triggerKittyDeployment(final String context) {
        if (mKittyDeployed) {
            android.util.Log.d("KittyFlow", "[KittyDeploy] Duplicate trigger suppressed — context: " + context);
            return;
        }
        mKittyDeployed = true;
        android.util.Log.d("KittyFlow", "[KittyDeploy] Deploying Kitty — context: " + context);
        mKittyHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Kitty();
                    android.util.Log.d("KittyFlow", "[KittyDeploy] Kitty deployment complete — context: " + context);
                } finally {
                    // Reset flag so the next legitimate credit operation can trigger Kitty once.
                    mKittyDeployed = false;
                }
            }
        });
    }

    private void Kitty() {

        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
        //int dayend = data_model_adapter.RetriveDayEnd();
        int dayend = 100;
        if(mDaysToZero == null)
        {
            mDaysToZero = data_model_adapter.RetrievemForecast();
            android.util.Log.d("Calorie Countdown app", "mDayToZero, retrieved, this is Contents:");
            if (mDaysToZero != null) {
                android.util.Log.d("Version  2.0.0", mDaysToZero.printDaysType());
            }
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
            final int finalCurrentBalance = currentbalance;
            mBgExecutor.execute(() -> {
                try {
                    SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(getApplicationContext());
                    int previousDayEnd = db.GetDayEndBalance();
                    int dayEndTarget = previousDayEnd - 250;
                    double stepsNumerator = 0.089;
                    double rawSteps = (finalCurrentBalance - dayEndTarget) / stepsNumerator;
                    int stepChallenge = (int) Math.ceil(rawSteps);
                    if (stepChallenge < 0) stepChallenge = 0;

                    StringBuilder msg = new StringBuilder("Client Step Challenge Calculation:\n\n");
                    msg.append("Current Balance:        ").append(String.format("%,d", finalCurrentBalance)).append("\n");
                    msg.append("Previous Day End:       ").append(String.format("%,d", previousDayEnd)).append("\n");
                    msg.append("Day End Target (- 250): ").append(String.format("%,d", dayEndTarget)).append("\n");
                    msg.append("Steps Numerator:        ").append(stepsNumerator).append("\n\n");
                    msg.append("(").append(String.format("%,d", finalCurrentBalance))
                       .append(" - ").append(String.format("%,d", dayEndTarget))
                       .append(") / ").append(stepsNumerator).append("\n\n");
                    msg.append("Client Step Challenge = ").append(String.format("%,d", stepChallenge)).append(" Steps");

                    if (stepChallenge >= 30_000) {
                        msg.append("\n\nYour Step Challenge has hit the 30,000 cap! Consider using the Accrual menu item to borrow calories from the previous day and reduce your challenge.");
                    }

                    msg.append("\n\nOnly dispose of the Dialog once you have performed it.");

                    final String message = msg.toString();
                    mKittyHandler.post(() -> {
                        Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                        display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                        display_dialog_cif11.Showing(message);
                    });
                } catch (Exception e) {
                    android.util.Log.e("KittyFlow", "Error building Steps Challenge message: " + e.getMessage(), e);
                }
            });
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
        int stepChallenge = GenerateStepsChallenge(in);
        String out = "Your Steps Challenge to successfully countdown your Balance by " + new RoundingCIF13().IntToString(kit + 250) + " points by Dayend (9:59 PM) is: " + new RoundingCIF13().IntToString(stepChallenge) + " Steps.";

        if (stepChallenge >= 30_000) {
            out += "\n\nYour Step Challenge has hit the 30,000 cap! Consider using the Accrual menu item to borrow calories from the previous day and reduce your challenge.";
        }

        out += "\n\nOnly dispose of the Dialog once you have performed it.";

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
        if (countdownbalance == null) {
            android.util.Log.e("BalanceRead", "[Get_currentBalanceInt] countdownbalance TextView is null, returning 0");
            return 0;
        }
        String balanceStr = countdownbalance.getText().toString().trim();
        // Root cause fix: empty TextView causes Integer.parseInt("") → NFE in StringToInt.
        // Guard here so all callers (Countdown, Countup, AddToBalance, etc.) never receive NFE.
        if (balanceStr.isEmpty()) {
            android.util.Log.w("BalanceRead", "[Get_currentBalanceInt] balance TextView is empty, returning 0");
            return 0;
        }
        // Strip commas that may be present when comma-format display is active
        balanceStr = AppCustomization.stripCommas(balanceStr);
        try {
            return new RoundingCIF13().StringToInt(balanceStr);
        } catch (NumberFormatException e) {
            android.util.Log.e("BalanceRead", "[Get_currentBalanceInt] NFE parsing '" + balanceStr + "': " + e.getMessage());
            return 0;
        }
    }

    public String Get_currentBalance() {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        if (countdownbalance == null) {
            android.util.Log.e("BalanceRead", "[Get_currentBalance] countdownbalance TextView is null, returning 0");
            return "0";
        }
        return AppCustomization.stripCommas(countdownbalance.getText().toString());
    }

    private void Set_currentBalance() {
        final TextView countdownbalance = (TextView) findViewById(R.id.textView);
        MIF4_Data_Model_Adapter model_adapter = new MIF4_Data_Model_Adapter(this);

        mBalance_text = model_adapter.RetrieveBalance();
        android.util.Log.d("BalanceLoad", "[Set_currentBalance] Raw DB balance: " + mBalance_text);

        // Root cause fix: null/empty balance from DB causes downstream NFE in Countdown/Countup
        // when code tries to parse the empty TextView string via StringToInt("").
        if (mBalance_text == null || mBalance_text.trim().isEmpty()) {
            mBalance_text = "0";
            android.util.Log.w("BalanceLoad", "[Set_currentBalance] DB balance null/empty, defaulting to 0");
        }

        model_adapter.StoreTargetWeightLossPounds("249");
        countdownbalance.setText(mBalance_text);
        android.util.Log.d("BalanceLoad", "[Set_currentBalance] UI balance set to: " + mBalance_text);
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
        android.util.Log.d("DebitCalc", "[Countdown] called with debit=" + debit);
        try {
            final TextView countdownbalance = (TextView) findViewById(R.id.textView);
            if (countdownbalance == null) {
                android.util.Log.e("DebitCalc", "[Countdown] countdownbalance TextView is null — cannot update UI");
                return;
            }

            // Root cause fix: empty TextView causes Integer.parseInt("") → NFE in StringToInt
            String CountdownFigure = countdownbalance.getText().toString().trim();
            android.util.Log.d("DebitCalc", "[Countdown] Balance before debit: '" + CountdownFigure + "'");
            if (CountdownFigure.isEmpty()) {
                CountdownFigure = "0";
                android.util.Log.w("DebitCalc", "[Countdown] Balance was empty, treating as 0");
            }
            // Strip commas that may be present when comma-format display is active
            CountdownFigure = AppCustomization.stripCommas(CountdownFigure);

            int currentBalance = new RoundingCIF13().StringToInt(CountdownFigure);
            int newBalance = currentBalance - debit;
            android.util.Log.d("DebitCalc", "[Countdown] currentBalance=" + currentBalance + ", debit=" + debit + ", newBalance=" + newBalance);

            String plainFigure = new RoundingCIF13().IntToString(newBalance);
            String numberFormat = getSharedPreferences("Calorie_Countdown", 0)
                    .getString(AppCustomization.KEY_NUMBER_FORMAT, AppCustomization.FORMAT_PLAIN);
            CountdownFigure = AppCustomization.formatBalance(plainFigure, numberFormat);
            mBalance_text = CountdownFigure;
            countdownbalance.setText(CountdownFigure);
            long storeResult = StoreCountdownBalance(plainFigure);
            android.util.Log.d("DebitCalc", "[Countdown] Balance updated to " + CountdownFigure + ", storeResult=" + storeResult);
            Kitty();
        } catch (NumberFormatException e) {
            android.util.Log.e("DebitCalc", "[Countdown] NFE: " + e.getMessage(), e);
        } catch (Exception e) {
            android.util.Log.e("DebitCalc", "[Countdown] Exception: " + e.getMessage(), e);
        }
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
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEW_DAY, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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

    public void AddToBalance(String input) {
        android.util.Log.d("AddToBalance", "[AddToBalance] Adding to balance, input='" + input + "'");
        try {
            // Root cause fix: Get_currentBalanceInt() now returns 0 safely on empty/null TextView
            // so NFE here is only from user-supplied 'input' being invalid — which we also guard.
            int currentBalance = Get_currentBalanceInt();

            // Guard against null/empty input before parsing
            String cleanInput = (input != null) ? AppCustomization.stripCommas(input.trim()) : "0";
            if (cleanInput.isEmpty()) {
                android.util.Log.w("AddToBalance", "[AddToBalance] input is empty, skipping");
                return;
            }
            int caloriesToAdd = Integer.parseInt(cleanInput);
            int newBalance = currentBalance + caloriesToAdd;
            android.util.Log.d("AddToBalance", "[AddToBalance] currentBalance=" + currentBalance
                    + ", caloriesToAdd=" + caloriesToAdd + ", newBalance=" + newBalance);

            // Update balance directly without triggering Kitty() dialog.
            // Kitty() tries to show an AlertDialog; when AddToBalance() is called from another
            // Activity (e.g. FoodNoteTableActivity) while this Activity is in the background,
            // AlertDialog.show() throws WindowManager$BadTokenException → crash.
            // The Kitty check fires naturally the next time this Activity resumes.
            String plainBalanceStr = String.valueOf(newBalance);
            String numberFormat = getSharedPreferences("Calorie_Countdown", 0)
                    .getString(AppCustomization.KEY_NUMBER_FORMAT, AppCustomization.FORMAT_PLAIN);
            String displayBalanceStr = AppCustomization.formatBalance(plainBalanceStr, numberFormat);
            mBalance_text = displayBalanceStr;
            final TextView countdownbalance = (TextView) findViewById(R.id.textView);
            if (countdownbalance != null) {
                countdownbalance.setText(displayBalanceStr);
            } else {
                android.util.Log.w("AddToBalance", "[AddToBalance] countdownbalance TextView is null");
            }
            // Store the pre-credit balance before the balance is updated.
            // DB: used by GetDayEndBalance() for Step Challenge calculations.
            // SharedPreferences: available to any component without a DB query.
            new SQLDatabase_Food_Items_CIF6(getApplicationContext()).setPreCreditDayEndBalance(currentBalance);
            getSharedPreferences("Calorie_Countdown", 0)
                    .edit()
                    .putInt("pre_credit_balance", currentBalance)
                    .apply();
            android.util.Log.d("AddToBalance", "[AddToBalance] Stored pre-credit balance=" + currentBalance);
            StoreCountdownBalance(plainBalanceStr);
            android.util.Log.d("AddToBalance", "[AddToBalance] Balance successfully updated to " + newBalance);
        } catch (NumberFormatException e) {
            android.util.Log.e("AddToBalance", "[AddToBalance] NFE: input='" + input + "', " + e.getMessage(), e);
            runOnUiThread(() -> android.widget.Toast.makeText(this,
                    "Error updating balance: invalid number format", android.widget.Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            android.util.Log.e("AddToBalance", "[AddToBalance] Exception: " + e.getMessage(), e);
            runOnUiThread(() -> android.widget.Toast.makeText(this,
                    "Error updating balance: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show());
        }
    }

    public void ChangeButtonColor() {

    }

    public void ChangeBackgroundImage() {}

    public void Recalibrate() {

    }


    public void InitializeCountdownToXeroDayType1004inCiF001asStatic(HealthProfileCiF3 IN)
    {
        mDaysToZero = new CountdownToZeroDayCiF1004(IN.getStartCountdown(), IN);
        // Also build the flat mDayZero list (Opening Balance / 25 = number of days)
        buildMDayZeroList(IN.getStartCountdown());
    }

    /**
     * Build the mDayZero flat list of DayCiF1005 objects.
     *
     * Rule (per client spec): numberOfDays = openingBalance / 25
     * Example: 100,000 / 25 = 4,000 days
     *
     * Each DayCiF1005 represents one future day from today, counting down by 25 points/day.
     * The last object's date is the estimated zero-balance date.
     *
     * @param openingBalance the countdown starting balance
     */
    public static void buildMDayZeroList(int openingBalance) {
        mDayZero = new java.util.ArrayList<>();
        if (openingBalance <= 0) {
            android.util.Log.w("mDayZero", "[buildMDayZeroList] openingBalance <= 0, list empty.");
            return;
        }

        int numberOfDays = openingBalance / 25; // 100000 / 25 = 4000 days
        android.util.Log.d("mDayZero", "[buildMDayZeroList] openingBalance=" + openingBalance
                + " → numberOfDays=" + numberOfDays);

        java.time.LocalDateTime date = java.time.LocalDateTime.now();
        int balance = openingBalance;

        for (int i = 0; i < numberOfDays; i++) {
            int endBalance = Math.max(0, balance - 25);
            DayCiF1005 day = new DayCiF1005(i, balance, openingBalance, endBalance, date, String.valueOf(balance - 25), "0");
            mDayZero.add(day);
            balance = endBalance;
            date = date.plusDays(1);
            if (balance == 0) break; // Stop early if we've reached zero
        }

        android.util.Log.d("mDayZero", "[buildMDayZeroList] Built " + mDayZero.size() + " DayCiF1005 entries."
                + " Last date: " + (mDayZero.isEmpty() ? "N/A" : mDayZero.get(mDayZero.size() - 1).getDay()));
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

        SharedPreferences pref = getApplicationContext().getSharedPreferences(  "Calorie_Countdown", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();

        editor.putString("Target_Weight", Input1);
        editor.commit();

    }

    public void Store_Gender_Type(String genderType) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();

        editor.putString("Gender_Type", genderType);
        editor.commit();
    }

    public String Retrieve_Gender_Type() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Calorie_Countdown", 0);
        return pref.getString("Gender_Type", null);
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

    /**
     * Generates a Step Challenge based on the calories that need to be burned.
     * Adds 250 bonus points to the target, then converts calories to steps.
     * Conversion: 1 step = 0.089 calories, so steps = calories / 0.089
     * Capped at 30,000 steps maximum.
     *
     * @param caloriesOverBudget the calories over budget that need to be burned
     * @return the number of steps for the challenge (max 30,000)
     */
    private int GenerateStepsChallenge(int caloriesOverBudget) {
        // Add 250 bonus points to the target
        int targetCalories = caloriesOverBudget + 250;

        // Convert calories to steps: steps = calories / 0.089
        int steps = (int) (targetCalories / 0.089);

        // Cap at 30,000 steps
        if (steps > 30_000) {
            steps = 30_000;
        }

        return steps;
    }

    private void showConversionInputDialog(String title, String message, String conversionType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("Convert", (dialog, which) -> {
            String inputText = input.getText().toString().trim();
            if (!inputText.isEmpty()) {
                try {
                    double value = Double.parseDouble(inputText);
                    String result = performConversion(value, conversionType);
                    showConversionResult(result);
                } catch (NumberFormatException e) {
                    showConversionResult("Invalid number entered. Please enter a valid number.");
                }
            } else {
                showConversionResult("Please enter a value to convert.");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private String performConversion(double value, String conversionType) {
        double result;
        String resultText;

        switch (conversionType) {
            case "stones_to_kg":
                // 1 Stone = 6.35029 Kilograms
                result = value * 6.35029;
                resultText = String.format("%.2f Stones = %.2f Kilograms", value, result);
                break;
            case "pounds_to_kg":
                // 1 Pound = 0.453592 Kilograms
                result = value * 0.453592;
                resultText = String.format("%.2f Pounds = %.2f Kilograms", value, result);
                break;
            case "kg_to_stones":
                // 1 Kilogram = 0.157473 Stones
                result = value * 0.157473;
                resultText = String.format("%.2f Kilograms = %.2f Stones", value, result);
                break;
            case "kg_to_pounds":
                // 1 Kilogram = 2.20462 Pounds
                result = value * 2.20462;
                resultText = String.format("%.2f Kilograms = %.2f Pounds", value, result);
                break;
            default:
                resultText = "Unknown conversion type.";
        }

        return resultText;
    }

    private void showConversionResult(String result) {
        Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
        display_dialog_cif11.Set_mAppContext(CCD_GUI_CD_CIF1.this);
        display_dialog_cif11.Showing(result);
    }

    /**
     * Shows the Accrual dialog that allows the user to borrow calories from
     * the previous day and shift them to the next day's DayCiF1005.
     * This lightens the current day's load and reduces the Step Challenge.
     */
    private void showAccrualDialog() {
        if (mDaysToZero == null) {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            mDaysToZero = adapter.RetrievemForecast();
        }

        if (mDaysToZero == null) {
            Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
            dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            dialog.Showing("Accrual is not available. Please start your weight loss journey first.");
            return;
        }

        DayCiF1005 today = mDaysToZero.getCurrentDayType1005();
        if (today == null) {
            Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
            dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            dialog.Showing("Could not find today's day record for Accrual.");
            return;
        }

        // Calculate what the current step challenge would be
        int currentBalance = Get_currentBalanceInt();
        int dayend = today.getBudgetedDayEndBalanceForThisDay();
        int deficit = currentBalance - dayend;
        int currentSteps = (deficit > 0) ? GenerateStepsChallenge(deficit) : 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Accrual - Borrow Calories");

        View dialogView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null);
        // Use a simple EditText for input
        final EditText input = new EditText(this);
        input.setHint("Enter calories to accrue to tomorrow");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        String message = "Current Step Challenge: " + new RoundingCIF13().IntToString(currentSteps) + " steps";
        if (currentSteps >= 30_000) {
            message += " (CAPPED at 30,000!)";
        }
        message += "\n\nEnter the number of calories you want to borrow from today and move to tomorrow. " +
                "This will reduce your Step Challenge for today but you must plan tomorrow's meals carefully.";
        builder.setMessage(message);

        builder.setPositiveButton("Accrue", (dialog, which) -> {
            String inputText = input.getText().toString().trim();
            if (inputText.isEmpty()) return;

            try {
                int accrualAmount = Integer.parseInt(inputText);
                if (accrualAmount <= 0) return;

                performAccrual(today, accrualAmount);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid accrual amount: " + inputText);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * Performs the accrual: moves calories from today to tomorrow's DayCiF1005.
     * Reduces today's load and the Step Challenge, but adds to tomorrow's burden.
     */
    private void performAccrual(DayCiF1005 today, int accrualAmount) {
        java.time.LocalDateTime todayDate = today.getDay();
        java.time.LocalDateTime tomorrowDate = todayDate.plusDays(1);

        DayCiF1005 tomorrow = null;
        for (Object obj : mDaysToZero.getNumberOFDaysToXero03FEB10()) {
            DayCiF1005 day = (DayCiF1005) obj;
            if (day != null &&
                day.getDay().getYear() == tomorrowDate.getYear() &&
                day.getDay().getMonthValue() == tomorrowDate.getMonthValue() &&
                day.getDay().getDayOfMonth() == tomorrowDate.getDayOfMonth()) {
                tomorrow = day;
                break;
            }
        }

        if (tomorrow == null) {
            Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
            dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
            dialog.Showing("Could not find tomorrow's day record. Accrual failed.");
            return;
        }

        // Move calories from today's budget to tomorrow
        int todayBudget = today.getBudgetedDayEndBalanceForThisDay();
        today.setBudgetedDayEndBalanceForThisDay(todayBudget + accrualAmount);

        int tomorrowBudget = tomorrow.getBudgetedDayEndBalanceForThisDay();
        tomorrow.setBudgetedDayEndBalanceForThisDay(tomorrowBudget - accrualAmount);

        // Add accrual as a food item to tomorrow's firstBrekkieBox for tracking
        Food_Item_CIF4 accrualItem = new Food_Item_CIF4();
        accrualItem.Set_food_item_name("Accrual from previous day");
        accrualItem.Set_calorie_value(accrualAmount);
        accrualItem.Set_calories_per_100g((float) accrualAmount);
        tomorrow.getFirstBrekkieBox().addFoodItem(accrualItem);

        // Recalculate step challenge
        int currentBalance = Get_currentBalanceInt();
        int newDayend = today.getBudgetedDayEndBalanceForThisDay();
        int newDeficit = currentBalance - newDayend;
        int newSteps = (newDeficit > 0) ? GenerateStepsChallenge(newDeficit) : 0;

        Log.d("Accrual", "Accrued " + accrualAmount + " calories to tomorrow. New step challenge: " + newSteps);

        // Show result
        String resultMessage = "Accrual Successful!\n\n" +
                "Calories accrued to tomorrow: " + accrualAmount + "\n" +
                "New Step Challenge: " + new RoundingCIF13().IntToString(newSteps) + " steps\n\n" +
                "Remember: Tomorrow you must carefully plan your meals to burn the accrued " +
                accrualAmount + " calories plus your regular target. Consider fasting or sticking to a planned diet.";

        Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
        dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
        dialog.Showing(resultMessage);

        Refresh();
    }

    /**
     * Marks the current day's debitUpdatePerformed as true in the mDaysToZero collection.
     */
    private void markDebitUpdatePerformed() {
        if (mDaysToZero == null) {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            mDaysToZero = adapter.RetrievemForecast();
        }

        if (mDaysToZero != null) {
            DayCiF1005 today = mDaysToZero.getCurrentDayType1005();
            if (today != null) {
                today.setDebitUpdatePerformed(true);
                Log.d("DebitUpdate", "Debit update performed for today: " + today.getDay());
            }
        }
    }

    /**
     * Shows the Countdown Report after a debit update.
     * Compares previous balance with new balance to determine if user counted down or up.
     *
     * @param previousBalance the balance before the debit was applied
     * @param newBalance      the balance after the debit was applied
     */
    /**
     * Steps Challenge — calculates the step target based on:
     *   (Current Balance - Day End Target - BMR) / Steps Numerator
     * where Day End Target = Previous Day End Balance - 250
     * Steps Numerator = 0.089 (for 265 lbs client)
     * BMR = 2500 (male) or 2000 (female)
     */
    private void showStepsChallengeFromMain() {
        final String todayDate = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                .format(new java.util.Date());
        final int currentBalance = Get_currentBalanceInt();
        final SharedPreferences prefs = getSharedPreferences("Calorie_Countdown", 0);

        mBgExecutor.execute(() -> {
            try {
                SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(getApplicationContext());

                final boolean alreadyProcessed = db.isAlreadyProcessedForDate(todayDate);
                final int previousDayEnd = db.GetDayEndBalance();

                mKittyHandler.post(() -> {
                    if (!alreadyProcessed) {
                        new android.app.AlertDialog.Builder(CCD_GUI_CD_CIF1.this)
                                .setTitle("Steps Challenge")
                                .setMessage("Please complete today's 4PM Food Notes processing before calculating the Steps Challenge.")
                                .setPositiveButton("OK", (d, w) -> d.dismiss())
                                .show();
                        return;
                    }

                    // BMR: gender-based daily calorie budget (2500 male / 2000 female)
                    String gender = prefs.getString("user_gender", "male");
                    int bmr = "female".equalsIgnoreCase(gender) ? 2000 : 2500;
                    double stepsNumerator = 0.089;

                    // Today's Countdown = Current Balance - Previous Day End Balance - 250 - BMR
                    int todayCountdown = currentBalance - previousDayEnd - 250 - bmr;

                    // Steps = Today's Countdown / Steps Numerator (conversion constant unchanged)
                    double rawSteps = todayCountdown / stepsNumerator;
                    int stepChallenge = (int) Math.ceil(rawSteps);
                    if (stepChallenge < 0) stepChallenge = 0;

                    // Save to SQLite — records the latest calculation (fire-and-forget)
                    final int finalStepChallenge = stepChallenge;
                    final int finalTodayCountdown = todayCountdown;
                    final int finalBmr = bmr;
                    mBgExecutor.execute(() ->
                            new SQLDatabase_Food_Items_CIF6(getApplicationContext())
                                    .saveStepsChallenge(todayDate, currentBalance, previousDayEnd,
                                            finalTodayCountdown, finalBmr, finalStepChallenge));

                    String clientName = "Client";
                    String name = prefs.getString("client_name", null);
                    if (name != null && !name.isEmpty()) clientName = name;

                    String message = clientName + " Step Challenge Calculation:\n\n"
                            + "Current Balance:    " + String.format("%,d", currentBalance) + " Cr\n"
                            + "Previous Day End:   " + String.format("%,d", previousDayEnd) + " Cr\n"
                            + "BMR (" + gender + "):      " + String.format("%,d", bmr) + " cal\n"
                            + "Today's Countdown:  " + String.format("%,d", finalTodayCountdown) + " Cr\n"
                            + "Steps Numerator:    " + stepsNumerator + "\n\n"
                            + "(" + String.format("%,d", currentBalance)
                            + " - " + String.format("%,d", previousDayEnd)
                            + " - 250 - " + String.format("%,d", bmr)
                            + ") / " + stepsNumerator + "\n\n"
                            + clientName + " Step Challenge = " + String.format("%,d", finalStepChallenge) + " Steps";

                    Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
                    dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                    dialog.Showing(message);

                    android.util.Log.d("StepsChallenge", "currentBalance=" + currentBalance
                            + " previousDayEnd=" + previousDayEnd + " bmr=" + finalBmr
                            + " todayCountdown=" + finalTodayCountdown
                            + " stepChallenge=" + finalStepChallenge);
                });

            } catch (Exception e) {
                android.util.Log.e("StepsChallenge", "Error: " + e.getMessage(), e);
                mKittyHandler.post(() -> {
                    Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
                    dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
                    dialog.Showing("Steps Challenge error: " + e.getMessage());
                });
            }
        });
    }

    /**
     * Shows the full Countdown Report after a debit update.
     * Format:
     *   Previous Balance / New Balance / Net Gain or Loss
     *   Kitty Value (gender-based budget - today's food calories)
     *   Estimated Zero Date
     *   Encouragement text
     */
    private void showCountdownReport(int previousBalance, int newBalance) {
        // Get client name from preferences
        String clientName = "Client";
        SharedPreferences pref = getSharedPreferences("Calorie_Countdown", 0);
        String name = pref.getString("client_name", null);
        if (name != null && !name.isEmpty()) {
            clientName = name;
        }

        // Today's date
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String dateStr = now.getDayOfMonth() + " " +
                now.getMonth().toString().substring(0, 1) +
                now.getMonth().toString().substring(1).toLowerCase() +
                " " + now.getYear();

        // Kitty value: gender-based budget - food calories consumed today
        String gender = pref.getString("user_gender", "male");
        int dailyBudget = "female".equalsIgnoreCase(gender) ? 2000 : 2500;
        int todayFoodCals = 0;
        try {
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(this);
            todayFoodCals = db.getTotalFoodNoteCaloriesToday();
        } catch (Exception e) {
            android.util.Log.e("ReportKitty", "Failed to get today food cals: " + e.getMessage());
        }
        int kittyValue = dailyBudget - todayFoodCals;

        // Estimated zero date at 250-point/day countdown rate
        int daysToZero = (newBalance > 0) ? (int) Math.ceil((double) newBalance / 250.0) : 0;
        Calendar zeroCal = Calendar.getInstance();
        zeroCal.add(Calendar.DAY_OF_YEAR, daysToZero);
        String estimatedZeroDate = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                .format(zeroCal.getTime());

        int difference = previousBalance - newBalance;

        StringBuilder report = new StringBuilder();
        report.append("═══ Countdown Report ═══\n\n");
        report.append("Date: ").append(dateStr).append("\n\n");
        report.append("Previous Balance:  ").append(new RoundingCIF13().IntToString(previousBalance)).append(" Cr\n");
        report.append("New Balance:       ").append(new RoundingCIF13().IntToString(newBalance)).append(" Cr\n");

        if (difference > 0) {
            report.append("Net Countdown:     -").append(new RoundingCIF13().IntToString(difference)).append(" pts \uD83D\uDD3B\n");
        } else if (difference == 0) {
            report.append("Net Countdown:     0 pts (held ground)\n");
        } else {
            report.append("Net Countup:       +").append(new RoundingCIF13().IntToString(Math.abs(difference))).append(" pts \uD83D\uDD3A\n");
        }

        report.append("\n── Kitty Report ──\n");
        report.append("Daily Budget:      ").append(new RoundingCIF13().IntToString(dailyBudget)).append(" cal\n");
        report.append("Food Consumed:     ").append(new RoundingCIF13().IntToString(todayFoodCals)).append(" cal\n");
        if (kittyValue >= 0) {
            report.append("Kitty Remaining:   ").append(new RoundingCIF13().IntToString(kittyValue)).append(" cal left\n");
        } else {
            report.append("Kitty Deficit:     ").append(new RoundingCIF13().IntToString(Math.abs(kittyValue))).append(" cal over budget\n");
        }

        report.append("\n── Zero Date ──\n");
        report.append("Estimated Zero:    ").append(estimatedZeroDate);
        report.append(" (").append(daysToZero).append(" days)\n");

        report.append("\n");
        if (difference > 0) {
            report.append("Well done, ").append(clientName).append("! Great countdown today! Keep up the momentum!");
        } else if (difference == 0) {
            report.append("You held your ground today, ").append(clientName).append(". Tomorrow is a new opportunity!");
        } else {
            report.append("Don't be discouraged, ").append(clientName)
                    .append("! Tomorrow is a brand new day — it's a marathon, keep going! \uD83D\uDE4C");
        }

        Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
        dialog.Set_mAppContext(CCD_GUI_CD_CIF1.this);
        dialog.Showing(report.toString());
    }

    // ── Variables Menu handlers ────────────────────────────────────────────────
    // Add new variable dialogs below this comment as the menu grows.

    private void showBalanceLastUpdatedDialog() {
        String lastUpdated;
        try {
            lastUpdated = new SQLDatabase_Food_Items_CIF6(this).getBalanceLastUpdated();
        } catch (Exception e) {
            android.util.Log.e("Variables", "getBalanceLastUpdated error: " + e.getMessage(), e);
            lastUpdated = null;
        }

        String message = (lastUpdated != null)
                ? "Main Countdown Balance was last updated on:\n\n" + lastUpdated
                : "No balance record found.\n\nThe balance has not been updated yet.";

        new android.app.AlertDialog.Builder(this)
                .setTitle("Balance Last Updated")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Checks if the previous day's debit update was performed.
     * Returns true if it was performed (or no previous day exists), false if pending.
     */
    public boolean isPreviousDayDebitComplete() {
        if (mDaysToZero == null) {
            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            mDaysToZero = adapter.RetrievemForecast();
        }

        if (mDaysToZero == null) return true; // No data, allow proceeding

        DayCiF1005 today = mDaysToZero.getCurrentDayType1005();
        if (today == null) return true;

        java.time.LocalDateTime todayDate = today.getDay();
        java.time.LocalDateTime yesterdayDate = todayDate.minusDays(1);

        for (Object obj : mDaysToZero.getNumberOFDaysToXero03FEB10()) {
            DayCiF1005 day = (DayCiF1005) obj;
            if (day != null &&
                day.getDay().getYear() == yesterdayDate.getYear() &&
                day.getDay().getMonthValue() == yesterdayDate.getMonthValue() &&
                day.getDay().getDayOfMonth() == yesterdayDate.getDayOfMonth()) {
                return day.getDebitUpdatePerformed();
            }
        }

        return true; // No previous day found, allow proceeding
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear static reference so GC can reclaim this Activity after it is destroyed.
        instance = null;
        // Shut down background DB executor.
        if (mBgExecutor != null) {
            mBgExecutor.shutdownNow();
        }
    }

}

