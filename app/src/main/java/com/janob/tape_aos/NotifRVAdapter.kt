package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.janob.tape_aos.databinding.ItemNotifBinding

class NotifRVAdapter : RecyclerView.Adapter<NotifRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotifRVAdapter.ViewHolder {
        val binding: ItemNotifBinding = ItemNotifBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifRVAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount() : Int =4

    inner class ViewHolder(val binding : ItemNotifBinding) : RecyclerView.ViewHolder(binding.root){

    }

}