package ese.com.caloriecountdownappforandroidbrown;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ESE on 07/10/2015.
 */
public class Populate_SQLDatabase_Food_Items_CIF7
{



    //or Constructor or OnCreate

    //Use a JSON File to load inshousecsv.txt from file to food_items table and back from food items table to inhouse?

    String filepath;

    String Data;
    String Volume1;
    String Volume2;
    String Volume3;
    String Volume4;
    String Volume5;
    String Volume6;
    String Volume7;
    String Volume1a;
    String Volume2a;
    String Volume3a;
    String Volume3c;
    String Volume4a;
    String Volume5a;
    String Volume6a;
    String Volume7a;
    String Volume4ff;
    String[] Data_Volume;

    Context mContext;
    Display_Dialog_CIF11 display;

    public Populate_SQLDatabase_Food_Items_CIF7(String filename, String d, Context m)
    {
        filepath = filename;
        mContext = m;
        display = new Display_Dialog_CIF11();
        display.Set_mAppContext(mContext);
        Data = d;
    }

    public Populate_SQLDatabase_Food_Items_CIF7(Context m)
    {
        filepath = "filpath";
        mContext = m;
        display = new Display_Dialog_CIF11();
        display.Set_mAppContext(mContext);



        Data = mContext.getResources().getString(R.string.Volume1a);
        Volume1 = mContext.getResources().getString(R.string.Volume1);
        Volume2 = mContext.getResources().getString(R.string.Volume2);
        Volume3 = mContext.getResources().getString(R.string.Volume3);
        Volume4 = mContext.getResources().getString(R.string.Volume3a);
        Volume5 = mContext.getResources().getString(R.string.Volume3d);
        Volume6 = mContext.getResources().getString(R.string.Volume4to2);
        Volume7 = mContext.getResources().getString(R.string.Volume4f);
        Volume1a = mContext.getResources().getString(R.string.Volume4d);
        Volume2a = mContext.getResources().getString(R.string.Volume4e);
        Volume3c = mContext.getResources().getString(R.string.Volume3c);
        Volume4ff = mContext.getResources().getString(R.string.Volume4ff);

        Data_Volume = new String[12];


        Data_Volume[0] = Volume1;
        Data_Volume[1] = Volume2;
        Data_Volume[2] = Volume3;
        Data_Volume[3] = Volume4;
        Data_Volume[4] = Volume5;
        Data_Volume[5] = Volume6;
        Data_Volume[6] = Volume7;
        Data_Volume[7] = Volume1a;
        Data_Volume[8] = Volume2a;
        Data_Volume[9] = Volume3c;
        Data_Volume[10] = Volume4ff;
        //Data_Volume[9] = "End_of_File";




        //Data = "ING,Tesco Cornish Pasty,200,234,31.5,,,9,31.5,2.3,1.25,N,1.5,2\n" +
        //        "ING,Cadbury egg,100,170,6,,,,,,,N,,\n" +
        //        "ING,Lindt Chocolate bunny solid 1.75 ounces,100,298,18,,,,,,,N,,\n" +
        //            "ING,Dove dark chocolate eggs 4,100,148,9.2,,,,,,,N,,\n";

        android.util.Log.d("getResources Contents", Volume1);


    }


    public boolean Populate()
    {
        //Inflate and Populate SQL Food Item Database from File (name/inhousecsv.txt) translating //then->
        return ImportXLSData2RMS(filepath);
        //Delete table first in database if Populate called


    }


    public boolean PopulateSampleData()
    {
        return true;
    }


    public boolean ImportXLSData2RMS(String filepath)
    {
        display.Showing("Please be patient, this could take several minutes, once thousands of items are imported you won't have to do this operation again");
        // DeleteAllRecordStores();
        if(this.Checker(filepath))
        {
            this.Parser(filepath);
            display.Showing("Data items Imported");
            return true;
        }
        else
        {
            // System.out.println("Sorry Checking Data File returned false, nothing is going to happen, bye bye.");
            display.Showing("Please Confirm Data Import");
            return false;
        }

    }

