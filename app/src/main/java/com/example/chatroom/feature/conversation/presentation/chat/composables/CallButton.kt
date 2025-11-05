package com.example.chatroom.feature.conversation.presentation.chat.composables

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton

@Composable
internal fun CallButton(
    callType: CallType,
    onClick: (ZegoSendCallInvitationButton) -> Unit
) {
    AndroidView(
        factory = { context: Context ->

            val button = ZegoSendCallInvitationButton(context)
            button.setIsVideoCall(callType == CallType.VIDEO)
            button.resourceID = "zego_data"
            button
        },
        modifier = Modifier.size(40.dp)
    ){ zegoCallButton ->
        zegoCallButton.setOnClickListener { _ -> onClick(zegoCallButton) }
    }
}