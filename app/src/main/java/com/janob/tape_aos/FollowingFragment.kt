package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    lateinit var binding : FragmentFollowingBinding
    lateinit var userDatas : List<User>
    lateinit var tapeDatas : List<Tape>

    lateinit var followRVAdapter : FollowRVAdapter

    // 팔로잉 리스트 대로 userDatas 재설정 변수
    private var following_list_status : String = ""
    private var following_list = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        // RoomDB 데이터 받기(activity = FollowActivity, frag = MainAcitivity)
        tapeDatas = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // 팔로잉 리스트 대로 userDatas 재설정
        if(following_list_status != null && following_list_status == "set_following_list"){
            var change_userDatas = ArrayList<User>()
            for(i in 0..following_list.size - 1){
                var user = TapeDatabase.Instance(context as MainActivity).userDao().getUserByName(following_list[i])
                change_userDatas.add(user)
            }
            userDatas = change_userDatas
        }

        // adapter 변수 선언
        followRVAdapter = FollowRVAdapter(userDatas)

        // ** Recycler Adapter : search_user_rv **
        binding.followingRv.adapter = followRVAdapter
        binding.followingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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

    // 팔로잉 리스트 대로 userDatas 재설정 함수
    fun setFollowingListStatus(s : String, list: ArrayList<String>){
        following_list_status = s
        this.following_list = list
    }
}