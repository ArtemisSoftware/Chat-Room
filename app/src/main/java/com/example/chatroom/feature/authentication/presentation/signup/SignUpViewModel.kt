package com.example.chatroom.feature.authentication.presentation.signup

import androidx.lifecycle.viewModelScope
import com.example.chatroom.core.presentation.event.UiEvent
import com.example.chatroom.core.presentation.event.UiEventViewModel
import com.example.chatroom.core.presentation.util.extensions.toText
import com.example.chatroom.feature.authentication.domain.repository.AuthenticationRepository
import com.example.chatroom.feature.authentication.presentation.navigation.OtherRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): UiEventViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onTriggerEvent(event: SignUpEvent){
        when(event){
            SignUpEvent.SignUp -> signUp()
            is SignUpEvent.UpdateEmail -> updateEmail(event.email)
            is SignUpEvent.UpdateName -> updateName(event.name)
            is SignUpEvent.UpdatePassword -> updatePassword(event.password)
            is SignUpEvent.UpdatePasswordConfirm -> updatePasswordConfirm(event.password)
        }
    }

    private fun signUp() = with(_state.value) {

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            authenticationRepository
                .signUp(name = name, email = email, password = password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    sendUiEvent(uiEvent = UiEvent.NavigateWithRoute(OtherRoute.Lounge))
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.toText()) }
                }
        }
    }

    private fun updateEmail(email: String) = with(_state) {
        update { it.copy(email = email) }
    }

    private fun updateName(name: String) = with(_state) {
        update { it.copy(name = name) }
    }

    private fun updatePassword(password: String) = with(_state) {
        update { it.copy(password = password) }
    }

    private fun updatePasswordConfirm(password: String) = with(_state) {
        update { it.copy(passwordConfirm = password) }
    }
}