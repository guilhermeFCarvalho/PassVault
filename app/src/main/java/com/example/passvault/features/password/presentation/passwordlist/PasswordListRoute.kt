package com.example.passvault.features.password.presentation.passwordlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun PasswordListRoute(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasswordListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PasswordListScreen(
        state = state,
        onNavigate = onNavigate,
        modifier = modifier,
        onEvent = viewModel::onEvent
    )
}