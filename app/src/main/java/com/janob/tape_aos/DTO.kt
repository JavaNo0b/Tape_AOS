package com.janob.tape_aos

import com.google.gson.annotations.SerializedName

data class SongDTO(
    //수정
    @SerializedName("musicId") var musicId: Int)

data class SongDetailResultDTO(
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("songs") var songs:List<SongDetailDTO>
)
data class SongDetailDTO(
    @SerializedName("songId") var songId:Int,
    @SerializedName("title") var title: String,
    @SerializedName("singer") var singer:String,
    @SerializedName("album") var album:String,
    @SerializedName("albumCoverImg") var albumCoverImg:String
)
data class FollowDTO(
    @SerializedName("followerId") var followerId : Int,
    @SerializedName("followedId") var followedId : Int)

data class ResultDTO(
    @SerializedName("success") var success:Boolean ,
    @SerializedName("message") var message:String
)
data class UserProResultDTO(
    @SerializedName("success") var success:Boolean,
    @SerializedName("message") var message:String,
    @SerializedName("data") var data:UserInnerDTO

)

data class UserInnerDTO(
    @SerializedName("userNickname") var userName:String,
    @SerializedName("userImage") var userImage:String,
    @SerializedName("introduce") var introduce:String,
    @SerializedName("followers") var followers:Int,
    @SerializedName("followings") var followings:Int,
    @SerializedName("tapeData") var tapeData: List<TapeInnerDTO>

)
data class TapeInnerDTO(
    @SerializedName("tapeId") var tapeId:Int,
    @SerializedName("tapeTitle") var tapeTitle:String,
    @SerializedName("tapeArtist") var tapeArtist: String,
    @SerializedName("tapeImage") var tapeImage:String,
    @SerializedName("isWatched") var isWatched: Boolean
)
data class UserDTO(
    @SerializedName("userId") var userId:Int,
    @SerializedName("userNickname") var userNickname:String ,
    @SerializedName("introduce") var introduce:String,
    @SerializedName("profileImage") var profileImage:String
)
data class TapeDTO(
    @SerializedName("userId") var userId:Int,
    @SerializedName("tapeId") var tapeId:Int,
    @SerializedName("tapeImage") var tapeImage:String,
    @SerializedName("tapeIntroduce") var tapeIntroduce:String
)
data class TodayTapeResultDTO(
    @SerializedName("success") var success:Boolean,
    @SerializedName("message") var message:String,
    @SerializedName("data") var data : List<TodayTapeDataDTO>
)
data class TodayTapeDataDTO(
    @SerializedName("userName") var userName:String,
    @SerializedName("userProfileImage") var profileImage: String,
    @SerializedName("tapeId") var tapeId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("artist") var artist:String,
    @SerializedName("image") var image:String,
    @SerializedName("isWatched") var isWatched:Boolean
)


data class LikedResultDTO(
    @SerializedName("success") var success:Boolean,
    @SerializedName("message") var message:String="",
    @SerializedName("data") var data:List<MusicDTO>
)
data class MusicDTO(
    @SerializedName("musicTitle") var musicTitle:String,
    @SerializedName("musicArtist") var musicArtist:String,
    @SerializedName("musicImage") var musicImage:String
)

data class UserResultDTO(
    @SerializedName("success") var success:Boolean ,
    @SerializedName("message") var message:String ,
    @SerializedName("data") var data:List<UserResultInnerDTO>

)
data class UserResultInnerDTO(
    @SerializedName("userId") var userId:Int,
    @SerializedName("userImage") var userImage: String,
    @SerializedName("userNickname") var userNickname: String,
    @SerializedName("introduce") var introduce:String
)
data class OrderbyLikedTapeDTO(
    @SerializedName("success") var success:Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data:List<LikedTapeInnerDTO>
)

data class LikedTapeInnerDTO(
    @SerializedName("tapeId") var tapeId:Int,
    @SerializedName("tapeTitle") var tapeTitle:String,
    @SerializedName("tapeArtist") var tapeArtist:String,
    @SerializedName("tapeImg") var tapeImg:String
)
data class AlarmResultDTO(
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message:String="",
    @SerializedName("data") var data:List<AlarmInnerDTO>
)
data class AlarmInnerDTO(
    @SerializedName("alarmId") var alarmId:Int,
    @SerializedName("alarmType") var alarmType:String,
    @SerializedName("alarmContent") var alarmContent:String,
    @SerializedName("alarmTime") var alarmTime:String
)
data class TodayTapeDTO(
    @SerializedName("tapeImg") var tapeImg:String,
    @SerializedName("tapeTitle") var tapeTitle:String,
    @SerializedName("tapeContent") var tapeContent:String,
    @SerializedName("tapeMusicData") var tapeMusicData :List<SongDTO>
)

data class MusicInnerDTO(
    @SerializedName("musicId") var musicId:Int,
    @SerializedName("content") var content:String
)
data class TapeDetailDTO(
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("tapeData") var albumData: AlbumDTO
)
data class AlbumDTO(
    @SerializedName("tapeImg") var tapeImg:String,
    @SerializedName("tapeTitle") var tapeTitle: String,
    @SerializedName("tapeIntro") var tapeIntro:String,
    @SerializedName("postDate") var postDate:String,
    @SerializedName("tapeMusicData") var tapeMusicData:List<MusicInnerDTO>,
    @SerializedName("comment") var comment:List<CommentDTO>
)
data class CommentDTO(
    @SerializedName("userId") var userId:Int,
    @SerializedName("content") var content:String,
    @SerializedName("created_at") var createdAt:String
)
data class ListenResultDTO(
    @SerializedName("success") var success: Boolean,
    @SerializedName("message") var message:String,
    @SerializedName("tapeMusicData") var tapeMusicData:List<MusicIdDTO>
)
data class MusicIdDTO(
    @SerializedName("musicId") var musicId:Int
)
data class PostCommentDTO(
    @SerializedName("tapeId") var tapeId:Int,
    @SerializedName("comment") var comment:String
)

data class ReportDTO(
    @SerializedName("tapeId") var tapeId: Int,
    @SerializedName("userId") var userId: Int,
    @SerializedName("commentId") var commentId:Int,
    @SerializedName("reportContent") var reportContent:String
)
data class LikeDTO(
    @SerializedName("userId") var userId:Int,
    @SerializedName("tapeId") var tapeId:Int

)
data class FriendTapeDTO(
    @SerializedName("success") var success:Boolean,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data:List<FriendTapeInnerDTO>

)
data class FriendTapeInnerDTO(
    @SerializedName("tapeId") var tapeId: Int,
    @SerializedName("tapeTitle") var tapeTitle: String,
    @SerializedName("tapeArtist") var tapeArtist: String,
    @SerializedName("tapeImage") var tapeImg: String,
    @SerializedName("isWatched") var isWatched:Boolean
)
data class HiddenDTO(
    @SerializedName("tapeId") var tapeId:Int,
    @SerializedName("userId") var userId: Int
)




