package ese.com.caloriecountdownappforandroidbrown;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

/**
 * Created by ESE on 14/10/2015.
 */
public class  FoodItemsListActivity_CIF7 extends FragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_list_cif7);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer1);

        if(fragment == null)
        {
            fragment = new FoodItemsListFragment_CIF8();
            fm.beginTransaction().add(R.id.fragmentContainer1,fragment).commit();
        }

    }

}

