package ese.com.caloriecountdownappforandroidbrown; //PUSH (Green) &

import android.content.Context;
import android.util.JsonReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by ESE on 08/03/2017.
 */
public class Transactions_CIF22 extends ArrayList implements JSONWrapperCIFInterface {


    // Always comment functions() new level/code fragment, Fragment,Rubix cube levels and twisting and interacting, no code, //
    // supreme IDO design see the high value outcome and what happens -> ESE* .further Algorithm engineered by Black Office,
    // Built by Builder
    // and submitted, for Builder to PUSH & (re)Load ▶️ ESE*.
    // (no code fragment IDO)etc, * writings


    private ArrayList<Transaction_CIF52> mTransactionLines;
    private BoxCIF17 Internal_Box;
    private String StatementText;
    private String JSONText = "";
    private org.json.JSONObject jsonObject = null;


    public Transactions_CIF22() {

        //Transaction_Line_CIF17 line = new Transaction_Line_CIF17();
        //Transaction_CIF52 Line_52 = new Transaction_CIF52();
        //Line_52.Set_Single_Transaction_Line(line);

        mTransactionLines = new ArrayList<Transaction_CIF52>();
        //mTransactionLines.add(Line_52);
        Internal_Box = new BoxCIF17("Dinner");
        StatementText = "www.ese-edet.eu";


    }

    public Transactions_CIF22(Nutritional_Content_CiFragmented_Object001 IN) {
        Transaction_Line_CIF17 line = new Transaction_Line_CIF17(IN);
        Transaction_CIF52 Line_52 = new Transaction_CIF52();
        Line_52.Set_Single_Transaction_Line(line);
        mTransactionLines = new ArrayList<Transaction_CIF52>();
        mTransactionLines.add(Line_52);
        Internal_Box = new BoxCIF17("Dinner");
        StatementText = "ESE";

        get_Food_item_or_items_Kotlin_1_5_0(IN);

    }


    public void Set_Statement_Text(String IN) {
        StatementText = IN;
    }


    public String Get_Statement_Text() {
        return StatementText;
    }


    public void Add_TransactionLine_2_List(Transaction_CIF52 IN) {
        mTransactionLines.add(IN);
    }


    public void Remove_TransactionLine_from_List(Transaction_CIF52 OUT) {
        mTransactionLines.remove(OUT);
    }


    public void Set_TransactionLines(ArrayList<Transaction_CIF52> IN) {
        mTransactionLines = IN;
    }


    public ArrayList<Transaction_CIF52> Get_TransactionLines() {
        return mTransactionLines;
    }

    //public long Get_Column_id()
    //{
    //    return mTransactionLine.Get_Transaction_id();
    // }


    public BoxCIF17 Strip_Column_ID(long column_id, BoxCIF17 mealitems) //Strips...
    {

        //long c_id_to_search_and_find = column_id;
        //BoxCIF17 stripped = new BoxCIF17();
        //Transaction_CIF52 IN;
        //mTransactions.Add_Box(mBox/Mealitmes
        // BoxCIF17 mBox_Food_Itmes = Strip_Column_Helper_LevelOne_Big(mTransactionLine.Get_Transaction_id(), IN, mealitems);
        //mTransactions.Add_Box(mBox_Food_Items)
        //IN.setmBox/mealitmes
        //return mBox_Food_Itmes; //Transaction.Get_Internal_Box;

        return new BoxCIF17();


    }


    public void Set_Internal_Box(BoxCIF17 IN) {
        Internal_Box = IN;
    }


    public BoxCIF17 Get_Internal_Box() {
        return Internal_Box;
    } //mTransactionLine.Get_Internal_Box();


    public ArrayList<Food_Item_CIF4> Strip_Food_Items_from_Cursor() {
        return new ArrayList<Food_Item_CIF4>();
    }


    private BoxCIF17 Strip_Column_Helper_LevelOne_Big(long column_id, Transaction_CIF52 IN, BoxCIF17 stripped) {
        Internal_Box = stripped;
        return stripped;
    }


    public String Print() {
        android.util.Log.d("IN CIF22 PRINT", "CIF52 Count : " + new RoundingCIF13().IntToString(mTransactionLines.size()));
        StatementText = "Transactions Lines empty";

        try {
            //Form Statemnt Text
            //Return Statement Text
            StatementText = "";//Date\t\tMeal Type\t\tDescription\t\tCalories IN\t\tCalories OUT\t\tCountdown Balance\n\n";
            for (Transaction_CIF52 m : mTransactionLines) {
                StatementText = StatementText + "Date: " + m.Get_Single_Transaction_Line().Get_Transaction_DateX().toString() + "\n\n" +
                        "Meal Type: " + m.Get_Single_Transaction_Line().Get_Transaction_Meal_Type() + "\n\n" +
                        "Description: " + m.Get_Single_Transaction_Line().Get_Description() + "\n\n" +
                        m.Print_Transaction_Line() + "\n" +
                        "Calories IN: " + new RoundingCIF13().IntToString(m.Get_Single_Transaction_Line().Get_Calorie_IN()) + "\n\n" +
                        "Calories OUT: " + new RoundingCIF13().IntToString(m.Get_Single_Transaction_Line().Get_Calorie_Outwards()) + "\n\n" +
                        "Countdown Balance: " + m.Get_Single_Transaction_Line().Get_New_Balance() + "\n\n" +
                        "__________________________________________" + "\n\n\n";

            }
        } catch (Exception e) {
            android.util.Log.d("TranssactionCiF22 Print", e.getMessage());
        }
        return StatementText;


    }


    public void add_BOX_to_Line(long ID, BoxCIF17 Input) {
        ;
    }


    public void Wipe() {
        if (mTransactionLines != null) {
            mTransactionLines.clear();
        }

    }


