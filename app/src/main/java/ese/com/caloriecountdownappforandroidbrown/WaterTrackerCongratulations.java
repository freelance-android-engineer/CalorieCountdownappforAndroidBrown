package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

/**
 * Water Tracker Congratulations Graphic: confetti, emojis and a "CONGRATULATIONS!" dialog
 * shown when the daily water goal is reached (moved unchanged from AddWaterData).
 *
 * Subclasses (e.g. {@link FinalCountdown}) reuse the same graphic and override the hooks below.
 */
public class WaterTrackerCongratulations {

    protected final Context context;
    private final int totalWaterMl;

    public WaterTrackerCongratulations(Context context, int totalWaterMl) {
        this.context = context;
        this.totalWaterMl = totalWaterMl;
    }

    /** The message shown under "CONGRATULATIONS!". */
    protected String getGoalMessage() {
        return "You have reached your daily water goal!";
    }

    /** The small total line under the message; return null to hide it. */
    protected String getTotalText() {
        double litres = totalWaterMl / 1000.0;
        return String.format(java.util.Locale.getDefault(),
                "Total: %d ml (%.2f L)", totalWaterMl, litres);
    }

    /** Called right after the dialog is shown. */
    protected void onShown(AlertDialog dialog) {
    }

    /** Called when the dialog is dismissed. */
    protected void onDismissed() {
    }

    public AlertDialog show() {
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
        partyTop.setText("🎉 🎊 🎉");
        partyTop.setTextSize(35);
        partyTop.setGravity(Gravity.CENTER);
        contentLayout.addView(partyTop);

        // Trophy emoji
        TextView trophy = new TextView(context);
        trophy.setText("🏆");
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
        goalText.setText(getGoalMessage());
        goalText.setTextSize(15);
        goalText.setGravity(Gravity.CENTER);
        goalText.setPadding(0, 0, 0, 15);
        contentLayout.addView(goalText);

        // Total water display
        String total = getTotalText();
        if (total != null) {
            TextView totalText = new TextView(context);
            totalText.setText(total);
            totalText.setTextSize(14);
            totalText.setTextColor(Color.parseColor("#666666"));
            totalText.setGravity(Gravity.CENTER);
            totalText.setPadding(0, 10, 0, 20);
            contentLayout.addView(totalText);
        }

        // Water drop and celebration emojis
        TextView celebrationEmoji = new TextView(context);
        celebrationEmoji.setText("💧 🎆 💧");
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
        congratsDialog.setOnDismissListener(dialog -> {
            confettiView.stopConfetti();
            onDismissed();
        });

        congratsDialog.show();
        onShown(congratsDialog);

        // Start confetti animation after layout is ready
        mainContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mainContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        confettiView.startConfetti(mainContainer.getWidth(), mainContainer.getHeight());
                    }
                });
        return congratsDialog;
    }
}
