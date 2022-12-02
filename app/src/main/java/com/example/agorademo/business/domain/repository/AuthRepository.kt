package com.example.agorademo.business.domain.repository

import com.example.agorademo.business.domain.model.UserDataResponse
import com.example.agorademo.business.domain.model.user_register.UserRegisterResponse
import com.example.agorademo.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getUserToken(userId: String, userPassword: String): Flow<Resource<UserDataResponse>>

    suspend fun registerUser(userId: String, userPassword: String) : Flow<Resource<UserRegisterResponse>>

}