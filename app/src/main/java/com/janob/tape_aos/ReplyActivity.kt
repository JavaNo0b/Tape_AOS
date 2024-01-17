package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ActivityReplyBinding


class ReplyActivity : AppCompatActivity()
{
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var recyclerView : RecyclerView
    lateinit var binding: ActivityReplyBinding
    lateinit var tapeReplyData: List<Reply>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//
//        //댓글 수정
//        var reply = intent.getParcelableExtra("reply", Reply::class.java)
//        if(reply != null){
//            recyclerView.adapter?.notifyItemChanged(reply.idx)
//        }

        binding = ActivityReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //roomDB에서 데이터 가져오기
        tapeReplyData = TapeDatabase.Instance(this).replyDao().getAll()

        //리사이클러뷰에 데이터 연결
        val manager = LinearLayoutManager(this)
        val adapter = ReplyAdapter(tapeReplyData,this)

        adapter.setMyItemClickLitner(object: ReplyAdapter.MyItemClickListner {
            override fun onEditClick(reply: Reply) {
                val intent = Intent(this@ReplyActivity,ReplyModifyActivity::class.java)
                intent.putExtra("reply",reply)
                resultLauncher.launch(intent)
            }
        })

        setResultNext()

        recyclerView = binding.replyRecyclerView
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        setContentView(binding.root)

    }
    private fun setResultNext(){
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            // 서브 액티비티로부터 돌아올 때의 결과 값을 받아 올 수 있는 구문
            if (result.resultCode == RESULT_OK){

                val replyResult = result.data?.getParcelableExtra("reply", Reply::class.java)!!
                replyResult.let {
                    val updatedIdx = it.idx
                    val updatedText = it.text

                    // Room 데이터베이스 업데이트
                    TapeDatabase.Instance(this).replyDao().updateReply(updatedText, updatedIdx.toLong())

                    // 리사이클러뷰 업데이트
                    tapeReplyData.find { reply -> reply.idx == updatedIdx }?.apply {
                        text = updatedText
                        recyclerView.adapter?.notifyItemChanged(updatedIdx)
                    }
                }
            }
        }
    }

}