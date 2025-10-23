package com.example.chatroom.data.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.data.constants.FirebaseConstant
import com.example.chatroom.data.constants.FirebaseConstant.MESSAGE_DATABASE_PATH
import com.example.chatroom.data.firebase.models.MessageFb
import com.example.chatroom.data.mapper.toMessage
import com.example.chatroom.domain.models.Message
import com.example.chatroom.domain.repository.ChatRepository
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
import java.util.UUID
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseDatabase: DatabaseReference = Firebase
        .database(FirebaseConstant.FIREBASE_DATABASE_URL)
        .getReference(FirebaseConstant.MESSAGE_DATABASE_PATH),
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
): ChatRepository {

    override suspend fun sendMessage(channelId: String, message: String, image: String?) {
        val message = MessageFb(
            id = firebaseDatabase.push().key ?: UUID.randomUUID().toString(),
            senderId = firebaseAuth.currentUser?.uid ?: "",
            message = message,
            senderName = firebaseAuth.currentUser?.displayName ?: "",
            senderImage = image,
        )

        firebaseDatabase
            .child(channelId)
            .push()
            .setValue(message)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //postNotificationToUsers(channelID, message.senderName, messageText ?: "")
                }
            }
    }

    override fun listenForMessages(channelId: String): Flow<Resource<List<Message>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val messages = mutableListOf<Message>()

                dataSnapshot.children.forEach { data ->
                    val message = data.getValue(MessageFb::class.java)
                    message?.let {
                        messages.add(
                            it.toMessage(isMyMessage = message.senderId == firebaseAuth.currentUser?.uid)
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

        firebaseDatabase
            .child(channelId)
            .orderByChild("createdAt")
            .addValueEventListener(listener)

        // Remove listener when flow collection is cancelled
        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }


    override suspend fun sendImage(channelId: String, uri: String) {
        TODO("Not yet implemented")
    }
    /*


    fun sendImageMessage(uri: Uri, channelID: String) {
        val imageRef = Firebase.storage.reference.child("images/${UUID.randomUUID()}")
        imageRef.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val currentUser = Firebase.auth.currentUser
            if (task.isSuccessful) {
                val downloadUri = task.result
                sendMessage(channelID, null, downloadUri.toString())
            }
        }
    }
*/

}