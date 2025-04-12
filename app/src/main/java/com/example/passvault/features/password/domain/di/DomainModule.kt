package com.example.passvault.features.password.domain.di

import com.example.passvault.features.password.domain.usecase.AddPasswordUseCase
import com.example.passvault.features.password.domain.usecase.DeletePasswordUseCase
import com.example.passvault.features.password.domain.usecase.GetPasswordsUseCase
import com.example.passvault.features.password.domain.usecase.GetSinglePasswordUseCase
import com.example.passvault.features.password.domain.usecase.PasswordUseCases
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::PasswordUseCases)
    singleOf(::GetPasswordsUseCase)
    singleOf(::DeletePasswordUseCase)
    singleOf(::AddPasswordUseCase)
    singleOf(::GetSinglePasswordUseCase)
}