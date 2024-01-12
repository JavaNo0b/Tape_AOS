package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="Reply")
data class Reply(var idx: Int,
                 var text: String?,
                 @PrimaryKey(autoGenerate = true)
                 var id:Long? = null)



