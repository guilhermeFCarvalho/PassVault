package com.example.passvault.features.password.domain.model

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}