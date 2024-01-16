package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityReplyModifyBinding

class ReplyModifyActivity  : AppCompatActivity(){
    lateinit var reply : Reply


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityReplyModifyBinding.inflate(layoutInflater)

        reply = intent.getParcelableExtra("reply",Reply::class.java)!!



        setContentView(binding.root)
    }
}