package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemFeedBinding

class FeedRVAdapter(var userList : ArrayList<User>) : RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeedRVAdapter.ViewHolder {
        val binding : ItemFeedBinding = ItemFeedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(val binding : ItemFeedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : User){
            binding.itemFeedAlbumcoverImgIv.setImageResource(user.thumbnailImg!!)
        }
    }

}