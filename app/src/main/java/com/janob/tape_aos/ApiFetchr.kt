package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
//Repository
class ApiFetchr {


    private lateinit var api :Api
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www./")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun fetchContents() : LiveData<String> {
        val responseLiveData : MutableLiveData<String> = MutableLiveData<String>()
        val call: Call<String> = api.fetchContents()
        //웹사이트에 응답 요청
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //사이트로 부터 응답 옴
                Log.d("ApiFetchr"," got response $response")
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                //응답이 실패
                Log.d("ApiFetchr","failed to response")
            }

        })

        return responseLiveData
    }

}