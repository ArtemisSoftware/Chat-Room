package com.example.chatroom.feature.conversation.presentation.chat.mapper

import androidx.compose.ui.graphics.Color
import com.example.chatroom.feature.conversation.domain.models.Message
import com.example.chatroom.ui.theme.MyBubbleColor
import com.example.chatroom.ui.theme.OtherBubbleColor
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

internal fun Message.toColor(): Color {
    return if (isMyMessage) MyBubbleColor else OtherBubbleColor
}

internal fun List<String>.toZegoUIKitUser(): List<ZegoUIKitUser>{
    return this.map {  ZegoUIKitUser(it, it) }
}