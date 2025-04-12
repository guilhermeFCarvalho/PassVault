package com.example.passvault.features.password.presentation.addpassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passvault.features.password.domain.model.InvalidPasswordException
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.usecase.PasswordUseCases
import com.example.passvault.features.password.presentation.addpassword.event.AddPasswordEvent
import com.example.passvault.features.password.presentation.addpassword.state.PasswordTextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddPasswordViewModel(
    private val passwordUseCases: PasswordUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(PasswordTextFieldState())
    val state: State<PasswordTextFieldState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()

    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPasswordId: Int? = null

    init {
        savedStateHandle.get<Int>("passwordId")?.let { passwordId ->
            if (passwordId != -1) {
                viewModelScope.launch {
                    passwordUseCases.getSinglePasswordUseCase(passwordId)?.also {
                        currentPasswordId = it.id
                        _state.value = _state.value.copy(
                            password = it.password,
                            label = it.label
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddPasswordEvent) {
        when (event) {
            is AddPasswordEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.value)
            }

            is AddPasswordEvent.LabelChanged -> {
                _state.value = _state.value.copy(label = event.value)
            }

            is AddPasswordEvent.SavePassword -> {
                viewModelScope.launch {
                    try {
                        passwordUseCases.addPasswordUseCase(
                            Password(
                                id = currentPasswordId,
                                password = state.value.password,
                                label = state.value.label
                            )
                        )

                        _eventFlow.emit(UiEvent.SavePassword)

                    } catch (e: InvalidPasswordException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: ""))
                    }
                }

            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data object SavePassword : UiEvent()
    }
}