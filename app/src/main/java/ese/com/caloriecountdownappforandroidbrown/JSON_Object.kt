package ese.com.caloriecountdownappforandroidbrown

import ese.com.caloriecountdownappforandroidbrown.Nutritional_Content_CiFragmented_Object001
import android.net.Uri

import java.lang.Exception
import java.net.URL

class JSON_Object(private var webPage: Uri?) {

    //i-ESE-SOFTWARE-PIPELINE-CD-PUSH-WWW.ESE-EDET.EU*


    //Parse the given web Page up to the point where adding to CiF006 then input user message with results
    //showing all the new food items added to SQLite, make sure the one you are looking for red white is among
    //Click the re) Credit lost but found food item after clicking ok, do all that here.

    //Open File var
    //readline the file returns String List
    //Split the list using""
    //See file structure

    //private val parsedWebPageString: android.net.Uri = TODO()


    var transactionCif22:SubClassTransactionsCIF22? = null
    var shallow_transactionCif22:SubClassTransactionsCIF22? = null

    var goldenegg: JSON_Object? = null
    private var parsedWebPageString: String = webPage?.encodedPath ?: ""

    init {
        startParsing(parsedWebPageString)
    }

    private val brandNamex: String = "Brand Name"

    var unit = "grams"


    val `description`: String = "description"
        get() {
            return field //ACCA : Black
        }



    constructor(input:JSON_Object_item) : this(null)
    {
        shallow_transactionCif22 = SubClassTransactionsCIF22()

        shallow_transactionCif22!!.brand_name = input.brand_name
        shallow_transactionCif22!!.country_code = input.country_code
        shallow_transactionCif22!!.description = input.description
        shallow_transactionCif22!!.food_item_name = input.description + ": " + input.brand_name

        shallow_transactionCif22!!.Vitamin_c = input.Vitamin_c
        shallow_transactionCif22!!.TransFat = input.TransFat
        shallow_transactionCif22!!.Sodium = input.Sodium
        shallow_transactionCif22!!.Potassium = input.Potassium
        shallow_transactionCif22!!.PolyunsturatedFat = input.PolyunsturatedFat
        shallow_transactionCif22!!.MonounsaturatedFat = input.MonounsaturatedFat
        shallow_transactionCif22!!.Calcuim = input.Calcuim
        shallow_transactionCif22!!.id = input.id
        shallow_transactionCif22!!.Calories = input.Calories
        shallow_transactionCif22!!.Carbohydrates = input.Carbohydrates
        shallow_transactionCif22!!.Cholesterol = input.Cholesterol
        shallow_transactionCif22!!.Fat = input.Fat
        shallow_transactionCif22!!.Fiber= input.Fiber
        shallow_transactionCif22!!.Iron = input.Iron
        shallow_transactionCif22!!.Protein = input.Protein
        shallow_transactionCif22!!.Saturated_fat = input.Saturated_fat
        shallow_transactionCif22!!.Sugar = input.Sugar
        shallow_transactionCif22!!.Vitamin_a = input.Vitamin_a
        shallow_transactionCif22!!.ServingSizesUnit = input.ServingSizesUnit
        shallow_transactionCif22!!.ServingSizesValue = input.ServingSizesValue



    }

    private val nutritionContent: List<String> = listOf()//create a type that contains this JSON's property specifically
    //private val fillMePlease: JSONWrapperCIFClass
    //private val fillMe: JSONWrapper = JSONWrapper()
    //private var ncStrip: NutritionalContentsFromWebPage? = null
    //private var externalDataStructure: Nutritional_Content_CiFragmented_Object001? = null

    //create a type that contains this JSON's property specifically
    private var jsonWrapperCIFClass: List<JSONWrapper> = listOf()


    fun startParsing(input: String) {
        //Step 1a : Get String containing Web Page
        // parsedWebPageString = GetStringFromWebPage(this.webPage)

        //Step Two : Call all the functions to display_Results
        if(input != "")
        PerformMainFunction(input)
        //else
        // {
        //    TODOx("Intialize it! and perform Main Funcion")
    }


