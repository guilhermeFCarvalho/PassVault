package com.example.passvault.features.password.domain.use_case

data class PasswordUseCases (
    val getPasswordUseCases: GetPasswordsUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase
)