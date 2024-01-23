package com.janob.tape_aos

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile2Binding

import java.io.ByteArrayOutputStream

class Profile2Activity : AppCompatActivity() {
    lateinit var binding: ActivityProfile2Binding
    private lateinit var imageByte : ByteArray
    private val loginUserViewModel : LoginUserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.profile2PicIv.clipToOutline = true


        //todo : 빈공간 누르면 키보드 hide
        binding.profile2IntroEt.setOnFocusChangeListener { view, hasFocus ->
            Toast.makeText(this, "키보드 $hasFocus", Toast.LENGTH_SHORT).show()
        }


        binding.profile2PicIv.setOnClickListener {
            openGallery()
        }

        binding.profile2ButtonBtn.setOnClickListener {
            if (checkProfile()) {
                Log.d("profile2", "image{$imageByte}")
                loginUserViewModel.Modelintro = binding.profile2IntroEt.text.toString()
                loginUserViewModel.ModelprofileImg = imageByte
                startActivity(Intent(this, Profile3Activity::class.java))
                finish()

            }
        }


    }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(gallery)
    }


    fun checkProfile(): Boolean {
        if (binding.profile2IntroEt.text.toString().length > 150) {
            binding.profile2IntroErrorTv.visibility = View.VISIBLE
            return false
        }
        return true
    }


    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
//
//                        val bitmap =
//                            MediaStore.Images.Media.getBitmap(this.contentResolver, it)
//                        binding.profile2PicIv.setImageBitmap(bitmap)
//                        imageByte = uriToByteArray(selectedImageUri)

                }
            }
        }




    private fun uriToByteArray(uri: Uri): ByteArray {
        val inputStream = this.contentResolver.openInputStream(uri)
        return inputStream?.readBytes() ?: byteArrayOf()
    }

    private fun imageToByteArray(drawable: Drawable?): ByteArray {
        val Drawable = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        Drawable.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
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


    /*
        private fun OpenGallery(){
            launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == RESULT_OK) {
                        val intent = checkNotNull(result.data)
                        val imageUri = intent.data

                        Glide.with(requireContext())
                            .load(imageUri)
                            .into(binding.profile2PicIv)

                        //갤러리에서 이미지 가져와 ByteArray 변환
                        imageByte = imageUri?.let { uriToByteArray(it) }!!


                        checkOpenGallery()
                        // Room 데이터베이스에 저장
                        *//*if (imageByte != null) {  //갤러리에서 사진 선택했을 때
                        //profile2DB.loginuserDao().insertProfileimage(imageByte)
                        checkOpenGallery(imageByte, false)
                    } else {
                        val ImgnothingByteArray = imageToByteArray(requireContext().getDrawable(R.drawable.albumcover_5))
                        checkOpenGallery(ImgnothingByteArray, true)

                        //profile2DB.loginuserDao().insertProfileimage(ImgnothingByteArray)

                    }*//*
                }
            }

        binding.profile2PicIv.setOnClickListener {
            val intent = Intent().apply {
                type = "image/"
                action = Intent.ACTION_GET_CONTENT
            }
            launcher.launch(intent)
        }
    }


    private fun uriToByteArray(uri: Uri): ByteArray {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        return inputStream?.readBytes() ?: byteArrayOf()
    }

    private fun imageToByteArray(drawable: Drawable?): ByteArray {
        val Drawable = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        Drawable.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }



    fun checkOpenGallery() : ByteArray {
        val ImgnothingByteArray = imageToByteArray(requireContext().getDrawable(R.drawable.prof2_layer))
        val Imgnothing = imageToByteArray(requireContext().getDrawable(R.drawable.albumcover_5))   //기본이미지 아무렇게나 설정

        if (imageByte != ImgnothingByteArray) {   //갤러리에서 사진 선택한 경우
            return imageByte
        }else{   ////갤러리에서 사진 선택안한 경우
            return Imgnothing
        }
    }




    val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            try{
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.drawable.prof2_layer),
                    resources.getDimensionPixelSize(R.drawable.prof2_layer)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream =contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.profile2PicIv.setImageResource(bitmap)
                }?:let { Log.d("profile2", "bitmap pull") }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.profile2PicIv.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            pickImage.launch(intent)
        }

       */ */
