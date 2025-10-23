@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatroom.feature.conversation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatroom.domain.models.Message
import com.example.chatroom.feature.conversation.chat.composables.ChatBubble
import com.example.chatroom.feature.conversation.chat.composables.MessageSender
import com.example.chatroom.core.presentation.composables.dialog.ContentSelectionDialog
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    ChatContent(
        state = state,
        onEvent = viewModel::onTriggerEvent
    )
}

@Composable
private fun ChatContent(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(state.channelName, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            )
        }
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.messages) { message ->
                    ChatBubble(
                        message = message,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            MessageSender(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                    .background(Color.LightGray),
                message = state.currentMessage,
                onMessageUpdate = { onEvent(ChatEvent.UpdateMessage(it)) },
                onSendMessage = { onEvent(ChatEvent.SendMessage) },
                onAttachementClick = { onEvent(ChatEvent.ShowContentDialog(true)) }
            )
        }
    }

    if (state.showMediaContentDialog) {
        ContentSelectionDialog(
            onDismiss = { onEvent(ChatEvent.ShowContentDialog(false)) },
            onSuccessResult = {
                onEvent(ChatEvent.SendImage(it))
            }

        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ChatContentPreview() {
    ChatRoomTheme {
        ChatContent(
            state = ChatState(
                channelName = "The channel",
                messages = listOf(
                    Message(
                        id = "1",
                        senderName = "Camus",
                        senderId = "ca",
                        isMyMessage = false,
                        message = "THe 1 message",
                        createdAt = 1L,
                    ),
                    Message(
                        id = "2",
                        senderName = "Milo",
                        senderId = "Mi",
                        isMyMessage = true,
                        message = "THe 2 message",
                        createdAt = 2L,
                    ),
                    Message(
                        id = "3",
                        senderName = "Camus",
                        senderId = "ca",
                        isMyMessage = false,
                        message = "THe 3 message",
                        createdAt = 3L,
                    )
                )
            ),
            onEvent = {}
        )
    }
}