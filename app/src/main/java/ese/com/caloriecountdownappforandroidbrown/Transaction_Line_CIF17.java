package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;

/**
 * Created by ESE on 09/03/2017.
 */
public class Transaction_Line_CIF17 extends Transaction_CIF52  //This fragmented object represents a transaction line in the Monthly Statement print out.
{
    private long mTransaction_ID;
    private long mTransaction_Date;
    private String mTransaction_Meal_Type;
    private int mTransaction_Meal_Type_ID;
    private int mTransaction_Amount;
    private int mTransaction_Balance;
    private int mTransaction_Meal_Box_ID;
    private String mTransaction_Meal_Box_Food_Type;
    public BoxCIF17 mfood_item_list;
    private String Description;

    private Breakfast_Transaction_CIF22 food_item_list;

    private int mCalorie_IN;
    private int Calorie_OUT;
    private int Countdown_BALANCE; //Balance Brought FORWARD.
    private String data_file_path;

    private Food_Item_CIF4 dummy_rice_4;

    public Transaction_Line_CIF17()
    {
        mTransaction_Date = new java.util.Date().getTime();

        mTransaction_ID = 7777777;

        mTransaction_Meal_Type = "C17runch";

        mTransaction_Amount = 1;

        mTransaction_Balance = 1;

        mTransaction_Meal_Box_ID = 1;

        mTransaction_Meal_Box_Food_Type = "Breakfast";

        mfood_item_list = new BoxCIF17("Stringy");

        dummy_rice_4 = new Food_Item_CIF4();

        dummy_rice_4.Set_category("ING");
        dummy_rice_4.Set_food_item_name("INTERNAL DUMMY");
        dummy_rice_4.Set_grams_per_serving_portion((float)100.00);
        dummy_rice_4.Set_calories_per_100g((float)445.00);
        dummy_rice_4.Set_fat_per_100g((float)5.00);
        dummy_rice_4.Set_carbs_per_100g((float)5.00);
        dummy_rice_4.Set_protein_per_100g((float)50);

        mfood_item_list.addFoodItem(dummy_rice_4);

        Description = "Food Items Eaten : ";

        mCalorie_IN = 1;
        Calorie_OUT = 0;
        Countdown_BALANCE = 1000;

        data_file_path = "data/inhousecsv.txt";

        food_item_list = new Breakfast_Transaction_CIF22();
    }

    public Transaction_Line_CIF17(String mealname)
    {
        super(mealname);
    }
    //public ID(){;}

    public Transaction_Line_CIF17(Nutritional_Content_CiFragmented_Object001 IN)
    {
        mTransaction_Date = new java.util.Date().getTime();

        mTransaction_ID = 7777777;

        mTransaction_Meal_Type = "C17runch";

        mTransaction_Amount = 1;

        mTransaction_Balance = 1;

        mTransaction_Meal_Box_ID = 1;

        mTransaction_Meal_Box_Food_Type = "Brunch";

        mfood_item_list = new BoxCIF17("Stringy");

        dummy_rice_4 = new Food_Item_CIF4();

        dummy_rice_4.Set_category(IN.getTransactionsCif22().getCategory());
        dummy_rice_4.Set_food_item_name(IN.getTransactionsCif22().getFood_item_name());
        dummy_rice_4.Set_grams_per_serving_portion((float) IN.getTransactionsCif22().getGrams_per_serving_portion()); //if in grams else something else
        dummy_rice_4.Set_calories_per_100g((float) IN.getTransactionsCif22().getCalories()); //Yeah

        dummy_rice_4.Set_fat_per_100g((float) IN.getTransactionsCif22().getFat());

        dummy_rice_4.Set_saturated_fat((float) IN.getTransactionsCif22().getSaturated_fat());

        dummy_rice_4.Set_protein_per_100g((float) IN.getTransactionsCif22().getProtein());

        dummy_rice_4.Set_carbs_per_100g((float) IN.getTransactionsCif22().getCarbohydrates());

        dummy_rice_4.Set_sugar_per_100g((float) IN.getTransactionsCif22().getSugar());

        dummy_rice_4.Set_salt_per_100g((float) IN.getTransactionsCif22().getSoduimorsalt());

        dummy_rice_4.Set_wellbeing_index(IN.getTransactionsCif22().getWellbeing_index());

        dummy_rice_4.Set_fiber((float) IN.getTransactionsCif22().getFiber());

        dummy_rice_4.Set_price_sterling(IN.getTransactionsCif22().getPrice_sterling());

        dummy_rice_4.Set_Barcode(IN.getTransactionsCif22().getBarcode());



        mfood_item_list.addFoodItem(dummy_rice_4);
        Description = "Food Items Eaten : ";
        mCalorie_IN = 1;
        Calorie_OUT = 0;
        Countdown_BALANCE = 1000;

        data_file_path = "data/inhousecsv.txt";

        food_item_list = new Breakfast_Transaction_CIF22();
    }

