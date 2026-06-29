package ese.com.caloriecountdownappforandroidbrown;

import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;

import java.util.ArrayList;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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
    private static final int VERSION = 13; // v13: steps_challenge_log table

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

    private static final String TABLE_DAYEND_BALANCE2 = "dayend_balance2";
    private static final String COLUMN_DAYEND_ID2 = "dayend_id";
    private static final String COLUMN_DAYEND_BALANCE_DATE2 = "balance_date";
    private static final String COLUMN_DAYEND_BALANCE_BALANCE_BUDGET = "balance_dayend_budget";
    private static final String COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL = "balance_dayend_actual";


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
    private static final String COLUMN_Client_VITALS_STRING = "client_vitals_string";
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

    //    Quick Food Note
    private static final String TABLE_QUICK_FOOD_NOTE = "quick_food_note";
    private static final String COLUMN_QUICK_FOOD_NOTE_ID = "note_id";
    private static final String COLUMN_QUICK_FOOD_NOTE_DATE = "note_date";
    private static final String COLUMN_QUICK_FOOD_NOTE_FOOD = "note_food";
    private static final String COLUMN_QUICK_FOOD_NOTE_CALORIES = "note_calories";
    private static final String COLUMN_QUICK_FOOD_NOTE_QUANTITY = "note_quantity";
    private static final String COLUMN_IS_TRANSFERRED = "isTransferred";
    private static final String COLUMN_IS_SAVED_TO_COLLECTION = "isSavedToCollection";
    // Backend sync status (added in DB version 11): 0 = pending, 1 = synced
    private static final String COLUMN_IS_SYNCED = "is_synced";

    // 4PM Processing columns (added in DB version 7)
    private static final String COLUMN_IS_4PM_PROCESSED = "is_4pm_processed";
    private static final String COLUMN_PROCESSED_DATE = "processed_date";
    private static final String COLUMN_AI_UPDATED_FIELDS = "ai_updated_fields";
    private static final String COLUMN_FINAL_CALORIES = "final_calories";
    private static final String COLUMN_FINAL_POINTS = "final_points";

    // 4PM Processing Log Table (added in DB version 7)
    private static final String TABLE_FOURPM_LOG = "fourpm_processing_log";
    private static final String COLUMN_LOG_ID = "log_id";
    private static final String COLUMN_LOG_PROCESSING_DATE = "processing_date";
    private static final String COLUMN_LOG_TOTAL_CALORIES = "total_calories";
    private static final String COLUMN_LOG_TOTAL_POINTS = "total_points";
    private static final String COLUMN_LOG_NOTE_IDS_JSON = "note_ids_json";
    private static final String COLUMN_LOG_CREATED_AT = "created_at";

    // 4PM Debit / Steps Challenge Table (added in DB version 8)
    private static final String TABLE_FOURPM_DEBIT = "fourpm_debit_challenge";
    private static final String COLUMN_DEBIT_ID              = "challenge_id";
    private static final String COLUMN_DEBIT_DATE            = "challenge_date";       // TEXT UNIQUE "dd-MM-yyyy"
    private static final String COLUMN_DEBIT_FOOD_CALORIES   = "total_food_calories";
    private static final String COLUMN_DEBIT_DAILY_BUDGET    = "daily_budget";
    private static final String COLUMN_DEBIT_BALANCE         = "countdown_balance";
    private static final String COLUMN_DEBIT_KITTY_EXCESS    = "kitty_excess";
    private static final String COLUMN_DEBIT_BALANCE_PENALTY = "balance_penalty";
    private static final String COLUMN_DEBIT_EXCESS_CALORIES = "excess_calories";
    private static final String COLUMN_DEBIT_STEP_CHALLENGE  = "step_challenge";
    private static final String COLUMN_DEBIT_IS_CAPPED       = "is_capped";            // 0/1
    private static final String COLUMN_DEBIT_CREATED_AT      = "created_at";

    // Exercise Items Table (added in DB version 9) — Cardio & Strength Training
    private static final String TABLE_EXERCISE_ITEMS          = "exercise_items";
    private static final String COLUMN_EXERCISE_ID            = "exercise_id";
    private static final String COLUMN_EXERCISE_CATEGORY      = "exercise_category"; // CARDIO | STRENGTH_TRAINING
    private static final String COLUMN_EXERCISE_NAME          = "exercise_name";
    private static final String COLUMN_EXERCISE_DURATION_MIN  = "exercise_duration_minutes";
    private static final String COLUMN_EXERCISE_REPS          = "exercise_reps";
    private static final String COLUMN_EXERCISE_CAL_PER_UNIT  = "exercise_calories_per_unit";

    // Recorded Steps Table (added in DB version 10) — Midnight Scrape reconciliation
    private static final String TABLE_RECORDED_STEPS   = "recorded_steps";
    private static final String COLUMN_RS_ID           = "rs_id";
    private static final String COLUMN_RS_DATE         = "rs_date";        // "dd-MM-yyyy"
    private static final String COLUMN_RS_STEP_COUNT   = "rs_step_count";

    //    Memo Table (FIFO max 10 entries)
    private static final String TABLE_MEMO = "memo";
    private static final String COLUMN_MEMO_ID = "memo_id";
    private static final String COLUMN_MEMO_DATE = "memo_date";
    private static final String COLUMN_MEMO_TEXT = "memo_text";
    private static final String COLUMN_MEMO_IMAGE = "memo_image";
    private static final String COLUMN_MEMO_TITLE = "memo_title";
    private static final int MAX_MEMO_COUNT = 10;

    //    Food Notes Collection Table - stores collections of food notes by date
    private static final String TABLE_FOOD_NOTES_COLLECTION = "food_notes_collection";
    private static final String COLUMN_COLLECTION_ID = "collection_id";
    private static final String COLUMN_COLLECTION_DATE = "collection_date";
    private static final String COLUMN_COLLECTION_NOTES_JSON = "notes_json";
    private static final String COLUMN_COLLECTION_TOTAL_CALORIES = "total_calories";
    private static final String COLUMN_COLLECTION_CREATED_AT = "created_at";

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


    public static final String TABLE_WATER_TRACKER = "WaterTracker";
    public static final String COLUMN_WATER_UNIQUE_ID = "uniqueId";
    public static final String COLUMN_WATER_DATE = "date";
    public static final String COLUMN_WATER_ML = "mlWaterDrunk";
    public static final String COLUMN_WATER_CUPS = "equivalentCups";

    // Heart Rate Tracker Table
    public static final String TABLE_HEART_RATE = "HeartRateTracker";
    public static final String COLUMN_HEART_RATE_ID = "id";
    public static final String COLUMN_HEART_RATE_DATE = "date";
    public static final String COLUMN_HEART_RATE_TIME = "time";
    public static final String COLUMN_HEART_RATE_BPM = "bpm";
    public static final String COLUMN_HEART_RATE_NOTE = "note";

    // ── Health Data Reading Tables (v12) ─────────────────────────────────────

    // Blood Pressure
    public static final String TABLE_HEALTH_BP         = "HealthDataBloodPressure";
    public static final String COLUMN_BP_ID            = "id";
    public static final String COLUMN_BP_DATE          = "date";
    public static final String COLUMN_BP_TIME          = "time";
    public static final String COLUMN_BP_SYSTOLIC      = "systolic";
    public static final String COLUMN_BP_DIASTOLIC     = "diastolic";
    public static final String COLUMN_BP_CREATED_AT    = "created_at";

    // Heart Rate Readings (manual entry — separate from camera-based HeartRateTracker)
    public static final String TABLE_HEALTH_HR         = "HealthDataHeartRate";
    public static final String COLUMN_HRDATA_ID        = "id";
    public static final String COLUMN_HRDATA_DATE      = "date";
    public static final String COLUMN_HRDATA_TIME      = "time";
    public static final String COLUMN_HRDATA_BPM       = "bpm";
    public static final String COLUMN_HRDATA_CREATED_AT = "created_at";

    // Blood Sugar
    public static final String TABLE_HEALTH_BS         = "HealthDataBloodSugar";
    public static final String COLUMN_BS_ID            = "id";
    public static final String COLUMN_BS_DATE          = "date";
    public static final String COLUMN_BS_TIME          = "time";
    public static final String COLUMN_BS_MGDL          = "mg_dl";
    public static final String COLUMN_BS_CREATED_AT    = "created_at";

    // ── Steps Challenge Log (v13) ─────────────────────────────────────────────
    private static final String TABLE_STEPS_CHALLENGE      = "steps_challenge_log";
    private static final String COLUMN_SC_ID               = "challenge_id";
    private static final String COLUMN_SC_DATE             = "challenge_date";
    private static final String COLUMN_SC_CURRENT_BALANCE  = "current_balance";
    private static final String COLUMN_SC_PREV_DAY_END     = "previous_day_end";
    private static final String COLUMN_SC_DAY_END_TARGET   = "day_end_target";
    private static final String COLUMN_SC_BMR              = "bmr";
    private static final String COLUMN_SC_STEP_CHALLENGE   = "step_challenge";
    private static final String COLUMN_SC_CREATED_AT       = "sc_created_at";

    private Context mContext;
    private Boolean time_is_After_Four_Thirty_PM = false;
    private Boolean BMR_has_been_performed = false;
    private Boolean havePerformedDayENDCFWD = false;


    //or onCreate
    public SQLDatabase_Food_Items_CIF6(Context context) {
        super(context, DB_NAME, null, VERSION);

        mContext = context;

        SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            android.util.Log.d("app", "For Some reason the database is not opening...");
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

        try //at launch take all table creations to OnCreate no such table: dayend_balance2
        {
            db.execSQL("Create table if not exists dayend_balance2 (" + "_id integer primary key autoincrement, " +
                    "balance_date varchar(100), " +
                    "balance_dayend_budget varchar(100), " +
                    "balance_dayend_actual varchar(100))");
        } catch (SQLException alreadyexist) {

        }

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

        try {
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


        } catch (SQLException alreadyexist) {

        }

        try {
            String CREATE_WATER_TRACKER_TABLE = "CREATE TABLE " + TABLE_WATER_TRACKER + " (" +
                    COLUMN_WATER_UNIQUE_ID + " TEXT PRIMARY KEY," +
                    COLUMN_WATER_DATE + " TEXT," +
                    COLUMN_WATER_ML + " INTEGER," +
                    COLUMN_WATER_CUPS + " REAL" +
                    ")";
            db.execSQL(CREATE_WATER_TRACKER_TABLE);
        } catch (SQLException alreadyexist) {

        }

        // Create Heart Rate Tracker Table
        try {
            String CREATE_HEART_RATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HEART_RATE + " (" +
                    COLUMN_HEART_RATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_HEART_RATE_DATE + " TEXT," +
                    COLUMN_HEART_RATE_TIME + " TEXT," +
                    COLUMN_HEART_RATE_BPM + " INTEGER," +
                    COLUMN_HEART_RATE_NOTE + " TEXT" +
                    ")";
            db.execSQL(CREATE_HEART_RATE_TABLE);
        } catch (SQLException alreadyexist) {
            // Table already exists
        }

        // ── Health Data Reading Tables (v12) ─────────────────────────────────
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_BP + " ("
                    + COLUMN_BP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_BP_DATE + " TEXT, "
                    + COLUMN_BP_TIME + " TEXT, "
                    + COLUMN_BP_SYSTOLIC + " INTEGER DEFAULT 0, "
                    + COLUMN_BP_DIASTOLIC + " INTEGER DEFAULT 0, "
                    + COLUMN_BP_CREATED_AT + " TEXT DEFAULT '')");
        } catch (Exception ignore) { /* already exists */ }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_HR + " ("
                    + COLUMN_HRDATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_HRDATA_DATE + " TEXT, "
                    + COLUMN_HRDATA_TIME + " TEXT, "
                    + COLUMN_HRDATA_BPM + " INTEGER DEFAULT 0, "
                    + COLUMN_HRDATA_CREATED_AT + " TEXT DEFAULT '')");
        } catch (Exception ignore) { /* already exists */ }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_BS + " ("
                    + COLUMN_BS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_BS_DATE + " TEXT, "
                    + COLUMN_BS_TIME + " TEXT, "
                    + COLUMN_BS_MGDL + " INTEGER DEFAULT 0, "
                    + COLUMN_BS_CREATED_AT + " TEXT DEFAULT '')");
        } catch (Exception ignore) { /* already exists */ }

        // Steps Challenge Log (v13)
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STEPS_CHALLENGE + " ("
                    + COLUMN_SC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_SC_DATE + " TEXT UNIQUE, "
                    + COLUMN_SC_CURRENT_BALANCE + " INTEGER DEFAULT 0, "
                    + COLUMN_SC_PREV_DAY_END + " INTEGER DEFAULT 0, "
                    + COLUMN_SC_DAY_END_TARGET + " INTEGER DEFAULT 0, "
                    + COLUMN_SC_BMR + " INTEGER DEFAULT 0, "
                    + COLUMN_SC_STEP_CHALLENGE + " INTEGER DEFAULT 0, "
                    + COLUMN_SC_CREATED_AT + " TEXT DEFAULT '')");
        } catch (Exception ignore) { /* already exists */ }
        // ─────────────────────────────────────────────────────────────────────

        // ====== 4PM Food Notes Processing — DB v7 additions ======
        // ALTER TABLE quick_food_note to add 4PM processing columns (safe to retry each launch)
        try { db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_IS_4PM_PROCESSED + " INTEGER DEFAULT 0"); } catch (Exception ignore4pm1) { /* column already exists */ }
        try { db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_PROCESSED_DATE + " TEXT DEFAULT ''"); } catch (Exception ignore4pm2) { /* column already exists */ }
        try { db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_AI_UPDATED_FIELDS + " TEXT DEFAULT ''"); } catch (Exception ignore4pm3) { /* column already exists */ }
        try { db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_FINAL_CALORIES + " INTEGER DEFAULT 0"); } catch (Exception ignore4pm4) { /* column already exists */ }
        try { db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_FINAL_POINTS + " INTEGER DEFAULT 0"); } catch (Exception ignore4pm5) { /* column already exists */ }

        // Create 4PM processing log table
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOURPM_LOG + " ("
                    + COLUMN_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_LOG_PROCESSING_DATE + " TEXT UNIQUE, "
                    + COLUMN_LOG_TOTAL_CALORIES + " INTEGER DEFAULT 0, "
                    + COLUMN_LOG_TOTAL_POINTS + " INTEGER DEFAULT 0, "
                    + COLUMN_LOG_NOTE_IDS_JSON + " TEXT DEFAULT '', "
                    + COLUMN_LOG_CREATED_AT + " TEXT DEFAULT '')");
        } catch (Exception ignore4pm6) { /* table already exists */ }
        // ====== End 4PM additions ======

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
        } catch (SQLException alreadyexist) {

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
        //Quick Food Note
        try {
            android.util.Log.d("Table creation", "Table quick note creation start");
            String CREATE_QUICK_FOOD_NOTE_TABLE = "CREATE TABLE " + TABLE_QUICK_FOOD_NOTE + " ("
                    + COLUMN_QUICK_FOOD_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_QUICK_FOOD_NOTE_DATE + " TEXT, "
                    + COLUMN_QUICK_FOOD_NOTE_FOOD + " TEXT, "
                    + COLUMN_QUICK_FOOD_NOTE_CALORIES + " TEXT, "
                    + COLUMN_QUICK_FOOD_NOTE_QUANTITY + " TEXT, "
                    + COLUMN_IS_TRANSFERRED + " INTEGER DEFAULT 0, " // 0 = false, 1 = true
                    + COLUMN_IS_SAVED_TO_COLLECTION + " INTEGER DEFAULT 0, " // 0 = not saved, 1 = saved
                    + COLUMN_IS_SYNCED + " INTEGER DEFAULT 0" // 0 = pending backend sync, 1 = synced
                    + ")";
            db.execSQL(CREATE_QUICK_FOOD_NOTE_TABLE);
            android.util.Log.d("Table creation", "created" + TABLE_QUICK_FOOD_NOTE);
        } catch (Exception e) {
            android.util.Log.d("Table creation", "Table creation in catch block");
            throw new RuntimeException(e);
        }

        // Create Memo Table
        try {
            android.util.Log.d("Table creation", "Table memo creation start");
            String CREATE_MEMO_TABLE = "CREATE TABLE " + TABLE_MEMO + " ("
                    + COLUMN_MEMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_MEMO_DATE + " TEXT, "
                    + COLUMN_MEMO_TEXT + " TEXT, "
                    + COLUMN_MEMO_IMAGE + " TEXT, "
                    + COLUMN_MEMO_TITLE + " TEXT"
                    + ")";
            db.execSQL(CREATE_MEMO_TABLE);
            android.util.Log.d("Table creation", "created " + TABLE_MEMO);
        } catch (Exception e) {
            android.util.Log.d("Table creation", "Memo table creation in catch block: " + e.getMessage());
        }

        // Create Food Notes Collection Table
        try {
            android.util.Log.d("Table creation", "Table food notes collection creation start");
            String CREATE_FOOD_NOTES_COLLECTION_TABLE = "CREATE TABLE " + TABLE_FOOD_NOTES_COLLECTION + " ("
                    + COLUMN_COLLECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_COLLECTION_DATE + " TEXT, "
                    + COLUMN_COLLECTION_NOTES_JSON + " TEXT, "
                    + COLUMN_COLLECTION_TOTAL_CALORIES + " INTEGER, "
                    + COLUMN_COLLECTION_CREATED_AT + " TEXT"
                    + ")";
            db.execSQL(CREATE_FOOD_NOTES_COLLECTION_TABLE);
            android.util.Log.d("Table creation", "created " + TABLE_FOOD_NOTES_COLLECTION);
        } catch (Exception e) {
            android.util.Log.d("Table creation", "Food notes collection table creation in catch block: " + e.getMessage());
        }

        // Create 4PM Debit / Steps Challenge Table (v8)
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOURPM_DEBIT + " ("
                    + COLUMN_DEBIT_ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DEBIT_DATE            + " TEXT UNIQUE, "
                    + COLUMN_DEBIT_FOOD_CALORIES   + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_DAILY_BUDGET    + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_BALANCE         + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_KITTY_EXCESS    + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_BALANCE_PENALTY + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_EXCESS_CALORIES + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_STEP_CHALLENGE  + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_IS_CAPPED       + " INTEGER DEFAULT 0, "
                    + COLUMN_DEBIT_CREATED_AT      + " TEXT DEFAULT '')");
            android.util.Log.d("Table creation", "created " + TABLE_FOURPM_DEBIT);
        } catch (Exception e) {
            android.util.Log.w("Table creation", TABLE_FOURPM_DEBIT + " creation: " + e.getMessage());
        }

        // Create exercise_items table (v9)
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE_ITEMS + " ("
                    + COLUMN_EXERCISE_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EXERCISE_CATEGORY     + " TEXT NOT NULL, "
                    + COLUMN_EXERCISE_NAME         + " TEXT NOT NULL, "
                    + COLUMN_EXERCISE_DURATION_MIN + " REAL DEFAULT 0, "
                    + COLUMN_EXERCISE_REPS         + " INTEGER DEFAULT 0, "
                    + COLUMN_EXERCISE_CAL_PER_UNIT + " REAL DEFAULT 0)");
            android.util.Log.d("Table creation", "created " + TABLE_EXERCISE_ITEMS);
        } catch (Exception e) {
            android.util.Log.w("Table creation", TABLE_EXERCISE_ITEMS + " creation: " + e.getMessage());
        }

        // Seed sample exercise data (inserts only if the table is empty)
        seedExerciseDataIfNeeded(db);

        // Create recorded_steps table (v10) — Midnight Scrape reconciliation
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORDED_STEPS + " ("
                    + COLUMN_RS_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_RS_DATE       + " TEXT UNIQUE, "
                    + COLUMN_RS_STEP_COUNT + " INTEGER DEFAULT 0)");
            android.util.Log.d("Table creation", "created " + TABLE_RECORDED_STEPS);
        } catch (Exception e) {
            android.util.Log.w("Table creation", TABLE_RECORDED_STEPS + " creation: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.d("DB_UPGRADE", "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Add isTransferred column if upgrading from version 1 to 2
        if (oldVersion < 2) {
            try {
                android.util.Log.d("DB_UPGRADE", "Adding isTransferred column to quick_food_note table");
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_IS_TRANSFERRED + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "Successfully added isTransferred column");
            } catch (Exception e) {
                android.util.Log.e("DB_UPGRADE", "Error adding isTransferred column: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Create memo table if upgrading from version < 3
        if (oldVersion < 3) {
            try {
                android.util.Log.d("DB_UPGRADE", "Creating memo table");
                String CREATE_MEMO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MEMO + " ("
                        + COLUMN_MEMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_MEMO_DATE + " TEXT, "
                        + COLUMN_MEMO_TEXT + " TEXT, "
                        + COLUMN_MEMO_IMAGE + " TEXT"
                        + ")";
                db.execSQL(CREATE_MEMO_TABLE);
                android.util.Log.d("DB_UPGRADE", "Successfully created memo table");
            } catch (Exception e) {
                android.util.Log.e("DB_UPGRADE", "Error creating memo table: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Create food notes collection table if upgrading from version < 4
        if (oldVersion < 4) {
            try {
                android.util.Log.d("DB_UPGRADE", "Creating food notes collection table");
                String CREATE_FOOD_NOTES_COLLECTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD_NOTES_COLLECTION + " ("
                        + COLUMN_COLLECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_COLLECTION_DATE + " TEXT, "
                        + COLUMN_COLLECTION_NOTES_JSON + " TEXT, "
                        + COLUMN_COLLECTION_TOTAL_CALORIES + " INTEGER, "
                        + COLUMN_COLLECTION_CREATED_AT + " TEXT"
                        + ")";
                db.execSQL(CREATE_FOOD_NOTES_COLLECTION_TABLE);
                android.util.Log.d("DB_UPGRADE", "Successfully created food notes collection table");
            } catch (Exception e) {
                android.util.Log.e("DB_UPGRADE", "Error creating food notes collection table: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Add isSavedToCollection column if upgrading from version < 5
        if (oldVersion < 5) {
            try {
                android.util.Log.d("DB_UPGRADE", "Adding isSavedToCollection column to quick_food_note table");
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_IS_SAVED_TO_COLLECTION + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "Successfully added isSavedToCollection column");
            } catch (Exception e) {
                android.util.Log.e("DB_UPGRADE", "Error adding isSavedToCollection column: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Add memo_title column to memo table if upgrading from version < 6
        if (oldVersion < 6) {
            try {
                android.util.Log.d("DB_UPGRADE", "Adding memo_title column to memo table");
                db.execSQL("ALTER TABLE " + TABLE_MEMO + " ADD COLUMN " + COLUMN_MEMO_TITLE + " TEXT");
                android.util.Log.d("DB_UPGRADE", "Successfully added memo_title column");
            } catch (Exception e) {
                android.util.Log.e("DB_UPGRADE", "Error adding memo_title column: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Add 4PM processing columns and log table if upgrading from version < 7
        if (oldVersion < 7) {
            android.util.Log.d("DB_UPGRADE", "Applying v7 upgrade: 4PM processing columns + log table");
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_IS_4PM_PROCESSED + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "Added is_4pm_processed column");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "is_4pm_processed already exists: " + e.getMessage());
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_PROCESSED_DATE + " TEXT DEFAULT ''");
                android.util.Log.d("DB_UPGRADE", "Added processed_date column");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "processed_date already exists: " + e.getMessage());
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_AI_UPDATED_FIELDS + " TEXT DEFAULT ''");
                android.util.Log.d("DB_UPGRADE", "Added ai_updated_fields column");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "ai_updated_fields already exists: " + e.getMessage());
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_FINAL_CALORIES + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "Added final_calories column");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "final_calories already exists: " + e.getMessage());
            }
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE + " ADD COLUMN " + COLUMN_FINAL_POINTS + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "Added final_points column");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "final_points already exists: " + e.getMessage());
            }
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOURPM_LOG + " ("
                        + COLUMN_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_LOG_PROCESSING_DATE + " TEXT UNIQUE, "
                        + COLUMN_LOG_TOTAL_CALORIES + " INTEGER DEFAULT 0, "
                        + COLUMN_LOG_TOTAL_POINTS + " INTEGER DEFAULT 0, "
                        + COLUMN_LOG_NOTE_IDS_JSON + " TEXT DEFAULT '', "
                        + COLUMN_LOG_CREATED_AT + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "Created fourpm_processing_log table");
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", "fourpm_processing_log already exists: " + e.getMessage());
            }
        }

        // v8: 4PM Debit / Steps Challenge result table
        if (oldVersion < 8) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOURPM_DEBIT + " ("
                        + COLUMN_DEBIT_ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_DEBIT_DATE            + " TEXT UNIQUE, "
                        + COLUMN_DEBIT_FOOD_CALORIES   + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_DAILY_BUDGET    + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_BALANCE         + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_KITTY_EXCESS    + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_BALANCE_PENALTY + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_EXCESS_CALORIES + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_STEP_CHALLENGE  + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_IS_CAPPED       + " INTEGER DEFAULT 0, "
                        + COLUMN_DEBIT_CREATED_AT      + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "v8: created " + TABLE_FOURPM_DEBIT);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_FOURPM_DEBIT + " already exists: " + e.getMessage());
            }
        }

        // v9: exercise_items table (Cardio + Strength Training)
        if (oldVersion < 9) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE_ITEMS + " ("
                        + COLUMN_EXERCISE_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_EXERCISE_CATEGORY     + " TEXT NOT NULL, "
                        + COLUMN_EXERCISE_NAME         + " TEXT NOT NULL, "
                        + COLUMN_EXERCISE_DURATION_MIN + " REAL DEFAULT 0, "
                        + COLUMN_EXERCISE_REPS         + " INTEGER DEFAULT 0, "
                        + COLUMN_EXERCISE_CAL_PER_UNIT + " REAL DEFAULT 0)");
                android.util.Log.d("DB_UPGRADE", "v9: created " + TABLE_EXERCISE_ITEMS);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_EXERCISE_ITEMS + " already exists: " + e.getMessage());
            }
            seedExerciseDataIfNeeded(db);
        }

        // v10: recorded_steps table (Midnight Scrape reconciliation)
        if (oldVersion < 10) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORDED_STEPS + " ("
                        + COLUMN_RS_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_RS_DATE       + " TEXT UNIQUE, "
                        + COLUMN_RS_STEP_COUNT + " INTEGER DEFAULT 0)");
                android.util.Log.d("DB_UPGRADE", "v10: created " + TABLE_RECORDED_STEPS);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_RECORDED_STEPS + " already exists: " + e.getMessage());
            }
        }

        // v11: is_synced column on quick_food_note (backend sync tracking)
        if (oldVersion < 11) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_QUICK_FOOD_NOTE
                        + " ADD COLUMN " + COLUMN_IS_SYNCED + " INTEGER DEFAULT 0");
                android.util.Log.d("DB_UPGRADE", "v11: added " + COLUMN_IS_SYNCED + " to " + TABLE_QUICK_FOOD_NOTE);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", COLUMN_IS_SYNCED + " column already exists: " + e.getMessage());
            }
        }

        // v12: Health Data Reading tables (Blood Pressure, Heart Rate, Blood Sugar)
        if (oldVersion < 12) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_BP + " ("
                        + COLUMN_BP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_BP_DATE + " TEXT, "
                        + COLUMN_BP_TIME + " TEXT, "
                        + COLUMN_BP_SYSTOLIC + " INTEGER DEFAULT 0, "
                        + COLUMN_BP_DIASTOLIC + " INTEGER DEFAULT 0, "
                        + COLUMN_BP_CREATED_AT + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "v12: created " + TABLE_HEALTH_BP);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_HEALTH_BP + " already exists: " + e.getMessage());
            }
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_HR + " ("
                        + COLUMN_HRDATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_HRDATA_DATE + " TEXT, "
                        + COLUMN_HRDATA_TIME + " TEXT, "
                        + COLUMN_HRDATA_BPM + " INTEGER DEFAULT 0, "
                        + COLUMN_HRDATA_CREATED_AT + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "v12: created " + TABLE_HEALTH_HR);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_HEALTH_HR + " already exists: " + e.getMessage());
            }
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HEALTH_BS + " ("
                        + COLUMN_BS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_BS_DATE + " TEXT, "
                        + COLUMN_BS_TIME + " TEXT, "
                        + COLUMN_BS_MGDL + " INTEGER DEFAULT 0, "
                        + COLUMN_BS_CREATED_AT + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "v12: created " + TABLE_HEALTH_BS);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_HEALTH_BS + " already exists: " + e.getMessage());
            }
        }

        // v13: Steps Challenge Log table
        if (oldVersion < 13) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STEPS_CHALLENGE + " ("
                        + COLUMN_SC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_SC_DATE + " TEXT UNIQUE, "
                        + COLUMN_SC_CURRENT_BALANCE + " INTEGER DEFAULT 0, "
                        + COLUMN_SC_PREV_DAY_END + " INTEGER DEFAULT 0, "
                        + COLUMN_SC_DAY_END_TARGET + " INTEGER DEFAULT 0, "
                        + COLUMN_SC_BMR + " INTEGER DEFAULT 0, "
                        + COLUMN_SC_STEP_CHALLENGE + " INTEGER DEFAULT 0, "
                        + COLUMN_SC_CREATED_AT + " TEXT DEFAULT '')");
                android.util.Log.d("DB_UPGRADE", "v13: created " + TABLE_STEPS_CHALLENGE);
            } catch (Exception e) {
                android.util.Log.w("DB_UPGRADE", TABLE_STEPS_CHALLENGE + " already exists: " + e.getMessage());
            }
        }

        android.util.Log.d("DB_UPGRADE", "Database upgrade completed");
    }

    // ── Exercise Items helpers ────────────────────────────────────────────────

    /** Inserts seed data the first time exercise_items is empty. */
    private void seedExerciseDataIfNeeded(SQLiteDatabase db) {
        try {
            android.database.Cursor c = db.rawQuery(
                    "SELECT COUNT(*) FROM " + TABLE_EXERCISE_ITEMS, null);
            int count = 0;
            if (c != null) {
                if (c.moveToFirst()) count = c.getInt(0);
                c.close();
            }
            if (count > 0) return; // already seeded

            // Cardio seed items (calories_per_unit = cal/min)
            insertExerciseSeed(db, ExerciseItem.CATEGORY_CARDIO,    "Running",  20f, 0,  10.0f);
            insertExerciseSeed(db, ExerciseItem.CATEGORY_CARDIO,    "Cycling",  30f, 0,   7.0f);

            // Strength Training seed items (calories_per_unit = cal/rep)
            insertExerciseSeed(db, ExerciseItem.CATEGORY_STRENGTH,  "Bench Press", 0f, 10, 0.5f);
            insertExerciseSeed(db, ExerciseItem.CATEGORY_STRENGTH,  "Squats",      0f, 15, 0.5f);

            android.util.Log.d("ExerciseSeed", "Seeded " + TABLE_EXERCISE_ITEMS + " with sample data");
        } catch (Exception e) {
            android.util.Log.e("ExerciseSeed", "Seed failed: " + e.getMessage(), e);
        }
    }

    private void insertExerciseSeed(SQLiteDatabase db, String category, String name,
                                    float durationMin, int reps, float calPerUnit) {
        android.content.ContentValues cv = new android.content.ContentValues();
        cv.put(COLUMN_EXERCISE_CATEGORY,     category);
        cv.put(COLUMN_EXERCISE_NAME,         name);
        cv.put(COLUMN_EXERCISE_DURATION_MIN, durationMin);
        cv.put(COLUMN_EXERCISE_REPS,         reps);
        cv.put(COLUMN_EXERCISE_CAL_PER_UNIT, calPerUnit);
        db.insert(TABLE_EXERCISE_ITEMS, null, cv);
    }

    /**
     * Returns all exercise items for the given category ("CARDIO" or "STRENGTH_TRAINING").
     */
    public java.util.List<ExerciseItem> getExerciseItems(String category) {
        java.util.List<ExerciseItem> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor c = null;
        try {
            c = db.query(TABLE_EXERCISE_ITEMS,
                    null,
                    COLUMN_EXERCISE_CATEGORY + " = ?",
                    new String[]{category},
                    null, null,
                    COLUMN_EXERCISE_NAME + " ASC");
            while (c.moveToNext()) {
                ExerciseItem item = new ExerciseItem(
                        c.getLong(c.getColumnIndexOrThrow(COLUMN_EXERCISE_ID)),
                        c.getString(c.getColumnIndexOrThrow(COLUMN_EXERCISE_CATEGORY)),
                        c.getString(c.getColumnIndexOrThrow(COLUMN_EXERCISE_NAME)),
                        c.getFloat(c.getColumnIndexOrThrow(COLUMN_EXERCISE_DURATION_MIN)),
                        c.getInt(c.getColumnIndexOrThrow(COLUMN_EXERCISE_REPS)),
                        c.getFloat(c.getColumnIndexOrThrow(COLUMN_EXERCISE_CAL_PER_UNIT)));
                list.add(item);
            }
        } catch (Exception e) {
            android.util.Log.e("ExerciseDB", "getExerciseItems: " + e.getMessage(), e);
        } finally {
            if (c != null) c.close();
        }
        return list;
    }

    /**
     * Returns true if an item with the same category + name already exists (case-insensitive).
     */
    public boolean exerciseItemExists(String category, String name) {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor c = null;
        try {
            c = db.query(TABLE_EXERCISE_ITEMS,
                    new String[]{COLUMN_EXERCISE_ID},
                    "LOWER(" + COLUMN_EXERCISE_CATEGORY + ") = LOWER(?) AND LOWER("
                            + COLUMN_EXERCISE_NAME + ") = LOWER(?)",
                    new String[]{category, name},
                    null, null, null);
            return c.moveToFirst();
        } catch (Exception e) {
            android.util.Log.e("ExerciseDB", "exerciseItemExists: " + e.getMessage(), e);
            return false;
        } finally {
            if (c != null) c.close();
        }
    }

    /**
     * Inserts a new exercise item. Returns the row id, or -1 on error / duplicate.
     */
    public long insertExerciseItem(String category, String name,
                                   float durationMinutes, int reps, float caloriesPerUnit) {
        if (exerciseItemExists(category, name)) return -1L;
        SQLiteDatabase db = getWritableDatabase();
        android.content.ContentValues cv = new android.content.ContentValues();
        cv.put(COLUMN_EXERCISE_CATEGORY,     category);
        cv.put(COLUMN_EXERCISE_NAME,         name);
        cv.put(COLUMN_EXERCISE_DURATION_MIN, durationMinutes);
        cv.put(COLUMN_EXERCISE_REPS,         reps);
        cv.put(COLUMN_EXERCISE_CAL_PER_UNIT, caloriesPerUnit);
        try {
            return db.insert(TABLE_EXERCISE_ITEMS, null, cv);
        } catch (Exception e) {
            android.util.Log.e("ExerciseDB", "insertExerciseItem: " + e.getMessage(), e);
            return -1L;
        }
    }

    // ── Recorded Steps helpers (Midnight Scrape) ──────────────────────────────

    /**
     * Stores (or replaces) the user-recorded step count for the given date.
     * Called from Debit_Steps when the user taps "Debit Steps".
     * @param date "dd-MM-yyyy" string for today
     * @param stepCount the step count the user entered
     */
    public void storeRecordedSteps(String date, int stepCount) {
        SQLiteDatabase db = getWritableDatabase();
        android.content.ContentValues cv = new android.content.ContentValues();
        cv.put(COLUMN_RS_DATE,       date);
        cv.put(COLUMN_RS_STEP_COUNT, stepCount);
        try {
            // REPLACE handles both INSERT (first time) and UPDATE (if already stored today)
            db.insertWithOnConflict(TABLE_RECORDED_STEPS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
            android.util.Log.d("RecordedSteps", "stored " + stepCount + " steps for " + date);
        } catch (Exception e) {
            android.util.Log.e("RecordedSteps", "storeRecordedSteps: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the recorded step count for the given date, or -1 if none was stored.
     * @param date "dd-MM-yyyy" string
     */
    public int getRecordedStepsForDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor c = null;
        try {
            c = db.query(TABLE_RECORDED_STEPS,
                    new String[]{COLUMN_RS_STEP_COUNT},
                    COLUMN_RS_DATE + " = ?",
                    new String[]{date},
                    null, null, null);
            if (c.moveToFirst()) return c.getInt(0);
        } catch (Exception e) {
            android.util.Log.e("RecordedSteps", "getRecordedStepsForDate: " + e.getMessage(), e);
        } finally {
            if (c != null) c.close();
        }
        return -1;
    }

    //From Green i
// List of Food item attributes and Food Item database Table Fields
//Category Eat in /Out Type Ingrediend meal etc restaurate items google search add guess etc for layers exe Name of Food or  Item	Calories per 100g or 100ml 	Grams in a Serving/Portion	Carbhydrates	of which are Sugars	Protein	Fat 	of which are saturated Fat	Polyunsaturated	Monounsaturated	Trans	Cholesterol (mg)	Sodium (mg)	Salt (g)	Potassuim (mg)	Fibre	Sugars	Vitamin A (%)	Vitamin C (%)	Calcium (%)	Iron (%)
// N.B arrange files to match initial orignial Blackberry fields then extend field with what you have leftover
    public void QueryForMatches(String food_item_subname, final FoodSearchCallback callback) {
        //Insert test data
        //long result = InsertDummyRice();

        String fooditemname = food_item_subname;

        Query_Specific_Food_Items_Table(mContext, fooditemname, new FoodSearchCallback() {
            @Override
            public void onResult(final ArrayList<Food_Item_CIF4> results) {
                // Update UI on main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Pass results to caller's callback
                        callback.onResult(results);
                    }
                });
            }
        });

        //See Results of Query for Matches
        // Check_Results(list);
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

//    public ArrayList<Food_Item_CIF4> Query_Specific_Food_Items_Table(String food_item_name) {
//        //Alogrithm Engineering : Get cursor pointing row and columns
//
//        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_FOODITEMS + " WHERE " + COLUMN_FOODITEMS_FOOD_ITEM_NAME + " LIKE " + "'" + "%" + food_item_name + "%" + "'", null);
//        cursor.moveToFirst();
//        FoodItemsCursor foodItemCursor = new FoodItemsCursor(cursor);
//        Log.d("Calorie Countdown", "Check to state of Cursor");
//        if (foodItemCursor.getCount() < 1) {
//            ArrayList<Food_Item_CIF4> placebo = new ArrayList<Food_Item_CIF4>();
//            Food_Item_CIF4 not_found = new Food_Item_CIF4();
//            not_found.Set_food_item_name(food_item_name + " not found");
//            placebo.add(not_found);
//            foodItemCursor.close();
//            return placebo;
//        } else {
//            return GetFoodItemII(new FoodItemsCursor(cursor));
//        }
//
//    }
//

    public void Query_Specific_Food_Items_Table(final Context context, final String food_item_name, final FoodSearchCallback callback) {
        ArrayList<Food_Item_CIF4> results = new ArrayList<>();
        final SQLiteDatabase db = getWritableDatabase();

        // Initialize API client with proper context
        SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(context);

        // Check internet
        if (NetworkUtil.isInternetAvailable(context)) {
            Log.d("FoodSearch", "🌐 Internet available, searching from API...");

            apiClient.searchFood(food_item_name, new ApiResultCallback() { // <-- Use ApiResultCallback
                @Override
                public void onSuccess(String response) {
                    if (response != null && !response.trim().isEmpty()) {
                        ArrayList<Food_Item_CIF4> parsedList = parseFoodApiResponse(response);
                        if (parsedList != null && !parsedList.isEmpty()) {
                            Log.d("FoodSearch", "✅ Found " + parsedList.size() + " items from API");
                            callback.onResult(parsedList);
                            return;
                        } else {
                            Log.d("FoodSearch", "⚠️ API returned empty list, falling back to local DB");
                        }
                    } else {
                        Log.d("FoodSearch", "⚠️ API response is null or empty, falling back to local DB");
                    }

                    // Fallback to local DB if API fails
                    searchLocalDB(db, food_item_name, callback);
                }

                @Override
                public void onFailure() {
                    Log.d("FoodSearch", "⚠️ API call failed, searching local DB");
                    searchLocalDB(db, food_item_name, callback);
                }
            });

        } else {
            Log.d("FoodSearch", "📴 No Internet, searching only from SQLite");
            searchLocalDB(db, food_item_name, callback);
        }
    }


    private void searchLocalDB(SQLiteDatabase db, String food_item_name, FoodSearchCallback callback) {
        ArrayList<Food_Item_CIF4> results = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_FOODITEMS + " WHERE " + COLUMN_FOODITEMS_FOOD_ITEM_NAME + " LIKE ?",
                    new String[]{"%" + food_item_name + "%"}
            );

            if (cursor != null && cursor.moveToFirst()) {
                FoodItemsCursor foodItemCursor = new FoodItemsCursor(cursor);

                if (foodItemCursor.getCount() > 0) {
                    results.addAll(GetFoodItemII(foodItemCursor));
                } else {
                    Food_Item_CIF4 notFound = new Food_Item_CIF4();
                    notFound.Set_food_item_name(food_item_name + " not found");
                    results.add(notFound);
                }

                foodItemCursor.close();
            } else {
                Food_Item_CIF4 notFound = new Food_Item_CIF4();
                notFound.Set_food_item_name(food_item_name + " not found");
                results.add(notFound);
            }

        } catch (Exception e) {
            Log.e("FoodSearch", "❌ Error while searching food: " + e.getMessage(), e);
            Food_Item_CIF4 errorItem = new Food_Item_CIF4();
            errorItem.Set_food_item_name("Error: " + e.getMessage());
            results.add(errorItem);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        callback.onResult(results);
    }


    private ArrayList<Food_Item_CIF4> parseFoodApiResponse(String jsonResponse) {
        ArrayList<Food_Item_CIF4> foodList = new ArrayList<>();

        try {
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                Log.w("FoodSearch", "⚠️ Empty API response");
                return foodList;
            }

            JSONObject root = new JSONObject(jsonResponse);

            // Validate success
            boolean success = root.optBoolean("success", false);
            if (!success) {
                Log.w("FoodSearch", "⚠️ API returned success=false");
                return foodList;
            }

            // Extract items array
            JSONArray itemsArray = root.optJSONArray("items");
            if (itemsArray == null) {
                Log.w("FoodSearch", "⚠️ No 'items' array found in JSON");
                return foodList;
            }

            // Loop through each item
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject obj = itemsArray.getJSONObject(i);
                Food_Item_CIF4 item = new Food_Item_CIF4();

                // ✅ Map JSON keys → model fields
                item.Set_food_item_name(obj.optString("food_item_name", ""));
                item.Set_id(obj.optInt("id", 0));
                item.Set_food_type(obj.optString("food_type", null));
                item.Set_grams_per_serving_portion((float) obj.optDouble("grams_per_serving", 0));
                item.Set_calories_per_100g((float) obj.optDouble("calories_per_100g", 0));
                item.Set_fat_per_100g((float) obj.optDouble("fat_per_100g", 0));
                item.Set_saturated_fat((float) obj.optDouble("saturated_fat", 0));
                item.Set_trans_fat((float) obj.optDouble("trans_fat", 0));
                item.Set_protein_per_100g((float) obj.optDouble("protein_per_100g", 0));
                item.Set_carbs_per_100g((float) obj.optDouble("carbs_per_100g", 0));
                item.Set_sugar_per_100g((float) obj.optDouble("sugar_per_100g", 0));
                item.Set_salt_per_100g((float) obj.optDouble("salt_per_100g", 0));
                item.Set_wellbeing_index(obj.optDouble("wellbeing_index", 0) > 0);
                item.Set_fiber((float) obj.optDouble("fiber", 0));
                item.Set_price_sterling((float) obj.optDouble("price_sterling", 0));
                item.Set_polyunsaturated((float) obj.optDouble("polyunsaturated", 0));
                item.Set_monounsaturated((float) obj.optDouble("monounsaturated", 0));
                item.Set_cholesterol_mg((float) obj.optDouble("cholesterol_mg", 0));
                item.Set_sodium_mg((float) obj.optDouble("sodium_mg", 0));
                item.Set_potassium_mg((float) obj.optDouble("potassium_mg", 0));
                item.Set_vitamin_a_percent((float) obj.optDouble("vitamin_a_percent", 0));
                item.Set_vitamin_c_percent((float) obj.optDouble("vitamin_c_percent", 0));
                item.Set_calcium_percent((float) obj.optDouble("calcium_percent", 0));
                item.Set_iron_percent((float) obj.optDouble("iron_percent", 0));


                foodList.add(item);
            }

        } catch (Exception e) {
            Log.e("FoodSearch", "❌ Error parsing API response: " + e.getMessage());
        }

        return foodList;
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

    public Transactions_CIF22 Get_All_Transactions(Date start, Date end) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS_TABLE + " WHERE " + COLMUM_TRANSACTIONS_DATE + " >= " + "'" + start.getTime() + "'" + " AND " + COLMUM_TRANSACTIONS_DATE + " <= " + "'" + end.getTime() + "'", null);
        cursor.moveToFirst();
        Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        if (TransactionCursor.getCount() < 1) {
            TransactionCursor.close();
            return new Transactions_CIF22();
        } else {
            return GetTransactions(TransactionCursor);
        }

    }

    public Transactions_CIF22 Really_Get_All_Transactions() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS_TABLE, null);
        cursor.moveToFirst();
        Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        Transactions_CIF22 OUTPUT;
        ArrayList<Breakfast_Box_CIF17> OUTPUTb;

        if (TransactionCursor.getCount() < 1) {
            Log.d(TAG, "NOTHING FOUND IN Transaction DATABASE");
            TransactionCursor.close();
            return new Transactions_CIF22();
        } else {
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
            } catch (Exception x) {
                Toast.makeText(mContext, "Meal Box is empty aborting function", Toast.LENGTH_SHORT);
            }


            return new Transactions_CIF22();
        }

    }

    public BoxCIF17 Get_Meal_items(long Transaction_ID) {
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
        } catch (Exception e) {
            android.util.Log.d("app", "database must have failed to open");
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
            } catch (java.lang.IllegalStateException e) {
                //Display_Dialog_CIF11 dialog = new Display_Dialog_CIF11();
                //dialog.Showing("Please enter your Target weight using main menu");
                return "0";
            } finally {
                countdownBalanceCursor.close();
            }
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


    public CountdownToZeroDayCiF1004 GetTransactions(CountdownToXeroDayType1004_Cursor bc) {
        CountdownToZeroDayCiF1004 dc = bc.GetDays();
        return dc;
    }


    public HealthProfileCiF3 GetHealthProfile_CIF3(SHealth_Cursor_241 bc) {

        HealthProfileCiF3 dc = bc.Get_HealthProfileCIF3();
        return dc;
    }

    public ArrayList<Breakfast_Box_CIF17> GetBoxes(Meal_Items_Cursor mc) {
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

    private String GetBreakfastCursor(BreakfastCursor obcursor) {
        String repulse = obcursor.getBreakfast();
        obcursor.close();
        return repulse;
    }


    private ArrayList<Food_Item_CIF4> GetFoodItemII(FoodItemsCursor food_cursor) {
        Food_Item_CIF4 food_match;
        ArrayList<Food_Item_CIF4> relist = new ArrayList<Food_Item_CIF4>();


        //for(int c = 0; c < food_cursor.getCount(); c++)
        // {
        //food_match = food_cursor.getFood_Item();
        //relist.add(food_match);
        //return relist;

        //while(food_cursor.isAfterLast() == false)
        //{

        for (int c = 0; c < food_cursor.getCount(); c++) {

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
        cv.put(COLUMN_DAYEND_BALANCE_DATE2, new Date().getTime());
        cv.put(COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL, bal);


       /* private static final String TABLE_DAYEND_BALANCE2 = "dayend_balance2";
        private static final String COLUMN_DAYEND_ID2 = "dayend_id";
        private static final String COLUMN_DAYEND_BALANCE_DATE2 = "balance_date";
        private static final String COLUMN_DAYEND_BALANCE_BALANCE_BUDGET = "balance_dayend_budget";
        private static final String COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL = "balance_dayend_actual";*/

        //int affected = getWritableDatabase().delete(TABLE_DAYEND_BALANCE, null, null);
        long res = getWritableDatabase().insert(TABLE_DAYEND_BALANCE2, null, cv);

        if (isTimeAfter4pm() && !havePerformedDayENDCFWD) {
            Insert_Dayend2Row(new Long(new Date().getTime()).toString(), null, new Integer(bal - 2000).toString());
            havePerformedDayENDCFWD = true;
        }

        return res;
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

    public long Insert_TransactionTable(Transaction_CIF52 transaction_type) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRANSACTIONS_ID, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_id()));
        Log.d("INSERT", new RoundingCIF13().LongToString(transaction_type.Get_Transaction_id()));
        cv.put(COLMUM_TRANSACTIONS_DATE, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Date()));
        Log.d("INSERT", new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Date()));
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE, transaction_type.Get_Transaction_Meal_Type());
        Log.d("INSERT", transaction_type.Get_Transaction_Meal_Type());
        cv.put(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID, new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Meal_Type_ID()));
        Log.d("INSERT", new RoundingCIF13().LongToString(transaction_type.Get_Transaction_Meal_Type_ID()));
        cv.put(COLUMUM_TRANSACTIONS_AMOUNT, new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Amount()));
        Log.d("INSERT", new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Amount()));
        cv.put(COLUMUM_TRANSACTIONS_BALANCE, new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Balance()));
        Log.d("INSERT", new RoundingCIF13().IntToString(transaction_type.Get_Transaction_Balance()));


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
        return getWritableDatabase().insert(TABLE_FOODITEMS, null, cv);
        //Continue for rest of variables.
    }

    public void Insert_Food_Item_Row(JSONWrapperCIFClass INPUT) {
        ;
    }

    /**
     * Check if a food item with the given name already exists in the food_items table.
     * Uses case-insensitive exact match.
     * @return the existing Food_Item_CIF4 if found, null otherwise
     */
    public Food_Item_CIF4 getFoodItemByExactName(String foodName) {
        if (foodName == null || foodName.trim().isEmpty()) return null;

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_FOODITEMS + " WHERE LOWER(" + COLUMN_FOODITEMS_FOOD_ITEM_NAME + ") = LOWER(?)",
                new String[]{foodName.trim()});

        Food_Item_CIF4 item = null;
        if (cursor.moveToFirst()) {
            item = new Food_Item_CIF4();
            int nameIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_FOOD_ITEM_NAME);
            int calIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_CALORIE_PER_100G);
            int fatIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_FAT_PER_100G);
            int proteinIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_PROTEIN_PER_100G);
            int carbsIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_CARBS_PER_100G);
            int sugarIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_SUGAR_PER_100G);
            int saltIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_SALT_PER_100G);
            int fiberIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_FIBER);
            int satFatIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_SATURATED_FAT);
            int transFatIdx = cursor.getColumnIndex(COLUMN_FOODITEMS_TRANS_FAT);

            if (nameIdx >= 0) item.Set_food_item_name(cursor.getString(nameIdx));
            if (calIdx >= 0) item.Set_calories_per_100g(cursor.getFloat(calIdx));
            if (fatIdx >= 0) item.Set_fat_per_100g(cursor.getFloat(fatIdx));
            if (proteinIdx >= 0) item.Set_protein_per_100g(cursor.getFloat(proteinIdx));
            if (carbsIdx >= 0) item.Set_carbs_per_100g(cursor.getFloat(carbsIdx));
            if (sugarIdx >= 0) item.Set_sugar_per_100g(cursor.getFloat(sugarIdx));
            if (saltIdx >= 0) item.Set_salt_per_100g(cursor.getFloat(saltIdx));
            if (fiberIdx >= 0) item.Set_fiber(cursor.getFloat(fiberIdx));
            if (satFatIdx >= 0) item.Set_saturated_fat(cursor.getFloat(satFatIdx));
            if (transFatIdx >= 0) item.Set_trans_fat(cursor.getFloat(transFatIdx));
        }
        cursor.close();
        return item;
    }

    /**
     * Delete a food item by exact name match (case-insensitive).
     * @return number of rows deleted
     */
    public int deleteFoodItemByName(String foodName) {
        if (foodName == null || foodName.trim().isEmpty()) return 0;
        return getWritableDatabase().delete(
                TABLE_FOODITEMS,
                "LOWER(" + COLUMN_FOODITEMS_FOOD_ITEM_NAME + ") = LOWER(?)",
                new String[]{foodName.trim()});
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

    public void Insert_Food_Item_CIF4(Food_Item_CIF4 IN) {
        String Category = IN.Get_category();
        String food_item_name = IN.Get_food_item_name();
        float grams_per_serving_portion = IN.Get_grams_per_serving_portion();
        float calories_per_serving_portion = IN.Get_calories_per_100g();
        float fat_per_serving_portion = IN.Get_fat_per_100g();
        float carbohydrates_per_serving = IN.Get_carbs_per_100g();
        float protein_per_serving = IN.Get_protein_per_100g();

        InsertDummyRice(Category, food_item_name, grams_per_serving_portion, calories_per_serving_portion, fat_per_serving_portion, carbohydrates_per_serving, protein_per_serving);


    }

    public BoxCIF17 Retrieve_Food_Items_CIF4(long StartDate, long EndDate) {
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

    public Transactions_CIF22 retrieveMonthlyStatementTransactions(int Sd, int Sm, int Sy, int Ed, int Em, int Ey) {
        //Read

        Calendar gregDatumSD = Calendar.getInstance();
        gregDatumSD.set(2017, 7, 1);

        Calendar gregDatumED = Calendar.getInstance();
        gregDatumED.set(2017, 7, 1);

        Date StartDate = new Date(gregDatumSD.getTimeInMillis());
        Date EndDate = new Date(gregDatumED.getTimeInMillis());

        MIF22FillHerUp fakenews = new MIF22FillHerUp(mContext, StartDate, EndDate);

        return fakenews.FillHerUpVerb(new Transactions_CIF22());
    }

    public void Update_Transactions_Table(MIF4_Data_Model_Adapter.Transaction INPUT) {
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


    public void Update_Meal_Box_Table(MIF4_Data_Model_Adapter.Meal_Type INPUT) {
        ;
    }

    private ArrayList<Breakfast_Box_CIF17> Get_Meal_Box_Items(Meal_Items_Cursor cursor) {
        ArrayList<Breakfast_Box_CIF17> dc = cursor.Get_Meal_Box_Action();
        return dc;

    }

    private void Insert_Meal_Type_Row(String Transaction_ID, MIF4_Data_Model_Adapter.Meal_Type INPUT) {

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

    public long Insert_BoxCIF(Transaction_Line_CIF17 INPUT) {
        return Insert_MealBoxCIF(INPUT.Get_Transaction_ID(), INPUT.Get_Transaction_Food_Items());
    }

    public long Insert_MealBoxCIF(long ID, BoxCIF17 In) {
        In.Set_Transaction_ID_for_all_Food_items(ID);
        return Insert_MealBox(In);
    }

    public long Insert_MealBox(BoxCIF17 Input) {
        Breakfast_Box_CIF17 into = Transform_Box_to_Breakfast_Meal_Box(Input);

        return Insert_BreakfastMealBoxTable(into);
    }

    private Breakfast_Box_CIF17 Transform_Box_to_Breakfast_Meal_Box(BoxCIF17 In) {
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

    public void Delete_Food_items_Table() {
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

    public void Delete_Transaction_Table() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM transactions_xp");
        db.close();
    }

    public void Delete_Meal_Box_Table() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM meal_box_items");
        db.close();
    }

    public long Insert_TransactionTable_x(Transactions_CIF22 IN) {

        //return Insert_TransactionTable(new MIF22FillHerUp(mContext).FillWithFakeData(new Transactions_CIF22()).Get_TransactionLines().get(0));

        //Transactions_CIF22 Output = (new MIF22FillHerUp(mContext)).FillWithFakeData(new Transactions_CIF22());

        long index = 0;

        for (Transaction_CIF52 x : IN.Get_TransactionLines()) {
            index = Insert_TransactionTable(x);

            Log.d("INDEX #", new RoundingCIF13().LongToString(index));
        }

        Log.d("SQLite", " Check this out Baby! " + IN.Print());

        return index;


    }

    public long Get_Unique_Row_Identifier_from_Transactions_Table() {
        long reserve = Insert_Dummy_Transaction_Row(new Transaction_CIF52());
        reserve = reserve + 1;
        return reserve;

    }

    public void onClose() {
        getWritableDatabase().close();
        close();
    }

    private long Insert_Dummy_Transaction_Row(Transaction_CIF52 transaction_type) {
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

    public int Get_Unique_Row_Identifier_from_Transaction_Table() {
        //IDO DOCUMENTION
        //Finds the insert ID of the last row in relevant field and adds one too it, make sure the next time
        //Something is inserted in doesn't give the same number, to prevent this, insert a Dummy row, use
        //ID of the Dummy row, return it then delete dummy row.

        //Algorithm Engineering -> ANDROID

        return 1;
    }

    public long PostBtime(int IN) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_BREAKFAST_TIME_BREAKFASTTIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_BREAKFAST_TIME, null, null);
        return getWritableDatabase().insert(TABLE_BREAKFAST_TIME, null, cv);

    }

    public long PostLtime(int IN) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_LUNCH_TIME_LUNCHTIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_LUNCH_TIME, null, null);
        return getWritableDatabase().insert(TABLE_LUNCH_TIME, null, cv);

    }

    public long PostDtime(int IN) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMUM_FINAL_MEAL_TIME_FINALMEATIME, new RoundingCIF13().IntToString(IN));
        int affected = getWritableDatabase().delete(TABLE_FINAL_MEAL_TIME, null, null);
        return getWritableDatabase().insert(TABLE_FINAL_MEAL_TIME, null, cv);

    }


    public int GetBtime() {
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

    public int GetLtime() {
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

    public int GetDtime() {
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


    public long StoreHealthProfile(HealthProfileCiF3 in) {
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

    public HealthProfileCiF3 Really_Get_HealthProfileCIF3() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_HEALTH_PROFILE_TABLE, null);
        cursor.moveToFirst();
        SHealth_Cursor_241 TransactionCursor = new SHealth_Cursor_241(cursor);
        HealthProfileCiF3 OUTPUT;


        if (TransactionCursor.getCount() < 1) {
            Log.d(TAG, "NOTHING FOUND IN Health Vitals DATABASE");
            TransactionCursor.close();
            return new HealthProfileCiF3();
        } else {
            try {
                Log.d(TAG, "CONTENTS IN Health Vitals DATABASE");
                OUTPUT = GetHealthProfile_CIF3(TransactionCursor);


                //Breakfast_Box_CIF17 itet = new Breakfast_Box_CIF17();

                //Log.d("Contents Meal Boxes", itet.Get_Food_Items().get(0).Get_food_item_name());

                return OUTPUT;

                //BoxCIF17 OUTPUTc = Transform_Breakfast_Box_to_Box(OUTPUTb);
                //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
                //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);
            } catch (Exception x) {
                Toast.makeText(mContext, "SHealth_Cursor is empty aborting function", Toast.LENGTH_SHORT);
            }


            return new HealthProfileCiF3();
        }

    }

    public HealthProfileCiF3 getHealthProfileObjectSerializable() {
        return new HealthProfileCiF3();
    }

    public void Iterate_through_thisTableA_Output_(JSONWrapperCIFClass INPUT_TABLE_A) {
        //For Table A go through all the Rows //iterate

        //For each row, use a fragemented_box to INPUT Row OUTPUT a data object frag_box

        //fragmented_box
        Row_Converter_CiF1001_fragment_box_Class rcc = new Row_Converter_CiF1001_fragment_box_Class(INPUT_TABLE_A);
        //rcc.iterate(MiF4_function_to_peform_on_Transaction_CIF52_Line(rcc.transform(INPUT_TABLE_A)));
        rcc.iterate();


    }

    private ArrayList<Breakfast_Box_CIF17> Really_Get_Box_items() {
        ArrayList<Breakfast_Box_CIF17> OUTPUTb;
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_MEAL_BOX_ITEMS_TABLE, null);
        cursor.moveToFirst();
        Meal_Items_Cursor MealItemCursor = new Meal_Items_Cursor(cursor);

        if (MealItemCursor.getCount() < 1) {
            Log.d(TAG, "NOTHING FOUND IN Meal box DATABASE");
            MealItemCursor.close();
            OUTPUTb = new ArrayList<Breakfast_Box_CIF17>();

            return OUTPUTb;
        } else {
            Log.d(TAG, "CONTENTS IN Meal box DATABASE");
            OUTPUTb = GetBoxes(MealItemCursor);

            //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
            //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);

            Display_Outputb(OUTPUTb);

            return OUTPUTb;
        }


    }

    private void Display_Outputb(ArrayList<Breakfast_Box_CIF17> INPUT) {
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
        } catch (Exception e) {
            Log.d("OUTPUTb err", "Something went wrong in one of the data content.");
        }

    }


    private BoxCIF17 Transform_Breakfast_Box_to_Box(Breakfast_Box_CIF17 IN) {
        BoxCIF17 OUTPUT = new BoxCIF17();

        OUTPUT.Set_Balance(IN.Get_Breakfast_Balance());
        android.util.Log.d("Transform Balance: ", new RoundingCIF13().IntToString(IN.Get_Balance()));

        OUTPUT.Set_Energy_OUT(IN.Get_Energy_OUT());
        android.util.Log.d("Transform Energy Out: ", new RoundingCIF13().IntToString(IN.Get_Energy_OUT()));

        OUTPUT.add_Meal_Box_ID((int) IN.Get_Meal_Box_ID());
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

    private Transactions_CIF22 Final_Transform(ArrayList<Breakfast_Box_CIF17> Input, Transactions_CIF22 IN) {

        //IN a Breakfast/Box Meal integrate into Transaction CIF22 to come up with final product.
        //CiF22 has CiF52 contains a transaction line.
        //Transaction Line Contains boxed item so breakfast box must be transffered in lines stages and layers to Cif17 boxed items

        BoxCIF17 boxCIF17;
        Transaction_CIF52 transaction_cif52;
        Transaction_Line_CIF17 line_cif17;


        for (Breakfast_Box_CIF17 m : Input) {
            boxCIF17 = Transform_Breakfast_Box_to_Box(m);
            line_cif17 = new Transaction_Line_CIF17();
            line_cif17.Set_Transaction_Food_Items(boxCIF17);

            transaction_cif52 = new Transaction_CIF52();
            transaction_cif52.Set_Single_Transaction_Line(line_cif17);
            IN.Add_TransactionLine_2_List(transaction_cif52);

        }

        return IN;
    }

    private Transactions_CIF22 Merge_Food_items(Transactions_CIF22 IN, ArrayList<Breakfast_Box_CIF17> INPUT) {
        for (Transaction_CIF52 m : IN.Get_TransactionLines()) {
            for (Breakfast_Box_CIF17 n : INPUT) {
                if (n.Get_Transactions_ID() == m.Get_Single_Transaction_Line().Get_Transaction_ID()) {
                    for (Food_Item_CIF4 o : n.Get_Food_Item_List_two()) {
                        m.Get_Single_Transaction_Line().add_Food_item(o);
                    }
                }
            }

        }

        return IN;
    }


    public void PostValueToTargetWeightTable(String Input1) {
        long res = Insert_Target_Weight(Input1);
    }

    public void PostDayEnd2Row(DayCiF1005 IN) {
        //Algorithm Engineering -> Step One
        //extract all properties individually that need to be stored in Table DayEnd2
        //but them individually declared vars
        //then write this value to the Table DayEnd2 using existing Technology
        //PUSH.

        java.time.LocalDateTime mDate = IN.getDay();

        String date = mDate.toString();
        String nextDay = plus24hour(convertLocalDateTimeToDate(mDate)).toString();
        String nextDayBudget = new Integer(IN.getActualDayEndBalance() - 300).toString();
        String budget = new Integer(IN.getStartBalanceBFWD() - 300).toString();
        String actualDayEnd = new Integer(IN.getActualDayEndBalance()).toString();

        Insert_Dayend2Row(date, IN.getBudget(), IN.getActualDayEnd());
        //Insert_Dayend2Row(nextDay,nextDayBudget,"0");
    }

    public void Insert_Dayend2Row(String date, String budget, String actualDayEnd) {
        ContentValues cv = new ContentValues();
        // Fix: Store the actual date string, not current time
        cv.put(COLUMN_DAYEND_BALANCE_DATE2, date);
        if (budget == null) {
            cv.put(COLUMN_DAYEND_BALANCE_BALANCE_BUDGET, "N/A");
        } else {
            cv.put(COLUMN_DAYEND_BALANCE_BALANCE_BUDGET, budget);
        }

        cv.put(COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL, actualDayEnd);


        long res = getWritableDatabase().insert(TABLE_DAYEND_BALANCE2, null, cv);
        Log.d("SQLDatabase", "Inserted DayEnd2 row: date=" + date + ", budget=" + budget + ", result=" + res);
        //sucessful insert.

        //int affected = getWritableDatabase().delete(TABLE_DAYEND_BALANCE, null, null);
        //long res = getWritableDatabase().insert(TABLE_DAYEND_BALANCE, null, cv);
        //Continue for rest of variables.

    }

    /**
     * Returns true if a dayend_balance2 snapshot has already been stored for the given date.
     * Used as idempotency guard inside Store_Dayend2() so we only snapshot once per day.
     *
     * @param date "dd-MM-yyyy" formatted date string
     */
    public boolean isDayEnd2StoredForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + TABLE_DAYEND_BALANCE2
                            + " WHERE " + COLUMN_DAYEND_BALANCE_DATE2 + " = ?",
                    new String[]{date});
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count > 0;
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "[isDayEnd2StoredForDate] " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Store a complete 9:59 PM day-end snapshot in dayend_balance2.
     * Captures balance, daily budget, food calories consumed, kitty value, and estimated zero date.
     * The date is stored as the primary "balance_dayend_budget" composite key to remain
     * compatible with the existing 3-column table schema.
     *
     * @param date             "dd-MM-yyyy" formatted date string
     * @param balance          countdown balance at day-end
     * @param dailyBudget      gender-based calorie budget (2000F / 2500M)
     * @param todayFoodCals    total calories consumed today (food notes)
     * @param kittyValue       dailyBudget - todayFoodCals
     * @param estimatedZeroDate human-readable estimated zero date
     */
    public void storeDayEnd2Snapshot(String date, int balance, int dailyBudget,
                                     int todayFoodCals, int kittyValue, String estimatedZeroDate) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_DAYEND_BALANCE_DATE2, date);
            // Repurpose budget column: store JSON-style summary string
            String budgetSummary = "budget=" + dailyBudget
                    + "|food=" + todayFoodCals
                    + "|kitty=" + kittyValue
                    + "|zeroDate=" + estimatedZeroDate;
            cv.put(COLUMN_DAYEND_BALANCE_BALANCE_BUDGET, budgetSummary);
            cv.put(COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL, String.valueOf(balance));
            long res = getWritableDatabase().insert(TABLE_DAYEND_BALANCE2, null, cv);
            Log.d(TAG, "[storeDayEnd2Snapshot] Stored date=" + date
                    + " balance=" + balance + " kitty=" + kittyValue
                    + " zeroDate=" + estimatedZeroDate + " rowId=" + res);
        } catch (Exception e) {
            Log.e(TAG, "[storeDayEnd2Snapshot] " + e.getMessage(), e);
        }
    }

    /**
     * Returns all day-end countdown balances from {@code dayend_balance2}, oldest first.
     * Used to compute average daily reduction for the Estimated Date to Zero feature.
     * Rows with null or unparseable balance values are silently skipped.
     *
     * @return List of integer balances ordered chronologically (oldest → newest).
     *         Empty list if the table has no data or an error occurs.
     */
    public List<Integer> getHistoricalDayEndBalances() {
        List<Integer> balances = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_DAYEND_BALANCE_BALANCE_ACTUAL
                            + " FROM " + TABLE_DAYEND_BALANCE2
                            + " ORDER BY _id ASC", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String val = cursor.getString(0);
                    if (val != null && !val.trim().isEmpty()) {
                        try {
                            balances.add(Integer.parseInt(val.trim()));
                        } catch (NumberFormatException ignore) {
                            // skip non-integer rows
                        }
                    }
                }
                cursor.close();
            }
            android.util.Log.d("HISTORY_DB", "getHistoricalDayEndBalances: count=" + balances.size());
        } catch (Exception e) {
            android.util.Log.e("HISTORY_DB", "Error reading historical balances: " + e.getMessage());
        }
        return balances;
    }

    /**
     * Returns the total calories from all non-transferred food notes entered today.
     * "Today" is defined as matching the current date prefix "dd-MM-yyyy".
     */
    public int getTotalFoodNoteCaloriesToday() {
        int total = 0;
        String todayPrefix = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT SUM(" + COLUMN_QUICK_FOOD_NOTE_CALORIES + ") FROM "
                            + TABLE_QUICK_FOOD_NOTE
                            + " WHERE " + COLUMN_IS_TRANSFERRED + " = 0"
                            + " AND " + COLUMN_QUICK_FOOD_NOTE_DATE + " LIKE ?",
                    new String[]{todayPrefix + "%"});
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "[getTotalFoodNoteCaloriesToday] " + e.getMessage(), e);
        }
        return total;
    }

    public CountdownToZeroDayCiF1004 Retrieve_CountdownToZeroDayCiF1004() {
        //Go through Table DayEnd2, Retreive and convert each row to a DayTypeCiF1003 add
        //to a newly created CountdownToZeroDayType1004 and return as OUTPUT of this
        //Module*

        return Retrieve_All_DayEnd2_Rows();

    }

    public CountdownToZeroDayCiF1004 Retrieve_All_DayEnd2_Rows() {
        Log.d("RetrieveDebug", "=== Retrieve_All_DayEnd2_Rows called ===");
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_DAYEND_BALANCE2, null);
        Log.d("RetrieveDebug", "Raw cursor count: " + cursor.getCount());
        cursor.moveToFirst();

        // Transaction_Cursor_CIF24 TransactionCursor = new Transaction_Cursor_CIF24(cursor);
        CountdownToXeroDayType1004_Cursor TransactionCursor = new CountdownToXeroDayType1004_Cursor(cursor);
        CountdownToZeroDayCiF1004 OUTPUT = new CountdownToZeroDayCiF1004(0, new HealthProfileCiF3());

        Log.d("RetrieveDebug", "TransactionCursor count: " + TransactionCursor.getCount());

        if (TransactionCursor.getCount() < 1) {
            Log.d(TAG, "NOTHING FOUND IN Transaction DATABASE");
            TransactionCursor.close();
            return OUTPUT;
        } else {
            try {
                Log.d(TAG, "CONTENTS IN Transaction DATABASE, count: " + TransactionCursor.getCount());
                OUTPUT = GetTransactions(TransactionCursor);
                Log.d("RetrieveDebug", "After GetTransactions, OUTPUT list size: " + OUTPUT.getNumberOFDaysToXero03FEB10().size());


                //BBox.Set_Breakfast_ID((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
                //Mealio = new Meal_Items_Cursor(cursor);
                //Transaction_Items = Mealio.Get_Transaction_Food_Items(new RoundingCIF13().StringToInt((getString(getColumnIndex(COLUMN_TRANSACTIONS_ID)))));
                //BBox.Set_Breakfast_Date((new RoundingCIF13()).StringToLong((getString(getColumnIndex(COLMUM_TRANSACTIONS_DATE)))));
                //BBox.Set_Breakfast_Meal_Type(getString(getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE)));
                //BBox.Set_Breakfast_Meal_Type_ID((new RoundingCIF13().StringToInt((getString((getColumnIndex(COLUMUM_TRANSACTIONS_MEAL_TYPE_ID)))))));
                //BBox.Set_Breakfast_Amount((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_AMOUNT)))));
                //BBox.Set_Breakfast_Balance((new RoundingCIF13()).StringToInt((getString(getColumnIndex(COLUMUM_TRANSACTIONS_BALANCE)))));

                Log.d(TAG, "Contents of OUTPUTb, Size : " + new RoundingCIF13().IntToString(OUTPUT.getNumberOFDaysToXero03FEB10().size()));

                Log.d(" Menuitem Module", "Crashes here Line 639");

                Breakfast_Box_CIF17 itet = new Breakfast_Box_CIF17(); //OUTPUTb.get(0);

                //Log.d("Contents Meal Boxes", itet.Get_Food_Items().get(0).Get_food_item_name());

                return OUTPUT;

                //BoxCIF17 OUTPUTc = Transform_Breakfast_Box_to_Box(OUTPUTb);
                //long Transaction_ID = OUTPUTc.Get_Transaction_ID();
                //OUTPUT.add_BOX_to_Line(Transaction_ID, OUTPUTc);
            } catch (Exception x) {
                Toast.makeText(mContext, "Meal Box is empty aborting function", Toast.LENGTH_SHORT);
            }


            return new CountdownToZeroDayCiF1004(0, new HealthProfileCiF3());
        }
    }

    private Date plus24hour(Date time) {
        long OneMinute = (1000 * 60 * 60 * 24);
        time.setTime(time.getTime() + (OneMinute * 3000));
        return time;
    }

    private void Delete_Dummy_Rows() {

        //Alogrithm Engineering -> Delete Row Where Meal_Type = 'Dummy"
//ve        //Delete all Rows where Meal_type = Dummy.
    }

    private void deleteTargetWeightTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM target_weight");
        db.close();
    }

    private boolean isTimeAfter4pm() {
        java.time.LocalDateTime Four_Thirty_const_ref = java.time.LocalDateTime.now();
        java.time.LocalDateTime Four_Thirty_const = java.time.LocalDateTime.of(Four_Thirty_const_ref.getYear(), Four_Thirty_const_ref.getMonth().getValue(), Four_Thirty_const_ref.getDayOfMonth(), 16, 35);

        if (Four_Thirty_const_ref.isAfter(Four_Thirty_const)) {
            time_is_After_Four_Thirty_PM = true;
            BMR_has_been_performed = true;
        }
        return time_is_After_Four_Thirty_PM;
    }

    private boolean haveNotYetPerformedDayENDCFWD() {
        return BMR_has_been_performed;
    }

    public long insertFoodNote(String date, String food, String calories, String quantity) {
        android.util.Log.d("INSERT ALL FOOD NOTES", "Insert Note function called");
        android.util.Log.d("INSERT ALL FOOD NOTES", "Parameters - date: " + date + ", food: " + food + ", calories: " + calories + ", quantity: " + quantity);

        SQLiteDatabase db = null;
        long insertedId = -1;

        try {
            db = this.getWritableDatabase();
            android.util.Log.d("INSERT ALL FOOD NOTES", "getWritableDatabase called");

            ContentValues values = new ContentValues();
            android.util.Log.d("INSERT ALL FOOD NOTES", "ContentValues created");
            values.put(COLUMN_QUICK_FOOD_NOTE_DATE, date);
            values.put(COLUMN_QUICK_FOOD_NOTE_FOOD, food);
            values.put(COLUMN_QUICK_FOOD_NOTE_CALORIES, calories);
            values.put(COLUMN_QUICK_FOOD_NOTE_QUANTITY, quantity);
            values.put(COLUMN_IS_TRANSFERRED, 0); // false when inserting

            android.util.Log.d("INSERT ALL FOOD NOTES", "Attempting insert into table: " + TABLE_QUICK_FOOD_NOTE);
            insertedId = db.insert(TABLE_QUICK_FOOD_NOTE, null, values);

            if (insertedId == -1) {
                android.util.Log.e("INSERT ALL FOOD NOTES", "Insert FAILED - returned -1");
                // Try insertOrThrow to get the actual error
                try {
                    db.insertOrThrow(TABLE_QUICK_FOOD_NOTE, null, values);
                } catch (Exception e) {
                    android.util.Log.e("INSERT ALL FOOD NOTES", "insertOrThrow exception: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                android.util.Log.d("INSERT ALL FOOD NOTES", "Data inserted successfully with ID: " + insertedId);
            }
        } catch (Exception e) {
            android.util.Log.e("INSERT ALL FOOD NOTES", "Exception during insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return insertedId;
    }

    public int updateFoodNote(int noteId, String date, String food, String calories, String quantity) {
        android.util.Log.d("UPDATE FOOD NOTE", "Update Note function called");
        SQLiteDatabase db = this.getWritableDatabase();
        android.util.Log.d("UPDATE FOOD NOTE", "getWritableDatabase called");

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUICK_FOOD_NOTE_DATE, date);
        values.put(COLUMN_QUICK_FOOD_NOTE_FOOD, food);
        values.put(COLUMN_QUICK_FOOD_NOTE_CALORIES, calories);
        values.put(COLUMN_QUICK_FOOD_NOTE_QUANTITY, quantity);
        values.put(COLUMN_IS_TRANSFERRED, 0); // reset transfer flag when updating

        int rowsAffected = db.update(
                TABLE_QUICK_FOOD_NOTE,
                values,
                COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                new String[]{String.valueOf(noteId)}
        );

        android.util.Log.d("UPDATE FOOD NOTE", "Rows updated: " + rowsAffected);

        db.close();
        return rowsAffected;
    }


    /**
     * Updates only the calories column for a food note by ID.
     * Used by the AI save-back flow so other fields (date, food name, quantity) are not touched.
     *
     * @param noteId   The note_id to update.
     * @param calories New calorie value to store.
     * @return Number of rows affected (1 on success, 0 if not found).
     */
    public int updateFoodNoteCaloriesOnly(int noteId, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUICK_FOOD_NOTE_CALORIES, String.valueOf(calories));
            int rows = db.update(
                    TABLE_QUICK_FOOD_NOTE,
                    values,
                    COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                    new String[]{String.valueOf(noteId)});
            android.util.Log.d("FoodNoteDB", "updateFoodNoteCaloriesOnly: id=" + noteId
                    + ", cal=" + calories + ", rows=" + rows);
            return rows;
        } catch (Exception e) {
            android.util.Log.e("FoodNoteDB", "Error updating calories for id=" + noteId
                    + ": " + e.getMessage());
            return 0;
        } finally {
            db.close();
        }
    }

    // DBHelper
    public void markAllAsTransferred() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_TRANSFERRED, 1);

        int rows = db.update(
                TABLE_QUICK_FOOD_NOTE,
                values,
                null,  // no WHERE → update all rows
                null
        );

        android.util.Log.d("UPDATE FOOD NOTE", "Rows updated: " + rows);
        db.close();
    }

    public Cursor getAllFoodNotes() {
        android.util.Log.d("GET ALL FOOD NOTES", "getAllFoodNotes CAlled");
        SQLiteDatabase db = this.getReadableDatabase();
        android.util.Log.d("GET ALL FOOD NOTES", "getReadableDatabase call done");
        return db.rawQuery("SELECT * FROM " + TABLE_QUICK_FOOD_NOTE, null);
    }

    /**
     * Get only food notes that have NOT been saved to a collection yet.
     * These are notes where isSavedToCollection = 0.
     *
     * @return Cursor containing unsaved food notes
     */
    public Cursor getUnsavedFoodNotes() {
        android.util.Log.d("GET_UNSAVED_NOTES", "getUnsavedFoodNotes called");
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUICK_FOOD_NOTE +
                " WHERE " + COLUMN_IS_SAVED_TO_COLLECTION + " = 0", null);
    }

    /**
     * Mark food notes as saved to collection.
     * Sets isSavedToCollection = 1 for the given note IDs.
     *
     * @param noteIds List of note IDs to mark as saved
     * @return Number of rows updated
     */
    public int markNotesAsSavedToCollection(List<Integer> noteIds) {
        if (noteIds == null || noteIds.isEmpty()) {
            android.util.Log.d("MARK_SAVED", "No note IDs provided");
            return 0;
        }

        android.util.Log.d("MARK_SAVED", "Marking " + noteIds.size() + " notes as saved to collection");
        SQLiteDatabase db = this.getWritableDatabase();

        // Build the IN clause for the query
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < noteIds.size(); i++) {
            if (i > 0) {
                inClause.append(",");
            }
            inClause.append(noteIds.get(i));
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_SAVED_TO_COLLECTION, 1);

        int rowsUpdated = db.update(
                TABLE_QUICK_FOOD_NOTE,
                values,
                COLUMN_QUICK_FOOD_NOTE_ID + " IN (" + inClause.toString() + ")",
                null
        );

        android.util.Log.d("MARK_SAVED", "Rows updated: " + rowsUpdated);
        db.close();
        return rowsUpdated;
    }

    /**
     * Check if a food note has been saved to collection.
     *
     * @param noteId The ID of the food note
     * @return true if saved to collection, false otherwise
     */
    public boolean isNoteSavedToCollection(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IS_SAVED_TO_COLLECTION +
                        " FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                new String[]{String.valueOf(noteId)});

        boolean isSaved = false;
        if (cursor.moveToFirst()) {
            isSaved = cursor.getInt(0) == 1;
        }
        cursor.close();
        db.close();
        return isSaved;
    }

    /** Mark a food note as successfully synced to the backend. */
    public void markFoodNoteAsSynced(long noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_SYNCED, 1);
            db.update(TABLE_QUICK_FOOD_NOTE, values,
                    COLUMN_QUICK_FOOD_NOTE_ID + "=?",
                    new String[]{String.valueOf(noteId)});
            android.util.Log.d("FoodNoteDB", "markFoodNoteAsSynced: id=" + noteId);
        } finally {
            db.close();
        }
    }

    /**
     * Returns all food notes that have not yet been synced to the backend.
     * Each String[] contains: [note_id, note_food, note_calories, note_quantity, note_date]
     */
    public List<String[]> getUnsyncedFoodNotes() {
        List<String[]> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT " + COLUMN_QUICK_FOOD_NOTE_ID + ", "
                            + COLUMN_QUICK_FOOD_NOTE_FOOD + ", "
                            + COLUMN_QUICK_FOOD_NOTE_CALORIES + ", "
                            + COLUMN_QUICK_FOOD_NOTE_QUANTITY + ", "
                            + COLUMN_QUICK_FOOD_NOTE_DATE
                            + " FROM " + TABLE_QUICK_FOOD_NOTE
                            + " WHERE " + COLUMN_IS_SYNCED + " = 0",
                    null);
            while (cursor.moveToNext()) {
                result.add(new String[]{
                        cursor.getString(0), // note_id
                        cursor.getString(1), // food
                        cursor.getString(2), // calories
                        cursor.getString(3), // quantity
                        cursor.getString(4)  // date
                });
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return result;
    }

    public Cursor getTransferredQuickFoodNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUICK_FOOD_NOTE + " WHERE isTransferred = 1", null);
    }

    /**
     * Deletes all transferred quick food notes from the database.
     * This permanently removes records where isTransferred = 1.
     *
     * @return Number of rows deleted
     */
    public int deleteTransferredQuickFoodNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedCount = 0;
        try {
            deletedCount = db.delete(TABLE_QUICK_FOOD_NOTE, COLUMN_IS_TRANSFERRED + " = 1", null);
            android.util.Log.d("DELETE TRANSFERRED", "Deleted " + deletedCount + " transferred food notes");
        } catch (Exception e) {
            android.util.Log.e("DELETE TRANSFERRED", "Error deleting transferred notes: " + e.getMessage(), e);
        } finally {
            db.close();
        }
        return deletedCount;
    }

    /**
     * Transfers up to N food notes while maintaining a maximum of N transferred notes at all times.
     * <p>
     * Logic:
     * 1. Count existing transferred notes (isTransferred = 1)
     * 2. Count available notes to transfer (isTransferred = 0)
     * 3. Calculate how many to transfer (minimum of available or limit)
     * 4. Calculate total after transfer
     * 5. If total > limit, delete oldest transferred notes by datetime to make room
     * 6. Mark new notes as transferred
     * <p>
     * Example scenarios:
     * - 15 available, 0 transferred, limit=10 → Transfer 10, delete 0
     * - 15 available, 10 transferred, limit=10 → Transfer 10, delete 10 (oldest by date)
     * - 5 available, 10 transferred, limit=10 → Transfer 5, delete 5 (oldest by date)
     * - 8 available, 3 transferred, limit=10 → Transfer 8, delete 1 (to keep total at 10)
     *
     * @param limit Maximum number of transferred notes to maintain (e.g., 10)
     */
    public void transferLastNFoodNotes(int limit) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();

            // Step 1: Count existing transferred notes (isTransferred = 1)
            String countExistingQuery = "SELECT COUNT(*) FROM " + TABLE_QUICK_FOOD_NOTE +
                    " WHERE " + COLUMN_IS_TRANSFERRED + " = 1";
            Cursor existingCursor = db.rawQuery(countExistingQuery, null);
            int existingCount = 0;
            if (existingCursor.moveToFirst()) {
                existingCount = existingCursor.getInt(0);
            }
            existingCursor.close();

            // Step 2: Count available notes to transfer (isTransferred = 0)
            String countAvailableQuery = "SELECT COUNT(*) FROM " + TABLE_QUICK_FOOD_NOTE +
                    " WHERE " + COLUMN_IS_TRANSFERRED + " = 0";
            Cursor availableCursor = db.rawQuery(countAvailableQuery, null);
            int availableCount = 0;
            if (availableCursor.moveToFirst()) {
                availableCount = availableCursor.getInt(0);
            }
            availableCursor.close();

            // Step 3: Calculate how many we want to transfer (minimum of available or limit)
            int wantToTransfer = Math.min(availableCount, limit);

            // Step 4: Calculate total after transfer
            int totalAfterTransfer = existingCount + wantToTransfer;

            // Step 5: Calculate how many to delete to maintain max limit
            int deleteCount = Math.max(0, totalAfterTransfer - limit);

            android.util.Log.d("TRANSFER NOTES",
                    "Existing transferred: " + existingCount +
                            ", Available to transfer: " + availableCount +
                            ", Want to transfer: " + wantToTransfer +
                            ", Total after: " + totalAfterTransfer +
                            ", Will delete: " + deleteCount);

            // Step 6: Delete oldest transferred notes by datetime if needed
            if (deleteCount > 0) {
                String deleteQuery = "DELETE FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_QUICK_FOOD_NOTE_ID + " IN (" +
                        "SELECT " + COLUMN_QUICK_FOOD_NOTE_ID + " FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_IS_TRANSFERRED + " = 1" +
                        " ORDER BY " + COLUMN_QUICK_FOOD_NOTE_DATE + " ASC" +
                        " LIMIT " + deleteCount +
                        ")";
                db.execSQL(deleteQuery);
                android.util.Log.d("TRANSFER NOTES", "Deleted " + deleteCount + " oldest transferred notes (by datetime)");
            }

            // Step 7: Get the IDs of notes to transfer (newest first by note_id)
            if (wantToTransfer > 0) {
                String query = "SELECT " + COLUMN_QUICK_FOOD_NOTE_ID + " FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_IS_TRANSFERRED + " = 0" +
                        " ORDER BY " + COLUMN_QUICK_FOOD_NOTE_ID + " DESC" +
                        " LIMIT " + wantToTransfer;

                Cursor cursor = db.rawQuery(query, null);
                ArrayList<Integer> noteIdsToTransfer = new ArrayList<>();

                if (cursor != null && cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(COLUMN_QUICK_FOOD_NOTE_ID);
                    do {
                        noteIdsToTransfer.add(cursor.getInt(idIndex));
                    } while (cursor.moveToNext());
                    cursor.close();
                }

                android.util.Log.d("TRANSFER NOTES", "Found " + noteIdsToTransfer.size() + " notes to transfer");

                // Step 8: Mark those specific notes as transferred
                if (!noteIdsToTransfer.isEmpty()) {
                    ContentValues transferValues = new ContentValues();
                    transferValues.put(COLUMN_IS_TRANSFERRED, 1);

                    for (Integer noteId : noteIdsToTransfer) {
                        db.update(
                                TABLE_QUICK_FOOD_NOTE,
                                transferValues,
                                COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                                new String[]{String.valueOf(noteId)}
                        );
                    }

                    android.util.Log.d("TRANSFER NOTES", "Marked " + noteIdsToTransfer.size() + " notes as transferred");
                }
            }

            db.setTransactionSuccessful();

            android.util.Log.d("TRANSFER NOTES", "Transfer completed successfully. Max " + limit + " transferred notes maintained.");

        } catch (Exception e) {
            android.util.Log.e("TRANSFER NOTES", "Error during transfer: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Transfer specific food notes by their IDs.
     * Only transfers notes where isTransferred = 0.
     * Maintains a maximum of 10 transferred notes (FIFO - oldest deleted first).
     *
     * @param noteIds List of note IDs to transfer
     * @return Number of notes actually transferred
     */
    public int transferFoodNotesByIds(List<Integer> noteIds) {
        if (noteIds == null || noteIds.isEmpty()) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        int transferredCount = 0;

        try {
            db.beginTransaction();

            // Step 1: Count existing transferred notes (isTransferred = 1)
            String countExistingQuery = "SELECT COUNT(*) FROM " + TABLE_QUICK_FOOD_NOTE +
                    " WHERE " + COLUMN_IS_TRANSFERRED + " = 1";
            Cursor existingCursor = db.rawQuery(countExistingQuery, null);
            int existingCount = 0;
            if (existingCursor.moveToFirst()) {
                existingCount = existingCursor.getInt(0);
            }
            existingCursor.close();

            // Step 2: Filter noteIds to only include notes that are not yet transferred
            List<Integer> validNoteIds = new ArrayList<>();
            for (Integer noteId : noteIds) {
                String checkQuery = "SELECT " + COLUMN_IS_TRANSFERRED + " FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_QUICK_FOOD_NOTE_ID + " = ?";
                Cursor checkCursor = db.rawQuery(checkQuery, new String[]{String.valueOf(noteId)});
                if (checkCursor.moveToFirst()) {
                    int isTransferred = checkCursor.getInt(0);
                    if (isTransferred == 0) {
                        validNoteIds.add(noteId);
                    }
                }
                checkCursor.close();
            }

            if (validNoteIds.isEmpty()) {
                db.endTransaction();
                return 0;
            }

            int wantToTransfer = validNoteIds.size();
            int limit = 10; // Maximum transferred notes to maintain

            // Step 3: Calculate total after transfer
            int totalAfterTransfer = existingCount + wantToTransfer;

            // Step 4: Calculate how many to delete to maintain max limit
            int deleteCount = Math.max(0, totalAfterTransfer - limit);

            android.util.Log.d("TRANSFER NOTES BY IDS",
                    "Existing transferred: " + existingCount +
                            ", Want to transfer: " + wantToTransfer +
                            ", Total after: " + totalAfterTransfer +
                            ", Will delete: " + deleteCount);

            // Step 5: Delete oldest transferred notes by datetime if needed
            if (deleteCount > 0) {
                String deleteQuery = "DELETE FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_QUICK_FOOD_NOTE_ID + " IN (" +
                        "SELECT " + COLUMN_QUICK_FOOD_NOTE_ID + " FROM " + TABLE_QUICK_FOOD_NOTE +
                        " WHERE " + COLUMN_IS_TRANSFERRED + " = 1" +
                        " ORDER BY " + COLUMN_QUICK_FOOD_NOTE_DATE + " ASC" +
                        " LIMIT " + deleteCount +
                        ")";
                db.execSQL(deleteQuery);
                android.util.Log.d("TRANSFER NOTES BY IDS", "Deleted " + deleteCount + " oldest transferred notes");
            }

            // Step 6: Mark the selected notes as transferred
            ContentValues transferValues = new ContentValues();
            transferValues.put(COLUMN_IS_TRANSFERRED, 1);

            for (Integer noteId : validNoteIds) {
                int updated = db.update(
                        TABLE_QUICK_FOOD_NOTE,
                        transferValues,
                        COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                        new String[]{String.valueOf(noteId)}
                );
                if (updated > 0) {
                    transferredCount++;
                }
            }

            db.setTransactionSuccessful();
            android.util.Log.d("TRANSFER NOTES BY IDS", "Transferred " + transferredCount + " notes successfully");

        } catch (Exception e) {
            android.util.Log.e("TRANSFER NOTES BY IDS", "Error during transfer: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
            db.close();
        }

        return transferredCount;
    }

    public int getTotalCalories() {
        int totalCalories = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_QUICK_FOOD_NOTE_CALORIES + ") FROM "
                + TABLE_QUICK_FOOD_NOTE + " WHERE " + COLUMN_IS_TRANSFERRED + " = 0";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalCalories = cursor.getInt(0);
            android.util.Log.d("GET TOTAL CALORIES", "totalCalories" + totalCalories);
        }
        cursor.close();
        db.close();
        android.util.Log.d("GET TOTAL CALORIES", "totalCalories" + totalCalories);
        return totalCalories;
    }


    public void deleteFoodNotes(List<Integer> noteIds) {
        android.util.Log.d("DELETE FOOD NOTES", "Delete multiple notes function called");
        SQLiteDatabase db = this.getWritableDatabase();

        if (noteIds == null || noteIds.isEmpty()) {
            android.util.Log.d("DELETE FOOD NOTES", "No IDs provided to delete");
            return;
        }

        // Create placeholders (?, ?, ? ...) for the IN clause
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < noteIds.size(); i++) {
            placeholders.append("?");
            if (i < noteIds.size() - 1) {
                placeholders.append(",");
            }
        }

        String whereClause = COLUMN_QUICK_FOOD_NOTE_ID + " IN (" + placeholders.toString() + ")";

        // Convert List<Integer> to String[] for whereArgs
        String[] whereArgs = new String[noteIds.size()];
        for (int i = 0; i < noteIds.size(); i++) {
            whereArgs[i] = String.valueOf(noteIds.get(i));
        }

        // Execute delete
        int rowsDeleted = db.delete(TABLE_QUICK_FOOD_NOTE, whereClause, whereArgs);

        android.util.Log.d("DELETE FOOD NOTES", "Deleted " + rowsDeleted + " note(s)");

        db.close();
    }


    public void insertWaterData(int mlWaterDrunk, double equivalentCups) {
        android.util.Log.d("INSERT WATER DATA", "Insert Water Data function called");

        // Calculate missing value
        if (mlWaterDrunk <= 0 && equivalentCups > 0) {
            mlWaterDrunk = (int) (equivalentCups * 250);
        } else if (equivalentCups <= 0 && mlWaterDrunk > 0) {
            equivalentCups = mlWaterDrunk / 250.0;
        }

        // Generate unique ID and date
        String uniqueId = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Now create the model with corrected values
        WaterTrackedModel model = new WaterTrackedModel(
                uniqueId,
                date,
                mlWaterDrunk,
                equivalentCups
        );

        // Insert into database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATER_UNIQUE_ID, model.getUniqueId());
        values.put(COLUMN_WATER_DATE, model.getDate());
        values.put(COLUMN_WATER_ML, model.getMlWaterDrunk());
        values.put(COLUMN_WATER_CUPS, model.getEquivalentCups());

        db.insert(TABLE_WATER_TRACKER, null, values);
        android.util.Log.d("INSERT WATER DATA", "Data inserted");
        db.close();
    }


    public List<WaterTrackedModel> getAllWaterData() {
        List<WaterTrackedModel> waterList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WATER_TRACKER, null);

        if (cursor.moveToFirst()) {
            do {
                String uniqueId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATER_UNIQUE_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATER_DATE));
                int mlWaterDrunk = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WATER_ML));
                double equivalentCups = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WATER_CUPS));

                waterList.add(new WaterTrackedModel(uniqueId, date, mlWaterDrunk, equivalentCups));
                android.util.Log.d("GET WATER DATA", waterList.size() + " " + waterList.get(0).getDate());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return waterList;
    }

    /**
     * Get total water intake for today in ml
     * @return Total ml of water consumed today
     */
    public int getTodayTotalWaterMl() {
        int totalMl = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
            "SELECT SUM(" + COLUMN_WATER_ML + ") FROM " + TABLE_WATER_TRACKER +
            " WHERE " + COLUMN_WATER_DATE + " = ?",
            new String[]{today}
        );

        if (cursor.moveToFirst()) {
            totalMl = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        android.util.Log.d("WATER TRACKER", "Today's total water: " + totalMl + " ml");
        return totalMl;
    }

    // ==================== HEART RATE TRACKER CRUD OPERATIONS ====================

    /**
     * Insert a new heart rate measurement.
     *
     * @param bpm  The heart rate in beats per minute
     * @param note Optional note about the measurement (can be null)
     * @return The ID of the inserted record, or -1 if failed
     */
    public long insertHeartRate(int bpm, String note) {
        android.util.Log.d("INSERT HEART RATE", "Insert Heart Rate function called with BPM: " + bpm);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEART_RATE_DATE, date);
        values.put(COLUMN_HEART_RATE_TIME, time);
        values.put(COLUMN_HEART_RATE_BPM, bpm);
        values.put(COLUMN_HEART_RATE_NOTE, note);

        long insertedId = db.insert(TABLE_HEART_RATE, null, values);
        android.util.Log.d("INSERT HEART RATE", "Heart rate data inserted with ID: " + insertedId);
        db.close();

        return insertedId;
    }

    /**
     * Get all heart rate measurements, ordered by date and time descending.
     *
     * @return List of heart rate records as Maps containing date, time, bpm, and note
     */
    public List<Map<String, Object>> getAllHeartRateData() {
        List<Map<String, Object>> heartRateList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_HEART_RATE + " ORDER BY " + COLUMN_HEART_RATE_DATE + " DESC, " + COLUMN_HEART_RATE_TIME + " DESC",
            null
        );

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> record = new HashMap<>();
                record.put("id", cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE_ID)));
                record.put("date", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE_DATE)));
                record.put("time", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE_TIME)));
                record.put("bpm", cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE_BPM)));
                record.put("note", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE_NOTE)));
                heartRateList.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        android.util.Log.d("HEART RATE TRACKER", "Retrieved " + heartRateList.size() + " heart rate records");
        return heartRateList;
    }

    /**
     * Get the last recorded heart rate.
     *
     * @return The last recorded BPM, or -1 if no records exist
     */
    public int getLastHeartRate() {
        int lastBpm = -1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
            "SELECT " + COLUMN_HEART_RATE_BPM + " FROM " + TABLE_HEART_RATE +
            " ORDER BY " + COLUMN_HEART_RATE_DATE + " DESC, " + COLUMN_HEART_RATE_TIME + " DESC LIMIT 1",
            null
        );

        if (cursor.moveToFirst()) {
            lastBpm = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return lastBpm;
    }

    /**
     * Delete a heart rate record by ID.
     *
     * @param id The ID of the record to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteHeartRate(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_HEART_RATE, COLUMN_HEART_RATE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

    private java.util.Date getCurrentTime() {
        return new Date();
    }


    private Date convertLocalDateTimeToDate(java.time.LocalDateTime Input) {
        Date date = Date.from(Input.atZone(ZoneId.systemDefault()).toInstant());

        return date;
    }

    // ==================== BLOOD PRESSURE CRUD ====================

    public long insertBloodPressure(String date, String time, int systolic, int diastolic) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            ContentValues values = new ContentValues();
            values.put(COLUMN_BP_DATE, date);
            values.put(COLUMN_BP_TIME, time);
            values.put(COLUMN_BP_SYSTOLIC, systolic);
            values.put(COLUMN_BP_DIASTOLIC, diastolic);
            values.put(COLUMN_BP_CREATED_AT, createdAt);
            id = db.insert(TABLE_HEALTH_BP, null, values);
            android.util.Log.d("HEALTH_DB", "insertBloodPressure: " + systolic + "/" + diastolic + " id=" + id);
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "insertBloodPressure error: " + e.getMessage());
        } finally {
            db.close();
        }
        return id;
    }

    public List<HealthReadingModel> getAllBloodPressure() {
        List<HealthReadingModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_HEALTH_BP + " ORDER BY " + COLUMN_BP_DATE + " DESC, " + COLUMN_BP_TIME + " DESC",
                    null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new HealthReadingModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BP_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BP_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BP_TIME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BP_SYSTOLIC)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BP_DIASTOLIC)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BP_CREATED_AT))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "getAllBloodPressure error: " + e.getMessage());
        } finally {
            db.close();
        }
        return list;
    }

    public boolean updateBloodPressure(int id, String date, String time, int systolic, int diastolic) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BP_DATE, date);
            values.put(COLUMN_BP_TIME, time);
            values.put(COLUMN_BP_SYSTOLIC, systolic);
            values.put(COLUMN_BP_DIASTOLIC, diastolic);
            int rows = db.update(TABLE_HEALTH_BP, values, COLUMN_BP_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "updateBloodPressure error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteBloodPressure(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_HEALTH_BP, COLUMN_BP_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "deleteBloodPressure error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    // ==================== HEART RATE READINGS CRUD (manual entry) ====================

    public long insertManualHeartRate(String date, String time, int bpm) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            ContentValues values = new ContentValues();
            values.put(COLUMN_HRDATA_DATE, date);
            values.put(COLUMN_HRDATA_TIME, time);
            values.put(COLUMN_HRDATA_BPM, bpm);
            values.put(COLUMN_HRDATA_CREATED_AT, createdAt);
            id = db.insert(TABLE_HEALTH_HR, null, values);
            android.util.Log.d("HEALTH_DB", "insertManualHeartRate: " + bpm + " BPM, id=" + id);
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "insertManualHeartRate error: " + e.getMessage());
        } finally {
            db.close();
        }
        return id;
    }

    public List<HealthReadingModel> getAllManualHeartRates() {
        List<HealthReadingModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_HEALTH_HR + " ORDER BY " + COLUMN_HRDATA_DATE + " DESC, " + COLUMN_HRDATA_TIME + " DESC",
                    null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new HealthReadingModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HRDATA_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HRDATA_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HRDATA_TIME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HRDATA_BPM)),
                            0,
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HRDATA_CREATED_AT))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "getAllManualHeartRates error: " + e.getMessage());
        } finally {
            db.close();
        }
        return list;
    }

    public boolean updateManualHeartRate(int id, String date, String time, int bpm) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_HRDATA_DATE, date);
            values.put(COLUMN_HRDATA_TIME, time);
            values.put(COLUMN_HRDATA_BPM, bpm);
            int rows = db.update(TABLE_HEALTH_HR, values, COLUMN_HRDATA_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "updateManualHeartRate error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteManualHeartRate(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_HEALTH_HR, COLUMN_HRDATA_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "deleteManualHeartRate error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    // ==================== BLOOD SUGAR CRUD ====================

    public long insertBloodSugar(String date, String time, int mgDl) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            ContentValues values = new ContentValues();
            values.put(COLUMN_BS_DATE, date);
            values.put(COLUMN_BS_TIME, time);
            values.put(COLUMN_BS_MGDL, mgDl);
            values.put(COLUMN_BS_CREATED_AT, createdAt);
            id = db.insert(TABLE_HEALTH_BS, null, values);
            android.util.Log.d("HEALTH_DB", "insertBloodSugar: " + mgDl + " mg/dL, id=" + id);
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "insertBloodSugar error: " + e.getMessage());
        } finally {
            db.close();
        }
        return id;
    }

    public List<HealthReadingModel> getAllBloodSugar() {
        List<HealthReadingModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_HEALTH_BS + " ORDER BY " + COLUMN_BS_DATE + " DESC, " + COLUMN_BS_TIME + " DESC",
                    null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new HealthReadingModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BS_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BS_DATE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BS_TIME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BS_MGDL)),
                            0,
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BS_CREATED_AT))
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "getAllBloodSugar error: " + e.getMessage());
        } finally {
            db.close();
        }
        return list;
    }

    public boolean updateBloodSugar(int id, String date, String time, int mgDl) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BS_DATE, date);
            values.put(COLUMN_BS_TIME, time);
            values.put(COLUMN_BS_MGDL, mgDl);
            int rows = db.update(TABLE_HEALTH_BS, values, COLUMN_BS_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "updateBloodSugar error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteBloodSugar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_HEALTH_BS, COLUMN_BS_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            android.util.Log.e("HEALTH_DB", "deleteBloodSugar error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    // ==================== MEMO TABLE CRUD OPERATIONS ====================

    /**
     * Insert a new memo with FIFO logic (max 10 entries).
     * If there are already MAX_MEMO_COUNT memos, the oldest one is deleted.
     *
     * @param memoText  The memo text content
     * @param memoImage Base64 encoded image string (can be null)
     * @return The ID of the inserted memo, or -1 if failed
     */
    public long insertMemo(String memoText, String memoImage) {
        return insertMemo(memoText, memoImage, null);
    }

    /**
     * Insert a new memo with FIFO logic (max 10 entries) with food note title.
     * If there are already MAX_MEMO_COUNT memos, the oldest one is deleted.
     * If a memo with the same title exists, it will be updated instead of inserted.
     *
     * @param memoText  The memo text content
     * @param memoImage Base64 encoded image string (can be null)
     * @param memoTitle The food note title this memo is associated with
     * @return The ID of the inserted/updated memo, or -1 if failed
     */
    public long insertMemo(String memoText, String memoImage, String memoTitle) {
        android.util.Log.d("MEMO", "insertMemo called with title: " + memoTitle);
        SQLiteDatabase db = null;
        long resultId = -1;

        try {
            db = this.getWritableDatabase();

            // Check if memo with this title already exists
            if (memoTitle != null && !memoTitle.isEmpty()) {
                Cursor existingCursor = db.query(TABLE_MEMO,
                        new String[]{COLUMN_MEMO_ID},
                        COLUMN_MEMO_TITLE + " = ?",
                        new String[]{memoTitle},
                        null, null, null);

                if (existingCursor != null && existingCursor.moveToFirst()) {
                    // Update existing memo
                    int existingId = existingCursor.getInt(existingCursor.getColumnIndex(COLUMN_MEMO_ID));
                    existingCursor.close();

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_MEMO_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                    values.put(COLUMN_MEMO_TEXT, memoText);
                    values.put(COLUMN_MEMO_IMAGE, memoImage);

                    int rowsUpdated = db.update(TABLE_MEMO, values,
                            COLUMN_MEMO_ID + " = ?",
                            new String[]{String.valueOf(existingId)});

                    if (rowsUpdated > 0) {
                        resultId = existingId;
                        android.util.Log.d("MEMO", "Memo updated successfully with ID: " + resultId);
                    }
                    return resultId;
                }
                if (existingCursor != null) {
                    existingCursor.close();
                }
            }

            // Check current count and enforce FIFO if needed
            enforceMemoFIFO(db);

            // Insert new memo
            ContentValues values = new ContentValues();
            values.put(COLUMN_MEMO_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            values.put(COLUMN_MEMO_TEXT, memoText);
            values.put(COLUMN_MEMO_IMAGE, memoImage);
            values.put(COLUMN_MEMO_TITLE, memoTitle);

            resultId = db.insert(TABLE_MEMO, null, values);

            if (resultId == -1) {
                android.util.Log.e("MEMO", "Insert FAILED - returned -1");
            } else {
                android.util.Log.d("MEMO", "Memo inserted successfully with ID: " + resultId);
            }
        } catch (Exception e) {
            android.util.Log.e("MEMO", "Exception during insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return resultId;
    }

    /**
     * Enforces FIFO logic for memo table.
     * If memo count >= MAX_MEMO_COUNT, deletes the oldest memo(s) to make room for new one.
     *
     * @param db The writable database instance
     */
    private void enforceMemoFIFO(SQLiteDatabase db) {
        try {
            // Get current count
            Cursor countCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MEMO, null);
            int currentCount = 0;
            if (countCursor.moveToFirst()) {
                currentCount = countCursor.getInt(0);
            }
            countCursor.close();

            android.util.Log.d("MEMO", "Current memo count: " + currentCount + ", Max: " + MAX_MEMO_COUNT);

            // If at or over limit, delete oldest entries to make room
            if (currentCount >= MAX_MEMO_COUNT) {
                int deleteCount = currentCount - MAX_MEMO_COUNT + 1; // +1 to make room for new entry
                android.util.Log.d("MEMO", "Deleting " + deleteCount + " oldest memo(s) to enforce FIFO");

                // Delete oldest memos (first in, first out - oldest by date)
                String deleteQuery = "DELETE FROM " + TABLE_MEMO +
                        " WHERE " + COLUMN_MEMO_ID + " IN (" +
                        "SELECT " + COLUMN_MEMO_ID + " FROM " + TABLE_MEMO +
                        " ORDER BY " + COLUMN_MEMO_DATE + " ASC, " + COLUMN_MEMO_ID + " ASC" +
                        " LIMIT " + deleteCount +
                        ")";
                db.execSQL(deleteQuery);
                android.util.Log.d("MEMO", "Deleted oldest memo(s)");
            }
        } catch (Exception e) {
            android.util.Log.e("MEMO", "Error enforcing FIFO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get all memos ordered by date (newest first)
     *
     * @return Cursor containing all memos
     */
    public Cursor getAllMemos() {
        android.util.Log.d("MEMO", "getAllMemos called");
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEMO + " ORDER BY " + COLUMN_MEMO_DATE + " DESC", null);
    }

    /**
     * Get memo by food note title.
     * Returns the memo associated with the given title.
     *
     * @param title The food note title to search for
     * @return Cursor containing the memo for this title, or empty cursor if not found
     */
    public Cursor getMemoByTitle(String title) {
        android.util.Log.d("MEMO", "getMemoByTitle called with title: " + title);
        SQLiteDatabase db = this.getReadableDatabase();

        // Safety check: ensure memo_title column exists
        try {
            Cursor checkCursor = db.rawQuery("SELECT " + COLUMN_MEMO_TITLE + " FROM " + TABLE_MEMO + " LIMIT 1", null);
            if (checkCursor != null) {
                checkCursor.close();
            }
        } catch (Exception e) {
            // Column doesn't exist, add it
            android.util.Log.d("MEMO", "memo_title column doesn't exist, adding it now");
            try {
                db.execSQL("ALTER TABLE " + TABLE_MEMO + " ADD COLUMN " + COLUMN_MEMO_TITLE + " TEXT");
                android.util.Log.d("MEMO", "Successfully added memo_title column");
            } catch (Exception e2) {
                android.util.Log.e("MEMO", "Error adding memo_title column: " + e2.getMessage());
            }
        }

        if (title == null || title.isEmpty()) {
            // Return empty cursor if no title provided
            return db.rawQuery("SELECT * FROM " + TABLE_MEMO + " WHERE 1=0", null);
        }
        return db.rawQuery("SELECT * FROM " + TABLE_MEMO + " WHERE " + COLUMN_MEMO_TITLE + " = ?",
                new String[]{title});
    }


    /**
     * Save a collection of food notes for a specific date.
     * This stores the food notes as a JSON string along with the total calories.
     *
     * @param collectionDate The date for this collection (format: "dd MMM yy")
     * @param notesJson      JSON string containing the array of food notes
     * @param totalCalories  Total calories for all notes in the collection
     * @return The ID of the inserted collection, or -1 if failed
     */
    public long insertFoodNotesCollection(String collectionDate, String notesJson, int totalCalories) {
        android.util.Log.d("FOOD_COLLECTION", "insertFoodNotesCollection called for date: " + collectionDate);
        SQLiteDatabase db = null;
        long insertedId = -1;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_COLLECTION_DATE, collectionDate);
            values.put(COLUMN_COLLECTION_NOTES_JSON, notesJson);
            values.put(COLUMN_COLLECTION_TOTAL_CALORIES, totalCalories);
            values.put(COLUMN_COLLECTION_CREATED_AT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

            insertedId = db.insert(TABLE_FOOD_NOTES_COLLECTION, null, values);

            if (insertedId == -1) {
                android.util.Log.e("FOOD_COLLECTION", "Insert FAILED - returned -1");
            } else {
                android.util.Log.d("FOOD_COLLECTION", "Food notes collection inserted successfully with ID: " + insertedId);
            }
        } catch (Exception e) {
            android.util.Log.e("FOOD_COLLECTION", "Exception during insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return insertedId;
    }

    // ==================================================================================
    // 4PM FOOD NOTES PROCESSING METHODS
    // ==================================================================================

    /**
     * Fetch all food notes from the previous calendar day that have NOT yet been
     * through 4PM processing (is_4pm_processed = 0).
     *
     * @return List of FoodNoteProcessingItem objects ready for AI enrichment.
     */
    public List<FoodNoteProcessingItem> getPreviousDayFoodNotes() {
        // Business rule: process notes from yesterday (previous calendar day) and today,
        // covering the 4PM-to-4PM window. Both date prefixes are queried so that notes
        // added yesterday are not excluded.
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String todayPrefix = sdf.format(new java.util.Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
        String yesterdayPrefix = sdf.format(cal.getTime());

        android.util.Log.d("4PM_DB", "Fetching notes for 4PM window: yesterday=" + yesterdayPrefix + ", today=" + todayPrefix);

        List<FoodNoteProcessingItem> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_QUICK_FOOD_NOTE_ID + ", "
                            + COLUMN_QUICK_FOOD_NOTE_DATE + ", "
                            + COLUMN_QUICK_FOOD_NOTE_FOOD + ", "
                            + COLUMN_QUICK_FOOD_NOTE_CALORIES + ", "
                            + COLUMN_QUICK_FOOD_NOTE_QUANTITY
                            + " FROM " + TABLE_QUICK_FOOD_NOTE
                            + " WHERE (" + COLUMN_QUICK_FOOD_NOTE_DATE + " LIKE ?"
                            + " OR " + COLUMN_QUICK_FOOD_NOTE_DATE + " LIKE ?)"
                            + " AND COALESCE(" + COLUMN_IS_4PM_PROCESSED + ", 0) = 0",
                    new String[]{yesterdayPrefix + "%", todayPrefix + "%"}
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUICK_FOOD_NOTE_ID));
                    String noteDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUICK_FOOD_NOTE_DATE));
                    String food = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUICK_FOOD_NOTE_FOOD));
                    String calories = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUICK_FOOD_NOTE_CALORIES));
                    String quantity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUICK_FOOD_NOTE_QUANTITY));
                    notes.add(new FoodNoteProcessingItem(noteId, noteDate, food, calories, quantity));
                } while (cursor.moveToNext());
                cursor.close();
            }
            android.util.Log.d("4PM_DB", "Found " + notes.size() + " notes for 4PM processing (yesterday + today)");
        } catch (Exception e) {
            android.util.Log.e("4PM_DB", "Error fetching previous day notes: " + e.getMessage());
            e.printStackTrace();
        }

        return notes;
    }

    /**
     * Persist the confirmed 4PM processing results for a single food note.
     * Call this only after the user confirms the review dialog.
     *
     * @param noteId          The note_id of the food note to update.
     * @param finalCalories   The confirmed final calorie value.
     * @param finalPoints     The confirmed final points value.
     * @param aiUpdatedFields Comma-separated names of fields AI filled (e.g. "calories"), or empty.
     */
    public void update4PMNote(int noteId, int finalCalories, int finalPoints, String aiUpdatedFields) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_4PM_PROCESSED, 1);
            values.put(COLUMN_PROCESSED_DATE,
                    new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date()));
            values.put(COLUMN_AI_UPDATED_FIELDS, aiUpdatedFields != null ? aiUpdatedFields : "");
            values.put(COLUMN_FINAL_CALORIES, finalCalories);
            values.put(COLUMN_FINAL_POINTS, finalPoints);

            int rows = db.update(TABLE_QUICK_FOOD_NOTE, values,
                    COLUMN_QUICK_FOOD_NOTE_ID + " = ?",
                    new String[]{String.valueOf(noteId)});

            android.util.Log.d("4PM_DB", "update4PMNote: noteId=" + noteId
                    + ", cal=" + finalCalories + ", pts=" + finalPoints
                    + ", rows=" + rows);
        } catch (Exception e) {
            android.util.Log.e("4PM_DB", "Error in update4PMNote for id=" + noteId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Check whether 4PM processing has already been completed for the given date string.
     * Used as an idempotent guard to prevent duplicate balance updates.
     *
     * @param dateStr Date in "dd-MM-yyyy" format.
     * @return true if a log entry exists for this date.
     */
    public boolean isAlreadyProcessedForDate(String dateStr) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_LOG_ID + " FROM " + TABLE_FOURPM_LOG
                            + " WHERE " + COLUMN_LOG_PROCESSING_DATE + " = ?",
                    new String[]{dateStr}
            );
            boolean exists = (cursor != null && cursor.getCount() > 0);
            if (cursor != null) cursor.close();
            android.util.Log.d("4PM_DB", "isAlreadyProcessedForDate(" + dateStr + ") = " + exists);
            return exists;
        } catch (Exception e) {
            android.util.Log.e("4PM_DB", "Error checking processed date: " + e.getMessage());
            return false;
        }
    }

    /**
     * Insert a 4PM processing log entry.
     * Uses CONFLICT_IGNORE so a duplicate date silently returns -1.
     *
     * @param dateStr      Date key in "dd-MM-yyyy" format.
     * @param totalCalories Total calories across all processed notes.
     * @param totalPoints   Total points across all processed notes.
     * @param noteIdsJson   JSON array string of processed note IDs, e.g. "[1,2,3]".
     * @return Inserted row ID, or -1 if duplicate / error.
     */
    public long insert4PMProcessingResult(String dateStr, int totalCalories, int totalPoints,
                                           String noteIdsJson) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LOG_PROCESSING_DATE, dateStr);
            values.put(COLUMN_LOG_TOTAL_CALORIES, totalCalories);
            values.put(COLUMN_LOG_TOTAL_POINTS, totalPoints);
            values.put(COLUMN_LOG_NOTE_IDS_JSON, noteIdsJson != null ? noteIdsJson : "[]");
            values.put(COLUMN_LOG_CREATED_AT,
                    new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date()));

            id = db.insertWithOnConflict(TABLE_FOURPM_LOG, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
            android.util.Log.d("4PM_DB", "insert4PMProcessingResult: date=" + dateStr
                    + ", cal=" + totalCalories + ", id=" + id);
        } catch (Exception e) {
            android.util.Log.e("4PM_DB", "Error inserting 4PM log: " + e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    // ==================================================================================
    // 4PM DEBIT / STEPS CHALLENGE — DB ACCESS LAYER  (added in v8)
    // ==================================================================================

    /**
     * Persist a {@link FourPMDebitResult} to the {@code fourpm_debit_challenge} table.
     *
     * <p>Uses {@code CONFLICT_IGNORE} so a duplicate date silently returns {@code -1}
     * instead of throwing — identical behaviour to {@link #insert4PMProcessingResult}.
     * The caller should check for {@code -1} and warn if needed, but never crash.</p>
     *
     * @param result The fully calculated challenge result (never null).
     * @return Inserted row ID, or {@code -1} on duplicate date or any error.
     */
    public long saveDebitStepsChallenge(FourPMDebitResult result) {
        if (result == null) {
            android.util.Log.e("4PM_DEBIT_DB", "saveDebitStepsChallenge: result is null — skipping");
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DEBIT_DATE,            result.processingDate);
            values.put(COLUMN_DEBIT_FOOD_CALORIES,   result.totalFoodCalories);
            values.put(COLUMN_DEBIT_DAILY_BUDGET,    result.dailyBudget);
            values.put(COLUMN_DEBIT_BALANCE,         result.currentBalance);
            values.put(COLUMN_DEBIT_KITTY_EXCESS,    result.kittyExcess);
            values.put(COLUMN_DEBIT_BALANCE_PENALTY, result.balancePenalty);
            values.put(COLUMN_DEBIT_EXCESS_CALORIES, result.excessCalories);
            values.put(COLUMN_DEBIT_STEP_CHALLENGE,  result.stepChallenge);
            values.put(COLUMN_DEBIT_IS_CAPPED,       result.isCapped ? 1 : 0);
            values.put(COLUMN_DEBIT_CREATED_AT,
                    new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                            .format(new Date()));

            id = db.insertWithOnConflict(TABLE_FOURPM_DEBIT, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);

            android.util.Log.d("4PM_DEBIT_DB", "saveDebitStepsChallenge: date=" + result.processingDate
                    + ", steps=" + result.stepChallenge + ", id=" + id);
        } catch (Exception e) {
            android.util.Log.e("4PM_DEBIT_DB", "Error saving debit challenge: " + e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Check whether a Debit / Steps Challenge has already been saved for the given date.
     *
     * <p>Used as an idempotent guard — prevents regenerating and re-showing the challenge
     * if the user taps the 4PM button again after confirming.</p>
     *
     * @param dateStr Date in {@code dd-MM-yyyy} format.
     * @return {@code true} if a row with that date already exists in the table.
     */
    public boolean isDebitChallengeProcessedForDate(String dateStr) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_DEBIT_ID + " FROM " + TABLE_FOURPM_DEBIT
                            + " WHERE " + COLUMN_DEBIT_DATE + " = ?",
                    new String[]{dateStr});
            boolean exists = (cursor != null && cursor.getCount() > 0);
            if (cursor != null) cursor.close();
            android.util.Log.d("4PM_DEBIT_DB", "isDebitChallengeProcessedForDate("
                    + dateStr + ") = " + exists);
            return exists;
        } catch (Exception e) {
            android.util.Log.e("4PM_DEBIT_DB",
                    "Error checking debit challenge date: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve the most recently saved Debit / Steps Challenge, or {@code null} if none exists.
     *
     * <p>Used to display the saved challenge when the user opens the 4PM screen after the
     * workflow has already run for today.</p>
     *
     * @return The latest {@link FourPMDebitResult}, or {@code null} if the table is empty.
     */
    public FourPMDebitResult getLastDebitChallenge() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_FOURPM_DEBIT
                            + " ORDER BY " + COLUMN_DEBIT_ID + " DESC LIMIT 1",
                    null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) cursor.close();
                return null;
            }

            FourPMDebitResult result = new FourPMDebitResult(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_FOOD_CALORIES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_DAILY_BUDGET)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_BALANCE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_KITTY_EXCESS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_BALANCE_PENALTY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_EXCESS_CALORIES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_STEP_CHALLENGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_IS_CAPPED)) == 1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEBIT_STEP_CHALLENGE)) > 0
            );
            cursor.close();
            android.util.Log.d("4PM_DEBIT_DB", "getLastDebitChallenge → " + result);
            return result;
        } catch (Exception e) {
            android.util.Log.e("4PM_DEBIT_DB", "Error reading last debit challenge: " + e.getMessage());
            return null;
        }
    }

    // ── Steps Challenge Log (v13) ─────────────────────────────────────────────

    /**
     * Persist a Steps Challenge calculation result to {@code steps_challenge_log}.
     * Uses CONFLICT_IGNORE so a duplicate date is silently skipped.
     *
     * @return Inserted row ID, or -1 on duplicate or error.
     */
    public long saveStepsChallenge(String date, int currentBalance, int previousDayEnd,
                                   int dayEndTarget, int bmr, int stepChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SC_DATE, date);
            values.put(COLUMN_SC_CURRENT_BALANCE, currentBalance);
            values.put(COLUMN_SC_PREV_DAY_END, previousDayEnd);
            values.put(COLUMN_SC_DAY_END_TARGET, dayEndTarget);
            values.put(COLUMN_SC_BMR, bmr);
            values.put(COLUMN_SC_STEP_CHALLENGE, stepChallenge);
            values.put(COLUMN_SC_CREATED_AT,
                    new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                            .format(new Date()));
            id = db.insertWithOnConflict(TABLE_STEPS_CHALLENGE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
            android.util.Log.d("STEPS_CHALLENGE_DB", "saveStepsChallenge: date=" + date
                    + ", steps=" + stepChallenge + ", id=" + id);
        } catch (Exception e) {
            android.util.Log.e("STEPS_CHALLENGE_DB", "Error saving steps challenge: " + e.getMessage());
        }
        return id;
    }

    /**
     * Returns true if a Steps Challenge has already been saved for the given date.
     *
     * @param dateStr Date in "dd-MM-yyyy" format.
     */
    public boolean isStepsChallengeCalculatedForDate(String dateStr) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_SC_ID + " FROM " + TABLE_STEPS_CHALLENGE
                            + " WHERE " + COLUMN_SC_DATE + " = ?",
                    new String[]{dateStr});
            boolean exists = (cursor != null && cursor.getCount() > 0);
            if (cursor != null) cursor.close();
            android.util.Log.d("STEPS_CHALLENGE_DB", "isStepsChallengeCalculatedForDate("
                    + dateStr + ") = " + exists);
            return exists;
        } catch (Exception e) {
            android.util.Log.e("STEPS_CHALLENGE_DB",
                    "Error checking steps challenge date: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the saved step challenge count for the given date, or 0 if not found.
     *
     * @param dateStr Date in "dd-MM-yyyy" format.
     */
    public int getSavedStepsChallenge(String dateStr) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " + COLUMN_SC_STEP_CHALLENGE + " FROM " + TABLE_STEPS_CHALLENGE
                            + " WHERE " + COLUMN_SC_DATE + " = ?",
                    new String[]{dateStr});
            if (cursor != null && cursor.moveToFirst()) {
                int steps = cursor.getInt(0);
                cursor.close();
                android.util.Log.d("STEPS_CHALLENGE_DB", "getSavedStepsChallenge(" + dateStr
                        + ") = " + steps);
                return steps;
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            android.util.Log.e("STEPS_CHALLENGE_DB",
                    "Error getting saved steps challenge: " + e.getMessage());
        }
        return 0;
    }
}


