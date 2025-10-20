package com.example.chatroom.core.domain.error

sealed interface DataError : CRError {

    sealed class FirebaseError : DataError {
        data class Error(val message: String? = "Unknown error") : NetworkError()
        data object NoUserFound : NetworkError()
    }

    sealed class NetworkError : DataError {
        data class Error(val message: String) : NetworkError()
    }
}