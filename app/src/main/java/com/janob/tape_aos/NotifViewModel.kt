package com.janob.tape_aos


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NotifViewModel : ViewModel() {

    private val apiFetchr = ApiFetchr()

    fun fetchAlarmAll(): LiveData<AlarmResultDTO> {
        return apiFetchr.fetchAlarmAll()
    }

}