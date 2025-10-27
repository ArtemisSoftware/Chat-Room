package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource

interface NotificationRepository {
    suspend fun subscribeForNotification(channelId: String): Resource<Unit>

    fun postNotificationToUsers(
        channelId: String,
        senderName: String,
        messageContent: String
    )
}