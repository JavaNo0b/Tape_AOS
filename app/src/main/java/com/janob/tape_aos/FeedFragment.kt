package com.janob.tape_aos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    lateinit var binding : FragmentFeedBinding
    lateinit var userDatas : List<User>
    lateinit var tapeDatas : List<Tape>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        /*
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
        */

        tapeDatas = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()

        // Recycler Adapter : feed_content_rv
        var feedRVAdapter = FeedRVAdapter(userDatas)
        binding.feedContentRv.adapter = feedRVAdapter
        binding.feedContentRv.layoutManager = GridLayoutManager(context, 3)

        // GridLayout 간격 맞추기
        binding.feedContentRv.run {
            adapter = FeedRVAdapter(userDatas)
            addItemDecoration(GridSpacingItemDecoration(3, 20))
        }


        return binding.root
    }

    // GridLayout 간격 맞추는 class
    internal class GridSpacingItemDecoration(private val spanCount : Int, private val spacing : Int) : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position : Int = parent.getChildLayoutPosition(view)
            val column = position % spanCount + 1

            if(position < spanCount){
                outRect.top = spacing
            }
            if(column == spanCount){
                outRect.right = spacing
            }
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing
            outRect.bottom = spacing
        }
    }
}