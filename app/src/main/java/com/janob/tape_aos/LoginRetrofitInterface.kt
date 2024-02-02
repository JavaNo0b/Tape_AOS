package com.janob.tape_aos

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface LoginRetrofitInterface {

    @GET("/kakao")
    fun getKakao() : Call<ResponseBody>
/*
    @GET("/kakao/callback")
    fun getKakaoCallback() : Call<LoginResponse>*/
}