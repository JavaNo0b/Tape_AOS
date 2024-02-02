package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class ApiResponseViewModel :ViewModel() {

    var responseLiveData : LiveData<List<ApiSong>> = MutableLiveData()

    private val apiFetchr = ApiFetchr()
    private val mutalbeSearchTerm = MutableLiveData<String>()

    init{
        responseLiveData = apiFetchr.fetchContents()
        mutalbeSearchTerm.value = ""

        responseLiveData = mutalbeSearchTerm.switchMap { mutalbeSearchTerm -> apiFetchr.searchContents(mutalbeSearchTerm) }




    }

    fun fetchSearchTerm(query:String){
        mutalbeSearchTerm.value = query
    }
}