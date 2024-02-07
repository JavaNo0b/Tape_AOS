package com.janob.tape_aos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    lateinit var followerFragment : FollowerFragment
    lateinit var followingFragment : FollowingFragment
    var my_status : String = ""

    // 팔로워, 팔로잉 리스트 대로 userDatas 재설정 변수
    var follower_list = ArrayList<String>()
    var following_list = ArrayList<String>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        followerFragment = FollowerFragment()
        followingFragment = FollowingFragment()

        // 팔로워 리사이클러뷰에 추가 : -> FollowerFragment로 status 전달
        followerFragment.setMyUserItemStatus(my_status)
        // 팔로워 리스트 대로 userDatas 재설정 : -> FollowerFragment로 status와 follower_list 전달
        followerFragment.setFollowerListStatus("set_follower_list", follower_list)

        // 팔로잉 리스트 대로 userDatas 재설정 : -> FollowingFragment로 status와 following_list 전달
        followingFragment.setFollowingListStatus("set_following_list", following_list)

        return when(position){
            0 -> followerFragment
            else -> followingFragment
        }
    }

    fun setMyUserItemStatus(my_status : String){
        this.my_status = my_status
    }

    // 팔로워, 팔로잉 리스트 대로 userDatas 재설정 함수
    fun setFollowerList(list : ArrayList<String>){
        this.follower_list = list
    }
    fun setFollowingList(list : ArrayList<String>){
        this.following_list = list
    }
}