package com.example.agorademo.presentation.home.roomChat

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.agorademo.ChatExtendMenuType
import com.example.agorademo.EaseChatFragmentHelper
import com.example.agorademo.R
import com.example.agorademo.data.source.Resource
import com.example.agorademo.databinding.ActivityRoomChatBinding
import com.example.agorademo.presentation.BaseVBActivity
import com.example.agorademo.presentation.MainViewModel
import com.example.agorademo.utils.Constants.DEFAULT_CHAT_ROOM
import com.example.agorademo.utils.LogUtils
import com.example.agorademo.utils.PermissionsManager
import dagger.hilt.android.AndroidEntryPoint
import io.agora.CallBack
import io.agora.ChatRoomChangeListener
import io.agora.ConnectionListener
import io.agora.ValueCallBack
import io.agora.chat.*
import io.agora.chat.uikit.EaseUIKit
import io.agora.chat.uikit.menu.EaseChatType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class RoomChat_A : BaseVBActivity<ActivityRoomChatBinding>(R.layout.activity_room_chat) {

    val chatRoomId = DEFAULT_CHAT_ROOM

    val mainViewModel: MainViewModel by viewModels()
    lateinit var tv_log : TextView

    @Inject
    lateinit var easeUIKit: EaseUIKit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }

    fun startChat() {

        val toChatUsername = chatRoomId
        // check username
        if (TextUtils.isEmpty(toChatUsername)) {
            LogUtils.showErrorToast(this, tv_log, getString(R.string.not_find_send_name))
            return
        }

        val easeChatFragmentHelper = EaseChatFragmentHelper(
            toChatUsername,
            EaseChatType.SINGLE_CHAT,
            onChatExtendMenuClic = { eventType ->
                when(eventType)
                {
                    ChatExtendMenuType.CAMERA -> {
                        return@EaseChatFragmentHelper !checkPermissions(
                            Manifest.permission.CAMERA,
                            111
                        )
                    }
                    ChatExtendMenuType.STORAGE ->{
                        return@EaseChatFragmentHelper !checkPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            112
                        )
                    }
                    ChatExtendMenuType.RECORD ->{
                        return@EaseChatFragmentHelper !checkPermissions(
                            Manifest.permission.RECORD_AUDIO,
                            113
                        )
                    }
                    else -> {
                        return@EaseChatFragmentHelper false
                    }
                }
            },
            onMessageSendCallBack = { logEvent ->
                LogUtils.showErrorLog(
                    tv_log,
                    logEvent
                )
            }
        )

        supportFragmentManager.beginTransaction().replace(R.id.fl_fragment,
            easeChatFragmentHelper.easeChatFragment).commit()
    }

    // Initializes the view.
    private fun initView() {

        tv_log = findViewById(R.id.tv_log);
        (findViewById<View>(R.id.tv_log) as TextView).movementMethod =
            ScrollingMovementMethod()
    }

    private fun initListener() {

        // Once the chat user successfully joins the chat room, all the other chat room members receive the onMemberJoined callback.
        lifecycleScope.launchWhenCreated {
           val result = mainViewModel.joinChatRoom(chatRoomId)
            when(result)
            {
                is Resource.Success -> {
                    binding.tvLog.text = result.data
                    startChat()
                }
                is Resource.Error -> {
                    binding.tvLog.text = result.message
                }
                is Resource.Loading ->{

                }
            }
        }

        mainViewModel.chatClient.chatroomManager().addChatRoomChangeListener(object  : ChatRoomChangeListener{
            override fun onChatRoomDestroyed(roomId: String?, roomName: String?) {
                showLog("Room Destroyed", false)
            }

            override fun onMemberJoined(roomId: String?, participant: String?) {
                showLog("Member Joined", false)
            }

            override fun onMemberExited(roomId: String?, roomName: String?, participant: String?) {
                showLog("Member Exited", false)
            }

            override fun onRemovedFromChatRoom(
                reason: Int,
                roomId: String?,
                roomName: String?,
                participant: String?
            ) {
                showLog("Member Removed", false)
            }

            override fun onMuteListAdded(
                chatRoomId: String?,
                mutes: MutableList<String>?,
                expireTime: Long
            ) {
                showLog(""+mutes?.get(0).toString(), false)
            }

            override fun onMuteListRemoved(chatRoomId: String?, mutes: MutableList<String>?) {
                showLog(""+mutes?.get(0).toString(), false)
            }

            override fun onWhiteListAdded(chatRoomId: String?, whitelist: MutableList<String>?) {
                showLog(""+whitelist?.get(0).toString(), false)
            }

            override fun onWhiteListRemoved(chatRoomId: String?, whitelist: MutableList<String>?) {
                showLog(""+whitelist?.get(0).toString(), false)
            }

            override fun onAllMemberMuteStateChanged(chatRoomId: String?, isMuted: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onAdminAdded(chatRoomId: String?, admin: String?) {
                showLog("Admin Added", false)
            }

            override fun onAdminRemoved(chatRoomId: String?, admin: String?) {
                showLog("Admin Removed", false)
            }

            override fun onOwnerChanged(chatRoomId: String?, newOwner: String?, oldOwner: String?) {
                showLog("Owner Changed", false)
            }

            override fun onAnnouncementChanged(chatRoomId: String?, announcement: String?) {
                showLog("Announcement Changed", false)
            }

        })
    }

    // Sends the first message.
    fun sendFirstMessage(view: View?) {
        val toSendName = chatRoomId
        val content =
            (findViewById<View>(R.id.et_msg_content) as EditText).text.toString().trim { it <= ' ' }
        // Creates a text message.
        val message = ChatMessage.createTextSendMessage(content, toSendName)
        message.chatType = ChatMessage.ChatType.ChatRoom
        // Sets the message callback before sending the message.
        message.setMessageStatusCallback(object : CallBack {
            override fun onSuccess() {
                showLog("Send message success!", true)
            }

            override fun onError(code: Int, error: String) {
                showLog(error, true)
            }
        })

        // Sends the message.
        ChatClient.getInstance().chatManager().sendMessage(message)
    }

    // Shows logs.
    private fun showLog(content: String, showToast: Boolean) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        runOnUiThread {
            if (showToast) {
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
            }
            val tv_log = findViewById<TextView>(R.id.tv_log)
            val preContent = tv_log.text.toString().trim { it <= ' ' }
            val builder = java.lang.StringBuilder()
            builder.append(
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).format(Date())
            )
                .append(" ").append(content).append("\n").append(preContent)
            tv_log.text = builder
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ChatClient.getInstance().chatroomManager().leaveChatRoom(chatRoomId);
    }

}