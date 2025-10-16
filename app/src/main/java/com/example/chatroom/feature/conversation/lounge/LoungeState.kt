package com.example.chatroom.feature.conversation.lounge

import com.example.chatroom.domain.models.Channel

data class LoungeState(
    val isLoading: Boolean = false,
    val showChannelDialog: Boolean = false,
    val channels: List<Channel> = emptyList(),
    val newChannel: String? = null
)
