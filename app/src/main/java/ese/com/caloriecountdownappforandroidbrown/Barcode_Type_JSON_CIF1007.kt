package ese.com.caloriecountdownappforandroidbrown

import android.media.Image
import android.nfc.tech.NfcBarcode
import java.nio.file.Path

data class Barcode_Type_JSON_CIF1007(val foodItemPrice: Int, val barcode: NfcBarcode, val barcode2: Long, val image: Image, val imageFilePath: Path, val imageFilePath2: String)
{
    val message = " Hello Universe, from Calorie Countdown app for android Version 2.0.0"
}