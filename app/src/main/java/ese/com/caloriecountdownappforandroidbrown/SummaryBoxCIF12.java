package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ESE on 10/11/2015.
 */
public class SummaryBoxCIF12
{

    private ArrayList<Food_Item_CIF4> mFoodItems;
    private Fitness_Item_CIF5 mFitnessItems;

    private static SummaryBoxCIF12 sSummaryBox;
    private Context mAppContext;

    private String mCurrentBalance;
    private int mNewBalance;
    private String tab = "\t\t\t";
    private String newline = "\n\n";
    private String totalListings;
    private String totaltitle;
    private String totaltitle2;
    private String total;
    private String title;
    private String description;
    private String description2;
    private String amount;
    private String amount2;
    private String newbalancetitle;
    private String oldbalancetitle;
    private String newbalance;
    private MIF22MonthlyStatement mFoodDiary;


    private SummaryBoxCIF12(Context appContext)
    {
        mFitnessItems = new Fitness_Item_CIF5();
        mFoodItems = new ArrayList<Food_Item_CIF4>();
        mCurrentBalance = "N/A";
        mNewBalance = 0;
        totalListings = "N/A";
        totaltitle = "Total";
        totaltitle2 = "Total Calories Burnt";
        total = "N/A";
        title = "Countdown Report";
        description = "Food Item Name";
        description2 = "Activity Done";
        amount = "Amount (calories)";
        amount2 = "Number of Minutes";
        newbalancetitle = "New [Countdown] Balance";
        oldbalancetitle = "Current Balance";
        newbalance = "N/A";
        mAppContext = appContext;
    }

    public static SummaryBoxCIF12 get(Context c)
    {
        if(sSummaryBox == null)
        {
            sSummaryBox = new SummaryBoxCIF12(c.getApplicationContext());

        }
        return sSummaryBox;
    }
    //public SummaryBoxCIF12(ArrayList<Food_Item_CIF4> food_item_list, ArrayList<Fitness_Item_CIF5> fitness_item_list)
    //{
        //Completed Data to Summarise and update app with, divisible IDO and lines and breakdown.
        //see notes and IDO (all notes to here) string like account list with item name and calories debit or credit value with CiF1 methods a
        //accepting string to display and debit or credit value exe
        //updateUI, done -> www.ese-edet.eu almost reinvented wheel again trust and (re)ad

      //  mFoodItems = food_item_list;
      //  mFitnessItems = fitness_item_list;
   // }




    public ArrayList<Food_Item_CIF4> Get_mFoodItems()
    {
        return mFoodItems;
    }

    public void Add_Fooditem(Food_Item_CIF4 INPUT)
    {
        mFoodItems.add(INPUT);
    }

    public void Set_mFitnessItems(Fitness_Item_CIF5 in){ mFitnessItems = in;}


    public void Set_mFoodItems(ArrayList<Food_Item_CIF4> in)
    {
        mFoodItems = in;

        for(Food_Item_CIF4 f : mFoodItems)
        {
            android.util.Log.d("SummaryBox"," What we have in : Fooditem name" + f.Get_food_item_name());
            android.util.Log.d("SummaryBox", " What we have in : 100 Calories" + new RoundingCIF13().FloatToString(f.Get_calories_per_100g()));
            android.util.Log.d("SummaryBox"," What we have in : weight" + new RoundingCIF13().FloatToString(f.Get_weight()));
            android.util.Log.d("SummaryBox"," What we have in : Calorie Value" + new RoundingCIF13().FloatToString(f.Get_calorie_value()));

        }
    }

    public Fitness_Item_CIF5 Get_mFitnessItems()
    {
        return mFitnessItems;
    }


    public String GetDebitSummaryString()
    {
        String out =
                oldbalancetitle +
                        tab +
                        Get_mCurrentBalance() +
                        newline +
                        description2 +
                        tab +
                        amount2 +
                        newline +
                        GetTotalFitnessItemsListing(tab) +
                        newline +
                        totaltitle2 +
                        tab +
                        GetListingsTotalCaloriesOUT() +
                        newline +
                        newbalancetitle +
                        tab +
                        GetNewBalance2();


        return out;
    }

