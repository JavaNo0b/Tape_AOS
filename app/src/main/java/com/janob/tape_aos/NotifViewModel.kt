package com.janob.tape_aos


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NotifViewModel : ViewModel() {

   /* private val apiFetchr = ApiFetchr()

    fun fetchAlarmAll(): LiveData<AlarmResultDTO> {
        return apiFetchr.fetchAlarmAll()
    }*/

    private val apiFetchr = ApiFetchr()

    var NotifLiveData : MutableLiveData<AlarmResultDTO> = MutableLiveData()

/*
    init{
        NotifLiveData.value = apiFetchr.fetchAlarmAll().value
    }
*/

    fun fetchAlarmAll() {
        val liveData = apiFetchr.fetchAlarmAll()
        NotifLiveData.postValue(liveData.value)
    }

}