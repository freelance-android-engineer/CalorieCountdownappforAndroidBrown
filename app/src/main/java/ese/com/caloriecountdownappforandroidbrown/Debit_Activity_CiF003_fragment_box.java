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
    private Button mMidnightScrape;
    private Button mStepsChallenge;
    private Button mAddNewDebitItem;
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
                try {
                    EditText editText63 = (EditText) findViewById(R.id.edit_text63);
                    EditText editText64 = (EditText) findViewById(R.id.edit_text64);
                    Spinner spinner = (Spinner) findViewById(R.id.spincity);

                    // Guard: weight field
                    if (editText63 == null || editText63.getText().toString().trim().isEmpty()) {
                        Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                                "Please enter your weight in lbs.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Guard: minutes/reps field
                    if (editText64 == null || editText64.getText().toString().trim().isEmpty()) {
                        Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                                "Please enter minutes or reps performed.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Guard: spinner selection
                    if (spinner == null || spinner.getSelectedItem() == null) {
                        Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                                "Please select an activity.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mCountdown = new Fitness_Item_CIF5();
                    mCountdown.setmUserWeightlbs(new RoundingCIF13().StringToFloat(editText63.getText().toString().trim()));
                    mCountdown.setmMinutesPerformed((int) (new RoundingCIF13().StringToFloat(editText64.getText().toString().trim())));
                    String s = spinner.getSelectedItem().toString();
                    mCountdown.ConvertSpinnerItem(s);

                    StoreDayEnd2(CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt());
                    BackToParent(GetCountdownDebit(mCountdown));

                } catch (NumberFormatException e) {
                    android.util.Log.e("DebitButton", "NFE parsing debit fields: " + e.getMessage(), e);
                    Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                            "Invalid number entered. Please check weight and minutes fields.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    android.util.Log.e("DebitButton", "Debit button exception: " + e.getMessage(), e);
                    Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                            "Error processing debit: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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

        // Midnight Scrape button - reconciles recorded vs actual step count
        mMidnightScrape = (Button) findViewById(R.id.btnMidnightScrape);
        mMidnightScrape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMidnightScrapeDialog();
            }
        });

        // Steps Challenge button - calculates step target based on balance, day-end, and BMR
        mStepsChallenge = (Button) findViewById(R.id.btnStepsChallenge);
        mStepsChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStepsChallengeDialog();
            }
        });

        // Add New Debit Item button - shows category selection then item input form
        mAddNewDebitItem = (Button) findViewById(R.id.btnAddNewDebitItem);
        mAddNewDebitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewDebitItemDialog();
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

    // ── Add New Debit Item ────────────────────────────────────────────────────

    /** Step 1: show the category selection dialog. */
    private void showAddNewDebitItemDialog() {
        String[] options = {"New Cardio Item", "New Activity Item", "New Strength Training Item"};
        new AlertDialog.Builder(this)
                .setTitle("Add New Debit Item")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0: showNewExerciseItemForm(ExerciseItem.CATEGORY_CARDIO,    "New Cardio Item");           break;
                        case 1: showNewExerciseItemForm(ExerciseItem.CATEGORY_ACTIVITY,  "New Activity Item");         break;
                        case 2: showNewExerciseItemForm(ExerciseItem.CATEGORY_STRENGTH,  "New Strength Training Item"); break;
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Step 2: show the input form for the selected category.
     * Cardio / Activity: collects Name + Duration (min) + Calories per minute.
     * Strength Training: collects Name + Reps + Calories per rep.
     */
    private void showNewExerciseItemForm(String category, String title) {
        boolean isStrength = ExerciseItem.CATEGORY_STRENGTH.equals(category);

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, 0);

        android.widget.EditText etName = new android.widget.EditText(this);
        etName.setHint("Item name");
        layout.addView(etName);

        android.widget.EditText etDuration = new android.widget.EditText(this);
        etDuration.setHint(isStrength ? "Number of reps" : "Duration (minutes)");
        etDuration.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(etDuration);

        android.widget.EditText etCalPerUnit = new android.widget.EditText(this);
        etCalPerUnit.setHint(isStrength ? "Calories per rep" : "Calories per minute");
        etCalPerUnit.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(etCalPerUnit);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name        = etName.getText().toString().trim();
                    String durationStr = etDuration.getText().toString().trim();
                    String calStr      = etCalPerUnit.getText().toString().trim();

                    if (name.isEmpty() || durationStr.isEmpty() || calStr.isEmpty()) {
                        Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        float durationOrReps = Float.parseFloat(durationStr);
                        float calPerUnit     = Float.parseFloat(calStr);
                        int   reps           = isStrength ? (int) durationOrReps : 0;
                        float durationMin    = isStrength ? 0f : durationOrReps;

                        SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(this);
                        long rowId = db.insertExerciseItem(category, name, durationMin, reps, calPerUnit);

                        if (rowId == -1L) {
                            Toast.makeText(this,
                                    "\"" + name + "\" already exists in this category.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this,
                                    "\"" + name + "\" saved successfully.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid number entered. Please check your values.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ─────────────────────────────────────────────────────────────────────────

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DEBIT_MAN && resultCode == RESULT_OK && data != null) {
            int debitValue = data.getIntExtra(TOTAL_DEBIT_VALUE, 0);
            if (debitValue > 0) {
                int balanceBeforeDebit = CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt();
                StoreDayEnd2(balanceBeforeDebit);

                // After 4 PM: persist the pre-debit balance as the Day End Balance.
                // GetDayEndBalance() reads from dayend_balance for Step Challenge calculations.
                java.util.Calendar cal = java.util.Calendar.getInstance();
                if (cal.get(java.util.Calendar.HOUR_OF_DAY) >= 16) {
                    try {
                        String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                                .format(cal.getTime());
                        new SQLDatabase_Food_Items_CIF6(getApplicationContext())
                                .setPreCreditDayEndBalance(balanceBeforeDebit);
                        android.util.Log.d("DebitSteps", "[onActivityResult] Stored Day End Balance for Step Challenge: "
                                + balanceBeforeDebit + " date=" + today);
                    } catch (Exception ex) {
                        android.util.Log.e("DebitSteps", "[onActivityResult] Failed to store Day End Balance: " + ex.getMessage(), ex);
                    }
                }

                BackToParent(debitValue);
            }
        }
    }

    private void weaselpop() {
        setResult(1);
        finish();
    }

    /**
     * Store a day-end snapshot at the moment the user taps Debit (if past 4 PM).
     * Delegates to SQLDatabase's storeDayEnd2Snapshot — idempotent (once per day).
     *
     * @param fourPMDayEndBalance the current countdown balance to snapshot
     */
    private void StoreDayEnd2(int fourPMDayEndBalance) {
        try {
            android.app.Application app = getApplication();
            java.util.Calendar now = java.util.Calendar.getInstance();
            int hour = now.get(java.util.Calendar.HOUR_OF_DAY);

            // Only persist if it's 4 PM or later
            if (hour < 16) {
                android.util.Log.d("StoreDayEnd2", "Before 4 PM — skipping.");
                return;
            }

            String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                    .format(now.getTime());
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(app);

            if (db.isDayEnd2StoredForDate(today)) {
                android.util.Log.d("StoreDayEnd2", "Already stored for " + today + " — skipping.");
                return;
            }

            android.content.SharedPreferences prefs = app.getSharedPreferences("Calorie_Countdown", 0);
            String gender = prefs.getString("user_gender", "male");
            int dailyBudget = "female".equalsIgnoreCase(gender) ? 2000 : 2500;
            int todayFoodCals = db.getTotalFoodNoteCaloriesToday();
            int kittyValue = dailyBudget - todayFoodCals;

            int daysToZero = (fourPMDayEndBalance > 0)
                    ? (int) Math.ceil((double) fourPMDayEndBalance / 250.0) : 0;
            java.util.Calendar zeroDate = java.util.Calendar.getInstance();
            zeroDate.add(java.util.Calendar.DAY_OF_YEAR, daysToZero);
            String estimatedZeroDate = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                    .format(zeroDate.getTime());

            db.storeDayEnd2Snapshot(today, fourPMDayEndBalance, dailyBudget,
                    todayFoodCals, kittyValue, estimatedZeroDate);

            android.util.Log.d("StoreDayEnd2", "Snapshot stored: balance=" + fourPMDayEndBalance
                    + " kitty=" + kittyValue + " zeroDate=" + estimatedZeroDate);

            // Azure backend sync — fire-and-forget, never blocks the Debit button flow
            final String snapDate = today;
            final int snapBalance = fourPMDayEndBalance;
            final int snapBudget = dailyBudget;
            final int snapFood = todayFoodCals;
            final int snapKitty = kittyValue;
            final String snapZeroDate = estimatedZeroDate;
            final android.content.Context appCtx = app.getApplicationContext();
            new Thread(() -> {
                try {
                    String clientName = prefs.getString("client_name", "Client");
                    if (clientName == null || clientName.isEmpty()) clientName = "Client";
                    SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(appCtx);
                    apiClient.syncDayEnd(clientName, snapDate, snapBalance, snapBudget,
                            snapFood, snapKitty, snapZeroDate, new ApiResultCallback() {
                        @Override public void onSuccess(String response) {
                            android.util.Log.d("StoreDayEnd2", "[syncDayEnd] OK date=" + snapDate);
                        }
                        @Override public void onFailure() {
                            android.util.Log.w("StoreDayEnd2", "[syncDayEnd] Backend sync failed — local DB is authoritative.");
                        }
                    });
                } catch (Exception ex) {
                    android.util.Log.w("StoreDayEnd2", "[syncDayEnd] Exception: " + ex.getMessage());
                }
            }).start();

        } catch (Exception e) {
            android.util.Log.e("StoreDayEnd2", "Exception: " + e.getMessage(), e);
        }
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
        android.util.Log.d("Workout", "[deduct100Points] Starting 100-point deduction");
        try {
            MIF4_Data_Model_Adapter dataAdapter = new MIF4_Data_Model_Adapter(getApplicationContext());
            String currentBalanceStr = dataAdapter.RetrieveBalance();
            android.util.Log.d("Workout", "[deduct100Points] Raw DB balance: " + currentBalanceStr);

            // Root cause fix: guard null/empty balance before parseInt to prevent NFE
            int currentBalance = 0;
            if (currentBalanceStr != null && !currentBalanceStr.isEmpty()) {
                currentBalance = Integer.parseInt(currentBalanceStr.replace(",", "").trim());
            }

            int newBalance = currentBalance - 100;
            android.util.Log.d("Workout", "[deduct100Points] old=" + currentBalance + ", new=" + newBalance);

            String newBalanceStr = String.valueOf(newBalance);
            dataAdapter.StoreBalance(newBalanceStr);
            dataAdapter.StoreDayEndBalance(newBalance - 100);

            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "100 points dropped! Balance: " + currentBalance + " → " + newBalance,
                Toast.LENGTH_SHORT).show();

            // Root cause fix: update main activity UI immediately so balance reflects the change
            // without requiring the user to navigate back and trigger onResume.
            if (CCD_GUI_CD_CIF1.instance != null) {
                CCD_GUI_CD_CIF1.instance.runOnUiThread(() -> {
                    android.util.Log.d("Workout", "[deduct100Points] Refreshing main activity UI");
                    CCD_GUI_CD_CIF1.instance.refreshBalanceDisplay();
                });
            }

            android.util.Log.d("Workout", "[deduct100Points] Completed. old=" + currentBalance + ", new=" + newBalance);
        } catch (NumberFormatException e) {
            android.util.Log.e("Workout", "[deduct100Points] NFE: " + e.getMessage(), e);
            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "Error updating balance: invalid number format", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            android.util.Log.e("Workout", "[deduct100Points] Exception: " + e.getMessage(), e);
            Toast.makeText(Debit_Activity_CiF003_fragment_box.this,
                "Error updating balance: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // ── Midnight Scrape ───────────────────────────────────────────────────────

    /**
     * Midnight Scrape reconciliation:
     *  - Fetches the step count the user recorded via "Debit Steps" today.
     *  - Compares it against the current system sensor reading (mStep_Count).
     *  - If the sensor shows MORE steps than recorded, the difference is debited
     *    from the Main Countdown Balance (difference × 0.089 points).
     *  - Nothing is debited if actual ≤ recorded.
     */
    private void showMidnightScrapeDialog() {
        try {
            // 1. Fetch recorded steps for today from SQLite
            String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                    .format(new java.util.Date());
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(getApplicationContext());
            int recordedSteps = db.getRecordedStepsForDate(today);

            // 2. Get actual system step count from the hardware sensor
            int actualSteps = mStep_Count;

            // 3. Build the dialog message regardless of outcome, so the user always sees data
            String recordedDisplay = (recordedSteps >= 0) ? String.valueOf(recordedSteps) : "Not recorded today";

            if (recordedSteps < 0) {
                // No entry stored yet — inform the user
                new AlertDialog.Builder(this)
                        .setTitle("Midnight Scrape")
                        .setMessage("No step entry found for today.\n\n"
                                + "Please use \"Debit Balance with Steps Manually\" first to record your steps, "
                                + "then run Midnight Scrape to reconcile any difference.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            int difference = actualSteps - recordedSteps;

            if (difference <= 0) {
                // Actual is equal to or less than recorded — no reconciliation needed
                new AlertDialog.Builder(this)
                        .setTitle("Midnight Scrape")
                        .setMessage("No reconciliation needed.\n\n"
                                + "Recorded steps : " + recordedSteps + "\n"
                                + "Actual steps   : " + actualSteps + "\n\n"
                                + "You have not exceeded your recorded count.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            // 4. Calculate the calorie-equivalent debit for the extra steps
            int debitPoints = Math.round(difference * 0.089f);
            if (debitPoints < 1) debitPoints = 1; // minimum 1 point if there is any difference

            final int finalDebit = debitPoints;
            final int finalDiff  = difference;

            new AlertDialog.Builder(this)
                    .setTitle("Midnight Scrape")
                    .setMessage("Step reconciliation found a discrepancy:\n\n"
                            + "Recorded steps : " + recordedSteps + "\n"
                            + "Actual steps   : " + actualSteps + "\n"
                            + "Extra steps    : " + finalDiff + "\n\n"
                            + "Debit          : " + finalDebit + " points\n\n"
                            + "Apply this debit to your Countdown Balance?")
                    .setPositiveButton("Yes, Apply Debit", new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            StoreDayEnd2(CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt());
                            BackToParent(finalDebit);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        } catch (Exception e) {
            android.util.Log.e("MidnightScrape", "showMidnightScrapeDialog: " + e.getMessage(), e);
            Toast.makeText(this, "Midnight Scrape error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showBMRDialog() {
        final String[] genderOptions = {"Male", "Female"};
        // selectedGender[0]: 0 = Male (2500 pts), 1 = Female (2000 pts)
        final int[] selectedGender = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BMR Deduction - Select Gender");
        builder.setSingleChoiceItems(genderOptions, 0, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                selectedGender[0] = which;
            }
        });

        builder.setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                // Male → 2500, Female → 2000
                int bmrPoints = (selectedGender[0] == 0) ? 2500 : 2000;
                StoreDayEnd2(CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt());
                BackToParent(bmrPoints);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * Steps Challenge:
     * Formula: ceil((CurrentBalance - DayEndTarget - BMR) / StepsNumerator)
     * where DayEndTarget = PreviousDayEndBalance - 250
     *       StepsNumerator = 0.089
     *       BMR = 2500 (male) or 2000 (female)
     */
    private void showStepsChallengeDialog() {
        try {
            // 1. Get current balance from main activity
            int currentBalance = CCD_GUI_CD_CIF1.instance.Get_currentBalanceInt();

            // 2. Get previous day-end balance from SQLite
            SQLDatabase_Food_Items_CIF6 db = new SQLDatabase_Food_Items_CIF6(getApplicationContext());
            int previousDayEnd = db.GetDayEndBalance();

            // 3. Day End Target = Previous Day End Balance - 250
            int dayEndTarget = previousDayEnd - 250;

            // 4. Steps Numerator (1 step burns 0.089 calories)
            double stepsNumerator = 0.089;

            // 5. Calculate steps needed to reach Day End Target from Current Balance
            // Formula: (CurrentBalance - DayEndTarget) / StepsNumerator
            //
            // -- BMR EXTENSION POINT --
            // When the BMR formula is defined, add it here before the division:
            //   int bmr = calculateBMR(gender);
            //   double rawSteps = (currentBalance - dayEndTarget - bmr) / stepsNumerator;
            double rawSteps = (currentBalance - dayEndTarget) / stepsNumerator;
            int stepChallenge = (int) Math.ceil(rawSteps);
            if (stepChallenge < 0) stepChallenge = 0;

            // 6. Display result
            String message = "Client Step Challenge Calculation:\n\n"
                    + "Current Balance:        " + String.format("%,d", currentBalance) + "\n"
                    + "Previous Day End:       " + String.format("%,d", previousDayEnd) + "\n"
                    + "Day End Target (- 250): " + String.format("%,d", dayEndTarget) + "\n"
                    + "Steps Numerator:        " + stepsNumerator + "\n\n"
                    + "(" + String.format("%,d", currentBalance)
                    + " - " + String.format("%,d", dayEndTarget)
                    + ") / " + stepsNumerator + "\n\n"
                    + "Client Step Challenge = " + String.format("%,d", stepChallenge) + " Steps";

            new AlertDialog.Builder(this)
                    .setTitle("Steps Challenge")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();

            android.util.Log.d("StepsChallenge", "currentBalance=" + currentBalance
                    + " previousDayEnd=" + previousDayEnd + " dayEndTarget=" + dayEndTarget
                    + " stepChallenge=" + stepChallenge);

        } catch (Exception e) {
            android.util.Log.e("StepsChallenge", "Error: " + e.getMessage(), e);
            Toast.makeText(this, "Steps Challenge error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}