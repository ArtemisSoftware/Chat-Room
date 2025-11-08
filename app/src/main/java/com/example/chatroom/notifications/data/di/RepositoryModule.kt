package com.example.chatroom.notifications.data.di

import android.content.Context
import com.example.chatroom.notifications.data.repository.NotificationRepositoryImpl
import com.example.chatroom.notifications.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNotificationRepository(@ApplicationContext context: Context): NotificationRepository {
        return NotificationRepositoryImpl(context = context)
    }
}