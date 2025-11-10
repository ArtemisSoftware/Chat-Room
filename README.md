# 💬 ChatRoom App

ChatRoom is a modern **real-time chat application** built with **Jetpack Compose**, designed for fast, secure, and feature-rich communication.  

It supports **text and image messages**, **voice/video calls**, and **real-time notifications** — powered by Firebase and ZEGOCLOUD.



## 🚀 Features

- 🔐 **User Authentication**
  - Register and log in using **Firebase Authentication**.
  - Supports email/password and future integration for OAuth providers.


- 💬 **Real-Time Messaging**
  - Messages are synced instantly using **Firebase Realtime Database**.
  - Supports **text** and **image** messages.
  - Images can be stored in **Supabase Storage** or **Firebase Storage**.


- 🔔 **Push Notifications**
  - Uses **Firebase Cloud Messaging (FCM)** to send notifications when new messages arrive.


- 📞 **Voice and Video Calls**
  - Integrated with **ZEGOCLOUD UIKit** for 1-to-1 and group **audio/video calls**.


## 🧩 Tech Stack and Libraries

| Purpose | Library |
|----------|----------|
| **UI** | Jetpack Compose, Material 3 |
| **Dependency Injection** | Dagger Hilt (`hilt-android`, `hilt-navigation-compose`) |
| **Image Loading** | Coil (`io.coil-kt:coil-compose`) |
| **Navigation** | Navigation Compose |
| **State Management** | ViewModel + LiveData (Lifecycle Runtime KTX) |
| **Networking** | Ktor Client, Volley |
| **Authentication & Database** | Firebase Authentication, Firebase Realtime Database |
| **Storage** | Firebase Storage, Supabase Storage |
| **Notifications** | Firebase Cloud Messaging (FCM) |
| **Voice & Video Calls** | ZEGOCLOUD UIKit (`zego_uikit_prebuilt_call_android`) |
| **Permissions** | PermissionX |
| **JSON Serialization** | Kotlinx Serialization |

 
## Screenshots

Image #1            |               Image #2               |               Image #3               |  Image #4            
:-------------------------:|:------------------------------------:|:------------------------------------:|:----------------------------:
<img src="images/CadmusDiary_4.jpg">    | <img src="images/CadmusDiary_1.jpg"> | <img src="images/CadmusDiary_2.jpg"> |  <img src="images/CadmusDiary_3.jpg"> 
