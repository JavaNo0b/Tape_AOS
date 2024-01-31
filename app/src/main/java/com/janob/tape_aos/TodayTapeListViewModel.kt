package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodayTapeListViewModel :ViewModel() {

    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : LiveData<List<Tape>> = MutableLiveData()
    init{
        todayTapeListLiveData = apiFetchr.fetchTodayTapes()
    }

}