package com.example.passvault.features.password.presentation.password

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.util.order_type.OrderType
import com.example.passvault.features.password.domain.util.order_type.PasswordOrder

data class PasswordState(
    val passwords : List<Password> = emptyList(),
    val passwordOrder: PasswordOrder = PasswordOrder.Label(OrderType.Ascending),
    val isOrderSectionVisible : Boolean = false
)
