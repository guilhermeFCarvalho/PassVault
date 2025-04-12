package com.example.passvault.features.password.presentation.passwordlist.event

sealed class PasswordEvent {
    data class DeletePassword(val id: Int) : PasswordEvent()
    data object RestorePassword : PasswordEvent()
    data object OrderPasswords : PasswordEvent()
}