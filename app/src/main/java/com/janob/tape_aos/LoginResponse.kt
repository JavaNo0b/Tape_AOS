package com.janob.tape_aos

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String
)
