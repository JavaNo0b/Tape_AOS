package com.janob.tape_aos


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentNotifBinding
import kotlinx.coroutines.flow.combine


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