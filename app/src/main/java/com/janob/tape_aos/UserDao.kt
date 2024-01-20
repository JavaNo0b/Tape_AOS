package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user : User) : Long
    @Update
    fun update(user : User)
    @Delete
    fun delete(user : User)
    @Query("SELECT * FROM UserTable")
    fun getAll() : List<User>
    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUser(id: Int) : User

    // 이름으로 특정User 찾기
    @Query("SELECT * FROM UserTable WHERE name LIKE :name")
    fun searchUser(name : String) : User
}