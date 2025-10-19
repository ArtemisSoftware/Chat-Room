package com.example.chatroom.presentation.composables.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatroom.R
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
fun ContentSelectionDialog(
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onCameraSelected) {
                Text(text = stringResource(R.string.camera))
            }
        },
        dismissButton = {
            TextButton(onClick = onGallerySelected) {
                Text(text = stringResource(R.string.gallery))
            }
        },
        title = { Text(text = stringResource(R.string.select_your_source)) },
        text = { Text(text = stringResource(R.string.would_you_like_to_pick_an_image_from_the_gallery_or_use_the)) })
}

@Preview(showBackground = true)
@Composable
private fun MessageSenderPreview() {
    ChatRoomTheme {
        ContentSelectionDialog(
            onCameraSelected = {},
            onGallerySelected = {}
        )
    }
}