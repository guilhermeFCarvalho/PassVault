package com.example.passvault.features.password.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Password")
data class PasswordEntity(
    val password: String,
    val label: String,
    @PrimaryKey val id: Int? = null
)