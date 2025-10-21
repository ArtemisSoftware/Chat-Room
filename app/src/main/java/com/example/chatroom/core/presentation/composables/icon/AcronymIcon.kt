package com.example.chatroom.core.presentation.composables.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatroom.ui.theme.Blue
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.example.chatroom.ui.theme.Cyan
import com.example.chatroom.ui.theme.Green
import com.example.chatroom.ui.theme.Orange
import com.example.chatroom.ui.theme.Red
import com.example.chatroom.ui.theme.Yellow
import kotlin.math.abs

private val IconColors = listOf(Red, Green, Blue, Yellow, Orange, Cyan)

@Composable
fun AcronymIcon(
    name: String,
    modifier: Modifier = Modifier
) {
    val color = IconColors[abs(name.hashCode()) % IconColors.size]
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(70.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.3f))

    ) {
        Text(
            text = name[0].uppercase(),
            fontSize = 32.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AcronymIconPreview() {
    ChatRoomTheme {
        AcronymIcon(
            name = "Camus"
        )
    }
}
