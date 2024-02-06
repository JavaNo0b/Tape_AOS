package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReplyListViewModel: ViewModel() {

    private val apiFetchr = ApiFetchr()
    var replyListLiveData : LiveData<List<Reply>> = MutableLiveData()

    init{
        replyListLiveData = apiFetchr.fetchTapeReply()
    }

}