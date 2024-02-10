package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding : FragmentProfileBinding
    private val info = arrayListOf("게시글", "좋아요 한 곡")

    private val settingFragment = SettingFragment()
    private val followFragment = FollowFragment()
    lateinit var profileVPAdapter : ProfileVPAdapter

    //lateinit var userDatas : List<User>
    lateinit var my_user : User
    lateinit var my_tape_list : ArrayList<Tape>
    //lateinit var my_tape_list : ArrayList<TapeInnerDTO>

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()

    // api
    private val model : ProfileViewModel by viewModels()
    private fun apiLoad(){
        model.loadUserProfile()
        model.getAll().observe(viewLifecycleOwner, Observer { my_user ->
            binding.profileNameTv.text = my_user.data.userName
            binding.profileCommentTv.text = my_user.data.introduce
            Glide.with(this).load(my_user.data.userImage).into(binding.profileProfileIv)

            binding.profileFollowerNumTv.text = my_user.data.followers.toString()
            binding.profileFollowingNumTv.text = my_user.data.followings.toString()

            //my_tape_list = ArrayList(my_user.data.tapeData)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        // init
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)
        my_tape_list = ArrayList(my_user.tapeList)

        // tabLayout과 viewPager2 연결
        profileVPAdapter = ProfileVPAdapter(this)
        binding.profileContentVp.adapter = profileVPAdapter
        TabLayoutMediator(binding.profileContentTb, binding.profileContentVp){
                tab, position -> tab.text = info[position]
        }.attach()

        // 테이프 세팅
        profileVPAdapter.setTapeList(my_tape_list)

        // 팔로워, 팔로잉 text 클릭 리스너
        followTextClick()

        // 프로필 수정 버튼 클릭 -> 프로필 수정 activity로 전환
        binding.profileProfileEditBtn.setOnClickListener {
            val intent = Intent(activity, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        // 메뉴
        binding.profileMenuBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, settingFragment)
                .commitAllowingStateLoss()
        }

        // 임시
        binding.profilePostTv.setOnClickListener {
            val intent = Intent(activity, ProfilePostActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)

        profileVPAdapter.setTapeList(my_tape_list)
    }

    // 처음 회원가입/로그인하고 내 프로필 정보 설정
    private fun setInit(user : User){
        //val setImageUri : Uri? = (user.userImg)?.let { Uri.parse(it) }
        binding.profileProfileIv.setImageResource(user.userImg!!)
        binding.profileNameTv.text = user.name
        binding.profileCommentTv.text = user.comment

        binding.profileTapeNumTv.text = user.tapeList.size.toString()
        binding.profileFollowerNumTv.text = user.followerList.size.toString()
        binding.profileFollowingNumTv.text = user.followingList.size.toString()

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

            // 뒤로가기x
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