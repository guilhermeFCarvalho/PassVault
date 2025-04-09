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

    private val _password = mutableStateOf(PasswordTextFieldState())
    val password: State<PasswordTextFieldState> = _password

    private val _label = mutableStateOf(PasswordTextFieldState())
    val label: State<PasswordTextFieldState> = _label

    private val _eventFlow = MutableSharedFlow<UiEvent>()

    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPasswordId: Int? = null

    init {
        savedStateHandle.get<Int>("passwordId")?.let { passwordId ->
            if (passwordId != -1) {
                viewModelScope.launch {
                    passwordUseCases.getSinglePasswordUseCase(passwordId)?.also {
                        currentPasswordId = it.id
                        _password.value = password.value.copy(
                            password = it.password
                        )
                        _label.value = label.value.copy(
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
                _password.value = password.value.copy(password = event.value)
            }

            is AddPasswordEvent.LabelChanged -> {
                _label.value = label.value.copy(label = event.value)
            }

            is AddPasswordEvent.SavePassword -> {
                viewModelScope.launch {
                    try {
                        passwordUseCases.addPasswordUseCase(
                            Password(
                                id = currentPasswordId,
                                password = password.value.password,
                                label = label.value.label
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
        object SavePassword : UiEvent()
    }


}