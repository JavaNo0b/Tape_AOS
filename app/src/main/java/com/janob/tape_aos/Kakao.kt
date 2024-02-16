package com.janob.tape_aos

import com.google.gson.annotations.SerializedName
import java.io.File

data class KakaoResponse(
    val success: Boolean,
    val message: String,
    val data: KakaoResponseSign
)

data class KakaoResponseSign(
    val isSignin: Boolean,
)

data class SignupResponse(
    val success: Boolean,
    val message: String
)

data class NicknameResponse(
    @SerializedName("success")val success: Boolean,
    @SerializedName("message")val message: String,

)
data class NicknameData(
    @SerializedName("nickname") val nickname: String,
)


data class UserProfileResponse(
    val success: Boolean,
    val message: String,
    val data : UserProfileResponseData
)

data class UserProfileResponseData(
    val introduce : String,
    val token :String
)

data class SignUp(
    var email: String = "",
    var nickname: String = "",
    var introduce: String = "",
    var file: File? = null
)

private data class SignUpResponse(
    val success: Boolean,
    val message: String
)