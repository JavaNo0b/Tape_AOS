package com.janob.tape_aos



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemNotif2Binding
import com.janob.tape_aos.databinding.ItemNotif3Binding
import com.janob.tape_aos.databinding.ItemNotif4Binding


class NotifRVAdapter : ListAdapter<AlarmInnerDTO, RecyclerView.ViewHolder>(DiffCallback()) {


    companion object{
        const val notif_4 = 0
        const val notif_2 = 1
        const val  notif_3 = 2
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View?
        return when(viewType){
            notif_4 -> {
                ViewHolder4(ItemNotif4Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
            notif_2 -> {
                ViewHolder2(ItemNotif2Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
            else -> {
                ViewHolder3(ItemNotif3Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data = getItem(position)
        when (holder) {
            is ViewHolder4 -> {
                holder.bind(data)
            }
            is ViewHolder2 -> {
                holder.bind(data)
            }
            is ViewHolder3 -> {
                holder.bind(data)
            }
        }
    }

    //override fun getItemCount() : Int = NotifData.size  자동구현

    override fun getItemViewType(position: Int): Int {
        //return notiflist[position].alarmId!!
        val alarmType = getItem(position).alarmType
        return when (alarmType){
            "좋아요" -> notif_4
            "팔로우" -> notif_2
            "댓글" -> notif_3
            else -> throw IllegalArgumentException("없는 alarm id")
        }
    }

    //좋아요
    inner class ViewHolder4(private val binding: ItemNotif4Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : AlarmInnerDTO){
            binding.itemNotif4TextTv.text = item.alarmContent
        }
    }

    //팔로우
    inner class ViewHolder2(private val binding: ItemNotif2Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : AlarmInnerDTO){

            binding.itemNotif2TextTv.text = item.alarmContent
        }
    }

    //댓글
    inner class ViewHolder3(private val binding: ItemNotif3Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : AlarmInnerDTO){
            binding.itemNotif3TextTv.text = item.alarmContent
        }
    }

    //새로운 데이터와 이전 데이터 차이 계산
    private class DiffCallback : DiffUtil.ItemCallback<AlarmInnerDTO>() {
        override fun areItemsTheSame(oldItem: AlarmInnerDTO, newItem: AlarmInnerDTO): Boolean {
            return oldItem.alarmId == newItem.alarmId
        }

        override fun areContentsTheSame(oldItem: AlarmInnerDTO, newItem: AlarmInnerDTO): Boolean {
            return oldItem == newItem
        }
    }
}