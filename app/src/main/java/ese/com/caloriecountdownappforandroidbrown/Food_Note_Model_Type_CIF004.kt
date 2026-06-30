package ese.com.caloriecountdownappforandroidbrown

import com.google.firebase.Timestamp
import java.time.LocalDateTime

class Food_Note_Model_Type_CIF004 : Food_Item_CIF4()
{
    val foodnote: String = "half of a Tamaris"
    val qty = 0
    val caloriesBurnedRecord = 0
    //val timestamp: Timestamp = Timestamp(null, null, null)
    val timestamp : java.time.LocalDateTime = java.time.LocalDateTime.now()
    val percentageCertainity = 1.0

    val barcodeTypeJsonCif1007: Barcode_Type_JSON_CIF1007? = null

}