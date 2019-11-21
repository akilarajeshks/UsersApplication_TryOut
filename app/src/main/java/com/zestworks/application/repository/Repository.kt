package com.zestworks.application.repository

import com.zestworks.application.viewmodel.ApplicationViewModel

interface Repository {
    suspend fun getUsersList():ApplicationViewModel.Status<Any>
    suspend fun getUser(userId:Int): ApplicationViewModel.Status<Any>
}