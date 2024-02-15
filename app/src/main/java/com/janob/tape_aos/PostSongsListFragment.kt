package com.janob.tape_aos

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostSongsListBinding

class PostSongsListFragment : Fragment() {

    //뷰 모델
    private val songListViewModel : SongListViewModel by lazy {
        ViewModelProvider(this).get(SongListViewModel::class.java)
    }

    interface SongsListListener { fun onSongsListCompleted(songDTOList : MutableList<SongDTO>) }
    lateinit var listener: SongsListListener

    // 리사이클러뷰
    lateinit var recyclerView:RecyclerView

    lateinit var binding : FragmentPostSongsListBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is SongsListListener){
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

        //recyclerView
        recyclerView = binding.searchSongRecyclerView
        val manager = LinearLayoutManager(context)
        val adapter= SongAdapter(emptyList())

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        //searchView
        val searchView = binding.searchView
        searchView.apply{
            setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                //글자 제출할 때마다
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("PostSongsListFragment","query text submit $query")
                    songListViewModel.fetchQueryTerm(query!!)
                    return true
                }
                //글자 바뀔 때마다
                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("PostSongsListFragment","query text change $newText")
                    return false
                }

            })
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //쿼리로 리스트 갱신하면 리사이클러뷰 연동
        songListViewModel.songListLiveData.observe(
            viewLifecycleOwner,
            Observer{
                result -> Log.d("post song list fragment","got songs ${result.songs}")
                updateUI(result.songs)
            }
        )


        songListViewModel.songsCount.observe(
            viewLifecycleOwner,
            Observer{
                songsCount->Log.d("post song list fragment","now songs count $songsCount")
                binding.includedSongCountTv.text = "$songsCount 곡을 선택하셨습니다"
                //다음 버튼
                if(songsCount > 0){
                    binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
                    binding.btnPostContinue.setOnClickListener{
                        listener.onSongsListCompleted(songListViewModel.includedSongList)
                    }
                }
                else{
                    binding.btnPostContinue.setImageResource(R.drawable.btn_continue_inactive)
                    binding.btnPostContinue.setOnClickListener {
                        //
                    }
                }




            }
        )

    }

    fun updateUI(songs : List<SongDetailDTO>){
        //통째로 리스트 교체
        val adapter = SongAdapter(songs)
        recyclerView.adapter = adapter
    }


    inner class SongAdapter(private var songs :List<SongDetailDTO>) :
        RecyclerView.Adapter<SongItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
            val view = layoutInflater.inflate(R.layout.item_song,parent,false)
            return SongItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return songs.size
        }

        override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {

            return holder.bind(songs[position])
        }

    }
    inner class SongItemViewHolder(view : View) : ViewHolder(view){

        private lateinit var songDTO : SongDTO
        private lateinit var includedSongDTO : SongDTO

        private var added = false
        private val songTitle = view.findViewById<TextView>(R.id.song_title_tv)
        private val songSinger = view.findViewById<TextView>(R.id.song_singer_tv)
        private val songCover = view.findViewById<ImageView>(R.id.song_cover_img)
        private val songAlbumName = view.findViewById<TextView>(R.id.song_album_title)

        fun bind(song : SongDetailDTO){
            this.songDTO = SongDTO(song.songId)

            songTitle.text = song.title
            songSinger.text = song.singer
            songCover.setImageURI(Uri.parse(song.albumCoverImg))
            songAlbumName.text = song.album

            includedSongDTO = SongDTO(song.songId)

            itemView.setOnClickListener {

                if(!added){
                    added = true
                    //클릭 이벤트
                    songListViewModel.includedSongList.add(includedSongDTO)
                    Log.d("post song list fragment","now add song ${song.title}")
                    songListViewModel.plusSong()
                    itemView.findViewById<ImageView>(R.id.btn_add_to_tape).setImageResource(R.drawable.btn_added_to_tape)
                    itemView.findViewById<ImageView>(R.id.btn_already_checked).visibility = View.VISIBLE
                }
                else{
                    added = false
                    songListViewModel.includedSongList.remove(includedSongDTO)
                    Log.d("post song list fragment","now delete song ${song.title}")
                    songListViewModel.minusSong()
                    itemView.findViewById<ImageView>(R.id.btn_add_to_tape).setImageResource(R.drawable.btn_add_to_tape)
                    itemView.findViewById<ImageView>(R.id.btn_already_checked).visibility = View.INVISIBLE
                }



            }


        }
    }
}