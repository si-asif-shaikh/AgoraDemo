package com.example.agorademo.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.agorademo.db.entities.UserDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserDataEntity): Long

    @Query("SELECT * FROM users")
     fun getAllUsers(): Flow<List<UserDataEntity>>


    @Delete
    suspend fun deleteUsers(user: UserDataEntity)

}