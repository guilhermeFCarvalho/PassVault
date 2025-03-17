package com.example.passvault.features.password.presentation.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.use_case.PasswordUseCases
import com.example.passvault.features.password.domain.util.order_type.OrderType
import com.example.passvault.features.password.domain.util.order_type.PasswordOrder
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PasswordViewModel(
    private val passwordUseCases: PasswordUseCases
) : ViewModel() {

    private val _state = mutableStateOf<PasswordState>(PasswordState())
    val state: State<PasswordState> = _state

    private var lastDeletedPassword: Password? = null

    private var getPasswordsJob: Job? = null

    init {
        getPasswords(PasswordOrder.Label(OrderType.Descending))
    }

    fun onEvent(event: PasswordEvent) {
        when (event) {
            is PasswordEvent.Order -> {
                if (state.value.passwordOrder::class == event.passwordOrder::class && state.value.passwordOrder.orderType == event.passwordOrder.orderType) {
                    return
                }
                getPasswords(event.passwordOrder)

            }

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

            is PasswordEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )

            }
        }
    }

    private fun getPasswords(order: PasswordOrder) {
        getPasswordsJob?.cancel()
        getPasswordsJob = passwordUseCases.getPasswordUseCases(order).onEach { passwords ->
            _state.value = state.value.copy(
                passwords = passwords,
                passwordOrder = order
            )
        }.launchIn(viewModelScope)

    }

}