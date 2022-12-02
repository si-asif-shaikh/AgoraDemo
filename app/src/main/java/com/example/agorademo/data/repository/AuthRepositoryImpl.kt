package com.example.agorademo.data.repository

import android.util.Log
import com.example.agorademo.business.domain.model.ErrorResponse
import com.example.agorademo.business.domain.model.UserDataResponse
import com.example.agorademo.business.domain.model.user_register.UserRegisterResponse
import com.example.agorademo.business.domain.repository.AuthRepository
import com.example.agorademo.data.source.Resource
import com.example.agorademo.data.source.network.AuthApis
import com.example.agorademo.utils.TextConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val provideAuthApi: AuthApis,
    ) : AuthRepository {

    val TAG = "AuthRepositoryImpl"

    override suspend fun getUserToken(userId: String, userPassword: String): Flow<Resource<UserDataResponse>>
    {
        return flow {
            val jsonBody = JSONObject()
            jsonBody.put("userAccount",userId)
            jsonBody.put("userPassword",userPassword)
            val response = provideAuthApi.userToken(jsonBody.toString())
            if(response.isSuccessful)
            {
                emit(Resource.Success(response.body()))
            }
            else{
                val type = object : TypeToken<ErrorResponse>() {}.type
                var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                errorResponse?.let { errorResponse ->
                    val code = errorResponse.code
                    emit(Resource.Error(errorResponse.message))
                }

                emit(Resource.Error("Couldn't Login"))
            }
        }
    }

    override suspend fun registerUser(
        userId: String,
        userPassword: String
    ): Flow<Resource<UserRegisterResponse>> {
        return flow {
            try {
                val jsonBody = JSONObject()
                jsonBody.put("userAccount",userId)
                jsonBody.put("userPassword",userPassword)
                val response = provideAuthApi.registerUser(jsonBody.toString())
                if(response.isSuccessful)
                {
                    emit(Resource.Success(response.body()))
                }
                else{
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    errorResponse?.let { errorResponse ->
                        emit(Resource.Error(response.message()))
                    }

                    emit(Resource.Error("Couldn't Register"))
                }
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
        }
    }

}