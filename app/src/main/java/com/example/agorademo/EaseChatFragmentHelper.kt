package com.example.agorademo

import android.Manifest
import com.example.agorademo.utils.LogUtils
import io.agora.chat.ChatMessage
import io.agora.chat.uikit.R
import io.agora.chat.uikit.chat.EaseChatFragment
import io.agora.chat.uikit.chat.interfaces.OnChatExtendMenuItemClickListener
import io.agora.chat.uikit.chat.interfaces.OnMessageSendCallBack
import io.agora.chat.uikit.menu.EaseChatType

class EaseChatFragmentHelper(
    val toChatUsername: String,
    val eachType: EaseChatType,
    val onChatExtendMenuClic : (itemType:ChatExtendMenuType) -> Boolean,
    val onMessageSendCallBack: (logMessage:String) -> Unit
) {

    val easeChatFragment: EaseChatFragment by lazy {
        EaseChatFragment.Builder(toChatUsername, eachType)
            .useHeader(false)
            .setOnChatExtendMenuItemClickListener(OnChatExtendMenuItemClickListener { view, itemId ->
                if (itemId == R.id.extend_item_take_picture) {
                    return@OnChatExtendMenuItemClickListener onChatExtendMenuClic(ChatExtendMenuType.CAMERA)
                }
                else if (itemId == R.id.extend_item_picture
                    || itemId == R.id.extend_item_file || itemId == R.id.extend_item_video) {
                    return@OnChatExtendMenuItemClickListener onChatExtendMenuClic(ChatExtendMenuType.STORAGE)
                }
                false
            })
            .setOnChatRecordTouchListener { v, event ->
                !onChatExtendMenuClic(ChatExtendMenuType.RECORD)
            }
            .setOnMessageSendCallBack(object : OnMessageSendCallBack {
                override fun onSuccess(message: ChatMessage) {
                    onMessageSendCallBack.invoke("Send success: message type: " + message.type.name)
                }

                override fun onError(code: Int, errorMsg: String) {
                    onMessageSendCallBack.invoke("Send failed: error code: $code errorMsg: $errorMsg")
                }
            })
            .build()
    }

}

enum class ChatExtendMenuType{
    CAMERA,
    STORAGE,
    RECORD
}