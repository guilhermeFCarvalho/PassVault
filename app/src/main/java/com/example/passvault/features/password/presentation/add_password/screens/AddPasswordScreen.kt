package com.example.passvault.features.password.presentation.add_password.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.passvault.features.password.presentation.add_password.AddPasswordEvent
import com.example.passvault.features.password.presentation.add_password.AddPasswordViewModel
import com.example.passvault.features.password.presentation.password.PasswordViewModel
import com.example.passvault.features.password.presentation.shared.components.HidePasswordButtonComponent


@Composable
fun AddPasswordScreen(
    viewModel: AddPasswordViewModel,
    navController: NavController
) {

    val labelState = viewModel.label.value
    val passwordState = viewModel.password.value
    val scaffoldState = rememberScaffoldState()

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icons.Default.Save
                },
            )
        },

        ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
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

        }
    }

}