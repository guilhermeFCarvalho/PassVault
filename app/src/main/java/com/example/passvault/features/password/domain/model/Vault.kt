package com.example.passvault.features.password.domain.model

import androidx.room.Entity

@Entity
data class Vault(
    val label: String,
    val passwords: List<Password>
)
