package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody

class KakaoLoginViewModel:ViewModel() {
    val kakaoApiFetchr = KakaoApiFetchr()
    var kakaoLiveData:LiveData<ResponseBody> = MutableLiveData()

    init {
        kakaoLiveData = kakaoApiFetchr.fetchContents()
    }
}