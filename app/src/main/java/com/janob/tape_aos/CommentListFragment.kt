package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
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
}
    class CommentItemViewHolder(val binding:ItemCommentBinding,val context: Context):RecyclerView.ViewHolder(binding.root){

        lateinit var data :CommentDTO


        fun bind(comment:CommentDTO){

            this.data = comment

            //binding.userProfileIv
            binding.userProfileTv.text = data.userId.toString()
            binding.replyTextTv.text = data.content
            // 클릭이벤트
            itemView.setOnClickListener {
                val popup = PopupMenu(context, binding.root)
                popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    Log.d("menu item click", "${item.title}") // 댓글 수정 + 댓글 삭제
                    true
                }
                popup.show()//보여주기
            }


        }


        }

    class CommentAdapter(var dataList: List<CommentDTO>):RecyclerView.Adapter<CommentItemViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
            val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            val context = parent.context
            return CommentItemViewHolder(binding,context)
        }

        override fun getItemCount(): Int { return dataList.size }

        override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
            holder
            holder.bind(dataList[position])
        }

    }

