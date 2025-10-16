package com.example.chatroom.feature.conversation.chat.mapper

import androidx.compose.ui.graphics.Color
import com.example.chatroom.domain.models.Message

internal fun Message.toColor(): Color {
    return if (isMyMessage) Color.Blue else Color.Green
}