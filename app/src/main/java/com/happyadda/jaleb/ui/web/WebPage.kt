package com.happyadda.jaleb.ui.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.web.*
import com.happyadda.jaleb.utils.vigenere

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPage(navController: NavController, string: String) {
    Log.d("TAG", "WebPage: $string")


    val nigState = rememberWebViewState(string)
    val nigNavigator = rememberWebViewNavigator()
    var nigCanGoBack by remember { mutableStateOf(false) }

    val nigFileData by remember { mutableStateOf<ValueCallback<Uri>?>(null) }
    var nigFilePath by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    fun nigProcessResult(data: Intent?) {
        if (nigFileData == null && nigFilePath == null) return

        var kakResultData: Uri? = null
        var kakResultsFilePath: Array<Uri>? = null
        if (data != null) {
            kakResultData = data.data
            kakResultsFilePath = arrayOf(Uri.parse(data.dataString))
        }
        nigFileData?.onReceiveValue(kakResultData)
        nigFilePath?.onReceiveValue(kakResultsFilePath)
    }

    val nigFileChooseForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                nigProcessResult(result.data)
            }
        }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WebView(state = nigState,
                navigator = nigNavigator,
                onCreated = { webView ->
                    webView.settings.apply {
                        javaScriptEnabled = true
                        allowContentAccess = true
                        domStorageEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                        setSupportMultipleWindows(false)
                        builtInZoomControls = true
                        useWideViewPort = true
                        setAppCacheEnabled(true)
                        displayZoomControls = false
                        allowFileAccess = true
                        lightTouchEnabled = true
                    }
                    webView.clearCache(false)
                    CookieManager.getInstance().setAcceptCookie(true)
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
                },
                client = object : AccompanistWebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        nigCanGoBack = view?.canGoBack() ?: false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        url?.let {
                            if (url.contains("gfdvr=penaids3o".vigenere()) || url.contains("fwehbatb.hwpl".vigenere())) {
                                navController.navigate("menu") {
                                    popUpTo("init") { inclusive = true }
                                }
                            }
                        }
                    }
                },
                chromeClient = object : AccompanistWebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        nigFilePath = filePathCallback
                        Intent(Intent.ACTION_GET_CONTENT).run {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "kamne/*".vigenere()
                            nigFileChooseForResult.launch(this)
                        }
                        return true
                    }
                }
            )
        }
    }
    BackHandler(nigCanGoBack) {
        nigNavigator.navigateBack()
    }
}