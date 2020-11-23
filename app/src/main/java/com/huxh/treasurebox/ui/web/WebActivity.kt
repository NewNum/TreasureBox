package com.huxh.treasurebox.ui.web

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.base.activity.BaseActivity
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.activity_web.*
import java.io.File

class WebActivity : BaseActivity() {

    private var mWebView: WebView? = null

    override fun onViewCreate() {
        initWebView()
        initListener()
        toolbar.setTitle(intent?.getStringExtra(KEY_TITLE)?:"")
        mWebView?.loadUrl(intent?.getStringExtra(KEY_WEB_URL))
    }

    private fun initListener() {
//        toolbar.setOnBackClickListener {
//            onBackPressed()
//        }
//        toolbar.setOnRightImageClickListener {
//            super.onBackPressed()
//        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_web
    }

    override fun onBackPressed() {
        if (mWebView?.canGoBack() == true) {
            mWebView?.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun initWebView() {
        mWebView = WebView(this, null)
        flContainer.addView(
            mWebView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        mWebView?.webViewClient = object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        mWebView?.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                arg0: WebView, arg1: String, arg2: String,
                arg3: JsResult
            ): Boolean {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3)
            }

            override fun onProgressChanged(p0: WebView?, p1: Int) {
                pbWeb.visibility =
                    if (p1 == 100) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }

                pbWeb.progress = p1
            }
        }
        val webSetting = mWebView?.settings
        webSetting?.allowFileAccess = true
        webSetting?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting?.setSupportZoom(false)
        webSetting?.builtInZoomControls = true
        webSetting?.setSupportMultipleWindows(false)
        webSetting?.useWideViewPort = true
        // webSetting?.setLoadWithOverviewMode(true);
        webSetting?.setAppCacheEnabled(true)
        // webSetting?.setDatabaseEnabled(true);
        webSetting?.domStorageEnabled = true
        webSetting?.javaScriptEnabled = true
        webSetting?.setGeolocationEnabled(true)
        webSetting?.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting?.setAppCachePath(this.getDir("appcache", 0).getPath())
        webSetting?.databasePath = this.getDir("databases", 0).getPath()
        webSetting?.setGeolocationDatabasePath(
            cacheDir.absolutePath + File.separator + "Geolocation"
        )
        webSetting?.javaScriptCanOpenWindowsAutomatically = true
        webSetting?.cacheMode = WebSettings.LOAD_NO_CACHE
        webSetting?.pluginState = WebSettings.PluginState.ON_DEMAND
        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().sync()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mWebView?.loadUrl(intent?.getStringExtra(KEY_WEB_URL))
    }

    override fun onDestroy() {
        mWebView?.destroy()
        super.onDestroy()
    }

    companion object {
        private const val KEY_WEB_URL = "KEY_WEB_URL"
        private const val KEY_TITLE = "KEY_TITLE"
        fun newIntent(context: Context, url: String?, title: String = "") =
            Intent(context, WebActivity::class.java).apply {
                putExtra(KEY_WEB_URL, url)
                putExtra(KEY_TITLE, title)
            }
    }
}