package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;

/**
 * Created by ESE on 16/09/2016.
 */
public class Breakfast_Box_CIF17 extends BoxCIF17
{
    private long Breakfast_ID;
    private long Breakfast_Date;
    private String Breakfast_Meal_Type;
    private long Breakfast_Meal_Type_ID;
    private int Breakfast_Amount;
    private int Breakfast_Balance;
    private int Meal_Box_ID;
    private String Meal_Box_Food_Type;
    private BoxCIF17 food_item_list;
    public ArrayList<Food_Item_CIF4> food_item_list_two;
    private String Description = "Meal eaten";
    private int Calorie_IN;
    private int Calorie_OUT;
    private int Countdown_BALANCE; //Balance Brought FORWARD.

    public Breakfast_Box_CIF17()
    {
        Breakfast_ID = 7777777;
        Breakfast_Date = new java.util.Date().getTime();
        Breakfast_Meal_Type = "Brunch";
        Breakfast_Meal_Type_ID = 2;
        Breakfast_Amount = 500;
        Breakfast_Balance = 2500;
        Meal_Box_ID = 3;
        Meal_Box_Food_Type = "Brunch";
        BoxCIF17 food_item_list = new BoxCIF17();
        food_item_list_two = new ArrayList<Food_Item_CIF4>();
        Description = "Meal eaten";
        Calorie_IN = 100;
        Calorie_OUT = 1; //Equilance to Energy_ItemCIF6 remember me? ahhhhh
        Countdown_BALANCE = 555; //Balance Brought FORWARD.

    }

    public Breakfast_Box_CIF17(String mealname)
    {
        super(mealname);
    }

    public long Get_Breakfast_ID()
    {
        return Breakfast_ID;
    }

    public long Get_Transactions_ID() { return Breakfast_ID; }

    public void Set_Transactions_ID(Long INPUT) { Breakfast_ID = INPUT;}

    public void Set_Breakfast_ID(long in)
    {
        Breakfast_ID = in;
    }

    public long Get_Meal_Box_ID()
    {
        return Meal_Box_ID;
    }

    public void Set_Meal_Box_ID(long in)
    {
        in = Meal_Box_ID;
    }

    public long Get_Breakfast_Date()
    {
        return Breakfast_Date;
    }

    public void Set_Breakfast_Date(long in)
    {
        Breakfast_Date = in;
    }

    public String Get_Breakfast_Meal_Type()
    {
        return Breakfast_Meal_Type;
    }

    public void Set_Breakfast_Meal_Type(String in)
    {
        Breakfast_Meal_Type = in;
    }

    public long Get_Breakfast_Meal_Type_ID()
    {
        return Breakfast_Meal_Type_ID;
    }

    public void Set_Breakfast_Meal_Type_ID(long in)
    {
        Breakfast_Meal_Type_ID = in;
    }

    public int Get_Breakfast_Amount()
    {
        return Breakfast_Amount;
    }

    public void Set_Breakfast_Amount(int in)
    {
        Breakfast_Amount = in;
    }

    public int Get_Breakfast_Balance()
    {
        return Breakfast_Balance;
    }

    public void Set_Breakfast_Balance(int in)
    {
        Breakfast_Balance = in;
    }

    public void Set_Food_Item_List_two(ArrayList<Food_Item_CIF4> INPUT) { food_item_list_two = INPUT;}

    public ArrayList<Food_Item_CIF4> Get_Food_Item_List_two() { return food_item_list_two;}

    public String Get_Meal_Box_Food_Type()
    {
        return Meal_Box_Food_Type;
    }

    public void Set_Meal_Box_Food_Type(String in)
    {
        in = Meal_Box_Food_Type;
    }

    public ArrayList<Food_Item_CIF4> Get_Food_Items(){ return mFoodBox;};

    public void Set_Food_Items(ArrayList<Food_Item_CIF4> in){ mFoodBox = in;}

    public void Set_Calories_IN(int IN)
    {
        Calorie_IN = IN;
    }

    public int Get_Calories_IN()
    {
        return Calorie_IN;
    }

    public void Set_Calories_OUT(int out)
    {
        Calorie_OUT = out;
    }

    public int Get_Calories_OUT()
    {
        return Calorie_OUT;
    }

    public void add_Food_items(Food_Item_CIF4 INPUT)
    {
        mFoodBox.add(INPUT);
    }




}
