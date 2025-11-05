package com.example.chatroom.feature.authentication.domain.repository

import com.example.chatroom.core.domain.Resource

interface AuthenticationRepository {
    suspend fun isLoggedIn(): Resource<Boolean>
    suspend fun signUp(name: String, email: String, password: String): Resource<Unit>
    suspend fun signIn(email: String, password: String): Resource<Unit>
}