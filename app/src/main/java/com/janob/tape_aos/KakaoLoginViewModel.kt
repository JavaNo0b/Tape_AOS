//package com.janob.tape_aos
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import okhttp3.ResponseBody
//
//class KakaoLoginViewModel:ViewModel() {
//    val kakaoApiFetchr = KakaoApiFetchr()
//    var kakaoLiveData:LiveData<KakaoResponse> = MutableLiveData()
//
//    private val mutableUserToken = MutableLiveData<String>()
//    private val mutableUserEmail = MutableLiveData<String>()
//
//    init {
//        mutableUserToken.value =""
//        mutableUserEmail.value =""
//        kakaoLiveData = kakaoApiFetchr.searchKakaoInfo(mutableUserToken.toString(), mutableUserEmail.toString())
//
//    }
//
//    fun fetchUserInfo(userToken:String, userEmail:String){
//        mutableUserToken.value = userToken
//        mutableUserEmail.value = userEmail
//    }
//}