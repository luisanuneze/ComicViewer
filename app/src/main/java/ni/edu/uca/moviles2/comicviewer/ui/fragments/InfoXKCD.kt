package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import ni.edu.uca.moviles2.comicviewer.R

//Fragment para presentar la página de Wikipedia con la información de XKCD
class InfoXKCD : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout para este fragment
        val view = inflater.inflate(R.layout.fragment_info_xkcd, container, false)
        val myWebView: WebView = view.findViewById(R.id.webview)
        myWebView.loadUrl("https://es.wikipedia.org/wiki/Xkcd")

        // Obliga a usar webview en lugar de un navegador
        myWebView.setWebViewClient(WebViewClient())
        return view
    }
}