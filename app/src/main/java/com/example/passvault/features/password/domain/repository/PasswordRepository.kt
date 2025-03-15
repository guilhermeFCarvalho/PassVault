package com.example.passvault.features.password.domain.repository

import com.example.passvault.features.password.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getPasswords(): Flow<List<Password>>

    suspend fun getPasswordById(id: Int): Password?

    suspend fun insertPassword(password: Password)

    suspend fun deletePassword(password: Password)

}