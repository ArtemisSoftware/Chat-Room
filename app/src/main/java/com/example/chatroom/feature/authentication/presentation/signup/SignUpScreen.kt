package com.example.chatroom.feature.authentication.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatroom.R
import com.example.chatroom.core.presentation.composables.event.ManageUIEvents
import com.example.chatroom.core.presentation.composables.text.ErrorDisplay
import com.example.chatroom.feature.authentication.presentation.navigation.OtherRoute

@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit,
    navigateToLounge: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SignUpContent(
        state = state,
        navigateToSignIn = navigateToSignIn,
        event = viewModel::onTriggerEvent
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigateWithRoute = {
            when(it.value){
                OtherRoute.Lounge -> navigateToLounge()
            }
        }
    )
}

@Composable
private fun SignUpContent(
    state: SignUpState,
    navigateToSignIn: () -> Unit,
    event: (SignUpEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .background(Color.White)
            )
            OutlinedTextField(
                value = state.name,
                onValueChange = { event(SignUpEvent.UpdateName(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.full_name)) }
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { event(SignUpEvent.UpdateEmail(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.email)) })

            OutlinedTextField(
                value = state.password,
                onValueChange = { event(SignUpEvent.UpdatePassword(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = state.passwordConfirm,
                onValueChange = { event(SignUpEvent.UpdatePasswordConfirm(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.confirm_password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = state.hasValidPasswords()
            )
            Spacer(modifier = Modifier.size(16.dp))


            when{
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    ErrorDisplay(
                        modifier = Modifier.fillMaxWidth(.9F),
                        message = state.error
                    )
                }
                else -> {
                    Button(
                        onClick = { event(SignUpEvent.SignUp) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = state.hasValidCredentials(),
                        content = {
                            Text(text = stringResource(R.string.sign_up))
                        }
                    )
                    TextButton(
                        onClick = navigateToSignIn,
                        content = {
                            Text(text = stringResource(R.string.already_have_an_account_sign_in))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpContentPreview() {
    SignUpContent(
        navigateToSignIn = {},
        event = {},
        state = SignUpState(email = "email@email.com", password = "1234")
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpContent_loading_Preview() {
    SignUpContent(
        navigateToSignIn = {},
        event = {},
        state = SignUpState(
            isLoading = true,
            email = "email@email.com",
            password = "1234"
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpContent_error_Preview() {
    SignUpContent(
        navigateToSignIn = {},
        event = {},
        state = SignUpState(
            isLoading = false,
            error = "Something went wrong",
            email = "email@email.com",
            password = "1234"
        )
    )
}