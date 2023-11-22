package ese.com.caloriecountdownappforandroidbrown;


        import android.content.Intent;
        //import androidx.core.app.FragmentActivity;
        import androidx.fragment.app.FragmentActivity;
        //import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.textservice.SpellCheckerSession;
        import android.view.textservice.TextServicesManager;
        import android.widget.Button;
        import android.widget.EditText;
        import java.util.*;
        import android.util.Log;
        import android.content.Context;
        import android.view.textservice.SuggestionsInfo;
        import android.view.textservice.SentenceSuggestionsInfo;
        import android.view.textservice.TextInfo;

// Controller Class for Food Diary Screen/Credit/Menuitem

public class Food_Diary_Sheet_CIF3 extends FragmentActivity implements SpellCheckerSession.SpellCheckerSessionListener

{ //ActionBarActivity {


    private static int REQUEST_CODE_FOODITEMSLIST = 2;
    private static int REQUEST_CODE_ADD_NEW_ITEM = 3;
    private static int REQUEST_CODE_SPELLINGITEMSLIST = 4;
    private static int REQUEST_CODE_RECALIBRATE = 5;
    private static int REQUEST_CODE_JUSTADD = 6;
    private static final String TAG = "Calorie Countdown App";
    public static final String TOTAL_CREDIT_VALUE = "Total Credit Countup Value";
    public static final String SUMMATION_TEXT = "Report Summary";
    private final StringBuffer sb = new StringBuffer(" ");
    private String Stringsb;

    //Class Member Attributes and Properties Area///////////////////////////////////////////////////

    //1. Array of Food_Item_CIF4 (use m/a/s convention)
    private ArrayList<Food_Item_CIF4> mFoodItems;

    private Button mMultiSearchButton;
    private Button mCancel;
    private Button mCancel2;
    private Button mAddNewItem;
    private Button mRecalibrate;
    private Button mJustAdd;

    private Context mAppContext;

    private ArrayList<Food_Item_CIF4> FetchedFoodItems;
    private ArrayList<Food_Item_CIF4> resultList;
    private ArrayList<Food_Item_CIF4> resultList2 = new ArrayList<Food_Item_CIF4>();

    private Food_Item_CIF4 enter;
    private String OUTsb = "empty";
    private Merge_List_fragment_object mPivot = new Merge_List_fragment_object();

    private Runnable r;


    //Class Member Attributes and Propeties Area////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        android.util.Log.d("Pre Multi-Search", "number1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__diary__sheet__cif3);
        mFoodItems = null;


        //Test
        DayendTest();


