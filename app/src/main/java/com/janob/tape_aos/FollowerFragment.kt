package com.janob.tape_aos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentFollowerBinding

class FollowerFragment() : Fragment() {
    lateinit var binding : FragmentFollowerBinding
    lateinit var userDatas : List<User>
    lateinit var tapeDatas : List<Tape>

    lateinit var followRVAdapter : FollowRVAdapter

    private var my_status : String = ""

    // 팔로워 리스트 대로 userDatas 재설정 변수
    private var follower_list_status : String = ""
    private var follower_list = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        // RoomDB 데이터 받기
        tapeDatas = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // 팔로워 리스트 대로 userDatas 재설정
        if(follower_list_status != null && follower_list_status == "set_follower_list"){
            var change_userDatas = ArrayList<User>()
            for(i in 0..follower_list.size - 1){
                var user = TapeDatabase.Instance(context as MainActivity).userDao().getUserByName(follower_list[i])
                change_userDatas.add(user)
            }
            userDatas = change_userDatas
        }

        // adapter 변수 선언
        Log.d("eunseo", "FollowerFragment - onCreateView - userDatas 전 확인!!")
        followRVAdapter = FollowRVAdapter(userDatas)
        Log.d("eunseo", "FollowerFragment - onCreateView - userDats 후 확인!!")

        // 팔로워 리사이클러뷰에 추가 : -> SearchRVAdapter로 status 전달
        if(my_status != null && my_status == "add_item") {
            val my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
            followRVAdapter.setAddItemByUser(my_user)

            // for 정보 유지
            /*
            val temp_userDatas = ArrayList(userDatas)
            temp_userDatas.add(my_user)
            userDatas = temp_userDatas.toList()
            */

            my_status = ""
        }
        else if(my_status != null && my_status == "delete_item") {
            val my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
            followRVAdapter.setRemoveItemByUser(my_user)

            // for 정보 유지
            /*
            val temp_userDatas = ArrayList(userDatas)
            temp_userDatas.remove(my_user)
            userDatas = temp_userDatas.toList()
            */

            my_status = ""
        }

        // ** Recycler Adapter : search_user_rv **
        binding.followerRv.adapter = followRVAdapter
        binding.followerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        followRVAdapter.setMyItemClickListener(object : FollowRVAdapter.MyItemClickListener{
            override fun onItemClick(user : User) {
                // 클릭시 타인 개인 프로필 페이지 프래그먼트로 전환 + 데이터 전달(gson)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fm, OtherprofileFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val userJson = gson.toJson(user)
                            putString("user", userJson)
                        }
                    })
                    .commitAllowingStateLoss()

            }

        })

        return binding.root
    }

    fun setMyUserItemStatus(my_status : String){
        this.my_status = my_status
    }

    // 팔로워 리스트 대로 userDatas 재설정 함수
    fun setFollowerListStatus(s : String, follower_list: ArrayList<String>){
        follower_list_status = s
        this.follower_list = follower_list
    }
}