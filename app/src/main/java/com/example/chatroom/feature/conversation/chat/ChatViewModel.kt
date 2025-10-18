package com.example.chatroom.feature.conversation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.feature.authentication.signin.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private lateinit var channelId: String

    init {
        listenMessages()
    }

    fun onTriggerEvent(event: ChatEvent){
        when(event){
            ChatEvent.SendMessage -> sendMessage()
            is ChatEvent.UpdateMessage -> updateMessage(event.message)
        }
    }

    private fun listenMessages() = with(_state){
        viewModelScope.launch {
            chatRepository
                .listenForMessages(channelId = channelId)
                .onSuccess { result ->
                    update { it.copy(messages = result) }
                }
                .onFailure {

                }
        }
    }

    private fun updateMessage(text: String) = with(_state){
        update { it.copy(currentMessage = text) }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatRepository
                .sendMessage(channelId = channelId, message = _state.value.currentMessage)

            _state.update { it.copy(currentMessage = "") }
        }
    }
}