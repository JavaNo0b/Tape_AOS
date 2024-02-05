package com.janob.tape_aos

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //필요한 메서드 추가
    //////////////////////스포티파이 연동 todo
    @GET("")
    fun fetchContents(): Call<List<SongDTO>>
    @GET("/")
    fun searchContents(@Query("text") query:String) :Call<List<SongDTO>>
    ///////////////////////////////
    //팔로우 페이지
    @POST("/follow")
    fun fetchFollow(@Body followDTO :FollowDTO) :Call<ResultDTO>
    //사용자 프로필 불러오기 페이지
    @GET("/profile")
    fun getUserProfile(@Query("userId") userId:Int) :Call<UserProResultDTO>
    //프로필 수정
    @PUT("/profile")
    fun updateUserProfile(@Body userDTO: UserDTO ):Call<ResultDTO>
    //프로필 공유
    @GET("/profile/share/{id}")
    fun shareUserProfile(@Path(value="id") id:String,@Query("userId") userId:Int):Call<UserProResultDTO>
    //테이프 게시글 등록
    @POST("/tape")
    fun fetchTape(@Body tapeDTO:TapeDTO):Call<ResultDTO>
    //게시물 삭제
    @DELETE("/tape/post")
    fun deletePost(@Query("tapePostId") tapePostId:Int):Call<ResultDTO>
    //테이프 게시물 불러오기
    @GET("/tape/post/all")
    fun fetchAllTape():Call<TapeResultDTO>
    //좋아요한 곡 불러오기
    @GET("/music/like")
    fun fetchLiked(@Query("userId")userId:Int) :Call<LikedResultDTO>
    //검색페이지
    @GET("/search")
    fun searchUser(@Query("keyword") keyword:String):Call<UserResultDTO>
    //좋아요순 테이프 불러오기
    @GET("/tape/orderby/like")
    fun fetchTapeOrderbyLike():Call<OrderbyLikedTapeDTO>
    //알림정보 불러오기
    @GET("/alarm/all")
    fun fetchAlarmAll(@Query("userId") userId:Int) :Call<AlarmResultDTO>
    //오늘의 테이프 등록
    @POST("/tape/today")
    fun postTodayTape(@Body tapeDTO: TodayTapeDTO):Call<ResultDTO>
    //테이프 상세 페이지
    @GET("/tape/{id}")
    fun fetchTapeDetail(@Path(value="id") id:Int,@Query("tapeId") tapeId:Int) :Call<TapeDetailDTO>
    //테이프 듣기
    @GET("/tape/listen")
    fun listenTape(@Query("tapeId") tapeId:Int):Call<ListenResultDTO>
    //댓글 작성
    @GET("/tape/:tapeId/comment")
    fun postComment(@Path(value="tapeId")tapeId: Int, @Body comment:PostCommentDTO):Call<ResultDTO>
    //댓글 삭제
    @DELETE("/tape/:tapeId/comment/:id")
    fun deleteComment(@Path(value="tapeId") tapId: Int,
                      @Path(value="id") id:Int,
                      @Query("tapeId") tapeId: Int,
                      @Query("commentId") commentId:Int):Call<ResultDTO>
    //댓글 수정
    @FormUrlEncoded
    @PUT("/tape/comment/")
    fun updateComment(@Field("content") content: String):Call<ResultDTO>
    //테이프 삭제
    @DELETE("/tape/")
    fun deleteTape(@Query("tapeId")tapeId: Int):Call<ResultDTO>
    //테이프 댓글 신고
    @POST("/tape/comment/report/")
    fun reportComment(@Body report:ReportDTO):Call<ResultDTO>
    //테이프 좋아요
    @POST("/tape/like")
    fun likeTape(@Body likeDTO: LikeDTO):Call<ResultDTO>
    //테이프 내의 음악 좋아요
    @FormUrlEncoded
    @POST("/tape/music/like")
    fun likeMusic(@Field("tapeMusicId") tapeMusicId:Int):Call<ResultDTO>
    //인스타그램 공유
    @POST("/tape/share/")
    fun shareTape(@Query("tapeId") tapeId:Int):Call<ResultDTO>
    //오늘의 테이프 정보 불러오기 (친구 )
    @GET("/tape/friends")
    fun fetchFriendTape(@Query("userId") userId: Int):Call<FriendTapeDTO>
    //오늘의 테이프 정보 불러오기 (내꺼)
    @GET("/tape")
    fun fetchMyTape(@Query("userId") userId:Int):Call<FriendTapeDTO>
    //테이프 안보기
    @POST("/hidden/tape")
    fun hideTape(@Body hiddenDTO: HiddenDTO):Call<ResultDTO>
    //이용자 차단
    //












}