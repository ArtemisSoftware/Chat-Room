package com.example.chatroom.feature.conversation.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.feature.conversation.domain.models.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun getChannels(): Flow<Resource<List<Channel>>>
    suspend fun addChannel(name: String): Resource<Unit>
    suspend fun registerUserIdToChannel(channelId: String): Resource<Unit>
    fun getAllUserEmails(channelId: String): Flow<Resource<List<String>>>
}