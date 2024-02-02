package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TapeApi {
    //오늘의 테이프 정보 불러오기 API (auth) (친구꺼)
    @GET("/tape/friends")
    fun fetchTodayTapesFriends(@Query("userId") userId:Int): Call<List<Tape>>

    //오늘의 테이프 정보 불러오기 API (auth) ( 본인꺼 )
    @GET("/tape")
    fun fetchTodayTapes(@Query("userId") userId:Int): Call<List<Tape>>
}