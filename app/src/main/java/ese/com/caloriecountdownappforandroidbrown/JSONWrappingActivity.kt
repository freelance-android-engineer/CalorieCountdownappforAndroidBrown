package ese.com.caloriecountdownappforandroidbrown

//import android.support.design.widget.FloatingActionButton
//import android.support.design.widget.Snackbar
//import android.support.v7.app.AppCompatActivity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup
import java.net.URLEncoder
import java.net.URL
import java.io.*


class JSONWrappingActivity : AppCompatActivity() {

    private val REQUEST_CODE_WEB_SEARCH = 7

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_s_o_n_wrapping)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{ view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val textField:EditText = findViewById(R.id.editTextTextPersonName)
        val button:Button = findViewById(R.id.button16)
        var search = CCD_GUI_CD_CIF1.itemToFind
        search = search.substring(0,(search.length - 10))

        textField.setText(search);



            //Get the Query String to Search for out of called activated Intent and apply to
        //editTextTextPersonName set it inside so can be recalled when button is clicked
        //WebView shown with Toast message, Click to top relevant link
        //then see ACCA : Black etc* in a Stream down page
        //Parse out JSON
        //Output JSON
        //Input JSON into function that returns JSONWrapperObject
        //Group them all in Arrays
        //pass resulting ArrayList<JSONWrappObjecy> to model adapter to Logic and Technology that 'Add New Food item'
        //Done, very quickly when user clicks button16
        //out message that informs the food items that have been added to see if relevant in the list (read notes)
        //so user can plan what to search when they pop the stack and go back the Multi Search to find again and credit
        //JSONWrapper Flows to e//drive, integrate with fast and vastly intelligne passion to fit e//drive config in
        //growing versions on (www.ese-edet.eu*)

        /*

       tracking":true}],"cloudFrontHeaders":{"country":"NG","hydrated":true},"foods":{"servingMultiplier":1,"item":{"id":32583658,"brand_name":"Key West Key Lime Pie Co.","description":"Key West Key Lime Pie Co. - Key Lime Pie","nutritional_contents":{"energy":{"unit":"calories","value":240},"fat":8,"saturated_fat":5,"monounsaturated_fat":2,"cholesterol":20,"sodium":90,"potassium":260,"sugar":38,"protein":5,"vitamin_a":4,"vitamin_c":4,"calcium":20,"carbohydrates":43},"public":true,"serving_sizes":[{"unit":"Slice","value":1,"index":0,"nutrition_multiplier":1},{"unit":"container (8 slices ea.)","value":1,"index":1,"nutrition_multiplier":8}],"verified":false},"errors":[],"hydrated":true},"nutrition":{"popularSearchResults":[{"item":{"brand_name":"Key West Key Lime Pie Co.","country_code":"US","deleted":false,"description":"Key Lime Pie","id":32583658,"nutritional_contents":{"energy":{"unit":"calories","value":240},"calcium":20,"carbohydrates":43,"cholesterol":20,"fat":8,"monounsaturated_fat":2,"potassium":260,"protein":5,"saturated_fat":5,"sodium":90,"sugar":38,"vitamin_a":4,"vitamin_c":4},"public":true,"serving_sizes":[{"id":"18863110246077","index":1,"nutrition_multiplier":1,"unit":"Slice","value":1},{"id":"19412866059965","index":2,"nutrition_multiplier":8,"unit":"container (8 slices ea.)","value":1}],"type":"food","user_id":"19264713821245","verified":false,"version":"58554919521709"},"tags":[],"type":"food"},{"item":{"brand_name":"Key Lime Pie Co","country_code":"US","deleted":false,"description":"Key Lime Pie Mini ","id":962863801,"nutritional_contents":{"energy":{"unit":"calories","value":680},"calcium":0,"carbohydrates":106,"cholesterol":10,"fat":25,"fiber":0,"iron":0,"monounsaturated_fat":3,"polyunsaturated_fat":0,"potassium":0,"protein":10,"saturated_fat":14,"sodium":250,"sugar":91,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"194784849175981","index":0,"nutrition_multiplier":1,"unit":"gram","value":254},{"id":"195334604989869","index":1,"nutrition_multiplier":0.00393701,"unit":"gram","value":1},{"id":"194784840820013","index":2,"nutrition_multiplier":1.00451,"unit":"ounce","value":9},{"id":"194784840820141","index":3,"nutrition_multiplier":0.111612,"unit":"ounce","value":1}],"type":"food","user_id":"32029381971645","verified":false,"version":"66941279333685"},"tags":[],"type":"food"},{"item":{"brand_name":"Kermit's Key West Key Lime Pie Shoppe","country_code":"US","deleted":false,"description":"Key Lime Pie Candy","id":268489765,"nutritional_contents":{"energy":{"unit":"calories","value":60},"calcium":0,"carbohydrates":16,"cholesterol":0,"fat":0,"fiber":0,"iron":0,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":0,"saturated_fat":0,"sodium":0,"sugar":16,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"264853087301037","index":1,"nutrition_multiplier":1,"unit":"pcs (17g)","value":3},{"id":"264303339875629","index":2,"nutrition_multiplier":0.333333,"unit":"pcs (17g)","value":1},{"id":"264853095689517","index":3,"nutrition_multiplier":12,"unit":"container (36 pcs (17g))","value":1}],"type":"food","user_id":"102958427303085","verified":false,"version":"97750950498157"},"tags":[],"type":"food"},{"item":{"brand_name":"Pie Guy  Key Lime Pie","country_code":"US","deleted":false,"description":"Key Lime Pie","id":314405409,"nutritional_contents":{"energy":{"unit":"calories","value":310},"calcium":8,"carbohydrates":36,"cholesterol":95,"fat":17,"fiber":1,"iron":2,"protein":4,"saturated_fat":9,"sodium":115,"sugar":27,"vitamin_a":10,"vitamin_c":2},"public":true,"serving_sizes":[{"id":"88132463406525","index":0,"nutrition_multiplier":1,"unit":"oz.","value":3},{"id":"88682219220413","index":1,"nutrition_multiplier":0.333333,"unit":"oz.","value":1},{"id":"88132471795005","index":2,"nutrition_multiplier":0.011758,"unit":"gram","value":1},{"id":"88682227608893","index":3,"nutrition_multiplier":0.99943,"unit":"gram","value":85}],"type":"food","user_id":"93893948116733","verified":false,"version":"22838123654717"},"tags":[],"type":"food"},{"item":{"brand_name":"Florida Key Lime Pie","country_code":"US","deleted":false,"description":"Key Lime Pie","id":41439232,"nutritional_contents":{"energy":{"unit":"calories","value":320},"calcium":25,"carbohydrates":59,"cholesterol":110,"fat":13,"fiber":1,"iron":6,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":9,"saturated_fat":7,"sodium":200,"sugar":54,"trans_fat":0,"vitamin_a":8,"vitamin_c":4},"public":true,"serving_sizes":[{"id":"124396807073085","index":1,"nutrition_multiplier":1,"unit":"Piece","value":1},{"id":"124946562886973","index":2,"nutrition_multiplier":10,"unit":"container (10 pieces ea.)","value":1}],"type":"food","user_id":"23783019638077","verified":false,"version":"102552720666221"},"tags":[],"type":"food"},{"item":{"brand_name":"IGA key lime pie","country_code":"CA","deleted":false,"description":"key lime pie","id":148090562,"nutritional_contents":{"energy":{"unit":"calories","value":117},"calcium":0,"carbohydrates":0,"cholesterol":0,"fat":0,"fiber":0,"iron":0,"monounsaturated_fat":0,"polyunsaturated_fat":0,"potassium":0,"protein":0,"saturated_fat":0,"sodium":0,"sugar":0,"trans_fat":0,"vitamin_a":0,"vitamin_c":0},"public":true,"serving_sizes":[{"id":"204397370740141","index":1,"nutrition_multiplier":1,"unit":"piece","value":1},{"id":"203847606570285","index":2,"nutrition_multiplier":10,"unit":"container (10.00 piece)","value":1}],"type":"food","user_id":"274772184108333","verified":false,"version":"53908037520813"},"tags":[],"type":"food"}],"items":[],"hasError":false,"isLoading":false,"totalResultsCount":0,"prefetched":false,"hydrated":true},"viewport":{"clientOs":null,"deviceWidth":1600,"isMobile":false,"isPhone":false,"isTablet":false,"isDesktop":true,"userAgent":null,"width":1600,"hydrated":true}}</script><script type="text/javascript" src="//cdn.polyfill.io/v2/polyfill.js?features=default,es6"></script><script type="text/javascript" src="/react-static/index-b1b46de61edcc7a49e55.js"></script></body></html>
         */

                button.setOnClickListener{
                    val kilishi = textField.text.toString()
                    val url = "http://www.myfitnesspal.com/nutrition-facts-calories/$kilishi"
                    val googleSearchText = "myfitnesspal Calories in " + textField.text.toString()
                    val escapedQuery = URLEncoder.encode(googleSearchText, "UTF-8")
                    val uri = Uri.parse("http://www.google.com/#q=$escapedQuery")


                    val myWebView: WebView = findViewById(R.id.webview1)
                    myWebView.webViewClient = Google_Search_WebView_CiF000123fragmented_Object(this)
                    myWebView.loadUrl(url)

                    val SDK_INT = Build.VERSION.SDK_INT
                    if (SDK_INT > 8) {
                        val policy = ThreadPolicy.Builder()
                            .permitAll().build()
                        StrictMode.setThreadPolicy(policy)
                    }

                   // val document: org.jsoup.nodes.Document = Jsoup.connect(url).userAgent("Mozilla/5.0")
                     //   .timeout(30000).get()
                    //val data: String = Jsoup.connect(url).ignoreContentType(true).execute().body();
                    //



                    //val myWebView = android.webkit.WebView(this)

                    //myWebView.loadUrl("http://www.google.com")

                    //setContentView(myWebView)

                    //List Select right food item.
                    //Food_Item_CIF4 emptyShell= new Food_Item_CIF4();

                   // val i = Intent(this, Google_Search_WebView_CiF000123fragmented_Object::class.java)

                    //Log.d("Multi-search", "We are below Intent")
                    //val apiData: String = URL(url).readText()
                    //



                    val urlx: URL? = try {
                        URL(url)
                    }catch (e: java.net.MalformedURLException){
                        Log.d("Exception", e.toString())
                        null
                    }

                    if (urlx != null) {
                        urlx.getString()?.let { it1 -> myWebView.parse(it1) }
                    }



                    //startActivityForResult(i, this.REQUEST_CODE_WEB_SEARCH )

                    Log.d("Multi-search", "We have left for Results")

                    //val intent = Intent(Intent.ACTION_VIEW, uri)

                    //startActivity(intent)

        }

        //val notFound = intent.getStringExtra("not found")

        Toast.makeText(this,"your app will add Food items not found to the local database now," + "\n" + "Please search and Credit missing items again after you Click OK",Toast.LENGTH_LONG).show()



    }

    fun showToast(toast: String)
    {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}

fun URL.getString(): String? {
    val stream = openStream()
    return try {
        val r = BufferedReader(InputStreamReader(stream))
        val result = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            result.append(line).appendLine()
        }
        result.toString()
    }catch (e: IOException){
        e.toString()
    }
}

private fun WebView.parse(url: String)
{
    Log.d("JSOUP", url)
}
