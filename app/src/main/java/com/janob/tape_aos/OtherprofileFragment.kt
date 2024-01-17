package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        // 팔로워, 팔로잉 클릭했을 때 Activity로 전환
        binding.otherprofileFollowerLl.setOnClickListener {
            val status : String = "follower"
            val intent = Intent(activity, FollowActivity::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
        }
        binding.otherprofileFollowingLl.setOnClickListener {
            val status : String = "following"
            val intent = Intent(activity, FollowActivity::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
        }

        // toggleButton 설정
        binding.otherprofileFollowToggleBtn.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // 팔로우 신청!
                binding.otherprofileFollowToggleBtn.setBackgroundResource(R.drawable.follow_clicked_btn)
                binding.otherprofileFollowToggleBtn.text = "팔로잉"
                binding.otherprofileFollowToggleBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))

                // 팔로워+1
                binding.otherprofileFollowerNumTv.text = (user.followerList.size + 1).toString()

                // 팔로워 ArrayList에 사용자 추가
                //
                //
            } else{
                // 팔로우 취소ㅠㅠ
                binding.otherprofileFollowToggleBtn.setBackgroundResource(R.drawable.follow_unclicked_btn)
                binding.otherprofileFollowToggleBtn.text = "팔로우"
                binding.otherprofileFollowToggleBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                // 팔로워 다시 돌아옴
                binding.otherprofileFollowerNumTv.text = (user.followerList.size).toString()

                // 팔로워 ArrayList에 사용자 삭제
                //
                //
            }

        }

        return binding.root
    }

    private fun setInit(user : User){
        // search에서 넘어온 데이터 재설정
        binding.otherprofileProfileIv.setImageResource(user.userImg!!)
        binding.otherprofileNameTv.text = user.name
        binding.otherprofileCommentTv.text = user.comment

        binding.otherprofileTapeNumTv.text = user.tapeList.size.toString()
        binding.otherprofileFollowerNumTv.text = user.followerList.size.toString()
        binding.otherprofileFollowingNumTv.text = user.followingList.size.toString()
    }
}