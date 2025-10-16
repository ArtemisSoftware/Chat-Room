package com.example.chatroom.feature.conversation.lounge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoungeViewModel constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoungeState())
    val state = _state.asStateFlow()

    init {
        getChannels()
    }

    fun onTriggerEvent(event: LoungeEvent){
        when(event){
            LoungeEvent.AddChanel -> addChannel()
            is LoungeEvent.UpdateChannelName -> updateChannelName(event.name)
        }
    }

    private fun getChannels() = with(_state) {
        viewModelScope.launch {
            channelRepository
                .getChannels()
                .onSuccess { result ->
                    update { it.copy(channels = result) }
                }
                .onFailure {  }
        }
    }

    private fun updateChannelName(name: String? = null) = with(_state){
        update { it.copy(newChannel = name) }
    }

    private fun addChannel() = with(_state.value){
        if(!newChannel.isNullOrEmpty()) {
            viewModelScope.launch {
                channelRepository.addChannel(name = newChannel)
                updateChannelName()
            }
        }
    }

}