package ese.com.caloriecountdownappforandroidbrown;

/**
 * Created by lokeke on 26/02/2016.
 */

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 *
 * CD = CCDGUI-CD (Calorie Countdown MIDlet)
 * Menu = Options
 * Menuitem = Activate Countdown
 *
 *
 * CiF name = IdealBMICiF19
 * Blackboard Account Allocated to = BL16231
 * Number of Blackboard Point Worth = 2 points plus 1 point per testcase. (Maximum 3 testcases)
 * Implementation Deadline = February 15 2007
 */

public class IdealBMICiF19 {


    private Hashtable idealBMIs;
    private Context mContext;
    private Display_Dialog_CIF11 display_dialog_cif11;

    public IdealBMICiF19() {

        //intialise hashtable with key = height in inches, value = bmi 22 corresponding wieight values.

        //display_dialog_cif11 = new Display_Dialog_CIF11();
        mContext = null;
        idealBMIs = new Hashtable();



        String key1 = "58";
        String value1 = "21.7";

        String key2 = "59";
        String value2 = "21.7";

        String key3 = "60";
        String value3 = "21.7";

        String key4 = "61";
        String value4 = "21.7";

        String key5 = "62";
        String value5 = "21.7";

        String key6 = "63";
        String value6 = "21.7";

        String key7 = "64";
        String value7 = "21.7";

        String key8 = "65";
        String value8 = "21.7";

        String key9 = "66";
        String value9 = "21.7";

        String key10 = "67";
        String value10 = "21.7";

        String key11 = "68";
        String value11 = "21.7";

        String key12 = "69";
        String value12 = "21.7";

        String key13 = "70";
        String value13 = "21.7";


        String key14 = "71";
        String value14 = "21.7";

        String key15 = "72";
        String value15 = "21.7";

        String key16 = "73";
        String value16 = "21.7";

        String key17 = "74";
        String value17 = "21.7";

        String key18 = "75";
        String value18 = "21.7";

        String key19 = "76";
        String value19 = "21.7";

        idealBMIs.put(key1,value1);
        idealBMIs.put(key2,value2);
        idealBMIs.put(key3,value3);
        idealBMIs.put(key4,value4);
        idealBMIs.put(key5,value5);
        idealBMIs.put(key6,value6);
        idealBMIs.put(key7,value7);
        idealBMIs.put(key8,value8);
        idealBMIs.put(key9,value9);
        idealBMIs.put(key10,value10);
        idealBMIs.put(key11,value11);
        idealBMIs.put(key12,value12);
        idealBMIs.put(key13,value13);
        idealBMIs.put(key14,value14);
        idealBMIs.put(key15,value15);
        idealBMIs.put(key16,value16);
        idealBMIs.put(key17,value17);
        idealBMIs.put(key18,value18);
        idealBMIs.put(key19,value19);


    }

    public String GetIdealWeight(String hieght)
    {
        try{
            return (String) idealBMIs.get(hieght);
        }
        catch(Exception c)
        {
            return "0";
        }
    }

