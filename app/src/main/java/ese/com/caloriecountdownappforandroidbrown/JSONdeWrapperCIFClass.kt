package ese.com.caloriecountdownappforandroidbrown

class JSONdeWrapperCIFClass : Transaction_CIF22(), JSONdeWrapperCIFInterface
{
    var wrapper: JSONWrapperCIFClass? = null
        private set
    var deWrapped: Transaction_CIF22? = null
        private set

    fun Set_Wrapper(IN: JSONWrapperCIFClass?)
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        wrapper = IN
    }

    fun Set_deWrapp(IN: Transaction_CIF22?)
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        deWrapped = IN
    }

    fun deWrappingParser(DOCUMENT_IN: JSONWrapperCIFClass?): Transaction_CIF22
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        return Transaction_CIF22()
    }

    fun Wrap(IN: Transaction_CIF22?): JSONWrapperCIFClass
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        return JSONWrapperCIFClass(Transactions_CIF22())
    }

    override fun WrapTransactions_CIF22(INPUT: Transaction_CIF22?): String?
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        var output = "deWrapper's empty"
        return output
    }



    override fun UnWrapTransactions_CIF22(INPUT: String?): Transaction_CIF22?
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        return Transaction_CIF22()
    }

    fun UnWrapTransactions_CIF22(INPUT: String?, In: Transaction_CIF22): Transaction_CIF22
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        return In
    }

    fun UnWrapTransaction_CIF22(INPUT: Transaction_CIF22): Transaction_CIF22
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE

        return INPUT
    }

    fun parseWebPage(): List<JSONdeWrapperCIFClass> /*ArrayList<JSONdeWrapperCIFClass> */
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE
        //String Returns, String contains parsed JSON from Web page as you override URLoader,
        //so user clicks URL for righ specified myfitnesspal weblink
        //as the page is about to be loaded, override URL loader kicks in the job of the listener you provide is to:
        // load the page and use a text stream to download the string you see in Developer sourve view
        //save it to  string
        // see it as a single line of all that text you view
        //parse in a straight line and strip out all the jsons in, it
        //stripping out all the JSON in it produces a string representing each JSON
        //put each String in an Array<List of Strings>
        //return to the next function that take this array list as input and returns at array of JSONdeWrapperCIFClass which is this function.

        //More call in the model adapter pass it this output to special function
        //the model adapter for each JSONWrapper see Add Menuitem technology to add to SQLite, in this function each are deWrapped and
        //added to SQLiteCiF6 exactly as you memory&Tech Add New Food item
        //remember to show viewer the results of new food items added to the SQLite so the can make the right choice(s) of what to credit
        //in countdown report
        //user goes back and tries multi search again, the new items are now found, 18 years
        //and added as per Credit value normal
        //see web calculator
        //add <Webviewfitnesspal in right place>
        //& technology reuse*Kotlin refactor web calculator to (see) Google Search Engine browser/webview results of key word search in webview
        //and then override URLoader &

        TODO("Returns an ArrayList<String?> Containing JSON Objects, used to intialize JSONWrapper, ultimately to add new items and Client/User Searches again move on towards 0.0")
/*
        object JSON_WRAPPER_generator
        {
            data class JSONdeWrapperCIFClass(val "Category"; "EATOUT",
            "Category": "EATOUT",
            "Food item name": "Apple",
            "Portion or Serving Size": 100,
            "Calories": 100,
            "Fat": 10,
            "Saturates": 3,
            "Trans fat": 0,
            "Protein": 38,
            "Carbohydrares": 50,                Software
            "Sugar": 22,
            "Salt": 6,
            "Superfood": true,
            "Fibre": 8,
            "Price": 1,
            "Barcode": 1001010101,
            "Image File": "appletwo.src",
            "Weight in Grams": 100)

            //takes in JSON from myfitness webpaage and converts that as per previously to JSONdeWrapperCIF in previous class func and Brownprint
            //convert JSON from myfitness pal the JSONWrapper and pass json wrapper to model adapert deWrapp CiF2 and in an existing tech add new food item
            //PUSH -> (www.ese-edet.eu*)

            fun fromApiData(apiData: String): CharacterData
            {
                val(race, name, dex, wis, str) = apiData.split(",")
                return CharacterData(name, race, dex, wis, str)
            }
        }

 */

}

    fun convertJSONWrapperToCIF22(INPUT: JSONdeWrapperCIFClass): Transaction_CIF22
    {
        TODO()
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE
    }

    fun convertJSONWrapperArrayToCIF22Array(INPUT: ArrayList<JSONdeWrapperCIFClass>): ArrayList<Transaction_CIF22>
    {
        TODO()
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE
    }

    fun addNewFoodItems(INPUT: Transaction_CIF22)
    {
        //Algorithm Engineering -> Kotlin Documentation
        //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
        //Description :
        //STEP ONE
    }

    fun addNewFoodItemsArray(INPUT: ArrayList<Transaction_CIF22>)
    {
            //Algorithm Engineering -> Kotlin Documentation
            //Introduction : Takes in a Transaction_CIF22 Object and adds in to SQLite like 'Add New Food Item' Module.
            //Description :
            //STEP ONE
    }
}