package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemLikeSongBinding

class LikeFeedRVAdapter(private var songList : List<Song>,
                        private var editStatus : Boolean,
                        private var allStatus : Boolean)
    : RecyclerView.Adapter<LikeFeedRVAdapter.ViewHolder>() {

    // 22
    var delete_list = ArrayList<Song>()
    var change_list = ArrayList<Song>()

    // 클릭 이벤트
    interface MyItemClickListener{
        fun onClick(song : Song, isChecked : Boolean, button : CompoundButton)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListner : MyItemClickListener){
        mItemClickListener = itemClickListner
    }
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): LikeFeedRVAdapter.ViewHolder {
        val binding : ItemLikeSongBinding = ItemLikeSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeFeedRVAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
        /*
        holder.binding.likeSongToggleBtnTb.setOnCheckedChangeListener { buttonView, isChecked ->
            mItemClickListener.onClick(songList[position], isChecked, buttonView)
            /*
            if(isChecked){
                deleteSongCount++
                Log.d("delete", deleteSongCount.toString())
            }
            else{ /deleteSongCount--
                Log.d("delete", deleteSongCount.toString())
            }
            */
        }
        */

        /* 인터페이스
        var likefeedFragment = LikeFeedFragment()
        likefeedFragment.setEditClickListener(object : LikeFeedFragment.EditClickListener{
            override fun onClick(status: Boolean) {
                holder.binding.likeSongToggleBtnTb.visibility = ToggleButton.VISIBLE
            }

        })
        */

        if(editStatus){ // 편집 클릭
            holder.binding.likeSongToggleBtnTb.visibility = ToggleButton.VISIBLE
            holder.binding.likeSongToggleBtnTb.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){ // 삭제할 노래 선택!
                    holder.binding.likeSongToggleBtnTb.setBackgroundResource(R.drawable.like_song_clicked_btn)

                    // 22 : delete_list에 추가
                    delete_list.add(songList[position])
                }
                else{ // 삭제할 노래 취소ㅠㅠ
                    holder.binding.likeSongToggleBtnTb.setBackgroundResource(R.drawable.like_song_unclicked_btn)

                    // 22 : delete_list에서 삭제
                    delete_list.remove(songList[position])
                }
            }


            // 전체 선택 클릭/해제(allStatus 상태 따라)
            // holder.binding.likeSongToggleBtnTb.isChecked = allStatus
            if(allStatus){
                holder.binding.likeSongToggleBtnTb.isChecked = true
                delete_list = ArrayList(songList)

                holder.binding.likeSongToggleBtnTb.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                        allStatus = false
                        delete_list.remove(songList[position])
                    }
                }
            }

        }
        else{ // 편집 해제
            holder.binding.likeSongToggleBtnTb.visibility = ToggleButton.GONE
        }


    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding : ItemLikeSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song : Song){
            binding.likeSongAlbumCoverIv.setImageResource(song.coverImg)
            binding.likeSongTitleTv.text = song.title
            binding.likeSongSingerTv.text = song.singer
            binding.likeSongAlbumNameTv.text = song.album

            // 전체 선택 클릭/해제(allStatus 상태 따라)
            //Log.d("status", "전체 선택 클릭 해제 : $allStatus")
            //binding.likeSongToggleBtnTb.isChecked = allStatus
        }
    }

    // 삭제 버튼 클릭 시 아이템들 삭제 후 데이터 재정의
    fun setItems(list : ArrayList<Song>){
        songList = list
        notifyDataSetChanged()
    }

    // 편집 toggle 클릭 status : true=편집 클릭!, false=편집 클릭 해제
    fun setEditStatus(b : Boolean){
        editStatus = b
        notifyDataSetChanged()
    }

    fun setAllStatus(b : Boolean){
        allStatus = b
        notifyDataSetChanged()
    }

    fun setDelete(){
        change_list = ArrayList(songList)
        for(i in 0..(delete_list.size - 1)){
            change_list.remove(delete_list[i])
        }
        songList = change_list.toList()

        notifyDataSetChanged()
    }
}