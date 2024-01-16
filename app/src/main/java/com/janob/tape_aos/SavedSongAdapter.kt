package com.janob.tape_aos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemSavedSongBinding


class SavedSongAdapter() : RecyclerView.Adapter<SavedSongAdapter.SavedSongItemViewHolder>() {
    private lateinit var songs : ArrayList<Song>

    interface SongItemListener {fun onSongItemRemoved() }
    lateinit var listener : SongItemListener

    class SavedSongItemViewHolder(val binding : ItemSavedSongBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var song : Song
        fun bind(s : Song){
            this.song = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongItemViewHolder {
        val binding = ItemSavedSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedSongItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SavedSongItemViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.btnRemove.setOnClickListener {
            listener.onSongItemRemoved()
            removeSong(position)//리샤이클러뷰 notifyItemChanged
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }
}