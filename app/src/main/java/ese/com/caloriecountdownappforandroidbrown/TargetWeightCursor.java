package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by lokeke on 04/05/2016.
 */
public class TargetWeightCursor extends CursorWrapper {

//A Convenience clas to wrap a cursor that returns rows for the "food items" table.
    //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.

    private static final String TABLE_TARGET_WEIGHT = "target_weight";
    private static final String COLUMUM_TARGET_WEIGHT_ID = "target_weight_id";
    private static final String COLUMUM_TARGET_WEIGHT_TARGETWEIGHT = "targetweight_targetweight";


    public TargetWeightCursor(Cursor c)
    {
        super(c);
    }

    public String getTargetWeight()
    {


        if(isBeforeFirst() || isAfterLast())return "0";



        return new RoundingCIF13().IntToString(getInt(getColumnIndex(COLUMUM_TARGET_WEIGHT_TARGETWEIGHT)));


    }
}

