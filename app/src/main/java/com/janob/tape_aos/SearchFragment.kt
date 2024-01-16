package com.janob.tape_aos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private var userDatas = ArrayList<User>()
    private var tapeDatas = ArrayList<TapeRealtime>()

    // search, realtime의 adapter 변수
    private var search_list = ArrayList<User>()
    private var original_list = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)


        userDatas.apply{
            add(User(R.drawable.albumcover_5, "music_play", "잡다한 음악 다 좋아해요♥", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "music_song", "노래 좋아합니다", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user1", "I love music", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user2", "음악 추천 부탁해요", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user3", "I love music", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user4", "음악 추천 부탁해요", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user5", "I love music", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user6", "음악 추천 부탁해요", R.drawable.albumcover_5))
            add(User(R.drawable.albumcover_5, "user7", "음악 추천 부탁해요", R.drawable.albumcover_5))
        }

        tapeDatas.apply {
            add(TapeRealtime("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(TapeRealtime("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
        }

        // adapter 변수 선언
        val searchRVAapter = SearchRVAdapter(userDatas)
        val realtimeRVAdapter = RealtimeRVAdapter(tapeDatas)

        // ** search **
        original_list = userDatas

        // editText 리스너 작성
        val editText = binding.searchEdittextEt

        editText.setOnFocusChangeListener(object : OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus){
                    binding.searchFrameUserFl.visibility = FrameLayout.VISIBLE
                    binding.searchFrameRealtimeFl.visibility = FrameLayout.GONE
                }
            }

        })

        editText.addTextChangedListener(object : TextWatcher{
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

        // ** Recycler Adapter : search_user_rv **
        binding.searchUserRv.adapter = searchRVAapter
        binding.searchUserRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        searchRVAapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                // 데이터 전달
                //
                //

                // 클릭시 타인 개인 프로필 페이지 프래그먼트로 전환
                (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, OtherprofileFragment())
                .commitAllowingStateLoss()

            }

        })

        // ** Recycler Adapter : search_realtime_rv **
        binding.searchRealtimeRv.adapter = realtimeRVAdapter
        binding.searchRealtimeRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        realtimeRVAdapter.setMyItemClickListener(object : RealtimeRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                // 클릭시 tape 페이지로 전환되는 fragment
                /*
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fm, 전환될프래그먼트())
                    .commitAllowingStateLoss()
                */
            }

        })


        return binding.root
    }
}
