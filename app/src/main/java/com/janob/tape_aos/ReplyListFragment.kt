package com.janob.tape_aos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentReplyListBinding

class ReplyListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReplyListBinding.inflate(layoutInflater)

        //더미데이터 가져오기
        val pref = (context as MainActivity).getSharedPreferences("reply", AppCompatActivity.MODE_PRIVATE)
        val replies = Reply.getReplyFromPreferences(pref)
        //리사이클러뷰에 데이터 연결
        val manager = LinearLayoutManager(context)
        val adapter = ReplyAdapter(replies)

        val recyclerView = binding.replyRecyclerView
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        //todo : 빈공간 누르면 키보드 hide
        binding.editTextEt.setOnFocusChangeListener{view , hasFocus ->
            Toast.makeText(context, "포커스 변경 $hasFocus", Toast.LENGTH_SHORT).show()
        }
       binding.editTextBtn.setOnClickListener {
           Reply.saveReplyFromPreferences(pref,0,"새로운 댓글을 등록하였습니다 ")
       }
        binding.replyBackIv.setOnClickListener { startActivity(Intent(requireContext(), AlbumActivity::class.java)) }

        return binding.root
    }
}