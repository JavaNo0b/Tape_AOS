package com.janob.tape_aos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostBinding

const val TAG = "TAPE_SONG_LIST_FRAGMENT"

class PostFragment : Fragment(),
    PostSelectTypeFragment.SelectTypeListener,
    PostIntroductionFragment.PostIntroductionListener,
    PostSongsListFragment.SongsListListener,
    PostIncludedSongsFragment.IncludedSongsListener,
    PostBackHomeFragment.PostBackToHomeListener{

    lateinit var binding : FragmentPostBinding
    private var type :Int =  TYPE_NONE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)

        //프래그먼트 교체
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostSelectTypeFragment())
            .addToBackStack(null)
            .commit()

        return binding.root
    }

    override fun onSelectTypeCompleted(type: Int) {

        if(type == TYPE_SINGLE){
            this.type = TYPE_SINGLE
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostSongsListFragment())
                .addToBackStack(null)
                .commit()
        }
        else if(type == TYPE_ALBUM){
            this.type = TYPE_ALBUM
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostIntroductionFragment())
                .addToBackStack(null)
                .commit()
        }
    }
    private lateinit var imageUri:Uri
    private lateinit var title:String
    private lateinit var content:String
    override fun onPostIntroductionCompleted() {

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostSongsListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onSongsListCompleted() {

        if(type == TYPE_ALBUM){
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostIncludedSongsFragment())
                    //.instance(this.imageUri, this.title, this.content))
                .addToBackStack(null)
                .commit()
        }
        else{
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostIncludedSongsFragment())
                .addToBackStack(null)
                .commit()
        }



    }

    override fun onIncludedSongsCompleted() {

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostBackHomeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onPostAllCompleted() {
        var intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

}