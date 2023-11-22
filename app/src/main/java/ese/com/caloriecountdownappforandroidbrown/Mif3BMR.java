package ese.com.caloriecountdownappforandroidbrown;

/**
 * Created by lokeke on 26/02/2016.
 */
/**
 * <p>Title: Calorie Countdown app</p>
 *
 * <p>Description: Weight Loss app</p>
 *
 * <p>Copyright: Copyright (c) 2018</p>
 *
 * <p>Company: ESE </p>
 *
 * @author ESE
 * @version 2.0
 *
 * CD = CCDGUI-CD (Calorie Countdown MIDlet)
 * Menu = Options
 * Menuitem = Activate Countdown
 *
 *
 * CiF name = Mif3BMIfunction
 * Blackboard Account Allocated to = BL16231
 * Number of Blackboard Point Worth = 2 points plus 1 point per testcase. (Maximum 3 testcases)
 * Implementation Deadline = Translate to PeoplePerHour ESE ANDRIOD BLACKBOARD
 *
 *
 *
 * BMR calculation for men (metric)	BMR = 66.5 + ( 13.75 × weight in kg ) + ( 5.003 × height in cm ) – ( 6.755 × age in years )
 * BMR calculation for men (imperial)	BMR = 66 + ( 6.2 × weight in pounds ) + ( 12.7 × height in inches ) – ( 6.76 × age in years )
 * BMR calculation for women (metric)	BMR = 655.1 + ( 9.563 × weight in kg ) + ( 1.850 × height in cm ) – ( 4.676 × age in years )
 * BMR calculation for women (imperial)	BMR = 655.1 + ( 4.35 × weight in pounds ) + ( 4.7 × height in inches ) - ( 4.7 × age in years )
 *
 *
 *
 *
 */

public class Mif3BMR {
    public Mif3BMR()
    {
    }

