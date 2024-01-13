package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentTapeBinding

class TapeFragment : Fragment() {



    lateinit var binding: FragmentTapeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTapeBinding.inflate(layoutInflater)


        //일단 텍스트 클릭하면 댓글로 이동함
        binding.tapeTodaytape.setOnClickListener{
            val transaction = (context as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fm, ReplyListFragment())
            transaction.commit()
        }
        

        //roomDB에서 데이터 가져오기
        val tapeAlbumData = TapeDatabase.Instance(context as MainActivity).albumDao().getAll()
        //리사이클러뷰 어댑터
        val tapeAlbumRVAdapter = TapeAlbumRVAdapter(tapeAlbumData, requireContext())
        binding.tapeTapelistRv.adapter=tapeAlbumRVAdapter
        binding.tapeTapelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tapeAlbumRVAdapter.setMyItemClickLitner(object: TapeAlbumRVAdapter.MyItemClickListner {
            override fun onItemClick(album: TapeAlbum) {
                changeAlbumFragment(album)
            }
        })


        return binding.root
    }

    private fun changeAlbumFragment(album: TapeAlbum){
        val intent = Intent(activity,AlbumActivity::class.java)
        intent.apply {
            this.putExtra("albumId",album.id) // 데이터 넣기
        }
        Log.d("position", album.id.toString())
        startActivity(intent)
    }

}