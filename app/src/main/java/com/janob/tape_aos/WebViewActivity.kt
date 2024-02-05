package com.janob.tape_aos

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityLoginBinding
import com.janob.tape_aos.databinding.ActivityWebBinding

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebBinding
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webView = binding.webView

        // 웹 설정 가져오기
        val webSettings: WebSettings = webView.settings

        // 자바스크립트 허용
        webSettings.javaScriptEnabled = true

        // 웹 최적화 로드
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        // 웹 확대/축소
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        // 웹에서 새창 열기
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        // 웹 페이지를 로드할 URL
        val url = intent.getStringExtra(EXTRA_URL)

    }
    companion object {
        const val EXTRA_URL = "extra_url"
    }
}