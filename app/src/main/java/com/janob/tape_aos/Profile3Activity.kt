
package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    // 권한 요청 코드
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    lateinit var imageUriString: String
    lateinit var userEmail: String
    lateinit var nickname: String
    lateinit var Intro: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //권한요청
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
        }

        // URI를 이미지 뷰에 설정
        val Intent = intent
        imageUriString = Intent.getStringExtra("imageUri").toString()
        userEmail = Intent.getStringExtra("userEmail").toString()
        nickname = Intent.getStringExtra("nickname").toString()
        Intro = Intent.getStringExtra("Intro").toString()
        //val Userid = Intent.getLongExtra("userid", 0)
        Log.d("Login1111", imageUriString.toString())
        Log.d("Login1111", userEmail.toString())
        Log.d("Login1111", nickname.toString())
        Log.d("Login1111", Intro.toString())


        val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }
        Log.d("Login1", imageUri.toString())
        binding.profile3ImageIv.setImageURI(imageUri)

        //val imageFile = imageUri?.let { uriToFile(this@Profile3Activity, it) }

        //val imagecrop : Bitmap = cropCenterBitmap(imageUri, 360, 422)!!

        binding.profile3ImageIv.post {
            //val imgBitmap: Bitmap = binding.profile3ImageIv.drawToBitmap()
            //val imgbyteArray = imgBitmap.toByteArray()

            seccessOnClick()
        }


    }
    fun seccessOnClick(){
        val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }
        binding.profile3ButtonBtn.setOnClickListener {
//                val User : LoginUser? = loginuserDB.getLoginUser(Userid)
//                User?.let {  //profileimg 설정
//                    User.profileimg = imgbyteArray
//                    Log.d("Login1111", User.profileimg.toString())
//                    loginuserDB.updateUser(User)
//                }
            //Log.d("profile2 nickname", nickname.toString())
            Log.d("Login2", imageUri.toString())
            postImage(userEmail!!, nickname!!, Intro, imageUri!!)


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되었으므로 원하는 작업을 수행합니다.

                binding.profile3ImageIv.post {
                    //val imgBitmap: Bitmap = binding.profile3ImageIv.drawToBitmap()
                    //val imgbyteArray = imgBitmap.toByteArray()

                    seccessOnClick()
                }
            } else {
                // 권한이 거부되었으므로 필요한 조치를 취합니다.
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


    private fun postImage(email : String, nickname: String, Intro : String?, imageUri: Uri) {  //회원 정보 서버 저장(일단 이미지 자르기빼고 인텐트로 받아온 이미지 저장)
        val service = getRetrofit().create(RetrofitInterface::class.java)

        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        this.contentResolver.takePersistableUriPermission(imageUri, flag)

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
                .enqueue(object : Callback<SignResponse> {
                    override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                        //Log.d("postUser_resp", signupProfile)
                        Log.d("postUser_resp", response.body().toString())
                        val resp = response.body()
                        //Log.d("postUser_resp", resp?.success.toString())
                        if (resp != null && resp.success) {
                            Log.d("postUser_resp", resp?.success.toString())
                            //Log.d("postUser_resp", signUp.toString())
                            Log.d("postUser_resp", resp.data.jwt)
                            saveJwt(resp.data.jwt)
                            startActivity(Intent(this@Profile3Activity, MainActivity::class.java))
                        }else{
                            Log.d("Login1111", "postUser_resp is null")

                        }
                    }

                    override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                        Log.d("postUser: onFailure", t.message.toString())
                    }
                })
        }
    }
    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf.edit()

        // 키 값 : "jwt", 인자값 : jwt
        editor.putString("jwt", jwt)
        editor.apply()
    }
}