package com.example.passvault.features.password.presentation.passwordlist.di

import com.example.passvault.features.password.presentation.passwordlist.PasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val passwordListModule = module {
    viewModelOf(::PasswordViewModel)
}