package com.example.chatroom.feature.conversation.domain.models

data class Channel(
    val id: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