    //public ArrayList<Transaction_CIF52> getLines()
    //{
    //   return Get_TransactionLines();
    //}
    //Transactions_CIF22 my_Transaction_CIF22 : Transactions_CIF22(), JSONWrapperCIFInterface {}
    //var Lines: ArrayList<Transaction_CIF52>? = null


    public String WrapTransactions_CIF22(Transactions_CIF22 Input) {
        return "JSON_Text";
    }


    ArrayList<Transaction_CIF52> getLines() {
        if (this.mTransactionLines == null) {
            return new ArrayList<Transaction_CIF52>();
        } else {
            return this.mTransactionLines;
        }
    }


    private Object UnWrapTransactions_CIF22() {


        Serialize_Me("/data", null); //return
        return this.clone();


        //Wrapper serializes Transaction_CIF022_Object to text Output JSONText.
        // DeWrap takes a JSONText and deserialize it into an Object Transaction_CIF022_fragment.
        //Parse take a JSONText and Produces a JSONText transmitted over the network.


    }


    @NotNull
    @Contract(pure = true)


    private String Parse_CiF22_to_JSON1() {

        JSONText = "";
        JSONText += "\"Transactions_CIF22\" : { ";

        for (int c = 0; c < mTransactionLines.size(); c++) {

            JSONText += "[ Transaction_CIF52_[" + Integer.toString(c) + "]\" : {" + "\"Transaction_Line_CIF17\" : { ";
            JSONText = JSON_Tree_Level_One(JSONText, mTransactionLines.get(c).Get_Single_Transaction_Line());
            JSONText = JSON_Tree_Level_Two(JSONText, mTransactionLines.get(c).Get_Single_Transaction_Line().Get_Transaction_Food_Items());
            JSONText += "\"Description\" : " + mTransactionLines.get(c).Get_Single_Transaction_Line().Get_Description() + "\n";
            JSONText += "\"mCalorie_IN\" : " + mTransactionLines.get(c).Get_Single_Transaction_Line().Get_Description() + "\n";
            JSONText += "\"Calorie_OUT\" : " + mTransactionLines.get(c).Get_Single_Transaction_Line().Get_Description() + "\n";
            JSONText += "\"Countdown_BALANCE\" : " + mTransactionLines.get(c).Get_Single_Transaction_Line().Get_Description() + "\n";
            JSONText += "\"Food_Item_CIF4_dummy_rice_4\" : " + "null" + "\n";

            JSONText = JSON_Tree_Level_Three_food_item_list(JSONText, mTransactionLines.get(c).Get_Single_Transaction_Line());

        }

        return JSONText;

    }


    private JSONObject Parse_CiF22_to_JSON2() {
        //String inhousecsvDOTjson = "JSON_TEXT";


        if (jsonObject == null) {
            try {
                jsonObject = new JSONObject(Parse_CiF22_to_JSON1());
            } catch (Exception e) {
                jsonObject = new JSONObject();
            }
        } else {
            ;
        }

        return jsonObject;


    }


    //private map<true,filepath> depopulate(SQLDatabase_Food_Items_CIF6 Input)
    //{
    //     return false;
    // }


    @Override
    public String Code_JSONText() {
        return Parse_CiF22_to_JSON1();
    }


    public JSONObject Code_JSONObject() {
        return jsonObject;
    }


    @Override
    public Transactions_CIF22 DeCode_JSONText(String json_input) {
        JsonReader reader = new JsonReader(new java.io.Reader() {
            /**
             * Reads characters into a portion of an array.  This method will block
             * until some input is available, an I/O error occurs, or the end of the
             * stream is reached.
             *
             * @param cbuf Destination buffer
             * @param off  Offset at which to start storing characters
             * @param len  Maximum number of characters to read
             * @return The number of characters read, or -1 if the end of the
             * stream has been reached
             * @throws IOException If an I/O error occurs
             */
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            /**
             * Closes the stream and releases any system resources associated with
             * it.  Once the stream has been closed, further read(), ready(),
             * mark(), reset(), or skip() invocations will throw an IOException.
             * Closing a previously closed stream has no effect.
             *
             * @throws IOException If an I/O error occurs
             */
            @Override
            public void close() throws IOException {

            }
        });

        return null;

        /*public Transactions_CIF22 DeCode_JSONText(String In)
    {
        return new Transactions_CIF22();
    } */
    }


    public Transactions_CIF22 DeCode_JSONText(JSONObject input) {
        JsonReader reader;
        reader = new JsonReader(new java.io.Reader() {
            /**
             * Reads characters into a portion of an array.  This method will block
             * until some input is available, an I/O error occurs, or the end of the
             * stream is reached.
             *
             * @param cbuf Destination buffer
             * @param off  Offset at which to start storing characters
             * @param len  Maximum number of characters to read
             * @return The number of characters read, or -1 if the end of the
             * stream has been reached
             * @throws IOException If an I/O error occurs
             */
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            /**
             * Closes the stream and releases any system resources associated with
             * it.  Once the stream has been closed, further read(), ready(),
             * mark(), reset(), or skip() invocations will throw an IOException.
             * Closing a previously closed stream has no effect.
             *
             * @throws IOException If an I/O error occurs
             */
            @Override
            public void close() throws IOException {

            }
        });
        return null;
    }


    @Override
    public void Set_JSONText(String json_input) {
        JSONText = json_input;
    }


    @Override
    public String Get_JSONText() {
        return null;
    }


    @Override
    public String Nutritional_Object_to_JSONText(Nutritional_Content_CiFragmented_Object001 INPUT) {
        return Convert_Nutritional_Object_to_JSONText(INPUT);
    }


    public Transaction_CIF22 deWrappingParser(JSONWrapperCIFClass DOCUMENT_IN) {
        return new Transaction_CIF22();
    }


    public JSONWrapperCIFClass Wrapper(Transactions_CIF22 IN) {
        return new JSONWrapperCIFClass(IN);
    }


