package com.example.chatroom.core.presentation.util.extensions

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createImageUri(): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = ContextCompat.getExternalFilesDirs(
        this,
        Environment.DIRECTORY_PICTURES
    ).first()

    return FileProvider.getUriForFile(
        this,
        "${this.packageName}.provider",
        File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            Uri.fromFile(this)
        }
    )
}