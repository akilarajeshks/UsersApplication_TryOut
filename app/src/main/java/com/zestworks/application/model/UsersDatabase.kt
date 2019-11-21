package com.zestworks.application.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zestworks.application.repository.UsersDAO

@Database(entities = [Users::class,UserInfo::class],version=1)
@TypeConverters(TypeConverter::class)
abstract class UsersDatabase:RoomDatabase(){
    abstract fun usersDAO() : UsersDAO
}
