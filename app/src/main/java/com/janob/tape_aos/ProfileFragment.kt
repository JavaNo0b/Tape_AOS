package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val info = arrayListOf("게시글", "좋아요 한 곡")

    lateinit var userDatas : List<User>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        // RoomDB 데이터 받기
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // tabLayout과 viewPager2 연결
        val otherprofileAdapter = OtherprofileVPAdapter(this)
        binding.otherprofileContentVp.adapter = otherprofileAdapter
        TabLayoutMediator(binding.otherprofileContentTb, binding.otherprofileContentVp){
                tab, position -> tab.text = info[position]
        }.attach()

        return binding.root
    }
}