package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Channel

interface ChannelRepository {
    suspend fun getChannels(): Resource<List<Channel>>
    suspend fun addChannel(name: String)
}