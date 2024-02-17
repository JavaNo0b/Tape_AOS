package com.janob.tape_aos

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.janob.tape_aos.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

private const val TAG1 = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isFabOpen = false





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //바텀 네비게이션
        initBottomNavigation()

    }


    private fun initBottomNavigation() {
        val selectedColor = ContextCompat.getColor(this, R.color.navi_selected)
        val unSelectedColor = ContextCompat.getColor(this, R.color.navi_unselected)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, TapeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomTapeIb.setColorFilter(selectedColor)
        binding.mainBottomTapeTv.setTextColor(selectedColor)

        binding.mainBottomTapeLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, TapeFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomTapeIb, binding.mainBottomTapeTv)
        }
        binding.mainBottomNotifLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, NotifFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomNotifIb, binding.mainBottomNotifTv)
        }
        binding.mainBottomPostLayout.setOnClickListener{
            toggleFab()
        }
        binding.mainBottomSearchLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, SearchFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
            setNavigationColor(binding.mainBottomSearchIb, binding.mainBottomSearchTv)
        }
        binding.mainBottomProfileLayout.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, ProfileFragment())
                .commitAllowingStateLoss()

            setNavigationColorNone()
        }
    }
    private fun setNavigationColor(icon: ImageView, text: TextView){
        val selectedColor = ContextCompat.getColor(this, R.color.navi_selected)
        icon.setColorFilter(selectedColor)
        text.setTextColor(selectedColor)
    }
    private fun setNavigationColorNone(){
        val unSelectedColor = ContextCompat.getColor(this, R.color.navi_unselected)

        binding.mainBottomTapeIb.setColorFilter(unSelectedColor)
        binding.mainBottomTapeTv.setTextColor(unSelectedColor)

        binding.mainBottomNotifIb.setColorFilter(unSelectedColor)
        binding.mainBottomNotifTv.setTextColor(unSelectedColor)

        binding.mainBottomSearchIb.setColorFilter(unSelectedColor)
        binding.mainBottomSearchTv.setTextColor(unSelectedColor)
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.mainBottomPostTapeBtn, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostPostBtn, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostBtn, View.ROTATION, 45f, 0f).apply { start() }
            binding.mainBottomPostBtn.backgroundTintList = getColorStateList(R.color.navi_unselected)

        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션

            ObjectAnimator.ofFloat(binding.mainBottomPostTapeBtn, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostPostBtn, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostBtn, View.ROTATION, 0f, 45f).apply { start() }
            binding.mainBottomPostBtn.backgroundTintList = getColorStateList(R.color.navi_selected)
        }

        fabOnClick()

        isFabOpen = !isFabOpen

    }

    private fun fabOnClick(){
        //테이프 등록 페이지로 이동
        binding.mainBottomPostTapeBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, PostFragment())
                .commitAllowingStateLoss()
        }
        binding.mainBottomPostPostBtn.setOnClickListener {
            //게시물 등록 페이지로 이동
            supportFragmentManager.beginTransaction()
                //.replace(R.id.main_fm, ProfilePostFragment())
                //.commitAllowingStateLoss()
        }
    }
}