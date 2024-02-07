package com.janob.tape_aos

import com.google.gson.annotations.SerializedName

data class Profile2Response(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : Introduce?
)

data class Introduce(
    @SerializedName("introduce") val introduce : String
)

