package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentTapeHomeBinding

class TapeHomeFragment : Fragment() {

    interface TapeHomeListener { fun onTapeHome() }
    lateinit var listener : TapeHomeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is TapeHomeListener){
            listener = parentFragment as TapeHomeListener
        }else{
            throw Exception("인터페이스 미구현 ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentTapeHomeBinding.inflate(inflater,container,false)

        binding.btnToHome.setOnClickListener {
            listener.onTapeHome()
        }
        return binding.root
    }
}