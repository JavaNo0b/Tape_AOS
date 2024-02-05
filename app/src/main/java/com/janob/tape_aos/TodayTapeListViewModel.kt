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
        //유저 아이디 바뀌면, 오늘의 테이프 갱신
        mutableUserId.value = 0
        todayTapeListLiveData = mutableUserId.switchMap { mutableUserId -> apiFetchr.fetchTodayTapes(mutableUserId) }

    }

    fun fetchUserId(userId:Int){
        mutableUserId.value = userId
    }

}