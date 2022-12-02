package com.example.agorademo.data.mappers

import com.example.agorademo.business.domain.model.UserData
import com.example.agorademo.db.entities.UserDataEntity

fun UserDataEntity.toUserData() : UserData{
    return UserData(user_id,user_name)
}

fun UserData.toUserDataEntity(): UserDataEntity{
    return UserDataEntity(user_id,user_name)
}