    private boolean Checker(String csvfilepath)
    {
        //System.out.println("Inside checker");
        String[] inhousecsv = {""};
        inhousecsv = GetInHouseCSV(csvfilepath);

try {
    //System.out.println(inhousecsv);

    if (inhousecsv == null) {
        //System.out.println("Get in house null");
        display.Showing("Upload of data file did not work");
        return false;
    } else {
        //System.out.println("get in house good");
        //for(int x = 0; x < inhousecsv.length(); x++)
        //    { System.out.println(inhousecsv.charAt(x));
        //    }
        if (true)//(inhousecsv.startsWith("\n") && inhousecsv.endsWith("\n")))
        {

            //System.out.println("in house starts with \n");
            for (int c = 0; c < inhousecsv.length; c++) {
                while (inhousecsv[c].length() > 0 ) {
                    String cut = this.CutFirstNextSubstring(inhousecsv[c]);
                    inhousecsv[c] = TrunkTrunk(inhousecsv[c], cut);

                    android.util.Log.d("inhousecsv[c]", inhousecsv[c]);

                    //System.out.println("this is string cut: "+ cut );
                    if (cut == "\n") {
                        android.util.Log.d("True Cut", "True cut True cut True cut");
                    }
                    //  System.out.println(inhousecsv);


                    if (cut.length() < 3) ;




                    else {
                        //System.out.println("checking sub code");

                        if (this.CheckSubSCode(cut)) {
                            android.util.Log.d("CheckSubCode", cut);
                            if (this.CheckSubSComma(cut)) {
                                //System.out.println("checking parse sub");
                                android.util.Log.d("CheckSubSComma", cut);
                                this.ParseSubS(cut);
                                android.util.Log.d("ParseSubS", cut);
                                //return true;
                            } else {
                                //System.out.println("checking new integer comma");
                                Integer inter = new Integer(1);
                                String row = inter.toString();
                                display.Showing("Continue importing.. ");// +
                                //row); //test with errors
                                return false;
                            }

                        } else {
                            //System.out.println("checking new integer category error");
                            Integer inter = new Integer(1);
                            String row = inter.toString();
                            //display.Showing("Formatting error with Category code, check Row: " +
                            //        row); //test with errors
                            display.Showing("Everything's fine. " +
                                    row); //test with errors
                            return false;

                        }
                    }
                }
            }
        }


        //1. check that file begins and ends with newline if not display message with this error require formating
        //2. check there is more than newline between the first and the last
        // 3a. check that there are characters between the first newline and the next one and so on and
        // 3b. if there are check that there are between 0 -13 commas no more

        //4. check that after each \n is a character code matching one of the five codes if no characters between newline ignore and move on the function
        //5. if still good turn into a parser and cut substrings between \n and next \n keep note of positon for cutting
        //6. find out what the code is and add it to correct recordstore using addpuredata function. You're done.
        //7. function extract substring \n\n in loop pass to feild function, do the checks, pass to parser for pure add in same loop
        //8. When raw raw extracted from record and turned into string substring in according to delimeter , into fields that match
        //   xls header hence cif - item attribute you're get/setting it in.
        //9. to reverse construnt string with category code append comma then attribute contents in right order, pure add to right
        //   record store or use add item or modified additem preffer pure add tho. and you're done. Use get name to display results

    }
}
catch (NumberFormatException e)
{
  display.Showing("Population Complete.");
}
catch (Exception e)
{

}

        if(inhousecsv.length == 0 )
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private String[] GetInHouseCSV(String filepath)
    {
        //java.io.InputStream is = getClass().getResourceAsStream(filepath);

        android.util.Log.d("Data Volume Contents", Data_Volume[3]);
        return Data_Volume;
        //try {

            //InputStream is = mContext.openFileInput(filepath);
            //File file = new File(filepath);
            //java.io.FileInputStream is = new FileInputStream(file.getAbsoluteFile().toString());

            //if (is == null)
            //{
            //    java.io.InputStreamReader isReader = new InputStreamReader(is);

                //java.io.FileReader filereader = new FileReader(new File("Volume1.txt"));
           //     java.io.BufferedReader bufferedReader = new BufferedReader(isReader);
           //     String receiveString = "";
           //     StringBuilder stringBuilder = new StringBuilder();

           //      while ((receiveString = bufferedReader.readLine()) != null)
            //      {
            //          stringBuilder.append(receiveString);
            //      }

            //     is.close();
            // ret = stringBuilder.toString();




                //StringBuffer csvdata = new StringBuffer();


                //int chars = 0;
                //int i = 0;

                //while ((chars = is.read()) != -1) {
                //    csvdata.append((char) chars);
                //}

                //return csvdata.toString();
           // }
           // return ret;
       // }
       // catch(FileNotFoundException a)
       // {
       //     display.Showing("File Was Not Found." + a.toString());
        //    return null;
        //}
        //catch(IOException b)
        //{
       //     display.Showing("File Access Error." + b.toString());
       //     return null;
        //}
        //catch(Exception c)
        //{
        //    display.Showing("General Error. " + c.toString());
        //    return null;
        //}



    }

    private void Parser(String path)
    {
        ;
    }

    private String CutFirstNextSubstring(String stream)
    {


        for(int p = 0; p  < stream.length(); p++)
        {

            if(stream.charAt(p) == '\n')
            {

                String cut = stream.substring(0,(p+1));

                return cut;

            }


        }
        return new String("");
    }

    private String TrunkTrunk(String ramoramo, String gone)
    {
        int len = gone.length();
        int ramolen = ramoramo.length();

        ramoramo = ramoramo.substring(len, ramolen);
        return ramoramo;

    }

    private boolean CheckSubSCode(String tream)
    {

        //if category code is not there goes auotmatically to ingredient recordstore i.e. manually reformat after error msg to ING category code.

        String commacut = CommaCutPlus(CommaCut(tream));

        android.util.Log.d("ratu6", commacut);



        if(commacut.charAt(0) == 'I' && commacut.charAt(1) == 'N' && commacut.charAt(2) == 'G')return true;
        if(commacut.charAt(0) == 'M' && commacut.charAt(1) == 'E' && commacut.charAt(2) == 'A' && commacut.charAt(3) == 'L') return true;
        if(commacut.charAt(0) == 'E' && commacut.charAt(1) == 'A' && commacut.charAt(2) == 'T')return true;
        if(commacut.charAt(0) == 'D' && commacut.charAt(1) == 'R' && commacut.charAt(2) == 'I' && commacut.charAt(3) == 'N' && commacut.charAt(4) == 'K') return true;
        if(commacut.charAt(0) == 'F' && commacut.charAt(1) == 'I' && commacut.charAt(2) == 'T') return true;
        if(commacut.charAt(0) == 'S' && commacut.charAt(1) == 'N' && commacut.charAt(2) == 'A' && commacut.charAt(3) == 'C' && commacut.charAt(4) == 'K') return true;
        if(commacut.charAt(0) == 'R' && commacut.charAt(1) == 'E' && commacut.charAt(2) == 'C' && commacut.charAt(3) == 'I' && commacut.charAt(4) == 'P' && commacut.charAt(5) == 'E') return true;
       // if(commacut.charAt(0) == 'E' && commacut.charAt(1) == 'A' && commacut.charAt(2) == 'T' && commacut.charAt(3) == 'O' && commacut.charAt(4) == 'U' && commacut.charAt(5) == 'T') return true;
        if(commacut.charAt(0) == 'X' && commacut.charAt(1) == 'R' && commacut.charAt(2) == 'W' && commacut.charAt(3) == 'G' && commacut.charAt(4) == 'L'&& commacut.charAt(5) == 'E' && commacut.charAt(6) == 'F')return true;
        else return false;


    }

    private boolean CheckSubSComma(String ream)
    {
        int count = 0;

        for(int c = 0; c < ream.length(); c++)
        {
            if(ream.charAt(c) == ',')count++;
        }

        if(count <= 13)
        {
            return true;
        }

        else return false;

    }

    private void ParseSubS(String eam)
    {
        //parse out category code if equal add pure data to ingredient recordstore data etc for all codes.
        //add string directly as a record to corresponding category code recordstore using add rawdataUI
        //add a clean \n to \n line to as record
        try
        {
            eam = CommaCutPlus(eam);
            String original = eam;
            String commacut = CommaCut(eam);

            if(commacut.charAt(0) == 'I' && commacut.charAt(1) == 'N' && commacut.charAt(2) == 'G')
            {
                MIF4_Data_Model_Adapter ingrec = new MIF4_Data_Model_Adapter(mContext);
                // if(ingrec.ingredientitemsrecordstorecif8.getNumRecords() < 0)
                //{
                //  Dialog.alert("Food Database already been populated are you sure you want import data? Yes no Yes no act not act FIX THIS TEST");
                //}
                ingrec.AddRawRecord(original);
                //System.out.println("Just added to Ingredient Rec " + original);
            }
            if(commacut.charAt(0) == 'M' && commacut.charAt(1) == 'E' && commacut.charAt(2) == 'A' && commacut.charAt(3) == 'L')
            {


                MIF4_Data_Model_Adapter ingrec = new MIF4_Data_Model_Adapter(mContext);
                ingrec.AddRawRecord(original);


                //MealItemsRecordStoreCiF7 mealrec = new MealItemsRecordStoreCiF7();
                //mealrec.AddRawRecord(original);
                //System.out.println("Just added to Meals Rec " + original);

            }
            if(commacut.charAt(0) == 'E' && commacut.charAt(1) == 'A' && commacut.charAt(2) == 'T')
            {
                MIF4_Data_Model_Adapter eatingoutrec = new MIF4_Data_Model_Adapter(mContext);
                eatingoutrec.AddRawRecord(original);
                //System.out.println("Just added to EatOut Rec " + original);
            }
            if(commacut.charAt(0) == 'D' && commacut.charAt(1) == 'R' && commacut.charAt(2) == 'I' && commacut.charAt(3) == 'N' && commacut.charAt(4) == 'K')
            {
                MIF4_Data_Model_Adapter ingrec = new MIF4_Data_Model_Adapter(mContext);
                ingrec.AddRawRecord(original);

                //DrinkRecordStoreCiF47 drinkingrec = new DrinkRecordStoreCiF47();
                //drinkingrec.AddRawRecord(original);
                //System.out.println("Just added to Drinks Rec " + original);
            }
            if(commacut.charAt(0) == 'F' && commacut.charAt(1) == 'I' && commacut.charAt(2) == 'T')
            {
                MIF4_Data_Model_Adapter fitrec = new MIF4_Data_Model_Adapter(mContext);
                fitrec.AddRawRecord(original);
                //System.out.println("Just added to Energy Rec " + original);
            }
            if(commacut.charAt(0) == 'R' && commacut.charAt(1) == 'E' && commacut.charAt(2) == 'C' && commacut.charAt(3) == 'I' && commacut.charAt(4) == 'P' && commacut.charAt(5) == 'E')
            {
                MIF4_Data_Model_Adapter ingrec = new MIF4_Data_Model_Adapter(mContext);
                ingrec.AddRawRecord(original);

                //DrinkRecordStoreCiF47 drinkingrec = new DrinkRecordStoreCiF47();
                //drinkingrec.AddRawRecord(original);
                //System.out.println("Just added to Drinks Rec " + original);
            }
            if(commacut.charAt(0) == 'X' && commacut.charAt(1) == 'R' && commacut.charAt(2) == 'W' && commacut.charAt(3) == 'G' && commacut.charAt(4) == 'L'&& commacut.charAt(5) == 'E' && commacut.charAt(6) == 'F')
            {
                End_Of_File();
            }

            //if(commacut.charAt(0) == 'E' && commacut.charAt(1) == 'A' && commacut.charAt(2) == 'T' && commacut.charAt(3) == 'O' && commacut.charAt(4) == 'U' && commacut.charAt(5) == 'T')
           // {
               // MIF4_Data_Model_Adapter ingrec = new MIF4_Data_Model_Adapter(mContext);
               // ingrec.AddRawRecord(original);

                //DrinkRecordStoreCiF47 drinkingrec = new DrinkRecordStoreCiF47();
                //drinkingrec.AddRawRecord(original);
                //System.out.println("Just added to Drinks Rec " + original);
            //}

        }
        catch(Exception c)
        {
            display.Showing("Exception caught: ");
        }

    }

    private String CommaCut(String ratu)
    {
        String cut;

        android.util.Log.d("ratu1", ratu);

        for(int p = 0; p  < ratu.length(); p++)
        {

            if(ratu.charAt(p) == ',' )
            {

                cut = ratu.substring(0,p);
                android.util.Log.d("ratu3", cut);


                return cut;

            }


        }
        android.util.Log.d("ratu4", ratu);
        return ratu;

    }

    private String CommaCutPlus(String ratu)
    {
        for(int p = 0; p  < ratu.length(); p++)
        {
            if (ratu.charAt(p) == '+') {
                ratu = ratu.substring((p + 2), ratu.length());
                android.util.Log.d("ratu2", ratu);
            }
        }

        return ratu;
    }


    public String Depopulate_Database()
    {
        //Documentation
        //This Public function returns a String contain all the contents of the SQLite Food and Exercise item Tables
        //in the same exact format as the inhousecsv.csv file


        //Black Office.Algorithm Engineering DOCUMENTATION

        //Here Check if the Food item the User is entering has already been entered and updated successfully in the past.
        //Every time the User Enters a successful transaction, that same transaction is also entered to the
        // Cache table
        //Before it is entered check that it is not already existing a copy of it, Exact copy? Similar Copy?
        //When Satisfied, enter.

        //Next time when we are in Fetch first match transaction/food item name to all Cache entries
        //If happy that a found Cache entry is right return it as fetched instead.

        //So Cache Box fragment function should always be done First, before proceeding in multi-search.

        //Really saves time and increase app's value.


        //Step 1
        //Create Cache Table (cache_table)
        //Cache Table should look exactly like the existing Food and Exercise Items, just a Copy of this table but with a different name to it.
        //That is the Food items ID Table
        //Create a new Table for Exercise items already in the String Resources
        //Exercise items should inherit for Energy_iten or Calorie_Value item or both.
        //Have a Corresponding Table for each of these inherited from, check if already there.
        //Identical
        //Find the Attributes that both Food item and Exercise item share
        //Both should inherit either or from Energy_item/or Calorie_Value item, or both, here in this table is where
        //" "
        //...you find their Core items.

        //This is how the app is able to search for Food and/or Exercise Debit items in EXACTLY THE SAME TABLE
        // (MyFitnessPal* & NutraCheck* (red) ESE S.C.I LTD in red CODESE*410 & Disney & Apple & Google & Tesla* & 2 (WWW.ESE-EDET.EU*)
        // These are writing Red.Number88&8 ESE8 exe 8 and all apps and fastic absorbed here and then to the Calorie Countdown Logo*&)

        //Now that you have exactly the same table use this to perform Cache


        //CODESE & Channel 410 & ACCA
        //ESE8exe
        //Call this Unifying Table A






        //For Table A go through all the Rows //iterate

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

        return new String("empty");

    }

    public boolean De_Populate_Database()
    {
        return true;
    }


    public void Delete_Database()
    {
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(mContext);
        jackie.Delete_Food_items_Table();
    }

    private void End_Of_File() throws NumberFormatException
    {
        throw new NumberFormatException();
    }


}
