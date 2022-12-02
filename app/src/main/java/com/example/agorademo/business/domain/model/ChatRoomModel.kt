package com.example.agorademo.business.domain.model

import io.agora.chat.ChatRoom

data class ChatRoomModel(
    val roomId: String,
    val roomName: String?
)

object ChatRoomMapper{
    fun ChatRoom.ChatRoomtoChatRoomModel() : ChatRoomModel{
        return ChatRoomModel(roomId = this.id, roomName = this.name)
    }
}
