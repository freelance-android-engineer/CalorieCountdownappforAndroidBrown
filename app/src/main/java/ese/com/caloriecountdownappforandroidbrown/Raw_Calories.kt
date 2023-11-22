package ese.com.caloriecountdownappforandroidbrown

import android.os.Bundle
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import ese.com.caloriecountdownappforandroidbrown.databinding.ActivityRawCaloriesBinding
import java.lang.Exception

class Raw_Calories : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRawCaloriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawCaloriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_raw_calories)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


            findViewById<android.widget.Button>(R.id.button22).setOnClickListener {

                view -> CCD_GUI_CD_CIF1.instance.Countup(CheckGetResults())

                finish()
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_raw_calories)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun CheckGetResults():Int
    {
      try{
          val cal = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString().toInt()
          val cal2 = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString().toInt()
          return (cal + cal2)

      }
      catch (e:Exception)
      {
          android.util.Log.d("Raw Calories", "Wrong Type")
      }

        return 0

    }
}