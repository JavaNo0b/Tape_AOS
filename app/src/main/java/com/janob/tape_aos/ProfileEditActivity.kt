package com.janob.tape_aos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.janob.tape_aos.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileEditBinding
    lateinit var user : User

    //lateinit var imageBitmap : Bitmap

    private var imageUri : Uri? = null
    private lateinit var imageView : ImageView

    private lateinit var my_user : UserDTO

    private val gson : Gson = Gson()

    val gallery : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intent = it.data
                intent?.data?.let{
                    imageUri = it
                    imageView.setImageURI(imageUri)
                    Log.d("eunseo", "uri1 = " + imageUri.toString())

                }
            } else {
                imageUri = null
            }
        }

    private val model : ProfileEditViewModel by viewModels()
    private fun apiLoad(userDTO : UserDTO?){
        model.loadUserProfileEdit(userDTO!!)
        model.userProfileEdit.observe(this, Observer { my_user ->
            my_user?.userNickname = userDTO?.userNickname
            my_user?.introduce = userDTO?.introduce
            my_user?.profileImage = userDTO?.profileImage
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        imageView = binding.profileEditUserImgIv

        // init
        user = TapeDatabase.Instance(this).userDao().getMyUser(1)

        val userJson = intent.getStringExtra("pass_user")
        Log.d("eunseo", "ProfileEditActivity - init - userJson is null? = " + userJson.toString())
        my_user = gson.fromJson(userJson, UserDTO::class.java) //???
        Log.d("eunseo", "ProfileEditActivity - init - my_user.name = " + my_user?.userNickname)
        setInit(my_user)

        // 갤러리 앱
        binding.profileEditUserImgIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            gallery.launch(intent)
        }

        // 수정 완료 버튼
        binding.profileEditCompleteBtn.setOnClickListener {
            val name = binding.profileUserNameEdittextEt.text.toString()
            val comment = binding.profileUserCommentEdittextEt.text.toString()

            // uri를 user db에 저장 -> 나중에 구현
            //val setImageUri : Uri? = (imageUri.toString())?.let { Uri.parse(it) }
            //binding.profileEditUserImgIv.setImageURI(setImageUri)
            //TapeDatabase.Instance(this).userDao().updateUserImgByUserKey(imageUri.toString(), 1)

            //TapeDatabase.Instance(this).userDao().updateUserNameByUserKey(name, 1)
            //TapeDatabase.Instance(this).userDao().updateUserCommentByUserKey(comment, 1)
            if(imageUri == null){
                apiLoad(UserDTO(name, comment, null))
            } else{
                apiLoad(UserDTO(name, comment, imageUri.toString()))
            }


            finish()
        }

        // 취소 버튼
        binding.profileEditCancelBtnTv.setOnClickListener {
            finish()
        }

    }

    private fun setInit(user : UserDTO){
        //val setImageUri : Uri? = (user.userImg)?.let { Uri.parse(it) }
        //binding.profileEditUserImgIv.setImageResource(user.userImg!!)
        Glide.with(this).load(user.profileImage).into(binding.profileEditUserImgIv)
        binding.profileUserNameEdittextEt.setText(user.userNickname)
        binding.profileUserCommentEdittextEt.setText(user.introduce)
    }



    // 갤러리 변수
    /*
    val requestGalleryLaucher = registerForActivityResult(
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
                binding.profileEditUserImgIv.setImageBitmap(bitmap)
                //imageBitmap = bitmap
                imageUri = bitmapToUri(bitmap)
            } ?: let {
                Log.d("kkang", "bitmap null")
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


    // 갤러리 함수
    fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //로딩 하고자 하는 이미지의 각종 정보가 options에 설정
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e : Exception) {
            e.printStackTrace()
        }
        //비율 계산
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        // inSampleSize 비율 계산
        if(height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while(halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun bitmapToUri(bitmap : Bitmap) : Uri {
        val context = applicationContext
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }
    */
}