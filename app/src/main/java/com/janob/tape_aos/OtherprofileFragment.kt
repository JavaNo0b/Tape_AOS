package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentOtherprofileBinding

class OtherprofileFragment : Fragment() {

    lateinit var binding : FragmentOtherprofileBinding
    private val info = arrayListOf("테이프")

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherprofileBinding.inflate(inflater, container, false)

        // 검색 recyclerView -> 데이터 받기
        val userJson = arguments?.getString("user")
        val user = gson.fromJson(userJson, User::class.java)
        setInit(user)

        // tabLayout과 viewPager2 연결
        val otherprofileAdapter = OtherprofileVPAdapter(this)
        binding.otherprofileContentVp.adapter = otherprofileAdapter
        TabLayoutMediator(binding.otherprofileContentTb, binding.otherprofileContentVp){
            tab, position -> tab.text = info[position]
        }.attach()

        return binding.root
    }

    private fun setInit(user : User){
        binding.otherprofileProfileIv.setImageResource(user.userImg!!)
        binding.otherprofileNameTv.text = user.name
        binding.otherprofileCommentTv.text = user.comment

        // binding.테이프tv.text도 설정해주어야 함
        binding.otherprofileFollowerNumTv.text = user.followerList.size.toString()
        binding.otherprofileFollowingNumTv.text = user.followingList.size.toString()
    }
}