package com.example.chatroom.data.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.data.constants.FirebaseConstant
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.domain.repository.ChannelRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference = Firebase
        .database(FirebaseConstant.FIREBASE_DATABASE_URL)
        .getReference(FirebaseConstant.CHANNEL_DATABASE_PATH),
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
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
                    //TODO: add error
                    /*
                    Resource.Failure(
                        DataError.FirebaseError.Error(error.message)
                    )
                    */
                }
        }
    }


    override fun registerUserIdtoChannel(channelId: String) {
        val currentUser = firebaseAuth.currentUser
        val ref = firebaseDatabase
            .child("channels")
            .child(channelId)
            .child("users")

        ref
            .child(currentUser?.uid ?: "")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        ref.child(currentUser?.uid ?: "").setValue(currentUser?.email)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )

    }
}