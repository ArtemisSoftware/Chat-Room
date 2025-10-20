package com.example.chatroom.feature.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.chatroom.feature.authentication.signin.SignInScreen
import com.example.chatroom.feature.authentication.signup.SignUpScreen

const val AUTHENTICATION_GRAPH = "authentication_graph"

fun NavGraphBuilder.authenticationNavGraph(
    navController: NavHostController,
) {

    composable<AuthenticationRoute.SignUp> {
        SignUpScreen(
            navigateToSignIn = {
                navController.popBackStack()
            },
        )
    }

    composable<AuthenticationRoute.SignIn> {
        SignInScreen(
            navigateToSingUp = { navController.navigate(AuthenticationRoute.SignUp) },
        )
    }
}