package ese.com.caloriecountdownappforandroidbrown;

import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import java.util.*;
import java.lang.*;

import java.util.ArrayList;

/**
 * Created by ESE on 07/10/2015.
 */
public class SQLDatabase_Food_Items_CIF6 extends SQLiteOpenHelper {
    //Create a global for populate that is checked if true of false or better
    // create a check if database populated private function
    // var knowledge time explosion job interview get knowledge

    private int Unique_ID = 0;

    private final String TAG = " SQLite App Data";

    private static final String DB_NAME = "food_items.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_FOODITEMS = "food_items";


    private static final String TABLE_COUNTDOWN_BALANCE = "countdown_balances";
    private static final String COLUMN_COUNTDOWN_BALANCE_ID = "balance_id";
    private static final String COLUMN_COUNTDOWN_BALANCE_DATE = "balance_date";
    private static final String COLUMN_COUNTDOWN_BALANCE_BALANCE = "balance_balance";


    private static final String TABLE_SEX = "sex";
    private static final String COLUMN_SEX_ID = "sex_id";
    private static final String COLUMN_SEX_SEX = "sex_sex";


    private static final String TABLE_DAYEND_BALANCE = "dayend_balance";
    private static final String COLUMN_DAYEND_ID = "dayend_id";
    private static final String COLUMN_DAYEND_BALANCE_DATE = "balance_date";
    private static final String COLUMN_DAYEND_BALANCE_BALANCE = "balance_dayend";


    private static final String TABLE_BREAKFAST_TIME = "breakfast_time";
    private static final String COLUMUM_BREAKFAST_TIME_ID = "breakfast_time_id";
    private static final String COLUMUM_BREAKFAST_TIME_BREAKFASTTIME = "breakfast_time";


    private static final String TABLE_LUNCH_TIME = "lunch_time";
    private static final String COLUMUM_LUNCH_TIME_ID = "lunch_time_id";
    private static final String COLUMUM_LUNCH_TIME_LUNCHTIME = "lunch_time";


    private static final String TABLE_FINAL_MEAL_TIME = "final_meal";
    private static final String COLUMUM_FINAL_MEAL_TIME_ID = "final_meal_item_id";
    private static final String COLUMUM_FINAL_MEAL_TIME_FINALMEATIME = "final_meal_time";


    private static final String TABLE_CURRENT_WEIGHT = "current_weight";
    private static final String COLUMUM_CURRENT_WEIGHT_ID = "current_weight_id";
    private static final String COLUMUM_CURRENT_WEIGHT_CURRENTWEIGHT = "current_weight";


    private static final String TABLE_TARGET_WEIGHT = "target_weight";
    private static final String COLUMUM_TARGET_WEIGHT_ID = "target_weight_id";
    private static final String COLUMUM_TARGET_WEIGHT_TARGETWEIGHT = "target_weight";


    private static final String TABLE_START_WEIGHT = "start_weight";
    private static final String COLUMUM_START_WEIGHT_ID = "start_weight_id";
    private static final String COLUMUM_START_WEIGHT_STARTWEIGHT = "start_weight";

    private static final String TABLE_DAYEND_COUNTDOWN_LAST_SESSIONS = "dayend_countdown_last_session";
    private static final String COLUMUM_DAYEND_COUNTDOWN_LAST_SESSIONS_ID = "dayend_countdown_last_sessions_id";
    private static final String COLUMUM_DAYEND_COUNTDOWN_LAST_SESSIONS_DATETIME = "countdownlastsessions_datetime";
    private static final String COLUMUM_DAYEND_COUNTDOWN_LAST_SESSIONS_DAYENDCOUNTDOWNLASTSESSIONS = "dayend_last_session";


    private static final String TABLE_NUMBER_OF_DAYS = "number_of_days";
    private static final String COLUMUM_NUMBER_OF_DAYS_ID = "number_of_days_id";
    private static final String COLUMUM_NUMBER_OF_DAYS_NUMBEROFDAYS = "number_of_days";


    private static final String TABLE_REMINDER_STATUS = "reminder_status";
    private static final String COLUMUM_REMINDER_STATUS_ID = "reminder_status_id";
    private static final String COLUMUM_REMINDER_STATUS_BOOLEANE = "reminder_status";

    private static final String TABLE_REMINDER_STATUSS = "reminder_statuss";
    private static final String COLUMUM_REMINDER_STATUSS_ID = "reminder_statuss_id";
    private static final String COLUMUM_REMINDER_STATUSS_BOOLEANE = "reminder_statuss";


    private static final String TABLE_START_DAY = "start_day";
    private static final String COLUMUM_START_DAY_ID = "start_day_id";
    private static final String COLUMUM_START_DAY_STARTDAY = "start_day";


    private static final String TABLE_OPENING_BALANCE = "account _open_balance";
    private static final String COLUMUM__OPENING_BALANCE_ID = "opening_balance_id";
    private static final String COLUMUM__OPENING_BALANCE_STARTCOUNTDOWN = "opening_balance";


    private static final String TABLE_TRANSACTIONS_TABLE = "transactions_xp";
    private static final String COLUMN_TRANSACTIONS_ID = "Column_Transactions_id";
    private static final String COLMUM_TRANSACTIONS_DATE = "Date";
    private static final String COLUMUM_TRANSACTIONS_MEAL_TYPE = "Meal_Type";
    private static final String COLUMUM_TRANSACTIONS_MEAL_TYPE_ID = "Meal_Type_id";
    private static final String COLUMUM_TRANSACTIONS_AMOUNT = "Amount";
    private static final String COLUMUM_TRANSACTIONS_BALANCE = "Balance";

    private static final String TABLE_BREAKFAST_TRANSACTIONS_TABLE = "breakfast_transaction";
    private static final String COLUMN_BREAKFAST_ID = "Column_Breakfast_id";
    private static final String COLMUM_BREAKFAST_DATE = "Date";
    private static final String COLUMUM_BREAKFAST_MEAL_TYPE = "Meal_Type";
    private static final String COLUMUM_BREAKFAST_MEAL_TYPE_ID = "Meal_Type_id";
    private static final String COLUMUM_BREAKFAST_AMOUNT = "Amount";
    private static final String COLUMUM_BREAKFAST_BALANCE = "Balance";

    private static final String TABLE_LUNCH_TRANSACTIONS_TABLE = "lunch_transaction";
    private static final String COLUMN_LUNCH_ID = "Column_Lunch_id";
    private static final String COLMUM_LUNCH_DATE = "Date";
    private static final String COLUMUM_LUNCH_MEAL_TYPE = "Meal_Type";
    private static final String COLUMUM_LUNCH_MEAL_TYPE_ID = "Meal_Type_id";
    private static final String COLUMUM_LUNCH_AMOUNT = "Amount";
    private static final String COLUMUM_LUNCH_BALANCE = "Balance";

    private static final String TABLE_DINNER_TRANSACTIONS_TABLE = "dinner_transaction";
    private static final String COLUMN_DINNER_ID = "Column_Dinner_id";
    private static final String COLMUM_DINNER_DATE = "Date";
    private static final String COLUMUM_DINNER_MEAL_TYPE = "Meal_Type";
    private static final String COLUMUM_DINNER_MEAL_TYPE_ID = "Meal_Type_id";
    private static final String COLUMUM_DINNER_AMOUNT = "Amount";
    private static final String COLUMUM_DINNER_BALANCE = "Balance";

    private static final String TABLE_MEAL_BOX_ITEMS_TABLE = "meal_box_items";
    private static final String COLUMN_MEAL_BOX_ID = "Column_Meal_Box_id";
    private static final String COLUMUM_MEAL_BOX_MEAL_TYPE = "Meal_Type";
    private static final String COLUMUM_MEAL_BOX_MEAL_TYPE_ID = "Meal_Type_id";
    private static final String COLUMN_MEAL_BOX_FOOD_TYPE = "food_type";
    private static final String COLUMN_MEAL_BOX_FOOD_ITEM_NAME = "food_item_name";
    private static final String COLUMN_MEAL_BOX_GRAMS_PER_SERVING_PORTION = "grams_per_serving_portion";
    private static final String COLUMN_MEAL_BOX_CALORIE_PER_100G = "calories_per_100g";
    private static final String COLUMN_MEAL_BOX_FAT_PER_100G = "fat_per_100g";
    private static final String COLUMN_MEAL_BOX_SATURATED_FAT = "saturated_fat";
    private static final String COLUMN_MEAL_BOX_TRANS_FAT = "trans_fat";
    private static final String COLUMN_MEAL_BOX_PROTEIN_PER_100G = "protein_per_100g";
    private static final String COLUMN_MEAL_BOX_CARBS_PER_100G = "carbs_per_100g";
    private static final String COLUMN_MEAL_BOX_SUGAR_PER_100G = "sugar_per_100g";
    private static final String COLUMN_MEAL_BOX_SALT_PER_100G = "salt_per_100g";
    private static final String COLUMN_MEAL_BOX_WELLBEING_INDEX = "wellbeing_index";
    private static final String COLUMN_MEAL_BOX_FIBER = "fiber";
    private static final String COLUMN_MEAL_BOX_PRICE_STERLING = "price_sterling";
    private static final String COLUMN_MEAL_BOX_POLYUNSATURATED = "polyunsaturated";
    private static final String COLUMN_MEAL_BOX_MONOUNSATURATED = "monounsaturated ";
    private static final String COLUMN_MEAL_BOX_CHOLESTEROL_MG = "cholesterol_mg";
    private static final String COLUMN_MEAL_BOX_SODIUM_MG = "sodium_mg";
    private static final String COLUMN_MEAL_BOX_POTASSIUM_MG = "potassium_mg";
    private static final String COLUMN_MEAL_BOX_VITAMIN_A_PERCENT = "vitamin_a_percent";
    private static final String COLUMN_MEAL_BOX_VITAMIN_C_PERCENT = "vitamin_c_percent";
    private static final String COLUMN_MEAL_BOX_CALCIUM_PERCENT = "calcium_percent";
    private static final String COLUMN_MEAL_BOX_IRON_PERCENT = "iron_percent";
    private static final String COLUMN_MEAL_BOX_CATEGORY = "category";

    private static final String TABLE_DAY_OBJECT_TABLE = "Day_Object";
    private static final String COLUMN_DAY = "Date";
    private static final String COLUMN_TRANSACTION_IDS = "all_CiF_17_JSON_wrapped_transaction_ids_for_this_date";


    private static final String COLUMN_FOODITEMS_ID = "food_item_id";
    private static final String COLUMN_FOODITEMS_FOOD_TYPE = "food_type";
    private static final String COLUMN_FOODITEMS_FOOD_ITEM_NAME = "food_item_name";
    private static final String COLUMN_FOODITEMS_GRAMS_PER_SERVING_PORTION = "grams_per_serving_portion";
    private static final String COLUMN_FOODITEMS_CALORIE_PER_100G = "calories_per_100g";
    private static final String COLUMN_FOODITEMS_FAT_PER_100G = "fat_per_100g";
    private static final String COLUMN_FOODITEMS_SATURATED_FAT = "saturated_fat";
    private static final String COLUMN_FOODITEMS_TRANS_FAT = "trans_fat";
    private static final String COLUMN_FOODITEMS_PROTEIN_PER_100G = "protein_per_100g";
    private static final String COLUMN_FOODITEMS_CARBS_PER_100G = "carbs_per_100g";
    private static final String COLUMN_FOODITEMS_SUGAR_PER_100G = "sugar_per_100g";
    private static final String COLUMN_FOODITEMS_SALT_PER_100G = "salt_per_100g";
    private static final String COLUMN_FOODITEMS_WELLBEING_INDEX = "wellbeing_index";
    private static final String COLUMN_FOODITEMS_FIBER = "fiber";
    private static final String COLUMN_FOODITEMS_PRICE_STERLING = "price_sterling";
    private static final String COLUMN_FOODITEMS_POLYUNSATURATED = "polyunsaturated";
    private static final String COLUMN_FOODITEMS_MONOUNSATURATED = "monounsaturated ";
    private static final String COLUMN_FOODITEMS_CHOLESTEROL_MG = "cholesterol_mg";
    private static final String COLUMN_FOODITEMS_SODIUM_MG = "sodium_mg";
    private static final String COLUMN_FOODITEMS_POTASSIUM_MG = "potassium_mg";
    private static final String COLUMN_FOODITEMS_VITAMIN_A_PERCENT = "vitamin_a_percent";
    private static final String COLUMN_FOODITEMS_VITAMIN_C_PERCENT = "vitamin_c_percent";
    private static final String COLUMN_FOODITEMS_CALCIUM_PERCENT = "calcium_percent";
    private static final String COLUMN_FOODITEMS_IRON_PERCENT = "iron_percent";
    private static final String COLUMN_FOODITEMS_CATEGORY = "category";



