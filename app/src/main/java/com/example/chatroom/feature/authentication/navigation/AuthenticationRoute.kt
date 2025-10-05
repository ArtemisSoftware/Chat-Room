package com.example.chatroom.feature.authentication.navigation

import kotlinx.serialization.Serializable

sealed class AuthenticationRoute {

    @Serializable
    object SignInRoute

    @Serializable
    object SignUpRoute
}