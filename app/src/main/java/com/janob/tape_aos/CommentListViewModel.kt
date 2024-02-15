package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class CommentListViewModel: ViewModel() {

    private val apiFetchr = ApiFetchr()

    var commentListLiveData : MutableLiveData<List<CommentDTO>> = MutableLiveData()
    var tapeIdLiveData : MutableLiveData<Int> = MutableLiveData()
    private var tapeId:Int = 0

    fun fetchTapeId(tapeId:Int){
        this.tapeId = tapeId
    }
    init{
        tapeIdLiveData.value = tapeId
        commentListLiveData.value = apiFetchr.fetchTapeDetailDTO(tapeId).value!!.tapeData.comment
    }
}