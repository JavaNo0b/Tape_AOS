//package com.janob.tape_aos
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
//
//class KakaoApiFetchr {
//
//    private var kakaoApi: KakaoApi
//    init{
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("http://3.34.42.155:3000/")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build()
//        kakaoApi = retrofit.create(KakaoApi::class.java)
//    }
//
////    fun fetchContents() : LiveData<ResponseBody> {
////        val call: Call<ResponseBody> = kakaoApi.fetchContents()
////        return fetchMetaData(call)
////    }
//
//
//    fun fetchMetaData(call : Call<KakaoResponse>): LiveData<KakaoResponse> {
//        val responseLiveData = MutableLiveData<KakaoResponse>()
//        //웹사이트에 응답 요청
//        call.enqueue(object: Callback<KakaoResponse> {
//
//            override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {
//                val resp = response.body()!!
//                val loginActivity = LoginActivity.getInstance()
//
//                if(resp.success){
//                    Log.d("kakao ApiFetchr",resp.success.toString())
//                    Log.d("kakao ApiFetchr",resp.data.isSignin.toString())
//                    responseLiveData.value = resp
//                    loginActivity?.NextActivity(resp.data.isSignin)
//
//                }else{
//                    Log.d("kakao ApiFetchr",resp.success.toString())
//                    Log.d("kakao ApiFetchr",resp.message)
//                }
//            }
//
//            override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
//                Log.d("kakao ApiFetchr","failed To Response ${t.message}")
//            }
//
//        })
//        return responseLiveData
//    }
//
//    fun searchKakaoInfo(userToken:String, userEmail:String) :LiveData<KakaoResponse>{
//        val call = kakaoApi.searchKakaoInfo(userToken, userEmail)
//        return fetchMetaData(call)
//    }
//
//}