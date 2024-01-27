package com.janob.tape_aos

import androidx.lifecycle.ViewModel

class SongListViewModel : ViewModel() {

    val songRepository : SongRepository = SongRepository.get()
    val songListLiveData = songRepository.getAll()

}