package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostFm1Binding

class PostFm1Fragment : Fragment() {

    interface PostFm1Listener { fun onPostFm1() }
    lateinit var listener : PostFm1Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //부모 프래그먼트와 연결
        if(parentFragment is PostFm1Fragment.PostFm1Listener){
            listener = parentFragment as PostFm1Listener
        }else{
            throw Exception("PostFm1Listener 미구현")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPostFm1Binding.inflate(inflater,container,false)


        //버튼 활성화
        binding.btnViewContainer.setOnClickListener{
            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
        binding.btnPostContinue.setOnClickListener {
            listener.onPostFm1()
        }
        return binding.root
    }
}