package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
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




        binding.tapeTapeimage.setOnClickListener{
            startActivity(Intent(it.context, AlbumActivity::class.java))
        }
        

        //db 데이터 가져오기
        var tapeAlbumData = TapeDatabase.Instance(context as MainActivity).tapeDao().getAll()
        //리사이클러뷰 어댑터
        val tapeAlbumRVAdapter = TapeAlbumRVAdapter(tapeAlbumData, requireContext())
        binding.tapeTapelistRv.adapter=tapeAlbumRVAdapter
        binding.tapeTapelistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}