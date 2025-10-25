package com.example.chatroom.core.presentation.composables.dialog

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatroom.R
import com.example.chatroom.ui.theme.ChatRoomTheme

@Composable
fun ContentSelectionDialog(
    onDismiss: () -> Unit,
    onSuccessResult: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current


    val cameraImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            /*
            cameraImageUri.value?.let {
                viewModel.sendImageMessage(it, channelId)
            }
            */
        }
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                // ✅ Persist permission properly
                context.contentResolver.takePersistableUriPermission(
                    selectedUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val inputStream = context.contentResolver.openInputStream(selectedUri)
                if (inputStream == null) {
                    Log.e("STORE_IMAGE", "Cannot open URI stream.")

                } else {
                    Log.d("STORE_IMAGE", "URI is readable.")
                    inputStream.close()
                }

            } catch (e: SecurityException) {
                e.printStackTrace()
            }
            onSuccessResult(selectedUri)
        }
        onDismiss()
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //cameraImageLauncher.launch(createImageUri())
            }
        }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    /*
                    if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        cameraImageLauncher.launch(createImageUri())
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
*/
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