@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatroom.feature.conversation.lounge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatroom.MainActivity
import com.example.chatroom.R
import com.example.chatroom.data.constants.ZegoConstant.AppID
import com.example.chatroom.data.constants.ZegoConstant.AppSign
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.feature.conversation.lounge.composables.AddChannelDialog
import com.example.chatroom.feature.conversation.lounge.composables.ChannelCard
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.example.chatroom.ui.theme.DarkGrey
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
internal fun LoungeScreen(
    navigateToChat: (String, String) -> Unit,
    viewModel: LoungeViewModel = hiltViewModel()
) {

    val context = LocalContext.current as MainActivity
    //TODO: Mudar isto
    LaunchedEffect(Unit) {
        Firebase.auth.currentUser?.let {
            context.initZegoService(
                appID = AppID,
                appSign = AppSign,
                userID = it.email!!,
                userName = it.email!!
            )
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    LoungeContent(
        state = state,
        navigateToChat = navigateToChat,
        onEvent = viewModel::onTriggerEvent
    )
}

@Composable
private fun LoungeContent(
    state: LoungeState,
    navigateToChat: (String, String) -> Unit,
    onEvent: (LoungeEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        floatingActionButton = {

            ExtendedFloatingActionButton(
                onClick = { onEvent(LoungeEvent.ShowAddChannelDialog(true)) }
            ) {
                Text(stringResource(R.string.add_channel))
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        Text(
                            text = "Messages",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    item {
                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text(text = "Search...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(
                                    RoundedCornerShape(40.dp)
                                ),
                            textStyle = TextStyle(color = Color.LightGray),
                            colors = TextFieldDefaults.colors().copy(
                                focusedContainerColor = DarkGrey,
                                unfocusedContainerColor = DarkGrey,
                                focusedTextColor = Color.Gray,
                                unfocusedTextColor = Color.Gray,
                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray,
                                focusedIndicatorColor = Color.Gray
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null
                                )
                            }
                        )
                    }

                    items(state.channels) { channel ->
                        ChannelCard(
                            channel = channel,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { id, name -> navigateToChat(id, name) },
                            shouldShowCallButtons = false,
                            onCall = {},
                        )
                    }
                }
            }
        }
    )

    if (state.showChannelDialog) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(LoungeEvent.ShowAddChannelDialog(false)) },
            sheetState = sheetState,
            content =  {
                AddChannelDialog(
                    channelName = state.newChannel.orEmpty(),
                    updateChannelName = {
                        onEvent(LoungeEvent.UpdateChannelName(it))
                    },
                    onAddChannel = {
                        onEvent(LoungeEvent.AddChanel)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoungeContentPreview() {
    ChatRoomTheme {
        LoungeContent(
            onEvent = {},
            state = LoungeState(
                showChannelDialog = false,
                channels = listOf(
                    Channel(id = "1", name = "The one"),
                    Channel(id = "2", name = "The two channel")
                )
            ),
            navigateToChat = {_,_ ->},
        )
    }
}

