package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class TodayTapeListViewModel:ViewModel() {
    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : MutableLiveData<List<TodayTapeDataDTO>> = MutableLiveData()
    var responseDTO : LiveData<TodayTapeResultDTO> = MutableLiveData()

    private var cursor :Int = 0
    var cursorLiveData :MutableLiveData<Int> = MutableLiveData()
    init{

        //테이프 페이징
        responseDTO = apiFetchr.fetchPageCursor(cursor)

        cursorLiveData.value = cursor
        todayTapeListLiveData.value = cursorLiveData.switchMap { cursor -> apiFetchr.fetchPageCursor(cursor) }
                                        .value?.data
        todayTapeListLiveData.value = responseDTO.switchMap { response->apiFetchr.fetchPageCursor(cursor) }
                                        .value?.data

    }
    fun nextPage(){
        cursor++
    }


}