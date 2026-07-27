package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by lokeke on 04/05/2016.
 */
public class CurrentWeightCursor extends CursorWrapper {

//A Convenience clas to wrap a cursor that returns rows for the "food items" table.
    //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.

    private static final String TABLE_CURRENT_WEIGHT = "current_weight";
    private static final String COLUMUM_CURRENT_WEIGHT_ID = "current_weight_id";
    private static final String COLUMUM_CURRENT_WEIGHT_CURRENTWEIGHT = "current_weight";


    public CurrentWeightCursor(Cursor c)
    {
        super(c);
    }

    public String getCurrentWeight()
    {


        if(isBeforeFirst() || isAfterLast())return "0";



        return new RoundingCIF13().IntToString(getInt(getColumnIndex(COLUMUM_CURRENT_WEIGHT_CURRENTWEIGHT)));


    }
}

