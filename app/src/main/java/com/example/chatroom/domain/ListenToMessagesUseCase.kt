package com.example.chatroom.domain

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.domain.models.Message
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenToMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(channelId: String): Flow<Resource<List<Message>>> {

        val isSubscribed = notificationRepository
            .subscribeForNotification(channelId = channelId)

        return chatRepository.listenForMessages(channelId)
    }

}