package com.example.chatroom.core.presentation.composables.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDisplay(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = Color.Red.copy(alpha = .2F), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDisplayPreview() {
    ErrorDisplay(
        modifier = Modifier.fillMaxWidth(),
        message = "Error here"
    )
}