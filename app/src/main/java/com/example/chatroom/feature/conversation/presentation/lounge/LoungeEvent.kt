package com.example.chatroom.feature.conversation.presentation.lounge

sealed interface LoungeEvent {
    data object AddChanel: LoungeEvent
    data class UpdateChannelName(val name: String): LoungeEvent
    data class ShowAddChannelDialog(val show: Boolean): LoungeEvent
}