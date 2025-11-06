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

        // TODO: check if logic can be changed to suspend corotine
        return try {
            val fileUri = uri.toUri()
            val imageRef = firebaseStorage.reference.child("images/${UUID.randomUUID()}")

            // Upload the file
            imageRef.putFile(fileUri).await()

            // Get the download URL
            val downloadUri = imageRef.downloadUrl.await()

            Resource.Success(downloadUri.toString())
        } catch (e: Exception) {
            Resource.Failure(DataError.FirebaseError.Error(e.message))
        }
        /*
        return suspendCoroutine { continuation ->
            imageRef
                .putFile(fileUri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    val result = if (task.isSuccessful) {
                        val downloadUri = task.result
                        Resource.Success(downloadUri.toString())
                    } else {
                        Resource.Failure(DataError.FirebaseError.Error(task.exception?.message))
                    }
                    continuation.resume(result)
                }

        } */
    }
}