    fun PerformMainFunction(input: String) {
       // externalDataStructure = ProduceNCStrip(input)
        //var jsonwrappercifclass = fillMePlease(externalDataStructure)
        //addNewFoodItemsInJava(JSONWrapperCIFClass)
        //displayResultsDialog("Results perfect")
        transactionCif22 = SubClassTransactionsCIF22((fillMe(input)))
        //That's it! Client should go back to Multi Search and find these new items there.
    }


    //Helper Function Sections


    //private fun stripOutNutritionalContent(ncStrip: String): Nutritional_Content_CiFragmented_Object001 {
      //  return externalDataStructure
    //}


    //private fun ProduceNCStrip(webCaloriePage: String): Nutritional_Content_CiFragmented_Object001 {
    //    if (isEncodedFormat(webCaloriePage)) {

         //   var ncfromwebpage = fillMe(webCaloriePage)
         //   var ncobject = fillMeAgain(ncfromwebpage)

          //  return ncobject
        //} else {
        //    var ncobject: Nutritional_Content_CiFragmented_Object001 = Nutritional_Content_CiFragmented_Object001()
        //    return ncobject
        //}
    //}

   // private fun fillMeAgain(input: NutritionalContentsFromWebPage): Nutritional_Content_CiFragmented_Object001 {
   //
   // }
    private fun fillMe(web_Resource_Request: String): Nutritional_Content_CiFragmented_Object001
    {
        val url: String = web_Resource_Request
        val apiData: String = URL(url).readText()
        return moneyShotFunction(apiData,null,null)

    }
    // public fun returnJSONdeWrapperCIFClass(INPUT: Nutritional_Content_CiFragmented_Object001x):JSONdeWrapperCIFClass
    // {

    //  return fillMePlease
    //}
    //private fun fillMe(ncStrip: String, INPUT: Nutritional_Content_CiFragmented_Object001): Nutritional_Content_CiFragmented_Object001 {
      //  return INPUT
    //}
   // private fun stripNutritionalContentsFromWebPage(ncStrip: String): Nutritional_Content_CiFragmented_Object001 {
     //   returnExternalDataStructureFromNCString()
       // return externalDataStructure
    //}
    //private fun returnJSONdeWrapperCIFClass(INPUT: Nutritional_Content_CiFragmented_Object001): JSONdeWrapperCIFClass {
      //  returnJSONdeWrapperCIFClass()
    //}
    //private fun returnNCStripFromWebPage(webPageString: String): String {
    //}
    //private fun returnExternalDataStructureFromNCString(NCStrip: String): Nutritional_Content_CiFragmented_Object001 {
      //  TODO()
    //}

    private fun GetStringFromWebPage(input: Uri?): String {
        var webText = URL(webPage.toString()).readText()
        return webText
    }


