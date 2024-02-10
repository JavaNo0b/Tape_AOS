package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val apiFetchr = ApiFetchr()

    private val userProfile : LiveData<UserProResultDTO>
        get() = apiFetchr.getUserProfileDTO()

    fun loadUserProfile(){
        apiFetchr.getUserProfileDTO()
    }
    fun getAll() : LiveData<UserProResultDTO>{
        return userProfile
    }
}