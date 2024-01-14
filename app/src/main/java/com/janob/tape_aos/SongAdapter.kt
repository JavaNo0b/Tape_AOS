package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.w3c.dom.Text

class SongAdapter(songs : List<Song>) : RecyclerView.Adapter<SongAdapter.SongItemViewHolder>(){

    private var dataList = songs

    class SongItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var song : Song

        //+ 뷰홀더에 클릭 이벤트
        init{
            view.setOnClickListener{
                view.findViewById<ImageView>(R.id.btn_add_to_tape).setImageResource(R.drawable.btn_added_to_tape)
            }
        }
        fun bind(s : Song){
            this.song = s
            view.findViewById<ImageView>(R.id.song_cover_img).setImageResource(song.coverImg)
            view.findViewById<TextView>(R.id.song_title_tv).text = song.title
            view.findViewById<TextView>(R.id.song_singer_tv).text = song.singer
            view.findViewById<TextView>(R.id.song_album_title).text = song.album
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return SongItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_song
    }
}