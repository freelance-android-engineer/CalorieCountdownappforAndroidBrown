package ese.com.caloriecountdownappforandroidbrown

class NutritionalContentsFromWebPage(var countryCode: Intermediate_fragmentedObject_CiF0001) {


    var transactionCif22: SubClassTransactionsCIF22? = null

    private val jsonObject: JSON_Object_item = JSON_Object_item(countryCode)


    private lateinit var brandeg: String //lateint

    var brand: String = ""
    var deleted: String
        get() {  //add getter and setters
            return ""
        }
        set(value) {

        }

    var description: String = ""
    var id: String                   //introducing backing property
        get() = _id
        set(value) {
            _id = value
        }

    companion object {
        internal lateinit var nutritionalContents: Nutritional_Content_CiFragmented_Object001
    }

    init {
        transactionCif22 = jsonObject.jsonwrapper().shallow_transactionCif22
    }





    private var _idBacking: String = ""
    private var NutritionalContentsFromWebPage._id: String
        get() = _idBacking
        set(value) {
            _idBacking = value
        }

    fun getNutritional_Content_CiFragmented_Object_001(): Nutritional_Content_CiFragmented_Object001 {
        convert_nutritional_contents_from_web_String()
        return getCompanionObject()
    }

    private fun convert_nutritional_contents_from_web_String() {
        //Initialize Companion Object
        //do the conversion to is
    }

    private fun getCompanionObject(): Nutritional_Content_CiFragmented_Object001 = nutritionalContents //.  //?:.convert //is conversion done if true return if not don't and

}