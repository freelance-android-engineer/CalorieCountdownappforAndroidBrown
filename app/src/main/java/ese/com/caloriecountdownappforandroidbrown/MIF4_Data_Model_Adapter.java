package ese.com.caloriecountdownappforandroidbrown;


/**
 * Created by ESE on 07/10/2015.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;



import androidx.fragment.app.FragmentActivity;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class MIF4_Data_Model_Adapter extends FragmentActivity
{
// Provides for the rest of the app interface and methods for querying, insert and manage data in database, it is the Getway to Data Model Worlk/Line.
    private Food_Item_CIF4 mDama_food_item_cif4;
    private Context context;
    private Transaction transaction;
    private Meal_Type x;

    public MIF4_Data_Model_Adapter(Food_Item_CIF4 fetchboobofetch, Context cont)
    {
        mDama_food_item_cif4 = fetchboobofetch;
        context = cont;
    }

    public MIF4_Data_Model_Adapter(Context c)
    {
        context = c;
    }


    public java.util.ArrayList<Food_Item_CIF4> FetchMatches()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        java.util.ArrayList<Food_Item_CIF4> jackmatch = jackie.QueryForMatches(mDama_food_item_cif4.Get_food_item_name());
        return jackmatch;
    }


    public String RetrieveBalance()
    {
       SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetLatestBalance();

    }

    public int RetriveDayEnd()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetDayEndBalance();

    }


    public long  StoreBalance(String cBalance)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.PostLatestBalance(cBalance);
    }

    public long StoreDayEndBalance(int dyendBal)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.PostDayend(dyendBal);
    }


    public void setSex(boolean maletrue)
    {
        if(maletrue)
        {
            SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
            jackie.PostSex("MALE");
        }
        else
        {
            SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
            jackie.PostSex("FEMALE");
        }
    }

    public String getSex()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetSex();
    }

    public String RetrieveBreakfastTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetBreakfastTime();

    }
    public String RetrieveLunchTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetLunchTime();

    }
    public String RetrieveFinalMealTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetFinalMealTime();
    }
    public String RetrieveCurrentWeight()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetCurrentWeight();
    }
    public String RetrieveTargetWeight()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetTargetWeight();
    }

    public String RetrieveReminder()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetReminder();
    }
    public java.util.Date RetrieveStartDate()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        String date = jackie.GetStartDate();
        return new java.util.Date(new RoundingCIF13().StringToInt(date));
    }
    public String RetrieveStartCountdown()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.GetStartCountdown();
    }

    public void StoreStartWeightLoss(HealthProfileCiF3 IN)
    {
        //ENGLISH ~> IDO (New Menu/Submenuitem from Functional Model?)~> Algorithm Engineering (Building Blocks&)~> Android ~>(Phone)*~>(re)Load ~> www.ese-edet.eu End&Repeat, (apk Dev Console progress Bar on Track!)(Remember Weekly Friday Lamppost for www) (Read same thing same spot for revolve 2 happen.)Repeat Constantly on Track till <*August 16,16 Fast Track> Opening Soon exe  www.ee-edet.eu (re)Load -> www.ese-edet.eu.
        // *Test Menu/Sub Menuitem for (Green) (CFF and Value QVM). up and down train 2 www fronts beleive I can do 100 by <> not going to change in fut
        //repeat till final reload, reload ONLY Menuitems and SubMenuitems ONLY ESE Software Pipeline. exe 1,2,3 Fast
        //Fragment stop and read former work on this, compare do not reinvent the Wheel. You write to exe that's all
        //exe (hot) to Build Software so Say. REad.
        //
        //REX

        //Takes in CIF3 Create/Post Values related to it to relevant tables Copy and Breakdown

        //All table include : (now compare with Rex already written note : Well done. Start as you Finish (red connection [loop]

        //Calendar* notes can be found in Spreadsheet (exe)

        //Other Table to Create in SQL Lite:
        //BreakfastTime Table
        //LunchTime Table
        //FinalMealTime Table -> NewDay DayEnd /Balance Cfwd to DayEnd new Calendar Day. Closer to Zero date. accounting Linguo Terms and Jargon Only.
        //CurrentWeight Table (Close Well no  leakage now Black fix) ~> work android ~> www.ese-edet.eu
        //TargetWeight Table
        //StartWeight Table
        //DayEnd Countdown /last session
        //Exercise Time Table
        //dayend countdown balances dashboard chart //resuse blackberry technology with improved or external charts, we are simply posting the dayend values of countdown at 9pm to this table one day at a time stack style.
        // All Sub Fragment Menuitems in their activity
        //Male or Female.
        //Number of Days
        //Reminder State Table
        //Start Calorie Balance Table
        //Start Day Table
        //add date field to last balance
        //Update table
        //now store stuff ok including data in database key of app let's go.

        //Now copy and repeat procedure rex to Food item databse etc. exe

        //Well done and Congratualtions for (re)Loading to www on August 16,16 but remember (re)Loading Never Stops, ESE S.C.I LTD -Company.

        //me : receive insert and repeat trailer follow and see earlier now you : Read : Not Bad good calabo and revolve, same spot, eye of the storm or tonado we all talk to each other throgh same writings laptop screen of iPhone something we'll all have..

        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.CreateAccountTables(IN);
        jackie.PostValuesToTables(IN);
        jackie.close();

    }

    public void StoreTargetWeightLossPounds(String InputWeight)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostValueToTargetWeightTable(InputWeight);
        jackie.close();
    }

    public java.util.Date getBreakfastTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        CalenderHelperClass helpermagic = new CalenderHelperClass(jackie.GetBreakfastTime());
        return helpermagic.DateText2Date();

    }

    public java.util.Date getLunchTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        CalenderHelperClass helpermagic = new CalenderHelperClass(jackie.GetLunchTime());
        return helpermagic.DateText2Date();
    }

    public java.util.Date getDinnerTime()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        CalenderHelperClass helpermagic = new CalenderHelperClass(jackie.GetFinalMealTime());
        return helpermagic.DateText2Date();
    }

    public void putBreakfastTime(java.util.Date BreakfastTime)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostBreakfastTime(BreakfastTime);
    }

    public void putLunchTime(java.util.Date LunchTime)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostLunchTime(LunchTime);
    }

    public void putDinnerTime(java.util.Date DinnerTime)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostDinnerTime(DinnerTime);
    }

    public long InsertDummyRice(String Category, String food_item_name, float grams_per_serving_portion, float calories_per_serving_portion, float fat_per_serving_portion, float carbohydrates_per_serving, float protein_per_serving)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.InsertDummyRice(Category,food_item_name,grams_per_serving_portion,calories_per_serving_portion,grams_per_serving_portion,carbohydrates_per_serving,protein_per_serving);
    }

    public void InsertBreakfastTransaction(Breakfast_Box_CIF17 bt)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostBreakfastTransaction(bt);
    }

    public void InsertLunchTransaction(Lunch_Box_CIF17 bt)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostLunchTransaction(bt);
    }

    public void InsertDinnerTransaction(Dinner_Box_CIF17 bt)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostDinnerTransaction(bt);
    }

    public Breakfast_Transaction_CIF22 RetrieveBreakfastTransaction(java.util.Date startPeriod, java.util.Date endPeriod)
    {
        //Function is an Oxymoron, actualy yreturnqing tracnsactions for ALL Meal types.
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Breakfast_Transaction_CIF22 gotten = jackie.Get_BreakfastTransaction_Table(startPeriod,endPeriod);
        return gotten;

    }

    public Lunch_Transaction_CIF20 InsertLunchTransaction(java.util.Date startPeriod, java.util.Date endPeriod)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Lunch_Transaction_CIF20 gotten = jackie.Get_LunchTransaction_Table(startPeriod,endPeriod);
        return gotten;

    }

    public Dinner_Transaction_CIF21 InsertDinnerTransaction(java.util.Date startPeriod, java.util.Date endPeriod)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Dinner_Transaction_CIF21 gotten = jackie.Get_DinnerTransaction_Table(startPeriod,endPeriod);
        return gotten;

    }

    public void InsertBreakfastTransaction(Breakfast_Transaction_CIF22 bt)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.Insert_BreakfastTransactionTable(bt.Get_BreakfastBox().get(0));
    }

    public void InsertLunchTransaction(Lunch_Transaction_CIF20 lt)
    {
        InsertLunchTransaction(lt.Get_LunchBox());
    }

    public void InsertDinnerTransaction(Dinner_Transaction_CIF21 dt)
    {
        InsertDinnerTransaction(dt.Get_DinnerBox());
    }

    public Breakfast_Transaction_CIF22  RetrieveBreakfastTransactionsData(java.util.Date Start, java.util.Date End)
    {
        //Function is an Oxymoron, actually returning tracnsactions for ALL Meal types.
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Breakfast_Transaction_CIF22 BTransactions4MonthlyStatements = jackie.Retrieve_BreakfastTransactionTable();

        return BTransactions4MonthlyStatements; // in right chronological Order.

        //IDO fragment Rubix, Levels, individual square Boxes connected by a circular red for Methods onther boxs or objectsion fit right in correcty like Jigsaw Raw Pieces.
    }

    public Transactions_CIF22 get_Transactions(java.util.Date Start, java.util.Date End)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Transactions_CIF22 transaction_cif52s = jackie.Get_Transaction_Table(Start,End);
        return transaction_cif52s;
    }

    public void SQLDatabase_Populate()
    //filepath or file poath I/O Object Builder BEFAM efficiency
    {

    }

    public ArrayList<Meal_Items_Cursor> Get_Meal_Box_Transactions(String Transaction_ID)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        ArrayList<Meal_Items_Cursor> macho = jackie.Get_Meal_Box_Table(Transaction_ID);
        return macho;
    }

    public void Store_Reminder_Times(int INB, int INL, int IND)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.PostBtime(INB);
        jackie.PostLtime(INL);
        jackie.PostDtime(IND);

    }

    public int Retrieve_Btime(Context c)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(c);
        return jackie.GetBtime();


    }

    public int Retrieve_Ltime(Context c)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(c);
        return jackie.GetLtime();


    }

    public int Retrieve_Dtime(Context c)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(c);
        return jackie.GetDtime();



    }


    public Transactions_CIF22 DummyCIF22Returned()
    {
        int transaction_id = 12345;
        long datum = 23435323;
        String mealtype = "Breakfast";
        int meal_type_id = 1;
        int amount = 70;
        int balance = 1000;
        int meal_box_id = 1500;
        String meal_box_food_type = "Breakfast";
        BoxCIF17 food_item_list_box = new BoxCIF17();
        String describe = "Breakfast items";
        int caloriein = 545;
        int calorieout = 565;
        int bal = 77000;

        int transaction_id2 = 5468;
        long datum2 = 544534545;
        String mealtype2 = "Lunch";
        int meal_type_id2 = 2;
        int amount2 = 75;
        int balance2 = 1500;
        int meal_box_id2 = 1000;
        String meal_box_food_type2 = "Lunch";
        BoxCIF17 food_item_list_box2 = new BoxCIF17();
        String describe2 = "Luncg items";
        int caloriein2 = 773;
        int calorieout2 = 774;
        int bal2 = 76000;

        int transaction_id3 = 73565;
        long datum3 = 54322266;
        String mealtype3 = "Dinner";
        int meal_type_id3 = 3;
        int amount3 = 100;
        int balance3 = 1300;
        int meal_box_id3 = 1600;
        String meal_box_food_type3 = "Dinner";
        BoxCIF17 food_item_list_box3 = new BoxCIF17();
        String describe3 = "Lunch items";
        int caloriein3 = 500;
        int calorieout3 = 800;
        int bal3 = 75700;


        Transactions_CIF22 dummy = new Transactions_CIF22();

        Transaction_Line_CIF17 transaction_line_cif17 = new Transaction_Line_CIF17();
        Transaction_Line_CIF17 transaction_line_cif172 = new Transaction_Line_CIF17();
        Transaction_Line_CIF17 transaction_line_cif173 = new Transaction_Line_CIF17();

        transaction_line_cif17.Set_Transaction_ID(transaction_id);
        transaction_line_cif17.Set_Transaction_DateX(datum);
        transaction_line_cif17.Set_Transaction_Meal_Type(mealtype);
        transaction_line_cif17.Set_Transaction_Meal_Type_ID(meal_type_id);
        transaction_line_cif17.Set_Transaction_Amount(amount);
        transaction_line_cif17.Set_Transaction_Balance(balance);
        transaction_line_cif17.Set_Transaction_Meal_Box_ID(meal_box_id);
        transaction_line_cif17.Set_Transaction_Meal_Box_Food_Type(meal_box_food_type);
        transaction_line_cif17.Set_Transaction_Food_Items(food_item_list_box);
        transaction_line_cif17.Set_Calorie_IN(caloriein);
        transaction_line_cif17.Set_Calorie_Out(calorieout);
        transaction_line_cif17.Set_Transaction_Balance(bal);

        transaction_line_cif172.Set_Transaction_ID(transaction_id2);
        transaction_line_cif172.Set_Transaction_DateX(datum2);
        transaction_line_cif172.Set_Transaction_Meal_Type(mealtype2);
        transaction_line_cif172.Set_Transaction_Meal_Type_ID(meal_type_id2);
        transaction_line_cif172.Set_Transaction_Amount(amount2);
        transaction_line_cif172.Set_Transaction_Balance(balance2);
        transaction_line_cif172.Set_Transaction_Meal_Box_ID(meal_box_id2);
        transaction_line_cif172.Set_Transaction_Meal_Box_Food_Type(meal_box_food_type2);
        transaction_line_cif172.Set_Transaction_Food_Items(food_item_list_box2);
        transaction_line_cif172.Set_Calorie_IN(caloriein2);
        transaction_line_cif172.Set_Calorie_Out(calorieout2);
        transaction_line_cif172.Set_Transaction_Balance(bal2);

        transaction_line_cif173.Set_Transaction_ID(transaction_id3);
        transaction_line_cif173.Set_Transaction_DateX(datum3);
        transaction_line_cif173.Set_Transaction_Meal_Type(mealtype3);
        transaction_line_cif173.Set_Transaction_Meal_Type_ID(meal_type_id3);
        transaction_line_cif173.Set_Transaction_Amount(amount3);
        transaction_line_cif173.Set_Transaction_Balance(balance3);
        transaction_line_cif173.Set_Transaction_Meal_Box_ID(meal_box_id3);
        transaction_line_cif173.Set_Transaction_Meal_Box_Food_Type(meal_box_food_type3);
        transaction_line_cif173.Set_Transaction_Food_Items(food_item_list_box3);
        transaction_line_cif173.Set_Calorie_IN(caloriein3);
        transaction_line_cif173.Set_Calorie_Out(calorieout3);
        transaction_line_cif173.Set_Transaction_Balance(bal3);
        return dummy;
    }

    public Food_Item_CIF4 AddRawRecord(String record)
    {
        System.out.println("In Raw Record to itemcif, this is raw record: ");
        System.out.println(record);

        String field;
        Food_Item_CIF4 itemcif = new Food_Item_CIF4();

        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_category(field);
        record = Trim(record,",");

        System.out.println("This is trimed record1:");
        System.out.println(record);

        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_food_item_name(field);
        record = Trim(record,",");

        System.out.println("This is trimed record2:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_grams_per_serving_portion(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("THIS IS CRITICAL FLOAT VALUE grams" + StringToFloat(field));

        System.out.println("This is trimed record3:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_calories_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("THIS IS CRITICAL FLOAT VALUE calories " + StringToFloat(field));

        System.out.println("This is trimed record4:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_fat_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record5:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_saturated_fat(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record6:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_trans_fat(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record7:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_protein_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record8:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_carbs_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record9:");
        System.out.println(record);



        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_sugar_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record10:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_salt_per_100g(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record11:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_wellbeing_index(StringToBool(field));
        record = Trim(record,",");

        System.out.println("This is trimed record12:");
        System.out.println(record);


        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }


        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_fiber(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record13:");
        System.out.println(record);

        if((field = FieldTokenizer(record, ",")) == null)
        {
            System.out.println("This is Field");
            System.out.println(field);
            InsertFood_Item_CIF4(itemcif);
            return itemcif;
        }

        System.out.println("This is Field");
        System.out.println(field);

        itemcif.Set_price_sterling(StringToFloat(field));
        record = Trim(record,",");

        System.out.println("This is trimed record14:");
        System.out.println(record);
        InsertFood_Item_CIF4(itemcif);
        return itemcif;
    }


    public void InsertFood_Item_CIF4(Food_Item_CIF4 item_cif4)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.Insert_Food_Item_CIF4(item_cif4);
    }

    public boolean Delete_Food_Items_Data_Table()
    {
        return false;
    }

    public void Insert_Transaction_CIF17(Transaction_Line_CIF17 IN)
    {
        //DOCUMENTATION
        //Algorithm Engineering -> ANDROID
        //General Description : Split fragment BOXCIF17 into
        // Step a) Transaction with Transaction ID and MealType insert into correct Table
        transaction = new Transaction();
        transaction.Set_Transaction_ID(new RoundingCIF13().LongToString(IN.Get_Transaction_ID()));
        transaction.Set_Transaction_Amount(new RoundingCIF13().IntToString(IN.Get_Transaction_Amount()));
        transaction.Set_Transaction_Balance(new RoundingCIF13().IntToString(IN.Get_Transaction_Balance()));
        transaction.Set_Transaction_Date(new Date().toString());
        transaction.Set_Transaction_Meal_Type(IN.Get_Transaction_Meal_Type());
        transaction.Set_Transaction_Meal_Type_ID(new RoundingCIF13().LongToString(IN.Get_Transaction_Meal_Type_ID()));

        ArrayList<Food_Item_CIF4> food_item_list = IN.Get_Transaction_Food_Items().Get_Food_Items();
        ArrayList<Meal_Type> meal_types = Transpose_Box_2_Meal_Type(food_item_list);

        // b) Meal Type linked with Transaction via Transaction_ID and Meal of the Day Type, insert into right table.
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        jackie.Update_Transactions_Table(transaction);
        jackie.Update_Meal_Box_Table(x);

        // ESE Algorithm Engineering Documentation ->
        // "Pass BoxBOXCIFWALLBoundary to Transaction Table Function plus something else, transaction_table function first creates transaction Row
        // then it creates Mealtype row by giving it it's ID in relevant Column a row for each Food_item in BoxBOXCIFWALL Boundary, Give unique
        // meal type id but same transaction id as the creating transaction, get this transaction id from returned int value on creating xor
        // inserting Transaction ID row.

        // c) Create a Reverse Function (using Date Range for Transaction and linked Meal_Type into BoxCIF
        // //and/or Transaction ID and/or Meal Type and your Done.

        // If Range of transaction CIF 17 or Single but in CIF52 x or CIF22 OUT

        // Replace with Fake CIF 22 Data real test

        // *First data put in Data Model Get CIF22

        // * then Data directly from Update Credit Balance Food_Diary Get to Data [Model] via SQLCIFBAOX6

        // *Return CIF22 from get data range

        // * Build & Test -> Click -> menuitem -> Value (re)load -> ese-edet.eu ->Pipeline ESEProduce Monthly Statement / Food_Journal & Work! (re)load -> ese-edet.eu/ . Version 3 Black/Green Interface Green Office reload exe Spreadsheet to 0 reconcillation Sheet & ese-edet.eu

    }

    private String FieldTokenizer(String stream, String token)
    {
        // find index location of first token
        // use that index location to return substring up to that point


        int index = stream.indexOf(token);

        if (index == -1)
        {
            return null;
        }
        else
        {
            String substring = stream.substring(0, index);
            return substring;
        }

    }


    private String Trim(String tream, String oken)
    {
        //find index location of first token
        //use that index location trim by cutting off all chars infront of it and return what's left

        int index = tream.indexOf(oken);

        if(index == -1)
        {
            return tream;
        }

        else
        {
            tream  = tream.substring((index+1), tream.length());
            return tream;
        }
    }

    private float StringToFloat(String convert)
    {
        float answer = 0;
        try
        {
            Float boat = new Float(0);
            answer = boat.parseFloat(convert);
            return answer;
        }
        catch(Exception dx)
        {
            System.out.println("Error in In House Data, wrong data type given in Ingredient Category");
            return answer;
        }
    }

    private ArrayList<Meal_Type> Transpose_Box_2_Meal_Type(ArrayList<Food_Item_CIF4> IN)
    {

        ArrayList<Meal_Type> meal_type_list = new ArrayList<>();
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);

        for(Food_Item_CIF4 m : IN)
        {
            x = new Meal_Type();

            x.Set_MEAL_BOX_ID(transaction.Get_Transaction_Meal_Type_ID());//jackie.Get_Unique_Meal_ID()) ;
            x.Set_MEAL_BOX_MEAL_TYPE(transaction.Get_Transaction_Meal_Type()) ;
            x.Set_MEAL_BOX_MEAL_TYPE_ID(transaction.Get_Transaction_ID());//(jackie.Get_Unique_Meal_Type_ID());
            x.Set_MEAL_BOX_FOOD_TYPE(m.Get_food_type());
            x.Set_MEAL_BOX_FOOD_ITEM_NAME(m.Get_food_item_name());
            try
            {
                x.Set_MEAL_BOX_GRAMS_PER_SERVING_PORTION(new RoundingCIF13().FloatToString(m.Get_grams_per_serving_portion()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_GRAMS_PER_SERVING_PORTION("1");
            }
            try
            {
                x.Set_MEAL_BOX_CALORIE_PER_100G(new RoundingCIF13().FloatToString(m.Get_calories_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_CALORIE_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_FAT_PER_100G(new RoundingCIF13().FloatToString(m.Get_fat_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_FAT_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_SATURATED_FAT(new RoundingCIF13().FloatToString(m.Get_saturated_fat()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_SATURATED_FAT("1");
            }
            try
            {
                x.Set_MEAL_BOX_TRANS_FAT(new RoundingCIF13().FloatToString(m.Get_trans_fat()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_TRANS_FAT("1");
            }
            try
            {
                x.Set_MEAL_BOX_PROTEIN_PER_100G(new RoundingCIF13().FloatToString(m.Get_protein_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_PROTEIN_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_CARBS_PER_100G(new RoundingCIF13().FloatToString(m.Get_carbs_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_CARBS_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_SUGAR_PER_100G(new RoundingCIF13().FloatToString(m.Get_sugar_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_SUGAR_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_SALT_PER_100G(new RoundingCIF13().FloatToString(m.Get_salt_per_100g()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_SALT_PER_100G("1");
            }
            try
            {
                x.Set_MEAL_BOX_WELLBEING_INDEX(new RoundingCIF13().BoolToString(m.Get_wellbeing_index()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_WELLBEING_INDEX("1");
            }
            try
            {
                x.Set_MEAL_BOX_FIBER(new RoundingCIF13().FloatToString(m.Get_fiber()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_FIBER("1");
            }
            try
            {
                x.Set_MEAL_BOX_PRICE_STERLING(new RoundingCIF13().FloatToString(m.Get_price_sterling()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_PRICE_STERLING("1");
            }
            try
            {
                x.Set_MEAL_BOX_POLYUNSATURATED(new RoundingCIF13().FloatToString(m.Get_polyunsaturated()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_POLYUNSATURATED("1");
            }
            try
            {
                x.Set_MEAL_BOX_MONOUNSATURATED(new RoundingCIF13().FloatToString(m.Get_monounsaturated()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_MONOUNSATURATED("1");
            }
            try
            {
                x.Set_MEAL_BOX_CHOLESTEROL_MG(new RoundingCIF13().FloatToString(m.Get_cholesterol_mg()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_CHOLESTEROL_MG("1");
            }
            try
            {
                x.Set_MEAL_BOX_SODIUM_MG(new RoundingCIF13().FloatToString(m.Get_sodium_mg()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_SODIUM_MG("1");
            }
            try
            {
                x.Set_MEAL_BOX_POTASSIUM_MG(new RoundingCIF13().FloatToString(m.Get_potassium_mg()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_POTASSIUM_MG("1");
            }
            try
            {
                x.Set_MEAL_BOX_VITAMIN_A_PERCENT(new RoundingCIF13().FloatToString(m.Get_vitamin_a_percent()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_VITAMIN_A_PERCENT("1");
            }
            try
            {
                x.Set_MEAL_BOX_VITAMIN_C_PERCENT(new RoundingCIF13().FloatToString(m.Get_vitamin_c_percent()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_VITAMIN_C_PERCENT("1");
            }
            try
            {
                x.Set_MEAL_BOX_CALCIUM_PERCENT(new RoundingCIF13().FloatToString(m.Get_calcium_percent()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_CALCIUM_PERCENT("1");
            }
            try
            {
                x.Set_MEAL_BOX_IRON_PERCENT(new RoundingCIF13().FloatToString(m.Get_iron_percent()));
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_IRON_PERCENT("1");
            }
            try
            {
                x.Set_MEAL_BOX_CATEGORY(m.Get_category());
            }
            catch(Exception e)
            {
                android.util.Log.d("Calorie Countdown app", "Something went wrong in casting " + e.getMessage());
                x.Set_MEAL_BOX_CATEGORY("1");
            }

            meal_type_list.add(x);
        }

        return meal_type_list;
    }







    private boolean StringToBool(String convert)
    {
        if(convert == "true" || convert == "True" || convert == "TRUE" || convert == "TRue" || convert == "TRUe" || convert == "Yes" || convert == "yes" || convert == "YEs")
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public void Record_Food_Journal(ArrayList<Food_Item_CIF4> INPUT)
    {
        Save_Monthly_Journal_Database(INPUT);
    }

    private void Save_To_Monthly_Report_Database()
    {
        //Do the Same_thing;
    }

    private long Save_Monthly_Journal_Transaction_CIF22(Transactions_CIF22 OUT)
    {

        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(this);
        long reserve = jackie.Insert_TransactionTable_x(OUT);
        return reserve;

    }

    private long Save_Monthly_Journal_Transaction_CIF22(JSONWrapperCIFClass OUT)
    {
        return 1;

    }

    private long Save_Monthly_Journal_Database(ArrayList<Food_Item_CIF4> OUT)
    {

        Transaction_CIF52 a = new Transaction_CIF52();
        Transactions_CIF22 b = new Transactions_CIF22();
        Transaction_Line_CIF17 c = new Transaction_Line_CIF17();


        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        Log.d("CCDGUI_CIF1.exe","Record Food Journal 4.");

        long Transaction_ID = jackie.Get_Unique_Row_Identifier_from_Transactions_Table();
        Log.d("RFJ 4a Transaction_ID ",new RoundingCIF13().IntToString((int) Transaction_ID));

        long Transaction_date = Get_Todays_Date_Long();
        Log.d("RFJ 4b Transaction_dat ",new RoundingCIF13().IntToString((int) Transaction_date));

        String Meal_type_x = Get_time_of_day_meal(new Date());
        Log.d("RFJ 4c meal type x ",Meal_type_x);

        int Meal_type_x_ID = jackie.Get_Unique_Row_Identifier_from_Transaction_Table();
        Log.d("RFJ 4d meal type x id ",new RoundingCIF13().IntToString(Meal_type_x_ID));

        int Amount = Sum_Food_Items_Calories(OUT);
        Log.d("RFJ 4e Amount ",new RoundingCIF13().IntToString(Amount));

        int Balance = See_Dialog_String_on_how_to_get_Balance();
        Log.d("RFJ 4f Balance ",new RoundingCIF13().IntToString(Balance));

        BoxCIF17 Meal_Transaction_Box = Transform_to_Box(OUT);
        Log.d("RFJ 4e MealTBox ",Meal_Transaction_Box.Print());

        String Description = Meal_type_x + " Transactions Food Journal items.";
        Log.d("RFJ 4g Description ",Description);

        int Joules_OUT = 0;
        int Joules_IN = Amount;
        int Bal_balance = See_Dialog_String_on_how_to_get_Balance_bfwd(Amount, Balance);
        Log.d("RFJ 4f Bal_balance ",new RoundingCIF13().IntToString(Bal_balance));

        c.Set_Transaction_Food_Items(Meal_Transaction_Box);
        c.Set_Calorie_IN(Amount);
        c.Set_Food_Item_List(OUT);
        c.Set_New_Balance(Bal_balance);
        c.Set_GeneralBox(Meal_Transaction_Box);
        c.Set_Transaction_DateX(Transaction_date);
        c.Set_Description(Description);
        c.Set_Calorie_Out(0);
        c.Set_Transaction_Meal_Type(Meal_type_x);
        c.Set_Calorie_Outwards(0);
        c.Set_Transaction_Amount(Amount);
        c.Set_Transaction_Balance(Balance);
        c.Set_Transaction_ID( Transaction_ID);
        c.Set_Transaction_Meal_Box_Food_Type(Meal_type_x);
        c.Set_Transaction_Meal_Box_ID(Meal_type_x_ID);
        c.Set_Transaction_Meal_Type(Meal_type_x);

        a.Set_Single_Transaction_Line(c);
        b.Add_TransactionLine_2_List(a);

        long reserve = jackie.Insert_TransactionTable_x(b);
        Log.d("RFJ 4h reserve ",new RoundingCIF13().IntToString((int) reserve));

        return reserve;


    }

    private long Get_Todays_Date_Long()
    {
        return new java.util.Date().getTime();
    }

    private java.util.Date Get_Todays_Date_Short()
    {
        return new java.util.Date();
    }

    private String Get_time_of_day_meal(java.util.Date IN)
    {
        if(isItBreakfast(IN))
        {
            return "Breakfast";
        }

        if(isItLunch(IN))
        {
            return "Lunch";
        }

        if(isItBrunch(IN))
        {
            return "Brunch";
        }

        if(isItDinner(IN))
        {
            return "Dinner";
        }

        else
        {
            return "Meal";
        }



    }

    private boolean isItBreakfast(java.util.Date IN)
    {


        if(inBetweenBreakfast(IN))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean isItLunch(java.util.Date IN)
    {
        if(inBetweenLunch(IN))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isItBrunch(java.util.Date IN)
    {
        if(inBetweenBrunch(IN))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean isItDinner(java.util.Date IN)
    {
        if(inBetweenDinner(IN))
        {
            return true;
        }
        else
        {
            return false;
        }

    }




    private boolean inBetweenBreakfast(java.util.Date IN)
    {
        java.util.Calendar greg = Calendar.getInstance();
        greg.setTime(new Date());
        greg.set(Calendar.HOUR_OF_DAY, 11);
        greg.set(Calendar.MINUTE, 30);

        long day_point = greg.getTimeInMillis();
        if(IN.getTime() < day_point)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean inBetweenLunch(java.util.Date IN)
    {
        java.util.Calendar greg_Start = Calendar.getInstance();
        greg_Start.setTime(new Date());
        greg_Start.set(Calendar.HOUR_OF_DAY, 11);
        greg_Start.set(Calendar.MINUTE, 30);

        java.util.Calendar greg_END = Calendar.getInstance();
        greg_END.setTime(new Date());
        greg_END.set(Calendar.HOUR_OF_DAY, 16);
        greg_END.set(Calendar.MINUTE, 0);

        long day_point_Start = greg_Start.getTimeInMillis();
        long day_point_END = greg_END.getTimeInMillis();

        if(IN.getTime() > day_point_Start || IN.getTime() < day_point_END)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean inBetweenDinner(java.util.Date IN)
    {
        java.util.Calendar greg_Start = Calendar.getInstance();
        greg_Start.setTime(new Date());
        greg_Start.set(Calendar.HOUR_OF_DAY, 16);
        greg_Start.set(Calendar.MINUTE, 0);

        java.util.Calendar greg_END = Calendar.getInstance();
        greg_END.setTime(new Date());
        greg_END.set(Calendar.HOUR_OF_DAY, 23);
        greg_END.set(Calendar.MINUTE, 59);

        long day_point_Start = greg_Start.getTimeInMillis();
        long day_point_END = greg_END.getTimeInMillis();

        if(IN.getTime() > day_point_Start || IN.getTime() < day_point_END)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean inBetweenBrunch(java.util.Date IN)
    {
        java.util.Calendar greg_Start = Calendar.getInstance();
        greg_Start.setTime(new Date());
        greg_Start.set(Calendar.HOUR_OF_DAY, 11);
        greg_Start.set(Calendar.MINUTE, 30);

        java.util.Calendar greg_END = Calendar.getInstance();
        greg_END.setTime(new Date());
        greg_END.set(Calendar.HOUR_OF_DAY, 12);
        greg_END.set(Calendar.MINUTE, 0);

        long day_point_Start = greg_Start.getTimeInMillis();
        long day_point_END = greg_END.getTimeInMillis();

        if(IN.getTime() > day_point_Start || IN.getTime() < day_point_END)
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    private int Sum_Food_Items_Calories(ArrayList<Food_Item_CIF4> IN)
    {
        //IDO DOCUMENTATION

        //Algorithm Engineering -> ANDROID (Builder) (Technology) (Tech) (tech) (Black) (ANDROID BLACKBOARD) (ESE PIPELINE) (ESE ANDROID BLACKBOARD) (Black Office) (Rigour Testing, Prefomance numbers and Optimization) Black (Builder) Reload > ese-edet.eu
        //Step One
        //
        int totaling = 1;

        if(IN == null)
        {

            return totaling;
        }
        else
        {
            for(Food_Item_CIF4 fooditem : IN)
            {

                totaling = totaling + fooditem.Get_calorie_value();
            }

            return totaling;
        }

    }

    private int See_Dialog_String_on_how_to_get_Balance()
    {
        //Summary_Box is a Singleton, get a Copy of the Singelton and get the Current Balance from it and add to this Object.
        SummaryBoxCIF12 summary = SummaryBoxCIF12.get (context);
        try
        {
            RoundingCIF13 roundup = new RoundingCIF13();
            return roundup.StringToInt(summary.Get_mCurrentBalance());
        }
        catch(Exception e)
        {
            return 0;
        }


    }

    private int See_Dialog_String_on_how_to_get_Balance_bfwd(int amount, int balance)
    {
        return (amount + balance);
    }

    private BoxCIF17 Transform_to_Box(ArrayList<Food_Item_CIF4> INPUTOBJECT)
    {
        BoxCIF17 OUTPUTOBJECT = new BoxCIF17();
        OUTPUTOBJECT.Set_Balance(0);
        OUTPUTOBJECT.Set_Balance(0);
        OUTPUTOBJECT.Set_Food_Items(INPUTOBJECT);
        OUTPUTOBJECT.add_Meal_Box_ID(0);
        OUTPUTOBJECT.Set_Meal_Type(Get_Meal_Type_of_the_Day());

        return OUTPUTOBJECT;

    }

    private String Get_Meal_Type_of_the_Day()
    {
        java.util.Date current_time = new java.util.Date();
        if(isItBreakfast(current_time))
        {
            return "Breakfast";
        }
        if(isItBrunch(current_time))
        {
            return "Brunch";
        }
        if(isItLunch(current_time))
        {
            return "Lunch";
        }
        if(isItDinner(current_time))
        {
            return "Dinner";
        }
        else
        {
            return "Breakfast";
        }
    }


    public class Transaction
{
    private String Transaction_ID;
    private String Transaction_Date;
    private String Transaction_Meal_Type;
    private String Transaction_Meal_Type_ID;
    private String Transaction_Amount;
    private String Transaction_Balance;
    private Meal_Type m_meal_type;

    public Transaction()
    {
        Transaction_ID = "";
        Transaction_Date = "";
        Transaction_Meal_Type = "";
        Transaction_Meal_Type_ID = "";
        Transaction_Amount = "";
        Transaction_Balance = "";
        m_meal_type = new Meal_Type();
    }

    public void Set_Transaction_ID(String IN)
    {
        Transaction_ID = IN;
    }

    public void Set_Transaction_Date(String IN)
    {
        Transaction_Date = IN;
    }

    public void Set_Transaction_Meal_Type(String IN)
    {
        Transaction_Meal_Type = IN;
    }

    public void Set_Transaction_Meal_Type_ID(String IN)
    {
        Transaction_Meal_Type_ID = IN;
    }

    public void Set_Transaction_Amount(String IN)
    {
        Transaction_Amount = IN;
    }

    public void Set_Transaction_Balance(String IN)
    {
        Transaction_Balance = IN;
    }

    public void Set_Meal_Type(Meal_Type IN) { m_meal_type = IN;}


    public Meal_Type Get_Meal_Type() { return m_meal_type; }

    public String Get_Transaction_ID()
    {
        return Transaction_ID;
    }

    public String Get_Transaction_Date()
    {
        return Transaction_Date;
    }

    public String Get_Transaction_Meal_Type()
    {
        return Transaction_Meal_Type;
    }

    public String Get_Transaction_Meal_Type_ID()
    {
        return Transaction_Meal_Type_ID;
    }

    public String Get_Transaction_Amount()
    {
        return Transaction_Amount;
    }

    public String Get_Transaction_Balance()
    {
        return Transaction_Balance;
    }



    public Transactions_CIF22 Reverse_Function(Date Start_Date, Date End_Range)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function2(int Meal_Type_ID)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function3(int Meal_Type_ID_Start, int Meal_Type_ID_End)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function4(int Transaction_ID)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function5(int Transaction_ID_Start, int Transaction_ID_End)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function6(int Transaction_ID, int Meal_Type_ID)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function7(int Transaction_ID_Start, int Meal_Type_ID_Start)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function8(int Transaction_ID_Start, int Meal_Type_ID_End)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function9(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_Start)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function0(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_END)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function11(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_Start, int Meal_Type_ID_END)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function12(int Transactio_ID_Start, int Meal_Type_ID_end, int Meal_Type_Start, int Transaction_ID_Start)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function13(int Transactio_ID_Start, int Meal_Type_ID_end, int Meal_Type_Start, int Transaction_ID_END)
    {
        return new Transactions_CIF22();
    }

    public Transactions_CIF22 Reverse_Function13a(int Transactio_ID_Start, int Meal_Type_ID_end)
    {
        return new Transactions_CIF22();
    }

    public Transaction_CIF52 Reverse_Function1(Date Start_Date, Date End_Range)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function12(int Meal_Type_ID)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function13(int Meal_Type_ID_Start, int Meal_Type_ID_End)
    {
        return new Transaction_CIF52();
    }

    public Transactions_CIF22 Reverse_Function14(int Transaction_ID)
    {
        return new Transactions_CIF22();
    }

    public Transaction_CIF52 Reverse_Function15(int Transaction_ID_Start, int Transaction_ID_End)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function16(int Transaction_ID, int Meal_Type_ID)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function17(int Transaction_ID_Start, int Meal_Type_ID_Start)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function18(int Transaction_ID_Start, int Meal_Type_ID_End)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function19(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_Start)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function10(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_END)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function111(int Transactio_ID_Start, int Transaction_ID_end, int Meal_Type_Start, int Meal_Type_ID_END)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function112(int Transactio_ID_Start, int Meal_Type_ID_end, int Meal_Type_Start, int Transaction_ID_Start)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function113(int Transactio_ID_Start, int Meal_Type_ID_end, int Meal_Type_Start, int Transaction_ID_END)
    {
        return new Transaction_CIF52();
    }

    public Transaction_CIF52 Reverse_Function113a(int Transactio_ID_Start, int Meal_Type_ID_end)
    {
        return new Transaction_CIF52();
    }

    public JSONWrapperCIFClass Wrap_in_JSON(Transactions_CIF22 IN)
    {
        return new JSONWrapperCIFClass(IN);
    }

    public Transaction_CIF22 UnWrap_JSON(JSONWrapperCIFClass IN)
    {
        return new Transaction_CIF22();
    }





}

public class Meal_Type
{


    //TABLE_MEAL_BOX_ITEMS_TABLE = "meal_box_items";
    private String MEAL_BOX_ID ;
    private String MEAL_BOX_MEAL_TYPE ;
    private String MEAL_BOX_MEAL_TYPE_ID ;
    private String MEAL_BOX_FOOD_TYPE ;
    private String MEAL_BOX_FOOD_ITEM_NAME;
    private String MEAL_BOX_GRAMS_PER_SERVING_PORTION ;
    private String MEAL_BOX_CALORIE_PER_100G ;
    private String MEAL_BOX_FAT_PER_100G ;
    private String MEAL_BOX_SATURATED_FAT ;
    private String MEAL_BOX_TRANS_FAT ;
    private String MEAL_BOX_PROTEIN_PER_100G ;
    private String MEAL_BOX_CARBS_PER_100G;
    private String MEAL_BOX_SUGAR_PER_100G ;
    private String MEAL_BOX_SALT_PER_100G ;
    private String MEAL_BOX_WELLBEING_INDEX;
    private String MEAL_BOX_FIBER;
    private String MEAL_BOX_PRICE_STERLING ;
    private String MEAL_BOX_POLYUNSATURATED;
    private String MEAL_BOX_MONOUNSATURATED ;
    private String MEAL_BOX_CHOLESTEROL_MG;
    private String MEAL_BOX_SODIUM_MG ;
    private String MEAL_BOX_POTASSIUM_MG;
    private String MEAL_BOX_VITAMIN_A_PERCENT ;
    private String MEAL_BOX_VITAMIN_C_PERCENT ;
    private String MEAL_BOX_CALCIUM_PERCENT;
    private String MEAL_BOX_IRON_PERCENT;
    private String MEAL_BOX_CATEGORY;

    public Meal_Type()
    {
        MEAL_BOX_ID ="";
        MEAL_BOX_MEAL_TYPE ="" ;
        MEAL_BOX_MEAL_TYPE_ID = "" ;
        MEAL_BOX_FOOD_TYPE = ""  ;
        MEAL_BOX_FOOD_ITEM_NAME = "" ;
        MEAL_BOX_GRAMS_PER_SERVING_PORTION =""  ;
        MEAL_BOX_CALORIE_PER_100G = ""  ;
        MEAL_BOX_FAT_PER_100G = ""  ;
        MEAL_BOX_SATURATED_FAT = ""  ;
        MEAL_BOX_TRANS_FAT = ""  ;
        MEAL_BOX_PROTEIN_PER_100G = ""  ;
        MEAL_BOX_CARBS_PER_100G = "" ;
        MEAL_BOX_SUGAR_PER_100G = ""  ;
        MEAL_BOX_SALT_PER_100G = ""  ;
        MEAL_BOX_WELLBEING_INDEX = "" ;
        MEAL_BOX_FIBER = "" ;
        MEAL_BOX_PRICE_STERLING = ""  ;
        MEAL_BOX_POLYUNSATURATED = "" ;
        MEAL_BOX_MONOUNSATURATED  = "" ;
        MEAL_BOX_CHOLESTEROL_MG = "" ;
        MEAL_BOX_SODIUM_MG = ""  ;
        MEAL_BOX_POTASSIUM_MG = "" ;
        MEAL_BOX_VITAMIN_A_PERCENT  = "" ;
        MEAL_BOX_VITAMIN_C_PERCENT  = "" ;
        MEAL_BOX_CALCIUM_PERCENT = "" ;
        MEAL_BOX_IRON_PERCENT = "" ;
        MEAL_BOX_CATEGORY = "" ;
    }


    public void Set_MEAL_BOX_ID(String IN)
    {
        MEAL_BOX_MEAL_TYPE_ID = IN;
    }
    public void Set_MEAL_BOX_MEAL_TYPE(String IN)
    {

    }
    public void Set_MEAL_BOX_MEAL_TYPE_ID(String IN)
    {
        MEAL_BOX_MEAL_TYPE_ID = IN;
    }
    private void Set_MEAL_BOX_FOOD_TYPE(String IN)
    {
        MEAL_BOX_MEAL_TYPE = IN;
    }
    private void Set_MEAL_BOX_FOOD_ITEM_NAME(String IN)
    {
        MEAL_BOX_FOOD_ITEM_NAME = IN;
    }
    private void Set_MEAL_BOX_GRAMS_PER_SERVING_PORTION(String IN)
    {
        MEAL_BOX_GRAMS_PER_SERVING_PORTION = IN;
    }
    private void Set_MEAL_BOX_CALORIE_PER_100G(String IN)
    {
        MEAL_BOX_CALORIE_PER_100G = IN;
    }
    private void Set_MEAL_BOX_FAT_PER_100G(String IN)
    {
        MEAL_BOX_FAT_PER_100G = IN;
    }
    private void Set_MEAL_BOX_SATURATED_FAT(String IN)
    {
        MEAL_BOX_SATURATED_FAT = IN;
    }
    private void Set_MEAL_BOX_TRANS_FAT(String IN)
    {
        MEAL_BOX_TRANS_FAT = IN;
    }
    private void Set_MEAL_BOX_PROTEIN_PER_100G(String IN)
    {
        MEAL_BOX_PROTEIN_PER_100G = IN;
    }
    private void Set_MEAL_BOX_CARBS_PER_100G(String IN)
    {
        MEAL_BOX_CARBS_PER_100G = IN;
    }
    private void Set_MEAL_BOX_SUGAR_PER_100G(String IN)
    {
        MEAL_BOX_SUGAR_PER_100G = IN;
    }
    private void Set_MEAL_BOX_SALT_PER_100G(String IN)
    {
        MEAL_BOX_SALT_PER_100G = IN;
    }
    private void Set_MEAL_BOX_WELLBEING_INDEX(String IN)
    {
        MEAL_BOX_WELLBEING_INDEX = IN;
    }
    private void Set_MEAL_BOX_FIBER(String IN)
    {
        MEAL_BOX_FIBER = IN;
    }
    private void Set_MEAL_BOX_PRICE_STERLING(String IN)
    {
        MEAL_BOX_PRICE_STERLING = IN;
    }
    private void Set_MEAL_BOX_POLYUNSATURATED(String IN)
    {
        MEAL_BOX_POLYUNSATURATED = IN;
    }
    private void Set_MEAL_BOX_MONOUNSATURATED(String IN)
    {
        MEAL_BOX_MONOUNSATURATED = IN;
    }
    private void Set_MEAL_BOX_CHOLESTEROL_MG(String IN)
    {
        MEAL_BOX_CHOLESTEROL_MG = IN;
    }
    private void Set_MEAL_BOX_SODIUM_MG(String IN)
    {
        MEAL_BOX_SODIUM_MG = IN;
    }
    private void Set_MEAL_BOX_POTASSIUM_MG(String IN)
    {
        MEAL_BOX_POTASSIUM_MG = IN;
    }
    private void Set_MEAL_BOX_VITAMIN_A_PERCENT(String IN)
    {
        MEAL_BOX_VITAMIN_A_PERCENT = IN;
    }
    private void Set_MEAL_BOX_VITAMIN_C_PERCENT(String IN)
    {
        MEAL_BOX_VITAMIN_C_PERCENT = IN;
    }
    private void Set_MEAL_BOX_CALCIUM_PERCENT(String IN)
    {
        MEAL_BOX_CALCIUM_PERCENT = IN;
    }
    private void Set_MEAL_BOX_IRON_PERCENT(String IN)
    {
        MEAL_BOX_IRON_PERCENT = IN;
    }
    private void Set_MEAL_BOX_CATEGORY(String IN)
    {
        MEAL_BOX_CATEGORY = IN;
    }


    public  String Get_MEAL_BOX_ID()
    {
        return MEAL_BOX_MEAL_TYPE_ID;
    }
    public String Get_MEAL_BOX_MEAL_TYPE()
    {
        return MEAL_BOX_MEAL_TYPE;
    }
    public String Get_MEAL_BOX_MEAL_TYPE_ID()
    {
        return  MEAL_BOX_MEAL_TYPE_ID;
    }
    public String Get_MEAL_BOX_FOOD_TYPE()
    {
        return MEAL_BOX_FOOD_TYPE;
    }
    public String Get_MEAL_BOX_FOOD_ITEM_NAME()
    {
        return MEAL_BOX_FOOD_ITEM_NAME;
    }
    public String Get_MEAL_BOX_GRAMS_PER_SERVING_PORTION()
    {
        return MEAL_BOX_GRAMS_PER_SERVING_PORTION;
    }
    public String Get_MEAL_BOX_CALORIE_PER_100G()
    {
        return MEAL_BOX_CALORIE_PER_100G;
    }
    public String Get_MEAL_BOX_FAT_PER_100G()
    {
        return MEAL_BOX_FAT_PER_100G;
    }
    public String Get_MEAL_BOX_SATURATED_FAT()
    {
        return MEAL_BOX_SATURATED_FAT;
    }
    public String Get_MEAL_BOX_TRANS_FAT()
    {
        return MEAL_BOX_TRANS_FAT;
    }
    public String Get_MEAL_BOX_PROTEIN_PER_100G()
    {
        return MEAL_BOX_PROTEIN_PER_100G;
    }
    public String Get_MEAL_BOX_CARBS_PER_100G()
    {
        return MEAL_BOX_CARBS_PER_100G;
    }
    public String Get_MEAL_BOX_SUGAR_PER_100G()
    {
        return MEAL_BOX_SUGAR_PER_100G;
    }
    public String Get_MEAL_BOX_SALT_PER_100G()
    {
        return MEAL_BOX_SALT_PER_100G;
    }
    public String Get_MEAL_BOX_WELLBEING_INDEX()
    {
        return MEAL_BOX_WELLBEING_INDEX;
    }
    public String Get_MEAL_BOX_FIBER()
    {
        return MEAL_BOX_FIBER;

    }
    public String Get_MEAL_BOX_PRICE_STERLING()
    {
        return MEAL_BOX_PRICE_STERLING;
    }
    public String Get_MEAL_BOX_POLYUNSATURATED()
    {
        return MEAL_BOX_POLYUNSATURATED;
    }
    public String Get_MEAL_BOX_MONOUNSATURATED()
    {
        return MEAL_BOX_POLYUNSATURATED;
    }
    public String Get_MEAL_BOX_CHOLESTEROL_MG()
    {
        return MEAL_BOX_CHOLESTEROL_MG;
    }
    public String Get_MEAL_BOX_SODIUM_MG()
    {
        return MEAL_BOX_SODIUM_MG;
    }
    public String Get_MEAL_BOX_POTASSIUM_MG()
    {
        return MEAL_BOX_POTASSIUM_MG;
    }
    public String Get_MEAL_BOX_VITAMIN_A_PERCENT()
    {
        return MEAL_BOX_VITAMIN_A_PERCENT;
    }
    public String Get_MEAL_BOX_VITAMIN_C_PERCENT()
    {
        return MEAL_BOX_VITAMIN_C_PERCENT;
    }
    public String Get_MEAL_BOX_CALCIUM_PERCENT()
    {
        return MEAL_BOX_CALCIUM_PERCENT;
    }
    public String Get_MEAL_BOX_IRON_PERCENT()
    {
        return MEAL_BOX_IRON_PERCENT;
    }
    public String Get_MEAL_BOX_CATEGORY()
    {
        return MEAL_BOX_CATEGORY;

    }


    public void Store_Health_Profile_CIF(HealthProfileCiF3 INPUT)
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);

        jackie.StoreHealthProfile(INPUT);


    }

    public HealthProfileCiF3 Retreive_Heatlh_Profile()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(context);
        return jackie.getHealthProfileObjectSerializable();
    }


}


    public boolean Populate_with_csv(String IN)
    {
        //Algorithm Engineering ->

        //Step One : Recieves a inhouse csv Striong as INPUT;
        //Step Two : add String by Hardwire to res value String file()
        //Step Three : Takes in a File name and converts it to csv String via IO file name()

        //Convert file to csv then add to res -> value > String File.
        // (re)Load... -> www.ese-edet.eu


        return true;

    }

}