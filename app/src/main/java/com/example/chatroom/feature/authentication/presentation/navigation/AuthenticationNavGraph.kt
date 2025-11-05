package com.example.chatroom.feature.authentication.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.chatroom.feature.authentication.presentation.signin.SignInScreen
import com.example.chatroom.feature.authentication.presentation.signup.SignUpScreen

const val AUTHENTICATION_GRAPH = "authentication_graph"

fun NavGraphBuilder.authenticationNavGraph(
    navController: NavHostController,
    navigateToLounge: () -> Unit,
) {

    composable<AuthenticationRoute.SignUp> {
        SignUpScreen(
            navigateToSignIn = {
                navController.popBackStack()
            },
            navigateToLounge = navigateToLounge
        )
    }

    composable<AuthenticationRoute.SignIn> {
        SignInScreen(
            navigateToSingUp = { navController.navigate(AuthenticationRoute.SignUp) },
            navigateToLounge = navigateToLounge
        )
    }
}