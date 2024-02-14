package com.janob.tape_aos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommentListViewModel: ViewModel() {

     val apiFetchr = ApiFetchr()

    var commentListLiveData : MutableLiveData<List<CommentDTO>> = MutableLiveData()
    var tapeIdLiveData : MutableLiveData<Int> = MutableLiveData()
    private var tapeId:Int = 0

    fun fetchTapeId(tapeId:Int){
        this.tapeId = tapeId
    }
    init{
        tapeIdLiveData.value = tapeId
        commentListLiveData.value = apiFetchr.fetchAlbumDTO(tapeId).value!!.albumData.comment
    }
}