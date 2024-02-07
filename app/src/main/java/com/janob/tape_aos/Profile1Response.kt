package com.janob.tape_aos

import com.google.gson.annotations.SerializedName

data class Profile1Response(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : Nickname?
)

data class Nickname(
    @SerializedName("nickname") val nickname : String
)
