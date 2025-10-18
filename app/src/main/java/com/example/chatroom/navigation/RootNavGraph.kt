package com.example.chatroom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.chatroom.feature.authentication.navigation.AuthenticationRoute
import com.example.chatroom.feature.authentication.navigation.authenticationNavGraph
import com.example.chatroom.feature.conversation.navigation.conversationNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: Any = AuthenticationRoute.SignInRoute,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        authenticationNavGraph(
            navController = navController,
        )

        conversationNavGraph(
            navController = navController,
        )
    }
}