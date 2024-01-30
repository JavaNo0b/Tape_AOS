package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
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

    private val followFragment = FollowFragment()
    lateinit var profileVPAdapter : ProfileVPAdapter

    //lateinit var userDatas : List<User>
    lateinit var my_user : User
    lateinit var my_tape_list : ArrayList<Tape>

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        // ** RoomDB 데이터 받기 **
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)
        my_tape_list = ArrayList(my_user.tapeList)

        // ** tabLayout과 viewPager2 연결 **
        profileVPAdapter = ProfileVPAdapter(this)
        binding.profileContentVp.adapter = profileVPAdapter
        TabLayoutMediator(binding.profileContentTb, binding.profileContentVp){
                tab, position -> tab.text = info[position]
        }.attach()

        // ** 테이프 세팅 **
        profileVPAdapter.setTapeList(my_tape_list)

        // ** 팔로워, 팔로잉 text 클릭 리스너 **
        followTextClick()

        // ** 프로필 수정 버튼 클릭 -> 프로필 수정 activity로 전환 **
        binding.profileProfileEditBtn.setOnClickListener {
            val intent = Intent(activity, ProfileEditActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()

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

    private fun followTextClick(){
        binding.profileFollowerLl.setOnClickListener {
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
                        val userJson = gson.toJson(my_user)
                        putString("pass_user", userJson)
                    }
                })
                .commitAllowingStateLoss()
        }
        binding.profileFollowingLl.setOnClickListener {
            val status : String = "following"

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)

                        val gson = Gson()
                        val userJson = gson.toJson(my_user)
                        putString("pass_user", userJson)
                    }
                })
                .commitAllowingStateLoss()
        }
    }
}