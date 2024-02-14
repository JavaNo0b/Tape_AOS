package com.janob.tape_aos

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostIncludedSongsBinding
import kotlin.properties.Delegates

class PostIncludedSongsFragment : Fragment() {

    //뷰 모델
    private val includedSongViewModel : IncludedSongViewModel by lazy{
        ViewModelProvider(this).get(IncludedSongViewModel::class.java)
    }

    fun newInstance(songDTOList: MutableList<SongDTO>) : PostIncludedSongsFragment{
        //정보 넘겨 받음
        includedSongViewModel.songList = songDTOList
        return PostIncludedSongsFragment()
    }

    interface IncludedSongsListener { fun onIncludedSongsCompleted(songDTOList: MutableList<SongDTO>) }
    lateinit var listener: IncludedSongsListener
    lateinit var binding : FragmentPostIncludedSongsBinding
    lateinit var continueBtn : ImageView


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

        binding = FragmentPostIncludedSongsBinding.inflate(inflater)
        continueBtn = binding.btnPostContinue
        recyclerView = binding.songsRecyclerView

        val adapter = IncludedSongAdapter(emptyList())
        val manager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        binding.btnPostContinue.setOnClickListener {
            listener.onIncludedSongsCompleted(includedSongViewModel.songList)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        includedSongViewModel.includedSongDTOLiveData.observe(
            viewLifecycleOwner,
            Observer{
                    songs ->
                Log.d("POST_INCLUDED_SONGS_FRAGMENT","now included songs ${songs.size}")
                updateUI(songs)
                if(songs.isNotEmpty()){
                    continueBtn.setImageResource(R.drawable.btn_continue_active)
                    continueBtn.setOnClickListener {
                        listener.onIncludedSongsCompleted(includedSongViewModel.songList)
                    }
                }
                else{
                    continueBtn.setImageResource(R.drawable.btn_continue_inactive)
                    continueBtn.setOnClickListener {
                        //
                    }
                }

            }


        )

    }

    private fun updateUI(songDTOList: List<SongDTO>){
        val adapter = IncludedSongAdapter(songDTOList)
        recyclerView.adapter = adapter
    }
    inner class IncludedSongAdapter(var includedSongs: List<SongDTO>) : RecyclerView.Adapter<IncludedSongItemViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): IncludedSongItemViewHolder {
            val view = layoutInflater.inflate(R.layout.item_included_song,parent,false)
            return IncludedSongItemViewHolder(view)
        }

        override fun getItemCount(): Int = includedSongs.size

        override fun onBindViewHolder(holder: IncludedSongItemViewHolder, position: Int) {
            var song = includedSongs[position]
            return holder.bind(song)
        }

    }
    inner class IncludedSongItemViewHolder(view:View) : ViewHolder(view) {
        lateinit var song : SongDTO
        val coverImg = view.findViewById<ImageView>(R.id.song_cover_img)
        val songTitle = view.findViewById<TextView>(R.id.song_title_tv)
        val songSinger = view.findViewById<TextView>(R.id.song_singer_tv)
        val songAlbumTitle = view.findViewById<TextView>(R.id.song_album_title)
        fun bind(song:SongDTO){

            this.song = song
            coverImg.setImageResource(R.drawable.albumcover_5)
            songTitle.text = "song.title"
            songSinger.text = "song.singer"
            songAlbumTitle.text = "song.album"

            itemView.setOnClickListener {
                includedSongViewModel.remove(song)

            }


        }

    }

}