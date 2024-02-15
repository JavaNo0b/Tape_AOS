package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class TodayTapeListViewModel:ViewModel() {
    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : MutableLiveData<List<TapeInnerDTO>> = MutableLiveData()

    init{
        //모든 테이프 가져오기
        //todayTapeListLiveData.value = apiFetchr.fetchAllTape().value?.data


    }

}