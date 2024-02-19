package com.janob.tape_aos


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NotifViewModel : ViewModel() {

   /* private val apiFetchr = ApiFetchr()

    fun fetchAlarmAll(): LiveData<AlarmResultDTO> {
        return apiFetchr.fetchAlarmAll()
    }*/
    lateinit var jwtView : String

    private val apiFetchr = ApiFetchr()

    var NotifLiveData : MutableLiveData<List<AlarmInnerDTO>> = MutableLiveData()

/*    init{
        NotifLiveData.value = apiFetchr.fetchAlarmAll(jwtView).value
        Log.d("message11", NotifLiveData.toString())
        //NotifLiveData.value = apiFetchr.fetchAlarmAll().value
    }*/

    fun checklivedata(jwt: String){
        jwtView = jwt

        apiFetchr.fetchAlarmAll(jwtView).observeForever { alarmResultDTO ->
            alarmResultDTO?.let { alarmResultDTO ->
                NotifLiveData.value = alarmResultDTO.data.flatten()
        }
        //NotifLiveData.value = apiFetchr.fetchAlarmAll(jwt).value
        //val Data = apiFetchr.fetchAlarmAll(jwtView).value
        //NotifLiveData.postValue()
        Log.d("message11", NotifLiveData.toString())
        Log.d("message11", "안녕!!")
        //NotifLiveData.value = apiFetchr.fetchAlarmAll().value
    }
/*    fun viewmodel(jwt: String){
        jwtView = jwt
    }*/

//
//    fun fetchAlarmAll() {
//        val liveData = apiFetchr.fetchAlarmAll()
//        NotifLiveData.postValue(liveData.value)
//    }

}}