    public HealthProfileCiF3 ReckonBMI(HealthProfileCiF3 IN)
    {
        float HeightinMeters = 1;
        float WeightinKilograms = new RoundingCIF13().StringToFloat(IN.getCurrentWeight());
        float BMI = 1;

        //IN.setWeightUnits("pounds");

        if(IN.getHieghtUnits() == "CM")
        {
            //try
            //{
                HeightinMeters = (float) (((new RoundingCIF13().StringToFloat(IN.getClientHeight())) / 100) * 1);
                android.util.Log.d("CM Height in KG = ", new RoundingCIF13().FloatToString(HeightinMeters));

            //}
           // catch (NumberFormatException e)
            //{

              //  display_dialog_cif11.Set_mAppContext(mContext);

                //display_dialog_cif11.Showing("You have not entered your Height value, please do this now.");
            //}

            if(IN.getWeightUnits() == "pounds")
            {
                android.util.Log.d("Last check WeightU", IN.getWeightUnits());
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("CM Weight in KG1 = ", new RoundingCIF13().FloatToString(HeightinMeters));
                WeightinKilograms = (float) (WeightinKilograms / 2.2);
                android.util.Log.d("CM Weight in KG2 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }

            if(IN.getWeightUnits() == "kilograms")
            {
                android.util.Log.d("Last check HeightKGU", IN.getWeightUnits());
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("CM Weight in KG1b = ", new RoundingCIF13().FloatToString(HeightinMeters));
                WeightinKilograms = (float) (WeightinKilograms);
                android.util.Log.d("CM Weight in KG2b = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }


        }

        if(IN.getHieghtUnits() == "Inches")
        {
            HeightinMeters = (float)(((new RoundingCIF13().StringToFloat(IN.getClientHeight()))/1) * 2.54) / 100;
            IN.setHeightm(HeightinMeters * 100);



            android.util.Log.d("Inches Height in KG = ", new RoundingCIF13().FloatToString(HeightinMeters));
            if(IN.getWeightUnits() == "pounds")
            {
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("Inches Weight in KG1 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
                WeightinKilograms = (float) (WeightinKilograms / 2.2);
                android.util.Log.d("Inches Weight in KG2 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }


        }

        if(IN.getHieghtUnits() == "Feet and Inches")
        {
            HeightinMeters = IN.getHeightm(); //(float)(((new RoundingCIF13().StringToFloat(IN.getClientHeight()))/100)*34.822); //30.4817
            android.util.Log.d("feet Height in KG = ", new RoundingCIF13().FloatToString(HeightinMeters));
            if(IN.getWeightUnits() == "pounds")
            {
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("feet Weight in KG1 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
                WeightinKilograms = (float) (WeightinKilograms / 2.2);
                android.util.Log.d("feet Weight in KG2 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }

            if(IN.getWeightUnits() == "kilograms")
            {
                android.util.Log.d("Last check HeightKGU", IN.getWeightUnits());
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("CM Weight in KG1b = ", new RoundingCIF13().FloatToString(HeightinMeters));
                WeightinKilograms = (float) (WeightinKilograms);
                android.util.Log.d("CM Weight in KG2b = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }
        }



        if(IN.getHieghtUnits() == "Meter and CM")
        {
            HeightinMeters = (float)(((new RoundingCIF13().StringToFloat(IN.getClientHeight()))/1) * 1 / 1);
            IN.setHeightm(HeightinMeters * 100);

            if(IN.getWeightUnits() == "pounds")
            {
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("meters Height in KG = ", new RoundingCIF13().FloatToString(HeightinMeters));
                android.util.Log.d("meters Weight in KG1 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
                WeightinKilograms = (float) (WeightinKilograms / 2.2);
                android.util.Log.d("Inches Weight in KG2 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }



            if(IN.getWeightUnits() == "kilograms")
            {
                WeightinKilograms = (new RoundingCIF13().StringToFloat(IN.getCurrentWeight()));
                android.util.Log.d("meters Height in KGb= ", new RoundingCIF13().FloatToString(HeightinMeters));
                android.util.Log.d("meters Weight in KG1b= ", new RoundingCIF13().FloatToString(WeightinKilograms));
                WeightinKilograms = (float) (WeightinKilograms);
                android.util.Log.d("Inches Weight in KG2b= ", new RoundingCIF13().FloatToString(WeightinKilograms));
            }
        }



        //Step One : Divide your weight in kilograms (kg) by your height in metres (m)
        WeightinKilograms = (WeightinKilograms /HeightinMeters);
        android.util.Log.d("Final Weight in KG1 = ", new RoundingCIF13().FloatToString(WeightinKilograms));
        //IN.setWeightUnits("kilograms");

        //Step Two : Then divide the answer by your height again to get your BMI.
        BMI = WeightinKilograms/ HeightinMeters;
        android.util.Log.d("BMI = ", new RoundingCIF13().FloatToString(BMI));


        BMI = (float) (round((double)BMI));

        IN.setClientBMI((double)BMI);
        return IN;
    }


    public void setmContext(Context IN)
    {
        mContext = IN;
    }


    public Context getmContext() {
        return mContext;
    }

    public void setDisplay_Monitor(Display_Dialog_CIF11 IN)
    {
        //display_dialog_cif11 = IN;

    }

    public Display_Dialog_CIF11 getDisply_Monitor()
    {
        //return display_dialog_cif11;
        return null;
    }


    private double round(double in)
    {
        in = in * 100;
        int k = (int) in;
        in = k;
        in = in/100;
        return in;
    }


}

/**************************DOCUMENTATION********************************
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
 * 1. //use hashtable key = height in meters value = ideal bmi all hardwired




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