package com.example.passvault.features.password.presentation.addpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passvault.features.password.presentation.addpassword.event.AddPasswordEvent
import com.example.passvault.features.password.presentation.shared.component.HidePasswordButtonComponent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddPasswordScreen(
    viewModel: AddPasswordViewModel = koinViewModel(),
    navController: NavController
) {

    val labelState = viewModel.label.value
    val passwordState = viewModel.password.value
    val scaffoldState = rememberScaffoldState()

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPasswordViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddPasswordViewModel.UiEvent.SavePassword -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = labelState.label,
                onValueChange = { label ->
                    viewModel.onEvent(
                        AddPasswordEvent.LabelChanged(
                            label
                        )
                    )
                },
                label = { Text("Label") },
                singleLine = true,
                placeholder = { Text("Label") },
            )
            Spacer(Modifier.height(12.dp))
            TextField(
                value = passwordState.password,
                onValueChange = { password ->
                    viewModel.onEvent(
                        AddPasswordEvent.PasswordChanged(
                            password
                        )
                    )
                },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    HidePasswordButtonComponent(
                        isPasswordVisible = isPasswordVisible,
                        onClick = { isPasswordVisible = it })
                }
            )
            Spacer(Modifier.height(12.dp))
            Button(onClick = { viewModel.onEvent(AddPasswordEvent.SavePassword) }) {
                Text("Save Password")
            }

        }
    }

}