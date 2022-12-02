package com.example.agorademo.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.agorademo.business.domain.repository.ChatRepository
import com.example.agorademo.R
import com.example.agorademo.business.domain.model.UserDataResponse
import com.example.agorademo.business.interactive.AgoraChatListenerLiveData
import com.example.agorademo.business.interactive.AgoraConnectionLiveData
import com.example.agorademo.data.source.Resource
import com.example.agorademo.data.source.network.AuthApis
import dagger.hilt.android.qualifiers.ApplicationContext
import io.agora.CallBack
import io.agora.ValueCallBack
import io.agora.chat.ChatClient
import io.agora.chat.ChatMessage
import io.agora.chat.ChatRoom
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val chatClient: ChatClient,
    ) : ChatRepository {


    /**
     * Listen Connection
     * */
    override fun agoraConnectionListener(): LiveData<String> {
        return AgoraConnectionLiveData(chatClient)
    }

    /**
     * Sign in
     */
    override suspend fun agoraLogin(userId:String,user_token:String?) : String {
        return suspendCoroutine { cont ->
            chatClient.loginWithAgoraToken(userId, user_token, object : CallBack {
                override fun onSuccess() {
                    cont.resume(context.getString(R.string.sign_in_success))
                }

                override fun onError(code: Int, error: String) {
                    cont.resume("Login failed! code: $code error: $error")
                }

                override fun onProgress(progress: Int, status: String) {}
            })
        }
    }

    override suspend fun agoraSingout(): String {
        return suspendCoroutine { continuation ->
            if (chatClient.isLoggedInBefore) {
                chatClient.logout(true, object : CallBack {
                    override fun onSuccess() {
                        continuation.resume(context.getString(R.string.sign_out_success))
                    }

                    override fun onError(code: Int, error: String) {
                        continuation.resume("Sign out failed! code: $code error: $error")
                    }

                    override fun onProgress(progress: Int, status: String) {}
                })
            }
        }
    }

    override suspend fun sendMessage(toSendName: String, content: String): String {
        return suspendCoroutine { continuation ->
            toSendName.trim { it <= ' ' }
            content.trim { it <= ' ' }

            // Creates a text message.
            val message = ChatMessage.createTextSendMessage(content, toSendName)
            // Sets the message callback before sending the message.
            message.setMessageStatusCallback(object : CallBack {
                override fun onSuccess() {
                    continuation.resume("Send message success!")
                }

                override fun onError(code: Int, error: String) {
                    continuation.resume(error)
                }
            })

            chatClient.chatManager().sendMessage(message)
        }
    }

    /**
     * Listen Message
     * */
    override fun chatListener(): LiveData<String> {
        return AgoraChatListenerLiveData(chatClient)
    }

    override suspend fun agoraJoinRoom(chatRoomId:String): Resource<String> {
        return suspendCoroutine { continuation ->
            chatClient.chatroomManager()
                .joinChatRoom(chatRoomId, object : ValueCallBack<ChatRoom?> {
                    override fun onSuccess(value: ChatRoom?) {
                        continuation.resume(Resource.Success(value.toString()))
                    }
                    override fun onError(error: Int, errorMsg: String) {
                        continuation.resume(Resource.Error(errorMsg.toString()))
                    }
                })
        }
    }
}