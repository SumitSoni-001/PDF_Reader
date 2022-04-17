package com.example.pdfreader

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.pdfreader.databinding.ActivityMainBinding
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.persistent_bottom_sheet.*
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

lateinit var pdfView: PDFView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfView = findViewById(R.id.pdfView)

//        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(persistentBottomSheet)

        binding.openBtn.isVisible = true

        binding.openBtn.setOnClickListener {
            Toast.makeText(this, "Persistent Bottom Sheet", Toast.LENGTH_SHORT).show()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        fromAsset.setOnClickListener {
            binding.openBtn.isVisible = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            /** PDF from Asset */
            pdfView.fromAsset("collection.pdf")
//                    .enableSwipe(true)
//                    .enableDoubletap(true)
                .load()
        }

        fromUrl.setOnClickListener {
            binding.openBtn.isVisible = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Toast.makeText(this, "Wait...", Toast.LENGTH_LONG).show()

            /** PDF from URI */
            getPdf().execute()
        }

    }

}

// Resource = Papaya Coder
/** Loading Pdf from InputStream */
private class getPdf : AsyncTask<String, Void, InputStream>() {
    override fun doInBackground(vararg params: String?): InputStream {
        val url = URL("https://bjpcjp.github.io/pdfs/devops/linux-commands-handbook.pdf")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        var inputStream: BufferedInputStream? = null

        if (urlConnection.responseCode == 200) {
            inputStream = BufferedInputStream(urlConnection.inputStream)
        }

        return inputStream!!
    }

    override fun onPostExecute(result: InputStream?) {
        pdfView.fromStream(result).load()
    }

}
