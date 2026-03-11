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
}