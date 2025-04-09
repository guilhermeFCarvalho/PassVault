package com.example.passvault.features.password.presentation.passwordlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun PasswordListRoute(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasswordViewModel = koinViewModel()
) {
    val state = viewModel.state.value

    PasswordListScreen(
        state = state,
        onNavigate = onNavigate,
        modifier = modifier,
        onEvent = viewModel::onEvent
    )
}