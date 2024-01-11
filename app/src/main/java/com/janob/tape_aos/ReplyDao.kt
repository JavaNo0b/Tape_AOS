package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ReplyDao{
    @Insert
    fun insert(reply: Reply) : Long
    @Update
    fun update(reply: Reply)
    @Delete
    fun delete(reply: Reply)
    @Query("SELECT * FROM reply")
    fun getAll() :List<Reply>
}