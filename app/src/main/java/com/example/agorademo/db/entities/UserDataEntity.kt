package com.example.agorademo.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDataEntity(
    @PrimaryKey val user_id:String,
    val user_name: String?
)