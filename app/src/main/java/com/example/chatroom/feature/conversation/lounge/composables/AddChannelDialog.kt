package com.example.chatroom.feature.conversation.lounge.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
internal fun AddChannelDialog(
    channelName: String,
    updateChannelName: (String) -> Unit,
    onAddChannel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Channel")

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = channelName,
            onValueChange = updateChannelName,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Channel Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(12.dp))

        Button(
            onClick = onAddChannel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddChannelDialogPreview() {
    ChatRoomTheme {
        AddChannelDialog(
            channelName = "Super channel",
            updateChannelName = {},
            onAddChannel = {},
        )
    }
}