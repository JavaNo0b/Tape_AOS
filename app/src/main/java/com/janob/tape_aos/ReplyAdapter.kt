package com.janob.tape_aos

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReplyAdapter(val replies : List<Reply>, val activity: ReplyActivity) : RecyclerView.Adapter<ReplyAdapter.ReplyItemViewHolder>() {

    private val dataList = replies

    class ReplyItemViewHolder(val view : View, val activity: ReplyActivity) : RecyclerView.ViewHolder(view)
    {
        lateinit var reply : Reply
        init{

            //클릭한 댓글만 수정
            view.setOnClickListener{
                val intent = Intent(it.context,ReplyModifyActivity::class.java)
                intent.putExtra("reply",reply)

                activity.startActivity(intent)

            }
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
        return ReplyItemViewHolder(view,activity)
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