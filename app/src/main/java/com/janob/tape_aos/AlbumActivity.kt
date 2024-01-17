package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.janob.tape_aos.databinding.ActivityAlbumBinding
import java.lang.Math.abs

class AlbumActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlbumBinding
    lateinit var includedSongs: ArrayList<IncludedSong>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        includedSongs = ArrayList()
        setDummyIncludedSong()

        //댓글 액티비티로 이동
        binding.albumCommentBtn.setOnClickListener {

            Toast.makeText(this,"댓글 액티비티으로 이동 ",Toast.LENGTH_SHORT).show()

            var intent = Intent(this , ReplyActivity::class.java)
            startActivity(intent)

        }

        val includedSongRVAdapter = IncludedSongRVAdapter(includedSongs)

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
    }

    fun setDummyIncludedSong(){
        includedSongs.apply {
            add(IncludedSong(1, R.drawable.album_2, "Let It Be", "The Beatles"))
            add(IncludedSong(1, R.drawable.album_3, "민들레", "우효"))
            add(IncludedSong(1, R.drawable.album_2, "All You Need Is Love", "The Beatles"))
            add(IncludedSong(1, R.drawable.album_3, "A Good Day", "우효"))
        }
    }
}