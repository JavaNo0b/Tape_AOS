package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    //필요한 메서드 추가
    @GET("/")
    fun fetchContents(): Call<String>
}