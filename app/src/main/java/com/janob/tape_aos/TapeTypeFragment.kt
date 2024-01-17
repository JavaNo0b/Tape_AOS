package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentTapeTypeBinding

const val TYPE_NONE = 0
const val TYPE_SINGLE = 1
const val TYPE_ALBUM = 2
class TapeTypeFragment : Fragment() {


    interface TapeTypeListener { fun onTapeType(type : Int) }
    lateinit var listener : TapeTypeListener
    lateinit var binding : FragmentTapeTypeBinding

    private var type = 0 //

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //부모 프래그먼트와 연결
        if(parentFragment is TapeTypeFragment.TapeTypeListener){
            listener = parentFragment as TapeTypeListener
        }else{
            throw Exception("인터페이스 미구현")
        }
    }

    fun updateUI(){
        //토글
        if(type == TYPE_SINGLE ){
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_active)
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_inactive)

            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
        else if(type == TYPE_ALBUM){
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_active)
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_inactive)

            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
        else{
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_inactive)
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_inactive)

            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_inactive)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTapeTypeBinding.inflate(inflater,container,false)


        //버튼 활성화
        binding.backgroundSingleTape.setOnClickListener{
            type = TYPE_SINGLE
            updateUI()
        }
        binding.backgroundAlbumTape.setOnClickListener {
            type = TYPE_ALBUM
            updateUI()
        }

        binding.btnPostContinue.setOnClickListener {
            if(type != TYPE_NONE) {
                //다음페이지로..
                listener.onTapeType(type)
            }
        }
        return binding.root
    }
}