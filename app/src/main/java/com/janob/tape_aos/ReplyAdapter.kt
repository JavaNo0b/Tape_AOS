package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemReplyBinding

class ReplyAdapter(private val replies : List<Reply>, private val context: Context) : RecyclerView.Adapter<ReplyAdapter.ReplyItemViewHolder>() {

    private val dataList = replies

    inner class ReplyItemViewHolder(val binding : ItemReplyBinding) : RecyclerView.ViewHolder(binding.root)
    {
        lateinit var reply : Reply

        //댓글 바인딩
        fun bind(reply : Reply){
            this.reply = reply

            var text = binding.replyTextTv
            text.text = reply.text

        }
        //replyEditBtn 클릭 시 menu
        init {
            binding.replyEditBtn.setOnClickListener {
                showPopup(it)
            }
        }

        //menu popup
        private fun showPopup(view: View) {
            val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.custom_reply_popup_menu, null)

            val width = 163.dpToPx(context)
            val height = 61.dpToPx(context)

            val popupWindow = PopupWindow(popupView, width, height, true)
            popupWindow.showAsDropDown(view, 0, 0)

        }

        //menu popup 사이즈 변경
        fun Int.dpToPx(context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyItemViewHolder {
        val binding : ItemReplyBinding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ReplyItemViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ReplyItemViewHolder, position: Int) {
        holder.bind(dataList[position])

    }
}