package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.janob.tape_aos.databinding.ActivityAlbumBinding
import java.lang.Math.abs

class AlbumActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val albumId = intent.getIntExtra("albumId", 2)
        Log.d("position", albumId.toString())

        initAlbumFragment(albumId)

    }


    private fun initAlbumFragment(albumId : Int){
        var albumFragment = AlbumFragment()
        var bundle = Bundle()
        bundle.putInt("albumId",albumId)
        albumFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.album_fm, albumFragment)
            .commitAllowingStateLoss()
    }
}