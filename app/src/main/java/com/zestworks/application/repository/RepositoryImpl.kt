package com.zestworks.application.repository

import com.zestworks.application.viewmodel.ApplicationViewModel.Status
import com.zestworks.application.viewmodel.ApplicationViewModel.Status.Error
import com.zestworks.application.viewmodel.ApplicationViewModel.Status.Success
import com.zestworks.application.viewmodel.ApplicationViewModel.UserData.UserInfoData
import com.zestworks.application.viewmodel.ApplicationViewModel.UserData.UsersList

class RepositoryImpl(private val usersDAO : UsersDAO, private val networkService: NetworkService ):Repository {
    override suspend fun getUser(userId: Int):Status<Any> {
        try {
           val userInfoResponse = networkService.getUserInfo(userId)
            if (userInfoResponse.isSuccessful && userInfoResponse.body()!=null){
                    usersDAO.addUserInfo(userInfoResponse.body()!!)
                }else{
                    return Error(userInfoResponse.message())
                }
        }catch (exception:Exception){
            return Error(exception.message!!)
        }
        return Success(UserInfoData(usersDAO.getUserInfo(userId)))
    }

    override suspend fun getUsersList(): Status<Any> {
        try {
            val usersListResponse = networkService.getUsersList()
            if (usersListResponse.isSuccessful && usersListResponse.body()!=null) {
                    usersDAO.addAllUsers(usersListResponse.body()!!)
            } else {
                return Error(usersListResponse.message())
            }
            return Success(UsersList(usersDAO.getAllUsers()))
        }
        catch (exception : Exception){
            return Error(exception.message!!)
        }
    }
}