package com.example.agorademo.presentation

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.*
import com.example.agorademo.business.domain.repository.ChatRepository
import com.example.agorademo.utils.UserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.agora.chat.ChatClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val chatClient: ChatClient,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

    var userId = UserInformation.USER2.userID
    val userPassword = UserInformation.USER2.userPassword
    var userToken = UserInformation.USER2.userToken

    private val _toolbarHeading = MutableLiveData<String>()
    val toolbarHeading get() = _toolbarHeading

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var preContent: String = ""


    fun setTitle(title: String) {
        _toolbarHeading.value = title
    }

    fun getLogMessage() = message

    init {

//        viewModelScope.launch {
//            sessionStoreManager.getUserId().first()?.let {
//                userId = it
//            }
//
//            sessionStoreManager.getUserToken().first()?.let {
//                userToken = it
//            }
//        }

        login()

        initListener()
    }

    fun showLog(content: String, showToast: Boolean) : String {
        if (TextUtils.isEmpty(content)) {
            return ""
        }
        if (showToast) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }
        val builder = java.lang.StringBuilder()
        builder.append(
            SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(Date())
        ).append(" ").append(content).append("\n").append(preContent)
        preContent = builder.toString()
        return builder.toString()
    }

    /**
     * Agora Listener
     */
    private fun initListener() {
        // Adds message event callbacks.

        // Adds connection event callbacks.
    }

    fun agoraMessageListener() =
        chatRepository.chatListener().map {
            emitMessage(it)
            it
        }

     fun agoraConnectionListener() =
        chatRepository.agoraConnectionListener().map {
            emitMessage(it)
            it
        }

    /**
     * Sign in
     */
    fun login() = viewModelScope.launch {
        emitMessage(chatRepository.agoraLogin(userId,userToken))
    }


    /**
     * Sign out
     */
    fun signOut() = viewModelScope.launch {
        emitMessage(chatRepository.agoraSingout())
    }

    /**
     * Send Message
     * */
    fun sendMessage(toUserId:String,content:String)
     = viewModelScope.launch {
        emitMessage(chatRepository.sendMessage(toUserId,content))
    }

    fun isClientLogin() = chatClient.isLoggedIn


    suspend fun joinChatRoom(chatRoomId:String) = chatRepository.agoraJoinRoom(chatRoomId)

}