    public String WrapTransactions_CIF22(Transaction_CIF22 INPUT) {
        return new String("deWrapper's empty");
    }


    public Transaction_CIF22 UnWrapTransactions_CIF22(String INPUT) {
        Transaction_CIF22 OUTPUT = new Transaction_CIF22();
        return OUTPUT;
    }


    public Transaction_CIF22 UnWrapTransactions_CIF22(String INPUT, Transaction_CIF22 In) {
        return In;
    }


    public Transaction_CIF22 UnWrapTransaction_CIF22(Transaction_CIF22 INPUT) {
        return INPUT;
    }


    public void Serialize_Me(String fileName, Context context) {

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (Exception e) {
        }

    }


    public Transactions_CIF22 deSerialize_Me(String fileName, Context context) {

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Transactions_CIF22 simpleClass = (Transactions_CIF22) is.readObject();
            is.close();
            fis.close();

            final Transactions_CIF22 transactions_cif22 = simpleClass;
            return transactions_cif22;
        } catch (Exception e) {
            return new Transactions_CIF22();
        }


    }


    private String JSON_Tree_Level_One(String input_json_text, Transaction_Line_CIF17 Input) {


        if (Input != null) {

            input_json_text += " \"mTransaction_ID\" : ";
            input_json_text += Long.toString(Input.Get_Transaction_ID());
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Date\" : ";
            input_json_text += "\"" + Input.Get_Date().toString() + "\"";
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Meal_Type\" : ";
            input_json_text += "\"" + Input.Get_Transaction_Meal_Type() + "\"";
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Meal_Type_ID\" : ";
            input_json_text += "\"" + Long.toString(Input.Get_Transaction_Meal_Type_ID()) + "\"";
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Amount\" : ";
            input_json_text += "\"" + Integer.toString(Input.Get_Transaction_Amount()) + "\"";
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Balance\" : ";
            input_json_text += "\"" + Integer.toString(Input.Get_Transaction_Balance()) + "\"";
            input_json_text += "\n";

            input_json_text += " \"mmTransaction_Meal_Box_ID\" : ";
            input_json_text += "\"" + Long.toString(Input.Get_Transaction_Meal_Box_ID()) + "\"";
            input_json_text += "\n";

            input_json_text += " \"mTransaction_Meal_Box_Food_Type\" : ";
            input_json_text += "\"" + Input.Get_Transaction_Meal_Box_Food_Type() + "\"";
            input_json_text += "\n";

            input_json_text += " \"Description\" : ";
            input_json_text += "\"" + Input.Get_Description() + "\"";
            input_json_text += "\n";

        }

        return input_json_text;

    }


    private String JSON_Tree_Level_Two(String input_json_text, BoxCIF17 Input) {
        if (Input != null) {
            input_json_text += " \"mfood_item_list\" : { ";
            input_json_text += "\"mFoodBox\" : [ ";
            for (int c = 0; c < Input.mFoodBox.size(); c++) {
                input_json_text += " Food_Item_CiF4[" + Integer.toString(c) + "]\" : {" + "\"id\" : " + Long.toString(Input.mFoodBox.get(c).Get_id()) + "," + "\n";

                input_json_text += "\"mMeal_ID\" : " + Long.toString(Input.mFoodBox.get(c).Get_Meal_Box_ID()) + "," + "\n";

                input_json_text += "\"food_type\" : " + Input.mFoodBox.get(c).Get_food_type() + "," + "\n";

                input_json_text += "\"food_item_name\" : " + Input.mFoodBox.get(c).Get_food_item_name() + "," + "\n";

                input_json_text += "\"grams_per_serving_portion\" : " + Float.toString(Input.mFoodBox.get(c).Get_grams_per_serving_portion()) + "," + "\n";

                input_json_text += "\"calories_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_calories_per_100g()) + "," + "\n";

                input_json_text += "\"fat_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_fat_per_100g()) + "," + "\n";

                input_json_text += "\"saturated_fat\" : " + Float.toString(Input.mFoodBox.get(c).Get_saturated_fat()) + "," + "\n";

                input_json_text += "\"trans_fat\" : " + Float.toString(Input.mFoodBox.get(c).Get_trans_fat()) + "," + "\n";

                input_json_text += "\"protein_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_protein_per_100g()) + "," + "\n";

                input_json_text += "\"carbs_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_carbs_per_100g()) + "," + "\n";

                input_json_text += "\"sugar_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_sugar_per_100g()) + "," + "\n";

                input_json_text += "\"salt_per_100g\" : " + Float.toString(Input.mFoodBox.get(c).Get_salt_per_100g()) + "," + "\n";

                input_json_text += "\"wellbeing_index\" : " + Boolean.toString(Input.mFoodBox.get(c).Get_wellbeing_index()) + "," + "\n";

                input_json_text += "\"fiber\" : " + Float.toString(Input.mFoodBox.get(c).Get_fiber()) + "," + "\n";

                input_json_text += "\"price_sterling\" : " + Float.toString(Input.mFoodBox.get(c).Get_price_sterling()) + "," + "\n";

                input_json_text += "\"polyunsaturated\" : " + Float.toString(Input.mFoodBox.get(c).Get_polyunsaturated()) + "," + "\n";

                input_json_text += "\"monounsaturated\" : " + Float.toString(Input.mFoodBox.get(c).Get_monounsaturated()) + "," + "\n";

                input_json_text += "\"cholesterol_mg\" : " + Float.toString(Input.mFoodBox.get(c).Get_cholesterol_mg()) + "," + "\n";

                input_json_text += "\"sodium_mg\" : " + Float.toString(Input.mFoodBox.get(c).Get_sodium_mg()) + "," + "\n";

                input_json_text += "\"potassium_mg\" : " + Float.toString(Input.mFoodBox.get(c).Get_potassium_mg()) + "," + "\n";

                input_json_text += "\"vitamin_a_percent\" : " + Float.toString(Input.mFoodBox.get(c).Get_vitamin_a_percent()) + "," + "\n";

                input_json_text += "\"vitamin_c_percent\" : " + Float.toString(Input.mFoodBox.get(c).Get_vitamin_c_percent()) + "," + "\n";

                input_json_text += "\"calcium_percent\" : " + Float.toString(Input.mFoodBox.get(c).Get_calcium_percent()) + "," + "\n";

                input_json_text += "\"iron_percent\" : " + Float.toString(Input.mFoodBox.get(c).Get_iron_percent()) + "," + "\n";

                input_json_text += "\"category\" : " + Input.mFoodBox.get(c).Get_category() + "," + "\n";

                input_json_text += "\"weight\" : " + Integer.toString(Input.mFoodBox.get(c).Get_weight()) + "," + "\n";

                input_json_text += "\"Calorie_Value\" : " + Integer.toString(Input.mFoodBox.get(c).Get_calorie_value()) + "," + "\n";

                input_json_text += "\"index\" : " + Integer.toString(Input.mFoodBox.get(c).Get_Index()) + "," + "\n";

                input_json_text += "\"Quantity\" : " + Integer.toString(Input.mFoodBox.get(c).Get_Quantity()) + "," + "\n";

                input_json_text += "\"reserve_food_item_name_list\" : [" + Get_JSON_reserve_food_item_name_list(Input.mFoodBox.get(c).getReserve_food_item_name_list()) + "]," + "\n";

                input_json_text += "\"Barcode\" : " + Long.toString(Input.mFoodBox.get(c).Get_Barcode()) + "," + "\n";

                input_json_text += "\"Image_File\" : " + Input.mFoodBox.get(c).Get_Image_File() + "," + " }" + ",";

                if (c == (Input.mFoodBox.size() - 1)) {
                    input_json_text += "} ]\n";
                }

            }

            input_json_text += "\"mMeal\" :" + "\"" + Input.Get_Meal_Type() + "\n";
            input_json_text += "\"mMeal_ID\" :" + "\"" + Input.Get_Transaction_ID() + "\n";
            input_json_text += "\"Meal_Box_ID\" :" + "\"" + Input.Get_Meal_Box_ID() + "\n";
            input_json_text += "\"Transaction_ID\" :" + "\"" + Input.Get_Transaction_ID() + "\n";
            input_json_text += "\"Balance\" :" + "\"" + Input.Get_Balance() + "\n";
            input_json_text += "\"Energy_OUT\" :" + "\"" + Input.Get_Energy_OUT() + "\n";
            input_json_text += "\"data_file_path\" :" + "\"" + Input.Get_data_file_path() + "\n";
        }

        return input_json_text;
    }


    private String Get_JSON_reserve_food_item_name_list(ArrayList<String> Input) {
        String real_json = "";

        if (Input != null) {
            for (String m : Input) {
                if (m == Input.get(Input.size() - 1)) {
                    real_json += "\"m\"";
                } else {
                    real_json += "\"m\",";
                }
            }
        }

        return real_json;
    }


    private String JSON_Tree_Level_Three_food_item_list(String input_json_text, Transaction_Line_CIF17 Input) {
        input_json_text += "\"Breakfast_Transaction_CIF22 food_item_list\" : " + Input.Get_Breakfast_Tranasction_CiF22_food_item_list().toString() + "],}," + "\n";
        input_json_text += "\"inhousecsvLinetext\"  : " + "\"data/csv_depopulate\"," + "\n";
        input_json_text += "\"Row_Type_fragment_type\" :" + "\"Row_Type_fragment.Neither\" ] }";

        return input_json_text;
    }


    private String Convert_Nutritional_Object_to_JSONText(Nutritional_Content_CiFragmented_Object001 Input) {
        return Input.convertToJSONText();
    }


    public void get_Food_item_or_items_Kotlin_1_5_0(Nutritional_Content_CiFragmented_Object001 Input) {
        get_Food_items_or_items_Kotlin_1_5_0(Input.get_Food_items()); //add to Box/mfoodbox
    }

    public long get_Food_items_or_items_Kotlin_1_5_0(ArrayList<Food_Item_CIF4> input) {
        //add to mbox

        return new Add_New_Item_Activity_CIF2Fragment().add_Transactions_CiF22_To_CiF6(input);
    }
}



























































































