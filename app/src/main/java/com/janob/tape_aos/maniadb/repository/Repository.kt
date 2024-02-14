package com.janob.tape_aos.maniadb.repository

import android.content.Context
import com.janob.tape_aos.maniadb.api.RetrofitInstance
import com.janob.tape_aos.maniadb.model.ManiaDBClientResponse
import retrofit2.Response

class Repository private constructor(context: Context){

    suspend fun getSong(keyword: String) : Response<ManiaDBClientResponse>{
        return RetrofitInstance.api.getSong(keyword)
    }
//
//    suspend fun getAlbum(keyword: String) : Response<ManiaAlbumResponse>{
//        return RetrofitInstance.api.getAlbum(keyword)
//    }

    // 싱글톤
    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}