package com.example.passvault.features.password.presentation.addpassword

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.passvault.features.password.presentation.addpassword.state.PasswordTextFieldState
import com.example.passvault.ui.theme.PassVaultTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddPasswordRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddPasswordViewModel = koinViewModel()
) {

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPasswordViewModel.UiEvent.ShowSnackBar -> {
                    snackbarState.showSnackbar(
                        message = event.message
                    )
                }

                is AddPasswordViewModel.UiEvent.SavePassword -> {
                    navigateUp()
                }
            }
        }
    }
    if(viewModel.state.value.snackBarMessage.isNotEmpty()){
        snackbarState.showSnackbar(
            message = event.message
        )

        viewModel.clearSnackbarMessage()
    }
    AddPasswordScreen(
        modifier = modifier,
        snackbarState = snackbarState,
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
}
