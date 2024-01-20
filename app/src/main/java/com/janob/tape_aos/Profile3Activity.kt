package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile3Binding

class Profile3Activity : AppCompatActivity() {

    lateinit var binding : ActivityProfile3Binding
    private val loginUserViewModel : LoginUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.profile3ButtonBtn.setOnClickListener{


            //roomdb에 데이터저장
            val nickname = loginUserViewModel.Modelnickname
            val profileImg = loginUserViewModel.ModelprofileImg
            val intro = loginUserViewModel.Modelintro
            val token = loginUserViewModel.Modeltoken

            Log.d("profile3", "{$token, $nickname, $profileImg, $intro}")
            val loginuserDB = TapeDatabase.Instance(this)!!
            val user = LoginUser(token, nickname, profileImg, intro)

            loginuserDB.loginuserDao().insert(user)
            startActivity(Intent(this, MainActivity::class.java))

            val users = loginuserDB.loginuserDao().getLoginUsers() //정보가 잘들어갔나 log로 확인
            Log.d("profile3", users.toString())

            finish()
        }

    }

}