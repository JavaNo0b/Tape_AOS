package com.janob.tape_aos

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
    lateinit var user : User // 타유저
    lateinit var my_user : User // 내유저

    private val followFragment = FollowFragment()

    private var follow_btn_status : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherprofileBinding.inflate(inflater, container, false)

        // ** 검색 recyclerView -> 데이터 받기 **
        val userJson = arguments?.getString("user")
        user = gson.fromJson(userJson, User::class.java)
        setInit(user)

        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)


        // ** tabLayout과 viewPager2 연결 **
        val otherprofileAdapter = OtherprofileVPAdapter(this)
        binding.otherprofileContentVp.adapter = otherprofileAdapter
        TabLayoutMediator(binding.otherprofileContentTb, binding.otherprofileContentVp){
            tab, position -> tab.text = info[position]
        }.attach()

        // ** 팔로잉 버튼 클릭 리스너 **
        followBtnClick()

        // ** 팔로워, 팔로잉 text 클릭 리스너 **
        followTextClick()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setInit(user)

        // ** 팔로우 이미 됐는지 아닌지 상태 검사 **
        var user_follower_list = user.followerList

        follow_btn_status = false
        for(i in 0 until user.followerList.size){
            if(user_follower_list[i] == my_user.name){
                follow_btn_status = true
                break
            }
        }

        // ** 버튼 디자인 세팅 **
        followBtnSetInit()
    }

    private fun setInit(user : User){
        // search에서 넘어온 데이터 재설정
        binding.otherprofileProfileIv.setImageResource(user.userImg!!)
        binding.otherprofileNameTv.text = user.name
        binding.otherprofileCommentTv.text = user.comment

        binding.otherprofileTapeNumTv.text = user.tapeList.size.toString()
        binding.otherprofileFollowerNumTv.text = user.followerList.size.toString()
        binding.otherprofileFollowingNumTv.text = user.followingList.size.toString()

        // 테이프 게시글 Feed 설정 구현 이어서 진행
    }

    // ** 버튼 디자인 세팅 **
    private fun followBtnSetInit(){
        if(follow_btn_status){ // 팔로우 이미 됐음
            binding.otherprofileFollowBtn.setBackgroundResource(R.drawable.follow_clicked_btn)
            binding.otherprofileFollowBtn.text = "팔로잉"
            binding.otherprofileFollowBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        }
        else{ // not 팔로우
            binding.otherprofileFollowBtn.setBackgroundResource(R.drawable.follow_unclicked_btn)
            binding.otherprofileFollowBtn.text = "팔로우"
            binding.otherprofileFollowBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }
    // ** 팔로잉 버튼 클릭 리스너 **
    private fun followBtnClick(){
        binding.otherprofileFollowBtn.setOnClickListener {
            var change_other_user_follower_list = ArrayList(user.followerList)
            var change_my_user_following_list = ArrayList(my_user.followingList)

            if(follow_btn_status){ // 팔로우 이미 됐음 -> 팔로우 취소ㅠㅠ
                // 버튼 디자인 re세팅
                binding.otherprofileFollowBtn.setBackgroundResource(R.drawable.follow_unclicked_btn)
                binding.otherprofileFollowBtn.text = "팔로우"
                binding.otherprofileFollowBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                // 타유저 팔로워, 내유저 팔로잉 리스트 변경
                change_other_user_follower_list.remove(my_user.name)
                change_my_user_following_list.remove(user.name)

                // follow_btn_status 상태 설정
                follow_btn_status = false
            }
            else{ // not 팔로우 -> 팔로잉!
                // 버튼 디자인 re세팅
                binding.otherprofileFollowBtn.setBackgroundResource(R.drawable.follow_clicked_btn)
                binding.otherprofileFollowBtn.text = "팔로잉"
                binding.otherprofileFollowBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))

                // 타유저 팔로워, 내유저 팔로잉 리스트 변경
                change_other_user_follower_list.add(my_user.name)
                change_my_user_following_list.add(user.name)

                // follow_btn_status 상태 설정
                follow_btn_status = true
            }
            user.followerList = change_other_user_follower_list.toList()
            my_user.followingList = change_my_user_following_list.toList()

            // db변경
            TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowerList(user.followerList, user.name)
            TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowingList(my_user.followingList, my_user.name)

            // 팔로워 text re세팅
            binding.otherprofileFollowerNumTv.text = user.followerList.size.toString()
        }
    }

    private fun followTextClick(){
        binding.otherprofileFollowerLl.setOnClickListener {
            val status : String = "follower"

            // activity ver
            /*
            val intent = Intent(activity, FollowActivity::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
            */

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)

                        val gson = Gson()
                        val userJson = gson.toJson(user)
                        putString("other_user", userJson)
                    }
                })
                .commitAllowingStateLoss()
        }
        binding.otherprofileFollowingLl.setOnClickListener {
            val status : String = "following"

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)
                    }
                })
                .commitAllowingStateLoss()
        }
    }
}