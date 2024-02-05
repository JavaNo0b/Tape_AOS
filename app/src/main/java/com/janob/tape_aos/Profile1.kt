package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="Profile1Table")
data class Profile1(

    @SerializedName(value = "nickname")val nickname: String,

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
