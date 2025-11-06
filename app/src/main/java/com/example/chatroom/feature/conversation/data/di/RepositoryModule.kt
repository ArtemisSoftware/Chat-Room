package com.example.chatroom.feature.conversation.data.di

import com.example.chatroom.feature.conversation.data.repository.ChannelRepositoryImpl
import com.example.chatroom.feature.conversation.data.repository.ChatRepositoryImpl
import com.example.chatroom.feature.conversation.domain.repository.ChannelRepository
import com.example.chatroom.feature.conversation.domain.repository.ChatRepository
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChannelRepository(
        @Named("channel_db_ref") channelDbRef: DatabaseReference,
        @Named("lounge_db_ref") loungeDbRef: DatabaseReference,
    ): ChannelRepository {
        return ChannelRepositoryImpl(
            loungeDbRef = loungeDbRef,
            channelDbRef = channelDbRef
        )
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        @Named("messages_db_ref") messagesDbRef: DatabaseReference,
    ): ChatRepository {
        return ChatRepositoryImpl(messagesDbRef = messagesDbRef)
    }
}