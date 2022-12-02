package com.example.agorademo.presentation.home.roomChat

import androidx.lifecycle.ViewModel
import com.example.agorademo.business.domain.model.ChatRoomMapper.ChatRoomtoChatRoomModel
import com.example.agorademo.business.domain.model.ChatRoomModel
import com.example.agorademo.utils.Constants
import com.example.agorademo.utils.Constants.DEFAULT_CHAT_ROOM
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agora.chat.ChatClient
import io.agora.chat.ChatRoom
import io.agora.chat.PageResult
import io.agora.chat.adapter.EMAChatRoom
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
   val chatClient: ChatClient
) : ViewModel() {

    private var packageNumber= 1;

    fun getChatRoomList(): List<ChatRoomModel>{
        return try {
            val list= chatClient.chatroomManager()
                .fetchPublicChatRoomsFromServer(packageNumber, 100)
            list.data.map { it.ChatRoomtoChatRoomModel() }
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf(ChatRoomModel(Constants.DEFAULT_CHAT_ROOM,"Default"))
        }
    }

    fun getRoomDetails(chatRoomId:String): ChatRoom {
        return try {
            return chatClient.chatroomManager().fetchChatRoomFromServer(chatRoomId)
        } catch (e: Exception) {
            e.printStackTrace()
            return ChatRoom()
        }
    }

}