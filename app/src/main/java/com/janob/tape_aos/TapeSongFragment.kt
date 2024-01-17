package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentTapeSongBinding

class TapeSongFragment : Fragment() ,
                        SongAdapter.SongAdapterListener {

    interface TapeSongListener { fun onAddTapeSong() }
    lateinit var listener: TapeSongListener
    lateinit var binding:FragmentTapeSongBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is TapeSongFragment.TapeSongListener){
            listener = parentFragment as TapeSongListener
        }
        else{
            throw Exception("인터페이스 미구현")
        }

    }
    override fun onStart() {
        super.onStart()
        //database 에서 데이터 가져옴
        var songs = TapeDatabase.Instance(context as MainActivity).songDao().getAll()

        //리사이클러뷰
        var manager = LinearLayoutManager(context)
        var adapter = SongAdapter(songs)

        adapter.setListener(this)

        var view = binding.searchSongRecyclerView
        view.layoutManager = manager
        view.adapter = adapter

        binding.btnPostContinue.setOnClickListener {
            listener.onAddTapeSong()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTapeSongBinding.inflate(inflater, container,false)


        return binding.root
    }

    private var songCount = 0
    override fun onAddSong(addOrNot : Boolean) {
       if(addOrNot)
           songCount+=1
        else
            songCount-=1

        binding.songCountTv.text = songCount.toString()
    }
}