package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class IncludedSongListViewModel : ViewModel() {

     val includedSongRepository = IncludedSongRepository.get()
     var includedSongsLiveData : LiveData<List<IncludedSong>> = includedSongRepository.getAllInAlbum(10)
    //     fun getAllInAlbum(albumId :Int) :LiveData<List<IncludedSong>>{
//        includedSongsLiveData =  includedSongRepository.getAllInAlbum(albumId)
//        return includedSongsLiveData
//    }

}