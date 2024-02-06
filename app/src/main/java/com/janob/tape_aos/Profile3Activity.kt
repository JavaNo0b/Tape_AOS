package com.janob.tape_aos

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile3Binding

class Profile3Activity : AppCompatActivity() {

    lateinit var binding : ActivityProfile3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.profile3ButtonBtn.setOnClickListener{

            /*val Intent = intent
            //val image = Intent.getB("imageBitmap")

            Log.d("Login1111", image.toString())

            image?.let {
                binding.profile3ImageIv.setImageBitmap(image)
            }

*/
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let { BitmapFactory.decodeByteArray(it,0, it.size) }
    }

}