    public HealthProfileCiF3 BMR(HealthProfileCiF3 bmidata)
    {




        String gender = bmidata.getClientGender();
        android.util.Log.d("BMR : gender",gender);
        String iHeight = bmidata.getClientHeightImperial();
        android.util.Log.d("BMR : height",iHeight);
        String iWeight = bmidata.getCurrentWeightImperial();
        android.util.Log.d("BMR : Weight",iWeight);
        String Hunits = bmidata.getHieghtUnits();
        android.util.Log.d("BMR : Height units",Hunits);
        String Wunits = bmidata.getWeightUnits();
        android.util.Log.d("BMR : Height units",Wunits);
        int dateofbirth = new RoundingCIF13().StringToInt(bmidata.GetClientAge());
        android.util.Log.d("BMR : DOB",new RoundingCIF13().IntToString(dateofbirth));

        double Height = (double) bmidata.getHeightm();//Double.parseDouble(iHeight);
        double Weight = Double.parseDouble(iWeight);
        double age = dateofbirth;
        double BMR = 2200;

        if(bmidata.getHieghtUnits() == "CM")
        {
            double hieghtIN = (double) new RoundingCIF13().StringToFloat(bmidata.getClientHeight());
            Height = (double) new RoundingCIF13().StringToFloat(bmidata.getClientHeight());

            android.util.Log.d("new height units" , bmidata.getHieghtUnits());
            android.util.Log.d("new height " , bmidata.getClientHeight());


        }

        if(bmidata.getHieghtUnits() == "Feet and Inches")
        {
            double hieghtIN = (double) bmidata.getHeightm();
            Height = (double) (bmidata.getHeightm() * 100);

            android.util.Log.d("new height units" , bmidata.getHieghtUnits());
            //android.util.Log.d("new height " , bmidata.getClientHeight());


        }

        if(Wunits == "pounds")
        {
            Weight = Weight / 2.2;
            android.util.Log.d("BMR Weight pounds : ", new RoundingCIF13().DoubleToString(Weight));
        }

        if(Wunits == "kilograms")
        {
            Weight = Weight; //assuming weight in pounds for some reason
            Height = Height;

            android.util.Log.d("BMR Weight KG: ", new RoundingCIF13().DoubleToString(Weight));
            android.util.Log.d("BMR Height KG : ", new RoundingCIF13().DoubleToString(Height));
        }

        if(Wunits == "meters")
        {
            ;
        }

        if(gender == "Female")
        {
            BMR = 655 + ( 9.6 * Weight) + ( 1.8 * Height ) - ( 4.7 * age );
        }

        if(gender == "Male")
        {
            android.util.Log.d("Final Check WeightU ", bmidata.getWeightUnits());
            android.util.Log.d("Final Check Weight: ", new RoundingCIF13().DoubleToString(Weight));
            android.util.Log.d("Final Check Height: ", new RoundingCIF13().DoubleToString(Height));
            android.util.Log.d("Final Check age: ", new RoundingCIF13().DoubleToString(dateofbirth));



            android.util.Log.d("BMR : Inside", "We are in Male");
            BMR = 66 + ( 13.75 * Weight ) + ( 5 * Height ) - ( 6.8 * age  );
        }



        BMR = round(BMR);

        bmidata.setBMR((BMR));


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
 * 1. This Mif calculates the amount of calories Client needs burns if they are inactive.
 * 1b. It takes in a HealthProfileCiF which should contain the information it needs which is:
 *      Gender
 *      Age
 *      Height
 *      Weight
 * 2. It uses this data in a formula used to calculate the BMR, the BMR is then put in the
 *    HealthProfileCiF3 and outputed.
 *
 *3. This is formula: English BMR Formula
 Women: BMR = 655 + ( 4.35 x weight in pounds ) + ( 4.7 x height in inches ) - ( 4.7 x age in years )
 Men: BMR = 66 + ( 6.23 x weight in pounds ) + ( 12.7 x height in inches ) - ( 6.8 x age in year )

 Metric BMR Formula
 Women: BMR = 655 + ( 9.6 x weight in kilos ) + ( 1.8 x height in cm ) - ( 4.7 x age in years )
 Men: BMR = 66 + ( 13.7 x weight in kilos ) + ( 5 x height in cm ) - ( 6.8 x age in years )

 *
 *The Discovery Health Channel's website has a BMR calculator
 which does the calculation for you. Just enter your gender,
 age, height and weight and hit Enter. This will be how many
 calories you'd burn if you were essentially inactive all day:
 http://health.discovery.com/tools/calculators/basal/basal.html

 About.com has another calculator that takes into account your
 level of activity:
 http://walking.about.com/cs/calories/l/blcalcalc.htm

 Entering the same data into the latter calculator as in the
 first provides a higher figure for how many calories you need
 to consume just to maintain your weight at the 'sedentary'
 activity level. For me, it calculated 2078 vs 1659.7 on the
 first one. The Discovery calculator seems more likely to be
 accurate, as I can maintain my weight with much less than
 2078 calories/day.

 Some differences can be expected, depending on the ratio of
 fat to muscle, as a higher percentage of muscle in the body
 will burn more calories than a lower one. Muscle tissue has
 the advantage of burning off calories 24/7, even while we
 sleep.

 The About page notes that:

 "If your goal is to lose weight by burning off excess body fat,
 aim to eat 500 fewer calories per day than your daily caloric
 needs, and maintain or increase your exercise activity."


 DietBites.com has a page that notes, more specifically:

 "It takes 3,500 calories to equal one pound of body weight.
 Generally, dieters dump an average of 1,000 calories per day
 in an effort to lose 2 pounds of body fat per week."
 http://www.dietbites.com/diet-pound.html


 Stevens Creek Software has a page with an even more detailed
 and useful calculator:
 http://www.stevenscreek.com/goodies/calories.shtml

 Plugging in the same data as in the first two, and entering
 sleeping as my activity level for 24 hours, it calculates
 my BMR at 1826, and separates out the calories burned from
 activity (sleeping) at 821 calories, for a total of 2648.

 That still seems a bit high, but the calculator on this page
 lets you also enter your actual levels of daily activity and
 then lets you enter your intended exercises (running, cycling
 or swimming, or any other activity, for which you can look up
 and enter the calories burned per hour), to see what the final
 outcome will be.

 It will add the calories burned by your exercise routine and
 calculate the total calories burned for the day. It also says
 that you need to burn 3500 calories (over your intake) to lose
 one pound. It also provides you (and lets you change) the
 number of calories per mile per pound used by the more common
 forms of exercise.

 To lose 3 pounds in a week, then, you need to burn 10,500 more
 calories than you consume within the same week, taking into
 account the amount of calories consumed by your basal rate,
 your normal daily activities and your intended exercise
 routine.

 Note that losing too much weight too quickly can be rough on
 the system, and some health experts recommend setting your
 sights on the goal of 2-3 pounds per month over an extended
 period of time. If you find you can lose more than that with
 no unpleasant effects on your system, then go for it, but if
 you feel stressed when you attempt to lose more than 2-3 lbs
 per month, it's probably wise to scale back. Also note that
 you shouldn't consume less than 1200 calories per day without
 medical supervision.

 See this article on dieting from nbc17.com

 "The calorie level of your diet should allow for a weight
 loss of no more than 1 pound per week (after the first week
 or two when weight loss may be more rapid because of initial
 water loss)."
 http://www.nbc17.com/health/3555485/detail.html

 That page refers to this excellent article on the site of
 the National Institute of Health's Weight Control Information
 Network, titled, 'Weight Loss for Life':
 http://win.niddk.nih.gov/publications/for_life.htm


 All of the calculators above take into account that your basal
 metabolic rate decreases as you age - you burn fewer calories
 at rest as you get older. This can be offset somewhat by
 increasing your muscle mass, so, believe it or not, some form
 of weightlifting is a good thing for elderly people.

 I think that speaks well to your stated question, but if
 anything's unclear, please post a Request for Clarification
 before rating this answer.

 sublime1-ga


 Additional information may be found from an exploration of
 the links resulting from the Google searches outlined below.

 Searches done, via Google:

 basal metabolic rate
 ://www.google.com/search?q=basal+metabolic+rate

 calories pounds
 ://www.google.com/search?q=calories+pounds

 "recommended rate of weight loss "pounds per month
 ://www.google.com/search?q=%22recommended+rate+of+weight+loss+%22pounds+per+month

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
