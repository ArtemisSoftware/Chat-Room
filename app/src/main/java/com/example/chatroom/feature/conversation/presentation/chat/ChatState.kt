package com.example.chatroom.feature.conversation.presentation.chat

import com.example.chatroom.feature.conversation.domain.models.Message
import com.example.chatroom.feature.conversation.domain.models.RegistrationData

data class ChatState(
    val channelName: String = "",
    val messages: List<Message> = emptyList(),
    val participants: List<String> = emptyList(),
    val text: String? = null,
    val imageUri: String? = null,
    val showMediaContentDialog: Boolean = false,
    val error: String? = null,
    val registerData: RegistrationData = RegistrationData(),
)
