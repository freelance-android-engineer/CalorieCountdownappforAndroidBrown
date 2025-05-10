package ese.com.caloriecountdownappforandroidbrown;

import java.util.ArrayList;

/**
 * Created by ESE on 07/10/2015.
 */
public class Food_Item_CIF4
{

    //yours plus + Partner myfitnesspal absorb (to Logo) attribute
    //Green i
    //Name of Food or  Item	Calories per 100g or 100ml 	Grams in a Serving/Portion	Carbohydrates	of which are Sugars	Protein	Fat 	of which are saturated Fat	Polyunsaturated	Monounsaturated	Trans	Cholesterol (mg)	Sodium (mg)	Salt (g)	Potassium (mg)	Fibre	Sugars	Vitamin A (%)	Vitamin C (%)	Calcium (%)	Iron (%)

    private long id;
    private long mMeal_ID;
    private String food_type;
    private String food_item_name;
    private float grams_per_serving_portion;
    private float calories_per_100g;
    private float fat_per_100g;
    private float saturated_fat;
    private float trans_fat;
    private float protein_per_100g;
    private float carbs_per_100g;
    private float sugar_per_100g;
    private float salt_per_100g;
    private boolean wellbeing_index;
    private float fiber;
    private float price_sterling;
    private float polyunsaturated;
    private float monounsaturated;
    private float cholesterol_mg;
    private float sodium_mg;
    private float potassium_mg;
    private float vitamin_a_percent;
    private float vitamin_c_percent;
    private float calcium_percent;
    private float iron_percent;
    private String category;
    private int weight;
    private int Calorie_Value;
    private int index;

    private int Quantity;

    private ArrayList<String> reserve_food_item_name_list;
    private long barcode;
    private String image_file;


    public Food_Item_CIF4(String name) {
        id = 7777777;
        food_type = "ING";
        food_item_name = name;
        grams_per_serving_portion = 4;
        calories_per_100g = 4;
        fat_per_100g = 4;
        saturated_fat = 4;
        trans_fat = 4;
        protein_per_100g = 4;
        carbs_per_100g = 4;
        sugar_per_100g = 4;
        salt_per_100g = 4;
        wellbeing_index = false;
        fiber = 4;
        price_sterling = 4;
        polyunsaturated = 4;
        monounsaturated = 4;
        cholesterol_mg = 4;
        sodium_mg = 4;
        potassium_mg = 4;
        vitamin_a_percent = 4;
        vitamin_c_percent = 4;
        calcium_percent = 4;
        iron_percent = 4;
        category = "ING";
        weight = 4;
        Calorie_Value = 4;
        Quantity = 0;

        reserve_food_item_name_list = new ArrayList<String>();
        reserve_food_item_name_list.add(food_item_name);
        barcode = 1010101011;
        image_file = "data/image_src_" + name;
    }

    public Food_Item_CIF4()
    {
        id = 0;
        food_type ="empty";
        food_item_name = "empty";
        grams_per_serving_portion = 0;
        calories_per_100g = 0;
        fat_per_100g = 0;
        saturated_fat = 0;
        trans_fat = 0;
        protein_per_100g = 0;
        carbs_per_100g = 0;
        sugar_per_100g = 0;
        salt_per_100g = 0;
        wellbeing_index = false;
        fiber = 0;
        price_sterling = 0;
        polyunsaturated = 0;
        monounsaturated = 0;
        cholesterol_mg = 0;
        sodium_mg = 0;
        potassium_mg = 0;
        vitamin_a_percent = 0;
        vitamin_c_percent = 0;
        calcium_percent = 0;
        iron_percent = 0;
        category = "empty";
        weight = 0;
        Calorie_Value = 0;

        Quantity = 0;

        reserve_food_item_name_list = new ArrayList<String>();
        reserve_food_item_name_list.add("empty");
        barcode = 1010101011;
        image_file = "data/image_src_" + food_item_name;
    }




    //public Bundle onCreate()

    //Get Functions

    public long Get_id()
    {
        return id;
    }
    public String Get_food_type()
    {
        return food_type;
    }
    public String Get_food_item_name()
    {
        return food_item_name;
    }
    public float Get_grams_per_serving_portion()
    {
        return grams_per_serving_portion;
    }
    public float Get_calories_per_100g()
    {
        return calories_per_100g;
    }
    public float Get_fat_per_100g()
    {
        return fat_per_100g;
    }
    public float Get_saturated_fat()
    {
        return saturated_fat;
    }
    public float Get_trans_fat()
    {
        return trans_fat;
    }
    public float Get_protein_per_100g()
    {
        return protein_per_100g;
    }
    public float Get_carbs_per_100g()
    {
        return carbs_per_100g;
    }
    public float Get_sugar_per_100g()
    {
        return sugar_per_100g;
    }
    public float Get_salt_per_100g()
    {
        return salt_per_100g;
    }
    public boolean Get_wellbeing_index()
    {
        return wellbeing_index;
    }
    public float Get_fiber()
    {
        return fiber;
    }
    public float Get_price_sterling()
    {
        return price_sterling;
    }
    public float Get_polyunsaturated()
    {
        return polyunsaturated;
    }
    public float Get_monounsaturated()
    {
        return monounsaturated;
    }
    public float Get_cholesterol_mg()
    {
        return cholesterol_mg;
    }
    public float Get_sodium_mg()
    {
        return sodium_mg;
    }
    public float Get_potassium_mg()
    {
        return potassium_mg;
    }
    public float Get_vitamin_a_percent()
    {
        return vitamin_a_percent;
    }
    public float Get_vitamin_c_percent()
    {
        return vitamin_c_percent;
    }
    public float Get_calcium_percent()
    {
        return calories_per_100g;
    }
    public float Get_iron_percent()
    {
        return iron_percent;
    }
    public String Get_category()
    {
        return category;
    }
    public int Get_weight(){return weight;}
    public int Get_calorie_value(){return Calorie_Value;}

