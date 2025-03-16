package com.example.passvault.features.password.domain.util.order_type

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}