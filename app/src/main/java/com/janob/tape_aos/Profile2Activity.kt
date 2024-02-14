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
            requestGalleryLauncher.launch(intent)

        }

        binding.profile2ButtonBtn.setOnClickListener {
            if (checkProfile()) {

                val Intro: String = binding.profile2IntroEt.text.toString()
                var nickname = intent.getStringExtra("nickname")
                var userEmail = intent.getStringExtra("userEmail")

                Log.d("profile2 email", userEmail.toString())
                Log.d("profile2 nickname", nickname.toString())

                /*if (imageUri == null) {  //갤러리 선택 안했을 때
                    //val postUserSignUp : SignUp =
                    //postUser(SignUp(userEmail!!, nickname!!, Intro, null))
                }*/

                Log.d("Login1111", imageUri.toString())
                Log.d("Login1111", Intro)

                val intent = Intent(this, Profile3Activity::class.java)

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


    //갤러리에서 이미지 가져오기
    val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                imageUri = it
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



    //파일 형식을 MultipartBody.Part로 바꾸기
    fun fileToMultipartBodyPart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

}