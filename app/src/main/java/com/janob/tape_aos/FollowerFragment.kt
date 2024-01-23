package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    lateinit var binding : FragmentFollowerBinding
    lateinit var userDatas : List<User>
    lateinit var tapeDatas : List<Tape>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        // RoomDB 데이터 받기
        tapeDatas = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // adapter 변수 선언
        val searchRVAapter = SearchRVAdapter(userDatas)

        // ** Recycler Adapter : search_user_rv **
        binding.followerRv.adapter = searchRVAapter
        binding.followerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        searchRVAapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener{
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
}