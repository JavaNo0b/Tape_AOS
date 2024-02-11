package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel(userDTO : UserDTO) : ViewModel() {
    private val apiFetchr = ApiFetchr()
    private var userDTO = userDTO

    private val updateUserProfile : LiveData<ResultDTO>
        get() = apiFetchr.updateUserProfileDTO(userDTO)

    fun loadUpdateUserProfile() {
        apiFetchr.updateUserProfileDTO(userDTO)
    }
    fun setUpdateUserProfile(userDTO : UserDTO) {
    }
    fun getAll() : LiveData<ResultDTO>{
        return updateUserProfile
    }
}