package ese.com.caloriecountdownappforandroidbrown;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Add_New_Item_Activity_CIF2Fragment extends Fragment {

    private static final String TAG = "AddNewItemFragment";

    // Request codes
    private static final int REQUEST_IMAGE_GALLERY = 1001;
    private static final int REQUEST_IMAGE_CAMERA = 1002;

    private static final int PERMISSION_REQUEST_CAMERA = 2001;

    private static final int PERMISSION_REQUEST_STORAGE = 2002;

    private Button mAdd;

    // Image UI components
    private ImageView imagePreview;
    private TextView tvImagePlaceholder;
    private Button btnSelectImage;
    private Button btnTakePhoto;

    // Image data
    private String currentPhotoPath;
    private String imageBase64 = null;
    private Bitmap selectedImageBitmap = null;

    public Add_New_Item_Activity_CIF2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add__new__item__activity__cif12, container, false);

        // Initialize Add button
        mAdd = v.findViewById(R.id.button10);
        mAdd.setOnClickListener(view -> {
            long out = Adding_New_Item_pressed();
            android.util.Log.d("Add New Item", new RoundingCIF13().IntToString((int) out));
        });

        // Initialize Image Upload UI components
        imagePreview = v.findViewById(R.id.imagePreview);
        tvImagePlaceholder = v.findViewById(R.id.tvImagePlaceholder);
        btnSelectImage = v.findViewById(R.id.btnSelectImage);
        btnTakePhoto = v.findViewById(R.id.btnTakePhoto);

        // Set up click listeners for image buttons
        btnSelectImage.setOnClickListener(view -> openGallery());
        btnTakePhoto.setOnClickListener(view -> openCamera());
        imagePreview.setOnClickListener(view -> openGallery());

        return v;
    }

    // ==================== IMAGE HANDLING ====================

    /**
     * Open gallery to select an image
     */
    private void openGallery() {
        if (checkStoragePermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
        }
    }

    /**
     * Open camera to take a photo
     */
    private void openCamera() {
        if (checkCameraPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e(TAG, "Error creating image file", ex);
                    Toast.makeText(getActivity(), "Error creating image file", Toast.LENGTH_SHORT).show();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(requireContext(),
                            requireContext().getPackageName() + ".fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
                }
            }
        }
    }

    /**
     * Create a temporary image file for camera capture
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "FOOD_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Check and request storage permission
     */
    private boolean checkStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST_STORAGE);
                return false;
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_STORAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Check and request camera permission
     */
    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
            return false;
        }
        return true;
    }

    // ==================== ACTIVITY RESULT HANDLING ====================

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_IMAGE_GALLERY:
                if (data != null && data.getData() != null) {
                    handleGalleryImage(data.getData());
                }
                break;

            case REQUEST_IMAGE_CAMERA:
                handleCameraImage();
                break;
        }
    }

    /**
     * Handle image selected from gallery
     */
    private void handleGalleryImage(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }

            // Display the image
            imagePreview.setImageBitmap(selectedImageBitmap);
            tvImagePlaceholder.setVisibility(View.GONE);

            // Convert to Base64 for upload
            imageBase64 = bitmapToBase64(selectedImageBitmap);
            Log.d(TAG, "Image selected from gallery, Base64 length: " + (imageBase64 != null ? imageBase64.length() : 0));

        } catch (Exception e) {
            Log.e(TAG, "Error loading gallery image", e);
            Toast.makeText(getActivity(), "Error loading image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handle image captured from camera
     */
    private void handleCameraImage() {
        try {
            if (currentPhotoPath != null) {
                File imgFile = new File(currentPhotoPath);
                if (imgFile.exists()) {
                    selectedImageBitmap = BitmapFactory.decodeFile(currentPhotoPath);

                    // Display the image
                    imagePreview.setImageBitmap(selectedImageBitmap);
                    tvImagePlaceholder.setVisibility(View.GONE);

                    // Convert to Base64 for upload
                    imageBase64 = bitmapToBase64(selectedImageBitmap);
                    Log.d(TAG, "Image captured from camera, Base64 length: " + (imageBase64 != null ? imageBase64.length() : 0));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading camera image", e);
            Toast.makeText(getActivity(), "Error loading image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Convert bitmap to Base64 string
     */
    private String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress to reduce size (80% quality)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PERMISSION_REQUEST_CAMERA:
                    openCamera();
                    break;
                case PERMISSION_REQUEST_STORAGE:
                    openGallery();
                    break;
            }
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    private long Adding_New_Item_pressed() {

        EditText editText40 = (EditText) getActivity().findViewById(R.id.edit_text40);
        EditText editText41 = (EditText) getActivity().findViewById(R.id.edit_text41);
        EditText editText42 = (EditText) getActivity().findViewById(R.id.edit_text42);
        EditText editText43 = (EditText) getActivity().findViewById(R.id.edit_text43);
        EditText editText44 = (EditText) getActivity().findViewById(R.id.edit_text44);
        EditText editText45 = (EditText) getActivity().findViewById(R.id.edit_text45);
        EditText editText46 = (EditText) getActivity().findViewById(R.id.edit_text46);
        EditText editText47 = (EditText) getActivity().findViewById(R.id.edit_text47);
        EditText editText48 = (EditText) getActivity().findViewById(R.id.edit_text48);
        EditText editText49 = (EditText) getActivity().findViewById(R.id.edit_text49);
        EditText editText50 = (EditText) getActivity().findViewById(R.id.edit_text50);
        EditText editText51 = (EditText) getActivity().findViewById(R.id.edit_text51);
        EditText editText52 = (EditText) getActivity().findViewById(R.id.edit_text52);
        EditText editText53 = (EditText) getActivity().findViewById(R.id.edit_text53);
        EditText editText54 = (EditText) getActivity().findViewById(R.id.edit_text54);
        EditText editText55 = (EditText) getActivity().findViewById(R.id.edit_text55);
        EditText editText56 = (EditText) getActivity().findViewById(R.id.edit_text56);
        EditText editText57 = (EditText) getActivity().findViewById(R.id.edit_text57);
        EditText editText58 = (EditText) getActivity().findViewById(R.id.edit_text58);
        EditText editText59 = (EditText) getActivity().findViewById(R.id.edit_text59);
        EditText editText60 = (EditText) getActivity().findViewById(R.id.edit_text60);
        EditText editText61 = (EditText) getActivity().findViewById(R.id.edit_text61);
        EditText editText62 = (EditText) getActivity().findViewById(R.id.edit_text62);

        Food_Item_CIF4 new_item = new Food_Item_CIF4();
        RoundingCIF13 rounding = new RoundingCIF13();


        if (!editText40.getText().toString().isEmpty()) {
            new_item.Set_food_item_name(editText40.getText().toString());
        }
        if (!editText41.getText().toString().isEmpty()) {
            new_item.Set_grams_per_serving_portion(rounding.StringToFloat(editText41.getText().toString()));
        }
        if (!editText42.getText().toString().isEmpty()) {
            new_item.Set_calories_per_100g(rounding.StringToFloat(editText42.getText().toString()));
        }
        if (!editText43.getText().toString().isEmpty()) {
            new_item.Set_fat_per_100g(rounding.StringToFloat(editText43.getText().toString()));
        }
        if (!editText44.getText().toString().isEmpty()) {
            new_item.Set_saturated_fat(rounding.StringToFloat(editText44.getText().toString()));
        }
        if (!editText45.getText().toString().isEmpty()) {
            new_item.Set_trans_fat(rounding.StringToFloat(editText45.getText().toString()));
        }
        if (!editText46.getText().toString().isEmpty()) {
            new_item.Set_protein_per_100g(rounding.StringToFloat(editText46.getText().toString()));
        }
        if (!editText47.getText().toString().isEmpty()) {
            new_item.Set_carbs_per_100g(rounding.StringToFloat(editText47.getText().toString()));
        }
        if (!editText48.getText().toString().isEmpty()) {
            new_item.Set_sugar_per_100g(rounding.StringToFloat(editText48.getText().toString()));
        }
        if (!editText49.getText().toString().isEmpty()) {
            new_item.Set_salt_per_100g(rounding.StringToFloat(editText49.getText().toString()));
        }
        if (!editText50.getText().toString().isEmpty()) {
            new_item.Set_wellbeing_index(new RoundingCIF13().StringToBool(editText50.getText().toString()));
        }

        if (!editText51.getText().toString().isEmpty()) {
            new_item.Set_fiber(rounding.StringToFloat(editText51.getText().toString()));
        }
        if (!editText52.getText().toString().isEmpty()) {
            new_item.Set_price_sterling(rounding.StringToFloat(editText52.getText().toString()));
        }
        if (!editText53.getText().toString().isEmpty()) {
            new_item.Set_category(editText53.getText().toString());
        }
        if (!editText54.getText().toString().isEmpty()) {
            new_item.Set_polyunsaturated(rounding.StringToFloat(editText54.getText().toString()));
        }
        if (!editText55.getText().toString().isEmpty()) {
            new_item.Set_monounsaturated(rounding.StringToFloat(editText55.getText().toString()));
        }
        if (!editText56.getText().toString().isEmpty()) {
            new_item.Set_cholesterol_mg(rounding.StringToFloat(editText56.getText().toString()));
        }
        if (!editText57.getText().toString().isEmpty()) {
            new_item.Set_sodium_mg(rounding.StringToFloat(editText57.getText().toString()));
        }
        if (!editText58.getText().toString().isEmpty()) {
            new_item.Set_potassium_mg(rounding.StringToFloat(editText58.getText().toString()));
        }
        if (!editText59.getText().toString().isEmpty()) {
            new_item.Set_vitamin_a_percent(rounding.StringToFloat(editText59.getText().toString()));
        }
        if (!editText60.getText().toString().isEmpty()) {
            new_item.Set_vitamin_c_percent(rounding.StringToFloat(editText60.getText().toString()));
        }
        if (!editText61.getText().toString().isEmpty()) {
            new_item.Set_calcium_percent(rounding.StringToFloat(editText61.getText().toString()));
        }
        if (!editText62.getText().toString().isEmpty()) {
            new_item.Set_iron_percent(rounding.StringToFloat(editText62.getText().toString()));
        }

//kar code
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
//        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());

//        return database.Insert_Food_Item_Row(new_item);
//        long localRowId = database.Insert_Food_Item_Row(new_item);
//        Log.e("ADD FOOD", "Data sent to local food row " + localRowId);

        long localRowId = 1; // Dummy ID taaki code crash na ho
        Log.e("ADD FOOD", "Local save skipped. Sending data to API only.");

        // Step 4: Check internet connection
        if (NetworkUtil.isInternetAvailable(getActivity())) {
            // Step 5: Prepare data map for backend
            Map<String, Object> foodData = new HashMap<>();
            foodData.put("food_item_name", new_item.Get_food_item_name());
            foodData.put("grams_per_serving", new_item.Get_grams_per_serving_portion());
            foodData.put("calories_per_100g", new_item.Get_calories_per_100g());
            foodData.put("fat_per_100g", new_item.Get_fat_per_100g());
            foodData.put("saturated_fat", new_item.Get_saturated_fat());
            foodData.put("trans_fat", new_item.Get_trans_fat());
            foodData.put("protein_per_100g", new_item.Get_protein_per_100g());
            foodData.put("carbs_per_100g", new_item.Get_carbs_per_100g());
            foodData.put("sugar_per_100g", new_item.Get_sugar_per_100g());
            foodData.put("salt_per_100g", new_item.Get_salt_per_100g());
//            foodData.put("wellbeing_index", new_item.Get_wellbeing_index());
            foodData.put("fiber", new_item.Get_fiber());
            foodData.put("price_sterling", new_item.Get_price_sterling());
            foodData.put("category", new_item.Get_category());
            foodData.put("polyunsaturated", new_item.Get_polyunsaturated());
            foodData.put("monounsaturated", new_item.Get_monounsaturated());
            foodData.put("cholesterol_mg", new_item.Get_cholesterol_mg());
            foodData.put("sodium_mg", new_item.Get_sodium_mg());
            foodData.put("potassium_mg", new_item.Get_potassium_mg());
            foodData.put("vitamin_a_percent", new_item.Get_vitamin_a_percent());
            foodData.put("vitamin_c_percent", new_item.Get_vitamin_c_percent());
            foodData.put("calcium_percent", new_item.Get_calcium_percent());
            foodData.put("iron_percent", new_item.Get_iron_percent());

            // Add image data (Base64 encoded)
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                foodData.put("image_base64", imageBase64);
                Log.d(TAG, "Image included in food data");
            }

            // Step 6: Call your API client (assume Kotlin interoperability)
            SQLHeavyClientType008 apiClient = new SQLHeavyClientType008(getActivity());
            apiClient.addFoodItem(foodData, new ApiResultCallback() {
                @Override
                public void onFailure() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clearAllFields();
                                Toast.makeText(getActivity(), "Data added locally (backend sync failed)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onSuccess(@Nullable String response) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clearAllFields();
                                Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } else {
            Log.w("Network", "⚠️ Offline mode: saved only locally (RowID: " + localRowId + ")");
            // Clear fields and show toast in offline mode
            clearAllFields();
            Toast.makeText(getActivity(), "Data added locally (offline)", Toast.LENGTH_SHORT).show();
        }

        return localRowId;
    }

    private void clearAllFields() {
        if (getActivity() != null) {
            EditText editText40 = (EditText) getActivity().findViewById(R.id.edit_text40);
            EditText editText41 = (EditText) getActivity().findViewById(R.id.edit_text41);
            EditText editText42 = (EditText) getActivity().findViewById(R.id.edit_text42);
            EditText editText43 = (EditText) getActivity().findViewById(R.id.edit_text43);
            EditText editText44 = (EditText) getActivity().findViewById(R.id.edit_text44);
            EditText editText45 = (EditText) getActivity().findViewById(R.id.edit_text45);
            EditText editText46 = (EditText) getActivity().findViewById(R.id.edit_text46);
            EditText editText47 = (EditText) getActivity().findViewById(R.id.edit_text47);
            EditText editText48 = (EditText) getActivity().findViewById(R.id.edit_text48);
            EditText editText49 = (EditText) getActivity().findViewById(R.id.edit_text49);
            EditText editText50 = (EditText) getActivity().findViewById(R.id.edit_text50);
            EditText editText51 = (EditText) getActivity().findViewById(R.id.edit_text51);
            EditText editText52 = (EditText) getActivity().findViewById(R.id.edit_text52);
            EditText editText53 = (EditText) getActivity().findViewById(R.id.edit_text53);
            EditText editText54 = (EditText) getActivity().findViewById(R.id.edit_text54);
            EditText editText55 = (EditText) getActivity().findViewById(R.id.edit_text55);
            EditText editText56 = (EditText) getActivity().findViewById(R.id.edit_text56);
            EditText editText57 = (EditText) getActivity().findViewById(R.id.edit_text57);
            EditText editText58 = (EditText) getActivity().findViewById(R.id.edit_text58);
            EditText editText59 = (EditText) getActivity().findViewById(R.id.edit_text59);
            EditText editText60 = (EditText) getActivity().findViewById(R.id.edit_text60);
            EditText editText61 = (EditText) getActivity().findViewById(R.id.edit_text61);
            EditText editText62 = (EditText) getActivity().findViewById(R.id.edit_text62);

            editText40.setText("");
            editText41.setText("");
            editText42.setText("");
            editText43.setText("");
            editText44.setText("");
            editText45.setText("");
            editText46.setText("");
            editText47.setText("");
            editText48.setText("");
            editText49.setText("");
            editText50.setText("");
            editText51.setText("");
            editText52.setText("");
            editText53.setText("");
            editText54.setText("");
            editText55.setText("");
            editText56.setText("");
            editText57.setText("");
            editText58.setText("");
            editText59.setText("");
            editText60.setText("");
            editText61.setText("");
            editText62.setText("");

            // Clear image fields
            if (imagePreview != null) {
                imagePreview.setImageResource(android.R.drawable.ic_menu_gallery);
            }
            if (tvImagePlaceholder != null) {
                tvImagePlaceholder.setVisibility(View.VISIBLE);
            }

            // Clear image data
            imageBase64 = null;
            selectedImageBitmap = null;
            currentPhotoPath = null;
        }
    }

    public long add_JSON_OBJ_TO_CIF6(JSON_Object INPUT) {
        final int i = 1;
        return i;
    }

    public long add_Transactions_CiF22_To_CiF6(Transactions_CIF22 Input_new_item) {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());
        return 0;
        //return database.Insert_Food_Item_Row(Input_new_item.get_Food_items_or_items_Kotlin_1_5_0());

        //transform Object0001 to CiF22 and from CiF22 to Food items/list of foodiems, to be added
        //directly to final function below and research database for new and missing item Credit as
        //normal to the end, PUSH, back to folder start using app in Beta -> £6.52
    }

    public long add_Transactions_CiF22_To_CiF6(java.util.ArrayList<Food_Item_CIF4> add) {
        //IDO : Should be using Data Model Adapter Here.                                                                Brown Architect Artist Slick Style presentation ref Apple
        SQLDatabase_Food_Items_CIF6 database = new SQLDatabase_Food_Items_CIF6(getActivity());

        for (Food_Item_CIF4 m : add) {
            database.Insert_Food_Item_Row(m);
        }

        return 0;
    }

}

