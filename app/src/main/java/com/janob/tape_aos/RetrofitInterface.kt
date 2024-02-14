package com.janob.tape_aos

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitInterface {

        //Tape 회원가입 여부 판단
    @GET("/kakao/callback")
    fun searchKakaoInfo(
            @Query("accessToken") userToken: String,
            @Query("email") userEmail: String,
        ): Call<KakaoResponse>

    //Tape 회원가입 여부 판단
    @POST("/account/nickname")
    fun signupNickname(
        @Body nicknameData: NicknameData
    ): Call<NicknameResponse>

    //Tape 회원가입 여부 판단


    @Multipart
    @POST("/account/profile")
    fun signupProfile(
        //@Body signUp: SignUp
        @Part("email") email: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("introduce") introduce: RequestBody,
        @Part image: MultipartBody.Part,
    ): Call<UserProfileResponse>
}