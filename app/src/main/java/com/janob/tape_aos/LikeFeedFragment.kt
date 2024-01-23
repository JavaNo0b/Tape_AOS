package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentLikeFeedBinding

class LikeFeedFragment : Fragment() {

    lateinit var binding : FragmentLikeFeedBinding
    lateinit var songDatas : List<Song>

    private var deleteCount : Int = 0
    lateinit var like_song_list : ArrayList<Song>
    private var delete_song_list = ArrayList<Song>()

    /* 편집 status -> adapter로 넘기기 위한 인터페이스
    interface EditClickListener{
        fun onClick(status : Boolean)
    }
    private lateinit var eClickListener : EditClickListener
    fun setEditClickListener(clickListener : EditClickListener){
        eClickListener = clickListener
    } */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikeFeedBinding.inflate(inflater, container, false)

        // RoomDB 데이터 받기
        songDatas = TapeDatabase.Instance(context as MainActivity).songDao().getAllList()
        like_song_list = ArrayList(TapeDatabase.Instance(context as MainActivity).songDao().getAllList())

        // adapter 변수 선언
        val likefeedRVAapter = LikeFeedRVAdapter(songDatas, false, false)

        // ** Recycler Adapter : like_feed_rv **
        binding.likeFeedRv.adapter = likefeedRVAapter
        binding.likeFeedRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // ** delete **
        /* 삭제 중
        likefeedRVAapter.setMyItemClickListener(object : LikeFeedRVAdapter.MyItemClickListener{
            override fun onClick(song: Song, isChecked: Boolean, button: CompoundButton) {

                if(isChecked){ // 클릭시 삭제 리스트에 추가 + 좋아요 리스트에서 삭제 + 삭제count++
                    delete_song_list.add(song)
                    like_song_list.remove(song)
                    deleteCount++
                }
                else{ // 클릭 해제시 삭제 리스트에서 삭제 + 좋아요 리스트에 추가 + 삭제 count--
                    delete_song_list.remove(song)
                    like_song_list.add(song)
                    deleteCount--
                }



            }
        })*/

        // 삭제 클릭 리스너
        binding.likeFeedDeleteButtonBtn.setOnClickListener {
            likefeedRVAapter.setDelete()
        }

        // 편집 toggleButton 설정
        binding.likeFeedEditToggleTb.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // 전체선택 toggle 나타나게 하기
                binding.likeFeedAllSelectToggleTb.visibility = ToggleButton.VISIBLE
                binding.likeFeedAllSelectTv.visibility = TextView.VISIBLE

                // 삭제 버튼 나타나게 하기
                binding.likeFeedDeleteButtonBtn.visibility = Button.VISIBLE

                //
                likefeedRVAapter.setEditStatus(true)


            } else{
                // 전체선택 toggle 사라지게 하기
                binding.likeFeedAllSelectToggleTb.visibility = ToggleButton.INVISIBLE
                binding.likeFeedAllSelectTv.visibility = TextView.INVISIBLE

                // 삭제 버튼 사라지게 하기
                binding.likeFeedDeleteButtonBtn.visibility = Button.GONE

                //
                likefeedRVAapter.setEditStatus(false)
            }

            /* 인터페이스
            eClickListener.onClick(b) */
        }

        // 전체선택 toggleButton 설정
        binding.likeFeedAllSelectToggleTb.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // 전체선택 클릭!
                binding.likeFeedAllSelectToggleTb.setBackgroundResource(R.drawable.like_song_clicked_btn)

                //
                likefeedRVAapter.setAllStatus(true)

            } else{
                // 전체선택 취소ㅠㅠ
                binding.likeFeedAllSelectToggleTb.setBackgroundResource(R.drawable.like_song_unclicked_btn)

                //
                likefeedRVAapter.setAllStatus(false)
            }
        }

        return binding.root
    }
}