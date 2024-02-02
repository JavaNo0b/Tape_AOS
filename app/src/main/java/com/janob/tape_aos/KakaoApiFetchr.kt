package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class KakaoApiFetchr {

    private var kakaoApi: KakaoApi
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://3.34.42.155:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        kakaoApi = retrofit.create(KakaoApi::class.java)
    }

    fun fetchContents() : LiveData<ResponseBody> {
        val call: Call<ResponseBody> = kakaoApi.fetchContents()
        return fetchMetaData(call)
    }
    fun fetchMetaData(call : Call<ResponseBody>): LiveData<ResponseBody> {
        val responseLiveData = MutableLiveData<ResponseBody>()
        //웹사이트에 응답 요청
        call.enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("kakao ApiFetchr",response.body()!!.string())
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("kakao ApiFetchr","failed To Response ${t.message}")
            }

        })
        return responseLiveData
    }
}