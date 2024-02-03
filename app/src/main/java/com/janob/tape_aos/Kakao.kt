package com.janob.tape_aos

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
    val success: Boolean,
    val message: String,
    val data: NicknameData
)
data class NicknameData(
    val nickname: String,
)


data class IntroduceResponse(
    val success: Boolean,
    val message: String,
    val data: IntroduceData
)
data class IntroduceData(
    val introduce: String,
)
data class SignUp(
    var email: String = "",
    var nickname: String = "",
    var introduce: String = "",
)