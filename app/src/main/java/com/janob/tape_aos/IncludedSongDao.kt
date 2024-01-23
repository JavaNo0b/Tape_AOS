package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface IncludedSongDao {
    @Insert
    fun insert(song: IncludedSong)

    @Update
    fun update(song: IncludedSong)

    @Delete
    fun delete(song: IncludedSong)

    @Query("DELETE FROM IncludedSongTable")
    fun deleteAll()
    @Query("SELECT * FROM IncludedSongTable")
    fun getSongs(): LiveData<List<IncludedSong>>


    @Query("SELECT * FROM IncludedSongTable WHERE id = :id")
    fun getSong(id: Int): IncludedSong

    @Query("SELECT * FROM IncludedSongTable WHERE tapeIdx = :albumIdx") //해당 앨범의 수록곡의 리스트
    fun getSongsInAlbum(albumIdx: Int): LiveData<List<IncludedSong>>
}