    public String GetSummaryString()
    {
        //Format is like ACCA so :



        String out =
                     oldbalancetitle +
                     tab +
                     Get_mCurrentBalance() +
                     newline +
                     description +
                     tab +
                     amount +
                     newline +
                     GetTotalFoodItemsListing(tab) +
                     newline +
                     totaltitle +
                     tab +
                     GetListingsTotalCaloriesIN() +
                     newline +
                     newbalancetitle +
                     tab +
                     GetNewBalance();


                     return out;

    }

    public String Get_mCurrentBalance()
    {
        return mCurrentBalance;
    }

    public void Set_mCurrentBalance(String in)
    {
        mCurrentBalance =  in;
    }



    public int GetDebitValue()
    {
        return 100;
    }

    public int GetCreditValue()
    {
        return 100;
    }

    private String GetTotalFoodItemsListing(String t)
    {
        String tab = t;
        String newline = "\n";


        if(mFoodItems == null)
        {
            totalListings = "Listings Not Avaliable";
            return totalListings;
        }
        else
        {
            totalListings = newline;
            for(Food_Item_CIF4 fooditem : mFoodItems)
            {
                totalListings = totalListings + fooditem.Get_food_item_name() + tab  + fooditem.Get_calorie_value() + newline;
            }

            return totalListings;
        }

    }

    private String GetTotalFitnessItemsListing(String t)
    {
        String tab = t;
        String newline = "\n";


        if(mFitnessItems == null)
        {
            totalListings = "Listings Not Avaliable";
            return totalListings;
        }

        if(mFitnessItems.getmNameOfActivity() == "Steps")
        {

                totalListings = newline;

                totalListings = totalListings + mFitnessItems.getmNameOfActivity() + tab  + mFitnessItems.getmMinutesPerformed() + " Calories" + newline;


                return totalListings;

        }
        else
        {
            totalListings = newline;

                totalListings = totalListings + mFitnessItems.getmNameOfActivity() + tab  + mFitnessItems.getmMinutesPerformed() + " minutes" + newline;


            return totalListings;
        }

    }
    public String GetListingsTotalCaloriesOUT()
    {
        return new RoundingCIF13().IntToString(mFitnessItems.CalculateCountdown());
    }

    public String GetListingsTotalCaloriesIN()
    {
        int totaling = 0;

        if(mFoodItems == null)
        {
            return null;
        }
        else
        {
            for(Food_Item_CIF4 fooditem : mFoodItems)
            {
                totaling = totaling + fooditem.Get_calorie_value();
            }

            return total = new RoundingCIF13().IntToString(totaling);
        }
    }

    public String GetNewBalance()
    {
        if("N/A".equals(mCurrentBalance))
        {
            return mCurrentBalance;
        }
        else
        {
            try {
                String caloriesIn = GetListingsTotalCaloriesIN();
                int balance = new RoundingCIF13().StringToInt(mCurrentBalance);
                int calories = (caloriesIn != null) ? new RoundingCIF13().StringToInt(caloriesIn) : 0;
                mNewBalance = balance + calories;
                return new RoundingCIF13().IntToString(mNewBalance);
            } catch (NumberFormatException e) {
                android.util.Log.e("SummaryBox", "GetNewBalance NFE: mCurrentBalance='" + mCurrentBalance + "': " + e.getMessage());
                return mCurrentBalance;
            }
        }

    }

    public String GetNewBalance2()
    {
        if("N/A".equals(mCurrentBalance))
        {
            return mCurrentBalance;
        }
        else
        {
            try {
                int med = new RoundingCIF13().StringToInt(mCurrentBalance);
                med = med - (new RoundingCIF13().StringToInt(GetListingsTotalCaloriesOUT()));
                mNewBalance = med;
                return new RoundingCIF13().IntToString(med);
            } catch (NumberFormatException e) {
                android.util.Log.e("SummaryBox", "GetNewBalance2 NFE: mCurrentBalance='" + mCurrentBalance + "': " + e.getMessage());
                return mCurrentBalance;
            }
        }

    }

    public String GetSummaryString1()
    {
        return " Yeah the Problem is that return Food Diary String is way to Large to Display and Bind.";
    }

    public void reset()
    {
        mFoodItems.clear();
    }


}


