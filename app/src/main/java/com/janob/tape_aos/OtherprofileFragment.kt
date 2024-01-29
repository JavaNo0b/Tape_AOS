package com.janob.tape_aos

import android.os.Bundle
import android.util.Log
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

    //
    private val followFragment = FollowFragment()

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
            // activity ver
            /*
            val intent = Intent(activity, FollowActivity::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
            */

            // fragment var
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)

                        val gson = Gson()
                        val userJson = gson.toJson(user)
                        putString("other_user", userJson)

                        Log.d("eunseo", "OtherprofileFragment - 팔로워 클릭")
                    }
                })
                .commitAllowingStateLoss()

        }
        binding.otherprofileFollowingLl.setOnClickListener {
            val status : String = "following"
            // activity ver
            /*
            val intent = Intent(activity, FollowActivity::class.java)
            intent.putExtra("status", status)
            startActivity(intent)
            */

            // fragment var
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)
                    }
                })
                .commitAllowingStateLoss()
        }

        // toggleButton 설정
        binding.otherprofileFollowToggleBtn.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // ** 팔로우 신청! **
                binding.otherprofileFollowToggleBtn.setBackgroundResource(R.drawable.follow_clicked_btn)
                binding.otherprofileFollowToggleBtn.text = "팔로잉"
                binding.otherprofileFollowToggleBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))

                // ** 레이아웃 팔로워 텍스트 변경 **
                binding.otherprofileFollowerNumTv.text = (user.followerList.size).toString()


                // ** 팔로워+1 **
                val my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1) // 내 user 받기
                // 타인 프로필 : 팔로워++
                val change_follower_list = ArrayList(user.followerList) // 타인 user 팔로워 리스트를 -> ArrayList로 변경
                change_follower_list.add(my_user.name) // 내 user 이름을 follower 리스트에 추가
                user.followerList = change_follower_list.toList() // ArrayList를 -> List로 변경
                // 내 프로필 : 팔로잉++
                val change_my_following_list = ArrayList(my_user.followingList) // 내 user의 팔로잉 리스트를 -> ArrayList로 변경
                change_my_following_list.add(user.name) // 타인 user 이름을 following 리스트에 추가


                // ** 팔로워 리사이클러뷰에 추가 : -> FollowFragment로 status 전달 **
                followFragment.setMyUserItemStatus("add_item")

                // ** 팔로워 ArrayList에 사용자 추가 **
                //
                //

                // ** db 유저의 팔로워리스트 재설정 **
                TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowerList(user.followerList, user.name) // 타인 프로필 팔로워 업데이트
                TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowingList(change_my_following_list, my_user.name) // 내 프로필 팔로잉 업데이트
            } else{
                // ** 팔로우 취소ㅠㅠ **
                binding.otherprofileFollowToggleBtn.setBackgroundResource(R.drawable.follow_unclicked_btn)
                binding.otherprofileFollowToggleBtn.text = "팔로우"
                binding.otherprofileFollowToggleBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                // ** 레이아웃 팔로워 텍스트 변경 **
                binding.otherprofileFollowerNumTv.text = (user.followerList.size).toString()

                // ** 팔로워-1 **
                val my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1) // 내 user 받기
                // 타인 프로필 : 팔로워--
                val change_follower_list = ArrayList(user.followerList) // 타인 user 팔로워 리스트를 -> ArrayList로 변경
                change_follower_list.remove(my_user.name) // 내 user 이름을 follower 리스트에서 삭제
                user.followerList = change_follower_list.toList() // ArrayList를 -> List로 변경
                // 내 프로필 : 팔로잉--
                val change_my_following_list = ArrayList(my_user.followingList) // 내 user의 팔로잉 리스트를 -> ArrayList로 변경
                change_my_following_list.remove(user.name) // 타인 user 이름을 following 리스트에서 삭제




                // ** 팔로워 리사이클러뷰에서 삭제 : -> FollowFragment로 status 전달 **
                followFragment.setMyUserItemStatus("delete_item")

                // ** 팔로워 ArrayList에 사용자 삭제 **
                //
                //

                // ** db 유저의 팔로워리스트 재설정 **
                TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowerList(user.followerList, user.name) // 타인 프로필 팔로워 업데이트
                TapeDatabase.Instance(context as MainActivity).userDao().updateUserFollowingList(my_user.followingList, my_user.name) // 내 프로필 팔로잉 업데이트
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

        // 테이프 게시글 Feed 설정 구현 이어서 진행
    }
}