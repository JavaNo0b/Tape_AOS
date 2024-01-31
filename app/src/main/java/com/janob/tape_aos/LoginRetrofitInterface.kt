package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET

interface LoginRetrofitInterface {

    @GET("/kakao")
    fun getKakao() : Call<LoginResponse>
/*
    @GET("/kakao/callback")
    fun getKakaoCallback() : Call<LoginResponse>*/
}