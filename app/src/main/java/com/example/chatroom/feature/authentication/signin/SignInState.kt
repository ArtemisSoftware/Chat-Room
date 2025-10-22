package com.example.chatroom.feature.authentication.signin

internal data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) {
    fun hasValidCredentials() = email.isNotEmpty() && password.isNotEmpty()
}
