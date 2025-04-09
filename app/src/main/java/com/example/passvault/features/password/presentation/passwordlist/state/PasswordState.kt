package com.example.passvault.features.password.presentation.passwordlist.state

import com.example.passvault.features.password.domain.model.OrderType
import com.example.passvault.features.password.domain.model.Password

data class PasswordState(
    val passwords: List<Password> = emptyList(),
    val orderType: OrderType = OrderType.Descending,
) {
    data class Password(
        val password: String,
        val label: String,
        val id: Int
    )
}

fun PasswordState(
    passwords: List<Password>,
    orderType: OrderType = OrderType.Descending,
): PasswordState {

    return PasswordState(
        passwords = passwords.map {
            PasswordState.Password(
                password = it.password,
                label = it.label,
                id = checkNotNull(it.id)
            )
        },
        orderType = orderType
    )
}