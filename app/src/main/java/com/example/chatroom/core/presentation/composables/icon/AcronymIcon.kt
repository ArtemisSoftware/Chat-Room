package com.example.chatroom.core.presentation.composables.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatroom.ui.theme.Blue
import com.example.chatroom.ui.theme.ChatRoomTheme
import com.example.chatroom.ui.theme.Cyan
import com.example.chatroom.ui.theme.Green
import com.example.chatroom.ui.theme.IconColors
import com.example.chatroom.ui.theme.Orange
import com.example.chatroom.ui.theme.Red
import com.example.chatroom.ui.theme.UserIconColors
import com.example.chatroom.ui.theme.Yellow
import kotlin.math.abs


@Composable
fun AcronymIcon(
    name: String,
    modifier: Modifier = Modifier,
    isUser: Boolean = false,
    useExtended: Boolean = false,
    userColors: List<Color> = UserIconColors,
    iconColors: List<Color> = IconColors
) {

    val size = if(isUser) 40.dp else 48.dp
    val fontSize = if(isUser) 18.sp else 24.sp

    val colors = if(isUser) userColors else iconColors
    val color = colors[abs(name.hashCode()) % colors.size]

    val acronym = remember(name, useExtended) {
        when {
            name.isBlank() -> "%"
            !useExtended -> name.first().uppercase()
            name.contains(" ") -> name
                .trim()
                .split(Regex("\\s+"))
                .take(2)
                .joinToString("") { it.first().uppercase() }
            name.length >= 2 -> name.take(2)
                .replaceFirstChar { it.uppercase() }
            else -> name.first().uppercase()
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(width = 1.dp, color = color.copy(alpha = 0.8f), shape = CircleShape)
            .background(color.copy(alpha = 0.3f))
    ) {
        Text(
            text = acronym,
            fontSize = fontSize,
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

@Preview(showBackground = true)
@Composable
private fun AcronymIcon_extended_Preview() {
    ChatRoomTheme {
        AcronymIcon(
            name = "Camus",
            useExtended = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AcronymIcon_user_Preview() {
    ChatRoomTheme {
        AcronymIcon(
            name = "Camus",
            useExtended = true,
            isUser = true
        )
    }
}
