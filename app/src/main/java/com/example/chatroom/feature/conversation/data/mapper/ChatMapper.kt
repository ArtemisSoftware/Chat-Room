package com.example.chatroom.feature.conversation.data.mapper

import com.example.chatroom.firebase.data.models.MessageFb
import com.example.chatroom.feature.conversation.domain.models.Message

internal fun MessageFb.toMessage(isMyMessage: Boolean, lastSender: String): Message {
    val sameSender = lastSender == senderName

    return if(imageUrl != null) {
        Message.Image(
            itemId = id,
            itemSenderId = senderId,
            itemIsMyMessage = isMyMessage,
            imageUrl = imageUrl,
            itemCreatedAt = createdAt,
            itemSenderName = senderName,
            itemSenderImage = senderImage,
            itemIsSameSender = sameSender
        )
    } else {
        Message.Text(
            itemId = id,
            itemSenderId = senderId,
            itemIsMyMessage = isMyMessage,
            text = message.orEmpty(),
            itemCreatedAt = createdAt,
            itemSenderName = senderName,
            itemSenderImage = senderImage,
            itemIsSameSender = sameSender
        )
    }
}