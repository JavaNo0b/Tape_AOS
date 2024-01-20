package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var token: String
    private val loginUserViewModel : LoginUserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        Log.d("test", "확인확인확인")

        var keyHash = Utility.getKeyHash(this)


        Log.d("test", "확인확인")
        setContentView(binding.root)

        binding.loginSignIn.setOnClickListener{
            onClick(binding.loginSignIn)
            Log.d("test", "확인")
        }

    }

    protected fun onClick(view : View){
        when (view?.id) {
            view.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        Log.d("test", "확인1")
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
                            firstlogincheck(token)
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
            firstlogincheck(token)
        }
    }


    private fun firstlogincheck(Token : OAuthToken){


        token = Token.toString()  //사용자 token String으로 변환해서 저장

        //이미 있는 계정인지 비교하기 위해 DB에서 정보 가져오기
        val TapeDB = TapeDatabase.Instance(this)!!
        val user = TapeDB.loginuserDao().getLoginUser(token)

        Log.d("Login", "token{$token}")
        val Equal : Boolean = token.equals(user.toString())



        loginUserViewModel.Modeltoken = token
        startActivity(Intent(this, OnboardActivity::class.java))
        finish()


        //다시 로그인하는 건 안됨... 일단 보류
     /*  if (Equal == true) { // 이후 로그인 시 처리
           startActivity(Intent(this, MainActivity::class.java))
        } else { // 처음 로그인 시 처리
           loginUserViewModel.Modeltoken = token
            //아이디를 저장하는 함수
           startActivity(Intent(this, OnboardActivity::class.java))
           finish()
        }*/
    }

}