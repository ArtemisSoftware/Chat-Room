package com.example.chatroom.feature.conversation.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.chatroom.feature.conversation.presentation.chat.ChatScreen
import com.example.chatroom.feature.conversation.presentation.lounge.LoungeScreen

fun NavGraphBuilder.conversationNavGraph(
    navController: NavHostController,
    initZegoService: () -> Unit,
) {

    composable<ConversationRoute.Lounge> {

        LaunchedEffect(key1 = Unit) {
            initZegoService()
        }

        LoungeScreen(
            navigateToChat = { id, name ->
                navController.navigate(ConversationRoute.Chat(channelId = id, channelName = name))
            }
        )
    }

    composable<ConversationRoute.Chat> {
        ChatScreen(
            navigateBack = { navController.popBackStack() }
        )
    }
}