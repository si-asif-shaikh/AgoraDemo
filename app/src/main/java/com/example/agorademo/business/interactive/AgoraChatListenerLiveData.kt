package com.example.agorademo.business.interactive

import androidx.lifecycle.LiveData
import io.agora.MessageListener
import io.agora.chat.ChatClient
import io.agora.chat.ChatMessage
import io.agora.chat.TextMessageBody

class AgoraChatListenerLiveData(val chatClient: ChatClient) : LiveData<String>() {
    override fun onActive() {
        super.onActive()
        chatClient.chatManager().addMessageListener(AgoraChatListener())
    }

    override fun onInactive() {
        super.onInactive()
        chatClient.chatManager().removeMessageListener(AgoraChatListener())
    }

    inner class AgoraChatListener : MessageListener{
        override fun onMessageReceived(messages: List<ChatMessage>) {
            for (message in messages) {
                val builder = StringBuilder()
                builder.append("Receive a ").append(message.type.name)
                    .append(" message from: ").append(message.from)
                if (message.type == ChatMessage.Type.TXT) {
                    builder.append(" content:")
                        .append((message.body as TextMessageBody).message)
                }

                value = builder.toString()
            }
        }
    }

}