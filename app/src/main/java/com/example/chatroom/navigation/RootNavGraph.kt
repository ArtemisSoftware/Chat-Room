package com.example.chatroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatroom.feature.authentication.signin.SignInScreen
import com.example.chatroom.feature.authentication.signup.SignUpScreen
import com.example.chatroom.navigation.routes.SignInRoute
import com.example.chatroom.navigation.routes.SignUpRoute

@Composable
fun RootNavGraph(
    startDestination: Any = SignInRoute,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<SignInRoute> {
            SignInScreen(
                navigateToSingUp = {
                    /*
                    navController.navigate(ScreenB(
                        name = null,
                        age = 25
                    ))

                     */
                }
            )
        }
        composable<SignUpRoute> {
            SignUpScreen(
                navigateBack = {
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