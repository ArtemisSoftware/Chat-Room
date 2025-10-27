package com.example.chatroom.feature.authentication.data.di

import com.example.chatroom.data.repository.AuthenticationRepositoryImpl
import com.example.chatroom.data.repository.ChannelRepositoryImpl
import com.example.chatroom.data.repository.ChatRepositoryImpl
import com.example.chatroom.domain.repository.AuthenticationRepository
import com.example.chatroom.domain.repository.ChannelRepository
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.domain.repository.ImageRepository
import com.example.chatroom.data.repository.ImageRepositoryImpl
import com.example.chatroom.data.repository.NotificationRepositoryImpl
import com.example.chatroom.domain.repository.NotificationRepository
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

    @Provides
    @Singleton
    fun provideImageRepository(): ImageRepository {
        return ImageRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepositoryImpl()
    }
}