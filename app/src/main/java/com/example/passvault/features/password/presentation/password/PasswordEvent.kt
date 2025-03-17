package com.example.passvault.features.password.presentation.password

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.util.order_type.PasswordOrder


sealed class PasswordEvent {
    data class Order(val passwordOrder: PasswordOrder): PasswordEvent()
    data class DeletePassword(val password: Password): PasswordEvent()
    object  RestorePassword: PasswordEvent()
    object ToggleOrderSection: PasswordEvent()

}