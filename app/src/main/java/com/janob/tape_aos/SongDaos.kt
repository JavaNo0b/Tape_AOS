package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDaos {
    @Insert
    fun insert(song: IncludedSong)

    @Update
    fun update(song: IncludedSong)

    @Delete
    fun delete(song: IncludedSong)

    @Query("SELECT * FROM IncludedSongTable")
    fun getSongs(): List<IncludedSong>

    @Query("SELECT * FROM IncludedSongTable WHERE id = :id")
    fun getSong(id: Int): IncludedSong
    @Query("UPDATE IncludedSongTable SET isLiked = :isLiked WHERE id = :id")
    fun updateIsLikeById(isLiked: Boolean,id: Int)
    @Query("SELECT * FROM IncludedSongTable WHERE isLiked = :isLiked")
    fun getLikedSongs(isLiked: Boolean): List<IncludedSong>

    @Query("SELECT * FROM IncludedSongTable WHERE albumIdx = :albumIdx") //해당 앨범의 수록곡의 리스트
    fun getSongsInAlbum(albumIdx: Int): List<IncludedSong>
}