    public long Get_Transaction_ID()
    {
        return mTransaction_ID;
    }

    public void Set_Transaction_ID(long IN)
    {
        mTransaction_ID = IN;
    }

    public long Get_Transaction_Meal_Box_ID()
    {
        return mTransaction_ID;
    }

    public void Set_Transaction_Meal_Box_ID(int in)
    {
        in = mTransaction_Meal_Box_ID;
    }


    public java.util.Date Get_Transaction_DateX()
    {
        return new java.util.Date(mTransaction_Date);
    }

    public void Set_Transaction_DateX(long in)
    {
        mTransaction_Date = in;
    }

    public String Get_Transaction_Meal_Type()
    {
        return mTransaction_Meal_Type;
    }

    public void Set_Transaction_Meal_Type(String in)
    {
        mTransaction_Meal_Type = in;
    }

    public long Get_Transaction_Meal_Type_ID()
    {
        return mTransaction_Meal_Type_ID;
    }

    public void Set_Transaction_Meal_Type_ID(int in)
    {
        mTransaction_Meal_Type_ID = in;
    }

    public int Get_Transaction_Amount()
    {
        return mTransaction_Amount;
    }

    public void Set_Transaction_Amount(int in)
    {
        mTransaction_Amount = in;
    }

    public void add_Food_item(Food_Item_CIF4 IN)
    {
        mfood_item_list.addFoodItem(IN);
    }

    public String Get_Description()
    {
        for(Food_Item_CIF4 m : mfood_item_list.Get_Food_Items())
        {
            Description = Description;
        }
        return Description;
    }

    public void Set_Description(String IN){Description = IN;}

    public void Set_Calorie_IN(int IN){mCalorie_IN = IN;}
    public int Get_Calorie_IN(){return mCalorie_IN;}

    public void Set_Calorie_Out(int Out){Calorie_OUT = Out;}
    public int Get_Calorie_Outwards(){return Calorie_OUT;};
    public void Set_Calorie_Outwards(int Out){Calorie_OUT = Out;}


    public int Get_Transaction_Balance()
    {
        return mTransaction_Balance;
    }

    public void Set_Transaction_Balance(int in)
    {
        mTransaction_Balance = in;
    }

    public String Get_Transaction_Meal_Box_Food_Type()
    {
        return mTransaction_Meal_Box_Food_Type;
    }

    public void Set_Transaction_Meal_Box_Food_Type(String in)
    {
        in = mTransaction_Meal_Box_Food_Type;
    }

    public String Get_data_file_path()
    {
        return data_file_path;
    }

    public void Set_data_file_path(String IN)
    {
        data_file_path = IN;
    }

    public BoxCIF17 Get_Transaction_Food_Items(){ return mfood_item_list;};

    public void Set_Transaction_Food_Items(BoxCIF17 in){ mfood_item_list = in;}

    public String Get_New_Balance()
    {
        return new String("31,787");
    }

    public void Set_New_Balance(int IN)
    {

    }

    public String Get_Calorie_OUT()
    {
        return new String("0");
    }

    public void Set_Calorie_OUT()
    {

    }

    public java.util.Date Get_Date(long IN)
    {
        java.util.Date date = new java.util.Date(IN);
        return date;
    }

    public void Set_Food_Item_List(ArrayList<Food_Item_CIF4> IN)
    {
        Set_Transaction_Food_Items(Transform2BoxCIF(IN));
    }

    public Breakfast_Transaction_CIF22 Get_Breakfast_Tranasction_CiF22_food_item_list()
    {
        return food_item_list;
    }

    public void Set_Breakfast_Transaction_CiF22_food_item_list(Breakfast_Transaction_CIF22 IN)
    {
        food_item_list = IN;
    }

    public Object Get_Dummy_Rice()
    {
        return null;
    }

    public void Set_Dummy_Rice(Object neil)
    {
        dummy_rice_4 = null;
    }

    private BoxCIF17 Transform2BoxCIF(ArrayList<Food_Item_CIF4> IN)
    {
        //Documentation
        //Algorithm Engineering -> ANDRIOD
        //Short Description : Takes in as Input an array of Food_items.
        //Transforms and inserts all correctly into a BoxCIF like for like.
        //Spit out and return as OUTPUT.

        //Step 1)
        BoxCIF17 the_Box = new BoxCIF17();
        the_Box.Set_Food_Items(IN);
        //more
        return the_Box;
    }

    public String Print_Food_items()
    {
        String Statement = "Apple" + "\n" + " Oats" + "\n" + "Generic";
        return Statement;
    }

}
