package com.example.chatroom.core.presentation.event

sealed interface UiEvent {
    data class NavigateWithRoute(val value: Any) : UiEvent
}