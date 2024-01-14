package com.janob.tape_aos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReplyAdapter(replies : List<Reply>) : RecyclerView.Adapter<ReplyAdapter.ReplyItemViewHolder>() {

    private val dataList = replies

    class ReplyItemViewHolder(val view : View) : RecyclerView.ViewHolder(view)
    {
        lateinit var reply : Reply
        //댓글 누르면 수정/삭제 프래그먼트로 전환
        init{

        }
        fun bind(reply : Reply){
            this.reply = reply

            var text = view.findViewById<TextView>(R.id.reply_text_tv)
            text.text = reply.text

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyItemViewHolder {
        //item view
        val view = LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return ReplyItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ReplyItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_reply
    }
}