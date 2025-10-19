@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatroom.feature.conversation.lounge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatroom.R
import com.example.chatroom.domain.models.Channel
import com.example.chatroom.feature.conversation.lounge.composables.AddChannelDialog
import com.example.chatroom.feature.conversation.lounge.composables.ChannelCard
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.example.chatroom.ui.theme.DarkGrey

@Composable
internal fun LoungeScreen(
    navigateToChat: (String) -> Unit
) {
    /*
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Scaffold(floatingActionButton = {
        Box(modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Blue)
            .clickable {
                addChannel.value = true
            }) {
            Text(
                text = "Add Channel", modifier = Modifier.padding(16.dp), color = Color.White
            )
        }
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(channels.value) { channel ->
                    Column {
                        Text(text = channel.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Red.copy(alpha = 0.3f))
                                .clickable {
                                    navController.navigate("chat/${channel.id}")
                                }
                                .padding(16.dp))
                    }
                }
            }
        }
    }

    if (addChannel.value) {
        ModalBottomSheet(onDismissRequest = { addChannel.value = false }, sheetState = sheetState) {
            AddChannelDialog {
                viewModel.addChannel(it)
                addChannel.value = false
            }
        }
    }
*/
}

@Composable
private fun LoungeContent(
    state: LoungeState,
    navigateToChat: (String) -> Unit,
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
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            onClick = { navigateToChat(it) },
                            shouldShowCallButtons = false,
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
            navigateToChat = {},
        )
    }
}

