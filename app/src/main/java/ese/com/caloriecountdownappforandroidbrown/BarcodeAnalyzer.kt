package ese.com.caloriecountdownappforandroidbrown

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * CameraX ImageAnalysis.Analyzer that uses ML Kit to detect barcodes on every frame.
 * Common food product formats (EAN-13, UPC-A, UPC-E, EAN-8, Code-128, QR) are supported.
 * [onDetected] is called once with the raw barcode value when a barcode is found.
 */
class BarcodeAnalyzer(private val onDetected: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_QR_CODE
            )
            .build()
    )

    override fun analyze(imageProxy: ImageProxy) {
        try {
            // Capture rotation before converting — imageProxy must stay open until we read its info
            val rotation = imageProxy.imageInfo.rotationDegrees
            val bitmap = imageProxy.toBitmap()
            // Close the proxy immediately now that we have our bitmap copy
            imageProxy.close()

            val image = InputImage.fromBitmap(bitmap, rotation)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { value ->
                        if (value.isNotBlank()) {
                            onDetected(value)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    android.util.Log.w("BarcodeAnalyzer", "ML Kit scan failed: ${e.message}")
                }
        } catch (e: Exception) {
            android.util.Log.e("BarcodeAnalyzer", "analyze() error: ${e.message}", e)
            imageProxy.close()
        }
    }
}
