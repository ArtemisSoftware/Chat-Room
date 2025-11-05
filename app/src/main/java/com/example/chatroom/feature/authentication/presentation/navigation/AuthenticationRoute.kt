package com.example.chatroom.feature.authentication.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AuthenticationRoute {

    @Serializable
    object SignIn

    @Serializable
    object SignUp
}

internal sealed class OtherRoute {

    @Serializable
    object Lounge
}