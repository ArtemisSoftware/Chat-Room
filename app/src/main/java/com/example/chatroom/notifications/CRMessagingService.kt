package com.example.chatroom.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.chatroom.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random
import javax.inject.Inject

class CRMessagingService @Inject constructor(
    private val firebaseAuth: FirebaseAuth = Firebase.auth
) : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("CRMessagingService", "From: ${message.from} Data: ${message.notification}")
        message.notification?.let {
            showNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("CRMessagingService", "New FCM Token: $token")
    }

    fun showNotification(title: String?, message: String?) {

        Firebase.auth.currentUser?.let {
            if(title?.contains(it.displayName.toString()) == true || message?.contains(it.displayName.toString()) == true) return
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel("messages", "Messages", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        val notificationId = Random().nextInt(1000)
        val notification = NotificationCompat.Builder(this, "messages")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}