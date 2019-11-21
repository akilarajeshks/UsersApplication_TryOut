package com.zestworks.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zestworks.application.model.UserInfo
import com.zestworks.application.model.Users
import com.zestworks.application.repository.Repository
import kotlinx.coroutines.launch

class ApplicationViewModel(private val repository: Repository) : ViewModel() {

    sealed class UserData{
        data class UsersList(val users: Users):UserData()
        data class UserInfoData(val user : UserInfo):UserData()
    }

    sealed class Status<out T:Any>{
        object Loading:Status<Nothing>()
        data class Success(val users:UserData):Status<UserData>()
        data class Error(val reason:String):Status<String>()
    }



    private val currentState = MutableLiveData<Status<Any>>().apply { postValue(Status.Loading)}
    val _currentState: LiveData<Status<Any>> = currentState

    fun onUiLoad() {
        viewModelScope.launch {
            currentState.value = (Status.Loading)
            currentState.value = (repository.getUsersList())
        }
    }

    fun getUserInfo(userId:Int){
         viewModelScope.launch {
             currentState.value = (Status.Loading)
             currentState.value = (repository.getUser(userId))
        }
    }


}