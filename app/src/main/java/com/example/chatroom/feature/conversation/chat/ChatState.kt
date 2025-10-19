package com.example.chatroom.feature.conversation.chat

import com.example.chatroom.domain.models.Message

data class ChatState(
    val channelName: String = "",
    val messages: List<Message> = emptyList(),
    val currentMessage: String = "",
    val showMediaContentDialog: Boolean = false,
)
