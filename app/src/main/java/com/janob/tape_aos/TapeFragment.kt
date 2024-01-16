package com.janob.tape_aos

import android.content.Intent
import android.opengl.Visibility
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
        

        //roomDB에서 데이터 가져오기
        val tapeAlbumData = TapeDatabase.Instance(context as MainActivity).albumDao().getAll()
        //리사이클러뷰 어댑터
        val tapeAlbumRVAdapter = TapeAlbumRVAdapter(tapeAlbumData, requireContext())
        binding.tapeTapelistRv.adapter=tapeAlbumRVAdapter
        binding.tapeTapelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tapeAlbumRVAdapter.setMyItemClickLitner(object: TapeAlbumRVAdapter.MyItemClickListner {
            override fun onItemClick(album: TapeAlbum) {
                changeAlbumActivity(album)
            }
        })

        binding.button.setOnClickListener { buttonClick() }


        return binding.root
    }

    private fun changeAlbumActivity(album: TapeAlbum){
        val intent = Intent(activity,AlbumActivity::class.java)
        intent.apply {
            this.putExtra("albumId",album.id) // 데이터 넣기
        }
        Log.d("position1", album.id.toString()) //album.id
        startActivity(intent)
    }


    //게시물이 있을 때, 없을 때를 임의로 확인하기 위한 버튼
    private fun buttonClick(){
        if(binding.tapeTapeLayout.visibility == View.VISIBLE){
            binding.tapeTapeLayout.visibility = View.GONE
            binding.tapeTapelistRv.visibility = View.GONE

            binding.tapeMaketapePlusLayout.visibility = View.VISIBLE
            binding.tapeTapelistZero.visibility = View.VISIBLE
        }else{
            binding.tapeTapeLayout.visibility = View.VISIBLE
            binding.tapeTapelistRv.visibility = View.VISIBLE

            binding.tapeMaketapePlusLayout.visibility = View.GONE
            binding.tapeTapelistZero.visibility = View.GONE
        }
    }

}