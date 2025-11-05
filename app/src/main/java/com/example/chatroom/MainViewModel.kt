package com.example.chatroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.feature.authentication.domain.repository.AuthenticationRepository
import com.example.chatroom.feature.authentication.presentation.navigation.AuthenticationRoute
import com.example.chatroom.feature.conversation.presentation.navigation.ConversationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        getMainRoute()
    }

    private fun getMainRoute() = with(_state){
        viewModelScope.launch {
            authenticationRepository
                .isLoggedIn()
                .onSuccess { isLoggedIn ->
                    val route = if (isLoggedIn) ConversationRoute.Lounge else AuthenticationRoute.SignIn
                    update { it.copy(destinationAfterSplash = route) }
                }
        }
    }
}