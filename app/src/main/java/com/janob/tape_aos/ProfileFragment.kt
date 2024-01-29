package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding : FragmentProfileBinding

    private val info = arrayListOf("게시글", "좋아요 한 곡")

    //lateinit var userDatas : List<User>
    lateinit var my_user : User

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        // RoomDB 데이터 받기
        //userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)


        // tabLayout과 viewPager2 연결
        val profileAdapter = ProfileVPAdapter(this)
        binding.profileContentVp.adapter = profileAdapter
        TabLayoutMediator(binding.profileContentTb, binding.profileContentVp){
                tab, position -> tab.text = info[position]
        }.attach()

        // 프로필 수정 버튼 클릭 -> 프로필 수정 activity로 전환
        binding.profileProfileEditBtn.setOnClickListener {
            val intent = Intent(activity, ProfileEditActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Log.d("eunseo", "ProfileFragment - onStart 확인")
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)
    }

    // 처음 회원가입/로그인하고 내 프로필 정보 설정
    private fun setInit(user : User){
        binding.profileProfileIv.setImageResource(user.userImg!!)
        binding.profileNameTv.text = user.name
        binding.profileCommentTv.text = user.comment

        binding.profileTapeNumTv.text = user.tapeList.size.toString()
        binding.profileFollowerNumTv.text = user.followerList.size.toString()
        binding.profileFollowingNumTv.text = user.followingList.size.toString()

        // 테이프 게시글 Feed 설정 구현 이어서 진행
        // 좋아요 노래도 설정 구현 이어서 진행
    }
}