package com.janob.tape_aos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.FragmentReplyListBinding
import com.janob.tape_aos.databinding.ItemCommentBinding

class CommentListFragment: Fragment() {

    lateinit var binding: FragmentReplyListBinding
    lateinit var recyclerView: RecyclerView

    lateinit var c: Context
    //뷰모델
    private val commentListViewModel: CommentListViewModel by lazy {
        ViewModelProvider(this).get(CommentListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReplyListBinding.inflate(inflater, container, false)
        recyclerView = binding.replyRecyclerView
        recyclerView.adapter = CommentAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentListViewModel.commentListLiveData.observe(
            viewLifecycleOwner,
            Observer { commentDTOList ->
                Log.d("reply list fragment", "now comment list $commentDTOList")
                recyclerView.adapter = CommentAdapter(commentDTOList)
            }
        )
    }
    fun popupMenu(v : View){
        //메뉴 리스트
        val menu = PopupMenu(c, v)
        menu.inflate(R.menu.menu_popup)
        menu.setOnMenuItemClickListener { item ->

            when(item.itemId){

                R.id.comment_rewrite ->{
                    val v = LayoutInflater.from(c).inflate(R.layout.add_comment,null)
                    val commentTv = v.findViewById<EditText>(R.id.edit_comment)

                            AlertDialog.Builder(c)
                                .setView(v)
                                .setPositiveButton("확인"){
                                        dialog, _ ->
                                    //댓글 수정
                                    commentListViewModel.apiFetchr.updateComment(commentTv.text.toString())
                                    Toast.makeText(c,"댓글 수정 ",Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()

                                }
                                .setNegativeButton("취소"){
                                    dialog,_ ->
                                    Toast.makeText(c,"취소",Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()

                                }
                                .create()
                                .show()
                    true
                }
                R.id.comment_remove->{
                    //
                    AlertDialog.Builder(c)
                        .setTitle("댓글 삭제")
                        .setMessage("정말로 댓글을 삭제하시겠습니까?")
                        .setPositiveButton("예"){
                            dialog,_ ->
                            //임의로 댓글 삭제
                            commentListViewModel.apiFetchr.deleteComment(1,1)
                            Toast.makeText(c,"댓글 삭제 ",Toast.LENGTH_SHORT).show()
                            dialog.dismiss()


                        }
                        .setNegativeButton("아니오"){
                            dialog,_ ->
                            //취소
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                    true


                }
                else -> true
            }
            true
        }
        menu.show()//보여주기
    }
    inner class CommentItemViewHolder(val binding:ItemCommentBinding,val context: Context):RecyclerView.ViewHolder(binding.root){

        lateinit var data :CommentDTO


        fun bind(comment:CommentDTO){

            this.data = comment

            //binding.userProfileIv = data.userProfileImg
            //binding.userProfileTv.text = data.userName
            binding.replyTextTv.text = data.content
            binding.createdAtTv.text = data.createdAt
            // 클릭이벤트
            itemView.setOnClickListener {
                popupMenu(it)
            }


        }


    }


    inner class CommentAdapter(var dataList: List<CommentDTO>):RecyclerView.Adapter<CommentItemViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
            val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            c = parent.context
            return CommentItemViewHolder(binding,c)
        }

        override fun getItemCount(): Int { return dataList.size }

        override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
            holder
            holder.bind(dataList[position])
        }

    }

}


