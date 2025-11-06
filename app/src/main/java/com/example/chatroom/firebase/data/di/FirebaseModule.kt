package com.example.chatroom.firebase.data.di

import com.example.chatroom.firebase.data.constants.FirebaseConstant
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    @Named("channel_db_ref")
    fun provideChannelDatabaseRef(): DatabaseReference {
        return Firebase
            .database(FirebaseConstant.FIREBASE_DATABASE_URL)
            .getReference(FirebaseConstant.CHANNEL_DATABASE_PATH)
    }

    @Provides
    @Singleton
    @Named("lounge_db_ref")
    fun provideLoungeDatabaseRef(): DatabaseReference {
        return Firebase
            .database(FirebaseConstant.FIREBASE_DATABASE_URL)
            .getReference(FirebaseConstant.LOUNGE_DATABASE_PATH)
    }

    @Provides
    @Singleton
    @Named("messages_db_ref")
    fun provideMessagesDatabaseRef(): DatabaseReference {
        return Firebase
            .database(FirebaseConstant.FIREBASE_DATABASE_URL)
            .getReference(FirebaseConstant.MESSAGE_DATABASE_PATH)
    }
}