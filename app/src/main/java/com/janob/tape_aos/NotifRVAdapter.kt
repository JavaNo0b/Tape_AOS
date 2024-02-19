package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemNotif2Binding


class NotifRVAdapter(var dataList: List<AlarmInnerDTO>): RecyclerView.Adapter<NotifRVAdapter.ViewHolder>(){


    interface MyItemClickListener{ fun onItemClick(item: AlarmInnerDTO) }


    private lateinit var ItemClickListener: MyItemClickListener

    fun updateData(newDataList: List<AlarmInnerDTO>) {
        dataList = newDataList
        notifyDataSetChanged() // 데이터가 변경되었음을 알립니다.
    }

    fun setMyItemClickListener(Listener: MyItemClickListener){
        ItemClickListener = Listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotifRVAdapter.ViewHolder {
        val binding: ItemNotif2Binding = ItemNotif2Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifRVAdapter.ViewHolder, position: Int) {

        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(val binding : ItemNotif2Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(content: AlarmInnerDTO){
            binding.itemNotif2TextTv1.text = content.receiverNickname
            binding.itemNotif2TextTv2.text = Notifcontent2(content.alarmType)

            if(adapterPosition != RecyclerView.NO_POSITION){
                binding.itemNotif2.setOnClickListener {
                    ItemClickListener?.onItemClick(content)
                }
            }

        }


    }

    private fun Notifcontent2(alarmType: String) : String{
        return when (alarmType){
            "좋아요" -> " 님이 회원님의 Tape를 좋아합니다."
            "팔로우" -> " 님이 회원님을 팔로우 합니다."
            "댓글" -> " 님이 회원님의 테이프에 댓글을 작성했습니다."
            else -> throw IllegalArgumentException("없는 알림 메시지")
        }
    }
}


