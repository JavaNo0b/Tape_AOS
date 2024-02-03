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