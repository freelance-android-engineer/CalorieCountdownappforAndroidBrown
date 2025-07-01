package ese.com.caloriecountdownappforandroidbrown;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by ESE on 04/05/2016.
 */
public class StartDayCursor extends CursorWrapper {

//A Convenience clas to wrap a cursor that returns rows for the "food items" table.
    //The {@ link getFoods()} method will give you a Food_Item instance representing the current row.

    private static final String TABLE_START_DAY = "start_day";
    private static final String COLUMUM_START_DAY_ID = "start_day_id";
    private static final String COLUMUM_START_DAY_STARTDAY = "startday_starday";


    public StartDayCursor(Cursor c)
    {
        super(c);
    }

    public String getStartDay()
    {


        if(isBeforeFirst() || isAfterLast())return "0";



        return new RoundingCIF13().IntToString(getInt(getColumnIndex(COLUMUM_START_DAY_STARTDAY)));


    }
}









