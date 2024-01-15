package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentPostFm2Binding
import javax.sql.RowSetListener

class PostFm2Fragment : Fragment() {

    interface PostFm2Listener { fun onPostFm2() }
    lateinit var listener: PostFm2Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is PostFm2Fragment.PostFm2Listener){
            listener = parentFragment as PostFm2Listener
        }
        else{
            throw Exception("PostFm2Listener 미구현")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPostFm2Binding.inflate(inflater, container,false)

        //database 에서 데이터 가져옴
        var songs = TapeDatabase.Instance(context as MainActivity).songDao().getAll()

        //리사이클러뷰
        var manager = LinearLayoutManager(context)
        var adapter = SongAdapter(songs)

        var view = binding.searchSongRecyclerView
        view.layoutManager = manager
        view.adapter = adapter

        binding.btnPostContinue.setOnClickListener {
            listener.onPostFm2()
        }

        return binding.root
    }
}