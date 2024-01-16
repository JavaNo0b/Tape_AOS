package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostBinding

class PostFragment : Fragment(),
    TapeTypeFragment.TapeTypeListener,
    TapeSongFragment.TapeSongListener,
    TapeTalkFragment.TapeTalkListener,
    TapeHomeFragment.TapeHomeListener,
    TapeAlbumFragment.TapeAlbumListener,
    TapeCheckFragment.TapeCheckListener{

    lateinit var binding : FragmentPostBinding
    var type :Int =  TYPE_NONE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)

        //프래그먼트 교체
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TapeTypeFragment())
            .commit()

        return binding.root
    }


    override fun onAddTapeSong() {
        if(type == TYPE_SINGLE){
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TapeTalkFragment())
                .commit()
        }
        else if(type == TYPE_ALBUM){
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TapeCheckFragment())
                .commit()
        }

    }

    override fun onTapeTalk() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TapeHomeFragment())
            .commit()
    }

    override fun onTapeHome() {
        var intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onTapeType(type: Int) {
        if(type == TYPE_SINGLE){
            this.type = TYPE_SINGLE
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TapeSongFragment())
                .commit()
        }
        else if(type == TYPE_ALBUM){
            this.type = TYPE_ALBUM
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TapeAlbumFragment())
                .commit()
        }
    }

    override fun onTapeAlbum() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TapeSongFragment())
            .commit()
    }

    override fun onTapeCheck() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TapeHomeFragment())
            .commit()
    }
}