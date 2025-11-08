package com.example.chatroom.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.core.domain.repository.ImageRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import java.util.UUID

class SupabaseImageRepositoryImpl(
    private val context: Context,
    private val supabaseClient: SupabaseClient
): ImageRepository {
    override suspend fun storeImage(uri: String): Resource<String> {
        try {
            val fileUri = uri.toUri()

            val extension = fileUri
                .path
                ?.substringAfterLast(".") ?: "jpg"

            val fileName = "${UUID.randomUUID()}.$extension"

            val inputStream = context
                .contentResolver
                .openInputStream(fileUri) ?: return Resource.Failure(DataError.SupabaseError.UnableToOpenStream)

            supabaseClient
                .storage
                .from(BUCKET_NAME)
                .upload(fileName, inputStream.readBytes())

            val publicUrl = supabaseClient
                .storage
                .from(BUCKET_NAME)
                .publicUrl(fileName)

            return  Resource.Success(publicUrl)
        } catch (e: Exception) {
            return Resource.Failure(DataError.SupabaseError.Error(e.message))
        }
    }

    private companion object {
        const val BUCKET_NAME = "chat_images"
    }
}