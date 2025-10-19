package com.example.chatroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.navigation.RootNavGraph
import com.example.chatroom.ui.theme.ChatRoomTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatRoomTheme {
                val navController = rememberNavController()
                val state = viewModel.state.collectAsStateWithLifecycle().value

                state.destinationAfterSplash?.let { route ->
                    RootNavGraph(
                        navController = navController,
                        startDestination = route
                    )
                }
            }
        }
    }
}
