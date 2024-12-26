package com.example.chatroom.feature.authentication.signup

sealed interface SignUpEvent {
    data class UpdateEmail(val email: String): SignUpEvent
    data class UpdatePassword(val password: String): SignUpEvent
    data class UpdatePasswordConfirm(val password: String): SignUpEvent
    data class UpdateName(val name: String): SignUpEvent
    data object SignUp: SignUpEvent
}