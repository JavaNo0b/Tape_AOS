package com.janob.tape_aos


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemNotif1Binding


class NotifRVAdapter : RecyclerView.Adapter<NotifRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotifRVAdapter.ViewHolder {
        val binding: ItemNotif1Binding = ItemNotif1Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifRVAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount() : Int =4

    inner class ViewHolder(val binding : ItemNotif1Binding) : RecyclerView.ViewHolder(binding.root){

    }

}
/*
class NotifRVAdapter(private val context: Context) : RecyclerView.Adapter<NotifRVAdapter.ViewHolder>() {

    var datas = mutableListOf<Notif_Multi>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotifRVAdapter.ViewHolder {
        val view : View?
        return when(viewType) {
            multi_type1 -> {
                view = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.item_notif_3,
                    viewGroup,
                    false
                )
                ViewHolder1(view)
            }
            multi_type2 -> {
                view = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.item_notif_2,
                    viewGroup,
                    false
                )
                ViewHolder2(view)
            }
            else -> {
                view = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.item_notif_1,
                    viewGroup,
                    false
                )
                ViewHolder3(view)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return datas[position].type
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            multi_type1 -> {
                (holder as ViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            multi_type2 -> {
                (holder as ViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as ViewHolder3).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }
    }


    inner class ViewHolder1(val binding : ItemNotif1Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Notif_Multi) {


        }
    }

}*/