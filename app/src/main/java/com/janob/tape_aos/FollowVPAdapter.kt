package com.janob.tape_aos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FollowerFragment()
            else -> FollowingFragment()
        }
    }

}