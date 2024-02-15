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
            val intent = Intent(this@Profile1Activity, Profile2Activity::class.java)
            Log.d("profile1->2 email", email)
            Log.d("profile1->2 kakaoemail", kakaoEmail)
            Log.d("profile1->2 nickname", nickname)
            intent.apply {
                putExtra("userEmail", email)
                putExtra("nickname", nickname)
            }
            startActivity(intent)
            finish()
        }
    }

    private val nextActivityHandler = NextActivityHandler()

    lateinit var binding: ActivityProfile1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kakaoEmail = intent.getStringExtra("userEmail").toString()
        binding = ActivityProfile1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        //todo : 빈공간 누르면 키보드 hide
        binding.profile1NicknameEt.setOnFocusChangeListener { view, hasFocus -> }


        binding.profile1ButtonBtn.setOnClickListener {
            var nickname = binding.profile1NicknameEt.text.toString()



            if (checkProfile()) {
                postNickname(NicknameData(nickname))
            }
                /*val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!
                val Nickname = binding.profile1NicknameEt.text.toString()
                val Intent = intent
                val Userid = Intent.getLongExtra("userid", 0)

                Log.d("Login1111", Nickname)
//                Log.d("Login1111", Userid.toString())
//                val User : LoginUser? = loginuserDB.getLoginUser(Userid)
//
//                User?.let {
//                    User.nickname = Nickname
//                    Log.d("Login1111", User.nickname!!)
//                    loginuserDB.updateUser(User)
//                }
//
//                Log.d("Login1111", loginuserDB.getLoginUsers().toString())
//
//                val intent = Intent(this, Profile2Activity::class.java)
//                intent.putExtra("userid", Userid)
//                startActivity(intent)
//                finish()*/
//
//
//                postNickname(binding.profile1NicknameEt.text.toString())


        }
    }

    fun checkProfile(): Boolean {    //프로필조건 충족하는지 확인

        Log.d("Profile1", "첫번째 오류임")
        if (binding.profile1NicknameEt.text.isEmpty() || binding.profile1NicknameEt.text.toString().length > 20) {
            Log.d("Profile1", "첫번째 오류")
            binding.profile1NicknameError2Tv.visibility = View.VISIBLE
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "첫번째 오류")
            return false

        } else if (!profile1NicknameEtCheck(binding.profile1NicknameEt.text.toString())) {
            binding.profile1NicknameError1Tv.visibility = View.VISIBLE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "두번째 오류")
            return false
        }
        else if(CheckExistNickname(binding.profile1NicknameEt.text.toString())) {//다른 아이디와 같을때
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.VISIBLE
            Log.d("Profile1", "세번째 오류")
            return false
        }
        Log.d("Profile1", "성공")


        return true
    }


    private fun profile1NicknameEtCheck(string : String) :Boolean{  //영어, 숫자, 마침표, _ 외 다른 거 있는지 확인
        val check = "[a-zA-Z0-9._]+".toRegex()
        return string.matches(check)
    }


    private fun CheckExistNickname(string : String) :Boolean {  //이미 있는 닉네임인지 확인
        val Nicknamedb = TapeDatabase.Instance(this).loginuserDao()!!
        val Nickname : LoginUser? = Nicknamedb.getLoginUserNickname(string)

        // 입력된 닉네임과 동일한 닉네임이 이미 존재하는지 확인
        val existNickname : Boolean = string.equals(Nickname?.nickname)
        return existNickname
    }

    /*
    fun checkProfile(): Boolean {    //프로필조건 충족하는지 확인

        Log.d("Profile1", "첫번째 오류임")
        if (binding.profile1NicknameEt.text.isEmpty() || binding.profile1NicknameEt.text.toString().length > 20) {
            Log.d("Profile1", "첫번째 오류")
            binding.profile1NicknameError2Tv.visibility = View.VISIBLE
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "첫번째 오류")
            return false

        } else if (!profile1NicknameEtCheck(binding.profile1NicknameEt.text.toString())) {
            binding.profile1NicknameError1Tv.visibility = View.VISIBLE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "두번째 오류")
            return false
        }
        else if(CheckExistNickname(binding.profile1NicknameEt.text.toString())) {//다른 아이디와 같을때
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.VISIBLE
            Log.d("Profile1", "세번째 오류")
            return false
        }
        Log.d("Profile1", "성공")


        return true
    }


    private fun profile1NicknameEtCheck(string : String) :Boolean{  //영어, 숫자, 마침표, _ 외 다른 거 있는지 확인
        val check = "[a-zA-Z0-9._]+".toRegex()
        return string.matches(check)
    }


    private fun CheckExistNickname(string : String) :Boolean {  //이미 있는 닉네임인지 확인
        val Nicknamedb = TapeDatabase.Instance(this).loginuserDao()!!
        val Nickname : LoginUser? = Nicknamedb.getLoginUserNickname(string)

        // 입력된 닉네임과 동일한 닉네임이 이미 존재하는지 확인
        val existNickname : Boolean = string.equals(Nickname?.nickname)
        return existNickname
    }

*/
//
//    private fun getNickname(){
//        //닉네임 get
//        profile1Service.getNickname().enqueue(object : Callback<Profile1Response>{
//            override fun onResponse(call: Call<Profile1Response>, response: Response<Profile1Response>) {
//                Log.d("Login1111", "닉네임 get 성공")
//                val profile1Response: Profile1Response = response.body()!!
//
//                if(!profile1Response.success){
//                    binding.profile1NicknameError1Tv.text = profile1Response.message
//                    //binding.profile1NicknameEt.text = nicknameET
//                }else{
//                    postNickname()
//                }
//            }
//            override fun onFailure(call: Call<Profile1Response>, t: Throwable) {
//                Log.d("Login1111", "닉네임 get 실패")
//
//            }
//        })
//    }
//
//    private fun postNickname(){
//
//        val profile1Service = getRetrofit().create(Profile1RetrofitInterface::class.java)
//        //닉네임 post
//        profile1Service.postNickname(ReturnNickname()).enqueue(object :Callback<Profile1Response>{
//            override fun onResponse(call: Call<Profile1Response>, response: Response<Profile1Response>) {
//                Log.d("Login1111", "닉네임 post 성공")
//                startActivity(Intent(this@Profile1Activity,Profile1Activity::class.java))
//            }
//
//            override fun onFailure(call: Call<Profile1Response>, t: Throwable) {
//                //Log.d("Login1111", t.message.toString())
//                Log.d("Login1111", "닉네임 post 실패 ")
//
//            }
//
//        })
//    }
//
//    private fun ReturnNickname() : Profile1{
//        return Profile1(binding.profile1NicknameEt.text.toString())
//    }


    private fun postNickname(nicknameData: NicknameData) {  //닉네임 저장
        val service = getRetrofit().create(RetrofitInterface::class.java)
        service.signupNickname(nicknameData).enqueue(object : Callback<NicknameResponse> {
            override fun onResponse(
                call: Call<NicknameResponse>,
                response: Response<NicknameResponse>
            ) {
                val resp = response.body()
                Log.d("postNickname_resp", resp?.success.toString())
//                Log.d("postNickname_resp", resp?.message.toString())
                if (resp!!.success) {
                    Log.d("postNickname_resp", resp.success.toString())
                    NextActivity(nicknameData.nickname, kakaoEmail)
                } else {   //닉네임 틀렸을 때 오류메시지
                    //binding.profile1NicknameError1Tv.text = resp.message
                }
            }

            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
                Log.d("postNickname: onFailure", t.message.toString())
            }

        })

    }

    fun NextActivity(nickname: String, email: String) {
        nextActivityHandler.launchProfile2Activity(nickname, kakaoEmail)
    }
}


