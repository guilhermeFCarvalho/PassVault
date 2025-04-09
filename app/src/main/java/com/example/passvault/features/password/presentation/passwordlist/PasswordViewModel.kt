package com.example.passvault.features.password.presentation.passwordlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.usecase.PasswordUseCases
import com.example.passvault.features.password.domain.model.OrderType
import com.example.passvault.features.password.presentation.passwordlist.event.PasswordEvent
import com.example.passvault.features.password.presentation.passwordlist.state.PasswordState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val passwordUseCases: PasswordUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PasswordState())
    val state: State<PasswordState> = _state

    private var lastDeletedPassword: Password? = null

    private var getPasswordsJob: Job? = null

    init {
        getPasswords(OrderType.Descending)
    }

    fun onEvent(event: PasswordEvent) {
        when (event) {
            is PasswordEvent.RestorePassword -> {
                viewModelScope.launch {
                    passwordUseCases.addPasswordUseCase(lastDeletedPassword ?: return@launch)
                    lastDeletedPassword = null

                }
            }

            is PasswordEvent.DeletePassword -> {
                viewModelScope.launch {
                    passwordUseCases.deletePasswordUseCase(event.password)
                    lastDeletedPassword = event.password
                }

            }

            is PasswordEvent.OrderPasswords -> {
                if (state.value.orderType is OrderType.Ascending) {
                    _state.value = state.value.copy(
                        orderType = OrderType.Descending
                    )

                } else {
                    _state.value = state.value.copy(
                        orderType = OrderType.Ascending
                    )
                }

                getPasswords(passwordOrder = state.value.orderType)
            }
        }
    }

    private fun getPasswords(passwordOrder: OrderType) {
        getPasswordsJob?.cancel()
        getPasswordsJob = passwordUseCases.getPasswordsUseCases(passwordOrder).onEach { passwords ->
            _state.value = state.value.copy(
                passwords = passwords,
            )
        }.launchIn(viewModelScope)
    }
}