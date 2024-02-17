package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile3Binding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class Profile3Activity : AppCompatActivity() {

    lateinit var binding: ActivityProfile3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)



        val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!

        // URI를 이미지 뷰에 설정
        val Intent = intent
        val imageUriString = Intent.getStringExtra("imageUri")
        val userEmail = Intent.getStringExtra("userEmail")
        val nickname = Intent.getStringExtra("nickname")
        val Intro = Intent.getStringExtra("Intro")
        //val Userid = Intent.getLongExtra("userid", 0)
        Log.d("Login1111", imageUriString.toString())
        Log.d("Login1111", userEmail.toString())
        Log.d("Login1111", nickname.toString())
        Log.d("Login1111", Intro.toString())


        val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }
        binding.profile3ImageIv.setImageURI(imageUri)

        //val imageFile = imageUri?.let { uriToFile(this@Profile3Activity, it) }

        //val imagecrop : Bitmap = cropCenterBitmap(imageUri, 360, 422)!!


        binding.profile3ImageIv.post {
            //val imgBitmap: Bitmap = binding.profile3ImageIv.drawToBitmap()
            //val imgbyteArray = imgBitmap.toByteArray()

            binding.profile3ButtonBtn.setOnClickListener {
//                val User : LoginUser? = loginuserDB.getLoginUser(Userid)
//                User?.let {  //profileimg 설정
//                    User.profileimg = imgbyteArray
//                    Log.d("Login1111", User.profileimg.toString())
//                    loginuserDB.updateUser(User)
//                }
                //Log.d("profile2 nickname", nickname.toString())

                postImage(userEmail!!, nickname!!, Intro, imageUri!!)


            }
        }
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun uriToFile(context: Context, uri: Uri): File? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val filePath = it.getString(columnIndex)
                if (!filePath.isNullOrEmpty()) {
                    return File(filePath)
                }
            }
        }
        return null
    }


//    fun cropCenterBitmap(uri: Uri?, w: Int, h: Int): Bitmap? {
//        var src: Bitmap? = null
//        try {
//            src = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        if (src == null) return null
//
//
//        var x=0
//        var y=0
//        if (src.width > w) { x = (src.width - w) / 2 }
//        if (src.height > h) { y = (src.height - h) / 2 }
//
//
//        var cw = w // crop width
//        var ch = h // crop height
//        if (w > src.width) cw = src.width
//        if (h > src.height) ch = src.height
//        return Bitmap.createBitmap(src, x, y, cw, ch)
//    }


    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf.edit()

        // 키 값 : "jwt", 인자값 : jwt
        editor.putString("jwt", jwt)
        editor.apply()
    }


    private fun postImage(email : String, nickname: String, Intro : String?, imageUri: Uri) {  //회원 정보 서버 저장(일단 이미지 자르기빼고 인텐트로 받아온 이미지 저장)
        val service = getRetrofit().create(RetrofitInterface::class.java)

        Log.d("Login1111", email)
        Log.d("Login1111", nickname)
        Log.d("Login1111", Intro.toString())
        Log.d("Login1111", imageUri.toString())
        val fileToUpload = if (imageUri != null) {
            // URI를 파일로 변환
            val file = uriToFile(this, Uri.parse(imageUri.toString()))
            // 파일이 null이 아닌 경우에만 MultipartBody.Part 생성
            file?.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", it.name, requestBody)//MultipartBody.Part
            }
            } else {    null }

        Log.d("Login1111", fileToUpload.toString())

        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val nicknameBody = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
        val introduceBody = Intro?.toRequestBody("text/plain".toMediaTypeOrNull())

        introduceBody?.let {
            service.signupProfile(emailBody, nicknameBody, it, fileToUpload!!)
                .enqueue(object : Callback<UserProfileResponse> {
                    override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                        //Log.d("postUser_resp", signupProfile)
                        Log.d("postUser_resp", response.body().toString())
                        val resp = response.body()
                        //Log.d("postUser_resp", resp?.success.toString())
                        if (resp != null && resp.success) {
                            Log.d("postUser_resp", resp?.success.toString())
                            //Log.d("postUser_resp", signUp.toString())
                            Log.d("postUser_resp", resp.data.token)
                            //KaKaoApplication.prefs.setString("accessToken", resp.data.token)
                            //Log.d("postUser_resp", KaKaoApplication.prefs.toString())
                            saveJwt(resp.data.token)
                            startActivity(Intent(this@Profile3Activity, MainActivity::class.java))
                        }else{
                            Log.d("Login1111", "postUser_resp is null")

                        }
                    }

                    override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                        Log.d("postUser: onFailure", t.message.toString())
                    }
                })
        }
    }


}
        /*service.signupProfile(signUp).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                val resp = response.body()!!
                Log.d("postImage_resp", resp?.success.toString())
                if (resp.success) {
                    Log.d("postImage_resp", resp?.success.toString())
                    startActivity(Intent(this@Profile3Activity, MainActivity::class.java))
                } else {
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Log.d("postImage: onFailure", t.message.toString())
            }

        })*/




/*    fun fileToMultipartBodyPart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        Log.d("Login1111", requestFile.toString())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }*/
}

    /*
    private fun postUser(signUp: SignUp){

        val filePart = signUp.file?.let { fileToMultipartBodyPart(it) }
        val service = getRetrofit().create(RetrofitInterface::class.java)

        if(filePart!=null){
            service.signupProfile(signUp.email, signUp.nickname, signUp.introduce, filePart).enqueue(object: Callback<UserProfileResponse>{
                override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                    val resp = response.body()!!
                    Log.d("postUser_resp", resp?.success.toString())
                    if(resp.success) {
                        Log.d("postUser_resp", resp?.success.toString())
                        startActivity(Intent(this@Profile2Activity,MainActivity::class.java))
                    }else {
                        binding.profile2IntroErrorTv.text = resp.message
                    }
                }
                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    Log.d("postUser: onFailure", t.message.toString())
                }
            })
        }else{   //이미지 선택 안한 경우 null로 데이터를 넘김
            service.signupProfile(signUp.email, signUp.nickname, signUp.introduce, null).enqueue(object: Callback<UserProfileResponse>{
                override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                    val resp = response.body()!!
                    Log.d("postUser_resp", resp?.success.toString())
                    if(resp.success) {
                        Log.d("postUser_resp", resp?.success.toString())
                        startActivity(Intent(this@Profile2Activity,MainActivity::class.java))
                    }else {
                        binding.profile2IntroErrorTv.text = resp.message
                    }
                }
                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    Log.d("postUser: onFailure", t.message.toString())
                }
            })
        }


    }

    fun fileToMultipartBodyPart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }*/