    private fun moneyShotFunction(apiData: String, uri: Uri?, url: URL?): Nutritional_Content_CiFragmented_Object001 //throws
    {

        //iterate apiData see Pages

        var jsontextDummy = JSON_Object_item()
        var jsontext: JSON_Object_item = JSON_Object_item()
        val units: ArrayList<JSON_Object_item> = ArrayList()
        var textstring = apiData.split("item")

        if(textstring.size > 1)
        {
        for (text in textstring) {

            if (text.contains("brand_name", false)) {
                jsontext.brand_name = getBrandName(text)
                jsontext.description = getDescription(text)
                jsontext.country_code = getCountryCode(text)


            }

            if (text.contains("energy", false)) {
                try {
                    jsontext.Calories = RoundingCIF13().StringToDouble(getCalories(text))
                    jsontext.Calcuim = RoundingCIF13().StringToDouble(getCalcium(text))
                    jsontext.Carbohydrates = RoundingCIF13().StringToDouble(getCabohydrates(text))
                    jsontext.Cholesterol = RoundingCIF13().StringToDouble(getCholesterol(text))
                    jsontext.Fat = RoundingCIF13().StringToDouble(getFat(text))
                    jsontext.Fiber = RoundingCIF13().StringToDouble(getFiber(text))
                    jsontext.Iron = RoundingCIF13().StringToDouble(getIron(text))
                    jsontext.MonounsaturatedFat = RoundingCIF13().StringToDouble(getMonounsaturated_fat(text))
                    jsontext.PolyunsturatedFat = RoundingCIF13().StringToDouble(getPolyunsaturated_fat(text))
                    jsontext.Potassium = RoundingCIF13().StringToDouble(getPotassium(text))
                    jsontext.Protein = RoundingCIF13().StringToDouble(getProtein(text))
                    jsontext.Saturated_fat = RoundingCIF13().StringToDouble(getSaturated_fat(text))
                    jsontext.Sodium = RoundingCIF13().StringToDouble(getSodium(text))
                    jsontext.Sugar = RoundingCIF13().StringToDouble(getSugar(text))
                    jsontext.TransFat = RoundingCIF13().StringToDouble(getTrans_fat(text))
                    jsontext.Vitamin_a = RoundingCIF13().StringToDouble(getVitamin_A(text))
                    jsontext.Vitamin_c = RoundingCIF13().StringToDouble(getVitamin_C(text))
                } catch (e: Exception) {
                    println(e)
                }

            }
            if (text.contains("serving_sizes")) {
                jsontext.ServingSizesUnit = getServingSizeUnit(text)
                jsontext.ServingSizesValue = RoundingCIF13().StringToDouble(getServingSizeValue(text))
            }
        }

            units.add(jsontext)

        }
       // List<ncStrips> ncstrips = ListOfncstrips apiData.delimiterToken("/" item /"")
        val placid: NutritionalContentsFromWebPage = NutritionalContentsFromWebPage(Intermediate_fragmentedObject_CiF0001(units))

        if (!SomethingHappedToWebsite(uri, url)) {
            val pre_goldenegg = JSON_Object_item(apiData, null)
            goldenegg = money_fragment(pre_goldenegg)
        }

        val output:Nutritional_Content_CiFragmented_Object001 = Nutritional_Content_CiFragmented_Object001(placid)

        return output
    }
    //if something happened to the webs


    private fun SomethingHappedToWebsite(input: Uri?, inputted: URL?): Boolean {
        return false
    }


    private fun money_fragment(input: JSON_Object_item): JSON_Object {
        return input.jsonwrapper()
    }

    public fun displayResultsDialog(input:String)
    {
        println(input)
    }

    private fun isEncodedFormat(website:String):Boolean
    {
        return true;
    }

    //delimet ands trip this out:

    //nutritional_contents":{"energy":{"unit":"calories","value":240},"calcium":20,"carbohydrates":43,"cholesterol":20,"fat":8,"monounsaturated_fat":2,"potassium":260,"protein":5,"saturated_fat":5,"sodium":90,"sugar":38,"vitamin_a":4,"vitamin_c":4},

    //function to parse through webPage String and at right delimeter strip out value for above properties
    //red a Comparison (memory, always see what you use first before lifting a finger, new CODESE Software Developer red&Purple normae ampersand
    //use in String Class property's or functions of String to compare Likeness of description content to return a reading hence ranking
    //only at last resort if it's not there create.


