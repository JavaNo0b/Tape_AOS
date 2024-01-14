package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostFm4Binding

class PostFm4Fragment : Fragment() {

    interface PostFm4Listener { fun onPostFm4() }
    lateinit var listener : PostFm4Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is PostFm4Listener){
            listener = parentFragment as PostFm4Listener
        }else{
            throw Exception("PostFm4Listener 미구현 ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPostFm4Binding.inflate(inflater,container,false)

        binding.btnToHome.setOnClickListener {
            listener.onPostFm4()
        }
        return binding.root
    }
}