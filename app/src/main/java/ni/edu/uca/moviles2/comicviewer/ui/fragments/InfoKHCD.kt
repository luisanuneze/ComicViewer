package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import ni.edu.uca.moviles2.comicviewer.R


class InfoKHCD : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout para este fragment
        val view = inflater.inflate(R.layout.fragment_info_khcd, container, false)
        val myWebView: WebView = view.findViewById(R.id.webview)
        myWebView.loadUrl("https://es.wikipedia.org/wiki/Xkcd")

        // Obliga a usar webview en lugar de un navegador
        myWebView.setWebViewClient(WebViewClient())
        return view
    }
}