package com.janob.tape_aos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    lateinit var binding : FragmentFollowBinding
    private val information = arrayListOf("팔로워", "팔로잉")

    lateinit var userDatas : List<User>

    private var search_list = ArrayList<User>()
    private var original_list = ArrayList<User>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        // RoomDB 데이터 받기
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // tabLayout과 viewPager2 연결
        val followAdpater = FollowVPAdapter(this)
        binding.followContentVp.adapter = followAdpater
        TabLayoutMediator(binding.followContentTb, binding.followContentVp){
                tab, position -> tab.text = information[position]
        }.attach()

        // tabLayout 초기 포커스 설정 : OtherprofileFragment에서 팔로워, 팔로우 status 데이터 받아오기
        val status = arguments?.getString("status")
        Log.d("mystatus", status!!)
        if(status == "following"){
            binding.followContentTb.selectTab(binding.followContentTb.getTabAt(1))
        }

        // 뒤로가기
        binding.followBackIv.setOnClickListener {
            // 나중에 구현
        }

        //
        //
        // ** search **
        //
        //
        // adapter 변수 선언
        val searchRVAapter = SearchRVAdapter(userDatas)

        original_list = ArrayList(userDatas)

        // editText 리스너 작성
        val editText = binding.followEdittextEt

        //
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // X
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // X
            }

            override fun afterTextChanged(s: Editable?) {
                var searchText : String = editText.text.toString()
                search_list.clear()

                if(searchText == ""){
                    searchRVAapter.setItems(original_list)
                }
                else{
                    for(i in 0..original_list.size - 1){
                        if(original_list[i].name!!.toLowerCase().contains(searchText.toLowerCase())){
                            search_list.add(original_list[i])
                        }
                        searchRVAapter.setItems(search_list)
                    }
                }
            }

        })

        return binding.root
    }
}