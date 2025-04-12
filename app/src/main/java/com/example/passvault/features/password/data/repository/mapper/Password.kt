package com.example.passvault.features.password.data.repository.mapper

import com.example.passvault.features.password.data.database.model.PasswordEntity
import com.example.passvault.features.password.domain.model.Password

fun PasswordEntity.toPassword() = Password(
    id = id,
    password = password,
    label = label
)

fun Password.toEntity() = PasswordEntity(
    id = id,
    password = password,
    label = label
)
