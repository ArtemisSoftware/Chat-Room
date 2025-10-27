package com.example.chatroom.core.presentation.composables.dialog

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatroom.R
import com.example.chatroom.core.presentation.util.extensions.createImageUri
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
fun ContentSelectionDialog(
    onDismiss: () -> Unit,
    onSuccessResult: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    var cameraImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraImageUri?.let { onSuccessResult(it) }
        }
        onDismiss()
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            context.contentResolver.takePersistableUriPermission(
                selectedUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            onSuccessResult(selectedUri)
        }
        onDismiss()
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncher.launch(cameraImageUri)
            }
        }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    cameraImageUri = context.createImageUri()
                    if (context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        cameraImageLauncher.launch(cameraImageUri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                content = {
                    Text(text = stringResource(R.string.camera))
                }
            ) 
        },
        dismissButton = {
            TextButton(
                onClick = {
                    imageLauncher.launch("image/*")
                },
                content = {
                    Text(text = stringResource(R.string.gallery))
                }
            ) 
        },
        title = {
            Text(text = stringResource(R.string.select_your_source))
        },
        text = {
            Text(text = stringResource(R.string.would_you_like_to_pick_an_image_from_the_gallery_or_use_the))
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MessageSenderPreview() {
    ChatRoomTheme {
        ContentSelectionDialog(
            onSuccessResult = {},
            onDismiss = {},
        )
    }
}