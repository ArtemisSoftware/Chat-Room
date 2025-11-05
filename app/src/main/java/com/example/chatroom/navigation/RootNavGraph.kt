package com.example.chatroom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.chatroom.feature.authentication.presentation.navigation.AuthenticationRoute
import com.example.chatroom.feature.authentication.presentation.navigation.authenticationNavGraph
import com.example.chatroom.feature.conversation.presentation.navigation.ConversationRoute
import com.example.chatroom.feature.conversation.presentation.navigation.conversationNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: Any = AuthenticationRoute.SignIn,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        authenticationNavGraph(
            navController = navController,
            navigateToLounge = {
                navController.navigate(ConversationRoute.Lounge) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        )

        conversationNavGraph(
            navController = navController,
        )
    }
}