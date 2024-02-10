package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    @SerializedName(value = "") var userKey : Int? = null, // userKey=1 -> 사용자, userKey=0 -> 타유저
    var userImg : Int? = null,
    //var userImg : String? = null,
    var name : String = "",
    var comment : String = "",
        // 팔로워, 팔로잉 목록
    var followerList : List<String>,
    var followingList : List<String>,
        // 테이프 목록
    var tapeList : List <Tape>,
    var likeList : List<Song>,
    @PrimaryKey(autoGenerate = true) var id : Long? = null,


//    @PrimaryKey var id : Int? = null,
//    var profile_id : Int? = null,
//    var name : String? = "",
//    @SerializedName(value = "userNickname") var nick_name : String? = "",
//    @SerializedName(value = "userImage") var image : String? = "",
//    @SerializedName(value = "introduce") var introduce : String? = "",
//    var create_date : String? = "",
//    var update_date : String? = "",
//    var is_deactived : Boolean? = null
)
