package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemSongBinding

class SongAdapter(data : List<Song>) : RecyclerView.Adapter<SongAdapter.SongItemViewHolder>(){

    interface SongAdapterListener { fun onAddSong (addOrNot :Boolean) }
    private lateinit var listener : SongAdapterListener
    private var songs = data

    fun setListener(listener : SongAdapterListener){
        this.listener = listener
    }

    class SongItemViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var song : Song
        private var added : Boolean = false

        fun bind(s : Song){
            this.song = s
            binding.songCoverImg.setImageResource(song.coverImg)
            binding.songTitleTv.text = song.title
            binding.songSingerTv.text = song.singer
            binding.songAlbumTitle.text = song.album
        }
        private var isAdded :Boolean = false
        fun addSong() : Boolean{
            if(isAdded){
                binding.btnAddToTape
                    .setImageResource(R.drawable.btn_add_to_tape)
            }
            else{
                binding.btnAddToTape
                    .setImageResource(R.drawable.btn_added_to_tape)
            }

            isAdded = !isAdded
            return isAdded
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SongItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs.size

    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.btnAddToTape.setOnClickListener {
            var addOrNot = holder.addSong()
            listener.onAddSong(addOrNot)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_song
    }
}