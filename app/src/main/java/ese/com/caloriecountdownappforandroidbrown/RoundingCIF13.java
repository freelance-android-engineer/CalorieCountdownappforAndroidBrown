package ese.com.caloriecountdownappforandroidbrown;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by ESE on 28/01/2016.
 */
public class RoundingCIF13
{
    private String HH = "00";
    private String MM = "00";
    private Calendar Kalends;

    public RoundingCIF13()
    {

    }


    public String FloatToString(float in)
    {
        return Float.toString(in);
    }


    public String IntToString(int in)
    {
        return Integer.toString(in);
    }

    public String LongToString(long in) { return Long.toString(in);}

    public long StringToLong(String in){return Long.parseLong(in);}

    public double StringToDouble(String in){return Double.parseDouble(in);}

    public String BoolToString(boolean INPUT)
    {
        if(INPUT)
        {
            return "1";
        }
        else
        {
            return "0";
        }
    }

    public boolean StringToBool(String in)
    {
        if(in == "true")
        {
            return true;
        }

        if(in == "True")
        {
            return true;
        }

        if(in == "Y")
        {
            return true;
        }

        if(in == "y")
        {
            return true;
        }

        if(in == "N")
        {
            return false;
        }

        if(in == "n")
        {
            return false;
        }

        if(in == "false")
        {
            return false;
        }

        if(in == "False")
        {
            return false;
        }

        if(in == "TRUE")
        {
            return true;
        }

        if(in == "FALSE")
        {
            return false;
        }

        if(in == "TRuE")
        {
            return true;
        }

        if(in == "TrUE")
        {
            return true;
        }

        if(in == "TruE")
        {
            return true;
        }

        if(in == "truE")
        {
            return true;
        }

        if(in == "TRUe")
        {
            return true;
        }

        else
        {
            return false;
        }


    }

    public int FloatToInt(float in)
    {
        return (int) in;
    }

    public float StringToFloat(String in)
    {
        return Float.parseFloat(in);
    }

    public int StringToInt(String in)
    {
        if(DoesInputHaveACommaQuestionMark(in))
        {
           in = StripComma(in,3);
           return Integer.parseInt(in);
        }
        else
            {
            return Integer.parseInt(in);
            }
    }

    public float DoubleToFloat(double in)
    {
        return (float) in;
    }

    public int DoubleToInt(double in)
    {
        return (int) in;
    }

    public String DoubleToString(double in)
    {
        return Double.toString(in);
    }

    public double FloatToDouble(float in)
    {
        return (double) in;
    }

    public java.util.Date StringToDate(String in)
    {
        //Format we are expecting is HH:MM:DoM:M:Yr
        if(CheckHHMMSSFormat(in))
        {
            //if not recieved display message
            //have theses checks in place before puting dates in in Start Weight Loss and  Data Model
            Stripe(in);
            int hour = new RoundingCIF13().StringToInt(HH);
            int minute = new RoundingCIF13().StringToInt(MM);

            Kalends = Calendar.getInstance();
            Kalends.setTimeInMillis(System.currentTimeMillis() + 180000);
            Kalends.set(Calendar.HOUR, hour);
            Kalends.set(Calendar.MINUTE, minute);

            return Kalends.getTime();
        }

        else
        {
            long out = new RoundingCIF13().StringToLong(in);
            Date datum = new Date(out);
            return datum;
        }

    }


    public String DateToStringStandard(java.util.Date in)
    {

        CalenderHelperClass helper = new CalenderHelperClass(in);

        int minute = helper.getMinute();
        int hour = helper.getHour();
        int day_of_month = helper.getDayOfMonth();
        int month = helper.getMonth();
        int year = helper.getYear();

        String out = LongToString(in.getTime());
        String esedotcomformat = this.IntToString(hour) + ";" + this.IntToString(minute) + ";" + this.IntToString(day_of_month) + ";" + this.IntToString(month)+ ";" + this.IntToString(year);
        String esedotcomformatlenient = this.IntToString(hour) + ";" + this.IntToString(minute);
        return out;
    }

    public String DateToStringeseformat(java.util.Date in)
    {

        CalenderHelperClass helper = new CalenderHelperClass(in);

        int minute = helper.getMinute();
        int hour = helper.getHour();
        int day_of_month = helper.getDayOfMonth();
        int month = helper.getMonth();
        int year = helper.getYear();

        String out = in.toString();
        String esedotcomformat = this.IntToString(hour) + ";" + this.IntToString(minute) + ";" + this.IntToString(day_of_month) + ";" + this.IntToString(month)+ ";" + this.IntToString(year);
        String esedotcomformatlenient = this.IntToString(hour) + ";" + this.IntToString(minute);
        return esedotcomformat;
    }

    public String DateToStringLenient(java.util.Date in)
    {

        CalenderHelperClass helper = new CalenderHelperClass(in);

        int minute = helper.getMinute();
        int hour = helper.getHour();
        int day_of_month = helper.getDayOfMonth();
        int month = helper.getMonth();
        int year = helper.getYear();

        String out = in.toString();
        String esedotcomformat = this.IntToString(hour) + ";" + this.IntToString(minute) + ";" + this.IntToString(day_of_month) + ";" + this.IntToString(month)+ ";" + this.IntToString(year);
        String esedotcomformatlenient = this.IntToString(hour) + ";" + this.IntToString(minute);
        return esedotcomformatlenient;
    }

    public float RoundTwoDecimals(float in)
    {
        float enter = in;
        enter = in * 100;
        int round = (int) enter;
        enter = (float) round;
        return enter/100;
    }

    private boolean CheckHHMMSSFormat(String form)
    {
        return false;
    }

    private void Stripe(String in)
    {
        java.util.StringTokenizer tokenizer = new StringTokenizer(in,":");
        HH = (String) tokenizer.nextElement();
        MM = (String) tokenizer.nextElement();

    }

    private boolean DoesInputHaveACommaQuestionMark(String INPUT)
    {
        return false;
    }

    private String StripComma(String INPUT, int pos)
    {
        return INPUT;
    }


}
