package com.example.passvault.features.password.domain.model

data class Password(
    val password: String,
    val label: String,
    val id: Int? = null
)
