package com.janob.tape_aos.maniadb.api

import com.janob.tape_aos.maniadb.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
    val api : ManiaDbApi by lazy {
        retrofit.create(ManiaDbApi::class.java)
    }
}