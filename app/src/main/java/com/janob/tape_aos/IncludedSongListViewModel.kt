package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class IncludedSongListViewModel : ViewModel() {

     val includedSongRepository = IncludedSongRepository.get()
     lateinit var includedSongsLiveData : LiveData<List<IncludedSong>>
    //테이프에 수록된 곡들
     fun getAllInAlbum(albumId :Int) :LiveData<List<IncludedSong>>{
        includedSongsLiveData =  includedSongRepository.getAllInAlbum(albumId)
        return includedSongsLiveData
    }

}