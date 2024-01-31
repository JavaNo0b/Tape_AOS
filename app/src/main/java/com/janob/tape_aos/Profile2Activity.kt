package com.janob.tape_aos

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.ByteArrayOutputStream
import java.util.Collections.min
import kotlin.math.min

class Profile2Activity : AppCompatActivity() {
    lateinit var binding: ActivityProfile2Binding
    lateinit var imageBitmap : Bitmap
    private lateinit var imageUri: Uri


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

                val Intro : String = binding.profile2IntroEt.text.toString()

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

                if(imageUri ==null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, Profile3Activity::class.java)
                    //intent.putExtra("userid", Userid)
                    intent.putExtra("imageUri", imageUri.toString())
                    Log.d("Login1111", imageUri.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }


    }

    //갤러리에서 이미지 가져오기
    val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {
        try {
            val calRatio = calculateInSampleSize(
                it.data!!.data!!,
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio

            var inputStream = contentResolver.openInputStream(it.data!!.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
            inputStream!!.close()
            inputStream = null

            bitmap?.let {
                binding.profile2PicIv.setImageBitmap(bitmap)
                imageUri = bitmapToUri(bitmap)
                Log.d("Login1111", imageUri.toString())
            } ?: let{
                Log.d("kkang", "bitmap null")
            }
        }catch (e: Exception){
            e.printStackTrace()
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


    fun checkProfile(): Boolean {
        Log.d("profile2", "확인8")
        if (binding.profile2IntroEt.text.toString().length > 150) {
            binding.profile2IntroErrorTv.visibility = View.VISIBLE
            return false
        }
        Log.d("profile2", "확인9")
        return true
    }


    private fun bitmapToUri(bitmap: Bitmap): Uri {
        val context = applicationContext
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }


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

}

