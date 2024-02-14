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

        val imageFile = imageUri?.let { uriToFile(this@Profile3Activity, it) }

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

                postImage(SignUp(userEmail!!, nickname!!, Intro!!, imageFile))


            }
        }
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun uriToFile(context: Context, uri: Uri): File? {  //이미지 uri를 file형식으로 바꾸기
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return if (filePath != null) File(filePath) else null
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


    private fun postImage(signUp: SignUp) {  //회원 정보 서버 저장(일단 이미지 자르기빼고 인텐트로 받아온 이미지 저장)
        val service = getRetrofit().create(RetrofitInterface::class.java)

        val filePart = signUp.file?.let { fileToMultipartBodyPart(it) }
        service.signupProfile(signUp.email, signUp.nickname, signUp.introduce, filePart)
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    val resp = response.body()!!
                    Log.d("postUser_resp", resp?.success.toString())
                    if (resp.success) {
                        Log.d("postUser_resp", resp?.success.toString())
                        startActivity(Intent(this@Profile3Activity, MainActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    Log.d("postUser: onFailure", t.message.toString())
                }
            })
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

    }

    fun fileToMultipartBodyPart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
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
    }*/*/

