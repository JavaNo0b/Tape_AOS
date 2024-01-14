package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostFm3Binding

class PostFm3Fragment : Fragment() {

    interface PostFm3Listener{ fun onPostFm3() }
    lateinit var listener : PostFm3Listener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is PostFm3Fragment.PostFm3Listener){
            listener = parentFragment as PostFm3Fragment.PostFm3Listener
        }
        else{
            throw Exception("PostFm3Listener 미구현")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPostFm3Binding.inflate(inflater, container, false)

        binding.btnPostContinue.setOnClickListener {
            listener.onPostFm3()
        }

        return binding.root
    }

}