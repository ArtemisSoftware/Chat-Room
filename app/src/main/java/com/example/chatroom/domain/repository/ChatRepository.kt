package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(channelId: String, message: String, image: String? = null)
    fun listenForMessages(channelId: String): Flow<Resource<List<Message>>>
    suspend fun sendImage(channelId: String, uri: String)
}