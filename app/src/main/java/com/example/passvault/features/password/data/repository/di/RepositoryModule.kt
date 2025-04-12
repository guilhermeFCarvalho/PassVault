package com.example.passvault.features.password.data.repository.di

import com.example.passvault.features.password.data.repository.PasswordRepositoryImpl
import com.example.passvault.features.password.domain.repository.PasswordRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PasswordRepositoryImpl) bind PasswordRepository::class
}