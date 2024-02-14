package com.janob.tape_aos

import com.google.gson.annotations.SerializedName

data class KakaoResponse(
    val success: Boolean,
    val message: String,
    val data: KakaoResponseSign
)

data class KakaoResponseSign(
    val isSignin: Boolean,
)


data class NicknameResponse(
    val success: Boolean,
    val message: String,
    val data: NicknameData
)
data class NicknameData(
    val nickname: String,
)


data class SignResponse(
    val success: Boolean,
    val message: String,
    val data: SignData
)
data class SignData(
    val introduce: String,
    @SerializedName(value = "token") var jwt: String,
)

//data class SignUpResponse(
//    val success: Boolean,
//    val message: String,
//    val data: SignUpData
//)
//
//data class SignUpData(
//)

data class SignUp(
    var email: String = "",
    var nickname: String = "",
    var introduce: String = "",
)