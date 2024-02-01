package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET

interface TapeApi {
    @GET("tape/friends")
    fun fetchTodayTapes(): Call<List<Tape>>
}