package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

public class Debit_Activity_CiF003_fragment_box extends AppCompatActivity implements SensorEventListener {

    public static final String TOTAL_DEBIT_VALUE = "Total Debit Countdown Value";
    public static int REQUEST_CODE_DEBIT_MAN = 1;

    private Button mDebit;
    private Button mSteps;
    private Button mCancel;
    private Button mStepsManaul;
    private Button mOpenStepApp;
    private Button mDrop100Points;
    private Button mBMR;
    private Fitness_Item_CIF5 mCountdown;
    private int mStep_Count = 0;

    private SensorManager sensorManager;
    private Sensor mCount_Sensor;
    private boolean running = false;
    private RoundingCIF13 rounder = new RoundingCIF13();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit___ci_f003_fragment_box);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name3);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                Update_with_Step_Count();

            }
        });

        mDebit = (Button) findViewById(R.id.button12);
        mSteps = (Button) findViewById(R.id.button123);
        mCancel = (Button) findViewById(R.id.button13);
        mStepsManaul = (Button) findViewById(R.id.button124);
        mOpenStepApp = (Button) findViewById(R.id.button_open_step_app);
        //In one of these Logic if time is beyone 4pm (0nce)
        //execute dayend on CiF001 current balance
        //mBalance goes in the right DayType0014 for current day
        //it also gets registered as dayend var and startday var for
        //next day and can be stored and flushed to SQLite

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaselpop();
            }
        });

        mDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText63 = (EditText) findViewById(R.id.edit_text63);
                EditText editText64 = (EditText) findViewById(R.id.edit_text64);
                Spinner spinner = (Spinner) findViewById(R.id.spincity);
                mCountdown = new Fitness_Item_CIF5();
                mCountdown.setmUserWeightlbs(new RoundingCIF13().StringToFloat(editText63.getText().toString()));
                mCountdown.setmMinutesPerformed((int) (new RoundingCIF13().StringToFloat(editText64.getText().toString())));
                String s = spinner.getSelectedItem().toString();
                mCountdown.ConvertSpinnerItem(s);

                //Algorithm Engineering
                //Insert Implementation Code Logic to Store Day End2 Balance here, if past 16:00
                //remember to implement those double try bug fixes.
                StoreDayEnd2(CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt());
                BackToParent(GetCountdownDebit(mCountdown));
            }
        });

        mSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Steps_Activity_CiF1003_fragment_box_Class get_Steps = new Steps_Activity_CiF1003_fragment_box_Class();
                Update_with_Step_Count();
            }
        });

        mStepsManaul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchManual();
            }
        });

        mOpenStepApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStepApp(view.getContext());
            }
        });

        // Drop 100 Points button - shows workout video and deducts points on completion
        mDrop100Points = (Button) findViewById(R.id.btnDrop100Points);
        mDrop100Points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWorkoutDialog();
            }
        });

        // BMR button - drops points based on gender (Male: 2500, Female: 2000)
        mBMR = (Button) findViewById(R.id.btnBMR);
        mBMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBMRDialog();
            }
        });

        android.util.Log.d("STEPS", "Above Sensor Manager");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        android.util.Log.d("STEPS", "Below Sensor Manager");

        running = true;

        mCount_Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (mCount_Sensor != null) {
            sensorManager.registerListener(this, mCount_Sensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not Found :(", Toast.LENGTH_SHORT).show();
        }


    }

    private void openStepApp(Context context) {
        String[] stepAppPackages = {
                "com.google.android.apps.fitness", // Google Fit
                "com.sec.android.app.shealth",     // Samsung Health
                "com.xiaomi.hm.health"             // Mi Fit
        };

        boolean isAppOpened = false;

        for (String pkg : stepAppPackages) {
            try {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(pkg);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                    isAppOpened = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isAppOpened) {
            // If no step app found, open Play Store search
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://search?q=step+counter"));
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                // Fallback if Play Store not available
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/search?q=step+counter"));
                context.startActivity(intent);
            }
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_debit__activity__cif13, container, false);
        return v;
    }

    private int GetCountdownDebit(Fitness_Item_CIF5 fizz) {
        //Alogrithm Engineering -> Android : GetCountdownDebit() (Track, hence eta date Transition Date = 5pm December 12, 2011 (strictly on Track)
        //Step 1. return weight * min * fizz data value.
        int debit = fizz.CalculateCountdown();
        SummaryBoxCIF12 summy = SummaryBoxCIF12.get(getApplicationContext());
        summy.Set_mFitnessItems(fizz);
        return debit;
    }

    private void Update_with_Step_Count() {
        android.util.Log.d("We are in Update with Steps", "Position 1");
        //Steps_Activity_CiF1003_fragment_box_Class mStep_Count = new Steps_Activity_CiF1003_fragment_box_Class();
        android.util.Log.d("We are in Update with Steps", "Position 2");

        Output_Step_Count();
        int Steps = Get_Step_Count();
        android.util.Log.d("THIS IS STEP COUNT...", new RoundingCIF13().IntToString(Steps));
        Fitness_Item_CIF5 fizz = new Fitness_Item_CIF5();
        fizz.setmMinutesPerformed(new RoundingCIF13().DoubleToInt(Steps * 0.089));
        fizz.setmNameOfActivity("Steps");
        fizz.setmCaloriesBurntPerMinute(7);
        fizz.setmCalorie_Debit_Value(new RoundingCIF13().DoubleToInt(Steps * 0.089));
        SummaryBoxCIF12 summy = SummaryBoxCIF12.get(getApplicationContext());
        summy.Set_mFitnessItems(fizz);
        BackToParent(fizz.getmCalorie_Debit_Value());
    }

    private void BackToParent(int debit) {
        //return intent call with intent packed with value of Debit or Credit and Summary String as well as summarybox ready
        Intent i2 = new Intent();
        i2.putExtra(TOTAL_DEBIT_VALUE, debit);
        //i2.putExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT, summary_box.GetDebitSummaryString());
        setResult(RESULT_OK, i2);
        finish();

    }


    public int Output_Step_Count() {

        //android.util.Log.d("STEPS", "Above Sensor Manager");
        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // android.util.Log.d("STEPS", "Below Sensor Manager");
        // mStep_Count = 6847;
        return mStep_Count;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (running == false) {
            running = true;

            mCount_Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            if (mCount_Sensor != null) {
                sensorManager.registerListener(this, mCount_Sensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                Toast.makeText(this, "Sensor not Found :(", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if (running == false) {
            //sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            mStep_Count = rounder.FloatToInt(event.values[0]);
            android.util.Log.d("Step_Count Value", new RoundingCIF13().IntToString(mStep_Count));
        } else {
            Toast.makeText(this, "Sensor not picked up :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor m, int accuracy) {

    }

    public int Get_Step_Count() {
        return mStep_Count;
    }

    private void launchManual() {
        Intent i = new Intent(Debit_Activity_CiF003_fragment_box.this, Debit_Steps.class);
        startActivityForResult(i, REQUEST_CODE_DEBIT_MAN);

    }

    private void weaselpop() {
        setResult(1);
        finish();
    }

    private void StoreDayEnd2(int fourPMDayEndBalance) {
        //Algorithm Engineering Noir:
        //Insert Implementation Code Logic to Store Day End2 Balance here, if past 16:00
        //remember to implement those double try bug fixes.

    }

    private void showWorkoutDialog() {
        final String workoutUrl = "https://www.facebook.com/share/r/19ZNuALHt4/?mibextid=wwXIfr";

        // Show choice dialog - open in browser or WebView
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(this);
        choiceBuilder.setTitle("Workout Video");
        choiceBuilder.setMessage("Watch the workout video to drop 100 points from your balance.\n\nHow would you like to open the video?");

        // Open in external browser
        choiceBuilder.setPositiveButton("Open in Browser", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                // Open URL in external browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(workoutUrl));
                startActivity(browserIntent);

                // Show confirmation dialog after returning
                showWorkoutCompletionDialog();
            }
        });

        // Cancel
        choiceBuilder.setNegativeButton("Cancel", null);

        choiceBuilder.show();
    }

    private void showWorkoutCompletionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Workout Complete?");
        builder.setMessage("Did you complete the workout?");

        builder.setPositiveButton("Yes, Done!", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                // Subtract 100 points from the main balance
                deduct100PointsFromBalance();
            }
        });

        builder.setNegativeButton("No, Cancel", null);
        builder.show();
    }

    private void deduct100PointsFromBalance() {
        try {
            // Get current balance from data adapter
            MIF4_Data_Model_Adapter dataAdapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            String currentBalanceStr = dataAdapter.RetrieveBalance();

            android.util.Log.d("Workout", "Current balance retrieved: " + currentBalanceStr);

            // Parse current balance (handle commas if present)
            int currentBalance = 0;
            if (currentBalanceStr != null && !currentBalanceStr.isEmpty()) {
                // Remove commas if present
                currentBalanceStr = currentBalanceStr.replace(",", "");
                currentBalance = Integer.parseInt(currentBalanceStr);
            }

            // Subtract 100 points
            int newBalance = currentBalance - 100;

            android.util.Log.d("Workout", "New balance after deduction: " + newBalance);

            // Store the new balance
            String newBalanceStr = String.valueOf(newBalance);
            dataAdapter.StoreBalance(newBalanceStr);

            // Also update DayEnd balance
            dataAdapter.StoreDayEndBalance(newBalance - 100);

            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "100 points dropped! Balance: " + currentBalance + " -> " + newBalance,
                Toast.LENGTH_SHORT).show();

            android.util.Log.d("Workout", "100 points deducted. Old: " + currentBalance + ", New: " + newBalance);

        } catch (Exception e) {
            android.util.Log.e("Workout", "Error deducting points: " + e.getMessage(), e);
            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "Error updating balance: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showBMRDialog() {
        // Get gender from SharedPreferences
        String genderType = CCD_GUI_CD_CIF1.instance.Retrieve_Gender_Type();

        // Determine points to drop based on gender
        // Male: 2500 points, Female: 2000 points, No gender (default): 2000 points
        int pointsToDrop;
        String genderDisplay;

        if (genderType != null && genderType.equals("Male")) {
            pointsToDrop = 2500;
            genderDisplay = "Male";
        } else {
            // Female or no gender set - default to 2000 points
            pointsToDrop = 2000;
            genderDisplay = (genderType != null) ? genderType : "Not set (defaulting to Female)";
        }

        // Show confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BMR Deduction");
        builder.setMessage("Gender: " + genderDisplay + "\n\nThis will drop " + pointsToDrop + " points from your balance.\n\nDo you want to proceed?");

        final int finalPointsToDrop = pointsToDrop;
        builder.setPositiveButton("Yes, Drop Points", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                deductBMRPointsFromBalance(finalPointsToDrop);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deductBMRPointsFromBalance(int pointsToDrop) {
        try {
            // Get current balance from data adapter
            MIF4_Data_Model_Adapter dataAdapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            String currentBalanceStr = dataAdapter.RetrieveBalance();

            android.util.Log.d("BMR", "Current balance retrieved: " + currentBalanceStr);

            // Parse current balance (handle commas if present)
            int currentBalance = 0;
            if (currentBalanceStr != null && !currentBalanceStr.isEmpty()) {
                // Remove commas if present
                currentBalanceStr = currentBalanceStr.replace(",", "");
                currentBalance = Integer.parseInt(currentBalanceStr);
            }

            // Subtract BMR points
            int newBalance = currentBalance - pointsToDrop;

            android.util.Log.d("BMR", "New balance after deduction: " + newBalance);

            // Store the new balance
            String newBalanceStr = String.valueOf(newBalance);
            dataAdapter.StoreBalance(newBalanceStr);

            // Also update DayEnd balance
            dataAdapter.StoreDayEndBalance(newBalance - 100);

            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                pointsToDrop + " BMR points dropped! Balance: " + currentBalance + " -> " + newBalance,
                Toast.LENGTH_SHORT).show();

            android.util.Log.d("BMR", pointsToDrop + " points deducted. Old: " + currentBalance + ", New: " + newBalance);

        } catch (Exception e) {
            android.util.Log.e("BMR", "Error deducting BMR points: " + e.getMessage(), e);
            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "Error updating balance: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}