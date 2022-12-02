package com.example.agorademo.business.interactive

import androidx.lifecycle.LiveData
import io.agora.ConnectionListener
import io.agora.chat.ChatClient

class AgoraConnectionLiveData(val chatClient: ChatClient) : LiveData<String>() {

    override fun onActive() {
        super.onActive()
        chatClient.addConnectionListener(AgoraConnectionListener())
    }

    override fun onInactive() {
        super.onInactive()
        chatClient.removeConnectionListener(AgoraConnectionListener())
    }

    inner class AgoraConnectionListener : ConnectionListener{
        override fun onConnected() {
            postValue("onConnected")
        }

        override fun onDisconnected(error: Int) {
            postValue("onDisconnected: $error")
        }

        override fun onLogout(errorCode: Int) {
            postValue("User needs to log out: $errorCode")
            chatClient.logout(false, null)
        }

        // This callback occurs when the token expires. When the callback is triggered, the app client must get a new token from the app server and logs in to the app again.
        override fun onTokenExpired() {
            postValue("ConnectionListener onTokenExpired")
        }

        // This callback occurs when the token is about to expire.
        override fun onTokenWillExpire() {
            postValue("ConnectionListener onTokenWillExpire")
        }
    }
}