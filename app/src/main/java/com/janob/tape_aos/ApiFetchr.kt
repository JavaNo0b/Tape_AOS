package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Repository
class ApiFetchr {


    //필요한 api 추가
    private  var api :Api
    private  var tapeApi:TapeApi
    private  var replyApi:ReplyApi

    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://3.34.42.155:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //생성
        api = retrofit.create(Api::class.java)
        tapeApi = retrofit.create(TapeApi::class.java)
        replyApi = retrofit.create(ReplyApi::class.java)

    }

    fun fetchContents() : LiveData<List<SongDTO>> {
        val call: Call<List<SongDTO>> = api.fetchContents()
        return fetchMetaData(call)
    }
    fun searchContents(query:String) : LiveData<List<SongDTO>>{
        val call:Call<List<SongDTO>> = api.searchContents(query)
        return fetchMetaData(call)
    }
    fun <T> fetchMetaData(call :Call<List<T>>):LiveData<List<T>>{
        val responseLiveData : MutableLiveData<List<T>> = MutableLiveData<List<T>>()
        //웹사이트에 응답 요청
        call.enqueue(object:Callback<List<T>>{
            override fun onResponse(
                call: Call<List<T>>,
                response: Response<List<T>>
            ) {
                Log.d("MelonApiFetchr",response.body().toString())
                responseLiveData.value = response.body() as List<T>
            }

            override fun onFailure(call: Call<List<T>>, t: Throwable) {
                Log.d("MelonApiFetchr","failed To Response")
            }

        })
        return responseLiveData
    }
//    fun fetchTodayTapesFriends(userId:Int) :LiveData<List<Tape>>{
//        val call = tapeApi.fetchTodayTapesFriends(userId)
//        return fetchMetaData(call)
//    }

    fun fetchTodayTapes(userId:Int) :LiveData<List<Tape>>{
        val call = tapeApi.fetchTodayTapes(userId)
        return fetchMetaData(call)
    }
    fun fetchTapeReply():LiveData<List<Reply>>{
        val call = replyApi.fetchContents()
        return fetchMetaData(call)
    }



}