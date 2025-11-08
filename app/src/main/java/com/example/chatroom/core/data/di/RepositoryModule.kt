package com.example.chatroom.core.data.di

import android.content.Context
import com.example.chatroom.core.data.repository.FirebaseImageRepositoryImpl
import com.example.chatroom.core.data.repository.SupabaseImageRepositoryImpl
import com.example.chatroom.core.domain.repository.ImageRepository
import com.example.chatroom.notifications.data.repository.NotificationRepositoryImpl
import com.example.chatroom.notifications.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    @Named("firebase_image_repo")
    fun provideFirebaseImageRepository(): ImageRepository {
        return FirebaseImageRepositoryImpl()
    }

    @Provides
    @Singleton
    @Named("supabase_image_repo")
    fun provideSupabaseImageRepository(
        @ApplicationContext context: Context,
        supabaseClient: SupabaseClient
    ): ImageRepository {
        return SupabaseImageRepositoryImpl(context = context, supabaseClient = supabaseClient)
    }
}