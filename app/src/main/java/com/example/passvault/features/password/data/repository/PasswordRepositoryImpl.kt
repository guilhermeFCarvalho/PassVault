package com.example.passvault.features.password.data.repository

import com.example.passvault.features.password.data.database.PasswordDao
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class PasswordRepositoryImpl(private val dao: PasswordDao) : PasswordRepository {
    override fun getPasswords(): Flow<List<Password>> {
        return dao.getPasswords()
    }

    override suspend fun getPasswordById(id: Int): Password? {
        return dao.getPasswordById(id = id)
    }

    override suspend fun insertPassword(password: Password) {
        dao.insertPassword(password = password)
    }

    override suspend fun deletePassword(password: Password) {
        dao.deletePassword(password = password)
    }
}