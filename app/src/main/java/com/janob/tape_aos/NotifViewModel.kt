package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifViewModel : ViewModel() {

    private val _notifData = MutableLiveData<List<NotifData>?>()
    val notifData: LiveData<List<NotifData>?> get() = _notifData

    init {
        getNotif()
    }


    private fun getNotif(){
        val notif = getRetrofit().create(NotifRetrofitInterface::class.java)
        notif.getNotif().enqueue(object : Callback<NotifResponse> {
            override fun onResponse(call: Call<NotifResponse>, response: Response<NotifResponse>) {
                val notifResponse = response.body()

                notifResponse?.let {
                    if (notifResponse.success) {
                        Log.d("", "서버 성공")
                        _notifData.value = it.data
                    } else{  // 서버로부터 오류
                        Log.d("", "서버 오류")
                        _notifData.value = null
                    }
                }
            }

            override fun onFailure(call: Call<NotifResponse>, t: Throwable) {
                Log.d("", "통신 오류")
                _notifData.value = null
            }

        })
    }
}