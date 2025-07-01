package ese.com.caloriecountdownappforandroidbrown;// ImageImportActivity.java

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageImportActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    private TextView overlayText;
    private Button scanButton;

    private String base64Image = null;

    private FoodDetectionService foodDetectionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_import);

        imageView = findViewById(R.id.imageBox);
        overlayText = findViewById(R.id.overlayText);
        scanButton = findViewById(R.id.scanWithAiBtn);
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(this);
        foodDetectionService = new FoodDetectionService(this, database);

        imageView.setOnClickListener(v -> openGallery());
        scanButton.setOnClickListener(v -> {
            if (base64Image == null) {
                Toast.makeText(this, "Please select an image from the gallery first.", Toast.LENGTH_SHORT).show();
                return;
            }

            scanButton.setEnabled(false); // Disable the button
            Breakfast_Box_CIF17 box = new Breakfast_Box_CIF17();
            foodDetectionService.fetchAndStoreFoodItems(base64Image, box, new ResultCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        imageView.setImageDrawable(null); // remove image
                        overlayText.setText(getString(R.string.import_image_instruction));
                        base64Image = null; // reset
                        scanButton.setEnabled(true); // Re-enable the button
                    });
                }

                @Override
                public void onFailure() {
                    runOnUiThread(() -> {
                        Toast.makeText(ImageImportActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        scanButton.setEnabled(true); // Re-enable the button
                    });
                }
            });
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
                overlayText.setText(getString(R.string.ready_to_scan));

                base64Image = convertBitmapToBase64(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
}
