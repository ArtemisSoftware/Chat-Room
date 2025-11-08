package com.example.chatroom.feature.conversation.domain.models

sealed class Message(
    val id: String,
    val senderId: String,
    val isMyMessage: Boolean = false,
    val isSameSender: Boolean = false,
    val createdAt: Long,
    val senderName: String,
    val senderImage: String? = null,
){
    data class Text(
        val text: String,
        val itemId: String,
        val itemIsMyMessage: Boolean = false,
        val itemIsSameSender: Boolean = false,
        val itemCreatedAt: Long,
        val itemSenderId: String,
        val itemSenderName: String,
        val itemSenderImage: String? = null,
    ): Message(
        id = itemId,
        isMyMessage = itemIsMyMessage,
        senderId = itemSenderId,
        createdAt = itemCreatedAt,
        senderName = itemSenderName,
        senderImage = itemSenderImage,
        isSameSender = itemIsSameSender
    )

    data class Image(
        val imageUrl: String,
        val itemId: String,
        val itemIsMyMessage: Boolean = false,
        val itemIsSameSender: Boolean = false,
        val itemCreatedAt: Long,
        val itemSenderId: String,
        val itemSenderName: String,
        val itemSenderImage: String? = null,
    ): Message(
        id = itemId,
        isMyMessage = itemIsMyMessage,
        senderId = itemSenderId,
        createdAt = itemCreatedAt,
        senderName = itemSenderName,
        senderImage = itemSenderImage,
        isSameSender = itemIsSameSender
    )
}

