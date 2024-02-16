package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val apiFetchr = ApiFetchr()

    @get:JvmName("userSearch_UserResultInnerDTO")
    val userSearch : LiveData<List<UserResultInnerDTO>>
        get() = apiFetchr._userSearch

    fun loadUserProfile(keyWord: String) {
        apiFetchr.loadUserSearchDTO(keyWord)
    }
    fun getUserSearch() : LiveData<List<UserResultInnerDTO>>{
        return userSearch
    }
}