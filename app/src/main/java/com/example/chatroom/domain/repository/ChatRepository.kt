package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Message

interface ChatRepository {
    suspend fun sendMessage(channelId: String, message: String)
    suspend fun listenForMessages(channelId: String): Resource<List<Message>>
}