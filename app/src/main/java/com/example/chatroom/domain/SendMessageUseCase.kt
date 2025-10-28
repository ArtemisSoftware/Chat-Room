package com.example.chatroom.domain

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.domain.repository.ImageRepository
import com.example.chatroom.domain.repository.NotificationRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val imageRepository: ImageRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        channelId: String,
        text: String? = null,
        imageUri: String? = null
    ): Resource<Unit>{

        val result = when{
            imageUri != null -> {
                sendImage(channelId = channelId, imageUri = imageUri)
            }
            text != null -> {
                sendText(channelId = channelId, text = text)
            }

            else -> {
                Resource.Failure(DataError.ChatError.MessageNotSent)
            }
        }

        when(result){
            is Resource.Failure -> Unit
            is Resource.Success -> {
                notificationRepository
                    .postNotificationToUsers(
                        channelId = channelId,
                        senderName = "",
                        messageContent = ""
                    )
            }
        }

        return result
    }

    private suspend fun sendImage(channelId: String, imageUri: String): Resource<Unit>{
        val result = imageRepository.storeImage(imageUri)

        when(result){
            is Resource.Failure -> {

            }
            is Resource.Success -> {
                chatRepository
                    .sendImage(channelId = channelId, image = result.data)
            }
        }

        //TODO: provisorio

        return Resource.Success(Unit)
    }

    private suspend fun sendText(channelId: String, text: String): Resource<Unit>{
        chatRepository
            .sendMessage(channelId = channelId, text = text)

        //TODO: provisorio

        return Resource.Success(Unit)
    }
}