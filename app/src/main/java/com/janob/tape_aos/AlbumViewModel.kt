package com.janob.tape_aos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class AlbumViewModel : ViewModel() {
    private val apiFetchr = ApiFetchr()
    //관찰 데이터
    var albumDTOLiveData : MutableLiveData<AlbumDTO> = MutableLiveData()
    var tapeIdLiveData : MutableLiveData<Int> = MutableLiveData()
    //실제 원본 데이터
    private var tapeId  = 1

    init {
        tapeIdLiveData.value = tapeId
        albumDTOLiveData.value = apiFetchr.fetchAlbumDTO(tapeId).value?.albumData

        albumDTOLiveData.value = tapeIdLiveData.switchMap { id -> apiFetchr.fetchAlbumDTO(id) }.value?.albumData



    }
    fun setAlbumId(id : Int){
        tapeId = id
    }




}