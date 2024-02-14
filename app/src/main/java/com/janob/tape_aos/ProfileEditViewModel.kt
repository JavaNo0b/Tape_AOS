package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel(userDTO : UserDTO) : ViewModel() {
    private val apiFetchr = ApiFetchr()
    //private var userDTO = userDTO

    @get:JvmName("UserDTO")
    val userProfileEdit : LiveData<UserDTO>
        get() = apiFetchr._userProfileEdit

    fun loadUserProfileEdit(userDTO : UserDTO){
        apiFetchr.loadUserProfileEditDTO(userDTO)
    }
    fun getUserProfileEdit() : LiveData<UserDTO>{
        return userProfileEdit
    }

//    private val updateUserProfile : LiveData<ResultDTO>
//        get() = apiFetchr.updateUserProfileDTO(userDTO)
//
//    fun loadUpdateUserProfile() {
//        apiFetchr.updateUserProfileDTO(userDTO)
//    }
//    fun setUpdateUserProfile(userDTO : UserDTO) {
//    }
//    fun getAll() : LiveData<ResultDTO>{
//        return updateUserProfile
//    }
}