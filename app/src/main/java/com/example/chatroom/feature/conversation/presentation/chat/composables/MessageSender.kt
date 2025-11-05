package com.example.chatroom.feature.conversation.presentation.chat.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatroom.R
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun MessageSender(
    message: String,
    onMessageUpdate:(String) -> Unit,
    onSendMessage:() -> Unit,
    onAttachementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hideKeyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            modifier = Modifier
                .rotate(45F),
            onClick = {
                hideKeyboardController?.hide()
                onMessageUpdate("")
                onAttachementClick()
            },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_attachement),
                    contentDescription = "send"
                )
            }
        )

        TextField(
            value = message,
            onValueChange = onMessageUpdate,
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = stringResource(R.string.type_a_message)) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { hideKeyboardController?.hide() }
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onSendMessage() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = ""
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageSenderPreview() {
    ChatRoomTheme {
        MessageSender(
            message = "The message",
            modifier = Modifier.fillMaxWidth(),
            onMessageUpdate = {},
            onSendMessage = {},
            onAttachementClick = {},
        )
    }
}