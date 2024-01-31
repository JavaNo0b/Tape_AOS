package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Profile1RetrofitInterface {

    @GET("/account/nickname")
    fun getNickname() : Call<Profile1Response>

    @POST("/account/nickname")
    fun postNickname(@Body profile1 : Profile1): Call<Profile1Response>
}