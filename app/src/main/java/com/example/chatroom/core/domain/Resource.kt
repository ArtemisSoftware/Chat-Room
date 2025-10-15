package com.example.chatroom.core.domain

import com.example.chatroom.core.domain.error.CRError

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Failure<T>(val error: CRError) : Resource<T>

    fun onSuccess(block: (T) -> Unit): Resource<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (CRError) -> Unit): Resource<T> {
        if (this is Failure) block(error)
        return this
    }
}

inline fun <T, R> Resource<T>.map(map: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(map(data))
        is Resource.Failure -> Resource.Failure(error)
    }
}