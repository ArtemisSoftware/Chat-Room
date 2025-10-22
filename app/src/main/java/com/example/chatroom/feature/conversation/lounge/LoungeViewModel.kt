package com.example.chatroom.feature.conversation.lounge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.domain.repository.ChannelRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.get

@HiltViewModel
internal class LoungeViewModel @Inject constructor(
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
            is LoungeEvent.ShowAddChannelDialog -> showAddChannelDialog(event.show)
        }
    }

    private fun getChannels() = with(_state) {
        viewModelScope.launch {
            channelRepository
                .getChannels()
                .collect { result ->
                    result
                        .onSuccess { result ->
                            update { it.copy(channels = result) }
                        }
                            .onFailure {  }
                        }
        }
    }

    private fun updateChannelName(name: String? = null) = with(_state){
        update { it.copy(newChannel = name) }
    }

    private fun showAddChannelDialog(show: Boolean) = with(_state){
        update { it.copy(showChannelDialog = show) }
    }

    private fun addChannel() = with(_state.value){
        if(!newChannel.isNullOrEmpty()) {
            viewModelScope.launch {
                channelRepository.addChannel(name = newChannel)
                _state.update { it.copy(newChannel = null, showChannelDialog = false) }
            }
        }
    }

}