package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityReplyBinding


class CommentActivity : AppCompatActivity()
{
    lateinit var binding: ActivityReplyBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityReplyBinding.inflate(layoutInflater)
        //프래그먼트 교체
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CommentListFragment())
            .addToBackStack(null)
            .commit()

        setContentView(binding.root)


    }


}