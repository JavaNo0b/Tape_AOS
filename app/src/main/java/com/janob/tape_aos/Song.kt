package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Song")
data class Song(val coverImg : Int,
                val title :String,
                val singer:String,
                val album : String,
                @PrimaryKey(autoGenerate = true)
                val id : Long? = null)
