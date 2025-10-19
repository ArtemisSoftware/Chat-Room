package com.example.chatroom.feature.conversation.chat

import android.net.Uri

sealed interface ChatEvent {
    data object SendMessage: ChatEvent
    data class UpdateMessage(val message: String): ChatEvent
    data class ShowContentDialog(val show: Boolean): ChatEvent
    data class SendImage(val uri: Uri): ChatEvent
}