package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.ActivityFollowBinding

class FollowActivity : AppCompatActivity() {

    lateinit var binding : ActivityFollowBinding
    private val information = arrayListOf("팔로워", "팔로잉")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tabLayout과 viewPager2 연결
        val followAdpater = FollowVPAdapter(this)
        binding.followerContentVp.adapter = followAdpater
        TabLayoutMediator(binding.followerContentTb, binding.followerContentVp){
                tab, position -> tab.text = information[position]
        }.attach()

        // tabLayout 초기 포커스 설정
        val status = intent.getStringExtra("status")
        if(status == "following"){
            binding.followerContentTb.selectTab(binding.followerContentTb.getTabAt(1))
        }

        // 뒤로가기
        binding.followerBackIv.setOnClickListener {
            finish()
        }
    }
}