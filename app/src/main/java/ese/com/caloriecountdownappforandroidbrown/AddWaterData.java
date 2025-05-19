package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddWaterData extends AppCompatActivity {

    Button btnCups, btnMl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_data); // XML updated below

        btnCups = findViewById(R.id.btn_cups);
        btnMl = findViewById(R.id.btn_ml);

        btnCups.setOnClickListener(view ->
                showInputDialog("Cups of Water Drank", "Enter number of cups(a metric cup is 250ml):", "cups")
        );

        btnMl.setOnClickListener(view ->
                showInputDialog("ml of Water Drank", "Enter quantity in ml:", "ml")
        );
    }

    private void showInputDialog(String title, String message, String unitType) {
        Context context = AddWaterData.this;

        EditText input = new EditText(context);

        // Set input hint and input type based on unitType
        if ("cups".equals(unitType)) {
            input.setHint("Enter number of cups");
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if ("ml".equals(unitType)) {
            input.setHint("Enter quantity in ml");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            input.setHint("Enter value");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String valueStr = input.getText().toString().trim();

                    if (!valueStr.isEmpty()) {
                        int mlWaterDrunk = 0;
                        double equivalentCups = 0.0;

                        try {
                            if ("cups".equals(unitType)) {
                                equivalentCups = Double.parseDouble(valueStr);
                                mlWaterDrunk = (int) (equivalentCups * 250);
                            } else if ("ml".equals(unitType)) {
                                mlWaterDrunk = Integer.parseInt(valueStr);
                                equivalentCups = mlWaterDrunk / 250.0;
                            }

                            SQLDatabase_Food_Items_CIF6 dbHelper = new SQLDatabase_Food_Items_CIF6(context);
                            dbHelper.insertWaterData(mlWaterDrunk, equivalentCups);

                            Toast.makeText(context,
                                    "Water data saved successfully",
                                    Toast.LENGTH_SHORT).show();
                            dbHelper.getAllWaterData();

                        } catch (NumberFormatException e) {
                            Toast.makeText(context,
                                    "Invalid number format",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context,
                                "Please enter a value",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
