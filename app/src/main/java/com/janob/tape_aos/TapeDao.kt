package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TapeDao {
    @Insert
    fun insert(tape: Tape) : Long
    @Update
    fun update(tape : Tape)
    @Delete
    fun delete(tape: Tape)
    @Query("SELECT * FROM TapeTable")
    fun getAll() : List<Tape>


}