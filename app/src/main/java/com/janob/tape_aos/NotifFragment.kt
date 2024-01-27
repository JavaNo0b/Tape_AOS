package com.janob.tape_aos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentNotifBinding
class NotifFragment : Fragment() {

    lateinit var binding: FragmentNotifBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifBinding.inflate(inflater, container, false)

        val notifRVAdapter = NotifRVAdapter()
        binding.notifRv.adapter=notifRVAdapter //리사이클러뷰 어댑터와 연결
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }

}
/*
class NotifFragment : Fragment() {

    lateinit var binding: FragmentNotifBinding
    lateinit var NotifAdapter : NotifRVAdapter
    val datas = mutableListOf<Notif_Multi>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifBinding.inflate(inflater, container, false)

        datas.apply {
            add(Notif_Multi(R.layout.item_notif_1))
            add(Notif_Multi(R.layout.item_notif_2))
            add(Notif_Multi(R.layout.item_notif_3))
            NotifAdapter.datas = datas
            NotifAdapter.notifyDataSetChanged()
        }

        binding.notifRv.adapter=NotifAdapter //리사이클러뷰 어댑터와 연결
        binding.notifRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }

}*/