package com.example.chatroom.feature.authentication.presentation.signin

internal data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    fun hasValidCredentials() = email.isNotEmpty() && password.isNotEmpty()
}
