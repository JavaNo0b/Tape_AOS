package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivitySplash2Binding


class Splash2Activity : AppCompatActivity() {
    lateinit var binding : ActivitySplash2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Splash1)

        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        }, 1000)

    }
}