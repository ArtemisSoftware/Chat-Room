package com.example.chatroom.core.presentation.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class UiEventViewModel : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch { _uiEvent.send(uiEvent) }

    }
}