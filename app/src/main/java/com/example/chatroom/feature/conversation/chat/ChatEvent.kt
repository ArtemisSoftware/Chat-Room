package com.example.chatroom.feature.conversation.chat

sealed interface ChatEvent {
    data object SendMessage: ChatEvent
    data class UpdateMessage(val message: String): ChatEvent
}