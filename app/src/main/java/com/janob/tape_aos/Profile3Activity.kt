package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile3Binding

class Profile3Activity : AppCompatActivity() {

    lateinit var binding : ActivityProfile3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.profile3ButtonBtn.setOnClickListener{


            //roomdb에 데이터저장
            val loginuserData = intent.getParcelableExtra<LoginUser>("loginuserData")
            Log.d("profile3", loginuserData.toString())

            val loginuserDB = TapeDatabase.Instance(this)!!

            if (loginuserData != null) {
                loginuserDB.loginuserDao().insert(loginuserData)
            }
            startActivity(Intent(this, MainActivity::class.java))

            val users = loginuserDB.loginuserDao().getLoginUsers() //정보가 잘들어갔나 log로 확인
            Log.d("profile3", users.toString())

            finish()
        }

    }

}