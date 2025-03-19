package com.example.passvault.features.password.domain.use_case

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository

class GetSinglePasswordUseCase(private val passwordRepository: PasswordRepository) {
    suspend operator fun invoke(id: Int): Password? {
        return passwordRepository.getPasswordById(id)
    }
}