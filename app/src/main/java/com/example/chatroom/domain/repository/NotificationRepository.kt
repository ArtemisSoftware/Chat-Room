package com.example.chatroom.domain.repository

interface NotificationRepository {
    fun subscribeForNotification(channelID: String)

    fun postNotificationToUsers(
        channelID: String,
        senderName: String,
        messageContent: String
    )
}