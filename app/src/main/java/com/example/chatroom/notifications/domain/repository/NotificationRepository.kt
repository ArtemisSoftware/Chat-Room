package com.example.chatroom.notifications.domain.repository

import com.example.chatroom.core.domain.Resource

interface NotificationRepository {
    suspend fun subscribeForNotification(channelId: String): Resource<Unit>

    fun postNotificationToUsers(
        channelId: String,
        channelName: String,
        senderName: String,
        messageContent: String
    )
}