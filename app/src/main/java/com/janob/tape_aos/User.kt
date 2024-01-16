package com.janob.tape_aos

data class User(
    var userImg : Int? = null,
    var name : String? = "",
    var comment : String? = "",

    //var thumbnailImg : Int? = null

    // 팔로워, 팔로잉 목록
    var followerList : ArrayList<String>,
    var followingList : ArrayList<String>,
)
