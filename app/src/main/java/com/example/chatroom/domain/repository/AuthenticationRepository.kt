package com.example.chatroom.domain.repository

import com.example.chatroom.core.domain.Resource

interface AuthenticationRepository {

    suspend fun signIn(email: String, password: String): Resource<Unit>
    suspend fun isLoggedIn(): Resource<Boolean>
}