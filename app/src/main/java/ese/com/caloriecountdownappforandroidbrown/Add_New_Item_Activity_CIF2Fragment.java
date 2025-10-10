package ese.com.caloriecountdownappforandroidbrown;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Add_New_Item_Activity_CIF2Fragment extends Fragment {

    private Button mAdd;

    public Add_New_Item_Activity_CIF2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add__new__item__activity__cif12, container, false);

        mAdd = (Button) v.findViewById(R.id.button10);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long out = Adding_New_Item_pressed();
                android.util.Log.d("Add New Item", new RoundingCIF13().IntToString((int) out));
            }
        });


        return v;
    }


    private long Adding_New_Item_pressed() {

        EditText editText40 = (EditText) getActivity().findViewById(R.id.edit_text40);
        EditText editText41 = (EditText) getActivity().findViewById(R.id.edit_text41);
        EditText editText42 = (EditText) getActivity().findViewById(R.id.edit_text42);
        EditText editText43 = (EditText) getActivity().findViewById(R.id.edit_text43);
        EditText editText44 = (EditText) getActivity().findViewById(R.id.edit_text44);
        EditText editText45 = (EditText) getActivity().findViewById(R.id.edit_text45);
        EditText editText46 = (EditText) getActivity().findViewById(R.id.edit_text46);
        EditText editText47 = (EditText) getActivity().findViewById(R.id.edit_text47);
        EditText editText48 = (EditText) getActivity().findViewById(R.id.edit_text48);
        EditText editText49 = (EditText) getActivity().findViewById(R.id.edit_text49);
        EditText editText50 = (EditText) getActivity().findViewById(R.id.edit_text50);
        EditText editText51 = (EditText) getActivity().findViewById(R.id.edit_text51);
        EditText editText52 = (EditText) getActivity().findViewById(R.id.edit_text52);
        EditText editText53 = (EditText) getActivity().findViewById(R.id.edit_text53);
        EditText editText54 = (EditText) getActivity().findViewById(R.id.edit_text54);
        EditText editText55 = (EditText) getActivity().findViewById(R.id.edit_text55);
        EditText editText56 = (EditText) getActivity().findViewById(R.id.edit_text56);
        EditText editText57 = (EditText) getActivity().findViewById(R.id.edit_text57);
        EditText editText58 = (EditText) getActivity().findViewById(R.id.edit_text58);
        EditText editText59 = (EditText) getActivity().findViewById(R.id.edit_text59);
        EditText editText60 = (EditText) getActivity().findViewById(R.id.edit_text60);
        EditText editText61 = (EditText) getActivity().findViewById(R.id.edit_text61);
        EditText editText62 = (EditText) getActivity().findViewById(R.id.edit_text62);

        Food_Item_CIF4 new_item = new Food_Item_CIF4();
        RoundingCIF13 rounding = new RoundingCIF13();


        if (!editText40.getText().toString().isEmpty()) {
            new_item.Set_food_item_name(editText40.getText().toString());
        }
        if (!editText41.getText().toString().isEmpty()) {
            new_item.Set_grams_per_serving_portion(rounding.StringToFloat(editText41.getText().toString()));
        }
        if (!editText42.getText().toString().isEmpty()) {
            new_item.Set_calories_per_100g(rounding.StringToFloat(editText42.getText().toString()));
        }
        if (!editText43.getText().toString().isEmpty()) {
            new_item.Set_fat_per_100g(rounding.StringToFloat(editText43.getText().toString()));
        }
        if (!editText44.getText().toString().isEmpty()) {
            new_item.Set_saturated_fat(rounding.StringToFloat(editText44.getText().toString()));
        }
        if (!editText45.getText().toString().isEmpty()) {
            new_item.Set_trans_fat(rounding.StringToFloat(editText45.getText().toString()));
        }
        if (!editText46.getText().toString().isEmpty()) {
            new_item.Set_protein_per_100g(rounding.StringToFloat(editText46.getText().toString()));
        }
        if (!editText47.getText().toString().isEmpty()) {
            new_item.Set_carbs_per_100g(rounding.StringToFloat(editText47.getText().toString()));
        }
        if (!editText48.getText().toString().isEmpty()) {
            new_item.Set_sugar_per_100g(rounding.StringToFloat(editText48.getText().toString()));
        }
        if (!editText49.getText().toString().isEmpty()) {
            new_item.Set_salt_per_100g(rounding.StringToFloat(editText49.getText().toString()));
        }
        if (!editText50.getText().toString().isEmpty()) {
            new_item.Set_wellbeing_index(new RoundingCIF13().StringToBool(editText50.getText().toString()));
        }

        if (!editText51.getText().toString().isEmpty()) {
            new_item.Set_fiber(rounding.StringToFloat(editText51.getText().toString()));
        }
        if (!editText52.getText().toString().isEmpty()) {
            new_item.Set_price_sterling(rounding.StringToFloat(editText52.getText().toString()));
        }
        if (!editText53.getText().toString().isEmpty()) {
            new_item.Set_category(editText53.getText().toString());
        }
        if (!editText54.getText().toString().isEmpty()) {
            new_item.Set_polyunsaturated(rounding.StringToFloat(editText54.getText().toString()));
        }
        if (!editText55.getText().toString().isEmpty()) {
            new_item.Set_monounsaturated(rounding.StringToFloat(editText55.getText().toString()));
        }
        if (!editText56.getText().toString().isEmpty()) {
            new_item.Set_cholesterol_mg(rounding.StringToFloat(editText56.getText().toString()));
        }
        if (!editText57.getText().toString().isEmpty()) {
            new_item.Set_sodium_mg(rounding.StringToFloat(editText57.getText().toString()));
        }
        if (!editText58.getText().toString().isEmpty()) {
            new_item.Set_potassium_mg(rounding.StringToFloat(editText58.getText().toString()));
        }
        if (!editText59.getText().toString().isEmpty()) {
            new_item.Set_vitamin_a_percent(rounding.StringToFloat(editText59.getText().toString()));
        }
        if (!editText60.getText().toString().isEmpty()) {
            new_item.Set_vitamin_c_percent(rounding.StringToFloat(editText60.getText().toString()));
        }
        if (!editText61.getText().toString().isEmpty()) {
            new_item.Set_calcium_percent(rounding.StringToFloat(editText61.getText().toString()));
        }
        if (!editText62.getText().toString().isEmpty()) {
            new_item.Set_iron_percent(rounding.StringToFloat(editText62.getText().toString()));
        }

        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());

