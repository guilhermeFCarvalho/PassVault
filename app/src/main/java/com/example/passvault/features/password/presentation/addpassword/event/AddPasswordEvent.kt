package com.example.passvault.features.password.presentation.addpassword.event

sealed class AddPasswordEvent {

    data class PasswordChanged(val value: String) : AddPasswordEvent()
    data class LabelChanged(val value: String) : AddPasswordEvent()

    object SavePassword : AddPasswordEvent()
}