    public int Get_Quantity(){return Quantity;}
    public long Get_Barcode(){return barcode;}
    public String Get_Image_File(){return image_file;}

    public ArrayList<String> getReserve_food_item_name_list() {
        return reserve_food_item_name_list;
    }

    public ArrayList<String> setResrve_food_item_name_list(){
        return reserve_food_item_name_list;
    }
    // Set Functions

    public void Set_id(long z)
    {
        id = z;
    }

    public void Set_food_type(String a)
    {
        food_type = a;
    }
    public void Set_food_item_name(String b)
    {
        food_item_name = b;

        reserve_food_item_name_list.add(b);
    }

    public void Set_reserve_food_item_name_list(ArrayList IN)
    {
        reserve_food_item_name_list = IN;
    }
    public void Set_grams_per_serving_portion(float c)
    {
        grams_per_serving_portion = c;
    }
    public void Set_calories_per_100g(float d )
    {
        calories_per_100g = d;
    }
    public void Set_fat_per_100g(float e)
    {
        fat_per_100g = e;
    }
    public void Set_saturated_fat(float f)
    {
        saturated_fat = f;
    }
    public void Set_trans_fat(float g)
    {
        trans_fat = g;
    }
    public void Set_protein_per_100g(float h)
    {
        protein_per_100g = h;
    }
    public void Set_carbs_per_100g(float i)
    {
        carbs_per_100g = i;
    }
    public void Set_sugar_per_100g(float j)
    {
        sugar_per_100g = j;
    }
    public void Set_salt_per_100g(float k)
    {
        salt_per_100g = k;
    }
    public void Set_wellbeing_index(boolean l)
    {
        wellbeing_index = l;
    }
    public void Set_fiber(float m)
    {
        fiber = m;
    }
    public void Set_price_sterling(float n)
    {
        price_sterling = n;
    }
    public void Set_polyunsaturated(float o)
    {
        polyunsaturated = o;
    }
    public void Set_monounsaturated(float p)
    {
        monounsaturated = p;
    }
    public void Set_cholesterol_mg(float q)
    {
        cholesterol_mg = q;
    }
    public void Set_sodium_mg(float r)
    {
        sodium_mg = r;
    }
    public void Set_potassium_mg(float s)
    {
        potassium_mg = s;
    }
    public void Set_vitamin_a_percent(float t)
    {
        vitamin_a_percent = t;
    }
    public void Set_vitamin_c_percent(float u)
    {
        vitamin_c_percent = u;
    }
    public void Set_calcium_percent(float v)
    {
        calcium_percent = v;
    }
    public void Set_iron_percent(float w)
    {
        iron_percent = w;
    }
    public void Set_category(String x)
    {
        category = x;
    }
    public void Set_weight(int y){weight = y;}
    public void Set_calorie_value(int in){Calorie_Value = in;}

    public void Set_Quantity(int IN){Quantity = IN;}

    public void Make_calorie_value(){Calorie_Value = (int) ((calories_per_100g * (float) weight))/100;}

    public void Set_Meal_Box_ID(long INPUT)
    {
        mMeal_ID = INPUT;
    }

    public long Get_Meal_Box_ID()
    {
        return mMeal_ID;
    }

    public void Set_Barcode(long Input){barcode = Input;}

    public void Set_Image_File(String Input){image_file = Input;}

    @Override
    public String toString()
    {
        return Get_food_item_name();
    }

    public int Get_Index()
    {
        return index;
    }

    public void Set_Index(int INPUT)
    {
        index = INPUT;
    }

    public String dePopulate()
    {
        return new String("ING,Tesco Cornish Pasty,200,234,31.5,,,9,31.5,2.3,1.25,N,1.5,2\n +");
    }

    public boolean hasQuantiyBeenChecked()
    {
        return false;
    }

    public boolean goToNextItem()
    {
        return false;
    }





    /*****
     *"ING,Tesco Cornish Pasty,200,234,31.5,,,9,31.5,2.3,1.25,N,1.5,2\n" +
     *       "ING,Cadbury egg,100,170,6,,,,,,,N,,\n" +
     *       "ING,Lindt Chocolate bunny solid 1.75 ounces,100,298,18,,,,,,,N,,\n" +
     *       "ING,Dove dark chocolate eggs 4,100,148,9.2,,,,,,,N,,\n"
     ***/
}

