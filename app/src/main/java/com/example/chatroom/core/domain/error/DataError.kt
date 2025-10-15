package com.example.chatroom.core.domain.error

sealed interface DataError : CRError {

    sealed class NetworkError : DataError {
        data class Error(val message: String) : NetworkError()
    }
}