package ese.com.caloriecountdownappforandroidbrown;
//import android.support.v7.app.ActionBarActivity;

import static ese.com.caloriecountdownappforandroidbrown.R.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


public class Start_Weight_Loss_ActivityCIF14 extends FragmentActivity
{

    private EditText mCMEdit;
    private EditText mInchesEdit;
    private EditText mFeetEdit1;
    private EditText mFeetEdit2;
    private EditText mMetersEdit1;
    private EditText mMetersEdit2;
    private EditText CM;

    private Display_Dialog_CIF11 display_dialog_cif11;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_start__weight__loss__activity_cif14);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(id.fragment);

        if(fragment == null)
        {
            fragment = new Start_Weight_Loss_ActivityCIF14Fragment();
            fm.beginTransaction().add(id.fragment,fragment).commit();
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View v) {
        CM = (EditText) findViewById(id.edit_text72);
        display_dialog_cif11 = new Display_Dialog_CIF11();
        display_dialog_cif11.Set_mAppContext(this);
        boolean checked = ((RadioButton) v).isChecked();

        if (v.getId() == id.CM1) {

            if (checked) {
                functionCM1(v);
            } else if (v.getId() == id.Inches1) {
                if (checked) {
                    functionInches1(v);
                } else if (v.getId() == id.Feet1) {
                    if (checked) {
                        functionFeet1(v);
                    } else if (v.getId() == id.Meters1) {
                        if (checked) {
                            functionMeters1(v);
                        }
                    }
                }
            }
        } else {
            //throw new IllegalStateException("Unexpected value: " + v.getId());
        }


    }
    /*switch(v.getId())
    {
        case id.CM1:
            if(checked)
            {
                functionCM1(v);
            }
            break;

        case id.Inches1:
            if(checked)
            {
                functionInches1(v);
            }
            break;

        case id.Feet1:
            if(checked)
            {
                functionFeet1(v);
            }
            break;

        case id.Meters1:
            if(checked)
            {
                functionMeters1(v);
            }
            break;

        default:
            throw new IllegalStateException("Unexpected value: " + v.getId());
    }
} */

    private void functionCM1(View view)
    {


        mCMEdit = (EditText) findViewById(id.edit_text76a);

        if(mCMEdit.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for your Height in CM");
        }
        else
        {
            double Centi = convertFrmCM2CM(mCMEdit);
            CM.setText(new RoundingCIF13().IntToString((int)Centi));
            Log.d("functionCM1",new RoundingCIF13().IntToString((int)Centi));
        }




    }

    private void functionInches1(View view)
    {
        mInchesEdit = (EditText) findViewById(id.edit_text77a);

        if(mInchesEdit.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for your Height in 'Inches'");
        }

        else
        {
            double Inches = convertFrmInches2CM(mInchesEdit);
            CM.setText(new RoundingCIF13().IntToString((int)Inches));
            Log.d("functionInches1",new RoundingCIF13().IntToString((int)Inches));
        }

    }

    private void functionFeet1(View view)
    {
        mFeetEdit1 = (EditText) findViewById(id.edit_text78a);
        mFeetEdit2 = (EditText) findViewById(id.edit_text79a);

        if(mFeetEdit1.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for 'Feet'");
        }

        if(mFeetEdit2.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for 'Inches'");
        }
        else
        {
            double Feet = convertFrmFeet2CM(mFeetEdit1, mFeetEdit2);
            CM.setText(new RoundingCIF13().DoubleToString(Feet));
            Log.d("functionFeet1",new RoundingCIF13().IntToString((int)Feet));
        }

    }

    private void functionMeters1(View view)
    {
        mMetersEdit1 = (EditText) findViewById(id.edit_text80a);
        mMetersEdit2 = (EditText) findViewById(id.edit_text81a);

        if(mMetersEdit1.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for 'Metres'");
        }

        if(mMetersEdit2.length() < 1)
        {
            display_dialog_cif11.Showing("Please enter value for 'CM'");
        }

        else
        {
            double Meters = convertFrmMeters2CM(mMetersEdit1, mMetersEdit2);
            CM.setText(new RoundingCIF13().DoubleToString(Meters));
            Log.d("functionMeters1",new RoundingCIF13().IntToString((int)Meters));
        }

    }

    private double convertFrmCM2CM(EditText IN)
    {
        float cm = new RoundingCIF13().StringToFloat(IN.getText().toString());
        return (double) cm;

    }

    private double convertFrmInches2CM(EditText IN)
    {
        float cm = new RoundingCIF13().StringToFloat(IN.getText().toString());
        cm = cm * (float)2.54;
        return (double) cm;

    }

    private double convertFrmFeet2CM(EditText IN1, EditText IN2)
    {
        float cm1 = new RoundingCIF13().StringToFloat(IN1.getText().toString());
        cm1 = cm1 * (float)30.48;
        float cm2 = new RoundingCIF13().StringToFloat(IN2.getText().toString());
        cm2 = cm2 * (float)2.54;

        return (double)(cm1 + cm2);

    }

    private double convertFrmMeters2CM(EditText IN1, EditText IN2)
    {
        float cm1 = new RoundingCIF13().StringToFloat(IN1.getText().toString());
        cm1 = cm1 * (float)100.00;
        float cm2 = new RoundingCIF13().StringToFloat(IN2.getText().toString());
        cm2 = cm2 * (float)1.0;

        return (double)(cm1 + cm2);

    }

}
/*ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__weight__loss__activity_cif14);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start__weight__loss__activity_cif14, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
*/