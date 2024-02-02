package com.janob.tape_aos

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityWebViewBinding

class WebViewActivity:AppCompatActivity() {

    lateinit var binding:ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val webview = binding.webview
        webview.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        webview.loadUrl("http://3.34.42.155:3000/kakao")
    }
}