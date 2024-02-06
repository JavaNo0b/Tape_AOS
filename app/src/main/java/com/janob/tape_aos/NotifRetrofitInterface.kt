package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.GET


interface NotifRetrofitInterface {

    @GET("/alarm/all")
    fun getNotif() : Call<NotifResponse>
}