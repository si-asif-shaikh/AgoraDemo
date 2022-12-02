package com.example.agorademo.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agorademo.business.domain.model.user_register.UserRegisterResponse
import com.example.agorademo.business.domain.repository.AuthRepository
import com.example.agorademo.common.Event
import com.example.agorademo.data.source.Resource
import com.example.agorademo.utils.SessionStoreManager
import com.example.agorademo.utils.TextConstants.USER_LOGIN_SUCCESSFULLY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val authRepository: AuthRepository,
   private val sessionStoreManager: SessionStoreManager
): ViewModel() {

    private val _successLogin: MutableLiveData<Event<String>> = MutableLiveData()
    val successLogin: LiveData<Event<String>> = _successLogin

    private val _failedLoginMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val failedLoginMessage: LiveData<Event<String>> = _failedLoginMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun registerUser(userId: String,userPassword: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = authRepository.registerUser(userId,userPassword).first()
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    data?.let {
                        handlerUserRegisterResponse(result.data)
                    }
                }
                is Resource.Error -> {
                    _failedLoginMessage.value = Event(""+result.message)
                }
                is Resource.Loading -> {

                }
            }
        }

    }

    private suspend fun handlerUserRegisterResponse(data: UserRegisterResponse) {
        val userEntity = data.entities.get(0)
        saveUserInformations(userEntity.uuid,userEntity.username,userEntity.nickname)
    }

    fun getUserInformation(){
        viewModelScope.launch {

        }
    }

    private suspend fun saveUserInformations(userId: String,userName:String,nickName:String){
        sessionStoreManager.setUserToken(userId)
        sessionStoreManager.setUserName(userName)
        sessionStoreManager.setNickName(nickName)
    }

}