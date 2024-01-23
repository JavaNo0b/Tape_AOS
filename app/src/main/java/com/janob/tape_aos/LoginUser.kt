package com.janob.tape_aos

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LoginUser")
data class LoginUser(

    val token: String?,
    val nickname: String?,   //닉네임
    var profileimg: ByteArray?,  //프로필
    val profileintro: String?,  //소개글
    @PrimaryKey(autoGenerate = true) var id: Int = 0
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createByteArray(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(nickname)
        parcel.writeByteArray(profileimg)
        parcel.writeString(profileintro)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginUser> {
        override fun createFromParcel(parcel: Parcel): LoginUser {
            return LoginUser(parcel)
        }

        override fun newArray(size: Int): Array<LoginUser?> {
            return arrayOfNulls(size)
        }
    }

}
