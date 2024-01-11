package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        //roomDB에서 데이터 가져오기
        var tapeReplyData = TapeDatabase.Instance(context as MainActivity).replyDao().getAll()

        //리사이클러뷰에 데이터 연결
        val manager = LinearLayoutManager(context)
        val adapter = ReplyAdapter(tapeReplyData)

        val recyclerView = binding.replyRecyclerView
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        //todo : 빈공간 누르면 키보드 hide
        binding.editTextEt.setOnFocusChangeListener{view , hasFocus ->
            Toast.makeText(context, "포커스 변경 $hasFocus", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }
}