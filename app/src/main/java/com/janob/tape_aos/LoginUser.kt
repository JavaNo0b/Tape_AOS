package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kakao.sdk.auth.model.OAuthToken


@Entity(tableName = "LoginUser")
data class LoginUser(

    val token: String,
    val nickname: String,   //닉네임
    var profileimg : ByteArray?=null,  //프로필
    val profileintro: String? = null,  //소개글
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
