package com.example.agorademo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agorademo.db.daos.UserDao
import com.example.agorademo.db.entities.UserDataEntity
import javax.inject.Singleton

@Database(
    entities = [UserDataEntity::class],
    version = 1
)
abstract class AgoraDatabase : RoomDatabase() {

    abstract val userDao: UserDao

}