package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
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

                            // Check if user has reached water goal (1890ml)
                            int totalWaterToday = dbHelper.getTodayTotalWaterMl();
                            if (totalWaterToday >= 1890) {
                                showCongratulationsDialog(totalWaterToday);
                            }

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

    private void showCongratulationsDialog(int totalWaterMl) {
        Context context = AddWaterData.this;

        // Create main container with FrameLayout for overlay effect
        FrameLayout mainContainer = new FrameLayout(context);
        // Create confetti view (will be behind the content)
        ConfettiView confettiView = new ConfettiView(context);

        // Create content layout
        LinearLayout contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(60, 80, 60, 60);
        contentLayout.setGravity(Gravity.CENTER);

        // Party popper emoji
        TextView partyTop = new TextView(context);
        partyTop.setText("\uD83C\uDF89 \uD83C\uDF8A \uD83C\uDF89");
        partyTop.setTextSize(35);
        partyTop.setGravity(Gravity.CENTER);
        contentLayout.addView(partyTop);

        // Trophy emoji
        TextView trophy = new TextView(context);
        trophy.setText("\uD83C\uDFC6");
        trophy.setTextSize(50);
        trophy.setGravity(Gravity.CENTER);
        trophy.setPadding(0, 20, 0, 20);
        contentLayout.addView(trophy);

        // Congratulations message
        TextView messageText = new TextView(context);
        messageText.setText("CONGRATULATIONS!");
        messageText.setTextSize(22);
        messageText.setTextColor(Color.parseColor("#1565C0"));
        messageText.setGravity(Gravity.CENTER);
        messageText.setPadding(0, 10, 0, 10);
        contentLayout.addView(messageText);

        // Goal reached message
        TextView goalText = new TextView(context);
        goalText.setText("You have reached your daily water goal!");
        goalText.setTextSize(15);
        goalText.setGravity(Gravity.CENTER);
        goalText.setPadding(0, 0, 0, 15);
        contentLayout.addView(goalText);

        // Total water display
        TextView totalText = new TextView(context);
        double litres = totalWaterMl / 1000.0;
        totalText.setText(String.format(java.util.Locale.getDefault(),
                "Total: %d ml (%.2f L)", totalWaterMl, litres));
        totalText.setTextSize(14);
        totalText.setTextColor(Color.parseColor("#666666"));
        totalText.setGravity(Gravity.CENTER);
        totalText.setPadding(0, 10, 0, 20);
        contentLayout.addView(totalText);

        // Water drop and celebration emojis
        TextView celebrationEmoji = new TextView(context);
        celebrationEmoji.setText("\uD83D\uDCA7 \uD83C\uDF86 \uD83D\uDCA7");
        celebrationEmoji.setTextSize(30);
        celebrationEmoji.setGravity(Gravity.CENTER);
        contentLayout.addView(celebrationEmoji);

        // Add confetti view first (background)
        mainContainer.addView(confettiView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        // Add content layout on top
        mainContainer.addView(contentLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        // Build the dialog
        AlertDialog congratsDialog = new AlertDialog.Builder(context)
                .setView(mainContainer)
                .setCancelable(true)
                .setPositiveButton("Thank you!", (dialog, which) -> {
                    confettiView.stopConfetti();
                })
                .create();

        // Set dialog dismiss listener to stop confetti
        congratsDialog.setOnDismissListener(dialog -> confettiView.stopConfetti());

        congratsDialog.show();

        // Start confetti animation after layout is ready
        mainContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mainContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        confettiView.startConfetti(mainContainer.getWidth(), mainContainer.getHeight());
                    }
                });
    }
}
