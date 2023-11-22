package ese.com.caloriecountdownappforandroidbrown;

/**
 * Created by lokeke on 26/02/2016.
 */




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
 * CiF name = Mif3BMIfunction
 * Blackboard Account Allocated to = BL16231
 * Number of Blackboard Point Worth = 2 points plus 1 point per testcase. (Maximum 3 testcases)
 * Implementation Deadline = February 15 2007
 */

public class Mif3BMIFunction {


    public HealthProfileCiF3 BMIfunction(HealthProfileCiF3 bmidata)
    {

        try {
            double ht = 0;
            if (bmidata.getHieghtUnits() == "CM") {
                // 1cm = .393700787 inches


                try {
                    ht = bmidata.getClientHeightinCM();//((double) Double.parseDouble(bmidata.getClientHeightinCM()));
                    ht = ht * 0.393700787;
                    ht = this.round(ht);
                    bmidata.setClientHeight(Double.toString(ht));
                    bmidata.setClientHeight(bmidata.getClientHeight());
                } catch (Exception dm) {
                    ht = 0;
                    //IDO ~> Alogrithm Engineering ~> Android : Dialog("Error with number format");
                    Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                    display_dialog_cif11.WrongNumberFormatMsg();
                    //See what happens.

                }
            }

            if (bmidata.getHieghtUnits() == "Feet and Inches") {
                // 1cm = .393700787 inches


                try {
                    String tht = bmidata.getClientHeight();
                    String ht1 = tht.substring(0);
                    String ht2 = tht.substring(2,(tht.length()-1));

                    double hta = Double.parseDouble(ht1);
                    hta = hta * 30;

                    double htb = Double.parseDouble(ht2);
                    htb = htb / 0.39370087;
                    hta = hta + htb;
                    int cm = (int) hta;

                    bmidata.setClientHeightinCM(cm);//((double) Double.parseDouble(bmidata.getClientHeightinCM()));
                    ht = bmidata.getClientHeightinCM();
                    ht = ht * 0.393700787;
                    ht = this.round(ht);
                    bmidata.setClientHeight(Double.toString(ht));
                    bmidata.setClientHeight(bmidata.getClientHeight());
                } catch (Exception dm) {
                    ht = 0;
                    //IDO ~> Alogrithm Engineering ~> Android : Dialog("Error with number format");
                    Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                    display_dialog_cif11.WrongNumberFormatMsg();
                    //See what happens.

                }
            }

            //double h = Double.parseDouble(bmidata.getClientHeightImperial()); //height in inches, standard unit
            double w = Double.parseDouble(bmidata.getCurrentWeightImperial()); // weight in pounds, standard unit
            if (bmidata.getWeightUnits() == "pounds") {
                ;
            }
            double bmi = ((w / (ht * ht)) * 703);
            bmi = round(bmi);
            bmidata.setClientBMI(bmi);

            IdealBMICiF19 ideal = new IdealBMICiF19();
            bmidata.setIdealBMI(ideal.GetIdealWeight(bmidata.getClientHeightImperial())); //gives you ideal wieght, use 22 for ideal bmi

            //Underweight = <18.5
            //Normal weight = 18.5-24.9
            //Overweight = 25-29.9
            //Obesity = BMI of 30 or greater


            return bmidata;
        }
        catch(Exception e)
        {
            android.util.Log.d("app","Probably Number format error");
            bmidata.setClientHeight("175");
            return bmidata;
        }

    }


    public HealthProfileCiF3 BMIfunction2(HealthProfileCiF3 bmidata)
    {
        return bmidata;
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
 * 1. This is an Mif that takes in the CiF HealthProfileCiF3 and outputs a HealthProfileCiF3 containing the BMI value of the client, this value is
 *    displayed in the pre comence countdown information on the screen and should also be able to be called up by a menuitem (eO blue sum of value of menuitems should ex
 * ceed £9.98, menuitems also source of the 3000 food items database, all menuitems, once menuitems loaded to www.ese-edet.eu blue ready for b*g g =  value = £9.98 * b = 1003 b*g = £9.98 * 1003
 *  b*g = £10,009.94, (all on Track please) Rex (re)ad must to stop reinventing stop Sales Campaign close with ACCA Blue all this after CFF QVM right and Approved for Sale [Campaign] Track exe. Fast Track time exe = numbers 1,2,3 Fast = exe = time
 *                                                              www.ese-edet.eu -> £10,009.94 (Streamline Merchant Account)
 * )
 *
 * So BMI value for the Client (that > www.ese-edet.eu) is stored in HealthProfileCiF3 which is then stored with CountdownCalendar and also stored in RMI database. HealthProfile
 * CiF3 should also contain intial start countdown number.
 * 2. This mif used that data stored in HealthProfileCiF3 to calculate BMI index
 * 2b. Answer also stored in HealthProfileCiF3
 * 3. Also use this Mif to use the data in Heal to calculate Body Analysis data so that eO black increase effciency, and makes other Mif redundants.
 *
 4. In alert screen with Next Show if above or below proper BMI hence overall health recommend appr wigith but ask them to chose their target in Sets

 5.Body Analysier Mif Function. Take in data from this Form,
 Use to calculate Body Analyser
 Display Current in Alert form




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
