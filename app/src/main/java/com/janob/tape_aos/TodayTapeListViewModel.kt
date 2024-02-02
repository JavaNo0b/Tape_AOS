package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class TodayTapeListViewModel :ViewModel() {

    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : LiveData<List<Tape>> = MutableLiveData()
    private val mutableUserId = MutableLiveData<Int>()
    init{
        mutableUserId.value = 0
        todayTapeListLiveData = mutableUserId.switchMap { mutableUserId -> apiFetchr.fetchTodayTapesFriends(mutableUserId) }

    }

    fun fetchUserId(userId:Int){
        mutableUserId.value = userId
    }

}