package ese.com.caloriecountdownappforandroidbrown;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HeartRateActivity - Measures heart rate using the phone's camera.
 *
 * Uses photoplethysmography (PPG) to detect blood flow changes through
 * the fingertip placed on the camera lens with flash enabled.
 */
public class HeartRateActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "HeartRateActivity";
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private static final int MEASUREMENT_DURATION_MS = 20000; // 20 seconds for better accuracy
    private static final int MIN_SAMPLES_FOR_CALCULATION = 50;

    // Finger detection thresholds - adjusted for different devices
    private static final double MIN_RED_VALUE_FOR_FINGER = 50; // Lowered threshold for compatibility
    private static final double RED_VALUE_STD_DEV_THRESHOLD = 0.5; // Minimum variation for pulse detection
    private static final int FINGER_DETECTION_SAMPLES = 5; // Samples to confirm finger placement

    // UI Components
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private TextView tvHeartRate;
    private TextView tvInstruction;
    private TextView tvStatus;
    private ProgressBar progressBar;
    private Button btnStartStop;
    private Button btnSave;
    private Button btnHistory;
    private ImageView ivHeartIcon;

    // Camera
    private Camera camera;
    private Camera.Parameters cameraParameters;

    // Measurement state
    private AtomicBoolean isMeasuring = new AtomicBoolean(false);
    private Handler handler;
    private int currentHeartRate = 0;
    private List<Long> sampleTimestamps = new ArrayList<>();
    private List<Double> redAvgValues = new ArrayList<>();
    private long measurementStartTime = 0;

    // Finger detection state
    private boolean fingerDetected = false;
    private int consecutiveGoodFrames = 0;

    // Text-to-Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    // Database
    private SQLDatabase_Food_Items_CIF6 databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        initializeViews();
        initializeDatabase();
        initializeTextToSpeech();

        handler = new Handler(Looper.getMainLooper());

        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }
    }

    private void initializeViews() {
        surfaceView = findViewById(R.id.surfaceViewCamera);
        tvHeartRate = findViewById(R.id.tvHeartRate);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);
        btnStartStop = findViewById(R.id.btnStartStop);
        btnSave = findViewById(R.id.btnSave);
        btnHistory = findViewById(R.id.btnHistory);
        ivHeartIcon = findViewById(R.id.ivHeartIcon);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        btnStartStop.setOnClickListener(v -> {
            if (isMeasuring.get()) {
                stopMeasurement();
            } else {
                startMeasurement();
            }
        });

        btnSave.setOnClickListener(v -> saveHeartRate());
        btnHistory.setOnClickListener(v -> showHeartRateHistory());
    }

    private void initializeDatabase() {
        databaseHelper = new SQLDatabase_Food_Items_CIF6(this);
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                ttsReady = (result != TextToSpeech.LANG_MISSING_DATA
                        && result != TextToSpeech.LANG_NOT_SUPPORTED);
            }
        });
    }

    private void startMeasurement() {
        if (camera == null) {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show();
            return;
        }

        isMeasuring.set(true);
        currentHeartRate = 0;
        sampleTimestamps.clear();
        redAvgValues.clear();
        fingerDetected = false;
        consecutiveGoodFrames = 0;
        measurementStartTime = System.currentTimeMillis();

        // Update UI
        btnStartStop.setText(R.string.heart_rate_stop);
        btnSave.setEnabled(false);
        tvHeartRate.setText("--");
        tvStatus.setText(R.string.heart_rate_measuring);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        // Turn on flash
        try {
            cameraParameters = camera.getParameters();

            // Check if torch mode is supported
            List<String> flashModes = cameraParameters.getSupportedFlashModes();
            if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(cameraParameters);
                android.util.Log.d(TAG, "Flash TORCH mode enabled");
            } else if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                camera.setParameters(cameraParameters);
                android.util.Log.d(TAG, "Flash ON mode enabled (TORCH not supported)");
            } else {
                android.util.Log.e(TAG, "Flash not supported on this device. Supported modes: " + flashModes);
                Toast.makeText(this, "Flash not available - results may be inaccurate", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            android.util.Log.e(TAG, "Error turning on flash: " + e.getMessage());
            Toast.makeText(this, "Could not enable flash", Toast.LENGTH_SHORT).show();
        }

        // Start preview with callback
        camera.setPreviewCallback(previewCallback);

        // Schedule measurement timeout
        handler.postDelayed(this::finishMeasurement, MEASUREMENT_DURATION_MS);

        // Update progress periodically
        updateProgress();
    }

    private void stopMeasurement() {
        isMeasuring.set(false);
        handler.removeCallbacksAndMessages(null);

        // Turn off flash
        if (camera != null) {
            try {
                camera.setPreviewCallback(null);
                cameraParameters = camera.getParameters();
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(cameraParameters);
            } catch (Exception e) {
                android.util.Log.e(TAG, "Error turning off flash: " + e.getMessage());
            }
        }

        // Update UI
        btnStartStop.setText(R.string.heart_rate_start);
        progressBar.setVisibility(View.GONE);
        tvStatus.setText("");

        if (currentHeartRate > 0) {
            btnSave.setEnabled(true);
        }
    }

    private void finishMeasurement() {
        if (!isMeasuring.get()) return;

        android.util.Log.d(TAG, "Finishing measurement with " + redAvgValues.size() + " samples");

        // Check if we have enough samples
        if (redAvgValues.size() < MIN_SAMPLES_FOR_CALCULATION) {
            stopMeasurement();
            tvStatus.setText("Not enough data (" + redAvgValues.size() + " samples). Ensure flash is on and finger covers camera.");
            tvHeartRate.setText("--");
            return;
        }

        // Log signal statistics for debugging
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0;
        for (Double v : redAvgValues) {
            if (v < min) min = v;
            if (v > max) max = v;
            sum += v;
        }
        double avg = sum / redAvgValues.size();
        android.util.Log.d(TAG, "Signal stats - min: " + min + ", max: " + max + ", avg: " + avg + ", range: " + (max - min));

        // Calculate final heart rate
        currentHeartRate = calculateHeartRate();
        stopMeasurement();

        if (currentHeartRate > 0) {
            tvHeartRate.setText(String.valueOf(currentHeartRate));
            tvStatus.setText(getString(R.string.heart_rate_result, currentHeartRate));
            btnSave.setEnabled(true);

            // Speak the result
            speakHeartRate(currentHeartRate);
        } else {
            String hint = "";
            if (max - min < 1.0) {
                hint = " Signal variation too low - ensure finger firmly covers flash.";
            } else if (avg < 50) {
                hint = " Low brightness - check if flash is working.";
            }
            tvStatus.setText("Could not detect pulse pattern." + hint);
            tvHeartRate.setText("--");
        }
    }

    private void updateProgress() {
        if (!isMeasuring.get()) return;

        long elapsed = System.currentTimeMillis() - measurementStartTime;
        int progress = (int) ((elapsed * 100) / MEASUREMENT_DURATION_MS);
        progressBar.setProgress(Math.min(progress, 100));

        // Calculate and display intermediate heart rate
        if (redAvgValues.size() > MIN_SAMPLES_FOR_CALCULATION) {
            int tempBpm = calculateHeartRate();
            if (tempBpm > 0) {
                tvHeartRate.setText(String.valueOf(tempBpm));
            }
        }

        if (isMeasuring.get()) {
            handler.postDelayed(this::updateProgress, 500);
        }
    }

    private final Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (!isMeasuring.get()) return;

            try {
                Camera.Size size = camera.getParameters().getPreviewSize();
                int width = size.width;
                int height = size.height;

                // Calculate average intensity from the center of the image
                double intensity = calculateRedAverage(data, width, height);

                // Always record samples - we'll analyze the pattern later
                redAvgValues.add(intensity);
                sampleTimestamps.add(System.currentTimeMillis());

                // Check finger detection for user feedback
                if (intensity >= MIN_RED_VALUE_FOR_FINGER) {
                    consecutiveGoodFrames++;
                    if (consecutiveGoodFrames >= FINGER_DETECTION_SAMPLES && !fingerDetected) {
                        fingerDetected = true;
                        runOnUiThread(() -> tvStatus.setText("Finger detected - measuring..."));
                    }
                } else {
                    if (consecutiveGoodFrames > 0) {
                        consecutiveGoodFrames--;
                    }
                    // Only show "place finger" if we haven't detected it yet
                    if (!fingerDetected && redAvgValues.size() > 30) {
                        runOnUiThread(() -> tvStatus.setText("Place finger firmly on camera + flash"));
                    }
                }

                // Log every 10th frame to reduce log spam
                if (redAvgValues.size() % 10 == 0) {
                    android.util.Log.d(TAG, "Intensity: " + String.format("%.1f", intensity) +
                            ", fingerDetected: " + fingerDetected +
                            ", samples: " + redAvgValues.size());
                }

            } catch (Exception e) {
                android.util.Log.e(TAG, "Error processing frame: " + e.getMessage());
            }
        }
    };

    /**
     * Calculate the average brightness/intensity from the YUV preview data.
     * Uses both Y (luminance) and V (red chrominance) for better pulse detection.
     * When finger covers camera with flash, we see brightness variations from blood flow.
     */
    private double calculateRedAverage(byte[] data, int width, int height) {
        // YUV NV21 format: Y plane first (width*height bytes), then interleaved VU
        int frameSize = width * height;

        // Sample from center region (50% of image for better coverage)
        int centerX = width / 2;
        int centerY = height / 2;
        int sampleWidth = width / 2;
        int sampleHeight = height / 2;

        long intensitySum = 0;
        int pixelCount = 0;

        int startX = centerX - sampleWidth / 2;
        int endX = centerX + sampleWidth / 2;
        int startY = centerY - sampleHeight / 2;
        int endY = centerY + sampleHeight / 2;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    int yIndex = y * width + x;

                    // Get Y (luminance) value - this is the brightness
                    int yValue = data[yIndex] & 0xFF;

                    // For NV21, V comes before U in the interleaved plane
                    // UV plane starts at frameSize, and is interleaved as VUVU...
                    int uvIndex = frameSize + (y / 2) * width + (x / 2) * 2;

                    int vValue = 128; // default
                    if (uvIndex < data.length) {
                        vValue = data[uvIndex] & 0xFF; // V value (red chrominance)
                    }

                    // Combine Y and V for better red detection
                    // Higher V values indicate more red
                    // We weight Y heavily since it shows the pulse variation
                    double intensity = yValue * 0.7 + vValue * 0.3;

                    intensitySum += (long) intensity;
                    pixelCount++;
                }
            }
        }

        double avg = pixelCount > 0 ? (double) intensitySum / pixelCount : 0;
        return avg;
    }

    /**
     * Calculate heart rate from the collected red average values.
     * Uses improved signal processing and peak detection.
     */
    private int calculateHeartRate() {
        if (redAvgValues.size() < MIN_SAMPLES_FOR_CALCULATION) {
            return 0;
        }

        android.util.Log.d(TAG, "Processing " + redAvgValues.size() + " samples");

        // Step 1: Apply smoothing first to reduce noise
        List<Double> smoothed = smoothData(redAvgValues, 3);

        // Step 2: Remove baseline drift (detrend the signal)
        List<Double> detrended = detrendSignal(smoothed);

        // Step 3: Apply another light smoothing pass
        List<Double> processed = smoothData(detrended, 2);

        // Step 3: Check if signal has enough variation (pulse detection)
        double signalStdDev = calculateStdDev(processed);
        android.util.Log.d(TAG, "Signal stdDev after processing: " + signalStdDev);

        if (signalStdDev < RED_VALUE_STD_DEV_THRESHOLD) {
            android.util.Log.d(TAG, "Signal variation too low: " + signalStdDev);
            return 0;
        }

        // Step 4: Find peaks using improved algorithm
        List<Integer> peakIndices = findPeaksImproved(processed);

        android.util.Log.d(TAG, "Found " + peakIndices.size() + " peaks in " + processed.size() + " samples");

        if (peakIndices.size() < 3) {
            // Try with less strict peak detection
            peakIndices = findPeaksSimple(processed);
            android.util.Log.d(TAG, "Simple peak detection found " + peakIndices.size() + " peaks");

            if (peakIndices.size() < 3) {
                return 0;
            }
        }

        // Step 5: Calculate BPM using timestamps for accurate timing
        List<Double> intervals = new ArrayList<>();

        for (int i = 1; i < peakIndices.size(); i++) {
            int idx1 = peakIndices.get(i - 1);
            int idx2 = peakIndices.get(i);

            if (idx1 < sampleTimestamps.size() && idx2 < sampleTimestamps.size()) {
                long timeDiffMs = sampleTimestamps.get(idx2) - sampleTimestamps.get(idx1);
                double intervalSeconds = timeDiffMs / 1000.0;

                // Only count reasonable intervals (30-200 BPM range = 0.3s to 2.0s)
                if (intervalSeconds > 0.3 && intervalSeconds < 2.0) {
                    intervals.add(intervalSeconds);
                }
            }
        }

        if (intervals.isEmpty()) {
            return 0;
        }

        // Remove outliers using median
        double medianInterval = getMedian(intervals);

        // Filter intervals close to median (within 30%)
        List<Double> filteredIntervals = new ArrayList<>();
        for (Double interval : intervals) {
            if (Math.abs(interval - medianInterval) < medianInterval * 0.3) {
                filteredIntervals.add(interval);
            }
        }

        if (filteredIntervals.isEmpty()) {
            filteredIntervals = intervals; // Fall back to all intervals
        }

        // Calculate average interval
        double avgInterval = 0;
        for (Double interval : filteredIntervals) {
            avgInterval += interval;
        }
        avgInterval /= filteredIntervals.size();

        int bpm = (int) Math.round(60.0 / avgInterval);

        // Sanity check: heart rate should be between 40-180 BPM
        if (bpm < 40 || bpm > 180) {
            return 0;
        }

        android.util.Log.d(TAG, "Calculated BPM: " + bpm + " from " + filteredIntervals.size() + " intervals");
        return bpm;
    }

    /**
     * Remove baseline drift from signal using moving average subtraction
     */
    private List<Double> detrendSignal(List<Double> data) {
        // Use a very large window for baseline estimation
        // This removes slow drift but keeps the faster pulse signal
        // At 30fps, a heartbeat cycle is ~20-40 frames, so use window of 60+ frames
        int baselineWindow = Math.max(data.size() / 3, 60);
        List<Double> baseline = smoothData(data, baselineWindow);

        List<Double> detrended = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            detrended.add(data.get(i) - baseline.get(i));
        }

        // Log detrended signal stats
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for (Double d : detrended) {
            if (d < min) min = d;
            if (d > max) max = d;
        }
        android.util.Log.d(TAG, "Detrended signal - min: " + min + ", max: " + max + ", range: " + (max - min));

        return detrended;
    }

    private List<Double> smoothData(List<Double> data, int windowSize) {
        List<Double> smoothed = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = Math.max(0, i - windowSize); j <= Math.min(data.size() - 1, i + windowSize); j++) {
                sum += data.get(j);
                count++;
            }
            smoothed.add(sum / count);
        }
        return smoothed;
    }

    private double calculateStdDev(List<Double> data) {
        double mean = 0;
        for (Double d : data) mean += d;
        mean /= data.size();

        double variance = 0;
        for (Double d : data) variance += (d - mean) * (d - mean);
        variance /= data.size();

        return Math.sqrt(variance);
    }

    /**
     * Improved peak detection with adaptive threshold and prominence check
     */
    private List<Integer> findPeaksImproved(List<Double> data) {
        List<Integer> peaks = new ArrayList<>();

        if (data.size() < 10) return peaks;

        // Calculate statistics
        double mean = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Double d : data) {
            mean += d;
            if (d > max) max = d;
            if (d < min) min = d;
        }
        mean /= data.size();

        double range = max - min;
        // Lower threshold - just above mean
        double threshold = mean + range * 0.05;

        android.util.Log.d(TAG, "Peak detection - mean: " + mean + ", range: " + range + ", threshold: " + threshold);

        // Minimum distance between peaks - more flexible
        // At 30 fps, 60 BPM = 1s = 30 frames, 180 BPM = 0.33s = 10 frames
        int minPeakDistance = Math.max(5, data.size() / 60);

        // Find local maxima above threshold
        for (int i = 2; i < data.size() - 2; i++) {
            double val = data.get(i);

            // Check if local maximum (relaxed - just check immediate neighbors)
            boolean isLocalMax = val > data.get(i - 1) && val > data.get(i - 2)
                    && val > data.get(i + 1) && val > data.get(i + 2);

            if (isLocalMax && val > threshold) {
                // Check minimum distance from last peak
                if (peaks.isEmpty() || i - peaks.get(peaks.size() - 1) >= minPeakDistance) {
                    // Relaxed prominence check
                    double leftMin = findLocalMin(data, Math.max(0, i - minPeakDistance), i);
                    double rightMin = findLocalMin(data, i, Math.min(data.size() - 1, i + minPeakDistance));
                    double prominence = val - Math.max(leftMin, rightMin);

                    // Lower prominence threshold - just 5% of range
                    if (prominence > range * 0.05) {
                        peaks.add(i);
                    }
                }
            }
        }

        android.util.Log.d(TAG, "findPeaksImproved found " + peaks.size() + " peaks");
        return peaks;
    }

    private double findLocalMin(List<Double> data, int start, int end) {
        double min = Double.MAX_VALUE;
        for (int i = start; i <= end && i < data.size(); i++) {
            if (data.get(i) < min) min = data.get(i);
        }
        return min;
    }

    /**
     * Simpler peak detection - just finds local maxima above mean
     */
    private List<Integer> findPeaksSimple(List<Double> data) {
        List<Integer> peaks = new ArrayList<>();

        if (data.size() < 5) return peaks;

        // Calculate mean
        double mean = 0;
        for (Double d : data) mean += d;
        mean /= data.size();

        // Minimum distance between peaks (assuming max 180 BPM, ~20fps = 6-7 frames between beats)
        int minDistance = 5;

        // Find local maxima above mean
        for (int i = 2; i < data.size() - 2; i++) {
            double val = data.get(i);

            // Simple local maximum check
            if (val > mean &&
                val >= data.get(i - 1) && val >= data.get(i - 2) &&
                val >= data.get(i + 1) && val >= data.get(i + 2)) {

                // Check distance from last peak
                if (peaks.isEmpty() || i - peaks.get(peaks.size() - 1) >= minDistance) {
                    peaks.add(i);
                }
            }
        }

        return peaks;
    }

    private double getMedian(List<Double> data) {
        List<Double> sorted = new ArrayList<>(data);
        java.util.Collections.sort(sorted);
        int mid = sorted.size() / 2;
        if (sorted.size() % 2 == 0) {
            return (sorted.get(mid - 1) + sorted.get(mid)) / 2.0;
        } else {
            return sorted.get(mid);
        }
    }

    private void speakHeartRate(int bpm) {
        if (ttsReady && textToSpeech != null) {
            String message = "Your heart rate is " + bpm + " beats per minute";
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "heartrate");
        }
    }

    private void saveHeartRate() {
        if (currentHeartRate <= 0) {
            Toast.makeText(this, "No heart rate to save", Toast.LENGTH_SHORT).show();
            return;
        }

        long insertedId = databaseHelper.insertHeartRate(currentHeartRate, null);
        if (insertedId > 0) {
            Toast.makeText(this, R.string.heart_rate_saved, Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);
        } else {
            Toast.makeText(this, "Failed to save heart rate", Toast.LENGTH_SHORT).show();
        }
    }

    private void showHeartRateHistory() {
        List<Map<String, Object>> records = databaseHelper.getAllHeartRateData();

        if (records.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Heart Rate History")
                    .setMessage("No heart rate records found.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> record : records) {
            String date = (String) record.get("date");
            String time = (String) record.get("time");
            int bpm = (Integer) record.get("bpm");
            sb.append(date).append(" ").append(time).append(": ").append(bpm).append(" BPM\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Heart Rate History")
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    // SurfaceHolder.Callback methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // Only open camera if permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera(holder);
        }
    }

    private void openCamera(SurfaceHolder holder) {
        if (camera != null) {
            return; // Camera already open
        }
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);

            cameraParameters = camera.getParameters();
            cameraParameters.setPreviewFormat(ImageFormat.NV21);

            // Set smallest preview size for efficiency
            List<Camera.Size> sizes = cameraParameters.getSupportedPreviewSizes();
            Camera.Size smallest = sizes.get(0);
            for (Camera.Size size : sizes) {
                if (size.width * size.height < smallest.width * smallest.height) {
                    smallest = size;
                }
            }
            cameraParameters.setPreviewSize(smallest.width, smallest.height);

            camera.setParameters(cameraParameters);
            camera.startPreview();

            android.util.Log.d(TAG, "Camera started with preview size: " + smallest.width + "x" + smallest.height);

        } catch (Exception e) {
            android.util.Log.e(TAG, "Error starting camera: " + e.getMessage());
            Toast.makeText(this, "Error starting camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            try {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            } catch (Exception e) {
                android.util.Log.e(TAG, "Error releasing camera: " + e.getMessage());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, now open camera if surface is ready
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                if (surfaceHolder != null && surfaceHolder.getSurface() != null
                        && surfaceHolder.getSurface().isValid()) {
                    openCamera(surfaceHolder);
                }
            } else {
                Toast.makeText(this, "Camera permission required for heart rate measurement",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isMeasuring.get()) {
            stopMeasurement();
        }
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reopen camera if permission granted and surface is ready
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                && surfaceHolder != null && surfaceHolder.getSurface() != null
                && surfaceHolder.getSurface().isValid()) {
            openCamera(surfaceHolder);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        releaseCamera();
    }
}
