package com.example.chatroom.feature.conversation.navigation

import kotlinx.serialization.Serializable

sealed class ConversationRoute {

    @Serializable
    object Lounge

    @Serializable
    data class Chat(val channelId: String)
}
