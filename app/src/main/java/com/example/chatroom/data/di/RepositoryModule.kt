package com.example.chatroom.data.di

import android.content.Context
import com.example.chatroom.feature.authentication.data.repository.AuthenticationRepositoryImpl
import com.example.chatroom.data.repository.ChannelRepositoryImpl
import com.example.chatroom.data.repository.ChatRepositoryImpl
import com.example.chatroom.feature.authentication.domain.repository.AuthenticationRepository
import com.example.chatroom.domain.repository.ChannelRepository
import com.example.chatroom.domain.repository.ChatRepository
import com.example.chatroom.domain.repository.ImageRepository
import com.example.chatroom.data.repository.FirebaseImageRepositoryImpl
import com.example.chatroom.data.repository.NotificationRepositoryImpl
import com.example.chatroom.data.repository.SupabaseImageRepositoryImpl
import com.example.chatroom.domain.repository.NotificationRepository
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
    fun provideChannelRepository(): ChannelRepository {
        return ChannelRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository {
        return ChatRepositoryImpl()
    }

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

    @Provides
    @Singleton
    fun provideNotificationRepository(@ApplicationContext context: Context): NotificationRepository {
        return NotificationRepositoryImpl(context = context)
    }
}