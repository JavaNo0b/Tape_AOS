package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.FragmentOtherprofileBinding

class OtherprofileFragment : Fragment() {

    lateinit var binding : FragmentOtherprofileBinding
    private val info = arrayListOf("테이프")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherprofileBinding.inflate(inflater, container, false)

        // tabLayout과 viewPager2 연결
        val otherprofileAdapter = OtherprofileVPAdapter(this)
        binding.otherprofileContentVp.adapter = otherprofileAdapter
        TabLayoutMediator(binding.otherprofileContentTb, binding.otherprofileContentVp){
            tab, position -> tab.text = info[position]
        }.attach()

        return binding.root
    }
}