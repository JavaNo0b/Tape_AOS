package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap

class TodayTapeListViewModel(private val jwtToken: String):ViewModel() {
    private val apiFetchr = ApiFetchr()
    var todayTapeListLiveData : MutableLiveData<List<TodayTapeDataDTO>> = MutableLiveData()

    private var cursor :Int = 0
    var cursorLiveData :MutableLiveData<Int> = MutableLiveData()

    init{

        //테이프 페이징
        cursorLiveData.value = cursor
        todayTapeListLiveData.value = cursorLiveData.switchMap { cursor -> apiFetchr.fetchPageCursor(jwtToken, cursor) }
                                        .value?.data
    }
    fun nextPage(){
        cursor++
    }



}
//class TodayTapeListViewModel : ViewModel() {
//    private val apiFetchr = ApiFetchr()
//    var todayTapeListLiveData: MutableLiveData<List<TodayTapeDataDTO>> = MutableLiveData()
//
//    private var cursor: Int = 0
//    var cursorLiveData: MutableLiveData<Int> = MutableLiveData()
//    var jwt: String = ""
//
//    fun nextPage() {
//        cursor++
//    }
//
//    // ViewModel 내에서 JWT 토큰 사용하기
//    fun setJwtToken(jwtToken: String) {
//        jwt = jwtToken
//        // 토큰 설정 후 API 호출
//        cursorLiveData.value = cursor
//        todayTapeListLiveData.value =
//            cursorLiveData.switchMap { cursor -> apiFetchr.fetchPageCursor(jwt, cursor) }
//                .value?.data
//    }
//}
