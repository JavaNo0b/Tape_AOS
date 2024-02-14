package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val apiFetchr = ApiFetchr()

    val userProfile : LiveData<UserInnerDTO>
        get() = apiFetchr._userProfile
    val userProfileEdit : LiveData<UserDTO>
        get() = apiFetchr._userProfileEdit

    fun loadUserProfile(){
        apiFetchr.loadUserProfileDTO()

        Log.d("eunseo", "뷰모델-loadUserProfile" + userProfile.value?.toString())
    }
    fun getAll() : LiveData<UserInnerDTO>{
        return userProfile
    }

    fun loadUserProfileEdit(userDTO : UserDTO){
        apiFetchr.loadUserProfileEditDTO(userDTO)
    }
    fun getUserProfileEdit() : LiveData<UserDTO>{
        return userProfileEdit
    }
}