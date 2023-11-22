  package ese.com.caloriecountdownappforandroidbrown

import android.app.Activity
import android.webkit.WebResourceRequest
import android.webkit.WebView

  class Google_Search_WebView_CiF000123fragmented_Object internal constructor(private val activity: android.app.Activity) : abstract_CCD_GUI_CiF_001()
  {



      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
      {
          return super.shouldOverrideUrlLoading(view, request)
          parse(request)
          //println("We are parsing through myfitnesspal page")
          //return true
      }




      fun parse(request: WebResourceRequest?): Unit
      {
          val url = request?.url
          val JSON_Object = ese.com.caloriecountdownappforandroidbrown.JSON_Object(url)

          JSON_Object.startParsing(url.toString())
      }

      constructor():this(Activity())


      //override fun onCreate(savedInstanceState: Bundle?) {
      //  super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_google__search__web_view__ci_f000123fragmented__object)
        //setSupportActionBar(findViewById(R.id.toolbar)) \r
        //findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
         //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //     .setAction("Action", null).show()
        //}
    //}









      fun addJSON_Object_to_Add_New_Food_item(jsonObject: JSON_Object)
      {
          //See Memory Old Technology, Sell/ Sale
          val addNewItemActivityCif2fragment = Add_New_Item_Activity_CIF2Fragment()
          addNewItemActivityCif2fragment.add_JSON_OBJ_TO_CIF6(jsonObject)

      }








      //val myWebView: android.webkit.WebView = findViewById(R.id.webview1)
      //val myWebView = android.webkit.WebView(this)

      //myWebView.loadUrl("http://www.google.com")

      //setContentView(myWebView)

      //List Select right food item.
      //Food_Item_CIF4 emptyShell= new Food_Item_CIF4();
      //val i = Intent(this, Google_Search_WebView_CiF000123fragmented_Object::class.java)

     // Log.d("Multi-search", "We are below Intent")


      //startActivityForResult(i, this.REQUEST_CODE_WEB_SEARCH )

      //Log.d("Multi-search", "We have left for Results")

      //val intent = Intent(Intent.ACTION_VIEW, uri)

      //this.startActivity(intent)
}



