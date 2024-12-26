package com.example.chatroom.feature.authentication.signin

sealed interface SignInEvent {
    data class UpdateEmail(val email: String): SignInEvent
    data class UpdatePassword(val password: String): SignInEvent
    data object SignIn: SignInEvent
}