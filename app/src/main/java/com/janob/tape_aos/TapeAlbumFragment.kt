package com.janob.tape_aos

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentTapeAlbumBinding

class TapeAlbumFragment : Fragment() {
    lateinit var imageUri : Uri
    lateinit var imageView : ImageView
    lateinit var imageIcView : ImageView

    interface TapeAlbumListener { fun onTapeAlbum()}
    lateinit var listener : TapeAlbumListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is TapeAlbumListener){
            listener = parentFragment as TapeAlbumListener
        }
        else{
            throw Exception("인터페이스 구현하세요")
        }
    }
    val callBack : ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val intent = it.data
            intent?.data?.let{
                imageUri = it
                imageView.setImageURI(imageUri)
                imageIcView.visibility = View.INVISIBLE

            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTapeAlbumBinding.inflate(inflater)

        //이미지뷰 참조
        imageView = binding.backgroundPhoto
        imageIcView = binding.icPhoto

        binding.backgroundPhoto.setOnClickListener {
            //사진앱 데이터 베이스 접근 요청
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            callBack.launch(intent)
        }
        binding.btnPostContinue.setOnClickListener {
            listener.onTapeAlbum()
        }
        return binding.root
    }
}