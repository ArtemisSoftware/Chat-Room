package com.example.chatroom.feature.authentication.data.di

import com.example.chatroom.data.constants.SupabaseConstant
import com.example.chatroom.data.repository.AuthenticationRepositoryImpl
import com.example.chatroom.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            SupabaseConstant.URL,
            SupabaseConstant.API_KEY
        ) {
            install(Storage)
        }
    }
}