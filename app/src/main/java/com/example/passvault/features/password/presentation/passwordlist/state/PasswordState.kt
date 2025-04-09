package com.example.passvault.features.password.presentation.passwordlist.state

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.model.OrderType

data class PasswordState(
    val passwords : List<Password> = emptyList(),
    val orderType: OrderType = OrderType.Descending,
)