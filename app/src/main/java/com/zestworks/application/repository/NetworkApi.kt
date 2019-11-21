package com.zestworks.application.repository

import com.zestworks.application.model.UserInfo
import com.zestworks.application.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NetworkService{

    @GET("user")
    suspend fun getUsersList() : Response<Users>

    @GET("user/{userId}")
    suspend fun getUserInfo(@Path("userId")userId:Int) : Response<UserInfo>
}