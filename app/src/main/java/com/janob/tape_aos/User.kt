package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var userImg : Int? = null,
    var name : String = "",
    var comment : String = "",
        // 팔로워, 팔로잉 목록
    var followerList : List<String>,
    var followingList : List<String>,
        // 테이프 목록
    var tapeList : List <Tape>,
    @PrimaryKey(autoGenerate = true) var id : Long? = null
)