//        return database.Insert_Food_Item_Row(new_item);
        long localRowId = database.Insert_Food_Item_Row(new_item);
        Log.e("ADD FOOD", "Data sent to local food row " + localRowId);

        // Step 4: Check internet connection
        if (NetworkUtil.isInternetAvailable(getActivity())) {
            // Step 5: Prepare data map for backend
            Map<String, Object> foodData = new HashMap<>();
            foodData.put("food_item_name", new_item.Get_food_item_name());
            foodData.put("grams_per_serving", new_item.Get_grams_per_serving_portion());
            foodData.put("calories_per_100g", new_item.Get_calories_per_100g());
            foodData.put("fat_per_100g", new_item.Get_fat_per_100g());
            foodData.put("saturated_fat", new_item.Get_saturated_fat());
            foodData.put("trans_fat", new_item.Get_trans_fat());
            foodData.put("protein_per_100g", new_item.Get_protein_per_100g());
            foodData.put("carbs_per_100g", new_item.Get_carbs_per_100g());
            foodData.put("sugar_per_100g", new_item.Get_sugar_per_100g());
            foodData.put("salt_per_100g", new_item.Get_salt_per_100g());
//            foodData.put("wellbeing_index", new_item.Get_wellbeing_index());
            foodData.put("fiber", new_item.Get_fiber());
            foodData.put("price_sterling", new_item.Get_price_sterling());
            foodData.put("category", new_item.Get_category());
            foodData.put("polyunsaturated", new_item.Get_polyunsaturated());
            foodData.put("monounsaturated", new_item.Get_monounsaturated());
            foodData.put("cholesterol_mg", new_item.Get_cholesterol_mg());
            foodData.put("sodium_mg", new_item.Get_sodium_mg());
            foodData.put("potassium_mg", new_item.Get_potassium_mg());
            foodData.put("vitamin_a_percent", new_item.Get_vitamin_a_percent());
            foodData.put("vitamin_c_percent", new_item.Get_vitamin_c_percent());
            foodData.put("calcium_percent", new_item.Get_calcium_percent());
            foodData.put("iron_percent", new_item.Get_iron_percent());

            // Step 6: Call your API client (assume Kotlin interoperability)
            SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(getActivity());
            apiClient.addFoodItem(foodData, new ApiResultCallback() {
                @Override
                public void onFailure() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clearAllFields();
                                Toast.makeText(getActivity(), "Data added locally (backend sync failed)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onSuccess(@Nullable String response) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clearAllFields();
                                Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } else {
            Log.w("Network", "⚠️ Offline mode: saved only locally (RowID: " + localRowId + ")");
            // Clear fields and show toast in offline mode
            clearAllFields();
            Toast.makeText(getActivity(), "Data added locally (offline)", Toast.LENGTH_SHORT).show();
        }

        return localRowId;
    }

    private void clearAllFields() {
        if (getActivity() != null) {
            EditText editText40 = (EditText) getActivity().findViewById(R.id.edit_text40);
            EditText editText41 = (EditText) getActivity().findViewById(R.id.edit_text41);
            EditText editText42 = (EditText) getActivity().findViewById(R.id.edit_text42);
            EditText editText43 = (EditText) getActivity().findViewById(R.id.edit_text43);
            EditText editText44 = (EditText) getActivity().findViewById(R.id.edit_text44);
            EditText editText45 = (EditText) getActivity().findViewById(R.id.edit_text45);
            EditText editText46 = (EditText) getActivity().findViewById(R.id.edit_text46);
            EditText editText47 = (EditText) getActivity().findViewById(R.id.edit_text47);
            EditText editText48 = (EditText) getActivity().findViewById(R.id.edit_text48);
            EditText editText49 = (EditText) getActivity().findViewById(R.id.edit_text49);
            EditText editText50 = (EditText) getActivity().findViewById(R.id.edit_text50);
            EditText editText51 = (EditText) getActivity().findViewById(R.id.edit_text51);
            EditText editText52 = (EditText) getActivity().findViewById(R.id.edit_text52);
            EditText editText53 = (EditText) getActivity().findViewById(R.id.edit_text53);
            EditText editText54 = (EditText) getActivity().findViewById(R.id.edit_text54);
            EditText editText55 = (EditText) getActivity().findViewById(R.id.edit_text55);
            EditText editText56 = (EditText) getActivity().findViewById(R.id.edit_text56);
            EditText editText57 = (EditText) getActivity().findViewById(R.id.edit_text57);
            EditText editText58 = (EditText) getActivity().findViewById(R.id.edit_text58);
            EditText editText59 = (EditText) getActivity().findViewById(R.id.edit_text59);
            EditText editText60 = (EditText) getActivity().findViewById(R.id.edit_text60);
            EditText editText61 = (EditText) getActivity().findViewById(R.id.edit_text61);
            EditText editText62 = (EditText) getActivity().findViewById(R.id.edit_text62);

            editText40.setText("");
            editText41.setText("");
            editText42.setText("");
            editText43.setText("");
            editText44.setText("");
            editText45.setText("");
            editText46.setText("");
            editText47.setText("");
            editText48.setText("");
            editText49.setText("");
            editText50.setText("");
            editText51.setText("");
            editText52.setText("");
            editText53.setText("");
            editText54.setText("");
            editText55.setText("");
            editText56.setText("");
            editText57.setText("");
            editText58.setText("");
            editText59.setText("");
            editText60.setText("");
            editText61.setText("");
            editText62.setText("");
        }
    }

    public long add_JSON_OBJ_TO_CIF6(JSON_Object INPUT) {
        final int i = 1;
        return i;
    }

    public long add_Transactions_CiF22_To_CiF6(Transactions_CIF22 Input_new_item) {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());
        return 0;
        //return database.Insert_Food_Item_Row(Input_new_item.get_Food_items_or_items_Kotlin_1_5_0());

        //transform Object0001 to CiF22 and from CiF22 to Food items/list of foodiems, to be added
        //directly to final function below and research database for new and missing item Credit as
        //normal to the end, PUSH, back to folder start using app in Beta -> £6.52
    }

    public long add_Transactions_CiF22_To_CiF6(java.util.ArrayList<Food_Item_CIF4> add) {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());

        for (Food_Item_CIF4 m : add) {
            database.Insert_Food_Item_Row(m);
        }

        return 0;
    }

}