        //Get Button 3 into Controller World.
        mMultiSearchButton = (Button) findViewById(R.id.button4);
        mCancel = (Button) findViewById(R.id.button8);
        mCancel2 = (Button) findViewById(R.id.button9);
        mJustAdd = (Button) findViewById(R.id.button6);
        mRecalibrate = (Button) findViewById(R.id.button11);
        mRecalibrate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               launch_recalibrate();
            }
        });
        mJustAdd.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {launch_JustAdd();}
        });
        mAddNewItem = (Button) findViewById(R.id.button3);
        mAddNewItem.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                launchAddNewFoodView();
            }
        });
        mCancel2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weaselpop();
            }

        });
        //Implement on Click Listener when button [Muiti]Search button clicked, extract food items from View World
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(FetchedFoodItems != null)
                {
                    SummaryBoxCIF12 summary_box = SummaryBoxCIF12.get(getApplicationContext());

                    summary_box.Set_mFoodItems(mFoodItems);


                    //summary_box.Add_Fooditem(new Food_Item_CIF4("Mocha Medium"));
                    //Save_Monthly_Journal_Transaction_CIF22(RecordMonthlyStatementData_Transaction_CIF22(summary_box));


                    BackToParentActivity(summary_box);
                }
                else
                {
                    weaselpop();
                }


            }
        });

        mMultiSearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                //Alogrithm Engineering :
                // For each textview per row extract Food_Item_CIF4
                //Builder :
                android.util.Log.d("Multi-search", "We are above Show Fetch a");
                mFoodItems = new ArrayList<Food_Item_CIF4>();
                android.util.Log.d("Multi-search", "We are above Show Fetch b");
                mFoodItems = ExtractFoodItems(mFoodItems);
                android.util.Log.d("Multi-search", "We are above Show Fetch c");
                FoodItemsLab_CIF9.get(getApplicationContext()).reset();
                android.util.Log.d("Multi-search", "We are above Show Fetch d");
                for(int c = 0; c < mFoodItems.size(); c++)
                {
                    //mPivot.merge(MultiSearch(mFoodItems.get(c)));
                    MultiSearch(mFoodItems.get(c));
                }

                android.util.Log.d("Multi-search", "We are above Show Fetch");

                mFoodItems = ShowFetch(mFoodItems);

                android.util.Log.d("Multi-search", "We are below Show Fetch");

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_diary_sheet_cif3, menu);
        return true;
    }

    @Override
    public boolean  onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ///////////////////////Controller/View Interface//////////////////////////////////////////////////////////

    ///////////////////////Black Office- Algorithm Engineering (red+ = Builder) *+ Partner.

    public void onGetSuggestions(SuggestionsInfo[] INPUT){}

    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] IN) {
        android.util.Log.d("MONEY SHOT", "If you are reading this it's all good");

        for (SentenceSuggestionsInfo result : IN) {
            int n = result.getSuggestionsCount();
            for (int i = 0; i < n; i++) {
                int m = result.getSuggestionsInfoAt(i).getSuggestionsCount();

                for (int k = 0; k < m; k++) {
                    sb.append(result.getSuggestionsInfoAt(i).getSuggestionAt(k))
                            .append("\n");

                    enter = new Food_Item_CIF4();
                    enter.Set_food_item_name(result.getSuggestionsInfoAt(i).getSuggestionAt(k));
                    resultList2.add(enter);
                }
                sb.append("\n");
            }
            //if((result.getSuggestionsInfoAt(i).getSuggestionsAttributes() &
                    //SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) != SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO )
                //continue;

            runOnUiThread(new Runnable() {
                public void run() {
                    android.util.Log.d("MUST BE", sb.toString());
                    DisplayContents(resultList2);
                }
            });
        }
    }








    //////////////Functions Area//////////////////////////////////////////////////////////////////////////////

    public void Add_Food_Item_CIF4(Food_Item_CIF4 foodItemCif4)
    {
        ;
    }


    private ArrayList<Food_Item_CIF4> ExtractFoodItems(ArrayList<Food_Item_CIF4> fooditemarray) {


        //Check to see if Meal Box has Food name if not, skip.
        //Get each view widget from View Layer to Controller Layer
        EditText editText6 = (EditText) this.findViewById(R.id.edit_text6);
        EditText editText7 = (EditText) this.findViewById(R.id.edit_text7);
        EditText editText8 = (EditText) this.findViewById(R.id.edit_text8);

        EditText editText9 = (EditText) this.findViewById(R.id.edit_text9);
        EditText editText10 = (EditText) this.findViewById(R.id.edit_text10);
        EditText editText11 = (EditText) this.findViewById(R.id.edit_text11);

        EditText editText12 = (EditText) this.findViewById(R.id.edit_text12);
        EditText editText13 = (EditText) this.findViewById(R.id.edit_text13);
        EditText editText14 = (EditText) this.findViewById(R.id.edit_text14);

        EditText editText15 = (EditText) this.findViewById(R.id.edit_text15);
        EditText editText16 = (EditText) this.findViewById(R.id.edit_text16);
        EditText editText17 = (EditText) this.findViewById(R.id.edit_text17);

        EditText editText18 = (EditText) this.findViewById(R.id.edit_text18);
        EditText editText19 = (EditText) this.findViewById(R.id.edit_text19);
        EditText editText20 = (EditText) this.findViewById(R.id.edit_text20);

        EditText editText21 = (EditText) this.findViewById(R.id.edit_text21);
        EditText editText22 = (EditText) this.findViewById(R.id.edit_text22);
        EditText editText23 = (EditText) this.findViewById(R.id.edit_text23);

        EditText editText24 = (EditText) this.findViewById(R.id.edit_text24);
        EditText editText25 = (EditText) this.findViewById(R.id.edit_text25);
        EditText editText26 = (EditText) this.findViewById(R.id.edit_text26);

        EditText editText27 = (EditText) this.findViewById(R.id.edit_text27);
        EditText editText28 = (EditText) this.findViewById(R.id.edit_text28);
        EditText editText29 = (EditText) this.findViewById(R.id.edit_text29);

        EditText editText30 = (EditText) this.findViewById(R.id.edit_text30);
        EditText editText31 = (EditText) this.findViewById(R.id.edit_text31);
        EditText editText32 = (EditText) this.findViewById(R.id.edit_text32);

        EditText editText33 = (EditText) this.findViewById(R.id.edit_text33);
        EditText editText34 = (EditText) this.findViewById(R.id.edit_text34);
        EditText editText35 = (EditText) this.findViewById(R.id.edit_text35);

        String playabout = editText12.getText().toString();
        Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();

        Food_Item_CIF4 fooditem = new Food_Item_CIF4();
        if (editText6.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText6.getText().toString());
            try
            {
                if (editText7.getText().toString().length() != 0)
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText7.getText().toString()));
                    fooditem.Set_food_item_name(editText7.getText().toString());
                if (editText8.getText().toString().length() != 0)
                    fooditem.Set_food_item_name(editText8.getText().toString());
                    //fooditem.Set_weight(display_dialog_cif11.changeType(editText8.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }



            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText9.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText9.getText().toString());
            try
            {
                if (editText10.getText().toString().length() != 0)
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText10.getText().toString()));
                    fooditem.Set_food_item_name(editText10.getText().toString());
                if (editText11.getText().toString().length() != 0)
                    fooditem.Set_food_item_name(editText11.getText().toString());
                //fooditem.Set_weight(display_dialog_cif11.changeType(editText11.getText().toString()));


            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText12.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText12.getText().toString());
            try
            {
                if (editText13.getText().toString().length() != 0)
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText13.getText().toString()));
                    fooditem.Set_food_item_name(editText13.getText().toString());
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText13.getText().toString()));
                if (editText14.getText().toString().length() != 0)
                    fooditem.Set_food_item_name(editText14.getText().toString());
                //fooditem.Set_weight(display_dialog_cif11.changeType(editText14.getText().toString()));


            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText15.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText15.getText().toString());
            try
            {
                if (editText16.getText().toString().length() != 0)
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText16.getText().toString()));
                    fooditem.Set_food_item_name(editText16.getText().toString());
                //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText13.getText().toString()));
                if (editText17.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText17.getText().toString()));

                if (editText16.getText().toString().length() != 0)
                    //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText16.getText().toString()));
                    fooditem.Set_food_item_name(editText16.getText().toString());
                //fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText13.getText().toString()));
                if (editText14.getText().toString().length() != 0)
                    fooditem.Set_food_item_name(editText14.getText().toString());
                //fooditem.Set_weight(display_dialog_cif11.changeType(editText14.getText().toString()));

                //i: need more rows and edit text at least 3
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText18.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText18.getText().toString());
            try
            {
                if (editText19.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText19.getText().toString()));
                if (editText20.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText20.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText21.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText21.getText().toString());
            try
            {
                if (editText22.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText22.getText().toString()));
                if (editText23.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText23.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText24.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText24.getText().toString());
            try
            {
                if (editText25.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText25.getText().toString()));
                if (editText26.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText26.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText27.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText27.getText().toString());
            try
            {
                if (editText28.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText28.getText().toString()));
                if (editText29.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText29.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText30.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText30.getText().toString());
            try
            {

                if (editText31.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText31.getText().toString()));
                if (editText32.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText32.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }


            fooditemarray.add(fooditem);
            fooditem = new Food_Item_CIF4();
        }

        if (editText33.getText().toString().length() != 0)
        {
            fooditem.Set_food_item_name(editText33.getText().toString());
            try
            {
                if (editText34.getText().toString().length() != 0)
                    fooditem.Set_calories_per_100g(display_dialog_cif11.changeTypeFloat(editText34.getText().toString()));
                if (editText35.getText().toString().length() != 0)
                    fooditem.Set_weight(display_dialog_cif11.changeType(editText35.getText().toString()));
            }
            catch (NumberFormatException noe)
            {
                display_dialog_cif11.WrongNumberFormatMsg();
            }

            fooditemarray.add(fooditem);
            return fooditemarray;

        }
        else return fooditemarray;


        //for each table row create CIF4 object and add to array
        //return array
        //If does have food item name create CIF4.
        //Use this.AddFoodItemCIF4 to add results from a function.

    }


    public ArrayList<SuggestionsInfo> SpellChecking(ArrayList<Food_Item_CIF4> INPUT)
    {

        Food_Item_CIF4 item;
        ArrayList<SuggestionsInfo> results = new ArrayList<SuggestionsInfo>();

        Spell_Checker_GranularFragment018 spell_checker = new Spell_Checker_GranularFragment018();

        Spell_Checker_GranularFragment018.Session sesh =  spell_checker.createSession();

         for(int m = 0; m < INPUT.size(); m++ )
        {
            item = INPUT.get(m);
            TextInfo words_check = new TextInfo(item.Get_food_item_name());

            SuggestionsInfo  suggest = sesh.onGetSuggestions(words_check, 5);

            results.add(suggest);
        }

        return results;

    }

    public String SpellChecker2(String Word)
    {
        android.util.Log.d("Spelling","Step in One");

        TextServicesManager tsm = (TextServicesManager) getSystemService(TEXT_SERVICES_MANAGER_SERVICE);

        android.util.Log.d("Spelling","Step in Two");

        SpellCheckerSession session = tsm.newSpellCheckerSession(null,Locale.ENGLISH,this, true);

        android.util.Log.d("Spelling","Step in Three");

        TextInfo INPUT = new TextInfo(Word);

        android.util.Log.d("Spelling","Step in Four");

        TextInfo[] InputSentence = new TextInfo[]{INPUT};

        android.util.Log.d("Spelling","Step in Five");

        session.getSentenceSuggestions(InputSentence,5);

        android.util.Log.d("Spelling","Step in Six");

        android.util.Log.d("Spelling sb equal",sb.toString());

        return sb.toString();




    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        super.onActivityResult(requestcode, resultcode, data);
        //add new item Food item CIF to results list is possible and return.
    }




    private ArrayList<Food_Item_CIF4> MultiSearch(Food_Item_CIF4 foodextracted)
    {

        //Alogrithm Engineering :
        // For each Food item in mFoodItems perform a Search
        //Builder :

        //Check if user already added Calories

        String Data = "empty";

        Data = this.getResources().getString(R.string.Volume2);

        android.util.Log.d("Contents of Resource DATA", Data);

        FetchedFoodItems = null;
        String message = "A typo was detected select from the list the word the best fits or click on 'Other' to either cancel or make your own suggestions";

        android.util.Log.d("Spelling", "Start of SpellChecker");

        Spelling_TextView_Fragment021 Spelling_Fragment = new Spelling_TextView_Fragment021();

        ArrayList<String> split_food_item_names = Spelling_Fragment.Delimit_Name_into_Words(foodextracted.Get_food_item_name());

        for(String m : split_food_item_names)
        {
            android.util.Log.d("Spellchecker", m);
            OUTsb = SpellChecker2(m);
            android.util.Log.d("Spellchecked", OUTsb);
            if(true)//if(OUTsb == " ")
            {
                break;
            }
            else
            {
                Display_Dialog_CIF11 display_dialog_cif11 = new Display_Dialog_CIF11();
                display_dialog_cif11.Set_mAppContext(this);
                display_dialog_cif11.Showing(message);

                 Stringsb = SpellChecker2(m);

                 //resultList = Split_Result_String_into_Words(Stringsb);

                FoodItemsLab_CIF9.get(getApplicationContext()).setResultsListStatic(resultList2);

                 //Shoot List View intent using resultList as input words to show.
                ShowFetchedSpellingSuggestions(resultList2);

            }

            foodextracted = Reconstruct_Food_item_name(FoodItemsLab_CIF9.get(getApplicationContext()).getNewWord(), foodextracted);
        }

        //android.util.Log.d("Spelling", ((SuggestionsInfo)OUT.get(1)).getSuggestionAt(1));

        //android.util.Log.d("Spelling2", sb.toString());

        //Get_Correct_Food_Item_Name(foodextracted);


        if(hasUserAddedCalories(foodextracted))
        {
            //if so add calories to Specific Calories attribute
            //timestamp summary class, string or array with info to be summed with food item name
            //log into food diary table
            // Seperate update other half to Sum total value
            //update balance background put food item back in its position in array continue with loop
        }
        else
        {
            //GoogleSearch the Food items name, if no Calories.
            //Return set of similar matches
            FetchedFoodItems = GoogleSearch(foodextracted, getApplicationContext());
            FetchedFoodItems = MissingItems(FetchedFoodItems);
            //Food_Item_CIF4 checking = FetchedFoodItems.get(0);
            //Place data in Singleton Storage, for all.
            FoodItemsLab_CIF9.get(getApplicationContext()).appendmFoodItems(FetchedFoodItems);

        }

        return FoodItemsLab_CIF9.get(getApplicationContext()).getmFoodItems();
    }



    private boolean hasUserAddedCalories(Food_Item_CIF4 foodie)
    {
        return false;
    }

    private ArrayList<Food_Item_CIF4> GoogleSearch(Food_Item_CIF4 foodfetch, Context c)
    {

        //return array of food items from database via Controller Database Adapter matching food item name

        MIF4_Data_Model_Adapter dama = new MIF4_Data_Model_Adapter(foodfetch,c);
        return dama.FetchMatches();


    }

    private void weaselpop()
    {
        setResult(1);
        finish();
    }

    private ArrayList<Food_Item_CIF4> ShowFetch(ArrayList<Food_Item_CIF4> mfd)
    {
        android.util.Log.d("Multi-search", "We are IN Show Fetch");

        Monthly_Statement_Recorded_Tracked_FoodItems();

        android.util.Log.d("Multi-search", "We are below Monthly_Statement");
        //List Select right food item.
        //Food_Item_CIF4 emptyShell= new Food_Item_CIF4();
        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, FoodItemsListActivity_CIF7.class);

        android.util.Log.d("Multi-search", "We are below Intent");


        startActivityForResult(i,REQUEST_CODE_FOODITEMSLIST);

        android.util.Log.d("Multi-search", "We have left for Results");




        //Log.d("Calorie Countdown", "Results of GoogleSearch FetchedFoodItems : " + checking.Get_food_item_name());

        //ListView this list in a new dialog new layout or fragment
        //User Select right food item if there can search or add then just add after search if not there
        //if not weight information dialog as presenting the average weight of portion size guide if info avaliable.
        //Calculate Calories put in Specific Calorie attribute
        //update summary file
        // update countdown balance, repeat to end.


        //Or have a function at the end of loop takes in array and updates and adds specific Calorie to Countdown Balance, Summary and Food Diary
        // and query for gaps, guestimate, search, add new in a dialog



        return FoodItemsLab_CIF9.get(getApplicationContext()).getmFoodItems();


        //Do for each L
    }

    private void CatString(String display)
    {
        Log.d(TAG,display);
    }

    private void CatInt(int display)
    {
        Log.d(TAG, new RoundingCIF13().IntToString(display));
    }

    private void BackToParentActivity(SummaryBoxCIF12 summary_box)
    {

        android.util.Log.d("Sending it Home", "Position 1");

        //return intent call with intent packed with value of Debit or Credit and Summary String as well as summarybox ready
        Intent i2 = new Intent();
        if(summary_box.GetListingsTotalCaloriesIN() == null)
        {
            i2.putExtra(TOTAL_CREDIT_VALUE, 0);
            Log.d(TAG,"Calories In is null Baby!");
        }
        else
        {
            i2.putExtra(TOTAL_CREDIT_VALUE, new RoundingCIF13().StringToInt(summary_box.GetListingsTotalCaloriesIN()));
            if (summary_box.GetSummaryString() == null)
            {
                i2.putExtra(SUMMATION_TEXT, "ese-edet.eu");
                Log.d(TAG,"Summation is null Baby!");
            }
            i2.putExtra(SUMMATION_TEXT, summary_box.GetSummaryString());
            setResult(RESULT_OK, i2);
            Log.d(TAG,"Everything is Cool Baby! " + summary_box.GetDebitSummaryString());
            finish();
        }
    }



    private void launchAddNewFoodView()
    {
        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, Add_New_Item_Activity_CIF12.class);
        startActivityForResult(i,REQUEST_CODE_ADD_NEW_ITEM);
    }



    private void launch_recalibrate()
    {
        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, Recalibrate.class);
        startActivityForResult(i, REQUEST_CODE_RECALIBRATE);
    }

    private void launch_JustAdd()
    {
        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, Raw_Calories.class);
        startActivityForResult(i, REQUEST_CODE_JUSTADD);
    }


    private Food_Item_CIF4 launchAddNewFoodView(Food_Item_CIF4 M)
    {
        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, Add_New_Item_Activity_CIF12.class);
        startActivityForResult(i,REQUEST_CODE_ADD_NEW_ITEM);
        return new Food_Item_CIF4();
    }


    private void RecordTrackedFoodItems(Breakfast_Box_CIF17 FoodItemBox)
    {
        MIF4_Data_Model_Adapter Data_Adapter = new MIF4_Data_Model_Adapter(mAppContext);

        /*
        get the time of the day
        if less than noon InsertBreakfastTransaction
        if less than 4pm but greater than noon
        if less greater than 4pm InsertDinnerTransaction
        */

        Breakfast_Transaction_CIF22 bt = new Breakfast_Transaction_CIF22();
        bt.Set_BreakfastBox(FoodItemBox);
        Data_Adapter.InsertBreakfastTransaction(bt);
    }



    private void RecordMonthlyStatementData_Summary_Box(SummaryBoxCIF12 summary_box)//(Lunch_Box_CIF17 FoodItemBox)
    {
        MIF4_Data_Model_Adapter Data_Adapter = new MIF4_Data_Model_Adapter(mAppContext);
        //Lunch_Transaction_CIF20 lt = new Lunch_Transaction_CIF20();
        //lt.Set_Lunch_Box(FoodItemBox);
        //Data_Adapter.InsertLunchTransaction(lt);

        Transaction_Line_CIF17 monthly_data_transaction = new Transaction_Line_CIF17();
        monthly_data_transaction.Set_Transaction_DateX(new Date().getTime());
        monthly_data_transaction.Set_Transaction_Meal_Type(return_meal_type());
        monthly_data_transaction.Set_Description(summary_box.GetSummaryString());
        monthly_data_transaction.Set_Calorie_IN(summary_box.GetCreditValue());
        monthly_data_transaction.Set_Calorie_Outwards(summary_box.GetDebitValue());
        monthly_data_transaction.Set_New_Balance(new RoundingCIF13().StringToInt(summary_box.GetNewBalance2()));
        monthly_data_transaction.Set_Transaction_Food_Items(Transform2BoxCIF(summary_box.Get_mFoodItems()));
        try
        {
            monthly_data_transaction.Get_Transaction_Food_Items().Set_Balance(new RoundingCIF13().StringToInt((summary_box.Get_mCurrentBalance())));
            monthly_data_transaction.Get_Transaction_Food_Items().Set_Balance(new RoundingCIF13().StringToInt((summary_box.GetNewBalance2())));

        }
        catch (Exception e)
        {
            Log.d(TAG,"Error in setting current balance in Box " + e.getMessage());
        }

        Data_Adapter.Insert_Transaction_CIF17(monthly_data_transaction);

    }

    private Transactions_CIF22 RecordMonthlyStatementData_Transaction_CIF22(SummaryBoxCIF12 summary_box)//(Lunch_Box_CIF17 FoodItemBox)
    {
        MIF4_Data_Model_Adapter Data_Adapter = new MIF4_Data_Model_Adapter(mAppContext);

        Transaction_Line_CIF17 monthly_data_transaction = new Transaction_Line_CIF17();
        monthly_data_transaction.Set_Transaction_DateX(new Date().getTime());
        monthly_data_transaction.Set_Transaction_Meal_Type(return_meal_type());
        monthly_data_transaction.Set_Description(summary_box.GetSummaryString());
        monthly_data_transaction.Set_Calorie_IN(summary_box.GetCreditValue());
        monthly_data_transaction.Set_Calorie_Outwards(summary_box.GetDebitValue());
        monthly_data_transaction.Set_New_Balance(new RoundingCIF13().StringToInt(summary_box.GetNewBalance2()));

        monthly_data_transaction.Set_Food_Item_List(summary_box.Get_mFoodItems());

        Transaction_CIF52 transaction_cif52 = new Transaction_CIF52(monthly_data_transaction);
        Transactions_CIF22 transactions_cif22 = new Transactions_CIF22();
        transactions_cif22.Add_TransactionLine_2_List(transaction_cif52);
        return transactions_cif22;


    }

    private ArrayList<Food_Item_CIF4> RecordMonthlyStatementData_Food_Item_Array(SummaryBoxCIF12 summary_box)//(Lunch_Box_CIF17 FoodItemBox)
    {
        return new ArrayList<Food_Item_CIF4>();

    }

    private Food_Item_CIF4 RecordMonthlyStatementData(SummaryBoxCIF12 summary_box)//(Lunch_Box_CIF17 FoodItemBox)
    {

        return new Food_Item_CIF4();
    }

    private void RecordMonthlyStatementData_Food_Item(Food_Item_CIF4 food_item_cif4)//(Lunch_Box_CIF17 FoodItemBox)
    {
        ;

    }

    private Transaction_Line_CIF17 RecordMonthlyStatementData(Food_Item_CIF4 food_item_cif4)//(Lunch_Box_CIF17 FoodItemBox)
    {
        return new Transaction_Line_CIF17();

    }

    private void RecordMonthlyStatementData_Food_Item_Array(ArrayList<Food_Item_CIF4> IN)//(Lunch_Box_CIF17 FoodItemBox)
    {
        ;

    }

    private Transaction_Line_CIF17 RecordMonthlyStatementData(ArrayList<Food_Item_CIF4> IN)//(Lunch_Box_CIF17 FoodItemBox)
    {
        return new Transaction_Line_CIF17();

    }

    private void RecordTrackedFoodItems(Dinner_Box_CIF17 FoodItemBox)
    {
        MIF4_Data_Model_Adapter Data_Adapter = new MIF4_Data_Model_Adapter(mAppContext);
        Dinner_Transaction_CIF21 dt = new Dinner_Transaction_CIF21();
        dt.Set_DinnerBox(FoodItemBox);
        Data_Adapter.InsertDinnerTransaction(dt);
    }

    private void Monthly_Statement_Recorded_Tracked_FoodItems()
    {

    }

    private void Add_Food_Item_Transaction_to_Data_Model(Transactions_CIF22 INPUT)
    {
        ;
    }



    private String return_meal_type()
    {
        //Returns Meal Type based on Time of Day.
        return "Brunch";
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

    private long Save_Monthly_Journal_Database(ArrayList<Food_Item_CIF4> OUT)
    {
        Transaction_CIF52 a = new Transaction_CIF52();
        Transactions_CIF22 b = new Transactions_CIF22();
        Transaction_Line_CIF17 c = new Transaction_Line_CIF17();
        SQLDatabase_Food_Items_CIF6 jackie = new SQLDatabase_Food_Items_CIF6(this);

        long Transaction_ID = jackie.Get_Unique_Row_Identifier_from_Transactions_Table();
        long Transaction_date = Get_Todays_Date_Long();
        String Meal_type_x = Get_time_of_day_meal(new Date());
        int Meal_type_x_ID = jackie.Get_Unique_Row_Identifier_from_Transaction_Table();
        int Amount = Sum_Food_Items_Calories(OUT);
        int Balance = See_Dialog_String_on_how_to_get_Balance();
        BoxCIF17 Meal_Transaction_Box = Transform_to_Box(OUT);

        String Description = Meal_type_x + " Transactions Food Journal items.";
        int Joules_OUT = 0;
        int Joules_IN = Amount;
        int Bal_balance = See_Dialog_String_on_how_to_get_Balance_bfwd(Amount, Balance);

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

        return 1;
    }

    private int See_Dialog_String_on_how_to_get_Balance()
    {
        //Summary_Box is a Singleton, get a Copy of the Singelton and get the Current Balance from it and add to this Object.
        SummaryBoxCIF12 summary = SummaryBoxCIF12.get (mAppContext);
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

    private ArrayList<Food_Item_CIF4> MissingItems(ArrayList<Food_Item_CIF4> IN)
    {
        Food_Item_CIF4 newitem;

        for (Food_Item_CIF4 m : IN)
        {
            if(m.Get_food_item_name() == "empty")
            {
                //Launch Add New Item Sheet after results shown
                //m = launchAddNewFoodView(m);

                launchAddNewFoodView();
            }
        }

        return IN;
    }

    private Food_Item_CIF4 Get_Correct_Food_Item_Name(Food_Item_CIF4 INPUT)
    {
        //If no results returned, do nothing.
        //If there are results, inform user there and errors and ask to select correct one.

        // Approved for Sale (Production/Beta, 3917 B * G = Xero Money and congratulation you have achieved weight, final countdown wav file played in at 10000 or less and congratulation graphic at zero)
        // -> www.ese-edet.eu /(Publish with units all 1000+ or till Green Satisfied third party invoive www.ese-edet.eu/ weightlossapp

        //Return updated Food_Item_CIF4 with Correct Spelling.

        if(INPUT.Get_food_item_name() == " ")
        {
            return INPUT;
        }
        else
        {
            return INPUT;
        }


    }


    private ArrayList<Food_Item_CIF4> Split_Result_String_into_Words(String INPUT)
    {
        //Algorithm Engineering -> android -> (re)Load -> www.ese-edet.eu

        //Step Three : Declare ArrayList<String>

        ArrayList<Food_Item_CIF4> Delimited = new ArrayList<Food_Item_CIF4>();
        String word;
        Food_Item_CIF4 CasedWord = new Food_Item_CIF4();

        //Step One : Declare StringTokenizer
        //Step Two : Delimeter with a " " space.
        //Step Four : Add Delimetered String Words to Array but if length = 1 char discard ignore.

        StringTokenizer st = new StringTokenizer(INPUT,"\n", false);
        while (st.hasMoreTokens())
        {
            word = st.nextToken();

            if(word.length() > 2)
            {
                CasedWord.Set_food_item_name(word);
                Delimited.add(CasedWord);
            }
        }

        //Step Five : Return ArrayList.
        return Delimited;
    }

    private void ShowFetchedSpellingSuggestions(ArrayList<Food_Item_CIF4> INPUT)
    {



        Intent i = new Intent(Food_Diary_Sheet_CIF3.this, Spelling_TextView_Frag_CIF7.class);


        startActivityForResult(i,REQUEST_CODE_SPELLINGITEMSLIST);




        //Log.d("Calorie Countdown", "Results of GoogleSearch FetchedFoodItems : " + checking.Get_food_item_name());

        //ListView this list in a new dialog new layout or fragment
        //User Select right food item if there can search or add then just add after search if not there
        //if not weight information dialog as presenting the average weight of portion size guide if info avaliable.
        //Calculate Calories put in Specific Calorie attribute
        //update summary file
        // update countdown balance, repeat to end.


        //Or have a function at the end of loop takes in array and updates and adds specific Calorie to Countdown Balance, Summary and Food Diary
        // and query for gaps, guestimate, search, add new in a dialog



        //return FoodItemsLab_CIF9.get(getApplicationContext()).getmFoodItems();


        //Do for each L

    }

    private Food_Item_CIF4 Reconstruct_Food_item_name(ArrayList<Food_Item_CIF4> INPUT, Food_Item_CIF4 UPDATE)
    {
        UPDATE.Set_food_item_name("Baked Beans");

        return UPDATE;
    }

    private void DisplayContents(ArrayList<Food_Item_CIF4> INPUT)
    {
        android.util.Log.d("Display Contents", "see is Split works");


        for(int x = 0; x < INPUT.size(); x++)
        {
            android.util.Log.d("In Split, word : " , INPUT.get(x).Get_food_item_name());
        }
    }


    private void DayendTest()
    {
        CCD_GUI_CD_CIF1 instance = (CCD_GUI_CD_CIF1) mAppContext;
        if(instance != null)
        {
            instance.Start_Day_End();
        }
        else
            {
                android.util.Log.d("Day End", "Sorry, Instance was null this time");
            }
    }


}






