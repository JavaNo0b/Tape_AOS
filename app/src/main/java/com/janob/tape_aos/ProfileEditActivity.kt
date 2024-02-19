package com.janob.tape_aos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileEditBinding
    lateinit var user : User
    lateinit var tapeDatas : List<Tape>

    //lateinit var imageBitmap : Bitmap

    private lateinit var imageUri : Uri
    private lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        imageView = binding.profileEditUserImgIv

        // RoomDB 데이터 받기
        user = TapeDatabase.Instance(this).userDao().getMyUser(1)
        tapeDatas = TapeDatabase.Instance(this).tapeDao().getAll()

        // 초기 설정
        //val setImageUri : Uri? = (user.userImg)?.let { Uri.parse(it) }
        binding.profileEditUserImgIv.setImageResource(user.userImg!!)
        binding.profileUserNameEdittextEt.setText(user.name)
        binding.profileUserCommentEdittextEt.setText(user.comment)

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

            TapeDatabase.Instance(this).userDao().updateUserNameByUserKey(name, 1)
            TapeDatabase.Instance(this).userDao().updateUserCommentByUserKey(comment, 1)


            // uri를 user db에 저장 -> 나중에 구현
            //val setImageUri : Uri? = (imageUri.toString())?.let { Uri.parse(it) }
            //binding.profileEditUserImgIv.setImageURI(setImageUri)
            //TapeDatabase.Instance(this).userDao().updateUserImgByUserKey(imageUri.toString(), 1)

            finish()
        }

        // 취소 버튼
        binding.profileEditCancelBtnTv.setOnClickListener {
            finish()
        }

    }

    val gallery : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intent = it.data
                intent?.data?.let{
                    imageUri = it
                    imageView.setImageURI(imageUri)
                    Log.d("eunseo", "uri1 = " + imageUri.toString())

                }
            }
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