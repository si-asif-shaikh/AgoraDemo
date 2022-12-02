package com.example.agorademo.data.repository

import androidx.lifecycle.LiveData
import com.example.agorademo.business.domain.model.UserData
import com.example.agorademo.business.domain.repository.UserRepository
import com.example.agorademo.business.interactive.DefaultUsers
import com.example.agorademo.data.mappers.toUserData
import com.example.agorademo.data.mappers.toUserDataEntity
import com.example.agorademo.data.source.Resource
import com.example.agorademo.db.AgoraDatabase
import com.example.agorademo.db.entities.UserDataEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val db: AgoraDatabase
) : UserRepository {

    private val userDao = db.userDao

    fun getUserListFromDatabase() : Flow<List<UserDataEntity>> {
        return userDao.getAllUsers()
    }

    override suspend fun getUsersList(): Flow<Resource<List<UserData>>>
    {
        return flow {
            emit(Resource.Loading())
            getUserListFromDatabase().collect{
                val userList = it.map { it.toUserData() }
                emit(Resource.Success(userList))

                if(userList.isEmpty())
                {
                    DefaultUsers.get().forEach {
                        addUser(it)
                    }
                }

            }
        }
    }

    override suspend fun addUser(userData: UserData) {
        userDao.upsert(userData.toUserDataEntity())
    }
}