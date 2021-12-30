package com.example.taeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val url = "https://400ye.csb.app/" // สามารถเปลี่ยนลิ้งค์เป็นของเราเอง เพื่อใช้เว็บเราได้
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()

        val hostingUrl: String = getHostingUrl()
        SingleDomainWebViewClient(hostingUrl)

        //Clear Cache
        val settings: WebSettings = webview.settings
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.domStorageEnabled = true

        //Check JS Handle Limit By Android Version 17 Up Now policy is 21
        webview?.addJavascriptInterface(
            AnalyticsWebInterface(this),
            AnalyticsWebInterface.TAG
        )

        webview?.clearCache(true)
        webview?.loadUrl(hostingUrl)
    }

    private fun getHostingUrl(): String {
        return url
//        test git save
    }
}