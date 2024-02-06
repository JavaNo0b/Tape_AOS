package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TapeApi {
    @GET("tape/friends")
    fun fetchTodayTapes(@Query("userId") userId:Int): Call<List<Tape>>
}