package com.example.passvault.features.password.data

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FakePasswordRepository : PasswordRepository {
    private val passwordsFlow = MutableStateFlow<List<Password>>(emptyList())

    override fun getPasswords(): Flow<List<Password>> = passwordsFlow.asStateFlow()

    override suspend fun getPasswordById(id: Int): Password? {
        return passwordsFlow.value.find { it.id == id }
    }

    override suspend fun insertPassword(password: Password) {
        passwordsFlow.value = passwordsFlow.value + password
    }

    override suspend fun deletePassword(password: Password) {
        passwordsFlow.value = passwordsFlow.value - password
    }
}

class PasswordRepositoryTest {
    private val repository: PasswordRepository = FakePasswordRepository()

    @Test
    fun `insets password correctly`() = runTest {
        val password = Password(password = "12345", label = "Netflix")
        repository.insertPassword(password)

        val passwords = repository.getPasswords().first()

        assertEquals(1, passwords.size)
        assertEquals(password, passwords.first())
    }

    @Test
    fun `getPasswordById returns correct password`() = runTest {
        val password = Password(id = 1, password = "12345", label = "Netflix")
        repository.insertPassword(password)

        val result = repository.getPasswordById(1)
        assertEquals(password, result)
    }

    @Test
    fun `getPasswordById returns null if password does not exist`() = runTest {
        val result = repository.getPasswordById(99)
        assertEquals(result, null)
    }

    @Test
    fun `deletePassword removes password from repository`() = runTest {
        val password = Password(password = "12345", label = "Netflix")
        repository.insertPassword(password)
        repository.deletePassword(password)

        val passwords = repository.getPasswords().first()
        assertEquals(0, passwords.size)
    }

}