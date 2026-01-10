package ese.com.caloriecountdownappforandroidbrown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfettiView extends View {

    private List<Confetti> confettiList = new ArrayList<>();
    private Paint paint = new Paint();
    private Random random = new Random();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isAnimating = false;

    // Colorful confetti colors
    private int[] colors = {
            Color.parseColor("#FF6B6B"),  // Red
            Color.parseColor("#4ECDC4"),  // Teal
            Color.parseColor("#FFE66D"),  // Yellow
            Color.parseColor("#95E1D3"),  // Mint
            Color.parseColor("#F38181"),  // Coral
            Color.parseColor("#AA96DA"),  // Purple
            Color.parseColor("#FCBAD3"),  // Pink
            Color.parseColor("#A8D8EA"),  // Light Blue
            Color.parseColor("#FF9F43"),  // Orange
            Color.parseColor("#6BCB77"),  // Green
    };

    public ConfettiView(Context context) {
        super(context);
        paint.setAntiAlias(true);
    }

    public void startConfetti(int width, int height) {
        confettiList.clear();

        // Create confetti pieces
        for (int i = 0; i < 100; i++) {
            confettiList.add(new Confetti(
                    random.nextInt(width),           // x position
                    -random.nextInt(height),         // y position (start above view)
                    random.nextInt(12) + 4,          // width of strip
                    random.nextInt(20) + 10,         // height of strip
                    colors[random.nextInt(colors.length)],
                    random.nextFloat() * 3 + 2,      // fall speed
                    random.nextFloat() * 4 - 2,      // horizontal drift
                    random.nextFloat() * 360,        // rotation
                    random.nextFloat() * 6 - 3       // rotation speed
            ));
        }

        isAnimating = true;
        runAnimation();
    }

    private void runAnimation() {
        if (!isAnimating) return;

        handler.postDelayed(() -> {
            if (isAnimating) {
                invalidate();
                runAnimation();
            }
        }, 16); // ~60 FPS
    }

    public void stopConfetti() {
        isAnimating = false;
        confettiList.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Confetti confetti : confettiList) {
            // Update position
            confetti.y += confetti.speed;
            confetti.x += confetti.drift;
            confetti.rotation += confetti.rotationSpeed;

            // Reset if off screen
            if (confetti.y > getHeight()) {
                confetti.y = -confetti.height;
                confetti.x = random.nextInt(getWidth());
            }

            // Draw confetti strip
            paint.setColor(confetti.color);

            canvas.save();
            canvas.translate(confetti.x, confetti.y);
            canvas.rotate(confetti.rotation, confetti.width / 2f, confetti.height / 2f);
            canvas.drawRect(0, 0, confetti.width, confetti.height, paint);
            canvas.restore();
        }
    }

    private static class Confetti {
        float x, y;
        int width, height;
        int color;
        float speed;
        float drift;
        float rotation;
        float rotationSpeed;

        Confetti(float x, float y, int width, int height, int color,
                 float speed, float drift, float rotation, float rotationSpeed) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.speed = speed;
            this.drift = drift;
            this.rotation = rotation;
            this.rotationSpeed = rotationSpeed;
        }
    }
}
