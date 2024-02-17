package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile2Binding

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class Profile2Activity : AppCompatActivity() {
    lateinit var binding: ActivityProfile2Binding
    lateinit var imageBitmap: Bitmap
    lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo : 빈공간 누르면 키보드 hide
        binding.profile2IntroEt.setOnFocusChangeListener { view, hasFocus -> }

        binding.profile2PicIv.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            //intent.putExtra("crop", true)
            requestGalleryLauncher.launch(intent)

        }

        binding.profile2ButtonBtn.setOnClickListener {
            if (checkProfile()) {

                val Intro: String = binding.profile2IntroEt.text.toString()
                var nickname = intent.getStringExtra("nickname")
                var userEmail = intent.getStringExtra("userEmail")

                Log.d("profile2 email", userEmail.toString())
                Log.d("profile2 nickname", nickname.toString())
/*
                if (imageUri == null) {  //갤러리 선택 안했을 때
                    //val postUserSignUp : SignUp =
                    postImage(userEmail!!, nickname!!, Intro, null)
                }*/

                Log.d("Login1111", imageUri.toString())
                Log.d("Login1111", Intro)



                val intent = Intent(this, Profile3Activity::class.java)

                //postImage(userEmail!!, nickname!!, Intro, imageUri!!)

                intent.apply {   //갤러리 선택안했을 땐 인텐트로 보내기
                    putExtra("userEmail", userEmail)
                    putExtra("nickname", nickname)
                    putExtra("imageUri", imageUri.toString())
                    putExtra("Intro", Intro)
                }
                startActivity(intent)
                finish()


                /*val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!
                val Intent = intent
                val Userid = Intent.getLongExtra("userid", 0)


                Log.d("Login1111", Userid.toString())
                val User : LoginUser? = loginuserDB.getLoginUser(Userid)
                User?.let {
                    User.profileintro = Intro
                    Log.d("Login1111", User.profileintro.toString())
                    loginuserDB.updateUser(User)
                }

                Log.d("Login1111", loginuserDB.getLoginUsers().toString())
*/

            }
        }


    }
/*
    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            //사각형 모양으로 자른다
            .start(this)
    }
*/

    //갤러리에서 이미지 가져오기
    val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                imageUri = it
                Log.d("kkang", "bitmap null")
                //cropImage(uri)
                try {
                    val calRatio = calculateInSampleSize(
                        uri,
                        resources.getDimensionPixelSize(R.dimen.imgSize),
                        resources.getDimensionPixelSize(R.dimen.imgSize)
                    )
                    val option = BitmapFactory.Options()
                    option.inSampleSize = calRatio

                    var inputStream = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                    inputStream!!.close()
                    inputStream = null

                    bitmap?.let {
                        binding.profile2PicIv.setImageBitmap(bitmap)
                    } ?: let {
                        Log.d("kkang", "bitmap null")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)


            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    private fun bitmapToUri(bitmap: Bitmap): Uri {
        val context = applicationContext
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }


    fun checkProfile(): Boolean {
        Log.d("profile2", "확인8")
        if (binding.profile2IntroEt.text.toString().length > 150) {
            binding.profile2IntroErrorTv.visibility = View.VISIBLE
            return false
        }
        Log.d("profile2", "확인9")
        return true
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

    private fun postImage(email: String, nickname: String, Intro: String?, imageUri: Uri?) {  //회원 정보 서버 저장(일단 이미지 자르기빼고 인텐트로 받아온 이미지 저장)
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
        } else {
            null
        }

        Log.d("Login1111", fileToUpload.toString())

        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val nicknameBody = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
        val introduceBody = Intro?.toRequestBody("text/plain".toMediaTypeOrNull())

        if(fileToUpload!=null){
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
                                // SharedPreference 에 accessToken 저장
                                //KaKaoApplication.prefs.setString("accessToken", resp.data.token)
                                //.d("postUser_resp", KaKaoApplication.prefs.toString())
                                startActivity(Intent(this@Profile2Activity, MainActivity::class.java))
                            } else {
                                Log.d("Login1111", "postUser_resp is null")

                            }
                        }

                        override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                            Log.d("postUser: onFailure", t.message.toString())
                        }
                    })
            }
        }else{
            introduceBody?.let {
                service.signupProfile(emailBody, nicknameBody, it, null)
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
                                //.d("postUser_resp", KaKaoApplication.prefs.toString())
                                startActivity(Intent(this@Profile2Activity, MainActivity::class.java))
                            } else {
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
}
    /*



        //이것도 보류
        /*fun addProfile() : ByteArray {
            val ImgNothingByteArray = imageToByteArray(this.getDrawable(R.drawable.prof2_layer))
            val ImgNothing = imageToByteArray(this.getDrawable(R.drawable.albumcover_5))   //기본이미지 아무렇게나 설정

            if (imageByte != ImgNothingByteArray) {   //갤러리에서 사진 선택한 경우
            }else{   ////갤러리에서 사진 선택안한 경우
                imageByte = ImgNothing
            }
            return imageByte
        }*/
    */
 /*   private fun postUser(signUp: SignUp){  //이미지 선택안했을 때 null로 저장

        val filePart = signUp.file?.let { fileToMultipartBodyPart(it) }
        val service = getRetrofit().create(RetrofitInterface::class.java)

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
        })*/
        /*if(filePart!=null){
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
        }*/


/*
//파일 형식을 MultipartBody.Part로 바꾸기
    fun fileToMultipartBodyPart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

}*/
*/
