package com.example.chatroom.feature.conversation.domain.usecase

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.domain.repository.ImageRepository
import com.example.chatroom.domain.repository.NotificationRepository
import com.example.chatroom.feature.conversation.domain.models.RegistrationData
import com.example.chatroom.feature.conversation.domain.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Named

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    @Named("supabase_image_repo") private val imageRepository: ImageRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        channelId: String,
        text: String? = null,
        imageUri: String? = null,
        registrationData: RegistrationData
    ): Resource<Unit> {

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
                if(registrationData.isSubscribedForNotifications){
                    notificationRepository
                        .postNotificationToUsers(
                            channelId = channelId,
                            senderName = "",
                            messageContent = ""
                        )
                }
            }
        }

        return result
    }

    private suspend fun sendImage(channelId: String, imageUri: String): Resource<Unit> {
        val result = imageRepository.storeImage(imageUri)

        return when(result){
            is Resource.Failure -> {
                Resource.Failure(result.error)
            }
            is Resource.Success -> {
                 chatRepository
                    .sendImage(channelId = channelId, image = result.data)
            }
        }
    }

    private suspend fun sendText(channelId: String, text: String): Resource<Unit> {
        return chatRepository
            .sendMessage(channelId = channelId, text = text)
    }
}