package com.example.chatroom.firebase.data.models

data class MessageFb(
    val id: String,
    val senderId: String = "",
    val senderName: String = "",
    val senderImage: String? = null,
    val message: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val imageUrl: String? = null
)
