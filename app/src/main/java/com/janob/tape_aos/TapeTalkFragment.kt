package com.janob.tape_aos

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentTapeTalkBinding

const val TYPE_INACTIVE  = 0
const val TYPE_ACTIVE = 1

class TapeTalkFragment : Fragment() {

    //겔러리 연동
    private lateinit var imageView : ImageView
    private var imageUri : Uri? = null

    interface TapeTalkListener{ fun onTapeTalk() }
    lateinit var listener : TapeTalkListener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is TapeTalkFragment.TapeTalkListener){
            listener = parentFragment as TapeTalkFragment.TapeTalkListener
        }
        else{
            throw Exception("인터페이스 미구현")
        }
    }
    lateinit var binding : FragmentTapeTalkBinding
    private lateinit var editText : TextView
    private var text : String? = null
    private var type : Int = 0
    private fun updateUI(){
        if(text.isNullOrBlank() || text?.length!! < 15){
            type = TYPE_INACTIVE
            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_inactive)
        }else{
            type = TYPE_ACTIVE
            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTapeTalkBinding.inflate(inflater, container, false)
        editText = binding.commentEditText


//        binding.commentEditText.addTextChangedListener(object:TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                text = s.toString()
//                editText.text = text
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
        binding.btnPostContinue.setOnClickListener {
            //if(type == TYPE_ACTIVE)
                listener.onTapeTalk()
        }


        return binding.root
    }

}