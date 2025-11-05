package com.example.chatroom.feature.conversation.domain.models

data class RegistrationData(
    val isSubscribedForNotifications: Boolean = false,
    val isRegisteredToChannel: Boolean = false
)
