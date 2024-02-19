package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Repository
class ApiFetchr {

    //필요한 api 추가
    private  var apiInterface :ApiInterface


    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://3.37.82.7:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //생성
        apiInterface = retrofit.create(ApiInterface::class.java)

    }
/*    fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : okhttp3.Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("(header Key)", "(header Value)")
                .build()
            proceed(newRequest)
        }
    }*/

    //음원 차트 가져오기
    fun fetchSongListDTO() : LiveData<SongDetailResultDTO> {
        val call: Call<SongDetailResultDTO> = apiInterface.fetchSongListDTO()
        return fetchMetaDTO(call)
    }
    fun searchSongDTO(query:String) : LiveData<SongDetailResultDTO>{
        val call:Call<SongDetailResultDTO> = apiInterface.searchSongDTO(query)
        return fetchMetaDTO(call)
    }
    //todo
    //오늘의 테이프 페이징
    fun fetchPageCursor(cursor:Int):LiveData<TodayTapeResultDTO>{
        val call:Call<TodayTapeResultDTO> = apiInterface.fetchPageCursor(cursor)
        return fetchMetaDTO(call)

    }
    //팔로우 페이지
    //사용자 프로필 불러오기 페이지
    var _userProfile = MutableLiveData<UserInnerDTO>()
    fun loadUserProfileDTO(jwt:String) {
        val call = apiInterface.getUserProfile(jwt!!)
        call.enqueue(object : Callback<UserProResultDTO>{
            override fun onResponse(call: Call<UserProResultDTO>, response: Response<UserProResultDTO>) {
                Log.d("eunseo", "ApiFetchr - onResponse - isSuccess = " + response.isSuccessful.toString())
                if(response.isSuccessful) {
                    Log.d("onResponse", "사용자 프로필 불러오기 / 통신 성공")
                    _userProfile.value = response.body()!!.data

                } else {
                    Log.d("eunseo", "응답 없음")
                }
            }
            override fun onFailure(call: Call<UserProResultDTO>, t: Throwable) {
                Log.d("onFailure", "사용자 프로필 불러오기 / 통신 실패")
            }

        })
    }

//    fun getUserProfileDTO() : Call<UserProResultDTO> {
//        val call : Call<UserProResultDTO> = apiInterface.getUserProfile()
//        return call
////        Log.d("eunseo", "ApiFetchr - getUserProfileDTO - fetchDTO(call) = " + fetchDTO(call).value.toString())
////        return fetchDTO(call)
//    }

    //프로필 수정
    var _userProfileEdit = MutableLiveData<UserDTO>()
    fun loadUserProfileEditDTO(userDTO : UserDTO?) {
        val call = apiInterface.updateUserProfile(userDTO!!)
        call.enqueue(object : Callback<ResultDTO>{
            override fun onResponse(call: Call<ResultDTO>, response: Response<ResultDTO>) {
                Log.d("onResponse", "프로필 수정 / 통신 성공")
                _userProfileEdit.value = userDTO!!
            }

            override fun onFailure(call: Call<ResultDTO>, t: Throwable) {
                Log.d("onFailure", "프로필 수정 / 통신 실패")
            }

        })
    }
    //프로필 공유
    //테이프 게시글 등록
    //게시물 삭제
    //테이프 게시물 불러오기
    fun fetchAllTape():LiveData<TodayTapeResultDTO>{
        val call:Call<TodayTapeResultDTO> = apiInterface.fetchAllTape()
        return fetchMetaDTO(call)
    }
    //좋아요한 곡 불러오기

    //검색페이지
    var _userSearch = MutableLiveData<List<UserResultInnerDTO>>()
    fun loadUserSearchDTO(keyWord : String) {
        val call = apiInterface.userSearch(keyWord)
        call.enqueue(object : Callback<UserResultDTO>{
            override fun onResponse(call: Call<UserResultDTO>, response: Response<UserResultDTO>) {
                Log.d("onResponse", "검색 페이지 / 통신 성공")
                Log.d("onResponse", "keyWord = " + keyWord)
                Log.d("onResponse", "오류메세지 = " + response.message())
                _userSearch.value = response.body()!!.data
            }

            override fun onFailure(call: Call<UserResultDTO>, t: Throwable) {
                Log.d("onFailure", "검색 페이지 / 통신 실패")
            }

        })
    }

    //좋아요순 테이프 불러오기

    //알림정보 불러오기
    fun fetchAlarmAll(jwt : String):LiveData<AlarmResultDTO>{
        val call:Call<AlarmResultDTO> = apiInterface.fetchAlarmAll(jwt)
        return fetchMetaDTO(call)
    }

    //오늘의 테이프 등록
    fun postTodayTape(todayTapeDTO: TodayTapeDTO) : LiveData<ResultDTO>{
        val call:Call<ResultDTO> = apiInterface.postTodayTape(todayTapeDTO)
        return fetchMetaDTO(call)
    }
    //테이프 상세 정보, 댓글 불러오기
    fun fetchAlbumDTO(tapeId:Int): LiveData<TapeDetailDTO>{
        val call:Call<TapeDetailDTO> = apiInterface.fetchTapeDetail(tapeId)
        return fetchMetaDTO(call)
    }
    //테이프 듣기
    //댓글 작성
    fun postComment(comment: PostCommentDTO) :LiveData<ResultDTO>{
        val call:Call<ResultDTO> = apiInterface.postComment(comment)
        return fetchMetaDTO(call)
    }
    //댓글 삭제
    fun deleteComment(tapeId: Int,commentId:Int):LiveData<ResultDTO>{
        val call:Call<ResultDTO> = apiInterface.deleteComment(tapeId,commentId)
        return fetchMetaDTO(call)
    }
    //댓글 수정
    fun updateComment(content:String) :LiveData<ResultDTO> {
        val call:Call<ResultDTO> = apiInterface.updateComment(content)
        return fetchMetaDTO(call)
    }
    //테이프 삭제
    //테이프 댓글 신고
    //테이프 좋아요
    //테이프 내의 음악 좋아요
    //인스타그램 공유
    //오늘의 테이프 정보 불러오기 (친구)
    //오늘의 테이프 정보 불러오기 (내꺼)
    //테이프 안보기
    //이용자 차단
    //
    fun <T> fetchMetaDTO(call:Call<T>) :LiveData<T>{
        val responseLiveData : MutableLiveData<T> = MutableLiveData()

        call.enqueue(object:Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                Log.d("fetch result DTO", response.body().toString())
                responseLiveData.value = response.body() as T
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("fetchResultDTO failed", t.toString())

            }

        })
        return responseLiveData

    }

    fun <T> fetchListDTO(call :Call<List<T>>):LiveData<List<T>>{
        val responseLiveData : MutableLiveData<List<T>> = MutableLiveData<List<T>>()
        //웹사이트에 응답 요청
        call.enqueue(object:Callback<List<T>>{
            override fun onResponse(
                call: Call<List<T>>,
                response: Response<List<T>>
            ) {
                Log.d("api fetchr",response.body().toString())
                responseLiveData.value = response.body() as List<T>
            }

            override fun onFailure(call: Call<List<T>>, t: Throwable) {
                Log.d("api fetchr","failed To Response")
            }

        })
        return responseLiveData
    }

    fun <T> fetchDTO(call: Call<T>) : LiveData<T> {
        val liveData : MutableLiveData<T> = MutableLiveData<T>()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                liveData.value = response.body() as T
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("fetchDTO", "fetchDTO onFailure")
            }
        })
        return liveData
    }





}