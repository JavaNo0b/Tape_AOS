package com.janob.tape_aos

import android.content.Context

class TapeRepository private constructor(context:Context){
    val database = TapeDatabase.Instance(context)
    val tapeDao = database.tapeDao()

    fun getTape(id:Int) = tapeDao.getTape(id)
    fun getAll() = tapeDao.getAll()

    companion object{
        var instance: TapeRepository? = null
         fun initialize(context: Context){
             if(instance == null)
                instance = TapeRepository(context)

         }
        fun get() : TapeRepository {
            return instance?:throw Exception("TapeRepository not initialized")
        }

    }

}