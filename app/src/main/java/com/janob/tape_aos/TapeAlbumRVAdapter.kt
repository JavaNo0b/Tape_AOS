package com.janob.tape_aos

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.CustomTapePopupMenuBinding
import com.janob.tape_aos.databinding.ItemTapeBinding



class TapeAlbumRVAdapter(private val TapeAlbumList : ArrayList<TapeAlbum>, private val context: Context) : RecyclerView.Adapter<TapeAlbumRVAdapter.ViewHolder>(){



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TapeAlbumRVAdapter.ViewHolder {
        val binding : ItemTapeBinding = ItemTapeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TapeAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bindTapeAlbum(TapeAlbumList[position])

    }


    override fun getItemCount(): Int = TapeAlbumList.size


    inner class ViewHolder(val binding : ItemTapeBinding) : RecyclerView.ViewHolder(binding.root){

        //itemTapeTapemoreIv 클릭 시 menu
        init {
            binding.itemTapeTapemoreIv.setOnClickListener {
                showPopup(it, TapeAlbum())
            }
        }


        //menu popup
        private fun showPopup(view: View, tapealbum : TapeAlbum) {
            val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.custom_tape_popup_menu, null)

            val width = 163.dpToPx(context)
            val height = 61.dpToPx(context)

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