    private static final String TABLE_HEALTH_PROFILE_TABLE = "health_profile";
    private static final String COLUMN_VITALS_ID = "vitals_id";
    private static final String COLUMN_Client_ACCOUNT_NAME = "client_account_name";
    private static final String COLUMN_Client_DOB = "client_dob";
    private static final String COLUMN_Client_BMI = "client_bmi";
    private static final String COLUMN_Client_BODYFAT = "client_bodyfat";
    private static final String COLUMN_Client_BMR = "client_bmr";
    private static final String COLUMN_Client_VITALS_STRING ="client_vitals_string";
    private static final String COLUMN_Client_OPENING_BALANCE = "client_opening_balance";
    private static final String COLUMN_Client_HEIGHT_CM = "client_height_cm";
    private static final String COLUMN_Client_EMAIL = "client_email";
    private static final String COLUMN_Client_GENDER = "client_gender";
    private static final String COLUMN_Client_START_WEIGHT = "client_start_weight";
    private static final String COLUMN_Client_TARGET_WEIGHT = "client_target_weight";
    private static final String COLUMN_Client_BODY_FRAME = "client_body_frame";
    private static final String COLUMN_Client_START_DATE = "client_start_date";
    private static final String COLUMN_Client_LEAN_BODY_MASS = "client_lean_body_mass";
    private static final String COLUMN_Client_WAIST_CIRCUMFERENCE = "client_waist_circumference";
    private static final String COLUMN_Client_BLOOD_PRESSURE = "client_blood_pressure";
    private static final String COLUMN_Client_BODY_TEMPERATURE = "client_body_temperature";
    private static final String COLUMN_Client_ALCOHOL_CONTENT = "client_alcohol_content";
    private static final String COLUMN_Client_BLOOD_GLUCOSE = "client_blood_glucose";
    private static final String COLUMN_Client_ECG_ = "client_ECG";
    private static final String COLUMN_Client_HEART_RATE_VARIABILITY = "client_heart_rate_variability";
    private static final String COLUMN_Client_HIGH_HEART_RATE_NOTIFICATION = "client_high_heart_rate_notification";
    private static final String COLUMN_Client_IRREGULAR_HEART_RHYTHM_NOTIFICATION = "client_irregular_heart_rhythm_notification";
    private static final String COLUMN_Client_LOW_HEART_RATE_NOTIFICATION = "client_low_heart_rate_notification";
    private static final String COLUMN_Client_WALKING_HEART_RATE = "client_walking_heart_rate";
    private static final String COLUMN_Client_HEART_RATE = "client_heart_rate";
    private static final String COLUMN_Client_RESTING_HEART_RATE = "client_resting_heart_rate";
    private static final String COLUMN_Client_RESPIRATORY_RATE = "client_respiratory_rate";
    private static final String COLUMN_Client_ELECTRODERMAL_ACTIVITY = "client_electrodermal_activity";
    private static final String COLUMN_Client_FORCED_EXPIRATORY_VOLUME = "client_forced_expiratory_volume";
    private static final String COLUMN_Client_FORCED_VITAL_CAPACITY = "client_forced_vital_capacity";
    private static final String COLUMN_Client_INHALER_USAGE = "client_inhaler_usage";
    private static final String COLUMN_Client_INSULIN_DELIVERY = "client_insulin_delivery";
    private static final String COLUMN_Client_OXYGEN_SATURATION = "client_oxygen_saturation";
    private static final String COLUMN_Client_PEAK_EXPIRATORY_FLOW_RATE = "client_peak_expiratory_flow_rate";
    private static final String COLUMN_Client_UV_INDEX = "client_uv_index";
    private static final String COLUMN_Client_PERIPHERAL_PERFUSION_INDEX = "client_peripheral_perfusion_index";
    private static final String COLUMN_Client_DATA_SOURCES = "client_data_sources";
    private static final String COLUMN_Client_MANUAL_SOURCES = "client_manual_sources";


    private static final String TABLE_CACHE_TABLE = "cache";
    //Here Check if the Food item the User is entering has already been entered and updated successfully in the past.
    //Every time the User Enters a successful transaction, that same transaction is also entered to the Cache table
    //Before in is entered check that it is not already existing a copy of it, Exact copy? Similar Copy?
    //Wen Satisfied, enter.
    //Next time when we are Fetch first match transaction/food item name to all Cache entries
    //If happy that a found Cache entry is right return as fetched.
    //So Cache Box fragment function should always be done First.
    //Really saves time and increase app's value.

    //Step 1
    //Create Cache Table (cache_table)
    //Cache Table should look exactly like the existing Food and Exercise Items, just a Copy of this table but with a different name to it.
    //That is the Food items ID Table
    //Create a new Table for Exercise items already in the String Resources
    //Exercise items should inherit for Energy_iten or Calorie_Value item
    //Have a Corresponding Table for each of these
    //Identical
    //Find the Attributes that both Food item and Exercise item share
    //Both should inherit either or from Energy_item/Calorie_Value item, here in this table is where
    //...you find their Core items.

    //This is how the app is able to search for Food and/or Exercise Debit items in EXACTLY THE SAME TABLE
    //Now that you have exactly the same table use this to perform Cache

    //Call this Unifying Table A
    //For Table A go through all the Rows
    //For each row, use a fragemented_box to INPUT Row OUTPUT a data object frag_box
    //Define very well and properly, individually this data object frag_box till Completion
    //This data object frag_box must/can be marked as either a a)First Row type, b) Last_Row type, c) Neither
    //If a) First Row type : the output string method stored in it has the Characteritic of outputng a String in the same...
    //...same format as the first line of the inhousecsvtext.csv file.
    //Example :
    //If type b) same thing but same format st the last line of the inhousecsv.csv file
    //Example :
    //If type c) then all look the same
    //Example :
    //
    //You can have various fragment dataset of data
    //if joining multiple dataset to form one single dataset have a fragment_Box Object INPUT/OUTPUT for this
    //INPUT :1,2 or more dataset of data OUTPUT one single correct inhousecsv like dataset...
    //...making sure the first row of string is in the correct format is first row example
    // same for last row, everything else in norml formet but the rest of between first/last format lines
    // are striped away set to normal by a break_down fragment_box Object
    //Change markings of affected Rows/Data object in appropiate properties so that it's "normal"
    //
    //Depopulate OUTPUT : new inhousetxt.csv format String.
    //Write the exact contents of this String/inhousetxt.csv to the depopulate database content of Table A that mirror...
    //... that mirrors intial/original inhousetxt.csv file but with addition of any and all new food/exercise items...
    //...items the User must have added in the Course of using the app to explain away correctly and checked the difference between
    //...the original inhousetxt.csv and the total content of Table A
    //the new inhousetxt.csv file should exist and mirror original inhousetxt.csv in the same content/format plus additions when viewed.
    //(re)Load -> www.ese-edet.e
    //Transit & Green Office. & Builder+
    //END OF DOCUMENTATION

    //New frag_box2 INPUT above frag_box OUTPUT : String

    //This String houses the exact same format as the inhousetext.csv file
    //Data Object must/can be

    private static final String TABLE_SLEEP_TABLE = "sleep_data";
    private static final String COLUMN__ID = "sleep_id";
    private static final String COLUMN_DATE = "night_date";
    private static final String COLUMN_NUMBER_OF_HOUR_SLEPT = "number_of_hours_slept";


    private static final String TABLE_ACTIVITY_TABLE = "steps_hours";
    private static final String COLUMN_STEPS_ID = "steps_id";
    private static final String COLUMN_STEPS_DATE = "step_date";
    private static final String COLUMN_NUMBER_OF_STEPS = "number_of_steps";
    private static final String COLUMN_NUMBER_OF_FLOORS_ClIMBED = "number_of_floors_climbed";
    private static final String COLUMN_DISTANCE_WALKED = "distance_walked";
    private static final String COLUMN_CALORIES_BURNT = "number_of_steps";
    private static final String COLUMN_EXERCISE_MINUTES = "number_of_steps";


    private static final String TABLE_MINDFULNESS_TABLE = "mindfulness_minutes";
    private static final String COLUMN_MINDFULNESS_ID = "mindfulness_id";
    private static final String COLUMN_MINDFULNESS_DATE = "night_date";
    private static final String COLUMN_MINDFULNESS_MINUTES = "mindfulness_minutes";

    private static final String TABLE_NUTRITION_TABLE = "nutrition_biotin_table";
    private static final String COLUMN_NUTRITION_ID = "nutrition_id";
    private static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_BIOTIN = "boitin";
    private static final String COLUMN_CAFFEINE = "caffeine";
    private static final String COLUMN_CALCIUM = "Calcium";
    private static final String COLUMN_CARBOHYDRATES = "Carbohydrates";
    private static final String COLUMN_CHLORIDE = "Chloride";
    private static final String COLUMN_CHROMIUM = "Chromium";
    private static final String COLUMN_COPPER = "Copper";
    private static final String COLUMN_DIETARY_CHOLESTEROL = "Dietary Cholesterol";
    private static final String COLUMN_DIETARY_ENERGY = "Dietary Energy";
    private static final String COLUMN_DIETARY_SUGAR = "Dietary Sugar";
    private static final String COLUMN_FIBRE = "Fibre";
    private static final String COLUMN_FOLATE = "Folate";
    private static final String COLUMN_IODINE = "Iodine";
    private static final String COLUMN_IRON = "Iron";
    private static final String COLUMN_MAGNESIUM = "Magnesium";
    private static final String COLUMN_MANGANESE = "Manganese";
    private static final String COLUMN_MOLYBDENUM = "Molybdenum";
    private static final String COLUMN_MONOUNSATURATED_FAT = "Monounsaturated Fat";
    private static final String COLUMN_NIACIN = "Niacin";
    private static final String COLUMN_PANTOTHENIC_ACID = "Pantothenic Acid";
    private static final String COLUMN_PHOSPHORUS = "Phosphorus";
    private static final String COLUMN_POLYUNSATURATED_FAT = "Polyunstaturated Fat";
    private static final String COLUMN_POTASSIUM = "Potassium";
    private static final String COLUMN_PROTEIN = "Protein";
    private static final String COLUMN_RIBOFLAVIN = "Riboflavin";
    private static final String COLUMN_SATURATED_FAT = "Saturated Fat";
    private static final String COLUMN_SELENIUM = "Selenium";
    private static final String COLUMN_SODIUM = "Sodium";
    private static final String COLUMN_THIAMINE = "Thiamine";
    private static final String COLUMN_TOTAL_FAT = "Total Fat";
    private static final String COLUMN_VITAMIN_A = "Vitatmin A";
    private static final String COLUMN_VITAMIN_B12 = "Vitamin B12";
    private static final String COLUMN_VITAMIN_B6 = "Vitamin B6";
    private static final String COLUMN_VITAMIN_C = "Vitamin C";
    private static final String COLUMN_VITAMIN_D = "Vitamin D";
    private static final String COLUMN_VITAMIN_E = "Vitamin E";
    private static final String COLUMN_VITAMIN_K = "Vitamin K";
    private static final String COLUMN_WATER = "Water";
    private static final String COLUMN_ZINC = "Zinc";



    private static final String TABLE_CACHE_TABLEX = "cache_tablex";

    private static final String TABLE_FAVOURITE_FOOD_ITEMS_TABLE = "favourites_table";






    private Context mContext;

    //or onCreate
    public SQLDatabase_Food_Items_CIF6(Context context) {
        super(context, DB_NAME, null, VERSION);

        mContext = context;

        SQLiteDatabase db = getWritableDatabase();
        if(db == null)
        {
            android.util.Log.d("app","For Some reason the database is not opening...");
            return;

        }

        /*try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists breakfast_transaction (" + "_id integer primary key autoincrement, " +
                    "Column_Breakfast_id integer, " +
                    "Date integer, " +
                    "Meal_Type, varchar(100)," +
                    "Meal_Type_id integer, " +
                    "Amount integer, " +
                    "Balance integer)");
        } catch (SQLException alreadyexist) {

        }*/

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists lunch_transaction (" + "_id integer primary key autoincrement, " +
                    "Column_Lunch_id integer, " +
                    "Date integer, " +
                    "Meal_Type varchar(100)," +
                    "Meal_Type_id integer, " +
                    "Amount integer, " +
                    "Balance integer)");
        } catch (SQLException alreadyexist) {

        }

