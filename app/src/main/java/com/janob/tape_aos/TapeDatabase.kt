package com.janob.tape_aos



import android.annotation.SuppressLint
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities=[Reply::class, Tape::class, Song::class, IncludedSong::class, LoginUser::class, User::class], version = 1)
@TypeConverters(StringListConverters::class, TapeListConverters::class, SongListConverters::class)


abstract class TapeDatabase : RoomDatabase(){

    //필요한 Dao 추가
    abstract fun replyDao() : ReplyDao
    abstract fun tapeDao() : TapeDao
    abstract fun songDao() : SongDao
    abstract fun IncludedSongDao() : IncludedSongDao
    abstract fun loginuserDao() : LoginUserDao



    abstract fun userDao() : UserDao
    companion object{


        var instance: TapeDatabase? = null


        fun Instance(context: Context): TapeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TapeDatabase::class.java,
                    "user-database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }


    }
}