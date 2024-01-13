package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song: IncludedSong)

    @Update
    fun update(song: IncludedSong)

    @Delete
    fun delete(song: IncludedSong)

    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<IncludedSong>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): IncludedSong

    @Query("SELECT * FROM SongTable WHERE albumIdx = :albumIdx") //해당 앨범의 수록곡의 리스트
    fun getSongsInAlbum(albumIdx: Int): List<IncludedSong>
}