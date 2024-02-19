package com.janob.tape_aos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    //lateinit var my_user : User
    lateinit var my_user : MyUser
    lateinit var my_tape_list : ArrayList<Tape>

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        //
        getJwt()

        // init
//        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
//        setInit(my_user)
//        my_tape_list = ArrayList(my_user.tapeList)
        //my_user = TapeDatabase.Instance(context as MainActivity).myUserDao().getAll()!!
        my_user = getJwt()
        setInit2(my_user)
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

//    override fun onStart() {
//        super.onStart()
//
////        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
////        setInit(my_user)
//        //my_user = TapeDatabase.Instance(context as MainActivity).myUserDao().getAll()
//        //setInit2(my_user)
//
//        profileVPAdapter.setTapeList(my_tape_list)
//    }

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
    private fun setInit2(user : MyUser){
        //val setImageUri : Uri? = (user.userImg)?.let { Uri.parse(it) }
        //Glide.with(requireContext()).load(user.userImg!!.toUri()).into(binding.profileProfileIv)
        val imageUri: Uri? = user.userImg?.let { Uri.parse(it) }
        binding.profileProfileIv.setImageURI(imageUri)
        binding.profileNameTv.text = user.name
        binding.profileCommentTv.text = user.comment

        binding.profileTapeNumTv.text = user.tapeList.size.toString()
        binding.profileFollowerNumTv.text = user.followerList.size.toString()
        binding.profileFollowingNumTv.text = user.followingList.size.toString()

        // 좋아요 노래도 설정 구현 이어서 진행
    }
    private fun getJwt() : MyUser {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        val userId = spf!!.getString("jwt", "")!!.toLong()
        val loginUser : LoginUser? = TapeDatabase.Instance(context as MainActivity).loginuserDao().getLoginUser(userId)

        var followerList : List<String> = arrayListOf("music_play", "k_pop_lover", "tape_123", "like_song", "_sing__", "dance_music__", "music_best", "listen_music_", "_k_pop__", "pop_song_", "user123")
        var my_user_followingList : List<String> = arrayListOf("myuser_1", "myuser_2", "myuser_3", "myuser_4", "myuser_5", "myuser_6", "myuser_7", "myuser_8", "myuser_9", "myuser_10")
        var tapeData = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        var songData = TapeDatabase.Instance(context as MainActivity).songDao().getAllList()
        val myUser : MyUser = MyUser(loginUser!!.profileimg, loginUser.nickname!!, loginUser.profileintro!!, followerList, my_user_followingList, tapeData, songData)

        return myUser
    }

    private fun followTextClick(){
        binding.profileFollowerLl.setOnClickListener {
            val status : String = "follower"

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