package com.example.chatroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatroom.feature.authentication.signin.SignInScreen
import com.example.chatroom.navigation.routes.SignInRoute

@Composable
fun RootNavGraph(
    startDestination: String = "",
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SignInRoute
    ) {
        composable<SignInRoute> {
            SignInScreen(
                navigateTo = {
                    /*
                    navController.navigate(ScreenB(
                        name = null,
                        age = 25
                    ))

                     */
                }
            )
        }

    }
}