package com.example.chatroom.feature.authentication.signup

internal data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val isLoading: Boolean = false
) {
    fun hasValidPasswords() = password.isNotEmpty() && passwordConfirm.isNotEmpty() && password == passwordConfirm
    fun hasValidCredentials() = name.isNotEmpty() && email.isNotEmpty() && hasValidPasswords()
}
