package com.example.chatroom.feature.authentication.data.di

import com.example.chatroom.data.repository.AuthenticationRepositoryImpl
import com.example.chatroom.data.repository.ChannelRepositoryImpl
import com.example.chatroom.data.repository.ChatRepositoryImpl
import com.example.chatroom.domain.repository.AuthenticationRepository
import com.example.chatroom.domain.repository.ChannelRepository
import com.example.chatroom.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChannelRepository(): ChannelRepository {
        return ChannelRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository {
        return ChatRepositoryImpl()
    }
}