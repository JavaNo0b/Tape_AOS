package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class SongListViewModel : ViewModel() {



    var songsCount : MutableLiveData<Int> = MutableLiveData()
    private val apiFetchr  = ApiFetchr()

    var songListLiveData: LiveData<SongDetailResultDTO> = MutableLiveData()

    var includedSongList =  mutableListOf<SongDTO>()
    private var queryTerm : MutableLiveData<String> = MutableLiveData()
    init
    {
        //
        songListLiveData = apiFetchr.fetchSongListDTO()
        //쿼리 변경
        songListLiveData = queryTerm.switchMap{ queryTerm  -> apiFetchr.searchSongDTO(queryTerm)  }
    }

    fun plusSong(){
        songsCount.value = (songsCount.value)?.plus(1)
    }
    fun minusSong(){
        songsCount.value = (songsCount.value)?.minus(1)
    }
    fun fetchQueryTerm(query:String){
        queryTerm.value = query
    }
    fun add(song:SongDTO){
        includedSongList.add(song)
    }
    fun remove(song:SongDTO){
        includedSongList.remove(song)
    }



}