/*
 *This Interface allows the app to receive Food/Energy item Data from either internal database or external database(s) or e://drive
 *and seamless i without ever knowing or differentiating the difference within the app.

*Blow and Blown & continue &
*class JSONWrapperCIFClass

 *
 *
 *(//Gets Data seamlessy from external or internal or AWS items database or all three.


            //Balance Brought FORWARD.

            pop()
                passio space() &

                link with e://drive.




 {

 "food_item" :{
                "Category": "EATOUT",
                "Food_item_name": "Apple",
                "Portion_or_Serving Size": 100,
                "Calories": 100,
                "Fat": 10,
                "Saturates": 3,
                "Trans fat": 0,
                "Protein": 38,
                "Carbohydrates": 50,                Software-On-Demand
                "Sugar": 22,
                "Salt": 6,
                "Superfood": true,
                "Fibre": 8,
                "Price": 1,
                "Barcode": 1001010101,
                "Image File": "Data/appletwo.src",
                "Weight in Grams": 100
               }
 }


*/




/*


    {
     "Transactions_CIF22" : {
                              [
                                  "Transaction_CIF52_[0]" :
                                                            {

                                                                    "Transaction_Line_CIF17":
                                                                                                  {
                                                                                                      "mTransaction_ID": 0123456789
                                                                                                      "mTransaction_Date": "08/04/2021"
                                                                                                      "mTransaction_Meal_Type": "Breakfast"
                                                                                                      "mTransaction_Meal_Type_ID": 9876543210
                                                                                                      "mTransaction_Amount" : 669
                                                                                                      "mTransaction_Balance": 58880
                                                                                                      "mTransaction_Meal_Box_ID": 7584343245
                                                                                                      "mTransaction_Meal_Box_Food_Type: "Breakfast"
                                                                                                      "Description": "Healthy Meal for Breakfast list of food eaten and drinks drunk"
            "mfood_item_list" : { "mFoodBox":

            [

                                                "Food_Item_CiF4[0]":{
                "id" : 1234567890,
                    "mMeal_ID" : 768675746,
                    "food_type" : breakfast,
                    "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                "grams_per_serving_portion" : 100
                "calories_per_100g" : 550
                "fat_per_100g" : 1.8
                "saturated_fat" : 0.2
                "trans_fat" : 0
                "protein_per_100g" : 9.3
                "carbs_per_100g" : 7.8
                "sugar_per_100g" : 2
                "salt_per_100g" : 0345
                "wellbeing_index" : true
                "fiber" :1.6
                "price_sterling" : 1
                "polyunsaturated" : 3
                "monounsaturated" : 3
                "cholesterol_mg" : 3
                "sodium_mg" : 3
                "potassium_mg" : 3
                "vitamin_a_percent" : 3
                "vitamin_c_percent" : 3
                "calcium_percent" : 3
                "iron_percent" : 3
                "category" : DRINK
                "weight" : 500g
                "Calorie_Value" : 550
                "index" : NA
                "Quantity" : 1
                "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                "Barcode": 1001010101,
                    "Image File": "appletwo.src"
            },

            "Food_Item_CiF4[1]":

            {
                "id" : 1234567890,
                    "mMeal_ID" : 768675746,
                    "food_type" : breakfast,
                    "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                "grams_per_serving_portion" : 100
                "calories_per_100g" : 550
                "fat_per_100g" : 1.8
                "saturated_fat" : 0.2
                "trans_fat" : 0
                "protein_per_100g" : 9.3
                "carbs_per_100g" : 7.8
                "sugar_per_100g" : 2
                "salt_per_100g" : 0345
                "wellbeing_index" : true
                "fiber" :1.6
                "price_sterling" : 1
                "polyunsaturated" : 3
                "monounsaturated" : 3
                "cholesterol_mg" : 3
                "sodium_mg" : 3
                "potassium_mg" : 3
                "vitamin_a_percent" : 3
                "vitamin_c_percent" : 3
                "calcium_percent" : 3
                "iron_percent" : 3
                "category" : DRINK
                "weight" : 500g
                "Calorie_Value" : 550
                "index" : NA
                "Quantity" : 1
                "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                "Barcode": 1001010101,
                    "Image File": "appletwo.src"
            },

            "Food_Item_CiF4[2]":

            {
                "id" : 1234567890,
                    "mMeal_ID" : 768675746,
                    "food_type" : breakfast,
                    "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                "grams_per_serving_portion" : 100
                "calories_per_100g" : 550
                "fat_per_100g" : 1.8
                "saturated_fat" : 0.2
                "trans_fat" : 0
                "protein_per_100g" : 9.3
                "carbs_per_100g" : 7.8
                "sugar_per_100g" : 2
                "salt_per_100g" : 0345
                "wellbeing_index" : true
                "fiber" :1.6
                "price_sterling" : 1
                "polyunsaturated" : 3
                "monounsaturated" : 3
                "cholesterol_mg" : 3
                "sodium_mg" : 3
                "potassium_mg" : 3
                "vitamin_a_percent" : 3
                "vitamin_c_percent" : 3
                "calcium_percent" : 3
                "iron_percent" : 3
                "category" : DRINK
                "weight" : 500g
                "Calorie_Value" : 550
                "index" : NA
                "Quantity" : 1
                "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                "Barcode": 1001010101,
                    "Image File": "appletwo.src"
            }
                ]
            "mMeal" : "Breakfast"
            "mMeal_ID" : 1
            "Meal_Box_ID" : 1
            "Transaction_ID" : 189803434343
            "Balance" : 1
            "Energy_OUT: : 1
            "inhousecsvtext" : "inhousecsvtext"
        }

            "Description" : "Healthy Meal"

            "Breakfast_Transaction_CIF22 food_item_list" : ["Orange Juice", "Corn flakes", "Butter and Toast"]
            "mCalorie_IN" : 100
            "Calorie_OUT" : 120
            "Countdown_BALANCE" : 46000
            "Food_Item_CIF4_dummy_rice_4" : null

            }, //CiF17[0] Closer











                "Transaction_Line_CIF17[1]":
            {
                "mTransaction_ID": 0123456789
                "mTransaction_Date": "08/04/2021"
                "mTransaction_Meal_Type": "Breakfast"
                "mTransaction_Meal_Type_ID": 9876543210
                "mTransaction_Amount" : 669
                "mTransaction_Balance": 58880
                "mTransaction_Meal_Box_ID": 7584343245
                "mTransaction_Meal_Box_Food_Type: "Breakfast"
                "Description": "Healthy Meal for Breakfast list of food eaten and drinks drunk"
                "mfood_item_list" : {

                "mFoodBox": [

                "Food_Item_CiF4[0]":
                {
                    "id" : 1234567890,
                        "mMeal_ID" : 768675746,
                        "food_type" : breakfast,
                        "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                    "grams_per_serving_portion" : 100
                    "calories_per_100g" : 550
                    "fat_per_100g" : 1.8
                    "saturated_fat" : 0.2
                    "trans_fat" : 0
                    "protein_per_100g" : 9.3
                    "carbs_per_100g" : 7.8
                    "sugar_per_100g" : 2
                    "salt_per_100g" : 0345
                    "wellbeing_index" : true
                    "fiber" :1.6
                    "price_sterling" : 1
                    "polyunsaturated" : 3
                    "monounsaturated" : 3
                    "cholesterol_mg" : 3
                    "sodium_mg" : 3
                    "potassium_mg" : 3
                    "vitamin_a_percent" : 3
                    "vitamin_c_percent" : 3
                    "calcium_percent" : 3
                    "iron_percent" : 3
                    "category" : DRINK
                    "weight" : 500g
                    "Calorie_Value" : 550
                    "index" : NA
                    "Quantity" : 1
                    "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                    "Barcode": 1001010101,
                        "Image File": "appletwo.src"
                },

                "Food_Item_CiF4[1]":

                {
                    "id" : 1234567890,
                        "mMeal_ID" : 768675746,
                        "food_type" : breakfast,
                        "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                    "grams_per_serving_portion" : 100
                    "calories_per_100g" : 550
                    "fat_per_100g" : 1.8
                    "saturated_fat" : 0.2
                    "trans_fat" : 0
                    "protein_per_100g" : 9.3
                    "carbs_per_100g" : 7.8
                    "sugar_per_100g" : 2
                    "salt_per_100g" : 0345
                    "wellbeing_index" : true
                    "fiber" :1.6
                    "price_sterling" : 1
                    "polyunsaturated" : 3
                    "monounsaturated" : 3
                    "cholesterol_mg" : 3
                    "sodium_mg" : 3
                    "potassium_mg" : 3
                    "vitamin_a_percent" : 3
                    "vitamin_c_percent" : 3
                    "calcium_percent" : 3
                    "iron_percent" : 3
                    "category" : DRINK
                    "weight" : 500g
                    "Calorie_Value" : 550
                    "index" : NA
                    "Quantity" : 1
                    "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                    "Barcode": 1001010101,
                        "Image File": "appletwo.src"
                },

                "Food_Item_CiF4[2]":

                {
                    "id" : 1234567890,
                        "mMeal_ID" : 768675746,
                        "food_type" : breakfast,
                        "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                    "grams_per_serving_portion" : 100
                    "calories_per_100g" : 550
                    "fat_per_100g" : 1.8
                    "saturated_fat" : 0.2
                    "trans_fat" : 0
                    "protein_per_100g" : 9.3
                    "carbs_per_100g" : 7.8
                    "sugar_per_100g" : 2
                    "salt_per_100g" : 0345
                    "wellbeing_index" : true
                    "fiber" :1.6
                    "price_sterling" : 1
                    "polyunsaturated" : 3
                    "monounsaturated" : 3
                    "cholesterol_mg" : 3
                    "sodium_mg" : 3
                    "potassium_mg" : 3
                    "vitamin_a_percent" : 3
                    "vitamin_c_percent" : 3
                    "calcium_percent" : 3
                    "iron_percent" : 3
                    "category" : DRINK
                    "weight" : 500g
                    "Calorie_Value" : 550
                    "index" : NA
                    "Quantity" : 1
                    "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                    "Barcode": 1001010101,
                        "Image File": "appletwo.src"
                }
                ] //mfoodcloser


                "mMeal" : "Breakfast"
                "mMeal_ID" : 1
                "Meal_Box_ID" : 1
                "Transaction_ID" : 189803434343
                "Balance" : 1
                "Energy_OUT: : 1
                "inhousecsvtext" : "inhousecsvtext"
            }

                "Description" : "Healthy Meal"

                "Breakfast_Transaction_CIF22 food_item_list" : ["Orange Juice", "Corn flakes", "Butter and Toast"]
                "mCalorie_IN" : 100
                "Calorie_OUT" : 120
                "Countdown_BALANCE" : 46000
                "Food_Item_CIF4_dummy_rice_4" : null

            ], //CiF052[0] closer & CiF17[1]

                "inhousecsvLinetext" : "csv_depopulate",

              "Row_Type_fragment_type" : "Row_Type_fragment.Neither"

              }, //Closes CiF052[0]









                    "Transaction_CIF52_[1]":
                     {
                            [

                                 "Transaction_Line_CIF17[0]":
                {
                    "mTransaction_ID": 0123456789
                    "mTransaction_Date": "08/04/2021"
                    "mTransaction_Meal_Type": "Breakfast"
                    "mTransaction_Meal_Type_ID": 9876543210
                    "mTransaction_Amount" : 669
                    "mTransaction_Balance": 58880
                    "mTransaction_Meal_Box_ID": 7584343245
                    "mTransaction_Meal_Box_Food_Type: "Breakfast"
                    "Description": "Healthy Meal for Breakfast list of food eaten and drinks drunk"
                    "mfood_item_list" :
                                      {
                    "mFoodBox": [

                    "Food_Item_CiF4[0]":
                    {
                        "id" : 1234567890,
                            "mMeal_ID" : 768675746,
                            "food_type" : breakfast,
                            "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                        "grams_per_serving_portion" : 100
                        "calories_per_100g" : 550
                        "fat_per_100g" : 1.8
                        "saturated_fat" : 0.2
                        "trans_fat" : 0
                        "protein_per_100g" : 9.3
                        "carbs_per_100g" : 7.8
                        "sugar_per_100g" : 2
                        "salt_per_100g" : 0345
                        "wellbeing_index" : true
                        "fiber" :1.6
                        "price_sterling" : 1
                        "polyunsaturated" : 3
                        "monounsaturated" : 3
                        "cholesterol_mg" : 3
                        "sodium_mg" : 3
                        "potassium_mg" : 3
                        "vitamin_a_percent" : 3
                        "vitamin_c_percent" : 3
                        "calcium_percent" : 3
                        "iron_percent" : 3
                        "category" : DRINK
                        "weight" : 500g
                        "Calorie_Value" : 550
                        "index" : NA
                        "Quantity" : 1
                        "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                        "Barcode": 1001010101,
                            "Image File": "appletwo.src"
                    },

                    "Food_Item_CiF4[1]":

                    {
                        "id" : 1234567890,
                            "mMeal_ID" : 768675746,
                            "food_type" : breakfast,
                            "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                        "grams_per_serving_portion" : 100
                        "calories_per_100g" : 550
                        "fat_per_100g" : 1.8
                        "saturated_fat" : 0.2
                        "trans_fat" : 0
                        "protein_per_100g" : 9.3
                        "carbs_per_100g" : 7.8
                        "sugar_per_100g" : 2
                        "salt_per_100g" : 0345
                        "wellbeing_index" : true
                        "fiber" :1.6
                        "price_sterling" : 1
                        "polyunsaturated" : 3
                        "monounsaturated" : 3
                        "cholesterol_mg" : 3
                        "sodium_mg" : 3
                        "potassium_mg" : 3
                        "vitamin_a_percent" : 3
                        "vitamin_c_percent" : 3
                        "calcium_percent" : 3
                        "iron_percent" : 3
                        "category" : DRINK
                        "weight" : 500g
                        "Calorie_Value" : 550
                        "index" : NA
                        "Quantity" : 1
                        "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                        "Barcode": 1001010101,
                            "Image File": "appletwo.src"
                    },

                    "Food_Item_CiF4[2]":

                    {
                        "id" : 1234567890,
                            "mMeal_ID" : 768675746,
                            "food_type" : breakfast,
                            "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                        "grams_per_serving_portion" : 100
                        "calories_per_100g" : 550
                        "fat_per_100g" : 1.8
                        "saturated_fat" : 0.2
                        "trans_fat" : 0
                        "protein_per_100g" : 9.3
                        "carbs_per_100g" : 7.8
                        "sugar_per_100g" : 2
                        "salt_per_100g" : 0345
                        "wellbeing_index" : true
                        "fiber" :1.6
                        "price_sterling" : 1
                        "polyunsaturated" : 3
                        "monounsaturated" : 3
                        "cholesterol_mg" : 3
                        "sodium_mg" : 3
                        "potassium_mg" : 3
                        "vitamin_a_percent" : 3
                        "vitamin_c_percent" : 3
                        "calcium_percent" : 3
                        "iron_percent" : 3
                        "category" : DRINK
                        "weight" : 500g
                        "Calorie_Value" : 550
                        "index" : NA
                        "Quantity" : 1
                        "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                        "Barcode": 1001010101,
                            "Image File": "appletwo.src"
                    }
                ] Closes mFoodBox
                    "mMeal" : "Breakfast"
                    "mMeal_ID" : 1
                    "Meal_Box_ID" : 1
                    "Transaction_ID" : 189803434343
                    "Balance" : 1
                    "Energy_OUT: : 1
                    "inhousecsvtext" : "inhousecsvtext"
                }

                    "Description" : "Healthy Meal"

                    "Breakfast_Transaction_CIF22 food_item_list" : ["Orange Juice", "Corn flakes", "Butter and Toast"]
                    "mCalorie_IN" : 100
                    "Calorie_OUT" : 120
                    "Countdown_BALANCE" : 46000
                    "Food_Item_CIF4_dummy_rice_4" : null,


                        "Transaction_Line_CIF17[1]":
                    {
                        "mTransaction_ID": 0123456789
                        "mTransaction_Date": "08/04/2021"
                        "mTransaction_Meal_Type": "Breakfast"
                        "mTransaction_Meal_Type_ID": 9876543210
                        "mTransaction_Amount" : 669
                        "mTransaction_Balance": 58880
                        "mTransaction_Meal_Box_ID": 7584343245
                        "mTransaction_Meal_Box_Food_Type: "Breakfast"
                        "Description": "Healthy Meal for Breakfast list of food eaten and drinks drunk"



                        "mfood_item_list" :
                        {


                        "mFoodBox":
                        [

                        "Food_Item_CiF4[0]":
                        {
                            "id" : 1234567890,
                                "mMeal_ID" : 768675746,
                                "food_type" : breakfast,
                                "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                            "grams_per_serving_portion" : 100
                            "calories_per_100g" : 550
                            "fat_per_100g" : 1.8
                            "saturated_fat" : 0.2
                            "trans_fat" : 0
                            "protein_per_100g" : 9.3
                            "carbs_per_100g" : 7.8
                            "sugar_per_100g" : 2
                            "salt_per_100g" : 0345
                            "wellbeing_index" : true
                            "fiber" :1.6
                            "price_sterling" : 1
                            "polyunsaturated" : 3
                            "monounsaturated" : 3
                            "cholesterol_mg" : 3
                            "sodium_mg" : 3
                            "potassium_mg" : 3
                            "vitamin_a_percent" : 3
                            "vitamin_c_percent" : 3
                            "calcium_percent" : 3
                            "iron_percent" : 3
                            "category" : DRINK
                            "weight" : 500g
                            "Calorie_Value" : 550
                            "index" : NA
                            "Quantity" : 1
                            "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                            "Barcode": 1001010101,
                                "Image File": "appletwo.src"
                        },

                        "Food_Item_CiF4[1]":

                        {
                            "id" : 1234567890,
                                "mMeal_ID" : 768675746,
                                "food_type" : breakfast,
                                "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                            "grams_per_serving_portion" : 100
                            "calories_per_100g" : 550
                            "fat_per_100g" : 1.8
                            "saturated_fat" : 0.2
                            "trans_fat" : 0
                            "protein_per_100g" : 9.3
                            "carbs_per_100g" : 7.8
                            "sugar_per_100g" : 2
                            "salt_per_100g" : 0345
                            "wellbeing_index" : true
                            "fiber" :1.6
                            "price_sterling" : 1
                            "polyunsaturated" : 3
                            "monounsaturated" : 3
                            "cholesterol_mg" : 3
                            "sodium_mg" : 3
                            "potassium_mg" : 3
                            "vitamin_a_percent" : 3
                            "vitamin_c_percent" : 3
                            "calcium_percent" : 3
                            "iron_percent" : 3
                            "category" : DRINK
                            "weight" : 500g
                            "Calorie_Value" : 550
                            "index" : NA
                            "Quantity" : 1
                            "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                            "Barcode": 1001010101,
                                "Image File": "appletwo.src"
                        },

                        "Food_Item_CiF4[2]":

                        {
                            "id" : 1234567890,
                                "mMeal_ID" : 768675746,
                                "food_type" : breakfast,
                                "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
                            "grams_per_serving_portion" : 100
                            "calories_per_100g" : 550
                            "fat_per_100g" : 1.8
                            "saturated_fat" : 0.2
                            "trans_fat" : 0
                            "protein_per_100g" : 9.3
                            "carbs_per_100g" : 7.8
                            "sugar_per_100g" : 2
                            "salt_per_100g" : 0345
                            "wellbeing_index" : true
                            "fiber" :1.6
                            "price_sterling" : 1
                            "polyunsaturated" : 3
                            "monounsaturated" : 3
                            "cholesterol_mg" : 3
                            "sodium_mg" : 3
                            "potassium_mg" : 3
                            "vitamin_a_percent" : 3
                            "vitamin_c_percent" : 3
                            "calcium_percent" : 3
                            "iron_percent" : 3
                            "category" : DRINK
                            "weight" : 500g
                            "Calorie_Value" : 550
                            "index" : NA
                            "Quantity" : 1
                            "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
                            "Barcode": 1001010101,
                                "Image File": "appletwo.src"
                        }
                ] //Closes mFoodBox

                        "mMeal" : "Breakfast"
                        "mMeal_ID" : 1
                        "Meal_Box_ID" : 1
                        "Transaction_ID" : 189803434343
                        "Balance" : 1
                        "Energy_OUT: : 1
                        "inhousecsvtext" : "inhousecsvtext"
                    }

                        "Description" : "Healthy Meal"

                        "Breakfast_Transaction_CIF22 food_item_list" : ["Orange Juice", "Corn flakes", "Butter and Toast"]
                        "mCalorie_IN" : 100
                        "Calorie_OUT" : 120
                        "Countdown_BALANCE" : 46000
                        "Food_Item_CIF4_dummy_rice_4" : null
            ], //Closes CiF17[1]
            }, //Close CiF052[1]

                        "inhousecsvLinetext" : "csv_depopulate",

                        "Row_Type_fragment_type" : "Row_Type_fragment.Neither
                            ] // Closes CiF22 Array


                            } // JSON













                            /*
    "" : { "mFoodBox":

            [

    "Food_Item_CiF4[0]":{
        "id" : 1234567890,
                "mMeal_ID" : 768675746,
                "" : breakfast,
                "" : "Yoghurt Plus Probiotic : 600ml"
        "" : 100
        "" : 550
        "" : 1.8
        "" : 0.2
        "" : 0
        "" : 9.3
        "" : 7.8
        "" : 2
        "" : 0345
        "" : true
        "" :1.6
        "" : 1
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : 3
        "" : DRINK
        "" : 500g
        "" : 550
        "" : NA
        "" : 1
        "" : [ "Yogurt", "Probiotic", "Cultures"]
        "": 1001010101,
                "": "appletwo.src"
    },

    "Food_Item_CiF4[1]":

    {
        "id" : 1234567890,
            "mMeal_ID" : 768675746,
            "food_type" : breakfast,
            "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
        "grams_per_serving_portion" : 100
        "calories_per_100g" : 550
        "fat_per_100g" : 1.8
        "saturated_fat" : 0.2
        "trans_fat" : 0
        "protein_per_100g" : 9.3
        "carbs_per_100g" : 7.8
        "sugar_per_100g" : 2
        "salt_per_100g" : 0345
        "wellbeing_index" : true
        "fiber" :1.6
        "price_sterling" : 1
        "polyunsaturated" : 3
        "monounsaturated" : 3
        "cholesterol_mg" : 3
        "sodium_mg" : 3
        "potassium_mg" : 3
        "vitamin_a_percent" : 3
        "vitamin_c_percent" : 3
        "calcium_percent" : 3
        "iron_percent" : 3
        "category" : DRINK
        "weight" : 500g
        "Calorie_Value" : 550
        "index" : NA
        "Quantity" : 1
        "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
        "Barcode": 1001010101,
            "Image File": "appletwo.src"
    },

    "Food_Item_CiF4[2]":

    {
        "id" : 1234567890,
            "mMeal_ID" : 768675746,
            "food_type" : breakfast,
            "food_item_name" : "Yoghurt Plus Probiotic : 600ml"
        "grams_per_serving_portion" : 100
        "calories_per_100g" : 550
        "fat_per_100g" : 1.8
        "saturated_fat" : 0.2
        "trans_fat" : 0
        "protein_per_100g" : 9.3
        "carbs_per_100g" : 7.8
        "sugar_per_100g" : 2
        "salt_per_100g" : 0345
        "wellbeing_index" : true
        "fiber" :1.6
        "price_sterling" : 1
        "polyunsaturated" : 3
        "monounsaturated" : 3
        "cholesterol_mg" : 3
        "sodium_mg" : 3
        "potassium_mg" : 3
        "vitamin_a_percent" : 3
        "vitamin_c_percent" : 3
        "calcium_percent" : 3
        "iron_percent" : 3
        "category" : DRINK
        "weight" : 500g
        "Calorie_Value" : 550
        "index" : NA
        "Quantity" : 1
        "reserve_food_item_name_list" : [ "Yogurt", "Probiotic", "Cultures"]
        "Barcode": 1001010101,
            "Image File": "appletwo.src"
    }
                ]*/










