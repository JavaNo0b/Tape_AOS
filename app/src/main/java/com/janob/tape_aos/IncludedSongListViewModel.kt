package com.janob.tape_aos


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IncludedSongViewModel: ViewModel() {
    private val apiFetchr = ApiFetchr()

    var songList = mutableListOf<SongDTO>()
    var includedSongDTOLiveData = MutableLiveData<List<SongDTO>>()
    init{
        includedSongDTOLiveData.value = songList
    }


    fun add(song:SongDTO){
        songList.add(song)
    }
    fun remove(song:SongDTO){
        songList.remove(song)
    }
}