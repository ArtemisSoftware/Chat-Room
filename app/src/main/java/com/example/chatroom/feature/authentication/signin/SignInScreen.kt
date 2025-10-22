package com.example.chatroom.feature.authentication.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatroom.R
import com.example.chatroom.core.presentation.composables.event.ManageUIEvents
import com.example.chatroom.feature.authentication.navigation.OtherRoute

@Composable
internal fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToSingUp:() -> Unit,
    navigateToLounge: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SignInContent(
        state = state,
        event = viewModel::onTriggerEvent,
        navigateToSingUp = navigateToSingUp
    )

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        onNavigateWithRoute = {
            when(it){
                OtherRoute.Lounge -> navigateToLounge()
            }
        }
    )
}


@Composable
private fun SignInContent(
    state: SignInState,
    event: (SignInEvent) -> Unit,
    navigateToSingUp:() -> Unit
) {
/*
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {

        when (uiState.value) {
            is SignInState.Success -> {
                navController.navigate("home")
            }

            is SignInState.Error -> {
                Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
*/
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = { event(SignInEvent.UpdateEmail(email = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.email))
                }
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { event(SignInEvent.UpdatePassword(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { event(SignInEvent.SignIn) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.hasValidCredentials()
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }

                TextButton(
                    onClick = navigateToSingUp
                ) {
                    Text(text = stringResource(R.string.don_t_have_an_account_sign_up))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInContentPreview() {
    SignInContent(
        navigateToSingUp = {},
        event = {},
        state = SignInState(email = "email@email.com", password = "1234")
    )
}