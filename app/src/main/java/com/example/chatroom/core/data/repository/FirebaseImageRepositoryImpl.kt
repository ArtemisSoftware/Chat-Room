package com.example.chatroom.core.data.repository

import androidx.core.net.toUri
import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.core.domain.repository.ImageRepository
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseImageRepositoryImpl(
    private val firebaseStorage: FirebaseStorage = Firebase.storage
): ImageRepository {
    override suspend fun storeImage(uri: String): Resource<String> {
        return try {
            val fileUri = uri.toUri()
            val imageRef = firebaseStorage
                .reference
                .child("images/${UUID.randomUUID()}")

            // Upload the file
            imageRef.putFile(fileUri).await()

            // Get the download URL
            val downloadUri = imageRef.downloadUrl.await()

            Resource.Success(downloadUri.toString())
        } catch (e: Exception) {
            Resource.Failure(DataError.FirebaseError.Error(e.message))
        }
    }
}