    /*
    {"popularSearchResults":[


{"item":{"brand_name":"Key West Key Lime Pie Co.","country_code":"US","deleted":false,"description":"Key Lime Pie","id":32583658,"nutritional_contents":{"energy":{"unit":"calories","value":240},"calcium":20,"carbohydrates":43,"cholesterol":20,"fat":8,"monounsaturated_fat":2,"potassium":260,"protein":5,"saturated_fat":5,"sodium":90,"sugar":38,"vitamin_a":4,"vitamin_c":4},"public":true,"serving_sizes":[{"id":"18863110246077","index":1,"nutrition_multiplier":1,"unit":"Slice","value":1},{"id":"19412866059965","index":2,"nutrition_multiplier":8,"unit":"container (8 slices ea.)”,”value":1}],



“type”:”food","user_id":"19264713821245","verified":false,"version
”
:"58554919521709"},"tags":[],
"type":"food"},

INPUT :

{"item":{"brand_name":"Key Lime Pie Co","country_code":"US","deleted":false,"description":"Key Lime Pie Mini “,"id":962863801,"nutritional_contents":{"energy":{"unit":"calories","value":680},"calcium":0,"carbohydrates":106,"cholesterol":10,"fat":25,"fiber":0,"iron":0,"monounsaturated_fat":3,"polyunsaturated_fat":0,"potassium":0,"protein":10,"saturated_fat":14,"sodium":250,"sugar":91,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"194784849175981","index":0,"nutrition_multiplier":1,"unit":"gram","value":254},{"id":"195334604989869","index":1,"nutrition_multiplier":0.00393701,"unit":"gram","value":1},{"id":"194784840820013","index":2,"nutrition_multiplier":1.00451,"unit":"ounce","value":9},{"id":"194784840820141","index":3,"nutrition_multiplier":0.111612,"unit":"ounce","value":1}],"type":"food","user_id":"32029381971645","verified":false,"version":"66941279333685"},"tags":[],"type":"food"},

OUTPUT : JSON Object

INPUT :

{"item":{"brand_name":"Kermit's Key West Key Lime Pie Shoppe","country_code":"US","deleted":false,"description":"Key Lime Pie Candy","id":268489765,"nutritional_contents":{"energy":{"unit":"calories","value":60},"calcium":0,"carbohydrates":16,"cholesterol":0,"fat":0,"fiber":0,"iron":0,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":0,"saturated_fat":0,"sodium":0,"sugar":16,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"264853087301037","index":1,"nutrition_multiplier":1,"unit":"pcs (17g)","value":3},{"id":"264303339875629","index":2,"nutrition_multiplier":0.333333,"unit":"pcs (17g)","value":1},{"id":"264853095689517","index":3,"nutrition_multiplier":12,"unit":"container (36 pcs (17g))”,"value":1}],"type":"food","user_id":"102958427303085","verified":false,"version":"97750950498157"},"tags":[],"type":"food"},

OUTPUT : JSON Object Kotlin

{"item":{"brand_name":"Pie Guy  Key Lime Pie","country_code":"US","deleted":false,"description":"Key Lime Pie”,”id":314405409,"nutritional_contents":{"energy":{"unit":"calories","value":310},"calcium":8,"carbohydrates":36,"cholesterol":95,"fat":17,"fiber":1,"iron":2,"protein":4,"saturated_fat":9,"sodium":115,"sugar":27,"vitamin_a":10,"vitamin_c":2},"public":true,"serving_sizes":[{"id":"88132463406525","index":0,"nutrition_multiplier":1,"unit":"oz.","value":3},{"id":"88682219220413","index":1,"nutrition_multiplier":0.333333,"unit":"oz.","value":1},{"id":"88132471795005","index":2,"nutrition_multiplier":0.011758,"unit":"gram","value":1},{"id":"88682227608893","index":3,"nutrition_multiplier":0.99943,"unit":"gram","value":85}],"type":"food","user_id":"93893948116733","verified":false,"version":"22838123654717"},"tags":[],"type":"food"},

OUTPUT : JSON Object Kotlin


{"item":{"brand_name":"Florida Key Lime Pie","country_code":"US","deleted":false,"description":"Key Lime Pie","id":41439232,"nutritional_contents":{"energy":{"unit":"calories","value":320},"calcium":25,"carbohydrates":59,"cholesterol":110,"fat":13,"fiber":1,"iron":6,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":9,"saturated_fat":7,"sodium":200,"sugar":54,"trans_fat":0,"vitamin_a":8,"vitamin_c":4},"public":true,"serving_sizes":[{"id":"124396807073085","index":1,"nutrition_multiplier":1,"unit":"Piece","value":1},{"id":"124946562886973","index":2,"nutrition_multiplier":10,"unit":"container (10 pieces ea.)”,”value":1}],"type":"food","user_id":"23783019638077","verified":false,"version":"102552720666221"},"tags":[],"type":"food"},

OUTPUT : JSON Object Kotlin

{"item":{"brand_name":"IGA key lime pie","country_code":"CA","deleted":false,"description":"key lime pie","id":148090562,"nutritional_contents":{"energy":{"unit":"calories","value":117},"calcium":0,"carbohydrates":0,"cholesterol":0,"fat":0,"fiber":0,"iron":0,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":0,"saturated_fat":0,"sodium":0,"sugar":0,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"204397370740141","index":1,"nutrition_multiplier":1,"unit":"piece","value":1},{"id":"203847606570285","index":2,"nutrition_multiplier":10,"unit":"container (10.00 piece)”,”value":1}],"type":"food","user_id":"274772184108333","verified":false,"version":"53908037520813"},"tags":[],"type":"food"}],


OUTPUT : JSON_Object Kotlin

//Put on JSON Objects in an ArrayList<JSON_Object> and return ArrayList<>.
     */


