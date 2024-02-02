package com.janob.tape_aos

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface KakaoApi {
    @GET("/kakao")
    fun fetchContents() : Call<ResponseBody>

}