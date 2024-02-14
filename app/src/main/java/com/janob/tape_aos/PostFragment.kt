package com.janob.tape_aos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.janob.tape_aos.databinding.FragmentPostBinding


class PostFragment : Fragment(),
    PostSelectTypeFragment.SelectTypeListener,
    PostIntroductionFragment.PostIntroductionListener,
    PostSongsListFragment.SongsListListener,
    PostIncludedSongsFragment.IncludedSongsListener,
    PostBackHomeFragment.PostBackToHomeListener{

    //오늘의 테이프 등록
    private val postFragmentViewModel : PostFragmentViewModel by lazy {
        ViewModelProvider(this).get(PostFragmentViewModel::class.java)
    }


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

    override fun onPostIntroductionCompleted(uri: Uri, title: String, content: String) {

        //반드시 채워야 함
        postFragmentViewModel.tapeImg = uri.toString()
        postFragmentViewModel.tapeTitle = title
        postFragmentViewModel.tapeContent = content

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostSongsListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onSongsListCompleted(songDTOList : MutableList<SongDTO>) {


        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostIncludedSongsFragment().newInstance(songDTOList))
            .addToBackStack(null)
            .commit()

    }

    override fun onIncludedSongsCompleted(songList: MutableList<SongDTO>) {

        postFragmentViewModel.songDTOList = songList

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostBackHomeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onPostAllCompleted() {
        //오늘의 테이프 완성
        val tape = TodayTapeDTO(
            postFragmentViewModel.tapeImg,
            postFragmentViewModel.tapeTitle,
            postFragmentViewModel.tapeContent,
            postFragmentViewModel.songDTOList
        )

        //오늘의 테이프 서버에 전송
        val resultDTOLiveData = postFragmentViewModel.apiFetchr.postTodayTape(tape)
        Log.d("post fragment","now today tape ${resultDTOLiveData.value.toString()}")

        //메인액티비티
        var intent = Intent(context, MainActivity::class.java)
        startActivity(intent)

    }

}