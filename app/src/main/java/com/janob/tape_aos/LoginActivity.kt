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
//asdfasdf

class LoginActivity : AppCompatActivity() {

    inner class NextActivityHandler {
        fun launchMainActivity() {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun launchOnboardActivity(userEmail: String) {
            val intent = Intent(this@LoginActivity, OnboardActivity::class.java)
            Log.d("Login->prof1 userEmail",userEmail)
            intent.putExtra("userEmail",userEmail)
            startActivity(intent)
            finish()
        }
    }

    private val nextActivityHandler = NextActivityHandler()

    //외부에서 LoginActivity를 사용하기 위함
//    init {
//        instance = this
//    }
//
//    companion object {
//        private var instance: LoginActivity? = null
//
//        fun getInstance(): LoginActivity? 		{
//            return instance
//        }
//    }

    lateinit var binding: ActivityLoginBinding
    var userToken: String = ""
    var userEmail: String = ""

    //카카오 로그인 정보 api 연동
//    private val kakaoLoginViewModel :KakaoLoginViewModel by lazy {
//        ViewModelProvider(this).get(KakaoLoginViewModel::class.java)
//    }

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
                    "\n사용자 이메일: ${userEmail}")

//            kakaoLoginViewModel.fetchUserInfo(userToken, userToken)
            getUserInfo(userEmail)

        }
    }

    private fun getUserInfo(userEmail:String){
        Log.d("Login1111", "hi")
        val service = getRetrofit().create(RetrofitInterface::class.java)
        service.searchKakaoInfo(userToken, userEmail).enqueue(object: Callback<KakaoResponse>{
            override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {
                val resp = response.body()!!
                Log.d("searchKakaoInfo_resp", resp?.success.toString())
                if(resp.success) {
//                    Log.d("searchKakaoInfo[SUCCESS]", resp.message)
                    NextActivity(resp.data.isSignin, userEmail)
                }else {
//                    Log.d("searchKakaoInfo[Failure]", resp.message)
                }
            }

            override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                Log.d("searchKakaoInfo: onFailure", t.message.toString())
            }

        })
    }

    fun NextActivity(isSignIn: Boolean, userEmail: String) {
        if (isSignIn) {
            nextActivityHandler.launchMainActivity()
        } else {
            nextActivityHandler.launchOnboardActivity(userEmail)
        }
    }
}