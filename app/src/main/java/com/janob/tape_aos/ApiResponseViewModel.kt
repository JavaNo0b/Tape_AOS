package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ApiResponseViewModel :ViewModel() {

    val responseLiveData : LiveData<String> = ApiFetchr().fetchContents()
}