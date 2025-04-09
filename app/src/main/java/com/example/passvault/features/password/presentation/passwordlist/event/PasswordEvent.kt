package com.example.passvault.features.password.presentation.passwordlist.event

import com.example.passvault.features.password.domain.model.Password

sealed class PasswordEvent {
    data class DeletePassword(val password: Password): PasswordEvent()
    data object  RestorePassword: PasswordEvent()
    data object OrderPasswords: PasswordEvent()
}