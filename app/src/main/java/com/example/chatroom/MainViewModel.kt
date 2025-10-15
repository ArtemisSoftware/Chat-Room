package com.example.chatroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.repository.AuthenticationRepository
import com.example.chatroom.feature.authentication.navigation.AuthenticationRoute
import com.example.chatroom.feature.authentication.signin.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel constructor(
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
                    //TODO: mudar o home para route
                    val route = if (isLoggedIn) "home" else AuthenticationRoute.SignInRoute

                    update { it.copy(destinationAfterSplash = route) }
                }
        }
    }
}