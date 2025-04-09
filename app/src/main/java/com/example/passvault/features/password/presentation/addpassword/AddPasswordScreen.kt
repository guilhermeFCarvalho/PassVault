package com.example.passvault.features.password.presentation.addpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passvault.features.password.presentation.addpassword.event.AddPasswordEvent
import com.example.passvault.features.password.presentation.addpassword.state.PasswordTextFieldState
import com.example.passvault.features.password.presentation.shared.component.HidePasswordButtonComponent
import com.example.passvault.ui.theme.PassVaultTheme

@Composable
fun AddPasswordScreen(
    state: PasswordTextFieldState,
    onEvent: (AddPasswordEvent) -> Unit,
    snackbarState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarState)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                Alignment.CenterVertically
            )
        ) {
            TextField(
                value = state.label,
                onValueChange = { label ->
                    onEvent(
                        AddPasswordEvent.LabelChanged(label)
                    )
                },
                label = { Text("Label") },
                singleLine = true,
                placeholder = { Text("Label") },
            )

            TextField(
                value = state.password,
                onValueChange = { password ->
                    onEvent(
                        AddPasswordEvent.PasswordChanged(password)
                    )
                },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    HidePasswordButtonComponent(
                        isPasswordVisible = isPasswordVisible,
                        onClick = { isPasswordVisible = it }
                    )
                }
            )

            Button(
                onClick = {
                    onEvent(AddPasswordEvent.SavePassword)
                }
            ) {
                Text("Save Password")
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    PassVaultTheme {
        AddPasswordScreen(
            state = PasswordTextFieldState(
                password = "123",
                label = "test"
            ),
            snackbarState = remember { SnackbarHostState() },
            onEvent = {}
        )
    }
}