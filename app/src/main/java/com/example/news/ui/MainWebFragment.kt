package com.example.news.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.FragmentMainWebBinding
import com.example.news.utils.AppConstants
import com.example.news.utils.ViewUtils

/**
 * Created by SURYA N on 22/3/20.
 */
class MainWebFragment : Fragment() {

    private var binding : FragmentMainWebBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_web,container,false)
        initView()
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding?.web?.apply {
            settings?.javaScriptEnabled = true
            webViewClient = CustomWebViewClient(context)
            webChromeClient = WebChromeClient()
            settings?.javaScriptEnabled = true
            settings?.allowContentAccess = true
            settings?.allowFileAccess = true
            settings?.allowFileAccessFromFileURLs = true
            settings?.allowUniversalAccessFromFileURLs = true
            settings?.javaScriptCanOpenWindowsAutomatically = true
            isVerticalScrollBarEnabled = true
            loadUrl(arguments?.getString(AppConstants.WEB_KEY))
        }
    }

    private inner class CustomWebViewClient(var context: Context) : WebViewClient() {
        //private var alertDialog: AlertDialog ?= null

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
           // alertDialog = ViewUtils.showProgress(context,getString(R.string.loading))
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if ((request?.url).toString().startsWith("tel:")) {
                    // It open the dialer app and allow user to call the number manually
                    val intent = Intent(Intent.ACTION_DIAL)
                    // Send phone number to intent as data
                    intent.data = request?.url
                    // Start the dialer app activity with number
                    startActivity(intent)
                    return true
                }
            }
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //ViewUtils.dismissProgress(alertDialog)
        }


        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)

        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
        }
    }
}