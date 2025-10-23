package com.example.chatroom.feature.conversation.chat.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chatroom.R
import com.example.chatroom.domain.models.Message
import com.example.chatroom.feature.conversation.chat.mapper.toColor
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun ChatBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val alignment = if (!message.isMyMessage) Alignment.CenterStart else Alignment.CenterEnd

    Box(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!message.isMyMessage) {
                Image(
                    painter = painterResource(id = R.drawable.ic_friend),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = message.toColor(),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                message.imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Text(
                        text = message.message,
                        color = Color.White,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubble_user_Preview() {
    ChatRoomTheme {
        ChatBubble(
            modifier = Modifier.fillMaxWidth(),
            message = Message(
                id = "2",
                senderName = "Milo",
                senderId = "Mi",
                isMyMessage = true,
                message = "THe 2 message",
                createdAt = 2L,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubble_other_user_Preview() {
    ChatRoomTheme {
        ChatBubble(
            modifier = Modifier.fillMaxWidth(),
            message = Message(
                id = "2",
                senderName = "Milo",
                senderId = "Mi",
                isMyMessage = false,
                message = "THe 2 message",
                createdAt = 2L,
            )
        )
    }
}