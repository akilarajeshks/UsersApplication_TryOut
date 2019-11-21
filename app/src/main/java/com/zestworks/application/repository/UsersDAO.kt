package com.zestworks.application.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zestworks.application.model.UserInfo
import com.zestworks.application.model.Users

@Dao
interface UsersDAO {

    @Query("SELECT * from users_table")
    suspend fun getAllUsers(): Users

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllUsers(usersList:Users)

    @Query("SELECT * from user_info_table WHERE id=:userId ")
    suspend fun getUserInfo(userId:Int):UserInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo)

}