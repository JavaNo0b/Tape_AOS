package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Profile2RetrofitInterface {

    @GET("/account/profile")
    fun getIntro() : Call<Profile2Response>

    @POST("/account/profile")
    fun postIntro(@Body intro : String): Call<Profile2Response>
}