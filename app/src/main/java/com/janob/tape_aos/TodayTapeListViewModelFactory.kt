package com.janob.tape_aos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodayTapeListViewModelFactory(private val jwtToken: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayTapeListViewModel::class.java)) {
            return TodayTapeListViewModel(jwtToken) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}