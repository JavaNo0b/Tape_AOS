package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostSongsListBinding
import com.janob.tape_aos.databinding.ItemSongBinding

class PostSongsListFragment : Fragment() {

    //뷰 모델
    private val apiResponseViewModel : ApiResponseViewModel by lazy{
        ViewModelProvider(this).get(ApiResponseViewModel::class.java)
    }
    private val songListViewModel : SongListViewModel by lazy {
        ViewModelProvider(this).get(SongListViewModel::class.java)
    }
    private val includedSongListViewModel : IncludedSongListViewModel by lazy{
        ViewModelProvider(this).get(IncludedSongListViewModel::class.java)
    }
    lateinit var manager :LinearLayoutManager
    lateinit var adapter : SongAdapter
    lateinit var view : RecyclerView
    interface SongsListListener { fun onSongsListCompleted() }
    lateinit var listener: SongsListListener
    lateinit var binding : FragmentPostSongsListBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is PostSongsListFragment.SongsListListener){
            listener = parentFragment as SongsListListener
        }
        else{
            throw Exception("인터페이스 미구현")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostSongsListBinding.inflate(inflater, container,false)

        view = binding.searchSongRecyclerView
        manager = LinearLayoutManager(context)
        adapter = SongAdapter(emptyList())

        view.layoutManager = manager
        view.adapter = adapter


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiResponseViewModel.responseLiveData.observe(
            viewLifecycleOwner,
            Observer {
                response->Log.d(TAG,"got response $response")
                //리사이클러뷰 adapter 갱신
            }
        )
        songListViewModel.songListLiveData.observe(
            viewLifecycleOwner,
            Observer{
                songs -> Log.d(TAG,"got songs ${songs.size}")
                updateUI(songs)
            }
        )
        binding.btnPostContinue.setOnClickListener {
            listener.onSongsListCompleted()
        }
    }
    fun updateUI(songs : List<Song>){
        //통째로 리스트 교체
        adapter = SongAdapter(songs)
        view.adapter = adapter
    }


    inner class SongAdapter(var songs :List<Song>) : RecyclerView.Adapter<SongItemViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
            val binding = ItemSongBinding.inflate(layoutInflater)
            return SongItemViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return songs.size
        }

        override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {

            return holder.bind(songs[position])
        }

    }
    inner class SongItemViewHolder(val songBinding : ItemSongBinding) : ViewHolder(binding.root){
        private lateinit var song : Song
        private val add : ImageView = songBinding.btnAddToTape
        private val songTitle = songBinding.songTitleTv
        private val songSinger = songBinding.songSingerTv
        private val songCover = songBinding.songCoverImg
        private val songAlbumName = songBinding.songAlbumTitle

        fun bind(song : Song){
            this.song = song
            songTitle.text = song.title
            songSinger.text = song.singer
            songCover.setImageResource(song.coverImg)
            songAlbumName.text = song.album

            add.setOnClickListener {

                //클릭 이벤트
                includedSongListViewModel
                    .includedSongRepository
                    .add(IncludedSong(song.title, song.singer, song.coverImg, 10))

                //토글

            }

        }
    }
}