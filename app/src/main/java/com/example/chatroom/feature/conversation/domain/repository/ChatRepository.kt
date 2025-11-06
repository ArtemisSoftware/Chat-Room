package com.example.chatroom.feature.conversation.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(channelId: String, text: String): Resource<Unit>
    suspend fun sendImage(channelId: String, image: String): Resource<Unit>
    fun listenForMessages(channelId: String): Flow<Resource<List<Message>>>
}