package com.example.chatroom.feature.conversation.domain.usecase

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.repository.NotificationRepository
import com.example.chatroom.feature.conversation.domain.models.RegistrationData
import com.example.chatroom.feature.conversation.domain.repository.ChannelRepository
import javax.inject.Inject

class RegisterToChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(channelId: String): RegistrationData {

        val isSubscribed = notificationRepository
            .subscribeForNotification(channelId = channelId)

        val isRegistered = channelRepository
            .registerUserIdToChannel(channelId = channelId)

        return if (isSubscribed is Resource.Success && isRegistered is Resource.Success) {
            RegistrationData(
                isSubscribedForNotifications = true,
                isRegisteredToChannel = true
            )
        } else {
            RegistrationData(
                isSubscribedForNotifications = false,
                isRegisteredToChannel = false
            )
        }
    }
}