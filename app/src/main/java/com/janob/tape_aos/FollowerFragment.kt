package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    lateinit var binding : FragmentFollowerBinding
    private var userDatas = ArrayList<User>()
    private var tapeDatas = ArrayList<Tape>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)


        tapeDatas.apply {
            add(Tape("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("Broken Melodies", "NCT DREAM", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("Thirsty", "aepsa", "K_pop_lover", R.drawable.albumcover_5, R.drawable.albumcover_5))
            add(Tape("와르르", "Colde", "music_play", R.drawable.albumcover_5, R.drawable.albumcover_5))
        }



        var followerList : ArrayList<String> = arrayListOf("follower1", "follower2", "follower3", "follower4", "follower5", "follower6", "follower7", "follower8", "follower9")
        var followingList : ArrayList<String> = arrayListOf("following1", "following2", "following3", "following4", "following5", "following6", "following7", "following8", "following9")
        var tapeList = tapeDatas
        userDatas.apply{
            add(User(R.drawable.albumcover_5, "music_play", "잡다한 음악 다 좋아해요♥", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "music_song", "노래 좋아합니다", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user1", "I love music", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user2", "음악 추천 부탁해요", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user3", "I love music", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user4", "음악 추천 부탁해요", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user5", "I love music", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user6", "음악 추천 부탁해요", followerList, followingList, tapeDatas))
            add(User(R.drawable.albumcover_5, "user7", "음악 추천 부탁해요", followerList, followingList, tapeDatas))
        }

        // adapter 변수 선언
        val searchRVAapter = SearchRVAdapter(userDatas)

        // ** Recycler Adapter : search_user_rv **
        binding.followerRv.adapter = searchRVAapter
        binding.followerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        searchRVAapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener{
            override fun onItemClick(user : User) {
                // 클릭시 타인 개인 프로필 페이지 프래그먼트로 전환 + 데이터 전달(gson)
                /*
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fm, OtherprofileFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val userJson = gson.toJson(user)
                            putString("user", userJson)
                        }
                    })
                    .commitAllowingStateLoss()
                 */

            }

        })

        return binding.root
    }
}