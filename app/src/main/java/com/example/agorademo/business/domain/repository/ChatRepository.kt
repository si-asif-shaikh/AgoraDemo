package com.example.agorademo.business.domain.repository

import androidx.lifecycle.LiveData
import com.example.agorademo.business.domain.model.UserDataResponse
import com.example.agorademo.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun agoraConnectionListener(): LiveData<String>

    suspend fun agoraLogin(userId: String, user_token: String?): String

    suspend fun agoraSingout(): String

    //    fun sendMessage(toSendName:String,content:String) : LiveData<String>
//
    suspend fun sendMessage(toSendName: String, content: String): String

    fun chatListener(): LiveData<String>

    suspend fun agoraJoinRoom(chatRoomId:String) : Resource<String>
}