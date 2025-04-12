package com.example.passvault.features.password.data.repository

import com.example.passvault.features.password.data.database.PasswordDao
import com.example.passvault.features.password.data.database.model.PasswordEntity
import com.example.passvault.features.password.data.repository.mapper.toEntity
import com.example.passvault.features.password.data.repository.mapper.toPassword
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordRepositoryImpl(private val dao: PasswordDao) : PasswordRepository {

    override fun getPasswords(): Flow<List<Password>> {
        return dao.getPasswords().map { passwords ->
            passwords.map(PasswordEntity::toPassword)
        }
    }

    override suspend fun getPasswordById(id: Int): Password? {
        return dao.getPasswordById(id = id)?.toPassword()
    }

    override suspend fun insertPassword(password: Password) {
        dao.insertPassword(
            password = password.toEntity()
        )
    }

    override suspend fun deletePassword(password: Password) {
        dao.deletePassword(
            password = password.toEntity()
        )
    }
}