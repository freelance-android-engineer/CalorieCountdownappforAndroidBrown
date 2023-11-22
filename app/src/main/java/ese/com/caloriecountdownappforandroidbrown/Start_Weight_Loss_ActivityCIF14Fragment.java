package ese.com.caloriecountdownappforandroidbrown;

import android.content.Intent;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


/**
 * A placeholder fragment containing a simple view.
 */
public class Start_Weight_Loss_ActivityCIF14Fragment extends Fragment {

    public static final String OPENING_BALANCE = "Opening Countdown Figure / Value / Balance";
    public static final String ResultBreakfastTime = "Breakfast time";
    public static final String ResultLunchTime = "Lunch time";
    public static final String ResultDinnerTime = "Dinner time";
    public static final String ResultMidnight = "Midnight time";
    public static final String VitalStats = "Vital Statistics;";


    private Button mOpenAccount;
    private HealthProfileCiF3 mAccount;
    private Display_Dialog_CIF11 display_dialog_cif11;

    private String mTargetWeight = "-1";


    public Start_Weight_Loss_ActivityCIF14Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start__weight__loss__activity_cif14, container, false);

        display_dialog_cif11 = new Display_Dialog_CIF11();
        mAccount = new HealthProfileCiF3();

        mOpenAccount = (Button) v.findViewById(R.id.button14);
        mOpenAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                {
                android.util.Log.d("Debugging","We are about to shoot intent0");

                OpenAccount(mAccount);

                if(CheckTimeFormat(mAccount)) {

                    if (CheckBreakfastLunchDinnerTimesIn(mAccount)) {
                        android.util.Log.d("Debugging", "We are about to shoot intent6");
                        BackToParentWithOpeningBalance(mAccount);
                        android.util.Log.d("Debugging", "We are about to shoot intent7");
                    } else {
                        BreakfastLunchDinnerTimesNotIn();
                    }
                }
                else
                {
                    Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                    display_dialog_cif11.Set_mAppContext(getActivity());
                    display_dialog_cif11.Showing("Meal Reminder Time entered in wrong format, please re-enter using the example text as guide");
                }




            }}
        });

        return v;
    }

    public void onRadioButtonClicked()
    {

    }



    private void OpenAccount(HealthProfileCiF3 IN)
    {
        //IDO ~> Algorithm Engineering ~> Android : Display VitalStats :
        DisplayVitalStats(GetOpeningBalance(GetVitalStatsString(FillInMIFs(ExtractView(IN)))));
    }


    private HealthProfileCiF3 ExtractView(HealthProfileCiF3 IN)
    {

            EditText editText65 = (EditText) getActivity().findViewById(R.id.edit_text65);
            EditText editText66 = (EditText) getActivity().findViewById(R.id.edit_text66);
            DatePicker editText76 = (DatePicker) getActivity().findViewById(R.id.date_picker_76);
            EditText editText67 = (EditText) getActivity().findViewById(R.id.edit_text67);
            EditText editText69 = (EditText) getActivity().findViewById(R.id.edit_text70);
            EditText editText70 = (EditText) getActivity().findViewById(R.id.edit_text70);
            EditText editText71 = (EditText) getActivity().findViewById(R.id.edit_text71);
            EditText editText72 = (EditText) getActivity().findViewById(R.id.edit_text72);
            EditText editText73 = (EditText) getActivity().findViewById(R.id.edit_text73);
            EditText editText74 = (EditText) getActivity().findViewById(R.id.edit_text74);
            EditText editText75 = (EditText) getActivity().findViewById(R.id.edit_text75);
            EditText editText76a = (EditText) getActivity().findViewById(R.id.edit_text76a);

        EditText editText77a = (EditText) getActivity().findViewById(R.id.edit_text77a);
        EditText editText80a = (EditText) getActivity().findViewById(R.id.edit_text80a);
        EditText editText81a = (EditText) getActivity().findViewById(R.id.edit_text81a);
        EditText editText78a = (EditText) getActivity().findViewById(R.id.edit_text78a);
        EditText editText79a = (EditText) getActivity().findViewById(R.id.edit_text79a);




        Spinner spinner1 = (Spinner) getActivity().findViewById(R.id.spincity1);
        Spinner spinner2 = (Spinner) getActivity().findViewById(R.id.spincity2);
        Spinner spinner3 = (Spinner) getActivity().findViewById(R.id.spincity3);
        Spinner spinner4 = (Spinner) getActivity().findViewById(R.id.spincity4);
        Spinner spinner15 = (Spinner) getActivity().findViewById(R.id.spincity15);

        RadioButton CM = (RadioButton) getActivity().findViewById(R.id.CM1);
        RadioButton Inches = (RadioButton) getActivity().findViewById(R.id.Inches1);
        RadioButton Feet = (RadioButton) getActivity().findViewById(R.id.Feet1);
        RadioButton Meter = (RadioButton) getActivity().findViewById(R.id.Meters1);




        IN.setFirstname(editText65.getText().toString());
        IN.setLastname(editText66.getText().toString());
        IN.setDOB(editText76.getDayOfMonth(), editText76.getMonth(), editText76.getYear());
        IN.setEmailaddress(editText67.getText().toString());
        if(spinner15.getSelectedItemPosition()== 0){
            IN.setClientGender("Male");
        }
        if(spinner15.getSelectedItemPosition()== 1) {
            IN.setClientGender("Female");
        }

        IN.setClientBodyFrame(spinner1.getSelectedItem().toString());

        if(spinner2.getSelectedItemPosition()== 0)
        {
            IN.setWeightUnits("pounds");
        }
        if(spinner2.getSelectedItemPosition() == 1)
        {
            IN.setWeightUnits("kilograms");
        }


        android.util.Log.d("Checking Weight units: ",IN.getWeightUnits());
        IN.setCurrentWeight(editText70.getText().toString());
        IN.setTargetWeight(editText71.getText().toString());
        addToSharedPref(IN.getTargetWeight());
        mTargetWeight = editText71.getText().toString();

        SQLDatabase_Food_Items_CIF6 cif6 = new SQLDatabase_Food_Items_CIF6(getActivity());
        cif6.PostValueToTargetWeightTable(mTargetWeight);

        android.util.Log.d("TargetWeight",IN.getTargetWeight());

        IN.setWeightUnits(IN.getWeightUnits());
        IN.setQuickStart(spinner3.getSelectedItem().toString());
        //IN.setHieghtUnits(spinner4.getSelectedItem().toString());

        if(FixedSumisChecked(spinner3) && TargetWeightnotChecked(IN, editText71) == "")
        {
            IN.setTargetWeightImperial("0");
            if(spinner3.getSelectedItemPosition() == 0)
            {
                IN.setOptionSelected("0");
            }
            if(spinner3.getSelectedItemPosition() == 1)
            {
                IN.setOptionSelected("1");
            }
            if(spinner3.getSelectedItemPosition() == 2)
            {
                IN.setOptionSelected("2");
            }

            IN.setCurrentWeight(editText70.getText().toString());
            android.util.Log.d("FixedT", editText70.getText().toString());
            IN.setTargetWeight(FixedBlockisChecked(IN));
            android.util.Log.d("Fixed2T", FixedBlockisChecked(IN));

        }


        if(CM.isChecked())
        {
            IN.setHieghtUnits("CM");
            IN.setClientHeight(editText76a.getText().toString());
        }

        if(Feet.isChecked())
        {
            IN.setHieghtUnits("Feet and Inches");

            android.util.Log.d("Checking Feet units: ",editText78a.getText().toString());
            int feet = new RoundingCIF13().StringToInt(editText78a.getText().toString());
            android.util.Log.d("Checking Inches units: ",editText79a.getText().toString());

            int inches = new RoundingCIF13().StringToInt(editText79a.getText().toString());
            double inchesdouble = inches;

            double heightinmeters = ((feet * 30) + (inchesdouble * 2.54));
            float heightm = (float) heightinmeters;
            int heihgtcm = (int) (heightm);
            IN.setClientHeightinCM(heihgtcm);

            String answer = (new RoundingCIF13().IntToString(feet)) + "." + (new RoundingCIF13().IntToString(inches));

            IN.setClientHeight(answer);
            IN.setHeightm(heightm/100);
        }

        if(Inches.isChecked())
        {
            IN.setHieghtUnits("Inches");
            IN.setClientHeight(editText77a.getText().toString());
            IN.setClientHeightinCM((int) ((Float.parseFloat(IN.getClientHeight())) /0.393700787));
        }

        if(Meter.isChecked())
        {
            IN.setHieghtUnits("Meter and CM");

            int meters = new RoundingCIF13().StringToInt(editText80a.getText().toString());
            int mcm = new RoundingCIF13().StringToInt(editText81a.getText().toString());
            int cm = (meters * 100) + mcm;

            String answer = (new RoundingCIF13().IntToString(meters)) + "." + (new RoundingCIF13().IntToString(mcm));

            android.util.Log.d("mcm1", new RoundingCIF13().IntToString(meters));
            android.util.Log.d("mcm2", new RoundingCIF13().IntToString(mcm));
            android.util.Log.d("mcm2", answer);
            android.util.Log.d("what units", IN.getWeightUnits());

            IN.setClientHeight(answer);
            IN.setClientHeightinCM(cm);
        }


        IN.setBreakfastTime(editText72.getText().toString());
        IN.setLunchTime(editText73.getText().toString());
        IN.setFinalMealTime(editText74.getText().toString());

        Store_Reminder_Times(editText72, editText73, editText74);

        return IN;


    }

    private HealthProfileCiF3 FillInMIFs(HealthProfileCiF3 IN)
    {

        IN.SetClientAge(IN.ReckonClientAge());

        Mif3BMIFunction bmiFunction = new Mif3BMIFunction();
        IdealBMICiF19 bmiFunction2 = new IdealBMICiF19();
        bmiFunction2.setDisplay_Monitor(display_dialog_cif11);

        //android.util.Log.d("BMI is", new RoundingCIF13().DoubleToString(bmiFunction2.ReckonBMI(IN).getClientBMI()));
        IN.setClientBMI(round((bmiFunction2.ReckonBMI(IN)).getClientBMI()));

        Mif3BMR bmr = new Mif3BMR();
        Mif3ClientBodyFat clientBodyFat = new Mif3ClientBodyFat();
        Mif3HealtVitalStats healtVitalStats = new Mif3HealtVitalStats();
        Mif3StartNumber startNumber = new Mif3StartNumber();
        Mif3StartDate startDate = new Mif3StartDate();
        Mif3TargetEndDate targetEndDate = new Mif3TargetEndDate();
        Mif3StartWeight startWeight = new Mif3StartWeight();
        Mif3DisplayContents displayContents = new Mif3DisplayContents();



        return healtVitalStats.HealtVitalStats(bmr.BMR(bmiFunction.BMIfunction2(clientBodyFat.ClientBodyFat(startDate.StartDate(targetEndDate.TargetEndDate(startWeight.StartWeight(startNumber.StartNumber(IN))))))));
    }

    private HealthProfileCiF3 GetVitalStatsString(HealthProfileCiF3 IN)
    {
        return IN;
    }

    private HealthProfileCiF3 GetOpeningBalance(HealthProfileCiF3 IN)
    {
        IN.CalculateBMI();

        return IN;

    }

    private void DisplayVitalStats(HealthProfileCiF3 IN)
    {
        //CiF11 display text
        //Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
        //display_dialog_cif11.Set_mAppContext(this.getActivity());
        //display_dialog_cif11.Showing(GetVitalStatsString(IN).getVitalStatsString());

    }

    private void BackToParentWithOpeningBalance(HealthProfileCiF3 IN)
    {
        MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(getActivity());
        data_model_adapter.StoreStartWeightLoss(IN);
        ObjectWithAllTheTimesCIF10 objectWithAllTimes = StartNotificationCycle(mAccount);
        //= goey.ResetAlarm(specialThreadCIF15); //Send and intent back to start reset Alarm
        //Readme : ActivateAlarm does it's thing and returns CIF10 packed data for next thread alarms
        //these are packed into an intent and shot off for The Bridge CIF1 to receive
        //unpack the data and reset a new special tread similar to this still for next Alarm, capish?

        Intent i2 = new Intent();
        android.util.Log.d("Debugging", "We are about to shoot intent1");
        i2.putExtra(OPENING_BALANCE, IN.getStartCountdown());
        i2.putExtra(ResultBreakfastTime, new RoundingCIF13().DateToStringStandard(objectWithAllTimes.getResetBreakfastTime()));
        i2.putExtra(ResultLunchTime, new RoundingCIF13().DateToStringStandard(objectWithAllTimes.getResetLunchTime()));
        i2.putExtra(ResultDinnerTime, new RoundingCIF13().DateToStringStandard(objectWithAllTimes.getResetDinnerTime()));
        i2.putExtra(ResultMidnight, new RoundingCIF13().DateToStringStandard(objectWithAllTimes.getResetDayEnd()));
        i2.putExtra(VitalStats, IN.getVitalStatsString());
        android.util.Log.d("Debugging", "We are about to shoot intent2");

        getActivity().setResult(getActivity().RESULT_OK, i2);
        android.util.Log.d("Debugging", "We are about to shoot intent3");
        getActivity().finish();
        android.util.Log.d("Debugging","We are about to shoot intent4");

    }

    private ObjectWithAllTheTimesCIF10 StartNotificationCycle(HealthProfileCiF3 IN)
    {
        //Step One : We have Breakfast, Lunch and Dinner time in IN,
        //Step Two : Use these values to intialize and activate SpecialThread CIF
        SpecialThreadCIF15 specialThreadCIF15 = new SpecialThreadCIF15(getActivity().getApplication());

        //Step Three : Then call activate alarm by itself no parameters and that's it's Build Pipeline straw till Reload Good Luck.

        return specialThreadCIF15.ActivateAlarm();
    }

    private boolean CheckBreakfastLunchDinnerTimesIn(HealthProfileCiF3 in)
    {
        return true;

        /*String breakfast = in.getBreakfastTime();
        String lunch = in.getLunchTime();
        String dinner = in.getFinalMealTime();

        if(checkTimeFormat(breakfast))
        {
            if(checkTimeFormat(lunch))
            {
                if(checkTimeFormat(dinner))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }*/

    }

    private void BreakfastLunchDinnerTimesNotIn()
    {
        ;
    }

    private boolean checkTimeFormat(String in)
    {


        if(in.length() != 5)
        {
            return false;
        }

        if(     in.substring(0,1) == "0" ||
                in.substring(0,1) == "1" ||
                in.substring(0,1) == "2" ||
                in.substring(0,1) == "3" ||
                in.substring(0,1) == "4" ||
                in.substring(0,1) == "5" ||
                in.substring(0,1) == "6" ||
                in.substring(0,1) == "7" ||
                in.substring(0,1) == "8" ||
                in.substring(0,1) == "9"
                )
        {
            if (in.substring(1,2) == "0" || in.substring(1,2) == "1" ||
                in.substring(1,2) == "2" ||
                in.substring(1,2) == "3" ||
                in.substring(1,2) == "4" ||
                in.substring(1,2) == "5" ||
                in.substring(1,2) == "6" ||
                in.substring(1,2) == "7" ||
                in.substring(1,2) == "8" ||
                in.substring(1,2) == "9"
                    )
            {
                if(in.substring(2,3) == ":")
                {
                    if(in.substring(3,4) == "0" ||
                            in.substring(3,4) == "1" ||
                            in.substring(3,4) == "2" ||
                            in.substring(3,4) == "3" ||
                            in.substring(3,4) == "4" ||
                            in.substring(3,4) == "5" ||
                            in.substring(3,4) == "6" ||
                            in.substring(3,4) == "7" ||
                            in.substring(3,4) == "8" ||
                            in.substring(3,4) == "9"
                            )
                    {
                        if(in.substring(4,5) == "0" ||
                                in.substring(4,5) == "1" ||
                                in.substring(4,5) == "2" ||
                                in.substring(4,5) == "3" ||
                                in.substring(4,5) == "4" ||
                                in.substring(4,5) == "5" ||
                                in.substring(4,5) == "6" ||
                                in.substring(4, 5) == "7" ||
                                in.substring(4,5) == "8" ||
                                in.substring(4,5) == "9"
                                )
                        {
                            return true;
                        }
                        else
                        {
                           return false;
                        }

                    }
                    else
                    {
                        return false;
                    }




                }
                else
                {
                    return false;
                }

            }
            else
            {
                return false;
            }




        }
        else
        {
            return false;
        }



    }

    private void Store_Reminder_Times(EditText IN1, EditText IN2, EditText IN3)
    {
        String Breakfast_Time;
        String Lunch_Time;
        String Dinner_Time;

        int Btime;
        int Ltime;
        int Dtime;

        Breakfast_Time = IN1.getText().toString();
        Lunch_Time = IN2.getText().toString();
        Dinner_Time = IN3.getText().toString();

        try
        {
            Btime = new RoundingCIF13().StringToInt(Breakfast_Time);
            Ltime = new RoundingCIF13().StringToInt(Lunch_Time);
            Dtime = new RoundingCIF13().StringToInt(Dinner_Time);

            MIF4_Data_Model_Adapter adapter = new MIF4_Data_Model_Adapter(getActivity());

            adapter.Store_Reminder_Times(Btime,Ltime,Dtime);

        }
        catch(Exception e)
        {

        }

    }

    private boolean CheckTimeFormat(HealthProfileCiF3 IN)
    {


        String Breakfasttime = IN.getBreakfastTime();
        android.util.Log.d("CheckTimingB", Breakfasttime);
        String Lunchtime = IN.getLunchTime();
        android.util.Log.d("CheckTimingL", Lunchtime);
        String Dinnertime = IN.getFinalMealTime();
        android.util.Log.d("CheckTimingB", Dinnertime);

        MIF10_Time_Format_Checker_Class checker_class = new MIF10_Time_Format_Checker_Class();

        if(checker_class.Time_Format_Checker(Breakfasttime) && checker_class.Time_Format_Checker(Lunchtime) && checker_class.Time_Format_Checker(Dinnertime))
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    private String FixedBlockisChecked(HealthProfileCiF3 IN) {
        String str = IN.getWeightUnits();
        String str3 = IN.getCurrentWeight();
        String str2 = "275";



        double float3 = new RoundingCIF13().StringToFloat(str3);



        if(str == "pounds")
        {
            if(IN.getOptionSelected() == "0")
            {
                return mTargetWeight;
            }

            if(IN.getOptionSelected() == "1")
            {
                float3 = float3 - 11.0;
            }

            if(IN.getOptionSelected() == "2")
            {
                float3 = float3 - 14.0;
            }

            if(IN.getOptionSelected() == "3")
            {
                float3 = float3 - 22.0;
            }
        }
        else
        {
            if(str == "kilograms")
            {
                if(getWeightLossTarget() != 0)
                {
                    float3 = float3 = getWeightLossTarget();
                }
                else {
                    if (IN.getOptionSelected() == "0") {
                        float3 = float3 - 5;
                    }

                    if (IN.getOptionSelected() == "1") {
                        float3 = float3 - 6;
                    }

                    if (IN.getOptionSelected() == "2") {
                        float3 = float3 - 10;
                    }
                }
            }
            else
            {
                return str = "110";
            }
        }

        return new RoundingCIF13().FloatToString((float)float3);
    }

    private boolean FixedSumisChecked(Spinner IN)
    {
        String str = IN.getSelectedItem().toString();

        return true;
    }

    private String TargetWeightnotChecked(HealthProfileCiF3 IN, EditText spinner3)
    {
        spinner3.getText().toString();

        String str = "";
        return str;
    }


    private double round(double in)
    {
        in = in * 100;
        int k = (int) in;
        in = k;
        in = in/100;
        return in;
    }

    private float getWeightLossTarget()
    {
        if(hasValue())
        {
            return getHasValue();
        }
        else
        {
            return 0;
        }
    }

    private boolean hasValue()
    {
       return true;
    }

    private float getHasValue()
    {

            return getWeightLossBoxValue();

    }

    private float getWeightLossBoxValue()
    {
        EditText hasValue = (EditText) getActivity().findViewById(R.id.edit_text71);
        String contents = hasValue.getText().toString();
        if(contents != null && contents.length() >= 1)
        {
            float weightlosstozero = new RoundingCIF13().StringToFloat(contents);
            return weightlosstozero;
        }
        else
        {
            return 0;
        }
    }

    private void addToSharedPref(String input)
    {
       CCD_GUI_CD_CIF1.instance.Store_Target_Weight_Pounds(input);
    }

}
