package com.example.chatroom.data.mapper

import com.example.chatroom.data.firebase.models.MessageFb
import com.example.chatroom.domain.models.Message

internal fun MessageFb.toMessage(isMyMessage: Boolean): Message {
    return Message(
        id = id,
        senderId = senderId,
        isMyMessage = isMyMessage,
        message = message,
        createdAt = createdAt,
        senderName = senderName,
        senderImage = senderImage,
        imageUrl = imageUrl
    )
}