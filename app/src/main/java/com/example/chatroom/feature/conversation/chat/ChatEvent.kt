package com.example.chatroom.feature.conversation.chat

import android.net.Uri

sealed interface ChatEvent {
    data object SendMessage: ChatEvent
    data class UpdateText(val text: String): ChatEvent
    data class UpdateImage(val uri: Uri): ChatEvent
    data class ShowContentDialog(val show: Boolean): ChatEvent
}