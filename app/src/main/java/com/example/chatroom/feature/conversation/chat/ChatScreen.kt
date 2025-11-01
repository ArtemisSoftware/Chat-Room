@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatroom.feature.conversation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatroom.core.presentation.composables.dialog.ContentSelectionDialog
import com.example.chatroom.domain.models.Message
import com.example.chatroom.feature.conversation.chat.composables.CallButton
import com.example.chatroom.feature.conversation.chat.composables.ChatBubble
import com.example.chatroom.feature.conversation.chat.composables.MessageSender
import com.example.chatroom.feature.conversation.chat.mapper.toZegoUIKitUser
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

@Composable
internal fun ChatScreen(
    navigateBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    ChatContent(
        state = state,
        onEvent = viewModel::onTriggerEvent,
        navigateBack = navigateBack
    )
}

@Composable
private fun ChatContent(
    state: ChatState,
    navigateBack: () -> Unit,
    onEvent: (ChatEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = state.channelName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = ""
                        )
                    }

                    CallButton(
                        isVideoCall = true,
                        onClick = { callButton->
                            val zegoUIKitUsers = state.participants.toZegoUIKitUser()
                            callButton.setInvitees(zegoUIKitUsers)
                        }
                    )
                },
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
                message = state.text.orEmpty(),
                onMessageUpdate = { onEvent(ChatEvent.UpdateText(it)) },
                onSendMessage = { onEvent(ChatEvent.SendMessage) },
                onAttachementClick = { onEvent(ChatEvent.ShowContentDialog(true)) }
            )
        }
    }

    if (state.showMediaContentDialog) {
        ContentSelectionDialog(
            onDismiss = { onEvent(ChatEvent.ShowContentDialog(false)) },
            onSuccessResult = {
                onEvent(ChatEvent.UpdateImage(it))
                onEvent(ChatEvent.SendMessage)
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
            onEvent = {},
            navigateBack = {}
        )
    }
}