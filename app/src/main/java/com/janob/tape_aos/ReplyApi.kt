package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET

interface ReplyApi {
    @GET("tape")
    fun fetchContents() : Call<List<Reply>>

}