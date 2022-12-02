package com.example.agorademo.business.domain.model

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("agoraUid")
    val agoraUid: String?,
    @SerializedName("chatUserName")
    val chatUserName: String?,
    @SerializedName("code")
    val code: String,
    @SerializedName("expireTimestamp")
    val expireTimestamp: Long?,
    @SerializedName("errorInfo")
    val errorInfo: String?
)

data class ErrorResponse(
    @SerializedName("error")
    val code: String?,
    @SerializedName("error_description")
    val message: String?,
)