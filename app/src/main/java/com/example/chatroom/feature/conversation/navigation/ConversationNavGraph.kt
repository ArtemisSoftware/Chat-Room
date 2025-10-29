package com.example.chatroom.feature.conversation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.chatroom.feature.conversation.chat.ChatScreen
import com.example.chatroom.feature.conversation.lounge.LoungeScreen

const val CONVERSATION_GRAPH = "conversation_graph"

fun NavGraphBuilder.conversationNavGraph(
    navController: NavHostController,
) {

    composable<ConversationRoute.Lounge> {
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