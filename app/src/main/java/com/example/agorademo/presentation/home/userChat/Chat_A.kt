package com.example.agorademo.presentation.home.userChat

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.agorademo.ChatExtendMenuType
import com.example.agorademo.EaseChatFragmentHelper
import com.example.agorademo.R
import com.example.agorademo.databinding.ActivityChatBinding
import com.example.agorademo.presentation.BaseVBActivity
import com.example.agorademo.presentation.MainViewModel
import com.example.agorademo.utils.LOGD
import com.example.agorademo.utils.PermissionsManager
import com.example.agorademo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import io.agora.chat.uikit.EaseUIKit
import io.agora.chat.uikit.menu.EaseChatType
import javax.inject.Inject

@AndroidEntryPoint
class Chat_A : BaseVBActivity<ActivityChatBinding>(R.layout.activity_chat) {

    val mainViewModel: MainViewModel by viewModels()


    @Inject
    lateinit var easeUIKit: EaseUIKit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        initChatView()
    }

    private fun initChatView() {
        mainViewModel.agoraConnectionListener().observe(this, Observer {  })

        mainViewModel.agoraMessageListener().observe(this, Observer {  })

        mainViewModel.login()

        val toChatUsername = intent.getStringExtra("toUserId")
        toChatUsername?.let {

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
                    showToast(logEvent)
                }
            )

            supportFragmentManager.beginTransaction().replace(R.id.fl_fragment, easeChatFragmentHelper.easeChatFragment).commit()

        }
    }

}