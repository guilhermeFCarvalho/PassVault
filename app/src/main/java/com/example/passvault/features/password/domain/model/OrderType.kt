package com.example.passvault.features.password.domain.model

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}