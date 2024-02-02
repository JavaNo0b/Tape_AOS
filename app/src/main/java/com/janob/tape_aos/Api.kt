package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    //필요한 메서드 추가
    @GET("")
    fun fetchContents(): Call<List<ApiSong>>
    @GET("/")
    fun searchContents(@Query("text") query:String) :Call<List<ApiSong>>

}