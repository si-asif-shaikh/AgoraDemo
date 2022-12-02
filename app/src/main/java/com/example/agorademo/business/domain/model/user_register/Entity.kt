package com.example.agorademo.business.domain.model.user_register

data class Entity(
    val activated: Boolean,
    val created: Long,
    val modified: Long,
    val nickname: String,
    val type: String,
    val username: String,
    val uuid: String
)