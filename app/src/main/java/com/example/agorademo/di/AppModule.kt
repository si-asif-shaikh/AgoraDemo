package com.example.agorademo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.agora.chat.ChatClient
import io.agora.chat.ChatOptions
import io.agora.chat.uikit.EaseUIKit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChatOption() : ChatOptions{
        return ChatOptions().apply {
            appKey = "41839699#1037464"
            requireDeliveryAck = true
            autoLogin = false
        }
    }

    @Provides
    @Singleton
    fun provideChatClient(
        @ApplicationContext context: Context,
        chatOptions: ChatOptions
    ) : ChatClient {
        return ChatClient.getInstance().apply {
            init(context,chatOptions)
            setDebugMode(true)
        }
    }

    @Provides
    @Singleton
    fun provideEaseUIKit(
        @ApplicationContext context: Context,
        chatOptions: ChatOptions
    ) : EaseUIKit{
        return EaseUIKit.getInstance().apply {
            init(context,chatOptions)
        }
    }

}