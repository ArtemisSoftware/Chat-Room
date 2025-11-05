package com.example.chatroom.feature.conversation.presentation.chat.mapper

import androidx.compose.ui.graphics.Color
import com.example.chatroom.domain.models.Message
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

internal fun Message.toColor(): Color {
    return if (isMyMessage) Color.Blue else Color.Green
}

internal fun List<String>.toZegoUIKitUser(): List<ZegoUIKitUser>{
    return this.map {  ZegoUIKitUser(it, it) }
}