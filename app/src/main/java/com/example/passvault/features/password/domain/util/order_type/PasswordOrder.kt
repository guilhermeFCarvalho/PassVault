package com.example.passvault.features.password.domain.util.order_type

sealed class PasswordOrder(val orderType: OrderType) {
    class Label(orderType: OrderType) : PasswordOrder(orderType)

}