package com.example.chatroom.feature.conversation.lounge.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.core.presentation.composables.icon.AcronymIcon
import com.example.chatroom.feature.conversation.chat.composables.CallButton
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton

@Composable
internal fun ChannelCard(
    channel: Channel,
    modifier: Modifier,
    shouldShowCallButtons: Boolean = false,
    onClick: (String, String) -> Unit,
    onCall: (ZegoSendCallInvitationButton) -> Unit
) {

    Card(
        modifier = modifier,
        onClick = { onClick(channel.id, channel.name) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AcronymIcon(name = channel.name)

                Text(
                    text = channel.name,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            }
            if (shouldShowCallButtons) {
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    CallButton(isVideoCall = true, onCall)
                    CallButton(isVideoCall = false, onCall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoungeContentPreview() {
    ChatRoomTheme {
        ChannelCard(
            channel = Channel(
                id = "ID",
                name = "Best one"
            ),
            modifier = Modifier.fillMaxWidth(),
            shouldShowCallButtons = false,
            onClick = {_, _ ->},
            onCall = {_ ->},
        )
    }
}