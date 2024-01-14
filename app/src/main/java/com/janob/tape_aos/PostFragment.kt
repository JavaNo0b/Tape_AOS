package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostBinding

class PostFragment : Fragment(),
    PostFm1Fragment.PostFm1Listener,
    PostFm2Fragment.PostFm2Listener,
    PostFm3Fragment.PostFm3Listener,
    PostFm4Fragment.PostFm4Listener {

    lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)

        //프래그먼트 교체
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostFm1Fragment())
            .commit()

        return binding.root
    }
    //콜백함수
    override fun onPostFm1() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostFm2Fragment())
            .commit()
    }

    override fun onPostFm2() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostFm3Fragment())
            .commit()
    }

    override fun onPostFm3() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostFm4Fragment())
            .commit()
    }

    override fun onPostFm4() {
        var intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}