package com.example.chatroom.feature.conversation.lounge

sealed interface LoungeEvent {
    data object AddChanel: LoungeEvent
    data class UpdateChannelName(val name: String): LoungeEvent
}