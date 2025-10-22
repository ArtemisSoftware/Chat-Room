package com.example.chatroom.data.repository

import android.util.Log
import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.data.contants.FirebaseConstant
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.domain.repository.ChannelRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChannelRepositoryImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference = Firebase
        .database(FirebaseConstant.FIREBASE_DATABASE_URL)
        .getReference(FirebaseConstant.DATABASE_PATH)
): ChannelRepository {

    override fun getChannels(): Flow<Resource<List<Channel>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

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

        firebaseDatabase.addValueEventListener(listener)

        // Remove listener when flow collection is cancelled
        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }

    override suspend fun addChannel(name: String) {
        val key = firebaseDatabase.push().key
        key?.let {
            firebaseDatabase
                .child(it)
                .setValue(name)
                .addOnFailureListener {

                }
        }
    }
}