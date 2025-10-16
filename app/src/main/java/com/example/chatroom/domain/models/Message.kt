package com.example.chatroom.domain.models

data class Message(
    val id: String = "",
    val senderId: String = "",
    val isMyMessage: Boolean = false,
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = "",
    val senderImage: String? = null,
    val imageUrl: String? = null
)
