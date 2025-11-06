package com.example.chatroom.feature.conversation.presentation.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatroom.R
import com.example.chatroom.core.presentation.composables.icon.AcronymIcon
import com.example.chatroom.domain.models.Message
import com.example.chatroom.feature.conversation.presentation.chat.mapper.toColor
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun ChatBubble(
    message: Message,
    modifier: Modifier = Modifier
) {

    val alignment = if (!message.isMyMessage) Alignment.CenterStart else Alignment.CenterEnd

    Box(
        modifier = modifier
            .padding(vertical = 0.dp, horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!message.isMyMessage) {
                AcronymIcon(
                    name = message.senderName,
                    size = 24.dp
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
                when(message){
                    is Message.Image -> {
                        ImageMessage(message = message)
                    }
                    is Message.Text -> {
                        TextMessage(message = message)
                    }
                }
            }
        }
    }
}

@Composable
private fun TextMessage(
    message: Message.Text,
    modifier: Modifier = Modifier
) {
    Text(
        text = message.text,
        color = Color.White,
        modifier = modifier.padding(12.dp)
    )
}

@Composable
private fun ImageMessage(
    message: Message.Image,
    modifier: Modifier = Modifier
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(message.imageUrl)
        .placeholder(R.drawable.ic_launcher_foreground)
        .crossfade(true)
        .build()

    AsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(4.dp))
            .size(200.dp),
        contentScale = ContentScale.Crop
    )
}


@Preview(showBackground = true)
@Composable
private fun ChatBubble_user_Preview() {
    ChatRoomTheme {
        ChatBubble(
            modifier = Modifier.fillMaxWidth(),
            message = Message.Text(
                itemId = "1",
                itemSenderName = "Camus",
                itemSenderId = "ca",
                itemIsMyMessage = true,
                text = "THe 1 message",
                itemCreatedAt = 1L,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubble_user_image_Preview() {
    ChatRoomTheme {
        ChatBubble(
            modifier = Modifier.fillMaxWidth(),
            message = Message.Image(
                itemId = "1",
                itemSenderName = "Camus",
                itemSenderId = "ca",
                itemIsMyMessage = true,
                itemCreatedAt = 1L,
                imageUrl = "https://picsum.photos/200/300"
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubble_other_user_Preview() {
    ChatRoomTheme {
        ChatBubble(
            modifier = Modifier.fillMaxWidth(),
            message = Message.Text(
                itemId = "1",
                itemSenderName = "Milo",
                itemSenderId = "Mi",
                itemIsMyMessage = false,
                text = "THe 1 message",
                itemCreatedAt = 1L,
            )
        )
    }
}