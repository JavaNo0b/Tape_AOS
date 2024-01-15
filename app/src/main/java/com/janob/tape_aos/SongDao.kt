package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song :Song) :Long?
    @Delete
    fun delete(song : Song)
    @Update
    fun update(song : Song)
    @Query("SELECT * FROM song")
    fun getAll() : List<Song>

}