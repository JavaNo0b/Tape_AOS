package com.janob.tape_aos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class TodayTapeListViewModel:ViewModel() {
    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : MutableLiveData<List<TodayTapeDataDTO>> = MutableLiveData()

    private var cursor :Int = 1
    var cursorLiveData :MutableLiveData<Int> = MutableLiveData()
    init{

        //테이프 페이징
        todayTapeListLiveData.value = apiFetchr.fetchPageCursor(cursor).value!!.data

        cursorLiveData.value = cursor
        todayTapeListLiveData.value = cursorLiveData.switchMap { cursor -> apiFetchr.fetchPageCursor(cursor) }
                                        .value!!
                                        .data
    }
    fun nextPage(){
        cursor++
    }


}