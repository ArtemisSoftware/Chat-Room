package com.example.chatroom.feature.conversation.data.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.firebase.data.models.MessageFb
import com.example.chatroom.feature.conversation.data.mapper.toMessage
import com.example.chatroom.feature.conversation.domain.models.Message
import com.example.chatroom.feature.conversation.domain.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl @Inject constructor(
    private val messagesDbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
): ChatRepository {

    override suspend fun sendMessage(channelId: String, text: String): Resource<Unit> {
        firebaseAuth.currentUser?.let { currentUser ->
            val message = MessageFb(
                id = messagesDbRef.push().key ?: UUID.randomUUID().toString(),
                senderId = currentUser.uid,
                senderName = currentUser.displayName ?: "",
                message = text,
            )

            return send(channelId = channelId, message = message)
        }
        return Resource.Failure(DataError.FirebaseError.UnableToGenerateMessage)
    }

    override suspend fun sendImage(channelId: String, image: String): Resource<Unit> {
        firebaseAuth.currentUser?.let { currentUser ->
            val message = MessageFb(
                id = messagesDbRef.push().key ?: UUID.randomUUID().toString(),
                senderId = currentUser.uid,
                senderName = currentUser.displayName ?: "",
                imageUrl = image
            )

            return send(channelId = channelId, message = message)
        }
        return Resource.Failure(DataError.FirebaseError.UnableToGenerateMessage)
    }

    private suspend fun send(channelId: String, message: MessageFb): Resource<Unit> {
        return suspendCoroutine { continuation ->
            messagesDbRef
                .child(channelId)
                .push()
                .setValue(message)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        continuation.resume(
                            Resource.Success(Unit)
                        )
                    }
                    else {
                        continuation.resume(
                            Resource.Failure(DataError.FirebaseError.UnableToSendMessage)
                        )
                    }
                }
        }
    }

    override fun listenForMessages(channelId: String): Flow<Resource<List<Message>>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val messages = mutableListOf<Message>()

                    dataSnapshot.children.forEach { data ->
                        val message = data.getValue(MessageFb::class.java)
                        message?.let {
                            val lastSender = if(messages.isNotEmpty()) messages.last().senderName else ""
                            messages.add(
                                it.toMessage(isMyMessage = message.senderId == firebaseAuth.currentUser?.uid, lastSender = lastSender)
                            )
                        }
                    }

                    trySend(Resource.Success(messages)).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(
                        Resource.Failure(
                            DataError.FirebaseError.Error(error.message)
                        )
                    ).isSuccess
                }
            }

            messagesDbRef
                .child(channelId)
                .orderByChild("createdAt")
                .addValueEventListener(listener)

            // Remove listener when flow collection is cancelled
            awaitClose {
                messagesDbRef.removeEventListener(listener)
            }
        }

}