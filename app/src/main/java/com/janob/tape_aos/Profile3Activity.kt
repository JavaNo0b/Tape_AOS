package com.janob.tape_aos

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.janob.tape_aos.databinding.ActivityProfile3Binding
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException


class Profile3Activity : AppCompatActivity() {

    lateinit var binding : ActivityProfile3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!

        // URI를 이미지 뷰에 설정
        val Intent = intent
        val imageUriString = Intent.getStringExtra("imageUri")
        val Userid = Intent.getLongExtra("userid", 0)
        Log.d("Login1111", imageUriString.toString())

        val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }

        //val imagecrop : Bitmap = cropCenterBitmap(imageUri, 360, 422)!!



        binding.profile3ImageIv.setImageURI(imageUri)

        binding.profile3ImageIv.post {
            val imgBitmap: Bitmap = binding.profile3ImageIv.drawToBitmap()
            val imgbyteArray = imgBitmap.toByteArray()

            binding.profile3ButtonBtn.setOnClickListener {
                val User : LoginUser? = loginuserDB.getLoginUser(Userid)
                User?.let {  //profileimg 설정
                    User.profileimg = imgbyteArray
                    Log.d("Login1111", User.profileimg.toString())
                    loginuserDB.updateUser(User)
                }

                Log.d("Login1111", loginuserDB.getLoginUsers().toString())

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
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
}


