package com.lnkj.weather.ui.web

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityWebBinding
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: 曾令贵
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 *  2022/2/13     曾令贵       v1.0.0        create
 **/
class WebMainActivity : BaseVMActivity<WebViewModel, WeatherActivityWebBinding>() {

    private var title = ""
    private var url = ""

    override val viewModel: WebViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_web

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.addTranslucentView(this, binding.toolbar)

        url = intent.getStringExtra("webUrl")
        title = intent.getStringExtra("webTitle")

        binding.tvTitle.text = title;

        initWebView()
        initWebSetting()
        loadUrl()

        binding.ivReturnBack.clickWithTrigger {
            finish()
        }
    }

    override fun initData() {

    }

    private fun initWebView() {
        binding.webView.getSettings().setUseWideViewPort(true)
        /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        binding.webView.getSettings().setLoadWithOverviewMode(true)
        //        dWebView.setLayerType(View.LAYER_TYPE_HARDWARE, new Paint());
        binding.webView.setBackgroundColor(resources.getColor(R.color.white))
        binding.webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Log.e("---webview", "加载页面完成")
            }

            override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                super.onReceivedSslError(view, handler, error)
                handler.proceed()
            }

            override fun shouldOverrideUrlLoading(view: WebView, loadUrl: String): Boolean {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }
        })
//        dWebView.setWebContentsDebuggingEnabled(true);
    }

    private fun initWebSetting() {
        val webSettings = binding.webView.settings
        //打开页面时， 自适应屏幕
        webSettings.useWideViewPort = true //设置此属性，可任意比例缩放。大视图模式
        webSettings.javaScriptEnabled = true //是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.setSupportZoom(true) //是否可以缩放，默认true
        //        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.domStorageEnabled = true //开启本地DOM存储
        webSettings.supportMultipleWindows()
        webSettings.allowContentAccess = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings.loadWithOverviewMode = true //和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.savePassword = true
        webSettings.saveFormData = true
        webSettings.javaScriptCanOpenWindowsAutomatically =
            true //设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.loadsImagesAutomatically = true // 加载图片
        webSettings.builtInZoomControls = false //是否显示缩放按钮，默认false
        webSettings.mediaPlaybackRequiresUserGesture = false //播放音频，多媒体需要用户手动？设置为false为可自动播放
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH) //提高渲染的优先级
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        webSettings.setBlockNetworkImage(true);//将图片下载阻塞

//        webSettings.setAppCachePath(localPath + "/" + WebConstants.CLIENT);
    }

    private fun loadUrl() {
        binding.webView.loadUrl(url)
    }
}