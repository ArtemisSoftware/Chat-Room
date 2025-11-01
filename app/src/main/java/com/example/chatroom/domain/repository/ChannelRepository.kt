package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun getChannels(): Flow<Resource<List<Channel>>>
    suspend fun addChannel(name: String)
    fun registerUserIdtoChannel(channelId: String)
    fun getAllUserEmails(channelId: String): Flow<Resource<List<String>>>
}