package com.example.chatroom.feature.conversation.chat

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.SendMessageUseCase
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.feature.conversation.navigation.ConversationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.orEmpty

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sendMessageUseCase: SendMessageUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private lateinit var channelId: String

    init {
        getData()
        listenMessages()
    }

    private fun getData() = with(_state){
        val channelName = savedStateHandle.get<String>(ConversationRoute.Chat::channelName.name)
        channelId = savedStateHandle.get<String>(ConversationRoute.Chat::channelId.name).orEmpty()

        update { it.copy(channelName = channelName.orEmpty()) }
    }

    fun onTriggerEvent(event: ChatEvent){
        when(event){
            ChatEvent.SendMessage -> sendMessage()
            is ChatEvent.UpdateText -> updateText(event.text)
            is ChatEvent.ShowContentDialog -> showContentDialog(event.show)
            is ChatEvent.UpdateImage -> updateImage(event.uri)
        }
    }

    private fun listenMessages() = with(_state){
        viewModelScope.launch {
            chatRepository
                .listenForMessages(channelId = channelId)
                .collect { result ->
                    result
                        .onSuccess { result ->
                            update { it.copy(messages = result) }
                        }
                        .onFailure {
                            //TODO: add error
                        }
                }
        }
    }

    private fun updateText(text: String) = with(_state){
        update { it.copy(text = text) }
    }

    private fun updateImage(uri: Uri) = with(_state){
        update { it.copy(imageUri = uri.toString()) }
    }

    private fun showContentDialog(show: Boolean) = with(_state){
        update { it.copy(showMediaContentDialog = show) }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            sendMessageUseCase
                .invoke(
                    channelId = channelId,
                    text = _state.value.text,
                    imageUri = _state.value.imageUri
                )

            _state.update { it.copy(text = null, imageUri = null) }
        }
    }
}