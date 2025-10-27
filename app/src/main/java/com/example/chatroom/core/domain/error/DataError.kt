package com.example.chatroom.core.domain.error

sealed interface DataError : CRError {

    sealed class FirebaseError : DataError {
        data class Error(val message: String? = "Unknown error") : FirebaseError()
        data object NoUserFound : FirebaseError()
        data object UnableToSubscribe: FirebaseError()
    }

    sealed class ChatError : DataError {
        data class Error(val message: String? = "Unknown error") : ChatError()
        data object MessageNotSent : ChatError()
    }

    sealed class NetworkError : DataError {
        data class Error(val message: String) : NetworkError()
    }
}