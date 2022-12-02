package com.example.agorademo.di

import com.example.agorademo.business.domain.repository.AuthRepository
import com.example.agorademo.business.domain.repository.ChatRepository
import com.example.agorademo.business.domain.repository.UserRepository
import com.example.agorademo.data.repository.AuthRepositoryImpl
import com.example.agorademo.data.repository.ChatRepositoryImpl
import com.example.agorademo.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
   abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ) : ChatRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository
}