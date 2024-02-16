package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val apiFetchr = ApiFetchr()

    @get:JvmName("userProfile_UserInnerDTO")
    val userProfile : LiveData<UserInnerDTO>
        get() = apiFetchr._userProfile


    fun loadUserProfile(){
        apiFetchr.loadUserProfileDTO()

        Log.d("eunseo", "뷰모델-loadUserProfile" + userProfile.value?.toString())
    }
    fun getAll() : LiveData<UserInnerDTO>{
        return userProfile
    }
}