package com.janob.tape_aos

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
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

        binding.root.setOnClickListener {
            if (isFabOpen) {
                toggleFab()
            }
        }

    }


    private fun initBottomNavigation() {
        val selectedColor = ContextCompat.getColor(this, R.color.navi_selected)



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
        if (isFabOpen) {
            // 플로팅 액션 버튼이 열려 있는 경우 닫기 애니메이션
            Handler().postDelayed({
                binding.mainBottomPostTapeBtn.visibility = View.GONE
                binding.mainBottomPostPostBtn.visibility = View.GONE
            }, 500)
            ObjectAnimator.ofFloat(binding.mainBottomPostTapeBtn, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostPostBtn, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostBtn, View.ROTATION, 45f, 0f).apply { start() }
            binding.mainBottomPostBtn.backgroundTintList = getColorStateList(R.color.navi_unselected)
        } else {
            // 플로팅 액션 버튼이 닫혀 있는 경우 열기 애니메이션
            binding.mainBottomPostTapeBtn.visibility = View.VISIBLE
            binding.mainBottomPostPostBtn.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.mainBottomPostTapeBtn, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostPostBtn, "translationY", -400f).apply { start() }
            ObjectAnimator.ofFloat(binding.mainBottomPostBtn, View.ROTATION, 0f, 45f).apply { start() }
            binding.mainBottomPostBtn.backgroundTintList = getColorStateList(R.color.navi_selected)

        }


        isFabOpen = !isFabOpen

        fabOnClick()
    }

    private fun fabOnClick(){
        //테이프 등록 페이지로 이동
        binding.mainBottomPostTapeBtn.setOnClickListener {
            if (isFabOpen) {
                toggleFab()
            }
            setNavigationColorNone()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, PostFragment())
                .commitAllowingStateLoss()
        }
        binding.mainBottomPostPostBtn.setOnClickListener {
            if (isFabOpen) {
                toggleFab()
            }
            setNavigationColorNone()
            startActivity(Intent(this, ProfilePostActivity::class.java))
        }
    }
}