package com.example.agorademo.business.domain.model.user_register

data class UserRegisterResponse(
    val action: String,
    val application: String,
    val applicationName: String,
    val `data`: List<Any>,
    val duration: Int,
    val entities: List<Entity>,
    val organization: String,
    val path: String,
    val timestamp: Long,
    val uri: String
)