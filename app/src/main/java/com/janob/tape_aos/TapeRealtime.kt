package com.janob.tape_aos

data class TapeRealtime(
    var title : String? = "",
    var singer : String? = "",
    var userName : String? = "",
    var albumCover : Int? = null,
    //유저 동그라미 화면
    var userImage : Int? = null,
)