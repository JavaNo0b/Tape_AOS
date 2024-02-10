package com.janob.tape_aos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TapeDetailListViewModel: ViewModel() {

    private val apiFetchr = ApiFetchr()

    var tapeDetailLiveData : MutableLiveData<TapeDetailInnerDTO> = MutableLiveData()
    var tapeDetailMusicListLiveData : MutableLiveData<List<MusicInnerDTO>> = MutableLiveData()
    var tapeIdLiveData : MutableLiveData<Int> = MutableLiveData()
    private var tapeId:Int = 0

    fun fetchTapeId(tapeId:Int){
        this.tapeId = tapeId
    }
    init{
        tapeIdLiveData.value = tapeId
        tapeDetailLiveData.value = apiFetchr.fetchTapeDetailDTO(tapeId).value!!.tapeData
        tapeDetailMusicListLiveData.value = apiFetchr.fetchTapeDetailDTO(tapeId).value!!.tapeData.tapeMusicData
    }
}