package com.example.chatroom.feature.conversation.chat

import com.example.chatroom.domain.models.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val currentMessage: String = ""
)
