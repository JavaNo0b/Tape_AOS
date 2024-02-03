package com.janob.tape_aos

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface KakaoApi {

    //Tape 회원가입 여부 판단
    @GET("/kakao/callback")
    fun searchKakaoInfo(
        @Query("accessToken") userToken: String,
        @Query("email") userEmail: String,
        ): Call<KakaoResponse>

    //Tape 회원가입 여부 판단
    @POST("/account/nickname")
    fun signupNickname(
        @Body nickname: String
    ): Call<SignupResponse>

    //Tape 회원가입 여부 판단
    @Multipart
    @POST("/account/profile")
    fun signupProfile(
        @Part("introduce") introduce: String,
        @Part imageFile : MultipartBody.Part
    ): Call<SignupResponse>
}