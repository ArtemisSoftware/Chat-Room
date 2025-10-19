package com.example.chatroom.feature.conversation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatroom.domain.models.Message
import com.example.chatroom.feature.conversation.chat.composables.ChatBubble
import com.example.chatroom.feature.conversation.chat.composables.MessageSender
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun ChatScreen() {

}

@Composable
private fun ChatContent(
    state: ChatState,
    onEvent: (ChatEvent) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {

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
                onAttachementClick = {}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ChatContentPreview() {
    ChatRoomTheme {
        ChatContent(
            state = ChatState(
                messages = listOf(
                    Message(
                        isMyMessage = false,
                        message = "THe 1 message"
                    ),
                    Message(
                        isMyMessage = true,
                        message = "THe 2 message"
                    ),
                    Message(
                        isMyMessage = false,
                        message = "THe 3 message"
                    )
                )
            ),
            onEvent = {}
        )
    }
}