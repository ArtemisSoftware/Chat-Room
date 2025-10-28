package com.example.chatroom.data.repository

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chatroom.R
import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.data.constants.FcmConstant.FCM_URL
import com.example.chatroom.domain.repository.NotificationRepository
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val firebaseMessaging: FirebaseMessaging = FirebaseMessaging.getInstance()
): NotificationRepository {

    override suspend fun subscribeForNotification(channelId: String): Resource<Unit> {
        return suspendCoroutine { continuation ->
            firebaseMessaging
                .subscribeToTopic("group_$channelId")
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        continuation.resume(Resource.Success(Unit))
                        Log.d("ChatViewModel", "Subscribed to topic: group_$channelId")
                    } else {
                        continuation.resume(Resource.Failure(DataError.FirebaseError.UnableToSubscribe))

                        Log.d("ChatViewModel", "Failed to subscribe to topic: group_$channelId")
                    }
                }
        }
    }

    override fun postNotificationToUsers(
        channelId: String,
        senderName: String,
        messageContent: String
    ) {
        val jsonBody = JSONObject().apply {
            put("message", JSONObject().apply {
                put("topic", "group_$channelId")
                put("notification", JSONObject().apply {
                    put("title", "New message in $channelId")
                    put("body", "$senderName: $messageContent")
                })
            })
        }

        val requestBody = jsonBody.toString()

        val request = object : StringRequest(
            Method.POST,
            FCM_URL,
            Response.Listener {
                Log.d("ChatViewModel", "Notification sent successfully")
            },
            Response.ErrorListener {
                Log.e("ChatViewModel", "Failed to send notification")
            }
        ) {
            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${getAccessToken()}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    private fun getAccessToken(): String {
        val inputStream = context.resources.openRawResource(R.raw.chatroom_key)
        val googleCreds = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
        return googleCreds.refreshAccessToken().tokenValue
    }

    // TODO: deveria haver um metodo para unsubscribe
    /*


    private fun postNotificationToUsers(
        channelID: String,
        senderName: String,
        messageContent: String
    ) {
        val fcmUrl = "https://fcm.googleapis.com/v1/projects/chatter-bbd0d/messages:send"
        val jsonBody = JSONObject().apply {
            put("message", JSONObject().apply {
                put("topic", "group_$channelID")
                put("notification", JSONObject().apply {
                    put("title", "New message in $channelID")
                    put("body", "$senderName: $messageContent")
                })
            })
        }

        val requestBody = jsonBody.toString()

        val request = object : StringRequest(Method.POST, fcmUrl, Response.Listener {
            Log.d("ChatViewModel", "Notification sent successfully")
        }, Response.ErrorListener {
            Log.e("ChatViewModel", "Failed to send notification")
        }) {
            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${getAccessToken()}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


     */

}