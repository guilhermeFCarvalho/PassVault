package com.example.passvault.features.password.domain.use_case

data class PasswordUseCases(
    val getPasswordsUseCases: GetPasswordsUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val addPasswordUseCase: AddPasswordUseCase,
    val getSinglePasswordUseCase: GetSinglePasswordUseCase,
)