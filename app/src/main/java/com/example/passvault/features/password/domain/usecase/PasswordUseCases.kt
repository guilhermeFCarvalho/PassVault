package com.example.passvault.features.password.domain.usecase

data class PasswordUseCases(
    val getPasswordsUseCases: GetPasswordsUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val addPasswordUseCase: AddPasswordUseCase,
    val getSinglePasswordUseCase: GetSinglePasswordUseCase,
)