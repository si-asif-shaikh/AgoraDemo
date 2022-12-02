package com.example.agorademo.business.domain.repository

import androidx.lifecycle.LiveData
import com.example.agorademo.business.domain.model.UserData
import com.example.agorademo.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

   suspend fun getUsersList() : Flow<Resource<List<UserData>>>

   suspend fun addUser(userData: UserData)
}