package com.janob.tape_aos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.janob.tape_aos.databinding.ItemSearchuserBinding

class SearchRVAdapter(private val context: Context, private var userList : List<UserResultInnerDTO>?) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(user : UserResultInnerDTO)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRVAdapter.ViewHolder {
        val binding : ItemSearchuserBinding = ItemSearchuserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList!![position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(userList!![position])
        }
    }

    override fun getItemCount(): Int {
        if(userList == null){
            return 0
        } else{
            return userList!!.size
        }
    }

    inner class ViewHolder(val binding : ItemSearchuserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : UserResultInnerDTO){
            Glide.with(context).load(user?.userImage).into(binding.itemSearchuserUserimgIv)
            binding.itemSearchuserNameTv.text = user.userNickname
            binding.itemSearchuserCommentTv.text = user.introduce
        }
    }

    // 검색 기능을 위한 추가 함수
    fun setItems(list : ArrayList<UserResultInnerDTO>){
        userList = list
        notifyDataSetChanged()
    }
}