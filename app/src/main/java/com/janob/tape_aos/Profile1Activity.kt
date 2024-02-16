package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile1Activity : AppCompatActivity() {
    lateinit var kakaoEmail: String
    inner class NextActivityHandler {
        fun launchProfile2Activity(nickname: String, email: String) {
            val intent = Intent(this@Profile1Activity,Profile2Activity::class.java)
            Log.d("profile1->2 email", email)
            Log.d("profile1->2 kakaoemail", kakaoEmail)
            Log.d("profile1->2 nickname", nickname)
            intent.apply {
                putExtra("userEmail",email)
                putExtra("nickname",nickname)
            }
            startActivity(intent)
            finish()
        }
    }

    private val nextActivityHandler = NextActivityHandler()

    lateinit var binding : ActivityProfile1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kakaoEmail = intent.getStringExtra("userEmail").toString()
        binding = ActivityProfile1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        //todo : 빈공간 누르면 키보드 hide
        binding.profile1NicknameEt.setOnFocusChangeListener { view, hasFocus -> }


        binding.profile1ButtonBtn.setOnClickListener {
            var nickname = binding.profile1NicknameEt.text.toString()

            postNickname(NicknameData(nickname))
        }
    }

    private fun postNickname(nicknameData: NicknameData){  //닉네임 저장
        val service = getRetrofit().create(RetrofitInterface::class.java)
        service.signupNickname(nicknameData).enqueue(object: Callback<NicknameResponse>{
            override fun onResponse(call: Call<NicknameResponse>, response: Response<NicknameResponse>) {
                val resp = response.body()
                Log.d("postNickname_resp", resp?.success.toString())
//                Log.d("postNickname_resp", resp?.message.toString())
                if(resp!!.success) {
                    Log.d("postNickname_resp", resp.success.toString())
                    NextActivity(nicknameData.nickname, kakaoEmail)
                }else {   //닉네임 틀렸을 때 오류메시지
                    binding.profile1NicknameError1Tv.text = resp.message
                }
            }

            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
                Log.d("postNickname: onFailure", t.message.toString())
            }

        })

    }

    fun NextActivity(nickname: String, email:String) {
        nextActivityHandler.launchProfile2Activity(nickname, kakaoEmail)
    }
}