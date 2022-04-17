package com.example.pdfreader

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.pdfreader.databinding.ActivityMainBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

lateinit var pdfView: PDFView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfView = findViewById(R.id.pdfView)
        binding.openBtn.isVisible = true

        binding.openBtn.setOnClickListener {

            binding.openBtn.isVisible = false

            /** PDF from URI */
            getPdf().execute()

            /** PDF from Asset */
//            pdfView.fromAsset("collection.pdf")
//                .enableSwipe(true)
//                .enableDoubletap(true)
//                .load()
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
