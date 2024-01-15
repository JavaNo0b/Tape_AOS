package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {
    @Insert
    fun insert(tapeAlbum: TapeAlbum) : Long
    @Update
    fun update(tapeAlbum : TapeAlbum)
    @Delete
    fun delete(tapeAlbum: TapeAlbum)
    @Query("SELECT * FROM album")
    fun getAll() : List<TapeAlbum>
}