        try
        {
            db.execSQL("CREATE table if not exists health_profile (" + "_id integer primary key autoincrement, " +

            "vitals_id, integer, " +
            "client_account_name varchar(100)," +
            "client_dob float, " +
            "client_bmi integer, " +
            "client_bmr integer, " +
            "client_bodyfat integer," +
            "client_vitals_string varchar(150), " +
            "client_opening_balance integer, " +
            "client_height_cm integer, " +
            "client_email varchar(100)," +
                    "client_gender varchar(50)," +
                    "client_start_weight integer," +
                    "client_target_weight integer," +
                    "client_bodyframe varchar(50), " +
                    "client_start_date float, " +
                    "client_lean_body_mass integer, " +
                    "client_waist_circumference integer, " +
                    "client_blood_pressure integer, " +
                    "client_body_temperature integer, " +
                    "client_alcohol_contents integer, " +
                    "client_blood_glucose integer, " +
                    "client_ECG integer, " +
                    "heart_rate_variablity integer, " +
                    "high_heart_rate_notification integer, " +
                    "irregular_heart_rhythm_notification integer, " +
                    "low_heart_rate_notification integer, " +
                     "walking_heart_rate integer, " +
                    "heart_rate integer, " +
                    "rest_heart_rate integer, " +
                    "respiratory_rate integer, " +
                    "electodermal_activity integer, " +
                    "forced_expiratory_volume integer, " +
                    "forced_vital_capacity integer, " +
                    "inhaler_usage integer, " +
                    "insulin_delivery integer, " +
                    "oxygen_saturation integer, " +
                    "peak_expiratory_flow_rate integer, " +
                    "peripheral_pefusion_index integer, " +
                    "uv_index integer, " +
                    "data_sources varchar(100), " +
                    "manual_data_sources integer) ");


        }
        catch (SQLException alreadyexist)
        {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists dinner_transaction (" + "_id integer primary key autoincrement, " +
                    "Column_Dinner_id integer, " +
                    "Date integer, " +
                    "Meal_Type varchar(100)," +
                    "Meal_Type_id integer, " +
                    "Amount integer, " +
                    "Balance integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists meal_box_items (" + "_id integer primary key autoincrement, " +
                    "Column_Meal_Box_id integer," +
                    "Meal_Type varchar(100)," +
                    "Meal_Type_id integer, " +
                    "food_type varchar(100), " +
                    "food_item_name varchar(100), " +
                    "grams_per_serving_portion real," +
                    "calories_per_100g real," +
                    "fat_per_100g real," +
                    "saturated_fat real," +
                    "trans_fat real," +
                    "protein_per_100g real," +
                    "carbs_per_100g real," +
                    "sugar_per_100g real," +
                    "salt_per_100g real," +
                    "wellbeing_index real," +
                    "fiber real," +
                    "price_sterling real," +
                    "category varchar(100)," +
                    "polyunsaturated real," +
                    "monounsaturated real," +
                    "cholesterol_mg real," +
                    "sodium_mg real," +
                    "potassium_mg real," +
                    "vitamin_a_percent real," +
                    "vitamin_c_percent real," +
                    "calcium_percent real," +
                    "iron_percent real)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("create table if not exists transactions_xp (" + "_id integer primary key autoincrement, " +
                    "Column_Transactions_id integer, " +
                    "Date integer, " +
                    "Meal_Type varchar(100)," +
                    "Meal_Type_id integer, " +
                    "Amount integer, " +
                    "Balance integer)");
        } catch (SQLException alreadyexist) {

        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Food / Drinks Item Table
        //Check if database already exist
        db.execSQL("Create table if not exists Food_Items (" + "_id integer primary key autoincrement, " +
                "food_type varchar(100), " +
                "food_item_name varchar(100), " +
                "grams_per_serving_portion real," +
                "calories_per_100g real," +
                "fat_per_100g real," +
                "saturated_fat real," +
                "trans_fat real," +
                "protein_per_100g real," +
                "carbs_per_100g real," +
                "sugar_per_100g real," +
                "salt_per_100g real," +
                "wellbeing_index real," +
                "fiber real," +
                "price_sterling real," +
                "category varchar(100)," +
                "polyunsaturated real," +
                "monounsaturated real," +
                "cholesterol_mg real," +
                "sodium_mg real," +
                "potassium_mg real," +
                "vitamin_a_percent real," +
                "vitamin_c_percent real," +
                "calcium_percent real," +
                "iron_percent real)");


        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists countdown_balances (" + "_id integer primary key autoincrement, " +
                    "balance_date integer, " +
                    "balance_balance varchar(100))");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists dayend_balance (" + "_id integer primary key autoincrement, " +
                    "balance_date integer, " +
                    "balance_dayend integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists sex (" + "_id integer primary key autoincrement, " +
                    "sex_sex varchar(100))");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists breakfast_time (" + "_id integer primary key autoincrement, " +
                    "breakfast_time integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists lunch_time (" + "_id integer primary key autoincrement, " +
                    "lunch_time integer)");
        } catch (SQLException alreadyexist)
        {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists final_meal (" + "_id integer primary key autoincrement, " +
                    "final_meal_time integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists current_weight (" + "_id integer primary key autoincrement, " +
                    "current_weight integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists target_weight (" + "_id integer primary key autoincrement, " +
                    "target_weight integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists start_weight (" + "_id integer primary key autoincrement, " +
                    "start_weight integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists dayend_countdown_last_session (" + "_id integer primary key autoincrement, " +
                    "dayend_last_session integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists list_of_dayend_balances (" + "_id integer primary key autoincrement, " +
                    "dayend_list integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists maleorfemaale (" + "_id integer primary key autoincrement, " +
                    "gender boolean)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists number_of_days (" + "_id integer primary key autoincrement, " +
                    "number_of_days integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists reminder_status (" + "_id integer primary key autoincrement, " +
                    "reminder_status boolean)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists reminder_statuss (" + "_id integer primary key autoincrement, " +
                    "reminder_statuss varchar(100))");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists account_open_balance (" + "_id integer primary key autoincrement, " +
                    "opening_balance integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("Create table if not exists start_day (" + "_id integer primary key autoincrement, " +
                    "start_day integer)");
        } catch (SQLException alreadyexist) {

        }

        try //at launch take all table creations to OnCreate
        {
            db.execSQL("create table if not exists transactions_xp (" + "_id integer primary key autoincrement, " +
                    "Column_Transactions_id integer, " +
                    "Date integer, " +
                    "Meal_Type varchar(100)," +
                    "Meal_Type_id integer, " +
                    "Amount integer, " +
                    "Balance integer)");
        } catch (SQLException alreadyexist) {

        }


        //Create Food / Drinks Item Table


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Implement schema changes and data massages here when upgrading
    }

    //From Green i
// List of Food item attributes and Food Item database Table Fields
//Category Eat in /Out Type Ingrediend meal etc restaurate items google search add guess etc for layers exe Name of Food or  Item	Calories per 100g or 100ml 	Grams in a Serving/Portion	Carbhydrates	of which are Sugars	Protein	Fat 	of which are saturated Fat	Polyunsaturated	Monounsaturated	Trans	Cholesterol (mg)	Sodium (mg)	Salt (g)	Potassuim (mg)	Fibre	Sugars	Vitamin A (%)	Vitamin C (%)	Calcium (%)	Iron (%)
// N.B arrange files to match initial orignial Blackberry fields then extend field with what you have leftover
    public ArrayList<Food_Item_CIF4> QueryForMatches(String food_item_subname) {
        //Insert test data
        //long result = InsertDummyRice();


        ArrayList<Food_Item_CIF4> list = new ArrayList<Food_Item_CIF4>();
        String fooditemname = food_item_subname;
        list = Query_Specific_Food_Items_Table(fooditemname);

        //See Results of Query for Matches
       // Check_Results(list);

        return list;
    }

    public long InsertDummyRice(String Category, String food_item_name, float grams_per_serving_portion, float calories_per_serving_portion, float fat_per_serving_portion, float carbohydrates_per_serving, float protein_per_serving) {


        Food_Item_CIF4 dummyrice = new Food_Item_CIF4();
        dummyrice.Set_category(Category);
        dummyrice.Set_food_item_name(food_item_name);

        dummyrice.Set_grams_per_serving_portion(grams_per_serving_portion);
        dummyrice.Set_calories_per_100g(calories_per_serving_portion);
        dummyrice.Set_fat_per_100g(fat_per_serving_portion);
        dummyrice.Set_carbs_per_100g(carbohydrates_per_serving);
        dummyrice.Set_protein_per_100g(protein_per_serving);

        return Insert_Food_Item_Row(dummyrice);

        //data_model_adapter.InsertDummyRice("ING","Orange Juice : Smooth Orange : innocent",100,190,0,(float) 8.2,(float) 0.7);
        //data_model_adapter.InsertDummyRice("EATOUT","Coffee : White Americano : Starbucks",100,40,0,0,0);
        //data_model_adapter.InsertDummyRice("EATOUT","Sandwich : Croque Monsieur : Starbucks",100,420,(float) 13.7,(float) 48.9,(float) 24);
        //data_model_adapter.InsertDummyRice("EATOUT","Pastry : Cinnamon Swirl : Starbucks",100,370,58,(float) 0,(float) 0);
        //data_model_adapter.InsertDummyRice("ING","Milk",100,40,(float)1.6,(float) 5.1,(float) 3.3);
        //data_model_adapter.InsertDummyRice("ING","Generic Food Item",100,100,(float)10,(float) 50,(float) 25);
        //MIF4_Data_Model_Adapter data_model_adapter = new MIF4_Data_Model_Adapter(this);
        //data_model_adapter.InsertDummyRice("EATOUT","Hot Dog : Bratwurst : on the roll",100,471,(float)0,(float) 0,(float) 0);
        //data_model_adapter.InsertDummyRice("ING","Coke : Can : Coke",100,190,(float)0,(float) 0,(float) 0);
    }

    public ArrayList<Food_Item_CIF4> Query_Specific_Food_Items_Table(String food_item_name) {
        //Alogrithm Engineering : Get cursor pointing row and columns

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_FOODITEMS + " WHERE " + COLUMN_FOODITEMS_FOOD_ITEM_NAME + " LIKE " + "'" + "%"+food_item_name+"%" + "'", null);
        cursor.moveToFirst();
        FoodItemsCursor foodItemCursor = new FoodItemsCursor(cursor);
        Log.d("Calorie Countdown", "Check to state of Cursor");
        if (foodItemCursor.getCount() < 1) {
            ArrayList<Food_Item_CIF4> placebo = new ArrayList<Food_Item_CIF4>();
            Food_Item_CIF4 not_found = new Food_Item_CIF4();
            not_found.Set_food_item_name(food_item_name + " not found");
            placebo.add(not_found);
            foodItemCursor.close();
            return placebo;
        } else {
            return GetFoodItemII(new FoodItemsCursor(cursor));
        }

    }

