package com.janob.tape_aos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities=[Reply::class, TapeAlbum::class, Song::class], version = 1)
abstract class TapeDatabase : RoomDatabase(){

    //필요한 Dao 추가
    abstract fun replyDao() : ReplyDao
    abstract fun albumDao() : AlbumDao
    abstract fun songDao() : SongDao
    companion object{

        var instance : TapeDatabase? = null


        fun Instance(context : Context) : TapeDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                    TapeDatabase::class.java,
                    "user-database")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }


    }

}