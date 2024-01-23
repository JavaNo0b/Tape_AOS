package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostIncludedSongsBinding

class PostIncludedSongsFragment : Fragment() {

    //뷰 모델
    private val includedSongListViewModel : IncludedSongListViewModel by lazy{
        ViewModelProvider(this).get(IncludedSongListViewModel::class.java)
    }
    interface IncludedSongsListener {fun onIncludedSongsCompleted()}

    lateinit var listener: IncludedSongsListener
    lateinit var adapter:IncludedSongAdapter
    lateinit var manager:LinearLayoutManager
    lateinit var recyclerView:RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is IncludedSongsListener)
            listener = parentFragment as IncludedSongsListener
        else
            throw Exception("인터페이스 미구현")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPostIncludedSongsBinding.inflate(inflater)
        recyclerView = binding.songsRecyclerView
        adapter = IncludedSongAdapter(emptyList())
        manager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        binding.btnPostContinue.setOnClickListener {
            listener.onIncludedSongsCompleted()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        includedSongListViewModel.getAllInAlbum(10)
        includedSongListViewModel.includedSongsLiveData.observe(
            viewLifecycleOwner,
            Observer{
                songs ->
                Log.d("POST_INCLUDED_SONGS_FRAGMENT","now included songs ${songs.size}")
                updateUI(songs)

            }


        )
    }

    fun updateUI(includedSongs: List<IncludedSong>){
        adapter = IncludedSongAdapter(includedSongs)
        recyclerView.adapter = adapter
    }
    inner class IncludedSongAdapter(var includedSongs: List<IncludedSong>) : RecyclerView.Adapter<IncludedSongItemViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): IncludedSongItemViewHolder {
            val view = layoutInflater.inflate(R.layout.item_song,parent,false)
            return IncludedSongItemViewHolder(view)
        }

        override fun getItemCount(): Int = includedSongs.size

        override fun onBindViewHolder(holder: IncludedSongItemViewHolder, position: Int) {
            var song = includedSongs[position]
            return holder.bind(song)
        }

    }
    inner class IncludedSongItemViewHolder(view:View) : ViewHolder(view) {
        lateinit var includedSong : IncludedSong
        fun bind(song:IncludedSong){

            includedSong = song

        }

    }
}