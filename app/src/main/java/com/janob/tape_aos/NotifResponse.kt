package com.janob.tape_aos

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class NotifResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<NotifData>?
)

data class NotifData(
    @SerializedName("alarmId") val alarmId : Int?,
    @SerializedName("alarmType") val alarmType : String?,
    @SerializedName("alarmContent") val alarmContent : String?,
    @SerializedName("alarmTime") val alarmTime : Time?
)