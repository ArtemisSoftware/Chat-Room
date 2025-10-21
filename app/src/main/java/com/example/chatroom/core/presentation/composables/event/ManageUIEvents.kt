package com.example.chatroom.core.presentation.composables.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.chatroom.core.presentation.event.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    onNavigateWithRoute: (UiEvent.NavigateWithRoute) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateWithRoute -> { onNavigateWithRoute(event) }
            }
        }
    }
}