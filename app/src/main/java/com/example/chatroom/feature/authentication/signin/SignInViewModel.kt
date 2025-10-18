package com.example.chatroom.feature.authentication.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onTriggerEvent(event: SignInEvent){
        when(event){
            SignInEvent.SignIn -> signIn()
            is SignInEvent.UpdateEmail -> updateEmail(event.email)
            is SignInEvent.UpdatePassword -> updatePassword(event.password)
        }
    }

    private fun signIn() = with(_state.value) {

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            authenticationRepository
                .signIn(email = email, password = password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    //TODO: enviar evento
                }
                .onFailure {
                    _state.update { it.copy(isLoading = false) }
                    //TODO: enviar evento
                }
        }
    }

    private fun updateEmail(email: String) = with(_state) {
        update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) = with(_state) {
        update { it.copy(password = password) }
    }
}