package ese.com.caloriecountdownappforandroidbrown;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

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
        mAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long out = Adding_New_Item_pressed();
                android.util.Log.d("Add New Item", new RoundingCIF13().IntToString((int) out));
            }
        });


        return v;
    }



    private long Adding_New_Item_pressed()
    {

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

        if(editText40.getText().toString() != "")
        {
            new_item.Set_food_item_name(editText40.getText().toString());
        }
        if(editText41.getText().toString() != "")
        {
            new_item.Set_grams_per_serving_portion(new RoundingCIF13().StringToFloat(editText41.getText().toString()));
        }
        if(editText42.getText().toString() != "")
        {
            new_item.Set_calories_per_100g(new RoundingCIF13().StringToFloat(editText42.getText().toString()));
        }
        if(editText43.getText().toString() != "")
        {
            new_item.Set_fat_per_100g(new RoundingCIF13().StringToFloat(editText43.getText().toString()));
        }
        if(editText44.getText().toString() != "")
        {
            new_item.Set_saturated_fat(new RoundingCIF13().StringToFloat(editText44.getText().toString()));
        }
        if(editText45.getText().toString() != "")
        {
            new_item.Set_trans_fat(new RoundingCIF13().StringToFloat(editText45.getText().toString()));
        }
        if(editText46.getText().toString() != "")
        {
            new_item.Set_protein_per_100g(new RoundingCIF13().StringToFloat(editText46.getText().toString()));
        }
        if(editText47.getText().toString() != "")
        {
            new_item.Set_carbs_per_100g(new RoundingCIF13().StringToFloat(editText47.getText().toString()));
        }
        if(editText48.getText().toString() != "")
        {
            new_item.Set_sugar_per_100g(new RoundingCIF13().StringToFloat(editText48.getText().toString()));
        }
        if(editText49.getText().toString() != "")
        {
            new_item.Set_salt_per_100g(new RoundingCIF13().StringToFloat(editText49.getText().toString()));
        }
        if(editText50.getText().toString() != "")
        {
            new_item.Set_wellbeing_index(new RoundingCIF13().StringToBool(editText50.getText().toString()));
        }
        if(editText51.getText().toString() != "")
        {
            new_item.Set_fiber(new RoundingCIF13().StringToFloat(editText51.getText().toString()));
        }
        if(editText52.getText().toString() != "")
        {
            new_item.Set_price_sterling(new RoundingCIF13().StringToFloat(editText52.getText().toString()));
        }
        if(editText53.getText().toString() != "")
        {
            new_item.Set_category(editText53.getText().toString());
        }
        if(editText54.getText().toString() != "")
        {
            new_item.Set_polyunsaturated(new RoundingCIF13().StringToFloat(editText54.getText().toString()));
        }
        if(editText55.getText().toString() != "")
        {
            new_item.Set_monounsaturated(new RoundingCIF13().StringToFloat(editText55.getText().toString()));
        }
        if(editText56.getText().toString() != "")
        {
            new_item.Set_cholesterol_mg(new RoundingCIF13().StringToFloat(editText56.getText().toString()));
        }
        if(editText57.getText().toString() != "")
        {
            new_item.Set_sodium_mg(new RoundingCIF13().StringToFloat(editText57.getText().toString()));
        }
        if(editText58.getText().toString() != "")
        {
            new_item.Set_potassium_mg(new RoundingCIF13().StringToFloat(editText58.getText().toString()));
        }
        if(editText59.getText().toString() != "")
        {
            new_item.Set_vitamin_a_percent(new RoundingCIF13().StringToFloat(editText59.getText().toString()));
        }
        if(editText60.getText().toString() != "")
        {
            new_item.Set_vitamin_c_percent(new RoundingCIF13().StringToFloat(editText60.getText().toString()));
        }
        if(editText61.getText().toString() != "")
        {
            new_item.Set_calcium_percent(new RoundingCIF13().StringToFloat(editText61.getText().toString()));
        }
        if(editText62.getText().toString() != "")
        {
            new_item.Set_iron_percent(new RoundingCIF13().StringToFloat(editText62.getText().toString()));
        }

        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());
        return database.Insert_Food_Item_Row(new_item);
    }
    
    public long add_JSON_OBJ_TO_CIF6(JSON_Object INPUT)
    {
        final int i = 1;
        return i;
    }

    public long add_Transactions_CiF22_To_CiF6(Transactions_CIF22 Input_new_item)
    {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());
        return 0;
        //return database.Insert_Food_Item_Row(Input_new_item.get_Food_items_or_items_Kotlin_1_5_0());

        //transform Object0001 to CiF22 and from CiF22 to Food items/list of foodiems, to be added
        //directly to final function below and research database for new and missing item Credit as
        //normal to the end, PUSH, back to folder start using app in Beta -> £6.52
    }

    public long add_Transactions_CiF22_To_CiF6(java.util.ArrayList<Food_Item_CIF4> add)
    {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());

        for(Food_Item_CIF4 m : add)
        {
            database.Insert_Food_Item_Row(m);
        }

        return 0;
    }

}

