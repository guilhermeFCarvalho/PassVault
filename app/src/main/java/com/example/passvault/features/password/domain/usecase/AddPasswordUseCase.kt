package com.example.passvault.features.password.domain.usecase

import com.example.passvault.features.password.domain.model.InvalidPasswordException
import com.example.passvault.features.password.domain.model.Password
import com.example.passvault.features.password.domain.repository.PasswordRepository
import kotlin.jvm.Throws

class AddPasswordUseCase(private val repository: PasswordRepository) {

    @Throws(InvalidPasswordException::class)
    suspend operator fun invoke(password: Password){
        if(password.password.isBlank()){
            throw InvalidPasswordException("A senha deve ser preenchida")

        }
        if(password.label.isBlank()){
            throw InvalidPasswordException("Dê um nome a senha para lembrar dela depois")
        }
        repository.insertPassword(password)
    }
}