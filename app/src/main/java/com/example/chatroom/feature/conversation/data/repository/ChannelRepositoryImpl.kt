package com.example.chatroom.feature.conversation.data.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.feature.conversation.domain.repository.ChannelRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChannelRepositoryImpl @Inject constructor(
    private val channelDbRef: DatabaseReference,
    private val loungeDbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
): ChannelRepository {

    override fun getChannels(): Flow<Resource<List<Channel>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.

                val channels = mutableListOf<Channel>()
                dataSnapshot.children.forEach { data ->
                    data.key?.let {
                        val channel = Channel(it, data.value.toString())
                        channels.add(channel)
                    }
                }

                trySend(Resource.Success(channels)).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(
                    Resource.Failure(
                        DataError.FirebaseError.Error(error.message)
                    )
                ).isSuccess
            }
        }

        channelDbRef.addValueEventListener(listener)

        // Remove listener when flow collection is cancelled
        awaitClose {
            channelDbRef.removeEventListener(listener)
        }
    }

    override suspend fun addChannel(name: String): Resource<Unit> {
        return suspendCoroutine { continuation ->
            channelDbRef.push().key?.let {
                channelDbRef
                    .child(it)
                    .setValue(name)
                    .addOnSuccessListener {
                        continuation.resume(Resource.Success(Unit))
                    }
                    .addOnFailureListener { error ->
                        continuation.resume(
                            Resource.Failure(
                                DataError.FirebaseError.Error(error.message)
                            )
                        )
                    }
            }
        }
    }

    override suspend fun registerUserIdToChannel(channelId: String): Resource<Unit> {
        val currentUser = firebaseAuth.currentUser
            ?: return Resource.Failure(DataError.FirebaseError.NoUserFound)

        val ref = getUsersDbRef(channelId)
            .child(currentUser.uid)

        return try {
            // Check if user already exists in channel
            val snapshot = ref.get().await()
            if (!snapshot.exists()) {
                // Add user and wait for completion
                ref.setValue(currentUser.email).await()
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(DataError.FirebaseError.Error(e.message))
        }
    }

    override fun getAllUserEmails(channelId: String): Flow<Resource<List<String>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.

                val userIds = mutableListOf<String>()
                dataSnapshot.children.forEach { it ->

                    val email = it.value.toString()
                    firebaseAuth.currentUser?.email?.let { userEmail ->
                        if (email != userEmail) {
                            userIds.add(email)
                        }
                    }
                }

                trySend(Resource.Success(userIds)).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(
                    Resource.Failure(
                        DataError.FirebaseError.Error(error.message)
                    )
                ).isSuccess
            }
        }

        getUsersDbRef(channelId)
            .addValueEventListener(listener)

        // Remove listener when flow collection is cancelled
        awaitClose {
            loungeDbRef.removeEventListener(listener)
        }
    }

    private fun getUsersDbRef(channelId: String): DatabaseReference = loungeDbRef
        .child(channelId)
        .child("users")
}