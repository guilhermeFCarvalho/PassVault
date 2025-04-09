package com.example.passvault.features.password.presentation.passwordlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passvault.features.password.domain.model.OrderType
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.usecase.PasswordUseCases
import com.example.passvault.features.password.presentation.passwordlist.event.PasswordEvent
import com.example.passvault.features.password.presentation.passwordlist.state.PasswordState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class PasswordListViewModel(
    private val passwordUseCases: PasswordUseCases
) : ViewModel() {

    private val orderType = MutableStateFlow<OrderType>(OrderType.Descending)

    val state = orderType.flatMapLatest { order ->
        passwordUseCases.getPasswordsUseCases(order).map { passwords ->
            PasswordState(
                passwords = passwords,
                orderType = order
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PasswordState()
    )

    private var lastDeletedPassword: Password? = null

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
                orderType.value = when (state.value.orderType) {
                    OrderType.Ascending -> OrderType.Descending
                    OrderType.Descending -> OrderType.Ascending
                }
            }
        }
    }
}
