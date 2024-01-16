package com.janob.tape_aos

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.CustomReplyPopupMenuBinding
import com.janob.tape_aos.databinding.ItemTapeBinding


class TapeAlbumRVAdapter(private val TapeAlbumList : List<TapeAlbum>, private val context: Context) : RecyclerView.Adapter<TapeAlbumRVAdapter.ViewHolder>(){

    interface MyItemClickListner{ //item clicklistner를 저장하기 위한 인터페이스
        fun onItemClick(album: TapeAlbum)
    }

    private lateinit var mItemClickListner: MyItemClickListner //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickLitner(itemClickListner: MyItemClickListner) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TapeAlbumRVAdapter.ViewHolder {
        val binding : ItemTapeBinding = ItemTapeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TapeAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bindTapeAlbum(TapeAlbumList[position])
        holder.binding.itemTape.setOnClickListener {
            mItemClickListner.onItemClick(TapeAlbumList[position])
        }
        //확인한 아이템에 흐릿해짐 효과를 임의로 주기 위한 버튼
        holder.binding.button.setOnClickListener {

            if(holder.binding.itemTape.alpha == 0.5F){
                holder.binding.itemTape.alpha = 1F
            }else{
                holder.binding.itemTape.alpha = 0.5F
            }
        }
    }


    override fun getItemCount(): Int = TapeAlbumList.size


    inner class ViewHolder(val binding : ItemTapeBinding) : RecyclerView.ViewHolder(binding.root){

        //itemTapeTapemoreIv 클릭 시 menu
        init {
            binding.itemTapeTapemoreIv.setOnClickListener {
                showPopup(it)
            }
        }


        //menu popup
        private fun showPopup(view: View) {
            val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.custom_tape_popup_menu, null)

            val width = 163.dpToPx(context)
            val height = 30.dpToPx(context)

            val popupWindow = PopupWindow(popupView, width, height, true)
            popupWindow.showAsDropDown(view, 0, 0)

        }


        fun bindTapeAlbum(tapealbum : TapeAlbum){

            binding.itemTapeTapetitleTv.text = tapealbum.tapeTitle
            binding.itemTapeSingerTv.text = tapealbum.singer
            binding.itemTapeUsernameTv.text = tapealbum.userName
            binding.itemTapeAlbumcoverImgIv.setImageResource(tapealbum.albumCover!!)
            binding.itemTapeUserimageIv.setImageResource(tapealbum.userImage!!)
        }


        //menu popup 사이즈 변경
        fun Int.dpToPx(context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

    }

}