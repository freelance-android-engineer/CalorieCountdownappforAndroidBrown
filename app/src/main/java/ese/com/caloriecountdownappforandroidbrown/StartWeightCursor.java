package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by ESE on 04/05/2016.
 */
public class StartWeightCursor  extends CursorWrapper {

//A Convenience clas to wrap a cursor that returns rows for the "food items" table.
    //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.

    private static final String TABLE_START_WEIGHT = "start_weight";
    private static final String COLUMUM_START_WEIGHT_ID = "start_weight_id";
    private static final String COLUMUM_START_WEIGHT_STARTWEIGHT = "startweight_startweight";

    public StartWeightCursor(Cursor c)
    {
        super(c);
    }

    public String getStartWeight()
    {


        if(isBeforeFirst() || isAfterLast())return "0";



        return new RoundingCIF13().IntToString(getInt(getColumnIndex(COLUMUM_START_WEIGHT_STARTWEIGHT)));


    }
}



//A Convenience clas to wrap a cursor that returns rows for the "food items" table.
    //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.







