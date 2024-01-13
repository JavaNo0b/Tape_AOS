package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentAlbumBinding
import java.lang.Math.abs

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    lateinit var includedSongsData: List<IncludedSong>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater)

        setDummyIncludedSong()

        val includedSongRVAdapter = IncludedSongRVAdapter(includedSongsData)
        binding.albumIncludedsongsVp.adapter = includedSongRVAdapter
//        binding.albumIncludedsongsVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 관리하는 페이지 수. default = 1
        binding.albumIncludedsongsVp.offscreenPageLimit = 4
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        binding.albumIncludedsongsVp.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }
        return binding.root
    }

    fun setDummyIncludedSong(){
        val albumId = arguments?.getInt("albumId")
        val songDB = TapeDatabase.Instance(requireContext())
        includedSongsData = songDB.songDao().getSongsInAlbum(albumId!!)
    }
}