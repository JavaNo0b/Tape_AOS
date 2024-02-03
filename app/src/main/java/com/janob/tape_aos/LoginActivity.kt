package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.janob.tape_aos.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    //외부에서 LoginActivity를 사용하기 위함
    init {
        instance = this
    }

    companion object {
        private var instance: LoginActivity? = null

        fun getInstance(): LoginActivity? 		{
            return instance
        }
    }

    lateinit var binding: ActivityLoginBinding
    var userToken: String = ""
    var userEmail: String = ""

    //카카오 로그인 정보 api 연동
    private val kakaoLoginViewModel :KakaoLoginViewModel by lazy {
        ViewModelProvider(this).get(KakaoLoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignIn.setOnClickListener{
            Log.d("Login1111", "카카오회원가입")
            onClick(binding.loginSignIn)
        }
    }


    protected fun onClick(view : View){
        when (view?.id) {
            view.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        Log.d("Login1111", "확인2")
                        Log.d("test", "확인3")
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

                            //사용자 액세스 토큰 추출
                            userToken = token.accessToken
                            firstlogincheck()
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

            //사용자 액세스 토큰 추출
            userToken = token.accessToken
            firstlogincheck()
        }

    }


    private fun firstlogincheck(){

        Log.d("Login1111", "확인5")
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("Login1111", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("Login1111", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                //사용자 이메일 추출
                userEmail = user.kakaoAccount?.email!!
            }


            Log.i("Login1111", "사용자 정보 요청 성공" +
                    "\n사용자 액세스 토큰: ${userToken}" +
                    "\n사용자 이메일: ${userToken}")

            kakaoLoginViewModel.fetchUserInfo(userToken, userToken)
        }
    }

    fun NextActivity(isSignIn: Boolean){

        if(isSignIn){
            //가입한 적이 있는 유저이면 메인액티비티로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            //가입한 적이 없는 유저이면 회원가입을 위해 온보딩액티비티로 이동
            val intent = Intent(this, OnboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}