    public String GetLatestBalance() {
        Date today = new Date();
        Calendar Greggs = Calendar.getInstance();
        Greggs.setTime(today);
        Greggs.set(Calendar.HOUR, 0);
        Greggs.set(Calendar.MINUTE, 0);
        Greggs.set(Calendar.YEAR, 1977);
        today.setTime(Greggs.getTimeInMillis());

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_COUNTDOWN_BALANCE + " WHERE " + COLUMN_COUNTDOWN_BALANCE_DATE + " >" + "'" + today.getTime() + "'", null);
        cursor.moveToFirst();
        CountdownBalanceCursor countdownBalanceCursor = new CountdownBalanceCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("0");
        } else {
            return GetBalances(countdownBalanceCursor);
        }
    }

    public Breakfast_Transaction_CIF22 Get_BreakfastTransaction_Table(Date startPeriod, Date endPeriod) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_BREAKFAST_TRANSACTIONS_TABLE + " WHERE " + COLMUM_BREAKFAST_DATE + " >" + "'" + startPeriod.getTime() + "'" + "AND" + "<" + endPeriod.getTime() + "'", null);
        cursor.moveToFirst();
        BreakfastTransaction_Cursor_CIF24 breakfastTransactionCursor = new BreakfastTransaction_Cursor_CIF24(cursor);
        if (breakfastTransactionCursor.getCount() < 1) {
            breakfastTransactionCursor.close();
            return new Breakfast_Transaction_CIF22();
        } else {
            return GetTransactions(breakfastTransactionCursor);
        }


    }

    public Lunch_Transaction_CIF20 Get_LunchTransaction_Table(Date startPeriod, Date endPeriod) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_LUNCH_TRANSACTIONS_TABLE + " WHERE " + COLMUM_LUNCH_DATE + " >" + "'" + startPeriod.getTime() + "'" + "AND" + "<" + endPeriod.getTime() + "'", null);
        cursor.moveToFirst();
        LunchTransaction_Cursor_CIF26 lunchTransactionCursor = new LunchTransaction_Cursor_CIF26(cursor);
        if (lunchTransactionCursor.getCount() < 1) {
            lunchTransactionCursor.close();
            return new Lunch_Transaction_CIF20();
        } else {
            return GetTransactions(lunchTransactionCursor);
        }

    }

    public Dinner_Transaction_CIF21 Get_DinnerTransaction_Table(Date startPeriod, Date endPeriod) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_LUNCH_TRANSACTIONS_TABLE + " WHERE " + COLMUM_DINNER_DATE + " >" + "'" + startPeriod.getTime() + "'" + "AND" + "<" + endPeriod.getTime() + "'", null);
        cursor.moveToFirst();
        DinnerTransaction_Cursor_CIF27 dinnerTransactionCursor = new DinnerTransaction_Cursor_CIF27(cursor);
        if (dinnerTransactionCursor.getCount() < 1) {
            dinnerTransactionCursor.close();
            return new Dinner_Transaction_CIF21();
        } else {
            return GetTransactions(dinnerTransactionCursor);
        }

    }

    public Transactions_CIF22 Get_All_Transactions(Date start, Date end)
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS_TABLE + " WHERE " + COLMUM_TRANSACTIONS_DATE + " >= " + "'" + start.getTime() + "'" + " AND " + COLMUM_TRANSACTIONS_DATE +" <= " + "'" + end.getTime() + "'", null);
        cursor.moveToFirst();
        Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        if (TransactionCursor.getCount() < 1) {
            TransactionCursor.close();
            return new Transactions_CIF22();
        } else {
            return GetTransactions(TransactionCursor);
        }

    }

    public Transactions_CIF22 Really_Get_All_Transactions()
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS_TABLE, null);
        cursor.moveToFirst();
        Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        Transactions_CIF22 OUTPUT;
        ArrayList<Breakfast_Box_CIF17> OUTPUTb;

        if (TransactionCursor.getCount() < 1)
        {
            Log.d(TAG, "NOTHING FOUND IN Transaction DATABASE");
            TransactionCursor.close();
            return new Transactions_CIF22();
        }
        else
        {
            try {
                Log.d(TAG, "CONTENTS IN Transaction DATABASE");
                OUTPUT = GetTransactions(TransactionCursor);

                OUTPUTb = new ArrayList<Breakfast_Box_CIF17>();  //Really_Get_Box_items();
                Breakfast_Box_CIF17 dummy = new Breakfast_Box_CIF17();
                OUTPUTb.add(dummy);

                OUTPUT = Merge_Food_items(OUTPUT, OUTPUTb);

                //BBox.Set_Breakfast_ID((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
                //Mealio = new Meal_Items_Cursor(cursor);
                //Transaction_Items = Mealio.Get_Transaction_Food_Items(new RoundingCIF13().StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
                //BBox.Set_Breakfast_Date((new RoundingCIF13()).StringToLong((getString(getColumnIndex(COLMUM_TRANSACTIONS_DATE)))));
                //BBox.Set_Breakfast_Meal_Type(getString(getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE)));
                //BBox.Set_Breakfast_Meal_Type_ID((new RoundingCIF13().StringToInt((getString((getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID)))))));
                //BBox.Set_Breakfast_Amount((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_AMOUNT)))));
                //BBox.Set_Breakfast_Balance((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_BALANCE)))));

                Log.d(TAG, "Contents of OUTPUTb, Size : " + new RoundingCIF13().IntToString(OUTPUTb.size()));

                Log.d(" Menuitem Module", "Crashes here Line 639");

                Breakfast_Box_CIF17 itet = new Breakfast_Box_CIF17(); //OUTPUTb.get(0);

                //Log.d("Contents Meal Boxes", itet.Get_Food_Items().get(0).Get_food_item_name());

                return OUTPUT;

                //BoxCIF17 OUTPUTc = Transform_Breakfast_Box_to_Box(OUTPUTb);
                //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
                //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);
            }
            catch(Exception x)
            {
                Toast.makeText(mContext, "Meal Box is empty aborting function", Toast.LENGTH_SHORT);
            }


            return new Transactions_CIF22();
        }

    }

    public BoxCIF17 Get_Meal_items(long Transaction_ID)
    {
        return new BoxCIF17();
    }


    public int GetDayEndBalance() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_DAYEND_BALANCE, null);
        cursor.moveToFirst();
        DayendCursor dayendCursor = new DayendCursor(cursor);
        if (dayendCursor.getCount() < 1) {
            dayendCursor.close();
            return new RoundingCIF13().StringToInt(GetLatestBalance());
        } else {
            return GetDayendBalance(dayendCursor);
        }

    }

    public String GetSex() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_SEX, null);
        cursor.moveToFirst();
        SexCursor sexCursor = new SexCursor(cursor);
        if (sexCursor.getCount() < 1) {
            sexCursor.close();
            return new String("HYMERPHORDITE");
        } else {
            return getSex(sexCursor);
        }
    }


    private ArrayList<Food_Item_CIF4> GetFoodItem(FoodItemsCursor food_cursor) {
        Food_Item_CIF4 food_match = new Food_Item_CIF4();
        ArrayList<Food_Item_CIF4> relist = new ArrayList<Food_Item_CIF4>();
        food_cursor.moveToFirst();
        //For each row create a food item CIF4
        while (food_cursor.moveToNext()) {
            food_match = food_cursor.getFood_Item();
            relist.add(food_match);
        }
        food_cursor.close();
        return relist;

        //Pass it on and only select the matches.

    }


    public String GetBreakfastTime() {
        try {
            Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_BREAKFAST_TIME, null);
            cursor.moveToFirst();
            BreakfastCursor countdownBalanceCursor = new BreakfastCursor(cursor);
            if (countdownBalanceCursor.getCount() < 1) {
                countdownBalanceCursor.close();
                return new String("15:35");
            } else {
                String repulse = GetBreakfastCursor(countdownBalanceCursor);
                countdownBalanceCursor.close();
                return repulse;
            }
        }
        catch(Exception e)
        {
            android.util.Log.d("app","database must have failed to open");
            return "1000";
        }
    }

    public String GetLunchTime() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_LUNCH_TIME, null);
        cursor.moveToFirst();
        LunchCursor countdownBalanceCursor = new LunchCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("15:37");
        } else {
            String repulse = GetLunchCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;
        }
    }

    public String GetFinalMealTime() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_FINAL_MEAL_TIME, null);
        cursor.moveToFirst();
        FinalMealCursor countdownBalanceCursor = new FinalMealCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("15:39");
        } else {
            String repulse = GetFinalMealCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;
        }
    }

    public String GetCurrentWeight() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_CURRENT_WEIGHT, null);
        cursor.moveToFirst();
        CurrentWeightCursor countdownBalanceCursor = new CurrentWeightCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("January 1, 2001");
        } else {
            String repulse = GetCurrentWeightCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;
        }
    }

    public String GetTargetWeight() {


        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TARGET_WEIGHT, null);
        cursor.moveToFirst();
        TargetWeightCursor countdownBalanceCursor = new TargetWeightCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("1");
        } else {
            try {
                android.util.Log.d("Target weight Count", Integer.toString(countdownBalanceCursor.getCount()));
                boolean bull = countdownBalanceCursor.moveToFirst();
                String repulse = GetTargetWeightCursor(countdownBalanceCursor);
                //countdownBalanceCursor.close();
                return repulse;
            }
            catch(java.lang.IllegalStateException e) {
                //Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
                //dialog.Showing("Please enter your Target weight using main menu");
                return "0";
            }
            finally { countdownBalanceCursor.close();}
        }
    }

    public String GetReminder() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_REMINDER_STATUS, null);
        cursor.moveToFirst();
        ReminderCursor countdownBalanceCursor = new ReminderCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("January 1, 2001");
        } else {
            String repulse = GetReminderCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;
        }
    }

    public String GetStartDate() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_START_DAY, null);
        cursor.moveToFirst();
        StartDayCursor countdownBalanceCursor = new StartDayCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("January 1, 2000");
        } else {
            String repulse = GetStartDayCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;
        }
    }

    public String GetStartCountdown() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_OPENING_BALANCE, null);
        cursor.moveToFirst();
        OpeningBalanceCursor countdownBalanceCursor = new OpeningBalanceCursor(cursor);
        if (countdownBalanceCursor.getCount() < 1) {
            countdownBalanceCursor.close();
            return new String("January 1, 2000");
        } else {
            String repulse = GetStartCountdownCursor(countdownBalanceCursor);
            countdownBalanceCursor.close();
            return repulse;

        }
    }


    private String GetBalances(CountdownBalanceCursor balanceCursor) {
        String repulse = balanceCursor.getBalance();
        balanceCursor.close();
        return repulse;
    }

    public Transactions_CIF22 GetTransactions(Transaction_Cursor_CIF24 bc) {
        Transactions_CIF22 dc = bc.GetTransactions();
        return dc;
    }

    public HealthProfileCiF3 GetHealthProfile_CIF3(SHealth_Cursor_241 bc)
    {
        HealthProfileCiF3 dc = bc.Get_HealthProfileCIF3();
        return dc;
    }

    public ArrayList<Breakfast_Box_CIF17> GetBoxes(Meal_Items_Cursor mc)
    {
        ArrayList<Breakfast_Box_CIF17> dc = mc.Get_Meal_Box_Action();
        return dc;
    }

    private Breakfast_Transaction_CIF22 GetTransactions(BreakfastTransaction_Cursor_CIF24 bc) {
        Breakfast_Transaction_CIF22 dc = bc.GetTransactions();
        return dc;

    }

    private Lunch_Transaction_CIF20 GetTransactions(LunchTransaction_Cursor_CIF26 lc) {
        Lunch_Transaction_CIF20 dc = lc.GetTransactions();
        return dc;
    }

    private Dinner_Transaction_CIF21 GetTransactions(DinnerTransaction_Cursor_CIF27 dc) {
        Dinner_Transaction_CIF21 pc = dc.GetTransactions();
        return pc;
    }

    private int GetDayendBalance(DayendCursor dayendCursor) {
        int repulse = dayendCursor.getDayend();
        dayendCursor.close();
        return repulse;

    }

    private int GetBreakfastttime(BreakfasttimeCursor btCursor) {
        int repulse = btCursor.getBreakfasttime();
        btCursor.close();
        return repulse;

    }

    private int GetLunchtttime(LunchtimeCursor ltCursor) {
        int repulse = ltCursor.getLunchtime();
        ltCursor.close();
        return repulse;

    }

    private int GetDinnertttime(DinnertimeCursor dtCursor) {
        int repulse = dtCursor.getDinnertime();
        dtCursor.close();
        return repulse;

    }

    private String getSex(SexCursor sexCursor) {
        String repulse = sexCursor.getSex();
        sexCursor.close();
        return repulse;
    }

    private String GetStartDayCursor(StartDayCursor obcursor) {
        String repulse = obcursor.getStartDay();
        obcursor.close();
        return repulse;
    }

    private String GetStartCountdownCursor(OpeningBalanceCursor obcursor) {
        String repulse = obcursor.getOpeningBalance();
        obcursor.close();
        return repulse;
    }

    private String GetReminderCursor(ReminderCursor obcursor) {
        String repulse = obcursor.getReminder();
        obcursor.close();
        return repulse;
    }

    private String GetTargetWeightCursor(TargetWeightCursor obcursor) {
        String repulse = obcursor.getTargetWeight();
        obcursor.close();
        return repulse;
    }

    private String GetCurrentWeightCursor(CurrentWeightCursor obcursor) {
        String repulse = obcursor.getCurrentWeight();
        obcursor.close();
        return repulse;
    }

    private String GetFinalMealCursor(FinalMealCursor obcursor) {
        String repulse = obcursor.getFinalMeal();
        obcursor.close();
        return repulse;
    }

    private String GetLunchCursor(LunchCursor obcursor) {
        String repulse = obcursor.getLunch();
        obcursor.close();
        return repulse;
    }

    private String GetBreakfastCursor(BreakfastCursor obcursor)
    {
        String repulse = obcursor.getBreakfast();
        obcursor.close();
        return repulse;
    }


    private ArrayList<Food_Item_CIF4> GetFoodItemII(FoodItemsCursor food_cursor)
    {
        Food_Item_CIF4 food_match;
        ArrayList<Food_Item_CIF4> relist = new ArrayList<Food_Item_CIF4>();



        //for(int c = 0; c < food_cursor.getCount(); c++)
       // {
        //food_match = food_cursor.getFood_Item();
        //relist.add(food_match);
        //return relist;

        //while(food_cursor.isAfterLast() == false)
        //{

        for(int c = 0; c < food_cursor.getCount(); c++) {

            food_match = new Food_Item_CIF4();

            android.util.Log.d("NUMBER OF ROWS IN FETCH", new RoundingCIF13().IntToString(food_cursor.getCount()));
            food_match.Set_food_item_name((food_cursor.getString(food_cursor.getColumnIndex(COLUMN_FOODITEMS_FOOD_ITEM_NAME))));
            food_match.Set_food_type(food_cursor.getString(food_cursor.getColumnIndex(COLUMN_FOODITEMS_FOOD_TYPE)));
            food_match.Set_grams_per_serving_portion(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_GRAMS_PER_SERVING_PORTION)));
            food_match.Set_calories_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_CALORIE_PER_100G)));
            food_match.Set_fat_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_FAT_PER_100G)));
            food_match.Set_saturated_fat(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_SATURATED_FAT)));
            food_match.Set_trans_fat(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_TRANS_FAT)));
            food_match.Set_protein_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_PROTEIN_PER_100G)));
            food_match.Set_carbs_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_CARBS_PER_100G)));
            food_match.Set_sugar_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_SUGAR_PER_100G)));
            food_match.Set_salt_per_100g(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_SALT_PER_100G)));
            food_match.Set_wellbeing_index(new RoundingCIF13().StringToBool(new RoundingCIF13().FloatToString(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_WELLBEING_INDEX)))));
            food_match.Set_fiber(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_FIBER)));
            food_match.Set_price_sterling(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_PRICE_STERLING)));
            food_match.Set_category(food_cursor.getString(food_cursor.getColumnIndex(COLUMN_FOODITEMS_CATEGORY)));

            relist.add(food_match);
            boolean trash = food_cursor.moveToNext();

        }


        //food_match.Set_monounsaturated(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_MONOUNSATURATED)));

        //try
        //{
        //food_match.Set_cholesterol_mg(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_CHOLESTEROL_MG)));
        // food_match.Set_sodium_mg(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_SODIUM_MG)));
        //food_match.Set_potassium_mg(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_POTASSIUM_MG)));
        //food_match.Set_vitamin_a_percent(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_VITAMIN_A_PERCENT)));
        //food_match.Set_vitamin_c_percent(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_VITAMIN_C_PERCENT)));
        //food_match.Set_calcium_percent(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_CALCIUM_PERCENT)));
        //food_match.Set_iron_percent(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_IRON_PERCENT)));
        //  food_match.Set_polyunsaturated(food_cursor.getFloat(food_cursor.getColumnIndex(COLUMN_FOODITEMS_POLYUNSATURATED)));
        //}
        //catch(IllegalStateException e)
        //{
        //  android.util.Log.d("Database","IllegalStateException Caught");
        //}



        //reloop here ESE
    //}
        food_cursor.close();
        //getReadableDatabase().clone()

        //}

        return relist;


    }

    private FoodItemsCursor queryFoodItems() {
        Cursor wrapped = getReadableDatabase().query(TABLE_FOODITEMS, null, null, null, null, null, COLUMN_FOODITEMS_FOOD_ITEM_NAME + " asc");
        FoodItemsCursor food_item_cursor = new FoodItemsCursor(wrapped);
        return food_item_cursor;


    }

    public long Insert_Balance(String bal) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COUNTDOWN_BALANCE_DATE, new Date().getTime());
        cv.put(COLUMN_COUNTDOWN_BALANCE_BALANCE, bal);
        int affected = getWritableDatabase().delete(TABLE_COUNTDOWN_BALANCE, null, null);
        return getWritableDatabase().insert(TABLE_COUNTDOWN_BALANCE, null, cv);  //sucessful insert.


    }

    public long Insert_Sex(String sex) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SEX_SEX, sex);
        int affected = getWritableDatabase().delete(TABLE_SEX, null, null);
        android.util.Log.d("Data Layer", "Insert sex2 Success! " + new RoundingCIF13().IntToString(affected));
        return getWritableDatabase().insert(TABLE_SEX, null, cv);

    }

    public long Insert_Dayend_Balance(int bal) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAYEND_BALANCE_DATE, new Date().getTime());
        cv.put(COLUMN_DAYEND_BALANCE_BALANCE, bal);
        int affected = getWritableDatabase().delete(TABLE_DAYEND_BALANCE, null, null);
        return getWritableDatabase().insert(TABLE_DAYEND_BALANCE, null, cv);
    }


    public long Insert_BreakfastTime(String bt) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_BREAKFAST_TIME_BREAKFASTTIME, bt);
        int affected = getWritableDatabase().delete(TABLE_BREAKFAST_TIME, null, null);
        return getWritableDatabase().insert(TABLE_BREAKFAST_TIME, null, cv);
    }


    public long Insert_LunchTime(String lt) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_LUNCH_TIME_LUNCHTIME, lt);
        int affected = getWritableDatabase().delete(TABLE_LUNCH_TIME, null, null);
        return getWritableDatabase().insert(TABLE_LUNCH_TIME, null, cv);
    }


    public long Insert_FinalMealTime(String fmt) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_FINAL_MEAL_TIME_FINALMEATIME, fmt);
        int affected = getWritableDatabase().delete(TABLE_FINAL_MEAL_TIME, null, null);
        return getWritableDatabase().insert(TABLE_FINAL_MEAL_TIME, null, cv);
    }


    public long Insert_CurrentWeight(String cw) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_CURRENT_WEIGHT_CURRENTWEIGHT, cw);
        int affected = getWritableDatabase().delete(TABLE_DAYEND_COUNTDOWN_LAST_SESSIONS, null, null);
        return getWritableDatabase().insert(TABLE_CURRENT_WEIGHT, null, cv);
    }


    public long Insert_Target_Weight(String tw) {

        deleteTargetWeightTable();
        ContentValues cv = new ContentValues();



        Unique_ID = new Random().nextInt();
        Unique_ID = Math.abs(Unique_ID);

        cv.put("_id", Unique_ID);
        cv.put(COLUMUM_TARGET_WEIGHT_TARGETWEIGHT, tw);

        int affected = getWritableDatabase().delete(TABLE_TARGET_WEIGHT, null, null);
        return getWritableDatabase().insert(TABLE_TARGET_WEIGHT, null, cv);
    }


    public long Insert_StartDate(Date sd) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_START_DAY_STARTDAY, sd.getTime());
        int affected = getWritableDatabase().delete(TABLE_START_DAY, null, null);
        return getWritableDatabase().insert(TABLE_START_DAY, null, cv);
    }


    public long Insert_ReminderOnOff(boolean r) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_REMINDER_STATUS_BOOLEANE, r);
        int affected = getWritableDatabase().delete(TABLE_REMINDER_STATUS, null, null);
        return getWritableDatabase().insert(TABLE_REMINDER_STATUS, null, cv);
    }

    public long Insert_BreakfastTransactionTable(Breakfast_Box_CIF17 in) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BREAKFAST_ID, in.Get_Breakfast_ID());
        cv.put(COLUMN_COUNTDOWN_BALANCE_DATE, in.Get_Breakfast_Date());
        cv.put(COLUMUM_BREAKFAST_MEAL_TYPE, in.Get_Breakfast_Meal_Type());
        cv.put(COLUMUM_BREAKFAST_MEAL_TYPE_ID, in.Get_Breakfast_Meal_Type_ID());
        cv.put(COLUMUM_BREAKFAST_AMOUNT, in.Get_Breakfast_Amount());
        cv.put(COLUMUM_BREAKFAST_BALANCE, in.Get_Breakfast_Balance());
        return getWritableDatabase().insert(TABLE_BREAKFAST_TRANSACTIONS_TABLE, null, cv);
    }

    public long Insert_TransactionTable(Transaction_CIF52 transaction_type)
    {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRANSACTIONS_ID, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_id()));
        Log.d("INSERT", new RoundingCIF13().LongToString(transaction_type.Get_Transaction_id()));
        cv.put(COLMUM_TRANSACTIONS_DATE, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Date()));
        Log.d("INSERT", new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Date()));
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE, transaction_type.Get_Transaction_Meal_Type());
        Log.d("INSERT", transaction_type.Get_Transaction_Meal_Type());
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Meal_Type_ID()));
        Log.d("INSERT",new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Meal_Type_ID()) );
        cv.put(COLUMUM_TRANSACTIONS_AMOUNT, new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Amount()));
        Log.d("INSERT",  new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Amount()));
        cv.put(COLUMUM_TRANSACTIONS_BALANCE, new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Balance()));
        Log.d("INSERT",new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Balance()) );


        //ContentValues cv = new ContentValues();
        //cv.put(COLUMN_TRANSACTIONS_ID, 2435664);
        //cv.put(COLMUM_TRANSACTIONS_DATE, new Date().getTime());
        //cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE, "Dummy");
        //cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID, 14);
        //cv.put(COLUMUM_TRANSACTIONS_AMOUNT, 56);
        //cv.put(COLUMUM_TRANSACTIONS_BALANCE, 87);

        Log.d("Insert Tran Table", "Checkpoint 2");
        //long ID2 = Insert_BoxCIF(transaction_type.Get_Single_Transaction_Line());

        Log.d("Insert Tran Table", "Checkpoint 1");
        long ID = getWritableDatabase().insert(TABLE_TRANSACTIONS_TABLE, null, cv);

        Log.d("Insert Tran Table", "Checkpoint 3");

        return ID;

    }

    public long Insert_Meal_Box_ID_Match(Meal_Items_Cursor mealbox) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MEAL_BOX_ID, mealbox.Get_Meal_Box_ID());
        return getWritableDatabase().insert(TABLE_MEAL_BOX_ITEMS_TABLE, null, cv);
    }


    //public BreakfastTransaction_Cursor_CIF24 Retrieve_Breakfast_Transaction_Table()[]

    //Brown sends Interfaces to Android Blackboard Black fragment assesment through Blackboard Posting to Blackboard Members including me Director and HoO Head of Office

    //public BreakfastMealBox_Cursor_CIF25 Retrieve_Breakfast_MealBox_Table()
    //{
    //    return new BreakfastMealBox_Cursor_CIF25();
    //}

    public Breakfast_Transaction_CIF22 Retrieve_BreakfastTransactionTable() {
        return new Breakfast_Transaction_CIF22();
    }

    public BreakfastMealBoxSlashLine Retrieve_Breakfast_MealBox_Table() {
        return new BreakfastMealBoxSlashLine(); //Get Data out of Data Level SQL to be put in the data object item CIF and sent on this is it's Output.
    }


    public long Insert_BreakfastMealBoxTable(Breakfast_Box_CIF17 in) {
        ContentValues cv = new ContentValues();


        for (Food_Item_CIF4 e : in.food_item_list_two) {
            cv.put(COLUMN_MEAL_BOX_ID, new RoundingCIF13().LongToString(in.Get_Transaction_ID()));
            cv.put(COLUMUM_MEAL_BOX_MEAL_TYPE, in.Get_Breakfast_Meal_Type());
            cv.put(COLUMUM_MEAL_BOX_MEAL_TYPE_ID, new RoundingCIF13().LongToString(in.Get_Breakfast_Meal_Type_ID()));
            cv.put(COLUMN_MEAL_BOX_FOOD_TYPE, in.Get_Meal_Box_Food_Type());


            cv.put(COLUMN_MEAL_BOX_CATEGORY, e.Get_category());
            cv.put(COLUMN_MEAL_BOX_FOOD_ITEM_NAME, e.Get_food_item_name());

            cv.put(COLUMN_MEAL_BOX_GRAMS_PER_SERVING_PORTION, new RoundingCIF13().FloatToString(e.Get_grams_per_serving_portion()));
            cv.put(COLUMN_MEAL_BOX_CALORIE_PER_100G, new RoundingCIF13().FloatToString(e.Get_calories_per_100g()));
            cv.put(COLUMN_MEAL_BOX_FAT_PER_100G, new RoundingCIF13().FloatToString(e.Get_fat_per_100g()));
            cv.put(COLUMN_MEAL_BOX_SATURATED_FAT, new RoundingCIF13().FloatToString(e.Get_saturated_fat()));
            cv.put(COLUMN_MEAL_BOX_TRANS_FAT, new RoundingCIF13().FloatToString(e.Get_trans_fat()));
            cv.put(COLUMN_MEAL_BOX_PROTEIN_PER_100G, new RoundingCIF13().FloatToString(e.Get_protein_per_100g()));
            cv.put(COLUMN_MEAL_BOX_CARBS_PER_100G, new RoundingCIF13().FloatToString(e.Get_calories_per_100g()));
            cv.put(COLUMN_MEAL_BOX_SUGAR_PER_100G, new RoundingCIF13().FloatToString(e.Get_sugar_per_100g()));
            cv.put(COLUMN_MEAL_BOX_SALT_PER_100G, new RoundingCIF13().FloatToString(e.Get_salt_per_100g()));
            cv.put(COLUMN_MEAL_BOX_WELLBEING_INDEX, new RoundingCIF13().BoolToString(e.Get_wellbeing_index()));
            cv.put(COLUMN_MEAL_BOX_FIBER, new RoundingCIF13().FloatToString(e.Get_fiber()));
            cv.put(COLUMN_MEAL_BOX_PRICE_STERLING, new RoundingCIF13().FloatToString(e.Get_price_sterling()));
            cv.put(COLUMN_MEAL_BOX_POLYUNSATURATED, new RoundingCIF13().FloatToString(e.Get_polyunsaturated()));
            cv.put(COLUMN_MEAL_BOX_MONOUNSATURATED, new RoundingCIF13().FloatToString(e.Get_monounsaturated()));
            cv.put(COLUMN_MEAL_BOX_CHOLESTEROL_MG, new RoundingCIF13().FloatToString(e.Get_cholesterol_mg()));
            cv.put(COLUMN_MEAL_BOX_SODIUM_MG, new RoundingCIF13().FloatToString(e.Get_sodium_mg()));
            cv.put(COLUMN_MEAL_BOX_POTASSIUM_MG, new RoundingCIF13().FloatToString(e.Get_potassium_mg()));
            cv.put(COLUMN_MEAL_BOX_VITAMIN_A_PERCENT, new RoundingCIF13().FloatToString(e.Get_vitamin_a_percent()));
            cv.put(COLUMN_MEAL_BOX_VITAMIN_C_PERCENT, new RoundingCIF13().FloatToString(e.Get_vitamin_c_percent()));
            cv.put(COLUMN_MEAL_BOX_CALCIUM_PERCENT, new RoundingCIF13().FloatToString(e.Get_calcium_percent()));
            cv.put(COLUMN_MEAL_BOX_IRON_PERCENT, new RoundingCIF13().FloatToString(e.Get_iron_percent()));


        }

        return getWritableDatabase().insert(TABLE_MEAL_BOX_ITEMS_TABLE, null, cv);

    }

    public long Insert_Food_Item_Row(Food_Item_CIF4 food_item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FOODITEMS_FOOD_TYPE, food_item.Get_food_type());
        cv.put(COLUMN_FOODITEMS_FOOD_ITEM_NAME, food_item.Get_food_item_name());
        cv.put(COLUMN_FOODITEMS_GRAMS_PER_SERVING_PORTION, food_item.Get_grams_per_serving_portion());
        cv.put(COLUMN_FOODITEMS_CALORIE_PER_100G, food_item.Get_calories_per_100g());
        cv.put(COLUMN_FOODITEMS_FAT_PER_100G, food_item.Get_fat_per_100g());
        cv.put(COLUMN_FOODITEMS_SATURATED_FAT, food_item.Get_saturated_fat());
        cv.put(COLUMN_FOODITEMS_TRANS_FAT, food_item.Get_trans_fat());
        cv.put(COLUMN_FOODITEMS_PROTEIN_PER_100G, food_item.Get_protein_per_100g());
        cv.put(COLUMN_FOODITEMS_CARBS_PER_100G, food_item.Get_carbs_per_100g());
        cv.put(COLUMN_FOODITEMS_SUGAR_PER_100G, food_item.Get_sugar_per_100g());
        cv.put(COLUMN_FOODITEMS_SALT_PER_100G, food_item.Get_salt_per_100g());
        cv.put(COLUMN_FOODITEMS_WELLBEING_INDEX, food_item.Get_wellbeing_index());
        cv.put(COLUMN_FOODITEMS_FIBER, food_item.Get_fiber());
        cv.put(COLUMN_FOODITEMS_PRICE_STERLING, food_item.Get_price_sterling());
        cv.put(COLUMN_FOODITEMS_CATEGORY, food_item.Get_category());
        cv.put(COLUMN_FOODITEMS_POLYUNSATURATED, food_item.Get_polyunsaturated());
        cv.put(COLUMN_FOODITEMS_MONOUNSATURATED, food_item.Get_monounsaturated());
        cv.put(COLUMN_FOODITEMS_CHOLESTEROL_MG, food_item.Get_cholesterol_mg());
        cv.put(COLUMN_FOODITEMS_SODIUM_MG, food_item.Get_sodium_mg());
        cv.put(COLUMN_FOODITEMS_POTASSIUM_MG, food_item.Get_potassium_mg());
        cv.put(COLUMN_FOODITEMS_VITAMIN_A_PERCENT, food_item.Get_vitamin_a_percent());
        cv.put(COLUMN_FOODITEMS_VITAMIN_C_PERCENT, food_item.Get_vitamin_c_percent());
        cv.put(COLUMN_FOODITEMS_CALCIUM_PERCENT, food_item.Get_calcium_percent());
        cv.put(COLUMN_FOODITEMS_IRON_PERCENT, food_item.Get_iron_percent());


        return getWritableDatabase().insert(TABLE_FOODITEMS, null, cv);  //sucessful insert.

        //Continue for rest of variables.

    }

    public void Insert_Food_Item_Row(JSONWrapperCIFClass INPUT)
    {
        ;
    }

    public void CreateAccountTables(HealthProfileCiF3 OUT) {
        //ENGLISH ~> IDO (New Menu/Submenuitem from Functional Model?)~> Algorithm Engineering (Building Blocks&)~> Android ~>(Phone)*~>(re)Load ~> www.ese-edet.eu End&Repeat, (apk Dev Console progress Bar on Track!)(Remember Weekly Friday Lamppost for www) (Read same thing same spot for revolve 2 happen.)Repeat Constantly on Track till <*August 16,16 Fast Track> Opening Soon exe  www.ee-edet.eu (re)Load -> www.ese-edet.eu.
        // *Test Menu/Sub Menuitem for (Green) (CFF and Value QVM). up and down train 2 www fronts beleive I can do 100 by <> not going to change in fut
        //repeat till final reload, reload ONLY Menuitems and SubMenuitems ONLY ESE Software Pipeline. exe 1,2,3 Fast
        //Fragment stop and read former work on this, compare do not reinvent the Wheel. You write to exe that's all
        //exe (hot) to Build Software so Say. REad.
        //
        //REX

        //Takes in CIF3 Create/Post Values related to it to relevant tables Copy and Breakdown

        //All table include : (now compare with Rex already written note : Well done. Start as you Finish (red connection [loop]

        //Calendar* notes can be found in Spreadsheet (exe Cube)

        //Other Table to Create in SQL Lite:
        //BreakfastTime Table
        //LunchTime Table
        //FinalMealTime Table -> NewDay DayEnd /Balance Cfwd to DayEnd new Calendar Day. Closer to Zero date. accounting Linguo Terms and Jargon Only.
        //CurrentWeight Table (Close Well no  leakage now Black fix) ~> work android ~> www.ese-edet.eu
        //TargetWeight Table
        //StartWeight Table
        //DayEnd Countdown /last session
        //Exercise Time Table
        //dayend countdown balances dashboard chart
        // All Sub Fragment Menuitems in their activity
        //Male or Female.
        //Number of Days
        //Reminder State Table
        //Start Calorie Balance Table
        //Start Day Table
        //add date field to last balance
        //Update Tables.
        //now store stuff ok including data in database key of app let's go.

        //Now copy and repeat procedure rex to Food item databse etc. exe

        //Well done and Congratualtions for (re)Loading to www on August 16,16 but remember (re)Loading Never Stops, ESE S.C.I LTD -Company.

        //me : receive insert and repeat trailer follow and see earlier now you : Read : Not Bad good calabo and revolve, same spot, eye of the storm or tonado we all talk to each other throgh same writings laptop screen of iPhone something we'll all have..

    }


    public void PostBreakfastTime(java.util.Date date) {
        long res = Insert_BreakfastTime(new RoundingCIF13().DateToStringeseformat(date));
        android.util.Log.d("Data Layer", "Insert Breakfast time Success! ");
    }

    public void PostLunchTime(java.util.Date date) {
        long res = Insert_LunchTime(new RoundingCIF13().DateToStringeseformat(date));
        android.util.Log.d("Data Layer", "Insert Lunch time Success! ");
    }

    public void PostDinnerTime(java.util.Date date) {
        long res = Insert_FinalMealTime(new RoundingCIF13().DateToStringeseformat(date));
        android.util.Log.d("Data Layer", "Insert Dinner Success! ");
    }


    public long PostLatestBalance(String post) {
        long res = Insert_Balance(post);
        android.util.Log.d("Data Layer", "Insert Balances Success! " + new RoundingCIF13().IntToString((int) res));
        return res;
    }

    public long PostDayend(int dayend) {
        long res = Insert_Dayend_Balance(dayend);
        android.util.Log.d("Data Layer", "Insert Dayend Success! " + new RoundingCIF13().IntToString((int) res));
        return res;
    }

    public long PostSex(String sex) {
        long res = Insert_Sex(sex);
        android.util.Log.d("Data Layer", "Insert Sex Success! " + new RoundingCIF13().IntToString((int) res));
        return res;
    }

    private boolean isFoodItemsTablePopulated() {
        return true;
    }


    public void PostValuesToTables(HealthProfileCiF3 enter) {
        if (enter.getBreakfastTime() != null) {
            String BreakfastTime = enter.getBreakfastTime();
            long res = Insert_BreakfastTime(BreakfastTime);
            android.util.Log.d("Data Layer", "Insert BreakfastTime Success! " + new RoundingCIF13().IntToString((int) res));

        }

        if (enter.getLunchTime() != null) {
            String LunchTime = enter.getLunchTime();
            long res = Insert_LunchTime(LunchTime);
            android.util.Log.d("Data Layer", "Insert LunchTime Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getFinalMealTime() != null) {
            String FinalMealTime = enter.getFinalMealTime();
            long res = Insert_FinalMealTime(FinalMealTime);
            android.util.Log.d("Data Layer", "Insert Final Meal Time Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getCurrentWeight() != null) {
            String CWeight = enter.getCurrentWeight();
            long res = Insert_CurrentWeight(CWeight);
            android.util.Log.d("Data Layer", "Insert CurrentWeight Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getTargetWeight() != null) {
            String TWeight = enter.getTargetWeight();
            long res = Insert_Target_Weight(TWeight);
            android.util.Log.d("Data Layer", "Insert TargetWeight Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getStartDate() != null) {
            Date datum = enter.getStartDate();
            long res = Insert_StartDate(datum);
            android.util.Log.d("Data Layer", "Insert StartDate Success! " + new RoundingCIF13().IntToString((int) res));
        }


        if (false) {
            //deal with
            //Exercise Time should be straight after Dinner time based on dinner time dinner time &  should be at 8:00 to have things ready for 9pm clear off hence 7:45pm should be exercise cycle time interchangebel by users.
            //endoflastsession for posting 9pm values. Add date field to this table.
            //number of days table depends on date
            //days left depends on caluculate reach zero date and substracted distance to that date.
            //last updated table with value, date and time credit balance was last used sos should be included in Countup or Countdown function

        }

        if (enter.getClientGender() != null) {
            String aGender = enter.getClientGender();
            long res = Insert_Sex(aGender);
            android.util.Log.d("Data Layer", "Insert Gender Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getReminderonoroff()) {
            boolean remind = enter.getReminderonoroff();
            long res = Insert_ReminderOnOff(remind);
            android.util.Log.d("Data Layer", "Insert ReminderOfON Success! " + new RoundingCIF13().IntToString((int) res));
        }

        if (enter.getStartCountdown() != 0) {
            int openingbalance = enter.getStartCountdown();
            long res = Insert_Balance(new RoundingCIF13().IntToString(openingbalance));
            android.util.Log.d("Data Layer", "Insert Opening Success! " + new RoundingCIF13().IntToString((int) res));
        }


    }

    public void PostBreakfastTransaction(Breakfast_Box_CIF17 bt) {
        long res = PostBreakfastTransactionTable(bt);
        long res2 = PostBreakfastMealBoxTable(bt);

    }

    public void PostLunchTransaction(Lunch_Box_CIF17 lt) {

    }

    public void PostDinnerTransaction(Dinner_Box_CIF17 dt) {

    }

    public Transactions_CIF22 Get_Transaction_Table(java.util.Date Start, java.util.Date End) {

        Transactions_CIF22 T100 = new Transactions_CIF22();

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS_TABLE + " WHERE " + COLMUM_TRANSACTIONS_DATE + " >" + "'" + Start.getTime() + "'" + "AND" + "<" + End.getTime() + "'", null);
        cursor.moveToFirst();
        Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        Meal_Items_Cursor mealio = new Meal_Items_Cursor(cursor);
        if (TransactionCursor.getCount() < 1) {
            TransactionCursor.close();
            return new Transactions_CIF22();
        } else {
            T100 = GetTransactions(TransactionCursor);
        }

        return T100;

    }

    public ArrayList<Breakfast_Box_CIF17> Get_Breakfast_Meal_Box() {

        ArrayList<Breakfast_Box_CIF17> breakfast_box_cif17 = new ArrayList<Breakfast_Box_CIF17>();

        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_MEAL_BOX_ITEMS_TABLE + "''", null);
        cursor.moveToFirst();
        Meal_Items_Cursor mealio = new Meal_Items_Cursor(cursor);
        if (mealio.getCount() < 1) {
            mealio.close();
            return new ArrayList<Breakfast_Box_CIF17>();
        } else {
            breakfast_box_cif17 = Get_Meal_Box_Items(mealio);
        }

        return breakfast_box_cif17;

    }

    public void Insert_Food_Item_CIF4(Food_Item_CIF4 IN)
    {
        String Category = IN.Get_category();
        String food_item_name = IN.Get_food_item_name();
        float grams_per_serving_portion = IN.Get_grams_per_serving_portion();
        float calories_per_serving_portion = IN.Get_calories_per_100g();
        float fat_per_serving_portion = IN.Get_fat_per_100g();
        float carbohydrates_per_serving = IN.Get_carbs_per_100g();
        float protein_per_serving = IN.Get_protein_per_100g();

        InsertDummyRice(Category, food_item_name, grams_per_serving_portion, calories_per_serving_portion, fat_per_serving_portion, carbohydrates_per_serving, protein_per_serving);


    }

    public BoxCIF17 Retrieve_Food_Items_CIF4(long StartDate, long EndDate)
    {
        return new BoxCIF17("Dinner Box");
    }

    private long PostBreakfastTransactionTable(Breakfast_Box_CIF17 in) {
        return Insert_BreakfastTransactionTable(in);
    }

    private long PostBreakfastMealBoxTable(Breakfast_Box_CIF17 in) {
        return Insert_BreakfastMealBoxTable(in);
    }

    public ArrayList<Meal_Items_Cursor> Get_Meal_Box_Table(String Transaction_ID) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_MEAL_BOX_ITEMS_TABLE + "WHERE " + COLUMN_MEAL_BOX_ID + " ==" + "'" + Transaction_ID + "'", null);
        cursor.moveToFirst();
        Meal_Items_Cursor meal_items_cursor = new Meal_Items_Cursor(cursor);
        if (meal_items_cursor.getCount() < 1) {
            meal_items_cursor.close();

        } else {

        }

        return new ArrayList<Meal_Items_Cursor>();
    }

    public Transactions_CIF22 retrieveMonthlyStatementTransactions(int Sd, int Sm, int Sy, int Ed, int Em, int Ey)
    {
        //Read

        Calendar gregDatumSD = Calendar.getInstance();
        gregDatumSD.set(2017,7,1);

        Calendar gregDatumED = Calendar.getInstance();
        gregDatumED.set(2017,7,1);

        Date StartDate = new Date(gregDatumSD.getTimeInMillis());
        Date EndDate = new Date(gregDatumED.getTimeInMillis());

        MIF22FillHerUp fakenews = new MIF22FillHerUp(mContext,StartDate,EndDate);

        return fakenews.FillHerUpVerb(new Transactions_CIF22());
    }

    public void Update_Transactions_Table(MIF4_Data_Model_Adapter.Transaction INPUT)
    {
        ;
        // ESE S.C.I. LTD Algorithm Engineering DOCUMENTATION ->

        // "Pass BoxBOXCIFWALLBoundary to Transaction Table Function plus something else,
        // transaction_table function first creates transaction Row,
        // then it creates Mealtype row by giving it it's ID in relevant Column a row for each Food_item in BoxBOXCIFWALL Boundary,
        // Give unique meal type id (auto done) but same transaction id as the creating transaction,
        // get this transaction id from returned int value on creating xor inserting Transaction ID row.


        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRANSACTIONS_ID, INPUT.Get_Transaction_ID());
        cv.put(COLMUM_TRANSACTIONS_DATE, INPUT.Get_Transaction_Date());
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE, INPUT.Get_Meal_Type().Get_MEAL_BOX_MEAL_TYPE());
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID, INPUT.Get_Transaction_Meal_Type_ID());
        cv.put(COLUMUM_TRANSACTIONS_AMOUNT, INPUT.Get_Transaction_Amount());
        cv.put(COLUMUM_BREAKFAST_BALANCE, INPUT.Get_Transaction_Balance());

        getWritableDatabase().insert(TABLE_TRANSACTIONS_TABLE, null, cv);  //sucessful insert.

        // Transaction ID

        Insert_Meal_Type_Row(INPUT.Get_Transaction_ID(), INPUT.Get_Meal_Type());

        //Continue for rest of variables.

    }



    public void Update_Meal_Box_Table(MIF4_Data_Model_Adapter.Meal_Type INPUT)
    {
        ;
    }

    private ArrayList<Breakfast_Box_CIF17> Get_Meal_Box_Items(Meal_Items_Cursor cursor)
    {
        ArrayList<Breakfast_Box_CIF17> dc = cursor.Get_Meal_Box_Action();
        return dc;

    }

    private void Insert_Meal_Type_Row(String Transaction_ID, MIF4_Data_Model_Adapter.Meal_Type INPUT)
    {

        // ESE S.C.I. LTD Algorithm Engineering Documentation ->
        // "Pass BoxBOXCIFWALLBoundary to Transaction Table Function plus something else,
        // transaction_table function first creates transaction Row,
        // then it creates Mealtype row by giving it it's ID in relevant Column a row for each Food_item in BoxBOXCIFWALL Boundary,
        // Give unique meal type id (auto done) but same transaction id as the creating transaction,
        // get this transaction id from returned int value on creating xor inserting Transaction ID row.


        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MEAL_BOX_ID, INPUT.Get_MEAL_BOX_ID());
        cv.put(COLUMUM_MEAL_BOX_MEAL_TYPE, INPUT.Get_MEAL_BOX_MEAL_TYPE());
        cv.put(COLUMUM_MEAL_BOX_MEAL_TYPE_ID, INPUT.Get_MEAL_BOX_MEAL_TYPE_ID());
        cv.put(COLUMN_MEAL_BOX_FOOD_TYPE, INPUT.Get_MEAL_BOX_FOOD_TYPE());
        cv.put(COLUMN_MEAL_BOX_FOOD_ITEM_NAME, INPUT.Get_MEAL_BOX_FOOD_ITEM_NAME());
        cv.put(COLUMN_MEAL_BOX_GRAMS_PER_SERVING_PORTION, INPUT.Get_MEAL_BOX_GRAMS_PER_SERVING_PORTION());
        cv.put(COLUMN_MEAL_BOX_FAT_PER_100G, INPUT.Get_MEAL_BOX_CALORIE_PER_100G());
        cv.put(COLUMN_MEAL_BOX_CALORIE_PER_100G, INPUT.Get_MEAL_BOX_CALORIE_PER_100G());
        cv.put(COLUMN_MEAL_BOX_SATURATED_FAT, INPUT.Get_MEAL_BOX_SATURATED_FAT());
        cv.put(COLUMN_MEAL_BOX_TRANS_FAT, INPUT.Get_MEAL_BOX_TRANS_FAT());
        cv.put(COLUMN_MEAL_BOX_PROTEIN_PER_100G, INPUT.Get_MEAL_BOX_PROTEIN_PER_100G());
        cv.put(COLUMN_MEAL_BOX_CARBS_PER_100G, INPUT.Get_MEAL_BOX_CARBS_PER_100G());
        cv.put(COLUMN_MEAL_BOX_SUGAR_PER_100G, INPUT.Get_MEAL_BOX_SUGAR_PER_100G());
        cv.put(COLUMN_MEAL_BOX_SALT_PER_100G, INPUT.Get_MEAL_BOX_SALT_PER_100G());
        cv.put(COLUMN_MEAL_BOX_WELLBEING_INDEX, INPUT.Get_MEAL_BOX_WELLBEING_INDEX());
        cv.put(COLUMN_MEAL_BOX_FIBER, INPUT.Get_MEAL_BOX_FIBER());
        cv.put(COLUMN_MEAL_BOX_PRICE_STERLING, INPUT.Get_MEAL_BOX_PRICE_STERLING());
        cv.put(COLUMN_MEAL_BOX_POLYUNSATURATED, INPUT.Get_MEAL_BOX_POLYUNSATURATED());
        cv.put(COLUMN_MEAL_BOX_MONOUNSATURATED, INPUT.Get_MEAL_BOX_MONOUNSATURATED());
        cv.put(COLUMN_MEAL_BOX_CHOLESTEROL_MG, INPUT.Get_MEAL_BOX_CHOLESTEROL_MG());
        cv.put(COLUMN_MEAL_BOX_SODIUM_MG, INPUT.Get_MEAL_BOX_SODIUM_MG());
        cv.put(COLUMN_MEAL_BOX_POTASSIUM_MG, INPUT.Get_MEAL_BOX_POTASSIUM_MG());
        cv.put(COLUMN_MEAL_BOX_VITAMIN_A_PERCENT, INPUT.Get_MEAL_BOX_VITAMIN_A_PERCENT());
        cv.put(COLUMN_MEAL_BOX_VITAMIN_C_PERCENT, INPUT.Get_MEAL_BOX_VITAMIN_C_PERCENT());
        cv.put(COLUMN_MEAL_BOX_CALCIUM_PERCENT, INPUT.Get_MEAL_BOX_CALCIUM_PERCENT());
        cv.put(COLUMN_MEAL_BOX_IRON_PERCENT, INPUT.Get_MEAL_BOX_IRON_PERCENT());
        cv.put(COLUMN_MEAL_BOX_CATEGORY, INPUT.Get_MEAL_BOX_CATEGORY());


        getWritableDatabase().insert(TABLE_MEAL_BOX_ITEMS_TABLE, null, cv);  //sucessful insert.

        // Transaction ID

        Insert_Meal_Type_Row(Transaction_ID, INPUT);

        //Continue for rest of variables.

    }

    public long Insert_BoxCIF(Transaction_Line_CIF17 INPUT)
    {
        return Insert_MealBoxCIF(INPUT.Get_Transaction_ID(),INPUT.Get_Transaction_Food_Items());
    }

    public long Insert_MealBoxCIF(long ID, BoxCIF17 In)
    {
        In.Set_Transaction_ID_for_all_Food_items(ID);
        return Insert_MealBox(In);
    }

    public long Insert_MealBox(BoxCIF17 Input)
    {
        Breakfast_Box_CIF17 into = Transform_Box_to_Breakfast_Meal_Box(Input);

        return Insert_BreakfastMealBoxTable(into);
    }

    private Breakfast_Box_CIF17 Transform_Box_to_Breakfast_Meal_Box(BoxCIF17 In)
    {
        Breakfast_Box_CIF17 OUTPUT = new Breakfast_Box_CIF17();

        OUTPUT.Set_Breakfast_ID(In.Get_Transaction_ID());
        OUTPUT.Set_Breakfast_Date(new Date().getTime());
        OUTPUT.Set_Breakfast_Meal_Type(In.Get_Meal_Type());
        OUTPUT.Set_Breakfast_Meal_Type_ID(In.Get_Transaction_ID());
        OUTPUT.Set_Breakfast_Amount(In.SumBox());
        OUTPUT.Set_Breakfast_Balance(In.Get_Balance());
        OUTPUT.Set_Meal_Box_ID(In.Get_Transaction_ID());
        OUTPUT.Set_Meal_Box_Food_Type(In.Get_Meal_Type());
        OUTPUT.Set_Food_Item_List_two(In.Get_Food_Items());
        OUTPUT.Set_Calories_IN(In.SumBox());
        OUTPUT.Set_Calories_OUT(In.Get_Energy_OUT());
        OUTPUT.Set_Balance((In.SumBox() + In.Get_Balance()));

        return OUTPUT;



    }

    public void Delete_Food_items_Table()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM food_items");
        db.close();

        //String[] args = {"null"};
        //String whereclause = "";
        //getWritableDatabase().delete(TABLE_FOODITEMS,whereclause,args);

        /*db.execSQL("Create table Food_Items (" + "_id integer primary key autoincrement, " +
                "food_type varchar(100), " +
                "food_item_name varchar(100), " +
                "grams_per_serving_portion real," +
                "calories_per_100g real," +
                "fat_per_100g real," +
                "saturated_fat real," +
                "trans_fat real," +
                "protein_per_100g real," +
                "carbs_per_100g real," +
                "sugar_per_100g real," +
                "salt_per_100g real," +
                "wellbeing_index real," +
                "fiber real," +
                "price_sterling real," +
                "category varchar(100)," +
                "polyunsaturated real," +
                "monounsaturated real," +
                "cholesterol_mg real," +
                "sodium_mg real," +
                "potassium_mg real," +
                "vitamin_a_percent real," +
                "vitamin_c_percent real," +
                "calcium_percent real," +
                "iron_percent real)");*/

    }

    public void Delete_Transaction_Table()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM transactions_xp");
        db.close();
    }

    public void Delete_Meal_Box_Table()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM meal_box_items");
        db.close();
    }

    public long Insert_TransactionTable_x(Transactions_CIF22 IN)
    {

        //return Insert_TransactionTable(new MIF22FillHerUp(mContext).FillWithFakeData(new Transactions_CIF22()).Get_TransactionLines().get(0));

        //Transactions_CIF22 Output = (new MIF22FillHerUp(mContext)).FillWithFakeData(new Transactions_CIF22());

        long index = 0;

        for(Transaction_CIF52 x : IN.Get_TransactionLines())
        {
          index = Insert_TransactionTable(x);

           Log.d("INDEX #", new RoundingCIF13().LongToString(index));
        }

        Log.d("SQLite", " Check this out Baby! " + IN.Print());

        return index;


    }

    public long Get_Unique_Row_Identifier_from_Transactions_Table()
    {
        long reserve = Insert_Dummy_Transaction_Row(new Transaction_CIF52());
        reserve = reserve + 1;
        return reserve;

    }

    public void onClose()
    {
        getWritableDatabase().close();
        close();
    }

    private long Insert_Dummy_Transaction_Row(Transaction_CIF52 transaction_type)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRANSACTIONS_ID, 2435664);
        cv.put(COLMUM_TRANSACTIONS_DATE, 59354345);
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE, "Dummy5");
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID, 14);
        cv.put(COLUMUM_TRANSACTIONS_AMOUNT, 56);
        cv.put(COLUMUM_TRANSACTIONS_BALANCE, 87);
        long ID = getWritableDatabase().insert(TABLE_TRANSACTIONS_TABLE, null, cv);

        //BROWN DOCUMENTAION
        //Delete last transaction row either specified where Meal type = Dummy any row or column or just delete the last entry.
        Delete_Dummy_Rows();

        return ID;
    }

    public int Get_Unique_Row_Identifier_from_Transaction_Table()
    {
        //IDO DOCUMENTION
        //Finds the insert ID of the last row in relevant field and adds one too it, make sure the next time
        //Something is inserted in doesn't give the same number, to prevent this, insert a Dummy row, use
        //ID of the Dummy row, return it then delete dummy row.

        //Algorithm Engineering -> ANDROID

        return 1;
    }

    public long PostBtime(int IN)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_BREAKFAST_TIME_BREAKFASTTIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_BREAKFAST_TIME, null, null);
        return getWritableDatabase().insert(TABLE_BREAKFAST_TIME, null, cv);

    }

    public long PostLtime(int IN)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_LUNCH_TIME_LUNCHTIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_LUNCH_TIME, null, null);
        return getWritableDatabase().insert(TABLE_LUNCH_TIME, null, cv);

    }

    public long PostDtime(int IN)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_FINAL_MEAL_TIME_FINALMEATIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_FINAL_MEAL_TIME, null, null);
        return getWritableDatabase().insert(TABLE_FINAL_MEAL_TIME, null, cv);

    }


    public int GetBtime()
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_BREAKFAST_TIME, null);
        cursor.moveToFirst();
        BreakfasttimeCursor BtimeCursor = new BreakfasttimeCursor(cursor);
        if (BtimeCursor.getCount() < 1) {
            BtimeCursor.close();
            return new RoundingCIF13().StringToInt("1000");
        } else {
            return GetBreakfastttime(BtimeCursor);
        }

    }

    public int GetLtime()
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_LUNCH_TIME, null);
        cursor.moveToFirst();
        LunchtimeCursor LtimeCursor = new LunchtimeCursor(cursor);
        if (LtimeCursor.getCount() < 1) {
            LtimeCursor.close();
            return new RoundingCIF13().StringToInt("1430");
        } else {
            return GetLunchtttime(LtimeCursor);
        }

    }

    public int GetDtime()
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_FINAL_MEAL_TIME, null);
        cursor.moveToFirst();
        DinnertimeCursor DtimeCursor = new DinnertimeCursor(cursor);
        if (DtimeCursor.getCount() < 1) {
            DtimeCursor.close();
            return new RoundingCIF13().StringToInt("2030");
        } else {
            return GetDinnertttime(DtimeCursor);
        }

    }


    public long StoreHealthProfile(HealthProfileCiF3 in)
    {
        ContentValues cv = new ContentValues();


            cv.put(COLUMN_VITALS_ID, (int) (in.getVital_id()));
            cv.put(COLUMN_Client_ACCOUNT_NAME, (in.getFirstname() + " " + in.getLastname()));
            cv.put(COLUMN_Client_DOB, (float) in.getDOB2());
            cv.put(COLUMN_Client_BMI, (int) in.getClientBMI());
            cv.put(COLUMN_Client_BMR, (int) in.getBMR());
            cv.put(COLUMN_Client_BODYFAT, (int) (in.getClientBodyFat()));
            cv.put(COLUMN_Client_VITALS_STRING, in.getVitalStatsString2());
            cv.put(COLUMN_Client_OPENING_BALANCE, in.getStartCountdown());
            cv.put(COLUMN_Client_HEIGHT_CM, new RoundingCIF13().StringToInt(in.getClientHeight()));
            cv.put(COLUMN_Client_EMAIL, in.getEmailaddress());
            cv.put(COLUMN_Client_GENDER, in.getClientGender());
            cv.put(COLUMN_Client_START_WEIGHT, new RoundingCIF13().StringToInt(in.getStartWeight()));
            cv.put(COLUMN_Client_TARGET_WEIGHT, new RoundingCIF13().StringToInt(in.getTargetWeight()));
            cv.put(COLUMN_Client_BODY_FRAME, in.getClientBodyFrame());
            cv.put(COLUMN_Client_START_DATE, (float) (in.getStartDate().getTime()));
            cv.put(COLUMN_Client_LEAN_BODY_MASS, (int) in.Get_Lean_Body_Mass());
            cv.put(COLUMN_Client_WAIST_CIRCUMFERENCE, (int) in.Get_Waist_Circumference());
            cv.put(COLUMN_Client_BLOOD_PRESSURE, (int) in.Get_Blood_Pressure());
            cv.put(COLUMN_Client_BODY_TEMPERATURE, (int) in.Get_Body_Temperture());
            cv.put(COLUMN_Client_ALCOHOL_CONTENT, (int) in.Get_Alcohol_Content());
            cv.put(COLUMN_Client_BLOOD_GLUCOSE, (int) in.Get_Blood_Glucose());
            cv.put(COLUMN_Client_ECG_, (int) in.Get_Electrocardiogram_ECG());
            cv.put(COLUMN_Client_HEART_RATE_VARIABILITY, (int) in.Get_Heart_Rate_Variability());
            cv.put(COLUMN_Client_HIGH_HEART_RATE_NOTIFICATION, in.Get_High_Heart_Rate_Notifications());
            cv.put(COLUMN_Client_IRREGULAR_HEART_RHYTHM_NOTIFICATION, in.Get_Irregular_Rhythm_Notifications());
            cv.put(COLUMN_Client_LOW_HEART_RATE_NOTIFICATION, in.Get_Low_Heart_Rate_Notifications());
            cv.put(COLUMN_Client_WALKING_HEART_RATE, (int) in.Get_Walking_Heart_Rate());
            cv.put(COLUMN_Client_HEART_RATE, (int) in.Get_Heart_Rates());
            cv.put(COLUMN_Client_RESTING_HEART_RATE, (int) (in.Get_Resting_Heart_Rate()));
            cv.put(COLUMN_Client_RESPIRATORY_RATE, (int) (in.Get_Respiratory_Rates()));
            cv.put(COLUMN_Client_ELECTRODERMAL_ACTIVITY, (int) (in.Get_Electrodermal_Activity()));
            cv.put(COLUMN_Client_FORCED_EXPIRATORY_VOLUME, (int) (in.Get_Forced_Expiratory_Volume_1_sec()));
            cv.put(COLUMN_Client_FORCED_VITAL_CAPACITY, (int) (in.Get_Forced_Vital_Capacity()));
            cv.put(COLUMN_Client_INHALER_USAGE, in.Get_Inhaler_Usage());
            cv.put(COLUMN_Client_INSULIN_DELIVERY, in.Get_Insulin_Delivery());
            cv.put(COLUMN_Client_OXYGEN_SATURATION, (int) (in.Get_Oxygen_Saturation()));
            cv.put(COLUMN_Client_PEAK_EXPIRATORY_FLOW_RATE, (int) (in.Get_Peak_Expiratory_Flow_Rate()));
            cv.put(COLUMN_Client_PERIPHERAL_PERFUSION_INDEX, (int) (in.Get_Peripheral_Perfusion_Index()));
            cv.put(COLUMN_Client_UV_INDEX, (int) (in.Get_UV_Index()));
            cv.put(COLUMN_Client_DATA_SOURCES, in.Get_Data_Sources());
            cv.put(COLUMN_Client_MANUAL_SOURCES, in.Get_Manual_Data_Sourcing());



        return getWritableDatabase().insert(TABLE_HEALTH_PROFILE_TABLE, null, cv);


    }

    public HealthProfileCiF3 Really_Get_HealthProfileCIF3()
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_HEALTH_PROFILE_TABLE, null);
        cursor.moveToFirst();
        SHealth_Cursor_241 TransactionCursor = new SHealth_Cursor_241(cursor);
        HealthProfileCiF3 OUTPUT;



        if (TransactionCursor.getCount() < 1)
        {
            Log.d(TAG, "NOTHING FOUND IN Health Vitals DATABASE");
            TransactionCursor.close();
            return new HealthProfileCiF3();
        }
        else
        {
            try {
                Log.d(TAG, "CONTENTS IN Health Vitals DATABASE");
                OUTPUT = GetHealthProfile_CIF3(TransactionCursor);


                //Breakfast_Box_CIF17 itet = new Breakfast_Box_CIF17();

                //Log.d("Contents Meal Boxes", itet.Get_Food_Items().get(0).Get_food_item_name());

                return OUTPUT;

                //BoxCIF17 OUTPUTc = Transform_Breakfast_Box_to_Box(OUTPUTb);
                //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
                //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);
            }
            catch(Exception x)
            {
                Toast.makeText(mContext, "SHealth_Cursor is empty aborting function", Toast.LENGTH_SHORT);
            }


            return new HealthProfileCiF3();
        }

    }

    public HealthProfileCiF3 getHealthProfileObjectSerializable()
    {
        return new HealthProfileCiF3();
    }

    public void Iterate_through_thisTableA_Output_(JSONWrapperCIFClass INPUT_TABLE_A)
    {
        //For Table A go through all the Rows //iterate

        //For each row, use a fragemented_box to INPUT Row OUTPUT a data object frag_box

        //fragmented_box
        Row_Converter_CiF1001_fragment_box_Class rcc = new Row_Converter_CiF1001_fragment_box_Class(INPUT_TABLE_A);
        //rcc.iterate(MiF4_function_to_peform_on_Transaction_CIF52_Line(rcc.transform(INPUT_TABLE_A)));
        rcc.iterate();


    }

    private ArrayList<Breakfast_Box_CIF17> Really_Get_Box_items()
    {
        ArrayList<Breakfast_Box_CIF17> OUTPUTb;
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_MEAL_BOX_ITEMS_TABLE, null);
        cursor.moveToFirst();
        Meal_Items_Cursor MealItemCursor = new Meal_Items_Cursor(cursor);

        if (MealItemCursor.getCount() < 1)
        {
            Log.d(TAG, "NOTHING FOUND IN Meal box DATABASE");
            MealItemCursor.close();
            OUTPUTb = new ArrayList<Breakfast_Box_CIF17>();

            return OUTPUTb;
        }
        else
        {
            Log.d(TAG, "CONTENTS IN Meal box DATABASE");
            OUTPUTb = GetBoxes(MealItemCursor);

            //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
            //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);

            Display_Outputb(OUTPUTb);

            return OUTPUTb;
        }


    }

    private void Display_Outputb(ArrayList<Breakfast_Box_CIF17> INPUT)
    {
        try {


            for (Breakfast_Box_CIF17 m : INPUT) {
                Log.d("OUTPUTb: B Meal_type", m.Get_Breakfast_Meal_Type());
                Log.d("OUTPUTb: B Meal_BxFtype", m.Get_Meal_Box_Food_Type());
                Log.d("OUTPUTb: B Amount", new RoundingCIF13().IntToString(m.Get_Breakfast_Amount()));
                Log.d("OUTPUTb: B Balance", new RoundingCIF13().IntToString(m.Get_Breakfast_Balance()));
                Log.d("OUTPUTb: Long Date", new RoundingCIF13().LongToString(m.Get_Breakfast_Date()));
                Log.d("OUTPUTb: The ID", new RoundingCIF13().LongToString((m.Get_Breakfast_ID())));
                Log.d("OUTPUTb: B Meal_ID", new RoundingCIF13().LongToString(m.Get_Breakfast_Meal_Type_ID()));
                Log.d("OUTPUTb: Meal Box ID", new RoundingCIF13().LongToString(m.Get_Meal_Box_ID()));
                Log.d("OUTPUTb: Calorie IN", new RoundingCIF13().IntToString(m.Get_Calories_IN()));
                Log.d("OUTPUTb: Calorie OUT", new RoundingCIF13().IntToString(m.Get_Calories_OUT()));

                for (Food_Item_CIF4 x : m.Get_Food_Item_List_two()) {
                    Log.d("Food Items Time : ", x.Get_food_item_name());
                }
            }
        }
            catch(Exception e)
            {
                Log.d("OUTPUTb err", "Something went wrong in one of the data content.");
            }

        }


    private BoxCIF17 Transform_Breakfast_Box_to_Box(Breakfast_Box_CIF17 IN)
    {
        BoxCIF17 OUTPUT = new BoxCIF17();

        OUTPUT.Set_Balance(IN.Get_Breakfast_Balance());
        android.util.Log.d("Transform Balance: ", new RoundingCIF13().IntToString(IN.Get_Balance()));

        OUTPUT.Set_Energy_OUT(IN.Get_Energy_OUT());
        android.util.Log.d("Transform Energy Out: ", new RoundingCIF13().IntToString(IN.Get_Energy_OUT()));

        OUTPUT.add_Meal_Box_ID((int)IN.Get_Meal_Box_ID());
        android.util.Log.d("Transform MealBxID: ", new RoundingCIF13().LongToString((IN.Get_Meal_Box_ID())));

        //OUTPUT.Set_Food_Items(IN.Get_Food_Item_List_two());
        //android.util.Log.d("Transform food item: ", IN.Get_Food_Item_List_two().get(0).Get_food_item_name());

        OUTPUT.Set_Meal_Box_ID(IN.Get_Meal_Box_ID());
        android.util.Log.d("Transform MealBxID: ", new RoundingCIF13().LongToString((IN.Get_Meal_Box_ID())));

        OUTPUT.Set_Meal_Type(IN.Get_Breakfast_Meal_Type());
        android.util.Log.d("Transform Meal type: ", IN.Get_Meal_Type());

        OUTPUT.Set_Transaction_ID_for_all_Food_items(IN.Get_Transactions_ID());
        android.util.Log.d("Transform Tran ID: ", new RoundingCIF13().LongToString((IN.Get_Transactions_ID())));

        return OUTPUT;
    }

    private Transactions_CIF22 Final_Transform(ArrayList<Breakfast_Box_CIF17> Input, Transactions_CIF22 IN)
    {

        //IN a Breakfast/Box Meal integrate into Transaction CIF22 to come up with final product.
        //CiF22 has CiF52 contains a transaction line.
        //Transaction Line Contains boxed item so breakfast box must be transffered in lines stages and layers to Cif17 boxed items

        BoxCIF17 boxCIF17;
        Transaction_CIF52 transaction_cif52;
        Transaction_Line_CIF17 line_cif17;


        for( Breakfast_Box_CIF17 m : Input)
        {
            boxCIF17 = Transform_Breakfast_Box_to_Box(m);
            line_cif17 = new Transaction_Line_CIF17();
            line_cif17.Set_Transaction_Food_Items(boxCIF17);

            transaction_cif52 = new Transaction_CIF52();
            transaction_cif52.Set_Single_Transaction_Line(line_cif17);
            IN.Add_TransactionLine_2_List(transaction_cif52);

        }

        return IN;
    }

    private Transactions_CIF22 Merge_Food_items(Transactions_CIF22 IN, ArrayList<Breakfast_Box_CIF17> INPUT)
    {
        for( Transaction_CIF52 m : IN.Get_TransactionLines())
        {
            for( Breakfast_Box_CIF17 n : INPUT)
            {
                if(n.Get_Transactions_ID() == m.Get_Single_Transaction_Line().Get_Transaction_ID())
                {
                    for( Food_Item_CIF4 o : n.Get_Food_Item_List_two())
                    {
                        m.Get_Single_Transaction_Line().add_Food_item(o);
                    }
                }
            }

        }

        return IN;
    }


    public void PostValueToTargetWeightTable(String Input1)
    {
        long res = Insert_Target_Weight(Input1);
    }



    private void Delete_Dummy_Rows()
    {
        //Alogrithm Engineering -> Delete Row Where Meal_Type = 'Dummy"
        //Delete all Rows where Meal_type = Dummy.
    }

    private void deleteTargetWeightTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM target_weight");
        db.close();
    }

}


