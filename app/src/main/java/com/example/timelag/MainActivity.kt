package com.example.timelag

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    private var webView:WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)
        initWebViewClient(webView)
        TimeLagPluginEntry.getInstance().bindService(this)
    }

    private fun initWebViewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.i(TAG, "加载完毕")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                TimeLagPluginEntry.getInstance().sendMessage("加载资源", 1)
                Log.i(TAG, "加载资源")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.i(TAG, "开始加载")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        private const val BAIDU_URL = "www.baidu.com"

        private const val TAOBAO_URL =
            "https://uland.taobao.com/semm/tbsearch?refpid=mm_26632258_3504122_32554087&keyword=%E5%A5%B3%E8%A3%85&bc_fl_src=alimama_sem_h5_mm_26632258_3504122_32554087&clk1=05be2a0c84e95793bf0684fa01c356ba&upsId=05be2a0c84e95793bf0684fa01c356ba"

        private const val JD_URL =
            "https://m.jd.com/?ad_od=3&cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=ba6fb982ce824f8382e493214bab3b10_0_2c51d2c85ec94e8f8cf59cc4df12c5ca"
    }

    fun jumpJd(view: View) {
        TimeLagPluginEntry.getInstance().sendMessage("跳转京东", 0)
        webView?.loadUrl(JD_URL)
    }

    fun jumpTaoBao(view: View) {
        TimeLagPluginEntry.getInstance().sendMessage("跳转淘宝", 0)
        webView?.loadUrl(TAOBAO_URL)
    }

    fun jumpBaiDu(view: View) {
        TimeLagPluginEntry.getInstance().sendMessage("跳转百度", 0)
        webView?.loadUrl(BAIDU_URL)
    }
}