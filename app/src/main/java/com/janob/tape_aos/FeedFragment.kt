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
    private var userDatas = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

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