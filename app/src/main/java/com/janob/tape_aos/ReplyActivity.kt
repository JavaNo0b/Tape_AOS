package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ActivityReplyBinding


class ReplyActivity : AppCompatActivity()
   {
    lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //댓글 수정
        var reply = intent.getParcelableExtra("reply", Reply::class.java)
        if(reply != null){
            recyclerView.adapter?.notifyItemChanged(reply.idx)
        }

        val binding = ActivityReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //roomDB에서 데이터 가져오기
        var tapeReplyData = TapeDatabase.Instance(this).replyDao().getAll()

        //리사이클러뷰에 데이터 연결
        val manager = LinearLayoutManager(this)
        val adapter = ReplyAdapter(tapeReplyData,this)

        recyclerView = binding.replyRecyclerView
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter




        setContentView(binding.root)

    }


   }
