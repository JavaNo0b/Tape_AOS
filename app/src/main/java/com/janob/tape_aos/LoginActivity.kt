package com.janob.tape_aos


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    class LoginActivity : AppCompatActivity() {

        lateinit var binding: ActivityLoginBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.loginSignIn.setOnClickListener{
                onClick(it)
            }

        }


         fun onClick(view : View){
            when (view?.id) {
                view.id -> {
                    if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                        UserApiClient.instance.loginWithKakaoTalk(this)
                        { token, error ->

                            if (error != null) {
                                Log.d("login failure(onClick)", "카카오톡으로 로그인 실패 $error")
                                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                    return@loginWithKakaoTalk
                                } else {
                                    UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                                }
                            } else if (token != null) {
                                Log.d("login success(onClick)", "카카오톡으로 로그인 성공 ${token.accessToken}")
                                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()

                            }
                        }
                    } else {
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                    }
                }
            }
        }

        private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("login failure(mCallback)", "카카오 계정으로 로그인 실패 $error")
            } else if (token != null) {
                Log.d("login success(mCallback)", "카카오 계정으로 로그인 성공 ${token.accessToken}")
                Log.d("Login1111", "확인4")

            }

        }



    }

}