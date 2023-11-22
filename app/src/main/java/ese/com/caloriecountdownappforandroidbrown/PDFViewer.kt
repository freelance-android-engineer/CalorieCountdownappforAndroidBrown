package ese.com.caloriecountdownappforandroidbrown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView

class PDFViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfviewer)

        val pdfView: PDFView = findViewById<PDFView>(R.id.pdfView1)
        val file = java.io.File("/Users/lokeke/AndroidStudioProjects/CalorieCountdownAppforAndroidBrown/app/src/main/assets/ClientGuide001.pdf")
        //pdfView.fromFile(file).load()
        pdfView.fromAsset("ClientGuide001.pdf").enableSwipe(true).load()

    }


}