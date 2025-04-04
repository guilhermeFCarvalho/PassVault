package com.example.passvault.features.password.presentation.password_list

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.util.order_type.OrderType

data class PasswordState(
    val passwords : List<Password> = emptyList(),
    val orderType: OrderType = OrderType.Descending,
)
