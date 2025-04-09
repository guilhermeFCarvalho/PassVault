package com.example.passvault.features.password.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    val password: String,
    val label:  String,
    @PrimaryKey val id: Int? = null
)

class InvalidPasswordException(message: String): Exception(message)