    fun addNewFoodItemsInJava(extractedFoodItems: ArrayList<JSONWrapper>) {

    }

    private fun getCalories(text: String): String {
        var bindex = 0
        var calories = ""

        text.forEach { Letter ->
            if (Letter == 'C' || Letter == 'c') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'c' || ch == 'C') {
                            if (text[(text.indexOf(ch)) + 1] == 'a') {
                                if (text[(text.indexOf(ch)) + 2] == 'l') {
                                    if (text[(text.indexOf(ch)) + 3] == 'o') {
                                        if (text[(text.indexOf(ch)) + 4] == 'r') {
                                            if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                    if (text[(text.indexOf(ch)) + 7] == 's')
                                                        calories = text.substring((bindex + 11), text.indexOf('}', (bindex + 11), true))

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (calories.last() == '}')
            return calories.substringBefore('}', "")
        else {
            return calories
        }

    }

    private fun getBrandName(text: String): String {
        var bindex = 0
        var brandName = ""

        text.forEach { Letter ->
            if (Letter == 'B' || Letter == 'b') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'b' || ch == 'B') {
                            if (text[(text.indexOf(ch)) + 1] == 'r') {
                                if (text[(text.indexOf(ch)) + 2] == 'a') {
                                    if (text[(text.indexOf(ch)) + 3] == 'n') {
                                        if (text[(text.indexOf(ch)) + 4] == 'd') {
                                            if (text[(text.indexOf(ch)) + 5] == '_') {
                                                if (text[(text.indexOf(ch)) + 6] == 'n') {
                                                    if (text[(text.indexOf(ch)) + 7] == 'a') {
                                                        if (text[(text.indexOf(ch)) + 7] == 'm') {
                                                            if (text[(text.indexOf(ch)) + 7] == 'e')
                                                                brandName = text.substring((bindex + 13), text.indexOf('"', (bindex + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (brandName.last() == '"')
            return brandName.substringBefore('"', "")
        else {
            return brandName
        }

    }

    private fun getDescription(text: String): String {
        var bindex = 0
        var description = ""

        text.forEach { Letter ->
            if (Letter == 'D' || Letter == 'd') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'd' || ch == 'D') {
                            if (text[(text.indexOf(ch)) + 1] == 'e') {
                                if (text[(text.indexOf(ch)) + 2] == 's') {
                                    if (text[(text.indexOf(ch)) + 3] == 'c') {
                                        if (text[(text.indexOf(ch)) + 4] == 'r') {
                                            if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                if (text[(text.indexOf(ch)) + 6] == 'p') {
                                                    if (text[(text.indexOf(ch)) + 7] == 't') {
                                                        if (text[(text.indexOf(ch)) + 7] == 'i') {
                                                            if (text[(text.indexOf(ch)) + 7] == 'o') {
                                                                if (text[(text.indexOf(ch)) + 7] == 'n')
                                                                    description = text.substring((bindex + 14), text.indexOf('"', (bindex + 14), true))

                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (description.last() == '"')
            return description.substringBefore('"', "")
        else {
            return description
        }

    }


    private fun getCountryCode(text: String): String {
        var bindex = 0
        var countryCode = ""

        text.forEach { Letter ->
            if (Letter == 'C' || Letter == 'c') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'c' || ch == 'C') {
                            if (text[(text.indexOf(ch)) + 1] == 'a') {
                                if (text[(text.indexOf(ch)) + 2] == 'l') {
                                    if (text[(text.indexOf(ch)) + 3] == 'o') {
                                        if (text[(text.indexOf(ch)) + 4] == 'r') {
                                            if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                    if (text[(text.indexOf(ch)) + 7] == 's')
                                                        countryCode = text.substring((bindex + 13), text.indexOf('"', (bindex + 13), true))

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (countryCode.last() == '"')
            return countryCode.substringBefore('"', "")
        else {
            return countryCode
        }

    }


    private fun getCalcium(text: String): String {
        var bindex = 0
        var calcium = ""

        text.forEach { Letter ->
            if (Letter == 'C' || Letter == 'c') {
                bindex = text.indexOf(Letter)

                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'c' || ch == 'C') {
                            if (text[(text.indexOf(ch)) + 1] == 'a') {
                                if (text[(text.indexOf(ch)) + 2] == 'l') {
                                    if (text[(text.indexOf(ch)) + 3] == 'c') {
                                        if (text[(text.indexOf(ch)) + 4] == 'i') {
                                            if (text[(text.indexOf(ch)) + 5] == 'u') {
                                                if (text[(text.indexOf(ch)) + 6] == 'm')
                                                    calcium = text.substring((bindex + 13), text.indexOf('"', (bindex + 15), true))

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (calcium.last() == ',')
            return calcium.substringBefore(',', "")
        else
        {
            return calcium
        }

    }


    private fun getCabohydrates(text: String): String {
        var bindex = 0
        var cabohydrates = ""

        text.forEach { Letter ->
            if (Letter == 'C' || Letter == 'c') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'c' || ch == 'C') {
                            if (text[(text.indexOf(ch)) + 1] == 'a') {
                                if (text[(text.indexOf(ch)) + 2] == 'b') {
                                    if (text[(text.indexOf(ch)) + 3] == 'o') {
                                        if (text[(text.indexOf(ch)) + 4] == 'h') {
                                            if (text[(text.indexOf(ch)) + 5] == 'y') {
                                                if (text[(text.indexOf(ch)) + 6] == 'd') {
                                                    if (text[(text.indexOf(ch)) + 7] == 'r') {
                                                        if (text[(text.indexOf(ch)) + 7] == 'a') {
                                                            if (text[(text.indexOf(ch)) + 7] == 't') {
                                                                if (text[(text.indexOf(ch)) + 7] == 'e') {
                                                                    if (text[(text.indexOf(ch)) + 7] == 's')

                                                                        cabohydrates = text.substring((bindex + 15), text.indexOf(',', (bindex + 13), true))

                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if(cabohydrates.last() == ',')
            return cabohydrates.substringBefore(',',"")
        else
        {
            return cabohydrates
        }

    }

    private fun getCholesterol(text:String):String {
        var bindex = 0
        var cholesterol = ""

        text.forEach { Letter ->
            if (Letter == 'C' || Letter == 'c') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'c' || ch == 'C') {
                            if (text[(text.indexOf(ch)) + 1] == 'h') {
                                if (text[(text.indexOf(ch)) + 2] == 'o') {
                                    if (text[(text.indexOf(ch)) + 3] == 'l') {
                                        if (text[(text.indexOf(ch)) + 4] == 'e') {
                                            if (text[(text.indexOf(ch)) + 5] == 's') {
                                                if (text[(text.indexOf(ch)) + 6] == 't') {
                                                    if (text[(text.indexOf(ch)) + 7] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 'r') {
                                                            if (text[(text.indexOf(ch)) + 7] == 'o') {
                                                                if (text[(text.indexOf(ch)) + 7] == 'l')
                                                                    cholesterol = text.substring((bindex + 13), text.indexOf(',', (bindex + 13), true))
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

            if (cholesterol.last() == ',')
                return cholesterol.substringBefore(',', "")
            else {
                return cholesterol
            }

        }


        private fun getFat(text: String): String {
            var bindex = 0
            var fat = ""

            text.forEach { Letter ->
                if (Letter == 'f' || Letter == 'F') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'f' || ch == 'F') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 't')
                                        fat = text.substring((bindex + 5), text.indexOf(',', (bindex + 5), true))

                                }
                            }
                        }
                    }
                }
            }


            if (fat.last() == ',')
                return fat.substringBefore(',', "")
            else {
                return fat
            }

        }

        private fun getFiber(text: String): String {
            var bindex = 0
            var fiber = ""

            text.forEach { Letter ->
                if (Letter == 'f' || Letter == 'F') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'f' || ch == 'F') {
                                if (text[(text.indexOf(ch)) + 1] == 'i') {
                                    if (text[(text.indexOf(ch)) + 2] == 'b') {
                                        if (text[(text.indexOf(ch)) + 3] == 'e') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r')
                                                fiber = text.substring((bindex + 7), text.indexOf(',', (bindex + 7), true))


                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (fiber.last() == ',')
                return fiber.substringBefore(',', "")
            else {
                return fiber
            }

        }

        private fun getIron(text: String): String {
            var bindex = 0
            var iron = ""

            text.forEach { Letter ->
                if (Letter == 'i' || Letter == 'I') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'i' || ch == 'I') {
                                if (text[(text.indexOf(ch)) + 1] == 'r') {
                                    if (text[(text.indexOf(ch)) + 2] == 'o') {
                                        if (text[(text.indexOf(ch)) + 3] == 'n')
                                            iron = text.substring((bindex + 6), text.indexOf(',', (bindex + 6), true))

                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (iron.last() == ',')
                return iron.substringBefore(',', "")
            else {
                return iron
            }

        }

        private fun getMonounsaturated_fat(text: String): String {
            var bindex = 0
            var mono = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            mono = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (mono.last() == '"')
                return mono.substringBefore('"', "")
            else {
                return mono
            }

        }

        private fun getPolyunsaturated_fat(text: String): String {
            var bindex = 0
            var poly = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            poly = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (poly.last() == '"')
                return poly.substringBefore('"', "")
            else {
                return poly
            }

        }

        private fun getPotassium(text: String): String {
            var bindex = 0
            var potassium = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            potassium = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (potassium.last() == '"')
                return potassium.substringBefore('"', "")
            else {
                return potassium
            }

        }

        private fun getProtein(text: String): String {
            var bindex = 0
            var protein = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            protein = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (protein.last() == '"')
                return protein.substringBefore('"', "")
            else {
                return protein
            }

        }

        private fun getSaturated_fat(text: String): String {
            var bindex = 0
            var sat = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            sat = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (sat.last() == '"')
                return sat.substringBefore('"', "")
            else {
                return sat
            }

        }

        private fun getSodium(text: String): String {
            var bindex = 0
            var sodium = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            sodium = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (sodium.last() == '"')
                return sodium.substringBefore('"', "")
            else {
                return sodium
            }

        }

        private fun getSugar(text: String): String {
            var bindex = 0
            var sugar = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            sugar = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (sugar.last() == '"')
                return sugar.substringBefore('"', "")
            else {
                return sugar
            }

        }

        private fun getTrans_fat(text: String): String {
            var bindex = 0
            var trans = ""

            text.forEach { Letter ->
                if (Letter == 'C' || Letter == 'c') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'c' || ch == 'C') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'o') {
                                            if (text[(text.indexOf(ch)) + 4] == 'r') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'e') {
                                                        if (text[(text.indexOf(ch)) + 7] == 's') {
                                                            var index = text.indexOf(ch)
                                                            trans = text.substring((index + 13), text.indexOf('"', (index + 13), true))
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (trans.last() == '"')
                return trans.substringBefore('"', "")
            else {
                return trans
            }

        }

        private fun getVitamin_A(text: String): String {
            var bindex = 0
            var vitamina = ""

            text.forEach { Letter ->
                if (Letter == 'v' || Letter == 'V') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'v' || ch == 'V') {
                                if (text[(text.indexOf(ch)) + 1] == 'i') {
                                    if (text[(text.indexOf(ch)) + 2] == 't') {
                                        if (text[(text.indexOf(ch)) + 3] == 'a') {
                                            if (text[(text.indexOf(ch)) + 4] == 'm') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'n') {
                                                        if (text[(text.indexOf(ch)) + 7] == '_') {
                                                            if (text[(text.indexOf(ch)) + 7] == 'a')
                                                                vitamina = text.substring((bindex + 11), text.indexOf(',', (bindex + 11), true))

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (vitamina.last() == ',')
                return vitamina.substringBefore(',', "")
            else {
                return vitamina
            }

        }

        private fun getVitamin_C(text: String): String {
            var bindex = 0
            var vitaminc = ""

            text.forEach { Letter ->
                if (Letter == 'v' || Letter == 'V') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'v' || ch == 'V') {
                                if (text[(text.indexOf(ch)) + 1] == 'i') {
                                    if (text[(text.indexOf(ch)) + 2] == 't') {
                                        if (text[(text.indexOf(ch)) + 3] == 'a') {
                                            if (text[(text.indexOf(ch)) + 4] == 'm') {
                                                if (text[(text.indexOf(ch)) + 5] == 'i') {
                                                    if (text[(text.indexOf(ch)) + 6] == 'n') {
                                                        if (text[(text.indexOf(ch)) + 7] == '_') {
                                                            if (text[(text.indexOf(ch)) + 7] == 'c') {
                                                                vitaminc = text.substring((bindex + 11), text.indexOf('}', (bindex + 11), true))
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (vitaminc.last() == '}')
                return vitaminc.substringBefore('}', "")
            else {
                return vitaminc
            }

        }


        private fun getServingSizeValue(text: String): String {
            var bindex = 0
            var value = ""

            text.forEach { Letter ->
                if (Letter == 'v' || Letter == 'V') {
                    bindex = text.indexOf(Letter)


                    if (bindex > 0) {
                        for (ch in text) {
                            if (ch == 'v' || ch == 'V') {
                                if (text[(text.indexOf(ch)) + 1] == 'a') {
                                    if (text[(text.indexOf(ch)) + 2] == 'l') {
                                        if (text[(text.indexOf(ch)) + 3] == 'u') {
                                            if (text[(text.indexOf(ch)) + 4] == 'e')
                                                value = text.substring((bindex + 7), text.indexOf('}', (bindex + 7), true))

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (value.last() == '}')
                value = value.substringBefore('}', "")

            return value

        }



    private fun getServingSizeUnit(text: String): String {
        var bindex = 0
        var unitx = ""



        text.forEach { Letter ->
            if (Letter == 'u' || Letter == 'U') {
                bindex = text.indexOf(Letter)


                if (bindex > 0) {
                    for (ch in text) {
                        if (ch == 'u' || ch == 'U') {
                            if (text[(text.indexOf(ch)) + 1] == 'n') {
                                if (text[(text.indexOf(ch)) + 2] == 'i') {
                                    if (text[(text.indexOf(ch)) + 3] == 't')
                                        unitx = text.substring((bindex + 7), text.indexOf('"', (bindex + 7), true))

                                }
                            }
                        }
                    }
                }
            }
        }

        if (unitx.last() == '"')
            unitx = unitx.substringBefore('"', "")

        unit = unitx

        return unitx


    }

    }



