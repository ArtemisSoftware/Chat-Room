package com.example.chatroom.feature.authentication.signin

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) {
    fun hasValidCredentials() = email.isNotEmpty() && password.isNotEmpty()
}
