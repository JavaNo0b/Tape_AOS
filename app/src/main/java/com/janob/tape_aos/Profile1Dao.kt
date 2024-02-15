package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface Profile1Dao {

    @Insert
    fun insert(profile1: Profile1)
}