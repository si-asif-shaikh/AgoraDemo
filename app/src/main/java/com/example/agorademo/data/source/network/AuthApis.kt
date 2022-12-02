package com.example.agorademo.data.source.network

import com.example.agorademo.business.domain.model.UserDataResponse
import com.example.agorademo.business.domain.model.user_register.UserRegisterResponse
import com.example.agorademo.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApis {

    @Headers("Content-Type: application/json")
    @POST(Constants.user_login)
    suspend fun userToken(
        @Body params: String
    ): Response<UserDataResponse>

    @GET
    suspend fun userData(
        @Url
        url: String
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST(Constants.user_register)
    suspend fun registerUser(
        @Body params: String
    ): Response<UserRegisterResponse>

}