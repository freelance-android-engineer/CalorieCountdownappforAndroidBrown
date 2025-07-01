package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;

/**
 * Created by ESE on 03/03/2016.
 * This Class Packages together all the elements that form a unit transaction used
 * by the app to represent a Meal consumed by the User.
 */
public class BoxCIF17
{
    public ArrayList<Food_Item_CIF4> mFoodBox;
    private String mMeal = "Breakfast";
    private long mMeal_ID = 1;
    private int Meal_Box_ID = 1;
    private long Transaction_ID = 1;
    private int Balance = 1;
    private int Energy_OUT = 1;
    private String inhousecsvtext;

    public BoxCIF17()
    {
        mFoodBox = new ArrayList<Food_Item_CIF4>();
        //mFoodBox.add(new Food_Item_CIF4());
        mMeal =  "Food Item Box";
        inhousecsvtext = "data/inhousecsv.txt";
    }

    public BoxCIF17(String mealname)
    {
        mFoodBox = new ArrayList<Food_Item_CIF4>();
        mMeal =  mealname;
        inhousecsvtext = "data/inhousecsv.txt";
    }

    public void addFoodItem(Food_Item_CIF4 m)
    {
       mFoodBox.add(m);
    }

    public int SumBox()
    {
        int sumly = 1;
        for(Food_Item_CIF4 e : mFoodBox )
        {
            sumly = sumly + e.Get_calorie_value();
        }

        return sumly;
    }

    public Food_Item_CIF4 removeFoodItem(String name)
    {
        int sumly = 0;
        for(Food_Item_CIF4 e : mFoodBox)
        {
            if(name == e.Get_food_item_name())
            {
                return mFoodBox.get(sumly);
            }
            sumly++;
        }
        return new Food_Item_CIF4();
    }

    public void Set_Balance(int set)
    {
        Balance = set;
    }

    public int Get_Balance()
    {
        return Balance;
    }

    public void add_Meal_Box_ID(int ID)
    {
        Meal_Box_ID = ID;
    }

    public void Set_Transaction_ID(long ID) { Transaction_ID = ID;}

    public long Get_Transaction_ID() { return Transaction_ID;}

    public ArrayList<Food_Item_CIF4> Get_Food_Items()
    {
        return mFoodBox;
    }

    public void Set_Food_Items(ArrayList<Food_Item_CIF4> IN)
    {
        mFoodBox = IN;
    }

    public void Set_Meal_Type(String breakfast)
    {
        mMeal = breakfast;
    }

    public String Get_Meal_Type()
    {
        return mMeal;
    }

    public void Set_Energy_OUT(int out)
    {
        Energy_OUT = out;
    }

    public int Get_Energy_OUT()
    {
        return Energy_OUT;
    }

    public String Get_data_file_path()
    {
        return inhousecsvtext;
    }

    public void Set_data_file_path(String IN)
    {
        inhousecsvtext = IN;
    }

    public String Get_inhousecsvtext()
    {
        return inhousecsvtext;
    }

    public void Set_inhousecsvtext(String IN)
    {
        inhousecsvtext = IN;
    }



    public void Set_Transaction_ID_for_all_Food_items(long ID)
    {
        //Algorithm Engineering Black : Do exactly what it says on the tin.

        for(Food_Item_CIF4 f : mFoodBox)
        {
            f.Set_Meal_Box_ID(ID);
        }
    }

    public void Set_Meal_Box_ID(long INPUT)
    {
        mMeal_ID = INPUT;

    }

    public long Get_Meal_Box_ID()
    {
        return Transaction_ID;
    }

    public String Print()
    {
        if(mFoodBox.size() > 0)
        {
            return mFoodBox.get(0).Get_food_item_name();
        }
        else
        {
            return "Meal Box empty/. www.ese-edet.eu";
        }
    }

    public String dePopulate()
    {
        for(Food_Item_CIF4 m : mFoodBox)
        {
            inhousecsvtext = inhousecsvtext + m.dePopulate();
        }

        return inhousecsvtext;

    }

    public String amalgamate()
    {
        return dePopulate();
    }





}
