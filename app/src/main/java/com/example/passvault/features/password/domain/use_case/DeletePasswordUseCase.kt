package com.example.passvault.features.password.domain.use_case

import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository

class DeletePasswordUseCase( private val passwordRepository: PasswordRepository) {

    suspend operator fun invoke(password: Password){
        passwordRepository.deletePassword(password)
    }
}